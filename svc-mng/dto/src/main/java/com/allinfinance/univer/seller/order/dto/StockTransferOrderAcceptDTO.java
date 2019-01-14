/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderAcceptDTO.java
 * Author:   13071598
 * Date:     2013-11-11 下午02:42:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 订单入库DTO
 *
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderAcceptDTO extends BaseDTO{

    /**
     */
    private static final long serialVersionUID = -7450845424336737561L;
    
    private String orderId;
    
    /**
     * 订单卡明细查询list
     */
    private List<SellOrderCardListQueryDTO> SellOrderCardQueryList;

    public List<SellOrderCardListQueryDTO> getSellOrderCardQueryList() {
        return SellOrderCardQueryList;
    }

    public void setSellOrderCardQueryList(List<SellOrderCardListQueryDTO> sellOrderCardQueryList) {
        SellOrderCardQueryList = sellOrderCardQueryList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
     
    
    
}
