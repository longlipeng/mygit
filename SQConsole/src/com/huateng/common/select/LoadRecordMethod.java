/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-21       first release
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.huateng.bo.base.T10106BO;
import com.huateng.bo.base.T10206BO;
import com.huateng.bo.base.T11501BO;
import com.huateng.bo.error.T10031BO;
import com.huateng.bo.error.T10082BO;
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.bo.mchnt.T20304BO;
import com.huateng.bo.mchnt.T20701BO;
import com.huateng.bo.mchnt.TblDefMchtService;
import com.huateng.bo.term.T3010BO;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.CstSysParam;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;
import com.huateng.po.base.TblAgentInfo;
import com.huateng.po.base.TblAuitStatus;
import com.huateng.po.base.TblCityCodeCode;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.po.base.TblEmvParaValue;
import com.huateng.po.base.TblTradeCode;
import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblAlgoDtlPK;
import com.huateng.po.error.TblElecCashInf;
import com.huateng.po.mchnt.CheckMchtInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.mchnt.TblDefMchtInf;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.mchnt.TblInfDiscCdTmp;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.po.mchtSrv.TblMarketAct;
import com.huateng.po.risk.RiskBefore;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class LoadRecordMethod {

	public static TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
	public ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	public static T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");

	private T20304BO t20304BO = (T20304BO) ContextUtil.getBean("T20304BO");
	
	private T20701BO t20701BO = (T20701BO) ContextUtil.getBean("T20701BO");

	private T10031BO t10031BO = (T10031BO) ContextUtil.getBean("T10031BO");
	
	private T10082BO t10082BO = (T10082BO) ContextUtil.getBean("T10082BO");
	

	
//	private T10209BO t10209BO = (T10209BO) ContextUtil.getBean("T10209BO");
	
	private static MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");
	
	private TblDefMchtService tblDefMchtService = (TblDefMchtService) ContextUtil.getBean("TblDefMchtService");

	/**
	 * 获得集团商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *             2011-6-22下午01:43:43
	 */
	public String getGroupMchnt(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		try {
			TblGroupMchtInf inf = service.getGroupInf(request.getParameter("groupMchtCd"));
			if (null == inf) {
				return null;
			}
			inf = (TblGroupMchtInf) CommonFunction.trimObject(inf);
			return getMessage(JSONArray.fromObject(inf).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(request.getParameter("mchntId"));
//			TblMchtSettleInfTmp settle = service.getSettleInfTmp(request.getParameter("mchntId"));
			TblMchtSupp1Tmp supp1Tmp=service.getMchtSupp1Tmp(request.getParameter("mchntId"));
			
			String sql1 = "select BUSI_RANGE_ID,MCHT_NO,BUSI_RANGE from TBL_MCHT_BASE_BUSI_RANGE where MCHT_NO = '" + request.getParameter("mchntId") + "'";
			List<Object[]> lists = CommonFunction.getCommQueryDAO().findBySQLQuery(sql1);
			
			TblMchtBaseBusiRange tblMchtBaseBusiRange = null;
			if(null == lists || lists.isEmpty() || lists.size() == 0){
				//商户经营范围
				tblMchtBaseBusiRange = new TblMchtBaseBusiRange();
				//生成随机数
				Random random = new Random();
				String mchtNoRandom = request.getParameter("mchntId") + random.nextInt(999999);
				
				tblMchtBaseBusiRange.setBusiRangeId(mchtNoRandom);
				tblMchtBaseBusiRange.setMchtNo(request.getParameter("mchntId"));
				tblMchtBaseBusiRange.setBusiRange(inf.getBusiRangeId());
				//将TblMchtBaseInfTmp表的busiRangeId字段的经营范围值赋值TblMchtBaseBusiRange表的busiRange字段后把TblMchtBaseBusiRange表的busiRangeId字段赋值给TblMchtBaseInfTmp表的busiRangeId字段
				inf.setBusiRangeId(mchtNoRandom);
				
				service.addBaseBusiRange(tblMchtBaseBusiRange);
			}else{
				for (Object[] objects : lists) {
					tblMchtBaseBusiRange = new TblMchtBaseBusiRange();
					tblMchtBaseBusiRange.setBusiRangeId(objects[0].toString());
					tblMchtBaseBusiRange.setMchtNo(objects[1].toString());
					tblMchtBaseBusiRange.setBusiRange(objects[2].toString());
				}
				service.upBaseBusiRange(tblMchtBaseBusiRange);
			}
			
			if(supp1Tmp==null)supp1Tmp=new TblMchtSupp1Tmp();
			if (null == inf) {
				return null;
			}
			
			//获取 tbl_his_disc_algo2_tmp中该商户 费率相关 tmpNo
			String sql="select max(tmp_No) from tbl_his_disc_algo2_tmp where mcht_no='"+inf.getMchtNo()+"' ";
			String tmpNo= commQueryDAO.findCountBySQLQuery(sql);
			inf.setTmpNo(tmpNo);
			inf = (TblMchtBaseInfTmp) CommonFunction.trimObject(inf);
			
			//修改代理商显示方式
			String agentNo =inf.getAgentNo();
			if (null != agentNo) {
				StringBuffer sb =new StringBuffer();
				sb.append("SELECT AGENT_NO||'-'||AGENT_NM FROM TBL_AGENT_INFO WHERE AGENT_NO ='").append(agentNo).append("'");
				String agentNm =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
				inf.setAgentName(agentNm.trim());
			}
//			settle = (TblMchtSettleInfTmp) CommonFunction.trimObject(settle);
			String[] srcs = { 
								JSONArray.fromObject(inf).toString(),
								JSONArray.fromObject(supp1Tmp).toString(),
								JSONArray.fromObject(tblMchtBaseBusiRange).toString()
							};

			// 由于《商户基本信息表》和《商户清算信息表》中都有reserved字段，页面上取值不方便
			// 将《商户基本信息表》取出的reserved更名为idOtherNo
			String sub1 = srcs[0].substring(0, srcs[0].indexOf("reserved"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("reserved") + "reserved".length());
			srcs[0] = sub1 + "idOtherNo" + sub2;
			
			String str1 = srcs[2].substring(0, srcs[2].indexOf("busiRangeId"));
			String str2 = srcs[2].substring(srcs[2].indexOf("busiRangeId") + "busiRangeId".length());
			srcs[2] = str1 + "busiRangeIds" + str2;
			//System.out.println("99999999999999"+ JSONArray.fromObject(inf).toString()+JSONArray.fromObject(settle).toString()+JSONArray.fromObject(supp1Tmp).toString());
			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得商户信息(检查关键字段是否重复及是否在黑名单)
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntInf4Add(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(request.getParameter("mchntId"));
			TblMchtSettleInfPK id =new TblMchtSettleInfPK();
			id.setMchtNo(request.getParameter("mchntId"));
			id.setTermId(CommonFunction.fillString("*",' ',8,true));
			TblMchtSettleInfTmp settle = service.getSettleInfTmp(id);
			TblMchtSupp1Tmp supp1Tmp=service.getMchtSupp1Tmp(request.getParameter("mchntId"));			
			
			if(supp1Tmp==null)supp1Tmp=new TblMchtSupp1Tmp();			
			if (null == inf || null == settle) {
				return null;
			}
			//获取 tbl_his_disc_algo2_tmp中该商户 费率相关 tmpNo
			String sql="select max(tmp_No) from tbl_his_disc_algo2_tmp where mcht_no='"+inf.getMchtNo()+"' ";
			String tmpNo= commQueryDAO.findCountBySQLQuery(sql);
			supp1Tmp.setMcc2(tmpNo);
			inf.setTmpNo(tmpNo);
			inf = (TblMchtBaseInfTmp) CommonFunction.trimObject(inf);
			
			//检查关键字段
			CheckMchtInf checkMchtInf = getCheckMchtInf(inf);
			checkMchtInf = (CheckMchtInf)CommonFunction.trimObject(checkMchtInf);
			
			//修改mcc显示方式
			/*String mcc =inf.getMcc();
			StringBuffer sb =new StringBuffer();
			sb.append("SELECT MCHNT_TP||'-'||DESCR FROM TBL_INF_MCHNT_TP WHERE MCHNT_TP ='").append(mcc).append("'");
			mcc =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
			inf.setMcc(mcc.trim());	*/	
			
			//修改代理商显示方式
			/*String agentNo =inf.getAgentNo();
			StringBuffer sb =new StringBuffer();
			sb.append("SELECT AGENT_NO||'-'||AGENT_NM FROM TBL_AGENT_INFO WHERE AGENT_NO ='").append(agentNo).append("'");
			String agentNm =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
			inf.setAgentName(agentNm.trim());*/	
			inf.setAgentName("");
			settle = (TblMchtSettleInfTmp) CommonFunction.trimObject(settle);		
			
			String[] srcs = {JSONArray.fromObject(inf).toString(),
					JSONArray.fromObject(settle).toString(),JSONArray.fromObject(supp1Tmp).toString(),
					JSONArray.fromObject(checkMchtInf).toString()};

			// 由于《商户基本信息表》和《商户清算信息表》中都有reserved字段，页面上取值不方便
			// 将《商户基本信息表》取出的reserved更名为idOtherNo
			String sub1 = srcs[0].substring(0, srcs[0].indexOf("reserved"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("reserved") + "reserved".length());
			srcs[0] = sub1 + "idOtherNo" + sub2;
			
			/*String sub3 = srcs[3].substring(0, srcs[3].indexOf("settleType"));
			String sub4 = srcs[3].substring(srcs[3].indexOf("settleType") + "settleType".length());
			srcs[3] = sub3 + "idSettleType" + sub4;*/
			
//			String sub5 = srcs[0].substring(0, srcs[0].indexOf("mcc"));
//			String sub6 = srcs[0].substring(srcs[0].indexOf("mcc") + "mcc".length());
//			srcs[0] = sub5 + "idMcc" + sub6;
			
			
			//北京商户号字段中存放的是北京银行商户和随行付商户号，前15个字段是北京银行商户号，后15个字段是随行付商户号
			
			/*String sub1x = srcs[0].substring(0, srcs[0].indexOf("mchtNoHx")+"mchtNoHx".length()+3);  //商户号字段前面的字符串,包括:"
			String sub2x = srcs[0].substring(srcs[0].indexOf("mchtNoHx")+"mchtNoHx".length()+3);  //商户号字段以及商户号字段后面的字符串
			String subMcht=sub2x.substring(0, 30);  //商户号数据
			String sub3x=sub2x.substring(sub2x.indexOf(","));  //商户号数据后面的字符串，包括逗号,
			String mchtBJ=subMcht.substring(0,15); //北京商户号数据
			String mchtSX=subMcht.substring(15); //随行付商户号数据
			String mchtSXstr=sub1x.substring(sub1x.lastIndexOf(",")+1,sub1x.length()-1);  //北京商户号字段名称
			String sStr=mchtSXstr.substring(0,1);  //取一个双引号
			srcs[0] =sub1x+mchtBJ+sStr+","+sStr+"mchtSXNoHx"+sStr+":"+sStr+mchtSX.trim()+sStr+sub3x;*/
			
			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    /***
     *检查关键字段是否重复、是否在黑名单、MCC高亮显示 
     * @param inf
     * @return
     */
	private CheckMchtInf getCheckMchtInf(TblMchtBaseInfTmp inf) {
		CheckMchtInf checkMchtInf = new CheckMchtInf();
		//商户名是否重复
		String sqlTmp1 = "select MCHT_NM FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and MCHT_NM = '"+inf.getMchtNm().trim()+"'"; 
		List listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//商户名是否在黑名单
		String sqlTmp2 = "select sa_mer_ch_name from tbl_ctl_mcht_inf where SA_STATE='2' and sa_mer_ch_name = '"+inf.getMchtNm().trim()+"'";
		List listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//商户名既重复又存在于黑名单中
				checkMchtInf.setCkMchtNm("3");
			}else{
				//商户名重复，不存在于黑名单
				checkMchtInf.setCkMchtNm("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//商户名不重复，但存在于黑名单
			checkMchtInf.setCkMchtNm("2");
		}else{
			//商户名既不重复也不存在于黑名单中
			checkMchtInf.setCkMchtNm("0");
		}
		if (inf.getLicenceNo() != null) {
			//营业执照注册号是否重复
			sqlTmp1 = "select LICENCE_NO FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and LICENCE_NO = '"+inf.getLicenceNo().trim()+"'"; 
			listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		}
		if (inf.getLicenceNo() != null) {
		//营业执照注册号是否在黑名单
		sqlTmp2 = "select LICENCE_NO from tbl_ctl_mcht_inf where SA_STATE='2' and LICENCE_NO = '"+inf.getLicenceNo().trim()+"'";
		listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		}
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//营业执照注册号既重复又存在于黑名单中
				checkMchtInf.setCkLicenceNo("3");
			}else{
				//营业执照注册号重复，不存在于黑名单
				checkMchtInf.setCkLicenceNo("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//营业执照注册号不重复，但存在于黑名单
			checkMchtInf.setCkLicenceNo("2");
		}else{
			//营业执照注册号既不重复也不存在于黑名单中
			checkMchtInf.setCkLicenceNo("0");
		}
		//法人代表身份证号是否重复
		sqlTmp1 = "select IDENTITY_NO FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and upper(IDENTITY_NO)= '"+inf.getIdentityNo().trim().toUpperCase()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//法人代表身份证号是否在黑名单
		sqlTmp2 = "select IDENTITY_NO from tbl_ctl_mcht_inf where SA_STATE='2' and upper(IDENTITY_NO)= '"+inf.getIdentityNo().trim().toUpperCase()+"'";
		listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//法人代表身份证号既重复又存在于黑名单中
				checkMchtInf.setCkIdentityNo("3");
			}else{
				//法人代表身份证号重复，不存在于黑名单
				checkMchtInf.setCkIdentityNo("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//法人代表身份证号不重复，但存在于黑名单
			checkMchtInf.setCkIdentityNo("2");
		}else{
			//法人代表身份证号既不重复也不存在于黑名单中
			checkMchtInf.setCkIdentityNo("0");
		}
		if(null!=inf.getBankLicenceNo()&&!"".equals(inf.getBankLicenceNo().trim())){
		//组织机构代码证号是否重复
		sqlTmp1 = "select BANK_LICENCE_NO FROM tbl_mcht_base_inf  WHERE MCHT_STATUS='0' AND BANK_LICENCE_NO = '"+inf.getBankLicenceNo().trim()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//组织机构代码证号是否在黑名单
		sqlTmp2 = "select BANK_LICENCE_NO from tbl_ctl_mcht_inf where SA_STATE='2' and BANK_LICENCE_NO = '"+inf.getBankLicenceNo().trim()+"'";
		listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//组织机构代码证号既重复又存在于黑名单中
				checkMchtInf.setCkBankLicenceNo("3");
			}else{
				//组织机构代码证号重复，不存在于黑名单
				checkMchtInf.setCkBankLicenceNo("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//组织机构代码证号不重复，但存在于黑名单
			checkMchtInf.setCkBankLicenceNo("2");
		}else{
			//组织机构代码证号既不重复也不存在于黑名单中
			checkMchtInf.setCkBankLicenceNo("0");
		}
		}
		
		if(null!=inf.getManagerTel()&&!"".equals(inf.getManagerTel().trim())){
			//法人电话是否重复
			sqlTmp1 = "select MANAGER_TEL FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and MANAGER_TEL = '"+inf.getManagerTel().trim()+"'"; 
			listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
			//法人电话是否在黑名单
			sqlTmp2 = "select MANAGER_TEL from tbl_ctl_mcht_inf where SA_STATE='2' and MANAGER_TEL = '"+inf.getManagerTel().trim()+"'";
			listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
			if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
				if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
					//法人电话既重复又存在于黑名单中
					checkMchtInf.setCkManagerTel("3");
				}else{
					//法人电话重复，不存在于黑名单
					checkMchtInf.setCkManagerTel("1");
				}
			}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//法人电话不重复，但存在于黑名单
				checkMchtInf.setCkManagerTel("2");
			}else{
				//法人电话既不重复也不存在于黑名单中
				checkMchtInf.setCkManagerTel("0");
			}
			}
		
		//商户地址是否重复
		sqlTmp1 = "select ADDR FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and ADDR = '"+inf.getAddr().trim()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){			
				//商户地址重复
				checkMchtInf.setCkAddr("1");
		}else{
			//商户地址不重复
			checkMchtInf.setCkAddr("0");
		}
		//检查MCC
		String mcc = inf.getMcc().trim();
		if("8062".equals(mcc)||"8211".equals(mcc)||"8220".equals(mcc)||"8398".equals(mcc)
				||"9703".equals(mcc)||"9704".equals(mcc)||"9705".equals(mcc)||"9706".equals(mcc)
				||"9707".equals(mcc)||"9708".equals(mcc)||"9498".equals(mcc)){
			checkMchtInf.setCkMCC("1");
		}
		return checkMchtInf;
	}
	
	
	/**
	 * 获得商户信息(进件系统商户审核)
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntInf4Def(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblDefMchtInf inf = tblDefMchtService.getDefMchtInf(request.getParameter("recId"));			
			if (null == inf) {
				return null;
			}
			
			//修改代理商显示方式
			String legCert =inf.getLegCert();
			inf.setLegCert(legCert.substring(legCert.length()-2, legCert.length()));
						
			//检查关键字段
			CheckMchtInf checkMchtInf = getCheckMchtInfDef(inf);
			checkMchtInf = (CheckMchtInf)CommonFunction.trimObject(checkMchtInf);
						
			String[] srcs = {JSONArray.fromObject(inf).toString(),JSONArray.fromObject(checkMchtInf).toString()};			
			
			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

  //检查关键字段（进件系统）
	private CheckMchtInf getCheckMchtInfDef(TblDefMchtInf inf) {
		CheckMchtInf checkMchtInf = new CheckMchtInf();
		//商户名是否重复
		String sqlTmp1 = "select MCHT_NM FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and MCHT_NM = '"+inf.getMchtName().trim()+"'"; 
		List listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//商户名是否在黑名单
		String sqlTmp2 = "select sa_mer_ch_name from tbl_ctl_mcht_inf where SA_STATE='2' and sa_mer_ch_name = '"+inf.getMchtName().trim()+"'";
		List listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//商户名既重复又存在于黑名单中
				checkMchtInf.setCkMchtNm("3");
			}else{
				//商户名重复，不存在于黑名单
				checkMchtInf.setCkMchtNm("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//商户名不重复，但存在于黑名单
			checkMchtInf.setCkMchtNm("2");
		}else{
			//商户名既不重复也不存在于黑名单中
			checkMchtInf.setCkMchtNm("0");
		}
		
		//营业执照注册号是否重复
		sqlTmp1 = "select LICENCE_NO FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and LICENCE_NO = '"+inf.getBusiLicNo().trim()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//营业执照注册号是否在黑名单
		sqlTmp2 = "select LICENCE_NO from tbl_ctl_mcht_inf where SA_STATE='2' and LICENCE_NO = '"+inf.getBusiLicNo().trim()+"'";
		listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//营业执照注册号既重复又存在于黑名单中
				checkMchtInf.setCkLicenceNo("3");
			}else{
				//营业执照注册号重复，不存在于黑名单
				checkMchtInf.setCkLicenceNo("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//营业执照注册号不重复，但存在于黑名单
			checkMchtInf.setCkLicenceNo("2");
		}else{
			//营业执照注册号既不重复也不存在于黑名单中
			checkMchtInf.setCkLicenceNo("0");
		}
		//法人代表身份证号是否重复
		sqlTmp1 = "select IDENTITY_NO FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and upper(IDENTITY_NO)= '"+inf.getLegalCardNo().trim().toUpperCase()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//法人代表身份证号是否在黑名单
		sqlTmp2 = "select IDENTITY_NO from tbl_ctl_mcht_inf where SA_STATE='2' and upper(IDENTITY_NO)= '"+inf.getLegalCardNo().trim().toUpperCase()+"'";
		listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//法人代表身份证号既重复又存在于黑名单中
				checkMchtInf.setCkIdentityNo("3");
			}else{
				//法人代表身份证号重复，不存在于黑名单
				checkMchtInf.setCkIdentityNo("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//法人代表身份证号不重复，但存在于黑名单
			checkMchtInf.setCkIdentityNo("2");
		}else{
			//法人代表身份证号既不重复也不存在于黑名单中
			checkMchtInf.setCkIdentityNo("0");
		}
		if(null!=inf.getOrgCertNo()&&!"".equals(inf.getOrgCertNo().trim())){
		//组织机构代码证号是否重复
		sqlTmp1 = "select BANK_LICENCE_NO FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and BANK_LICENCE_NO = '"+inf.getOrgCertNo().trim()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		//组织机构代码证号是否在黑名单
		sqlTmp2 = "select BANK_LICENCE_NO from tbl_ctl_mcht_inf where SA_STATE='2' and BANK_LICENCE_NO = '"+inf.getOrgCertNo().trim()+"'";
		listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
			if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//组织机构代码证号既重复又存在于黑名单中
				checkMchtInf.setCkBankLicenceNo("3");
			}else{
				//组织机构代码证号重复，不存在于黑名单
				checkMchtInf.setCkBankLicenceNo("1");
			}
		}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
			//组织机构代码证号不重复，但存在于黑名单
			checkMchtInf.setCkBankLicenceNo("2");
		}else{
			//组织机构代码证号既不重复也不存在于黑名单中
			checkMchtInf.setCkBankLicenceNo("0");
		}
		}
		
		if(null!=inf.getLegalTel()&&!"".equals(inf.getLegalTel().trim())){
			//法人电话是否重复
			sqlTmp1 = "select MANAGER_TEL FROM tbl_mcht_base_inf WHERE MCHT_STATUS='0' and MANAGER_TEL = '"+inf.getLegalTel().trim()+"'"; 
			listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
			//法人电话是否在黑名单
			sqlTmp2 = "select MANAGER_TEL from tbl_ctl_mcht_inf where SA_STATE='2' and MANAGER_TEL = '"+inf.getLegalTel().trim()+"'";
			listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp2);
			if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){
				if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
					//法人电话既重复又存在于黑名单中
					checkMchtInf.setCkManagerTel("3");
				}else{
					//法人电话重复，不存在于黑名单
					checkMchtInf.setCkManagerTel("1");
				}
			}else if(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0){
				//法人电话不重复，但存在于黑名单
				checkMchtInf.setCkManagerTel("2");
			}else{
				//法人电话既不重复也不存在于黑名单中
				checkMchtInf.setCkManagerTel("0");
			}
			}
		
		/*//商户地址是否重复
		sqlTmp1 = "select ADDR FROM tbl_mcht_base_inf WHERE ADDR = '"+inf.getAddr().trim()+"'"; 
		listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
		if(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0){			
				//商户地址重复
				checkMchtInf.setCkAddr("1");
		}else{
			//商户地址不重复
			checkMchtInf.setCkAddr("0");
		}*/
		//检查MCC
		String mcc = inf.getMcc().trim();
		if("8062".equals(mcc)||"8211".equals(mcc)||"8220".equals(mcc)||"8398".equals(mcc)
				||"9703".equals(mcc)||"9704".equals(mcc)||"9705".equals(mcc)||"9706".equals(mcc)
				||"9707".equals(mcc)||"9708".equals(mcc)||"9498".equals(mcc)){
			checkMchtInf.setCkMCC("1");
		}
		return checkMchtInf;
	}

	/**
	 * 获得机构详细信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getAgenInf(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		final String sql = "select * from tbl_agency_info  where AGEN_ID='" + request.getParameter("agenid") + "' and TRAN_TYPE ='"+request.getParameter("tranType")+"'";
		ICommQueryDAO dao = CommonFunction.getCommQueryDAO();
		List<AgencyInfoPK> AgencyInfoPKList = (List<AgencyInfoPK>) dao.findBySQLQuery1PK(sql);
		
		
		
		String agentype = "";// 机构类型名称
		String trantype = "";// 交易连接类型名称
		String cardhome = "";// 归属卡组织
		String agenregbody = "";// 机构所属地区
		String agenmechcaltype = "";// 机构所辖商户清算方式
		String agencaltype = "";// 机构清算方式
		String agencalprincycle = "";// 机构本金清算周期
	//	String agencalprinmode = "";// 机构本金清算模式
		String agencalhandcycle = "";// 机构手续费清算周期
	//	String agencalhandmodel = "";// 机构手续费清算模式
		String agencallubcycle = "";// 机构分润清算周期
		String agencallubmode = "";// 机构分润清算模式
		String agenentrymode = "";// 机构出入账模式
		String agencleardetail = "";// 机构清分明细是否生成
		String feeflag="";     //费率方式   1按固定值  2按百分比
		AgencyInfo agencyInfo = null;
		
		T10106BO t10106BO = (T10106BO) ContextUtil.getBean("T10106BO");
		AgencyInfoPK agencyInfoPK = null;
		for (int i = 0; i < AgencyInfoPKList.size(); i++) {
			agencyInfoPK = AgencyInfoPKList.get(i);
			agencyInfo = t10106BO.get(agencyInfoPK);
			agentype = agencyInfo.getAGEN_TYPE();// 机构类型名称
			trantype = agencyInfoPK.getTRAN_TYPE();// 交易连接类型名称
			cardhome = agencyInfo.getCARD_HOME();// 归属卡组织
			agenregbody = agencyInfo.getAGEN_REG_BODY();// 机构所属地区
			agenmechcaltype = agencyInfo.getAGEN_MECH_CAL_TYPE();// 机构所辖商户清算方式
			agencaltype = agencyInfo.getAGEN_CAL_TYPE();// 机构清算方式
			agencalprincycle = agencyInfo.getAGEN_CAL_PRIN_CYCLE();// 机构本金清算周期
	//		agencalprinmode = agencyInfo.getAGEN_CAL_PRIN_MODE();// 机构本金清算模式
			agencalhandcycle = agencyInfo.getAGEN_CAL_HAND_CYCLE();// 机构手续费清算周期
	//	    agencalhandmodel = agencyInfo.getAGEN_CAL_HAND_MODE();// 机构手续费清算模式
			agencallubcycle = agencyInfo.getAGEN_CAL_LUB_CYCLE();// 机构分润清算周期
			agencallubmode = agencyInfo.getAGEN_CAL_LUB_MODE();// 机构分润清算模式
			agenentrymode = agencyInfo.getAGEN_ENTRY_MODE();// 机构出入账模式
			agencleardetail = agencyInfo.getAGEN_CLEAR_DETAIL();// 机构清分明细是否生成
			
			feeflag = agencyInfo.getFEE_FLAG();//费率方式
		}
		String sql2 = "select * from tbl_aduit_status b where type='2' and b.STATUE_ID='" + agentype + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql2);
		
		TblAuitStatus tblAuitStatus = TblAuitStatusList.get(0);
		
		String agentypeName = tblAuitStatus.getSTATUE_NAME();// 机构类型名称
		agencyInfo.setAgentypeName(agentypeName);
		
		String sql3 = "select * from tbl_aduit_status b where type='3' and b.STATUE_ID='" + trantype + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList2 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql3);
		TblAuitStatus tblAuitStatus2 = TblAuitStatusList2.get(0);
		
		String trantypename = tblAuitStatus2.getSTATUE_NAME();// 交易连接类型名称
		agencyInfo.setTrantypename(trantypename);
		// 归属卡组织
		StringBuffer cardhomename1 = new StringBuffer();
		String yinlian = cardhome.charAt(0) + "";
		if (yinlian.trim().equals("0")) {
			cardhomename1.append("");
		} else {
			cardhomename1.append("银联");
		}
		String visa = cardhome.charAt(1) + "";
		if (visa.trim().equals("0")) {
			cardhomename1.append("");
		} else {
			cardhomename1.append(" VISA");
		}
		String Master = cardhome.charAt(2) + "";
		if (Master.trim().equals("0")) {
			cardhomename1.append("");
		} else {
			cardhomename1.append(" Master");
		}
		String JCB = cardhome.charAt(3) + "";
		if (JCB.trim().equals("0")) {
			cardhomename1.append("");
		} else {
			cardhomename1.append(" JCB");
		}
		String DinersClub = cardhome.charAt(4) + "";
		if (DinersClub.trim().equals("0")) {
			cardhomename1.append("");
		} else {
			cardhomename1.append(" DinersClub");
		}
		String AmericanExpress = cardhome.charAt(5) + "";
		if (AmericanExpress.trim().equals("0")) {
			cardhomename1.append("");
		} else {
			cardhomename1.append(" AmericanExpress");
		}
		String cardhomename = cardhomename1.toString();
		agencyInfo.setCardhomename(cardhomename);
		agencyInfo.setYinlian(yinlian);
		agencyInfo.setVisa(visa);
		agencyInfo.setMaster(Master);
		agencyInfo.setJCB(JCB);
		agencyInfo.setDinersClub(DinersClub);
		agencyInfo.setAmericanExpress(AmericanExpress);
		// String
		// sql4="select * from tbl_aduit_status b where type='4' and b.STATUE_ID="+cardhome+"";
		// ArrayList<TblAuitStatus>
		// TblAuitStatusList3=(ArrayList<TblAuitStatus>)dao.findBySQLQuery2(sql4);
		// TblAuitStatus tblAuitStatus3=TblAuitStatusList3.get(0);
		// String cardhomename=tblAuitStatus3.getSTATUE_NAME();//归属卡组织
		// agencyInfo.setCardhomename(cardhomename);
		//String sql5 = "select * from tbl_city_code where CITY_CODE='" + agenregbody.trim() + "'";
		String sql5 = "select * from(select CITY_CODE,CITY_DES from TBL_CITY_CODE union select key,value  from cst_sys_param where owner = 'ALLAREA' ) where CITY_CODE = '"+agenregbody.trim()+"'";
		ArrayList<TblCityCodeCode> TblCityCodeCodeList = (ArrayList<TblCityCodeCode>) dao.findBySQLQuery3(sql5);
		TblCityCodeCode tblCityCodeCode = TblCityCodeCodeList.get(0);		
//		String agenregbodyhome = tblCityCodeCode.getCITY_DES();// 机构所属地区
//		agencyInfo.setAgenregbodyhome(agenregbodyhome);
		agencyInfo.setAgenregbodyhome(tblCityCodeCode.getCITY_DES());// 机构所属地区代码20121011修改
		
		String sql6 = "select * from tbl_aduit_status b where type='5' and b.STATUE_ID='" + agenmechcaltype + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList4 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql6);
		TblAuitStatus tblAuitStatus4 = TblAuitStatusList4.get(0);
		String agenmechcaltypename = tblAuitStatus4.getSTATUE_NAME();// 机构所辖商户清算方式
		agencyInfo.setAgenmechcaltypename(agenmechcaltypename);
		
		String sql7 = "select * from tbl_aduit_status b where type='6' and b.STATUE_ID='" + agencaltype + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList5 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql7);
		TblAuitStatus tblAuitStatus5 = TblAuitStatusList5.get(0);
		String agencaltypename = tblAuitStatus5.getSTATUE_NAME();// 机构清算方式
		agencyInfo.setAgencaltypename(agencaltypename);
		
		String sql8 = "select * from tbl_aduit_status b where type='7' and b.STATUE_ID='" + agencalprincycle + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList6 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql8);
		TblAuitStatus tblAuitStatus6 = TblAuitStatusList6.get(0);
		String agencalprincyclename = tblAuitStatus6.getSTATUE_NAME();// 机构本金清算周期
		agencyInfo.setAgencalprincyclename(agencalprincyclename);
		
		/*String sql9 = "select * from tbl_aduit_status b where type='8' and b.STATUE_ID='" + agencalprinmode + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList7 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql9);
		TblAuitStatus tblAuitStatus7 = TblAuitStatusList7.get(0);
		String agencalprinmodename = tblAuitStatus7.getSTATUE_NAME();
		agencyInfo.setAgencalprinmodename(agencalprinmodename);// 机构本金清算模式
*/		
		String sql10 = "select * from tbl_aduit_status b where type='9' and b.STATUE_ID='" + agencalhandcycle + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList8 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql10);
		TblAuitStatus tblAuitStatus8 = TblAuitStatusList8.get(0);
		String agencalhandcyclename = tblAuitStatus8.getSTATUE_NAME();
		agencyInfo.setAgencalhandcyclename(agencalhandcyclename);// 机构手续费清算周期
		
		/*String sql11 = "select * from tbl_aduit_status b where type='10' and b.STATUE_ID='" + agencalhandmodel + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList9 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql11);
		TblAuitStatus tblAuitStatus9 = TblAuitStatusList9.get(0);
		String agencalhandmodelname = tblAuitStatus9.getSTATUE_NAME();
		agencyInfo.setAgencalhandmodelname(agencalhandmodelname); // 机构手续费清算模式
*/		
		/*String sql12 = "select * from tbl_aduit_status b where type='11' and b.STATUE_ID='" + agencallubcycle + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList10 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql12);
		TblAuitStatus tblAuitStatus10 = TblAuitStatusList10.get(0);
		String agencallubcyclename = tblAuitStatus10.getSTATUE_NAME();
		agencyInfo.setAgencallubcyclename(agencallubcyclename); // 机构分润清算周期
*/		
		/*String sql13 = "select * from tbl_aduit_status b where type='12' and b.STATUE_ID='" + agencallubmode + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList11 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql13);
		TblAuitStatus tblAuitStatus11 = TblAuitStatusList11.get(0);
		String agencallubmodename = tblAuitStatus11.getSTATUE_NAME();
		agencyInfo.setAgencallubmodename(agencallubmodename); // 机构分润清算模式
*/		
		String sql14 = "select * from tbl_aduit_status b where type='13' and b.STATUE_ID='" + agenentrymode + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList12 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql14);
		TblAuitStatus tblAuitStatus12 = TblAuitStatusList12.get(0);
		String agenentrymodename = tblAuitStatus12.getSTATUE_NAME();
		agencyInfo.setAgenentrymodename(agenentrymodename); // 机构出入账模式
		
