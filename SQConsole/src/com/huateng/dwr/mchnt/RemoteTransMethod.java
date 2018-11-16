/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-6-23       first release
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
package com.huateng.dwr.mchnt;

import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 
 * 
 * File: RemoteTransMethod.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-23
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class RemoteTransMethod {
	
	public static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	/**
	 * mcc码翻译
	 * 
	 * @param val
	 * @return
	 */
	public String mcc(String val){
		
		String sql = "select descr from tbl_inf_mchnt_tp where trim(mchnt_tp) = '" +  val.trim() + "'";
		
		return findData(sql,val);
	}
	
	/**
	 * mcc码翻译(带*带组别)
	 * 
	 * @param val
	 * @return
	 */
	public String mcc2(String val){
		
		//String sql = "select a.mchnt_descr from (select mchnt_tp,mchnt_tp ||' - '||descr as mchnt_descr from tbl_inf_mchnt_tp union select trim(key),trim(key) ||' - ' ||trim(value) from cst_sys_param where owner='ALLMCC') a where a.mchnt_tp ='" +  val.trim() + "'";
		String sql = "select t3.descr from (select t1.mchnt_tp as mchnt_tp,'(' || t2.descr || ')' || t1.mchnt_tp || ' - ' || t1.descr as descr "+
		  "from tbl_inf_mchnt_tp t1 "+
		  "left join tbl_inf_mchnt_tp_grp t2 "+
		    "on t1.mchnt_tp_grp = t2.mchnt_tp_grp "+    
		"union "+ 
		"select trim(key) as mchnt_tp,trim(key) ||' - ' ||trim(value) as descr from cst_sys_param where owner='ALLMCC') t3 "+
		" where t3.mchnt_tp =  '" +  val.trim() + "'";
				return findData(sql,val);
	}
	
	
	/**
	 * 机构名称翻译
	 * 环讯没有机构，只有分公司
	 * @param val
	 * @return
	 */
	public String brh(String val){
		
		//String sql = "select brh_name from tbl_brh_info where trim(brh_id) = '" +  val.trim() + "'";
		String sql="select branch_name from tbl_branch_manager_true where trim(branch_no)= '" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 地区翻译翻译（带*）
	 * @param val
	 * @return
	 */
	public String city(String val){
		
		//String sql = "select brh_name from tbl_brh_info where trim(brh_id) = '" +  val.trim() + "'";
		String sql="select a.city_des from (select CITY_CODE,CITY_CODE||' - '||CITY_DES as city_des from TBL_CITY_CODE union select key,key||' - '||value  from cst_sys_param where owner = 'ALLAREA') a where a.city_code ='" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 分润方式翻译
	 * @param val
	 * @return
	 */
	public String divideType(String val){
		
		String sql="select Value from cst_sys_param where trim(KEY)= '" +  val.trim() + "' and owner like 'DIVIDETYPE'";
		return findData(sql,val);
	}
	
	/**
	 * 商户号翻译成  商户号-商户名
	 * @param val
	 * @return
	 */
	public String mchtIdName(String val){
		
		//String sql="select Value from cst_sys_param where trim(KEY)= '" +  val.trim() + "' and owner like 'DIVIDETYPE'";
		String sql = "select Distinct trim(a.MCHT_NO)||' - '|| trim(a.MCHT_NM) from TBL_MCHT_BASE_INF a,TBL_TERM_INF b where a.MCHT_NO=b.MCHT_CD and a.MCHT_NO = '"+val.trim()+"'";
		return findData(sql,val);
	}
	
	/**
	 * 商户号翻译成  商户号-商户名 （带*号）
	 * @param val
	 * @return
	 */
	public String mchtIdName2(String val){
		
	//	String sql = "select Distinct trim(a.MCHT_NO)||' - '|| trim(a.MCHT_NM) from TBL_MCHT_BASE_INF a,TBL_TERM_INF b where a.MCHT_NO=b.MCHT_CD and a.MCHT_NO = '"+val.trim()+"'";
		String sql = "select trim(a.MCHT_NMS) from(select MCHT_NO, trim(MCHT_NO) ||' - '|| trim(MCHT_NM) as MCHT_NMS from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' union select key,key|| '-' ||value from cst_sys_param where owner='ALLMCHNT' and type='00') a where a.MCHT_NO = '"+val.trim()+"'";
		return findData(sql,val);
	}
	
	//select Distinct a.MCHT_NO, trim(a.MCHT_NO)||' - '|| trim(a.MCHT_NM) from TBL_MCHT_BASE_INF a,TBL_TERM_INF b where a.MCHT_NO=b.MCHT_CD
	
	/**
	 * 代理商费率方式翻译
	 * @param val
	 * @return
	 */
	public String discCd(String val){
		
		String sql="select DISC_CD|| '-' ||DISC_NM from tbl_inf_disc_cd where trim(DISC_CD)= '" +  val.trim() + "'";
		return findData(sql,val);
	}
	/**
	 * 机构名称翻译
	 * @param val
	 * @return
	 */
	public String agenNm(String val){
		
		String sql="select agen_id||' - '||agen_name from tbl_agency_info where trim(agen_id)= '" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 发卡行名称翻译
	 * @param val
	 * @return
	 */
	public String bankCode(String val){
		
		String sql="select distinct(ins_id_cd ||'- '|| card_dis) from tbl_bank_bin_inf  where trim(ins_id_cd)= '" +  val.trim() + "'";
		return findData(sql,val);
	}
	/**
	 * 机构信息管理维护中的机构所属地区翻译
	 * @param val
	 * @return
	 */
	public String AgencyCity(String val){
		
		String sql = "select CITY_DES from(select CITY_CODE,CITY_DES from TBL_CITY_CODE union select key,value  from cst_sys_param where owner = 'ALLAREA' ) where CITY_CODE = '"+val.trim()+"'";
		
	//	String sql="select DISC_NM from tbl_inf_disc_cd where trim(DISC_CD)= '" +  val.trim() + "'";
		return findData(sql,val);
	}
	/**
	 * 翻译商户名称
	 * @param val
	 * @return
	 * 2011-8-4下午06:00:20
	 */
	public String mchntName(String val){
		String sql = "select MCHT_NO||'-'||MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO = '" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 翻译操作员名称
	 * @param val
	 * @return
	 * 2014-9-2下午06:00:20
	 */
	public String getOprNm(String val){
		String sql = "select OPR_ID||'-'||OPR_NAME from TBL_OPR_INFO where OPR_ID = '" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 翻译代理商名称
	 * @param val
	 * @return
	 * 2014-9-2下午06:00:20
	 */
	public String getAgentNm(String val){
		String sql = "select AGENT_NO||'-'||AGENT_NM from TBL_AGENT_INFO where AGENT_NO = '" +  val.trim() + "'";
		return findData(sql,val);
	}
	
	/**
	 * 翻译终端地址
	 * @param val
	 * @return
	 * 2014-9-2下午06:00:20
	 */
	public String posAddrVal(String val){
		String[] addr = val.split("-");
		String posAddr="";
		int l = addr.length;
		if(addr.length==4){
			l=3;
		}
		for(int i = 0;i<l;i++){
			if(!"".equals(addr[i])){
				String sql = "select CITY_DES from TBL_CITY_CODE where CITY_CODE = '" +  addr[i].trim() + "'";
				posAddr+=findData(sql,addr[i]);
			}
		}
		if(addr.length==4){
			posAddr+=addr[3];
		}
		
		
		/*String sql = "select AGENT_NO||'-'||AGENT_NM from TBL_AGENT_INFO where AGENT_NO = '" +  val.trim() + "'";
		return findData(sql,val);*/
		
		return posAddr;
	}
	
	/**
	 * 简单sql语句执行
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findData(String sql,String val){
		
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
			return list.get(0).toString();
		} else {
			return val;
		}
	}

}

