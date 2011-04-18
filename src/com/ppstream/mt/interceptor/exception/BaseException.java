package com.ppstream.mt.interceptor.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -6765360320533958383L;

	private String messageCode;

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String messageCode, String message) {
		super(message);
		setMessageCode(messageCode);

	}

	public BaseException(String messageCode, String message, Throwable cause) {
		super(message, cause);
		setMessageCode(messageCode);
	}
}
