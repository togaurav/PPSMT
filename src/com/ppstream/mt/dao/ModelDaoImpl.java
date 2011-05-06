package com.ppstream.mt.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ppstream.mt.utils.GenericsUtils;

@Transactional()
@Repository("modelDao")
public class ModelDaoImpl<T> extends HibernateDaoSupport implements ModelDao<T> {
	
	// 为父类HibernateDaoSupport注入sessionFactory的值
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected String entityClassName = getEntityName(this.entityClass);

	protected String keyFieldName = getKeyFieldName(this.entityClass);

	/** 
	 * @see ModelDao#findByEntity(java.lang.Object)
	 */

	public List<T> findByEntity(Object entiey){
		return super.getHibernateTemplate().findByExample(entiey);
	}

	/** 
	 * @see ModelDao#findByProperty(java.lang.String, java.lang.Object)
	 */

	public List<T> findByProperty(String propertyName, Object value){
		String queryString = "from " + entityClassName + " o where o." + propertyName + "= ?";
		return super.getHibernateTemplate().find(queryString, value);
	}

	/** 
	 * @see ModelDao#delete(java.io.Serializable[])
	 */
	public void delete(Serializable... entityids){
		for (Object id : entityids){
			super.getHibernateTemplate().delete(find((Serializable) id));
		}
	}

	/** 
	 * @see ModelDao#find(java.io.Serializable)
	 */

	public T find(Serializable entityId){
		if (null != entityId)
			return (T) super.getHibernateTemplate().get(entityClass, entityId);
		return null;
	}

	/** 
	 * @see ModelDao#getCount()
	 */
	public int getCount(){
		String hql = "select count( " + keyFieldName + ") from " + entityClassName;
		int count = Integer.parseInt(super.getHibernateTemplate().find(hql).get(0).toString());
		return count;
	}

	/** 
	 * @see ModelDao#save(java.lang.Object)
	 */
	public void save(Object entity){
		super.getHibernateTemplate().save(entity);
	}

	/** 
	 * @see ModelDao#update(java.lang.Object)
	 */
	public void update(Object entity){
		super.getHibernateTemplate().update(entity);
	}


	/**
	 * 
	 * 获取实体的名称
	 * @param <E>
	 * @param clazz  实体类
	 * @return
	 */
	protected static <E> String getEntityName(Class<E> clazz){
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity != null && entity.name() != null && !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}

	/**
	 * 
	 * 获取实体的主键
	 * @param <E>
	 * @param clazz 实体类
	 * @return 主键名
	 */

	protected static <E> String getKeyFieldName(Class<E> clazz){
		try{
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors){
				Method method = propertydesc.getReadMethod();
				if (null != method && null != method.getAnnotation(javax.persistence.Id.class))
				{
					return propertydesc.getName();
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return "id";

	}

	/**
	 * 设置HQL里边的属性值
	 * @param query
	 * @param queryParams
	 */

	protected static void setQueryParams(Query query, Object[] queryParams){
		if (queryParams != null && queryParams.length > 0){
			for (int i = 0; i < queryParams.length; i++){
				query.setParameter(i, queryParams[i]);// 从0开始
			}
		}
	}

}
