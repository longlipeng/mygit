/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceRequirementrefDto.java
 * Author:   13040446
 * Date:     2013-10-31 上午09:02:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

/**
 * 发票需求关系Dto
 *
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceRequirementrefDto {
    /**
     * 发票需求ID
     */
    private long requireId;
    /**
     * 发票ID
     */
    private long invoiceId;
    /**
     * 金额
     */
    private long amount;
    /**
     * @return the requireId
     */
    public long getRequireId() {
        return requireId;
    }
    /**
     * @param requireId the requireId to set
     */
    public void setRequireId(long requireId) {
        this.requireId = requireId;
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
    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }
    
}
