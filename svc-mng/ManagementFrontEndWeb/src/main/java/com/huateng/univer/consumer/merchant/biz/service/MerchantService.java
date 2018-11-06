package com.huateng.univer.consumer.merchant.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantTxnQueryDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 商户管理服务接口
 * 
 * @author zengfenghua
 */
public interface MerchantService {
	/**
	 * 查询商户接口
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryMerchant(MerchantQueryDTO dto)
			throws BizServiceException;

	/**
	 * 初始化商户
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantDTO initMerchant(MerchantDTO dto) throws BizServiceException;

	public MerchantDTO initWebSiteName(MerchantDTO dto)
			throws BizServiceException;

	/**
	 * 添加商户接口
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantDTO insertMerchant(MerchantDTO dto)
			throws BizServiceException;

	public void updateMerchant(MerchantDTO dto) throws BizServiceException;

	public MerchantDTO viewMerchant(MerchantDTO dto) throws BizServiceException;

	public void deleteMerchant(MerchantDTO dto) throws BizServiceException;

	public MerchantDTO loadMerchant(MerchantDTO dto) throws BizServiceException;

	/**
	 * 联系人
	 */
	public void insertContact(ContactDTO dto) throws BizServiceException;

	public ContactDTO viewContact(ContactDTO dto) throws BizServiceException;

	public void updateContact(ContactDTO dto) throws BizServiceException;

	public void deleteContact(ContactDTO dto) throws BizServiceException;

	public String checkWebName(MerchantDTO merchantDTO)
			throws BizServiceException;

	public MerchantDTO loadForModifyPassowrd(MerchantDTO merchantDTO)
			throws BizServiceException;

	public void updatePart(MerchantDTO merchantDTO) throws BizServiceException;

	public MerchantDTO selectMerchantByKey(MerchantDTO merchantDTO)
			throws BizServiceException;

	public PageDataDTO merchantTxnQuery(MerchantTxnQueryDTO merchantTxnQueryDTO)
			throws BizServiceException;

	public MerchantTxnQueryDTO loadAllMerchant(
			MerchantTxnQueryDTO merchantTxnQueryDTO) throws BizServiceException;

	public List<ShopDTO> getShopListByMerchantId(MerchantTxnQueryDTO merchantDTO)
			throws BizServiceException;

	public MerchantDTO getMerchantDTO(MerchantDTO dto)
			throws BizServiceException;

	/**
	 * 查询商户信息（审核）
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryMerchantVerifier(MerchantQueryDTO dto)
			throws BizServiceException;

	/**
	 * 查询外部商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryExternalMerchant(MerchantQueryDTO dto)
	        throws BizServiceException ;
	
	public void updateConMerchant(MerchantDTO dto) throws BizServiceException;

	public MerchantDTO viewMerchantAfterAdd(MerchantDTO dto)
			throws BizServiceException;
	public void frozen(MerchantDTO dto) throws BizServiceException;
	public void unfrozen(MerchantDTO dto) throws BizServiceException;
}