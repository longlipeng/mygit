package com.allinfinance.univer.system.syslog;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 系统日志DTO
 */
public class SystemLogDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 操作时间str
	 */
	private String operationStr;

	private String logId;
	private String txnCode;
	private String txnContent;
	private String successFlag;
	private String operationUser;
	private String operationUserName;
	private String operationTime;
	private String operationMemo;

	public String getOperationStr() {
		return operationStr;
	}

	public void setOperationStr(String operationStr) {
		this.operationStr = operationStr;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	public String getTxnContent() {
		return txnContent;
	}

	public void setTxnContent(String txnContent) {
		this.txnContent = txnContent;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationMemo() {
		return operationMemo;
	}

	public void setOperationMemo(String operationMemo) {
		this.operationMemo = operationMemo;
	}

	public String getOperationUserName() {
		return operationUserName;
	}

	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}

}