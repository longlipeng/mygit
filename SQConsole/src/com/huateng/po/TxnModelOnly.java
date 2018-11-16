package com.huateng.po;

public class TxnModelOnly implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public static String CARD_ACCP_ID="";//受卡方标识码(商户号)
	public static String TERM_ID="";//终端号
	public static String INST_DATE="";//记录产生日期
	public static String UPDT_DATE="";//记录更新日期
	public static String TXN_TYPE="";//交易类型
	public static String CUP_SSN="";//银联流水号
	public static String SYS_SEQ_NUM="";//系统流水号
	public static String TERM_SSN=""; //终端流水号
	public static String REFE_NUM="";//交易参考号
	public static String ORDER_TRSNUM="";//商户订单号
	public static String TXN_STATE="";//交易状态
	public static String REVSAL_FLAG="";//冲正标志
	public static String REVSAL_SSN="";//冲正流水号
	public static String CANCEL_FLAG="";//撤销标志
	public static String CANCEL_SSN="";//撤销流水号
	public static String PAN="";//卡号
	public static String TRS_AMOUNT;//交易金额
	public static String DATE_SETTLE;//清算日期
	public static String AUTHR_ID_RESP;//授权标识应答码
	public static String TLR_NUM;//终端操作员
	public static String ISS_CODE="";//持卡人发卡行

	public static String getISS_CODE() {
		return ISS_CODE;
	}
	public static void setISS_CODE(String iSSCODE) {
		ISS_CODE = iSSCODE;
	}
	public static String getCARD_ACCP_ID() {
		return CARD_ACCP_ID;
	}
	public static void setCARD_ACCP_ID(String cARDACCPID) {
		CARD_ACCP_ID = cARDACCPID;
	}
	public static String getTERM_ID() {
		return TERM_ID;
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
	public static String getUPDT_DATE() {
		return UPDT_DATE;
	}
	public static void setUPDT_DATE(String uPDTDATE) {
		UPDT_DATE = uPDTDATE;
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
	public static String getSYS_SEQ_NUM() {
		return SYS_SEQ_NUM;
	}
	public static void setSYS_SEQ_NUM(String sYSSEQNUM) {
		SYS_SEQ_NUM = sYSSEQNUM;
	}
	public static String getTERM_SSN() {
		return TERM_SSN;
	}
	public static void setTERM_SSN(String tERMSSN) {
		TERM_SSN = tERMSSN;
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
	public static String getREVSAL_FLAG() {
		return REVSAL_FLAG;
	}
	public static void setREVSAL_FLAG(String rEVSALFLAG) {
		REVSAL_FLAG = rEVSALFLAG;
	}
	public static String getREVSAL_SSN() {
		return REVSAL_SSN;
	}
	public static void setREVSAL_SSN(String rEVSALSSN) {
		REVSAL_SSN = rEVSALSSN;
	}
	public static String getCANCEL_FLAG() {
		return CANCEL_FLAG;
	}
	public static void setCANCEL_FLAG(String cANCELFLAG) {
		CANCEL_FLAG = cANCELFLAG;
	}
	public static String getCANCEL_SSN() {
		return CANCEL_SSN;
	}
	public static void setCANCEL_SSN(String cANCELSSN) {
		CANCEL_SSN = cANCELSSN;
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
	public static String getDATE_SETTLE() {
		return DATE_SETTLE;
	}
	public static void setDATE_SETTLE(String dATESETTLE) {
		DATE_SETTLE = dATESETTLE;
	}
	public static String getAUTHR_ID_RESP() {
		return AUTHR_ID_RESP;
	}
	public static void setAUTHR_ID_RESP(String aUTHRIDRESP) {
		AUTHR_ID_RESP = aUTHRIDRESP;
	}
	public static String getTLR_NUM() {
		return TLR_NUM;
	}
	public static void setTLR_NUM(String tLRNUM) {
		TLR_NUM = tLRNUM;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
