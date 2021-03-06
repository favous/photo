package com.cloudsea.photo.utils.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;   
import org.apache.log4j.PropertyConfigurator;   
  


/**  
 *  
 * @author zhangxiaorong
 * 2014-10-23
 */
public class Log {
	
	static {
		String configPath = Log.class.getResource("/").getPath() + "config/log4j.properties";
		if (configPath.startsWith("/"))
			configPath = configPath.substring(1);
		PropertyConfigurator.configure(configPath);
	}
	
	private static Map<Class<?>, Logger> loggerMap = new HashMap<Class<?>, Logger>();
	private static Map<Class<?>, LoggerExpand> loggerExpandMap = new HashMap<Class<?>, LoggerExpand>();
	
	private Log(Class<?> clazz) {}

	public static Logger getLoger(Class<?> clazz) {
		Logger logger = loggerMap.get(clazz);
		if (logger == null){
			logger = Logger.getLogger(clazz);
			loggerMap.put(clazz, logger);
		}
		return logger;
	}
	
	public static LoggerExpand getLoggerExpand(Class<?> clazz) {
		LoggerExpand logger = loggerExpandMap.get(clazz);
		if (logger == null){
			logger = LoggerExpand.getLoggerExpand(Logger.getLogger(clazz));
			loggerExpandMap.put(clazz, logger);
		}
		return logger;
	}
	
	

}
