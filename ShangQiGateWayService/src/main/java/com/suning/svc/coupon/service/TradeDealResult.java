/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeDealResult.java
 * Author:   秦伟
 * Date:     2013-11-5 下午10:30:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;

/**
 * 交易处理结果
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TradeDealResult {

    /**
     * 交易处理结果
     */
    boolean success = true;

    /**
     * 交易处理成功 ，生成的订单ID 
     */
    long orderId;

    /**
     * 订单类型 TradeConstants.TradeOrderType
     */
    String orderType;

    String message;

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the orderId
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void fail(String message){
        this.success = false;
        this.message = message;
    }
}
