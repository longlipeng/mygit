package com.huateng.po.mchnt.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_DEF_MCHT_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_DEF_MCHT_INF"
 */

public abstract class BaseTblDefMchtInf  implements Serializable {

	public static String REF = "TblDefMchtInf";
	public static String PROP_BUSI = "busi";
	public static String PROP_PUB_ACC_BRANCH = "pubAccBranch";
	public static String PROP_PERS_ACC_NO = "persAccNo";
	public static String PROP_RCV_COUNTRY_CODE = "rcvCountryCode";
	public static String PROP_AVE_MONTH_INCOME = "aveMonthIncome";
	public static String PROP_D_MON_TRANS_LIMIT = "dMonTransLimit";
	public static String PROP_BUSI_AREA = "busiArea";
	public static String PROP_FUNC_PREAUTH = "funcPreauth";
	public static String PROP_LIC_END_DATE = "licEndDate";
	public static String PROP_CONTACTS_PHONE = "contactsPhone";
	public static String PROP_MCHT_SETTLE_TYPE = "mchtSettleType";
	public static String PROP_REG_DATE = "regDate";
	public static String PROP_SETTLE_ACC_TYPE = "settleAccType";
	public static String PROP_LEG_ACC_BANK = "legAccBank";
	public static String PROP_PERS_ACC_NAME = "persAccName";
	public static String PROP_REG_CAPITAL = "regCapital";
	public static String PROP_FUNC_POS_IN_EXP = "funcPosInExp";
	public static String PROP_LIC_START_DATE = "licStartDate";
	public static String PROP_CRT_DATE = "crtDate";
	public static String PROP_EMAIL = "email";
	public static String PROP_PERS_ACC_BRANCH = "persAccBranch";
	public static String PROP_FUNC_AUTO_WITHDRAW = "funcAutoWithdraw";
	public static String PROP_MCHT_FAX = "mchtFax";
	public static String PROP_OPR_ID = "oprId";
	public static String PROP_WEB_URL = "webUrl";
	public static String PROP_CONTACTS = "contacts";
	public static String PROP_C_SINGLE_TRANS_LIMIT = "cSingleTransLimit";
	public static String PROP_AUTHORIZER_TEL = "authorizerTel";
	public static String PROP_PUB_ACC_NAME = "pubAccName";
	public static String PROP_COUNTRY_CODE = "countryCode";
	public static String PROP_BUSI_PLACE_TYPE = "busiPlaceType";
	public static String PROP_EXPANDER = "expander";
	public static String PROP_MCHT_SHORT_NAME = "mchtShortName";
	public static String PROP_ICP_INS_NAME = "icpInsName";
	public static String PROP_C_SINGLE_HIGH_TRANS = "cSingleHighTrans";
	public static String PROP_DISC_NO = "discNo";
	public static String PROP_WEB_NAME = "webName";
	public static String PROP_LEG_ACC_BRANCH = "legAccBranch";
	public static String PROP_BUSI_START_TIME = "busiStartTime";
	public static String PROP_BUSI_USER_TEL = "busiUserTel";
	public static String PROP_RESERVE1 = "reserve1";
	public static String PROP_RESERVE2 = "reserve2";
	public static String PROP_PERS_ACC_BANK = "persAccBank";
	public static String PROP_RESERVE3 = "reserve3";
	public static String PROP_AUTHORIZER_NAME = "authorizerName";
	public static String PROP_LEGAL_TEL = "legalTel";
	public static String PROP_POST_CODE = "postCode";
	public static String PROP_LEG_CERT = "legCert";
	public static String PROP_PUB_ACC_BRANCH_NM = "pubAccBranchNm";
	public static String PROP_RESERVE8 = "reserve8";
	public static String PROP_FUNC_POS_OUT = "funcPosOut";
	public static String PROP_RESERVE9 = "reserve9";
	public static String PROP_RCV_CITY_CODE = "rcvCityCode";
	public static String PROP_RESERVE4 = "reserve4";
	public static String PROP_SETTLE_INTERVAL = "settleInterval";
	public static String PROP_RESERVE5 = "reserve5";
	public static String PROP_RESERVE6 = "reserve6";
	public static String PROP_RESERVE7 = "reserve7";
	public static String PROP_MCHNT_TP_GRP = "mchntTpGrp";
	public static String PROP_UPT_DATE = "uptDate";
	public static String PROP_BUSI_LIC_NO = "busiLicNo";
	public static String PROP_MCHT_TYPE = "mchtType";
	public static String PROP_D_SINGLE_TRANS_LIMIT = "dSingleTransLimit";
	public static String PROP_ORG_CERT_NO = "orgCertNo";
	public static String PROP_FUNC_RETURN = "funcReturn";
	public static String PROP_CONTACTS_TEL = "contactsTel";
	public static String PROP_D_DAY_TRANS_LIMIT = "dDayTransLimit";
	public static String PROP_PAY_CARD_TYPE = "payCardType";
	public static String PROP_AUTHORIZER_ID = "authorizerId";
	public static String PROP_MCHT_TEL = "mchtTel";
	public static String PROP_D_SINGLE_HIGH_TRANS = "dSingleHighTrans";
	public static String PROP_MCHT_NAME = "mchtName";
	public static String PROP_ADJUST_USER_TEL = "adjustUserTel";
	public static String PROP_ADJUST_USER = "adjustUser";
	public static String PROP_FUNC_CREDIT_CARD = "funcCreditCard";
	public static String PROP_MCHT_NO = "mchtNo";
	public static String PROP_C_MON_TRANS_LIMIT = "cMonTransLimit";
	public static String PROP_REC_ID = "recId";
	public static String PROP_TAX_REG_CERT_NO = "taxRegCertNo";
	public static String PROP_EXPANDER_TEL = "expanderTel";
	public static String PROP_LEGAL_CARD_NO = "legalCardNo";
	public static String PROP_AGENT_NO = "agentNo";
	public static String PROP_INDUSTRY = "industry";
	public static String PROP_CONTACTS_EMAIL = "contactsEmail";
	public static String PROP_PUB_ACC_NO = "pubAccNo";
	public static String PROP_LEG_ACC_NAME = "legAccName";
	public static String PROP_REG_ADDRESS = "regAddress";
	public static String PROP_C_DAY_TRANS_LIMIT = "cDayTransLimit";
	public static String PROP_RCV_ADDR = "rcvAddr";
	public static String PROP_RCV_PRO_CODE = "rcvProCode";
	public static String PROP_GEN_MCHT_NO = "genMchtNo";
	public static String PROP_RESERVE10 = "reserve10";
	public static String PROP_PUB_ACC_BANK = "pubAccBank";
	public static String PROP_MCHT_NO_IN = "mchtNoIn";
	public static String PROP_LEG_ACC_NO = "legAccNo";
	public static String PROP_FUNC_POS_OUT_EXP = "funcPosOutExp";
	public static String PROP_BUSI_SCOPE = "busiScope";
	public static String PROP_LEG_ACC_BRANCH_NM = "legAccBranchNm";
	public static String PROP_PERS_ACC_BRANCH_NM = "persAccBranchNm";
	public static String PROP_PRO_CODE = "proCode";
	public static String PROP_LEG_NATION = "legNation";
	public static String PROP_FUNC_RETURN_WITH_FEE = "funcReturnWithFee";
	public static String PROP_CITY_CODE = "cityCode";
	public static String PROP_BUSI_USER = "busiUser";
	public static String PROP_FUNC_HOLIDAY_WITHDRAW = "funcHolidayWithdraw";
	public static String PROP_EMP_COUNT = "empCount";
	public static String PROP_STATUS = "status";
	public static String PROP_LEGAL_NAME = "legalName";
	public static String PROP_MCC = "mcc";
	public static String PROP_USEABLE = "useable";
	public static String PROP_POS_CNT = "posCnt";
	public static String PROP_BUSI_END_TIME = "busiEndTime";
	public static String PROP_ICP_NO = "icpNo";
	public static String PROP_REAL_FEE_PERCENT = "realFeePercent";
	public static String PROP_APPLY_DESC = "applyDesc";


