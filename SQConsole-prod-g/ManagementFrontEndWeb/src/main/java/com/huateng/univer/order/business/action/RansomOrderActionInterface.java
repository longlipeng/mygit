package com.huateng.univer.order.business.action;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

public interface RansomOrderActionInterface {
	
	/**
	 * 批量进行赎回卡片
	 * @param sellOrderDTO
	 * @throws BizServiceException
	 */
	void submitOrder(SellOrderDTO sellOrderDTO) throws BizServiceException;
}
