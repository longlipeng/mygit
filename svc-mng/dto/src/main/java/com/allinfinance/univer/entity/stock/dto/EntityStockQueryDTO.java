package com.allinfinance.univer.entity.stock.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 库存实体查询DTO
 * 
 * @author xxl
 */
public class EntityStockQueryDTO extends PageQueryDTO {

	private static final long serialVersionUID = 1L;

	// 根据订单找订单明细，在根据明细找库存
	private String orderId;

	private String cardNo;

	private String cardNoArray;
	
	private String startCardNo;

	private String endCardNo;
	
	private String lastRowNum;
	
	public String getLastRowNum() {
		return lastRowNum;
	}

	public void setLastRowNum(String lastRowNum) {
		this.lastRowNum = lastRowNum;
	}
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStartCardNo() {
		return startCardNo;
	}

	public void setStartCardNo(String startCardNo) {
		this.startCardNo = startCardNo;
	}

	public String getEndCardNo() {
		return endCardNo;
	}

	public void setEndCardNo(String endCardNo) {
		this.endCardNo = endCardNo;
	}

	public String getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String cardNoArray) {
		this.cardNoArray = cardNoArray;
	}
	

}
