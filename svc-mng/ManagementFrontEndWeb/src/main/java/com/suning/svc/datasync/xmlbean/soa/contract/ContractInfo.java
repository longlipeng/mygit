/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ContractInfo.java
 * Author:   luwanchuan
 * Date:     2013-5-25 上午09:47:16
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.soa.contract;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * SOA通过ESB下发的商户合同信息-合同编号部分
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("contInfo")
public class ContractInfo {

    /**
     * 供应商编号
     */
    @XStreamAlias("supplierCode")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @XStreamAlias("supplierName")
    private String supplierName;

    /**
     * 合同编码
     */
    @XStreamAlias("contractCode")
    private String contractCode;

    /**
     * 合同起始日期
     */
    @XStreamAlias("contStartDate")
    private String contStartDate;

    /**
     * 合同结束日期
     */
    @XStreamAlias("contEndDate")
    private String contEndDate;

    /**
     * 合同签订日期
     */
    @XStreamAlias("contReDate")
    private String contReDate;

    /**
     * 发起公司代码
     */
    @XStreamAlias("comCode")
    private String comCode;

    /**
     * 发起公司名称
     */
    @XStreamAlias("comName")
    private String comName;

    /**
     * 出单日
     */
    @XStreamAlias("bilDate")
    private String bilDate;

    /**
     * 结算周期
     */
    @XStreamAlias("periodRuleNo")
    private String periodRuleNo;

    /**
     * @return the supplierCode
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * @param supplierCode the supplierCode to set
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    /**
     * @return the supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName the supplierName to set
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return the contractCode
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @param contractCode the contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * @return the contStartDate
     */
    public String getContStartDate() {
        return contStartDate;
    }

    /**
     * @param contStartDate the contStartDate to set
     */
    public void setContStartDate(String contStartDate) {
        this.contStartDate = contStartDate;
    }

    /**
     * @return the contEndDate
     */
    public String getContEndDate() {
        return contEndDate;
    }

    /**
     * @param contEndDate the contEndDate to set
     */
    public void setContEndDate(String contEndDate) {
        this.contEndDate = contEndDate;
    }

    /**
     * @return the contReDate
     */
    public String getContReDate() {
        return contReDate;
    }

    /**
     * @param contReDate the contReDate to set
     */
    public void setContReDate(String contReDate) {
        this.contReDate = contReDate;
    }

    /**
     * @return the comCode
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * @param comCode the comCode to set
     */
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * @return the comName
     */
    public String getComName() {
        return comName;
    }

    /**
     * @param comName the comName to set
     */
    public void setComName(String comName) {
        this.comName = comName;
    }

    /**
     * @return the bilDate
     */
    public String getBilDate() {
        return bilDate;
    }

    /**
     * @param bilDate the bilDate to set
     */
    public void setBilDate(String bilDate) {
        this.bilDate = bilDate;
    }

    /**
     * @return the periodRuleNo
     */
    public String getPeriodRuleNo() {
        return periodRuleNo;
    }

    /**
     * @param periodRuleNo the periodRuleNo to set
     */
    public void setPeriodRuleNo(String periodRuleNo) {
        this.periodRuleNo = periodRuleNo;
    }

}
