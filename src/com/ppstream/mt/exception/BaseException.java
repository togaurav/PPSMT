package com.ppstream.mt.exception;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义异常的基类
 * 
 * Best Practices for Exception Handling and Logging
 * http://onjava.com/pub/a/onjava/2003/11/19/exceptions.html
 * 
 * @author liupeng
 */
public abstract class BaseException extends RuntimeException {

    // AtomicLong,原子类。创建具有给定初始值的新 AtomicLong
	static AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis());
    String id;
    
    public BaseException() {
        super();
        setId();
    }
    
    public BaseException(String message) {
        super(message);
        setId();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        setId();
    }

    void setId() {
    	// 以原子方式将当前值加 1
        long nid = atomicLong.incrementAndGet();
        id = Long.toString(nid, 26);
    }
    
    public String getId() {
        return id;
    }

    public abstract String getErrorTitle();

    public abstract String getErrorDescription();

}