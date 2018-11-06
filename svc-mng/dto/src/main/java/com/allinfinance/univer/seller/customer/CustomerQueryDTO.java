package com.allinfinance.univer.seller.customer;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class CustomerQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String entityId;

	private String fatherEntityId;

	private String customerName;

	private String customerCode;

	private String customerEnglishName;

	private String customerAddress;

	private Date startTime;

	private Date stopTime;

	private String dataState;
	private String cusState;
	private String externalId;
	private String paymentTerm;
	private String activitySector;
	private String salesRegionId;
	private String flag;
	private String channel;
	private String customerType;
	private String sellContractId;
	private String telephoneNumber;
	// 法人证件号
	private String corpCredId;
	//证件类型
	private String corpCredType;
	
	
	

	public String getCorpCredType() {
		return corpCredType;
	}

	public void setCorpCredType(String corpCredType) {
		this.corpCredType = corpCredType;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getCorpCredId() {
		return corpCredId;
	}

	public void setCorpCredId(String corpCredId) {
		this.corpCredId = corpCredId;
	}

	public String getSellContractId() {
		return sellContractId;
	}

	public void setSellContractId(String sellContractId) {
		this.sellContractId = sellContractId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCusState() {
		return cusState;
	}

	public void setCusState(String cusState) {
		this.cusState = cusState;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getActivitySector() {
		return activitySector;
	}

	public void setActivitySector(String activitySector) {
		this.activitySector = activitySector;
	}

	public String getSalesRegionId() {
		return salesRegionId;
	}

	public void setSalesRegionId(String salesRegionId) {
		this.salesRegionId = salesRegionId;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerEnglishName() {
		return customerEnglishName;
	}

	public void setCustomerEnglishName(String customerEnglishName) {
		this.customerEnglishName = customerEnglishName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

}
