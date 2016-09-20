package com.cloudsea.common.unit.onebyone;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class DBLock extends Lock {
	
	SqlSessionFactory sqlSessionFactory;
    
    /** 业务类型 */
    private String bizType;

    /** 业务ID */
    private String bizId;
    
	public DBLock(String bizType, String bizId) {
		super();
		this.bizType = bizType;
		this.bizId = bizId;
	}
	
	public DBLock(String bizType, String bizId, int timeWait, int expireTime, int rollAskTime) {
		super(timeWait, expireTime, rollAskTime);
		this.bizType = bizType;
		this.bizId = bizId;
	}

	@Override
	boolean acquireLock() {
		final String key = KEY_TYPE + "_" + bizType + "_" + bizId;
		int len = insertLock(key);
		if (len > 0){
			return true;
		}

		SqlSession session = sqlSessionFactory.openSession();
		int insertTime = session.selectOne("", key);
		if (insertTime + getExpireTime() > System.currentTimeMillis()) {
			len = session.update("", new Object[]{key, System.currentTimeMillis()});
			return len > 0;
		}

        return false;
	}

	@Override
	void releaseLock() {
		final String key = KEY_TYPE + "_" + bizType + "_" + bizId;
		deleteLock(key);
	}

	/**
	 * 
	 * @param key 做为表的ID插入表中，能插入就是占有锁，不能插入表示锁被占用
	 * @return
	 * @throws RuntimeException
	 */
	public int insertLock(String key) throws RuntimeException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession(TransactionIsolationLevel.NONE);
			int len = session.insert("", key);
			session.getConnection().commit();
			return len;
		} catch (Exception e) {
			throw new RuntimeException("插入数据库并发锁（" + key + "）时发生异常", e);
		} finally{
			if (session != null)
				session.close();
		}
	}
	
	public int deleteLock(String key) throws RuntimeException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int len = session.delete("", key);
			session.getConnection().commit();
			return len;
		} catch (Exception e) {
			throw new RuntimeException("删除数据库并发锁（" + key + "）时发生异常", e);
		} finally{
			if (session != null)
				session.close();
		}
	}

}
