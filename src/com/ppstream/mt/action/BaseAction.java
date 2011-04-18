package com.ppstream.mt.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@Namespace("/default")   
@ParentPackage("struts-default")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "exception") })  
public class BaseAction extends ActionSupport implements Preparable {

	HttpServletRequest request;
    HttpSession session;
    //构造函数

    public BaseAction() {
    }
	
//	private Log log = LogFactory.getLog(UserLoginAction.class);

	private Map<String, String> map = new HashMap<String, String>();

	

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	

	/**
	 * 一个记录日志的方法
	 * @param clazz 发生异常的类
	 * @param ex  异常信息
	 */
	public static void writeLog(Class clazz, String ex) {

	}

	
	/**
	 * 防止重复提示错误信息
	 */
	public void prepare() {
		clearErrorsAndMessages();
	}

}
