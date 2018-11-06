package com.allinfinance.univer.issuer.dto.service;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ServiceQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7350744078821644962L;

	private String serviceId;

	private String serviceName;

	private String entityIdStr;

	private String entityName;
	
	private String productId;
	
	private String serviceFee;
	private String expiryDate;
	

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		if(serviceId.equals(""))serviceId=null;
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		if(serviceName.equals(""))serviceName=null;
		this.serviceName = serviceName;
	}

	public String getEntityIdStr() {
		return entityIdStr;
	}

	public void setEntityIdStr(String entityIdStr) {
		if(entityIdStr.equals(""))entityIdStr=null;
		this.entityIdStr = entityIdStr;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
