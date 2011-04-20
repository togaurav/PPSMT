package com.ppstream.mt.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

public class AuthorityListener implements PreResultListener{
	
	/**
	 * 定义在处理Result之前的行为  
	 * resultCode 参数就是被拦截 Action 的 execute() 　返回值。
	 */
    public void beforeResult(ActionInvocation invocation, String resultCode) {  
        System.out.println("返回的逻辑视图为:" + resultCode);  
    } 

}
