/**
 * Classname RansomBatchDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wanglu		2013-1-4
 * =============================================================================
 */

package com.allinfinance.univer.businessbatch.dto;

/**
 * @author wanglu
 *
 */
public class ActBatchDTO {
	public static final String ACTIVATE_SUCCESS = "00";		//激活成功
	public static final String ACTIVATE_DOING = "01";		//激活中
	public static final String ACTIVATE_FAIL = "02";		//激活失败
	
	private String txnSeq;
	private String txnType;
	private String smltDt;
	private String txnAmt = "0";
	private String txnState;
	private String respCode;
	private String cardNo;
	//激活状态
	private String cardState;
	private String recCrtTs;
	private String recUpdTs;
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getSmltDt() {
		return smltDt;
	}
	public void setSmltDt(String smltDt) {
		this.smltDt = smltDt;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getTxnState() {
		return txnState;
	}
	public void setTxnState(String txnState) {
		this.txnState = txnState;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}
	public String getRecUpdTs() {
		return recUpdTs;
	}
	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}
	public String getCardState() {
		return cardState;
	}
	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

}
