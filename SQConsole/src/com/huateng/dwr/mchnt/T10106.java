/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-5       first release
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import org.apache.log4j.Logger;

import com.huateng.bo.base.T1010601BO;
import com.huateng.common.StringUtil;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.base.TblInstBdbBankCodeTmp;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * Title: 
 * 
 * File: T20100.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T10106 {
	private static Logger log = Logger.getLogger(T10106.class);
	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	private static T1010601BO t1010601BO=(T1010601BO) ContextUtil.getBean("T1010601BO");

	
	
	
	/*
	 * 在新增机构页面 新增发卡行
	 * 2014.11.04  by shiyiwen
	 */
	public HashMap<String, String> addBankCode(String tmpNo,String bankCode ){
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		if(!StringUtil.isEmpty(tmpNo)){
			String sql = "select count(*) from TBL_INST_BDB_BANK_CODE_TMP where TMP_NO='"+tmpNo+"' and BANK_CODE ='"+bankCode+"'";
			String count=commQueryDAO.findCountBySQLQuery(sql);
			if(!"0".equals(count)){
				map.put("result", "E");
				map.put("msg", "已存在相同的开户行");
				return map;
			}
		}

		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = new TblInstBdbBankCodeTmp();
	//	T1010601BO t1010601BO=(T1010601BO) ContextUtil.getBean("T1010601BO");
		String id=GenerateNextId.getBankCodeId();
		if(StringUtil.isEmpty(tmpNo))tmpNo=id;
		
		tblInstBdbBankCodeTmp.setId(id);  //主键
		tblInstBdbBankCodeTmp.setTmpNo(tmpNo);  //批次号
		tblInstBdbBankCodeTmp.setBankCode(bankCode); //发卡行
		tblInstBdbBankCodeTmp.setState("0"); //状态为新增待审核
		
		
		try{
			t1010601BO.save(tblInstBdbBankCodeTmp);
			map.put("result","N");
			map.put("tmpNo",tmpNo);
		}catch(Exception e){
			e.printStackTrace();
			map.put("result","E");
			map.put("msg", "添加机构开户行失败"); 
		}

		
		return map;
	}
	
	
	/*
	 * 在修改机构页面 添加发卡行
	 * 2014.11.06  by shiyiwen
	 */
	public HashMap<String, String> addBankCode2(String tmpNo,String bankCode,String agenid ){
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		
		String sql0 = "select id,tmp_no,inst_code,bank_code,state from TBL_INST_BDB_BANK_CODE_TMP where inst_code = '"+agenid+"'";
		List<Object[]> list = commQueryDAO.findBySQLQuery(sql0);
		
		if(list.size() != 0){
		  tmpNo = list.get(0)[1].toString();
		}
		
		if(!StringUtil.isEmpty(tmpNo)){
			
			/*//如果批次号不为空，那么将该批次号的开户行，状态都置为 修改待审核 
			String sql2 = "select id,tmp_no,inst_code,bank_code,state from tbl_inst_bdb_bank_code_tmp where tmp_no = '"+tmpNo+"'";
			List<Object[]> list2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
			List<TblInstBdbBankCodeTmp> tblInstBdbBankCodeTmpList = new ArrayList<TblInstBdbBankCodeTmp>();
			
			TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp2 = null;
			for(int i=0;i<list2.size();i++){
				tblInstBdbBankCodeTmp2 = t1010601BO.query(list2.get(i)[0].toString());
				tblInstBdbBankCodeTmp2.setState("3");
				tblInstBdbBankCodeTmpList.add(tblInstBdbBankCodeTmp2);
				
			}
			t1010601BO.update(tblInstBdbBankCodeTmpList);*/
			
			//判断添加的开户行是否已存在
			String sql = "select count(*) from TBL_INST_BDB_BANK_CODE_TMP where TMP_NO='"+tmpNo+"' and state !=1 and BANK_CODE ='"+bankCode+"'";
			String count=commQueryDAO.findCountBySQLQuery(sql);
			if(!"0".equals(count)){
				map.put("result", "E");
				map.put("msg", "已存在相同的开户行");
				return map;
			}
		}
        
		
		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = new TblInstBdbBankCodeTmp();
		
		String id=GenerateNextId.getBankCodeId();
		if(StringUtil.isEmpty(tmpNo))tmpNo=id;
		
		tblInstBdbBankCodeTmp.setId(id);  //主键
		tblInstBdbBankCodeTmp.setTmpNo(tmpNo);  //批次号
		tblInstBdbBankCodeTmp.setInstCode(agenid);   //机构号
		tblInstBdbBankCodeTmp.setBankCode(bankCode); //发卡行
		tblInstBdbBankCodeTmp.setState("0"); //状态为新增待审核
		
		
		try{
			t1010601BO.save(tblInstBdbBankCodeTmp);
			map.put("result","N");
			map.put("tmpNo",tmpNo);
		}catch(Exception e){
			e.printStackTrace();
			map.put("result","E");
			map.put("msg", "添加机构开户行失败"); 
		}

		
		return map;
	}
	
	
}
