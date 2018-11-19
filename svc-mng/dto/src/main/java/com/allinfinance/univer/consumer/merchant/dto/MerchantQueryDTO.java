package com.allinfinance.univer.consumer.merchant.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class MerchantQueryDTO extends PageQueryDTO implements
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8365789208544894712L;
	private Integer selecthisFlag;
	private String merchantState;
	private Date txnStartTime;
	private Date txnStopTime;
	private String entityId;
	private String merchantName;
	private String externalId;
	private String fatherEntityId;

	private String merchantRealityName;
	private String merchantAttribute;
	private String ePayIn;
	private String dataState;
	private String merchantCode;

	public Integer getSelecthisFlag() {
		return selecthisFlag;
	}

	public void setSelecthisFlag(Integer selecthisFlag) {
		this.selecthisFlag = selecthisFlag;
	}

	public String getMerchantState() {
		return merchantState;
	}

	public void setMerchantState(String merchantState) {
		this.merchantState = merchantState;
	}

	public Date getTxnStartTime() {
		return txnStartTime;
	}

	public void setTxnStartTime(Date txnStartTime) {
		this.txnStartTime = txnStartTime;
	}

	public Date getTxnStopTime() {
		return txnStopTime;
	}

	public void setTxnStopTime(Date txnStopTime) {
		this.txnStopTime = txnStopTime;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public String getMerchantRealityName() {
		return merchantRealityName;
	}

	public void setMerchantRealityName(String merchantRealityName) {
		this.merchantRealityName = merchantRealityName;
	}

	public String getMerchantAttribute() {
		return merchantAttribute;
	}

	public void setMerchantAttribute(String merchantAttribute) {
		this.merchantAttribute = merchantAttribute;
	}

	public String getePayIn() {
		return ePayIn;
	}

	public void setePayIn(String ePayIn) {
		this.ePayIn = ePayIn;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

}
