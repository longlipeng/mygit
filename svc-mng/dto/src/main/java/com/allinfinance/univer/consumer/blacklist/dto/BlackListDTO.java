package com.allinfinance.univer.consumer.blacklist.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class BlackListDTO extends BaseDTO implements java.io.Serializable{

	/**
	 * 人员黑名单
	 */
	private static final long serialVersionUID = 1L;
	private String cardNo;
	private String meno;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;
	private String startNo;
	private String endNo;
	private List<BlackListDTO> blackListDTOList;
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
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

	public void setBlackListDTOList(List<BlackListDTO> blackListDTOList) {
		this.blackListDTOList = blackListDTOList;
	}

	public List<BlackListDTO> getBlackListDTOList() {
		return blackListDTOList;
	}

	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	public String getEndNo() {
		return endNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public String getStartNo() {
		return startNo;
	}

}
