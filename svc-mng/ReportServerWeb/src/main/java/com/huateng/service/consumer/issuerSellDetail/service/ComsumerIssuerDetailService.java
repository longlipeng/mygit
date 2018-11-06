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

package com.huateng.service.consumer.issuerSellDetail.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.consumer.issuerSellDetail.dto.ComsumerIssuerDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author wpf
 * 
 */
public class ComsumerIssuerDetailService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {

		ComsumerIssuerDetailDTO cwd = (ComsumerIssuerDetailDTO) JSONObject
				.toBean(jsonDto, ComsumerIssuerDetailDTO.class);
		return baseDao.queryForList("consumer_issuer_detail", "consumer_issuer_detail",cwd);
	}
}
