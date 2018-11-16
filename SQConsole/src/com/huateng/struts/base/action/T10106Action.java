package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.TAN;

import com.huateng.bo.base.T1010601BO;
import com.huateng.bo.base.T10106BO;
import com.huateng.bo.base.T1010802BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;
import com.huateng.po.base.AgencyInfoTrue;
import com.huateng.po.base.AgencyInfoTruePK;
import com.huateng.po.base.TblInstBdbBankCodeTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10106Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private String agenid;
	private String agenname;
	private String agentype;
	private String trantype;
	private String cardhome;
	private String visa;
	private String Master;
	private String JCB;
	private String DinersClub;
	private String AmericanExpress;
	private String agenregbody;
	private String agenmechcaltype;
	private String agencaltype;
	private String agencalprincycle;
	private String agencalprinmode;
	private String agencalhandcycle;
	private String agencalhandmode;
	private String agencallubcycle;
	private String agencallubmode;
	private String agenbrandratio;
	private String agenmisratio;
	private String agenbankname;
	private String agenentrymode;
	private String bankname;
	private String agenincomeaccountname;
	private String agenincomeaccount;
	private String agenexpressaccountname;
	private String agenexpressaccount;
	private String agensettlementdate;
	private String agencleardetail;
	private String agenpaymentsystem;
	private String EXTENSION_FIELD1;
	private String EXTENSION_FIELD2;
	private String feeFlag;   //费率方式
	private String feeValue;   //费率值
	private String minFee;    //费率下限值
	private String maxFee;    //费率上限值
	private String minTrade;  //最低交易金额
	private String tmpNo;  //发卡行批次号
	
	private String tranType;
	private String trantypename;
	
	
	public String getTmpNo() {
		return tmpNo;
	}
	public void setTmpNo(String tmpNo) {
		this.tmpNo = tmpNo;
	}
	public String getTrantypename() {
		return trantypename;
	}
	public void setTrantypename(String trantypename) {
		this.trantypename = trantypename;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
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
	public String getAgenid() {
		return agenid;
	}
	public void setAgenid(String agenid) {
		this.agenid = agenid;
	}
	public String getAgenname() {
		return agenname;
	}
	public void setAgenname(String agenname) {
		this.agenname = agenname;
	}
	public String getAgentype() {
		return agentype;
	}
	public void setAgentype(String agentype) {
		this.agentype = agentype;
	}
	public String getTrantype() {
		return trantype;
	}
	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}
	public String getCardhome() {
		return cardhome;
	}
	public void setCardhome(String cardhome) {
		this.cardhome = cardhome;
	}
	public String getAgenregbody() {
		return agenregbody;
	}
	public void setAgenregbody(String agenregbody) {
		this.agenregbody = agenregbody;
	}
	public String getAgenmechcaltype() {
		return agenmechcaltype;
	}
	public void setAgenmechcaltype(String agenmechcaltype) {
		this.agenmechcaltype = agenmechcaltype;
	}
	public String getAgencaltype() {
		return agencaltype;
	}
	public void setAgencaltype(String agencaltype) {
		this.agencaltype = agencaltype;
	}
	public String getAgencalprincycle() {
		return agencalprincycle;
	}
	public void setAgencalprincycle(String agencalprincycle) {
		this.agencalprincycle = agencalprincycle;
	}
	public String getAgencalprinmode() {
		return agencalprinmode;
	}
	public void setAgencalprinmode(String agencalprinmode) {
		this.agencalprinmode = agencalprinmode;
	}
	public String getAgencalhandcycle() {
		return agencalhandcycle;
	}
	public void setAgencalhandcycle(String agencalhandcycle) {
		this.agencalhandcycle = agencalhandcycle;
	}
	public String getAgencalhandmode() {
		return agencalhandmode;
	}
	public void setAgencalhandmode(String agencalhandmode) {
		this.agencalhandmode = agencalhandmode;
	}
	public String getAgencallubcycle() {
		return agencallubcycle;
	}
	public void setAgencallubcycle(String agencallubcycle) {
		this.agencallubcycle = agencallubcycle;
	}
	public String getAgencallubmode() {
		return agencallubmode;
	}
	public void setAgencallubmode(String agencallubmode) {
		this.agencallubmode = agencallubmode;
	}
	public String getAgenbrandratio() {
		return agenbrandratio;
	}
	public void setAgenbrandratio(String agenbrandratio) {
		this.agenbrandratio = agenbrandratio;
	}
	public String getAgenmisratio() {
		return agenmisratio;
	}
	public void setAgenmisratio(String agenmisratio) {
		this.agenmisratio = agenmisratio;
	}
	public String getAgenbankname() {
		return agenbankname;
	}
	public void setAgenbankname(String agenbankname) {
		this.agenbankname = agenbankname;
	}
	public String getAgenentrymode() {
		return agenentrymode;
	}
	public void setAgenentrymode(String agenentrymode) {
		this.agenentrymode = agenentrymode;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAgenincomeaccountname() {
		return agenincomeaccountname;
	}
	public void setAgenincomeaccountname(String agenincomeaccountname) {
		this.agenincomeaccountname = agenincomeaccountname;
	}
	public String getAgenincomeaccount() {
		return agenincomeaccount;
	}
	public void setAgenincomeaccount(String agenincomeaccount) {
		this.agenincomeaccount = agenincomeaccount;
	}
	public String getAgenexpressaccountname() {
		return agenexpressaccountname;
	}
	public void setAgenexpressaccountname(String agenexpressaccountname) {
		this.agenexpressaccountname = agenexpressaccountname;
	}
	public String getAgenexpressaccount() {
		return agenexpressaccount;
	}
	public void setAgenexpressaccount(String agenexpressaccount) {
		this.agenexpressaccount = agenexpressaccount;
	}
	public String getAgensettlementdate() {
		return agensettlementdate;
	}
	public void setAgensettlementdate(String agensettlementdate) {
		this.agensettlementdate = agensettlementdate;
	}
	public String getAgencleardetail() {
		return agencleardetail;
	}
	public void setAgencleardetail(String agencleardetail) {
		this.agencleardetail = agencleardetail;
	}
	public String getAgenpaymentsystem() {
		return agenpaymentsystem;
	}
	public void setAgenpaymentsystem(String agenpaymentsystem) {
		this.agenpaymentsystem = agenpaymentsystem;
	}
	
	
	
	public String getFeeFlag() {
		return feeFlag;
	}
	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}
	public String getFeeValue() {
		return feeValue;
	}
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}
	public String getMinFee() {
		return minFee;
	}
	public void setMinFee(String minFee) {
		this.minFee = minFee;
	}
	public String getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(String maxFee) {
		this.maxFee = maxFee;
	}
	public String getMinTrade() {
		return minTrade;
	}
	public void setMinTrade(String minTrade) {
		this.minTrade = minTrade;
	}




	//修改的
	private String AGEN_ID;
	
	private String AGEN_NAME;
	private String AGEN_TYPE;
	private String TRAN_TYPE;
	private String cardhomename;
	private String AGEN_REG_BODY;
	private String AGEN_MECH_CAL_TYPE;
	private String AGEN_CAL_TYPE;
	private String AGEN_CAL_PRIN_CYCLE;
	private String AGEN_CAL_PRIN_MODE;
	private String AGEN_CAL_HAND_CYCLE;
	private String AGEN_CAL_HAND_MODE;
	private String AGEN_CAL_LUB_CYCLE;
	private String AGEN_CAL_LUB_MODE;
	private String AGEN_BRAND_RATIO;
	private String AGEN_MIS_RATIO;
	private String AGEN_BANK_NUM;
	private String AGEN_ENTRY_MODE;
	private String BANK_NAME;
	private String AGEN_INCOME_ACCOUNT_NAME;
	private String AGEN_INCOME_ACCOUNT;
	private String AGEN_EXPENSES_ACCOUNT_NAME;
	private String AGEN_EXPENSES_ACCOUNT;
	private String AGEN_SETTLEMENT_DATE;
	private String AGEN_CLEAR_DETAIL;
	private String AGEN_PAYMENT_SYSTEM;
	private String yinlian;
	
	private String feeflagname;
	private String FEE_FLAG;
	private String FEE_VALUE;
	private String MIN_FEE;
	private String MAX_FEE;
	private String MIN_TRADE;
	
	
	
	
	
	public String getFEE_FLAG() {
		return FEE_FLAG;
	}
	public void setFEE_FLAG(String fEE_FLAG) {
		FEE_FLAG = fEE_FLAG;
	}
	public String getYinlian() {
		return yinlian;
	}
	public void setYinlian(String yinlian) {
		this.yinlian = yinlian;
	}
	public String getAGEN_ID() {
		return AGEN_ID;
	}
	public void setAGEN_ID(String aGENID) {
		AGEN_ID = aGENID;
	}
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
	public String getTRAN_TYPE() {
		return TRAN_TYPE;
	}
	public void setTRAN_TYPE(String tRANTYPE) {
		TRAN_TYPE = tRANTYPE;
	}
	public String getCardhomename() {
		return cardhomename;
	}
	public void setCardhomename(String cardhomename) {
		this.cardhomename = cardhomename;
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
	
	public String getFeeflagname() {
		return feeflagname;
	}
	public void setFeeflagname(String feeflagname) {
		this.feeflagname = feeflagname;
	}
	public String getFEE_VALUE() {
		return FEE_VALUE;
	}
	public void setFEE_VALUE(String fEE_VALUE) {
		FEE_VALUE = fEE_VALUE;
	}
	public String getMIN_FEE() {
		return MIN_FEE;
	}
	public void setMIN_FEE(String mIN_FEE) {
		MIN_FEE = mIN_FEE;
	}
	public String getMAX_FEE() {
		return MAX_FEE;
	}
	public void setMAX_FEE(String mAX_FEE) {
		MAX_FEE = mAX_FEE;
	}
	public String getMIN_TRADE() {
		return MIN_TRADE;
	}
	public void setMIN_TRADE(String mIN_TRADE) {
		MIN_TRADE = mIN_TRADE;
	}



	private T10106BO t10106BO = (T10106BO) ContextUtil.getBean("T10106BO");
	//真实表
	private T1010802BO t1010802BO = (T1010802BO) ContextUtil.getBean("T1010802BO");
	//开户行表
	private T1010601BO t1010601BO = (T1010601BO) ContextUtil.getBean("T1010601BO");
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对机构的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	public String add() throws Exception{
		
		String sql = "select count(*) from tbl_agency_info where agen_id ='"+agenid+"'";
		String countSql = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		if (!countSql.equals("0")) {
			return "机构号不能重复";
		}
		AgencyInfoPK agencyInfoPK = new AgencyInfoPK(agenid,trantype);
		
	//	if(!"0".equals(countSql) ){
		if(t10106BO.get(agencyInfoPK)!=null){
		//	String sql2 = "select tran_type from tbl_agency_info where agen_id ='"+agenid+"'";
		//	String tranType = (String) CommonFunction.getCommQueryDAO().findBySQLQuery(sql2).get(0);
			
		//	AgencyInfoPK agencyInfoPK = new AgencyInfoPK(agenid.trim(),tranType.trim());
			
			AgencyInfo agencyInfo=t10106BO.get(agencyInfoPK);
			String state=agencyInfo.getSTATUE();
			if("3".equals(state)||"7".equals(state)){
			//	agencyInfo.setAGEN_ID(getAgenid());
				agencyInfo.setAGEN_NAME(getAgenname());
				agencyInfo.setAGEN_TYPE(getAgentype());
			//	agencyInfo.setTRAN_TYPE(getTrantype());
				StringBuffer cardhomename=new StringBuffer();
				if (!StringUtil.isNull(getYinlian())){
					cardhomename.append("1");	
				}else{
					cardhomename.append("0");
				}
				if (!StringUtil.isNull(getVisa())){
					cardhomename.append("1");	
				}else{
					cardhomename.append("0");
				}
				if (!StringUtil.isNull(getMaster())){
					cardhomename.append("1");	
				}else{
					cardhomename.append("0");
				}
				if (!StringUtil.isNull(getJCB())){
					cardhomename.append("1");	
				}else{
					cardhomename.append("0");
				}
				if (!StringUtil.isNull(getDinersClub())){
					cardhomename.append("1");	
				}else{
					cardhomename.append("0");
				}
				if (!StringUtil.isNull(getAmericanExpress())){
					cardhomename.append("1");	
				}else{
					cardhomename.append("0");
				}
				agencyInfo.setCARD_HOME(cardhomename.toString());
				agencyInfo.setAGEN_REG_BODY(getAgenregbody());
				System.out.print(agenregbody);
				agencyInfo.setAGEN_MECH_CAL_TYPE(getAgenmechcaltype());
				System.out.println(agenmechcaltype);
				System.out.println(agencyInfo.getAGEN_MECH_CAL_TYPE());
				agencyInfo.setAGEN_CAL_TYPE(getAgencaltype());
				agencyInfo.setAGEN_CAL_PRIN_CYCLE(getAgencalprincycle());
				agencyInfo.setAGEN_CAL_PRIN_MODE(getAgencalprinmode());
				agencyInfo.setAGEN_CAL_HAND_CYCLE(getAgencalhandcycle());
				agencyInfo.setAGEN_CAL_HAND_MODE(getAgencalhandmode());
				agencyInfo.setAGEN_CAL_LUB_CYCLE(getAgencallubcycle());
				agencyInfo.setAGEN_CAL_LUB_MODE(getAgencallubmode());
				agencyInfo.setAGEN_BRAND_RATIO(getAgenbrandratio());
				agencyInfo.setAGEN_MIS_RATIO(getAgenmisratio());
				agencyInfo.setAGEN_BANK_NUM(getAgenbankname());
				agencyInfo.setAGEN_ENTRY_MODE(getAgenentrymode());
				agencyInfo.setBANK_NAME(getBankname());
				agencyInfo.setAGEN_INCOME_ACCOUNT_NAME(getAgenincomeaccountname());
				agencyInfo.setAGEN_INCOME_ACCOUNT(getAgenincomeaccount());
				agencyInfo.setAGEN_EXPENSES_ACCOUNT_NAME(getAgenexpressaccountname());
				agencyInfo.setAGEN_EXPENSES_ACCOUNT(getAgenexpressaccount());
				agencyInfo.setAGEN_SETTLEMENT_DATE(getAgensettlementdate());
				agencyInfo.setAGEN_CLEAR_DETAIL(getAgencleardetail());
				agencyInfo.setAGEN_PAYMENT_SYSTEM(getAgenpaymentsystem());
				agencyInfo.setCRE_OPR_ID(operator.getOprId());
				agencyInfo.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
				agencyInfo.setEXTENSION_FIELD1(EXTENSION_FIELD1);
				agencyInfo.setEXTENSION_FIELD2(EXTENSION_FIELD2);
				
//				agencyInfo.setFEE_FLAG(feeFlag);
//				agencyInfo.setFEE_VALUE(Double.parseDouble(feeValue));
//				agencyInfo.setMAX_FEE(Double.parseDouble(maxFee));
//				agencyInfo.setMIN_FEE(Double.parseDouble(minFee));
//				agencyInfo.setMIN_TRADE(Double.parseDouble(minTrade));
				
				agencyInfo.setSTATUE("0");
				t10106BO.saveOrUpdate(agencyInfo);
				return Constants.SUCCESS_CODE;
			}else{
			    return "相同交易联接类型的机构号已经存在";
			//	 return "机构号已经存在";
			}
		}
		
	
		AgencyInfo agencyInfo=new AgencyInfo();
		
		
		agencyInfo.setAgencyInfoPK(agencyInfoPK);
	//	agencyInfo.setAGEN_ID(getAgenid());
		agencyInfo.setAGEN_NAME(getAgenname());
		agencyInfo.setAGEN_TYPE(getAgentype());
	//	agencyInfo.setTRAN_TYPE(getTrantype());
		StringBuffer cardhomename=new StringBuffer();
		if (!StringUtil.isNull(getYinlian())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getVisa())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getMaster())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getJCB())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getDinersClub())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getAmericanExpress())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		agencyInfo.setCARD_HOME(cardhomename.toString());
		agencyInfo.setAGEN_REG_BODY(getAgenregbody());
		System.out.print(agenregbody);
		agencyInfo.setAGEN_MECH_CAL_TYPE(getAgenmechcaltype());
		System.out.println(agenmechcaltype);
		System.out.println(agencyInfo.getAGEN_MECH_CAL_TYPE());
		agencyInfo.setAGEN_CAL_TYPE(getAgencaltype());
		agencyInfo.setAGEN_CAL_PRIN_CYCLE(getAgencalprincycle());
		agencyInfo.setAGEN_CAL_PRIN_MODE(getAgencalprinmode());
		agencyInfo.setAGEN_CAL_HAND_CYCLE(getAgencalhandcycle());
		agencyInfo.setAGEN_CAL_HAND_MODE(getAgencalhandmode());
		agencyInfo.setAGEN_CAL_LUB_CYCLE(getAgencallubcycle());
		agencyInfo.setAGEN_CAL_LUB_MODE(getAgencallubmode());
		agencyInfo.setAGEN_BRAND_RATIO(getAgenbrandratio());
		agencyInfo.setAGEN_MIS_RATIO(getAgenmisratio());
		agencyInfo.setAGEN_BANK_NUM(getAgenbankname());
		agencyInfo.setAGEN_ENTRY_MODE(getAgenentrymode());
		agencyInfo.setBANK_NAME(getBankname());
		agencyInfo.setAGEN_INCOME_ACCOUNT_NAME(getAgenincomeaccountname());
		agencyInfo.setAGEN_INCOME_ACCOUNT(getAgenincomeaccount());
		agencyInfo.setAGEN_EXPENSES_ACCOUNT_NAME(getAgenexpressaccountname());
		agencyInfo.setAGEN_EXPENSES_ACCOUNT(getAgenexpressaccount());
		agencyInfo.setAGEN_SETTLEMENT_DATE(getAgensettlementdate());
		agencyInfo.setAGEN_CLEAR_DETAIL(getAgencleardetail());
		agencyInfo.setAGEN_PAYMENT_SYSTEM(getAgenpaymentsystem());
		agencyInfo.setCRE_OPR_ID(operator.getOprId());
		agencyInfo.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
		agencyInfo.setEXTENSION_FIELD1(EXTENSION_FIELD1);
		agencyInfo.setEXTENSION_FIELD2(EXTENSION_FIELD2);
		agencyInfo.setSTATUE("0");
		
