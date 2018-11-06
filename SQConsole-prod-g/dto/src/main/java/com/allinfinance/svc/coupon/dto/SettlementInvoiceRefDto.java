/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SettlementInvoiceRefDto.java
 * Author:   13040446
 * Date:     2013-10-31 上午09:04:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

/**
 * 发票结算关系Dto
 *
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SettlementInvoiceRefDto {

    /**
     * 结算单ID
     */
    private long balanceId;
    /**
     * 结算单类型
     */
    private String balanceType;
    /**
     * 发票ID
     */
    private long invoiceId;
    /**
     * @return the balanceId
     */
    public long getBalanceId() {
        return balanceId;
    }
    /**
     * @param balanceId the balanceId to set
     */
    public void setBalanceId(long balanceId) {
        this.balanceId = balanceId;
    }
   
    /**
     * @return the balanceType
     */
    public String getBalanceType() {
        return balanceType;
    }
    /**
     * @param balanceType the balanceType to set
     */
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }
    /**
     * @return the invoiceId
     */
    public long getInvoiceId() {
        return invoiceId;
    }
    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    
}
