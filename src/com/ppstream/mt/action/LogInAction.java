package com.ppstream.mt.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.PrivilegeType;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.entity.User;
import com.ppstream.mt.formmodel.UserLogIn;
import com.ppstream.mt.gsonmodel.GPrivilege;
import com.ppstream.mt.gsonmodel.GPrivilegeCate;
import com.ppstream.mt.gsonmodel.GPrivilegeType;
import com.ppstream.mt.service.UserService;
import com.ppstream.mt.utils.gson.GsonUtils;


@Results({
  @Result(name="login", location="/user/login.jsp")
})
public class LogInAction extends BaseAction implements ModelDriven<UserLogIn> {

	private static final long serialVersionUID = 1L;
	
	@Autowired  
	private UserService userService;
	
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
		/**
		 * 多重角色,所拥有的权限或许有重复
		 */
		List<GPrivilegeType> lists = new ArrayList<GPrivilegeType>();
		
		long time1 = System.currentTimeMillis();
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
				GPrivilege gp = new GPrivilege(privilegeName,privilegeAction);
				if(lists.size() == 0){  // 初始，新增大类
					Set<GPrivilege> gprivilegeSet = new HashSet<GPrivilege>();
					gprivilegeSet.add(gp);
					GPrivilegeCate gpc = new GPrivilegeCate(cateName,gprivilegeSet);
					List<GPrivilegeCate> gprivilegeCateSet = new ArrayList<GPrivilegeCate>();
					gprivilegeCateSet.add(gpc);
					lists.add(new GPrivilegeType(typeName,gprivilegeCateSet));
				}
				int m = 0;
				for(int i = 0;i<lists.size();i++){
					GPrivilegeType gpt = lists.get(i);
					if(typeName.equalsIgnoreCase(gpt.getTypeName())){ // 如果大类相同,遍历中类
//						List<GPrivilegeCate> catelists = gpt.getPrivilegeCates();
//						for(int j = 0;j<catelists.size();j++){
//							GPrivilegeCate cate = catelists.get(j);
//							if(cateName.equalsIgnoreCase(cate.getCateName())){  // 中类名字相同
//								cate.getPrivileges().add(gp);
//							}else{
//								if(j == (catelists.size() - 1)){ // 新增中类
//									Set<GPrivilege> gprivilegeSet = new HashSet();
//									gprivilegeSet.add(gp);
//									GPrivilegeCate gpc = new GPrivilegeCate(cateName,gprivilegeSet);
//									catelists.add(gpc);
//								}
//							}
//						}
					}else{
						m++;
						if(m == lists.size()){ // 新增大类
							Set<GPrivilege> gprivilegeSet = new HashSet<GPrivilege>();
							gprivilegeSet.add(gp);
							GPrivilegeCate gpc = new GPrivilegeCate(cateName,gprivilegeSet);
							List<GPrivilegeCate> gprivilegeCateSet = new ArrayList<GPrivilegeCate>();
							gprivilegeCateSet.add(gpc);
							lists.add(new GPrivilegeType(typeName,gprivilegeCateSet));
						}
					}
				}
				
				
				
			}
		}
		long time2 = System.currentTimeMillis();
		System.out.println("time 2:"+(time2 - time1));
		System.out.println(GsonUtils.getInstance().bean2json(lists));
		// 保存session
//		Map session = ActionContext.getContext().getSession();  
		
		return SUCCESS;  
	}
	
	
}
