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
	 * ��P011���Ĳ��ҳֿ��������Ϣ
	 * @param cardholder
	 * @return
	 * @throws BizServiceException
	 */
	public int countBySomeExample(CardHolder cardholder);
	
	/**
	 * ��P011���ĸ��³ֿ��������Ϣ
	 * @param req
	 * @throws BizServiceException
	 */
	public void updateCardholderByReq(BasicMessage basicReq) throws BizServiceException;

	
	public boolean checkCardByIdNo(BasicMessage basicReq) throws BizServiceException;
	
	public CardHolder selectByCardNo(String cardNo);
	
	public List<CardHolder> selectByExample(CardHolderExample example);
}
