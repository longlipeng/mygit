package com.allinfinance.prepay.service;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

public interface StockOrderCommonService {
	public void addNewOrderFlow(SellOrderDTO sellOrderDTO,
			String orderFlowNode, String operateType) throws Exception;

}
