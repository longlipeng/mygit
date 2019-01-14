/**
 * Classname SellerSellTradeDetailDTO.java
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
 *		yaoxin		2012-11-20
 * =============================================================================
 */

package com.allinfinance.service.sellOperation.sellerSellTradeDetail.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author yaoxin
 *
 */
public class SellerSellTradeDetailDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String issuerId;
	private String issuerName;
	private String customerId;
	private String customerName;
	private String startDate;
	private String endDate;
	
	private String CUSTOMERID1;
	private String CUSTOMERNAME;
	private String CONTACT_NAME;
	private String ORDERTYPE;
	private String ORDERID;
	private String CARD_NO;
	private String PRODUCTNAME;
	private String ONYMOUSNAME;
	private String FACEVALUE;
	private String PAYMENTSTATE;
	private String CHANNEL;
	private String PAYDATE;
	private String FROMBANK;
	private String FROMBANKACCOUNT;
	private String BANK_NAME;
	private String BANK_ACCOUNT;
	private String MODIFYTIME;
	
	
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
	
	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}
	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}
	public String getCONTACT_NAME() {
		return CONTACT_NAME;
	}
	public void setCONTACT_NAME(String cONTACTNAME) {
		CONTACT_NAME = cONTACTNAME;
	}
	public String getORDERTYPE() {
		return ORDERTYPE;
	}
	public void setORDERTYPE(String oRDERTYPE) {
		ORDERTYPE = oRDERTYPE;
	}
	public String getORDERID() {
		return ORDERID;
	}
	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARDNO) {
		CARD_NO = cARDNO;
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getONYMOUSNAME() {
		return ONYMOUSNAME;
	}
	public void setONYMOUSNAME(String oNYMOUSNAME) {
		ONYMOUSNAME = oNYMOUSNAME;
	}
	public String getFACEVALUE() {
		return FACEVALUE;
	}
	public void setFACEVALUE(String fACEVALUE) {
		FACEVALUE = fACEVALUE;
	}
	public String getPAYMENTSTATE() {
		return PAYMENTSTATE;
	}
	public void setPAYMENTSTATE(String pAYMENTSTATE) {
		PAYMENTSTATE = pAYMENTSTATE;
	}
	public String getCHANNEL() {
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	public String getPAYDATE() {
		return PAYDATE;
	}
	public void setPAYDATE(String pAYDATE) {
		PAYDATE = pAYDATE;
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
	public String getMODIFYTIME() {
		return MODIFYTIME;
	}
	public void setMODIFYTIME(String mODIFYTIME) {
		MODIFYTIME = mODIFYTIME;
	}
	
	public String getCUSTOMERID1() {
		return CUSTOMERID1;
	}
	public void setCUSTOMERID1(String cUSTOMERID1) {
		CUSTOMERID1 = cUSTOMERID1;
	}

}
