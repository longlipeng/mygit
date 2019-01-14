package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;

public interface OrderService {
	public SellOrderInputDTO orderInsert(SellOrderDTO sellOrderDTO)
			throws BizServiceException ;
	
	public SellOrderInputDTO insertCardholderList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException ;
	
	public SellOrderInputDTO initAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException;
	public void updateOrderStat(String orderId)
			throws BizServiceException;
	public void delSellOrderCardList(String orderId) throws Exception;
	
	public void insertOrderListForChangeCardOrder(SellOrderListDTO sellOrderListDTO) throws BizServiceException;
	
	public void insertChangeCardOrderOrigCard(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
}
