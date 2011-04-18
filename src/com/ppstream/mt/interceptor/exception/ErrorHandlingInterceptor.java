package com.ppstream.mt.interceptor.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ErrorHandlingInterceptor extends AbstractInterceptor{
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
     * @param e
     */
    private void handleException(Exception e) {
        boolean handled = false;
        Throwable throwEx = e;
        while (throwEx != null) {
            if(throwEx instanceof BusinessException) {
                BusinessException be = (BusinessException)throwEx;
                String errorCode = be.getMessageCode();
                
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

    /**
     * 通过ErrorCode取得对应message
     * @param messageCode
     * @return
     */
	public String getMessage(String messageCode){
		// 待实现
		return messageCode;
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
}
