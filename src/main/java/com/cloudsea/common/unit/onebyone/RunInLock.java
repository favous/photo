package com.cloudsea.common.unit.onebyone;

public class RunInLock {
/*
	private <T> T invoke(final OneByOne oneByOne, final int timeoutMsecs, final int expireMsecs, CallBack<T> callBack) {
        final String key = RedisCacheKeyConstants.REDIS_ONE_BY_ONE + oneByOne.getBizType() + "_" + oneByOne.getBizId();
        SedisLock sedisLock = new SedisLock(RedisUtil.redisClient, key, timeoutMsecs, expireMsecs);
        try {
            if (sedisLock.acquire()) { // 启用锁
                return callBack.invoke();
            } else {
                throw new AppException("bizType：" + oneByOne.getBizType() + "，bizId：" + oneByOne.getBizId() + "，并发执行！");
            }
        } catch (InterruptedException e) {
            throw new AppException("");
        } finally {
            sedisLock.release();
        }
    }
	
	public <T> T execute(OneByOne oneByOne, boolean waitInQueue, int timeoutMsecs, int expireMsecs, CallBack<T> callBack) {
        int timeoutMsecsTemp = DEFAULT_TIME_OUT_MSECS;
        int expireMsecsTemp = DEFAULT_EXPIRE_MSECS;
        // 需要排队
        if (waitInQueue) {
            // 当参数timeoutMsecs取值小于等于零时，则使用默认的排队10秒
            if (timeoutMsecs <= 0) {
                timeoutMsecsTemp = DEFAULT_TIME_OUT_MSECS;
            } else {
                timeoutMsecsTemp = timeoutMsecs;
            }

            // 不需要排队
        } else {
            timeoutMsecsTemp = 0;
        }

        // 当参数expireMsecs取值小于等于零时，则使用默认的有效期30秒
        if (expireMsecs <= 0) {
            expireMsecsTemp = DEFAULT_EXPIRE_MSECS;
        } else {
            expireMsecsTemp = expireMsecs;
        }

        return invoke(oneByOne, timeoutMsecsTemp, expireMsecsTemp, callBack);
    }*/
	
	public static <T> T run(Lock lock, CallBack<T> callBack) throws RuntimeException {
        try {
            if (lock.acquire()) { // 获取锁
                return callBack.invoke();
            } else {
                throw new RuntimeException("锁被占用，并发执行！");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	lock.release();
        }
    }

	public static <T> T run(CallBack<T> callBack, String bizType, String bizId) {
    	Lock lock = new RedisLock(bizType, bizId);
    	return run(lock, callBack);
	}
}
