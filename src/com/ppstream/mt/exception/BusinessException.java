package com.ppstream.mt.exception;

/**
 * 自定义异常的一个例子
 * 业务异常
 * @author liupeng
 *
 */
public class BusinessException extends BaseException{	
	
	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable exception) {
        super("Business Error", exception);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
	
	@Override
    public String getErrorTitle() {
		// Throwable
        if(getCause() == null) {
            return "Business error";
        }
        return String.format("Oops: %s", getCause().getClass().getSimpleName());
    }

    @Override
    public String getErrorDescription() {
    	if(getCause() != null && getCause().getClass() != null)
    		return String.format("An business error occured caused by exception <strong>%s</strong>:<br/> <strong>%s</strong>", 
    				getCause().getClass().getSimpleName(), 
    				getCause().getMessage());
    	else 
    		return String.format("Business error : %s", getMessage());
    } 

}
