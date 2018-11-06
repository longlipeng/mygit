package com.allinfinance.univer.issuer.dto.cardserialnumber;

import com.allinfinance.framework.dto.BaseDTO;

public class CardSerialNumberDTO extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cardBin;
	
	private String binType;//0:自动生成  1：文件导入
	 
	private String issuerId;
	
	private String issuerCode;

    private String serialNumber;
   
    private String createUser;

    private String createTime;

    
    
	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerCode() {
		return issuerCode;
	}

	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
	
	
}
