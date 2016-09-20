package com.cloudsea.common.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter{
	
	String escapeUrlStr;
	String applicationName;//tomcat部署指定的项目路径名

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		escapeUrlStr = filterConfig.getInitParameter("escapeUrl");
		applicationName = filterConfig.getInitParameter("applicationName");
		applicationName = applicationName == null ? "" : applicationName;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		String servletPath = request.getServletPath();
		String[] urls = escapeUrlStr.split(",");
		
		for (String escapeUrl : urls){
			escapeUrl = escapeUrl.trim();
			if (matchUrl(servletPath, escapeUrl)){
				chain.doFilter(req, resp);
				return;
			}
		}
		
		HttpSession session = request.getSession();
		Object currentUser = session.getAttribute("currentUser");
		
		if (currentUser == null) {
			HttpServletResponse response = (HttpServletResponse) resp;
			response.sendRedirect(applicationName + "/user/admin");
			return;
		}
		
		chain.doFilter(req, resp);
	}

	private boolean matchUrl(String servletPath, String escapeUrl) {
		if (escapeUrl.contains("*")){
			escapeUrl.replace("*", ".*");
		}
		Pattern pattern = Pattern.compile(escapeUrl);
		Matcher matcher = pattern.matcher(servletPath);
		return matcher.matches();
	}

	@Override
	public void destroy() {
		
	}

}
