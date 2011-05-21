package com.ppstream.mt.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.ppstream.mt.email.SpringEmailService;

public class EmailTest extends BaseTest {

	@Resource
	private SpringEmailService springEmailService;

	public SpringEmailService getSpringEmailService() {
		return springEmailService;
	}

	public void setSpringEmailService(SpringEmailService springEmailService) {
		this.springEmailService = springEmailService;
	}

	@Test
	public void secondMailTest(){
		springEmailService.sendAMessage("spring email","spring发送email");
	}
}
