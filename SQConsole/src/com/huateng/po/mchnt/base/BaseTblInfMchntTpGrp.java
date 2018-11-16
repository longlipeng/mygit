package com.huateng.po.mchnt.base;

import java.io.Serializable;
import com.huateng.po.mchnt.TblInfMchntTpGrp;
import com.huateng.po.mchnt.TblInfMchntTpGrpPK;

/**
 * This is an object that contains data related to the TBL_INF_MCHNT_TP_GRP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_INF_MCHNT_TP_GRP"
 */

@SuppressWarnings("unchecked")
public abstract class BaseTblInfMchntTpGrp  implements Comparable, Serializable {

	private static final long serialVersionUID = 1L;
	public static String REF = "TblInfMchntTpGrp";
	public static String PROP_DESCR = "Descr";
	public static String PROP_REC_ST = "RecSt";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_LAST_OPER_IN = "LastOperIn";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_USR_ID = "RecUpdUsrId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String STATUSID = "statusid";

	// constructors
	public BaseTblInfMchntTpGrp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblInfMchntTpGrp (TblInfMchntTpGrpPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblInfMchntTpGrp (
		TblInfMchntTpGrpPK id,
		java.lang.String descr,
		java.lang.String recSt,
		java.lang.String lastOperIn,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,
		java.lang.String statusid) {

		this.setId(id);
		this.setDescr(descr);
		this.setRecSt(recSt);
		this.setLastOperIn(lastOperIn);
		this.setRecUpdUsrId(recUpdUsrId);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		this.setStatusid(statusid);
		initialize();
	}

	protected void initialize () {}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private TblInfMchntTpGrpPK id;

	// fields
	private java.lang.String descr;
	private java.lang.String recSt;
	private java.lang.String lastOperIn;
	private java.lang.String recUpdUsrId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String statusid;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public TblInfMchntTpGrpPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (TblInfMchntTpGrpPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: DESCR
	 */
	public java.lang.String getDescr () {
		return descr;
	}

	/**
	 * Set the value related to the column: DESCR
	 * @param descr the DESCR value
	 */
	public void setDescr (java.lang.String descr) {
		this.descr = descr;
	}

	/**
	 * Return the value associated with the column: REC_ST
	 */
	public java.lang.String getRecSt () {
		return recSt;
	}

	/**
	 * Set the value related to the column: REC_ST
	 * @param recSt the REC_ST value
	 */
	public void setRecSt (java.lang.String recSt) {
		this.recSt = recSt;
	}

	/**
	 * Return the value associated with the column: LAST_OPER_IN
	 */
	public java.lang.String getLastOperIn () {
		return lastOperIn;
	}

	/**
	 * Set the value related to the column: LAST_OPER_IN
	 * @param lastOperIn the LAST_OPER_IN value
	 */
	public void setLastOperIn (java.lang.String lastOperIn) {
		this.lastOperIn = lastOperIn;
	}

	/**
	 * Return the value associated with the column: REC_UPD_USR_ID
	 */
	public java.lang.String getRecUpdUsrId () {
		return recUpdUsrId;
	}

	/**
	 * Set the value related to the column: REC_UPD_USR_ID
	 * @param recUpdUsrId the REC_UPD_USR_ID value
	 */
	public void setRecUpdUsrId (java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
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

	public java.lang.String getStatusid() {
		return statusid;
	}

	public void setStatusid(java.lang.String statusid) {
		this.statusid = statusid;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblInfMchntTpGrp)) return false;
		else {
			TblInfMchntTpGrp tblInfMchntTpGrp = (TblInfMchntTpGrp) obj;
			if (null == this.getId() || null == tblInfMchntTpGrp.getId()) return false;
			else return (this.getId().equals(tblInfMchntTpGrp.getId()));
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

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}

}