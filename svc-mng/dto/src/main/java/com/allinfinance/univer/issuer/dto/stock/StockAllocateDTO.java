package com.allinfinance.univer.issuer.dto.stock;

import java.io.Serializable;
import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;

public class StockAllocateDTO extends PageQueryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String allocateId;
	public String allocateIn;
	public String allocateOut;
	public String allocateCount;
	public String allocateUser;
	public String allocateUserName;
	public String allocateDate;
	public String productId;
	public String productName;
	public String faceValueType;
	public String faceValue;
	public String cardLayoutName;
	public List<String> cardNos;
	public String cardNo;
	public PageDataDTO orderList=new PageDataDTO();
	public PageDataDTO orderCardList=new PageDataDTO();
	public String createUser;
	public String createDate;
	public String cardNoList;
	public String startDate;
	public String endDate;
	
	public String getAllocateId() {
		return allocateId;
	}
	public void setAllocateId(String allocateId) {
		this.allocateId = allocateId;
	}
	public String getAllocateIn() {
		return allocateIn;
	}
	public void setAllocateIn(String allocateIn) {
		this.allocateIn = allocateIn;
	}
	public String getAllocateOut() {
		return allocateOut;
	}
	public void setAllocateOut(String allocateOut) {
		this.allocateOut = allocateOut;
	}
	public String getAllocateCount() {
		return allocateCount;
	}
	public void setAllocateCount(String allocateCount) {
		this.allocateCount = allocateCount;
	}
	public String getAllocateUser() {
		return allocateUser;
	}
	public void setAllocateUser(String allocateUser) {
		this.allocateUser = allocateUser;
	}
	public String getAllocateDate() {
		return allocateDate;
	}
	public void setAllocateDate(String allocateDate) {
		this.allocateDate = allocateDate;
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
	
	
	public String getCardLayoutName() {
		return cardLayoutName;
	}
	public void setCardLayoutName(String cardLayoutName) {
		this.cardLayoutName = cardLayoutName;
	}
	public List<String> getCardNos() {
		return cardNos;
	}
	public void setCardNos(List<String> cardNos) {
		this.cardNos = cardNos;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAllocateUserName() {
		return allocateUserName;
	}
	public void setAllocateUserName(String allocateUserName) {
		this.allocateUserName = allocateUserName;
	}
	public PageDataDTO getOrderList() {
		return orderList;
	}
	public void setOrderList(PageDataDTO orderList) {
		this.orderList = orderList;
	}
	public PageDataDTO getOrderCardList() {
		return orderCardList;
	}
	public void setOrderCardList(PageDataDTO orderCardList) {
		this.orderCardList = orderCardList;
	}
	public String getCardNoList() {
		return cardNoList;
	}
	public void setCardNoList(String cardNoList) {
		this.cardNoList = cardNoList;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
}
