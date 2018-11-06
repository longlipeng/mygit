/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderInputService1.java
 * Author:   13071598
 * Date:     2013-11-2 上午09:11:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

import java.util.List;

/**
 * 库存调拨订单录入接口<br>
 * 
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface StockTransferOrderQueryService {

    /**
     * 获得录入状态库存调拨订单
     * 
     * @param SellOrderQueryDTO
     * @return PageDataDTO
     * @throws BizServiceException
     * */
    PageDataDTO queryAllOrder(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

}
