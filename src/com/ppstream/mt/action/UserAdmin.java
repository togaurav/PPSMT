package com.ppstream.mt.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.ppstream.mt.bean.GPrivilege;
import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.PrivilegeType;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.entity.User;
import com.ppstream.mt.formmodel.UserLogIn;
import com.ppstream.mt.service.UserService;


public class UserAdmin extends BaseAction implements ModelDriven<UserLogIn> {

	private static final long serialVersionUID = 1L;

	/**
	 * 多重角色,所拥有的权限或许有重复，说使用Set
	 */
	private HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps= new HashMap<String,HashMap<String,HashSet<GPrivilege>>>();
	
	@Autowired  
	private UserService userService;

	private UserLogIn userLogIn = new UserLogIn();
	
	@Override
	public UserLogIn getModel() {
		return userLogIn;
	}

//	@BeforeClass
//	public static void authorityValidate() throws Exception {
//		
//	}
	
	
	@Action(value="login",
//		interceptorRefs=@InterceptorRef("customException"),  // 异常处理拦截器
		results={
			@Result(name="success", location="/user/index.jsp" ) 
        }
	)
    public String login() throws Exception { 
		// 验证
		
		// 查询数据库	
		User user = userService.getUserByNameAndPwd(userLogIn.getUsername(),userLogIn.getPassword());
		if(user == null){
			throw new Exception("测试异常");
//			return "login";
		}
		Set<Role> roles = user.getRoles();
		Iterator<Role> roleIterator = roles.iterator();
		
		StringBuilder roleName = new StringBuilder();;
		while(roleIterator.hasNext()){
			Role role = roleIterator.next();
			// 多重角色
			roleName.append(role.getRoleName());
			roleName.append(',');
			Set<Privilege> privileges = role.getPrivileges();
			Iterator<Privilege> privilegeIterators = privileges.iterator();
			while(privilegeIterators.hasNext()){
				Privilege privilege = privilegeIterators.next();
				String privilegeName = privilege.getName();
				String privilegeAction = privilege.getAction();
				Integer showNav = privilege.getShowNav();
				PrivilegeCate pc = privilege.getPrivilegeCate();
				String cateName = pc.getCateName();
				PrivilegeType pt = pc.getPrivilegeType();
				String typeName = "未分类";
				if(pt != null){
					typeName = pt.getTypeName();
				}
				
				// 将拥有的权限放入一个集合中
				GPrivilege gp = new GPrivilege(privilegeName,privilegeAction,showNav);
				if(maps.containsKey(typeName)){
					HashMap<String, HashSet<GPrivilege>> cateMap = maps.get(typeName);
					if(cateMap.containsKey(cateName)){ // 新增小类
						HashSet<GPrivilege> privilegeSet = cateMap.get(cateName);
						privilegeSet.add(gp);
					}else{ // 新增中类
						HashSet<GPrivilege> gprivilegeSet = new HashSet<GPrivilege>();
//						if(!gprivilegeSet.contains(gp)){
							gprivilegeSet.add(gp);
//						}
						cateMap.put(cateName, gprivilegeSet);
					}
				}else{ // 新增大类
					HashMap<String, HashSet<GPrivilege>> cateMap = new HashMap<String, HashSet<GPrivilege>>();
					HashSet<GPrivilege> gprivilegeSet = new HashSet<GPrivilege>();
					gprivilegeSet.add(gp);
					cateMap.put(cateName, gprivilegeSet);
					maps.put(typeName, cateMap);
				}
				
				
				
			}
		}
		// 保存session,使用HttpSession,出现第一次无数据的情况
		session.put("roleName", StringUtils.removeEndIgnoreCase(roleName.toString(), ","));
		session.put("userId",user.getId());
		session.put("userName",user.getUserName());
		session.put("nickName",user.getNickName());
		if(session.get("maps") == null || (session.get("maps") != null && !maps.equals(session.get("maps")))){
			if(session.get("maps") != null) System.out.println("not null");
			session.put("maps", maps);  
		}
		return SUCCESS;  
	}
	
	@Action(value="userList",
//			interceptorRefs=@InterceptorRef("customException"),  // 异常处理拦截器
			results={
				@Result(name="success", location="/authority/userList.jsp" ) 
	        }
		)
	public String userList() throws Exception { 
		List<User> users = userService.getUserList();
		request.setAttribute("users", users);
		return SUCCESS;
	}
	
	
}
