package com.cloudsea.common.dto;

import java.io.Serializable;

public class Result implements Serializable {
	
	private static final long serialVersionUID = -3103027662042908579L;
	
	private boolean success;
	private Object value;
	private String errorCode;
	private String message;

	public Result() {
		super();
	}

	public Result(boolean success, Object value, String errorCode, String message) {
		super();
		this.success = success;
		this.value = value;
		this.errorCode = errorCode;
		this.message = message;
	}

	public static Result succ(Object value) {
		return new Result(true, value, null, null);
	}

	public static Result succ(String message) {
		return new Result(true, null, null, message);
	}

	public static Result succ(Object value, String message) {
		return new Result(true, value, null, message);
	}

	public static Result fail(String message) {
		return new Result(false, null, null, message);
	}

	public static Result fail(String errorCode, String message) {
		return new Result(false, null, errorCode, message);
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
