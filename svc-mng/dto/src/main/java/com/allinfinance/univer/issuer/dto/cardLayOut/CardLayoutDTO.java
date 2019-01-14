package com.allinfinance.univer.issuer.dto.cardLayOut;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class CardLayoutDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cardLayoutId;

	private String cardName;

	private Short validFlag;

	private String memo;

	private String createUser;

	private String createTime;

	private Integer modifyUser;

	private String modifyTime;

	private Short dataState;

	private byte[] picture;

	private Integer printFormat;

	private String relId;

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	private Integer backPanleInfo;

	private List<CardLayoutDTO> cardLayoutDTO;

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public Short getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Short validFlag) {
		this.validFlag = validFlag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Integer getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Short getDataState() {
		return dataState;
	}

	public void setDataState(Short dataState) {
		this.dataState = dataState;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public List<CardLayoutDTO> getCardLayoutDTO() {
		return cardLayoutDTO;
	}

	public void setCardLayoutDTO(List<CardLayoutDTO> cardLayoutDTO) {
		this.cardLayoutDTO = cardLayoutDTO;
	}

	public Integer getBackPanleInfo() {
		return backPanleInfo;
	}

	public void setBackPanleInfo(Integer backPanleInfo) {
		this.backPanleInfo = backPanleInfo;
	}

	public Integer getPrintFormat() {
		return printFormat;
	}

	public void setPrintFormat(Integer printFormat) {
		this.printFormat = printFormat;
	}

}
