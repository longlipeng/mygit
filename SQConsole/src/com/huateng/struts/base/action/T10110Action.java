package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T10110BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T10110Action extends BaseAction{
	private static final long serialVersionUID = 1L;
	private T10110BO t10110BO = (T10110BO) ContextUtil.getBean("T10110BO");
	
	private String FEE_ID;
	
	public String getFEE_ID() {
		return FEE_ID;
	}
	
	public void setFEE_ID(String fEEID) {
		FEE_ID = fEEID;
	}
	private String AGEN_ID;
	private String AGEN_TYPE;
	private String TERM_ID;
	private String MTCH_NO;
	private String MCC_CODE;
	private String TRADE_ACCEPT_REG;
	private String AGEN_TARGET_BODY;
	private String AGEN_CRE_CARD;
	private String CARD_STYLE;
	private String CARD_MEDIUM;
	private String TRADE_CHANNEL;
	private String BUSINESS_TYPE;
	private String TRAN_TYPE;
	private String MCHT_RATE_TYPE;
	private String EXTEN_FIELD1;  //交易连接类型
	
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
	private String MCHT_RATE_METHOD;
	private String AMOUNT_LIMIT;
	private String MCHT_RATE_PARAM;
	private String MCHT_PERCENT_LIMIT;
	private String MCHT_PERCENT_MAX;
	private String MCHT_LUB_TYPE;
	private String MCHT_LUB_METHOD;
	private String MCHT_LUB_PARAM;
	private String MCHT_LUB_PERCENT_LIMIT;
	private String MCHT_LUB_PERCENT_MAX;
	private String RATE_PARAM1;
	private String LUB_PARAM1;
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
	
	
	public String getAMOUNT_LIMIT() {
		return AMOUNT_LIMIT;
	}
	
	public void setAMOUNT_LIMIT(String aMOUNTLIMIT) {
		AMOUNT_LIMIT = aMOUNTLIMIT;
	}
	
	
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
			log("操作员编号：" + operator.getOprId()+ "，对机构分润的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String add() {
		AgencyFeeLubTmp  agencyFeeLub=new AgencyFeeLubTmp();
		ICommQueryDAO dao=CommonFunction.getCommQueryDAO();
		String sql="";
		String result="";
//		sql="select count(*) from TBL_AGENCY_FEE_LUB_tmp where AGEN_ID='" +AGEN_ID+ "' and AGEN_TYPE='" +AGEN_TYPE+ "' and TERM_ID='" +TERM_ID+
//			"' and MTCH_NO='"+MTCH_NO+"' and TRADE_ACCEPT_REG='" +TRADE_ACCEPT_REG+ "' and MCC_CODE='" +MCC_CODE+ "' and AGEN_TARGET_BODY='"
//			+AGEN_TARGET_BODY+ "' and AGEN_CRE_CARD='" +AGEN_CRE_CARD+ "' and CARD_STYLE='" +CARD_STYLE+ "' and CARD_MEDIUM='" +CARD_MEDIUM
//			+"' and TRADE_CHANNEL='" +TRADE_CHANNEL+ "' and BUSINESS_TYPE='" +BUSINESS_TYPE+ "' and TRAN_TYPE='"+TRAN_TYPE
//			+"' and STATUE not in('3','7') ";
		sql = "select count(*) from TBL_AGENCY_FEE_LUB_tmp where AGEN_ID = '"+AGEN_ID+"' and MCC_CODE = '"+MCC_CODE+"'and STATUE not in('3','7') ";
		result = dao.findCountBySQLQuery(sql);
		//在机构号，机构类型，终端号，商户号，交易受理地区，mcc码，目标机构，请输入发卡机构，卡类型，卡介质，交易渠道，业务类型，交易类型都相同的情况下才会提示已存在
		if(!"0".equals(result)){
			return "同一MCC码的机构分润信息已存在";
		}
		if(!"*".equals(AGEN_ID)){
			sql = "select count(*) from tbl_agency_info_true where AGEN_ID='"+StringUtil.replace(AGEN_ID, "'", "''")+"' and statue='1'";
			result = dao.findCountBySQLQuery(sql);
			if("0".equals(result))
//				return "该机构代码系统中不存在或已注销!";
				return "该机构代码系统中不存在或非正常状态!";
		}
		/*if(!"*".equals(TERM_ID)){
			sql="select count(*) from tbl_term_inf where term_id='"+StringUtil.replace(TERM_ID, "'", "''")+"' ";
			result=dao.findCountBySQLQuery(sql);
			if("0".equals(result))
				return "该终端号系统中不存在!";
		}
		if(!"*".equals(MTCH_NO)){
			sql="select count(*) from tbl_mcht_base_inf where mcht_no='"+StringUtil.replace(MTCH_NO, "'", "''")+"'";
			result=dao.findCountBySQLQuery(sql);
			if("0".equals(result))
				return "该商户号系统中不存在!";
		}*/
		agencyFeeLub.setFEE_ID(GenerateNextId.getFeeId());//分润编号
		agencyFeeLub.setAGEN_ID(AGEN_ID);//机构代码
		agencyFeeLub.setAGEN_TYPE(AGEN_TYPE);//机构类型
		agencyFeeLub.setTERM_ID(TERM_ID);//终端号
		agencyFeeLub.setMTCH_NO(MTCH_NO);//商户号
		agencyFeeLub.setMCC_CODE(MCC_CODE);//MCC码
		agencyFeeLub.setTRADE_ACCEPT_REG(TRADE_ACCEPT_REG);//交易受理地区
		agencyFeeLub.setAGEN_TARGET_BODY(AGEN_TARGET_BODY);//目标机构
		agencyFeeLub.setAGEN_CRE_CARD(AGEN_CRE_CARD);//发卡机构
		agencyFeeLub.setCARD_STYLE(CARD_STYLE);//卡类型
		agencyFeeLub.setCARD_MEDIUM(CARD_MEDIUM);//卡介质
		agencyFeeLub.setTRADE_CHANNEL(TRADE_CHANNEL);//交易渠道
		agencyFeeLub.setBUSINESS_TYPE(BUSINESS_TYPE);//业务类型
		agencyFeeLub.setTRAN_TYPE(TRAN_TYPE);//交易类型
		agencyFeeLub.setMCHT_RATE_TYPE(MCHT_RATE_TYPE);//机构费率类型
		agencyFeeLub.setMCHT_RATE_METHOD(MCHT_RATE_METHOD);//机构费率方式
		agencyFeeLub.setAMOUNT_LIMIT(AMOUNT_LIMIT);//最低交易金额
		//机构费率参数与机构分润参数 要唯一，所以将其值设为主键的值
		agencyFeeLub.setMCHT_RATE_PARAM(agencyFeeLub.getFEE_ID());
		agencyFeeLub.setMCHT_LUB_PARAM(agencyFeeLub.getFEE_ID());
		agencyFeeLub.setMCHT_PERCENT_LIMIT(MCHT_PERCENT_LIMIT);//机构费率百分比下限值
		agencyFeeLub.setMCHT_PERCENT_MAX(MCHT_PERCENT_MAX);//机构费率百分比上限值
		agencyFeeLub.setMCHT_LUB_TYPE(MCHT_LUB_TYPE);//机构分润类型
		agencyFeeLub.setMCHT_LUB_METHOD(MCHT_LUB_METHOD);//机构分润方式
		agencyFeeLub.setMCHT_LUB_PERCENT_LIMIT(MCHT_LUB_PERCENT_LIMIT);//机构分润百分比下限值
		agencyFeeLub.setMCHT_LUB_PERCENT_MAX(MCHT_LUB_PERCENT_MAX);//机构分润百分比上限值
		agencyFeeLub.setRATE_PARAM1(RATE_PARAM1);//费率值
		agencyFeeLub.setLUB_PARAM1(LUB_PARAM1);//分润参数
		agencyFeeLub.setEXTEN_FIELD1(EXTEN_FIELD1);//交易联接类型
		agencyFeeLub.setCRE_OPR_ID(operator.getOprId());
		agencyFeeLub.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
		agencyFeeLub.setUP_OPR_ID(operator.getOprId());
		agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
		agencyFeeLub.setSTATUE(Constants.ADD_TO_CHECK);//新增待审核状态
		t10110BO.add(agencyFeeLub);
		log("添加机构分润信息成功。操作员编号：" + "，分润编号：" +getFEE_ID() );
		return Constants.SUCCESS_CODE;
	}
	
	private String update() throws Exception{
		ICommQueryDAO dao=CommonFunction.getCommQueryDAO();
		String sql="";
		String result="";
		sql="select FEE_ID from TBL_AGENCY_FEE_LUB_tmp where AGEN_ID='"+AGEN_ID+"' and AGEN_TYPE='"+AGEN_TYPE+"' and TERM_ID='"+TERM_ID+"' and MTCH_NO='"+MTCH_NO+"' and TRADE_ACCEPT_REG='"+TRADE_ACCEPT_REG+"' and MCC_CODE='"+MCC_CODE+"' and AGEN_TARGET_BODY='"+AGEN_TARGET_BODY+"' and AGEN_CRE_CARD='"+AGEN_CRE_CARD+"' and CARD_STYLE='"+CARD_STYLE+"' and CARD_MEDIUM='"+CARD_MEDIUM+"' and TRADE_CHANNEL='"+TRADE_CHANNEL+"' and BUSINESS_TYPE='"+BUSINESS_TYPE+"' and TRAN_TYPE='"+TRAN_TYPE+"'";
		List list=dao.findBySQLQuery(sql);
		if(list.size()!=0){
		if(!list.get(0).equals(FEE_ID)){
			return "该机构分润信息系统中已经存在";
		}
		}
		if(!"*".equals(TERM_ID)){
			sql="select count(*) from tbl_agency_info_true where AGEN_ID='"+StringUtil.replace(AGEN_ID, "'", "''")+"' and statue='1'";
			result=dao.findCountBySQLQuery(sql);
			if("0".equals(result))return "该机构代码系统中不存在或已注销!";
		}
		/*if(!"*".equals(TERM_ID)){
			sql="select count(*) from tbl_term_inf where term_id='"+StringUtil.replace(TERM_ID, "'", "''")+"' ";
			result=dao.findCountBySQLQuery(sql);
			if("0".equals(result))return "该终端号系统中不存在!";
		}
		if(!"*".equals(MTCH_NO)){
			sql="select count(*) from tbl_mcht_base_inf where mcht_no='"+StringUtil.replace(MTCH_NO, "'", "''")+"'";
			result=dao.findCountBySQLQuery(sql);
			if("0".equals(result))return "该商户号系统中不存在!";
		}*/
		
		List<AgencyFeeLubTmp> agencyInfoList=new ArrayList<AgencyFeeLubTmp>();
		AgencyFeeLubTmp agencyFeeLub=t10110BO.getTmp(getFEE_ID());
		agencyFeeLub.setAGEN_ID(AGEN_ID);//机构代码
		agencyFeeLub.setAGEN_TYPE(AGEN_TYPE);//机构类型
		agencyFeeLub.setTERM_ID(TERM_ID);//终端号
		agencyFeeLub.setMTCH_NO(MTCH_NO);//商户号
		agencyFeeLub.setMCC_CODE(MCC_CODE);//MCC码
		agencyFeeLub.setTRADE_ACCEPT_REG(TRADE_ACCEPT_REG);//交易受理地区
		agencyFeeLub.setAGEN_TARGET_BODY(AGEN_TARGET_BODY);//目标机构
		agencyFeeLub.setAGEN_CRE_CARD(AGEN_CRE_CARD);//发卡机构
		agencyFeeLub.setCARD_STYLE(CARD_STYLE);//卡类型
		agencyFeeLub.setCARD_MEDIUM(CARD_MEDIUM);//卡介质
		agencyFeeLub.setTRADE_CHANNEL(TRADE_CHANNEL);//交易渠道
		agencyFeeLub.setBUSINESS_TYPE(BUSINESS_TYPE);//业务类型
		agencyFeeLub.setTRAN_TYPE(TRAN_TYPE);//交易类型
		agencyFeeLub.setMCHT_RATE_TYPE(MCHT_RATE_TYPE);//机构费率类型
		agencyFeeLub.setMCHT_RATE_METHOD(MCHT_RATE_METHOD);//机构费率方式
		agencyFeeLub.setAMOUNT_LIMIT(AMOUNT_LIMIT);//最低交易金额
		agencyFeeLub.setEXTEN_FIELD1(EXTEN_FIELD1);//交易连接类型
		agencyFeeLub.setMCHT_RATE_PARAM(getMCHT_RATE_PARAM()!=null?getMCHT_RATE_PARAM():"");//机构费率参数
		agencyFeeLub.setMCHT_PERCENT_LIMIT(MCHT_PERCENT_LIMIT);//机构费率百分比下限值
		agencyFeeLub.setMCHT_PERCENT_MAX(MCHT_PERCENT_MAX);//机构费率百分比上限值
		agencyFeeLub.setMCHT_LUB_TYPE(getMCHT_LUB_TYPE()!=null?getMCHT_LUB_TYPE():"");//机构分润类型
		agencyFeeLub.setMCHT_LUB_METHOD(getMCHT_LUB_METHOD()!=null?getMCHT_LUB_METHOD():"");//机构分润方式
		agencyFeeLub.setMCHT_LUB_PARAM(getMCHT_LUB_PARAM()!=null?getMCHT_LUB_PARAM():"");//机构分润参数
		agencyFeeLub.setMCHT_LUB_PERCENT_LIMIT(getMCHT_LUB_PERCENT_LIMIT()!=null?getMCHT_LUB_PERCENT_LIMIT():"");//机构分润百分比下限值
		agencyFeeLub.setMCHT_LUB_PERCENT_MAX(getMCHT_LUB_PERCENT_MAX()!=null?getMCHT_LUB_PERCENT_MAX():"");//机构分润百分比上限值
		agencyFeeLub.setRATE_PARAM1(getRATE_PARAM1()!=null?getRATE_PARAM1():"");//费率参数
		agencyFeeLub.setLUB_PARAM1(getLUB_PARAM1()!=null?getLUB_PARAM1():"");//分润参数
		//正常状态:update状态、修改人和修改时间
		String state = agencyFeeLub.getSTATUE();
		if((Constants.NORMAL).equals(state)){
//			agencyFeeLub.setUP_OPR_ID(operator.getOprId());
//			agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyFeeLub.setSTATUE(Constants.MODIFY_TO_CHECK);
		}
		//新增拒绝修改之后状态变为新增未审核
		if((Constants.ADD_REFUSE).equals(state)){
//			agencyFeeLub.setUP_OPR_ID(operator.getOprId());
//			agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyFeeLub.setSTATUE(Constants.MODIFY_TO_CHECK);
		}
		//修改未审核状态仍为修改未审核
		if((Constants.MODIFY_TO_CHECK).equals(state)){
//			agencyFeeLub.setUP_OPR_ID(operator.getOprId());
//			agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyFeeLub.setSTATUE(Constants.MODIFY_TO_CHECK);
		}
		//新增待审核修改后成为修改待审核
		if((Constants.ADD_TO_CHECK).equals(state)){
//			agencyFeeLub.setUP_OPR_ID(operator.getOprId());
//			agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
			agencyFeeLub.setSTATUE(Constants.MODIFY_TO_CHECK);
		}
		agencyFeeLub.setCRE_OPR_ID(operator.getOprId());
		agencyFeeLub.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
		agencyInfoList.add(agencyFeeLub);
		t10110BO.updateTmp(agencyInfoList);
		log("更新机构分润信息成功。操作员编号：" + operator.getOprId());
	    return Constants.SUCCESS_CODE;
	}

	private String delete() {//注销
		AgencyFeeLubTmp agencyFeeLub=t10110BO.getTmp(this.getFEE_ID());
		String state = agencyFeeLub.getSTATUE();
		
		//修改状态到临时表
		List<AgencyFeeLubTmp> agencyFeeInfoList=new ArrayList<AgencyFeeLubTmp>();
		
		//正常状态注销
		if((Constants.ADD_TO_CHECK).equals(state)){//新增待审核状态,注销时删除
			t10110BO.delete(this.getFEE_ID());
			t10110BO.delete2(this.getFEE_ID());
			return Constants.SUCCESS_CODE;
		}
		if((Constants.NORMAL).equals(state)){//正常状态，正常注销
			agencyFeeLub.setSTATUE(Constants.LOGOUT_TO_CHECK);	
		}
		if((Constants.ADD_REFUSE).equals(state)){//新增拒绝状态,注销时删除
			t10110BO.delete(this.getFEE_ID());
			t10110BO.delete2(this.getFEE_ID());
			return Constants.SUCCESS_CODE;	
		}
		agencyFeeLub.setCRE_OPR_ID(operator.getOprId());
		agencyFeeLub.setCRE_OPR_DATE(CommonFunction.getCurrentDateTime());
		
		agencyFeeInfoList.add(agencyFeeLub);
		t10110BO.updateTmp(agencyFeeInfoList);
		return Constants.SUCCESS_CODE;
	}
}
