/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerSynchDTO.java
 * Author:   12073942
 * Date:     2013-10-25 下午3:11:22
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户同步DTO
 * 
 * @author LEO
 */
public class CustomerSynchDTO {

    /**
     * 客户实体ID
     */
    private String entityId;

    /**
     * 上级实体ID
     */
    private String fatherEntityId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户地址
     */
    private String customerAddress;

    /**
     * 客户邮编
     */
    private String customerPostcode;

    /**
     * 客户电话
     */
    private String customerTelephone;

    /**
     * 客户传真
     */
    private String customerFax;

    /**
     * 法人信息
     */
    private String corpName;

    /**
     * 法人证件号
     */
    private String corpCredId;

    /**
     * 营业执照号
     */
    private String licenseId;

    /**
     * 营业执照有效开始日期
     */
    private String licenseStaValidity;

    /**
     * 营业执照有效结束日期
     */
    private String licenseEndValidity;

    /**
     * 注册资本
     */
    private String regiCapital;

    /**
     * 税类型
     */
    private String taxTypeCode;

    /**
     * 税类型描述
     */
    private String taxTypeDesc;

    /**
     * 税号
     */
    private String taxCode;

    /**
     * 税号类型
     */
    private String taxNoType;

    /**
     * 公司注册地址
     */
    private String regAddress;

    /**
     * 付款方式描述
     */
    private String paymentTypeDesc;

    /**
     * 城市邮编
     */
    private String cityPostCode;

    /**
     * 邮政信箱
     */
    private String mailBox;

    /**
     * 公司类型
     */
    private String coTypeCode;

    /**
     * 银行信息-不支持set
     */
    private final List<BankInfoDTO> bankInfoList = new ArrayList<BankInfoDTO>();

    /**
     * 联系人信息-不支持set
     */
    private final List<ContactInfoDTO> contactInfoList = new ArrayList<ContactInfoDTO>();

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
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the customerPostcode
     */
    public String getCustomerPostcode() {
        return customerPostcode;
    }

    /**
     * @param customerPostcode the customerPostcode to set
     */
    public void setCustomerPostcode(String customerPostcode) {
        this.customerPostcode = customerPostcode;
    }

    /**
     * @return the customerTelephone
     */
    public String getCustomerTelephone() {
        return customerTelephone;
    }

    /**
     * @param customerTelephone the customerTelephone to set
     */
    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    /**
     * @return the customerFax
     */
    public String getCustomerFax() {
        return customerFax;
    }

    /**
     * @param customerFax the customerFax to set
     */
    public void setCustomerFax(String customerFax) {
        this.customerFax = customerFax;
    }

    /**
     * @return the corpName
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * @param corpName the corpName to set
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * @return the corpCredId
     */
    public String getCorpCredId() {
        return corpCredId;
    }

    /**
     * @param corpCredId the corpCredId to set
     */
    public void setCorpCredId(String corpCredId) {
        this.corpCredId = corpCredId;
    }

    /**
     * @return the licenseId
     */
    public String getLicenseId() {
        return licenseId;
    }

    /**
     * @param licenseId the licenseId to set
     */
    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    /**
     * @return the licenseStaValidity
     */
    public String getLicenseStaValidity() {
        return licenseStaValidity;
    }

    /**
     * @param licenseStaValidity the licenseStaValidity to set
     */
    public void setLicenseStaValidity(String licenseStaValidity) {
        this.licenseStaValidity = licenseStaValidity;
    }

    /**
     * @return the licenseEndValidity
     */
    public String getLicenseEndValidity() {
        return licenseEndValidity;
    }

    /**
     * @param licenseEndValidity the licenseEndValidity to set
     */
    public void setLicenseEndValidity(String licenseEndValidity) {
        this.licenseEndValidity = licenseEndValidity;
    }

    /**
     * @return the regiCapital
     */
    public String getRegiCapital() {
        return regiCapital;
    }

    /**
     * @param regiCapital the regiCapital to set
     */
    public void setRegiCapital(String regiCapital) {
        this.regiCapital = regiCapital;
    }

    /**
     * @return the taxTypeCode
     */
    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    /**
     * @param taxTypeCode the taxTypeCode to set
     */
    public void setTaxTypeCode(String taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
    }

    /**
     * @return the taxTypeDesc
     */
    public String getTaxTypeDesc() {
        return taxTypeDesc;
    }

    /**
     * @param taxTypeDesc the taxTypeDesc to set
     */
    public void setTaxTypeDesc(String taxTypeDesc) {
        this.taxTypeDesc = taxTypeDesc;
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
     * @return the taxNoType
     */
    public String getTaxNoType() {
        return taxNoType;
    }

    /**
     * @param taxNoType the taxNoType to set
     */
    public void setTaxNoType(String taxNoType) {
        this.taxNoType = taxNoType;
    }

    /**
     * @return the regAddress
     */
    public String getRegAddress() {
        return regAddress;
    }

    /**
     * @param regAddress the regAddress to set
     */
    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    /**
     * @return the paymentTypeDesc
     */
    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    /**
     * @param paymentTypeDesc the paymentTypeDesc to set
     */
    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    /**
     * @return the cityPostCode
     */
    public String getCityPostCode() {
        return cityPostCode;
    }

    /**
     * @param cityPostCode the cityPostCode to set
     */
    public void setCityPostCode(String cityPostCode) {
        this.cityPostCode = cityPostCode;
    }

    /**
     * @return the mailBox
     */
    public String getMailBox() {
        return mailBox;
    }

    /**
     * @param mailBox the mailBox to set
     */
    public void setMailBox(String mailBox) {
        this.mailBox = mailBox;
    }

    /**
     * @return the coTypeCode
     */
    public String getCoTypeCode() {
        return coTypeCode;
    }

    /**
     * @param coTypeCode the coTypeCode to set
     */
    public void setCoTypeCode(String coTypeCode) {
        this.coTypeCode = coTypeCode;
    }

    /**
     * @return the bankInfoList
     */
    public List<BankInfoDTO> getBankInfoList() {
        return bankInfoList;
    }

    /**
     * @return the contactInfoList
     */
    public List<ContactInfoDTO> getContactInfoList() {
        return contactInfoList;
    }

}
