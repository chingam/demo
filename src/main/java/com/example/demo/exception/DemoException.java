package com.example.demo.exception;

public class DemoException extends Exception {

	private static final long serialVersionUID = -2149114973028910082L;

	public DemoException() {
		super();
	}

	public DemoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DemoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DemoException(String message) {
		super(message);
	}

	public DemoException(Throwable cause) {
		super(cause);
	}
}
