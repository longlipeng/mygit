package com.allinfinance.univer.issuer.dto.service;

import java.io.Serializable;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class ServiceDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4690985468577758778L;

	private String createTime;

	private String createUser;

	private String dataState;

	private String defaultRate;

	private String entityId;

	private String expiryDate;

	private String isAgent;

	private String modifyTime;

	private String modifyUser;

	private String serviceEnglishName;

	private String serviceId;

	private String serviceName;

	private String serviceShortName;

	private String entityName;

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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	private List<ServiceDTO> serviceDTOs;

	public List<ServiceDTO> getServiceDTOs() {
		return serviceDTOs;
	}

	public void setServiceDTOs(List<ServiceDTO> serviceDTOs) {
		this.serviceDTOs = serviceDTOs;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getDataState() {
		return dataState;
	}

	public String getDefaultRate() {
		return defaultRate;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public String getIsAgent() {
		return isAgent;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public String getServiceEnglishName() {
		return serviceEnglishName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceShortName() {
		return serviceShortName;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public void setDefaultRate(String defaultRate) {
		this.defaultRate = defaultRate;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setServiceEnglishName(String serviceEnglishName) {
		this.serviceEnglishName = serviceEnglishName;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceShortName(String serviceShortName) {
		this.serviceShortName = serviceShortName;
	}

}
