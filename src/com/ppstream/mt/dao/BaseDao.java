package com.ppstream.mt.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {
	
	public void save(Object entity);
	
	public void update(Object entity);
	
	public void refresh(Object entity);
	
	public void delete(Object entity);
	
	public void saveOrUpdate(Object entity);
	
	public <T> T load(Class<T> entity, Serializable id);
	
	public <T> T get(Class<T> entity, Serializable id);

	public <T> List<T> findBySql(final String sql, final Class<T> entityType,final Object... args);
	
	public <T> List<T> findByHql(final String hql, final Object... args);
}
