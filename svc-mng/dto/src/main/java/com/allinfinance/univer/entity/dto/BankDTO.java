package com.allinfinance.univer.entity.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
//银行信息
public class BankDTO extends BaseDTO {
	//银行ID
	private String bankId;
	//外联ID
	private String entityId;
	//银行名称
	private String bankName;
	//银行账户
	private String bankAccount;
	//银行账户名
	private String bankAccountName;
	//银行账户外部系统号
	private String bankAccountCode;
	//设为默认账户
	private String accountFlag;
	//银行所属类型
	private String bankType;
	private String mchantCode;
	private List<BankDTO> bankDTOs;
	
	public List<BankDTO> getBankDTOs() {
		return bankDTOs;
	}

	public void setBankDTOs(List<BankDTO> bankDTOs) {
		this.bankDTOs = bankDTOs;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}

	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	public String getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getMchantCode() {
		return mchantCode;
	}

	public void setMchantCode(String mchantCode) {
		this.mchantCode = mchantCode;
	}
	

}
