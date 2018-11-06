package com.allinfinance.univer.seller.sellercontract.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class SellerAcctypeContractDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3248237809961561500L;
	private String id;
	private String sellContractId;
	private String ruleNo;

	private String ruleName;

	private String productName;

	private String productId;

	private String acctypeId;

	private String serviceName;

	private String txnNum;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String fee;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSellContractId() {
		return sellContractId;
	}

	public void setSellContractId(String sellContractId) {
		this.sellContractId = sellContractId;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAcctypeId() {
		return acctypeId;
	}

	public void setAcctypeId(String acctypeId) {
		this.acctypeId = acctypeId;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
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

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

}