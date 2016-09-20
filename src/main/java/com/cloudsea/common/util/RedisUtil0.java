package com.cloudsea.common.util;
///*
// * Copyright (C), 2002-2015, 苏宁易购电子商务有限公司
// * FileName: RedisUtil.java
// * Author:   luwanchuan
// * Date:     2015年3月11日 下午9:36:06
// * Description: //模块目的、功能描述      
// * History: //修改记录
// * <author>      <time>      <version>    <desc>
// * 修改人姓名             修改时间            版本号                  描述
// */
//package com.cloudsea.common.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.suning.framework.sedis.ShardedJedisAction;
//import com.suning.framework.sedis.ShardedJedisClient;
//import com.suning.framework.sedis.impl.ShardedJedisClientImpl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import redis.clients.jedis.ShardedJedis;
//
///**
// * Redis操作工具类
// * 
// * @author luwanchuan
// * @see [相关类/方法]（可选）
// * @since [产品/模块版本] （可选）
// */
//public class RedisUtil {
//
//    /**
//     * 默认时间，作为常量，可以使用该值
//     */
//    public static final int DEFAULT_EXPIRE_TIME = 1800;
//
//    /**
//     * redis客户端，配置文件通过SCM读取
//     */
//    public static ShardedJedisClient redisClient = new ShardedJedisClientImpl("redis.conf");
//
//    /**
//     * 日志
//     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
//
//    /**
//     * 过期标识，大于0才会设置过期时间
//     */
//    private static final int EXPIRE_FLAG = 0;
//
//    /**
//     * 向redis中存放键值对 <br>
//     * 当且仅当expireSeconds大于零的时候才会设置过期时间
//     * 
//     * @param key
//     * @param value
//     * @param expireSeconds
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static void putKeyValue(final String key, final String value, final int expireSeconds) {
//        if (StringUtils.isNotNull(key) && StringUtils.isNotNull(value)) {
//            try {
//                redisClient.execute(new ShardedJedisAction<String>() {
//                    public String doAction(ShardedJedis jedis) {
//                        jedis.set(key, value);
//                        if (expireSeconds > EXPIRE_FLAG) {
//                            jedis.expire(key, expireSeconds);
//                        }
//                        return null;
//                    }
//                });
//            } catch (Exception e) {
//                LOGGER.error("Redis存值方法putKeyValue发生异常，异常信息为：", e);
//            }
//        }
//    }
//
//    /**
//     * 向redis中存放键值对，会抛异常 <br>
//     * 当且仅当expireSeconds大于零的时候才会设置过期时间
//     * 
//     * @param key
//     * @param value
//     * @param expireSeconds
//     * @throws Exception
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static void putKeyValueWithException(final String key, final String value, final int expireSeconds)
//            throws Exception {
//        if (StringUtils.isNotNull(key) && StringUtils.isNotNull(value)) {
//            try {
//                redisClient.execute(new ShardedJedisAction<String>() {
//                    public String doAction(ShardedJedis jedis) {
//                        jedis.set(key, value);
//                        if (expireSeconds > EXPIRE_FLAG) {
//                            jedis.expire(key, expireSeconds);
//                        }
//                        return null;
//                    }
//                });
//            } catch (Exception e) {
//                LOGGER.error("Redis存值方法putKeyValue发生异常，异常信息为：", e);
//                throw e;
//            }
//        }
//    }
//
//    /**
//     * 通过Key从redis中获取值 <br>
//     * 
//     * @param key：存放在redis中key
//     * @return
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static String getValue(final String key) {
//        if (StringUtils.isNull(key)) {
//            return null;
//        }
//        try {
//            return redisClient.execute(new ShardedJedisAction<String>() {
//                public String doAction(ShardedJedis jedis) {
//                    return jedis.get(key);
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error("Redis取值方法getValue发生异常，异常信息为：", e);
//            return null;
//        }
//    }
//
//    public static Long setnx(final String key, final String value) {
//        if (StringUtils.isNotNull(key) && StringUtils.isNotNull(value)) {
//            try {
//                return redisClient.execute(new ShardedJedisAction<Long>() {
//                    public Long doAction(ShardedJedis jedis) {
//                        return jedis.setnx(key, value);
//                    }
//                });
//            } catch (Exception e) {
//                LOGGER.error("Redis存值方法putKeyValue发生异常，异常信息为：", e);
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 通过Key从redis中获取值，会抛异常
//     * 
//     * @param key：存放在redis中key
//     * @return
//     * @throws Exception
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static String getValueWithException(final String key) {
//        if (StringUtils.isNull(key)) {
//            return null;
//        }
//        try {
//            return redisClient.execute(new ShardedJedisAction<String>() {
//                public String doAction(ShardedJedis jedis) {
//                    return jedis.get(key);
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error("Redis取值方法getValue发生异常，异常信息为：", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 向redis中添加对象，对象建议是标准的JavaBean或者Map类型
//     * 
//     * @param key
//     * @param beanObj
//     * @param expireSeconds 当且仅当expireSeconds大于零的时候才会设置过期时间
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static void putBeanObj(String key, Object beanObj, int expireSeconds) {
//        if (beanObj != null && StringUtils.isNotNull(key)) {
//            putKeyValue(key, JSON.toJSONString(beanObj, SerializerFeature.WriteClassName), expireSeconds);
//        }
//    }
//
//    /**
//     * 向redis中添加对象，对象建议是标准的JavaBean或者Map类型，会抛异常
//     * 
//     * @param key
//     * @param beanObj
//     * @param expireSeconds 当且仅当expireSeconds大于零的时候才会设置过期时间
//     * @throws Exception
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static void putBeanObjWithException(String key, Object beanObj, int expireSeconds) throws Exception {
//        if (beanObj != null && StringUtils.isNotNull(key)) {
//            putKeyValue(key, JSON.toJSONString(beanObj, SerializerFeature.WriteClassName), expireSeconds);
//        }
//    }
//
//    /**
//     * Redis中获取对象，该对象通过putBeanObj方法放入<br>
//     * 取不到返回空
//     * 
//     * @param key
//     * @return
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T getBeanObj(String key) {
//        String value = getValue(key);
//        if (StringUtils.isNull(value)) {
//            return null;
//        }
//        try {
//            return (T) JSON.parse(value);
//        } catch (JSONException e) {
//            LOGGER.error("JSON转换发生异常，异常信息为：", e);
//            return null;
//        }
//    }
//
//    /**
//     * Redis中获取对象，该对象通过putBeanObj方法放入，会抛异常<br>
//     * 取不到返回空
//     * 
//     * @param key
//     * @return
//     * @throws Exception
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T getBeanObjWithException(String key) {
//        String value = getValue(key);
//        if (StringUtils.isNull(value)) {
//            return null;
//        }
//        try {
//            return (T) JSON.parse(value);
//        } catch (JSONException e) {
//            LOGGER.error("JSON转换发生异常，异常信息为：", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 
//     * 将key所对应的值从redis从删除 <br>
//     * 〈功能详细描述〉
//     * 
//     * @param key
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static void delete(final String key) {
//        try {
//            redisClient.execute(new ShardedJedisAction<String>() {
//                public String doAction(ShardedJedis jedis) {
//                    jedis.del(key);
//                    return null;
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error("Redis删除方法delete发生异常，异常信息为：", e);
//        }
//    }
//
//    /**
//     * 查看redis中key对应的数据是否存在 <br>
//     * 
//     * @param key
//     * @return
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static boolean isExist(final String key) {
//        try {
//            return redisClient.execute(new ShardedJedisAction<Boolean>() {
//                public Boolean doAction(ShardedJedis jedis) {
//                    return jedis.exists(key);
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error("Redis查看数据是否存在方法isExist发生异常，异常信息为：", e);
//            return false;
//        }
//    }
//
//    /**
//     * redis中对应的key，其value自增1，并返回自增后的值<br>
//     * 前提:这个key中存储的value为数值型字符串<br>
//     * Increment the number stored at key by one. If the key does not exist or contains a value of a wrong type, set the
//     * key to the value of "0" before to perform the increment operation.
//     * 
//     * @param key
//     * @return Integer reply, this commands will reply with the new value of key after the increment.
//     * @throws Exception
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    public static long increaseByOne(final String key) {
//        try {
//            return redisClient.execute(new ShardedJedisAction<Long>() {
//                public Long doAction(ShardedJedis jedis) {
//                    return jedis.incr(key);
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error("Redis中key对应的value自增1方法increaseByOne发生异常，异常信息为：", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//}
