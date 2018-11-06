package com.huateng.univer.order.business.service;

import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.exception.BizServiceException;

public interface OrderReadyService {

	public void cardReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

	public void deleteRecord(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

	public void deleteAllRecord(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public SellBuyOrderDTO insertBuyOrder(SellBuyOrderDTO sellBuyOrderDTO)
			throws BizServiceException;

	public SellBuyOrderDTO insertLoyaltyBuyOrder(SellBuyOrderDTO sellBuyOrderDTO)
			throws BizServiceException;

	public SellOrderDTO getSellOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	public void cardSegmentReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

	public void orderListAllReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

	public void updateOrigCardState(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;

	public void orderReadyForChangeCard(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;
}