/* Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: sss.java
 * Author:   Administrator
 * Date:     2013-11-1 下午04:12:00
 * Description://模块目的、功能描述
 * History: //修改记录
 * <author> gouhao <time> 2013-11-1 下午04:12:00  
 * <version> 1.0 <desc> 发票查询的DTO
 */

package com.allinfinance.univer.seller.invoice.dto;


import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 发票查询DTO<br>
 * 包装发票查询信息
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceReceiveQueryDTO extends PageQueryDTO {

	/**
	 * long.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 发票名称.
	 */
	private String name;
	/**
	 * 发票类型.
	 */
	private String type;

	/**
	 * 开票人.
	 */
	private String openInvoicer;
	/**
	 * 发票状态.
	 */
	private String status;
	

	/**
	 * 开始时间.
	 */
	private String startDate;
	/**
	 * 结束时间.
	 */
	private String endDate;
	/**
	 * 客户编码
	 */
	private String customerEntityId;
	/**
	 * 开票项目
	 */
	private String invoiceProject;

	
	/**
	 * 发票信息DTO.
	 */
	private InvoiceReceiveDTO invoiceReceiveDTO = new InvoiceReceiveDTO();
	
	private String mchtEntityId;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenInvoicer() {
		return openInvoicer;
	}

	public void setOpenInvoicer(String openInvoicer) {
		this.openInvoicer = openInvoicer;
	}

	public InvoiceReceiveDTO getInvoiceReceiveDTO() {
		return invoiceReceiveDTO;
	}

	public void setInvoiceReceiveDTO(InvoiceReceiveDTO invoiceReceiveDTO) {
		this.invoiceReceiveDTO = invoiceReceiveDTO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    /**
     * @return the customerEntityId
     */
    public String getCustomerEntityId() {
        return customerEntityId;
    }
    /**
     * @param customerEntityId the customerEntityId to set
     */
    public void setCustomerEntityId(String customerEntityId) {
        this.customerEntityId = customerEntityId;
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
     * @return the mchtEntityId
     */
    public String getMchtEntityId() {
        return mchtEntityId;
    }
    /**
     * @param mchtEntityId the mchtEntityId to set
     */
    public void setMchtEntityId(String mchtEntityId) {
        this.mchtEntityId = mchtEntityId;
    }
	
}
