package com.huateng.po.error;

import java.io.Serializable;

public class BthCupErrTxnPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dateSettlmt;
	private String txnNum;
	private String txnSsn;
	
	public BthCupErrTxnPK() {
		super();
	}

	public BthCupErrTxnPK(String dateSettlmt,String txnNum,String txnSsn){
		this.dateSettlmt = dateSettlmt;
		this.txnNum = txnNum;
		this.txnSsn = txnSsn;
	}
	
	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getTxnSsn() {
		return txnSsn;
	}

	public void setTxnSsn(String txnSsn) {
		this.txnSsn = txnSsn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateSettlmt == null) ? 0 : dateSettlmt.hashCode());
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
		BthCupErrTxnPK other = (BthCupErrTxnPK) obj;
		if (dateSettlmt == null) {
			if (other.dateSettlmt != null)
				return false;
		} else if (!dateSettlmt.equals(other.dateSettlmt))
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
