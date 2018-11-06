/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SumTradeItemDto.java
 * Author:   13040443
 * Date:     2013-10-29 上午10:42:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

/**
 * 对接受的临时数据的DTO。 有可汇总的情况，也有不会总的情况
 * 
 * @author yanbin
 */
public class TradeItemTempDto {

    /** 券号 */
    private String couponNo;
    /** 商户号 */
    private String mchtCode;
    /** 总金额 */
    private Long amount;
    /** 行项 */
    private String itemOrderNo;
    /** 类型总数 */
    private Long count;
    /** 最小的ID */
    private Long minId;
    /** 交易日期 */
    private String tradeTime;

    /**
     * @return the couponNo
     */
    public String getCouponNo() {
        return couponNo;
    }

    /**
     * @param couponNo the couponNo to set
     */
    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    /**
     * @return the mchtCode
     */
    public String getMchtCode() {
        return mchtCode;
    }

    /**
     * @param mchtCode the mchtCode to set
     */
    public void setMchtCode(String mchtCode) {
        this.mchtCode = mchtCode;
    }

    /**
     * @return the amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * @return the itemOrderNo
     */
    public String getItemOrderNo() {
        return itemOrderNo;
    }

    /**
     * @param itemOrderNo the itemOrderNo to set
     */
    public void setItemOrderNo(String itemOrderNo) {
        this.itemOrderNo = itemOrderNo;
    }

    /**
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * @return the minId
     */
    public Long getMinId() {
        return minId;
    }

    /**
     * @param minId the minId to set
     */
    public void setMinId(Long minId) {
        this.minId = minId;
    }

    /**
     * @return the tradeTime
     */
    public String getTradeTime() {
        return tradeTime;
    }

    /**
     * @param tradeTime the tradeTime to set
     */
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

}
