/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementQueryDto.java
 * Author:   孙超
 * Date:     2013-10-31 下午06:59:46
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 结算单查询dto
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettlementQueryDto extends PageQueryDTO {

    /**
     */
    private static final long serialVersionUID = 1L;
    private String mchtCode;
    private String fatherEntityId;
    private String createdDate;
    private String sendStatus;
    private Long[] settlementIds;
    private String invoiceSendStatus;

    public String getMchtCode() {
        return mchtCode;
    }

    public void setMchtCode(String mchtCode) {
        this.mchtCode = mchtCode;
    }

    public String getFatherEntityId() {
        return fatherEntityId;
    }

    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Long[] getSettlementIds() {
        return settlementIds;
    }

    public void setSettlementIds(Long[] settlementIds) {
        this.settlementIds = settlementIds;
    }

    public String getInvoiceSendStatus() {
        return invoiceSendStatus;
    }

    public void setInvoiceSendStatus(String invoiceSendStatus) {
        this.invoiceSendStatus = invoiceSendStatus;
    }

}
