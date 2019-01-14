package com.allinfinance.prepay.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 联机交易消息模板
 * @author joesun.jiang
 *
 */
@XStreamAlias("DATA")
public abstract class ReqMessageTemplate extends BasicMessage
{
	private String MSGTYPEID;
	private String PROCCODE;
	private String TRANSAMT;
	private String SETTLMTAMT;
	private String CDHLDRBILAMT;
	private String TRANSMSNTIME;
	private String SETTLEMTCONVRATE;
	private String CDHLDRBILCONVRATE;
	private String SYSTRACENUM;
	private String LOCTRANSTIME;
	private String LOCTRANSDATE;
	private String EXPRDATE;
	private String SETTLMTDATE;
	private String CONVDATE;
	private String MCHNTTYPE;
	private String ACQNTRYCODE;
	private String POSENTRYCODE;
	private String POSCONDCODE;
	private String POSPINCODE;
	private String ACQIDCODE;
	private String FWDIDCODE;
	private String RETRIVLNUM;
	private String AUTHRIDRESP;
	private String RESPCODE;
	private String CARDACCTERMID;
	private String CARDACCID;
	private String CARDACCLOC;
	private String ADDRESPDATA;
	private String TRACK1DATA;
	private String ADDDATA;
	private String TRANSCURRCODE;
	private String SETTLMTCURRCODE;
	private String CDHLDRCURRCODE;
	private String ARQC;
	private String CRYPINFODATA;
	private String APPDATA;
	private String RANDOMNUMBER;
	private String APPTRADNO;
	private String TVR;
	private String TRANSDATE;
	private String TRANSTYPE;
	private String TRANSAMOUNT;
	private String TCURCODE;
	private String AIP;
	private String TCYCODE;
	private String AMOUNTOTHER;
	private String TERMNLCAPBS;
	private String CVMR;
	private String TERMTYPE;
	private String IFD;
	private String DF;
	private String TERMAPPVERNUM;
	private String TRANSSEQCOUNT;
	private String ARPC;
	private String ISSSCRPT1;
	private String ISSSCRPT2;
	private String ECIAC;
	private String CARDPRODID;
	private String RESERV60;
	private String RECIDCODE;
	private String SWTRESERV;
	private String ACQRESERV;
	private String ISSRESERV;
	private String MSGAUTHNCODE;
	public String getMSGTYPEID()
	{
		return MSGTYPEID;
	}
	public void setMSGTYPEID(String mSGTYPEID)
	{
		MSGTYPEID = mSGTYPEID;
	}
	public String getPROCCODE()
	{
		return PROCCODE;
	}
	public void setPROCCODE(String pROCCODE)
	{
		PROCCODE = pROCCODE;
	}
	public String getTRANSAMT()
	{
		return TRANSAMT;
	}
	public void setTRANSAMT(String tRANSAMT)
	{
		TRANSAMT = tRANSAMT;
	}
	public String getSETTLMTAMT()
	{
		return SETTLMTAMT;
	}
	public void setSETTLMTAMT(String sETTLMTAMT)
	{
		SETTLMTAMT = sETTLMTAMT;
	}
	public String getCDHLDRBILAMT()
	{
		return CDHLDRBILAMT;
	}
	public void setCDHLDRBILAMT(String cDHLDRBILAMT)
	{
		CDHLDRBILAMT = cDHLDRBILAMT;
	}
	public String getTRANSMSNTIME()
	{
		return TRANSMSNTIME;
	}
	public void setTRANSMSNTIME(String tRANSMSNTIME)
	{
		TRANSMSNTIME = tRANSMSNTIME;
	}
	public String getSYSTRACENUM()
	{
		return SYSTRACENUM;
	}
	public void setSYSTRACENUM(String sYSTRACENUM)
	{
		SYSTRACENUM = sYSTRACENUM;
	}
	public String getLOCTRANSTIME()
	{
		return LOCTRANSTIME;
	}
	public void setLOCTRANSTIME(String lOCTRANSTIME)
	{
		LOCTRANSTIME = lOCTRANSTIME;
	}
	public String getLOCTRANSDATE()
	{
		return LOCTRANSDATE;
	}
	public void setLOCTRANSDATE(String lOCTRANSDATE)
	{
		LOCTRANSDATE = lOCTRANSDATE;
	}
	public String getEXPRDATE()
	{
		return EXPRDATE;
	}
	public void setEXPRDATE(String eXPRDATE)
	{
		EXPRDATE = eXPRDATE;
	}
	public String getSETTLMTDATE()
	{
		return SETTLMTDATE;
	}
	public void setSETTLMTDATE(String sETTLMTDATE)
	{
		SETTLMTDATE = sETTLMTDATE;
	}
	public String getCONVDATE()
	{
		return CONVDATE;
	}
	public void setCONVDATE(String cONVDATE)
	{
		CONVDATE = cONVDATE;
	}
	public String getMCHNTTYPE()
	{
		return MCHNTTYPE;
	}
	public void setMCHNTTYPE(String mCHNTTYPE)
	{
		MCHNTTYPE = mCHNTTYPE;
	}
	public String getACQNTRYCODE()
	{
		return ACQNTRYCODE;
	}
	public void setACQNTRYCODE(String aCQNTRYCODE)
	{
		ACQNTRYCODE = aCQNTRYCODE;
	}
	public String getPOSENTRYCODE()
	{
		return POSENTRYCODE;
	}
	public void setPOSENTRYCODE(String pOSENTRYCODE)
	{
		POSENTRYCODE = pOSENTRYCODE;
	}
	public String getPOSCONDCODE()
	{
		return POSCONDCODE;
	}
	public void setPOSCONDCODE(String pOSCONDCODE)
	{
		POSCONDCODE = pOSCONDCODE;
	}
	public String getPOSPINCODE()
	{
		return POSPINCODE;
	}
	public void setPOSPINCODE(String pOSPINCODE)
	{
		POSPINCODE = pOSPINCODE;
	}
	public String getACQIDCODE()
	{
		return ACQIDCODE;
	}
	public void setACQIDCODE(String aCQIDCODE)
	{
		ACQIDCODE = aCQIDCODE;
	}
	public String getFWDIDCODE()
	{
		return FWDIDCODE;
	}
	public void setFWDIDCODE(String fWDIDCODE)
	{
		FWDIDCODE = fWDIDCODE;
	}
	public String getRETRIVLNUM()
	{
		return RETRIVLNUM;
	}
	public void setRETRIVLNUM(String rETRIVLNUM)
	{
		RETRIVLNUM = rETRIVLNUM;
	}
	public String getAUTHRIDRESP()
	{
		return AUTHRIDRESP;
	}
	public void setAUTHRIDRESP(String aUTHRIDRESP)
	{
		AUTHRIDRESP = aUTHRIDRESP;
	}
	public String getRESPCODE()
	{
		return RESPCODE;
	}
	public void setRESPCODE(String rESPCODE)
	{
		RESPCODE = rESPCODE;
	}
	public String getCARDACCTERMID()
	{
		return CARDACCTERMID;
	}
	public void setCARDACCTERMID(String cARDACCTERMID)
	{
		CARDACCTERMID = cARDACCTERMID;
	}
	public String getCARDACCID()
	{
		return CARDACCID;
	}
	public void setCARDACCID(String cARDACCID)
	{
		CARDACCID = cARDACCID;
	}
	public String getCARDACCLOC()
	{
		return CARDACCLOC;
	}
	public void setCARDACCLOC(String cARDACCLOC)
	{
		CARDACCLOC = cARDACCLOC;
	}
	public String getADDRESPDATA()
	{
		return ADDRESPDATA;
	}
	public void setADDRESPDATA(String aDDRESPDATA)
	{
		ADDRESPDATA = aDDRESPDATA;
	}
	public String getTRACK1DATA()
	{
		return TRACK1DATA;
	}
	public void setTRACK1DATA(String tRACK1DATA)
	{
		TRACK1DATA = tRACK1DATA;
	}
	public String getADDDATA()
	{
		return ADDDATA;
	}
	public void setADDDATA(String aDDDATA)
	{
		ADDDATA = aDDDATA;
	}
	public String getTRANSCURRCODE()
	{
		return TRANSCURRCODE;
	}
	public void setTRANSCURRCODE(String tRANSCURRCODE)
	{
		TRANSCURRCODE = tRANSCURRCODE;
	}
	public String getSETTLMTCURRCODE()
	{
		return SETTLMTCURRCODE;
	}
	public void setSETTLMTCURRCODE(String sETTLMTCURRCODE)
	{
		SETTLMTCURRCODE = sETTLMTCURRCODE;
	}
	public String getCDHLDRCURRCODE()
	{
		return CDHLDRCURRCODE;
	}
	public void setCDHLDRCURRCODE(String cDHLDRCURRCODE)
	{
		CDHLDRCURRCODE = cDHLDRCURRCODE;
	}
	public String getARQC()
	{
		return ARQC;
	}
	public void setARQC(String aRQC)
	{
		ARQC = aRQC;
	}
	public String getCRYPINFODATA()
	{
		return CRYPINFODATA;
	}
	public void setCRYPINFODATA(String cRYPINFODATA)
	{
		CRYPINFODATA = cRYPINFODATA;
	}
	public String getAPPDATA()
	{
		return APPDATA;
	}
	public void setAPPDATA(String aPPDATA)
	{
		APPDATA = aPPDATA;
	}
	public String getRANDOMNUMBER()
	{
		return RANDOMNUMBER;
	}
	public void setRANDOMNUMBER(String rANDOMNUMBER)
	{
		RANDOMNUMBER = rANDOMNUMBER;
	}
	public String getAPPTRADNO()
	{
		return APPTRADNO;
	}
	public void setAPPTRADNO(String aPPTRADNO)
	{
		APPTRADNO = aPPTRADNO;
	}
	public String getTVR()
	{
		return TVR;
	}
	public void setTVR(String tVR)
	{
		TVR = tVR;
	}
	public String getTRANSDATE()
	{
		return TRANSDATE;
	}
	public void setTRANSDATE(String tRANSDATE)
	{
		TRANSDATE = tRANSDATE;
	}
	public String getTRANSTYPE()
	{
		return TRANSTYPE;
	}
	public void setTRANSTYPE(String tRANSTYPE)
	{
		TRANSTYPE = tRANSTYPE;
	}
	public String getTRANSAMOUNT()
	{
		return TRANSAMOUNT;
	}
	public void setTRANSAMOUNT(String tRANSAMOUNT)
	{
		TRANSAMOUNT = tRANSAMOUNT;
	}
	public String getTCURCODE()
	{
		return TCURCODE;
	}
	public void setTCURCODE(String tCURCODE)
	{
		TCURCODE = tCURCODE;
	}
	public String getAIP()
	{
		return AIP;
	}
	public void setAIP(String aIP)
	{
		AIP = aIP;
	}
	public String getTCYCODE()
	{
		return TCYCODE;
	}
	public void setTCYCODE(String tCYCODE)
	{
		TCYCODE = tCYCODE;
	}
	public String getAMOUNTOTHER()
	{
		return AMOUNTOTHER;
	}
	public void setAMOUNTOTHER(String aMOUNTOTHER)
	{
		AMOUNTOTHER = aMOUNTOTHER;
	}
	public String getTERMNLCAPBS()
	{
		return TERMNLCAPBS;
	}
	public void setTERMNLCAPBS(String tERMNLCAPBS)
	{
		TERMNLCAPBS = tERMNLCAPBS;
	}
	public String getCVMR()
	{
		return CVMR;
	}
	public void setCVMR(String cVMR)
	{
		CVMR = cVMR;
	}
	public String getTERMTYPE()
	{
		return TERMTYPE;
	}
	public void setTERMTYPE(String tERMTYPE)
	{
		TERMTYPE = tERMTYPE;
	}
	public String getIFD()
	{
		return IFD;
	}
	public void setIFD(String iFD)
	{
		IFD = iFD;
	}
	public String getDF()
	{
		return DF;
	}
	public void setDF(String dF)
	{
		DF = dF;
	}
	public String getTERMAPPVERNUM()
	{
		return TERMAPPVERNUM;
	}
	public void setTERMAPPVERNUM(String tERMAPPVERNUM)
	{
		TERMAPPVERNUM = tERMAPPVERNUM;
	}
	public String getTRANSSEQCOUNT()
	{
		return TRANSSEQCOUNT;
	}
	public void setTRANSSEQCOUNT(String tRANSSEQCOUNT)
	{
		TRANSSEQCOUNT = tRANSSEQCOUNT;
	}
	public String getARPC()
	{
		return ARPC;
	}
	public void setARPC(String aRPC)
	{
		ARPC = aRPC;
	}
	public String getISSSCRPT1()
	{
		return ISSSCRPT1;
	}
	public void setISSSCRPT1(String iSSSCRPT1)
	{
		ISSSCRPT1 = iSSSCRPT1;
	}
	public String getISSSCRPT2()
	{
		return ISSSCRPT2;
	}
	public void setISSSCRPT2(String iSSSCRPT2)
	{
		ISSSCRPT2 = iSSSCRPT2;
	}
	public String getECIAC()
	{
		return ECIAC;
	}
	public void setECIAC(String eCIAC)
	{
		ECIAC = eCIAC;
	}
	public String getCARDPRODID()
	{
		return CARDPRODID;
	}
	public void setCARDPRODID(String cARDPRODID)
	{
		CARDPRODID = cARDPRODID;
	}
	public String getRESERV60()
	{
		return RESERV60;
	}
	public void setRESERV60(String rESERV60)
	{
		RESERV60 = rESERV60;
	}
	public String getRECIDCODE()
	{
		return RECIDCODE;
	}
	public void setRECIDCODE(String rECIDCODE)
	{
		RECIDCODE = rECIDCODE;
	}
	public String getSWTRESERV()
	{
		return SWTRESERV;
	}
	public void setSWTRESERV(String sWTRESERV)
	{
		SWTRESERV = sWTRESERV;
	}
	public String getACQRESERV()
	{
		return ACQRESERV;
	}
	public void setACQRESERV(String aCQRESERV)
	{
		ACQRESERV = aCQRESERV;
	}
	public String getISSRESERV()
	{
		return ISSRESERV;
	}
	public void setISSRESERV(String iSSRESERV)
	{
		ISSRESERV = iSSRESERV;
	}
	public String getMSGAUTHNCODE()
	{
		return MSGAUTHNCODE;
	}
	public void setMSGAUTHNCODE(String mSGAUTHNCODE)
	{
		MSGAUTHNCODE = mSGAUTHNCODE;
	}
	public String getSETTLEMTCONVRATE()
	{
		return SETTLEMTCONVRATE;
	}
	public void setSETTLEMTCONVRATE(String sETTLEMTCONVRATE)
	{
		SETTLEMTCONVRATE = sETTLEMTCONVRATE;
	}
	public String getCDHLDRBILCONVRATE()
	{
		return CDHLDRBILCONVRATE;
	}
	public void setCDHLDRBILCONVRATE(String cDHLDRBILCONVRATE)
	{
		CDHLDRBILCONVRATE = cDHLDRBILCONVRATE;
	}
}
