package com.huateng.po.mchnt.base;

import java.io.Serializable;


public abstract class BaseTblMchntLogsPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String txnTime;
	private java.lang.String mchntInd;


	public BaseTblMchntLogsPK () {}
	
	public BaseTblMchntLogsPK (
		java.lang.String txnTime,
		java.lang.String mchntInd) {

		this.setTxnTime(txnTime);
		this.setMchntInd(mchntInd);
	}


	/**
	 * Return the value associated with the column: TXN_TIME
	 */
	public java.lang.String getTxnTime () {
		return txnTime;
	}

	/**
	 * Set the value related to the column: TXN_TIME
	 * @param txnTime the TXN_TIME value
	 */
	public void setTxnTime (java.lang.String txnTime) {
		this.txnTime = txnTime;
	}



	/**
	 * Return the value associated with the column: MCHNT_IND
	 */
	public java.lang.String getMchntInd () {
		return mchntInd;
	}

	/**
	 * Set the value related to the column: MCHNT_IND
	 * @param mchntInd the MCHNT_IND value
	 */
	public void setMchntInd (java.lang.String mchntInd) {
		this.mchntInd = mchntInd;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblMchntLogsPK)) return false;
		else {
			com.huateng.po.mchnt.TblMchntLogsPK mObj = (com.huateng.po.mchnt.TblMchntLogsPK) obj;
			if (null != this.getTxnTime() && null != mObj.getTxnTime()) {
				if (!this.getTxnTime().equals(mObj.getTxnTime())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMchntInd() && null != mObj.getMchntInd()) {
				if (!this.getMchntInd().equals(mObj.getMchntInd())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getTxnTime()) {
				sb.append(this.getTxnTime().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMchntInd()) {
				sb.append(this.getMchntInd().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}