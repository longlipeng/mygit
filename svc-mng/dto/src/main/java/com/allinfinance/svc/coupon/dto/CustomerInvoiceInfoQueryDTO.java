/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerInvoiceInfoDTO.java
 * Author:   12073942
 * Date:     2013-10-30 下午7:48:05
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 客户发票信息查询DTO
 * 
 * @author LEO
 */
public class CustomerInvoiceInfoQueryDTO extends PageQueryDTO {

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 公司名称
     */
    private String companyName;

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
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
