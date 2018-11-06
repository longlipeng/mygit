/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: InvoiceRequireMentQueryDto.java
 * Author:   13040446
 * Date:     2013-10-29 下午09:20:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 发票需求查询Dto
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceRequireMentQueryDto extends PageQueryDTO {

    /**
     */
    private static final long serialVersionUID = 1L;
    /**
     * 客户编码
     */
    private String entityId;

    /**
     * 机构号
     */
    private String fatherEntityId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 结算单ID
     */
    private Long settlementId;
    /**
     * 关联单据类型
     */
    private String refOrderType;
    /**
     * 税号
     */
    private String taxCode;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String stopTime;
    /**
     * 发票临时表ID
     */

    private long[] invoiceTmpIds;
    /**
     * 发票需求多条
     */
    private String[] invoiceRequireIds;

    /**
     * 开普票金额
     */
    private long commonAmount;

    /**
     * 当前登录用户
     */
    private String userName;

    private long invoiceId;
    /**
     * 开票项目
     */
    private String invoiceProject;
    /**
     * 当前时间
     */
    private Date newDate;

    /**
     * @return the invoiceRequireIds
     */
    public String[] getInvoiceRequireIds() {
        return invoiceRequireIds;
    }

    /**
     * @param invoiceRequireIds the invoiceRequireIds to set
     */
    public void setInvoiceRequireIds(String[] invoiceRequireIds) {
        this.invoiceRequireIds = invoiceRequireIds;
    }

    /**
     * @return the entityId
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
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
     * @return the settlementId
     */
    public Long getSettlementId() {
        return settlementId;
    }

    /**
     * @param settlementId the settlementId to set
     */
    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    /**
     * @return the invoiceTmpIds
     */
    public long[] getInvoiceTmpIds() {
        return invoiceTmpIds;
    }

    /**
     * @param invoiceTmpIds the invoiceTmpIds to set
     */
    public void setInvoiceTmpIds(long[] invoiceTmpIds) {
        this.invoiceTmpIds = invoiceTmpIds;
    }

    /**
     * @return the fatherEntityId
     */
    public String getFatherEntityId() {
        return fatherEntityId;
    }

    /**
     * @param fatherEntityId the fatherEntityId to set
     */
    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }

    /**
     * @return the commonAmount
     */
    public long getCommonAmount() {
        return commonAmount;
    }

    /**
     * @param commonAmount the commonAmount to set
     */
    public void setCommonAmount(long commonAmount) {
        this.commonAmount = commonAmount;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the stopTime
     */
    public String getStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @return the invoiceProject
     */
    public String getInvoiceProject() {
        return invoiceProject;
    }

    /**
     * @param invoiceProject the invoiceProject to set
     */
    public void setInvoiceProject(String invoiceProject) {
        this.invoiceProject = invoiceProject;
    }

    /**
     * @return the newDate
     */
    public Date getNewDate() {
        return newDate;
    }

    /**
     * @param newDate the newDate to set
     */
    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }

}
