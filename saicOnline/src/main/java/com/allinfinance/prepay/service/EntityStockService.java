package com.allinfinance.prepay.service;

import java.util.List;

public interface EntityStockService {
	
	public void addStockOperaterRecord(String cardNo, String orderId,
			String entityId, String functionRoleId, String orderFlowNode,
			String operaterType, String stockState, String operateUser)
			throws Exception;
	
	public void delStockOperaterRecord(String orderId)
			throws Exception;

}
