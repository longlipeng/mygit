/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchTradeProcessDao.java
 * Author:   13040443
 * Date:     2013-10-29 下午07:48:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.ibatis.dao;

import java.util.Date;
import java.util.List;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;

/**
 * 批量处理交易的Dao
 * 
 * @author yanbin
 */
public interface CommonSupportServiceDao {

    /**
     * 根据batchNo获取所有的批量，分组汇总
     * 
     * @param batchNo
     * @return
     */
    List<TradeItemTempDto> selectGroupByBatchNo(long batchNo);

    /**
     * 根据batchNo获取批量，不分组，不汇总
     * 
     * @param batchNo
     * @return
     */
    List<TradeItemTempDto> selectByBatchNo(long batchNo);

    /**
     * 根据类型获取对应类型需要处理的最小id 和 总数
     * 
     * @param tradeType
     * @param direction
     * @return
     */
    TradeItemTempDto selectMinIdAndCount(String tradeType, String direction, Date spaceTime, Long counts);

    /**
     * 处理数据设置批量号
     * 
     * @param batchNo
     */
    void updateBatch(long batchNo, String tradeType, String direction, Date spaceTime, Long counts);

    /**
     * 分批处理设置批量号
     * 
     * @param batchNo
     * @param minId
     * @param maxId
     */
    void updateBatch(long batchNo, long minId, long maxId, String tradeType, String direction, Date spaceTime,
            Long counts);

}
