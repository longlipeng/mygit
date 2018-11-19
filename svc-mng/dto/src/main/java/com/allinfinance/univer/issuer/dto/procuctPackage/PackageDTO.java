package com.allinfinance.univer.issuer.dto.procuctPackage;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class PackageDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String relId;
	
	

	private String packageId;

	private String packageName;

	private String packageFee;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String dataState;

	private List<PackageDTO> packageDTOs;
	
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
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

	public List<PackageDTO> getPackageDTOs() {
		return packageDTOs;
	}

	public void setPackageDTOs(List<PackageDTO> packageDTOs) {
		this.packageDTOs = packageDTOs;
	}

}
