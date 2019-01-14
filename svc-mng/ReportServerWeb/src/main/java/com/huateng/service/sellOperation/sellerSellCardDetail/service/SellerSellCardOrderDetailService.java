/**
 * Classname SellerIssueSellSumService.java
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
 *		administrator		2012-10-24
 * =============================================================================
 */

package com.huateng.service.sellOperation.sellerSellCardDetail.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.sellerSellCardDetail.dto.SellerSellCardDetailDTO;
import com.huateng.framework.util.SystemInfo;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

/**
 * @author administrator
 * 
 */
public class SellerSellCardOrderDetailService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		SellerSellCardDetailDTO sisc = (SellerSellCardDetailDTO) JSONObject.toBean(
				jsonDto, SellerSellCardDetailDTO.class);
		List<Object> list=new ArrayList<Object>();
		list=baseDao.queryForList("sellerSellCardDetail", "sellerSellCardOrderDetail", sisc);
		if(null!=list){
			SystemInfo.dictCodeformat(sisc.getIssuerId(),list,"205","ORDER_TYPE","136","FACE_VALUE_TYPE");
		}
		
		return list;
	}

}
