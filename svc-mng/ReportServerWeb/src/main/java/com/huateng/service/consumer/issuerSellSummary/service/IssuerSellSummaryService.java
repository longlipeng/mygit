/**
 * Classname IssuerSellSummaryService.java
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

package com.huateng.service.consumer.issuerSellSummary.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.consumer.issuerSellSummary.dto.IssuerSellSummaryDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class IssuerSellSummaryService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		IssuerSellSummaryDTO issuerSellSummaryDTO = (IssuerSellSummaryDTO) JSONObject.toBean(jsonDto, IssuerSellSummaryDTO.class);
		return baseDao.queryForList("issuer_sell_summary", "issuer_sell_summary",issuerSellSummaryDTO);
	}

}