//		agencyInfo.setFEE_FLAG(feeFlag);
//		agencyInfo.setFEE_VALUE(Double.parseDouble(feeValue));
//		agencyInfo.setMAX_FEE(Double.parseDouble(maxFee));
//		agencyInfo.setMIN_FEE(Double.parseDouble(minFee));
//		agencyInfo.setMIN_TRADE(Double.parseDouble(minTrade));
		
		//对发卡行进行操作
		String id = "";
		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = new TblInstBdbBankCodeTmp();
		String bankSql = "select id from tbl_inst_bdb_bank_code_tmp where tmp_no ='"+tmpNo.trim()+"'";
		List dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(bankSql);
		if(dataList.size() != 0){
			for(int i=0;i<dataList.size();i++){
				id = dataList.get(i).toString();
				tblInstBdbBankCodeTmp = t1010601BO.query(id);
				tblInstBdbBankCodeTmp.setInstCode(agenid);
				t1010601BO.update(tblInstBdbBankCodeTmp);
				
			}		
		}
		
		
		
		
		t10106BO.add(agencyInfo);
		log("添加机构信息成功。操作员编号：");
		return Constants.SUCCESS_CODE;
	}
	
	private String update() throws Exception{
		List<AgencyInfo> agencyInfoList=new ArrayList<AgencyInfo>();
//		System.out.println(AGEN_ID);
		AgencyInfoPK agencyInfoPK = new AgencyInfoPK(agenid,tranType);
		
		AgencyInfo agencyInfo=t10106BO.get(agencyInfoPK);
		agencyInfo.setAGEN_NAME(getAGEN_NAME());
		agencyInfo.setAGEN_TYPE(getAGEN_TYPE());
		System.out.println(getAGEN_TYPE());
//		agencyInfo.setTRAN_TYPE(getTRAN_TYPE());
		StringBuffer cardhomename=new StringBuffer();
		if (!StringUtil.isNull(getYinlian())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getVisa())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getMaster())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getJCB())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getDinersClub())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		if (!StringUtil.isNull(getAmericanExpress())){
			cardhomename.append("1");	
		}else{
			cardhomename.append("0");
		}
		agencyInfo.setCARD_HOME(cardhomename.toString());
		agencyInfo.setAGEN_REG_BODY(getAGEN_REG_BODY());
		agencyInfo.setAGEN_MECH_CAL_TYPE(getAGEN_MECH_CAL_TYPE());
		agencyInfo.setAGEN_CAL_TYPE(getAGEN_CAL_TYPE());
		agencyInfo.setAGEN_CAL_PRIN_CYCLE(getAGEN_CAL_PRIN_CYCLE());
		agencyInfo.setAGEN_CAL_PRIN_MODE(getAGEN_CAL_PRIN_MODE());
		agencyInfo.setAGEN_CAL_HAND_CYCLE(getAGEN_CAL_HAND_CYCLE());
		agencyInfo.setAGEN_CAL_HAND_MODE(getAGEN_CAL_HAND_MODE());
		agencyInfo.setAGEN_CAL_LUB_CYCLE(getAGEN_CAL_LUB_CYCLE());
		agencyInfo.setAGEN_CAL_LUB_MODE(getAGEN_CAL_LUB_MODE());
		agencyInfo.setAGEN_BRAND_RATIO(getAGEN_BRAND_RATIO()!=null?getAGEN_BRAND_RATIO():"");
		agencyInfo.setAGEN_MIS_RATIO(getAGEN_MIS_RATIO()!=null?getAGEN_MIS_RATIO():"");
		agencyInfo.setAGEN_BANK_NUM(getAGEN_BANK_NUM()!=null?getAGEN_BANK_NUM():"");
		agencyInfo.setAGEN_ENTRY_MODE(getAGEN_ENTRY_MODE());
		agencyInfo.setBANK_NAME(getBANK_NAME()!=null?getBANK_NAME():"");
		agencyInfo.setAGEN_INCOME_ACCOUNT_NAME(getAGEN_INCOME_ACCOUNT_NAME()!=null?getAGEN_INCOME_ACCOUNT_NAME():"");
		agencyInfo.setAGEN_INCOME_ACCOUNT(getAGEN_INCOME_ACCOUNT()!=null?getAGEN_INCOME_ACCOUNT():"");
		agencyInfo.setAGEN_EXPENSES_ACCOUNT_NAME(getAGEN_EXPENSES_ACCOUNT_NAME()!=null?getAGEN_EXPENSES_ACCOUNT_NAME():"");
		agencyInfo.setAGEN_EXPENSES_ACCOUNT(getAGEN_EXPENSES_ACCOUNT()!=null?getAGEN_EXPENSES_ACCOUNT():"");
		agencyInfo.setAGEN_SETTLEMENT_DATE(getAGEN_SETTLEMENT_DATE()!=null?getAGEN_SETTLEMENT_DATE():"");
		agencyInfo.setAGEN_CLEAR_DETAIL(getAGEN_CLEAR_DETAIL()!=null?getAGEN_CLEAR_DETAIL():"");
		agencyInfo.setAGEN_PAYMENT_SYSTEM(getAGEN_PAYMENT_SYSTEM()!=null?getAGEN_PAYMENT_SYSTEM():"");
		agencyInfo.setEXTENSION_FIELD1(EXTENSION_FIELD1);
		agencyInfo.setEXTENSION_FIELD2(EXTENSION_FIELD2);
	//	System.out.println(feeflagname);
