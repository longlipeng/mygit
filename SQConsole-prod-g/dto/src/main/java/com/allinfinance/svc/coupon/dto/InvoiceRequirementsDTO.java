package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

import java.util.Date;

public class InvoiceRequirementsDTO extends PageQueryDTO {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8691115602898159199L;
    private Long id;
    private Long refOrderId;
    private String refOrderType;

    private Long yetAmount;
    private Long waitAmount;
    private String invoiceName;
    private String customerEntityId;
    private String fatherEntityId;
    private String invoiceIds;
    private String invoiceNO;

    public String getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(String invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public String getFatherEntityId() {
        return fatherEntityId;
    }

    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private String aspect;
    private String type;
    private Date createdTime;
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefOrderId() {
        return refOrderId;
    }

    public void setRefOrderId(Long refOrderId) {
        this.refOrderId = refOrderId;
    }

    public String getRefOrderType() {
        return refOrderType;
    }

    public void setRefOrderType(String refOrderType) {
        this.refOrderType = refOrderType;
    }

    public Long getYetAmount() {
        return yetAmount;
    }

    public void setYetAmount(Long yetAmount) {
        this.yetAmount = yetAmount;
    }

    public Long getWaitAmount() {
        return waitAmount;
    }

    public void setWaitAmount(Long waitAmount) {
        this.waitAmount = waitAmount;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getCustomerEntityId() {
        return customerEntityId;
    }

    public void setCustomerEntityId(String customerEntityId) {
        this.customerEntityId = customerEntityId;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * @return the invoiceNO
     */
    public String getInvoiceNO() {
        return invoiceNO;
    }

    /**
     * @param invoiceNO the invoiceNO to set
     */
    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

}
