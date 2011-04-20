package com.ppstream.mt.gsonmodel;

import java.util.List;

public class GPrivilegeType {

	private String typeName;
	private List<GPrivilegeCate> privilegeCates;
	
	public GPrivilegeType(String typeName,List<GPrivilegeCate> privilegeCates){
		this.typeName = typeName;
		this.privilegeCates = privilegeCates;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<GPrivilegeCate> getPrivilegeCates() {
		return privilegeCates;
	}

	public void setPrivilegeCates(List<GPrivilegeCate> privilegeCates) {
		this.privilegeCates = privilegeCates;
	}
	
	
}
