package com.allinfinance.univer.issuer.dto.product;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class ProdFaceValueDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String faceValueId;

	private String productId;

	private String faceValueType;

	private String faceValue;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;
	private String dataState;

	private List<ProdFaceValueDTO> prodFaceValueDTO;

	public String getFaceValueId() {
		return faceValueId;
	}

	public void setFaceValueId(String faceValueId) {
		this.faceValueId = faceValueId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getFaceValueType() {
		return faceValueType;
	}

	public void setFaceValueType(String faceValueType) {
		this.faceValueType = faceValueType;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
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

	public List<ProdFaceValueDTO> getProdFaceValueDTO() {
		return prodFaceValueDTO;
	}

	public void setProdFaceValueDTO(List<ProdFaceValueDTO> prodFaceValueDTO) {
		this.prodFaceValueDTO = prodFaceValueDTO;
	}

}