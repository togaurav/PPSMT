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
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 检查是否登录的拦截器
 * @author laupeng
 */
public class AuthorityInterceptor extends AbstractInterceptor {

    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        
        ActionContext ctx = invocation.getInvocationContext();
        Map session = ctx.getSession();
        String user = (String) session.get("loginname");
        if (user != null) {
            return invocation.invoke();   // 递归调用拦截器
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
