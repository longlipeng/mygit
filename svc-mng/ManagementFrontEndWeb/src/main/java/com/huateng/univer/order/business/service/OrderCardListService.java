package com.huateng.univer.order.business.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputCardListDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.SellOrderCardList;

/**
 * 订单卡明细service
 * 
 * @author xxl
 * 
 */
public interface OrderCardListService {
	public void makeCardComplate(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	/**
	 * 修改制卡状态
	 */
	public PageDataDTO getOrderCardListForCardState(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException;

	/**
	 * 查询订单卡明细
	 */
	public List<SellOrderCardList> getOrderCardList(String orderId)
			throws Exception;

	/**
	 * 查询分页订单卡明细
	 */
	public PageDataDTO getOrderCardListPageData(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException;
	
	/**
	 * 查询分页订单录入卡
	 */
	public PageDataDTO getOrderInputCardListPageData(
			SellOrderInputCardListDTO sellOrderInputCardListDTO)
			throws BizServiceException;

	public List<String> getSuccessCardNoList(String orderId)
			throws BizServiceException;

	/**
	 * 查看制卡未完成数量
	 */
	public Integer countUnSuccessCard(String orderId)
			throws BizServiceException;
	/**
	 * 查看采购订单卡接收明细
	 */
	public PageDataDTO getOrderReceiveListPageData(
	        OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO)
	        throws BizServiceException;
	/**
	 * 制卡失败数量 用于退回时判断
	 */
	public Integer countFailCard(String orderId) throws BizServiceException;

	public SellOrderCardListDTO view(SellOrderCardListDTO sellOrderCardListDTO)
			throws BizServiceException;

	public void update(SellOrderCardListDTO sellOrderCardListDTO)
			throws BizServiceException;

	public void deleteCardListByOrderId(String orderId) throws Exception;

	public PageDataDTO getOrderCardListForCheckcard(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException;

	public void checkCard(SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException;

	public void endCheckCard(SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException;

	public SellOrderDTO hadCheckCard(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
}
