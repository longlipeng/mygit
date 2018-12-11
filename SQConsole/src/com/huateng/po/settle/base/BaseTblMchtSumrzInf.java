package com.huateng.po.settle.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_MCHT_SUMRZ_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MCHT_SUMRZ_INF"
 */

public abstract class BaseTblMchtSumrzInf  implements Serializable {

	public static String REF = "TblMchtSumrzInf";
	public static String PROP_REC_CRT_TS = "recCrtTs";
	public static String PROP_SETTLE_ACC_NAME = "settleAccName";
	public static String PROP_REC_UPD_OPR = "recUpdOpr";
	public static String PROP_SUMRZ_NOTE = "sumrzNote";
	public static String PROP_SUMRZ_DATE = "sumrzDate";
	public static String PROP_MCHT_NO = "mchtNo";
	public static String PROP_ACC_FLAG = "accFlag";
	public static String PROP_ID = "id";
	public static String PROP_REC_UPD_TS = "recUpdTs";
	public static String PROP_SETTLE_ACC_NUM = "settleAccNum";
	public static String PROP_SUM_AMT = "sumAmt";
	public static String PROP_BANK_NAME = "bankName";
	public static String PROP_SA_STATUS = "saStatus";
	public static String CAUSE_STAT = "causeStat";

	public static String AUDIT_STATUS = "auditStatus";
	public static String AUDIT_ID = "auditId";
	public static String AUDIT_DATE = "auditDate";
	public static String REC_ID = "recId";
	public static String REC_DATE = "recDate";
	

	// constructors
	public BaseTblMchtSumrzInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchtSumrzInf (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblMchtSumrzInf (
		java.lang.Integer id,
		java.lang.String mchtNo) {

		this.setId(id);
		this.setMchtNo(mchtNo);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String mchtNo;
	private java.lang.String settleAccName;
	private java.lang.String settleAccNum;
	private java.lang.String bankName;
//	private java.lang.Integer sumAmt;
	private java.lang.String causeStat;
	private java.lang.String saStatus;
	private java.lang.String sumrzDate;
	private java.lang.String sumrzNote;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;
	private java.lang.String accFlag;

	private java.lang.String auditStatus;
	private java.lang.String auditId;
	private java.lang.String auditDate;
	private java.lang.String recId;
	private java.lang.String recDate;
	
	private java.lang.String sumrzBatch;


	
	public java.lang.String getSumrzBatch() {
		return sumrzBatch;
	}

	public void setSumrzBatch(java.lang.String sumrzBatch) {
		this.sumrzBatch = sumrzBatch;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public java.lang.String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(java.lang.String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public java.lang.String getAuditId() {
		return auditId;
	}

	public void setAuditId(java.lang.String auditId) {
		this.auditId = auditId;
	}

	public java.lang.String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(java.lang.String auditDate) {
		this.auditDate = auditDate;
	}

	public java.lang.String getRecId() {
		return recId;
	}

	public void setRecId(java.lang.String recId) {
		this.recId = recId;
	}

	public java.lang.String getRecDate() {
		return recDate;
	}

	public void setRecDate(java.lang.String recDate) {
		this.recDate = recDate;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="UUID"
     *  column="SEQ_NUM"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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



	/**
	 * Return the value associated with the column: SETTLE_ACC_NAME
	 */
	public java.lang.String getSettleAccName () {
		return settleAccName;
	}

	/**
	 * Set the value related to the column: SETTLE_ACC_NAME
	 * @param settleAccName the SETTLE_ACC_NAME value
	 */
	public void setSettleAccName (java.lang.String settleAccName) {
		this.settleAccName = settleAccName;
	}



	/**
	 * Return the value associated with the column: SETTLE_ACC_NUM
	 */
	public java.lang.String getSettleAccNum () {
		return settleAccNum;
	}

	/**
	 * Set the value related to the column: SETTLE_ACC_NUM
	 * @param settleAccNum the SETTLE_ACC_NUM value
	 */
	public void setSettleAccNum (java.lang.String settleAccNum) {
		this.settleAccNum = settleAccNum;
	}



	/**
	 * Return the value associated with the column: BANK_NAME
	 */
	public java.lang.String getBankName () {
		return bankName;
	}

	/**
	 * Set the value related to the column: BANK_NAME
	 * @param bankName the BANK_NAME value
	 */
	public void setBankName (java.lang.String bankName) {
		this.bankName = bankName;
	}



	/**
	 * Return the value associated with the column: SUM_AMT
	 *//*
	public java.lang.Integer getSumAmt () {
		return sumAmt;
	}

	*//**
	 * Set the value related to the column: SUM_AMT
	 * @param sumAmt the SUM_AMT value
	 *//*
	public void setSumAmt (java.lang.Integer sumAmt) {
		this.sumAmt = sumAmt;
	}
*/


	/**
	 * Return the value associated with the column: SA_STATUS
	 */
	public java.lang.String getSaStatus () {
		return saStatus;
	}

	public java.lang.String getCauseStat() {
		return causeStat;
	}

	public void setCauseStat(java.lang.String causeStat) {
		this.causeStat = causeStat;
	}

	/**
	 * Set the value related to the column: SA_STATUS
	 * @param saStatus the SA_STATUS value
	 */
	public void setSaStatus (java.lang.String saStatus) {
		this.saStatus = saStatus;
	}



	/**
	 * Return the value associated with the column: SUMRZ_DATE
	 */
	public java.lang.String getSumrzDate () {
		return sumrzDate;
	}

	/**
	 * Set the value related to the column: SUMRZ_DATE
	 * @param sumrzDate the SUMRZ_DATE value
	 */
	public void setSumrzDate (java.lang.String sumrzDate) {
		this.sumrzDate = sumrzDate;
	}



	/**
	 * Return the value associated with the column: SUMRZ_NOTE
	 */
	public java.lang.String getSumrzNote () {
		return sumrzNote;
	}

	/**
	 * Set the value related to the column: SUMRZ_NOTE
	 * @param sumrzNote the SUMRZ_NOTE value
	 */
	public void setSumrzNote (java.lang.String sumrzNote) {
		this.sumrzNote = sumrzNote;
	}



	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * @param recUpdOpr the REC_UPD_OPR value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}



	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}




	public java.lang.String getAccFlag() {
		return accFlag;
	}

	public void setAccFlag(java.lang.String accFlag) {
		this.accFlag = accFlag;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.settle.TblMchtSumrzInf)) return false;
		else {
			com.huateng.po.settle.TblMchtSumrzInf tblMchtSumrzInf = (com.huateng.po.settle.TblMchtSumrzInf) obj;
			if (null == this.getId() || null == tblMchtSumrzInf.getId()) return false;
			else return (this.getId().equals(tblMchtSumrzInf.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}