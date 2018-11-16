package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cst_mcht_fee_inf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="cst_mcht_fee_inf"
 */

@SuppressWarnings("serial")
public class CstMchtFeeInfTmp  implements Serializable {

	public static String REF = "CstMchtFeeInfTmp";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_CHANNEL = "Channel";
	public static String PROP_MON_NUM = "MonNum";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_DAY_SINGLE = "DaySingle";
	public static String PROP_DAY_AMT = "DayAmt";
	public static String PROP_MON_AMT = "MonAmt";
	public static String PROP_DAY_NUM = "DayNum";
	public static String PROP_TXN_NUM = "TxnNum";
	
	public static String PROP_CALL_LISTNUM = "callListNum";
	public static String PROP_MON_AVRAMT = "monAvrAmt";
	public static String PROP_DAY_AVRAMT = "dayAvrAmt";
	public static String PROP_AVR_SINGLEAMT = "avrSingleAmt";


	// constructors
	public CstMchtFeeInfTmp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public CstMchtFeeInfTmp (CstMchtFeeInfPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 *//*
	public CstMchtFeeInfTmp (
		CstMchtFeeInfPK id,
		java.lang.String channel,
		java.lang.String dayNum,
		java.lang.Double dayAmt,
		java.lang.Double daySingle,
		java.lang.String monNum,
		java.lang.Double monAmt,
		java.lang.String callListNum,
		java.lang.String monAvrAmt,  //月平均金额
		java.lang.String dayAvrAmt, //日平均金额
		java.lang.String avrSingleAmt  //平均单笔交易金额
		){
		this.setId(id);
		this.setChannel(channel);
		this.setDayNum(dayNum);
		this.setDayAmt(dayAmt);
		this.setDaySingle(daySingle);
		this.setMonNum(monNum);
		this.setMonAmt(monAmt);
		this.setCallListNum(callListNum);
		this.setMonAvrAmt(monAvrAmt);
		this.setDayAvrAmt(dayAvrAmt);
		this.setAvrSingleAmt(avrSingleAmt);
		initialize();
	}*/

	protected void initialize () {}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private CstMchtFeeInfPK id;

	// fields
	private java.lang.String channel;
	private java.lang.String dayNum;
//	private java.lang.Float dayAmt;
//	private java.lang.Float daySingle;
	private java.lang.Double dayAmt;
	private java.lang.Double daySingle;
	private java.lang.String monNum;
//	private java.lang.Float monAmt;
	private java.lang.Double monAmt;
	private java.lang.String reserved;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;
	private String oprID;
	private String saState;
	private String saAction;
	private String callListNum;  //调单次数
	private java.lang.String txnNum;
	private java.lang.String monAvrAmt;  //月平均金额
	private java.lang.String dayAvrAmt;  //日平均金额
	private java.lang.String avrSingleAmt;  //平均单笔交易金额
	private java.lang.String remark;
	

	
	
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getMonAvrAmt() {
		return monAvrAmt;
	}

	public void setMonAvrAmt(java.lang.String monAvrAmt) {
		this.monAvrAmt = monAvrAmt;
	}

	public java.lang.String getDayAvrAmt() {
		return dayAvrAmt;
	}

	public void setDayAvrAmt(java.lang.String dayAvrAmt) {
		this.dayAvrAmt = dayAvrAmt;
	}

	public java.lang.String getAvrSingleAmt() {
		return avrSingleAmt;
	}

	public void setAvrSingleAmt(java.lang.String avrSingleAmt) {
		this.avrSingleAmt = avrSingleAmt;
	}

	public String getSaAction() {
		return saAction;
	}

	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	public String getOprID() {
		return oprID;
	}

	public void setOprID(String oprID) {
		this.oprID = oprID;
	}

 

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public CstMchtFeeInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (CstMchtFeeInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: channel
	 */
	public java.lang.String getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel
	 * @param channel the channel value
	 */
	public void setChannel (java.lang.String channel) {
		this.channel = channel;
	}

	/**
	 * Return the value associated with the column: day_num
	 */
	public java.lang.String getDayNum () {
		return dayNum;
	}

	/**
	 * Set the value related to the column: day_num
	 * @param dayNum the day_num value
	 */
	public void setDayNum (java.lang.String dayNum) {
		this.dayNum = dayNum;
	}

	/**
	 * Return the value associated with the column: day_amt
	 */
	public java.lang.Double getDayAmt () {
		return dayAmt;
	}

	/**
	 * Set the value related to the column: day_amt
	 * @param dayAmt the day_amt value
	 */
	public void setDayAmt (java.lang.Double dayAmt) {
		this.dayAmt = dayAmt;
	}

	/**
	 * Return the value associated with the column: day_single
	 */
	public java.lang.Double getDaySingle () {
		return daySingle;
	}

	/**
	 * Set the value related to the column: day_single
	 * @param daySingle the day_single value
	 */
	public void setDaySingle (java.lang.Double daySingle) {
		this.daySingle = daySingle;
	}

	/**
	 * Return the value associated with the column: mon_num
	 */
	public java.lang.String getMonNum () {
		return monNum;
	}

	/**
	 * Set the value related to the column: mon_num
	 * @param monNum the mon_num value
	 */
	public void setMonNum (java.lang.String monNum) {
		this.monNum = monNum;
	}

	/**
	 * Return the value associated with the column: mon_amt
	 */
	public java.lang.Double getMonAmt () {
		return monAmt;
	}

	/**
	 * Set the value related to the column: mon_amt
	 * @param monAmt the mon_amt value
	 */
	public void setMonAmt (java.lang.Double monAmt) {
		this.monAmt = monAmt;
	}

	/**
	 * Return the value associated with the column: reserved
	 */
	public java.lang.String getReserved () {
		return reserved;
	}

	/**
	 * Set the value related to the column: reserved
	 * @param reserved the reserved value
	 */
	public void setReserved (java.lang.String reserved) {
		this.reserved = reserved;
	}

	/**
	 * Return the value associated with the column: rec_crt_ts
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: rec_crt_ts
	 * @param recCrtTs the rec_crt_ts value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}



	/**
	 * Return the value associated with the column: rec_upd_ts
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: rec_upd_ts
	 * @param recUpdTs the rec_upd_ts value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}




	public String getCallListNum() {
		return callListNum;
	}

	public void setCallListNum(String callListNum) {
		this.callListNum = callListNum;
	}
	
	/**
	 * Return the value associated with the column: txn_num
	 */
	public java.lang.String getTxnNum () {
		return txnNum;
	}

	/**
	 * Set the value related to the column: txn_num
	 * @param txnNum the txn_num value
	 */
	public void setTxnNum (java.lang.String txnNum) {
		this.txnNum = txnNum;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CstMchtFeeInfTmp)) return false;
		else {
			CstMchtFeeInfTmp cstMchtFeeInf = (CstMchtFeeInfTmp) obj;
			if (null == this.getId() || null == cstMchtFeeInf.getId()) return false;
			else return (this.getId().equals(cstMchtFeeInf.getId()));
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