package com.cloudsea.common.dto;

public class DataResult<T> extends Result {
	
	private static final long serialVersionUID = 7522159136541955178L;
	
	private T data;

	public DataResult() {
		super();
	}

	public DataResult(T data, boolean success, String errorCode, String message) {
		super(success, null, errorCode, message);
		this.data = data;
	}
	
	public static <T> DataResult<T> failure(String message) {
		return new DataResult<T>(null, false, null, message);
	}

	public static <T> DataResult<T> failure(String errorCode, String message) {
		return new DataResult<T>(null, false, errorCode, message);
	}

	public static <T> DataResult<T> success(T data) {
		return new DataResult<T>(data, true, null, null);
	}

	public static <T> DataResult<T> success(T data, String message) {
		return new DataResult<T>(data, true, null, message);
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
