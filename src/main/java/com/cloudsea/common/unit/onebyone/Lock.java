package com.cloudsea.common.unit.onebyone;

import com.cloudsea.common.constants.RedisCacheKeyConstants;

public abstract class Lock {

    /** 业务类型 */
	public static final String KEY_TYPE = RedisCacheKeyConstants.ONE_BY_ONE;

    private int timeWait = 10 * 1000; // 线程等待时间

    private int expireTime = 600 * 1000; // 持锁超时时间，防止锁被无限占用，线程在入锁以后，无限的执行等待
    
    private int rollAskTime = 100; // 轮询锁的时间间隔

    private boolean locked = false;
    
    public Lock() {
    	super();
    }
    
    public Lock(int timeWait, int expireTime, int rollAskTime) {
    	super();
    	this.timeWait = timeWait > 0 ? timeWait : this.timeWait;
    	this.expireTime = expireTime > 0 ? expireTime : this.expireTime;
    	this.rollAskTime = rollAskTime > 0 ? rollAskTime : this.rollAskTime;
    }
    
    public int getTimeWait() {
    	return timeWait;
    }
    
    public void setTimeWait(int timeWait) {
    	this.timeWait = timeWait;
    }
    
    public int getExpireTime() {
    	return expireTime;
    }
    
    public void setExpireTime(int expireTime) {
    	this.expireTime = expireTime;
    }
    
    public int getRollAskTime() {
    	return rollAskTime;
    }
    
    public void setRollAskTime(int rollAskTime) {
    	this.rollAskTime = rollAskTime;
    }
    
    
    abstract boolean acquireLock();

	abstract void releaseLock();

	public final synchronized boolean acquire() throws InterruptedException {
        int timeout = timeWait;
        while (timeout >= 0) {
        	if (acquireLock()){
                locked = true;
                return true;
        	}
        	timeout -= rollAskTime;
            Thread.sleep(rollAskTime);
        }
		return false;
	}
	
	public final synchronized void release() {
		if (locked){
			releaseLock();
			locked = false;
		}
	}

}
