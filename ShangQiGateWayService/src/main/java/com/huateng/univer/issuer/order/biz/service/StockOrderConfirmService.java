package com.huateng.univer.issuer.order.biz.service;

import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 库存订单流程service
 * 
 * @author xxl
 * 
 */
public interface StockOrderConfirmService {

	/**
	 * 批量提交订单
	 */
	public void batchSubmit(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	/**
	 * 批量取消订单
	 */
	public void batchCancel(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	/**
	 * 确认订单
	 */
	public void confirm(SellOrderDTO sellOrderDTO) throws BizServiceException;

	/**
	 * 退回订单
	 */
	public void reject(SellOrderDTO sellOrderDTO) throws BizServiceException;

	/**
	 * 退回到重新生成制卡文件
	 */
	public void rejectOrderAcceptToFileMake(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	/**
	 * 取消订单
	 */
	public void cancel(SellOrderDTO sellOrderDTO) throws BizServiceException;
	
	/**
	 * 卡号段订单接收
	 * */
	public void submitAcceptOrder(AcceptOrderDTO acceptOrderDTO)throws Exception;
	/**
	 * 卡号段订单删除
	 * */
	public void delete(AcceptOrderDTO acceptOrderDTO)throws Exception;
	/**
	 * 卡号段订单全部删除
	 * */
	public void deleteAll(AcceptOrderDTO acceptOrderDTO)throws Exception;
}
