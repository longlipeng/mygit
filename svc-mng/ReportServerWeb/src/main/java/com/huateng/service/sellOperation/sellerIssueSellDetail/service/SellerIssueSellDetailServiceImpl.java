/**
 * Classname SellerIssueSellDetailServiceImpl.java
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
 *		htd033		2012-11-8
 * =============================================================================
 */

package com.huateng.service.sellOperation.sellerIssueSellDetail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.sellerIssueSellDetail.dto.IssuerInfoDTO;
import com.allinfinance.service.sellOperation.sellerIssueSellDetail.dto.SellerIssueSellDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class SellerIssueSellDetailServiceImpl extends BizBaseService implements
		BizService {

	
	private IssuerInfoDTO issuerInfo=new IssuerInfoDTO();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		SellerIssueSellDetailDTO sellerIssueSellDetailDTO = (SellerIssueSellDetailDTO) JSONObject
		.toBean(jsonDto, SellerIssueSellDetailDTO.class);
		issuerInfo=(IssuerInfoDTO)baseDao.queryForList("issueselldetail", "issuerInfo",
				sellerIssueSellDetailDTO).get(0);
		List<Object> list=new ArrayList<Object>();
		list=baseDao.queryForList("issueselldetail", "issueselldetail",
					sellerIssueSellDetailDTO);
		return list;
	}
	
	public Map<String, String> setParamters(Map<String, String> map) {
		map.put("issuerId", issuerInfo.getIssuerId());
		map.put("issuerName", issuerInfo.getIssuerName());
		return map;
	}
}
