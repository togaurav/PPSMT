package com.ppstream.mt.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.ppstream.mt.utils.StringUtil;

public class BaseEntityListener {

	/**
	 * 插入之前
	 * 
	 * @param ent
	 */
	@PrePersist
	public synchronized void preInsertDB(BaseEntity ent) {
		// GUID
		if (StringUtil.isEmpty(ent.guid)) {
			ent.guid = UUID.randomUUID().toString();
		}

		// 创建时间
		Date date = Calendar.getInstance().getTime();
		ent.createTime = date;
		ent.modifyTime = date;
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
		// 修改时间
		Date date = Calendar.getInstance().getTime();
		ent.modifyTime = date;
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
