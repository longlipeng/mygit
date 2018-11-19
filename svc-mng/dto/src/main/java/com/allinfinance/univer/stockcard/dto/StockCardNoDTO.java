package com.allinfinance.univer.stockcard.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class StockCardNoDTO extends PageQueryDTO {
	
	private String cardNo;
    private String cardValidDate;
    private String productId;
    private String functionRoleId;
    private  String entityId;
    private String stockState;
    private String cardNoList;
    
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardValidDate() {
		return cardValidDate;
	}
	public void setCardValidDate(String cardValidDate) {
		this.cardValidDate = cardValidDate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getFunctionRoleId() {
		return functionRoleId;
	}
	public void setFunctionRoleId(String functionRoleId) {
		this.functionRoleId = functionRoleId;
	}
	public String getStockState() {
		return stockState;
	}
	public void setStockState(String stockState) {
		this.stockState = stockState;
	}
	public String getCardNoList() {
		return cardNoList;
	}
	public void setCardNoList(String cardNoList) {
		this.cardNoList = cardNoList;
	}
    
    
    

}
