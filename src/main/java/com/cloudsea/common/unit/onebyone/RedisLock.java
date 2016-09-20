package com.cloudsea.common.unit.onebyone;

import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

public class RedisLock extends Lock {
	
	ShardedJedis shardedJedis;

    /**
     * 锁拥有者标识
     */
    private String owner = UUID.randomUUID().toString().replaceAll("-", "");
    
    /** 业务类型 */
    private String bizType;

    /** 业务ID */
    private String bizId;
    
	public RedisLock(String bizType, String bizId) {
		super();
		this.bizType = bizType;
		this.bizId = bizId;
	}
	
	public RedisLock(String bizType, String bizId, int timeWait, int expireTime, int rollAskTime) {
		super(timeWait, expireTime, rollAskTime);
		this.bizType = bizType;
		this.bizId = bizId;
	}

	@Override
	boolean acquireLock() {
		final String key = KEY_TYPE + "_" + bizType + "_" + bizId;
		Jedis j = shardedJedis.getShard(key);
        return "OK".equals(j.set(key, owner, "NX", "PX", super.getExpireTime()));
	}

	@Override
	void releaseLock() {
		final String key = KEY_TYPE + "_" + bizType + "_" + bizId;
		if (owner.equals(shardedJedis.get(key))){			
			shardedJedis.del(key);
		}
	}

}
