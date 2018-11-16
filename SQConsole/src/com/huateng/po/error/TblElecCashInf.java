package com.huateng.po.error;

import java.io.Serializable;

public class TblElecCashInf implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;			//主键
	private String mchtCd;		//商户号
	private String termId;		//终端号
	private String acqInsIdCd;	//受理行标识
	private String brhInsIdCd;	//发卡行标识
	private String pan;			//卡号
	private String txnNum;		//交易类型
	private String txnBatchNo;	//交易批次号
	private String txnCerNo;	//交易凭证号
	private String transAmt;	//交易金额
	private String transDate;	//交易日期
	private String dateSettlmt;	//清算日期
	private String settlmtAmt;	//清算金额
	private String settlDate;	//结算日期
	private String stlmFlg;		//清分状态
	private String feeCredit;	//应收手续费
	private String feeDebit;	//应付手续费
	private String icCert;		//IC卡证书
	private String tvr;			//TVR
	private String tsi;			//tsi
	private String aid;			//aid
	private String atc;			//atc
	private String appTag;		//应用标签
	private String appPreName;	//应用首选名称
	private String saState;		//记录状态
	private String reservedCup1;//预留字段1
	private String reservedCup2;//预留字段2
	private String recCrtUsr;	//创建人
	private String recCrtTs;	//创建时间
	private String recUpdUsr;	//修改人编号
	private String recUpdTs;	//更新时间
	private String recCheUsr;	//审核人编号
	private String recCheTs;	//审核时间
	
	public TblElecCashInf(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getAcqInsIdCd() {
		return acqInsIdCd;
	}

	public void setAcqInsIdCd(String acqInsIdCd) {
		this.acqInsIdCd = acqInsIdCd;
	}

	public String getBrhInsIdCd() {
		return brhInsIdCd;
	}

	public void setBrhInsIdCd(String brhInsIdCd) {
		this.brhInsIdCd = brhInsIdCd;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getTxnBatchNo() {
		return txnBatchNo;
	}

	public void setTxnBatchNo(String txnBatchNo) {
		this.txnBatchNo = txnBatchNo;
	}

	public String getTxnCerNo() {
		return txnCerNo;
	}

	public void setTxnCerNo(String txnCerNo) {
		this.txnCerNo = txnCerNo;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getIcCert() {
		return icCert;
	}

	public void setIcCert(String icCert) {
		this.icCert = icCert;
	}

	public String getTvr() {
		return tvr;
	}

	public void setTvr(String tvr) {
		this.tvr = tvr;
	}

	public String getTsi() {
		return tsi;
	}

	public void setTsi(String tsi) {
		this.tsi = tsi;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	public String getAppTag() {
		return appTag;
	}

	public void setAppTag(String appTag) {
		this.appTag = appTag;
	}

	public String getAppPreName() {
		return appPreName;
	}

	public void setAppPreName(String appPreName) {
		this.appPreName = appPreName;
	}

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getReservedCup1() {
		return reservedCup1;
	}

	public void setReservedCup1(String reservedCup1) {
		this.reservedCup1 = reservedCup1;
	}

	public String getReservedCup2() {
		return reservedCup2;
	}

	public void setReservedCup2(String reservedCup2) {
		this.reservedCup2 = reservedCup2;
	}

	public String getRecCrtUsr() {
		return recCrtUsr;
	}

	public void setRecCrtUsr(String recCrtUsr) {
		this.recCrtUsr = recCrtUsr;
	}

	public String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getRecUpdUsr() {
		return recUpdUsr;
	}

	public void setRecUpdUsr(String recUpdUsr) {
		this.recUpdUsr = recUpdUsr;
	}

	public String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public String getRecCheUsr() {
		return recCheUsr;
	}

	public void setRecCheUsr(String recCheUsr) {
		this.recCheUsr = recCheUsr;
	}

	public String getRecCheTs() {
		return recCheTs;
	}

	public void setRecCheTs(String recCheTs) {
		this.recCheTs = recCheTs;
	}

	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	public String getSettlDate() {
		return settlDate;
	}

	public void setSettlDate(String settlDate) {
		this.settlDate = settlDate;
	}

	public String getSettlmtAmt() {
		return settlmtAmt;
	}

	public void setSettlmtAmt(String settlmtAmt) {
		this.settlmtAmt = settlmtAmt;
	}

	public String getStlmFlg() {
		return stlmFlg;
	}

	public void setStlmFlg(String stlmFlg) {
		this.stlmFlg = stlmFlg;
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
	
}
