package com.cloudsea.photo.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public enum Week {

	MONDAY(1,"星期一"),TEUSDAY(2,"星期二"), WEDNESDAY(3,"星期三"),THURSDAY(4,"星期四"),FRIDAY(5,"星期五"),SATURDAY(6,"星期六"),SUNDAY(7,"星期日");
	
	private int key;
	private String value;
	
	private Week(int key,String value) {
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
			case 1 : return MONDAY.value;
			case 2 : return TEUSDAY.value;
			case 3 : return WEDNESDAY.value;
			case 4 : return THURSDAY.value;
			case 5 : return FRIDAY.value;
			case 6 : return SATURDAY.value;
			case 7 : return SUNDAY.value;
			
			default: return "";
		}
	}
	
	public static JSONArray toJSON() {
		JSONArray jsonArray = new JSONArray();
		JSONObject strMonday = new JSONObject();
		strMonday.put("key", MONDAY.getKey());
		strMonday.put("value", MONDAY.getValue());
		jsonArray.add(strMonday);
		
		JSONObject strTuesday = new JSONObject();
		strTuesday.put("key", TEUSDAY.getKey());
		strTuesday.put("value", TEUSDAY.getValue());
		jsonArray.add(strTuesday);
		
		JSONObject strWenesDay = new JSONObject();
		strWenesDay.put("key", WEDNESDAY.getKey());
		strWenesDay.put("value", WEDNESDAY.getValue());
		jsonArray.add(strWenesDay);
		
		JSONObject strThursday = new JSONObject();
		strThursday.put("key", THURSDAY.getKey());
		strThursday.put("value", THURSDAY.getValue());
		jsonArray.add(strThursday);
		
		JSONObject strFriday = new JSONObject();
		strFriday.put("key", FRIDAY.getKey());
		strFriday.put("value", FRIDAY.getValue());
		jsonArray.add(strFriday);
		
		JSONObject strSaturday = new JSONObject();
		strSaturday.put("key", SATURDAY.getKey());
		strSaturday.put("value", SATURDAY.getValue());
		jsonArray.add(strSaturday);
		
		JSONObject strSunday = new JSONObject();
		strSunday.put("key", SUNDAY.getKey());
		strSunday.put("value", SUNDAY.getValue());
		jsonArray.add(strSunday);
		return jsonArray;
	}


}
