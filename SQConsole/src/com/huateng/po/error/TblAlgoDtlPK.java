package com.huateng.po.error;

import java.io.Serializable;

public class TblAlgoDtlPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dateSettlmt;
	private String txnKey;
	
	public TblAlgoDtlPK(){
		super();
	}

	public TblAlgoDtlPK(String dateSettlmt,String txnKey){
		this.dateSettlmt = dateSettlmt;
		this.txnKey = txnKey;
	}
	
	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	public String getTxnKey() {
		return txnKey;
	}

	public void setTxnKey(String txnKey) {
		this.txnKey = txnKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateSettlmt == null) ? 0 : dateSettlmt.hashCode());
		result = prime * result + ((txnKey == null) ? 0 : txnKey.hashCode());
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
		TblAlgoDtlPK other = (TblAlgoDtlPK) obj;
		if (dateSettlmt == null) {
			if (other.dateSettlmt != null)
				return false;
		} else if (!dateSettlmt.equals(other.dateSettlmt))
			return false;
		if (txnKey == null) {
			if (other.txnKey != null)
				return false;
		} else if (!txnKey.equals(other.txnKey))
			return false;
		return true;
	}
	
}
