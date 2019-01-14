/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: T_ZMDIFS051.java
 * Author:   luwanchuan
 * Date:     2013-4-22 下午04:52:18
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.merchant;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 〈银行信息〉<br>
 * 〈MDM下发的供应商（商户）数据，包含的银行信息，本系统不需要，只接收，不处理〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS051")
public class BankInfo {

    @XStreamAlias("SUPPLIER_CODE")
    private String supplierCode;

    @XStreamAlias("BANK_LINES_NO")
    private String bankLinesNo;

    @XStreamAlias("BANK_GRP_CODE")
    private String bankGrpCode;

    @XStreamAlias("BANK_GRP_DESC")
    private String bankGrpDesc;

    @XStreamAlias("BRANCH_BANK_NAME")
    private String branchBankName;

    @XStreamAlias("BANK_ACCT_CODE")
    private String bankAcctCode;

    @XStreamAlias("PROVINCE_CODE")
    private String provinceCode;

    @XStreamAlias("PROVINCE_DESC")
    private String provinceDesc;

    @XStreamAlias("CITY_NAME")
    private String cityName;

    @XStreamAlias("CITY_DESC")
    private String cityDesc;

    @XStreamAlias("PAYMENT_TYPE_CODE")
    private String paymentTypeCode;

    @XStreamAlias("PAYMENT_TYPE_DESC")
    private String paymentTypeDesc;

    @XStreamAlias("COUNTRY_CODE")
    private String countryCode;

    @XStreamAlias("BANK_SWIFT_CODE")
    private String bankSwiftCode;

    @XStreamAlias("BRANCH_BANK_CODE")
    private String branchBankCode;

    @XStreamAlias("BRANCH_BANK_CODE2")
    private String branchBankCode2;

    @XStreamAlias("ACCT_HOLDER_NAME")
    private String acctHolderName;

    @XStreamAlias("EXTENSION")
    private String extension;

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
     * @return the bankLinesNo
     */
    public String getBankLinesNo() {
        return bankLinesNo;
    }

    /**
     * @param bankLinesNo the bankLinesNo to set
     */
    public void setBankLinesNo(String bankLinesNo) {
        this.bankLinesNo = bankLinesNo;
    }

    /**
     * @return the bankGrpCode
     */
    public String getBankGrpCode() {
        return bankGrpCode;
    }

    /**
     * @param bankGrpCode the bankGrpCode to set
     */
    public void setBankGrpCode(String bankGrpCode) {
        this.bankGrpCode = bankGrpCode;
    }

    /**
     * @return the bankGrpDesc
     */
    public String getBankGrpDesc() {
        return bankGrpDesc;
    }

    /**
     * @param bankGrpDesc the bankGrpDesc to set
     */
    public void setBankGrpDesc(String bankGrpDesc) {
        this.bankGrpDesc = bankGrpDesc;
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
     * @return the provinceCode
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * @param provinceCode the provinceCode to set
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * @return the provinceDesc
     */
    public String getProvinceDesc() {
        return provinceDesc;
    }

    /**
     * @param provinceDesc the provinceDesc to set
     */
    public void setProvinceDesc(String provinceDesc) {
        this.provinceDesc = provinceDesc;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the cityDesc
     */
    public String getCityDesc() {
        return cityDesc;
    }

    /**
     * @param cityDesc the cityDesc to set
     */
    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
    }

    /**
     * @return the paymentTypeCode
     */
    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    /**
     * @param paymentTypeCode the paymentTypeCode to set
     */
    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
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
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the bankSwiftCode
     */
    public String getBankSwiftCode() {
        return bankSwiftCode;
    }

    /**
     * @param bankSwiftCode the bankSwiftCode to set
     */
    public void setBankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    /**
     * @return the branchBankCode
     */
    public String getBranchBankCode() {
        return branchBankCode;
    }

    /**
     * @param branchBankCode the branchBankCode to set
     */
    public void setBranchBankCode(String branchBankCode) {
        this.branchBankCode = branchBankCode;
    }

    /**
     * @return the branchBankCode2
     */
    public String getBranchBankCode2() {
        return branchBankCode2;
    }

    /**
     * @param branchBankCode2 the branchBankCode2 to set
     */
    public void setBranchBankCode2(String branchBankCode2) {
        this.branchBankCode2 = branchBankCode2;
    }

    /**
     * @return the acctHolderName
     */
    public String getAcctHolderName() {
        return acctHolderName;
    }

    /**
     * @param acctHolderName the acctHolderName to set
     */
    public void setAcctHolderName(String acctHolderName) {
        this.acctHolderName = acctHolderName;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

}
