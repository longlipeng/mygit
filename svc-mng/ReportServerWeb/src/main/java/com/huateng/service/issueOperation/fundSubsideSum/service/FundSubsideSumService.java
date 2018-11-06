/**
 * Classname FundSubsideSumService.java
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
 *		yaoxin		2012-10-26
 * =============================================================================
 */

package com.huateng.service.issueOperation.fundSubsideSum.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.fundSubsideSum.dto.FundSubsideSumDTO;
import com.huateng.framework.util.DateUtil;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * 发行机构-沉淀资金报表
 * 
 * @since 20121212
 * @author yaoxin
 */

public class FundSubsideSumService extends BizBaseService implements 
	BizService {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		
			FundSubsideSumDTO fundSubsideSumDTO = (FundSubsideSumDTO) JSONObject
					.toBean(jsonDto, FundSubsideSumDTO.class);
			fundSubsideSumDTO.setQueryDate(DateUtil.StringDate(fundSubsideSumDTO.getQueryDate()));
			return baseDao.queryForList("sedimentation_funds", "sedimentation_funds",
					fundSubsideSumDTO);
	}
	


}
