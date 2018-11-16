package com.huateng.po.mchnt;

import java.io.Serializable;

public class TblHisDiscAlgo1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DISC_ID;
	private Integer INDEX_NUM;
	private String TERM_ID;
	private String MCHT_NO;
	private String CITY_CODE;
	private String TO_BRCH_NO;
	private String FK_BRCH_NO;
	private String CARD_TYPE;
	private String CHANNEL_NO;
	private String BUSINESS_TYPE;
	private String TXN_NUM;
	private double MIN_FEE;
	private double MAX_FEE;
	private double FLOOR_AMOUNT;
	private String FLAG;
	private double FEE_VALUE;
	private String REC_UPD_USR_ID;
	private String REC_UPD_TS;
	private String REC_CRT_TS;
	private String SA_SATUTE;
	private String misc_1;//备用字段,目前用来存放tbl_his_disc_algo2_tmp表的主键
	private String citycodename;
	private String cardtypename;
	private String channelname;
	private String bussinesstypename;
	private String txnnumname;
	private String flagename;
	private String MIN_FEE1;
	
	
	public String getMisc_1() {
		return misc_1;
	}
	public void setMisc_1(String misc_1) {
		this.misc_1 = misc_1;
	}
	public String getMIN_FEE1() {
		return MIN_FEE1;
	}
	public void setMIN_FEE1(String mINFEE1) {
		MIN_FEE1 = mINFEE1;
	}
	public String getMAX_FEE1() {
		return MAX_FEE1;
	}
	public void setMAX_FEE1(String mAXFEE1) {
		MAX_FEE1 = mAXFEE1;
	}
	public String getFLOOR_AMOUNT1() {
		return FLOOR_AMOUNT1;
	}
	public void setFLOOR_AMOUNT1(String fLOORAMOUNT1) {
		FLOOR_AMOUNT1 = fLOORAMOUNT1;
	}
	public String getFEE_VALUE1() {
		return FEE_VALUE1;
	}
	public void setFEE_VALUE1(String fEEVALUE1) {
		FEE_VALUE1 = fEEVALUE1;
	}
	private String MAX_FEE1;
	private String FLOOR_AMOUNT1;
	private String FEE_VALUE1;
	public double getMIN_FEE() {
		return MIN_FEE;
	}
	public void setMIN_FEE(double mINFEE) {
		MIN_FEE = mINFEE;
	}
	public double getMAX_FEE() {
		return MAX_FEE;
	}
	public void setMAX_FEE(double mAXFEE) {
		MAX_FEE = mAXFEE;
	}
	public double getFLOOR_AMOUNT() {
		return FLOOR_AMOUNT;
	}
	public void setFLOOR_AMOUNT(double fLOORAMOUNT) {
		FLOOR_AMOUNT = fLOORAMOUNT;
	}
	public double getFEE_VALUE() {
		return FEE_VALUE;
	}
	public void setFEE_VALUE(double fEEVALUE) {
		FEE_VALUE = fEEVALUE;
	}

	public String getCardtypename() {
		return cardtypename;
	}
	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}
	
	public String getCitycodename() {
		return citycodename;
	}
	public void setCitycodename(String citycodename) {
		this.citycodename = citycodename;
	}

	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	public String getBussinesstypename() {
		return bussinesstypename;
	}
	public void setBussinesstypename(String bussinesstypename) {
		this.bussinesstypename = bussinesstypename;
	}
	public String getTxnnumname() {
		return txnnumname;
	}
	public void setTxnnumname(String txnnumname) {
		this.txnnumname = txnnumname;
	}
	public String getFlagename() {
		return flagename;
	}
	public void setFlagename(String flagename) {
		this.flagename = flagename;
	}

	public String getDISC_ID() {
		return DISC_ID;
	}
	public void setDISC_ID(String dISCID) {
		DISC_ID = dISCID;
	}
	public Integer getINDEX_NUM() {
		return INDEX_NUM;
	}
	public void setINDEX_NUM(Integer iNDEXNUM) {
		INDEX_NUM = iNDEXNUM;
	}
	public String getTERM_ID() {
		return TERM_ID;
	}
	public void setTERM_ID(String tERMID) {
		TERM_ID = tERMID;
	}
	public String getMCHT_NO() {
		return MCHT_NO;
	}
	public void setMCHT_NO(String mCHTNO) {
		MCHT_NO = mCHTNO;
	}
	public String getCITY_CODE() {
		return CITY_CODE;
	}
	public void setCITY_CODE(String cITYCODE) {
		CITY_CODE = cITYCODE;
	}
	public String getTO_BRCH_NO() {
		return TO_BRCH_NO;
	}
	public void setTO_BRCH_NO(String tOBRCHNO) {
		TO_BRCH_NO = tOBRCHNO;
	}
	public String getFK_BRCH_NO() {
		return FK_BRCH_NO;
	}
	public void setFK_BRCH_NO(String fKBRCHNO) {
		FK_BRCH_NO = fKBRCHNO;
	}
	public String getCARD_TYPE() {
		return CARD_TYPE;
	}
	public void setCARD_TYPE(String cARDTYPE) {
		CARD_TYPE = cARDTYPE;
	}
	public String getCHANNEL_NO() {
		return CHANNEL_NO;
	}
	public void setCHANNEL_NO(String cHANNELNO) {
		CHANNEL_NO = cHANNELNO;
	}
	public String getBUSINESS_TYPE() {
		return BUSINESS_TYPE;
	}
	public void setBUSINESS_TYPE(String bUSINESSTYPE) {
		BUSINESS_TYPE = bUSINESSTYPE;
	}
	public String getTXN_NUM() {
		return TXN_NUM;
	}
	public void setTXN_NUM(String tXNNUM) {
		TXN_NUM = tXNNUM;
	}
	
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	
	public String getREC_UPD_USR_ID() {
		return REC_UPD_USR_ID;
	}
	public void setREC_UPD_USR_ID(String rECUPDUSRID) {
		REC_UPD_USR_ID = rECUPDUSRID;
	}
	public String getREC_UPD_TS() {
		return REC_UPD_TS;
	}
	public void setREC_UPD_TS(String rECUPDTS) {
		REC_UPD_TS = rECUPDTS;
	}
	public String getREC_CRT_TS() {
		return REC_CRT_TS;
	}
	public void setREC_CRT_TS(String rECCRTTS) {
		REC_CRT_TS = rECCRTTS;
	}
	public String getSA_SATUTE() {
		return SA_SATUTE;
	}
	public void setSA_SATUTE(String sASATUTE) {
		SA_SATUTE = sASATUTE;
	}


}
