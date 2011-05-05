package com.ppstream.mt.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.ppstream.mt.entity.Group;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.entity.User;
import com.ppstream.mt.service.AuthorityService;
import com.ppstream.mt.service.UserService;
import com.ppstream.mt.utils.pager.PgInfo;
import com.ppstream.mt.utils.pager.TbData;

@Controller("userAdmin")
public class UserAdmin extends BaseAction {

	private static final long serialVersionUID = 1L;

	private UserService userService;
	private AuthorityService authorityService;

	private Integer userId;
	private String userName;
	private String password;
	private String email;
	private String roleIds;
	private Integer groupLeader;
	private Integer groupId;
	private String nickName;
	private String subPhone;
	private String privilegeIds;

	private final static int pageSize = 30;
	private Integer totalPages;
	private Integer currentPage;
	
	private String repeatForm;

	public String getRepeatForm() {
		return repeatForm;
	}

	public void setRepeatForm(String repeatForm) {
		this.repeatForm = repeatForm;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(Integer groupLeader) {
		this.groupLeader = groupLeader;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSubPhone() {
		return subPhone;
	}

	public void setSubPhone(String subPhone) {
		this.subPhone = subPhone;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	@Resource
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 员工列表
	@Action(value = "userList", results = { @Result(name = "success", location = "/authority/userList.jsp") })
	public String userList() throws Exception {
		if(currentPage == null){
		   currentPage = 1;
		}
	    TbData tbData = userService.getUserList(currentPage,pageSize);
	    @SuppressWarnings("unchecked")
	    List<User> users = tbData.getList();
	    PgInfo pgInfo = tbData.getPageInfo();
	    request.setAttribute("users", users);
	    request.setAttribute("pginfo",pgInfo);
	    return SUCCESS;
	}

	// 停用账户
	@Action(value = "changeUserStatusToStop", results = { @Result(name = "success", type = "redirectAction", location = "userList.action") })
	public String changeUserStatusToStop() throws Exception {
		userService.changeUserStatus(userId, 2);
		return SUCCESS;
	}

	// 启用账户
	@Action(value = "changeUserStatusToUse", results = { @Result(name = "success", type = "redirectAction", location = "userList.action") })
	public String changeUserStatusToUse() throws Exception {
		userService.changeUserStatus(userId, 1);
		return SUCCESS;
	}

	// 增加用户的页面
	@Action(value = "addUserView", results = { @Result(name = "success", location = "/authority/addUser.jsp") })
	public String addUserView() throws Exception {
		List<Role> roles = authorityService.getRoleList();
		List<Group> groups = userService.getGroupList();
		request.setAttribute("roles", roles);
		request.setAttribute("groups", groups);
		session.setAttribute("repeatForm", Math.random() + ""); // 防止表单重复提交
		return SUCCESS;
	}

	// 增加用户
	@Action(value = "addUser", results = { 
			@Result(name = "success", type = "redirectAction", location = "userList.action"),
			@Result(name = "invalid", type = "redirectAction", location = "addUserView.action") 
	})
	public String addUser() throws Exception {
		// 校验,userName应该可以重复，因为有Key [两个汪洋]

		String hiddenValue = (String) session.getAttribute("repeatForm");
        if (repeatForm != null && repeatForm.equals(hiddenValue)) {            // 如果是等同的则清空后保存
            session.removeAttribute("repeatForm");
            // 保存数据
            userService.addOrUpdateUser(null, userName, password, email, roleIds,
    				groupLeader, groupId, nickName, subPhone);
            
            return SUCCESS;
        } else {
            return "invalid";
        }
	}

	// 编辑用户页面
	@Action(value = "editUserView", results = { @Result(name = "success", location = "/authority/editUser.jsp") })
	public String editUserView() throws Exception {
		// userId查出user记录
		User user = userService.getUserById(userId);
		Set<Role> userRoles = user.getRoles();
		Set<Integer> roleIds = new HashSet<Integer>();
		Iterator<Role> userRolesIterator = userRoles.iterator();
		while (userRolesIterator.hasNext()) {
			Role role = userRolesIterator.next();
			roleIds.add(role.getId());
		}
		List<Role> roles = authorityService.getRoleList();
		List<Group> groups = userService.getGroupList();
		request.setAttribute("user", user);
		request.setAttribute("roleIds", roleIds);
		request.setAttribute("roles", roles);
		request.setAttribute("groups", groups);
		return SUCCESS;
	}

	// 编辑用户
	@Action(value = "editUser", results = { @Result(name = "success", type = "redirectAction", location = "userList.action") })
	public String editUser() throws Exception {
		userService.addOrUpdateUser(userId, userName, password, email, roleIds,
				groupLeader, groupId, nickName, subPhone);
		return SUCCESS;
	}

	// 设置用户权限页面
	@Action(value = "assignPrivilegeToUserView", results = { @Result(name = "success", location = "/authority/assignPrivilegeToUserView.jsp") })
	public String assignPrivilegeToUserView() throws Exception {
		Set<Integer> privilegeIds = userService.getPrivilegeIdsByUserId(userId);
		List<PrivilegeCate> privilegeCates = authorityService
				.getPrivilegeCateList();
		request.setAttribute("privilegeCates", privilegeCates);
		request.setAttribute("privilegeIds", privilegeIds);
		return SUCCESS;
	}

	// 设置用户权限
	@Action(value = "assignPrivilegeToUser", results = { @Result(name = "success", type = "redirectAction", location = "userList.action") })
	public String assignPrivilegeToUser() throws Exception {
		userService.configUserPrivilege(userId, privilegeIds);
		return SUCCESS;
	}

}
