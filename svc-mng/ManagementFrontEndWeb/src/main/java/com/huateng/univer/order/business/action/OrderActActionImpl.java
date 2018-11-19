package com.huateng.univer.order.business.action;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.service.OrderMakeCardService;
import com.huateng.univer.order.business.service.SubmitOrderService;

public class OrderActActionImpl implements OrderActActionInterface {
	private static Logger logger = Logger.getLogger(OrderMakeCardActionImpl.class);
	private SubmitOrderService submitOrderService;

	@Override
	public void submitOrderForAct(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		logger.debug("开始激活订单");
		//准备批量激活数据，并发送报文
		submitOrderService.updateOrderActState(sellOrderDTO);
		submitOrderService.sumbitForActive(sellOrderDTO);

	}

	public SubmitOrderService getSubmitOrderService() {
		return submitOrderService;
	}

	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
		this.submitOrderService = submitOrderService;
	}

}
