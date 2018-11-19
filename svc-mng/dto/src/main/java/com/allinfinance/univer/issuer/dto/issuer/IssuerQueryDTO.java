package com.allinfinance.univer.issuer.dto.issuer;

import com.allinfinance.framework.dto.PageQueryDTO;

public class IssuerQueryDTO extends PageQueryDTO {

	/**
	 * @author:sff
	 */
	private static final long serialVersionUID = 1L;
	private String entityId;

	private String issuerName;

	private String issuerCode;

	private String issuerEnglishName;

	private String dataState;
	private String queryEntityId;
	private String queryName;
	private String entityFlag;
    private String fatherEntityId;
	public String getIssuerEnglishName() {
		return issuerEnglishName;
	}

	public void setIssuerEnglishName(String issuerEnglishName) {
		if(issuerEnglishName.equals(""))issuerEnglishName=null;
		this.issuerEnglishName = issuerEnglishName;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		if(dataState.equals(""))dataState=null;
		this.dataState = dataState;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		if(entityId.equals(""))entityId=null;
		this.entityId = entityId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		if(issuerName.equals(""))issuerName=null;
		this.issuerName = issuerName;
	}

	public String getIssuerCode() {
		return issuerCode;
	}

	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
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
    
}
