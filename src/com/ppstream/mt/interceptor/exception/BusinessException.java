package com.ppstream.mt.interceptor.exception;

public class BusinessException extends BaseException {
	private static final long serialVersionUID = -1657938434382769721L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String messageCode, String message) {
		super(messageCode, message);
		setMessageCode(messageCode);
	}

	public BusinessException(String messageCode, String message, Throwable cause) {
		super(messageCode, message, cause);
		setMessageCode(messageCode);
	}
}
