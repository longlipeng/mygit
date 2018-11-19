/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ShopSynchDTO.java
 * Author:   lfr
 * Date:     1970-1-1 上午12:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

/**
 * 
 * 门店同步DTO
 * 
 * @author lfr
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ShopSynchDTO implements java.io.Serializable {

    private static final long serialVersionUID = -3839356641531804725L;

    private String shopId;
    private String entityId;
    private String shopName;
    private String shopState;
    private String consumerId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopState() {
        return shopState;
    }

    public void setShopState(String shopState) {
        this.shopState = shopState;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

}
