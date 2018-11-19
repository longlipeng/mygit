package com.huateng.univer.issuer.cardLayout.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface CardLayoutService {

	/**
	 * 查询卡面信息
	 * 
	 * @param cardLayoutQueryDTO
	 * @return PageDataDTO
	 * @throws BizServiceException
	 * */
	public PageDataDTO inqueryCardLayout(CardLayoutQueryDTO cardLayoutQueryDTO)
			throws BizServiceException;

	public List<CardLayoutDTO> selectCardLayout(
			CardLayoutQueryDTO cardLayoutQueryDTO);

	/**
	 * 调用卡面信息
	 * 
	 * @param cardLayoutDTO
	 * @return CardLayoutDTO
	 * @throws BizServiceException
	 */
	public CardLayoutDTO viewCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException;

	/**
	 * 增加卡面信息
	 * 
	 * @param cardLayoutDTO
	 * 
	 * @throws BizServiceException
	 */
	public void insertCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException;

	/**
	 * 修改卡面信息
	 * 
	 * @param cardLayoutDTO
	 * 
	 * @throws BizServiceException
	 */
	public void updateCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException;

	/**
	 * 删除卡面信息
	 * 
	 * @param cardLayoutDTO
	 * 
	 * @throws BizServiceException
	 */
	public void deleteCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException;

	public void initCardLayout() throws BizServiceException;

	public CardLayoutDTO loadCardLayoutForEdit(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException;
}
