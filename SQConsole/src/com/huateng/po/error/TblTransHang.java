package com.huateng.po.error;

import java.io.Serializable;

public class TblTransHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TblTransHangPK getId() {
		return id;
	}
	public void setId(TblTransHangPK id) {
		this.id = id;
	}
	private TblTransHangPK id;
	private String transdate;
	private String hangflag;
	private String hangdate;
	private String relievedate;
	private String remark;
	private String oprid;
	private String termid;
	private String transTime;
	private String transAmt;
	private String txnNum;
	
	
	
	/**
	 * @return the txnNum
	 */
	public String getTxnNum() {
		return txnNum;
	}
	/**
	 * @param txnNum the txnNum to set
	 */
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}
	/**
	 * @return the transAmt
	 */
	public String getTransAmt() {
		return transAmt;
	}
	/**
	 * @param transAmt the transAmt to set
	 */
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	/**
	 * @return the termid
	 */
	public String getTermid() {
		return termid;
	}
	/**
	 * @param termid the termid to set
	 */
	public void setTermid(String termid) {
		this.termid = termid;
	}
	/**
	 * @return the transTime
	 */
	public String getTransTime() {
		return transTime;
	}
	/**
	 * @param transTime the transTime to set
	 */
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getMchtno() {
		return mchtno;
	}
	public void setMchtno(String mchtno) {
		this.mchtno = mchtno;
	}
	private String mchtno;
	
	public String getReliveremark() {
		return reliveremark;
	}
	public void setReliveremark(String reliveremark) {
		this.reliveremark = reliveremark;
	}
	private String reliveremark;
	
	
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getHangflag() {
		return hangflag;
	}
	public void setHangflag(String hangflag) {
		this.hangflag = hangflag;
	}
	public String getHangdate() {
		return hangdate;
	}
	public void setHangdate(String hangdate) {
		this.hangdate = hangdate;
	}
	public String getRelievedate() {
		return relievedate;
	}
	public void setRelievedate(String relievedate) {
		this.relievedate = relievedate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOprid() {
		return oprid;
	}
	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

}
