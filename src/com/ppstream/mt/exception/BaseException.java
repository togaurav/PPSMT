package com.ppstream.mt.exception;

import java.util.concurrent.atomic.AtomicLong;

// 所有业务异常的基类
public abstract class BaseException extends RuntimeException {
	
	static AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis());
    String id;
    
    private String messageCode;
    public String getMessageCode() {
        return messageCode;
    }
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

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
        long nid = atomicLong.incrementAndGet();
        id = Long.toString(nid, 26);
    }

    public abstract String getErrorTitle();

    public abstract String getErrorDescription();

}