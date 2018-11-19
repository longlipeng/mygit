package com.allinfinance.univer.system.role.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ResourceDTO implements Serializable{

	private static final long serialVersionUID = 3213123123312L;
	
    private String createTime;
    
    private String modifyTime;
    
    private String dataState;
    
	private String resourceId;

	private String resourceName;

	private String resourceUrl;

	private String resourceType;

	private String resourceTxnCode;

	private String fatherResourceId;

	private String surResource;

	private Integer createUser;

	private Integer modifyUser;

	private List<RoleDTO> roleDTOList;

	private String roles;
    private String functionRoleId;
	

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	

	public String getResourceTxnCode() {
		return resourceTxnCode;
	}

	public void setResourceTxnCode(String resourceTxnCode) {
		this.resourceTxnCode = resourceTxnCode;
	}

	
	public String getSurResource() {
		return surResource;
	}

	public void setSurResource(String surResource) {
		this.surResource = surResource;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public List<RoleDTO> getRoleDTOList() {
		return roleDTOList;
	}

	public void setRoleDTOList(List<RoleDTO> roleDTOList) {
		this.roleDTOList = roleDTOList;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	

	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getFatherResourceId() {
		return fatherResourceId;
	}

	public void setFatherResourceId(String fatherResourceId) {
		this.fatherResourceId = fatherResourceId;
	}

	public String getFunctionRoleId() {
		return functionRoleId;
	}

	public void setFunctionRoleId(String functionRoleId) {
		this.functionRoleId = functionRoleId;
	}
	
}
