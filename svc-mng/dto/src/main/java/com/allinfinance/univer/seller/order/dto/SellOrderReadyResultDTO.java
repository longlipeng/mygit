package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageDataDTO;

/**
 * 订单准备查询结果DTO
 * 
 * @author xxl
 * 
 */
public class SellOrderReadyResultDTO extends BaseDTO {

	private static final long serialVersionUID = -3284295608739541282L;
	private SellOrderDTO sellOrderDTO;
	private PageDataDTO orderLists;
	private PageDataDTO orderCardLists;

	public PageDataDTO getOrderLists() {
		return orderLists;
	}

	public void setOrderLists(PageDataDTO orderLists) {
		this.orderLists = orderLists;
	}

	public PageDataDTO getOrderCardLists() {
		return orderCardLists;
	}

	public void setOrderCardLists(PageDataDTO orderCardLists) {
		this.orderCardLists = orderCardLists;
	}

	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}

	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}

}
