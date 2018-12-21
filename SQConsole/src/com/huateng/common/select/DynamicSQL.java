/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-10-26       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.common.select;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class DynamicSQL extends DynamicSQLSupport{
	
	static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	public static DynamicSqlBean getMchntId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		sql += " and MCHT_STATUS = '0' ";
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdTrue(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		sql += " and (MCHT_STATUS = '0' OR MCHT_STATUS = '1')";
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
public static DynamicSqlBean getFeeValueRefuse(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct FEE_VALUE as id, FEE_VALUE  from TBL_AGENT_FEE_REFUSE_INFO ";
		
		sql += provideSql(sql, "FEE_VALUE", inputValue);
//		sql += " order by MCC_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
public static DynamicSqlBean getMccCodeRefuse(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct MCC_CODE as id, MCC_CODE  from TBL_AGENT_FEE_REFUSE_INFO ";
		
		sql += provideSql(sql, "MCC_CODE", inputValue);
//		sql += " order by MCC_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

//商户MCC查询，包含*
	public static DynamicSqlBean getMchntMccRefuse(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql= "select a.mchnt_tp as tp ,'('||b.descr||')'||a.mchnt_tp ||' - '||a.descr as tpdesc from tbl_inf_mchnt_tp a left join tbl_inf_mchnt_tp_grp b on a.mchnt_tp_grp = b.mchnt_tp_grp";
		
		sql += provideSql(sql, "'('||b.descr||')'||a.mchnt_tp ||' - '||a.descr", inputValue);

		sql += " union select trim(key) as tp,trim(key) ||' - ' ||trim(value) as tpdesc from cst_sys_param where owner='ALLMCC' and type='00' ";
		sql += provideSql(sql, "trim(key) ||' - ' ||trim(value)", inputValue);
		
		String newSql = "select * from  ("+sql+") order by tpdesc desc";
		
		return new DynamicSqlBean(newSql, commQueryDAO);
	}
	
public static DynamicSqlBean getFeeValue(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct FEE_VALUE as id, FEE_VALUE  from TBL_AGENT_FEE_TMP ";
		
		sql += provideSql(sql, "FEE_VALUE", inputValue);
//		sql += " order by MCC_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
public static DynamicSqlBean getMccCode(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct MCC_CODE as id, MCC_CODE  from TBL_AGENT_FEE_TMP ";
		
		sql += provideSql(sql, "MCC_CODE", inputValue);
//		sql += " order by MCC_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean geteditBrh(String inputValue, Operator operator, HttpServletRequest request){
			
		
		String sql = "SELECT BRANCH_NO,BRANCH_NO||'-'||BRANCH_NAME as BRH_NAME FROM tbl_branch_manage where state In('0','1','2','7','8')";
			
		sql += provideSql(sql, "branch_no||'-'||branch_name", inputValue);
		sql += " ORDER BY branch_no";
		

		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
public static DynamicSqlBean getconTxn(String inputValue, Operator operator, HttpServletRequest request){
			
		
		String sql = "SELECT FUNC_ID,FUNC_NAME FROM TBL_FUNC_INF WHERE FUNC_TYPE = '0' ";
			
		sql += provideSql(sql, "FUNC_NAME", inputValue);
		sql += " ORDER BY FUNC_ID";
	
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	

public static DynamicSqlBean getoprNo(String inputValue, Operator operator, HttpServletRequest request){
	
	
	String sql = "SELECT Distinct a.OPR_ID as id,a.OPR_ID FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID AND b.BRH_ID IN "
	+ operator.getBrhBelowId();
		
	sql += provideSql(sql, "a.OPR_ID", inputValue);
	sql += " ORDER BY a.OPR_ID";

	return new DynamicSqlBean(sql, commQueryDAO);
}
	public static DynamicSqlBean getAgentNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select AGENT_NO, trim(AGENT_NO) ||' - '|| trim(AGENT_NM) from TBL_AGENT_INFO ";
		
		sql += provideSql(sql, "AGENT_NO ||' - '|| AGENT_NM", inputValue);
//		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
//		sql += " and (MCHT_STATUS = '0' OR MCHT_STATUS = '1')";
//		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
public static DynamicSqlBean getBankNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select BANK_ACCOUNT, trim(BANK_ACCOUNT) ||' - '|| trim(BANK_NAME) from TBL_BANKNO_INFO where 1 = 1 ";
		sql += provideSql(sql, "trim(BANK_ACCOUNT) ||' - '|| trim(BANK_NAME)", inputValue);
		return new DynamicSqlBean(sql, commQueryDAO);
}
	
public static DynamicSqlBean getbrhId(String inputValue, Operator operator, HttpServletRequest request){

		String sql = "select BRANCH_NO, trim(BRANCH_NO) ||' - '|| trim(BRANCH_NAME) from TBL_BRANCH_MANAGE where state='1' and BRANCH_NO in "+operator.getBrhBelowId();
		
		sql += provideSql(sql, "trim(BRANCH_NO) ||' - '|| trim(BRANCH_NAME)", inputValue);
		return new DynamicSqlBean(sql, commQueryDAO);
	}


	public static DynamicSqlBean getDiscCd(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select DISC_CD, DISC_CD|| '-' ||DISC_NM from tbl_inf_disc_cd_temp ";
		
		sql += provideSql(sql, "DISC_CD", inputValue);
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' ";
		//TODO 当全部都输对时，会查询不出，但是现在我不想改了
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/** 包含*－－所有商户 */
	public static DynamicSqlBean getMchntNo2(String inputValue, Operator operator, HttpServletRequest request){//20121107
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' " ;
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		sql += " and MCHT_STATUS = '0' ";
		
		sql += " union select key,key || '-' ||value from cst_sys_param where owner='ALLMCHNT' and type='00' ";
		sql += provideSql(sql, "value", inputValue);
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdTmp(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP where MCHT_STATUS = '0' ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMchntIdAll(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF_TMP ";
		
		sql += provideSql(sql, "MCHT_NO ||' - '|| MCHT_NM", inputValue);
		//sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 根据客户账户条件查询
	 * @return
	 */
	public static DynamicSqlBean getAccountAll(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select REDEMPTION_ACCOUNT, trim(REDEMPTION_ACCOUNT) ||' - '|| trim(REDEMPTION_ACCOUNT_NAME) from TBL_SETTLE_REDEMPTION_INF_TMP ";
		
		sql += provideSql(sql, "REDEMPTION_ACCOUNT ||' - '|| REDEMPTION_ACCOUNT_NAME", inputValue);
		sql += " order by REDEMPTION_ACCOUNT";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 根据回款账户条件查询
	 * @return
	 */
	public static DynamicSqlBean getPayAccountAll(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select PAYMENT_ACCOUNT, trim(PAYMENT_ACCOUNT) ||' - '|| trim(PAYMENT_ACCOUNT_NAME) from TBL_PAYMENT_RESERVE_TMP ";
		
		sql += provideSql(sql, "PAYMENT_ACCOUNT ||' - '|| PAYMENT_ACCOUNT_NAME", inputValue);
		sql += " order by PAYMENT_ACCOUNT";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 根据回款账户条件查询
	 * @return
	 */
	public static DynamicSqlBean getFocusAccountAll(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select FOCUS_ACCOUNT, trim(FOCUS_ACCOUNT) ||' - '|| trim(FOCUS_ACCOUNT_NAME) from TBL_FOCUS_RESERVE_TMP ";
		
		sql += provideSql(sql, "FOCUS_ACCOUNT ||' - '|| FOCUS_ACCOUNT_NAME", inputValue);
		sql += " order by FOCUS_ACCOUNT";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**
	 * 根据白名单账户条件查询
	 * @return
	 */
	public static DynamicSqlBean getRosterAccountAll(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select ROSTER_ACCOUNT, trim(ROSTER_ACCOUNT) ||' - '|| trim(ROSTER_ACCOUNT_NAME) from TBL_SETTLE_ROSTER_INF_TMP ";
		
		sql += provideSql(sql, "ROSTER_ACCOUNT ||' - '|| ROSTER_ACCOUNT_NAME", inputValue);
		sql += " order by ROSTER_ACCOUNT";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//事前风险控制查询，商户名称20120813
	public static DynamicSqlBean getMchntNameAll(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select MCHT_NM, trim(MCHT_NM) from TBL_MCHT_BASE_INF_TMP ";
		
		sql += provideSql(sql, "MCHT_NM", inputValue.trim());
		sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		
		sql += " order by MCHT_NM";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

   public static DynamicSqlBean getAgenID(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select AGEN_ID,AGEN_ID||' - '||AGEN_NAME from TBL_AGENCY_INFO a where a.STATUE In('0','1','2','3','4','6','8')";
		
		sql += provideSql(sql, "AGEN_ID ||' - '|| AGEN_NAME", inputValue);
		
		sql += " order by AGEN_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   public static DynamicSqlBean getNormalAgentID(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select AGEN_ID,AGEN_ID||' - '||AGEN_NAME from TBL_AGENCY_INFO a where a.STATUE='1'";
		
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlIn(sql, "substr(AGEN_ID,1,4)", request.getParameter("parentP"));
		}
		
		sql += provideSql(sql, "AGEN_ID ||' - '|| AGEN_NAME", inputValue);
		
		sql += " order by AGEN_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   //新增机构费率中的交易联接类型
   public static DynamicSqlBean getTranType2(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select key,key|| ' - ' ||value from cst_sys_param where owner = 'TRAN_TYPE'";
		
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			String s = request.getParameter("parentP");
			sql += " and key in (select tran_type from TBL_AGENCY_INFO_TRUE where statue='1' and agen_id = "+s.trim()+")";
		}
		
	//	sql += provideSql(sql, "key|| ' - ' ||value", inputValue);
		
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   
   public static DynamicSqlBean getNormalAgentIDOnly(String inputValue, Operator operator, HttpServletRequest request){
		
	   String sql = "SELECT trim(KEY), trim(KEY) ||' - '||trim(VALUE) FROM CST_SYS_PARAM WHERE OWNER = '"+request.getParameter("parentP")+"' ";
		
		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
   public static DynamicSqlBean getNormalAgentIDOnlyOne(String inputValue, Operator operator, HttpServletRequest request){
		
	   String sql = "SELECT trim(KEY), trim(KEY) ||' - '||trim(VALUE) FROM CST_SYS_PARAM WHERE OWNER like 'AgentID%' and owner not like 'AgentID%Index' ";
		
		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
	public static DynamicSqlBean getAreaCode(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select CITY_CODE,CITY_CODE||' - '||CITY_DES from tbl_CITY_CODE ";
		sql += provideSql(sql, "CITY_CODE||' - '||CITY_DES", inputValue);
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**查询不包含*的地区码
	 * @param inputValue
	 * @param operator
	 * @param request
	 * @return
	 */
	public static DynamicSqlBean getAreaCodePart(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select CITY_CODE,CITY_CODE||' - '||CITY_DES from tbl_CITY_CODE WHERE trim(CITY_CODE) != '*'";
		
		sql += provideSql(sql, "CITY_CODE||' - '||CITY_DES", inputValue);
		
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//排序类型
    public static DynamicSqlBean getOrderOp(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT trim(KEY), trim(KEY) ||' - '||trim(VALUE) FROM CST_SYS_PARAM WHERE OWNER = 'ORDEROPTION' ";
		
		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//目的机构
    public static DynamicSqlBean getAgencyInf(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT AGEN_ID||'-'||AGEN_NAME,AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE where statue='1' ";
		
		sql += provideSql(sql, "AGEN_ID||'-'||AGEN_NAME", inputValue);
		
		sql += " order by AGEN_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    
 
    public static DynamicSqlBean getAgencyInf1(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT AGEN_ID,AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE where statue='1' ";
		
		sql += provideSql(sql, "AGEN_ID||'-'||AGEN_NAME", inputValue);
		
		sql += " order by AGEN_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

    
  //机构费率 下拉框选择机构
    public static DynamicSqlBean getAgencyInf2(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT distinct(AGEN_ID),AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE where statue='1' ";
		
		sql += provideSql(sql, "AGEN_ID||'-'||AGEN_NAME", inputValue);
		
		sql += " order by AGEN_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//卡类型
    public static DynamicSqlBean getRiskCardType(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT trim(KEY), trim(KEY) ||' - '||trim(VALUE) FROM CST_SYS_PARAM WHERE OWNER = 'CARDTYPE' ";
		
		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    
  //渠道
    public static DynamicSqlBean getChannel(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='CHANNEL'";
		
		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    
    //终端交易限额表商户号
    public static DynamicSqlBean getMchtcd(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select Distinct a.MCHT_NO, trim(a.MCHT_NO)||' - '|| trim(a.MCHT_NM) from TBL_MCHT_BASE_INF a,TBL_TERM_INF b where a.MCHT_NO=b.MCHT_CD ";
		
		sql += provideSql(sql, "a.MCHT_NO||' - '||a.MCHT_NM", inputValue);
		
		sql += " order by a.MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
  
    public static DynamicSqlBean getMchtIdNew(String inputValue, Operator operator, HttpServletRequest request){//20120920新增
		
		String sql = "select Distinct MCHT_NO, trim(MCHT_NO)||' - '|| trim(MCHT_NM) from TBL_MCHT_BASE_INF  where 1=1 ";
		
		sql += provideSql(sql, "MCHT_NO||' - '||MCHT_NM", inputValue);
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    
    //渠道交易交易
    public static DynamicSqlBean getTxnClt(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='TXNTYPE'";
		
		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    
   //机构所属地区getAreaCodecode_as
   public static DynamicSqlBean getAreaCodecode_as(String inputValue, Operator operator, HttpServletRequest request){
	  return getAreaCodecode(inputValue,operator,request);
   }
   
   //地区代码下拉框
   public static DynamicSqlBean getAreaCodecode(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select CITY_CODE,CITY_CODE||' - '||CITY_DES from TBL_CITY_CODE";
		
		sql += provideSql(sql, "CITY_CODE||' - '||CITY_DES", inputValue);
		
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   //地区代码下拉框  带*-全部地区
   public static DynamicSqlBean getAreaCodecode2(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select CITY_CODE,CITY_CODE||' - '||CITY_DES from TBL_CITY_CODE";
		
		sql += provideSql(sql, "CITY_CODE||' - '||CITY_DES", inputValue);
		
		sql += " union select key,key||' - '||value  from cst_sys_param where owner = 'ALLAREA'";
		sql += provideSql(sql, "key||' - '||value", inputValue);
		
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   //所有分公司信息的下拉框
   public static DynamicSqlBean getBranchList(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select BRANCH_NO,BRANCH_NO||' - '||BRANCH_NAME from TBL_BRANCH_MANAGE where STATE in ('1','2','4','6','8')";
		
		sql += provideSql(sql, "BRANCH_NO||' - '||BRANCH_NAME", inputValue);
		
		sql += " order by BRANCH_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   //所有分公司信息的下拉框
   public static DynamicSqlBean getBranchListForSerch(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select BRANCH_NO,BRANCH_NO||' - '||BRANCH_NAME from TBL_BRANCH_MANAGER_TRUE where STATE = '1'";
		
		sql += provideSql(sql, "BRANCH_NO||' - '||BRANCH_NAME", inputValue);
		
		sql += " order by BRANCH_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
 //所属大区下拉框 （分公司）
   public static DynamicSqlBean getDistrict(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "select BRANCH_NO,BRANCH_NAME from TBL_BRANCH_MANAGER_TRUE where STATE = '1' ";
		
		sql += provideSql(sql, "BRANCH_NAME", inputValue);
		
		sql += " order by BRANCH_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   //省市信息的下拉框
   public static DynamicSqlBean getProvinces(String inputValue, Operator operator, HttpServletRequest request){
//		String sql = "select trim(KEY),trim(KEY) ||' - '||trim(VALUE) from CST_SYS_PARAM where owner='PROVINCE'";
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,3,2)='00'";
		
		sql += provideSql(sql, "trim(CITY_CODE) ||' - '||trim(CITY_DES)", inputValue);
		
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   /**
    * 外籍名
    * @return
    */
   public static DynamicSqlBean getForeignName(String inputValue, Operator operator, HttpServletRequest request){
//		String sql = "select trim(KEY),trim(KEY) ||' - '||trim(VALUE) from CST_SYS_PARAM where owner='PROVINCE'";
	   String sql = "SELECT EN_ABBR, trim(EN_ABBR) ||' - '||trim(CHINESE) FROM TBL_NATIONALITY_INF where  1 = 1 ";
	   sql += provideSql(sql, "trim(CHINESE)", inputValue);
	   
	   return new DynamicSqlBean(sql, commQueryDAO);
   }
   
   /** 根据省筛选,所属市 */
	public static DynamicSqlBean getCitys(String inputValue, Operator operator, HttpServletRequest request){//20121107
		
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  ((substr(CITY_CODE,3,1)<>'0' and substr(CITY_CODE,4,1)='0')" +
				" or (CITY_CODE IN (1000,1100,2900,6900))) " ;
		String descr = request.getParameter("parentP");
		if (descr.equals("3100")) {
			String sql1 = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,3,2)='00' and CITY_CODE='3100'";
			sql1 += " order by CITY_CODE";
			return new DynamicSqlBean(sql1, commQueryDAO);
		}
		if(!StringUtil.isEmpty(descr)){
//			sql+=provideSqlWith(sql, "descr", descr);
			String cityParentP =descr.substring(0, 2);
			String descr2 = String.valueOf(Integer.parseInt(descr)+100);
			String sqlTmp = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr2+"' ";
			List listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp);
			if(!(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0)){
				String descr3 = String.valueOf(Integer.parseInt(descr)+200);
				String sqlTmp1 = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr3+"' ";
				List listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
				if(!(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0)){
					sql+=" and substr(CITY_CODE,1,2) in ('"+descr.substring(0, 2)+"','"+descr2.substring(0, 2)+"','"+descr3.substring(0, 2)+"') ";
				}else{
					sql+=" and substr(CITY_CODE,1,2) in ('"+descr.substring(0, 2)+"','"+descr2.substring(0, 2)+"') ";
				}
				
			}else{
				if (descr.equals("3000")) {
					sql +=" AND SUBSTR(CITY_CODE,1,2)= '31' OR  SUBSTR(CITY_CODE,4,4)= '0'  ";
				}
			sql +=" and substr(CITY_CODE,1,2)= '"+cityParentP+"' ";
			}
		}else{
			sql +=" and CITY_CODE='' ";
		}
		
		sql += provideSql(sql, "trim(CITY_CODE) ||' - '||trim(CITY_DES)", inputValue);
		
		
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	 /** 根据市筛选,所属县 */
	public static DynamicSqlBean getAreas(String inputValue, Operator operator, HttpServletRequest request){//20121107
		
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,4,1)<>'0' " ;
		String descr = request.getParameter("parentP");
		if(!StringUtil.isEmpty(descr)){
			if("1000".equals(descr)||"1100".equals(descr)||"2900".equals(descr)||"6900".equals(descr)){
				sql+=" and substr(CITY_CODE,1,2)='"+descr.substring(0, 2)+"' ";
			}else{
			String descr2 = String.valueOf(Integer.parseInt(descr)+10);
			String sqlTmp = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr2+"' ";
			List listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp);
			if(!(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0)){
				String descr3 = String.valueOf(Integer.parseInt(descr)+20);
				String sqlTmp1 = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr3+"' ";
				List listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
				if(!(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0)){
					sql+=" and substr(CITY_CODE,1,3) in ('"+descr.substring(0, 3)+"','"+descr2.substring(0, 3)+"','"+descr3.substring(0, 3)+"') ";
				}else{
					sql+=" and substr(CITY_CODE,1,3) in ('"+descr.substring(0, 3)+"','"+descr2.substring(0, 3)+"') ";
				}
			}else{
				sql+=" and substr(CITY_CODE,1,3)= '"+descr.substring(0, 3)+"' ";
			}
//			sql+=provideSqlWith(sql, "descr", descr);
		}
		}else{
			sql +=" and CITY_CODE='' ";
		}
		
		sql += provideSql(sql, "trim(CITY_CODE) ||' - '||trim(CITY_DES)", inputValue);
		
		
		sql += " order by CITY_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	  //省市信息的下拉框
	   public static DynamicSqlBean getXingZheng(String inputValue, Operator operator, HttpServletRequest request){
			String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  (substr(CITY_CODE,3,2)<>'00' " +
					" or (CITY_CODE IN (1000,1100,2900,6900)))";
			
			sql += provideSql(sql, "trim(CITY_CODE) ||'-'||trim(CITY_DES)", inputValue);
			
			sql += " order by CITY_CODE";
			return new DynamicSqlBean(sql, commQueryDAO);
		}
	
	 //商户费率信息的下拉框
	   public static DynamicSqlBean getDiscId(String inputValue, Operator operator, HttpServletRequest request){
			String sql = "SELECT DISC_CD,DISC_CD||'-'||DISC_NM as AGE_NM FROM TBL_INF_DISC_CD where sa_state ='2' ";
			
			sql += provideSql(sql, "DISC_CD||'-'||DISC_NM", inputValue);
			
			sql += " order by DISC_CD";
			return new DynamicSqlBean(sql, commQueryDAO);
		}
	   //ADD
	   //定向委托银行名称的下拉框
	   public static DynamicSqlBean getdirOpenBank(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(dir_open_bank),trim(dir_open_bank) as dir_open_bank from TBL_MCHT_SETTLE_INF_TMP where dir_open_bank is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(dir_open_bank) like '%"+inputValue+"%'";
		   }
//		   sql += provideSql(sql, "dir_open_bank", inputValue);
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //定向委托开户行所在省的下拉框
	   public static DynamicSqlBean getdirBankProvince(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(dir_bank_province),trim(dir_bank_province) as dir_bank_province from TBL_MCHT_SETTLE_INF_TMP  where dir_bank_province is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(dir_bank_province) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //定向委托开户行所在市的下拉框
	   public static DynamicSqlBean getdirBankCity(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(dir_bank_city),trim(dir_bank_city) as dir_bank_city from TBL_MCHT_SETTLE_INF_TMP  where dir_bank_city is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(dir_bank_city) like '%"+ inputValue +"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //对公账号开户总行名称的下拉框
	   public static DynamicSqlBean getOpenBank(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(comp_open_bank),trim(comp_open_bank) as comp_open_bank from TBL_MCHT_SETTLE_INF_TMP where comp_open_bank is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(comp_open_bank) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //对公账号开户行所在省的下拉框
	   public static DynamicSqlBean getBankProvince(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(comp_bank_province),trim(comp_bank_province) as comp_bank_province from TBL_MCHT_SETTLE_INF_TMP where comp_bank_province is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(comp_bank_province) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //对公账号开户行所在省的下拉框
	   public static DynamicSqlBean getBankCity(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(comp_bank_city),trim(comp_bank_city) as comp_bank_city from TBL_MCHT_SETTLE_INF_TMP  where comp_bank_city is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(comp_bank_city) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //对私账号开户总行名称的下拉框
	   public static DynamicSqlBean getCorpOpenBank(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(corp_open_bank),trim(corp_open_bank) as corp_open_bank from TBL_MCHT_SETTLE_INF_TMP  where corp_open_bank is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(corp_open_bank) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //对私账号开户行所在省的下拉框
	   public static DynamicSqlBean getCorpBankProvince(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(corp_bank_province),trim(corp_bank_province) as corp_bank_province from TBL_MCHT_SETTLE_INF_TMP  where corp_bank_province is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(corp_bank_province) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   //对私账号开户行所在市的下拉框
	   public static DynamicSqlBean getCorpBankCity(String inputValue, Operator operator, HttpServletRequest request){
		   String sql = "select trim(corp_bank_city),trim(corp_bank_city) as corp_bank_city from TBL_MCHT_SETTLE_INF_TMP where corp_bank_city is not null " ;
		   if (inputValue !=null && !inputValue.isEmpty()) {
			   sql += " and trim(corp_bank_city) like '%"+inputValue+"%'";
		   }
		   return new DynamicSqlBean(sql, commQueryDAO);
	   }
	   
	   //END
   
   //参数属性
   public static DynamicSqlBean getOwner(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select Distinct OWNER,OWNER as title from cst_sys_param where type = '00'";	
		sql += provideSql(sql, "OWNER", inputValue);
		sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
 //参数键值
   public static DynamicSqlBean getIDkey(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select Distinct key as id,key from cst_sys_param where type = '00'";	
		sql += provideSql(sql, "key", inputValue);
		sql += " order by key";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
 //显示名称
   public static DynamicSqlBean getIdValue(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select Distinct value as id,value from cst_sys_param where type = '00'";	
		sql += provideSql(sql, "value", inputValue);
		sql += " order by value";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
   public static DynamicSqlBean getTranType_as(String inputValue, Operator operator, HttpServletRequest request){
	   return getTranType(inputValue,operator,request);
   }
   
   //机构分润交易类型
   public static DynamicSqlBean getTranType(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select TRADE_CODE,TRADE_CODE||' - '||TRADE_NAME from TBL_TRADE_CODE ";
		
		sql += provideSql(sql, "TRADE_CODE||' - '||TRADE_NAME", inputValue);
		
		sql += " order by TRADE_CODE";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   //报表下载
   public static DynamicSqlBean getReport(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select KEY,VALUE from CST_SYS_PARAM where OWNER='REPORT'";
		
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlIn(sql, "KEY", request.getParameter("parentP"));
		}
		
		sql += provideSql(sql, "VALUE", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   
 //报表下载
   public static DynamicSqlBean getReportSettle(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select KEY, VALUE from CST_SYS_PARAM where OWNER='REPORT'";
		
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlIn(sql, "KEY", request.getParameter("parentP"));
		}
		
		sql += provideSql(sql, "KEY||' - '||VALUE", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   //报表下载
   public static DynamicSqlBean getChannelReportSettle(String inputValue, Operator operator, HttpServletRequest request){
		
//		String sql = "select KEY,KEY||' - '||VALUE from CST_SYS_PARAM where OWNER='CHANNELREPORT'";
		String sql = "select KEY, VALUE from CST_SYS_PARAM where OWNER='CHANNELREPORT'";
		
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlIn(sql, "KEY", request.getParameter("parentP"));
		}
		
		sql += provideSql(sql, "KEY||' - '||VALUE", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
   //经营场地
   public static DynamicSqlBean getPremises(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select KEY,trim(KEY)||' - '||trim(VALUE) from cst_sys_param Where owner='PREMISES'";

		sql += provideSql(sql, "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
  
	public static DynamicSqlBean getAreaCode_as(String inputValue, Operator operator, HttpServletRequest request){
		
		return getAreaCodecode(inputValue,operator,request);
		/*String sql = "select CITY_CODE_NEW,CITY_CODE_NEW||' - '||CITY_NAME from CST_CITY_CODE ";
		
		sql += provideSql(sql, "CITY_CODE_NEW", inputValue);
		
		sql += " order by CITY_CODE_NEW";
		return new DynamicSqlBean(sql, commQueryDAO);*/
	}
	
	public static DynamicSqlBean getTermId(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID),trim(TERM_ID) as name from TBL_TERM_INF where 1=1 ";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlWith(sql, "MCHT_CD", request.getParameter("parentP"));
		}
		sql+=provideSql(sql,"TERM_ID",inputValue);
		sql+=" order by TERM_ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	public static DynamicSqlBean getTermIdNor(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID),trim(TERM_ID) as name from TBL_TERM_INF where TERM_STA not in ('4','7') ";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlWith(sql, "MCHT_CD", request.getParameter("parentP"));
		}
		sql+=provideSql(sql,"TERM_ID",inputValue);
		sql+=" order by TERM_ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	//签到表商户号
    public static DynamicSqlBean getSignMchtcd(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT distinct(trim(a.MCHT_ID)) as mcht_cd," +
				"trim(a.MCHT_ID)||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = A.MCHT_ID) as mcht_name FROM TBL_SIGN_INF a where TERMID_VALID ='1' ";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlWith(sql, "trim(INST_ID)", request.getParameter("parentP"));
		}
		sql += provideSql(sql, "trim(a.MCHT_ID)||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = A.MCHT_ID)", inputValue);
		
		sql += " order by mcht_cd";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    //签到表终端号
	public static DynamicSqlBean getSignTermId(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID),trim(TERM_ID) as name from TBL_SIGN_INF where TERMID_VALID ='1' ";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			if(request.getParameter("parentP").substring(0, 3).equals("BRH")){//机构号
				sql+=provideSqlWith(sql, "trim(INST_ID)", request.getParameter("parentP").substring(3));
			}else{//商户号
				sql+=provideSqlWith(sql, "trim(MCHT_ID)", request.getParameter("parentP"));
			}
		}
		sql+=provideSql(sql,"TERM_ID",inputValue);
		//mzhu
		sql = sql + " union  select distinct(trim(mcht_term_id)),trim(mcht_term_id) as name from tbl_term_channel_inf  where  mcht_term_id !='*' and stat in('0','2','3','4')";
		
		sql = sql + " and instr(mcht_term_id,'" + inputValue.trim() + "') > 0 ";
//		sql+=" order by TERM_ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	//获取主密钥表商户号
    public static DynamicSqlBean getTmkMchtcd(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT distinct(trim(a.MCHT_ID)) as mcht_cd," +
				"trim(a.MCHT_ID)||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = A.MCHT_ID) as mcht_name FROM TBL_GET_TMK_INF a where TERMID_VALID ='1' ";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlWith(sql, "trim(INST_ID)", request.getParameter("parentP"));
		}
		sql += provideSql(sql, "trim(a.MCHT_ID)||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = A.MCHT_ID)", inputValue);
		
		sql += " order by mcht_cd";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
    //获取主密钥表终端号
	public static DynamicSqlBean getTmkTermId(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID),trim(TERM_ID) as name from TBL_GET_TMK_INF where TERMID_VALID ='1' ";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			if(request.getParameter("parentP").substring(0, 3).equals("BRH")){//机构号
				sql+=provideSqlWith(sql, "trim(INST_ID)", request.getParameter("parentP").substring(3));
			}else{//商户号
				sql+=provideSqlWith(sql, "trim(MCHT_ID)", request.getParameter("parentP"));
			}
		}
		sql+=provideSql(sql,"TERM_ID",inputValue);
		//mzhu
		sql = sql + " union  select distinct(trim(mcht_term_id)),trim(mcht_term_id) as name from tbl_term_channel_inf  where  mcht_term_id !='*' and stat in('0','2','3','4')";
		
		sql = sql + " and instr(mcht_term_id,'" + inputValue.trim() + "') > 0 ";
//		sql+=" order by TERM_ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	//商户手续费规则业务类型
	public static DynamicSqlBean getBusinType_as(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='SERVICECODE'";
		
		sql += provideSql(sql,  "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//商户性质
	public static DynamicSqlBean getEnt(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='MCHNT_ATTR'";
		
		sql += provideSql(sql,  "trim(KEY)||' - '||trim(VALUE)", inputValue);
		
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	/**
	 * @param inputValue
	 * @param operator
	 * @param request
	 * @return
	 */
	public static DynamicSqlBean getBranchId(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "SELECT BRH_ID,BRH_ID||'-'||BRH_NAME as BRH_NAME FROM TBL_BRH_INFO where BRH_LEVEL in ('0','1') ";
		
		sql += provideSql(sql, "BRH_ID||' - '||BRH_NAME", inputValue);
		sql += provideSqlIn(sql, "BRH_ID", operator.getBrhBelowId());
		
		sql += " order by BRH_ID";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	public static DynamicSqlBean getMcc(String inputValue,Operator operator,HttpServletRequest request){
		String sql=" select mchnt_tp,mchnt_tp||'-'||descr from tbl_inf_mchnt_tp where mchnt_tp_grp='"+request.getParameter("parentP")+"' ";
		sql+=provideSqlDouLike(sql, "mchnt_tp||'-'||descr", inputValue);
		sql+=" order by mchnt_tp";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	public static DynamicSqlBean getMccs(String inputValue,Operator operator,HttpServletRequest request){
		String sql=" select mchnt_tp,mchnt_tp||'-'||descr from tbl_inf_mchnt_tp where mchnt_tp!='*' ";
		sql+=provideSqlDouLike(sql, "mchnt_tp||'-'||descr", inputValue);
		sql+=" order by mchnt_tp";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	
	//查询终端
	public static DynamicSqlBean getTermIdAll(String inputValue,Operator operator, HttpServletRequest request){
		String sql = " select term_id,term_id as id_name from tbl_term_inf_tmp ";
		String mcht_cd = request.getParameter("parentP");
		if(!StringUtil.isEmpty(mcht_cd)){
			sql +=provideSqlWith(sql, "mcht_cd", mcht_cd);
		}
		sql+=provideSqlDouLike(sql, "term_id", inputValue);
		sql+=" order by id_name";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	//商户组别
	public static DynamicSqlBean getMchtGrp(String inputValue,Operator operator, HttpServletRequest request){
		String sql = " SELECT MCHNT_TP_GRP,MCHNT_TP_GRP ||' - '||DESCR as DESCR FROM TBL_INF_MCHNT_TP_GRP ";
		sql+=provideSqlDouLike(sql, "MCHNT_TP_GRP ||' - '||DESCR", inputValue);
		sql+=" order by MCHNT_TP_GRP";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
/*	public static DynamicSqlBean getTermIdAll(String inputValue,Operator operator, HttpServletRequest request){
		String sql = " select term_id,term_id as id_name from tbl_term_inf_tmp where 1=1" ;
		String termid = request.getParameter("parentP");
		if(!StringUtil.isEmpty(termid) && !termid.equals("*")){
			sql+=provideSqlWith(sql, "term_id", termid);
		}
//		sql+=provideSql(sql,"trim(TERM_ID)",inputValue);
//		sql+=provideSqlDouLike(sql, "term_id", inputValue);
		sql+=" order by id_name";
		return new DynamicSqlBean(sql, commQueryDAO);
	}*/
	
	
	//查询所有MCC
	public static DynamicSqlBean getAllMcc(String inputValue,Operator operator,HttpServletRequest request){
		String sql=" select mchnt_tp,mchnt_tp||'-'||descr from tbl_inf_mchnt_tp ";
		sql+=provideSqlDouLike(sql, "mchnt_tp||'-'||descr", inputValue);
		sql+=" order by mchnt_tp";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getMccAll(String inputValue,Operator operator,HttpServletRequest request){//20120905,新增商户的“MCC2”
		String sql=" select mchnt_tp,mchnt_tp||'-'||descr from tbl_inf_mchnt_tp ";
		sql+=provideSqlDouLike(sql, "mchnt_tp||'-'||descr", inputValue);
		sql+=" order by mchnt_tp";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//计费代码
	public static DynamicSqlBean getdiscCdNor(String inputValue,Operator operator,HttpServletRequest request){
		String sql = "SELECT DISC_CD as id,DISC_CD FROM TBL_INF_DISC_CD_temp where 1=1  ";
		sql += provideSql(sql, "DISC_CD", inputValue);
		sql += " order by DISC_CD";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//计费代码
	public static DynamicSqlBean getdiscCdNorShen(String inputValue,Operator operator,HttpServletRequest request){
		String sql = "SELECT DISC_CD as id,DISC_CD FROM TBL_INF_DISC_CD_temp where sa_state <>'2' and sa_state <> '4' ";
		sql += provideSql(sql, "DISC_CD", inputValue);
		sql += " order by DISC_CD";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//计费名称
	public static DynamicSqlBean getdiscNm(String inputValue,Operator operator,HttpServletRequest request){
		String sql = "SELECT DISC_NM as id,DISC_NM FROM TBL_INF_DISC_CD_TEMP   ";
		sql += provideSql(sql, "DISC_NM", inputValue);
		sql += " order by DISC_CD";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	public static DynamicSqlBean getdiscNmShen(String inputValue,Operator operator,HttpServletRequest request){
		String sql = "SELECT DISC_NM as id,DISC_NM FROM TBL_INF_DISC_CD_TEMP where sa_state <>'2' and sa_state <> '4' ";
		sql += provideSql(sql, "DISC_NM", inputValue);
		sql += " order by DISC_CD";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//入网机构代码
	public static DynamicSqlBean getOrgcode(String inputValue,Operator operator,HttpServletRequest request){
		String sql = "select KEY,KEY||' - '||VALUE from CST_SYS_PARAM where OWNER='ORGCODE' ";
		sql += provideSql(sql, "KEY||' - '||VALUE", inputValue);
		sql += " order by KEY";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//发卡机构查询，包含*
	public static DynamicSqlBean getBankCode(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct(trim(INS_ID_CD)), trim(INS_ID_CD) ||' - '|| trim(CARD_DIS) as bank_name from TBL_BANK_BIN_INF ";
		
		sql += provideSql(sql, "INS_ID_CD ||' - '|| CARD_DIS", inputValue);

	//	sql += " union select trim(key),trim(key) ||' - '||trim(value) from cst_sys_param where owner='ALLBANK' and type='00' ";
	//	sql += provideSql(sql, "trim(key) ||' - '||trim(value)", inputValue);
		
		//sql += " order by trim(INS_ID_CD)";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//发卡行 编号
		public static DynamicSqlBean getSendCardNo(String inputValue, Operator operator, HttpServletRequest request){
			
			String sql = "select distinct(trim(INS_ID_CD)), trim(INS_ID_CD) ||' - '|| trim(CARD_DIS) as bank_name from TBL_BANK_BIN_INF ";
			
			sql += provideSql(sql, "INS_ID_CD ||' - '|| CARD_DIS", inputValue);

			sql += " union select trim(key),trim(key) ||' - '||trim(value) from cst_sys_param where owner='ALLBANK' and type='00' ";
			sql += provideSql(sql, "trim(key) ||' - '||trim(value)", inputValue);
			
			//sql += " order by trim(INS_ID_CD)";
			return new DynamicSqlBean(sql, commQueryDAO);
		}
	
	//商户MCC查询，包含*
	public static DynamicSqlBean getMchntMcc(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql= "select a.mchnt_tp as tp ,'('||b.descr||')'||a.mchnt_tp ||' - '||a.descr as tpdesc from tbl_inf_mchnt_tp a left join tbl_inf_mchnt_tp_grp b on a.mchnt_tp_grp = b.mchnt_tp_grp";
		
		sql += provideSql(sql, "'('||b.descr||')'||a.mchnt_tp ||' - '||a.descr", inputValue);

		sql += " union select trim(key) as tp,trim(key) ||' - ' ||trim(value) as tpdesc from cst_sys_param where owner='ALLMCC' and type='00' ";
		sql += provideSql(sql, "trim(key) ||' - ' ||trim(value)", inputValue);
		
		String newSql = "select * from  ("+sql+") order by tpdesc desc";
		
		return new DynamicSqlBean(newSql, commQueryDAO);
	}
	

	//查询发卡行
	public static DynamicSqlBean getinsIdCd(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select distinct trim(INS_ID_CD) as id,trim(INS_ID_CD)||' - '||trim(CARD_DIS) from TBL_BANK_BIN_INF ";
		
		sql += provideSql(sql, "trim(INS_ID_CD)||' - '||trim(CARD_DIS)", inputValue);

		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//查询卡bin
	public static DynamicSqlBean getbinStaNo(String inputValue, Operator operator, HttpServletRequest request){
		
		String sql = "select BIN_STA_NO as id,trim(BIN_STA_NO) from TBL_BANK_BIN_INF ";
		
		sql += provideSql(sql, "trim(BIN_STA_NO)", inputValue);

		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//根据商户组别获得商户编号
		public static DynamicSqlBean getMchntNoMccWub(String inputValue, Operator operator, HttpServletRequest request){
			
			String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp ";
			String mchnt_tp = request.getParameter("parentP");
			if(!StringUtil.isEmpty(mchnt_tp)){
				sql += provideSqlWith(sql, "mchnt_tp", mchnt_tp);
			}
			sql += provideSql(sql, "descr", inputValue);

			return new DynamicSqlBean(sql, commQueryDAO);
		}
	
	/** 根据商户MCC筛选,包含*－所有商户 */
	public static DynamicSqlBean getMchntNoMcc(String inputValue, Operator operator, HttpServletRequest request){//20121107
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' " ;
		String mcc = request.getParameter("parentP");
		if(!StringUtil.isEmpty(mcc) && !mcc.equals("*")){
			sql+=provideSqlWith(sql, "MCC", mcc);
		}
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		sql += " and MCHT_STATUS = '0' ";
		
		sql += " union select key,key || '-' ||value from cst_sys_param where owner='ALLMCHNT' and type='00' ";
		sql += provideSql(sql, "value", inputValue);
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/** 根据商户MCC筛选,包含*－所有商户 */
	public static DynamicSqlBean getMchntNoMcc2(String inputValue, Operator operator, HttpServletRequest request){//20121107
		
		String sql = "select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' " ;
		String mcc = request.getParameter("parentP");
		if(!StringUtil.isEmpty(mcc) && !mcc.equals("*")){
//			sql = sql + " and MCC = '" + mcc + "'";
			sql+=provideSqlWith(sql, "MCC", mcc);
		}
		
		sql += provideSql(sql, "trim(MCHT_NO) ||' - '|| trim(MCHT_NM)", inputValue);
		sql += " and MCHT_STATUS = '0' ";
		
		sql += " union select key,key || '-' ||value from cst_sys_param where owner='ALLMCHNT' and type='00' ";
		sql += provideSql(sql, "value", inputValue);
		
		sql += " order by MCHT_NO";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	/**根据商户号筛选终端号（包含*）(若商户号为*，再根据商户MCC筛选终端)*/
	public static DynamicSqlBean getTermIdMchnt(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID) as ID,trim(TERM_ID) as name from TBL_TERM_INF where 1=1";
		String mchntNo = request.getParameter("parentP");
		if(!StringUtil.isEmpty(mchntNo) && !("*").equals(mchntNo)){
			String mcc = mchntNo.split("-")[0].trim();
			String mchtNo = mchntNo.split("-")[1].trim();
			if(!mchtNo.equals("*")){
				sql = sql + " and MCHT_CD = '" + mchtNo + "'";
			}else if(!mcc.equals("*")){
				sql = sql + " and MCHT_CD in (select MCHT_NO from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' and MCC ='"+mcc+"')";
			}
		}
		sql+=provideSql(sql,"trim(TERM_ID)",inputValue);
		
		sql += " union select key,value from cst_sys_param where owner='ALLTERM' and type='00' ";
		sql += provideSql(sql, "value", inputValue);
		
		sql+=" order by ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	
	/**根据商户号筛选终端号（包含*）(若商户号为*，再根据商户MCC筛选终端)*/
	public static DynamicSqlBean getTermIdMchntOnly(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID) as ID,trim(TERM_ID) as name from TBL_TERM_INF where 1=1";
		String mchntNo = request.getParameter("parentP");
		if(!StringUtil.isEmpty(mchntNo) && !("*").equals(mchntNo)){
			String mcc = mchntNo.split("-")[0].trim();
			String mchtNo = mchntNo.split("-")[1].trim();
			if(!mchtNo.equals("*")){
				sql = sql + " and MCHT_CD = '" + mchtNo + "'";
			}else if(!mcc.equals("*")){
				sql = sql + " and MCHT_CD in (select MCHT_NO from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' and MCC ='"+mcc+"')";
			}
		}
		sql+=provideSql(sql,"trim(TERM_ID)",inputValue);
		
		sql += " union select key,key from cst_sys_param where owner='ALLTERM' and type='00' ";
		sql += provideSql(sql, "key", inputValue);
		
		sql+=" order by ID desc";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	/**根据商户号筛选终端号（包含*）(若商户号为*，再根据商户MCC筛选终端)*/
	public static DynamicSqlBean getTermIdMchnt2(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID) as ID,trim(TERM_ID) as name from TBL_TERM_INF where 1=1";
		String mchntNo = request.getParameter("parentP");
		String mchtNo = mchntNo.trim();
		if(!StringUtil.isEmpty(mchtNo) && !mchtNo.equals("*")){
			sql+=provideSqlWith(sql, "MCHT_CD", mchtNo);
		}
		sql+=provideSql(sql,"trim(TERM_ID)",inputValue);
		
		sql += " union select key,key|| '-' ||value from cst_sys_param where owner='ALLTERM' and type='00' ";
		sql += provideSql(sql, "key|| '-' ||value", inputValue);
		
		sql+=" order by ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	/**根据商户号选择终端号，并带*的  */
	public static DynamicSqlBean getTermIdMchnt3(String inputValue, Operator operator, HttpServletRequest request){
		String sql=" select trim(TERM_ID) as ID,trim(TERM_ID) as name from TBL_TERM_INF where 1=1";
		if(!StringUtil.isEmpty(request.getParameter("parentP"))){
			sql+=provideSqlWith(sql, "MCHT_CD", request.getParameter("parentP"));
		}
		sql+=provideSql(sql,"trim(TERM_ID)",inputValue);
		
		sql += " union select key,key|| '-' ||value from cst_sys_param where owner='ALLTERM' and type='00' ";
		sql += provideSql(sql, "key|| '-' ||value", inputValue);
		
		sql+=" order by ID";
		return new DynamicSqlBean(sql,commQueryDAO);
	}
	
	 /** 划款汇总表根据日期,查询批次号 */
	public static DynamicSqlBean getBatchNum(String inputValue, Operator operator, HttpServletRequest request){//20121107
		
		String sql = "select distinct(batch_num),trim(batch_num) from TBL_MCHT_SUMRZ_INF where 1=1 " ;
		String descr = request.getParameter("parentP");
		if(!StringUtil.isEmpty(descr)){
			sql+=" and INST_DATE = '"+descr+"' ";
		}
		
		sql += provideSql(sql, "trim(batch_num)", inputValue);
		
		
		sql += " order by batch_num";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//终端管理-终端类型
	public static DynamicSqlBean getTermType(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'TERM_TYPE'";	
		sql += provideSql(sql, "VALUE", inputValue);
	//	sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//终端管理-终端厂商
	public static DynamicSqlBean getFACTORY(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'FACTORY'";	
		sql += provideSql(sql, "VALUE", inputValue);
	//	sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//终端管理-连接类型
	public static DynamicSqlBean getCONNTYPE(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'CONN_TYPE'";	
		sql += provideSql(sql, "VALUE", inputValue);
	//	sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//终端管理-产权属性
	public static DynamicSqlBean getPROPTP(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'PROP_TP'";	
		sql += provideSql(sql, "VALUE", inputValue);
	//	sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//终端管理-支持交易
	public static DynamicSqlBean getTRADETP(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'TRADE_TP'";	
		sql += provideSql(sql, "VALUE", inputValue);
	//	sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}
	
	//联机交易-卡类型
	public static DynamicSqlBean getCARDSTYLE(String inputValue, Operator operator, HttpServletRequest request){
		String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'CARD_STYLE'";	
		sql += provideSql(sql, "VALUE", inputValue);
	//	sql += " order by OWNER";
		return new DynamicSqlBean(sql, commQueryDAO);
	}

	//联机交易-交易方式
		public static DynamicSqlBean getTRANSWAY(String inputValue, Operator operator, HttpServletRequest request){
			String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'TRANSWAY'";	
			sql += provideSql(sql, "VALUE", inputValue);
		//	sql += " order by OWNER";
			return new DynamicSqlBean(sql, commQueryDAO);
		}
		
	//终端型号，和终端厂商匹配
	  public static DynamicSqlBean getTermMachTp(String inputValue, Operator operator, HttpServletRequest request){
			
			String sql = "select key,value from CST_SYS_PARAM where OWNER = 'TERMMACHTP'";
			String reserve = request.getParameter("parentP");
			if(!StringUtil.isEmpty(reserve)){
				sql+="and RESERVE = '"+reserve+"'";
			}
			
			sql += provideSql(sql, "value", inputValue);
			
	//		sql += " order by AGEN_ID";
			return new DynamicSqlBean(sql, commQueryDAO);
		}
		
		//经营场所权属
				public static DynamicSqlBean getOwnerBusi(String inputValue, Operator operator, HttpServletRequest request){
					String sql = "Select  trim(KEY),trim(VALUE)  from cst_sys_param where OWNER = 'MANAGEOWNER'";	
					sql += provideSql(sql, "trim(VALUE)", inputValue);
				//	sql += " order by OWNER";
					return new DynamicSqlBean(sql, commQueryDAO);
				}
				//结算账户类型
				public static DynamicSqlBean getSettleRpt(String inputValue, Operator operator, HttpServletRequest request){
					String sql = "Select  KEY,VALUE  from cst_sys_param where OWNER = 'SETTLERPT'";	
					sql += provideSql(sql, "VALUE", inputValue);
				//	sql += " order by OWNER";
					return new DynamicSqlBean(sql, commQueryDAO);
				}		
				
				
		 //POS终端恢复里面的查询条件，只能查到停用的终端
		  public static DynamicSqlBean getStopTerm(String inputValue, Operator operator, HttpServletRequest request){
				
				String sql = "  SELECT t1.MCHT_CD,t1.MCHT_CD||'-'||t2.MCHT_NM FROM TBL_TERM_INF_TMP t1 left join TBL_MCHT_BASE_INF t2 on t1.MCHT_CD = t2.MCHT_NO   where  t1.TERM_STA='4'  ";
				
				sql += provideSql(sql, "t1.MCHT_CD||'-'||t2.MCHT_NM", inputValue);
//						sql += provideSqlIn(sql, "ACQ_INST_ID", operator.getBrhBelowId());
		//		sql += " and MCHT_STATUS = '0' ";
		//		sql += " order by MCHT_NO";
				return new DynamicSqlBean(sql, commQueryDAO);
			}		
		  
		  //向机构签到商户号
		    public static DynamicSqlBean getMchtcdSign(String inputValue, Operator operator, HttpServletRequest request){
		    	String wheresql = "SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"+request.getParameter("parentP")+"' ";
		    	System.out.println("parentP"+request.getParameter("parentP"));
		    	List list =   CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);
				String sql = "SELECT DISTINCT A.MCHT_ID,trim(A.MCHT_ID) FROM tbl_sign_inf a where a.TERMID_VALID='1' and trim(INST_ID) ='"+list.get(0)+"'";
				
				sql += provideSql(sql, "trim(A.MCHT_ID)", inputValue);
				
				sql += " order by a.MCHT_ID";
				return new DynamicSqlBean(sql, commQueryDAO);
			}
		    
			//向机构签到终端
			public static DynamicSqlBean getTermIdSign(String inputValue, Operator operator, HttpServletRequest request){
				String sql=" select trim(TERM_ID),trim(TERM_ID) as name from tbl_sign_inf ";
				if(!StringUtil.isEmpty(request.getParameter("parentP"))){
					sql+=provideSqlWith(sql, "MCHT_ID", request.getParameter("parentP"));
				}
				sql+=provideSql(sql,"TERM_ID",inputValue);
				sql+=" order by TERM_ID";
				return new DynamicSqlBean(sql,commQueryDAO);
			}

}
