package com.ppstream.mt.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.ppstream.mt.test.BaseTest;

public class UserServiceTest extends BaseTest{
	@Resource
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void testSaveUser(){
		userService.addOrUpdateUser(null, "测试三", "123456", 
				"laupeng@126.com", "288", 0, 2, "测试三", "861");
	}
}