	// constructors
	public BaseTblDefMchtInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblDefMchtInf (java.lang.String recId) {
		this.setRecId(recId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String recId;

	// fields
	private java.lang.String mchtNo;
	private java.lang.String genMchtNo;
	private java.lang.String mchtNoIn;
	private java.lang.String mchtName;
	private java.lang.String mchtShortName;
	private java.lang.String mchtType;
	private java.lang.String proCode;
	private java.lang.String cityCode;
	private java.lang.String countryCode;
	private java.lang.String regAddress;
	private java.lang.String postCode;
	private java.lang.String email;
	private java.lang.String busiLicNo;
	private java.lang.String orgCertNo;
	private java.lang.String taxRegCertNo;
	private java.lang.String busiScope;
	private java.lang.String busi;
	private java.lang.String industry;
	private java.lang.String regDate;
	private java.math.BigDecimal regCapital;
	private java.lang.String legNation;
	private java.lang.String legalName;
	private java.lang.String legCert;
	private java.lang.String legalCardNo;
	private java.lang.String authorizerName;
	private java.lang.String authorizerId;
	private java.math.BigDecimal busiArea;
	private java.lang.String busiPlaceType;
	private java.lang.Integer empCount;
	private java.lang.String webName;
	private java.lang.String webUrl;
	private java.lang.String icpNo;
	private java.lang.String icpInsName;
	private java.lang.String adjustUser;
	private java.lang.String adjustUserTel;
	private java.lang.String mchtTel;
	private java.lang.String mchtFax;
	private java.lang.String legalTel;
	private java.lang.String contacts;
	private java.lang.String contactsPhone;
	private java.lang.String contactsTel;
	private java.lang.String contactsEmail;
	private java.lang.String rcvProCode;
	private java.lang.String rcvCityCode;
	private java.lang.String rcvCountryCode;
	private java.lang.String rcvAddr;
	private java.lang.Integer dSingleTransLimit;
	private java.lang.Integer dDayTransLimit;
	private java.lang.Integer dMonTransLimit;
	private java.lang.Integer dSingleHighTrans;
	private java.lang.Integer cSingleTransLimit;
	private java.lang.Integer cDayTransLimit;
	private java.lang.Integer cMonTransLimit;
	private java.lang.Integer cSingleHighTrans;
	private java.lang.String funcCreditCard;
	private java.lang.String funcPreauth;
	private java.lang.String funcHolidayWithdraw;
	private java.lang.String funcReturn;
	private java.lang.String funcAutoWithdraw;
	private java.lang.String funcReturnWithFee;
	private java.lang.String funcPosInExp;
	private java.lang.String funcPosOutExp;
	private java.lang.String funcPosOut;
	private java.lang.String expander;
	private java.lang.String expanderTel;
	private java.lang.String busiStartTime;
	private java.lang.String busiEndTime;
	private java.lang.String agentNo;
	private java.lang.String applyDesc;
	private java.lang.String settleAccType;
	private java.lang.String mchntTpGrp;
	private java.lang.String mcc;
	private java.lang.String busiUser;
	private java.lang.String busiUserTel;
	private java.lang.String licStartDate;
	private java.lang.String licEndDate;
	private java.lang.String authorizerTel;
	private java.lang.Integer aveMonthIncome;
	private java.lang.String settleInterval;
	private java.lang.Integer mchtSettleType;
	private java.lang.String pubAccBank;
	private java.lang.String pubAccBranch;
	private java.lang.String pubAccBranchNm;
	private java.lang.String pubAccName;
	private java.lang.String pubAccNo;
	private java.lang.String persAccBank;
	private java.lang.String persAccBranch;
	private java.lang.String persAccBranchNm;
	private java.lang.String persAccName;
	private java.lang.String persAccNo;
	private java.lang.String legAccBank;
	private java.lang.String legAccBranch;
	private java.lang.String legAccBranchNm;
	private java.lang.String legAccName;
	private java.lang.String legAccNo;
	private java.lang.Integer posCnt;
	private java.lang.String payCardType;
	private java.lang.String discNo;
	private java.lang.String realFeePercent;
	private java.lang.String reserve1;
	private java.lang.String reserve2;
	private java.lang.String reserve3;
	private java.lang.String reserve4;
	private java.lang.String reserve5;
	private java.lang.String reserve6;
	private java.lang.String reserve7;
	private java.lang.String reserve8;
	private java.lang.String reserve9;
	private java.lang.String reserve10;
	private java.lang.String status;
	private java.lang.String useable;
	private java.lang.String oprId;
	private java.lang.String crtDate;
	private java.lang.String uptDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="REC_ID"
     */
	public java.lang.String getRecId () {
		return recId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setRecId (java.lang.String recId) {
		this.recId = recId;
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
	 * Return the value associated with the column: GEN_MCHT_NO
	 */
	public java.lang.String getGenMchtNo () {
		return genMchtNo;
	}

	/**
	 * Set the value related to the column: GEN_MCHT_NO
	 * @param genMchtNo the GEN_MCHT_NO value
	 */
	public void setGenMchtNo (java.lang.String genMchtNo) {
		this.genMchtNo = genMchtNo;
	}



	/**
	 * Return the value associated with the column: MCHT_NO_IN
	 */
	public java.lang.String getMchtNoIn () {
		return mchtNoIn;
	}

	/**
	 * Set the value related to the column: MCHT_NO_IN
	 * @param mchtNoIn the MCHT_NO_IN value
	 */
	public void setMchtNoIn (java.lang.String mchtNoIn) {
		this.mchtNoIn = mchtNoIn;
	}



	/**
	 * Return the value associated with the column: MCHT_NAME
	 */
	public java.lang.String getMchtName () {
		return mchtName;
	}

	/**
	 * Set the value related to the column: MCHT_NAME
	 * @param mchtName the MCHT_NAME value
	 */
	public void setMchtName (java.lang.String mchtName) {
		this.mchtName = mchtName;
	}



	/**
	 * Return the value associated with the column: MCHT_SHORT_NAME
	 */
	public java.lang.String getMchtShortName () {
		return mchtShortName;
	}

	/**
	 * Set the value related to the column: MCHT_SHORT_NAME
	 * @param mchtShortName the MCHT_SHORT_NAME value
	 */
	public void setMchtShortName (java.lang.String mchtShortName) {
		this.mchtShortName = mchtShortName;
	}



	/**
	 * Return the value associated with the column: MCHT_TYPE
	 */
	public java.lang.String getMchtType () {
		return mchtType;
	}

	/**
	 * Set the value related to the column: MCHT_TYPE
	 * @param mchtType the MCHT_TYPE value
	 */
	public void setMchtType (java.lang.String mchtType) {
		this.mchtType = mchtType;
	}



	/**
	 * Return the value associated with the column: PRO_CODE
	 */
	public java.lang.String getProCode () {
		return proCode;
	}

	/**
	 * Set the value related to the column: PRO_CODE
	 * @param proCode the PRO_CODE value
	 */
	public void setProCode (java.lang.String proCode) {
		this.proCode = proCode;
	}



	/**
	 * Return the value associated with the column: CITY_CODE
	 */
	public java.lang.String getCityCode () {
		return cityCode;
	}

	/**
	 * Set the value related to the column: CITY_CODE
	 * @param cityCode the CITY_CODE value
	 */
	public void setCityCode (java.lang.String cityCode) {
		this.cityCode = cityCode;
	}



	/**
	 * Return the value associated with the column: COUNTRY_CODE
	 */
	public java.lang.String getCountryCode () {
		return countryCode;
	}

	/**
	 * Set the value related to the column: COUNTRY_CODE
	 * @param countryCode the COUNTRY_CODE value
	 */
	public void setCountryCode (java.lang.String countryCode) {
		this.countryCode = countryCode;
	}



	/**
	 * Return the value associated with the column: REG_ADDRESS
	 */
	public java.lang.String getRegAddress () {
		return regAddress;
	}

	/**
	 * Set the value related to the column: REG_ADDRESS
	 * @param regAddress the REG_ADDRESS value
	 */
	public void setRegAddress (java.lang.String regAddress) {
		this.regAddress = regAddress;
	}



	/**
	 * Return the value associated with the column: POST_CODE
	 */
	public java.lang.String getPostCode () {
		return postCode;
	}

	/**
	 * Set the value related to the column: POST_CODE
	 * @param postCode the POST_CODE value
	 */
	public void setPostCode (java.lang.String postCode) {
		this.postCode = postCode;
	}



	/**
	 * Return the value associated with the column: EMAIL
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: EMAIL
	 * @param email the EMAIL value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: BUSI_LIC_NO
	 */
	public java.lang.String getBusiLicNo () {
		return busiLicNo;
	}

	/**
	 * Set the value related to the column: BUSI_LIC_NO
	 * @param busiLicNo the BUSI_LIC_NO value
	 */
	public void setBusiLicNo (java.lang.String busiLicNo) {
		this.busiLicNo = busiLicNo;
	}



	/**
	 * Return the value associated with the column: ORG_CERT_NO
	 */
	public java.lang.String getOrgCertNo () {
		return orgCertNo;
	}

	/**
	 * Set the value related to the column: ORG_CERT_NO
	 * @param orgCertNo the ORG_CERT_NO value
	 */
	public void setOrgCertNo (java.lang.String orgCertNo) {
		this.orgCertNo = orgCertNo;
	}



	/**
	 * Return the value associated with the column: TAX_REG_CERT_NO
	 */
	public java.lang.String getTaxRegCertNo () {
		return taxRegCertNo;
	}

	/**
	 * Set the value related to the column: TAX_REG_CERT_NO
	 * @param taxRegCertNo the TAX_REG_CERT_NO value
	 */
	public void setTaxRegCertNo (java.lang.String taxRegCertNo) {
		this.taxRegCertNo = taxRegCertNo;
	}



	/**
	 * Return the value associated with the column: BUSI_SCOPE
	 */
	public java.lang.String getBusiScope () {
		return busiScope;
	}

	/**
	 * Set the value related to the column: BUSI_SCOPE
	 * @param busiScope the BUSI_SCOPE value
	 */
	public void setBusiScope (java.lang.String busiScope) {
		this.busiScope = busiScope;
	}



	/**
	 * Return the value associated with the column: BUSI
	 */
	public java.lang.String getBusi () {
		return busi;
	}

	/**
	 * Set the value related to the column: BUSI
	 * @param busi the BUSI value
	 */
	public void setBusi (java.lang.String busi) {
		this.busi = busi;
	}



	/**
	 * Return the value associated with the column: INDUSTRY
	 */
	public java.lang.String getIndustry () {
		return industry;
	}

	/**
	 * Set the value related to the column: INDUSTRY
	 * @param industry the INDUSTRY value
	 */
	public void setIndustry (java.lang.String industry) {
		this.industry = industry;
	}



	/**
	 * Return the value associated with the column: REG_DATE
	 */
	public java.lang.String getRegDate () {
		return regDate;
	}

	/**
	 * Set the value related to the column: REG_DATE
	 * @param regDate the REG_DATE value
	 */
	public void setRegDate (java.lang.String regDate) {
		this.regDate = regDate;
	}



	/**
	 * Return the value associated with the column: REG_CAPITAL
	 */
	public java.math.BigDecimal getRegCapital () {
		return regCapital;
	}

	/**
	 * Set the value related to the column: REG_CAPITAL
	 * @param regCapital the REG_CAPITAL value
	 */
	public void setRegCapital (java.math.BigDecimal regCapital) {
		this.regCapital = regCapital;
	}



	/**
	 * Return the value associated with the column: LEG_NATION
	 */
	public java.lang.String getLegNation () {
		return legNation;
	}

	/**
	 * Set the value related to the column: LEG_NATION
	 * @param legNation the LEG_NATION value
	 */
	public void setLegNation (java.lang.String legNation) {
		this.legNation = legNation;
	}



	/**
	 * Return the value associated with the column: LEGAL_NAME
	 */
	public java.lang.String getLegalName () {
		return legalName;
	}

	/**
	 * Set the value related to the column: LEGAL_NAME
	 * @param legalName the LEGAL_NAME value
	 */
	public void setLegalName (java.lang.String legalName) {
		this.legalName = legalName;
	}



	/**
	 * Return the value associated with the column: LEG_CERT
	 */
	public java.lang.String getLegCert () {
		return legCert;
	}

	/**
	 * Set the value related to the column: LEG_CERT
	 * @param legCert the LEG_CERT value
	 */
	public void setLegCert (java.lang.String legCert) {
		this.legCert = legCert;
	}



	/**
	 * Return the value associated with the column: LEGAL_CARD_NO
	 */
	public java.lang.String getLegalCardNo () {
		return legalCardNo;
	}

	/**
	 * Set the value related to the column: LEGAL_CARD_NO
	 * @param legalCardNo the LEGAL_CARD_NO value
	 */
	public void setLegalCardNo (java.lang.String legalCardNo) {
		this.legalCardNo = legalCardNo;
	}



	/**
	 * Return the value associated with the column: AUTHORIZER_NAME
	 */
	public java.lang.String getAuthorizerName () {
		return authorizerName;
	}

	/**
	 * Set the value related to the column: AUTHORIZER_NAME
	 * @param authorizerName the AUTHORIZER_NAME value
	 */
	public void setAuthorizerName (java.lang.String authorizerName) {
		this.authorizerName = authorizerName;
	}



	/**
	 * Return the value associated with the column: AUTHORIZER_ID
	 */
	public java.lang.String getAuthorizerId () {
		return authorizerId;
	}

	/**
	 * Set the value related to the column: AUTHORIZER_ID
	 * @param authorizerId the AUTHORIZER_ID value
	 */
	public void setAuthorizerId (java.lang.String authorizerId) {
		this.authorizerId = authorizerId;
	}



	/**
	 * Return the value associated with the column: BUSI_AREA
	 */
	public java.math.BigDecimal getBusiArea () {
		return busiArea;
	}

	/**
	 * Set the value related to the column: BUSI_AREA
	 * @param busiArea the BUSI_AREA value
	 */
	public void setBusiArea (java.math.BigDecimal busiArea) {
		this.busiArea = busiArea;
	}



	/**
	 * Return the value associated with the column: BUSI_PLACE_TYPE
	 */
	public java.lang.String getBusiPlaceType () {
		return busiPlaceType;
	}

	/**
	 * Set the value related to the column: BUSI_PLACE_TYPE
	 * @param busiPlaceType the BUSI_PLACE_TYPE value
	 */
	public void setBusiPlaceType (java.lang.String busiPlaceType) {
		this.busiPlaceType = busiPlaceType;
	}



	/**
	 * Return the value associated with the column: EMP_COUNT
	 */
	public java.lang.Integer getEmpCount () {
		return empCount;
	}

	/**
	 * Set the value related to the column: EMP_COUNT
	 * @param empCount the EMP_COUNT value
	 */
	public void setEmpCount (java.lang.Integer empCount) {
		this.empCount = empCount;
	}



	/**
	 * Return the value associated with the column: WEB_NAME
	 */
	public java.lang.String getWebName () {
		return webName;
	}

	/**
	 * Set the value related to the column: WEB_NAME
	 * @param webName the WEB_NAME value
	 */
	public void setWebName (java.lang.String webName) {
		this.webName = webName;
	}



	/**
	 * Return the value associated with the column: WEB_URL
	 */
	public java.lang.String getWebUrl () {
		return webUrl;
	}

	/**
	 * Set the value related to the column: WEB_URL
	 * @param webUrl the WEB_URL value
	 */
	public void setWebUrl (java.lang.String webUrl) {
		this.webUrl = webUrl;
	}



	/**
	 * Return the value associated with the column: ICP_NO
	 */
	public java.lang.String getIcpNo () {
		return icpNo;
	}

	/**
	 * Set the value related to the column: ICP_NO
	 * @param icpNo the ICP_NO value
	 */
	public void setIcpNo (java.lang.String icpNo) {
		this.icpNo = icpNo;
	}



	/**
	 * Return the value associated with the column: ICP_INS_NAME
	 */
	public java.lang.String getIcpInsName () {
		return icpInsName;
	}

	/**
	 * Set the value related to the column: ICP_INS_NAME
	 * @param icpInsName the ICP_INS_NAME value
	 */
	public void setIcpInsName (java.lang.String icpInsName) {
		this.icpInsName = icpInsName;
	}



	/**
	 * Return the value associated with the column: ADJUST_USER
	 */
	public java.lang.String getAdjustUser () {
		return adjustUser;
	}

	/**
	 * Set the value related to the column: ADJUST_USER
	 * @param adjustUser the ADJUST_USER value
	 */
	public void setAdjustUser (java.lang.String adjustUser) {
		this.adjustUser = adjustUser;
	}



	/**
	 * Return the value associated with the column: ADJUST_USER_TEL
	 */
	public java.lang.String getAdjustUserTel () {
		return adjustUserTel;
	}

	/**
	 * Set the value related to the column: ADJUST_USER_TEL
	 * @param adjustUserTel the ADJUST_USER_TEL value
	 */
	public void setAdjustUserTel (java.lang.String adjustUserTel) {
		this.adjustUserTel = adjustUserTel;
	}



	/**
	 * Return the value associated with the column: MCHT_TEL
	 */
	public java.lang.String getMchtTel () {
		return mchtTel;
	}

	/**
	 * Set the value related to the column: MCHT_TEL
	 * @param mchtTel the MCHT_TEL value
	 */
	public void setMchtTel (java.lang.String mchtTel) {
		this.mchtTel = mchtTel;
	}



	/**
	 * Return the value associated with the column: MCHT_FAX
	 */
	public java.lang.String getMchtFax () {
		return mchtFax;
	}

	/**
	 * Set the value related to the column: MCHT_FAX
	 * @param mchtFax the MCHT_FAX value
	 */
	public void setMchtFax (java.lang.String mchtFax) {
		this.mchtFax = mchtFax;
	}



	/**
	 * Return the value associated with the column: LEGAL_TEL
	 */
	public java.lang.String getLegalTel () {
		return legalTel;
	}

	/**
	 * Set the value related to the column: LEGAL_TEL
	 * @param legalTel the LEGAL_TEL value
	 */
	public void setLegalTel (java.lang.String legalTel) {
		this.legalTel = legalTel;
	}



	/**
	 * Return the value associated with the column: CONTACTS
	 */
	public java.lang.String getContacts () {
		return contacts;
	}

	/**
	 * Set the value related to the column: CONTACTS
	 * @param contacts the CONTACTS value
	 */
	public void setContacts (java.lang.String contacts) {
		this.contacts = contacts;
	}



	/**
	 * Return the value associated with the column: CONTACTS_PHONE
	 */
	public java.lang.String getContactsPhone () {
		return contactsPhone;
	}

	/**
	 * Set the value related to the column: CONTACTS_PHONE
	 * @param contactsPhone the CONTACTS_PHONE value
	 */
	public void setContactsPhone (java.lang.String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}



	/**
	 * Return the value associated with the column: CONTACTS_TEL
	 */
	public java.lang.String getContactsTel () {
		return contactsTel;
	}

	/**
	 * Set the value related to the column: CONTACTS_TEL
	 * @param contactsTel the CONTACTS_TEL value
	 */
	public void setContactsTel (java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}



	/**
	 * Return the value associated with the column: CONTACTS_EMAIL
	 */
	public java.lang.String getContactsEmail () {
		return contactsEmail;
	}

	/**
	 * Set the value related to the column: CONTACTS_EMAIL
	 * @param contactsEmail the CONTACTS_EMAIL value
	 */
	public void setContactsEmail (java.lang.String contactsEmail) {
		this.contactsEmail = contactsEmail;
	}



	/**
	 * Return the value associated with the column: RCV_PRO_CODE
	 */
	public java.lang.String getRcvProCode () {
		return rcvProCode;
	}

	/**
	 * Set the value related to the column: RCV_PRO_CODE
	 * @param rcvProCode the RCV_PRO_CODE value
	 */
	public void setRcvProCode (java.lang.String rcvProCode) {
		this.rcvProCode = rcvProCode;
	}



	/**
	 * Return the value associated with the column: RCV_CITY_CODE
	 */
	public java.lang.String getRcvCityCode () {
		return rcvCityCode;
	}

	/**
	 * Set the value related to the column: RCV_CITY_CODE
	 * @param rcvCityCode the RCV_CITY_CODE value
	 */
	public void setRcvCityCode (java.lang.String rcvCityCode) {
		this.rcvCityCode = rcvCityCode;
	}



	/**
	 * Return the value associated with the column: RCV_COUNTRY_CODE
	 */
	public java.lang.String getRcvCountryCode () {
		return rcvCountryCode;
	}

	/**
	 * Set the value related to the column: RCV_COUNTRY_CODE
	 * @param rcvCountryCode the RCV_COUNTRY_CODE value
	 */
	public void setRcvCountryCode (java.lang.String rcvCountryCode) {
		this.rcvCountryCode = rcvCountryCode;
	}



	/**
	 * Return the value associated with the column: RCV_ADDR
	 */
	public java.lang.String getRcvAddr () {
		return rcvAddr;
	}

	/**
	 * Set the value related to the column: RCV_ADDR
	 * @param rcvAddr the RCV_ADDR value
	 */
	public void setRcvAddr (java.lang.String rcvAddr) {
		this.rcvAddr = rcvAddr;
	}



	/**
	 * Return the value associated with the column: D_SINGLE_TRANS_LIMIT
	 */
	public java.lang.Integer getdSingleTransLimit () {
		return dSingleTransLimit;
	}

	/**
	 * Set the value related to the column: D_SINGLE_TRANS_LIMIT
	 * @param dSingleTransLimit the D_SINGLE_TRANS_LIMIT value
	 */
	public void setdSingleTransLimit (java.lang.Integer dSingleTransLimit) {
		this.dSingleTransLimit = dSingleTransLimit;
	}



	/**
	 * Return the value associated with the column: D_DAY_TRANS_LIMIT
	 */
	public java.lang.Integer getdDayTransLimit () {
		return dDayTransLimit;
	}

	/**
	 * Set the value related to the column: D_DAY_TRANS_LIMIT
	 * @param dDayTransLimit the D_DAY_TRANS_LIMIT value
	 */
	public void setdDayTransLimit (java.lang.Integer dDayTransLimit) {
		this.dDayTransLimit = dDayTransLimit;
	}



	/**
	 * Return the value associated with the column: D_MON_TRANS_LIMIT
	 */
	public java.lang.Integer getdMonTransLimit () {
		return dMonTransLimit;
	}

	/**
	 * Set the value related to the column: D_MON_TRANS_LIMIT
	 * @param dMonTransLimit the D_MON_TRANS_LIMIT value
	 */
	public void setdMonTransLimit (java.lang.Integer dMonTransLimit) {
		this.dMonTransLimit = dMonTransLimit;
	}



	/**
	 * Return the value associated with the column: D_SINGLE_HIGH_TRANS
	 */
	public java.lang.Integer getdSingleHighTrans () {
		return dSingleHighTrans;
	}

	/**
	 * Set the value related to the column: D_SINGLE_HIGH_TRANS
	 * @param dSingleHighTrans the D_SINGLE_HIGH_TRANS value
	 */
	public void setdSingleHighTrans (java.lang.Integer dSingleHighTrans) {
		this.dSingleHighTrans = dSingleHighTrans;
	}



	/**
	 * Return the value associated with the column: C_SINGLE_TRANS_LIMIT
	 */
	public java.lang.Integer getcSingleTransLimit () {
		return cSingleTransLimit;
	}

	/**
	 * Set the value related to the column: C_SINGLE_TRANS_LIMIT
	 * @param cSingleTransLimit the C_SINGLE_TRANS_LIMIT value
	 */
	public void setcSingleTransLimit (java.lang.Integer cSingleTransLimit) {
		this.cSingleTransLimit = cSingleTransLimit;
	}



	/**
	 * Return the value associated with the column: C_DAY_TRANS_LIMIT
	 */
	public java.lang.Integer getcDayTransLimit () {
		return cDayTransLimit;
	}

	/**
	 * Set the value related to the column: C_DAY_TRANS_LIMIT
	 * @param cDayTransLimit the C_DAY_TRANS_LIMIT value
	 */
	public void setcDayTransLimit (java.lang.Integer cDayTransLimit) {
		this.cDayTransLimit = cDayTransLimit;
	}



	/**
	 * Return the value associated with the column: C_MON_TRANS_LIMIT
	 */
	public java.lang.Integer getcMonTransLimit () {
		return cMonTransLimit;
	}

	/**
	 * Set the value related to the column: C_MON_TRANS_LIMIT
	 * @param cMonTransLimit the C_MON_TRANS_LIMIT value
	 */
	public void setcMonTransLimit (java.lang.Integer cMonTransLimit) {
		this.cMonTransLimit = cMonTransLimit;
	}



	/**
	 * Return the value associated with the column: C_SINGLE_HIGH_TRANS
	 */
	public java.lang.Integer getcSingleHighTrans () {
		return cSingleHighTrans;
	}

	/**
	 * Set the value related to the column: C_SINGLE_HIGH_TRANS
	 * @param cSingleHighTrans the C_SINGLE_HIGH_TRANS value
	 */
	public void setcSingleHighTrans (java.lang.Integer cSingleHighTrans) {
		this.cSingleHighTrans = cSingleHighTrans;
	}



	/**
	 * Return the value associated with the column: FUNC_CREDIT_CARD
	 */
	public java.lang.String getFuncCreditCard () {
		return funcCreditCard;
	}

	/**
	 * Set the value related to the column: FUNC_CREDIT_CARD
	 * @param funcCreditCard the FUNC_CREDIT_CARD value
	 */
	public void setFuncCreditCard (java.lang.String funcCreditCard) {
		this.funcCreditCard = funcCreditCard;
	}



	/**
	 * Return the value associated with the column: FUNC_PREAUTH
	 */
	public java.lang.String getFuncPreauth () {
		return funcPreauth;
	}

	/**
	 * Set the value related to the column: FUNC_PREAUTH
	 * @param funcPreauth the FUNC_PREAUTH value
	 */
	public void setFuncPreauth (java.lang.String funcPreauth) {
		this.funcPreauth = funcPreauth;
	}



	/**
	 * Return the value associated with the column: FUNC_HOLIDAY_WITHDRAW
	 */
	public java.lang.String getFuncHolidayWithdraw () {
		return funcHolidayWithdraw;
	}

	/**
	 * Set the value related to the column: FUNC_HOLIDAY_WITHDRAW
	 * @param funcHolidayWithdraw the FUNC_HOLIDAY_WITHDRAW value
	 */
	public void setFuncHolidayWithdraw (java.lang.String funcHolidayWithdraw) {
		this.funcHolidayWithdraw = funcHolidayWithdraw;
	}



	/**
	 * Return the value associated with the column: FUNC_RETURN
	 */
	public java.lang.String getFuncReturn () {
		return funcReturn;
	}

	/**
	 * Set the value related to the column: FUNC_RETURN
	 * @param funcReturn the FUNC_RETURN value
	 */
	public void setFuncReturn (java.lang.String funcReturn) {
		this.funcReturn = funcReturn;
	}



	/**
	 * Return the value associated with the column: FUNC_AUTO_WITHDRAW
	 */
	public java.lang.String getFuncAutoWithdraw () {
		return funcAutoWithdraw;
	}

	/**
	 * Set the value related to the column: FUNC_AUTO_WITHDRAW
	 * @param funcAutoWithdraw the FUNC_AUTO_WITHDRAW value
	 */
	public void setFuncAutoWithdraw (java.lang.String funcAutoWithdraw) {
		this.funcAutoWithdraw = funcAutoWithdraw;
	}



	/**
	 * Return the value associated with the column: FUNC_RETURN_WITH_FEE
	 */
	public java.lang.String getFuncReturnWithFee () {
		return funcReturnWithFee;
	}

	/**
	 * Set the value related to the column: FUNC_RETURN_WITH_FEE
	 * @param funcReturnWithFee the FUNC_RETURN_WITH_FEE value
	 */
	public void setFuncReturnWithFee (java.lang.String funcReturnWithFee) {
		this.funcReturnWithFee = funcReturnWithFee;
	}



	/**
	 * Return the value associated with the column: FUNC_POS_IN_EXP
	 */
	public java.lang.String getFuncPosInExp () {
		return funcPosInExp;
	}

	/**
	 * Set the value related to the column: FUNC_POS_IN_EXP
	 * @param funcPosInExp the FUNC_POS_IN_EXP value
	 */
	public void setFuncPosInExp (java.lang.String funcPosInExp) {
		this.funcPosInExp = funcPosInExp;
	}



	/**
	 * Return the value associated with the column: FUNC_POS_OUT_EXP
	 */
	public java.lang.String getFuncPosOutExp () {
		return funcPosOutExp;
	}

	/**
	 * Set the value related to the column: FUNC_POS_OUT_EXP
	 * @param funcPosOutExp the FUNC_POS_OUT_EXP value
	 */
	public void setFuncPosOutExp (java.lang.String funcPosOutExp) {
		this.funcPosOutExp = funcPosOutExp;
	}



	/**
	 * Return the value associated with the column: FUNC_POS_OUT
	 */
	public java.lang.String getFuncPosOut () {
		return funcPosOut;
	}

	/**
	 * Set the value related to the column: FUNC_POS_OUT
	 * @param funcPosOut the FUNC_POS_OUT value
	 */
	public void setFuncPosOut (java.lang.String funcPosOut) {
		this.funcPosOut = funcPosOut;
	}



	/**
	 * Return the value associated with the column: EXPANDER
	 */
	public java.lang.String getExpander () {
		return expander;
	}

	/**
	 * Set the value related to the column: EXPANDER
	 * @param expander the EXPANDER value
	 */
	public void setExpander (java.lang.String expander) {
		this.expander = expander;
	}



	/**
	 * Return the value associated with the column: EXPANDER_TEL
	 */
	public java.lang.String getExpanderTel () {
		return expanderTel;
	}

	/**
	 * Set the value related to the column: EXPANDER_TEL
	 * @param expanderTel the EXPANDER_TEL value
	 */
	public void setExpanderTel (java.lang.String expanderTel) {
		this.expanderTel = expanderTel;
	}



	/**
	 * Return the value associated with the column: BUSI_START_TIME
	 */
	public java.lang.String getBusiStartTime () {
		return busiStartTime;
	}

	/**
	 * Set the value related to the column: BUSI_START_TIME
	 * @param busiStartTime the BUSI_START_TIME value
	 */
	public void setBusiStartTime (java.lang.String busiStartTime) {
		this.busiStartTime = busiStartTime;
	}



	/**
	 * Return the value associated with the column: BUSI_END_TIME
	 */
	public java.lang.String getBusiEndTime () {
		return busiEndTime;
	}

	/**
	 * Set the value related to the column: BUSI_END_TIME
	 * @param busiEndTime the BUSI_END_TIME value
	 */
	public void setBusiEndTime (java.lang.String busiEndTime) {
		this.busiEndTime = busiEndTime;
	}



	/**
	 * Return the value associated with the column: AGENT_NO
	 */
	public java.lang.String getAgentNo () {
		return agentNo;
	}

	/**
	 * Set the value related to the column: AGENT_NO
	 * @param agentNo the AGENT_NO value
	 */
	public void setAgentNo (java.lang.String agentNo) {
		this.agentNo = agentNo;
	}



	/**
	 * Return the value associated with the column: APPLY_DESC
	 */
	public java.lang.String getApplyDesc () {
		return applyDesc;
	}

	/**
	 * Set the value related to the column: APPLY_DESC
	 * @param applyDesc the APPLY_DESC value
	 */
	public void setApplyDesc (java.lang.String applyDesc) {
		this.applyDesc = applyDesc;
	}



	/**
	 * Return the value associated with the column: SETTLE_ACC_TYPE
	 */
	public java.lang.String getSettleAccType () {
		return settleAccType;
	}

	/**
	 * Set the value related to the column: SETTLE_ACC_TYPE
	 * @param settleAccType the SETTLE_ACC_TYPE value
	 */
	public void setSettleAccType (java.lang.String settleAccType) {
		this.settleAccType = settleAccType;
	}



	/**
	 * Return the value associated with the column: MCHNT_TP_GRP
	 */
	public java.lang.String getMchntTpGrp () {
		return mchntTpGrp;
	}

	/**
	 * Set the value related to the column: MCHNT_TP_GRP
	 * @param mchntTpGrp the MCHNT_TP_GRP value
	 */
	public void setMchntTpGrp (java.lang.String mchntTpGrp) {
		this.mchntTpGrp = mchntTpGrp;
	}



	/**
	 * Return the value associated with the column: MCC
	 */
	public java.lang.String getMcc () {
		return mcc;
	}

	/**
	 * Set the value related to the column: MCC
	 * @param mcc the MCC value
	 */
	public void setMcc (java.lang.String mcc) {
		this.mcc = mcc;
	}



	/**
	 * Return the value associated with the column: BUSI_USER
	 */
	public java.lang.String getBusiUser () {
		return busiUser;
	}

	/**
	 * Set the value related to the column: BUSI_USER
	 * @param busiUser the BUSI_USER value
	 */
	public void setBusiUser (java.lang.String busiUser) {
		this.busiUser = busiUser;
	}



	/**
	 * Return the value associated with the column: BUSI_USER_TEL
	 */
	public java.lang.String getBusiUserTel () {
		return busiUserTel;
	}

	/**
	 * Set the value related to the column: BUSI_USER_TEL
	 * @param busiUserTel the BUSI_USER_TEL value
	 */
	public void setBusiUserTel (java.lang.String busiUserTel) {
		this.busiUserTel = busiUserTel;
	}



	/**
	 * Return the value associated with the column: LIC_START_DATE
	 */
	public java.lang.String getLicStartDate () {
		return licStartDate;
	}

	/**
	 * Set the value related to the column: LIC_START_DATE
	 * @param licStartDate the LIC_START_DATE value
	 */
	public void setLicStartDate (java.lang.String licStartDate) {
		this.licStartDate = licStartDate;
	}



	/**
	 * Return the value associated with the column: LIC_END_DATE
	 */
	public java.lang.String getLicEndDate () {
		return licEndDate;
	}

	/**
	 * Set the value related to the column: LIC_END_DATE
	 * @param licEndDate the LIC_END_DATE value
	 */
	public void setLicEndDate (java.lang.String licEndDate) {
		this.licEndDate = licEndDate;
	}



	/**
	 * Return the value associated with the column: AUTHORIZER_TEL
	 */
	public java.lang.String getAuthorizerTel () {
		return authorizerTel;
	}

	/**
	 * Set the value related to the column: AUTHORIZER_TEL
	 * @param authorizerTel the AUTHORIZER_TEL value
	 */
	public void setAuthorizerTel (java.lang.String authorizerTel) {
		this.authorizerTel = authorizerTel;
	}



	/**
	 * Return the value associated with the column: AVE_MONTH_INCOME
	 */
	public java.lang.Integer getAveMonthIncome () {
		return aveMonthIncome;
	}

	/**
	 * Set the value related to the column: AVE_MONTH_INCOME
	 * @param aveMonthIncome the AVE_MONTH_INCOME value
	 */
	public void setAveMonthIncome (java.lang.Integer aveMonthIncome) {
		this.aveMonthIncome = aveMonthIncome;
	}



	/**
	 * Return the value associated with the column: SETTLE_INTERVAL
	 */
	public java.lang.String getSettleInterval () {
		return settleInterval;
	}

	/**
	 * Set the value related to the column: SETTLE_INTERVAL
	 * @param settleInterval the SETTLE_INTERVAL value
	 */
	public void setSettleInterval (java.lang.String settleInterval) {
		this.settleInterval = settleInterval;
	}



	/**
	 * Return the value associated with the column: MCHT_SETTLE_TYPE
	 */
	public java.lang.Integer getMchtSettleType () {
		return mchtSettleType;
	}

	/**
	 * Set the value related to the column: MCHT_SETTLE_TYPE
	 * @param mchtSettleType the MCHT_SETTLE_TYPE value
	 */
	public void setMchtSettleType (java.lang.Integer mchtSettleType) {
		this.mchtSettleType = mchtSettleType;
	}



	/**
	 * Return the value associated with the column: PUB_ACC_BANK
	 */
	public java.lang.String getPubAccBank () {
		return pubAccBank;
	}

	/**
	 * Set the value related to the column: PUB_ACC_BANK
	 * @param pubAccBank the PUB_ACC_BANK value
	 */
	public void setPubAccBank (java.lang.String pubAccBank) {
		this.pubAccBank = pubAccBank;
	}



	/**
	 * Return the value associated with the column: PUB_ACC_BRANCH
	 */
	public java.lang.String getPubAccBranch () {
		return pubAccBranch;
	}

	/**
	 * Set the value related to the column: PUB_ACC_BRANCH
	 * @param pubAccBranch the PUB_ACC_BRANCH value
	 */
	public void setPubAccBranch (java.lang.String pubAccBranch) {
		this.pubAccBranch = pubAccBranch;
	}



	/**
	 * Return the value associated with the column: PUB_ACC_BRANCH_NM
	 */
	public java.lang.String getPubAccBranchNm () {
		return pubAccBranchNm;
	}

	/**
	 * Set the value related to the column: PUB_ACC_BRANCH_NM
	 * @param pubAccBranchNm the PUB_ACC_BRANCH_NM value
	 */
	public void setPubAccBranchNm (java.lang.String pubAccBranchNm) {
		this.pubAccBranchNm = pubAccBranchNm;
	}



	/**
	 * Return the value associated with the column: PUB_ACC_NAME
	 */
	public java.lang.String getPubAccName () {
		return pubAccName;
	}

	/**
	 * Set the value related to the column: PUB_ACC_NAME
	 * @param pubAccName the PUB_ACC_NAME value
	 */
	public void setPubAccName (java.lang.String pubAccName) {
		this.pubAccName = pubAccName;
	}



	/**
	 * Return the value associated with the column: PUB_ACC_NO
	 */
	public java.lang.String getPubAccNo () {
		return pubAccNo;
	}

	/**
	 * Set the value related to the column: PUB_ACC_NO
	 * @param pubAccNo the PUB_ACC_NO value
	 */
	public void setPubAccNo (java.lang.String pubAccNo) {
		this.pubAccNo = pubAccNo;
	}



	/**
	 * Return the value associated with the column: PERS_ACC_BANK
	 */
	public java.lang.String getPersAccBank () {
		return persAccBank;
	}

	/**
	 * Set the value related to the column: PERS_ACC_BANK
	 * @param persAccBank the PERS_ACC_BANK value
	 */
	public void setPersAccBank (java.lang.String persAccBank) {
		this.persAccBank = persAccBank;
	}



	/**
	 * Return the value associated with the column: PERS_ACC_BRANCH
	 */
	public java.lang.String getPersAccBranch () {
		return persAccBranch;
	}

	/**
	 * Set the value related to the column: PERS_ACC_BRANCH
	 * @param persAccBranch the PERS_ACC_BRANCH value
	 */
	public void setPersAccBranch (java.lang.String persAccBranch) {
		this.persAccBranch = persAccBranch;
	}



	/**
	 * Return the value associated with the column: PERS_ACC_BRANCH_NM
	 */
	public java.lang.String getPersAccBranchNm () {
		return persAccBranchNm;
	}

	/**
	 * Set the value related to the column: PERS_ACC_BRANCH_NM
	 * @param persAccBranchNm the PERS_ACC_BRANCH_NM value
	 */
	public void setPersAccBranchNm (java.lang.String persAccBranchNm) {
		this.persAccBranchNm = persAccBranchNm;
	}



	/**
	 * Return the value associated with the column: PERS_ACC_NAME
	 */
	public java.lang.String getPersAccName () {
		return persAccName;
	}

	/**
	 * Set the value related to the column: PERS_ACC_NAME
	 * @param persAccName the PERS_ACC_NAME value
	 */
	public void setPersAccName (java.lang.String persAccName) {
		this.persAccName = persAccName;
	}



	/**
	 * Return the value associated with the column: PERS_ACC_NO
	 */
	public java.lang.String getPersAccNo () {
		return persAccNo;
	}

	/**
	 * Set the value related to the column: PERS_ACC_NO
	 * @param persAccNo the PERS_ACC_NO value
	 */
	public void setPersAccNo (java.lang.String persAccNo) {
		this.persAccNo = persAccNo;
	}



	/**
	 * Return the value associated with the column: LEG_ACC_BANK
	 */
	public java.lang.String getLegAccBank () {
		return legAccBank;
	}

	/**
	 * Set the value related to the column: LEG_ACC_BANK
	 * @param legAccBank the LEG_ACC_BANK value
	 */
	public void setLegAccBank (java.lang.String legAccBank) {
		this.legAccBank = legAccBank;
	}



	/**
	 * Return the value associated with the column: LEG_ACC_BRANCH
	 */
	public java.lang.String getLegAccBranch () {
		return legAccBranch;
	}

	/**
	 * Set the value related to the column: LEG_ACC_BRANCH
	 * @param legAccBranch the LEG_ACC_BRANCH value
	 */
	public void setLegAccBranch (java.lang.String legAccBranch) {
		this.legAccBranch = legAccBranch;
	}



	/**
	 * Return the value associated with the column: LEG_ACC_BRANCH_NM
	 */
	public java.lang.String getLegAccBranchNm () {
		return legAccBranchNm;
	}

	/**
	 * Set the value related to the column: LEG_ACC_BRANCH_NM
	 * @param legAccBranchNm the LEG_ACC_BRANCH_NM value
	 */
	public void setLegAccBranchNm (java.lang.String legAccBranchNm) {
		this.legAccBranchNm = legAccBranchNm;
	}



	/**
	 * Return the value associated with the column: LEG_ACC_NAME
	 */
	public java.lang.String getLegAccName () {
		return legAccName;
	}

	/**
	 * Set the value related to the column: LEG_ACC_NAME
	 * @param legAccName the LEG_ACC_NAME value
	 */
	public void setLegAccName (java.lang.String legAccName) {
		this.legAccName = legAccName;
	}



	/**
	 * Return the value associated with the column: LEG_ACC_NO
	 */
	public java.lang.String getLegAccNo () {
		return legAccNo;
	}

	/**
	 * Set the value related to the column: LEG_ACC_NO
	 * @param legAccNo the LEG_ACC_NO value
	 */
	public void setLegAccNo (java.lang.String legAccNo) {
		this.legAccNo = legAccNo;
	}



	/**
	 * Return the value associated with the column: POS_CNT
	 */
	public java.lang.Integer getPosCnt () {
		return posCnt;
	}

	/**
	 * Set the value related to the column: POS_CNT
	 * @param posCnt the POS_CNT value
	 */
	public void setPosCnt (java.lang.Integer posCnt) {
		this.posCnt = posCnt;
	}



	/**
	 * Return the value associated with the column: PAY_CARD_TYPE
	 */
	public java.lang.String getPayCardType () {
		return payCardType;
	}

	/**
	 * Set the value related to the column: PAY_CARD_TYPE
	 * @param payCardType the PAY_CARD_TYPE value
	 */
	public void setPayCardType (java.lang.String payCardType) {
		this.payCardType = payCardType;
	}



	/**
	 * Return the value associated with the column: DISC_NO
	 */
	public java.lang.String getDiscNo () {
		return discNo;
	}

	/**
	 * Set the value related to the column: DISC_NO
	 * @param discNo the DISC_NO value
	 */
	public void setDiscNo (java.lang.String discNo) {
		this.discNo = discNo;
	}



	/**
	 * Return the value associated with the column: REAL_FEE_PERCENT
	 */
	public java.lang.String getRealFeePercent () {
		return realFeePercent;
	}

	/**
	 * Set the value related to the column: REAL_FEE_PERCENT
	 * @param realFeePercent the REAL_FEE_PERCENT value
	 */
	public void setRealFeePercent (java.lang.String realFeePercent) {
		this.realFeePercent = realFeePercent;
	}



	/**
	 * Return the value associated with the column: RESERVE1
	 */
	public java.lang.String getReserve1 () {
		return reserve1;
	}

	/**
	 * Set the value related to the column: RESERVE1
	 * @param reserve1 the RESERVE1 value
	 */
	public void setReserve1 (java.lang.String reserve1) {
		this.reserve1 = reserve1;
	}



	/**
	 * Return the value associated with the column: RESERVE2
	 */
	public java.lang.String getReserve2 () {
		return reserve2;
	}

	/**
	 * Set the value related to the column: RESERVE2
	 * @param reserve2 the RESERVE2 value
	 */
	public void setReserve2 (java.lang.String reserve2) {
		this.reserve2 = reserve2;
	}



	/**
	 * Return the value associated with the column: RESERVE3
	 */
	public java.lang.String getReserve3 () {
		return reserve3;
	}

	/**
	 * Set the value related to the column: RESERVE3
	 * @param reserve3 the RESERVE3 value
	 */
	public void setReserve3 (java.lang.String reserve3) {
		this.reserve3 = reserve3;
	}



	/**
	 * Return the value associated with the column: RESERVE4
	 */
	public java.lang.String getReserve4 () {
		return reserve4;
	}

	/**
	 * Set the value related to the column: RESERVE4
	 * @param reserve4 the RESERVE4 value
	 */
	public void setReserve4 (java.lang.String reserve4) {
		this.reserve4 = reserve4;
	}



	/**
	 * Return the value associated with the column: RESERVE5
	 */
	public java.lang.String getReserve5 () {
		return reserve5;
	}

	/**
	 * Set the value related to the column: RESERVE5
	 * @param reserve5 the RESERVE5 value
	 */
	public void setReserve5 (java.lang.String reserve5) {
		this.reserve5 = reserve5;
	}



	/**
	 * Return the value associated with the column: RESERVE6
	 */
	public java.lang.String getReserve6 () {
		return reserve6;
	}

	/**
	 * Set the value related to the column: RESERVE6
	 * @param reserve6 the RESERVE6 value
	 */
	public void setReserve6 (java.lang.String reserve6) {
		this.reserve6 = reserve6;
	}



	/**
	 * Return the value associated with the column: RESERVE7
	 */
	public java.lang.String getReserve7 () {
		return reserve7;
	}

	/**
	 * Set the value related to the column: RESERVE7
	 * @param reserve7 the RESERVE7 value
	 */
	public void setReserve7 (java.lang.String reserve7) {
		this.reserve7 = reserve7;
	}



	/**
	 * Return the value associated with the column: RESERVE8
	 */
	public java.lang.String getReserve8 () {
		return reserve8;
	}

	/**
	 * Set the value related to the column: RESERVE8
	 * @param reserve8 the RESERVE8 value
	 */
	public void setReserve8 (java.lang.String reserve8) {
		this.reserve8 = reserve8;
	}



	/**
	 * Return the value associated with the column: RESERVE9
	 */
	public java.lang.String getReserve9 () {
		return reserve9;
	}

	/**
	 * Set the value related to the column: RESERVE9
	 * @param reserve9 the RESERVE9 value
	 */
	public void setReserve9 (java.lang.String reserve9) {
		this.reserve9 = reserve9;
	}



	/**
	 * Return the value associated with the column: RESERVE10
	 */
	public java.lang.String getReserve10 () {
		return reserve10;
	}

	/**
	 * Set the value related to the column: RESERVE10
	 * @param reserve10 the RESERVE10 value
	 */
	public void setReserve10 (java.lang.String reserve10) {
		this.reserve10 = reserve10;
	}



	/**
	 * Return the value associated with the column: STATUS
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS
	 * @param status the STATUS value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: USEABLE
	 */
	public java.lang.String getUseable () {
		return useable;
	}

	/**
	 * Set the value related to the column: USEABLE
	 * @param useable the USEABLE value
	 */
	public void setUseable (java.lang.String useable) {
		this.useable = useable;
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
	 * Return the value associated with the column: CRT_DATE
	 */
	public java.lang.String getCrtDate () {
		return crtDate;
	}

	/**
	 * Set the value related to the column: CRT_DATE
	 * @param crtDate the CRT_DATE value
	 */
	public void setCrtDate (java.lang.String crtDate) {
		this.crtDate = crtDate;
	}



	/**
	 * Return the value associated with the column: UPT_DATE
	 */
	public java.lang.String getUptDate () {
		return uptDate;
	}

	/**
	 * Set the value related to the column: UPT_DATE
	 * @param uptDate the UPT_DATE value
	 */
	public void setUptDate (java.lang.String uptDate) {
		this.uptDate = uptDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblDefMchtInf)) return false;
		else {
			com.huateng.po.mchnt.TblDefMchtInf tblDefMchtInf = (com.huateng.po.mchnt.TblDefMchtInf) obj;
			if (null == this.getRecId() || null == tblDefMchtInf.getRecId()) return false;
			else return (this.getRecId().equals(tblDefMchtInf.getRecId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getRecId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getRecId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}