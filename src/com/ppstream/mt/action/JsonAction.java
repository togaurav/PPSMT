package com.ppstream.mt.action;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.ppstream.mt.service.AuthorityService;

public class JsonAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private AuthorityService authorityService;
	
	private Integer privilegeId;
	private Integer sortIndex;
	private Integer cateId;
	
	private String jsonData;
	
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
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
			@Result(name="success", location="/jsonData.jsp")  
        }
	)
	public String editCateSortIndex() throws Exception{
		authorityService.editPrivilegeCateSortIndex(cateId,sortIndex);
//		JSONObject json = new JSONObject();
//		json.put("success", true);
//		setJsonData(json.toString());
		return SUCCESS;
	}
	
	
	// 修改资源排序
	@Action(value="editPrivilegeSortIndex",
		results={
			@Result(name="success", location="/jsonData.jsp")  
        }
	)
	public String editPrivilegeSortIndex() throws Exception{
		authorityService.editPrivilegeSortIndex(privilegeId,sortIndex);
//		JSONObject json = new JSONObject();
//		json.put("success", true);
//		setJsonData(json.toString());
		return SUCCESS; 
	}

}
