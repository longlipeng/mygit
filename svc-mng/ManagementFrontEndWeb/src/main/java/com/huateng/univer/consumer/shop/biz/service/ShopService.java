package com.huateng.univer.consumer.shop.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopQueryDTO;
import com.allinfinance.univer.consumer.shoppos.dto.ShopPosDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.exception.BizServiceException;

//import com.huateng.accorecard.authoritycard.dto.AuthorityCardDTO;

/**
 * author:zengfenghua
 */
public interface ShopService {

	/**
	 * 查询门店
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryShop(ShopQueryDTO dto) throws BizServiceException;

	/**
	 * 添加门店
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	public ShopDTO insertShop(ShopDTO dto) throws BizServiceException;

	/**
	 * 查看门店
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public ShopDTO viewShop(ShopDTO dto) throws BizServiceException;

	/**
	 * 更新门店
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	public void updateShop(ShopDTO dto) throws BizServiceException;

	public void deleteShop(ShopDTO dto) throws BizServiceException;

	public void insertContact(ContactDTO dto) throws BizServiceException;

	public ContactDTO viewContact(ContactDTO dto) throws BizServiceException;

	public void updateContact(ContactDTO dto) throws BizServiceException;

	public void deleteContact(ContactDTO dto) throws BizServiceException;

}