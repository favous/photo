package com.cloudsea.photo.enums;

public enum DBOperateType {

	ADD("C", "增加"),
	DELETE("D", "删除"),
	UPDATE("U", "修改"),
	QUERY("R", "查询");
	
	private String key;
	private String desc;
	
	private DBOperateType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
