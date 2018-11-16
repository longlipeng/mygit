package com.huateng.po.base;

import java.io.Serializable;

public class AgencyFeeLub implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public AgencyFeeLub(){}
public AgencyFeeLub(
		AgencyFeeLub info,
		 String agentypename,
		 String tradeacceptreg,
		 String cardstylename,
         String cardmediumname,
	     String tradechannelname,
		 String businesstype,
		 String trantypename,
		 String mechratetypename,
		 String mechratemethodname,
		 String mechlubtypename,
		 String mechlubmethodname
		){
	this.FEE_ID=info.getFEE_ID();
	this.AGEN_ID=info.getAGEN_ID();
	this.AGEN_TYPE=info.getAGEN_TYPE();
	this.TERM_ID=info.getTERM_ID();
	this.MTCH_NO=info.getMTCH_NO();
	this.MCC_CODE=info.getMCC_CODE();
	this.TRADE_ACCEPT_REG=info.getTRADE_ACCEPT_REG();
    this.AGEN_TARGET_BODY=info.getAGEN_TARGET_BODY();
	this.AGEN_CRE_CARD=info.getAGEN_CRE_CARD();
    this.CARD_STYLE=info.getCARD_STYLE();
    this.CARD_MEDIUM=info.getCARD_MEDIUM();
    this.TRADE_CHANNEL=info.getTRADE_CHANNEL();
    this.BUSINESS_TYPE=info.getBUSINESS_TYPE();
	this.TRAN_TYPE=info.getTRAN_TYPE();
	this.RES=info.getRES();
	this.MCHT_RATE_TYPE=info.getMCHT_RATE_TYPE();
	this.MCHT_RATE_METHOD=info.getMCHT_RATE_METHOD();
	this.AMOUNT_LIMIT=info.getAMOUNT_LIMIT();
	this.MCHT_RATE_PARAM=info.getMCHT_RATE_PARAM();
	this.MCHT_PERCENT_LIMIT=info.getMCHT_PERCENT_LIMIT();
	this.MCHT_PERCENT_MAX=info.getMCHT_PERCENT_MAX();
	this.MCHT_LUB_TYPE=info.getMCHT_LUB_TYPE();
	this.MCHT_LUB_METHOD=info.getMCHT_LUB_METHOD();//机构分润方式
	this.MCHT_LUB_PARAM=info.getMCHT_LUB_PARAM();//机构分润参数
	this.MCHT_LUB_PERCENT_LIMIT=info.getMCHT_LUB_PERCENT_LIMIT();//机构分润百分比下限值
	this.MCHT_LUB_PERCENT_MAX=info.getMCHT_LUB_PERCENT_MAX();//机构分润百分比上限值
	 this.agentypename=agentypename;
	 this.tradeacceptreg=tradeacceptreg;
	 this.cardstylename=cardstylename;
     this.cardmediumname=cardmediumname;
     this.tradechannelname=tradechannelname;
	 this.businesstype=businesstype;
     this.trantypename=trantypename;
	 this.mechratetypename=mechratetypename;
	 this.mechratemethodname=mechratemethodname;
	 this.mechlubtypename=mechlubtypename;
	 this.mechlubmethodname=mechlubmethodname;
	
}

private String FEE_ID;//分润编号
public String AGEN_ID;//机构代码
public String AGEN_TYPE;//机构类型
public String TERM_ID;//终端号
public String MTCH_NO;//商户号
public String MCC_CODE;//MCC码
public String TRADE_ACCEPT_REG;//交易受理地区
public String AGEN_TARGET_BODY;//目标机构
public String AGEN_CRE_CARD;//发卡机构
public String CARD_STYLE;//卡类型
public String CARD_MEDIUM;//卡介质
public String TRADE_CHANNEL;//交易渠道
public String BUSINESS_TYPE;//业务类型
public String TRAN_TYPE;//交易类型
public String RES;//分润匹配保留域
public String MCHT_RATE_TYPE;//机构费率类型
public String MCHT_RATE_METHOD;//机构费率方式
public String AMOUNT_LIMIT;//最低交易金额
public String MCHT_RATE_PARAM;//机构费率参数
public String MCHT_PERCENT_LIMIT;//机构费率百分比下限值
public String MCHT_PERCENT_MAX;//机构费率百分比上限值
public String MCHT_LUB_TYPE;//机构分润类型
public String MCHT_LUB_METHOD;//机构分润方式
public String MCHT_LUB_PARAM;//机构分润参数
public String MCHT_LUB_PERCENT_LIMIT;//机构分润百分比下限值
public String MCHT_LUB_PERCENT_MAX;//机构分润百分比上限值
public String EXTEN_FIELD1;//交易联接类型

