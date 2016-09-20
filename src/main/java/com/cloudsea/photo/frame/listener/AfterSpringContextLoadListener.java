package com.cloudsea.photo.frame.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cloudsea.photo.beans.SpringContextBean;
import com.cloudsea.photo.datacache.CacheService;

public class AfterSpringContextLoadListener implements ServletContextListener {

	private static String contextReakPath;
	private static ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		servletContext = event.getServletContext();
		contextReakPath = servletContext.getRealPath("");
		SpringContextBean.setServletContext(servletContext);
		CacheService cacheService = (CacheService) SpringContextBean.getBean("cacheService");
		cacheService.loadAllCache();
		
	}
	
	public static String getWebRootRealPath(){
		return contextReakPath;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}



}
