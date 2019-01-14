package com.huateng.service.consumer.calculateWithMerchant.service;

/**
 * Classname CalculateWithMerchantService.java
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
 *		administrator		2012-10-23
 * =============================================================================
 */

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.consumer.calculateWithMerchant.dto.CalculateWithMerchantDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;


/**
 * @author administrator
 * 
 */
public class CalculateWithMerchantService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		CalculateWithMerchantDTO calculateWithMerchantDTO = (CalculateWithMerchantDTO) JSONObject.toBean(jsonDto, CalculateWithMerchantDTO.class);
		return baseDao.queryForList("calculate_with_merchant", "calculate_with_merchant",calculateWithMerchantDTO);
	}

}
