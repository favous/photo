package com.cloudsea.photo.enums;

public enum EnableStatus {
	
	NORMAL(1,"正常"),CANCEL(2,"注销");
	
	private int key;
	private String value;
	
	private EnableStatus(int key,String value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public static String getValueByKey(int key) {
		switch(key) {
			case 1 : return NORMAL.value;
			case 2 : return CANCEL.value;
			default: return "";
		}
	}
	

}
