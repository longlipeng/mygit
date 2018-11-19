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

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 客户发票信息DTO
 * 
 * @author LEO
 */
public class CustomerInvoiceInfoDTO extends BaseDTO {

    /**
     */
    private static final long serialVersionUID = 3128679770011029046L;

    /** 客户表：customerName */
    private String customerName;

    /** 客户表：customerCode */
    private String customerCode;

    /** 客户表：customerAddress */
    private String customerAddress;

    /** 客户表：customerPostcode */
    private String customerPostcode;

    /** 客户表：customerTelephone */
    private String customerTelephone;

    /** 客户表：customerFax */
    private String customerFax;

    /** 客户表：licenseId */
    private String licenseId;

    /** 客户表：regiCapital */
    private String regiCapital;

    /** 客户表：licenseStaValidity */
    private String licenseStaValidity;

    /** 客户表：licenseEndValidity */
    private String licenseEndValidity;

    /** 客户表：corpName */
    private String corpName;

    /** 客户表：corpCredId */
    private String corpCredId;

    /** id */
    private String id;

    /** customerEntityId */
    private String customerEntityId;

    /** fatherEntityId */
    private String fatherEntityId;

    /** companyName */
    private String companyName;

    /** invoiceItem */
    private String invoiceItem;

    /** addressTel */
    private String addressTel;

    /** taxTypeCode */
    private String taxTypeCode;

    /** taxTypeDesc */
    private String taxTypeDesc;

    /** taxCode */
    private String taxCode;

    /** taxNoType */
    private String taxNoType;

    /** regAddress */
    private String regAddress;

    /** invoiceType */
    private String invoiceType;

    /** takerAddress */
    private String takerAddress;

    /** branchBankName */
    private String branchBankName;

    /** bankAcctCode */
    private String bankAcctCode;

    /** takerName */
    private String takerName;

    /** takerTel */
    private String takerTel;

    /** takerDept */
    private String takerDept;

    /** paymentTypeDesc */
    private String paymentTypeDesc;

    /** cityPostCode */
    private String cityPostCode;

    /** mailBox */
    private String mailBox;

    /** coTypeCode */
    private String coTypeCode;

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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * @return the invoiceItem
     */
    public String getInvoiceItem() {
        return invoiceItem;
    }

    /**
     * @param invoiceItem the invoiceItem to set
     */
    public void setInvoiceItem(String invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    /**
     * @return the addressTel
     */
    public String getAddressTel() {
        return addressTel;
    }

    /**
     * @param addressTel the addressTel to set
     */
    public void setAddressTel(String addressTel) {
        this.addressTel = addressTel;
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
     * @return the takerAddress
     */
    public String getTakerAddress() {
        return takerAddress;
    }

    /**
     * @param takerAddress the takerAddress to set
     */
    public void setTakerAddress(String takerAddress) {
        this.takerAddress = takerAddress;
    }

    /**
     * @return the branchBankName
     */
    public String getBranchBankName() {
        return branchBankName;
    }

    /**
     * @param branchBankName the branchBankName to set
     */
    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
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
     * @return the takerName
     */
    public String getTakerName() {
        return takerName;
    }

    /**
     * @param takerName the takerName to set
     */
    public void setTakerName(String takerName) {
        this.takerName = takerName;
    }

    /**
     * @return the takerTel
     */
    public String getTakerTel() {
        return takerTel;
    }

    /**
     * @param takerTel the takerTel to set
     */
    public void setTakerTel(String takerTel) {
        this.takerTel = takerTel;
    }

    /**
     * @return the takerDept
     */
    public String getTakerDept() {
        return takerDept;
    }

    /**
     * @param takerDept the takerDept to set
     */
    public void setTakerDept(String takerDept) {
        this.takerDept = takerDept;
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

}
