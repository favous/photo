/**
 * 
 */
package com.cloudsea.photo.beans;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * @author zhangxiaorong
 *
 * 2014-3-26
 */
public class SpringContextBean {

	private static ApplicationContext context;
	private static ServletContext ServletContext;
	
	private SpringContextBean(){}
	
	private static void init() {
		if (ServletContext == null){
			throw new RuntimeException("ServletContext值为空，不可以初始化");
		}
		
		//获取web环境下的ApplicationContext
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext);
		if (context == null)
			context = (ApplicationContext) ContextLoader.getCurrentWebApplicationContext();
	}
	
    public static ApplicationContext getContext() {
    	if (context == null)
    		synchronized(SpringContextBean.class){
    			if (context == null)
    				init();
    		}
    	
        return context;
    }
    
    public static Object getBean(String id) {
    	try {
			return getContext().getBean(id);
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return null;
	}

    public static Object getCopyBean(String id) {
    	try {
    		Object bean = getContext().getBean(id);
    		Object obj = BeanUtils.cloneBean(bean);
			return obj;
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setServletContext(ServletContext servletContext) {
		ServletContext = servletContext;
	}
    
    
    
}
