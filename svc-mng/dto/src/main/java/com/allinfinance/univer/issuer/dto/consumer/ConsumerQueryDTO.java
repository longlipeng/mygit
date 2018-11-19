package com.allinfinance.univer.issuer.dto.consumer;

import com.allinfinance.framework.dto.PageQueryDTO;

public class ConsumerQueryDTO extends PageQueryDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String entityId;

	private String consumerName;

	private String consumerCode;

	private String externalId;

	private String consumerState;

	private String consumerEnglishName;
	 private String queryEntityId;
		private String queryName;
		private String entityFlag;
		private String fatherEntityId;
		private String modifyTime;
		private String bankId;
		private String bankName;
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerCode() {
		return consumerCode;
	}

	public void setConsumerCode(String consumerCode) {
		this.consumerCode = consumerCode;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getConsumerState() {
		return consumerState;
	}

	public void setConsumerState(String consumerState) {
		this.consumerState = consumerState;
	}

	public String getConsumerEnglishName() {
		return consumerEnglishName;
	}

	public void setConsumerEnglishName(String consumerEnglishName) {
		this.consumerEnglishName = consumerEnglishName;
	}

	public String getQueryEntityId() {
		return queryEntityId;
	}

	public void setQueryEntityId(String queryEntityId) {
		this.queryEntityId = queryEntityId;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getEntityFlag() {
		return entityFlag;
	}

	public void setEntityFlag(String entityFlag) {
		this.entityFlag = entityFlag;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
    
}
