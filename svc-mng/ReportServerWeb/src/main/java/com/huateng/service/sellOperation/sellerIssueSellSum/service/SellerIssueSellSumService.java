/**
 * Classname SellerIssueSellSumService.java
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
 *		administrator		2012-10-24
 * =============================================================================
 */

package com.huateng.service.sellOperation.sellerIssueSellSum.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.sellerIssueSellSum.dto.SellerIssueSellSumDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class SellerIssueSellSumService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		SellerIssueSellSumDTO siss = (SellerIssueSellSumDTO) JSONObject.toBean(
				jsonDto, SellerIssueSellSumDTO.class);
		return baseDao.queryForList("issueSellSum", "issueSellSum", siss);
	}

}
