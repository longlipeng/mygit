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
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.allinfinance.univer.seller.order.dto.StockTransferOrderAcceptDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.SellOrderCardList;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface StockTransferOrderAcceptService {

    PageDataDTO queryStockTransferOrderAtAccept(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

    List<String> queryOrderCardList(SellOrderDTO sellOrderDTO) throws BizServiceException;

    void submitAccept(StockTransferOrderAcceptDTO stockTransferOrderAcceptDTO) throws BizServiceException;

    void checkAccept(SellOrderDTO sellOrderDTO) throws BizServiceException;

    void sendBackAtAccept(SellOrderDTO sellOrderDTO) throws BizServiceException;
    
    void closeAccept(SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;

}
