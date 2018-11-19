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

package com.huateng.service.issueOperation.issuerCardSum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.issuerCardSum.dto.IssuerCardSumDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * 发行机构-发卡情况汇总报表查询
 * 
 * @since 20121212
 * @author yaoxin
 */
public class IssuerCardSumService extends BizBaseService implements
		BizService {
	private IssuerCardSumDTO issuerCardSumDTO = null;
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		issuerCardSumDTO = (IssuerCardSumDTO) JSONObject
				.toBean(jsonDto, IssuerCardSumDTO.class);
		List list=new ArrayList();
		//按日查询
		if("day".equals(issuerCardSumDTO.getQueryType())){
			list=baseDao.queryForList("issuer_card_sum", "issuer_card_sum_day",issuerCardSumDTO);
		}
		//按月查询
		if("month".equals(issuerCardSumDTO.getQueryType())){
			
			list=baseDao.queryForList("issuer_card_sum", "issuer_card_sum_month",issuerCardSumDTO);
		}
		return list;
	}
	public Map<String, String> setParamters(Map<String, String> map) {
		if("month".equals(issuerCardSumDTO.getQueryType())){
			map.put("startDate", issuerCardSumDTO.getStartDate().substring(0,7));
			map.put("endDate", issuerCardSumDTO.getEndDate().substring(0,7));
		}
		return map;
	}
}
