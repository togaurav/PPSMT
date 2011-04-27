package com.ppstream.mt.action;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Component;

import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.service.AuthorityService;

@Component("roleAdmin") 
public class RoleAdmin extends BaseAction{

	private static final long serialVersionUID = 1L;

	private AuthorityService authorityService;
	
	private Integer roleId;
	private String roleName; 
	private String privilegeIds;
	
	public AuthorityService getAuthorityService() {
		return authorityService;
	}
	@Resource  
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	
	// 增加角色
	@Action(value="addRole",
			results={
				@Result(name="success", type="redirectAction",location = "roleList.action")
	        }
		)
	public String addRole() throws Exception{
		authorityService.addRole(roleName);
		return SUCCESS;
	}
	
	// 编辑角色页面
	@Action(value="editRoleView",
			results={
				@Result(name="success",location = "/authority/editRoleView.jsp")
	        }
		)
	public String editRoleView() throws Exception{
		Role role = authorityService.getRole(roleId);
		request.setAttribute("role", role);
		return SUCCESS;
	}
	
	// 编辑角色
	@Action(value="editRole",
			results={
				@Result(name="success",type="redirectAction",location = "roleList.action")
	        }
		)
	public String editRole() throws Exception{
		authorityService.editRole(roleId,roleName);
		return SUCCESS;
	}
	
	// 删除角色
	@Action(value="deleteRole",
			results={
				@Result(name="success",type="redirectAction",location = "roleList.action")
	        }
		)
	public String deleteRole() throws Exception{
		authorityService.deleteRole(roleId);
		return SUCCESS;
	}
	
	
	// 设置角色权限页面
	@Action(value="assignPrivilegeToRoleView",
			results={
				@Result(name="success", location="/authority/assignPrivilegeToRoleView.jsp" )  
	        }
		)
	public String assignPrivilegeToRoleView() throws Exception{
		Set<Integer> privilegeIds = authorityService.getPrivilegeIdsByRoleId(roleId);
		List<PrivilegeCate> privilegeCates = authorityService.getPrivilegeCateList();
		request.setAttribute("privilegeCates", privilegeCates);
		request.setAttribute("privilegeIds", privilegeIds);
		return SUCCESS;
	}
	
	// 设置角色权限
	@Action(value="assignPrivilegeToRole",
			results={
				@Result(name="success", type="redirectAction",location = "roleList.action")  
	        }
		)
	public String assignPrivilegeToRole() throws Exception{
		authorityService.configRolePrivilege(roleId,privilegeIds);
		return SUCCESS;
	}
	
}
