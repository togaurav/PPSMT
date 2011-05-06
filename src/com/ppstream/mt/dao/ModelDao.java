package com.ppstream.mt.dao;

import java.io.Serializable;
import java.util.List;


/**
 * DAO层封装接口  [针对实体T]
 * @author laupeng
 */

public interface ModelDao<T> {

	/**
     * 根据属性查找对象
     * @param propertyName  属性（对应Bean）
     * @param value 属性
     * @return 根据属性查找对象
     */
    public List<T> findByProperty(String propertyName, Object value);

 

    /**
     * 根据实体查找对象
     * @param entiey 实体（T类型）
     * @return 根据属性查找对象
     */

    public List<T> findByEntity(Object entiey);

 

    /**
     * 获取记录总数
     * @param entityClass 实体类
     * @return
     */

    public int getCount();

 

    /**
     * 保存实体
     * @param entity 实体id
     */

    public void save(Object entity);

 

    /**
     * 更新实体
     * @param entity 实体id
     */

    public void update(Object entity);

 
    /**
     * 删除实体
     * @param entityClass 实体类
     * @param entityids 实体id数组
     */

    public void delete(Serializable... entityids);

    /**
     * 获取实体
     * @param <T>
     * @param entityClass 实体类
     * @param entityId 实体id
     * @return
     */

    public T find(Serializable entityId);
 
	
}
