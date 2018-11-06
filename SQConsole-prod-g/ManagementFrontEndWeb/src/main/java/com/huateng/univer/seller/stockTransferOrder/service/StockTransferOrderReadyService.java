/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderReadyService.java
 * Author:   13071598
 * Date:     2013-11-7 下午03:38:33
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface StockTransferOrderReadyService {

    PageDataDTO queryStockTransferOrderAtReady(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

    SellOrderReadyResultDTO queryForReady(SellOrderReadyQueryDTO orderReadyQueryDTO) throws BizServiceException;

    PageDataDTO queryCardForReady(OrderReadyDTO orderReadyDTO) throws BizServiceException;

    void stockTransferOrderReadyByCard(OrderReadyDTO orderReadyDTO) throws BizServiceException;

    void deleteCheckedRecord(OrderReadyDTO orderReadyDTO) throws BizServiceException;

    void deleteAllRecord(SellOrderDTO sellOrderDTO) throws BizServiceException;

    void submitOrderReady(SellOrderDTO sellOrderDTO) throws BizServiceException;

    void cardAllReady(OrderReadyDTO orderReadyDTO) throws BizServiceException;

    void sendBackOrder(SellOrderDTO sellOrderDTO) throws BizServiceException;

}
