package com.cloudsea.photo.enums;

import java.util.Locale;

public enum LocaleEnum {
	
	CHINESE(Locale.CHINESE,"中文简体"),
	JAPANESE(Locale.JAPANESE,"日语"),
	ENGLISH(Locale.ENGLISH,"英语");
	
	private Locale locale;
	private String displayName;
	
	private LocaleEnum(Locale locale,String displayName) {
		this.locale = locale;
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public String toString() {
		return displayName;
	}
}
