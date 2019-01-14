package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 订单准备查询DTO
 * 
 * @author xxl
 * 
 */
public class SellOrderReadyQueryDTO extends BaseDTO {

	private static final long serialVersionUID = -2585128569883046641L;

	private String orderId;

	private SellOrderListQueryDTO orderListQueryDTO;

	private SellOrderCardListQueryDTO orderCardListQueryDTO;

	public SellOrderListQueryDTO getOrderListQueryDTO() {
		return orderListQueryDTO;
	}

	public void setOrderListQueryDTO(SellOrderListQueryDTO orderListQueryDTO) {
		this.orderListQueryDTO = orderListQueryDTO;
	}

	public SellOrderCardListQueryDTO getOrderCardListQueryDTO() {
		return orderCardListQueryDTO;
	}

	public void setOrderCardListQueryDTO(
			SellOrderCardListQueryDTO orderCardListQueryDTO) {
		this.orderCardListQueryDTO = orderCardListQueryDTO;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
