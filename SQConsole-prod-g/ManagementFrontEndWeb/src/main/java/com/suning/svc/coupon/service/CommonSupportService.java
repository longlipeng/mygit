/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CommonServiceSupport.java
 * Author:   13040443
 * Date:     2013-10-29 下午02:33:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import java.util.List;

import com.allinfinance.svc.coupon.dto.TradeItemTempDto;

/**
 * 公共的service 支持接口
 * 
 * @author yanbin
 */
public interface CommonSupportService {

    /**
     * 根据批次号，分组获取汇总获取交易记录
     * 
     * @param batchNo
     * @return
     */
    List<TradeItemTempDto> getGroupTradesByBatchNo(long batchNo);

    /**
     * 根据批次号，获取不分组，不汇总的交易单条记录
     * 
     * @param batchNo
     * @return
     */
    List<TradeItemTempDto> getTradesByBatchNo(long batchNo);

    /**
     * 根据交易类型，方向获取批次号
     * 
     * @param tradeType
     * @param direction
     * @return
     */
    long getBatchNO(String tradeType, String direction);

}
