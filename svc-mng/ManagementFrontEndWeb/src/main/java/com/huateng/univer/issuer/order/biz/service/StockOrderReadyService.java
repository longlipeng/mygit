package com.huateng.univer.issuer.order.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 库存订单流程service
 * 
 * @author xxl
 * 
 */
public interface StockOrderReadyService {

	/**
	 * 查看订单明细和卡明细
	 */
	public SellOrderReadyResultDTO getOrderReadyList(
			SellOrderReadyQueryDTO orderReadyQueryDTO)
			throws BizServiceException;

	/**
	 * 订单准备,根据订单所有明细查库存
	 */
	public PageDataDTO getStockCardForReady(
			EntityStockQueryDTO entityStockQueryDTO) throws BizServiceException;

	/**
	 * 订单准备,添加指定卡号段到订单卡明细
	 */
	public void readyStockCardToCardList(EntityStockDTO entityStockDTO)
			throws BizServiceException;

	/**
	 * 删除已准备的订单卡明细
	 */
	public void deleteReadCardList(SellOrderCardListDTO sellOrderCardListDTO)
			throws BizServiceException;
}
