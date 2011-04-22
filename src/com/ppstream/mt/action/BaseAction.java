package com.ppstream.mt.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

//@InterceptorRefs({@InterceptorRef("customException"),@InterceptorRef("defaultStack")})
@ParentPackage(value="convention-default")  // interceptor-package  
@Namespace("/default")   
//@Controller
@SuppressWarnings("serial")
@Scope("prototype")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "exception") })
@Results({
  @Result(name="login", location="/user/login.jsp")
})
public class BaseAction extends ActionSupport implements Preparable,ServletRequestAware {

	public BaseAction() { }
	
	HttpServletRequest request;
    Map session;
    HttpServletResponse response;

    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
        this.session = ActionContext.getContext().getSession();  
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Map getSession() {
        return session;
    }
    
    public HttpServletResponse getResponse() {
    	this.response = ServletActionContext.getResponse();
		return this.response;
	}
    
	/**
	 * 防止重复提示错误信息
	 */
	public void prepare() {
		clearErrorsAndMessages();
	}

}
