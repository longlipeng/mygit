package com.huateng.univer.order.business.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SellOrderPaymentService {

	/**
	 * 订单支付方式分页查询
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public PageDataDTO queryPage(SellOrderPaymentQueryDTO dto)
			throws BizServiceException;

	/**
	 * 订单付款方式添加
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return null
	 * */
	public void insertOrderPayment(SellOrderPaymentDTO dto)
			throws BizServiceException;

	/**
	 * 订单付款方式删除
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return null
	 * */
	public void deleteOrderPayment(SellOrderPaymentDTO dto)
			throws BizServiceException;

	/**
	 * 查询打印付款信息
	 * */
	public SellOrderDTO queryOrderForPaymentPrint(SellOrderDTO dto)
			throws BizServiceException;

	public List<SellOrderPaymentDTO> queryOrderPayments(String orderId);

	/**
	 * 合并付款
	 * 
	 * @param sellOrderPaymentDTO
	 * @throws BizServiceException
	 */
	public void combinePayment(SellOrderPaymentDTO sellOrderPaymentDTO)
			throws BizServiceException;

	/**
	 * 合并付款LIST
	 */
	public SellOrderDTO combineList(SellOrderDTO sellOrderDTO) throws Exception;

	/**
	 * 通过订单id查询相关银行信息
	 */
	public List<BankDTO> queryBankInfoByOrderId(SellOrderDTO sellOrderDTO)
			throws Exception;

	public void delete(SellOrderPaymentDTO dto) throws Exception;

	public void confirm(SellOrderPaymentDTO sellOrderPaymentDTO)
			throws BizServiceException;

}