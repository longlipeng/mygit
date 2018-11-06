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

package com.huateng.service.issueOperation.issuerStockStream.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.issuerStockStream.dto.IssuerStockStreamDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class IssuerStockStreamService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		IssuerStockStreamDTO issuerStockStreamDTO = (IssuerStockStreamDTO) JSONObject
				.toBean(jsonDto, IssuerStockStreamDTO.class);
		return baseDao.queryForList("issuer_stockstream_ireport", "issuer_stockstream_ireport",
				issuerStockStreamDTO);
	}

}
