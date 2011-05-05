package com.ppstream.mt.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.ppstream.mt.service.AuthorityService;

public class AuthorityServiceTest extends BaseTest {
	
	@Resource
	private AuthorityService authorityService;
	
	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@Test
	public void testSaveUser(){
		System.out.println(authorityService == null);
		System.out.println(authorityService.getPrivilegeTypeList());
	}

}
