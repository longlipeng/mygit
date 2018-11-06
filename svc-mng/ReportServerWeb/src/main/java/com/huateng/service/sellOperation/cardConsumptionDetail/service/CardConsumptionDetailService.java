/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardConsumptionDetailService.java
 * Author:   zqs
 * Date:     2013-4-17 
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-17        
 */

package com.huateng.service.sellOperation.cardConsumptionDetail.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.cardConsumptionDetail.dto.CardConsumptionDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;


/**
 *  卡消费明细报表中的Service<br> 
 *  收单机构下根据时间段查询本机构下所有卡交易明细。包括历史的与当天的
 *
 * @author zqs
 * @see 
 * @since 
 */
public class CardConsumptionDetailService extends BizBaseService implements
		BizService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(JSONObject jsonDto) {
		CardConsumptionDetailDTO cardConsumptionDetailDTO = (CardConsumptionDetailDTO) JSONObject
				.toBean(jsonDto, CardConsumptionDetailDTO.class);
		String flag = cardConsumptionDetailDTO.getCardFlag();
		if("1".equals(flag)) {
			return baseDao.queryForList("card_consumption_detail", "card_consumption_detail",
				cardConsumptionDetailDTO);
		}
		else {
			List<Object>  list = baseDao.queryForList("card_consumption_detail", "card_consumption_detail_today1",
					cardConsumptionDetailDTO);
			if(list.size() == 0) {
				return baseDao.queryForList("card_consumption_detail", "card_consumption_detail_today2",
						cardConsumptionDetailDTO);
			}
			return list;
		}
	}

}
