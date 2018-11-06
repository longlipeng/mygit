/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: Item.java
 * Author:   12073942
 * Date:     2013-10-18 下午2:30:07
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.trans.detail;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 行项
 * 
 * @author LEO
 */
public class Item {

    /** mchtCode 供应商编号 */
    @XStreamAlias("mchtCode")
    private String mchtCode;

    /** tradeType 交易类型 */
    @XStreamAlias("tradeType")
    private String tradeType;

    /** billType 单据方向 */
    @XStreamAlias("billType")
    private String billType;

    /** couponNo 券号 */
    @XStreamAlias("couponNo")
    private String couponNo;

    /** itemOrderNo 消费行项 */
    @XStreamAlias("itemOrderNo")
    private String itemOrderNo;

    /** tradeTime 交易时间 */
    @XStreamAlias("tradeTime")
    private String tradeTime;

    /** amount 金额 */
    @XStreamAlias("amount")
    private String amount;

    /** orderNo 交易单号 */
    @XStreamAlias("orderNo")
    private String orderNo;

    /** itemDes 行项描述 */
    @XStreamAlias("itemDes")
    private String itemDes;

    /** itemRemark 行项备注 */
    @XStreamAlias("itemRemark")
    private String itemRemark;

    /** sign 签名 */
    @XStreamAlias("sign")
    private String sign;

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
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * @return the billType
     */
    public String getBillType() {
        return billType;
    }

    /**
     * @param billType the billType to set
     */
    public void setBillType(String billType) {
        this.billType = billType;
    }

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

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the itemDes
     */
    public String getItemDes() {
        return itemDes;
    }

    /**
     * @param itemDes the itemDes to set
     */
    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    /**
     * @return the itemRemark
     */
    public String getItemRemark() {
        return itemRemark;
    }

    /**
     * @param itemRemark the itemRemark to set
     */
    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

}
