/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-18       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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
package com.huateng.system.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.huateng.commquery.dao.ICommQueryDAO;

/**
 * Title:生成系统编号
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class GenerateNextId {
	
	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	private static Logger log = Logger.getLogger(GenerateNextId.class);
	//交易流水表中 交易金额（amt_trans） 不位空的 交易代码 
	//private static String TXN_NUM="('1011','3011','2011','4011','1091','2091','3091','4091','1101','2101','3101','4101','5151')";
	/**
	 * 获得idcd
	 * @return
	 */
	
	public static synchronized String getDiscId() {
		String idcd = "00000";
		String sql = "select max(DISC_CD) from TBL_INF_DISC_CD";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			idcd = CommonFunction.fillString(idList.get(0).toString(),'0', 5, false);
		}			
		idcd = CommonFunction.fillString(String.valueOf(Integer.parseInt(idcd) + 1), '0', 5, false);
		return idcd;
		
	}
	
	/**
	 * 获取机构分润Id
	 */
	
	public static synchronized String getFeeId(){
		String id="00000000";
		String sql="select max(fee_id) from tbl_agency_fee_lub_tmp ";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			id= CommonFunction.fillString(idList.get(0).toString(),'0', 8, false);
		}	
		id=CommonFunction.fillString(String.valueOf(Integer.parseInt(id) + 1), '0', 8, false);
		return id;
	}
	
	public static synchronized String getMchtFeeId(){
		String id="00000000";
		String sql="select max(DISC_ID) from TBL_HIS_DISC_ALGO1_tmp";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			id= CommonFunction.fillString(idList.get(0).toString(),'0', 8, false);
		}	
		id=CommonFunction.fillString(String.valueOf(Integer.parseInt(id) + 1), '0', 8, false);
		return id;
	}
	
	public static synchronized String getALGO1Id(){
		String id="00000000";
		String sql="select max(DISC_ID) from TBL_HIS_DISC_ALGO1";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			id= CommonFunction.fillString(idList.get(0).toString(),'0', 8, false);
		}	
		id=CommonFunction.fillString(String.valueOf(Integer.parseInt(id) + 1), '0', 8, false);
		return id;
	}
	//获取差错流水号
	public static synchronized String getSSn(){
		int s=1000001;
		String sql="select max(txn_ssn) from BTH_CUP_ERR_TXN";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			s+=Integer.parseInt(idList.get(0).toString());
		}	
		return String.valueOf(s).substring(1);
	}
	
	public static synchronized String getAlgo2Id(){
		int s=1000001;
		String sql="select max(DISC_ID) from TBL_HIS_DISC_ALGO2_TMP";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			s+=Integer.parseInt(idList.get(0).toString());
		}	
		return String.valueOf(s).substring(1);
	}
	public static synchronized String getBankCodeId(){
		int s=1000001;
		String sql="select max(ID) from TBL_INST_BDB_BANK_CODE_TMP";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			s+=Integer.parseInt(idList.get(0).toString());
		}	
		return String.valueOf(s).substring(1);
	}
	/**
	 * 根据规则编号生成caseId
	 * @param ruleId
	 * @return
	 */
	public static synchronized String getCaseId(String ruleId){
		int i=10001;
		String sql="select max(substr(case_id,3)) from CST_AFTER_RULE_INF where substr(case_id,0,2)='"+ruleId+"'";
		List list=commQueryDAO.findBySQLQuery(sql);
		if(list!=null&&list.size()!=0&&list.get(0)!=null&&list.get(0).toString().trim().length() != 0){
			i+=Integer.parseInt(list.get(0).toString());
		}
		return ruleId+String.valueOf(i).substring(1);
	}
	/**
	 * 获得银联卡编号
	 * @returns
	 */
	public static synchronized String getBankBinId() {
		String sql = "select min(IND + 1) from TBL_BANK_BIN_INF where " +
				"(IND + 1) not in (select IND from TBL_BANK_BIN_INF)";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
		
	}
	
	/**
	 * 获得角色信息的编号
	 * @return
	 */
	public static synchronized String getNextRoleId() {
		String sql = "select min(role_id + 1) from tbl_role_inf where " +
				"(role_id + 1) not in (select role_id from tbl_role_inf)";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		return resultSet.get(0).toString();
	}
	
	/**
	 * 获得商户编号
	 * @param str
	 * @return 如果返回""，则表示该类商户已经达到数量上线
	 */
	public static synchronized String getMchntId(String str) {
		//判断是否存在序号为0001的ID
		String sql = "select count(1) from TBL_MCHT_BASE_INF_TMP where trim(mcht_no) = '" + str + "0001" + "'" ;
		BigDecimal c = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (c.intValue() == 0) {
			return str + "0001";
		}
		
		sql = "select min(substr(mcht_no,12,4) + 1) from TBL_MCHT_BASE_INF_TMP where (substr(mcht_no,12,4) + 1) not in " +
				"(select substr(mcht_no,12,4) + 0 from TBL_MCHT_BASE_INF_TMP where substr(mcht_no,1,11) = '" + str + "') " +
				"and substr(mcht_no,1,11) = '" + str + "'";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.get(0) == null) {
			return str + "0001";
		}
		int id = resultSet.get(0).intValue();
		if(id == 10000) {
			return "";
		}
		return str + CommonFunction.fillString(String.valueOf(id), '0', 4, false);
	}
	
	/**
	 * 获得终端类型编号
	 * @return
	 */
	public static synchronized String getTermTpId() {
		String sql = "select min(term_tp + 1) from tbl_term_tp where " +
				"(term_tp + 1) not in (select term_tp from tbl_term_tp)";
		List<Double> resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.get(0) == null) {
			return "01";
		}
		int id = resultSet.get(0).intValue();
		if(id == 100) {
			return "";
		}
		return CommonFunction.fillString(String.valueOf(id), '0', 2, false);
	}
	
	/**
	 * 查询操作日志流水号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getTxnSeq() {
		String sql = "SELECT SEQ_TERM_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		sql = "1" + CommonFunction.fillString(sql, '0', 14, false);
		return sql;
	}
	
	/**
	 * 查询CA银联公钥编号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getParaId() {
		String sql = "SELECT SEQ_EMV_PARA_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		sql = "1" + CommonFunction.fillString(sql, '0', 8, false);
		return sql;
	}
	
	/**
	 * 获得内部参数索引编号
	 * @return
	 */
	public static synchronized String getParaIdx(String usageKey) {
		String sql = "select min(para_idx + 1) from tbl_emv_para where " +
				"(para_idx + 1) not in (select para_idx from tbl_emv_para where trim(usage_key) = '" + usageKey + "') and trim(usage_key) = '" + usageKey + "'";
		List resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.get(0) == null) {
			return "01";
		}
		String id = resultSet.get(0).toString();

		if(id.equals("100")) {
			return "";
		}
		return CommonFunction.fillString(id, '0', 2, false);
	}
	
	
	public synchronized static List<String> getParalist(String sql) {
		
		List<String> idList = commQueryDAO.findBySQLQuery(sql);
		
		return idList;
	}
	
	/**
	 * 获得交易数据总笔数
	 * @return
	 */
	public static synchronized String getTradeCount(Map paramMap) {
		
		String Card_Accp_Id ="";/*商户号  */
		String Card_Accp_Term_Id="";/*终端号  */
		String retrivl_ref ="";/*交易参考号  */
		String Order_No =""; /*商户订单号  */
		String pan =""; /*卡号  */
		String amt_trans_max="";//最大交易金额*/
		String amt_trans_min="";//最小交易金额*/
		String Crans_Type ="";/*交易类型  */
		String Trans_State ="";/*交易状态  */
		String Date_Local_Trans ="";/*交易日期  */
		String cup_ssn="";//银联交易流水
		
		 Card_Accp_Id = paramMap.get("Ips_Mcht_Code").toString();
		 if(paramMap.get("Txn_Type")!=null && !paramMap.get("Txn_Type").equals("")){
			 Crans_Type = paramMap.get("Txn_Type").toString();
			 }
		 
		 if(paramMap.get("Txn_State")!=null && !paramMap.get("Txn_State").equals("")){
			 Trans_State = paramMap.get("Txn_State").toString();
			 }
		
		 Date_Local_Trans = CommonFunction.getCurrentDate();// 系统当前日期
		 String Date_before =CommonFunction.getBeforeDate(Date_Local_Trans, -1);
		
		if(paramMap.get("Term_Id")!=null && !paramMap.get("Term_Id").equals("")){
		   Card_Accp_Term_Id = paramMap.get("Term_Id").toString();
		}

		if(paramMap.get("ReFe_Num")!=null && !paramMap.get("ReFe_Num").equals("")){
		   retrivl_ref = paramMap.get("ReFe_Num").toString();
		}
		
		if(paramMap.get("Order_TrsNum")!=null && !paramMap.get("Order_TrsNum").equals("")){
		   Order_No = paramMap.get("Order_TrsNum").toString(); 
		}
		if(paramMap.get("Pan")!=null &&!paramMap.get("Pan").equals("")){
		   pan = paramMap.get("Pan").toString(); 
		}
		if(paramMap.get("Max_Amount")!=null && !paramMap.get("Max_Amount").equals("")){
		   amt_trans_max = paramMap.get("Max_Amount").toString(); 
		}
		if(paramMap.get("Min_Amount")!=null && !paramMap.get("Min_Amount").equals("")){
		   amt_trans_min = paramMap.get("Min_Amount").toString(); 
		}
		if(paramMap.get("Cup_Ssn")!=null&&!paramMap.get("Cup_Ssn").equals("")){
			cup_ssn = paramMap.get("Cup_Ssn").toString();
		}
		StringBuffer sql = new StringBuffer();
		  sql.append("Select count(*) From Tbl_n_Txn t,tbl_mcht_base_inf m  "+
				 " Where  t.Card_Accp_Id=m.mcht_no And (substr(t.inst_date,0,8) = '"+Date_Local_Trans+"' or substr(t.inst_date,0,8) ='"+Date_before+"') ");
          if(!Card_Accp_Term_Id.equals("")){
		  sql.append("  And t.card_accp_term_id = '"+Card_Accp_Term_Id+ "'");
          }
          
          
          
          if(!"".equals(cup_ssn)){
        	  sql.append(" and t.Sys_Seq_Num='").append(cup_ssn).append("' ");
          }
          if(!Card_Accp_Id.equals("")){
        	//  String mchtNoSql="select mcht_no from tbl_mcht_base_inf where mcht_no_hx='"+Card_Accp_Id+"'"; 
            //  List list=commQueryDAO.findBySQLQuery(mchtNoSql,0,1);
           //   if(list!=null&&list.size()>0)Card_Accp_Id=(String)list.get(0);
              
          //    log.info("商户号t.Card_Accp_Id："+Card_Accp_Id);
		//  sql.append("  And t.Card_Accp_Id = '"+Card_Accp_Id+ "'");
        	  sql.append(" and m.mcht_no_hx='"+Card_Accp_Id+"' "); 
          }
         /* if(!Trans_State.equals("")&&!Trans_State.equals("0000")){
		  sql.append("  And t.trans_state = '"+Trans_State+ "'");
          }*/
          if(!Crans_Type.equals("")&&!Crans_Type.equals("0000")){
		  sql.append("  And t.txn_num = '"+Crans_Type+ "'");
          }
          if(!Order_No.equals("")){
		  sql.append("   And t.Order_No = '"+Order_No+ "'");
          }
          if(!retrivl_ref.equals("")){
		  sql.append("   And t.retrivl_ref = '"+retrivl_ref+ "'");
          }
          if(!pan.equals("")){
		  sql.append("   And t.pan ='"+pan+ "'");
          } 
          //当查询交易金额的时候需要过滤掉交易金额为空字符的交易代码
          //if(!amt_trans_max.equals("")||!amt_trans_min.equals("")){
          sql.append(" And t.txn_num <>'1021' and t.txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE"));
        //  }
          
          if(!amt_trans_max.equals("")){
        	  //接口传过来的是以元为单位的，数据库中存的是以分为单位的
	          long amtMax=(long)(Float.parseFloat(amt_trans_max)*100);
			  sql.append("   And cast(replace(amt_trans,' ','0') As Number) <= "+amtMax);
          }
          if(!amt_trans_min.equals("")){
        	  //接口传过来的是以元为单位的，数据库中存的是以分为单位的
        	  long amtMin=(long)(Float.parseFloat(amt_trans_min)*100);
    		  sql.append("   And cast(replace(amt_trans,' ','0') As Number) >="+amtMin);
            }
          if(Trans_State.equals("2")){//失败可以查出 已被冲正的原交易
      		sql.append(" and (t.resp_code <>'00' or revsal_flag ='1' ) ");  
      	  }else if(Trans_State.equals("1")){//成功 需要过滤掉 已被冲正的交易
      		  sql.append("  And t.resp_code = '00' and revsal_flag<>'1' "); //and txn_num not in "+SysParamUtil.getParam("REVSALCODE"));
      	  }else if(Trans_State.equals("3")){//查出原交易被撤销的记录 即撤销标志位是‘1’的记录
      		  sql.append("  And t.cancel_flag='1' ");
      	  }
		
		String resultSet = commQueryDAO.findCountBySQLQuery(sql.toString());

		return resultSet;
	}
	/**
	 * 获得交易数据
	 * @return
	 */
	public static synchronized List  getTrade(Map paramMap) {
	
		int begin =0;//显示页数第几页*/
		int count=0;//每页显示条数*/
		String Card_Accp_Id ="";/*商户号  */
		String Card_Accp_Term_Id="";/*终端号  */
		String retrivl_ref ="";/*交易参考号  */
		String Order_No =""; /*商户订单号  */
		String pan =""; /*卡号  */
		String amt_trans_max="";//最大交易金额*/
		String amt_trans_min="";//最小交易金额*/
		String Crans_Type ="";/*交易类型  */
		String Trans_State ="";/*交易状态  */
		String Date_Local_Trans ="";/*交易日期  */
		String cup_Ssn="";//银联交易流水号
		 Date_Local_Trans = CommonFunction.getCurrentDate();// 系统当前日期
		
		 String Date_before =CommonFunction.getBeforeDate(Date_Local_Trans, -1);
		
		 Card_Accp_Id = paramMap.get("Ips_Mcht_Code").toString();
		 
		 if(paramMap.get("Txn_Type")!=null && !paramMap.get("Txn_Type").equals("")){
		 Crans_Type = paramMap.get("Txn_Type").toString();
		 }
		 if(paramMap.get("Txn_State")!=null && !paramMap.get("Txn_State").equals("")){
		 Trans_State = paramMap.get("Txn_State").toString();
		 }
		
		
		if(paramMap.get("Term_Id")!=null && !paramMap.get("Term_Id").equals("")){
		   Card_Accp_Term_Id = paramMap.get("Term_Id").toString();
		}

		if(paramMap.get("ReFe_Num")!=null && !paramMap.get("ReFe_Num").equals("")){
		   retrivl_ref = paramMap.get("ReFe_Num").toString();
		}
		
		if(paramMap.get("Order_TrsNum")!=null && !paramMap.get("Order_TrsNum").equals("")){
		   Order_No = paramMap.get("Order_TrsNum").toString(); 
		}
		if(paramMap.get("Pan")!=null && !paramMap.get("Pan").equals("")){
		   pan = paramMap.get("Pan").toString(); 
		}
		if(paramMap.get("Max_Amount")!=null && !paramMap.get("Max_Amount").equals("")){
		   amt_trans_max = paramMap.get("Max_Amount").toString(); 
		}
		if(paramMap.get("Min_Amount")!=null && !paramMap.get("Min_Amount").equals("")){
		   amt_trans_min = paramMap.get("Min_Amount").toString(); 
		}
		if(paramMap.get("Cup_Ssn")!=null&&!paramMap.get("Cup_Ssn").equals("")){
			cup_Ssn=paramMap.get("Cup_Ssn").toString();
		}
		
		 begin = Integer.parseInt(paramMap.get("Page_Num").toString())-1; //显示第几页*/
		 count = Integer.parseInt(paramMap.get("Count").toString()); //每页显示条数*/
		
		StringBuffer sql = new StringBuffer();
		  sql.append("Select  t.Card_Accp_Term_Id As TERM_ID, t.Inst_Date As INST_DATE," +
		  		" t.txn_num As TXN_TYPE, t.Sys_Seq_Num As CUP_SSN,"+
			" t.Retrivl_Ref As REFE_NUM, t.Order_No As ORDER_TRSNUM,"+
			" t.resp_code As TXN_STATE, t.Pan As  PAN,m.mcht_no_hx as IPS_MERCHT,"+
			" cast(replace(amt_trans,' ','0') As Number)/100 As TRS_AMOUNT,t.cancel_flag,revsal_flag From TBL_N_TXN t,tbl_mcht_base_inf m "+
			" Where  t.Card_Accp_Id=m.mcht_no and  (substr(t.inst_date,0,8) = '"+Date_Local_Trans+"' or substr(t.inst_date,0,8) ='"+Date_before+"') ");
          if(!Card_Accp_Term_Id.equals("")){
		  sql.append("  And t.card_accp_term_id = '"+Card_Accp_Term_Id+ "'");
          }
          
          if(cup_Ssn!=null&&!cup_Ssn.equals("")){
        	  sql.append(" and t.Sys_Seq_Num ='").append(cup_Ssn).append("' ");
          }
          if(!Card_Accp_Id.equals("")){
        //	  String mchtNoSql="select mcht_no from tbl_mcht_base_inf where mcht_no_hx='"+Card_Accp_Id+"'"; 
        //      List list=commQueryDAO.findBySQLQuery(mchtNoSql,0,1);
       //       if(list!=null&&list.size()>0)Card_Accp_Id=(String)list.get(0);
       //       log.info("商户号t.Card_Accp_Id："+Card_Accp_Id);
		 // sql.append("  And t.Card_Accp_Id = '"+Card_Accp_Id+ "'");
		  sql.append(" and m.mcht_no_hx='"+Card_Accp_Id+"' ");
          }
          
          if(!Crans_Type.equals("")&&!Crans_Type.equals("0000")){
		  sql.append("  And t.txn_num = '"+Crans_Type+ "'");
          }
          if(!Order_No.equals("")){
		  sql.append("   And t.Order_No = '"+Order_No+ "'");
          }
          if(!retrivl_ref.equals("")){
		  sql.append("   And t.retrivl_ref = '"+retrivl_ref+ "'");
          }
          if(!pan.equals("")){
		  sql.append("   And t.pan ='"+pan+ "'");
          }
          
          if(!Trans_State.equals("")&&!Trans_State.equals("0000")){
        	  if(Trans_State.equals("2")){//失败可以查出已被冲正的数据
        		sql.append(" and (t.resp_code <>'00' or revsal_flag='1') ");  
        	  }else if(Trans_State.equals("1")){//成功 需要过滤掉 已被冲正的交易
        		  sql.append("  And t.resp_code = '00' and revsal_flag<>'1' ");
        	  }else if(Trans_State.equals("3")){//查出原交易被撤销的记录 即撤销标志位是‘1’的记录
        		  sql.append("  And t.cancel_flag='1' ");
        	  }
          }
          //当查询交易金额的时候需要过滤掉交易金额为空字符的交易代码
        //  if(!amt_trans_max.equals("")||!amt_trans_min.equals("")){
        //	  sql.append(" And t.txn_num in "+TXN_NUM );
          //过滤掉冲正交易,查询交易
          sql.append(" And t.txn_num <>'1021' and t.txn_num<>'8311' and txn_num not in "+SysParamUtil.getParam("REVSALCODE"));
        //  }
          
          if(!amt_trans_max.equals("")){
        	  //接口传过来的是以元为单位的，数据库中存的是以分为单位的
        	  long amtMax=(long)(Float.parseFloat(amt_trans_max)*100);
			  sql.append("   And cast(replace(amt_trans,' ','0') As Number) <= "+amtMax);
          }
          if(!amt_trans_min.equals("")){
        	  //接口传过来的是以元为单位的，数据库中存的是以分为单位的
        	  long amtMin=(long)(Float.parseFloat(amt_trans_min)*100);
    		  sql.append("   And cast(replace(amt_trans,' ','0') As Number) >="+amtMin);
            }
         /* if(!amt_trans_max.equals("")){
		  sql.append("   And cast(amt_trans As Int) <= '"+amt_trans_max+ "'");
          }
          if(!amt_trans_min.equals("")){
    		  sql.append("   And cast(amt_trans As Int) >= '"+amt_trans_min+ "'");
            }*/
		//List<TxnModel> resultSet = commQueryDAO.findBySQLQueryModel(sql.toString(),begin,count);
          sql.append(" order by t.INST_DATE desc ");
		List list =commQueryDAO.findBySQLQuery(sql.toString(),begin*count,count);
		
		return list;
	}
	/**
	 * 获得单条交易详细数据
	 * @return
	 */
	public static synchronized List<Object[]> getTradeAll(Map paramMap) {
	
		String Card_Accp_Id ="";/*商户号  */
		String Card_Accp_Term_Id="";/*终端号  */
		String retrivl_ref ="";/*交易参考号  */
		String pan =""; /*卡号  */
		String Crans_Type ="";/*交易类型  */
		String  Inst_Date="";/*记录产生日期 */
//		String  before_date="";
		
		 Card_Accp_Id = paramMap.get("Ips_Mcht_Code").toString();
		
		if(paramMap.get("Term_Id")!=null && !paramMap.get("Term_Id").equals("")){
		   Card_Accp_Term_Id = paramMap.get("Term_Id").toString();
		}
		if(paramMap.get("Txn_Type")!=null && !paramMap.get("Txn_Type").equals("")){
			Crans_Type = paramMap.get("Txn_Type").toString();
			}
		if(paramMap.get("ReFe_Num")!=null && !paramMap.get("ReFe_Num").equals("")){
		   retrivl_ref = paramMap.get("ReFe_Num").toString();
		}
		
		if(paramMap.get("Inst_Date")!=null&&!paramMap.get("Inst_Date").equals("")){
			Inst_Date=paramMap.get("Inst_Date").toString();
		}
		if(paramMap.get("Pan")!=null && !paramMap.get("Pan").equals("")){
		   pan = paramMap.get("Pan").toString(); 
		}
		
	/*	Inst_Date = CommonFunction.getCurrentDate();// 系统当前日期
		before_date=CommonFunction.getBeforeDate(Inst_Date, -1);*/
		
		StringBuffer sql = new StringBuffer();
		  sql.append("Select card_accp_id As CARD_ACCP_ID, Card_Accp_Term_Id As TERM_ID, Inst_Date As INST_DATE, Updt_Date As UPDT_DATE,"+
			" txn_num As TXN_TYPE, Cup_Ssn As CUP_SSN, Sys_Seq_Num As Sys_Seq_Num, Term_Ssn As TERM_SSN, Retrivl_Ref As REFE_NUM,"+
			" Order_No As ORDER_TRSNUM, t.resp_code As TXN_STATE, Revsal_Flag As REVSAL_FLAG, Revsal_Ssn As REVSAL_SSN,"+
			" Cancel_Flag As CANCEL_FLAG, Cancel_Ssn As CANCEL_SSN, Pan As PAN, cast(replace(amt_trans,' ','0') As Number)/100 As TRS_AMOUNT,FWD_INST_ID_CODE as ISS_CODE,"+
			" Date_Settlmt As DATE_SETTLE, Authr_Id_Resp As AUTHR_ID_RESP, Tlr_Num As TLR_NUM From TBL_N_TXN t "+
			" Where 1=1 ");
          if(!Card_Accp_Term_Id.equals("")){
		  sql.append("  And t.card_accp_term_id = '"+Card_Accp_Term_Id+ "'");
          }
          if(!"".equals(Inst_Date)){
        	  sql.append(" And inst_date= '"+Inst_Date+"' ");
          }
          if(!Card_Accp_Id.equals("")){
        	  String mchtNoSql="select mcht_no from tbl_mcht_base_inf where mcht_no_hx='"+Card_Accp_Id+"'"; 
              List list=commQueryDAO.findBySQLQuery(mchtNoSql,0,1);
              if(list!=null&&list.size()>0)Card_Accp_Id=(String)list.get(0);
              log.info("商户号t.Card_Accp_Id："+Card_Accp_Id);
		  sql.append("  And t.card_accp_id = '"+Card_Accp_Id+ "'");
          }
           
          if(!Crans_Type.equals("")&&!Crans_Type.equals("0000")){
    		  sql.append("  And t.txn_num = '"+Crans_Type+ "'");
          }
          /*if(!Crans_Type.equals("")){
		  sql.append("  And t.trans_type = '"+Crans_Type+ "'");
          }*/
        
          if(!retrivl_ref.equals("")){
		  sql.append("   And t.retrivl_ref = '"+retrivl_ref+ "'");
          }
          if(!pan.equals("")){
		  sql.append("   And t.pan ='"+pan+ "'");
          }
          
//		  sql.append("   And t.amt_trans  Between  '"+amt_trans_max+ "'  and  '"+ amt_trans_min+"'");
       //   sql.append(" And t.txn_num in "+TXN_NUM );
		
		//List<TxnModelOnly> resultSet = commQueryDAO.findBySQLQueryModelAll(sql.toString());
		List<Object[]> resultSet=commQueryDAO.findBySQLQuery(sql.toString());

		return resultSet;
	}
	
	/**
	 * STR_ZERO 字符串0
	 */
	private static final String STR_ZERO = "0";
	/**
	 * 生成编号，从1开始
	 * @param yetExistNoLists 已存在列号列表
	 * @param noPrefix 编号前缀
	 * @param noLen 编号长度
	 * @return 编号
	 * 2012-4-27 下午4:28:25
	 * @author zhangq
	 */
	public synchronized static String genNo(List<String> yetExistNoLists,String noPrefix, int noLen) {
		// 前一个编号
		String prevNo = yetExistNoLists.get(0);

		StringBuilder minNoStr = new StringBuilder();
		for (int i = 0; i < noLen - 1; i++) {
			minNoStr.append(STR_ZERO);
		}
		minNoStr.append("1");  // 0001
		if(Integer.valueOf(prevNo.substring(noPrefix.length()).trim()) != 0){
			if (!prevNo.trim().equals(noPrefix + minNoStr.toString())) {
				return noPrefix + minNoStr.toString();
			}
		}

		int tempNo = Integer.valueOf(prevNo.substring(noPrefix.length()).trim());
		for (int i = 1; i < yetExistNoLists.size(); i++) {
			// 当前编号
			String currNo = yetExistNoLists.get(i);
			currNo = currNo.trim();
			tempNo = Integer.valueOf(prevNo.substring(noPrefix.length()).trim()) + 1;
			if (tempNo == Integer.valueOf(currNo.substring(noPrefix.length()))) {
				prevNo = currNo;
				continue;
			} else {
				return noGenerate(tempNo, noPrefix, noLen);
			}
		}
		return noGenerate(tempNo + 1, noPrefix, noLen);
	}
	
	/**
	 * 生成编号，从10000001开始
	 * @param yetExistNoLists 已存在列号列表
	 * @param noPrefix 编号前缀
	 * @param noLen 编号长度
	 * @return 编号
	 * 2012-4-27 下午4:28:25
	 * @author zhangq
	 */
	public synchronized static String genTermNo(List<String> yetExistNoLists,String noPrefix, int noLen) {
		// 前一个编号
		String prevNo = yetExistNoLists.get(0);

		StringBuilder minNoStr = new StringBuilder();
		minNoStr.append("1");
		for (int i = 0; i < noLen - 2; i++) {
			minNoStr.append(STR_ZERO);
		}
		minNoStr.append("1");
		if(Integer.valueOf(prevNo.substring(noPrefix.length()).trim()) != 0){
			if (!prevNo.trim().equals(noPrefix + minNoStr.toString())) {
				return noPrefix + minNoStr.toString();
			}
		}

		int tempNo = Integer.valueOf(prevNo.substring(noPrefix.length()).trim());
		for (int i = 1; i < yetExistNoLists.size(); i++) {
			// 当前编号
			String currNo = yetExistNoLists.get(i);
			currNo = currNo.trim();
			tempNo = Integer.valueOf(prevNo.substring(noPrefix.length()).trim()) + 1;
			if (tempNo == Integer.valueOf(currNo.substring(noPrefix.length()))) {
				prevNo = currNo;
				continue;
			} else {
				return noGenerate(tempNo, noPrefix, noLen);
			}
		}
		return noGenerate(tempNo + 1, noPrefix, noLen);
	}
	
	
	/**
	 * 编号生成器
	 * @param currNo 当前编号
	 * @param noPrefix 编号前缀
	 * @param noLen 编号长度
	 * @return 编号
	 * 2012-4-27 下午4:27:46
	 * @author zhangqy
	 */
	private static String noGenerate(int currNo, String noPrefix, int noLen) {
		String currNoStr = String.valueOf(currNo);
		String no = currNoStr;
		StringBuilder maxNoStr = new StringBuilder();
		maxNoStr.append("1");
		for (int i = 0; i < noLen; i++) {
			maxNoStr.append("0");
		}
		if (currNoStr.trim().equals(maxNoStr.toString())) {
			return "-1";
		} else {
			for (int j = 0; j < noLen - currNoStr.length(); j++) {
				no = STR_ZERO + no;
			}
			return no = noPrefix + no;
		}
	}
	
	
	/**
	 * 生成编号，从1开始
	 * @param yetExistNoLists 已存在列号列表
	 * @param noPrefix 编号前缀
	 * @param noLen 编号长度
	 * @return 编号
	 * 2012-4-27 下午4:28:25
	 * @author zhangq
	 */
	public synchronized static String agentNo(List<String> yetExistNoLists, int noLen) {   //传入agentNo的list 和编号长度
		
		if(yetExistNoLists.size() == 0)
			return "0000";
		
		
		// 前一个编号
		String prevNo = yetExistNoLists.get(0).toString();
       
		
		//最小值为0000
		StringBuilder minNoSB = new StringBuilder();  
		for (int i = 0; i < noLen; i++) {
			minNoSB.append(STR_ZERO);
		}
 		
		if (!prevNo.trim().equals( minNoSB.toString())) {
			return minNoSB.toString();
		}
		

		int tempNo = Integer.valueOf(prevNo.trim());
		for (int i = 1; i < yetExistNoLists.size(); i++) {
			
			String currNo = yetExistNoLists.get(i).trim(); //取第二个编号
			tempNo = Integer.valueOf(prevNo.trim()) + 1;   //取第一个编号+1
			if (tempNo == Integer.valueOf(currNo)) {
				prevNo = currNo;
				continue;
			} else {
				return String.format("%04d", tempNo);
			}
		}
		return String.format("%04d", tempNo + 1);
	}
}