public String getEXTEN_FIELD1() {
	return EXTEN_FIELD1;
}
public void setEXTEN_FIELD1(String eXTEN_FIELD1) {
	EXTEN_FIELD1 = eXTEN_FIELD1;
}
public String getMCHT_RATE_TYPE() {
	return MCHT_RATE_TYPE;
}
public void setMCHT_RATE_TYPE(String mCHTRATETYPE) {
	MCHT_RATE_TYPE = mCHTRATETYPE;
}
public String getMCHT_RATE_METHOD() {
	return MCHT_RATE_METHOD;
}
public void setMCHT_RATE_METHOD(String mCHTRATEMETHOD) {
	MCHT_RATE_METHOD = mCHTRATEMETHOD;
}
public String getMCHT_RATE_PARAM() {
	return MCHT_RATE_PARAM;
}
public void setMCHT_RATE_PARAM(String mCHTRATEPARAM) {
	MCHT_RATE_PARAM = mCHTRATEPARAM;
}
public String getMCHT_PERCENT_LIMIT() {
	return MCHT_PERCENT_LIMIT;
}
public void setMCHT_PERCENT_LIMIT(String mCHTPERCENTLIMIT) {
	MCHT_PERCENT_LIMIT = mCHTPERCENTLIMIT;
}
public String getMCHT_PERCENT_MAX() {
	return MCHT_PERCENT_MAX;
}
public void setMCHT_PERCENT_MAX(String mCHTPERCENTMAX) {
	MCHT_PERCENT_MAX = mCHTPERCENTMAX;
}
public String getMCHT_LUB_TYPE() {
	return MCHT_LUB_TYPE;
}
public void setMCHT_LUB_TYPE(String mCHTLUBTYPE) {
	MCHT_LUB_TYPE = mCHTLUBTYPE;
}
public String getMCHT_LUB_METHOD() {
	return MCHT_LUB_METHOD;
}
public void setMCHT_LUB_METHOD(String mCHTLUBMETHOD) {
	MCHT_LUB_METHOD = mCHTLUBMETHOD;
}
public String getMCHT_LUB_PARAM() {
	return MCHT_LUB_PARAM;
}
public void setMCHT_LUB_PARAM(String mCHTLUBPARAM) {
	MCHT_LUB_PARAM = mCHTLUBPARAM;
}
public String getMCHT_LUB_PERCENT_LIMIT() {
	return MCHT_LUB_PERCENT_LIMIT;
}
public void setMCHT_LUB_PERCENT_LIMIT(String mCHTLUBPERCENTLIMIT) {
	MCHT_LUB_PERCENT_LIMIT = mCHTLUBPERCENTLIMIT;
}
public String getMCHT_LUB_PERCENT_MAX() {
	return MCHT_LUB_PERCENT_MAX;
}
public void setMCHT_LUB_PERCENT_MAX(String mCHTLUBPERCENTMAX) {
	MCHT_LUB_PERCENT_MAX = mCHTLUBPERCENTMAX;
}
public void setAMOUNT_LIMIT(String aMOUNTLIMIT) {
	AMOUNT_LIMIT = aMOUNTLIMIT;
}

public String RATE_PARAM1;//费率参数
public String LUB_PARAM1;//分润参数
public String STATUE;
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

public String CRE_OPR_ID;
public String CRE_OPR_DATE;
public String UP_OPR_ID;
public String UP_OPR_DATE;
//添加的名字
public String agentypename;//机构类型名称
public String tradeacceptreg;//TRADE_ACCEPT_REG交易受理地区
public String cardstylename;//CARD_STYLE卡类型名称
public String cardmediumname;//CARD_MEDIUM卡介质名称
public String tradechannelname;//TRADE_CHANNEL交易渠道名称
public String businesstype;//BUSINESS_TYPE业务类型名称
public String trantypename;//TRAN_TYPE交易类型名臣
public String mechratetypename;//MECH_RATE_TYPE机构费率类型名称
public String mechratemethodname;//MECH_RATE_METHOD机构费率方式名称
public String mechlubtypename;//MECH_LUB_TYPE机构分润类型名称
public String mechlubmethodname;//MECH_LUB_METHOD机构分润方式名称
public String getRATE_PARAM1() {
	return RATE_PARAM1;
}
public void setRATE_PARAM1(String rATEPARAM1) {
	RATE_PARAM1 = rATEPARAM1;
}
public String getLUB_PARAM1() {
	return LUB_PARAM1;
}
public void setLUB_PARAM1(String lUBPARAM1) {
	LUB_PARAM1 = lUBPARAM1;
}
public String getTradeacceptreg() {
	return tradeacceptreg;
}
public void setTradeacceptreg(String tradeacceptreg) {
	this.tradeacceptreg = tradeacceptreg;
}
public String getMechratemethodname() {
	return mechratemethodname;
}
public void setMechratemethodname(String mechratemethodname) {
	this.mechratemethodname = mechratemethodname;
}
public String getAgentypename() {
	return agentypename;
}
public void setAgentypename(String agentypename) {
	this.agentypename = agentypename;
}
public String getCardstylename() {
	return cardstylename;
}
public void setCardstylename(String cardstylename) {
	this.cardstylename = cardstylename;
}
public String getCardmediumname() {
	return cardmediumname;
}
public void setCardmediumname(String cardmediumname) {
	this.cardmediumname = cardmediumname;
}
public String getTradechannelname() {
	return tradechannelname;
}
public void setTradechannelname(String tradechannelname) {
	this.tradechannelname = tradechannelname;
}
public String getBusinesstype() {
	return businesstype;
}
public void setBusinesstype(String businesstype) {
	this.businesstype = businesstype;
}
public String getTrantypename() {
	return trantypename;
}
public void setTrantypename(String trantypename) {
	this.trantypename = trantypename;
}
public String getMechratetypename() {
	return mechratetypename;
}
public void setMechratetypename(String mechratetypename) {
	this.mechratetypename = mechratetypename;
}

