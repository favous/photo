package com.cloudsea.photo.enums;

public enum DataType {

	
	STRING("string",""),
	BYTE_ARRAY("byteArray",""),
	FILE("file",""),
	BEAN("bean",""),
	JSON("jsom",""),
	XML("xml","");
	
	private String key;
	private String desc;
	
	private DataType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

}
