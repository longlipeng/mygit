package com.huateng.univer.order.business.service;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SendBackOrderService {

	public void sendBackAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void sendBackAtReady(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void sendBackAtHandOut(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void sendBackAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void sendBackAtOrderImmdeiatelyCredit(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void sendBackAtRansome(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

}
