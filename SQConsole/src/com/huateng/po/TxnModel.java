package com.huateng.po;

public class TxnModel implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public static String IPS_MERCHT= "";//IPS商户号（6.0商户号）
	public static String TERM_ID = "";//终端号  card_accp_term_id
	public static String INST_DATE = "";//记录产生日期 inst_date
	public static String TXN_TYPE = "";//交易类型 trans_type
	public static String CUP_SSN = "";//银联流水号sys_seq_num
	public static String REFE_NUM = "";//交易参考号retrivl_ref
	public static String ORDER_TRSNUM = "";//商户订单号 ORDER_NO
	
	public static String TXN_STATE = "";//交易状态trans_state
	public static String PAN = "";//卡号 pan
	public static String TRS_AMOUNT = "";//交易金额 amt_trans
	
//	/** full constructor */
//	public TxnModel(String TERM_ID, String INST_DATE, String TXN_TYPE,
//			String CUP_SSN, String REFE_NUM,String ORDER_TRSNUM,
//			String TXN_STATE, String PAN,
//			String TRS_AMOUNT) {
//		this.TERM_ID = TERM_ID;
//		this.INST_DATE = INST_DATE;
//		this.TXN_TYPE = TXN_TYPE;
//		this.CUP_SSN = CUP_SSN;
//		this.REFE_NUM = REFE_NUM;
//		this.ORDER_TRSNUM = ORDER_TRSNUM;
//		this.TXN_STATE = TXN_STATE;
//		this.PAN = PAN;
//		this.TRS_AMOUNT = TRS_AMOUNT;
//		
//	}
	
	public static String getTERM_ID() {
		return TERM_ID;
	}
	public static String getIPS_MERCHT() {
		return IPS_MERCHT;
	}
	public static void setIPS_MERCHT(String iPSMERCHT) {
		IPS_MERCHT = iPSMERCHT;
	}
	public static void setTERM_ID(String tERMID) {
		TERM_ID = tERMID;
	}
	public static String getINST_DATE() {
		return INST_DATE;
	}
	public static void setINST_DATE(String iNSTDATE) {
		INST_DATE = iNSTDATE;
	}
	public static String getTXN_TYPE() {
		return TXN_TYPE;
	}
	public static void setTXN_TYPE(String tXNTYPE) {
		TXN_TYPE = tXNTYPE;
	}
	public static String getCUP_SSN() {
		return CUP_SSN;
	}
	public static void setCUP_SSN(String cUPSSN) {
		CUP_SSN = cUPSSN;
	}
	public static String getREFE_NUM() {
		return REFE_NUM;
	}
	public static void setREFE_NUM(String rEFENUM) {
		REFE_NUM = rEFENUM;
	}
	public static String getORDER_TRSNUM() {
		return ORDER_TRSNUM;
	}
	public static void setORDER_TRSNUM(String oRDERTRSNUM) {
		ORDER_TRSNUM = oRDERTRSNUM;
	}
	public static String getTXN_STATE() {
		return TXN_STATE;
	}
	public static void setTXN_STATE(String tXNSTATE) {
		TXN_STATE = tXNSTATE;
	}
	public static String getPAN() {
		return PAN;
	}
	public static void setPAN(String pAN) {
		PAN = pAN;
	}
	public static String getTRS_AMOUNT() {
		return TRS_AMOUNT;
	}
	public static void setTRS_AMOUNT(String tRSAMOUNT) {
		TRS_AMOUNT = tRSAMOUNT;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
