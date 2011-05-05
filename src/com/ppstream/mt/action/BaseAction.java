package com.ppstream.mt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Namespace("/default")   
@SuppressWarnings("serial")
@Scope("prototype")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = Action.ERROR) })
@Results({
  @Result(name=Action.LOGIN, type = "redirectAction", location = "logout.action"),
  @Result(name=Action.ERROR, location="/error.jsp"),
  @Result(name=Action.INPUT, location="/default.jsp")
})
public class BaseAction extends ActionSupport implements Preparable,ServletRequestAware {

	public BaseAction() { }
	
	HttpServletRequest request;
    HttpSession session;
    HttpServletResponse response;

    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
        this.session = this.request.getSession();
//        this.session = ActionContext.getContext().getSession();  
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpSession getSession() {
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
