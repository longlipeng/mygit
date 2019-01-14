package com.huateng.univer.order.business.action;


import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.service.RansomOrderService;

public class RansomOrderActionImpl implements RansomOrderActionInterface {
	private static Logger logger = Logger.getLogger(RansomOrderActionImpl.class);
	private RansomOrderService ransomOrderService;

	@Override
	public void submitOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		logger.debug("开始批量赎回");
		// 获取订单信息及更新订单状态为处理中
		ransomOrderService.submitOrderForUpdate(sellOrderDTO);
		ransomOrderService.submitOrder(sellOrderDTO);

	}

	public RansomOrderService getRansomOrderService() {
		return ransomOrderService;
	}

	public void setRansomOrderService(RansomOrderService ransomOrderService) {
		this.ransomOrderService = ransomOrderService;
	}
	
	
	
}
