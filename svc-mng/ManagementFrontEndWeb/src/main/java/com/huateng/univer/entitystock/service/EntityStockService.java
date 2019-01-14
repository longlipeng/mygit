package com.huateng.univer.entitystock.service;

import java.util.List;

import com.huateng.framework.ibatis.model.EntityStockOperater;
import com.huateng.framework.ibatis.model.EntityStockOperaterExample;
import com.huateng.framework.ibatis.model.SellOrder;

/**
 * 库存信息service
 * 
 * @author xxl
 * 
 */
public interface EntityStockService {
	/**
	 * 修改过卡库存状态
	 */
	public void modifyStockState(List<String> cardNos,
			String currentStockState, String newStockState) throws Exception;

	/**
	 * 修改库存状态
	 */
	public void modifyStockStateAndEntity(List<String> cardNos,
			String newEntityId, String currentStockState, String newStockState)
			throws Exception;

	public void addStockRecord(List<String> cardNos, SellOrder sellOrder,
			String entityId, String operateUser) throws Exception;

	public void addStockOperaterRecord(List<String> cardNos, String orderId,
			String entityId, String functionRoleId, String orderFlowNode,
			String operaterType, String stockState, String operateUser)
			throws Exception;

	public List<EntityStockOperater> queryEntityStockOperaters(
			EntityStockOperaterExample entityStockOperaterExample)
			throws Exception;

	public void deleteStockOperaterRecord(List<String> cardNos, String orderId)
			throws Exception;
	public void addStockOperaterRecord(String cardNo, String orderId,
			String entityId, String functionRoleId, String orderFlowNode,
			String operaterType, String stockState, String operateUser)
			throws Exception;
}
