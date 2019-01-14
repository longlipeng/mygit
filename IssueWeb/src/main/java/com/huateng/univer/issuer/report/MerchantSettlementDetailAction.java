/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MerchantSettlementDetailAction.java
 * Author:   zqs
 * Date:     2013-4-23 下午12:16:45
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs           2013-4-23 下午12:16:45                 
 */
package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.MerchantSettlementDetailDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 商户结算明细报表 action<br> 
 * 
 *
 * @author zqs
 */
public class MerchantSettlementDetailAction extends NewIreportAction {
   
    private static final long serialVersionUID = 6911509068002612132L;

    private MerchantSettlementDetailDTO merchantSettlementDetailDTO = new MerchantSettlementDetailDTO();

    
    public String inQuery() throws Exception {
        merchantSettlementDetailDTO.setReportName("merchant_settlement_detail");
        merchantSettlementDetailDTO.setReportType("xls");
        merchantSettlementDetailDTO.setIssuerId(getUser().getEntityId());
        merchantSettlementDetailDTO.setIssuerName(getUser().getIssuerName());
        merchantSettlementDetailDTO.setReportFileName("商户结算明细表");
        return "list";
    }

    @Override
    protected JSONObject getJSONOBJect() {
        return JSONObject.fromObject(merchantSettlementDetailDTO);
    }

    public MerchantSettlementDetailDTO getMerchantSettlementDetailDTO() {
        return merchantSettlementDetailDTO;
    }

    public void setMerchantSettlementDetailDTO(MerchantSettlementDetailDTO merchantSettlementDetailDTO) {
        this.merchantSettlementDetailDTO = merchantSettlementDetailDTO;
    }

   
    
}
