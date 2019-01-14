package com.allinfinance.univer.manager.transactionQuery.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class StanStifQueryDTO extends PageQueryDTO {
	private String CTIF_ID; // 主体客户号
	private String CTIF_TP; // 主体类别
	private String CLIENT_TP; // 客户类别
	private String SMID; // 主体特约商户编码
	private String CTNM; // 主体姓名/名称
	private String CITP; // 主体身份证件/证明文件类型
	private String CITP_NT; // 主体身份证件/证明文件类型说明
	private String CTID; // 主体身份证件/证明文件号码
	private String CBAT; // 主体的银行账号种类
	private String CBAC; // 主体的银行账号
	private String CABM; // 主体银行账号的开户行名称
	private String CTAT; // 主体的交易账号种类
	private String CTAC; // 主体的交易账号
	private String CPIN; // 主体所在支付机构的名称
	private String CPBA; // 主体所在支付机构的银行账号
	private String CPBN; // 主体所在支付机构的银行账号的开户行名称
	private String CTIP; // 主体的交易IP地址
	private String TSTM; // 交易时间
	private String CTTP; // 货币资金转移方式
	private String TSDR; // 资金收付标志
	private String CRPP; // 资金用途
	private String CRTP; // 交易币种
	private String CRAT; // 交易金额
	private String TCIF_ID; // 交易对手ID
	private String TCNM; // 交易对手姓名/名称
	private String TSMI; // 交易对手特约商户编码
	private String TCIT; // 交易对手证件/证明文件类型
	private String TCIT_NT; // 交易对手证件/证明文件类型说明
	private String TCID; // 交易对手证件/证明文件号码
	private String TCAT; // 交易对手的银行账号种类
	private String TCBA; // 交易对手的银行账号
	private String TCBN; // 交易对手银行账号的开户行名称
	private String TCTT; // 交易对手的交易账号种类
	private String TCTA; // 交易对手的交易账号
	private String TCPN; // 交易对手所在支付机构的名称
	private String TCPA; // 交易对手所在支付机构的银行账号
	private String TPBN; // 交易对手所在支付机构银行账号的开户行名称
	private String TCIP; // 交易对手的交易IP地址
	private String TMNM; // 交易商品名称
	private String BPTC; // 银行与支付机构之间的业务交易编码
	private String PMTC; // 支付机构与商户之间的业务交易编码
	private String TICD; // 业务标识号
	private String BUSI_TYPE; // 业务类型
	private String TRANS_TYPE; // 交易类型
	private String POS_DEV_ID; // 交易终端号
	private String TRANS_STAT; // 交易状态
	private String BANK_STAT; // 银行状态
	private String MER_PROV; // 地区省
	private String MER_AREA; // 地区县
	private String POS_PROV; // 交易省
	private String POS_AREA; // 交易县
	private String MER_UNIT; // 分支机构
	private String EXTEND1; // 转换标识
	private String EXTEND2; // 是否为记名卡交易
	private String RATE_RMB; // 人民币汇率
	private String RATE_USA; // 美元汇率
	private String IOFG; // 境内外标识
	
	private String startDate;
	
	private String endDate;
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCTIF_ID() {
		return CTIF_ID;
	}
	public void setCTIF_ID(String cTIF_ID) {
		CTIF_ID = cTIF_ID;
	}
	public String getCTIF_TP() {
		return CTIF_TP;
	}
	public void setCTIF_TP(String cTIF_TP) {
		CTIF_TP = cTIF_TP;
	}
	public String getCLIENT_TP() {
		return CLIENT_TP;
	}
	public void setCLIENT_TP(String cLIENT_TP) {
		CLIENT_TP = cLIENT_TP;
	}
	public String getSMID() {
		return SMID;
	}
	public void setSMID(String sMID) {
		SMID = sMID;
	}
	public String getCTNM() {
		return CTNM;
	}
	public void setCTNM(String cTNM) {
		CTNM = cTNM;
	}
	public String getCITP() {
		return CITP;
	}
	public void setCITP(String cITP) {
		CITP = cITP;
	}
	public String getCITP_NT() {
		return CITP_NT;
	}
	public void setCITP_NT(String cITP_NT) {
		CITP_NT = cITP_NT;
	}
	public String getCTID() {
		return CTID;
	}
	public void setCTID(String cTID) {
		CTID = cTID;
	}
	public String getCBAT() {
		return CBAT;
	}
	public void setCBAT(String cBAT) {
		CBAT = cBAT;
	}
	public String getCBAC() {
		return CBAC;
	}
	public void setCBAC(String cBAC) {
		CBAC = cBAC;
	}
	public String getCABM() {
		return CABM;
	}
	public void setCABM(String cABM) {
		CABM = cABM;
	}
	public String getCTAT() {
		return CTAT;
	}
	public void setCTAT(String cTAT) {
		CTAT = cTAT;
	}
	public String getCTAC() {
		return CTAC;
	}
	public void setCTAC(String cTAC) {
		CTAC = cTAC;
	}
	public String getCPIN() {
		return CPIN;
	}
	public void setCPIN(String cPIN) {
		CPIN = cPIN;
	}
	public String getCPBA() {
		return CPBA;
	}
	public void setCPBA(String cPBA) {
		CPBA = cPBA;
	}
	public String getCPBN() {
		return CPBN;
	}
	public void setCPBN(String cPBN) {
		CPBN = cPBN;
	}
	public String getCTIP() {
		return CTIP;
	}
	public void setCTIP(String cTIP) {
		CTIP = cTIP;
	}
	public String getTSTM() {
		return TSTM;
	}
	public void setTSTM(String tSTM) {
		TSTM = tSTM;
	}
	public String getCTTP() {
		return CTTP;
	}
	public void setCTTP(String cTTP) {
		CTTP = cTTP;
	}
	public String getTSDR() {
		return TSDR;
	}
	public void setTSDR(String tSDR) {
		TSDR = tSDR;
	}
	public String getCRPP() {
		return CRPP;
	}
	public void setCRPP(String cRPP) {
		CRPP = cRPP;
	}
	public String getCRTP() {
		return CRTP;
	}
	public void setCRTP(String cRTP) {
		CRTP = cRTP;
	}
	public String getCRAT() {
		return CRAT;
	}
	public void setCRAT(String cRAT) {
		CRAT = cRAT;
	}
	public String getTCIF_ID() {
		return TCIF_ID;
	}
	public void setTCIF_ID(String tCIF_ID) {
		TCIF_ID = tCIF_ID;
	}
	public String getTCNM() {
		return TCNM;
	}
	public void setTCNM(String tCNM) {
		TCNM = tCNM;
	}
	public String getTSMI() {
		return TSMI;
	}
	public void setTSMI(String tSMI) {
		TSMI = tSMI;
	}
	public String getTCIT() {
		return TCIT;
	}
	public void setTCIT(String tCIT) {
		TCIT = tCIT;
	}
	public String getTCIT_NT() {
		return TCIT_NT;
	}
	public void setTCIT_NT(String tCIT_NT) {
		TCIT_NT = tCIT_NT;
	}
	public String getTCID() {
		return TCID;
	}
	public void setTCID(String tCID) {
		TCID = tCID;
	}
	public String getTCAT() {
		return TCAT;
	}
	public void setTCAT(String tCAT) {
		TCAT = tCAT;
	}
	public String getTCBA() {
		return TCBA;
	}
	public void setTCBA(String tCBA) {
		TCBA = tCBA;
	}
	public String getTCBN() {
		return TCBN;
	}
	public void setTCBN(String tCBN) {
		TCBN = tCBN;
	}
	public String getTCTT() {
		return TCTT;
	}
	public void setTCTT(String tCTT) {
		TCTT = tCTT;
	}
	public String getTCTA() {
		return TCTA;
	}
	public void setTCTA(String tCTA) {
		TCTA = tCTA;
	}
	public String getTCPN() {
		return TCPN;
	}
	public void setTCPN(String tCPN) {
		TCPN = tCPN;
	}
	public String getTCPA() {
		return TCPA;
	}
	public void setTCPA(String tCPA) {
		TCPA = tCPA;
	}
	public String getTPBN() {
		return TPBN;
	}
	public void setTPBN(String tPBN) {
		TPBN = tPBN;
	}
	public String getTCIP() {
		return TCIP;
	}
	public void setTCIP(String tCIP) {
		TCIP = tCIP;
	}
	public String getTMNM() {
		return TMNM;
	}
	public void setTMNM(String tMNM) {
		TMNM = tMNM;
	}
	public String getBPTC() {
		return BPTC;
	}
	public void setBPTC(String bPTC) {
		BPTC = bPTC;
	}
	public String getPMTC() {
		return PMTC;
	}
	public void setPMTC(String pMTC) {
		PMTC = pMTC;
	}
	public String getTICD() {
		return TICD;
	}
	public void setTICD(String tICD) {
		TICD = tICD;
	}
	public String getBUSI_TYPE() {
		return BUSI_TYPE;
	}
	public void setBUSI_TYPE(String bUSI_TYPE) {
		BUSI_TYPE = bUSI_TYPE;
	}
	public String getTRANS_TYPE() {
		return TRANS_TYPE;
	}
	public void setTRANS_TYPE(String tRANS_TYPE) {
		TRANS_TYPE = tRANS_TYPE;
	}
	public String getPOS_DEV_ID() {
		return POS_DEV_ID;
	}
	public void setPOS_DEV_ID(String pOS_DEV_ID) {
		POS_DEV_ID = pOS_DEV_ID;
	}
	public String getTRANS_STAT() {
		return TRANS_STAT;
	}
	public void setTRANS_STAT(String tRANS_STAT) {
		TRANS_STAT = tRANS_STAT;
	}
	public String getBANK_STAT() {
		return BANK_STAT;
	}
	public void setBANK_STAT(String bANK_STAT) {
		BANK_STAT = bANK_STAT;
	}
	public String getMER_PROV() {
		return MER_PROV;
	}
	public void setMER_PROV(String mER_PROV) {
		MER_PROV = mER_PROV;
	}
	public String getMER_AREA() {
		return MER_AREA;
	}
	public void setMER_AREA(String mER_AREA) {
		MER_AREA = mER_AREA;
	}
	public String getPOS_PROV() {
		return POS_PROV;
	}
	public void setPOS_PROV(String pOS_PROV) {
		POS_PROV = pOS_PROV;
	}
	public String getPOS_AREA() {
		return POS_AREA;
	}
	public void setPOS_AREA(String pOS_AREA) {
		POS_AREA = pOS_AREA;
	}
	public String getMER_UNIT() {
		return MER_UNIT;
	}
	public void setMER_UNIT(String mER_UNIT) {
		MER_UNIT = mER_UNIT;
	}
	public String getEXTEND1() {
		return EXTEND1;
	}
	public void setEXTEND1(String eXTEND1) {
		EXTEND1 = eXTEND1;
	}
	public String getEXTEND2() {
		return EXTEND2;
	}
	public void setEXTEND2(String eXTEND2) {
		EXTEND2 = eXTEND2;
	}
	public String getRATE_RMB() {
		return RATE_RMB;
	}
	public void setRATE_RMB(String rATE_RMB) {
		RATE_RMB = rATE_RMB;
	}
	public String getRATE_USA() {
		return RATE_USA;
	}
	public void setRATE_USA(String rATE_USA) {
		RATE_USA = rATE_USA;
	}
	public String getIOFG() {
		return IOFG;
	}
	public void setIOFG(String iOFG) {
		IOFG = iOFG;
	}
	
	

}
