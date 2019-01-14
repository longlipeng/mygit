/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeItemDealedQueryDto.java
 * Author:   13040446
 * Date:     2013-11-6 下午02:25:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13040446
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TradeItemDealedQueryDto extends PageQueryDTO{
    /**
     */
    private static final long serialVersionUID = 1L;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.ID
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Long id;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.REF_ORDER_ID
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Long refOrderId;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.REF_ORDER_TYPE
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String refOrderType;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.DIRECTION
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String direction;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.PARTNER
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String partner;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.MCHT_CODE
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String mchtCode;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.TRADE_TYPE
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String tradeType;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.COUPON_NO
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String couponNo;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.ITEM_ORDER_NO
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String itemOrderNo;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.TRADE_TIME
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String tradeTime;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.AMOUNT
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Long amount;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.ORDER_NO
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String orderNo;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.ITEM_DES
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String itemDes;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.ITEM_REMARK
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String itemRemark;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.STATUS
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private String status;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.COUNTER
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Integer counter;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.BATCH_ID
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Long batchId;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.CREATED_TIME
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Date createdTime;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.UPDATED_TIME
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Date updatedTime;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.ACCEPTED_TIME
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Date acceptedTime;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * CP_TRADE_ITEM_DEALED.DEALED_TIME
     * 
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    private Date dealedTime;

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.ID
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.ID
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.ID
     * 
     * @param id the value for CP_TRADE_ITEM_DEALED.ID
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.REF_ORDER_ID
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.REF_ORDER_ID
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Long getRefOrderId() {
        return refOrderId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.REF_ORDER_ID
     * 
     * @param refOrderId the value for CP_TRADE_ITEM_DEALED.REF_ORDER_ID
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setRefOrderId(Long refOrderId) {
        this.refOrderId = refOrderId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.REF_ORDER_TYPE
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.REF_ORDER_TYPE
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getRefOrderType() {
        return refOrderType;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.REF_ORDER_TYPE
     * 
     * @param refOrderType the value for CP_TRADE_ITEM_DEALED.REF_ORDER_TYPE
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setRefOrderType(String refOrderType) {
        this.refOrderType = refOrderType;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.DIRECTION
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.DIRECTION
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getDirection() {
        return direction;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.DIRECTION
     * 
     * @param direction the value for CP_TRADE_ITEM_DEALED.DIRECTION
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.PARTNER
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.PARTNER
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getPartner() {
        return partner;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.PARTNER
     * 
     * @param partner the value for CP_TRADE_ITEM_DEALED.PARTNER
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.MCHT_CODE
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.MCHT_CODE
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getMchtCode() {
        return mchtCode;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.MCHT_CODE
     * 
     * @param mchtCode the value for CP_TRADE_ITEM_DEALED.MCHT_CODE
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setMchtCode(String mchtCode) {
        this.mchtCode = mchtCode;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.TRADE_TYPE
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.TRADE_TYPE
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.TRADE_TYPE
     * 
     * @param tradeType the value for CP_TRADE_ITEM_DEALED.TRADE_TYPE
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.COUPON_NO
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.COUPON_NO
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getCouponNo() {
        return couponNo;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.COUPON_NO
     * 
     * @param couponNo the value for CP_TRADE_ITEM_DEALED.COUPON_NO
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.ITEM_ORDER_NO
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.ITEM_ORDER_NO
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getItemOrderNo() {
        return itemOrderNo;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.ITEM_ORDER_NO
     * 
     * @param itemOrderNo the value for CP_TRADE_ITEM_DEALED.ITEM_ORDER_NO
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setItemOrderNo(String itemOrderNo) {
        this.itemOrderNo = itemOrderNo;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.TRADE_TIME
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.TRADE_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getTradeTime() {
        return tradeTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.TRADE_TIME
     * 
     * @param tradeTime the value for CP_TRADE_ITEM_DEALED.TRADE_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.AMOUNT
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.AMOUNT
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.AMOUNT
     * 
     * @param amount the value for CP_TRADE_ITEM_DEALED.AMOUNT
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.ORDER_NO
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.ORDER_NO
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.ORDER_NO
     * 
     * @param orderNo the value for CP_TRADE_ITEM_DEALED.ORDER_NO
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.ITEM_DES
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.ITEM_DES
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getItemDes() {
        return itemDes;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.ITEM_DES
     * 
     * @param itemDes the value for CP_TRADE_ITEM_DEALED.ITEM_DES
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.ITEM_REMARK
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.ITEM_REMARK
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getItemRemark() {
        return itemRemark;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.ITEM_REMARK
     * 
     * @param itemRemark the value for CP_TRADE_ITEM_DEALED.ITEM_REMARK
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.STATUS
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.STATUS
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.STATUS
     * 
     * @param status the value for CP_TRADE_ITEM_DEALED.STATUS
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.COUNTER
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.COUNTER
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Integer getCounter() {
        return counter;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.COUNTER
     * 
     * @param counter the value for CP_TRADE_ITEM_DEALED.COUNTER
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.BATCH_ID
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.BATCH_ID
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Long getBatchId() {
        return batchId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.BATCH_ID
     * 
     * @param batchId the value for CP_TRADE_ITEM_DEALED.BATCH_ID
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.CREATED_TIME
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.CREATED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.CREATED_TIME
     * 
     * @param createdTime the value for CP_TRADE_ITEM_DEALED.CREATED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.UPDATED_TIME
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.UPDATED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.UPDATED_TIME
     * 
     * @param updatedTime the value for CP_TRADE_ITEM_DEALED.UPDATED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.ACCEPTED_TIME
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.ACCEPTED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Date getAcceptedTime() {
        return acceptedTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.ACCEPTED_TIME
     * 
     * @param acceptedTime the value for CP_TRADE_ITEM_DEALED.ACCEPTED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setAcceptedTime(Date acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the value of the database column
     * CP_TRADE_ITEM_DEALED.DEALED_TIME
     * 
     * @return the value of CP_TRADE_ITEM_DEALED.DEALED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public Date getDealedTime() {
        return dealedTime;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the value of the database column
     * CP_TRADE_ITEM_DEALED.DEALED_TIME
     * 
     * @param dealedTime the value for CP_TRADE_ITEM_DEALED.DEALED_TIME
     * @abatorgenerated Tue Nov 05 22:35:44 CST 2013
     */
    public void setDealedTime(Date dealedTime) {
        this.dealedTime = dealedTime;
    }
}
