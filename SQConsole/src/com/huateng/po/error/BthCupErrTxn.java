package com.huateng.po.error;

import java.io.Serializable;

public class BthCupErrTxn implements Serializable {

	private static final long serialVersionUID = 1L;

	//Key:dateSettlmt,txnNum,txnSsn
	private BthCupErrTxnPK id;
	
	private String errTrigFlag;
	private String errFlag;
	private String stlmFlg;
	private String transDateTime;
	private String authorRspCd;
	private String pan;
	private String amtTrans;
	private String txnFeeC;
	private String txnFeeD;
	private String acqInsIdCd;
	private String rcvInsIdCd;
	private String issInsIdCd;
	private String posEntryMode;
	private String mchtId;
	private String ipsNo;
	private String termId;
	private String mchtTp;
	private String mchtAccInType;
	private String respCode;
	private String feeCredit;
	private String feeDebit;
	private String amtTransFee;
	private String fwdInsIdCd;
	private String msgType;
	private String proccessingCode;
	private String posCondCode;
	private String xferFee;
	private String xferOutInsCd;
	private String xferOutPan;
	private String xferInInsCd;
	private String xferInPan;
	private String cardSeqNum;
	private String termCap;
	private String chipCond;
	private String domesticFlg;
	private String eciFlg;
	private String orgTransAmt;
	private String orgRetrivlRef;
	private String orgTxnSsn;
	private String orgTermSsn;
	private String orgOrderNo;
	private String orgDateTime;
	private String orgHostTxnSsn;
	private String orgStlmDate;
	private String errCode;
	private String lstUpdTlr;
	private String createTime;
	private String lstUpdTime;
	
