package com.ppstream.mt.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.PrivilegeType;
import com.ppstream.mt.service.AuthorityService;

@Controller("privilegeAdmin") 
public class PrivilegeAdmin extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private AuthorityService authorityService;
	
	private Integer typeId;
	private String typeName;
	private Integer cateId;
	private String cateName;
	private Integer privilegeId;
	private String name;
	private String action;
	private Integer showNav;
	
	public AuthorityService getAuthorityService() {
		return authorityService;
	}
	@Resource  
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getShowNav() {
		return showNav;
	}

	public void setShowNav(Integer showNav) {
		this.showNav = showNav;
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
	
	// 添加大分类
	@Action(value="addPrivilegeType",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action" )  
        }
	)
	public String addPrivilegeType() throws Exception{
		authorityService.addPrivilegeType(typeName,showNav);
		return SUCCESS;
	}
	
	// 添加资源分类页面
	@Action(value="addPrivilegeCateView",
		results={
			@Result(name="success", location="/authority/addPrivilegeCate.jsp" )  
        }
	)
	public String addPrivilegeCateView() throws Exception{
		List<PrivilegeType> lists = authorityService.getPrivilegeTypeList();
		request.setAttribute("privilegeTypes", lists);
		return SUCCESS;
	}
	
	// 添加资源分类
	@Action(value="addPrivilegeCate",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action"  )  
        }
	)
	public String addPrivilegeCate() throws Exception{
		authorityService.addPrivilegeCate(typeId,cateName,showNav);
		return SUCCESS;
	}
	
	// 添加资源页面
	@Action(value="addPrivilegeView",
		results={
			@Result(name="success", location="/authority/addPrivilege.jsp" )  
        }
	)
	public String addPrivilegeView() throws Exception{
		List<PrivilegeCate> lists = authorityService.getPrivilegeCateList();
		request.setAttribute("privilegeCates", lists);
		request.setAttribute("cateId", cateId);  // 不同button触发添加,传递参数不同
		return SUCCESS;
	}
	
	// 添加资源
	@Action(value="addPrivilege",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action"  )  
        }
	)
	public String addPrivilege() throws Exception{
		authorityService.addPrivilege(cateId,name,action,showNav);
		return SUCCESS;
	}
	
	// 编辑资源类别页面
	@Action(value="editPrivilegeCateView",
		results={
			@Result(name="success", location="/authority/editPrivilegeCateView.jsp" )  
        }
	)
	public String editPrivilegeCateView() throws Exception{
		PrivilegeCate privilegeCate = authorityService.getPrivilegeCateById(cateId);
		List<PrivilegeType> lists = authorityService.getPrivilegeTypeList();
		request.setAttribute("privilegeTypes", lists);
		request.setAttribute("privilegeCate", privilegeCate);
		return SUCCESS;
	}
	
	// 编辑资源类别
	@Action(value="editPrivilegeCate",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action" )  
        }
	)
	public String editPrivilegeCate() throws Exception{
		authorityService.editPrivilegeCate(cateId,typeId,cateName,showNav);
		return SUCCESS;
	}
	
	// 删除资源分类
	@Action(value="deletePrivilegeCate",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action" )  
        }
	)
	public String deletePrivilegeCate() throws Exception{
		authorityService.deletePrivilegeCate(cateId);
		return SUCCESS;
	}
	
	// 编辑资源页面
	@Action(value="editPrivilegeView",
		results={
			@Result(name="success", location="/authority/editPrivilegeView.jsp" )  
        }
	)
	public String editPrivilegeView() throws Exception{
		Privilege privilege = authorityService.getPrivilegeById(privilegeId);
		List<PrivilegeCate> catelists = authorityService.getPrivilegeCateList();
		request.setAttribute("privilege", privilege);
		request.setAttribute("catelists", catelists);
		return SUCCESS;
	}
	
	// 编辑资源
	@Action(value="editPrivilege",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action" )  
        }
	)
	public String editPrivilege() throws Exception{
		authorityService.editPrivilege(privilegeId,cateId,name,action,showNav);
		return SUCCESS;
	}
	
	// 删除资源
	@Action(value="deletePrivilege",
		results={
			@Result(name="success", type="redirectAction",location="privilegeCateList.action" )  
        }
	)
	public String deletePrivilege() throws Exception{
		authorityService.deletePrivilege(privilegeId);
		return SUCCESS;
	}
}
