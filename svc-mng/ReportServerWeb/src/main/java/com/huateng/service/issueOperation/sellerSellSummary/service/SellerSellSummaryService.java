/**
 * Classname SellerSellSummaryService.java
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
 *		wpf		2012-10-22
 * =============================================================================
 */

package com.huateng.service.issueOperation.sellerSellSummary.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.sellerSellSummary.dto.SellerSellSummaryDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class SellerSellSummaryService extends BizBaseService implements
		BizService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.service.BizService#getList(net.sf.json.JSONObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		SellerSellSummaryDTO sellerSellSummaryDTO = (SellerSellSummaryDTO) JSONObject
				.toBean(jsonDto, SellerSellSummaryDTO.class);
		return baseDao.queryForList("sellerSummary", "sellerSummary",
				sellerSellSummaryDTO);
	}

}
