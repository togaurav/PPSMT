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
@ParentPackage(value="convention-default")
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
    
    /** *//**
     * 处理异常
     * @param e
     */
    private void handleException(Exception e) {
        boolean handled = false;
        Throwable throwEx = e;
        while (throwEx != null) {
            if(throwEx instanceof BaseException ) {
            	BaseException  be = (BaseException )throwEx;
            	System.out.println("捕获异常："+throwEx.getMessage());
                String errorCode = be.getMessageCode();
                
                // 从缓存中通过ErrorCode取得对应message
                String errorMsg = getMessage(errorCode);
                
                // 页面显示错误提示信息
                fillError4Display(errorMsg);
                handled = true;
            }
            throwEx = throwEx.getCause();
        }
        
        if(!handled) {
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
        getRequest().setAttribute("_error_msg_", msg);
    }
    /**
     * 当为 自定义的BusinessException时，根据抛出异常时的msgCode，取得对应的显示信息。
		msgCode与显示信息的对应关系 可先配置好，系统启动时将其缓存起来。
		如果非BusinessException，则统一显示为 “系统忙，请稍候再试。”
     */
    private String getMessage(String errorCode){
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
