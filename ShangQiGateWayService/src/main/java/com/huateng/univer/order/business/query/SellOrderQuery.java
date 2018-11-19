package com.huateng.univer.order.business.query;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.CardNoSectionDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.SellOrderList;

public interface SellOrderQuery {

	public PageDataDTO query(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;

	/**
	 * 销售部门订单录入节点
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO queryOrderAtSellInput(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;

	/**
	 * 采购部门订单录入节点
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO queryOrderAtBuyInput(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;

	public PageDataDTO queryOrderAtConfrim(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;

	public PageDataDTO queryOrderAtSellConfrim(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

	public PageDataDTO queryOrderAtBuyConfrim(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

	public PageDataDTO queryOrderForOrderAccept(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

	public SellOrderCompositeDTO getSellOrderDTO(
			SellOrderCompositeDTO sellOrderCompositeDTO)
			throws BizServiceException;

	public PageDataDTO getCardForSellOrderReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException;

	/**
	 * 订单配送确定节点
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws Exception
	 */
	public PageDataDTO queryOrderAtSendConfirm(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

	public PageDataDTO queryOrderActivate(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException;
	/**
	 * 
	 * 功能描述: <br>
	 * 查找非现金、银行卡满足条件的待激活的订单
	 *
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
    public PageDataDTO queryOrderActivateEx(SellOrderQueryDTO sellOrderQueryDTO)
    throws BizServiceException;
    
	/**
	 * 购卡、换卡订单打印凭证信息查询
	 * 
	 * @author Yifeng.Shi
	 * @since 2012-02-06
	 * */
	public SellOrderDTO queryOrderPrintCert(SellOrderDTO orderDTO)
			throws BizServiceException;

	/**
	 * 凭证打印充值订单生成连续、断续卡号信息
	 * */
	public List<CardNoSectionDTO> opeCardNoSectionForCreditOrder(String orderId)
			throws Exception;

	public List<String> opeCardNoSection(String orderId, String orderType)
			throws Exception;

	public List<CardNoSectionDTO> opeCardNoSectionForUnsign(
			List<SellOrderList> orderLists) throws Exception;
	/**
	 * 接收卡号段
	 * @throws Exception 
	 * */
	public void accept(AcceptOrderDTO acceptOrderDTO)throws Exception;
	 /**
     * 订单根据卡号段删除
     * */
	public void delete(AcceptOrderDTO acceptOrderDTO)throws Exception;
	/**
     * 订单根据卡号段删除
     * */
	public void deleteAll(AcceptOrderDTO acceptOrderDTO)throws Exception;
}
