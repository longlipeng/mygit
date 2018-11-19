package com.huateng.univer.order.business.service;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.huateng.framework.exception.BizServiceException;

public interface CancelOrderService {

	public void cancelOrderAtInput(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;

	public void cancelOrderAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

}
