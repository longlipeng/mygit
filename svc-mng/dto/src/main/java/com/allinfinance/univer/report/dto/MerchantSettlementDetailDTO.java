/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MerchantSettlementDetailDTO.java
 * Author:   zqs
 * Date:     2013-4-23 上午09:54:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * zqs          2013-4-23 上午09:54:52          
 */
package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * 商户结算明细报表中的DTO<br> 
 * 
 *
 * @author zqs
 * 
 */
public class MerchantSettlementDetailDTO extends IreportDTO {
    
    private static final long serialVersionUID = 1L;  
    
    /**
     * 开始时间
     */
    private String startDate;
    
    /**
     * 结束时间
     */
    private String endDate;
    
    /**
     * 商户编码输入参数
     */
    private String merchantNo;
    
    /**
     * 卡号
     */
    private String priAcctNo;
    
    /**
     * 商户编码
     */
    private String mchntCd;
    
    /**
     * 商户名称
     */
    private String merchantName;
    
    /**
     * 门店编码
     */
    private String shopCode;
    
    /**
     * 门店名称
     */
    private String shopName;
    
    /**
     * 终端号
     */
    private String termId;
    
    /**
     * 流水号
     */
    private String primaryKey;
    
    /**
     * 卡产品
     */
    private String primaryKey2;
    
    /**
     * 交易类型
     */
    private String transName;
    
    /**
     * 交易日期
     */
    private String settleDT;
    
    /**
     * 交易时间
     */
    private String transTime;
    
    /**
     * 交易金额
     */
    private String transAt;
    
    /**
     * 交易总金额
     */
    private String transAtSum;
    
    /**
     * 结算金额
     */
    private String transAtInSum;
    
    /**
     * 佣金金额
     */
    private String transFeeInSum;

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

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getPriAcctNo() {
        return priAcctNo;
    }

    public void setPriAcctNo(String priAcctNo) {
        this.priAcctNo = priAcctNo;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey2() {
        return primaryKey2;
    }

    public void setPrimaryKey2(String primaryKey2) {
        this.primaryKey2 = primaryKey2;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getSettleDT() {
        return settleDT;
    }

    public void setSettleDT(String settleDT) {
        this.settleDT = settleDT;
    }

    public String getTransAt() {
        return transAt;
    }

    public void setTransAt(String transAt) {
        this.transAt = transAt;
    }

    public String getTransAtSum() {
        return transAtSum;
    }

    public void setTransAtSum(String transAtSum) {
        this.transAtSum = transAtSum;
    }

    public String getTransAtInSum() {
        return transAtInSum;
    }

    public void setTransAtInSum(String transAtInSum) {
        this.transAtInSum = transAtInSum;
    }

    public String getTransFeeInSum() {
        return transFeeInSum;
    }

    public void setTransFeeInSum(String transFeeInSum) {
        this.transFeeInSum = transFeeInSum;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }
    
    
    

}
