package com.huateng.univer.issuer.cardcompany.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.CardCompanyExample;

/**
 * 制卡商管理service
 * 
 * @author xxl
 * 
 */
public interface CardCompanyService {

	/**
	 * 查询制卡商
	 */
	public PageDataDTO inquery(CardCompanyQueryDTO cardCompanyQueryDTO)
			throws BizServiceException;

	/**
	 * 根据条件查询制卡商
	 */
	public List<CardCompanyDTO> getCardCompanyDTOsByExample(
			CardCompanyExample cardCompanyExample) throws BizServiceException;

	/**
	 * 查看制卡商
	 */
	public CardCompanyDTO view(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException;

	/**
	 * 添加制卡商
	 */
	public void insert(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException;

	/**
	 * 更新制卡商
	 */
	public void update(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException;

	/**
	 * 修改制卡商状态
	 */
	public void modifyCardCompany(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException;

}
