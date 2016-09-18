package com.geochallenge.api.provider;

public class ExceptionResponse {

	private final String message;
	private final String clazz;

	public static ExceptionResponse from(Throwable e) {
		return new ExceptionResponse(e.getMessage(), e.getClass().getName());
	}

	private ExceptionResponse(String message, String clazz) {
		this.message = message;
		this.clazz = clazz;
	}

	public String getMessage() {
		return message;
	}

	public String getClazz() {
		return clazz;
	}

}
