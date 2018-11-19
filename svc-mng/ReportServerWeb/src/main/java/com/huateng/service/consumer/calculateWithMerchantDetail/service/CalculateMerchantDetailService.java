/**
 * Classname SellerIssueSellDetailServiceImpl.java
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
 *		htd033		2012-11-8
 * =============================================================================
 */

package com.huateng.service.consumer.calculateWithMerchantDetail.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.consumer.calculateWithMerchantDetail.dto.CalculateWithMerchantDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class CalculateMerchantDetailService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {

		CalculateWithMerchantDetailDTO cwd = (CalculateWithMerchantDetailDTO) JSONObject
				.toBean(jsonDto, CalculateWithMerchantDetailDTO.class);
		return baseDao.queryForList("CalculateWithMerchantDetailReprt", "CalculateWithMerchantDetailReprt",cwd);
	}
}
