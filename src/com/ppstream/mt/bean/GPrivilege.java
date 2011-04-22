package com.ppstream.mt.bean;

import java.io.Serializable;

public class GPrivilege implements Serializable{
	
	private String name;
	private Integer showNav;
	private String action;
	
	public GPrivilege(String name,String action,Integer showNav){
		this.name = name;
		this.action = action;
		this.showNav = showNav;
	}
	
	

	public Integer getShowNav() {
		return showNav;
	}



	public void setShowNav(Integer showNav) {
		this.showNav = showNav;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	

}
