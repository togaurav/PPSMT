/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppstream.mt.interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.ppstream.mt.bean.GPrivilege;

/**
 * 登录以及权限判断拦截器
 * @author laupeng
 */
@ParentPackage(value="convention-default")
public class AuthorityInterceptor extends MethodFilterInterceptor {
	
	// 简单拦截器的名字  
    private String name;  
  
    // 为该简单拦截器设置名字的setter方法  
    public void setName(String name) {  
        this.name = name;  
    }  

    public String doIntercept(ActionInvocation invocation) throws Exception {
    	
    	// 将一个拦截结果的监听器注册给该拦截器  
        invocation.addPreResultListener(new AuthorityListener());  
    	
        ActionContext ctx = invocation.getInvocationContext();
        Map session = ctx.getSession();
        String userName = (String) session.get("userName");
        System.out.println("execute方法执行之前的拦截...");  
        if (userName != null) {
            System.out.println("execute方法执行之后的拦截...");
            /*
             * 判断权限
             */
            HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps = (HashMap<String, HashMap<String, HashSet<GPrivilege>>>)session.get("maps");

            // 取得被拦截的Action实例  
//               Object action = invocation.getAction();
            
            
            
            // 执行该拦截器的后一个拦截器  ,如果该拦截器后没有其他拦截器，则直接执行Action的execute方法  
            return invocation.invoke();
        }
        /**
                             在此处可将用户访问的jsp路径记录下来，action后缀的不记录
           HttpServletRequest request = ServletActionContext.getRequest();
           String currentURL = request.getRequestURI();
      	   String targetURL = currentURL.substring(currentURL.indexOf("/", 1));
      	   String[] arr = currentURL.split("/");
      	   
      	   return Constants.INPUT; 
         */
        ctx.put("tip", "您还没有登录网站");
        return Action.LOGIN;   // 返回登录界面
    }
}
