package com.huateng.po.mchnt.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_MCHT_SETTLE_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MCHT_SETTLE_INF"
 */

public abstract class BaseTblMchtSettleInf  implements Serializable {

	public static String REF = "TblMchtSettleInf";
	public static String PROP_SETTLE_ACCT_NM = "SettleAcctNm";
	public static String PROP_SPE_SETTLE_LV = "SpeSettleLv";
	public static String PROP_FEE_ACCT_NM = "FeeAcctNm";
	public static String PROP_FEE_TYPE = "FeeType";
	public static String PROP_SETTLE_TYPE = "SettleType";
	public static String PROP_OPEN_STLNO = "OpenStlno";
	public static String PROP_CORP_BANK_NAME = "CorpBankName";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_FEE_DIV2 = "FeeDiv2";
	public static String PROP_FEE_ACCT = "FeeAcct";
	public static String PROP_FEE_DIV1 = "FeeDiv1";
	public static String PROP_FEE_MIN_AMT = "FeeMinAmt";
	public static String PROP_SETTLE_RPT = "SettleRpt";
	public static String PROP_GROUP_FLAG = "GroupFlag";
	public static String PROP_BANK_ACCOUNT_CODE = "BankAccountCode";
	public static String PROP_CURR_ACCOUNT = "CurrAccount";
	public static String PROP_SETTLE_CHN = "SettleChn";
	public static String PROP_SETTLE_MODE = "SettleMode";
	public static String PROP_FEE_CYCLE = "FeeCycle";
	public static String PROP_HOLIDAY_SET_FLAG = "HolidaySetFlag";
	public static String PROP_DIR_BANK_NAME = "DirBankName";
	public static String PROP_FEE_DIV3 = "FeeDiv3";
	public static String PROP_DIR_BANK_CODE = "DirBankCode";
	public static String PROP_CHANGE_STLNO = "ChangeStlno";
	public static String PROP_SETTLE_BANK_NO = "SettleBankNo";
	public static String PROP_SETTLE_ACCT = "SettleAcct";
	public static String PROP_SPE_SETTLE_DS = "SpeSettleDs";
	public static String PROP_RATE_FLAG = "RateFlag";
	public static String PROP_PART_NUM = "PartNum";
	public static String PROP_COMP_ACCOUNT_BANK_CODE = "CompAccountBankCode";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_AUTO_STL_FLG = "AutoStlFlg";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_SPE_SETTLE_TP = "SpeSettleTp";
	public static String PROP_SETTLE_BANK_NM = "SettleBankNm";
	public static String PROP_DIR_ACCOUNT = "DirAccount";
	public static String PROP_CRE_FLAG = "CreFlag";
	public static String PROP_FEE_FIXED = "FeeFixed";
	public static String PROP_AUTO_FLAG = "AutoFlag";
	public static String PROP_FEE_MAX_AMT = "FeeMaxAmt";
	public static String PROP_FEE_BACK_FLG = "FeeBackFlg";
	public static String PROP_FEE_RATE = "FeeRate";
	public static String PROP_COMP_ACCOUNT_BANK_NAME = "CompAccountBankName";
	public static String PROP_ID = "Id";
	public static String PROP_DIR_FLAG = "DirFlag";
	public static String PROP_DIR_ACCOUNT_NAME = "DirAccountName";
	public static String PROP_BAT_TIME = "BatTime";
	public static String PROP_RETURN_FEE_FLAG = "ReturnFeeFlag";

	public static String LEGAL_NAM = "LegalNam";
	public static String COMPANY_NAM = "CompanyNam";
	//ADD
//	定向委托账号开户总行名称 dir_open_bank
	public static String DIR_OPEN_BANK = "DirOpenBank";
//	定向委托账号开户行所在省 dir_bank_province
	public static String DIR_BANK_PROVINCE = "DirBankProvince";
//	定向委托账号开户行所在市 dir_bank_city
	public static String DIR_BANK_CITY = "DirBankCity";
//	对公账号开户总行名称  comp_open_bank
	public static String COMP_OPEN_BANK = "CompOpenBank";
//	对公账号开户行所在省  comp_bank_province
	public static String COMP_BANK_PROVINCE = "CompBankProvince";
//	对公账号开户行所在市  comp_bank_city
	public static String COMP_BANK_CITY = "CompBankCity";
//	对私账号开户总行名称  corp_open_bank
	public static String CORP_OPEN_BANK = "CorpOpenBank";
//	对私账号开户行所在省  corp_bank_province
	public static String CORP_BANK_PROVINCE = "CorpBankProvince";
//	对私账号开户行所在市  corp_bank_city
	public static String CORP_BANK_CITY = "CorpBankCity";
	
