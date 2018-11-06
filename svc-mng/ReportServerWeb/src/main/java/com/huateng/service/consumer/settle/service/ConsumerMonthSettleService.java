/**
 * Classname ConsumerMonthSettleService.java
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
 *		yaoxin		2012-11-13
 * =============================================================================
 */

package com.huateng.service.consumer.settle.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.consumer.settle.dto.ConsumerMonthSettleDTO;
import com.allinfinance.service.issueOperation.sellerSellDetail.dto.SellerSellDetailDTO;
import com.huateng.framework.util.SystemInfo;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author yaoxin
 *
 */
public class ConsumerMonthSettleService extends BizBaseService implements
		BizService {

	/* (non-Javadoc)
	 * @see com.huateng.service.BizService#getList(net.sf.json.JSONObject)
	 */
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		ConsumerMonthSettleDTO consumerMonthSettleDTO = (ConsumerMonthSettleDTO) JSONObject
		.toBean(jsonDto, ConsumerMonthSettleDTO.class);
		List<Object> list= baseDao.queryForList("issuer_sell_summary", "month_settle",
				consumerMonthSettleDTO);
		//SystemInfo.dictCodeformat(consumerMonthSettleDTO.getIssuerId(),list,"205","ORDER_TYPE","136","FACE_VALUE_TYPE");
		return list;
	}

}
