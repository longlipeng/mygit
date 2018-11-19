/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemTempQueryDTO.java
 * Author:   12073942
 * Date:     2013-11-6 下午2:11:11
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 交易临时表查询条件
 * 
 * @author LEO
 */
public class TradeItemTempQueryDTO extends PageQueryDTO {

    /**
     */
    private static final long serialVersionUID = -828200616169256150L;

    private String id;

    private String partner;

    private String mchtCode;

    private String tradeType;

    private String direction;

    private String couponNo;

    private String itemOrderNo;

    private String tradeTime;

    private String amount;

    private String status;

    private String startTime;

    private String stopTime;

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
     * @return the partner
     */
    public String getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(String partner) {
        this.partner = partner;
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
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the stopTime
     */
    public String getStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

}