	// constructors
	public BaseTblMchtSettleInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchtSettleInf (com.huateng.po.mchnt.TblMchtSettleInfPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblMchtSettleInf (
		com.huateng.po.mchnt.TblMchtSettleInfPK id,
		java.lang.String settleType,
		java.lang.String rateFlag,
		java.lang.String feeType,
		java.lang.String feeMaxAmt,
		java.lang.String feeMinAmt,
		java.lang.String feeDiv1,
		java.lang.String feeDiv2,
		java.lang.String feeDiv3,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs) {

		this.setId(id);
		this.setSettleType(settleType);
		this.setRateFlag(rateFlag);
		this.setFeeType(feeType);
		this.setFeeMaxAmt(feeMaxAmt);
		this.setFeeMinAmt(feeMinAmt);
		this.setFeeDiv1(feeDiv1);
		this.setFeeDiv2(feeDiv2);
		this.setFeeDiv3(feeDiv3);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.mchnt.TblMchtSettleInfPK id;

	// fields
	private java.lang.String settleType;
	private java.lang.String rateFlag;
	private java.lang.String settleChn;
	private java.lang.String batTime;
	private java.lang.String autoStlFlg;
	private java.lang.String partNum;
	private java.lang.String feeType;
	private java.lang.String feeFixed;
	private java.lang.String feeMaxAmt;
	private java.lang.String feeMinAmt;
	private java.lang.String feeRate;
	private java.lang.String feeDiv1;
	private java.lang.String feeDiv2;
	private java.lang.String feeDiv3;
	private java.lang.String settleMode;
	private java.lang.String feeCycle;
	private java.lang.String settleRpt;
	private java.lang.String settleBankNo;
	private java.lang.String settleBankNm;
	private java.lang.String settleAcctNm;
	private java.lang.String settleAcct;
	private java.lang.String feeAcctNm;
	private java.lang.String feeAcct;
	private java.lang.String groupFlag;
	private java.lang.String openStlno;
	private java.lang.String changeStlno;
	private java.lang.String speSettleTp;
	private java.lang.String speSettleLv;
	private java.lang.String speSettleDs;
	private java.lang.String feeBackFlg;
	private java.lang.String reserved;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String holidaySetFlag;
	private java.lang.String currAccount;
	private java.lang.String bankAccountCode;
	private java.lang.String corpBankName;
	private java.lang.String compAccountBankCode;
	private java.lang.String compAccountBankName;
	private java.lang.String dirBankCode;
	private java.lang.String dirBankName;
	private java.lang.String dirAccountName;
	private java.lang.String dirAccount;
	private java.lang.String dirFlag;
	private java.lang.String autoFlag;
	private java.lang.String creFlag;
	private java.lang.String returnFeeFlag;

	public java.lang.String legalNam;
	public java.lang.String companyNam;

	//ADD
//	定向委托账号开户总行名称 dir_open_bank
	public java.lang.String  dirOpenBank ;
//	定向委托账号开户行所在省 dir_bank_province
	public java.lang.String  dirBankProvince ;
//	定向委托账号开户行所在市 dir_bank_city
	public java.lang.String  dirBankCity ;
//	对公账号开户总行名称  comp_open_bank
	public java.lang.String  compOpenBank;
//	对公账号开户行所在省  comp_bank_province
	public java.lang.String compBankProvince;
//	对公账号开户行所在市  comp_bank_city
	public java.lang.String compBankCity;
//	对私账号开户总行名称  corp_open_bank
	public java.lang.String  corpOpenBank;
//	对私账号开户行所在省  corp_bank_province
	public java.lang.String  corpBankProvince;
//	对私账号开户行所在市  corp_bank_city
	public java.lang.String  corpBankCity;
	
	
	
	
	public java.lang.String getDirOpenBank() {
		return dirOpenBank;
	}

	public void setDirOpenBank(java.lang.String dirOpenBank) {
		this.dirOpenBank = dirOpenBank;
	}

	public java.lang.String getDirBankProvince() {
		return dirBankProvince;
	}

	public void setDirBankProvince(java.lang.String dirBankProvince) {
		this.dirBankProvince = dirBankProvince;
	}

	public java.lang.String getDirBankCity() {
		return dirBankCity;
	}

	public void setDirBankCity(java.lang.String dirBankCity) {
		this.dirBankCity = dirBankCity;
	}

	public java.lang.String getCompOpenBank() {
		return compOpenBank;
	}

	public void setCompOpenBank(java.lang.String compOpenBank) {
		this.compOpenBank = compOpenBank;
	}

	public java.lang.String getCompBankProvince() {
		return compBankProvince;
	}

	public void setCompBankProvince(java.lang.String compBankProvince) {
		this.compBankProvince = compBankProvince;
	}

	public java.lang.String getCompBankCity() {
		return compBankCity;
	}

	public void setCompBankCity(java.lang.String compBankCity) {
		this.compBankCity = compBankCity;
	}

	public java.lang.String getCorpOpenBank() {
		return corpOpenBank;
	}

	public void setCorpOpenBank(java.lang.String corpOpenBank) {
		this.corpOpenBank = corpOpenBank;
	}

	public java.lang.String getCorpBankProvince() {
		return corpBankProvince;
	}

	public void setCorpBankProvince(java.lang.String corpBankProvince) {
		this.corpBankProvince = corpBankProvince;
	}

	public java.lang.String getCorpBankCity() {
		return corpBankCity;
	}

	public void setCorpBankCity(java.lang.String corpBankCity) {
		this.corpBankCity = corpBankCity;
	}

	public java.lang.String getLegalNam() {
		return legalNam;
	}

	public void setLegalNam(java.lang.String legalNam) {
		this.legalNam = legalNam;
	}

	public java.lang.String getCompanyNam() {
		return companyNam;
	}

	public void setCompanyNam(java.lang.String companyNam) {
		this.companyNam = companyNam;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.huateng.po.mchnt.TblMchtSettleInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.huateng.po.mchnt.TblMchtSettleInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: SETTLE_TYPE
	 */
	public java.lang.String getSettleType () {
		return settleType;
	}

	/**
	 * Set the value related to the column: SETTLE_TYPE
	 * @param settleType the SETTLE_TYPE value
	 */
	public void setSettleType (java.lang.String settleType) {
		this.settleType = settleType;
	}



	/**
	 * Return the value associated with the column: RATE_FLAG
	 */
	public java.lang.String getRateFlag () {
		return rateFlag;
	}

	/**
	 * Set the value related to the column: RATE_FLAG
	 * @param rateFlag the RATE_FLAG value
	 */
	public void setRateFlag (java.lang.String rateFlag) {
		this.rateFlag = rateFlag;
	}



	/**
	 * Return the value associated with the column: SETTLE_CHN
	 */
	public java.lang.String getSettleChn () {
		return settleChn;
	}

	/**
	 * Set the value related to the column: SETTLE_CHN
	 * @param settleChn the SETTLE_CHN value
	 */
	public void setSettleChn (java.lang.String settleChn) {
		this.settleChn = settleChn;
	}



	/**
	 * Return the value associated with the column: BAT_TIME
	 */
	public java.lang.String getBatTime () {
		return batTime;
	}

	/**
	 * Set the value related to the column: BAT_TIME
	 * @param batTime the BAT_TIME value
	 */
	public void setBatTime (java.lang.String batTime) {
		this.batTime = batTime;
	}



	/**
	 * Return the value associated with the column: AUTO_STL_FLG
	 */
	public java.lang.String getAutoStlFlg () {
		return autoStlFlg;
	}

	/**
	 * Set the value related to the column: AUTO_STL_FLG
	 * @param autoStlFlg the AUTO_STL_FLG value
	 */
	public void setAutoStlFlg (java.lang.String autoStlFlg) {
		this.autoStlFlg = autoStlFlg;
	}



	/**
	 * Return the value associated with the column: PART_NUM
	 */
	public java.lang.String getPartNum () {
		return partNum;
	}

	/**
	 * Set the value related to the column: PART_NUM
	 * @param partNum the PART_NUM value
	 */
	public void setPartNum (java.lang.String partNum) {
		this.partNum = partNum;
	}



	/**
	 * Return the value associated with the column: FEE_TYPE
	 */
	public java.lang.String getFeeType () {
		return feeType;
	}

	/**
	 * Set the value related to the column: FEE_TYPE
	 * @param feeType the FEE_TYPE value
	 */
	public void setFeeType (java.lang.String feeType) {
		this.feeType = feeType;
	}



	/**
	 * Return the value associated with the column: FEE_FIXED
	 */
	public java.lang.String getFeeFixed () {
		return feeFixed;
	}

	/**
	 * Set the value related to the column: FEE_FIXED
	 * @param feeFixed the FEE_FIXED value
	 */
	public void setFeeFixed (java.lang.String feeFixed) {
		this.feeFixed = feeFixed;
	}



	/**
	 * Return the value associated with the column: FEE_MAX_AMT
	 */
	public java.lang.String getFeeMaxAmt () {
		return feeMaxAmt;
	}

	/**
	 * Set the value related to the column: FEE_MAX_AMT
	 * @param feeMaxAmt the FEE_MAX_AMT value
	 */
	public void setFeeMaxAmt (java.lang.String feeMaxAmt) {
		this.feeMaxAmt = feeMaxAmt;
	}



	/**
	 * Return the value associated with the column: FEE_MIN_AMT
	 */
	public java.lang.String getFeeMinAmt () {
		return feeMinAmt;
	}

	/**
	 * Set the value related to the column: FEE_MIN_AMT
	 * @param feeMinAmt the FEE_MIN_AMT value
	 */
	public void setFeeMinAmt (java.lang.String feeMinAmt) {
		this.feeMinAmt = feeMinAmt;
	}



	/**
	 * Return the value associated with the column: FEE_RATE
	 */
	public java.lang.String getFeeRate () {
		return feeRate;
	}

	/**
	 * Set the value related to the column: FEE_RATE
	 * @param feeRate the FEE_RATE value
	 */
	public void setFeeRate (java.lang.String feeRate) {
		this.feeRate = feeRate;
	}



	/**
	 * Return the value associated with the column: FEE_DIV_1
	 */
	public java.lang.String getFeeDiv1 () {
		return feeDiv1;
	}

	/**
	 * Set the value related to the column: FEE_DIV_1
	 * @param feeDiv1 the FEE_DIV_1 value
	 */
	public void setFeeDiv1 (java.lang.String feeDiv1) {
		this.feeDiv1 = feeDiv1;
	}



	/**
	 * Return the value associated with the column: FEE_DIV_2
	 */
	public java.lang.String getFeeDiv2 () {
		return feeDiv2;
	}

	/**
	 * Set the value related to the column: FEE_DIV_2
	 * @param feeDiv2 the FEE_DIV_2 value
	 */
	public void setFeeDiv2 (java.lang.String feeDiv2) {
		this.feeDiv2 = feeDiv2;
	}



	/**
	 * Return the value associated with the column: FEE_DIV_3
	 */
	public java.lang.String getFeeDiv3 () {
		return feeDiv3;
	}

	/**
	 * Set the value related to the column: FEE_DIV_3
	 * @param feeDiv3 the FEE_DIV_3 value
	 */
	public void setFeeDiv3 (java.lang.String feeDiv3) {
		this.feeDiv3 = feeDiv3;
	}



	/**
	 * Return the value associated with the column: SETTLE_MODE
	 */
	public java.lang.String getSettleMode () {
		return settleMode;
	}

	/**
	 * Set the value related to the column: SETTLE_MODE
	 * @param settleMode the SETTLE_MODE value
	 */
	public void setSettleMode (java.lang.String settleMode) {
		this.settleMode = settleMode;
	}



	/**
	 * Return the value associated with the column: FEE_CYCLE
	 */
	public java.lang.String getFeeCycle () {
		return feeCycle;
	}

	/**
	 * Set the value related to the column: FEE_CYCLE
	 * @param feeCycle the FEE_CYCLE value
	 */
	public void setFeeCycle (java.lang.String feeCycle) {
		this.feeCycle = feeCycle;
	}



	/**
	 * Return the value associated with the column: SETTLE_RPT
	 */
	public java.lang.String getSettleRpt () {
		return settleRpt;
	}

	/**
	 * Set the value related to the column: SETTLE_RPT
	 * @param settleRpt the SETTLE_RPT value
	 */
	public void setSettleRpt (java.lang.String settleRpt) {
		this.settleRpt = settleRpt;
	}



	/**
	 * Return the value associated with the column: SETTLE_BANK_NO
	 */
	public java.lang.String getSettleBankNo () {
		return settleBankNo;
	}

	/**
	 * Set the value related to the column: SETTLE_BANK_NO
	 * @param settleBankNo the SETTLE_BANK_NO value
	 */
	public void setSettleBankNo (java.lang.String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}



	/**
	 * Return the value associated with the column: SETTLE_BANK_NM
	 */
	public java.lang.String getSettleBankNm () {
		return settleBankNm;
	}

	/**
	 * Set the value related to the column: SETTLE_BANK_NM
	 * @param settleBankNm the SETTLE_BANK_NM value
	 */
	public void setSettleBankNm (java.lang.String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}



	/**
	 * Return the value associated with the column: SETTLE_ACCT_NM
	 */
	public java.lang.String getSettleAcctNm () {
		return settleAcctNm;
	}

	/**
	 * Set the value related to the column: SETTLE_ACCT_NM
	 * @param settleAcctNm the SETTLE_ACCT_NM value
	 */
	public void setSettleAcctNm (java.lang.String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}



	/**
	 * Return the value associated with the column: SETTLE_ACCT
	 */
	public java.lang.String getSettleAcct () {
		return settleAcct;
	}

	/**
	 * Set the value related to the column: SETTLE_ACCT
	 * @param settleAcct the SETTLE_ACCT value
	 */
	public void setSettleAcct (java.lang.String settleAcct) {
		this.settleAcct = settleAcct;
	}



	/**
	 * Return the value associated with the column: FEE_ACCT_NM
	 */
	public java.lang.String getFeeAcctNm () {
		return feeAcctNm;
	}

	/**
	 * Set the value related to the column: FEE_ACCT_NM
	 * @param feeAcctNm the FEE_ACCT_NM value
	 */
	public void setFeeAcctNm (java.lang.String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}



	/**
	 * Return the value associated with the column: FEE_ACCT
	 */
	public java.lang.String getFeeAcct () {
		return feeAcct;
	}

	/**
	 * Set the value related to the column: FEE_ACCT
	 * @param feeAcct the FEE_ACCT value
	 */
	public void setFeeAcct (java.lang.String feeAcct) {
		this.feeAcct = feeAcct;
	}



	/**
	 * Return the value associated with the column: GROUP_FLAG
	 */
	public java.lang.String getGroupFlag () {
		return groupFlag;
	}

	/**
	 * Set the value related to the column: GROUP_FLAG
	 * @param groupFlag the GROUP_FLAG value
	 */
	public void setGroupFlag (java.lang.String groupFlag) {
		this.groupFlag = groupFlag;
	}



	/**
	 * Return the value associated with the column: OPEN_STLNO
	 */
	public java.lang.String getOpenStlno () {
		return openStlno;
	}

	/**
	 * Set the value related to the column: OPEN_STLNO
	 * @param openStlno the OPEN_STLNO value
	 */
	public void setOpenStlno (java.lang.String openStlno) {
		this.openStlno = openStlno;
	}



	/**
	 * Return the value associated with the column: CHANGE_STLNO
	 */
	public java.lang.String getChangeStlno () {
		return changeStlno;
	}

	/**
	 * Set the value related to the column: CHANGE_STLNO
	 * @param changeStlno the CHANGE_STLNO value
	 */
	public void setChangeStlno (java.lang.String changeStlno) {
		this.changeStlno = changeStlno;
	}



	/**
	 * Return the value associated with the column: SPE_SETTLE_TP
	 */
	public java.lang.String getSpeSettleTp () {
		return speSettleTp;
	}

	/**
	 * Set the value related to the column: SPE_SETTLE_TP
	 * @param speSettleTp the SPE_SETTLE_TP value
	 */
	public void setSpeSettleTp (java.lang.String speSettleTp) {
		this.speSettleTp = speSettleTp;
	}



	/**
	 * Return the value associated with the column: SPE_SETTLE_LV
	 */
	public java.lang.String getSpeSettleLv () {
		return speSettleLv;
	}

	/**
	 * Set the value related to the column: SPE_SETTLE_LV
	 * @param speSettleLv the SPE_SETTLE_LV value
	 */
	public void setSpeSettleLv (java.lang.String speSettleLv) {
		this.speSettleLv = speSettleLv;
	}



	/**
	 * Return the value associated with the column: SPE_SETTLE_DS
	 */
	public java.lang.String getSpeSettleDs () {
		return speSettleDs;
	}

	/**
	 * Set the value related to the column: SPE_SETTLE_DS
	 * @param speSettleDs the SPE_SETTLE_DS value
	 */
	public void setSpeSettleDs (java.lang.String speSettleDs) {
		this.speSettleDs = speSettleDs;
	}



	/**
	 * Return the value associated with the column: FEE_BACK_FLG
	 */
	public java.lang.String getFeeBackFlg () {
		return feeBackFlg;
	}

	/**
	 * Set the value related to the column: FEE_BACK_FLG
	 * @param feeBackFlg the FEE_BACK_FLG value
	 */
	public void setFeeBackFlg (java.lang.String feeBackFlg) {
		this.feeBackFlg = feeBackFlg;
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



	/**
	 * Return the value associated with the column: HOLIDAY_SET_FLAG
	 */
	public java.lang.String getHolidaySetFlag () {
		return holidaySetFlag;
	}

	/**
	 * Set the value related to the column: HOLIDAY_SET_FLAG
	 * @param holidaySetFlag the HOLIDAY_SET_FLAG value
	 */
	public void setHolidaySetFlag (java.lang.String holidaySetFlag) {
		this.holidaySetFlag = holidaySetFlag;
	}



	/**
	 * Return the value associated with the column: CURR_ACCOUNT
	 */
	public java.lang.String getCurrAccount () {
		return currAccount;
	}

	/**
	 * Set the value related to the column: CURR_ACCOUNT
	 * @param currAccount the CURR_ACCOUNT value
	 */
	public void setCurrAccount (java.lang.String currAccount) {
		this.currAccount = currAccount;
	}



	/**
	 * Return the value associated with the column: BANK_ACCOUNT_CODE
	 */
	public java.lang.String getBankAccountCode () {
		return bankAccountCode;
	}

	/**
	 * Set the value related to the column: BANK_ACCOUNT_CODE
	 * @param bankAccountCode the BANK_ACCOUNT_CODE value
	 */
	public void setBankAccountCode (java.lang.String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}



	/**
	 * Return the value associated with the column: CORP_BANK_NAME
	 */
	public java.lang.String getCorpBankName () {
		return corpBankName;
	}

	/**
	 * Set the value related to the column: CORP_BANK_NAME
	 * @param corpBankName the CORP_BANK_NAME value
	 */
	public void setCorpBankName (java.lang.String corpBankName) {
		this.corpBankName = corpBankName;
	}



	/**
	 * Return the value associated with the column: COMP_ACCOUNT_BANK_CODE
	 */
	public java.lang.String getCompAccountBankCode () {
		return compAccountBankCode;
	}

	/**
	 * Set the value related to the column: COMP_ACCOUNT_BANK_CODE
	 * @param compAccountBankCode the COMP_ACCOUNT_BANK_CODE value
	 */
	public void setCompAccountBankCode (java.lang.String compAccountBankCode) {
		this.compAccountBankCode = compAccountBankCode;
	}



	/**
	 * Return the value associated with the column: COMP_ACCOUNT_BANK_NAME
	 */
	public java.lang.String getCompAccountBankName () {
		return compAccountBankName;
	}

	/**
	 * Set the value related to the column: COMP_ACCOUNT_BANK_NAME
	 * @param compAccountBankName the COMP_ACCOUNT_BANK_NAME value
	 */
	public void setCompAccountBankName (java.lang.String compAccountBankName) {
		this.compAccountBankName = compAccountBankName;
	}



	/**
	 * Return the value associated with the column: DIR_BANK_CODE
	 */
	public java.lang.String getDirBankCode () {
		return dirBankCode;
	}

	/**
	 * Set the value related to the column: DIR_BANK_CODE
	 * @param dirBankCode the DIR_BANK_CODE value
	 */
	public void setDirBankCode (java.lang.String dirBankCode) {
		this.dirBankCode = dirBankCode;
	}



	/**
	 * Return the value associated with the column: DIR_BANK_NAME
	 */
	public java.lang.String getDirBankName () {
		return dirBankName;
	}

	/**
	 * Set the value related to the column: DIR_BANK_NAME
	 * @param dirBankName the DIR_BANK_NAME value
	 */
	public void setDirBankName (java.lang.String dirBankName) {
		this.dirBankName = dirBankName;
	}



	/**
	 * Return the value associated with the column: DIR_ACCOUNT_NAME
	 */
	public java.lang.String getDirAccountName () {
		return dirAccountName;
	}

	/**
	 * Set the value related to the column: DIR_ACCOUNT_NAME
	 * @param dirAccountName the DIR_ACCOUNT_NAME value
	 */
	public void setDirAccountName (java.lang.String dirAccountName) {
		this.dirAccountName = dirAccountName;
	}



	/**
	 * Return the value associated with the column: DIR_ACCOUNT
	 */
	public java.lang.String getDirAccount () {
		return dirAccount;
	}

	/**
	 * Set the value related to the column: DIR_ACCOUNT
	 * @param dirAccount the DIR_ACCOUNT value
	 */
	public void setDirAccount (java.lang.String dirAccount) {
		this.dirAccount = dirAccount;
	}



	/**
	 * Return the value associated with the column: DIR_FLAG
	 */
	public java.lang.String getDirFlag () {
		return dirFlag;
	}

	/**
	 * Set the value related to the column: DIR_FLAG
	 * @param dirFlag the DIR_FLAG value
	 */
	public void setDirFlag (java.lang.String dirFlag) {
		this.dirFlag = dirFlag;
	}



	/**
	 * Return the value associated with the column: AUTO_FLAG
	 */
	public java.lang.String getAutoFlag () {
		return autoFlag;
	}

	/**
	 * Set the value related to the column: AUTO_FLAG
	 * @param autoFlag the AUTO_FLAG value
	 */
	public void setAutoFlag (java.lang.String autoFlag) {
		this.autoFlag = autoFlag;
	}



	/**
	 * Return the value associated with the column: CRE_FLAG
	 */
	public java.lang.String getCreFlag () {
		return creFlag;
	}

	/**
	 * Set the value related to the column: CRE_FLAG
	 * @param creFlag the CRE_FLAG value
	 */
	public void setCreFlag (java.lang.String creFlag) {
		this.creFlag = creFlag;
	}




	public java.lang.String getReturnFeeFlag() {
		return returnFeeFlag;
	}

	public void setReturnFeeFlag(java.lang.String returnFeeFlag) {
		this.returnFeeFlag = returnFeeFlag;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblMchtSettleInf)) return false;
		else {
			com.huateng.po.mchnt.TblMchtSettleInf tblMchtSettleInf = (com.huateng.po.mchnt.TblMchtSettleInf) obj;
			if (null == this.getId() || null == tblMchtSettleInf.getId()) return false;
			else return (this.getId().equals(tblMchtSettleInf.getId()));
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