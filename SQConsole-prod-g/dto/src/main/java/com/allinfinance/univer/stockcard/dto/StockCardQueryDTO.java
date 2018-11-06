package com.allinfinance.univer.stockcard.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class StockCardQueryDTO extends PageQueryDTO {
	private static final long serialVersionUID = -7873956869297802560L;
	private String productId;
	private String productName;
	private String faceValueType;
	private String faceValue;
	private String onymousStat;
	private String cardValidDate;
	private String entityId;
	
	/**
	 * 2015-01-05	xiehao
	 * 新增属性 通过此属性区分发行机构和一级营销机构的库存 
	 */
	private String functionRoleId;
	
	public String getFunctionRoleId() {
		return functionRoleId;
	}
	
	public void setFunctionRoleId(String functionRoleId) {
		this.functionRoleId = functionRoleId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFaceValueType() {
		return faceValueType;
	}

	public void setFaceValueType(String faceValueType) {
		this.faceValueType = faceValueType;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getOnymousStat() {
		return onymousStat;
	}

	public void setOnymousStat(String onymousStat) {
		this.onymousStat = onymousStat;
	}

	public String getCardValidDate() {
		return cardValidDate;
	}

	public void setCardValidDate(String cardValidDate) {
		this.cardValidDate = cardValidDate;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityId() {
		return entityId;
	}

}
