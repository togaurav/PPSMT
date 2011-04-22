package com.ppstream.mt.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional()
@Repository("baseDao")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{

	@Autowired
	public void fillUpSessionFactory(SessionFactory sf) {
		super.setSessionFactory(sf);
	}
	
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
	@Transactional(readOnly = true)
	public <T> T load(Class<T> entity, Serializable id) {
		return (T) getHibernateTemplate().load(entity, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <T> T get(Class<T> entity, Serializable id) {
		return (T) getHibernateTemplate().get(entity, id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findBySql(final String sql, final Class<T> entityType,
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
	@Transactional(readOnly = true)
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

}
