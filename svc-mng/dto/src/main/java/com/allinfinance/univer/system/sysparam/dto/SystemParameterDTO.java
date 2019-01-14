package com.allinfinance.univer.system.sysparam.dto;

/**
 * 系统参数dto
 * 
 * @author jianji.dai
 * 
 */
public class SystemParameterDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6060325208943531863L;
	private String parameterCode;

	private String parameterName;

	private String parameterValue;

	private String parameterRole;

	private String parameterComment;

	private String dataState;

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getParameterRole() {
		return parameterRole;
	}

	public void setParameterRole(String parameterRole) {
		this.parameterRole = parameterRole;
	}

	public String getParameterComment() {
		return parameterComment;
	}

	public void setParameterComment(String parameterComment) {
		this.parameterComment = parameterComment;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
}
