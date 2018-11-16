package com.huateng.po.base;

import java.io.Serializable;

	public  class AgencyInfoTrue implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public AgencyInfoTrue(){}
	public AgencyInfoTrue(
			AgencyInfoTrue info ,
			String agentypeName,
			String trantypename,
			String cardhomename,
			String agenregbodyhome,
			String agenmechcaltypename,
			String agencaltypename,
			String agencalprincyclename,
			String agencalprinmodename,
		    String agencalhandcyclename,
			String agencalhandmodelname,
			String agencallubcyclename,
	    	String agencallubmodename,
		    String agenentrymodename,
		    String agencleardetailname,
		    String yinlian,
		    String visa,
		    String Master,
			String JCB,
		    String DinersClub,
			String AmericanExpress) 
	{
//		this.AGEN_ID = info.getAGEN_ID();
		this.AGEN_NAME=info.getAGEN_NAME();
		this.AGEN_TYPE=info.getAGEN_TYPE();
//		this.TRAN_TYPE=info.getTRAN_TYPE();
		this.CARD_HOME=info.getCARD_HOME();
		this.AGEN_REG_BODY=info.getAGEN_REG_BODY();
		this.AGEN_MECH_CAL_TYPE=info.getAGEN_MECH_CAL_TYPE();
		this.AGEN_CAL_TYPE=info.getAGEN_CAL_TYPE();
		this.AGEN_CAL_PRIN_CYCLE=info.getAGEN_CAL_PRIN_CYCLE();
		this.AGEN_CAL_PRIN_MODE=info.getAGEN_CAL_PRIN_MODE();
		this.AGEN_CAL_HAND_CYCLE=info.getAGEN_CAL_HAND_CYCLE();
		this.AGEN_CAL_HAND_MODE=info.getAGEN_CAL_HAND_MODE();
		this.AGEN_CAL_LUB_CYCLE=info.getAGEN_CAL_LUB_CYCLE();
		this.AGEN_CAL_LUB_MODE=info.getAGEN_CAL_LUB_MODE();
		this.AGEN_BRAND_RATIO=info.getAGEN_BRAND_RATIO();
		this.AGEN_MIS_RATIO=info.getAGEN_MIS_RATIO();
		this.AGEN_BANK_NUM=info.getAGEN_BANK_NUM();
		this.AGEN_ENTRY_MODE=info.getAGEN_ENTRY_MODE();
		this.BANK_NAME=info.getBANK_NAME();
		this.AGEN_INCOME_ACCOUNT_NAME=info.getAGEN_INCOME_ACCOUNT_NAME();
		this.AGEN_INCOME_ACCOUNT=info.getAGEN_INCOME_ACCOUNT();
		this.AGEN_EXPENSES_ACCOUNT_NAME=info.getAGEN_EXPENSES_ACCOUNT_NAME();
		this.AGEN_EXPENSES_ACCOUNT=info.getAGEN_EXPENSES_ACCOUNT();
		this.AGEN_SETTLEMENT_DATE=info.getAGEN_SETTLEMENT_DATE();
		this.AGEN_CLEAR_DETAIL=info.getAGEN_CLEAR_DETAIL();
		
	    this.AGEN_PAYMENT_SYSTEM=info.getAGEN_PAYMENT_SYSTEM(); 
		this.agentypeName = agentypeName;//机构类型
		this.trantypename=trantypename;//交易连接类型名称
		this.cardhomename=cardhomename;//归属卡组织
		this.agenregbodyhome=agenregbodyhome;//机构所属地区
		this.agenmechcaltypename=agenmechcaltypename;//机构所辖商户清算方式
		this.agencaltypename=agencaltypename;//机构清算方式
		this.agencalprincyclename=agencalprincyclename;//机构本金清算周期
		this.agencalprinmodename=agencalprinmodename;//机构本金清算模式
	    this.agencalhandcyclename=agencalhandcyclename;//机构手续费清算周期
		this.agencalhandmodelname=agencalhandmodelname;//机构手续费清算模式
		this.agencallubcyclename=agencallubcyclename;//机构分润清算周期
		this.agencallubmodename=agencallubmodename;//机构分润清算模式
		this.agenentrymodename=agenentrymodename;//机构出入账模式
		this.agencleardetailname=agencleardetailname;//机构清分明细是否生成
		this.yinlian=yinlian;
		this.visa=visa;
		this.Master=Master;
		this.JCB=JCB;
		this.DinersClub=DinersClub;
		this.AmericanExpress=AmericanExpress;
	}
	/*public  String AGEN_ID;//机构号
	public String getAGEN_ID() {
		return AGEN_ID;
	}
	public void setAGEN_ID(String aGENID) {
		AGEN_ID = aGENID;
	}*/
	public String getAGEN_NAME() {
		return AGEN_NAME;
	}
	public void setAGEN_NAME(String aGENNAME) {
		AGEN_NAME = aGENNAME;
	}
	public String getAGEN_TYPE() {
		return AGEN_TYPE;
	}
	public void setAGEN_TYPE(String aGENTYPE) {
		AGEN_TYPE = aGENTYPE;
	}
