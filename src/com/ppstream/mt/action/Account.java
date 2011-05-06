package com.ppstream.mt.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.ppstream.mt.bean.GPrivilege;
import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.entity.User;
import com.ppstream.mt.formmodel.UserLogIn;
import com.ppstream.mt.service.UserService;

@Controller("account")
public class Account extends BaseAction implements ModelDriven<UserLogIn> {

	private static final long serialVersionUID = 1L;

	/**
	 * 多重角色,所拥有的权限或许有重复，说使用Set
	 */
	private HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps = new HashMap<String, HashMap<String, HashSet<GPrivilege>>>();

	private Map<String, String> errorMap = new HashMap<String, String>();

	/**
	 * @Autowired 默认按类型装配,@Resource默认按名称装配,在使用Spring
	 *            AOP之后,使用@Autowired注入为null,改为@Resource
	 */
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private UserLogIn userLogIn = new UserLogIn();

	@Override
	public UserLogIn getModel() {
		return userLogIn;
	}

	// 用户登录
	@Action(value = "login", results = { @Result(name = "success", location = "/user/index.jsp") })
	public String login() throws Exception {
		String method = ServletActionContext.getRequest().getMethod();
		boolean isPostMethod = "GET".equalsIgnoreCase(method);
		if (isPostMethod) {   // 不能是非POST提交
			ActionContext ac = ActionContext.getContext();
        	ac.put("loginTips", "请重新登录！");    
			return LOGIN;
		}
		// 验证
		if (StringUtils.isEmpty(userLogIn.getUsername())) {
			errorMap.put("username", "Please enter a username");
		}
		if (StringUtils.isEmpty(userLogIn.getPassword())) {
			errorMap.put("username", "Please provide a password");
		}
		if (errorMap.keySet().size() > 0) {
			return LOGIN;
		}
		String ip = request.getRemoteAddr();
		// 查询数据库
		User user = userService.getUserByNameAndPwd(userLogIn.getUsername(),
				userLogIn.getPassword());
		if (user == null) {
			ActionContext ac = ActionContext.getContext();
        	ac.put("loginTips", "请确认您的登录名或密码是否正确！");    
			return LOGIN;
		}
//		else{
//			user.setLastIp(ip);
//			userService.changeUserLastVisit(null,ip);
//		}
		Set<Role> roles = user.getRoles();
		Iterator<Role> roleIterator = roles.iterator();

		StringBuilder roleName = new StringBuilder();
		
		while (roleIterator.hasNext()) {
			Role role = roleIterator.next();
			// 多重角色
			roleName.append(role.getRoleName());
			roleName.append(',');
			// 角色权限
			Set<Privilege> rolePrivileges = userService.getPrivilegeByRoleId(role.getId());
			maps = userService.setPrivilegeToMap(maps, rolePrivileges);
		}
		// 用户权限
		Set<Privilege> userPrivileges = userService.getPrivilegeByUserId(user.getId());
		maps = userService.setPrivilegeToMap(maps, userPrivileges);
		
		// 保存session,使用HttpSession,出现第一次无数据的情况
		session.setAttribute("roleName",StringUtils.removeEndIgnoreCase(roleName.toString(), ","));
		session.setAttribute("ip",ip);
		session.setAttribute("userId", user.getId());
		session.setAttribute("userName", user.getUserName());
		session.setAttribute("nickName", user.getNickName());
		if (session.getAttribute("maps") == null
				|| (session.getAttribute("maps") != null && !maps.equals(session.getAttribute("maps")))) {
			session.setAttribute("maps", maps);
		}
		return SUCCESS;
	}

	// 退出
	@Action(value = "logout", results = { @Result(name = "success", location = "/user/login.jsp") })
	public String logout() throws Exception {
		session.invalidate();
		return SUCCESS;
	}
}
