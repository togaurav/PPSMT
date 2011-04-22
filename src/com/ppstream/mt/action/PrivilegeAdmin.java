package com.ppstream.mt.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.service.AuthorityService;

public class PrivilegeAdmin extends BaseAction {

	@Autowired  
	private AuthorityService authorityService;
	
	private Integer roleId;
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	// 二级权限列表
	@Action(value="privilegeCateList",
			results={
				@Result(name="success", location="/authority/privilegeCateList.jsp" )  
	        }
		)
    public String privilegeCateList() throws Exception { 
		List<PrivilegeCate> privilegeCates = authorityService.getPrivilegeCateList();
		request.setAttribute("privilegeCates", privilegeCates);
		return SUCCESS;
	}
	
	// 角色列表
	@Action(value="roleList",
			results={
				@Result(name="success", location="/authority/roleList.jsp" )  
	        }
		)
    public String roleList() throws Exception { 
		List<Role> roles = authorityService.getRoleList();
		request.setAttribute("roles", roles);
		return SUCCESS;
	}
	
	// 设置角色权限
	@Action(value="configRolePrivilege",
			results={
				@Result(name="success", location="/authority/configRolePrivilege.jsp" )  
	        }
		)
	public String configRolePrivilege() throws Exception{
		List<PrivilegeCate> privilegeCates = authorityService.getPrivilegeCateList();
		request.setAttribute("privilegeCates", privilegeCates);
		return SUCCESS;
	}
	
}
