package com.allinfinance.univer.consumer.pos.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class PosInfoQueryDTO extends PageQueryDTO {
    private static final long serialVersionUID = 1L;

    private String termId;
    private String mchntId;
    private String mchntName;
    private String shopId;
    private String shopAddress;
    private String shopName;

    private String merchantCode;
    private String merchantCodeArray;
    private String termStat;

    private String prmChangeFlag1;
    private String prmChangeFlag2;
    private String termType;
    private String termTypeName;

    public String getTermTypeName() {
        return termTypeName;
    }

    public void setTermTypeName(String termTypeName) {
        this.termTypeName = termTypeName;
    }

    private String shopCode;

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermStat() {
        return termStat;
    }

    public void setTermStat(String termStat) {
        this.termStat = termStat;
    }

    public String getMerchantCodeArray() {
        return merchantCodeArray;
    }

    public void setMerchantCodeArray(String merchantCodeArray) {
        this.merchantCodeArray = merchantCodeArray;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setPrmChangeFlag1(String prmChangeFlag1) {
        this.prmChangeFlag1 = prmChangeFlag1;
    }

    public String getPrmChangeFlag1() {
        return prmChangeFlag1;
    }

    public void setPrmChangeFlag2(String prmChangeFlag2) {
        this.prmChangeFlag2 = prmChangeFlag2;
    }

    public String getPrmChangeFlag2() {
        return prmChangeFlag2;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopCode() {
        return shopCode;
    }

}
