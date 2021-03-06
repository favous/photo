package com.cloudsea.photo.utils.log;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

public class LoggingEventExpand extends LoggingEvent {
	
	private static final long serialVersionUID = -444539092223212123L;
	
	private String value;
	private String consumeTime;

	public LoggingEventExpand(String fqnOfCategoryClass, Category logger, Priority level, Object message, Throwable throwable) {  
        super(fqnOfCategoryClass, logger, level, message, throwable);  
    }  
      
	public LoggingEventExpand(String fqnOfCategoryClass, Category logger, Priority level, Object message, Throwable throwable, String value, String consumeTime) {  
        super(fqnOfCategoryClass, logger, level, message, throwable);  
        this.value = value;
        this.consumeTime = consumeTime;
    }

	public String getValue() {
		return value;
	}

	public String getConsumeTime() {
		return consumeTime;
	}  
}
