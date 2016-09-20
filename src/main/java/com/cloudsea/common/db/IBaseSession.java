package com.cloudsea.common.db;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 
 * @author zhangxiaorong
 * 2015-3-21
 */
public interface IBaseSession {
	
	
	/**
	 * 获得SqlSession
	 * @return
	 */
	public SqlSession getSqlSession();
		
	public SqlSessionFactory getSqlSessionFactory();
  
}
