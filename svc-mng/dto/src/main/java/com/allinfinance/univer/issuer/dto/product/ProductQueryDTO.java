package com.allinfinance.univer.issuer.dto.product;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ProductQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -973878341193447258L;
	private String productId;
	
	private String productName;

	private String productType;

	private String cardType;

	private String prodStat;

	private String entityId;
	
	private String entityName;
	private String validPeriodRule;
	

	public String getValidPeriodRule() {
		return validPeriodRule;
	}

	public void setValidPeriodRule(String validPeriodRule) {
		this.validPeriodRule = validPeriodRule;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		if(productId.equals(""))productId=null;
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		if(productName.equals(""))productName=null;
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		if(productType.equals(""))productType=null;
		this.productType = productType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		if(cardType.equals(""))cardType=null;
		this.cardType = cardType;
	}

	public String getProdStat() {
		return prodStat;
	}

	public void setProdStat(String prodStat) {
		if(prodStat.equals(""))prodStat=null;
		this.prodStat = prodStat;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		if(entityId.equals(""))entityId=null;
		this.entityId = entityId;
	}

}
