/**
 * Classname TradeSummaryServiceImpl.java
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
 *		htd033		2012-11-12
 * =============================================================================
 */

package com.huateng.service.sellOperation.tradeSummary.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.tradeSummary.dto.TradeSummaryDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class TradeSummaryServiceImpl extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		TradeSummaryDTO tradeSummaryDTO = (TradeSummaryDTO) JSONObject.toBean(
				jsonDto, TradeSummaryDTO.class);
		return baseDao.queryForList("tradeSummary", "tradeSummary",
				tradeSummaryDTO);
	}

}
