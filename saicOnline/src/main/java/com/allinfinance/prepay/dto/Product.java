package com.allinfinance.prepay.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("product")
public class Product {
	@XStreamAlias("productId")
	private String productId;//����Ʒ����
	@XStreamAlias("productName")
	private String productName;//����Ʒ����
	@XStreamAlias("entityId")
	private String entityId;//����Ʒ������ϵͳ
	@XStreamAlias("cardType")
	private String cardType;//������
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
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	
	
	
}
