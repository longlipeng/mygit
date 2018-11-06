package com.allinfinance.univer.entity.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 部门信息DTO
 * 
 * @author dawn
 * 
 */
public class DepartmentDTO extends BaseDTO {

	private static final long serialVersionUID = -2888916857489456256L;

	private String departmentId;
	private String entityId;
	private String departmentName;

	private String defaultFlag;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

	private List<DepartmentDTO> departmentDTO;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public List<DepartmentDTO> getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(List<DepartmentDTO> departmentDTO) {
		this.departmentDTO = departmentDTO;
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
