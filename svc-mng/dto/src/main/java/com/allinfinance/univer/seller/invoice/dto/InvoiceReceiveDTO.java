/* Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: sss.java
 * Author:   Administrator
 * Date:     2013-11-1 下午04:12:00
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author> gouhao <time> 2013-11-1 下午04:12:00
 * <version> 1.0<desc> 发票基本信息的DTO
 */
package com.allinfinance.univer.seller.invoice.dto;

import java.util.Date;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 〈一句话功能简述〉<br>.
 * 〈功能详细描述〉
 *
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceReceiveDTO extends BaseDTO {

    /**
     * 发票信息DTO<br>.
     * 包装发票基础信息
     * @author gouhao
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private static final long serialVersionUID = 1L;
    /**
     * 发票id.
     */
    private long id;
    /**
     * 发票名称.
     */
    private String name;
    /**
     * 发票金额.
     */
    private long amount;
    /**
     * 开票人.
     */
    private String Invoicer;
    /**
     * 发票类型.
     */
    private String type;
    /**
     * 发票状态.
     */
    private String status;
    /**
     * 分配人.
     */
    private String assigner;
    /**
     * 分配时间.
     */
    private String assignedTime;
    /**
     * 收票人.
     */
    private String checkInvoicer;
    /**
     * 收票时间.
     */
    private Date checkedTime;
    /**
     * 交票人.
     */
    private String handInvoicer;
    /**
     * 交票时间.
     */
    private Date handedTime;
    /**
     * 客户编号.
     */
    private String customerEntityId;
    /**
     * 开票项目.
     */
    private String invoiceProject;
    /**
     * 创建时间.
     */
    private Date createdTime;
    /**
     * 更新时间.
     */
    private Date updatedTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getInvoicer() {
        return Invoicer;
    }

    public void setInvoicer(String invoicer) {
        Invoicer = invoicer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(String assignedTime) {
        this.assignedTime = assignedTime;
    }

    public String getCheckInvoicer() {
        return checkInvoicer;
    }

    public void setCheckInvoicer(String checkInvoicer) {
        this.checkInvoicer = checkInvoicer;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getHandInvoicer() {
        return handInvoicer;
    }

    public void setHandInvoicer(String handInvoicer) {
        this.handInvoicer = handInvoicer;
    }

    public Date getHandedTime() {
        return handedTime;
    }

    public void setHandedTime(Date handedTime) {
        this.handedTime = handedTime;
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

	public String getCustomerEntityId() {
		return customerEntityId;
	}

	public void setCustomerEntityId(String customerEntityId) {
		this.customerEntityId = customerEntityId;
	}

	public String getInvoiceProject() {
		return invoiceProject;
	}

	public void setInvoiceProject(String invoiceProject) {
		this.invoiceProject = invoiceProject;
	}

}