package com.cloudsea.photo.enums;

public enum ColourType {

	RED("","正常"),
	BLUE("","注销");
	
	private String key;
	private String value;
	
	private ColourType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
}
