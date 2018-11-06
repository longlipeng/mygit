package com.allinfinance.univer.settle.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

public class SettleDTO extends BaseDTO{
	private String settleId;
	private String settleObject1;
	private String settleObject2; 
	private String settleObject2Name;
	private String settleStartDate;
	private String settleDate;
	private String settleMoney; 
	//private String settleState;
	private String settleFeeOffset;
	private List<String> settleIdList;
	private List<String> settleObject2List;
	private String memo;
	private String loginUserId;
	private String merchantCode;
	private String contractId;
	
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getSettleObject2Name() {
		return settleObject2Name;
	}
	public void setSettleObject2Name(String settleObject2Name) {
		this.settleObject2Name = settleObject2Name;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getSettleStartDate() {
		return settleStartDate;
	}
	public void setSettleStartDate(String settleStartDate) {
		this.settleStartDate = settleStartDate;
	}
	public String getSettleId() {
		return settleId;
	}
	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}
	public String getSettleObject1() {
		return settleObject1;
	}
	public void setSettleObject1(String settleObject1) {
		this.settleObject1 = settleObject1;
	}
	public String getSettleObject2() {
		return settleObject2;
	}
	public void setSettleObject2(String settleObject2) {
		this.settleObject2 = settleObject2;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getSettleMoney() {
		return settleMoney;
	}
	public void setSettleMoney(String settleMoney) {
		this.settleMoney = settleMoney;
	}
	
	public List<String> getSettleObject2List() {
		return settleObject2List;
	}
	public List<String> getSettleIdList() {
		return settleIdList;
	}
	public void setSettleObject2List(List<String> settleObject2List) {
		this.settleObject2List = settleObject2List;
	}
	public void setSettleIdList(List<String> settleIdList) {
		this.settleIdList = settleIdList;
	}
	public String getSettleFeeOffset() {
		return settleFeeOffset;
	}
	public void setSettleFeeOffset(String settleFeeOffset) {
		this.settleFeeOffset = settleFeeOffset;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	
}
