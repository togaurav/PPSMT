package com.ppstream.mt.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.ppstream.mt.exception.BaseException;

/**
 * 异常拦截器
 * @author liupeng
 */
public class ErrorHandlingInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        try {
            return invocation.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            handleException(e);
        }
        return Action.INPUT;
    }
    
    /** 
     * 处理异常
     */
    private void handleException(Exception e) {
        boolean handled = false;
        Throwable throwEx = e;
        while (throwEx != null) {
            if(throwEx instanceof BaseException ) { // 可得知是什么业务异常
            	BaseException  be = (BaseException )throwEx;
            	String errorCode = be.getMessageCode();
            	String errorMessage = throwEx.getMessage();
            	System.out.println("捕获异常："+ errorMessage);
                
                // 通过ErrorCode取得对应业务异常信息
                String errorMsg = getMessage(errorCode);
                
                // 页面显示错误提示信息
                fillError4Display(errorMsg);
                handled = true;
            }
            throwEx = throwEx.getCause();
        }
        
        if(!handled) {  // 显示默认异常
            fillDefaultError();
        }
    }
    
    private HttpServletRequest getRequest() {
        return (HttpServletRequest)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
    }
    
    private void fillDefaultError() {
        fillError4Display("系统忙，请稍候再试。");
    }
    
    private void fillError4Display(String msg) {
        getRequest().setAttribute("errorMsg", msg);
    }
    
    private String getMessage(String errorCode){
    	// 根据errorCode来获取业务异常信息
    	return "error";
    }
    
    
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
}
