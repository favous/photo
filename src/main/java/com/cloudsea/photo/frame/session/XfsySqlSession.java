package com.cloudsea.photo.frame.session;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author zhangxiaorong
 * 2014-12-15
 */
@Repository
public class XfsySqlSession extends SqlSessionTemplate implements IXfsySqlSession  {

	@Autowired
	public XfsySqlSession(@Qualifier("sqlSessionFactory") SqlSessionFactory tmsSqlSessionFactory) {
		super(tmsSqlSessionFactory);
	}


	@Override
	public SqlSession getSqlSession() {
		return this;
	}

}