/*	public String getTRAN_TYPE() {
		return TRAN_TYPE;
	}
	public void setTRAN_TYPE(String tRANTYPE) {
		TRAN_TYPE = tRANTYPE;
	}*/
	public String getCARD_HOME() {
		return CARD_HOME;
	}
	public void setCARD_HOME(String cARDHOME) {
		CARD_HOME = cARDHOME;
	}
	public String getAGEN_REG_BODY() {
		return AGEN_REG_BODY;
	}
	public void setAGEN_REG_BODY(String aGENREGBODY) {
		AGEN_REG_BODY = aGENREGBODY;
	}
	public String getAGEN_MECH_CAL_TYPE() {
		return AGEN_MECH_CAL_TYPE;
	}
	public void setAGEN_MECH_CAL_TYPE(String aGENMECHCALTYPE) {
		AGEN_MECH_CAL_TYPE = aGENMECHCALTYPE;
	}
	public String getAGEN_CAL_TYPE() {
		return AGEN_CAL_TYPE;
	}
	public void setAGEN_CAL_TYPE(String aGENCALTYPE) {
		AGEN_CAL_TYPE = aGENCALTYPE;
	}
	public String getAGEN_CAL_PRIN_CYCLE() {
		return AGEN_CAL_PRIN_CYCLE;
	}
	public void setAGEN_CAL_PRIN_CYCLE(String aGENCALPRINCYCLE) {
		AGEN_CAL_PRIN_CYCLE = aGENCALPRINCYCLE;
	}
	public String getAGEN_CAL_PRIN_MODE() {
		return AGEN_CAL_PRIN_MODE;
	}
	public void setAGEN_CAL_PRIN_MODE(String aGENCALPRINMODE) {
		AGEN_CAL_PRIN_MODE = aGENCALPRINMODE;
	}
	public String getAGEN_CAL_HAND_CYCLE() {
		return AGEN_CAL_HAND_CYCLE;
	}
	public void setAGEN_CAL_HAND_CYCLE(String aGENCALHANDCYCLE) {
		AGEN_CAL_HAND_CYCLE = aGENCALHANDCYCLE;
	}
	public String getAGEN_CAL_HAND_MODE() {
		return AGEN_CAL_HAND_MODE;
	}
	public void setAGEN_CAL_HAND_MODE(String aGENCALHANDMODE) {
		AGEN_CAL_HAND_MODE = aGENCALHANDMODE;
	}
	public String getAGEN_CAL_LUB_CYCLE() {
		return AGEN_CAL_LUB_CYCLE;
	}
	public void setAGEN_CAL_LUB_CYCLE(String aGENCALLUBCYCLE) {
		AGEN_CAL_LUB_CYCLE = aGENCALLUBCYCLE;
	}
	public String getAGEN_CAL_LUB_MODE() {
		return AGEN_CAL_LUB_MODE;
	}
	public void setAGEN_CAL_LUB_MODE(String aGENCALLUBMODE) {
		AGEN_CAL_LUB_MODE = aGENCALLUBMODE;
	}
	public String getAGEN_BRAND_RATIO() {
		return AGEN_BRAND_RATIO;
	}
	public void setAGEN_BRAND_RATIO(String aGENBRANDRATIO) {
		AGEN_BRAND_RATIO = aGENBRANDRATIO;
	}
	public String getAGEN_MIS_RATIO() {
		return AGEN_MIS_RATIO;
	}
	public void setAGEN_MIS_RATIO(String aGENMISRATIO) {
		AGEN_MIS_RATIO = aGENMISRATIO;
	}
	public String getAGEN_BANK_NUM() {
		return AGEN_BANK_NUM;
	}
	public void setAGEN_BANK_NUM(String aGENBANKNUM) {
		AGEN_BANK_NUM = aGENBANKNUM;
	}
	public String getAGEN_ENTRY_MODE() {
		return AGEN_ENTRY_MODE;
	}
	public void setAGEN_ENTRY_MODE(String aGENENTRYMODE) {
		AGEN_ENTRY_MODE = aGENENTRYMODE;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANKNAME) {
		BANK_NAME = bANKNAME;
	}
	public String getAGEN_INCOME_ACCOUNT_NAME() {
		return AGEN_INCOME_ACCOUNT_NAME;
	}
	public void setAGEN_INCOME_ACCOUNT_NAME(String aGENINCOMEACCOUNTNAME) {
		AGEN_INCOME_ACCOUNT_NAME = aGENINCOMEACCOUNTNAME;
	}
	public String getAGEN_INCOME_ACCOUNT() {
		return AGEN_INCOME_ACCOUNT;
	}
	public void setAGEN_INCOME_ACCOUNT(String aGENINCOMEACCOUNT) {
		AGEN_INCOME_ACCOUNT = aGENINCOMEACCOUNT;
	}
	public String getAGEN_EXPENSES_ACCOUNT_NAME() {
		return AGEN_EXPENSES_ACCOUNT_NAME;
	}
	public void setAGEN_EXPENSES_ACCOUNT_NAME(String aGENEXPENSESACCOUNTNAME) {
		AGEN_EXPENSES_ACCOUNT_NAME = aGENEXPENSESACCOUNTNAME;
	}
	public String getAGEN_EXPENSES_ACCOUNT() {
		return AGEN_EXPENSES_ACCOUNT;
	}
	public void setAGEN_EXPENSES_ACCOUNT(String aGENEXPENSESACCOUNT) {
		AGEN_EXPENSES_ACCOUNT = aGENEXPENSESACCOUNT;
	}
	public String getAGEN_SETTLEMENT_DATE() {
		return AGEN_SETTLEMENT_DATE;
	}
	public void setAGEN_SETTLEMENT_DATE(String aGENSETTLEMENTDATE) {
		AGEN_SETTLEMENT_DATE = aGENSETTLEMENTDATE;
	}
	public String getAGEN_CLEAR_DETAIL() {
		return AGEN_CLEAR_DETAIL;
	}
	public void setAGEN_CLEAR_DETAIL(String aGENCLEARDETAIL) {
		AGEN_CLEAR_DETAIL = aGENCLEARDETAIL;
	}
	public String getAGEN_PAYMENT_SYSTEM() {
		return AGEN_PAYMENT_SYSTEM;
	}
	public void setAGEN_PAYMENT_SYSTEM(String aGENPAYMENTSYSTEM) {
		AGEN_PAYMENT_SYSTEM = aGENPAYMENTSYSTEM;
	}
	
	private AgencyInfoTruePK agencyInfoTruePK;
	public  String AGEN_NAME;//机构名称
	public  String AGEN_TYPE;//机构类型
