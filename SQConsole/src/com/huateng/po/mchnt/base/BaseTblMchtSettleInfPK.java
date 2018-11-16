package com.huateng.po.mchnt.base;

import java.io.Serializable;


public abstract class BaseTblMchtSettleInfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String termId;
	private java.lang.String mchtNo;


	public BaseTblMchtSettleInfPK () {}
	
	public BaseTblMchtSettleInfPK (
		java.lang.String termId,
		java.lang.String mchtNo) {

		this.setTermId(termId);
		this.setMchtNo(mchtNo);
	}


	/**
	 * Return the value associated with the column: TERM_ID
	 */
	public java.lang.String getTermId () {
		return termId;
	}

	/**
	 * Set the value related to the column: TERM_ID
	 * @param termId the TERM_ID value
	 */
	public void setTermId (java.lang.String termId) {
		this.termId = termId;
	}



	/**
	 * Return the value associated with the column: MCHT_NO
	 */
	public java.lang.String getMchtNo () {
		return mchtNo;
	}

	/**
	 * Set the value related to the column: MCHT_NO
	 * @param mchtNo the MCHT_NO value
	 */
	public void setMchtNo (java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblMchtSettleInfPK)) return false;
		else {
			com.huateng.po.mchnt.TblMchtSettleInfPK mObj = (com.huateng.po.mchnt.TblMchtSettleInfPK) obj;
			if (null != this.getTermId() && null != mObj.getTermId()) {
				if (!this.getTermId().equals(mObj.getTermId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMchtNo() && null != mObj.getMchtNo()) {
				if (!this.getMchtNo().equals(mObj.getMchtNo())) {
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
			if (null != this.getTermId()) {
				sb.append(this.getTermId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMchtNo()) {
				sb.append(this.getMchtNo().hashCode());
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