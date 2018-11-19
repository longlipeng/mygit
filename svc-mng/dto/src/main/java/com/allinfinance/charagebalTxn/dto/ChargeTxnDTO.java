package com.allinfinance.charagebalTxn.dto;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageQueryDTO;

public class ChargeTxnDTO  extends PageQueryDTO{
	private String dateSettle;
	private String seqNo;
	private String sysNo;
	private String dateTxn;
	private String timeTxn;
	private String txnType;
	private String cardNo;
	private String chargeAmt;
	private String chargeFee;
	private String chargeStat;
	private String  chargeMisc;
	private String  respCode;
	private String txnStat;
	private String transSeqNo1;
	private String transSeqNo2;
	
	
	private String startDate;
	private String endDate;
	
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDateSettle() {
		return dateSettle;
	}
	public void setDateSettle(String dateSettle) {
		this.dateSettle = dateSettle;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getSysNo() {
		return sysNo;
	}
	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}
	public String getDateTxn() {
		return dateTxn;
	}
	public void setDateTxn(String dateTxn) {
		this.dateTxn = dateTxn;
	}
	public String getTimeTxn() {
		return timeTxn;
	}
	public void setTimeTxn(String timeTxn) {
		this.timeTxn = timeTxn;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(String chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public String getChargeFee() {
		return chargeFee;
	}
	public void setChargeFee(String chargeFee) {
		this.chargeFee = chargeFee;
	}
	public String getChargeStat() {
		return chargeStat;
	}
	public void setChargeStat(String chargeStat) {
		this.chargeStat = chargeStat;
	}
	public String getChargeMisc() {
		return chargeMisc;
	}
	public void setChargeMisc(String chargeMisc) {
		this.chargeMisc = chargeMisc;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getTxnStat() {
		return txnStat;
	}
	public void setTxnStat(String txnStat) {
		this.txnStat = txnStat;
	}
	public String getTransSeqNo1() {
		return transSeqNo1;
	}
	public void setTransSeqNo1(String transSeqNo1) {
		this.transSeqNo1 = transSeqNo1;
	}
	public String getTransSeqNo2() {
		return transSeqNo2;
	}
	public void setTransSeqNo2(String transSeqNo2) {
		this.transSeqNo2 = transSeqNo2;
	}
	
	
	
}