	public BthCupErrTxnPK getId() {
		return id;
	}
	public void setId(BthCupErrTxnPK id) {
		this.id = id;
	}
	public String getErrTrigFlag() {
		return errTrigFlag;
	}
	public void setErrTrigFlag(String errTrigFlag) {
		this.errTrigFlag = errTrigFlag;
	}
	public String getErrFlag() {
		return errFlag;
	}
	public void setErrFlag(String errFlag) {
		this.errFlag = errFlag;
	}
	public String getStlmFlg() {
		return stlmFlg;
	}
	public void setStlmFlg(String stlmFlg) {
		this.stlmFlg = stlmFlg;
	}
	public String getTransDateTime() {
		return transDateTime;
	}
	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}
	public String getAuthorRspCd() {
		return authorRspCd;
	}
	public void setAuthorRspCd(String authorRspCd) {
		this.authorRspCd = authorRspCd;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAmtTrans() {
		return amtTrans;
	}
	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}
	public String getTxnFeeC() {
		return txnFeeC;
	}
	public void setTxnFeeC(String txnFeeC) {
		this.txnFeeC = txnFeeC;
	}
	public String getTxnFeeD() {
		return txnFeeD;
	}
	public void setTxnFeeD(String txnFeeD) {
		this.txnFeeD = txnFeeD;
	}
	public String getAcqInsIdCd() {
		return acqInsIdCd;
	}
	public void setAcqInsIdCd(String acqInsIdCd) {
		this.acqInsIdCd = acqInsIdCd;
	}
	public String getRcvInsIdCd() {
		return rcvInsIdCd;
	}
	public void setRcvInsIdCd(String rcvInsIdCd) {
		this.rcvInsIdCd = rcvInsIdCd;
	}
	public String getIssInsIdCd() {
		return issInsIdCd;
	}
	public void setIssInsIdCd(String issInsIdCd) {
		this.issInsIdCd = issInsIdCd;
	}
	public String getPosEntryMode() {
		return posEntryMode;
	}
	public void setPosEntryMode(String posEntryMode) {
		this.posEntryMode = posEntryMode;
	}
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getIpsNo() {
		return ipsNo;
	}
	public void setIpsNo(String ipsNo) {
		this.ipsNo = ipsNo;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getMchtTp() {
		return mchtTp;
	}
	public void setMchtTp(String mchtTp) {
		this.mchtTp = mchtTp;
	}
	public String getMchtAccInType() {
		return mchtAccInType;
	}
	public void setMchtAccInType(String mchtAccInType) {
		this.mchtAccInType = mchtAccInType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getFeeCredit() {
		return feeCredit;
	}
	public void setFeeCredit(String feeCredit) {
		this.feeCredit = feeCredit;
	}
	public String getFeeDebit() {
		return feeDebit;
	}
	public void setFeeDebit(String feeDebit) {
		this.feeDebit = feeDebit;
	}
	public String getAmtTransFee() {
		return amtTransFee;
	}
	public void setAmtTransFee(String amtTransFee) {
		this.amtTransFee = amtTransFee;
	}
	public String getFwdInsIdCd() {
		return fwdInsIdCd;
	}
	public void setFwdInsIdCd(String fwdInsIdCd) {
		this.fwdInsIdCd = fwdInsIdCd;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getProccessingCode() {
		return proccessingCode;
	}
	public void setProccessingCode(String proccessingCode) {
		this.proccessingCode = proccessingCode;
	}
	public String getPosCondCode() {
		return posCondCode;
	}
	public void setPosCondCode(String posCondCode) {
		this.posCondCode = posCondCode;
	}
	public String getXferFee() {
		return xferFee;
	}
	public void setXferFee(String xferFee) {
		this.xferFee = xferFee;
	}
	public String getXferOutInsCd() {
		return xferOutInsCd;
	}
	public void setXferOutInsCd(String xferOutInsCd) {
		this.xferOutInsCd = xferOutInsCd;
	}
	public String getXferOutPan() {
		return xferOutPan;
	}
	public void setXferOutPan(String xferOutPan) {
		this.xferOutPan = xferOutPan;
	}
	public String getXferInInsCd() {
		return xferInInsCd;
	}
	public void setXferInInsCd(String xferInInsCd) {
		this.xferInInsCd = xferInInsCd;
	}
	public String getXferInPan() {
		return xferInPan;
	}
	public void setXferInPan(String xferInPan) {
		this.xferInPan = xferInPan;
	}
	public String getCardSeqNum() {
		return cardSeqNum;
	}
	public void setCardSeqNum(String cardSeqNum) {
		this.cardSeqNum = cardSeqNum;
	}
	public String getTermCap() {
		return termCap;
	}
	public void setTermCap(String termCap) {
		this.termCap = termCap;
	}
	public String getChipCond() {
		return chipCond;
	}
	public void setChipCond(String chipCond) {
		this.chipCond = chipCond;
	}
	public String getDomesticFlg() {
		return domesticFlg;
	}
	public void setDomesticFlg(String domesticFlg) {
		this.domesticFlg = domesticFlg;
	}
	public String getEciFlg() {
		return eciFlg;
	}
	public void setEciFlg(String eciFlg) {
		this.eciFlg = eciFlg;
	}
	public String getOrgTransAmt() {
		return orgTransAmt;
	}
	public void setOrgTransAmt(String orgTransAmt) {
		this.orgTransAmt = orgTransAmt;
	}
	public String getOrgRetrivlRef() {
		return orgRetrivlRef;
	}
	public void setOrgRetrivlRef(String orgRetrivlRef) {
		this.orgRetrivlRef = orgRetrivlRef;
	}
	public String getOrgTxnSsn() {
		return orgTxnSsn;
	}
	public void setOrgTxnSsn(String orgTxnSsn) {
		this.orgTxnSsn = orgTxnSsn;
	}
	public String getOrgTermSsn() {
		return orgTermSsn;
	}
	public void setOrgTermSsn(String orgTermSsn) {
		this.orgTermSsn = orgTermSsn;
	}
	public String getOrgOrderNo() {
		return orgOrderNo;
	}
	public void setOrgOrderNo(String orgOrderNo) {
		this.orgOrderNo = orgOrderNo;
	}
	public String getOrgDateTime() {
		return orgDateTime;
	}
	public void setOrgDateTime(String orgDateTime) {
		this.orgDateTime = orgDateTime;
	}
	public String getOrgHostTxnSsn() {
		return orgHostTxnSsn;
	}
	public void setOrgHostTxnSsn(String orgHostTxnSsn) {
		this.orgHostTxnSsn = orgHostTxnSsn;
	}
	public String getOrgStlmDate() {
		return orgStlmDate;
	}
	public void setOrgStlmDate(String orgStlmDate) {
		this.orgStlmDate = orgStlmDate;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getLstUpdTlr() {
		return lstUpdTlr;
	}
	public void setLstUpdTlr(String lstUpdTlr) {
		this.lstUpdTlr = lstUpdTlr;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLstUpdTime() {
		return lstUpdTime;
	}
	public void setLstUpdTime(String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}
	
}
