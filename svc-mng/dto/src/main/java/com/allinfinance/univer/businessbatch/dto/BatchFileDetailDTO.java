package com.allinfinance.univer.businessbatch.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class BatchFileDetailDTO extends PageQueryDTO {

	public static final String TXN_STATE_INIT = "0";
	public static final String TXN_STATE_SUCCESS = "1";
	public static final String TXN_STATE_FAIL = "2";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;
	// 批量文件类型
	// 01-激活
	// 02-充值
	// 03-制卡

	private String batchType;
	// 批次号s
	private String batchNO;
	// 结算日期
	private String smltDate;
	// 交易流水号
	private String txnSeq;
	// 交易金额
	private String txnAmt = "0";
	// 批量状态
	private String batchState;
	//交易状态 
	private String txnState;
	//卡号
	private String cardNo;
	//账户号
	private String accType;
	//有效期
	private String expDate;
	//激活标志
	private String actFlag;
	//创建日期
	private String recCrtTs;
	//修改日期
	private String recUpdTs;
	
	//回收标志
	private String callBack;
	
	
	
	
	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public String getBatchNO() {
		return batchNO;
	}

	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}

	public String getSmltDate() {
		return smltDate;
	}

	public void setSmltDate(String smltDate) {
		this.smltDate = smltDate;
	}

	public String getTxnSeq() {
		return txnSeq;
	}

	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getActFlag() {
		return actFlag;
	}

	public void setActFlag(String actFlag) {
		this.actFlag = actFlag;
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

	public String getBatchState() {
		return batchState;
	}

	public void setBatchState(String batchState) {
		this.batchState = batchState;
	}
	
}
