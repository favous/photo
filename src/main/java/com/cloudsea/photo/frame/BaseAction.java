package com.cloudsea.photo.frame;

import net.sf.json.JSONObject;

public class BaseAction {

	public <T> T formateJsonObject(String dataStr, Class<T> clazz) {
		JSONObject jobj = JSONObject.fromObject(dataStr);
		@SuppressWarnings("unchecked")
		T obj = (T) JSONObject.toBean(jobj, clazz);
		return obj;
	}
	
	

}
