package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;

public interface CardholderService {
	
	public CardHolder insert(CardholderDTO cardholderDTO) throws BizServiceException;
	
	/**
	 * 按P011报文查找持卡人相关信息
	 * @param cardholder
	 * @return
	 * @throws BizServiceException
	 */
	public int countBySomeExample(CardHolder cardholder);
	
	/**
	 * 按P011报文更新持卡人相关信息
	 * @param req
	 * @throws BizServiceException
	 */
	public void updateCardholderByReq(BasicMessage basicReq) throws BizServiceException;

	
	public boolean checkCardByIdNo(BasicMessage basicReq) throws BizServiceException;
	
	public CardHolder selectByCardNo(String cardNo);
	
	public List<CardHolder> selectByExample(CardHolderExample example);
}
