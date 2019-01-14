/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MerchantSettlementDetailService.java
 * Author:   zqs
 * Date:     2013-4-23 上午11:42:52
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-23 上午11:42:52         
 */
package com.huateng.service.sellOperation.merchantSettlementDetail.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.allinfinance.service.sellOperation.merchantSettlementDetail.dto.MerchantSettlementDetailDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;


/**
 *  商户结算明细报表中的Service<br> 
 *  根据时间段查询收单机构下，时间段内的生成的结算单的商户交易明细
 *
 * @author zqs
 * @see 
 * @since 
 */
public class MerchantSettlementDetailService extends BizBaseService implements
        BizService {
    private List<MerchantSettlementDetailDTO> countList;
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getList(JSONObject jsonDto) {
        MerchantSettlementDetailDTO merchantSettlementDetailDTO = (MerchantSettlementDetailDTO) JSONObject
                .toBean(jsonDto, MerchantSettlementDetailDTO.class);
        countList = baseDao.queryForList("merchant_settlement_detail", "merchant_settlement_detail_count",
                merchantSettlementDetailDTO);
        return baseDao.queryForList("merchant_settlement_detail", "merchant_settlement_detail",
                merchantSettlementDetailDTO);
    }
    
    /**
     * 周期内的总金额，结算金额，佣金金额
     */
    public Map<String, String> setParamters(Map<String, String> map) {
        MerchantSettlementDetailDTO dto = countList.get(0);
        map.put("transAtSum", dto.getTransAtSum());
        map.put("transAtInSum", dto.getTransAtInSum());
        map.put("transFeeInSum", dto.getTransFeeInSum());
        return map;
    }

    public List<MerchantSettlementDetailDTO> getCountList() {
        return countList;
    }

    public void setCountList(List<MerchantSettlementDetailDTO> countList) {
        this.countList = countList;
    }
        
}
