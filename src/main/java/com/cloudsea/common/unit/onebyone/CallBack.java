/*
 * Copyright (C), 2002-2015, 苏宁易购电子商务有限公司
 * FileName: CallBack.java
 * Author:   luwanchuan
 * Date:     2015年3月10日 下午8:41:07
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cloudsea.common.unit.onebyone;

/**
 * 回调接口
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CallBack<T> {

    /**
     * 调用
     * 
     * @return 结果
     */
    T invoke();

}
