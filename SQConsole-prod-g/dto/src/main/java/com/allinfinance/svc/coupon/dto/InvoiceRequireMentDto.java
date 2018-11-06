/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceRequireMentDto.java
 * Author:   13040446
 * Date:     2013-10-29 下午08:47:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

/**
 * 发票需求列表Dto
 *
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceRequireMentDto {
    /**
     * 发票需求主键
     */
    private Long id;
    /**
     * 关联单据类型
     */
    private String refOrderType;
    /**
     * 已开票金额
     */
    private Long yetAmount;
    /**
     * 未开票金额
     */
    private Long waitAmount;
    /**
     * 发票名称
     */
    private String invoiceName;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 银行账号
     */
    private String bankAcctCode; 
    /**
     * 税号
     */
    private String taxCode;
    /**
     * 申请金额
     */
    private long amount;
    /**
     * 发票种类
     */
    private String invoiceType;
    /**
     * 开票项目
     */
    private String[] items;
    /**
     * 字典类型
     */
    private String dictTypeCode;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the refOrderType
     */
    public String getRefOrderType() {
        return refOrderType;
    }
    /**
     * @param refOrderType the refOrderType to set
     */
    public void setRefOrderType(String refOrderType) {
        this.refOrderType = refOrderType;
    }
    /**
     * @return the yetAmount
     */
    public Long getYetAmount() {
        return yetAmount;
    }
    /**
     * @param yetAmount the yetAmount to set
     */
    public void setYetAmount(Long yetAmount) {
        this.yetAmount = yetAmount;
    }
    /**
     * @return the waitAmount
     */
    public Long getWaitAmount() {
        return waitAmount;
    }
    /**
     * @param waitAmount the waitAmount to set
     */
    public void setWaitAmount(Long waitAmount) {
        this.waitAmount = waitAmount;
    }
    /**
     * @return the invoiceName
     */
    public String getInvoiceName() {
        return invoiceName;
    }
    /**
     * @param invoiceName the invoiceName to set
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }
    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }
    /**
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * @return the bankAcctCode
     */
    public String getBankAcctCode() {
        return bankAcctCode;
    }
    /**
     * @param bankAcctCode the bankAcctCode to set
     */
    public void setBankAcctCode(String bankAcctCode) {
        this.bankAcctCode = bankAcctCode;
    }
    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }
    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
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
    /**
     * @return the invoiceType
     */
    public String getInvoiceType() {
        return invoiceType;
    }
    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    /**
     * @return the items
     */
    public String[] getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(String[] items) {
        this.items = items;
    }
    /**
     * @return the dictTypeCode
     */
    public String getDictTypeCode() {
        return dictTypeCode;
    }
    /**
     * @param dictTypeCode the dictTypeCode to set
     */
    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

}
