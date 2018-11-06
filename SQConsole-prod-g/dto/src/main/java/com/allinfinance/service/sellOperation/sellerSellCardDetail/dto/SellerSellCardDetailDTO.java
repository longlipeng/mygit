/**
 * Classname SellerSellCardDetailDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		yaoxin		2012-11-16
 * =============================================================================
 */

package com.allinfinance.service.sellOperation.sellerSellCardDetail.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author yaoxin
 *
 */
public class SellerSellCardDetailDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String issuerId;
	private String issuerName;
	private String customerId;
	private String customerName;
	private String startDate;
	private String endDate;
	
	private String customerTelephone;
	private String customerIdNo;
	//持卡人信息
	private String cardholderGender;
	private String plateNumber;
	private String cardholderName;
	private String cardholderMobile;
	private String cardholderIdNo;
	private String cardNo;
	/**
	 * 快递点名称
	 */
	private String deliveryName;
	/**
	 * 快递点联系电话
	 */
	private String contactPhone;
	/**
	 * 收货地址
	 */
	private String deliveryAddress;
	private String deliveryPostcode;
	
	private String CUSTOMER_NAME;
	private String ORDERID;
	private String ORDERTYPE;
	private String FACEVALUE;
	private String AMOUNT;
	private String AL;
	private String ORDERTIME;
	private String SALEMAN;
	private String CONTACT_NAME;
	private String PRODUCT_NAME;
	private String PAYMENTTERM;
	private String ALOTHER;
	private String CHANNEL;
	private String BANK_NAME;
	private String BANK_ACCOUNT;
	private String PAYMENTSTATE;
	private String ORDERSTATE;
	private String FROMBANK;
	private String FROMBANKACCOUNT;
	private String MODIFY_TIME;
	
	private String sellChannel;//售卡方式
	private String termChannel;//售卡渠道号
	private String branchId;//售卡服务点
	private String cardholderComment;//备注
	
	
	
	
	private String ENTITY_ID;
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public String getENTITY_ID() {
		return ENTITY_ID;
	}
	public void setENTITY_ID(String eNTITYID) {
		ENTITY_ID = eNTITYID;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMERNAME) {
		CUSTOMER_NAME = cUSTOMERNAME;
	}
	public String getORDERID() {
		return ORDERID;
	}
	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}
	public String getORDERTYPE() {
		return ORDERTYPE;
	}
	public void setORDERTYPE(String oRDERTYPE) {
		ORDERTYPE = oRDERTYPE;
	}
	public String getFACEVALUE() {
		return FACEVALUE;
	}
	public void setFACEVALUE(String fACEVALUE) {
		FACEVALUE = fACEVALUE;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getAL() {
		return AL;
	}
	public void setAL(String aL) {
		AL = aL;
	}
	public String getORDERTIME() {
		return ORDERTIME;
	}
	public void setORDERTIME(String oRDERTIME) {
		ORDERTIME = oRDERTIME;
	}
	public String getSALEMAN() {
		return SALEMAN;
	}
	public void setSALEMAN(String sALEMAN) {
		SALEMAN = sALEMAN;
	}
	public String getCONTACT_NAME() {
		return CONTACT_NAME;
	}
	public void setCONTACT_NAME(String cONTACTNAME) {
		CONTACT_NAME = cONTACTNAME;
	}
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	public void setPRODUCT_NAME(String pRODUCTNAME) {
		PRODUCT_NAME = pRODUCTNAME;
	}
	public String getPAYMENTTERM() {
		return PAYMENTTERM;
	}
	public void setPAYMENTTERM(String pAYMENTTERM) {
		PAYMENTTERM = pAYMENTTERM;
	}
	public String getALOTHER() {
		return ALOTHER;
	}
	public void setALOTHER(String aLOTHER) {
		ALOTHER = aLOTHER;
	}
	public String getCHANNEL() {
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANKNAME) {
		BANK_NAME = bANKNAME;
	}
	public String getBANK_ACCOUNT() {
		return BANK_ACCOUNT;
	}
	public void setBANK_ACCOUNT(String bANKACCOUNT) {
		BANK_ACCOUNT = bANKACCOUNT;
	}
	public String getPAYMENTSTATE() {
		return PAYMENTSTATE;
	}
	public void setPAYMENTSTATE(String pAYMENTSTATE) {
		PAYMENTSTATE = pAYMENTSTATE;
	}
	public String getORDERSTATE() {
		return ORDERSTATE;
	}
	public void setORDERSTATE(String oRDERSTATE) {
		ORDERSTATE = oRDERSTATE;
	}
	public String getFROMBANK() {
		return FROMBANK;
	}
	public void setFROMBANK(String fROMBANK) {
		FROMBANK = fROMBANK;
	}
	public String getFROMBANKACCOUNT() {
		return FROMBANKACCOUNT;
	}
	public void setFROMBANKACCOUNT(String fROMBANKACCOUNT) {
		FROMBANKACCOUNT = fROMBANKACCOUNT;
	}
	public String getMODIFY_TIME() {
		return MODIFY_TIME;
	}
	public void setMODIFY_TIME(String mODIFY_TIME) {
		MODIFY_TIME = mODIFY_TIME;
	}
	public String getCustomerIdNo() {
		return customerIdNo;
	}
	public void setCustomerIdNo(String customerIdNo) {
		this.customerIdNo = customerIdNo;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getCardholderMobile() {
		return cardholderMobile;
	}
	public void setCardholderMobile(String cardholderMobile) {
		this.cardholderMobile = cardholderMobile;
	}
	public String getCardholderIdNo() {
		return cardholderIdNo;
	}
	public void setCardholderIdNo(String cardholderIdNo) {
		this.cardholderIdNo = cardholderIdNo;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardholderGender() {
		return cardholderGender;
	}
	public void setCardholderGender(String cardholderGender) {
		this.cardholderGender = cardholderGender;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getDeliveryPostcode() {
		return deliveryPostcode;
	}
	public void setDeliveryPostcode(String deliveryPostcode) {
		this.deliveryPostcode = deliveryPostcode;
	}
	
	public String getTermChannel() {
		return termChannel;
	}
	public void setTermChannel(String termChannel) {
		this.termChannel = termChannel;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getCardholderComment() {
		return cardholderComment;
	}
	public void setCardholderComment(String cardholderComment) {
		this.cardholderComment = cardholderComment;
	}
	public String getSellChannel() {
		return sellChannel;
	}
	public void setSellChannel(String sellChannel) {
		this.sellChannel = sellChannel;
	}
	
	
}