public String getMechlubtypename() {
	return mechlubtypename;
}
public void setMechlubtypename(String mechlubtypename) {
	this.mechlubtypename = mechlubtypename;
}
public String getMechlubmethodname() {
	return mechlubmethodname;
}
public void setMechlubmethodname(String mechlubmethodname) {
	this.mechlubmethodname = mechlubmethodname;
}
//
public String getFEE_ID() {
	return FEE_ID;
}
public void setFEE_ID(String fEEID) {
	FEE_ID = fEEID;
}
public String getAGEN_ID() {
	return AGEN_ID;
}
public void setAGEN_ID(String aGENID) {
	AGEN_ID = aGENID;
}
public String getAGEN_TYPE() {
	return AGEN_TYPE;
}
public void setAGEN_TYPE(String aGENTYPE) {
	AGEN_TYPE = aGENTYPE;
}
public String getTERM_ID() {
	return TERM_ID;
}
public void setTERM_ID(String tERMID) {
	TERM_ID = tERMID;
}
public String getMTCH_NO() {
	return MTCH_NO;
}
public void setMTCH_NO(String mTCHNO) {
	MTCH_NO = mTCHNO;
}
public String getMCC_CODE() {
	return MCC_CODE;
}
public void setMCC_CODE(String mCCCODE) {
	MCC_CODE = mCCCODE;
}
public String getTRADE_ACCEPT_REG() {
	return TRADE_ACCEPT_REG;
}
public void setTRADE_ACCEPT_REG(String tRADEACCEPTREG) {
	TRADE_ACCEPT_REG = tRADEACCEPTREG;
}
public String getAGEN_TARGET_BODY() {
	return AGEN_TARGET_BODY;
}
public void setAGEN_TARGET_BODY(String aGENTARGETBODY) {
	AGEN_TARGET_BODY = aGENTARGETBODY;
}
public String getAGEN_CRE_CARD() {
	return AGEN_CRE_CARD;
}
public void setAGEN_CRE_CARD(String aGENCRECARD) {
	AGEN_CRE_CARD = aGENCRECARD;
}
public String getCARD_STYLE() {
	return CARD_STYLE;
}
public void setCARD_STYLE(String cARDSTYLE) {
	CARD_STYLE = cARDSTYLE;
}
public String getCARD_MEDIUM() {
	return CARD_MEDIUM;
}
public void setCARD_MEDIUM(String cARDMEDIUM) {
	CARD_MEDIUM = cARDMEDIUM;
}
public String getTRADE_CHANNEL() {
	return TRADE_CHANNEL;
}
public void setTRADE_CHANNEL(String tRADECHANNEL) {
	TRADE_CHANNEL = tRADECHANNEL;
}
public String getBUSINESS_TYPE() {
	return BUSINESS_TYPE;
}
public void setBUSINESS_TYPE(String bUSINESSTYPE) {
	BUSINESS_TYPE = bUSINESSTYPE;
}
public String getTRAN_TYPE() {
	return TRAN_TYPE;
}
public void setTRAN_TYPE(String tRANTYPE) {
	TRAN_TYPE = tRANTYPE;
}
public String getRES() {
	return RES;
}
public void setRES(String rES) {
	RES = rES;
}

public String getAMOUNT_LIMIT() {
	return AMOUNT_LIMIT;
}



}
