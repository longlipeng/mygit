/**
 * Classname OrderSendConfirmActionImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wanglu		2013-1-14
 * =============================================================================
 */

package com.huateng.univer.order.business.action;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.order.business.service.SubmitOrderService;

/**
 * @author wanglu
 *
 */
public class OrderSendConfirmActionImpl implements
		OrderSendConfimActionInterface {
	private static Logger logger = Logger.getLogger(OrderMakeCardActionImpl.class);
	private SubmitOrderService submitOrderService;
	/* (non-Javadoc)
	 * @see com.huateng.univer.order.business.action.OrderSendConfimActionInterface#submitOrderAtSendConfirm(com.huateng.univer.seller.order.dto.SellOrderDTO)
	 */
	@Override
	public void submitOrderAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		logger.debug("开始充值激活");
		// 获取订单信息及更新订单状态为处理中
		submitOrderService.submitOrder(sellOrderDTO);
		submitOrderService.submitOrderSendConfirm(sellOrderDTO);

	}
	public SubmitOrderService getSubmitOrderService() {
		return submitOrderService;
	}
	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
		this.submitOrderService = submitOrderService;
	}

	
}
