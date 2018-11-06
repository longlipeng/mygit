/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: sss.java
 * Author:   Administrator
 * Date:     2013-11-1 下午04:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author> gouhao     <time> 2013-11-1 下午04:12:00     <version> 1.0    <desc> 发票信息更新的DTO
 */
package com.allinfinance.univer.seller.invoice.dto;

import java.util.ArrayList;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 发票信息更新DTO<br> 
 * 包装发票更新信息
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceReceiveUpdateDTO extends BaseDTO {
	
	/**
	 * 版本
     */
    private static final long serialVersionUID = 1L;

    /**
	 * 接收多Id
	 */
	private ArrayList<Long> ids;
	
	/**
	 * 多id数组类型
	 */
	private Long[] idss;
	/**
	 * 登陆操作员Id
	 */
	private String userId;
	/**
	 * 发票号
	 */
	private String invoiceNO;
   
    public String getInvoiceNO() {
        return invoiceNO;
    }
    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
	 * 发票信息DTO.
	 */
	private InvoiceReceiveDTO invoiceReceiveDTO;

	
	public ArrayList<Long> getIds() {
		return ids;
	}
	public void setIds(ArrayList<Long> ids) {
		this.ids = ids;
	}
	public InvoiceReceiveDTO getInvoiceReceiveDTO() {
		return invoiceReceiveDTO;
	}

	public void setInvoiceReceiveDTO(InvoiceReceiveDTO invoiceReceiveDTO) {
		this.invoiceReceiveDTO = invoiceReceiveDTO;
	}
	public Long[] getIdss() {
        return idss;
    }
    public void setIdss(Long[] idss) {
        this.idss = idss;
    }
}
