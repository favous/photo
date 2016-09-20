package com.cloudsea.photo.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import com.cloudsea.photo.beans.SpringContextBean;

public class DBCPStatusUtil {

	public static Map<String, Long> getDBCPStatus(String id){
		BasicDataSource ds = (BasicDataSource) SpringContextBean.getBean(id);
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("active_number", (long) ds.getNumActive());
		map.put("idle_number", (long) ds.getNumIdle());
		map.put("initialSize", (long) ds.getInitialSize());
		map.put("maxIdle", (long) ds.getMaxIdle());
		map.put("minIdle", (long) ds.getMinIdle());
		map.put("maxActive", (long) ds.getMaxActive());
		map.put("maxWait", ds.getMaxWait());
		return map;
	}
}
