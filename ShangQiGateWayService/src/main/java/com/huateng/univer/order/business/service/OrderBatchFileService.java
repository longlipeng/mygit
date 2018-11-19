package com.huateng.univer.order.business.service;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

public interface OrderBatchFileService {
	public void insertOrderFile(String fileName, SellOrderDTO sellOrderDTO);
}
