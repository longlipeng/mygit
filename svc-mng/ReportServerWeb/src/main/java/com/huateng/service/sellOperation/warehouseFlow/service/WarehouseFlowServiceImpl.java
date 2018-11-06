/**
 * Classname WarehouseFlowService.java
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
 *		wpf		2012-10-18
 * =============================================================================
 */

package com.huateng.service.sellOperation.warehouseFlow.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.warehouseFlow.dto.WarehouseFlowDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class WarehouseFlowServiceImpl extends BizBaseService implements
		BizService {

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getList(JSONObject jsonDto) {
		WarehouseFlowDTO wfdto = (WarehouseFlowDTO) JSONObject
				.toBean(jsonDto, WarehouseFlowDTO.class);
		return baseDao.queryForList("warehouseflow", "warehouseflow", wfdto);
	}
}
