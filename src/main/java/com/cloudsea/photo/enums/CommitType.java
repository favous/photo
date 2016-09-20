package com.cloudsea.photo.enums;

public enum CommitType {

	NOT_NEED("0", "不需提交"),
	WAITE("1", "待提交"),
	SUCCESS("2", "提交成功"),
	FAILURE("3", "提交失败");
	
	private String key;
	private String desc;
	
	private CommitType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
