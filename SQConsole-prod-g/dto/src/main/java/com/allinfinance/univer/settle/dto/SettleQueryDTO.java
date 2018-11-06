package com.allinfinance.univer.settle.dto;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;

public class SettleQueryDTO extends PageQueryDTO{
	private String settleId;
	private String settleObject1;
	private String settleObject2;
	private String settleStartDate;
	private String settleEndDate;
	private String minAmt;
	private String maxAmt;
	private String settleState;
	private String settleObject2Name;
	private String settleAmount;
	private List<MerchantDTO> merchantDTOs;
	private Map<String, List<byte[]>> fileData;
	
	public List<MerchantDTO> getMerchantDTOs() {
		return merchantDTOs;
	}
	public void setMerchantDTOs(List<MerchantDTO> merchantDTOs) {
		this.merchantDTOs = merchantDTOs;
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
	public String getSettleStartDate() {
		return settleStartDate;
	}
	public void setSettleStartDate(String settleStartDate) {
		this.settleStartDate = settleStartDate;
	}
	public String getSettleEndDate() {
		return settleEndDate;
	}
	public void setSettleEndDate(String settleEndDate) {
		this.settleEndDate = settleEndDate;
	}
	public String getMinAmt() {
		return minAmt;
	}
	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}
	public String getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}
	public String getSettleState() {
		return settleState;
	}
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	public String getSettleObject2Name() {
		return settleObject2Name;
	}
	public void setSettleObject2Name(String settleObject2Name) {
		this.settleObject2Name = settleObject2Name;
	}
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public Map<String, List<byte[]>> getFileData() {
		return fileData;
	}
	public void setFileData(Map<String, List<byte[]>> fileData) {
		this.fileData = fileData;
	}

}
