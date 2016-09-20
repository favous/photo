package com.cloudsea.photo.utils.commonutils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesReader extends PropertyPlaceholderConfigurer {
	
	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();  
 
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,  
            Properties props) throws BeansException { 
    	
        super.processProperties(beanFactoryToProcess, props);  
        
        if (props.size() == 0)
        	throw new BootstrapException("没有加载到任何一个properties文件");
        
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
  
    public static Object getValue(String name) {  
    	if (ctxPropertiesMap == null || ctxPropertiesMap.size() == 0)
    		return null;
        return ctxPropertiesMap.get(name);  
    }  
}
