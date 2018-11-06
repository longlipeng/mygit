/**
 * Classname PosDetailService.java
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

package com.huateng.service.consumer.posDetail.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.consumer.posDetail.dto.PosDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class PosDetailService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		PosDetailDTO posDetailDTO = (PosDetailDTO) JSONObject
				.toBean(jsonDto, PosDetailDTO.class);
		return baseDao.queryForList("pos_detail", "pos_detail",
				posDetailDTO);
	}

}