//		agencyInfo.setFEE_FLAG(FEE_FLAG);
//		agencyInfo.setFEE_VALUE(Double.parseDouble(FEE_VALUE));
//		agencyInfo.setMAX_FEE(Double.parseDouble(MAX_FEE));
//		agencyInfo.setMIN_FEE(Double.parseDouble(MIN_FEE));
//		agencyInfo.setMIN_TRADE(Double.parseDouble(MIN_TRADE));
		
		
		//如果状态时正常的话，我们就需要往表里面插修改人和修改时间，状态也要修改
		String state=agencyInfo.getSTATUE();
		if(("1").equals(state)){
			//agencyInfo.setUP_OPR_ID(operator.getOprId());
			//agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyInfo.setSTATUE("2");
		}
		//新增拒绝修改之后状态变为新增未审核
		if(("3").equals(state)){
		//	agencyInfo.setUP_OPR_ID(operator.getOprId());
		//	agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyInfo.setSTATUE("0");
		}
		//修改拒绝之后状态变为修改未审核
		if(("4").equals(state)){
		//	agencyInfo.setUP_OPR_ID(operator.getOprId());
		//	agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyInfo.setSTATUE("2");
		}
		//注销拒绝后变成注销未审核
		if(("6").equals(state)){
		//	agencyInfo.setUP_OPR_ID(operator.getOprId());
		//	agencyInfo.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyInfo.setSTATUE("8");
		}
		agencyInfo.setCRE_OPR_ID(operator.getOprId());
		agencyInfo.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
		agencyInfoList.add(agencyInfo);
		t10106BO.update(agencyInfoList);
		log("更新机构信息成功。操作员编号：" + operator.getOprId());
	    return Constants.SUCCESS_CODE;
	}

	private String delete() throws IllegalAccessException, InvocationTargetException{
		AgencyInfoPK agencyInfoPK = new AgencyInfoPK(agenid,tranType);
		AgencyInfoTruePK agencyInfoTruePK = new AgencyInfoTruePK(agenid,tranType);
		
		AgencyInfo agencyInfo=t10106BO.get(agencyInfoPK);
		String state=agencyInfo.getSTATUE();
		
		//对开户行进行操作
		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = null;
		String bankSql = "select id,tmp_no,inst_code,bank_code,state from TBL_INST_BDB_BANK_CODE_TMP where inst_code = '"+agenid.trim()+"'";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(bankSql);
		
		List<TblInstBdbBankCodeTmp> tblInstBdbBankCodeTmpList = new ArrayList<TblInstBdbBankCodeTmp>(); 
		List<String> strList = new ArrayList<String >();
		
		if(dataList.size() != 0){
			for(int i=0;i<dataList.size();i++){
				tblInstBdbBankCodeTmp = t1010601BO.query(dataList.get(i)[0].toString());
				strList.add(tblInstBdbBankCodeTmp.getId());
				tblInstBdbBankCodeTmp.setState("4");  //状态为删除待审核
				tblInstBdbBankCodeTmpList.add(tblInstBdbBankCodeTmp);				
			}
		}
		
		
		//新增 未审核、新增审核拒绝状态的注销直接删除
		if(("0").equals(state)||("3").equals(state)){
			t10106BO.delete(agencyInfo);
			t1010601BO.delete(strList);
			return Constants.SUCCESS_CODE;
		}
		//正常
		if(("1").equals(state)){
			agencyInfo.setSTATUE("8");	
		}
		//修改未审核
		if(("2").equals(state)){
			agencyInfo.setSTATUE("8");	
		}
		//修改拒绝
		if(("4").equals(state)){
			agencyInfo.setSTATUE("8");	
		}
		//修改状态到临时表
		List<AgencyInfo> agencyInfoList=new ArrayList<AgencyInfo>();
		agencyInfoList.add(agencyInfo);
		t10106BO.update(agencyInfoList);
		t1010601BO.update(tblInstBdbBankCodeTmpList);
		
		//修改状态和数据到真实表
		AgencyInfoTrue agencyInfotrue=t1010802BO.get(agencyInfoTruePK);
		BeanUtils.copyProperties(agencyInfo,agencyInfotrue);
		agencyInfo.setCRE_OPR_ID(operator.getOprId());
		agencyInfo.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
		t1010802BO.update(agencyInfotrue);
		return Constants.SUCCESS_CODE;
	}
}
