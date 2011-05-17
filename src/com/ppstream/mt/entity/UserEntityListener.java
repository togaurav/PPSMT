package com.ppstream.mt.entity;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import com.ppstream.mt.service.UserService;

public class UserEntityListener {
	
	
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@PostPersist
	@PostUpdate
	public synchronized void postPersist(User user) {
		System.out.println(user.getNickName());
		List<Group> groups = userService.getGroupList();
		System.out.println(groups.size());
	}

}
