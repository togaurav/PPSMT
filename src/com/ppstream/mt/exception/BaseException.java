package com.ppstream.mt.exception;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseException extends RuntimeException {
	
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
        long nid = atomicLong.incrementAndGet();
        id = Long.toString(nid, 26);
    }

    public abstract String getErrorTitle();

    public abstract String getErrorDescription();

}