//		String sql16 = "select * from tbl_aduit_status b where type='24' and b.STATUE_ID='" + feeflag + "'";
//		ArrayList<TblAuitStatus> TblAuitStatusList16 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql16);
//		TblAuitStatus tblAuitStatus16 = TblAuitStatusList16.get(0);
//		String feeflagname = tblAuitStatus16.getSTATUE_NAME();
//		agencyInfo.setFeeflagname(feeflagname); // 费率方式
		
		if (agencleardetail != null) {
			String sql15 = "select * from tbl_aduit_status b where type='14' and b.STATUE_ID='" + agencleardetail + "'";
			ArrayList<TblAuitStatus> TblAuitStatusList13 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql15);
			TblAuitStatus tblAuitStatus13 = TblAuitStatusList13.get(0);
			String agencleardetailname = tblAuitStatus13.getSTATUE_NAME();
			agencyInfo.setAgencleardetailname(agencleardetailname); // 机构清分明细是否生成
		}
		try {
			agencyInfo = (AgencyInfo) CommonFunction.trimObject(agencyInfo);
//			System.out.println(JSONArray.fromObject(agencyInfo).toString());
			return getMessage(JSONArray.fromObject(agencyInfo).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* 获得机构分润的详细信息 */
	public String getAgenFeeInf(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		String feeid = request.getParameter("feeid");
		final String sql = "select * from TBL_AGENCY_FEE_LUB_TMP a where a.FEE_ID='" + feeid + "'";
		ICommQueryDAO dao = CommonFunction.getCommQueryDAO();
		List<AgencyFeeLubTmp> AgencyFeeInfoList = (List<AgencyFeeLubTmp>) dao.findBySQLQuery4(sql);
		String agentype = "";
		String tradeacceptreg = "";
		String cardstyle = "";
		String cardmedium = "";
		String tradechannel = "";
		String businesstype = "";
		String trantype = "";
		String mechratetype = "";
		String mechratemethod = "";
		String mechlubtype = "";
		String mechlubmethod = "";
		@SuppressWarnings("unused")
		String TERM_ID = "";
		@SuppressWarnings("unused")
		String MTCH_NO = "";
		@SuppressWarnings("unused")
		String MCC = "";
		@SuppressWarnings("unused")
		String AGEN_CRE_CARD = "";
		@SuppressWarnings("unused")
		String AGEN_TARGET_BODY = "";
		AgencyFeeLubTmp agencyFeeLub = null;
		for (int i = 0; i < AgencyFeeInfoList.size(); i++) {
			agencyFeeLub = AgencyFeeInfoList.get(i);
			agentype = agencyFeeLub.getAGEN_TYPE();
			System.out.println(agencyFeeLub.getEXTEN_FIELD1());
			tradeacceptreg = agencyFeeLub.getTRADE_ACCEPT_REG();
			cardstyle = agencyFeeLub.getCARD_STYLE();
			cardmedium = agencyFeeLub.getCARD_MEDIUM();
			tradechannel = agencyFeeLub.getTRADE_CHANNEL();
			businesstype = agencyFeeLub.getBUSINESS_TYPE();
			trantype = agencyFeeLub.getTRAN_TYPE();
			mechratetype = agencyFeeLub.getMCHT_RATE_TYPE();
			mechratemethod = agencyFeeLub.getMCHT_RATE_METHOD();
			mechlubtype = agencyFeeLub.getMCHT_LUB_TYPE();
			mechlubmethod = agencyFeeLub.getMCHT_LUB_METHOD();
			TERM_ID = agencyFeeLub.getTERM_ID();
			MTCH_NO = agencyFeeLub.getMTCH_NO();
			MCC = agencyFeeLub.getMCC_CODE();
			AGEN_CRE_CARD = agencyFeeLub.getAGEN_CRE_CARD();
			AGEN_TARGET_BODY = agencyFeeLub.getAGEN_TARGET_BODY();

		}
		// if(TERM_ID.equals("*")){
		// agencyFeeLub.setTERM_ID("全部支持");
		// }
		// if(MTCH_NO.equals("*")){
		// agencyFeeLub.setMTCH_NO("全部支持");
		// }
		 /*if("*".equals(MCC)){
		 agencyFeeLub.setMCC_CODE("全部支持");
		 }*/
		// if(AGEN_CRE_CARD.equals("*")){
		// agencyFeeLub.setAGEN_CRE_CARD("全部支持");
		// }
		// if(AGEN_TARGET_BODY.equals("*")){
		// agencyFeeLub.setAGEN_TARGET_BODY("全部支持");
		// }
		if ("*".equals(agentype)) {
			agencyFeeLub.setAgentypename("全部支持");
		} else {
			String sql2 = "select * from tbl_aduit_status b where type='2' and b.STATUE_ID='"
					+ agentype + "'";
			ArrayList<TblAuitStatus> TblAuitStatusList = (ArrayList<TblAuitStatus>) dao
					.findBySQLQuery2(sql2);
			TblAuitStatus tblAuitStatus = TblAuitStatusList.get(0);
			String agentypename = tblAuitStatus.getSTATUE_NAME();// 机构类型名称
			agencyFeeLub.setAgentypename(agentypename);
		}

		if("*".equals(tradeacceptreg)){
			agencyFeeLub.setTradeacceptreg("全部地区");
		}else{
		String sql12 = "select * from tbl_city_code where CITY_CODE='" + tradeacceptreg + "'";
		ArrayList<TblCityCodeCode> TblCityCodeCodeList = (ArrayList<TblCityCodeCode>) dao.findBySQLQuery3(sql12);
		TblCityCodeCode tblCityCodeCode = TblCityCodeCodeList.get(0);
		String tradeacceptregname = tblCityCodeCode.getCITY_DES();// 机构受理地区
		agencyFeeLub.setTradeacceptreg(tradeacceptregname);
		}
		
		
		if(cardstyle !=null){
			if ("*".equals(cardstyle)) {
				agencyFeeLub.setCardstylename("全部支持");
			} else {
				String sql3 = "select * from tbl_aduit_status b where type='15' and b.STATUE_ID='"
						+ cardstyle + "'";
				ArrayList<TblAuitStatus> TblAuitStatusList1 = (ArrayList<TblAuitStatus>) dao
						.findBySQLQuery2(sql3);
				TblAuitStatus tblAuitStatus1 = TblAuitStatusList1.get(0);
				String cardstylename = tblAuitStatus1.getSTATUE_NAME();//
				agencyFeeLub.setCardstylename(cardstylename);
			}
		}else{
			agencyFeeLub.setCardstylename("");
		}
		
		if(cardmedium !=null){
			if ("*".equals(cardmedium)) {
				agencyFeeLub.setCardmediumname("全部支持");
			} else {
				String sql4 = "select * from tbl_aduit_status b where type='16' and b.STATUE_ID='"
						+ cardmedium + "'";
				ArrayList<TblAuitStatus> TblAuitStatusList2 = (ArrayList<TblAuitStatus>) dao
						.findBySQLQuery2(sql4);
				TblAuitStatus tblAuitStatus2 = TblAuitStatusList2.get(0);
				String cardmediumname = tblAuitStatus2.getSTATUE_NAME();//
				agencyFeeLub.setCardmediumname(cardmediumname);
			}
		}else{
			agencyFeeLub.setCardmediumname("");
		}
		
		
		if(tradechannel !=null){
			if ("*".equals(tradechannel)) {
				agencyFeeLub.setTradechannelname("全部支持");
			} else {
				String sql5 = "select * from tbl_aduit_status b where type='17' and b.STATUE_ID='"
						+ tradechannel + "'";
				ArrayList<TblAuitStatus> TblAuitStatusList3 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql5);
				TblAuitStatus tblAuitStatus3 = TblAuitStatusList3.get(0);
				String tradechannelname = tblAuitStatus3.getSTATUE_NAME();//
				agencyFeeLub.setTradechannelname(tradechannelname);
			}
		}else{
			agencyFeeLub.setTradechannelname("");
		}
		
		if(businesstype !=null){
			if ("*".equals(businesstype)) {
				agencyFeeLub.setBusinesstype("全部支持");
			} else {
				String sql6 = "select * from tbl_aduit_status b where type='18' and b.STATUE_ID='" + businesstype + "'";
				ArrayList<TblAuitStatus> TblAuitStatusList4 = (ArrayList<TblAuitStatus>) dao.findBySQLQuery2(sql6);
				TblAuitStatus tblAuitStatus4 = TblAuitStatusList4.get(0);
				String businesstypename = tblAuitStatus4.getSTATUE_NAME();//
				agencyFeeLub.setBusinesstype(businesstypename);
			}
		}else{
			
			agencyFeeLub.setBusinesstype("");
		}
		
		if(trantype !=null){
			String sql7 = "select * from TBL_TRADE_CODE b where b.TRADE_CODE='" + trantype + "'";
			ArrayList<TblTradeCode> tblTradeCodeList5 = (ArrayList<TblTradeCode>) dao.findBySQLQuery5(sql7);
			TblTradeCode tblTradeCode = tblTradeCodeList5.get(0);
			String trantypename = tblTradeCode.getTRADE_NAME();//
			agencyFeeLub.setTrantypename(trantypename);
		}else{
			agencyFeeLub.setTrantypename("");
		}
		
		if(mechratetype !=null){
			String sql8 = "select * from tbl_aduit_status b where type='19' and b.STATUE_ID='"
					+ mechratetype + "'";
			ArrayList<TblAuitStatus> TblAuitStatusList5 = (ArrayList<TblAuitStatus>) dao
					.findBySQLQuery2(sql8);
			TblAuitStatus tblAuitStatus5 = TblAuitStatusList5.get(0);
			String mechratetypename = tblAuitStatus5.getSTATUE_NAME();//
			agencyFeeLub.setMechratetypename(mechratetypename);
		}else{
			agencyFeeLub.setMechratetypename("");
		}
		
		
		if(mechratemethod != null){
		String sql9 = "select * from tbl_aduit_status b where type='20' and b.STATUE_ID='"
				+ mechratemethod + "'";
		ArrayList<TblAuitStatus> TblAuitStatusList6 = (ArrayList<TblAuitStatus>) dao
				.findBySQLQuery2(sql9);
		TblAuitStatus tblAuitStatus6 = TblAuitStatusList6.get(0);
		String mechratemethodname = tblAuitStatus6.getSTATUE_NAME();//
		agencyFeeLub.setMechratemethodname(mechratemethodname);
		}else{
			agencyFeeLub.setMechratemethodname("");
		}
		
		if (mechlubtype != null) {
			String sql10 = "select * from tbl_aduit_status b where type='21' and b.STATUE_ID='"
					+ mechlubtype + "'";
			ArrayList<TblAuitStatus> TblAuitStatusList7 = (ArrayList<TblAuitStatus>) dao
					.findBySQLQuery2(sql10);
			TblAuitStatus tblAuitStatus7 = TblAuitStatusList7.get(0);
			String mechlubtypename = tblAuitStatus7.getSTATUE_NAME();//
			agencyFeeLub.setMechlubtypename(mechlubtypename);
		} else {
			agencyFeeLub.setMechlubtypename("");
		}
		if (mechlubmethod != null) {
			String sql11 = "select * from tbl_aduit_status b where type='22' and b.STATUE_ID='"
					+ mechlubmethod + "'";
			ArrayList<TblAuitStatus> TblAuitStatusList8 = (ArrayList<TblAuitStatus>) dao
					.findBySQLQuery2(sql11);
			TblAuitStatus tblAuitStatus8 = TblAuitStatusList8.get(0);
			String mechlubmethodname = tblAuitStatus8.getSTATUE_NAME();//
			agencyFeeLub.setMechlubmethodname(mechlubmethodname);
		} else {
			agencyFeeLub.setMechlubmethodname("");
		}
		try {
			agencyFeeLub = (AgencyFeeLubTmp) CommonFunction
					.trimObject(agencyFeeLub);

			return getMessage(JSONArray.fromObject(agencyFeeLub).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getRiskBefore(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		String mcht_Nm = request.getParameter("mcht_Nm");
		String sql = "select * from risk_before a where a.MCHT_NM='" + mcht_Nm + "'";
		ICommQueryDAO dao = CommonFunction.getCommQueryDAO();
		List<RiskBefore> RiskBeforeList = (List<RiskBefore>) dao.findBySQLQuery7(sql);
		RiskBefore riskBefore = null;
		String grade = "";
		String PREMISES = "";
//		String mcc = "";
		String param13="";
		String param11="";
		for (int i = 0; i < RiskBeforeList.size(); i++) {
			riskBefore = RiskBeforeList.get(i);
			grade = riskBefore.getGRADE();
			PREMISES = riskBefore.getPREMISES();
			//mcc = riskBefore.getPARAM2();
			param13=riskBefore.getPARAM13();
			param11=riskBefore.getPARAM11();
		}
		
		String sql1 = "select * from cst_sys_param a where a.KEY=" + grade
				+ " and OWNER='RISKLEVEL'";
		List<CstSysParam> CstSysParamList = (List<CstSysParam>) dao.findBySQLQuery6(sql1);
		CstSysParam cstSysParam = CstSysParamList.get(0);
		String gradename = cstSysParam.getValue();
		riskBefore.setGradename(gradename);
		String sql2 = "select * from cst_sys_param a where a.KEY=" + PREMISES
				+ " and OWNER='MCHNT_ATTR'";
		List<CstSysParam> CstSysParamList1 = (List<CstSysParam>) dao.findBySQLQuery6(sql2);
		CstSysParam cstSysParam1 = CstSysParamList1.get(0);
		String PREMISESname = cstSysParam1.getValue();
		riskBefore.setPREMISESname(PREMISESname);
//		String sql3 = "select * from tbl_aduit_status b where type='23' and b.STATUE_ID="
//				+ mcc + "";
//		ArrayList<TblAuitStatus> TblAuitStatusList6 = (ArrayList<TblAuitStatus>) dao
//				.findBySQLQuery2(sql3);
//		TblAuitStatus tblAuitStatus6 = TblAuitStatusList6.get(0);
//		String mccname = tblAuitStatus6.getSTATUE_NAME();//
//		riskBefore.setMccName(mccname);
		if(param13.substring(0, 1).equals("1")){
			riskBefore.setRbauto15("1");
			}else{
				riskBefore.setRbauto15("0");
			}
		if(param13.substring(1, 2).equals("1")){
			riskBefore.setRbauto16("1");
			}else{
				riskBefore.setRbauto16("0");
			}
		if(param13.substring(2, 3).equals("1")){
			riskBefore.setRbauto17("1");
			}else{
				riskBefore.setRbauto17("0");
			}
		if(param13.substring(3, 4).equals("1")){
			riskBefore.setRbauto18("1");
			}else{
				riskBefore.setRbauto18("0");
			}
		//
		if(param11.substring(0,1).equals("1")){
			riskBefore.setPARAM27("1");
			}else{
				riskBefore.setPARAM27("0");
			}
		if(param11.substring(1,2).equals("1")){
			riskBefore.setPARAM15("1");
			}else{
				riskBefore.setPARAM15("0");
			}
		if(param11.substring(2,3).equals("1")){
			riskBefore.setPARAM16("1");
			}else{
				riskBefore.setPARAM16("0");
			}
		if(param11.substring(3,4).equals("1")){
			riskBefore.setPARAM17("1");
			}else{
				riskBefore.setPARAM17("0");
			}
		if(param11.substring(4,5).equals("1")){
			riskBefore.setPARAM18("1");
			}else{
				riskBefore.setPARAM18("0");
			}
		if(param11.substring(5,6).equals("1")){
			riskBefore.setPARAM21("1");
			}else{
				riskBefore.setPARAM21("0");
			}
		if(param11.substring(6,7).equals("1")){
			riskBefore.setPARAM19("1");
			}else{
				riskBefore.setPARAM19("0");
			}
		if(param11.substring(7,8).equals("1")){
			riskBefore.setPARAM20("1");
			}else{
				riskBefore.setPARAM20("0");
			}
		if(param11.substring(8,9).equals("1")){
			riskBefore.setPARAM22("1");
			}else{
				riskBefore.setPARAM22("0");
			}
		if(param11.substring(9,10).equals("1")){
			riskBefore.setPARAM23("1");
			}else{
				riskBefore.setPARAM23("0");
			}
		if(param11.substring(10,11).equals("1")){
			riskBefore.setPARAM24("1");
			}else{
				riskBefore.setPARAM24("0");
			}
		if(param11.substring(11,12).equals("1")){
			riskBefore.setPARAM25("1");
			}else{
				riskBefore.setPARAM25("0");
			}
		try {
			riskBefore = (RiskBefore) CommonFunction.trimObject(riskBefore);
			System.out.println(JSONArray.fromObject(riskBefore).toString());
			return getMessage(JSONArray.fromObject(riskBefore).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 商户手续费规则
	 */

	public String getHisDisCal(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		String DISC_ID = request.getParameter("DISC_ID");
		String sql = "select * from TBL_HIS_DISC_ALGO1_TMP where DISC_ID='"
				+ DISC_ID + "'";
		ICommQueryDAO dao = CommonFunction.getCommQueryDAO();
		List<TblHisDiscAlgo1Tmp> tblHisDiscAlgo1TmpList = (List<TblHisDiscAlgo1Tmp>) dao
				.findBySQLQuery8(sql);
		TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp = null;
		String CITY_CODE = "";
		String CARD_TYPE = "";
		String CHANNEL_NO = "";
		String BUSINESS_TYPE = "";
		String TXN_NUM = "";
		String FLAG = "";
		for (int i = 0; i < tblHisDiscAlgo1TmpList.size(); i++) {
			tblHisDiscAlgo1Tmp = tblHisDiscAlgo1TmpList.get(i);
			CITY_CODE = tblHisDiscAlgo1Tmp.getCITY_CODE();
			CARD_TYPE = tblHisDiscAlgo1Tmp.getCARD_TYPE();
			CHANNEL_NO = tblHisDiscAlgo1Tmp.getCHANNEL_NO();
			BUSINESS_TYPE = tblHisDiscAlgo1Tmp.getBUSINESS_TYPE();
			FLAG = tblHisDiscAlgo1Tmp.getFLAG();
			TXN_NUM=tblHisDiscAlgo1Tmp.getTXN_NUM();
			tblHisDiscAlgo1Tmp.setFEE_VALUE1(tblHisDiscAlgo1Tmp.getFEE_VALUE()+"");
			tblHisDiscAlgo1Tmp.setFLOOR_AMOUNT1(tblHisDiscAlgo1Tmp.getFLOOR_AMOUNT()+"");
			tblHisDiscAlgo1Tmp.setMAX_FEE1(tblHisDiscAlgo1Tmp.getMAX_FEE()+"");
			tblHisDiscAlgo1Tmp.setMIN_FEE1(tblHisDiscAlgo1Tmp.getMIN_FEE()+"");
		}
		String sql1 = "select * from cst_sys_param a where a.KEY='"
				+ BUSINESS_TYPE + "' and OWNER='SERVICECODE'";
		List<CstSysParam> CstSysParamList = (List<CstSysParam>) dao
				.findBySQLQuery6(sql1);
		CstSysParam cstSysParam = CstSysParamList.get(0);
		String bussinesstypename = cstSysParam.getValue();
		tblHisDiscAlgo1Tmp.setBussinesstypename(bussinesstypename);
		String sql2 = "select * from tbl_city_code where CITY_CODE='"
				+ CITY_CODE + "'";
		ArrayList<TblCityCodeCode> TblCityCodeCodeList = (ArrayList<TblCityCodeCode>) dao
				.findBySQLQuery3(sql2);
		TblCityCodeCode tblCityCodeCode = TblCityCodeCodeList.get(0);
		String citycodename = tblCityCodeCode.getCITY_DES();// 地区
		tblHisDiscAlgo1Tmp.setCitycodename(citycodename);
		String sql3 = "select * from cst_sys_param a where a.KEY='" + CARD_TYPE
				+ "' and OWNER='CARDTYPE'";
		List<CstSysParam> CstSysParamList1 = (List<CstSysParam>) dao
				.findBySQLQuery6(sql3);
		CstSysParam cstSysParam1 = CstSysParamList1.get(0);
		String cardtypename = cstSysParam1.getValue();
		tblHisDiscAlgo1Tmp.setCardtypename(cardtypename);
		String sql4 = "select * from cst_sys_param a where a.KEY='" + CHANNEL_NO
				+ "' and OWNER='CHANNEL'";
		List<CstSysParam> CstSysParamList2 = (List<CstSysParam>) dao
				.findBySQLQuery6(sql4);
		CstSysParam cstSysParam2 = CstSysParamList2.get(0);
		String channelname = cstSysParam2.getValue();
		tblHisDiscAlgo1Tmp.setChannelname(channelname);
		String sql5 = "select * from cst_sys_param a where a.KEY='" + FLAG
				+ "' and OWNER='FLAG'";
		List<CstSysParam> CstSysParamList3 = (List<CstSysParam>) dao
				.findBySQLQuery6(sql5);
		CstSysParam cstSysParam3 = CstSysParamList3.get(0);
		String flagename = cstSysParam3.getValue();
		tblHisDiscAlgo1Tmp.setFlagename(flagename);
		String sql6 = "select * from cst_sys_param a where a.KEY='" + TXN_NUM
				+ "' and OWNER='TXNTYPE'";
		List<CstSysParam> CstSysParamList4 = (List<CstSysParam>) dao
				.findBySQLQuery6(sql6);
		CstSysParam cstSysParam4 = CstSysParamList4.get(0);
		String txnnumname = cstSysParam4.getValue();
		tblHisDiscAlgo1Tmp.setTxnnumname(txnnumname);
		try {
			tblHisDiscAlgo1Tmp = (TblHisDiscAlgo1Tmp) CommonFunction
					.trimObject(tblHisDiscAlgo1Tmp);
			System.out
					.println(JSONArray.fromObject(tblHisDiscAlgo1Tmp).toString());
			return getMessage(JSONArray.fromObject(tblHisDiscAlgo1Tmp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计费算法信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getFeeInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		try {
			TblInfDiscCdTmp inf = t20701BO.getTblInfDiscCd(request.getParameter("discCd"));

			if (null == inf) {
				return null;
			}
			inf = (TblInfDiscCdTmp) CommonFunction.trimObject(inf);
			return getMessage(JSONArray.fromObject(inf).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取终端信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *             2011-6-27下午04:17:15
	 */
	public String getTermInfo(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		String termId = request.getParameter("termId");
		String recCrtTs = request.getParameter("recCrtTs");
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		TblMchtBaseInf tblMchtBaseInfo = service.getMccByMchntId(tblTermInfTmp.getMchtCd());
		tblMchtBaseInfo.setContTel(tblTermInfTmp.getContTel());
		
		
		if (tblTermInfTmp != null){
			
			//修改商户号显示方式
			String mchtCd = tblTermInfTmp.getMchtCd();
			StringBuffer sb =new StringBuffer();
			sb.append("SELECT MCHT_NO||'-'||MCHT_NM FROM TBL_MCHT_BASE_INF WHERE MCHT_NO ='").append(mchtCd).append("'");
			mchtCd =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
			tblTermInfTmp.setMchtCd(mchtCd);	
			
			String[] srcs = { JSONArray.fromObject(tblTermInfTmp).toString(),
					JSONArray.fromObject(tblMchtBaseInfo).toString()};
			
			return getMessage(srcs);
	}
		else
		    return null;
	}

	/**
	 * 获取差错详细信息(差错流水信息)
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *  
	 */
	public String getBthCupErrTxnDetail(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		try{
			BthCupErrTxnPK bthCupErrTxnPK = new BthCupErrTxnPK();
			bthCupErrTxnPK.setDateSettlmt(request.getParameter("dateSettlmt"));
			bthCupErrTxnPK.setTxnNum(request.getParameter("txnNum"));
			bthCupErrTxnPK.setTxnSsn(request.getParameter("txnSsn"));
			
			BthCupErrTxn bthCupErrTxn = new BthCupErrTxn();
			try {
				bthCupErrTxn = t10031BO.get(bthCupErrTxnPK);
				String dateSettlmtNew = formatToYMD(bthCupErrTxn.getId().getDateSettlmt());
				String orgStlmDateNew = "";
				if(bthCupErrTxn.getOrgStlmDate()!=null && !"".equals(bthCupErrTxn.getOrgStlmDate().trim())){
					orgStlmDateNew = formatToYMD(bthCupErrTxn.getOrgStlmDate());
				}
				String transDateTimeNew = "";
				if(bthCupErrTxn.getTransDateTime()!=null && !"".equals(bthCupErrTxn.getTransDateTime().trim())){
					transDateTimeNew = formatToYMD(bthCupErrTxn.getTransDateTime().trim());
				}
				String orgDateTimeNew = "";
				if(bthCupErrTxn.getOrgDateTime()!=null && !"".equals(bthCupErrTxn.getOrgDateTime().trim())){
					orgDateTimeNew = formatToYMD(bthCupErrTxn.getOrgDateTime().trim());
				}
				String createTimeNew = formatToYMDHMS(bthCupErrTxn.getCreateTime());
				String lstUpdTimeNew = formatToYMDHMS(bthCupErrTxn.getLstUpdTime());
				String amtTransNew = formatToYuan(bthCupErrTxn.getAmtTrans());
				String feeCreditNew = formatToYuan(bthCupErrTxn.getFeeCredit());
				String feeDebitNew = formatToYuan(bthCupErrTxn.getFeeDebit());
				String amtTransFeeNew  = formatToYuan(bthCupErrTxn.getAmtTransFee());
				String orgTransAmtNew  = formatToYuan(bthCupErrTxn.getOrgTransAmt());
					
				bthCupErrTxnPK.setDateSettlmt(dateSettlmtNew);
				bthCupErrTxn.setId(bthCupErrTxnPK);
				bthCupErrTxn.setOrgStlmDate(orgStlmDateNew);
				bthCupErrTxn.setTransDateTime(transDateTimeNew);
				bthCupErrTxn.setOrgDateTime(orgDateTimeNew);
				bthCupErrTxn.setCreateTime(createTimeNew);
				bthCupErrTxn.setLstUpdTime(lstUpdTimeNew);
				if(bthCupErrTxn.getStlmFlg().trim().equals("0"))
					bthCupErrTxn.setStlmFlg("未清分");
				else if(bthCupErrTxn.getStlmFlg().equals("1"))
					bthCupErrTxn.setStlmFlg("已清分");
				else if(bthCupErrTxn.getStlmFlg().equals("2"))
					bthCupErrTxn.setStlmFlg("手工处理");
				else bthCupErrTxn.setStlmFlg("未知");
				bthCupErrTxn.setAmtTrans(amtTransNew);
				bthCupErrTxn.setAmtTransFee(amtTransFeeNew);
				bthCupErrTxn.setFeeCredit(feeCreditNew);
				bthCupErrTxn.setFeeDebit(feeDebitNew);
				bthCupErrTxn.setOrgTransAmt(orgTransAmtNew);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bthCupErrTxn != null){
				return getMessage(JSONArray.fromObject(bthCupErrTxn).toString());
			}else
				return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取差错详细信息（从清分清算表和交易流水表查询）
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *  
	 */
	public String getTblAlgoDtlDetail(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		String dateSettlmt = request.getParameter("dateSettlmt");
		String txnKey = request.getParameter("txnKey");
		String txnSsn = request.getParameter("txnSsn");
		String transDateTime = request.getParameter("transDateTime");
		transDateTime = transDateTime.replace("-", "");
		try{
			//查询T-1日交易是否已经清分清算，如果未清分清算则需要到交易流水表中查询挂账和解挂信息，已清算则从清分清算流水表中查询
			String countsql="select count(*) from TBL_ALGO_DTL d Where (stlm_flg In ('2','3') or OLD_TXN_FLG='3') And txn_ssn='" + txnSsn+"' and trans_date='"+transDateTime.substring(0, 8)+"'";
			if( "0".equals(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countsql)) ){
				//从交易流水表中查询
				StringBuffer sql = new StringBuffer();
				sql.append("select '").append(CommonFunction.getBeforeDate(CommonFunction.getCurrentDate(), -1))
					.append("' as DATE_SETTLMT,a.card_accp_term_id,a.card_accp_id,a.txn_num as TXN_NUM,a.BATCH_FLAG as STLM_FLG,")
					.append("(substr(a.inst_date,9,6)) as TRANS_DATE_TIME,(substr(a.inst_date,0,8)) as TRANS_DATE,a.PAN,")
					.append("a.SYS_SEQ_NUM as TXN_SSN,a.TERM_SSN,a.AMT_TRANS,a.AMT_SETTLMT,a.RCVG_CODE from tbl_n_txn a where " +
						/*	"(substr(a.inst_date,0,8))='")
					.append(CommonFunction.getBeforeDate(CommonFunction.getCurrentDate(), -1))
					.append("' and " +*/
							"a.BATCH_FLAG in ('2','3') and a.SYS_SEQ_NUM='").append(txnSsn)
					.append("' and substr(a.inst_date,0,8)='").append(transDateTime.substring(0, 8)).append("'");
				try{
					Object[] temp = (Object[]) CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString()).get(0);
					TblAlgoDtl tblAlgoDtl = new TblAlgoDtl();
					TblAlgoDtlPK id = new TblAlgoDtlPK(formatToYMD(dateSettlmt),CommonFunction.fillString(txnKey,' ',48, true));
					tblAlgoDtl.setId(id);
					tblAlgoDtl.setTermId((String)temp[1]);
					tblAlgoDtl.setMchtCd((String)temp[2]);
					tblAlgoDtl.setTxnNum((String)temp[3]);
					if(((String)temp[4]).equals("2"))
						tblAlgoDtl.setStlmFlg("交易挂账");
					else if(((String)temp[4]).equals("3"))
						tblAlgoDtl.setStlmFlg("交易解挂");
					else 
						tblAlgoDtl.setStlmFlg((String)temp[4]);
					tblAlgoDtl.setTransDateTime(formatToHMS((String)temp[5]));
					tblAlgoDtl.setOrgTxnNum("");
					tblAlgoDtl.setPan((String)temp[7]);
					tblAlgoDtl.setTxnSsn((String)temp[8]);
					tblAlgoDtl.setTermSsn((String)temp[9]);
					tblAlgoDtl.setFeeDOut("");
					tblAlgoDtl.setTransDate(formatToYMD((String)temp[6]));
					tblAlgoDtl.setTransAmt(formatToYuan((String)temp[10]));
					tblAlgoDtl.setSettlAmt(formatToYuan((String)temp[11]));
					tblAlgoDtl.setStlmInsIdCd(getBankName((String)temp[12]));	//对应页面受理机构20130723
					tblAlgoDtl.settOFlag("");
					tblAlgoDtl.setFeeCOut("");
					tblAlgoDtl.setSettlDate("");//20121206对应页面中清算日期
					
					if (tblAlgoDtl != null){
						//return getMessage(JSONArray.fromObject(tblAlgoDtl).toString());
						return returnMsg(tblAlgoDtl);
					}else
						return null;
				}catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}else{
				String sql = "select Date_Settlmt from TBL_ALGO_DTL d Where (stlm_flg In ('2','3') or OLD_TXN_FLG='3') And txn_ssn='" 
						+ txnSsn + "' and trans_date='" + transDateTime.substring(0, 8) + "'";//20121128修改
				String queryDateSettlmt = CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0).toString();
				
				String sql2 = "select OLD_TXN_FLG from TBL_ALGO_DTL d Where (stlm_flg In ('2','3') or OLD_TXN_FLG='3') And txn_ssn='" 
					+ txnSsn + "' and trans_date='" + transDateTime.substring(0, 8) + "'";//20121128修改
				String queryOldTxnFlg = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2).get(0).toString();
			
//				TblAlgoDtlPK id = new TblAlgoDtlPK(dateSettlmt,CommonFunction.fillString(txnKey,' ',48, true));
				TblAlgoDtlPK id = new TblAlgoDtlPK(queryDateSettlmt,CommonFunction.fillString(txnKey,' ',48, true));//20121128修改
				TblAlgoDtl tblAlgoDtl = new TblAlgoDtl();
				
				try {
					tblAlgoDtl = t10031BO.get(id);//从清分清算流水表中查询
//					String dateSettlmtNew = formatToYMD(request.getParameter("dateSettlmt"));
//					String dateSettlmtNew = formatToYMD(queryDateSettlmt);
					String transDateNew = formatToYMD(tblAlgoDtl.getTransDate());
					String transDateTimeNew = formatToHMS(tblAlgoDtl.getTransDateTime());
					String settlDateNew = "";
					if(tblAlgoDtl.getSettlDate()!=null && !"".equals(tblAlgoDtl.getSettlDate())){
						settlDateNew = formatToYMD(tblAlgoDtl.getSettlDate());
					}
					String transAmtNew = formatToYuan(tblAlgoDtl.getTransAmt());
					String settlAmtNew = formatToYuan(tblAlgoDtl.getSettlAmt());
					Double feeCOutNew = Double.valueOf(tblAlgoDtl.getFeeCOut());
					Double feeDOutNew = Double.valueOf(tblAlgoDtl.getFeeDOut());
					String stlmInsIdCd = getBankName(tblAlgoDtl.getStlmInsIdCd().toString().trim());
						
					id.setDateSettlmt(settlDateNew);
					tblAlgoDtl.setId(id);
					tblAlgoDtl.setTransDate(transDateNew);
					tblAlgoDtl.setTransDateTime(transDateTimeNew);
					tblAlgoDtl.setTransAmt(transAmtNew);
					tblAlgoDtl.setSettlAmt(settlAmtNew);
					tblAlgoDtl.setSettlDate(settlDateNew);
					tblAlgoDtl.setFeeCOut(String.valueOf(feeCOutNew));
					tblAlgoDtl.setFeeDOut(String.valueOf(feeDOutNew));
					tblAlgoDtl.setStlmInsIdCd(stlmInsIdCd);
					
					if("2".equals(tblAlgoDtl.getStlmFlg().trim()))
						tblAlgoDtl.setStlmFlg("交易挂账");
					else if("3".equals(tblAlgoDtl.getStlmFlg().trim()))
						tblAlgoDtl.setStlmFlg("交易解挂");
					else if("1".equals(tblAlgoDtl.getStlmFlg().trim()) && "3".equals(queryOldTxnFlg))
						tblAlgoDtl.setStlmFlg("交易解挂");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (tblAlgoDtl != null){
					//return getMessage(JSONArray.fromObject(tblAlgoDtl).toString());
					return returnMsg(tblAlgoDtl);
				}else
					return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String returnMsg(TblAlgoDtl tblAlgoDtl) throws IllegalAccessException, InvocationTargetException{
		String[] srcs = { JSONArray.fromObject(tblAlgoDtl).toString()};
		String buyDate = getBuyDate(tblAlgoDtl.getMchtCd(),tblAlgoDtl.getTermId(),tblAlgoDtl.getTxnSsn(),tblAlgoDtl.getTransDate());
		if(buyDate!=null && !"".equals(buyDate)){
			buyDate = formatToYMD(buyDate);
		}
		srcs[0] = addStringValue("buyDate",buyDate,srcs[0]);	//挂账日期
		return getMessage(srcs);
	}
	
	/**查询电子现金消费的差错信息
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getElecCashDetail(HttpServletRequest request)
		throws IllegalAccessException, InvocationTargetException {
	
	try {
		TblElecCashInf inf = t10082BO.get(request.getParameter("id"));
		if (null == inf) {
			return null;
		}
		inf.setTransAmt(formatToYuan(inf.getTransAmt()));		//交易金额
		inf.setDateSettlmt(formatToYMD(inf.getDateSettlmt()));	//清算日期
		inf.setSettlDate(formatToYMD(inf.getSettlDate()));		//结算日期
		inf.setSettlmtAmt(formatToYuan(inf.getSettlmtAmt()));	//清算金额
		inf.setFeeCredit(formatToYuan(inf.getFeeCredit()));		//应收手续费
		inf.setFeeDebit(formatToYuan(inf.getFeeDebit()));		//应付手续费
		inf = (TblElecCashInf) CommonFunction.trimObject(inf);
		String t0flag = "";
		
		TblMchtSettleInfPK id =new TblMchtSettleInfPK();
		id.setMchtNo(request.getParameter("mchntId"));
		id.setTermId("*");
		
		TblMchtSettleInf settleInf = service.getSettleInf(id);
		if(settleInf != null){
			t0flag = settleInf.getSettleChn().trim();
		}
		String[] srcs = { JSONArray.fromObject(inf).toString()};
		
		//srcs[0] = addStringValue("txnSsn","1",srcs[0]);	//收单流水号
		srcs[0] = addStringValue("errType","补电子现金消费",srcs[0]);	//差错交易类型
		srcs[0] = addStringValue("tOFlag",t0flag,srcs[0]);	//T+0入账标识
		return getMessage(srcs);
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}
	
	public String formatToYMD(String input) throws IllegalAccessException, InvocationTargetException {
		if(input==null || input.equals("")||input.length() != 8){
			return "";
		}else{
			StringBuffer formatTo= new StringBuffer("");
			formatTo.append(input.substring(0, 4)).append("-");
			formatTo.append(input.subSequence(4, 6)).append("-");
			formatTo.append(input.subSequence(6, 8));
			return formatTo.toString();
		}
	}
	
	public String formatToHMS(String input) throws IllegalAccessException, InvocationTargetException {
		if(input==null || input.equals("") || input.length() != 6){
			return "";
		}else{
			StringBuffer formatTo= new StringBuffer("");
			formatTo.append(input.subSequence(0, 2)).append(":");
			formatTo.append(input.subSequence(2, 4)).append(":");
			formatTo.append(input.subSequence(4, 6));
			return formatTo.toString();
		}
	}
	
	public String formatToYMDHMS(String input) throws IllegalAccessException, InvocationTargetException {
		if(input==null || input.equals("") || input.length() != 14){
			return "";
		}else{
			StringBuffer formatTo= new StringBuffer("");
			formatTo.append(input.substring(0, 4)).append("-");
			formatTo.append(input.subSequence(4, 6)).append("-");
			formatTo.append(input.subSequence(6, 8)).append(" ");
			formatTo.append(input.subSequence(8, 10)).append(":");
			formatTo.append(input.subSequence(10, 12)).append(":");
			formatTo.append(input.subSequence(12, 14));
			return formatTo.toString();
		}
	}
	
	public String formatToMDHMS(String input) throws IllegalAccessException, InvocationTargetException {
		if(input==null || input.equals("") || input.length() != 10){
			return "";
		}else{
			StringBuffer formatTo= new StringBuffer("");
			formatTo.append(input.substring(0, 2)).append("-");
			formatTo.append(input.subSequence(2, 4)).append(" ");
			formatTo.append(input.subSequence(4, 6)).append(":");
			formatTo.append(input.subSequence(6, 8)).append(":");
			formatTo.append(input.subSequence(8, 10));
			return formatTo.toString();
		}
	}
	
	public String formatToYuan(String input) throws IllegalAccessException, InvocationTargetException {
		if(input==null || input.trim().equals("")){
			return "";
		}else if(input.length() != 12){
			return input;
		}else if(input.trim().equals("000000000000") || input.trim().equals("-00000000000")){
			return "0";
		}else if(input.trim().indexOf(".") != -1){
			return input;
		}else if(input.startsWith("-")){
			StringBuffer formatTo= new StringBuffer("-");
			int temp = Integer.valueOf(input.substring(1, 10));//20121128修改
			formatTo.append(String.valueOf(temp));
			formatTo.append(".").append(input.subSequence(10, 12));
			return formatTo.toString();
		}else{
			StringBuffer formatTo = new StringBuffer("");
			int temp = Integer.valueOf(input.substring(0, 10));
			formatTo.append(String.valueOf(temp));
			formatTo.append(".").append(input.subSequence(10, 12));
			return formatTo.toString();
		}
	}
	
	public String getTermInfoEdit(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		String termId = request.getParameter("termId");
		String recCrtTs = request.getParameter("recCrtTs");
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		System.out.println("99999999999" + JSONArray.fromObject(tblTermInfTmp).toString());
		
		TblMchtBaseInf tblMchtBaseInfo = service.getMccByMchntId(tblTermInfTmp.getMchtCd());
		tblMchtBaseInfo.setContTel(tblTermInfTmp.getContTel());
		String[] srcs = { JSONArray.fromObject(tblTermInfTmp).toString(),
				JSONArray.fromObject(tblMchtBaseInfo).toString()};
			return getMessage(srcs);
		}
	
	
/*	String termId = request.getParameter("termId");
	String recCrtTs = request.getParameter("recCrtTs");
	TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
	TblMchtBaseInf tblMchtBaseInfo = service.getMccByMchntId(tblTermInfTmp.getMchtCd());
	tblMchtBaseInfo.setContTel(tblTermInfTmp.getContTel());
	
	
	if (tblTermInfTmp != null){
		
		//修改商户号显示方式
		String mchtCd = tblTermInfTmp.getMchtCd();
		StringBuffer sb =new StringBuffer();
		sb.append("SELECT MCHT_NO||'-'||MCHT_NM FROM TBL_MCHT_BASE_INF WHERE MCHT_NO ='").append(mchtCd).append("'");
		mchtCd =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
		tblTermInfTmp.setMchtCd(mchtCd);	
		
		String[] srcs = { JSONArray.fromObject(tblTermInfTmp).toString(),
				JSONArray.fromObject(tblMchtBaseInfo).toString()};
		
		return getMessage(srcs);*/
	public String getMchtFeeInfEdit(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		String cardType = request.getParameter("cardType");
		String mchtCd = request.getParameter("mchtCd");
		CstMchtFeeInfTmp cstMchtFeeInf = t20304BO.getMchtLimitTmp(new CstMchtFeeInfPK(mchtCd,cardType));
		System.out.println("111111111111:+" +JSONArray.fromObject(cstMchtFeeInf).toString());
		return getMessage(JSONArray.fromObject(cstMchtFeeInf).toString());
	}
	
	
	public String getActInfo(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		String actNo = request.getParameter("actNo");
		TblMarketAct tblMarketAct = marketActSrv.getActInfo(actNo);
		if (tblMarketAct != null)
			return getMessage(JSONArray.fromObject(tblMarketAct).toString());
		return null;
	}

	public String getIcParamInf(HttpServletRequest request) {
		T10206BO t10206BO = (T10206BO) ContextUtil.getBean("T10206BO");

		TblEmvPara para = t10206BO.get(new TblEmvParaPK(CommonFunction
				.fillString("0", ' ', 8, true), CommonFunction.fillString(request.getParameter("index"), ' ', 4, true)));

		TblEmvParaValue value = new TblEmvParaValue(para.getParaVal(), para.getParaOrg());

		return getMessage(JSONArray.fromObject(value).toString());
	}

	/**查询发卡机构名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getBankName(String id){
		String name = id;
		String sql = "SELECT distinct(trim(INS_ID_CD)),trim(INS_ID_CD)||' - '||trim(CARD_DIS) FROM TBL_BANK_BIN_INF";
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list != null && list.size()>0){
			for(Object[] tmp : list){
				if(tmp[0].toString().trim().equals(id)){
					name = tmp[1].toString().trim();
				}
			}
		}
		return name; 
	}
	
	
	
	public String getAgentInf(HttpServletRequest request) {
		T11501BO t11501BO = (T11501BO) ContextUtil.getBean("T11501BO");

		TblAgentInfo tbl = t11501BO.get(request.getParameter("agentNo"));
		String province = tbl.getAgentProvince();
		String city = tbl.getAgentCity();
		String district = tbl.getDistrict();
		String manageOwner = tbl.getManageOwner();
		String channelType = tbl.getChannelType();
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sb4 = new StringBuffer();
		StringBuffer sb5 = new StringBuffer();
		
		sb.append("SELECT VALUE FROM cst_sys_param WHERE KEY = '").append(province).append("'");
		sb2.append("SELECT VALUE FROM cst_sys_param WHERE KEY = '").append(city).append("'");
		sb3.append("select BRANCH_NAME from TBL_BRANCH_MANAGER_TRUE where BRANCH_NO ='").append(district).append("'");
		sb4.append("select VALUE from cst_sys_param where  owner = 'MANAGEOWNER' and KEY = '").append(manageOwner).append("'");
		sb5.append("select VALUE from cst_sys_param where  owner = 'CHANTYPE' and KEY = '").append(channelType).append("'");
		
		province=(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
		city=(String)commQueryDAO.findBySQLQuery(sb2.toString()).get(0);
		district=(String)commQueryDAO.findBySQLQuery(sb3.toString()).get(0);
		manageOwner=(String)commQueryDAO.findBySQLQuery(sb4.toString()).get(0);
		channelType=(String)commQueryDAO.findBySQLQuery(sb5.toString()).get(0);
		

		
		tbl.setAgentProvince(province.trim());
		tbl.setAgentCity(city.trim());
		tbl.setDistrict(district.trim());
		tbl.setManageOwner(manageOwner.trim());
		tbl.setChannelType(channelType);
		
		return getMessage(JSONArray.fromObject(tbl).toString());
	}
	
	/**为返回值添加其他字段
	 * @param name
	 * @param value
	 * @param srcs
	 * @return
	 */
	public String addStringValue(String name,String value,String srcs){
		String sub1 = srcs.substring(2).trim();
		String sub = "[{\""+name+"\":\""+value+"\","+sub1;
		return sub;
	}
	
	/**查询挂账日期
	 * @param key
	 * @param date
	 * @return
	 */
	public String getBuyDate(String mchtNo,String termId,String txnSnn,String date){
		String hangDate = "";
		date = date.replace("-", "");
		//按交易挂账
		String sql = "select HANG_DATE from TBL_TRANS_HANG where TRANS_DATE = '"+date+"' and TXN_SSN='"+txnSnn+"' and MCHT_NO='"+mchtNo+"' and TERM_ID='"+termId+"'";
		hangDate = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		if(StringUtil.isNull(hangDate) || hangDate.isEmpty()){
			//按终端挂账
			sql = "select HANG_DATE from TBL_TERM_HANG where TRANS_DATE = '"+date+"' and TERM_ID='"+termId+"'";
			hangDate = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		}
		if(StringUtil.isNull(hangDate) || hangDate.isEmpty()){
			//按商户挂账
			sql = "select HANG_DATE from TBL_MCHT_HANG where TRANS_DATE = '"+date+"' and MCHT_NO='"+mchtNo+"'";
			hangDate = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		}
		return hangDate;
	}
	/**
	 * 格式化输出
	 * 
	 * @param src
	 * @return 2011-6-22下午01:43:30
	 */
	public String getMessage(String src) {
		return "{\"data\":" + src + "}";
	}

	/**
	 * 多对象格式化输出
	 * 
	 * @param src
	 * @return 2011-6-22下午01:43:30
	 */
	public String getMessage(String[] srcs) {
		StringBuffer sb = new StringBuffer("{\"data\":[{");
		for (String src : srcs) {
			src = src.substring(2).trim();
			sb.append(src);
			sb.delete(sb.length() - 2, sb.length());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}]}");
		return sb.toString();
	}
}
