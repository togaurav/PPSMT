package com.ppstream.mt.entity;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {

	/**
	 * 插入之前
	 * 
	 * @param ent
	 */
	@PrePersist
	public synchronized void preInsertDB(BaseEntity ent) {
		
	}

	@PostPersist
	public synchronized void postPersist(BaseEntity ent) {
		
	}

	/**
	 * 更新之前
	 * 
	 * @param ent
	 */
	@PreUpdate
	public synchronized void preUpdateDB(BaseEntity ent) {
		
	}

	/**
	 * 更新之后
	 * 
	 * @param ent
	 */
	@PostUpdate
	public synchronized void postUpdateDB(BaseEntity ent) {

	}

	@PostRemove
	public synchronized void postRemoveDB(BaseEntity ent) {

	}

}