//	public  String TRAN_TYPE;//交易联接类型
	public  String CARD_HOME;//归属卡组织
	public  String AGEN_REG_BODY;//机构所属地区
	public  String AGEN_MECH_CAL_TYPE;//机构所辖商户清算方式
	public  String AGEN_CAL_TYPE;//机构清算方式
	public  String AGEN_CAL_PRIN_CYCLE;//机构本金清算周期
	public  String AGEN_CAL_PRIN_MODE;//机构本金清算模式
	public  String AGEN_CAL_HAND_CYCLE;//机构手续费清算周期
	public  String AGEN_CAL_HAND_MODE;//机构手续费清算模式
	public  String AGEN_CAL_LUB_CYCLE;//机构分润清算周期
	public  String AGEN_CAL_LUB_MODE;//机构分润清算模式
	public  String AGEN_BRAND_RATIO;//机构品牌服务费承担比例
	public  String AGEN_MIS_RATIO;//机构差错费承担比例
	public  String AGEN_BANK_NUM;//机构开户行机构号
	public  String AGEN_ENTRY_MODE;//机构出入账模式
	public  String BANK_NAME;//开户行名称
	public  String AGEN_INCOME_ACCOUNT_NAME;//机构收入账号开户名
	public  String AGEN_INCOME_ACCOUNT;//机构收入账号
	public  String AGEN_EXPENSES_ACCOUNT_NAME;//机构支出账号开户名
	public  String AGEN_EXPENSES_ACCOUNT;//机构支出账号
	public String AGEN_SETTLEMENT_DATE;//机构当前清算日期
	public String AGEN_CLEAR_DETAIL;//机构清分明细是否生成
	public String AGEN_PAYMENT_SYSTEM;//机构支付系统行号
	public String STATUE;//审核状态
	public String CRE_OPR_ID;//添加信息的人
	public String CRE_OPR_DATE;//添加的时间
	public String UP_OPR_ID;//修改信息的人
	public String UP_OPR_DATE;//修改时间
	
	public String FEE_FLAG;//费率方式 
	public double FEE_VALUE;//费率值
	public double MIN_FEE;//费率下限值
	public double MAX_FEE;//费率上限值
	public double MIN_TRADE;//最低交易金额
	
	
	
	
	
	public AgencyInfoTruePK getAgencyInfoTruePK() {
		return agencyInfoTruePK;
	}
	public void setAgencyInfoTruePK(AgencyInfoTruePK agencyInfoTruePK) {
		this.agencyInfoTruePK = agencyInfoTruePK;
	}
	public String getSTATUE() {
		return STATUE;
	}
	public void setSTATUE(String sTATUE) {
		STATUE = sTATUE;
	}
	public String getCRE_OPR_ID() {
		return CRE_OPR_ID;
	}
	public void setCRE_OPR_ID(String cREOPRID) {
		CRE_OPR_ID = cREOPRID;
	}
	public String getCRE_OPR_DATE() {
		return CRE_OPR_DATE;
	}
	public void setCRE_OPR_DATE(String cREOPRDATE) {
		CRE_OPR_DATE = cREOPRDATE;
	}
	public String getUP_OPR_ID() {
		return UP_OPR_ID;
	}
	public void setUP_OPR_ID(String uPOPRID) {
		UP_OPR_ID = uPOPRID;
	}
	public String getUP_OPR_DATE() {
		return UP_OPR_DATE;
	}
	public void setUP_OPR_DATE(String uPOPRDATE) {
		UP_OPR_DATE = uPOPRDATE;
	}
	
	
	public String getFEE_FLAG() {
		return FEE_FLAG;
	}
	public void setFEE_FLAG(String fEE_FLAG) {
		FEE_FLAG = fEE_FLAG;
	}
	public double getFEE_VALUE() {
		return FEE_VALUE;
	}
	public void setFEE_VALUE(double fEE_VALUE) {
		FEE_VALUE = fEE_VALUE;
	}
	public double getMIN_FEE() {
		return MIN_FEE;
	}
	public void setMIN_FEE(double mIN_FEE) {
		MIN_FEE = mIN_FEE;
	}
	public double getMAX_FEE() {
		return MAX_FEE;
	}
	public void setMAX_FEE(double mAX_FEE) {
		MAX_FEE = mAX_FEE;
	}
	public double getMIN_TRADE() {
		return MIN_TRADE;
	}
	public void setMIN_TRADE(double mIN_TRADE) {
		MIN_TRADE = mIN_TRADE;
	}
	//名称是要从状态表查出的对应的状态名称
	public String agentypeName;//机构类型名称
	public String trantypename;//交易连接类型名称
	public String cardhomename;//归属卡组织
	public String agenregbodyhome;//机构所属地区
	public String agenmechcaltypename;//机构所辖商户清算方式
	public String agencaltypename;//机构清算方式
	public String agencalprincyclename;//机构本金清算周期
	public String agencalprinmodename;//机构本金清算模式
	public  String agencalhandcyclename;//机构手续费清算周期
	public  String agencalhandmodelname;//机构手续费清算模式
	public  String agencallubcyclename;//机构分润清算周期
	public  String agencallubmodename;//机构分润清算模式
	public  String agenentrymodename;//机构出入账模式
	public String agencleardetailname;//机构清分明细是否生成
	private String yinlian;
	private String visa;
	private String Master;
	private String JCB;
	private String DinersClub;
	private String AmericanExpress;
	public String EXTENSION_FIELD1;
	public String EXTENSION_FIELD2;
	private String feeflagname;  //费率方式
	/**
	 * @return the eXTENSION_FIELD2
	 */
	public String getEXTENSION_FIELD2() {
		return EXTENSION_FIELD2;
	}
	/**
	 * @param eXTENSIONFIELD2 the eXTENSION_FIELD2 to set
	 */
	public void setEXTENSION_FIELD2(String eXTENSIONFIELD2) {
		EXTENSION_FIELD2 = eXTENSIONFIELD2;
	}
	/**
	 * @return the eXTENSION_FIELD1
	 */
	public String getEXTENSION_FIELD1() {
		return EXTENSION_FIELD1;
	}
	/**
	 * @param eXTENSIONFIELD1 the eXTENSION_FIELD1 to set
	 */
	public void setEXTENSION_FIELD1(String eXTENSIONFIELD1) {
		EXTENSION_FIELD1 = eXTENSIONFIELD1;
	}
	public String getYinlian() {
		return yinlian;
	}
	public void setYinlian(String yinlian) {
		this.yinlian = yinlian;
	}
	public String getVisa() {
		return visa;
	}
	public void setVisa(String visa) {
		this.visa = visa;
	}
	public String getMaster() {
		return Master;
	}
	public void setMaster(String master) {
		Master = master;
	}
	public String getJCB() {
		return JCB;
	}
	public void setJCB(String jCB) {
		JCB = jCB;
	}
	public String getDinersClub() {
		return DinersClub;
	}
	public void setDinersClub(String dinersClub) {
		DinersClub = dinersClub;
	}
	public String getAmericanExpress() {
		return AmericanExpress;
	}
	public void setAmericanExpress(String americanExpress) {
		AmericanExpress = americanExpress;
	}
	
	public String getAgentypeName() {
		return agentypeName;
	}
	public void setAgentypeName(String agentypeName) {
		this.agentypeName = agentypeName;
	}
	public String getTrantypename() {
		return trantypename;
	}
	public void setTrantypename(String trantypename) {
		this.trantypename = trantypename;
	}
	public String getCardhomename() {
		return cardhomename;
	}
	public void setCardhomename(String cardhomename) {
		this.cardhomename = cardhomename;
	}
	public String getAgenregbodyhome() {
		return agenregbodyhome;
	}
	public void setAgenregbodyhome(String agenregbodyhome) {
		this.agenregbodyhome = agenregbodyhome;
	}
	public String getAgenmechcaltypename() {
		return agenmechcaltypename;
	}
	public void setAgenmechcaltypename(String agenmechcaltypename) {
		this.agenmechcaltypename = agenmechcaltypename;
	}
	public String getAgencaltypename() {
		return agencaltypename;
	}
	public void setAgencaltypename(String agencaltypename) {
		this.agencaltypename = agencaltypename;
	}
	public String getAgencalprincyclename() {
		return agencalprincyclename;
	}
	public void setAgencalprincyclename(String agencalprincyclename) {
		this.agencalprincyclename = agencalprincyclename;
	}
	public String getAgencalprinmodename() {
		return agencalprinmodename;
	}
	public void setAgencalprinmodename(String agencalprinmodename) {
		this.agencalprinmodename = agencalprinmodename;
	}
	public String getAgencalhandcyclename() {
		return agencalhandcyclename;
	}
	public void setAgencalhandcyclename(String agencalhandcyclename) {
		this.agencalhandcyclename = agencalhandcyclename;
	}
	public String getAgencalhandmodelname() {
		return agencalhandmodelname;
	}
	public void setAgencalhandmodelname(String agencalhandmodelname) {
		this.agencalhandmodelname = agencalhandmodelname;
	}
	public String getAgencallubcyclename() {
		return agencallubcyclename;
	}
	public void setAgencallubcyclename(String agencallubcyclename) {
		this.agencallubcyclename = agencallubcyclename;
	}
	public String getAgencallubmodename() {
		return agencallubmodename;
	}
	public void setAgencallubmodename(String agencallubmodename) {
		this.agencallubmodename = agencallubmodename;
	}
	public String getAgenentrymodename() {
		return agenentrymodename;
	}
	public void setAgenentrymodename(String agenentrymodename) {
		this.agenentrymodename = agenentrymodename;
	}
	public String getAgencleardetailname() {
		return agencleardetailname;
	}
	public void setAgencleardetailname(String agencleardetailname) {
		this.agencleardetailname = agencleardetailname;
	}
	public String getFeeflagname() {
		return feeflagname;
	}
	public void setFeeflagname(String feeflagname) {
		this.feeflagname = feeflagname;
	}
	
}
