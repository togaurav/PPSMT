package com.ppstream.mt.entity;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class UserEntityListener {
	
	
	@PostPersist
	@PostUpdate
	public synchronized void postPersist(User user) {
		
	}

}
