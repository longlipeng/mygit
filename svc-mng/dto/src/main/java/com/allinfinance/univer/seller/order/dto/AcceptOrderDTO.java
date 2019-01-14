/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AcceptOrderDTO.java
 * Author:   xuwei
 * Date:     2013-11-18 上午11:28:47
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

import java.io.Serializable;

/**
 * 接收卡片明细DTO
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AcceptOrderDTO extends PageQueryDTO {

    /**
     */
    private static final long serialVersionUID = 1L;
    //订单ID
    private String orderId;
    //订单类型
    private String orderType;
    //起始卡号
    private String startCardNo;
    //结束卡号
    private String endCardNo;
    //张数
    private String cardNum;
    //卡号
    private String cardNoList;
    //机构号
    private String entity;
    //接收删除信息
    private String[] orderListCardPool;
    
    
    /**
     * @return the cardNoList
     */
    
    public String getCardNoList() {
		return cardNoList;
	}
	
	/**
     * @param cardNoList the cardNoList to set
     */
    public void setCardNoList(String cardNoList) {
		this.cardNoList = cardNoList;
	}
	/**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
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
     * @return the startCardNo
     */
    public String getStartCardNo() {
        return startCardNo;
    }
    /**
     * @param startCardNo the startCardNo to set
     */
    public void setStartCardNo(String startCardNo) {
        this.startCardNo = startCardNo;
    }
    /**
     * @return the endCardNo
     */
    public String getEndCardNo() {
        return endCardNo;
    }
    /**
     * @param endCardNo the endCardNo to set
     */
    public void setEndCardNo(String endCardNo) {
        this.endCardNo = endCardNo;
    }
    /**
     * @return the cardNum
     */
    public String getCardNum() {
        return cardNum;
    }
    /**
     * @param cardNum the cardNum to set
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    /**
     * @return the entity
     */
    public String getEntity() {
        return entity;
    }
    /**
     * @param entity the entity to set
     */
    public void setEntity(String entity) {
        this.entity = entity;
    }
    /**
     * @return the orderListCardPool
     */
    public String[] getOrderListCardPool() {
        return orderListCardPool;
    }
    /**
     * @param orderListCardPool the orderListCardPool to set
     */
    public void setOrderListCardPool(String[] orderListCardPool) {
        this.orderListCardPool = orderListCardPool;
    }
    
 
}
