package com.huateng.po.error;

import java.io.Serializable;

public class TblAlgoDtl implements Serializable {

	private static final long serialVersionUID = 1L;

	//Key:dateSettlmt,txnKey
	private TblAlgoDtlPK id;
	
	private String transDate;
	private String transDateTime;
	private String txnSsn;
	private String termSsn;
	private String pan;
	private String transAmt;
	private String settlAmt;
	private String settlDate;
	private String mchtCd;
	private String termId;
	private String txnNum;
	private String feeDOut;
	private String feeCOut;
	private String tOFlag;
	private String orgTxnNum;
	private String stlmFlg;
	private String misc1;//前12位保存系统跟踪号
	private String ipsNo;//IPS 商户号
	private String stlmInsIdCd;	//受理机构
	
	
	public TblAlgoDtl(){
		
	}
	public TblAlgoDtl(TblAlgoDtlPK id){
		this.id = id;
	}
	public TblAlgoDtl(TblAlgoDtlPK id,String transDate,String transDateTime,String txnSsn,String termSsn,
			String pan,String transAmt,String settlAmt,String settlDate,String mchtCd,String termId,
			String txnNum,String feeDOut,String feeCOut,String tOFlag,String orgTxnNum,String stlmFlg,
			String misc1,String ipsNo,String stlmInsIdCd){
		this.id = id;
		this.transDate = transDate;
		this.transDateTime = transDateTime;
		this.txnSsn = txnSsn;
		this.termSsn = termSsn;
		this.pan = pan;
		this.transAmt = transAmt;
		this.settlAmt = settlAmt;
		this.settlDate = settlDate;
		this.mchtCd = mchtCd;
		this.termId = termId;
		this.txnNum = txnNum;
		this.feeDOut = feeDOut;
		this.feeCOut = feeCOut;
		this.tOFlag = tOFlag;
		this.orgTxnNum = orgTxnNum;
		this.stlmFlg = stlmFlg;
		this.misc1 = misc1;
		this.ipsNo=ipsNo;
		this.stlmInsIdCd=stlmInsIdCd;
	}
	
	
	public String getIpsNo() {
		return ipsNo;
	}
	public void setIpsNo(String ipsNo) {
		this.ipsNo = ipsNo;
	}
	public TblAlgoDtlPK getId() {
		return id;
	}
	public void setId(TblAlgoDtlPK id) {
		this.id = id;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransDateTime() {
		return transDateTime;
	}
	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}
	public String getTxnSsn() {
		return txnSsn;
	}
	public void setTxnSsn(String txnSsn) {
		this.txnSsn = txnSsn;
	}
	public String getTermSsn() {
		return termSsn;
	}
	public void setTermSsn(String termSsn) {
		this.termSsn = termSsn;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getSettlAmt() {
		return settlAmt;
	}
	public void setSettlAmt(String settlAmt) {
		this.settlAmt = settlAmt;
	}
	public String getSettlDate() {
		return settlDate;
	}
	public void setSettlDate(String settlDate) {
		this.settlDate = settlDate;
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
	public String getTxnNum() {
		return txnNum;
	}
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}
	public String getFeeDOut() {
		return feeDOut;
	}
	public void setFeeDOut(String feeDOut) {
		this.feeDOut = feeDOut;
	}
	public String getFeeCOut() {
		return feeCOut;
	}
	public void setFeeCOut(String feeCOut) {
		this.feeCOut = feeCOut;
	}
	public String gettOFlag() {
		return tOFlag;
	}
	public void settOFlag(String tOFlag) {
		this.tOFlag = tOFlag;
	}
	public String getOrgTxnNum() {
		return orgTxnNum;
	}
	public void setOrgTxnNum(String orgTxnNum) {
		this.orgTxnNum = orgTxnNum;
	}
	public String getStlmFlg() {
		return stlmFlg;
	}
	public void setStlmFlg(String stlmFlg) {
		this.stlmFlg = stlmFlg;
	}
	
	public String getMisc1() {
		return misc1;
	}
	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}
	
