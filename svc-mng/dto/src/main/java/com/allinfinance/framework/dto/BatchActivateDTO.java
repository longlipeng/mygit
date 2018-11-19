package com.allinfinance.framework.dto;

import java.util.List;

/**
 * 批量激活
 */
public class BatchActivateDTO extends PageQueryDTO {
	private static final long serialVersionUID = 1L;
	private String cardNo;				//卡号
	private String activateAmt;			//充值金额
	private String activateState;		//激活状态
	private String createTime;			
	private String createUser;
	private List<String> list;
	private String batchNo;				//批次号
	private String beginTime;			//查询起始日期
	private String endTime;				//查询截止日期
	private String cardNum;				//卡数
	private String fileName;			//文件名
	private String flag;				//操作类型00-首充 01-激活 02-再充值
	private List<BatchActivateDTO> batList;
	private List<BatchRechargeDTO> recList;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getActivateAmt() {
		return activateAmt;
	}
	public void setActivateAmt(String activateAmt) {
		this.activateAmt = activateAmt;
	}
	public String getActivateState() {
		return activateState;
	}
	public void setActivateState(String activateState) {
		this.activateState = activateState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<BatchActivateDTO> getBatList() {
		return batList;
	}
	public void setBatList(List<BatchActivateDTO> batList) {
		this.batList = batList;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<BatchRechargeDTO> getRecList() {
		return recList;
	}
	public void setRecList(List<BatchRechargeDTO> recList) {
		this.recList = recList;
	}
	
}
	
