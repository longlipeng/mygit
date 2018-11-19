package com.allinfinance.univer.consumer.blacklistmchnt.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class BlackListMchntDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mchntNo;
	private String meno;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;
	private String startNo;
	private String endNo;
	private List<BlackListMchntDTO> blackListMchntDTOList;
	public String getMchntNo() {
		return mchntNo;
	}

	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
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

	public void setBlackListMchntDTOList(List<BlackListMchntDTO> blackListMchntDTOList) {
		this.blackListMchntDTOList = blackListMchntDTOList;
	}

	public List<BlackListMchntDTO> getBlackListMchntDTOList() {
		return blackListMchntDTOList;
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
