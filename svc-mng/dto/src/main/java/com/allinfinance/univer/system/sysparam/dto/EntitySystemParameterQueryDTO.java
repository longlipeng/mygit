package com.allinfinance.univer.system.sysparam.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 实体系统参数
 * 
 * @author xxl
 * 
 */
public class EntitySystemParameterQueryDTO extends PageQueryDTO {

	private static final long serialVersionUID = 3896690578010034024L;
	private String entityId;
	private String fatherEntityId;
	private String parameterCode;
	private String parameterName;
	private String parameterValue;
	private String parameterRole;
	private String parameterComment;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

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

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getParameterRole() {
		return parameterRole;
	}

	public void setParameterRole(String parameterRole) {
		this.parameterRole = parameterRole;
	}

	public String getParameterComment() {
		return parameterComment;
	}

	public void setParameterComment(String parameterComment) {
		this.parameterComment = parameterComment;
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

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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

}
