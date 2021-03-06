package com.cloudsea.photo.utils.log;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class LoggerExpand {

	private Logger logger;

	private LoggerExpand(){}
	
	public static LoggerExpand getLoggerExpand(Logger logger) {
		LoggerExpand loggerExpand = new LoggerExpand();
		loggerExpand.logger = logger;
		return loggerExpand;
	}
	
	public void info(Object message) {
		logger.info(message);
	}
	
	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	public void log(Priority level, Object message) {
		logger.log(level, message);
	}
	
	public void log(Priority level, Object message, Throwable t) {
		logger.log(level, message, t);
	}
	
	public void log(Priority level, Object message, String value, String consumeTime) {
		log(level, message, null, value, consumeTime);
	}

	public void log(Priority level, Object message, Throwable t, String value, String consumeTime) {
		if (logger.getLoggerRepository().isDisabled(level.toInt())) {
		    return;
	    }
	    if (level.isGreaterOrEqual(logger.getEffectiveLevel()))
	    	forcedLog(Category.class.getName(), level, message, t, value, consumeTime);
	}
	
	public void warn(Object message) {
		logger.warn(message);
	}

	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}
	
	public void warn(Object message, String value, String consumeTime) {
		if (logger.getLoggerRepository().isDisabled(30000))
			return;
		if (Level.WARN.isGreaterOrEqual(logger.getEffectiveLevel()))
			forcedLog(Category.class.getName(), Level.WARN, message, null, value, consumeTime);
	}

	public void warn(Object message, Throwable t, String value, String consumeTime) {
		if (logger.getLoggerRepository().isDisabled(30000))
		    return;
	    if (Level.WARN.isGreaterOrEqual(logger.getEffectiveLevel()))
	    	forcedLog(Category.class.getName(), Level.WARN, message, t, value, consumeTime);
	}
	
	public void error(Object message) {
		logger.error(message);
	}
	
	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	public void error(Object message, String value, String consumeTime) {
		error(message, null, value, consumeTime);
	}

	public void error(Object message, Throwable t, String value, String consumeTime) {
		if (logger.getLoggerRepository().isDisabled(40000))
		    return;
	    if (Level.ERROR.isGreaterOrEqual(logger.getEffectiveLevel()))
	    	forcedLog(Category.class.getName(), Level.ERROR, message, t, value, consumeTime);
	}
	
	protected void forcedLog(String fqcn, Priority level, Object message, Throwable t, String value, String consumeTime) {
		logger.callAppenders(new LoggingEventExpand(fqcn, logger, level, message, t, value, consumeTime));
	}
	


}
