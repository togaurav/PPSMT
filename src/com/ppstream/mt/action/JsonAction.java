package com.ppstream.mt.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.ppstream.mt.service.AuthorityService;

@ParentPackage("json-default")
public class JsonAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private AuthorityService authorityService;
	
	private Integer privilegeId;
	private Integer sortIndex;
	private Integer cateId;
	private boolean success;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public Integer getCateId() {
		return cateId;
	}
	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}
	public AuthorityService getAuthorityService() {
		return authorityService;
	}
	@Resource  
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}
	
	
	// 修改资源分类排序
	@Action(value="editCateSortIndex",
		results={
			@Result(name="success", type="json")  
        }
	)
	public String editCateSortIndex() throws Exception{
		authorityService.editPrivilegeCateSortIndex(cateId,sortIndex);
		success = true;
		return SUCCESS;
	}
	
	
	// 修改资源排序
	@Action(value="editPrivilegeSortIndex",
		results={
			@Result(name="success", type="json")  
        }
	)
	public String editPrivilegeSortIndex() throws Exception{
		authorityService.editPrivilegeSortIndex(privilegeId,sortIndex);
		success = true;
		return SUCCESS;
	}

}
