/**
 * Classname MDMSynchServic.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		lfr		2013-4-7
 * =============================================================================
 */

package com.huateng.univer.synch.MDMSynch;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.synch.dto.MerchantSynchDTO;
import com.huateng.univer.synch.dto.ShopSynchDTO;

/**
 * @author lfr
 * 
 */
public interface MDMSynchService {

	/**
	 * 商户信息同步
	 * 
	 * @param MerchantSynchDTO
	 * @return
	 * @throws BizServiceException
	 */
	public String merchantInfoSynch(MerchantSynchDTO merchantSynchDTO)
			throws BizServiceException;
    
	/**
	 * 新增商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantSynchDTO insertMerchant(MerchantSynchDTO merchantSynchDTO)
			throws BizServiceException;
    
	/**
	 * 更新商户信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public MerchantSynchDTO updateMerchant(MerchantSynchDTO merchantSynchDTO)
			throws BizServiceException;
    
	/**
	 * 商户号重复校验
	 * 
	 * @param MerchantSynchDTO
	 * @return
	 * @throws BizServiceException
	 */
	public int chkMchntCode(MerchantSynchDTO dto);
    
	/**
	 * 商户名重名校验
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException 
	 */
	public int chkMchntName(MerchantSynchDTO dto) throws BizServiceException;

	/**
	 * 门店信息同步
	 * 
	 * @param MerchantSynchDTO
	 * @return
	 * @throws BizServiceException
	 */
	public String shopInfoSynch(ShopSynchDTO shopSynchDTO)
			throws BizServiceException;

	/**
	 * 添加门店
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public ShopSynchDTO insertShop(ShopSynchDTO shopSynchDTO)
			throws BizServiceException;

	/**
	 * 更新门店
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void updateShop(ShopSynchDTO shopSynchDTO)
			throws BizServiceException;

	/**
	 * 门店名称重名校验
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public int chkShopName(ShopSynchDTO dto);

}
