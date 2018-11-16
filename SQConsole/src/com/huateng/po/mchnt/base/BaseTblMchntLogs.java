package com.huateng.po.mchnt.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_MCHNT_LOGS table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MCHNT_LOGS"
 */

public abstract class BaseTblMchntLogs  implements Serializable {

	public static String REF = "TblMchntLogs";
	public static String PROP_UPD_OPR_ID = "updOprId";
	public static String PROP_OPR_ID = "oprId";
	public static String PROP_OPR_STATUS = "oprStatus";
	public static String PROP_REC_UPD_TS = "recUpdTs";
	public static String PROP_OPR_TYPE = "oprType";
	public static String PROP_RESERVED1 = "reserved1";
	public static String PROP_REC_CRT_TS = "recCrtTs";
	public static String PROP_RESERVED3 = "reserved3";
	public static String PROP_RESERVED2 = "reserved2";
	public static String PROP_MCHNT_ID = "mchntId";
	public static String PROP_RESERVED4 = "reserved4";
	public static String PROP_CRT_OPR_ID = "crtOprId";
	public static String PROP_ID = "id";
	public static String PROP_OPR_INFO = "oprInfo";


	// constructors
	public BaseTblMchntLogs () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchntLogs (com.huateng.po.mchnt.TblMchntLogsPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.mchnt.TblMchntLogsPK id;

	// fields
	private java.lang.String mchntId;
	private java.lang.String oprId;
	private java.lang.String oprType;
	private java.lang.String oprStatus;
	private java.lang.String oprInfo;
	private java.lang.String reserved1;
	private java.lang.String reserved2;
	private java.lang.String reserved3;
	private java.lang.String reserved4;
	private java.lang.String crtOprId;
	private java.lang.String updOprId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.mchnt.TblMchntLogsPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.mchnt.TblMchntLogsPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: MCHNT_ID
	 */
	public java.lang.String getMchntId () {
		return mchntId;
	}

	/**
	 * Set the value related to the column: MCHNT_ID
	 * @param mchntId the MCHNT_ID value
	 */
	public void setMchntId (java.lang.String mchntId) {
		this.mchntId = mchntId;
	}



	/**
	 * Return the value associated with the column: OPR_ID
	 */
	public java.lang.String getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: OPR_ID
	 * @param oprId the OPR_ID value
	 */
	public void setOprId (java.lang.String oprId) {
		this.oprId = oprId;
	}



	/**
	 * Return the value associated with the column: OPR_TYPE
	 */
	public java.lang.String getOprType () {
		return oprType;
	}

	/**
	 * Set the value related to the column: OPR_TYPE
	 * @param oprType the OPR_TYPE value
	 */
	public void setOprType (java.lang.String oprType) {
		this.oprType = oprType;
	}



	/**
	 * Return the value associated with the column: OPR_STATUS
	 */
	public java.lang.String getOprStatus () {
		return oprStatus;
	}

	/**
	 * Set the value related to the column: OPR_STATUS
	 * @param oprStatus the OPR_STATUS value
	 */
	public void setOprStatus (java.lang.String oprStatus) {
		this.oprStatus = oprStatus;
	}



	/**
	 * Return the value associated with the column: OPR_INFO
	 */
	public java.lang.String getOprInfo () {
		return oprInfo;
	}

	/**
	 * Set the value related to the column: OPR_INFO
	 * @param oprInfo the OPR_INFO value
	 */
	public void setOprInfo (java.lang.String oprInfo) {
		this.oprInfo = oprInfo;
	}



	/**
	 * Return the value associated with the column: RESERVED1
	 */
	public java.lang.String getReserved1 () {
		return reserved1;
	}

	/**
	 * Set the value related to the column: RESERVED1
	 * @param reserved1 the RESERVED1 value
	 */
	public void setReserved1 (java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}



	/**
	 * Return the value associated with the column: RESERVED2
	 */
	public java.lang.String getReserved2 () {
		return reserved2;
	}

	/**
	 * Set the value related to the column: RESERVED2
	 * @param reserved2 the RESERVED2 value
	 */
	public void setReserved2 (java.lang.String reserved2) {
		this.reserved2 = reserved2;
	}



	/**
	 * Return the value associated with the column: RESERVED3
	 */
	public java.lang.String getReserved3 () {
		return reserved3;
	}

	/**
	 * Set the value related to the column: RESERVED3
	 * @param reserved3 the RESERVED3 value
	 */
	public void setReserved3 (java.lang.String reserved3) {
		this.reserved3 = reserved3;
	}



	/**
	 * Return the value associated with the column: RESERVED4
	 */
	public java.lang.String getReserved4 () {
		return reserved4;
	}

	/**
	 * Set the value related to the column: RESERVED4
	 * @param reserved4 the RESERVED4 value
	 */
	public void setReserved4 (java.lang.String reserved4) {
		this.reserved4 = reserved4;
	}



	/**
	 * Return the value associated with the column: CRT_OPR_ID
	 */
	public java.lang.String getCrtOprId () {
		return crtOprId;
	}

	/**
	 * Set the value related to the column: CRT_OPR_ID
	 * @param crtOprId the CRT_OPR_ID value
	 */
	public void setCrtOprId (java.lang.String crtOprId) {
		this.crtOprId = crtOprId;
	}



	/**
	 * Return the value associated with the column: UPD_OPR_ID
	 */
	public java.lang.String getUpdOprId () {
		return updOprId;
	}

	/**
	 * Set the value related to the column: UPD_OPR_ID
	 * @param updOprId the UPD_OPR_ID value
	 */
	public void setUpdOprId (java.lang.String updOprId) {
		this.updOprId = updOprId;
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
		if (!(obj instanceof com.huateng.po.mchnt.TblMchntLogs)) return false;
		else {
			com.huateng.po.mchnt.TblMchntLogs tblMchntLogs = (com.huateng.po.mchnt.TblMchntLogs) obj;
			if (null == this.getId() || null == tblMchntLogs.getId()) return false;
			else return (this.getId().equals(tblMchntLogs.getId()));
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