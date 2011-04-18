package com.ppstream.mt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;
	
	 
    @Temporal(TemporalType.TIMESTAMP)
    public Date createTime;

    
    @Temporal(TemporalType.TIMESTAMP)
    public Date modifyTime;
	
    /**
     * GUID
     */
    public String guid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
