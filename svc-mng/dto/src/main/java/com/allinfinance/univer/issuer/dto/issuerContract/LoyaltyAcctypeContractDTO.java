package com.allinfinance.univer.issuer.dto.issuerContract;

import com.allinfinance.framework.dto.BaseDTO;

public class LoyaltyAcctypeContractDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String loyaltyContractId;

	private String ruleNo;

	private String productId;

	private String acctypeId;

	private String txnNum;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoyaltyContractId() {
		return loyaltyContractId;
	}

	public void setLoyaltyContractId(String loyaltyContractId) {
		this.loyaltyContractId = loyaltyContractId;
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

}
