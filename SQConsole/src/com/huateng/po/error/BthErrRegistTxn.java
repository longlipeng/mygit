package com.huateng.po.error;

import java.io.Serializable;

public class BthErrRegistTxn  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static String REF = "BthErrRegistTxn";
	public static String PROP_MCHNT_NM = "MchntNm";
	public static String PROP_ERR_SEQ_NO = "ErrSqlNo";
	public static String PROP_ERR_DESC = "ErrDesc";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_CD_FLAG = "CdFlag";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_SYS_SEQ_NO = "SysSeqNo";
	public static String PROP_MCHNT_NO = "MchntNo";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_FEE1 = "Fee1";
	public static String PROP_REGIST_OPR = "RegistOpr";
	public static String PROP_FEE3 = "Fee3";
	public static String PROP_FEE2 = "Fee2";
	public static String PROP_REGIST_TIME = "RegistTime";
	public static String PROP_AMT1 = "Amt1";
	public static String PROP_STATUS= "errStatus";
	public static String PROP_ID = "Id";
	public static String PROP_ERR_TYPE = "ErrType";
	public static String PROP_START_TIME = "StartTime";
	public static String PROP_AMT2 = "Amt2";

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BthErrRegistTxn () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BthErrRegistTxn (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BthErrRegistTxn (
		java.lang.String id,
		java.lang.String errSeqNo,
		java.lang.String mchntNo,
		java.lang.String errType,
		java.lang.String amt1,
		java.lang.String errStatus) {

		this.setId(id);
		this.setErrSeqNo(errSeqNo);
		this.setMchntNo(mchntNo);
		this.setErrType(errType);
		this.setAmt1(amt1);
		this.setErrStatus(errStatus);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String mchntNo;
	private java.lang.String errSeqNo;
	/**
	 * @return the errSeqNo
	 */
	public java.lang.String getErrSeqNo() {
		return errSeqNo;
	}

	/**
	 * @param errSeqNo the errSeqNo to set
	 */
	public void setErrSeqNo(java.lang.String errSeqNo) {
		this.errSeqNo = errSeqNo;
	}



	private java.lang.String mchntNm;
	private java.lang.String termId;
	private java.lang.String errType;
	private java.lang.String errDesc;
	private java.lang.String cdFlag;
	private java.lang.String sysSeqNo;
	private java.lang.String amt1;
	private java.lang.String amt2;
	private java.lang.String fee1;
	private java.lang.String fee2;
	private java.lang.String fee3;
	private java.lang.String registOpr;
	private java.lang.String registTime;
	private java.lang.String startTime;
	private java.lang.String reserved;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String errStatus;



	/**
	 * @return the status
	 */
	public java.lang.String getErrStatus() {
		return errStatus;
	}

	/**
	 * @param status the status to set
	 */
	public void setErrStatus(java.lang.String errStatus) {
		this.errStatus = errStatus;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="ERR_SEQ_NO"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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



	/**
	 * Return the value associated with the column: MCHNT_NM
	 */
	public java.lang.String getMchntNm () {
		return mchntNm;
	}

	/**
	 * Set the value related to the column: MCHNT_NM
	 * @param mchntNm the MCHNT_NM value
	 */
	public void setMchntNm (java.lang.String mchntNm) {
		this.mchntNm = mchntNm;
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
	 * Return the value associated with the column: ERR_TYPE
	 */
	public java.lang.String getErrType () {
		return errType;
	}

	/**
	 * Set the value related to the column: ERR_TYPE
	 * @param errType the ERR_TYPE value
	 */
	public void setErrType (java.lang.String errType) {
		this.errType = errType;
	}



	/**
	 * Return the value associated with the column: ERR_DESC
	 */
	public java.lang.String getErrDesc () {
		return errDesc;
	}

	/**
	 * Set the value related to the column: ERR_DESC
	 * @param errDesc the ERR_DESC value
	 */
	public void setErrDesc (java.lang.String errDesc) {
		this.errDesc = errDesc;
	}



	/**
	 * Return the value associated with the column: CD_FLAG
	 */
	public java.lang.String getCdFlag () {
		return cdFlag;
	}

	/**
	 * Set the value related to the column: CD_FLAG
	 * @param cdFlag the CD_FLAG value
	 */
	public void setCdFlag (java.lang.String cdFlag) {
		this.cdFlag = cdFlag;
	}



	/**
	 * Return the value associated with the column: SYS_SEQ_NO
	 */
	public java.lang.String getSysSeqNo () {
		return sysSeqNo;
	}

	/**
	 * Set the value related to the column: SYS_SEQ_NO
	 * @param sysSeqNo the SYS_SEQ_NO value
	 */
	public void setSysSeqNo (java.lang.String sysSeqNo) {
		this.sysSeqNo = sysSeqNo;
	}



	/**
	 * Return the value associated with the column: AMT1
	 */
	public java.lang.String getAmt1 () {
		return amt1;
	}

	/**
	 * Set the value related to the column: AMT1
	 * @param amt1 the AMT1 value
	 */
	public void setAmt1 (java.lang.String amt1) {
		this.amt1 = amt1;
	}



	/**
	 * Return the value associated with the column: AMT2
	 */
	public java.lang.String getAmt2 () {
		return amt2;
	}

	/**
	 * Set the value related to the column: AMT2
	 * @param amt2 the AMT2 value
	 */
	public void setAmt2 (java.lang.String amt2) {
		this.amt2 = amt2;
	}



	/**
	 * Return the value associated with the column: FEE1
	 */
	public java.lang.String getFee1 () {
		return fee1;
	}

	/**
	 * Set the value related to the column: FEE1
	 * @param fee1 the FEE1 value
	 */
	public void setFee1 (java.lang.String fee1) {
		this.fee1 = fee1;
	}



	/**
	 * Return the value associated with the column: FEE2
	 */
	public java.lang.String getFee2 () {
		return fee2;
	}

	/**
	 * Set the value related to the column: FEE2
	 * @param fee2 the FEE2 value
	 */
	public void setFee2 (java.lang.String fee2) {
		this.fee2 = fee2;
	}



	/**
	 * Return the value associated with the column: FEE3
	 */
	public java.lang.String getFee3 () {
		return fee3;
	}

	/**
	 * Set the value related to the column: FEE3
	 * @param fee3 the FEE3 value
	 */
	public void setFee3 (java.lang.String fee3) {
		this.fee3 = fee3;
	}



	/**
	 * Return the value associated with the column: REGIST_OPR
	 */
	public java.lang.String getRegistOpr () {
		return registOpr;
	}

	/**
	 * Set the value related to the column: REGIST_OPR
	 * @param registOpr the REGIST_OPR value
	 */
	public void setRegistOpr (java.lang.String registOpr) {
		this.registOpr = registOpr;
	}



	/**
	 * Return the value associated with the column: REGIST_TIME
	 */
	public java.lang.String getRegistTime () {
		return registTime;
	}

	/**
	 * Set the value related to the column: REGIST_TIME
	 * @param registTime the REGIST_TIME value
	 */
	public void setRegistTime (java.lang.String registTime) {
		this.registTime = registTime;
	}



	/**
	 * Return the value associated with the column: START_TIME
	 */
	public java.lang.String getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: START_TIME
	 * @param startTime the START_TIME value
	 */
	public void setStartTime (java.lang.String startTime) {
		this.startTime = startTime;
	}



	/**
	 * Return the value associated with the column: RESERVED
	 */
	public java.lang.String getReserved () {
		return reserved;
	}

	/**
	 * Set the value related to the column: RESERVED
	 * @param reserved the RESERVED value
	 */
	public void setReserved (java.lang.String reserved) {
		this.reserved = reserved;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.error.BthErrRegistTxn)) return false;
		else {
			com.huateng.po.error.BthErrRegistTxn bthErrRegistTxn = (com.huateng.po.error.BthErrRegistTxn) obj;
			if (null == this.getId() || null == bthErrRegistTxn.getId()) return false;
			else return (this.getId().equals(bthErrRegistTxn.getId()));
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