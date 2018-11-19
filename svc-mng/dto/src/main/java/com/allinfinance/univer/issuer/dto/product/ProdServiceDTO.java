package com.allinfinance.univer.issuer.dto.product;

import java.io.Serializable;

import com.allinfinance.framework.dto.BaseDTO;

public class ProdServiceDTO extends BaseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productId;

	private String serviceId;

	private String maxTxnAmt;

	private String maxDayTxnAmt;

	private String webMaxTxnAmt;

	private String webMaxDayTxnAmt;

	private String serviceName;

	private String serviceFee;
	
	private String relId;
	
	private String defaultFlag;

    

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getMaxTxnAmt() {
		return maxTxnAmt;
	}

	public void setMaxTxnAmt(String maxTxnAmt) {
		this.maxTxnAmt = maxTxnAmt;
	}

	public String getMaxDayTxnAmt() {
		return maxDayTxnAmt;
	}

	public void setMaxDayTxnAmt(String maxDayTxnAmt) {
		this.maxDayTxnAmt = maxDayTxnAmt;
	}

	public String getWebMaxTxnAmt() {
		return webMaxTxnAmt;
	}

	public void setWebMaxTxnAmt(String webMaxTxnAmt) {
		this.webMaxTxnAmt = webMaxTxnAmt;
	}

	public String getWebMaxDayTxnAmt() {
		return webMaxDayTxnAmt;
	}

	public void setWebMaxDayTxnAmt(String webMaxDayTxnAmt) {
		this.webMaxDayTxnAmt = webMaxDayTxnAmt;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
