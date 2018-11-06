package com.huateng.univer.order.business.action;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

public interface OrderPaymentActionInterface {
	
	/**
	 * 批量进行开户和制卡操作
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	void submitOrderForPayment(SellOrderDTO sellOrderDTO) throws BizServiceException;
}
