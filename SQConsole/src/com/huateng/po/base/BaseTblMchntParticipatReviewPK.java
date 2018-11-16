package com.huateng.po.base;

import java.io.Serializable;

public abstract class BaseTblMchntParticipatReviewPK implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String actNo;
	private java.lang.String mchntNo;


	public BaseTblMchntParticipatReviewPK () {}
	
	public BaseTblMchntParticipatReviewPK (
		java.lang.String actNo,
		java.lang.String mchntNo) {

		this.setActNo(actNo);
		this.setMchntNo(mchntNo);
	}

	/**
	 * Return the value associated with the column: ACT_NO
	 */
	public java.lang.String getActNo () {
		return actNo;
	}

	/**
	 * Set the value related to the column: ACT_NO
	 * @param actNo the ACT_NO value
	 */
	public void setActNo (java.lang.String actNo) {
		this.actNo = actNo;
	}

	/**
	 * Return the value associated with the column: MCHNT_NO
	 */
	public java.lang.String getMchntNo () {
		return mchntNo;
	}

	/**
	 * Set the value related to the column: MCHNT_NO
	 * @param mchntNo the MCHNT_NO value
	 */
	public void setMchntNo (java.lang.String mchntNo) {
		this.mchntNo = mchntNo;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblMchntParticipatReviewPK)) return false;
		else {
			com.huateng.po.mchtSrv.TblMchntParticipatReviewPK mObj = (com.huateng.po.mchtSrv.TblMchntParticipatReviewPK) obj;
			if (null != this.getActNo() && null != mObj.getActNo()) {
				if (!this.getActNo().equals(mObj.getActNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMchntNo() && null != mObj.getMchntNo()) {
				if (!this.getMchntNo().equals(mObj.getMchntNo())) {
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
			if (null != this.getActNo()) {
				sb.append(this.getActNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMchntNo()) {
				sb.append(this.getMchntNo().hashCode());
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