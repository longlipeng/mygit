package com.huateng.univer.order.business.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderMakeCardDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 订单制卡
 * 
 * @author xxl
 * 
 */

public interface OrderMakeCardService {

	/**
	 * 获取制卡商
	 */
	public SellOrderMakeCardDTO getCardCompanyList(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException;

	/**
	 * 下载制卡文件
	 */
	public SellOrderMakeCardDTO downMakeCardFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException;

	/**
	 * 下载卡PIN文件
	 */
	public SellOrderMakeCardDTO downPinFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException;

	/**
	 * 获取c生成的制卡文件
	 */
	public SellOrderMakeCardDTO makeMakeCardFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException;

	/**
	 * 获取c生成的卡PIN文件
	 */
	public SellOrderMakeCardDTO makeCardPINFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException;

	/**
	 * 查询卡商可以制卡的订单
	 */
	public PageDataDTO inqueryOrderForCardCompany(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException;

	/**
	 * 记录订单流程信息
	 */
	public void flowOperate(SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException;

	// public void submitOrderForOpenAccount(SellOrderDTO sellOrderDTO) throws
	// BizServiceException;
	public void submitOrderForOpenAccountWithoutForUpdate(
			SellOrderDTO sellOrderDTO) throws BizServiceException;

	public SellOrderDTO zhikaForUpdate(SellOrderDTO sellOrderDTO)
			throws BizServiceException;
}
