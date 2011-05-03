package com.ppstream.mt.dao;

import java.io.Serializable;
import java.util.List;

import com.ppstream.mt.utils.pager.TbData;

public interface BaseDao {
	
	public void save(Object entity);
	
	public void update(Object entity);
	
	public void refresh(Object entity);
	
	public void delete(Object entity);
	
	public void saveOrUpdate(Object entity);
	
	public <T> T load(Class<T> entity, Serializable id);
	
	public <T> T get(Class<T> entity, Serializable id);

	public <T> List<T> findByNativeSql(final String sql, final Class<T> entityType,final Object... args);
	
	public <T> List<T> findByHql(final String hql, final Object... args);
	
	public <T> TbData runNativeSql(final Class<T>[] entityClz,
			final int pageSize, final int pageNo, final String sql,
			final Object... args);
	
	public int getRows(String hql);
	
	public TbData runHQL(final int totalCount,final int pageSize, final int pageNo, final String hql,final Object... args);
}