	public String getStlmInsIdCd() {
		return stlmInsIdCd;
	}
	public void setStlmInsIdCd(String stlmInsIdCd) {
		this.stlmInsIdCd = stlmInsIdCd;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feeCOut == null) ? 0 : feeCOut.hashCode());
		result = prime * result + ((feeDOut == null) ? 0 : feeDOut.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mchtCd == null) ? 0 : mchtCd.hashCode());
		result = prime * result
				+ ((orgTxnNum == null) ? 0 : orgTxnNum.hashCode());
		result = prime * result + ((pan == null) ? 0 : pan.hashCode());
		result = prime * result
				+ ((settlAmt == null) ? 0 : settlAmt.hashCode());
		result = prime * result
				+ ((settlDate == null) ? 0 : settlDate.hashCode());
		result = prime * result + ((stlmFlg == null) ? 0 : stlmFlg.hashCode());
		result = prime * result + ((tOFlag == null) ? 0 : tOFlag.hashCode());
		result = prime * result + ((termId == null) ? 0 : termId.hashCode());
		result = prime * result + ((termSsn == null) ? 0 : termSsn.hashCode());
		result = prime * result
				+ ((transAmt == null) ? 0 : transAmt.hashCode());
		result = prime * result
				+ ((transDate == null) ? 0 : transDate.hashCode());
		result = prime * result
				+ ((transDateTime == null) ? 0 : transDateTime.hashCode());
		result = prime * result + ((txnNum == null) ? 0 : txnNum.hashCode());
		result = prime * result + ((txnSsn == null) ? 0 : txnSsn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblAlgoDtl other = (TblAlgoDtl) obj;
		if (feeCOut == null) {
			if (other.feeCOut != null)
				return false;
		} else if (!feeCOut.equals(other.feeCOut))
			return false;
		if (feeDOut == null) {
			if (other.feeDOut != null)
				return false;
		} else if (!feeDOut.equals(other.feeDOut))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mchtCd == null) {
			if (other.mchtCd != null)
				return false;
		} else if (!mchtCd.equals(other.mchtCd))
			return false;
		if (orgTxnNum == null) {
			if (other.orgTxnNum != null)
				return false;
		} else if (!orgTxnNum.equals(other.orgTxnNum))
			return false;
		if (pan == null) {
			if (other.pan != null)
				return false;
		} else if (!pan.equals(other.pan))
			return false;
		if (settlAmt == null) {
			if (other.settlAmt != null)
				return false;
		} else if (!settlAmt.equals(other.settlAmt))
			return false;
		if (settlDate == null) {
			if (other.settlDate != null)
				return false;
		} else if (!settlDate.equals(other.settlDate))
			return false;
		if (stlmFlg == null) {
			if (other.stlmFlg != null)
				return false;
		} else if (!stlmFlg.equals(other.stlmFlg))
			return false;
		if (tOFlag == null) {
			if (other.tOFlag != null)
				return false;
		} else if (!tOFlag.equals(other.tOFlag))
			return false;
		if (termId == null) {
			if (other.termId != null)
				return false;
		} else if (!termId.equals(other.termId))
			return false;
		if (termSsn == null) {
			if (other.termSsn != null)
				return false;
		} else if (!termSsn.equals(other.termSsn))
			return false;
		if (transAmt == null) {
			if (other.transAmt != null)
				return false;
		} else if (!transAmt.equals(other.transAmt))
			return false;
		if (transDate == null) {
			if (other.transDate != null)
				return false;
		} else if (!transDate.equals(other.transDate))
			return false;
		if (transDateTime == null) {
			if (other.transDateTime != null)
				return false;
		} else if (!transDateTime.equals(other.transDateTime))
			return false;
		if (txnNum == null) {
			if (other.txnNum != null)
				return false;
		} else if (!txnNum.equals(other.txnNum))
			return false;
		if (txnSsn == null) {
			if (other.txnSsn != null)
				return false;
		} else if (!txnSsn.equals(other.txnSsn))
			return false;
		return true;
	}
	
}
