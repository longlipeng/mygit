package com.huateng.univer.order.business.action;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.service.OrderMakeCardService;

public class OrderMakeCardActionImpl implements OrderMakeCardActionInterface {
	private static Logger logger = Logger.getLogger(OrderMakeCardActionImpl.class);
	private OrderMakeCardService orderMakeCardService;

	@Override
	public void submitOrderForOpenAccount(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		logger.debug("开始制卡开户");
		//准备批量制卡数据，并发送报文
		orderMakeCardService.zhikaForUpdate(sellOrderDTO);
		orderMakeCardService.submitOrderForOpenAccountWithoutForUpdate(sellOrderDTO);

	}

	public OrderMakeCardService getOrderMakeCardService() {
		return orderMakeCardService;
	}

	public void setOrderMakeCardService(OrderMakeCardService orderMakeCardService) {
		this.orderMakeCardService = orderMakeCardService;
	}
	
}
