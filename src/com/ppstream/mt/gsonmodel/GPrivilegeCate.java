package com.ppstream.mt.gsonmodel;

import java.util.Set;

public class GPrivilegeCate {
	
	private String cateName;
	private Set<GPrivilege> privileges;
	
	public GPrivilegeCate(String cateName,Set<GPrivilege> privileges){
		this.cateName = cateName;
		this.privileges = privileges;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Set<GPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<GPrivilege> privileges) {
		this.privileges = privileges;
	}

}
