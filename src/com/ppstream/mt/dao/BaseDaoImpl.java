package com.ppstream.mt.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Level;

import com.ppstream.mt.utils.pager.PgInfo;
import com.ppstream.mt.utils.pager.SqlRow;
import com.ppstream.mt.utils.pager.TbData;

@Repository("baseDao")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{

	@Autowired
	public void fillUpSessionFactory(SessionFactory sf) {
		super.setSessionFactory(sf);
	}
	
	@Resource
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public void save(Object entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void refresh(Object entity) {
		getHibernateTemplate().refresh(entity);
	}

	@Override
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}
	
	@Override
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T load(Class<T> entity, Serializable id) {
		return (T) getHibernateTemplate().load(entity, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> entity, Serializable id) {
		return (T) getHibernateTemplate().get(entity, id);
	}
	
	/**
	 ****************************************************************************** 基于NativeSQL 或 HQL 的非分页查询*********/
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByNativeSql(final String sql, final Class<T> entityType,
			final Object... args) {
		return (List<T>) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery q = session.createSQLQuery(sql).addEntity(entityType);
//						q.setCacheable(true);
						if (args != null) {
							for (int i = 0; i < args.length; i++) {
								q.setParameter(i, args[i]);
							}
						}
						return q.list();
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByHql(final String hql, final Object... args) {
		return (List<T>) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(hql);
//						q.setCacheable(true);
						if (args != null) {
							for (int i = 0; i < args.length; i++) {
								q.setParameter(i, args[i]);
							}
						}
						return q.list();
					}
				});
	}
	
	
	/**
	 ****************************************************************************** 基于NativeSQL分页查询*********/
	
	private final static Pattern PTN_ORDERBY = Pattern.compile("order\\s+by");

	private static String trimOrderBy(String sql) {
		Matcher mtchr = PTN_ORDERBY.matcher(sql.toLowerCase());
		boolean b = mtchr.find();
		if (!b) {
			return sql;
		}
		int pos = mtchr.start();
		return sql.substring(0, pos);
	}
	
	private long queryCount(final String sql, final Object... args) {
		String s2 = sql;
		int pos = s2.toLowerCase().indexOf(" from ");
		s2 = "select count(*) " + s2.substring(pos);
		s2 = trimOrderBy(s2);   // select count(*) from...不能带order by 语句

		final String countSql = s2;
		Object result = getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						SQLQuery q = session.createSQLQuery(countSql);

						if (args != null) {
							for (int i = 0; i < args.length; i++) {
								q.setParameter(i, args[i]);
							}
						}
						return q.uniqueResult();
					}
				});

		return ((BigInteger) result).longValue();
	}
	
	@Override
	public <T> TbData runNativeSql(final Class<T>[] entityClz,
			final int pageSize, final int pageNo, final String sql,
			final Object... args) {

		final int totalCount = (int) queryCount(sql, args);// 查询总记录条数。

		Object result = getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery q = session.createSQLQuery(sql);
						for (int i = 0; i < entityClz.length; i++) {
							if (entityClz[i] == null) {
								break;
							}
							q.addEntity(entityClz[i]);
						}
						if (args != null) {
							for (int i = 0; i < args.length; i++) {
								q.setParameter(i, args[i]);
							}
						}

						int innerPageSize = pageSize;
						if (innerPageSize > 200) {		// 最多查200条
							innerPageSize = 200;
						} else if (innerPageSize < 1) {
							innerPageSize = 1;
						}
						q.setFirstResult((pageNo - 1) * innerPageSize); // 设置分页起始记录号
						q.setMaxResults(innerPageSize); // 设置页内数据量

						PgInfo pageInfo = new PgInfo();
						pageInfo.setTotalCount(totalCount); // 总共记录数
						pageInfo.setPageSize(innerPageSize); // 每页大小
						pageInfo.setFirst((pageNo == 1 ? true : false)); // 是否为第一页   
						int sumPage = (int) Math.ceil((double) totalCount
								/ innerPageSize); // 设置总页数
						if (sumPage == 0) { // 是否为最后一页
							pageInfo.setLast(true);
						} else {
							pageInfo.setLast((pageNo == sumPage ? true
									: false));
						}
						pageInfo.setSumPage(sumPage); // 总页数
						if (sumPage == 0) {
							pageInfo.setPageNo(0); // 当前页
						} else {
							pageInfo.setPageNo(pageNo); // 当前页
						}

						List<?> list0 = q.list();

						TbData data = new TbData();
						List l2 = new ArrayList(list0.size());
						for (int i = 0; i < list0.size(); i++) {
							Object obj = list0.get(i);
							if (obj instanceof Object[]) {
								SqlRow row = new SqlRow();
								Object[] arr = (Object[]) obj;
								for (int x = 0; x < arr.length; x++) {
									if (x == 0) {
										row.setA(arr[x]);
									} else if (x == 1) {
										row.setB(arr[x]);
									} else if (x == 2) {
										row.setC(arr[x]);
									}
								}
								l2.add(row);
							} else {
								l2.add(obj);
							}
						}
						data.setList(l2);
						data.setPageInfo(pageInfo);
						return data;
					}
				});

		return (TbData) result;
	}
	
	/**
	 ****************************************************************************** 基于HQL分页查询*********/
	@Override
	public int getRows(String hql){  
		int size = getHibernateTemplate().find(hql).size();
        return size;  
    }
	
	@Override
	public TbData runHQL(final int totalCount,final int pageSize, final int pageNo, final String hql,final Object... args){
		Object result = getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						Query q = session.createQuery(hql);
						if (args != null) {
							for (int i = 0; i < args.length; i++) {
								q.setParameter(i, args[i]);
							}
						}

						int innerPageSize = pageSize;
						if (innerPageSize > 200) {		// 最多查200条
							innerPageSize = 200;
						} else if (innerPageSize < 1) {
							innerPageSize = 1;
						}
						q.setFirstResult((pageNo - 1) * innerPageSize); // 设置分页起始记录号
						q.setMaxResults(innerPageSize); // 设置页内数据量
						
						PgInfo pageInfo = new PgInfo();
						pageInfo.setTotalCount(totalCount); // 总共记录数
						pageInfo.setPageSize(innerPageSize); // 每页大小
						pageInfo.setFirst((pageNo == 1 ? true : false)); // 是否为第一页   
						int sumPage = (int) Math.ceil((double) totalCount
								/ innerPageSize); // 设置总页数
						if (sumPage == 0) { // 是否为最后一页
							pageInfo.setLast(true);
						} else {
							pageInfo.setLast((pageNo == sumPage ? true
									: false));
						}
						pageInfo.setSumPage(sumPage); // 总页数
						if (sumPage == 0) {
							pageInfo.setPageNo(0); // 当前页
						} else {
							pageInfo.setPageNo(pageNo); // 当前页
						}
						
						List<?> list = q.list();
						
						TbData data = new TbData();
						
						data.setList(list);
						data.setPageInfo(pageInfo);
						return data;
					}
				});
		return (TbData)result;
	}
	
	
	
	
	/**
	 * 调用存储过程  【TEST】
	 * @param procudure 存储过程,eg :  "{Call COMMON_PKG.GET_SAMPLE_DETAIL(?,?)}"
	 * @param args 参数
	 * @return  Map 集合
	 * @throws SQLException
	 */
//	public Map callProcedure(final String procudure,final Object... args) throws SQLException {
//        Map obj = (Map) jdbcTemplate.execute(new ConnectionCallback() {
//
//            public Map doInConnection(Connection conn) throws SQLException, DataAccessException {
//                // 样本量Map
//                Map result = new HashMap();
//                CallableStatement cs = conn.prepareCall(procudure);
//                cs.setInt(1, 10);
//                cs.registerOutParameter(2, OracleTypes.CURSOR);
//                cs.execute();
//                ResultSet rs = (ResultSet) cs.getObject(2);
//                while (rs.next()) {
//                	result.put(rs.getString("DATE_ID"), rs.getString("USER_NUM"));
//                }
//                return result;
//            }
//        });
//        return obj;
//    }
	
}
