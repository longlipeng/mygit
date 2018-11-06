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
public class RansomBatchDTO {
	private String txnSeq;
	private String txnType;
	private String smltDt;
	private String txnAmt = "0";
	private String txnFee = "0";
	private String txnState;
	private String respCode;
	private String cardNo;
	/*账户明细 后台赋值*/
	private String rsvd1;
	/*卡注销状态*/
	private String cancelState;
	private String recCrtTs;
	private String recUpdTs;
	/*卡注销状态标志*/
	private String cancelFlag;
	
	//回收
	private String callBack;
	
	

	public String getCallBack() {
		return callBack;
	}
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
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
	public String getTxnFee() {
		return txnFee;
	}
	public void setTxnFee(String txnFee) {
		this.txnFee = txnFee;
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
	public String getRsvd1() {
		return rsvd1;
	}
	public void setRsvd1(String rsvd1) {
		this.rsvd1 = rsvd1;
	}
	public String getCancelState() {
		return cancelState;
	}
	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
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

}
