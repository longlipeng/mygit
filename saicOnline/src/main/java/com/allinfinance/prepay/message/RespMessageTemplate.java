package com.allinfinance.prepay.message;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DATA")
public abstract class RespMessageTemplate extends BasicMessage
{
	private String MSGTYPEID;
	private String PROCCODE;
	private String TRANSAMT;
	private String TRANSMSNTIME;
	private String SYSTRACENUM;
	private String LOCTRANSTIME;
	private String LOCTRANSDATE;
	private String EXPRDATE;
	private String SETTLMTDATE;
	private String MCHNTTYPE;
	private String ACQNTRYCODE;
	private String POSCONDCODE;
	private String ACQIDCODE;
	private String FWDIDCODE;
	private String RETRIVLNUM;
	private String AUTHRIDRESP;
	private String CARDACCTERMID;
	private String CARDACCID;
	private String ADDRESPDATA;
	private String TRANSCURRCODE;
	private String APPTRADNO;
	private String ARPC;
	private String ISSSCRPT1;
	private String ISSSCRPT2;
	private String RECIDCODE;
	private String SWTRESERV;
	private String ISSRESERV;
	private String MSGAUTHNCODE;
	private String PANSEQ;
	
	
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
		SETTLMTDATE = StringUtils.right(sETTLMTDATE, 4);
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
	public String getPOSCONDCODE()
	{
		return POSCONDCODE;
	}
	public void setPOSCONDCODE(String pOSCONDCODE)
	{
		POSCONDCODE = pOSCONDCODE;
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
	public String getADDRESPDATA()
	{
		return ADDRESPDATA;
	}
	public void setADDRESPDATA(String aDDRESPDATA)
	{
		ADDRESPDATA = aDDRESPDATA;
	}
	public String getTRANSCURRCODE()
	{
		return TRANSCURRCODE;
	}
	public void setTRANSCURRCODE(String tRANSCURRCODE)
	{
		TRANSCURRCODE = tRANSCURRCODE;
	}
	public String getAPPTRADNO()
	{
		return APPTRADNO;
	}
	public void setAPPTRADNO(String aPPTRADNO)
	{
		APPTRADNO = aPPTRADNO;
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
	public String getPANSEQ()
	{
		return StringUtils.leftPad(PANSEQ, 3, "0");
	}
	public void setPANSEQ(String pANSEQ)
	{
		PANSEQ = StringUtils.leftPad(pANSEQ, 3, "0");
	}

}
