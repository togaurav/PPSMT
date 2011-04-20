package com.ppstream.mt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
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
public class BaseAction extends ActionSupport implements Preparable,ServletRequestAware {

	HttpServletRequest request;
    HttpSession session;
    //构造函数

    public BaseAction() {
    	
    }
    
    //request session 的 getter setter方法
    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
        this.session = this.request.getSession();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpSession getSession() {
        return session;
    }

    // 记录异常信息
//	private Map<String, String> map = new HashMap<String, String>();	
//
//	public Map<String, String> getMap() {
//		return map;
//	}
//
//	public void setMap(Map<String, String> map) {
//		this.map = map;
//	}
	

	/**
	 * 一个记录日志的方法
	 * @param clazz 需要记录日志的类
	 * @param ex  日志内容
	 */
    private static Log logger ;
	public static void writeLog(Class clazz, String ex) {
		logger = LogFactory.getLog(clazz);
	//	调试	logger.debug( " 111 " );
	//	信息	logger.info( " 222 " );
	//	警告	logger.warn( " 333 " );
	//	错误	logger.error( " 444 " );
	//	致命错误	logger.fatal( " 555 " );
	}

	
	/**
	 * 防止重复提示错误信息
	 */
	public void prepare() {
		clearErrorsAndMessages();
	}

}
