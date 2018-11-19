package com.allinfinance.shangqi.gateway.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class AccountConsumDTO extends BaseDTO{
	
	private String cardNo;//卡号
	
	private String phone;//手机号
	
	private String money;//交易金额
	
	private String sysTraceAuditNum;//流水号(posp  6位)
	
	private String traceAuditNum;//流水号(18位)
	
//	private String CardAccptrTermnlId;//终端号
//	
//	private String CardAccptrId;//商户号
	
	private String sPinData;//个人标识码数据
	
//	private String entityId;//机构号
	
	private String chanlTime;//门户的时间
	
//	private String TxnTlr;//操作员号
	
	//食堂充值交易码
	private String operationType;//0,中石油；1：食堂

	private String cardSeqId;//卡片序列号
	
	private String posCondCode;//服务点条件码
	
	private String posEntryModeCode;//服务点输入方式码
	
	private String secRelatdCtrlInfo;//安全控制信息
	
	private String emvVal;//IC卡数据域
	
	private String processingCode;//交易处理码
	
	private String returnCode;//返回码
	
	private String chanlTxnNum;//返回流水
	/*---------------商城退货---------------*/
	private String retrivlRefNum; //原交易的检索参考号
	
	private String sDateLocalTrans;//原交易日期
	
	private String  version;//版本号（上汽用，为了DTO同步）
	
	private String txnRspCode;//退款状态
	
	private String txnReplyMsg;//退款状态原因描述
	
	
	
	
//
//	public String getTxnTlr() {
//		return TxnTlr;
//	}
//
//	public void setTxnTlr(String txnTlr) {
//		TxnTlr = txnTlr;
//	}

	
	
	public String getTxnRspCode() {
		return txnRspCode;
	}



	public void setTxnRspCode(String txnRspCode) {
		this.txnRspCode = txnRspCode;
	}



	public String getTxnReplyMsg() {
		return txnReplyMsg;
	}



	public void setTxnReplyMsg(String txnReplyMsg) {
		this.txnReplyMsg = txnReplyMsg;
	}



	public String getsDateLocalTrans() {
		return sDateLocalTrans;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public void setsDateLocalTrans(String sDateLocalTrans) {
		this.sDateLocalTrans = sDateLocalTrans;
	}



	public String getRetrivlRefNum() {
		return retrivlRefNum;
	}



	public void setRetrivlRefNum(String retrivlRefNum) {
		this.retrivlRefNum = retrivlRefNum;
	}



	public String getChanlTxnNum() {
		return chanlTxnNum;
	}



	public void setChanlTxnNum(String chanlTxnNum) {
		this.chanlTxnNum = chanlTxnNum;
	}



	public String getReturnCode() {
		return returnCode;
	}

	

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getProcessingCode() {
		return processingCode;
	}

	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}

	public String getSecRelatdCtrlInfo() {
		return secRelatdCtrlInfo;
	}

	public void setSecRelatdCtrlInfo(String secRelatdCtrlInfo) {
		this.secRelatdCtrlInfo = secRelatdCtrlInfo;
	}

	public String getEmvVal() {
		return emvVal;
	}

	public void setEmvVal(String emvVal) {
		this.emvVal = emvVal;
	}

	public String getCardSeqId() {
		return cardSeqId;
	}

	public void setCardSeqId(String cardSeqId) {
		this.cardSeqId = cardSeqId;
	}

	public String getPosCondCode() {
		return posCondCode;
	}

	public void setPosCondCode(String posCondCode) {
		this.posCondCode = posCondCode;
	}

	public String getPosEntryModeCode() {
		return posEntryModeCode;
	}

	public void setPosEntryModeCode(String posEntryModeCode) {
		this.posEntryModeCode = posEntryModeCode;
	}

//	public String getCardAccptrTermnlId() {
//		return CardAccptrTermnlId;
//	}
//
//	public void setCardAccptrTermnlId(String cardAccptrTermnlId) {
//		CardAccptrTermnlId = cardAccptrTermnlId;
//	}
//
//	public String getCardAccptrId() {
//		return CardAccptrId;
//	}
//
//	public void setCardAccptrId(String cardAccptrId) {
//		CardAccptrId = cardAccptrId;
//	}

	public String getsPinData() {
		return sPinData;
	}

	public void setsPinData(String sPinData) {
		this.sPinData = sPinData;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getChanlTime() {
		return chanlTime;
	}

	public String getTraceAuditNum() {
		return traceAuditNum;
	}

	public void setTraceAuditNum(String traceAuditNum) {
		this.traceAuditNum = traceAuditNum;
	}

	public void setChanlTime(String chanlTime) {
		this.chanlTime = chanlTime;
	}

//	public String getEntityId() {
//		return entityId;
//	}
//
//	public void setEntityId(String entityId) {
//		this.entityId = entityId;
//	}

	public String getSPinData() {
		return sPinData;
	}

	public void setSPinData(String sPinData) {
		this.sPinData = sPinData;
	}

	public String getSysTraceAuditNum() {
		return sysTraceAuditNum;
	}

	public void setSysTraceAuditNum(String sysTraceAuditNum) {
		this.sysTraceAuditNum = sysTraceAuditNum;
	}

//	public String getCardAccptrTermnlId() {
//		return CardAccptrTermnlId;
//	}
//
//	public void setCardAccptrTermnlId(String cardAccptrTermnlId) {
//		CardAccptrTermnlId = cardAccptrTermnlId;
//	}
//
//	public String getCardAccptrId() {
//		return CardAccptrId;
//	}
//
//	public void setCardAccptrId(String cardAccptrId) {
//		CardAccptrId = cardAccptrId;
//	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	
	

}
