package com.huateng.univer.issuer.order.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 库存订单服务
 * 
 * @author xxl
 * 
 */
public interface StockOrderService {

	public PageDataDTO inquery(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;

	public SellOrderInputDTO initAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public SellOrderInputDTO insert(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public SellOrderInputDTO update(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public SellOrderDTO load(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public SellOrderCompositeDTO view(
			SellOrderCompositeDTO sellOrderCompositeDTO)
			throws BizServiceException;

	public SellOrderFlowDTO viewOrderFlow(SellOrderFlowDTO sellOrderFlowDTO)
			throws BizServiceException;

}
