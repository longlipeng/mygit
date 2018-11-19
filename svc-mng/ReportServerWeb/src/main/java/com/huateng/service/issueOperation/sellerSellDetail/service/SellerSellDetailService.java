/**
 * Classname SellerSellDetail.java
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

package com.huateng.service.issueOperation.sellerSellDetail.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.issueOperation.sellerSellDetail.dto.SellerSellDetailDTO;
import com.huateng.framework.util.SystemInfo;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class SellerSellDetailService extends BizBaseService implements
		BizService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.service.BizService#getList(net.sf.json.JSONObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		SellerSellDetailDTO sellerSellDetailDTO = (SellerSellDetailDTO) JSONObject
				.toBean(jsonDto, SellerSellDetailDTO.class);
		
		List<Object> list=new ArrayList<Object>();
		//if("all".equals(sellerSellDetailDTO.getQueryType())){
		//	list= baseDao.queryForList("seller_sell_detail", "seller_sell_detail",sellerSellDetailDTO);
		//}
		
		if("issue".equals(sellerSellDetailDTO.getQueryType())){
			list= baseDao.queryForList("seller_sell_detail", "seller_sell_detail_issue",sellerSellDetailDTO);
		}

		if("sell".equals(sellerSellDetailDTO.getQueryType())){
			list= baseDao.queryForList("seller_sell_detail", "seller_sell_detail_sell",sellerSellDetailDTO);
		}
		
//		if(list!=null){
//			SystemInfo.dictCodeformat(sellerSellDetailDTO.getIssuerId(),list,"205","ORDER_TYPE","136","FACE_VALUE_TYPE");
//		}
		return list;
	}

}
