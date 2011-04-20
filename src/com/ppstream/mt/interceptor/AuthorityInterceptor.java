/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppstream.mt.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 检查是否登录的拦截器
 * @author laupeng
 */
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
    	
        HttpServletRequest request = ServletActionContext.getRequest();
     // 取得被拦截的Action实例  
//        Object action = invocation.getAction();
        ActionContext ctx = invocation.getInvocationContext();
        Map session = ctx.getSession();
        String user = (String) session.get("loginname");
        System.out.println("execute方法执行之前的拦截...");  
        if (user != null) {
            System.out.println("execute方法执行之后的拦截...");
        	// 执行该拦截器的后一个拦截器  
            // 如果该拦截器后没有其他拦截器，则直接执行Action的execute方法  
            return invocation.invoke();
        }
        /**
                             在此处可将用户访问的jsp路径记录下来，action后缀的不记录
           String currentURL = request.getRequestURI();
      	   String targetURL = currentURL.substring(currentURL.indexOf("/", 1));
      	   String[] arr = currentURL.split("/");
         */
        ctx.put("tip", "您还没有登录网站");
        return Action.LOGIN;   // 返回登录界面
    }
}
