package com.ppstream.mt.dao;

import java.util.List;

public interface BaseDao {

	public <T> List<T> findBySql(final String sql, final Class<T> entityType,final Object... args);
	
	public <T> List<T> findByHql(final String hql, final Object... args);
}
