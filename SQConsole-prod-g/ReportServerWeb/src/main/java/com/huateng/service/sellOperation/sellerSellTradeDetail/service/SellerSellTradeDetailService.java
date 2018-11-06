/**
 * Classname SellerSellTradeDetailService.java
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
 *		yaoxin		2012-11-20
 * =============================================================================
 */

package com.huateng.service.sellOperation.sellerSellTradeDetail.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.sellerSellTradeDetail.dto.SellerSellTradeDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author yaoxin
 *
 */
public class SellerSellTradeDetailService extends BizBaseService implements
		BizService {

	/* (non-Javadoc)
	 * @see com.huateng.service.BizService#getList(net.sf.json.JSONObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		
		SellerSellTradeDetailDTO siss = (SellerSellTradeDetailDTO) JSONObject.toBean(
				jsonDto, SellerSellTradeDetailDTO.class);
		return baseDao.queryForList("tradeDetail", "tradeDetail", siss);
	}

}
