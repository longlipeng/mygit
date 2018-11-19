/**
 * Classname ConsumerSellSummaryService.java
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

package com.huateng.service.issueOperation.issuerStockSum.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.issuerStockSum.dto.IssuerStockSumDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class IssuerStockSumService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		IssuerStockSumDTO issuerStockSumDTO = (IssuerStockSumDTO) JSONObject
				.toBean(jsonDto, IssuerStockSumDTO.class);
		return baseDao.queryForList("issuer_stock_sum", "issuer_stock_sum",
				issuerStockSumDTO);
	}

}
