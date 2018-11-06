/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemService.java
 * Author:   13040443
 * Date:     2013-11-6 下午02:21:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.TradeItemDealedQueryDto;
import com.huateng.framework.exception.BizServiceException;

/**
 * 交易已处理查询
 * 
 * @author yanbin
 */
public interface TradeItemDealedService {

    /**
     * 获取所有已处理数据
     * 
     * @param tradeItemDealedQueryDto
     * @return
     * @throws BizServiceException
     */
    public PageDataDTO query(TradeItemDealedQueryDto tradeItemDealedQueryDto) throws BizServiceException;

}
