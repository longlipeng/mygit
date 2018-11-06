/**
 * Classname WarehouseSummaryServiceImpl.java
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
 *		htd033		2012-11-9
 * =============================================================================
 */

package com.huateng.service.sellOperation.warehouseSummary.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.warehouseSummary.dto.WarehouseSummaryDTO;
import com.huateng.framework.util.SystemInfo;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class WarehouseSummaryServiceImpl extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {

		WarehouseSummaryDTO warehouseSummaryDTO = (WarehouseSummaryDTO) JSONObject
				.toBean(jsonDto, WarehouseSummaryDTO.class);
		List<Object> list = baseDao.queryForList("WarehouseSummary",
				"WarehouseSummary", warehouseSummaryDTO);
		SystemInfo.dictCodeformat(warehouseSummaryDTO.getIssuerId(), list,
				"102", "cardType", "816", "onymousStat");
		return list;
	}
}
