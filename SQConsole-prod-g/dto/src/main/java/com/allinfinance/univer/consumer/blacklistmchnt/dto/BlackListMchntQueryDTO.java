package com.allinfinance.univer.consumer.blacklistmchnt.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class BlackListMchntQueryDTO extends PageQueryDTO implements
java.io.Serializable{

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
	/**
	 * 起始日期
	 */
	private String startDate;
	/**
	 * 结束日期
	 */
	private String endDate;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
