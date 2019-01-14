package com.allinfinance.univer.entity.stock.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 实体库存表
 * 
 * @author dawn
 * 
 */
public class EntityStockDTO extends PageQueryDTO {

	private static final long serialVersionUID = 1L;
	private String orderId;
	private String startCardNo;
	private String endCardNo;
	private String cardNo;
	private String cardNoArray;
	private String entityId;
	private String functionRoleId;
	private String productId;
	private String cardLayoutId;
	private String faceValueType;
	private String faceValue;
	private String cardValidDate;
	private String stockState;
	private List<String> cardNoList=new ArrayList<String>();
	
	private String productIds;
	private String productName;

	private String cardLayoutName;

	private String cardQuantity;
	

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

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

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
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

	public String getCardValidDate() {
		return cardValidDate;
	}

	public void setCardValidDate(String cardValidDate) {
		this.cardValidDate = cardValidDate;
	}

	public String getStockState() {
		return stockState;
	}

	public void setStockState(String stockState) {
		this.stockState = stockState;
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

	public List<String> getCardNoList() {
		return cardNoList;
	}

	public void setCardNoList(List<String> cardNoList) {
		this.cardNoList = cardNoList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCardLayoutName() {
		return cardLayoutName;
	}

	public void setCardLayoutName(String cardLayoutName) {
		this.cardLayoutName = cardLayoutName;
	}

	public String getCardQuantity() {
		return cardQuantity;
	}

	public void setCardQuantity(String cardQuantity) {
		this.cardQuantity = cardQuantity;
	}

}
