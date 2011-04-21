package com.ppstream.mt.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
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
import com.ppstream.mt.utils.gson.GsonUtils;


@Results({
  @Result(name="login", location="/user/login.jsp")
})
public class LogInAction extends BaseAction implements ModelDriven<UserLogIn> {

	private static final long serialVersionUID = 1L;

	/**
	 * 多重角色,所拥有的权限或许有重复，说使用Set
	 */
	private static HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps= new HashMap<String,HashMap<String,HashSet<GPrivilege>>>();
	
	@Autowired  
	private UserService userService;
	
//	public static HashMap<String, HashMap<String, HashSet<GPrivilege>>> getMaps() {
//		return maps;
//	}
//
//
//	public static void setMaps(
//			HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps) {
//		LogInAction.maps = maps;
//	}


	private UserLogIn userLogIn = new UserLogIn();
	
	@Override
	public UserLogIn getModel() {
		return userLogIn;
	}

	
	@Action(value="login",
//		interceptorRefs=@InterceptorRef("validation"),
		results={
			@Result(name="success", location="/user/index.jsp" )  // , type="redirect"
        }
	)
    public String login() throws Exception { 
		// 验证
		// 查询数据库	
		User user = userService.getUserByNameAndPwd(userLogIn.getUsername(),userLogIn.getPassword());
		if(user == null){
			return "login";
		}
		Set<Role> roles = user.getRoles();
		Iterator<Role> roleIterator = roles.iterator();
		
		
//		long time1 = System.currentTimeMillis();
		while(roleIterator.hasNext()){
			Role role = roleIterator.next();
			Set<Privilege> privileges = role.getPrivileges();
			Iterator<Privilege> privilegeIterators = privileges.iterator();
			while(privilegeIterators.hasNext()){
				Privilege privilege = privilegeIterators.next();
				String privilegeName = privilege.getName();
//				System.out.println("privilegeName:" + privilegeName);
				String privilegeAction = privilege.getAction();
				PrivilegeCate pc = privilege.getPrivilegeCate();
				String cateName = pc.getCateName();
				PrivilegeType pt = pc.getPrivilegeType();
				String typeName = "未分类";
				if(pt != null){
					typeName = pt.getTypeName();
				}
				
				// 将拥有的权限放入一个集合中
//				maps = new HashMap<String,HashMap<String,HashSet<GPrivilege>>>();
				GPrivilege gp = new GPrivilege(privilegeName,privilegeAction);
				if(maps.containsKey(typeName)){
					HashMap<String, HashSet<GPrivilege>> cateMap = maps.get(typeName);
					if(cateMap.containsKey(cateName)){ // 新增小类
						HashSet<GPrivilege> privilegeSet = cateMap.get(cateName);
						privilegeSet.add(gp);
					}else{ // 新增中类
						HashSet<GPrivilege> gprivilegeSet = new HashSet<GPrivilege>();
						gprivilegeSet.add(gp);
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
//		long time2 = System.currentTimeMillis();
//		System.out.println("time 2:"+(time2 - time1));
		request.setAttribute("maps", maps);  
		// 保存session
//		Map session = ActionContext.getContext().getSession();  
		
		return SUCCESS;  
	}
	
	
}
