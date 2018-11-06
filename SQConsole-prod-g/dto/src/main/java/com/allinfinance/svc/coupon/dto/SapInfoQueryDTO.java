/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoQueryDTO.java
 * Author:   12073942
 * Date:     2013-11-6 下午7:03:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 记录数据查询条件
 * 
 * @author 12073942
 */
public class SapInfoQueryDTO extends PageQueryDTO {

    /**
     */
    private static final long serialVersionUID = -1525787344204123947L;

    private String transType;

    private String transDt;

    private String transCompany;

    private String vendor;

    private String payment;

    private String flag;

    /**
     * @return the transType
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return the transDt
     */
    public String getTransDt() {
        return transDt;
    }

    /**
     * @param transDt the transDt to set
     */
    public void setTransDt(String transDt) {
        this.transDt = transDt;
    }

    /**
     * @return the transCompany
     */
    public String getTransCompany() {
        return transCompany;
    }

    /**
     * @param transCompany the transCompany to set
     */
    public void setTransCompany(String transCompany) {
        this.transCompany = transCompany;
    }

    /**
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the payment
     */
    public String getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

}
