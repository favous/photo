package com.cloudsea.photo.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cloudsea.common.constants.RedisCacheKeyConstants;
import com.cloudsea.common.entity.Area;
import com.cloudsea.common.util.RedisUtil;

public class CacheUtil {

    public static Map<String, String> getDict(String code) {
        
        return null;
    }
    
    public static String getDictItem(String code, String key) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("dictCode can not be null");
        }
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("dictKey can not be null");
        }

        Map<String, String> dictInfo = getDict(code);
        if (null == dictInfo || dictInfo.size() == 0) {
            return null;
        }
        return dictInfo.get(key);
    }
    
    public static Area getArea(String code, String level) {
        return RedisUtil.getBean(RedisCacheKeyConstants.AREA + "_" + level);
    }

    
    
    
    
    
    

}
