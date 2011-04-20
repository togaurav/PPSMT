package com.ppstream.mt.entity;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {
	
}
