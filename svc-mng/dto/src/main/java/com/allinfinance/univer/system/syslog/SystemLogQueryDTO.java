package com.allinfinance.univer.system.syslog;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 系统参数dto
 *
 */
public class SystemLogQueryDTO extends PageQueryDTO{
	
	private static final long serialVersionUID = 1L;
	/**
     * 日志Id
     */
	 private String logId;
    /**
     * 交易代码
     */
	 private String txnCode;
    /**
     * 成功标识1代码成功，0代码失败
     */
	 private Short successFlag;
	/**
	 * 交易内容
	 */
	 private String txnContent;
	/**
	 * 操作人
	 */
	 private String operationUser;
	 /**
	  * 操作时间
	  */
	 private Date operationTime;
	 /**
	  * 操作描述
	  */
	 private String operationMemo;
	 /**
	  * 交易名称
	  */
	 private String txnName;
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
	public Short getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(Short successFlag) {
		this.successFlag = successFlag;
	}
	public String getTxnContent() {
		return txnContent;
	}
	public void setTxnContent(String txnContent) {
		this.txnContent = txnContent;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getOperationMemo() {
		return operationMemo;
	}
	public void setOperationMemo(String operationMemo) {
		this.operationMemo = operationMemo;
	}
	public String getTxnName() {
		return txnName;
	}
	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}
	
	

}
