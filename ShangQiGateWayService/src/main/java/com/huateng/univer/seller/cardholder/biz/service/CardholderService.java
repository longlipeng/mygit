package com.huateng.univer.seller.cardholder.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 持卡人service
 * 
 * @author xxl
 * 
 */
public interface CardholderService {

	/**
	 * 查看持卡人
	 */
	public CardholderDTO view(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 查询持卡人
	 */
	public PageDataDTO inquery(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException;

	/**
	 * 查询持卡人
	 */
	public PageDataDTO queryCardHolder(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException;

	/**
	 * 查询没有关联到orderList表的持卡人
	 */
	public CardholderDTO inqueryCardholderWithOrderList(
			CardholderQueryDTO cardholderQueryDTO) throws BizServiceException;

	/**
	 * 添加持卡人
	 */
	public void insert(CardholderDTO cardholderDTO) throws BizServiceException;

	public CardholderDTO insertCardholderDTO(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 导入持卡人
	 */
	public CardholderDTO insertCardholder(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 修改更新持卡人
	 */
	public void update(CardholderDTO cardholderDTO) throws BizServiceException;

	/**
	 * 管理持卡人新客户
	 */
	public void updateCust(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 删除持卡人
	 */
	public void delete(CardholderDTO cardholderDTO) throws BizServiceException;

	/**
	 * 通过卡号查询配送完成订单中的持卡人信息
	 * */
	public List<CardholderQueryDTO> getCardHolderByCardNo(String cardNo)
			throws BizServiceException;

	/**
	 * 记录记名库存卡的持卡人信息，并将持卡人信息与持卡人信息进行关联
	 */
	public void insertSignCardHolder(CardholderDTO cardholderDTO)
			throws BizServiceException;

	public CardholderDTO insertCardholderReturnIds(CardholderDTO cardholderDTO)
			throws BizServiceException;
}
