/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-17       first release
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
package com.huateng.bo.impl.mchnt;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;

import su.HibernateSessionFactory;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.CstMchtFeeInfDAO;
import com.huateng.dao.iface.mchnt.ITblGroupMchtInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtAcctInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgo1DAO;
import com.huateng.dao.iface.mchnt.TblMchntLogsDAO;
import com.huateng.dao.iface.mchnt.TblMchntRefuseDAO;
import com.huateng.dao.iface.risk.TblCtlMchtInfDAO;
import com.huateng.dao.iface.risk.TblRiskRefuseDAO;
import com.huateng.dao.iface.risk.TblWhiteListDAO;
import com.huateng.dao.iface.risk.TblWhiteListTmpDAO;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.TblMchntRefuse;
import com.huateng.po.TblMchntRefusePK;
import com.huateng.po.mchnt.TblMchtBeneficiaryInf;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblMchntLogs;
import com.huateng.po.mchnt.TblMchntLogsPK;
import com.huateng.po.mchnt.TblMchtAcctInf;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.po.risk.TblWhiteListTmp;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.StatusUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblMchntServiceImpl implements TblMchntService {
	private String feeAcctNm;//对私账户名称
	private String settleAcctNm;//对公账户名称
	
	public String getFeeAcctNm() {
		return feeAcctNm;
	}

	public void setFeeAcctNm(String feeAcctNm) {
		this.feeAcctNm = feeAcctNm;
	}

	public String getSettleAcctNm() {
		return settleAcctNm;
	}

	public void setSettleAcctNm(String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(
			ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public ITblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	public void setTblMchtSettleInfTmpDAO(
			ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	public ITblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	public void setTblMchtSettleInfDAO(ITblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	public TblMchntRefuseDAO getTblMchntRefuseDAO() {
		return tblMchntRefuseDAO;
	}

	public void setTblMchntRefuseDAO(TblMchntRefuseDAO tblMchntRefuseDAO) {
		this.tblMchntRefuseDAO = tblMchntRefuseDAO;
	}

	public ITblGroupMchtInfDAO getTblGroupMchtInfDAO() {
		return tblGroupMchtInfDAO;
	}

	public void setTblGroupMchtInfDAO(ITblGroupMchtInfDAO tblGroupMchtInfDAO) {
		this.tblGroupMchtInfDAO = tblGroupMchtInfDAO;
	}

	public ITblMchtAcctInfDAO getTblMchtAcctInfDAO() {
		return tblMchtAcctInfDAO;
	}

	public void setTblMchtAcctInfDAO(ITblMchtAcctInfDAO tblMchtAcctInfDAO) {
		this.tblMchtAcctInfDAO = tblMchtAcctInfDAO;
	}
	private TblMchntLogsDAO tblMchntLogsDAO;

	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;

	public ITblMchtBaseInfDAO tblMchtBaseInfDAO;

	public ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;

	public ITblMchtSettleInfDAO tblMchtSettleInfDAO;

	public TblMchntRefuseDAO tblMchntRefuseDAO;

	public ITblGroupMchtInfDAO tblGroupMchtInfDAO;

	public ITblMchtAcctInfDAO tblMchtAcctInfDAO;
	
	private TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO;
	
    public CstMchtFeeInfDAO cstMchtFeeInfDAO;
	
	private TblWhiteListTmpDAO tblWhiteListTmpDAO;
	
	private TblCtlMchtInfDAO tblCtlMchtInfDAO;
	
	private TblRiskRefuseDAO tblRiskRefuseDAO;
	


	public TblRiskRefuseDAO getTblRiskRefuseDAO() {
		return tblRiskRefuseDAO;
	}

	public void setTblRiskRefuseDAO(TblRiskRefuseDAO tblRiskRefuseDAO) {
		this.tblRiskRefuseDAO = tblRiskRefuseDAO;
	}

	public TblCtlMchtInfDAO getTblCtlMchtInfDAO() {
		return tblCtlMchtInfDAO;
	}

	public void setTblCtlMchtInfDAO(TblCtlMchtInfDAO tblCtlMchtInfDAO) {
		this.tblCtlMchtInfDAO = tblCtlMchtInfDAO;
	}

	public CstMchtFeeInfDAO getCstMchtFeeInfDAO() {
		return cstMchtFeeInfDAO;
	}

	public void setCstMchtFeeInfDAO(CstMchtFeeInfDAO cstMchtFeeInfDAO) {
		this.cstMchtFeeInfDAO = cstMchtFeeInfDAO;
	}

	

	public TblWhiteListTmpDAO getTblWhiteListTmpDAO() {
		return tblWhiteListTmpDAO;
	}

	public void setTblWhiteListTmpDAO(TblWhiteListTmpDAO tblWhiteListTmpDAO) {
		this.tblWhiteListTmpDAO = tblWhiteListTmpDAO;
	}

	public TblHisDiscAlgo1DAO getTblHisDiscAlgo1DAO() {
		return tblHisDiscAlgo1DAO;
	}

	public void setTblHisDiscAlgo1DAO(TblHisDiscAlgo1DAO tblHisDiscAlgo1DAO) {
		this.tblHisDiscAlgo1DAO = tblHisDiscAlgo1DAO;
	}
						
	public TblMchntLogsDAO getTblMchntLogsDAO() {
		return tblMchntLogsDAO;
	}

	public void setTblMchntLogsDAO(TblMchntLogsDAO tblMchntLogsDAO) {
		this.tblMchntLogsDAO = tblMchntLogsDAO;
	}

	public   ICommQueryDAO commQueryDAO ;
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	/***
	 * 记录商户操作日志
	 * @param tblMchtBaseInfTmp
	 * @param oprType
	 * @param oprStatus
	 * @param oprInfo
	 */
	public void updateMchntLogs(TblMchtBaseInfTmp tblMchtBaseInfTmp, String oprType, String oprStatus, String oprInfo) {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		TblMchntLogsPK id = new TblMchntLogsPK(CommonFunction.getCurrentDateTime(), tblMchtBaseInfTmp.getMchntInd());
		TblMchntLogs tblMchntLogs = new TblMchntLogs(id);
		tblMchntLogs.setMchntId(tblMchtBaseInfTmp.getMchtNo());
		tblMchntLogs.setOprId(opr.getOprId());
		tblMchntLogs.setOprType(oprType);
		tblMchntLogs.setOprStatus(oprStatus);
		tblMchntLogs.setOprInfo(oprInfo);
		tblMchntLogs.setCrtOprId(opr.getOprId());
		tblMchntLogs.setUpdOprId(opr.getOprId());
		tblMchntLogs.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblMchntLogs.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblMchntLogsDAO.save(tblMchntLogs);
		
	}


	/*
	 * 保存商户信息至临时表
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#saveTmp(com.huateng.po.mchnt
	 * .TblMchtBaseInfTmp, as.huateng.po.management.mer.TblMchtSettleInfTmp)
	 */
	public String saveTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtSupp1Tmp supp1Tmp,
			TblMchtBaseBusiRange tblMchtBaseBusiRange) {

		if(tblMchtBaseInfTmpDAO.get(tblMchtBaseInfTmp.getMchtNo()) != null) {
			return "您自定义的商户编号已经存在";
		}
		tblMchtBaseInfTmpDAO.save(tblMchtBaseInfTmp);
		tblMchtSettleInfTmpDAO.save(tblMchtSettleInfTmp);
		tblMchtSettleInfTmpDAO.save(supp1Tmp);
		tblMchtSettleInfTmpDAO.save(tblMchtBaseBusiRange);
		
		String oprType = "新增商户信息";
		String oprStatus = "新增";
		String oprInfo = "";
		updateMchntLogs(tblMchtBaseInfTmp,oprType,oprStatus,oprInfo);

		return Constants.SUCCESS_CODE;
	}

	
	/*
	 * 更新商户信息至临时表
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#updateTmp(com.huateng.po.mchnt
	 * .TblMchtBaseInfTmp, com.huateng.po.mchnt.TblMchtSettleInfTmp)
	 */
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,TblMchtSupp1Tmp supp1Tmp) {

		//遗留旧数据中mchntInd字段为空，暂时填入商户号
		if(tblMchtBaseInfTmp.getMchntInd()==null||"".equals(tblMchtBaseInfTmp.getMchntInd())){
			tblMchtBaseInfTmp.setMchntInd(tblMchtBaseInfTmp.getMchtNo());
		}
		
		tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
//		tblMchtSettleInfTmpDAO.update(tblMchtSettleInfTmp);
		tblMchtSettleInfTmpDAO.saveOrUpdate(supp1Tmp);
		
		String oprType = "商户信息维护";
		String oprStatus = "修改";
		String oprInfo = "";
		updateMchntLogs(tblMchtBaseInfTmp,oprType,oprStatus,oprInfo);
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim()))
			return true;
		else
			return false;
	}
	/*
	 * 商户审核通过
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#accept(java.lang.String)
	 */
	public String accept(String mchntId,String oprInfo) throws IllegalAccessException, InvocationTargetException {
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
//		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		TblMchtSupp1Tmp suppTmp=tblMchtSettleInfTmpDAO.getSupp1Tmp(mchntId);//商户补充信息
		List<TblMchtBeneficiaryInfTmp> amltList = tblMchtBaseInfTmpDAO.getBeneficiaryTmp(mchntId);//受益人信息
		
		if (null == tmp /*|| null == tmpSettle*/) {
			return "没有找到商户的临时信息，请重试";
		}
		
		String mchtNm = tmp.getMchtNm();
		String ctnmSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and ctnm = '"+ mchtNm +"'";
		String ctnmcout = CommonFunction.getCommQueryDAO().findCountBySQLQuery(ctnmSql);
		if (!ctnmcout.equals("0")) {
			back2(mchntId, "商户名与黑名单商户名匹配不允审核！");
			return "商户名与黑名单商户名匹配不允审核！";
		}
		String manager = tmp.getManager();
		String clegSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cleg = '"+ manager +"'";
		String clegcout = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clegSql);
		if (!clegcout.equals("0")) {
			back2(mchntId, "法人与黑名单法人匹配不允审核！");
			return "法人与黑名单法人匹配不允审核！";
		}
		String identityNo = tmp.getIdentityNo();
		String clidSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and ciid = '"+ identityNo +"'";
		String clidCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql);
		if (!clidCount.equals("0")) {
			back2(mchntId, "法人证件号与黑名单法人证件号匹配不允审核！");
			return "法人证件号与黑名单法人证件号匹配不允审核！";
		}
		String regAddr = tmp.getRegAddr();
		String careSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and care like '"+ regAddr +"%'";
		String careCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(careSql);
		if (!careCount.equals("0")) {
			back2(mchntId, "注册地与黑名单注册地匹配不允审核！");
			return "注册地与黑名单注册地匹配不允审核！";
		}
		if (isNotEmpty(tmp.getUscCode())) {
			String clidSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '2'  and ciid = '"+ tmp.getUscCode() +"'";
			String clidCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql1);
			if (!clidCount1.equals("0")) {
				back2(mchntId, "社会信用代码与黑名单证件号匹配不允审核！");
				return "社会信用代码与黑名单证件号匹配不允审核！";
			}
		}
		if (isNotEmpty(tmp.getLicenceNo())) {
			String clidSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '2'  and ciid = '"+ tmp.getUscCode() +"'";
			String clidCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql1);
			if (!clidCount1.equals("0")) {
				back2(mchntId, "营业执照与黑名单证件号匹配不允审核！");
				return "营业执照与黑名单证件号匹配不允审核！";
			}
		}
		String regAddr1 = tmp.getRegAddr();
		String careSql1 = "select count(region_name) from t_lst_region where status = '1' and region_name like '%"+ regAddr1 +"%'";
		String careCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(careSql1);
		if (!careCount1.equals("0")) {
			back2(mchntId, "注册地与黑名单注册地匹配不允审核！");
			return "注册地与黑名单注册地匹配不允审核！";
		}
		String ctnmSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '1' and ctnm = '"+ manager +"'";
		String ctnmcout1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(ctnmSql1);
		if (!ctnmcout1.equals("0")) {
			back2(mchntId, "法人与黑名单法人匹配不允审核！");
			return "法人与黑名单法人匹配不允审核！";
		}
		String clidSql2 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '1' and ciid = '"+ identityNo +"'";
		String clidCount2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql2);
		if (!clidCount2.equals("0")) {
			back2(mchntId,"法人证件号与黑名单法人证件号匹配不允审核！");
			return "法人证件号与黑名单法人证件号匹配不允审核！";
		}
		String rislLvl = tmp.getRislLvl();
		String mchtStatus = tmp.getMchtStatus();
		if (!mchtStatus.equals("I") && !mchtStatus.equals("J")  && !mchtStatus.equals("K") && !mchtStatus.equals("L")) {
			if (rislLvl.equals("2")) {
				
				try {
					if (mchtStatus.equals("1")) {
						tmp.setMchtStatus("I");
						tmp.setCrtOprId(opr.getOprId());
						tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
						String oprType = "商户维护首审";
						String oprStatus="通过";
						updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
						return Constants.SUCCESS_CODE;
					}else if (mchtStatus.equals("3")){
						tmp.setMchtStatus("J");
						tmp.setCrtOprId(opr.getOprId());
						tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
						String oprType = "商户维护首审";
						String oprStatus="通过";
						updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
						return Constants.SUCCESS_CODE;
					}else if (mchtStatus.equals("5")){
						tmp.setCrtOprId(opr.getOprId());
						tmp.setMchtStatus("K");
						tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
						String oprType = "商户维护首审";
						String oprStatus="通过";
						updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
						return Constants.SUCCESS_CODE;
					}else if (mchtStatus.equals("9")){
						tmp.setCrtOprId(opr.getOprId());
						tmp.setMchtStatus("L");
						tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
						String oprType = "商户维护首审";
						String oprStatus="通过";
						updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
						return Constants.SUCCESS_CODE;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "审核失败！";
				}
			}
		}
		
		if(suppTmp==null){
			suppTmp=new TblMchtSupp1Tmp();
			suppTmp.setMchtNo(mchntId);
		}
		

		// 取得原始信息
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
//		TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle.getMchtNo());
		TblMchtSupp1 supp=tblMchtSettleInfTmpDAO.getSupp1(mchntId);
		List<TblMchtBeneficiaryInf> list = tblMchtBaseInfTmpDAO.getBeneficiary(mchntId);
		
		
		if (null == inf) {
			inf = new TblMchtBaseInf();
		} 
		/*if (null == infSettle) {
			infSettle = new TblMchtSettleInf();
		}*/
		if(supp==null){
			supp = new TblMchtSupp1();
		}
		
		/*TblMchtBeneficiaryInf tmbi = null;
		List<TblMchtBeneficiaryInf> list1 = null;
		for (TblMchtBeneficiaryInf tblMchtBeneficiaryInf : list) {
			if(tblMchtBeneficiaryInf==null){
				tmbi = new TblMchtBeneficiaryInf();
			}else{
				for (TblMchtBeneficiaryInf tblMchtBeneficiaryInf1 : list1) {
					tblMchtBeneficiaryInf1.setBeneficiaryId(tblMchtBeneficiaryInf.getBeneficiaryId());
					
				}
			}
		}*/
		
		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());
//		tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());

		
		//判断如果为修改待审核并且更新了中文简称就同步到终端信息表
		if (TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tmp.getMchtStatus()) 
				&& (inf.getMchtCnAbbr() == null ||!inf.getMchtCnAbbr().equals(tmp.getMchtCnAbbr()))) {
		//需要优化，暂时修养一下，不改了
	    StringBuffer sql0 = new StringBuffer("update tbl_term_inf set term_para = substr(term_para,1,90)||'")
	    	.append(CommonFunction.fillStringByDB(tmp.getMchtCnAbbr(), ' ', 40, true))
	    	.append("'||substr(term_para,(91+40+length(term_para)-lengthb(term_para))) where MCHT_CD = '").append(mchntId).append("'");
		
	    StringBuffer sql1 = new StringBuffer("update tbl_term_inf_tmp set term_para = substr(term_para,1,90)||'")
	    	.append(CommonFunction.fillStringByDB(tmp.getMchtCnAbbr(), ' ', 40, true))
	    	.append("'||substr(term_para,(91+40+length(term_para)-lengthb(term_para))) where MCHT_CD = '").append(mchntId).append("'");
			
		 //同时更新临时表和正式表
		 CommonFunction.getCommQueryDAO().excute(sql0.toString());
		 CommonFunction.getCommQueryDAO().excute(sql1.toString());
		}
		//判断是否为修改状态，修改状态则同步更新事前风控信息
		try{
			if(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tmp.getMchtStatus())){
				StringBuffer sql=new StringBuffer();
				sql.append("update RISK_BEFORE set LICENSE_NO='").append(tmp.getLicenceNo()).append("',ORG_CODE='").append(tmp.getFaxNo()).append("',")
					.append(" IDENTITY='").append(tmp.getIdentityNo()).append("' where mcht_nm='").append(inf.getMchtNm()).append("' and LICENSE_NO='")
					.append(inf.getLicenceNo()).append("' and ORG_CODE='").append(inf.getBankLicenceNo()).append("' and IDENTITY='").append(inf.getIdentityNo()).append("'");
				commQueryDAO.excute(sql.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		// 获得下一状态
		String mchtStatus1 = tmp.getMchtStatus();
		if (mchtStatus1.equals("I") || mchtStatus1.equals("J")) {
			tmp.setMchtStatus("0");
		} else{
			
			if (mchtStatus.equals("I")) {
				tmp.setMchtStatus("0");
			}else if (mchtStatus.equals("J")){
				tmp.setMchtStatus("0");
			}else if (mchtStatus.equals("K")){
				tmp.setMchtStatus("6");
			}else if (mchtStatus.equals("L")){
				tmp.setMchtStatus("8");
			}else{
				tmp.setMchtStatus(StatusUtil.getNextStatus("A."	+ tmp.getMchtStatus()));
			}
			
		}
		//记录最后一次的审核人
		tmp.setAuditOprId(opr.getOprId());										
		//遗留旧数据中mchntInd字段为空，暂时填入商户号
		if(tmp.getMchntInd()==null||"".equals(tmp.getMchntInd())){
			tmp.setMchntInd(tmp.getMchtNo());
		}
		// 复制新的信息
		BeanUtils.copyProperties(tmp, inf);
//		BeanUtils.copyProperties(tmpSettle, infSettle);
		BeanUtils.copyProperties(suppTmp, supp);
		
		/*for (int i = 0; i < amltList.size(); i++) {
			TblMchtBeneficiaryInf tblMchtBeneficiaryInf = null;
			if(amltList.size() > list.size()){
				tblMchtBeneficiaryInf = new TblMchtBeneficiaryInf();
				//左边赋给右边
				BeanUtils.copyProperties(amltList.get(i), tblMchtBeneficiaryInf);
				
				list.add(tblMchtBeneficiaryInf);
			}else if(amltList.size() < list.size()){
				list.remove(i);
			}else if(!amltList.get(i).getBeneficiaryId().equals(list.get(i).getBeneficiaryId())){
				tblMchtBeneficiaryInf = new TblMchtBeneficiaryInf();
				tblMchtBeneficiaryInf.setBeneficiaryId(amltList.get(i).getBeneficiaryId());
				tblMchtBaseInfTmpDAO.beneficiaryUpdate(tblMchtBeneficiaryInf);
			}
			
			BeanUtils.copyProperties(amltList.get(i), list.get(i));
		}*/
		
		//思路是先把正式表里的数据清空,然后再把临时表修改后的数据新增到正式表里
		for (int i = 0; i < list.size(); i++) {
			String beneficiaryDel = "delete from TBL_MCHT_BENEFICIARY_INF where BENEFICIARY_ID = '"+list.get(i).getBeneficiaryId()+"'";
			commQueryDAO.excute(beneficiaryDel);
		}
		for (int i = 0; i < amltList.size(); i++) {
			String beneficiaryDel = "insert into TBL_MCHT_BENEFICIARY_INF(BENEFICIARY_ID,MCHT_NO,BENEFICIARY_NAME,BENEFICIARY_ADDRESS,"
					+ "BENEFICIARY_ID_TYPE,BENEFICIARY_ID_CARD,BENEFICIARY_EXPIRATION) values ('"+amltList.get(i).getBeneficiaryId()+"',"
					+ "'"+amltList.get(i).getMchtNo()+"','"+amltList.get(i).getBeneficiaryName()+"','"+amltList.get(i).getBeneficiaryAddress()+"',"
					+ "'"+amltList.get(i).getBeneficiaryIdType()+"','"+amltList.get(i).getBeneficiaryIdCard()+"','"+amltList.get(i).getBeneficiaryExpiration()+"')";
			commQueryDAO.excute(beneficiaryDel);
		}
		
		//更新费率信息到计费信息表中tbl_his_disc_algo1
		changeDiscAlgo1(tmp.getMchtNo());
//		tmp.setRislLvl("0");
//		inf.setRislLvl("0");
		//更新清算信息到清算表中
		changeSettleInf(tmp.getMchtNo());
		// 更新到数据库
		tblMchtBaseInfTmpDAO.update(tmp);
		tblMchtBaseInfDAO.saveOrUpdate(inf);
		
		/*tblMchtSettleInfTmpDAO.update(tmpSettle);
		tblMchtSettleInfDAO.saveOrUpdate(infSettle);*/
		
		tblMchtSettleInfTmpDAO.saveOrUpdate(suppTmp);
		tblMchtSettleInfTmpDAO.saveOrUpdate(supp);
				
		/*for (TblMchtBeneficiaryInf tblMchtBeneficiaryInf : list) {			
			tblMchtBaseInfTmpDAO.saveOrUpdate(tblMchtBeneficiaryInf);
		}*/
		
		String oprType = "商户维护审核";
		String oprStatus="通过";
		updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
		
		return Constants.SUCCESS_CODE;
	}
	
	private void changeSettleInf(String mchtNo) {
		
//		feeAcctNm;//对私账户名称
//		settleAcctNm;//对公账户名称
//		System.out.println("对私账户名称："+feeAcctNm+"            对公账户名称:"+settleAcctNm);
		String settleDelSql = "delete from TBL_MCHT_SETTLE_INF where MCHT_NO= '"+mchtNo+"'";
	
		commQueryDAO.excute(settleDelSql);
	
		/*String settleInsSql = "insert into TBL_MCHT_SETTLE_INF(MCHT_NO,TERM_ID,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG," +
				"PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT,FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT," +
				"SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM,SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP," +
				"SPE_SETTLE_LV,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED,REC_UPD_TS,REC_CRT_TS,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME," +
				"COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE,DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG,LEGAL_NAM,COMPANY_NAM,DIR_OPEN_BANK , DIR_BANK_PROVINCE , DIR_BANK_CITY , COMP_OPEN_BANK , COMP_BANK_PROVINCE , COMP_BANK_CITY , CORP_OPEN_BANK , CORP_BANK_PROVINCE , CORP_BANK_CITY )" +
				" select MCHT_NO,TERM_ID,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG,PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT," +
				"FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT,SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM," +
				"SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP,SPE_SETTLE_LV,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED," +
				"REC_UPD_TS,REC_CRT_TS,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME,COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE," +
				"DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG,LEGAL_NAM,COMPANY_NAM,DIR_OPEN_BANK , DIR_BANK_PROVINCE , DIR_BANK_CITY , COMP_OPEN_BANK , COMP_BANK_PROVINCE , COMP_BANK_CITY , CORP_OPEN_BANK , CORP_BANK_PROVINCE , CORP_BANK_CITY from TBL_MCHT_SETTLE_INF_TMP where MCHT_NO= '"+mchtNo+"'";*/
		String settleInsSql = "insert into TBL_MCHT_SETTLE_INF(select * from TBL_MCHT_SETTLE_INF_TMP where MCHT_NO= '"+mchtNo+"')";
//		System.out.println("更新清算信息"+settleInsSql);
		commQueryDAO.excute(settleInsSql);
		
	
	}

	public String getNextId(String id,boolean isNext){
		if(isNext){
			int s=100000001;
			s+=Integer.valueOf(id);
			id=String.valueOf(s).substring(1);
		}
		return id;
	}
	
	/** 
	 * 根据商户编号更新商户计费信息
	 * @param mchtNo
	 */
	@SuppressWarnings({ "unchecked" })
	public void changeDiscAlgo1(String mchtNo){
		String sql="select MCHT_NO,TMP_NO,TERM_ID,FEE_TYPE,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM," +
				"DISC_ID,fee_type_old,flag,sa_satute from tbl_his_disc_algo2_tmp where mcht_no='"+mchtNo+"' and sa_satute in ('0','3','4')";
		List<Object[]> algo2List = commQueryDAO.findBySQLQuery(sql);
		String disc_id = GenerateNextId.getALGO1Id();
		boolean isNext = false;
		for(Object[] obj:algo2List){
			TblHisDiscAlgo2Tmp algo2 = new TblHisDiscAlgo2Tmp();
			algo2.setMCHT_NO(String.valueOf(obj[0]));
			algo2.setTmpNo(String.valueOf(obj[1]));
			algo2.setTERM_ID(String.valueOf(obj[2]));
			algo2.setDISC_ID(String.valueOf(obj[11]));
			algo2.setFeeType(String.valueOf(obj[3]));
			
			if("0".equals(obj[13])){//新增的,直接往费率表中插入数据
				List<TblHisDiscAlgo> algoList=getDiscAlgoByDiscId((String)obj[3]);
				for(TblHisDiscAlgo algo:algoList){
					TblHisDiscAlgo1 algo1=new TblHisDiscAlgo1();
					algo1.setDISC_ID(getNextId(disc_id,isNext));
					algo1.setINDEX_NUM(algo.getId().getIndexNum().intValue());
					algo1.setMCHT_NO((String)obj[0]);
					algo1.setTERM_ID((String)obj[2]);
					algo1.setCITY_CODE((String)obj[4]);
					algo1.setTO_BRCH_NO((String)obj[5]);
					algo1.setFK_BRCH_NO((String)obj[6]);
					algo1.setCARD_TYPE((String)obj[7]);
					algo1.setCHANNEL_NO((String)obj[8]);
					algo1.setBUSINESS_TYPE((String)obj[9]);
					algo1.setTXN_NUM((String)obj[10]);
					algo1.setFLAG(algo.getFlag().toString());
					algo1.setMIN_FEE(algo.getMinFee().doubleValue());
					algo1.setMAX_FEE(algo.getMaxFee().doubleValue());
					algo1.setFEE_VALUE(algo.getFeeValue().doubleValue());
					algo1.setFLOOR_AMOUNT(algo.getFloorMount().doubleValue());
					algo1.setSA_SATUTE("2");//正常状态
					algo1.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
					algo1.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
					algo1.setMisc_1((String)obj[11]+(String)obj[3]);
					tblHisDiscAlgo1DAO.saveOrUpdate(algo1);
					isNext=true;
					disc_id=algo1.getDISC_ID();
				}
				//更新tbl_his_disc_algo2_tmp 中字段状态
				String updateSql="update tbl_his_disc_algo2_tmp set flag='1',sa_satute='2' where DISC_ID='"+obj[11]+"'";
				commQueryDAO.excute(updateSql);
			}else{//非新增的,则需要判断本次修改是 删除或是修改 
				if("4".equals(obj[14])){//删除待审核状态
					//String updateStr="update TBL_HIS_DISC_ALGO1 set sa_satute='1' where substr(misc_1,0,6)='"+obj[11]+"'";
					String updateStr="delete from TBL_HIS_DISC_ALGO1 where substr(misc_1,0,6)='"+obj[11]+"'";
					commQueryDAO.excute(updateStr);
					
//					updateStr="update tbl_his_disc_algo2_tmp set flag='1',sa_satute='1' where DISC_ID='"+obj[11]+"'";20120926 修改
					updateStr = "delete from tbl_his_disc_algo2_tmp where DISC_ID='" + obj[11] + "'";//20120926 修改成del操作，否则不能添加之前删除的商户费率，会提示重复
					commQueryDAO.excute(updateStr);
				}else if("3".equals(obj[14])){//修改状态  不考虑计费信息被人工直接从数据库删除的情况
					//获取计费算法列表
					List<TblHisDiscAlgo> algoList=getDiscAlgoByDiscId((String)obj[3]);//计费算法不能为空
					//获取当前正在生效的计费信息
					List<TblHisDiscAlgo1> algolList=getTblHisDiscAlgo1ByMisc_1((String)obj[11]);
					if(algoList!=null&&algoList.size()==1){//计费算法只有一档的时候,直接更新当前生效的计费信息（不考虑计费信息被人工直接从数据库删除的情况）
						String uptStr="update TBL_HIS_DISC_ALGO1 set MIN_FEE="+algoList.get(0).getMinFee().doubleValue()+" , max_fee="+algoList.get(0).getMaxFee().doubleValue()
							+" , floor_amount="+algoList.get(0).getFloorMount().doubleValue()+" , flag="+algoList.get(0).getFlag()
							+" ,fee_value="+algoList.get(0).getFeeValue().doubleValue()+",misc_1='"+obj[11]+obj[3]+"'  where substr(misc_1,0,6)='"+obj[11]+"'";
						commQueryDAO.excute(uptStr);
					}else {
						if(algoList.size()>algolList.size()){//更新已有的计费信息
							for(int i=0;i<algolList.size();i++){
								TblHisDiscAlgo1 algo1=algolList.get(i);
								algo1.setMIN_FEE(algoList.get(i).getMinFee().doubleValue());
								algo1.setMAX_FEE(algoList.get(i).getMaxFee().doubleValue());
								algo1.setFLAG(algoList.get(i).getFlag().toString());
								algo1.setFLOOR_AMOUNT(algoList.get(i).getFloorMount().doubleValue());
								algo1.setFEE_VALUE(algoList.get(i).getFeeValue().doubleValue());
								algo1.setINDEX_NUM(algoList.get(i).getId().getIndexNum());
								algo1.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
								algo1.setMisc_1((String)obj[11]+(String)obj[3]);
								tblHisDiscAlgo1DAO.saveOrUpdate(algo1);
							}
							//String disc_id=GenerateNextId.getALGO1Id();
							//boolean isNext=false;
							for(int i=algolList.size();i<algoList.size();i++){//插入没有的计费信息
								TblHisDiscAlgo1 algo1=new TblHisDiscAlgo1();
								algo1.setDISC_ID(getNextId(disc_id,isNext));
								algo1.setINDEX_NUM(algoList.get(i).getId().getIndexNum());
								algo1.setMCHT_NO((String)obj[0]);
								algo1.setTERM_ID((String)obj[2]);
								algo1.setCITY_CODE((String)obj[4]);
								algo1.setTO_BRCH_NO((String)obj[5]);
								algo1.setFK_BRCH_NO((String)obj[6]);
								algo1.setCARD_TYPE((String)obj[7]);
								algo1.setCHANNEL_NO((String)obj[8]);
								algo1.setBUSINESS_TYPE((String)obj[9]);
								algo1.setTXN_NUM((String)obj[10]);
								algo1.setFLAG(algoList.get(i).getFlag().toString());
								algo1.setMIN_FEE(algoList.get(i).getMinFee().doubleValue());
								algo1.setMAX_FEE(algoList.get(i).getMaxFee().doubleValue());
								algo1.setFEE_VALUE(algoList.get(i).getFeeValue().doubleValue());
								algo1.setFLOOR_AMOUNT(algoList.get(i).getFloorMount().doubleValue());
								algo1.setMisc_1((String)obj[11]+(String)obj[3]);
								algo1.setSA_SATUTE("2");//正常状态
								algo1.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
								algo1.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
								tblHisDiscAlgo1DAO.saveOrUpdate(algo1);
								isNext=true;
								disc_id=algo1.getDISC_ID();
							}
						}else{
							for(int i=0;i<algoList.size();i++){//更新已有的
								TblHisDiscAlgo1 algo1=algolList.get(i);
								algo1.setMIN_FEE(algoList.get(i).getMinFee().doubleValue());
								algo1.setMAX_FEE(algoList.get(i).getMaxFee().doubleValue());
								algo1.setFLAG(algoList.get(i).getFlag().toString());
								algo1.setFLOOR_AMOUNT(algoList.get(i).getFloorMount().doubleValue());
								algo1.setFEE_VALUE(algoList.get(i).getFeeValue().doubleValue());
								algo1.setINDEX_NUM(algoList.get(i).getId().getIndexNum());
								algo1.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
								algo1.setMisc_1((String)obj[11]+(String)obj[3]);
								tblHisDiscAlgo1DAO.saveOrUpdate(algo1);
							}
							for(int i=algoList.size();i<algolList.size();i++){//删除多余的
								TblHisDiscAlgo1 algo1=algolList.get(i);
								tblHisDiscAlgo1DAO.delete(algo1);
							}
						}
					}
					//更新tbl_his_disc_algo2_tmp 中字段状态
					String updateSql="update tbl_his_disc_algo2_tmp set flag='1',sa_satute='2',fee_type_old='"+obj[3]+"' where DISC_ID='"+obj[11]+"'";
					commQueryDAO.excute(updateSql);
				}
			}
		}
	}
	
	//2-比例，1-笔数
	@SuppressWarnings("unchecked")
	public List<TblHisDiscAlgo1> getTblHisDiscAlgo1ByMisc_1(String misc_1){
		List <TblHisDiscAlgo1> algo1List=new ArrayList<TblHisDiscAlgo1>();
		String sql="select MCHT_NO,TERM_ID,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE," +
				"TXN_NUM,DISC_ID,INDEX_NUM,MIN_FEE,max_fee,floor_amount,flag,fee_value from TBL_HIS_DISC_ALGO1 where substr(misc_1,0,6)='"+misc_1+"' and sa_satute='2' order by INDEX_NUM";
		List<Object[]> list=commQueryDAO.findBySQLQuery(sql);
		for(Object[]obj:list){
			TblHisDiscAlgo1 algo1=new TblHisDiscAlgo1();
			algo1.setMCHT_NO((String)obj[0]);
			algo1.setTERM_ID((String)obj[1]);
			algo1.setCITY_CODE((String)obj[2]);
			algo1.setTO_BRCH_NO((String)obj[3]);
			algo1.setFK_BRCH_NO((String)obj[4]);
			algo1.setCARD_TYPE((String)obj[5]);
			algo1.setCHANNEL_NO((String)obj[6]);
			algo1.setBUSINESS_TYPE((String)obj[7]);
			algo1.setTXN_NUM((String)obj[8]);
			algo1.setDISC_ID((String)obj[9]);
//			algo1.setINDEX_NUM((Integer)obj[10]);20120803修改ClassCastException
			algo1.setINDEX_NUM(Integer.parseInt(obj[10].toString()));
//			algo1.setMIN_FEE((Double)obj[11]);20120803修改ClassCastException
			algo1.setMIN_FEE(Double.parseDouble(obj[11].toString()));
//			algo1.setMAX_FEE((Double)obj[12]);20120803修改ClassCastException
			algo1.setMAX_FEE(Double.parseDouble(obj[12].toString()));
//			algo1.setFLOOR_AMOUNT((Double)obj[13]);20120803修改ClassCastException
			algo1.setFLOOR_AMOUNT(Double.parseDouble(obj[13].toString()));
			algo1.setFLAG((String)obj[14]);
//			algo1.setFEE_VALUE((Double)obj[15]);20120803修改ClassCastException
			algo1.setFEE_VALUE(Double.parseDouble(obj[15].toString()));
			algo1List.add(algo1);
		}
		return algo1List;
	}
	
	/**
	 * 根据计费代码获取计费算法信息
	 * @param discId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblHisDiscAlgo> getDiscAlgoByDiscId(String discId){
		String sql="select disc_id ,min_fee,max_fee,floor_amount,flag,fee_value,INDEX_NUM  from TBL_HIS_DISC_ALGO where disc_id='"+discId+"' order by INDEX_NUM";
		List<Object[]> list=commQueryDAO.findBySQLQuery(sql);
		List<TblHisDiscAlgo> algoList=new ArrayList<TblHisDiscAlgo>();
		for(Object[] obj:list){
			TblHisDiscAlgo algo=new TblHisDiscAlgo();
			algo.setMinFee((BigDecimal)obj[1]);
			algo.setMaxFee((BigDecimal)obj[2]);
			algo.setFloorMount((BigDecimal)obj[3]);
			algo.setFlag(((BigDecimal)obj[4]).intValue());
			algo.setFeeValue((BigDecimal)obj[5]);
			algo.setId(new TblHisDiscAlgoPK(String.valueOf(obj[0]),((BigDecimal)obj[6]).intValue()));
			algoList.add(algo);
		}
		return algoList;
	}
	
	/**
	 * 根据临时表计费代码获取计费算法信息
	 * @param discId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblHisDiscAlgoTmp> getDiscAlgoTmpByDiscId(String discId){
		String sql="select DISC_ID,min_fee,max_fee,floor_amount,flag,fee_value,INDEX_NUM  from tbl_his_disc_algo_tmp where DISC_ID='"+discId+"' order by INDEX_NUM";
		List<Object[]> list=commQueryDAO.findBySQLQuery(sql);
		List<TblHisDiscAlgoTmp> algoList=new ArrayList<TblHisDiscAlgoTmp>();
		for(Object[] obj:list){
			TblHisDiscAlgoTmp algo=new TblHisDiscAlgoTmp();
			algo.setMinFee((BigDecimal)obj[1]);
			algo.setMaxFee((BigDecimal)obj[2]);
			algo.setFloorMount((BigDecimal)obj[3]);
			algo.setFlag(((BigDecimal)obj[4]).intValue());
			algo.setFeeValue((BigDecimal)obj[5]);
			algo.setId(new TblHisDiscAlgoPK(String.valueOf(obj[0]),((BigDecimal)obj[6]).intValue()));
			algoList.add(algo);
		}
		return algoList;
	}
	
	/*
	 * 商户审核退回
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#back(java.lang.String,
	 * java.lang.String)
	 */
	public String back(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		/*TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);

		// 记录退回信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(opr.getOprBrhId());
		refuse.setOprId(opr.getOprId());

		// 获得退回信息
		refuse.setRefuseType(StatusUtil.getNextStatus("BM."	+ tmp.getMchtStatus()));
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNextStatus("B." + tmp.getMchtStatus()));

		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());

		// 更新到数据库
		tblMchtBaseInfTmpDAO.update(tmp);
		tblMchntRefuseDAO.save(refuse);*/

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核拒绝
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#refuse(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String refuse(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException {
		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		TblMchtSettleInfPK id = new TblMchtSettleInfPK();
		id.setMchtNo(mchntId);
		id.setTermId(CommonFunction.fillString("*",' ',8,true));//默认全部终端
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(id);
		TblMchtSupp1Tmp supp1Tmp = tblMchtSettleInfTmpDAO.getSupp1Tmp(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);

		tmp.setAuditOprId(opr.getOprId());
		
		//遗留旧数据中mchntInd字段为空，暂时填入商户号
		if(tmp.getMchntInd()==null||"".equals(tmp.getMchntInd())){
			tmp.setMchntInd(tmp.getMchtNo());
		}
		
		// 记录拒绝信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(opr.getOprBrhId());
		refuse.setOprId(opr.getOprId());

		// 获得拒绝信息
		refuse.setRefuseType("新增审核拒绝");

		//重置审批流程号
		tmp.setReserved("");

		tmp.setMchtStatus(TblMchntInfoConstants.MCHT_ST_BLACK);//20120906，20121106
		//拒绝商户加入黑名单（待开发……）
		TblCtlMchtInf tblCtlMchtInf = new TblCtlMchtInf();
//		tblCtlMchtInf.setId(mchntId);
		tblCtlMchtInf.setDatePk(CommonFunction.getCurrentDateTime());
		
		tblCtlMchtInf.setSaMerNo("  ");
		
		tblCtlMchtInf.setSaMerChName(tmp.getMchtNm().trim());
		if(StringUtil.isNull(tmp.getEngName())){
			tblCtlMchtInf.setSaMerEnName(" ");
		}else{
			tblCtlMchtInf.setSaMerEnName(tmp.getEngName());
		}

			tblCtlMchtInf.setSaAction("0");
		
		
			tblCtlMchtInf.setSaLimitAmt("0.0");
		
		if(StringUtil.isNull(tmp.getBankNo())){
			tblCtlMchtInf.setSaZoneNo(" ");
		}else{
			tblCtlMchtInf.setSaZoneNo(tmp.getBankNo());
		}
		tblCtlMchtInf.setSaAdmiBranNo(Constants.DEFAULT);
		
			tblCtlMchtInf.setSaConnOr(tmp.getContact().trim());

		if(StringUtil.isNull(tmp.getCommTel())){
			tblCtlMchtInf.setSaConnTel(" ");
		}else{
			tblCtlMchtInf.setSaConnTel(tmp.getCommTel());
		}
		
		tblCtlMchtInf.setLicenceNo(tmp.getLicenceNo());
		tblCtlMchtInf.setBankLicenceNo(tmp.getBankLicenceNo());
		tblCtlMchtInf.setIdentityNo(tmp.getIdentityNo());
		tblCtlMchtInf.setManagerTel(tmp.getManagerTel());
		tblCtlMchtInf.setAddType("1");
		
		tblCtlMchtInf.setSaInitZoneNo(opr.getOprBrhId());
		tblCtlMchtInf.setSaInitOprId(opr.getOprId());
		tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());
		tblCtlMchtInf.setSaState("2");
		tblCtlMchtInfDAO.save(tblCtlMchtInf);
		
		//商户黑名单审核记录表
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(tblCtlMchtInf.getSaMerChName().trim());  
		riskRefuse.setOprId(opr.getOprId());  //审核人
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("2"); //标志
		riskRefuse.setParam5(tblCtlMchtInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlMchtInf.getAddType());
		
		tblRiskRefuseDAO.save(riskRefuse);
		
		tblMchtBaseInfTmpDAO.update(tmp);//20120906
			
			
		tblMchntRefuseDAO.save(refuse);

		String oprType="商户新增审核";
		String oprStatus = "拒绝";
		updateMchntLogs(tmp, oprType, oprStatus, refuseInfo);
		
		
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 保存集团商户信息
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#saveGroup()
	 */
	public String saveGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf) throws IllegalAccessException, InvocationTargetException {

		tblGroupMchtInfDAO.save(inf);

		// 缺表暂时屏蔽
		// tblMchtAcctInfDAO.save(acctinf);

		return Constants.SUCCESS_CODE;
	}

	public TblMchtBaseInf getMccByMchntId(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		if(StringUtil.isNull(mchntId)){
			return null;
		}
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchntId);
		if (null == inf) {
			return null;
		} else {
			return inf;
		}
	}

	/*
	 * 获取集团商户基本信息
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#getGroupInf(java.lang.String)
	 */
	public TblGroupMchtInf getGroupInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		TblGroupMchtInf inf = tblGroupMchtInfDAO.get(StringUtil.fillValue(mchntId, 8, ' '));

		return inf;
	}

	/*
	 * 更新集团商户
	 * 
	 * @seecom.huateng.bo.impl.mchnt.TblMchntService#updateGroup(as.huateng.po.
	 * management.mer.TblGroupMchtInf,
	 * as.huateng.po.management.mer.TblMchtAcctInf)
	 */
	public String updateGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf)
			throws IllegalAccessException, InvocationTargetException {
		try {
			inf.setGroupMchtCd(CommonFunction.fillString(inf.getGroupMchtCd(),' ', 8, true));

			tblGroupMchtInfDAO.update(inf);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	/*
	 * GET商户临时基本信息
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#getBaseInfTmp(java.lang.String)
	 */
	public TblMchtBaseInfTmp getBaseInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtBaseInfTmpDAO.get(mchntId);
	}

	public TblMchtSettleInfTmp getSettleInfTmp(TblMchtSettleInfPK id)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtSettleInfTmpDAO.get(id);
	}
	
	public TblMchtBaseInf getBaseInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtBaseInfDAO.get(mchntId);
	}

	public TblMchtSettleInf getSettleInf(TblMchtSettleInfPK id)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtSettleInfDAO.get(id);
	}

	public String updateBaseInfTmp(TblMchtBaseInfTmp inf)
			throws IllegalAccessException, InvocationTargetException {
		//遗留旧数据中mchntInd字段为空，暂时填入商户号
				if(inf.getMchntInd()==null||"".equals(inf.getMchntInd())){
					inf.setMchntInd(inf.getMchtNo());
				}
		
		tblMchtBaseInfTmpDAO.update(inf);
		
		String oprType = "商户信息维护";
		String oprStatus = "";
		if(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK.equals(inf.getMchtStatus())){
			oprStatus="冻结";
		}
		if(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK.equals(inf.getMchtStatus())){
			oprStatus="恢复";
		}
		if(TblMchntInfoConstants.MCHNT_ST_DEL_UNCK.equals(inf.getMchtStatus())){
			oprStatus="注销";
		}
		String oprInfo = inf.getPartNum();
		updateMchntLogs(inf,oprType,oprStatus,oprInfo);
		
		return Constants.SUCCESS_CODE;
	}

	public TblMchtSupp1Tmp getMchtSupp1Tmp(String mchtId) {
		return tblMchtSettleInfTmpDAO.getSupp1Tmp(mchtId);
	}
	
	/**
	 * 临时表
	 * 根据商户编号查询受益人信息
	 */
	public List<TblMchtBeneficiaryInfTmp> getMchtBeneficiaryTmp(String mchtNo){
		return tblMchtBaseInfTmpDAO.getBeneficiaryTmp(mchtNo);
	}
	
	/**
	 * 正式表
	 * 根据商户编号查询受益人信息
	 */
	public List<TblMchtBeneficiaryInf> getMchtBeneficiary(String mchtNo) {
		// TODO Auto-generated method stub
		return tblMchtBaseInfTmpDAO.getBeneficiary(mchtNo);
	}
	
	/**
	 * 正式表
	 * 新增或修改
	 */
	public String saveOrUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf){
		tblMchtBaseInfTmpDAO.saveOrUpdate(tblMchtBeneficiaryInf);
		return "00";
	}
	
	/**
	 * 临时表
	 * 新增或修改
	 */
	public String saveOrUpdate(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp){
		tblMchtBaseInfTmpDAO.saveOrUpdate(tblMchtBeneficiaryInfTmp);
		return "00";
	}
	
	/**
	 * 修改正式表id
	 */
	public String beneficiaryUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf){
		tblMchtBaseInfTmpDAO.beneficiaryUpdate(tblMchtBeneficiaryInf);
		return "00";
	}
	
	/**
	 * 修改临时表id
	 */
	public String beneficiaryUpdateTmp(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp){
		tblMchtBaseInfTmpDAO.beneficiaryUpdateTmp(tblMchtBeneficiaryInfTmp);
		return "00";
	}
	
	/**
	 * 根据id查询数据
	 * @return
	 */
	public TblMchtBeneficiaryInf getBeneficiaryInf(String beneficiaryId){
		return tblMchtBaseInfTmpDAO.getBeneficiaryInf(beneficiaryId);
	}
	
	/**
	 * 根据mchtId查询数据
	 */
	public TblMchtBaseBusiRange getBaseBusiRange(String mchtId) {
		// TODO Auto-generated method stub
		return tblMchtBaseInfTmpDAO.getBaseBusiRange(mchtId);
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String beneficiaryDel(String beneficiaryId){
		tblMchtBaseInfTmpDAO.beneficiaryDel(beneficiaryId);
		return "00";
	}
	
	/**
	 * 新增
	 * @param tblMchtBeneficiaryInfTmp
	 * @return
	 */
	public String beneficiaryAdd(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp){
		tblMchtBaseInfTmpDAO.beneficiaryAdd(tblMchtBeneficiaryInfTmp);
		return "00";
	}
	
	/**
	 * 新增
	 * 临时表
	 * @param tblMchtBeneficiaryInf
	 * @return
	 */
	public String beneficiaryAdd1(TblMchtBeneficiaryInf tblMchtBeneficiaryInf){
		tblMchtBaseInfTmpDAO.beneficiaryAdd1(tblMchtBeneficiaryInf);
		return "00";
	}
	
	public TblMchtSupp1 getMchtSupp1(String mchtId){
		return tblMchtSettleInfTmpDAO.getSupp1(mchtId);
	}

	public String getMchtNo(String mchtNo) {
		// TODO Auto-generated method stub
		String no = "0001";
		String sql = "SELECT substr(MCHT_NO,12,4) FROM TBL_MCHT_BASE_INF_TMP WHERE substr(MCHT_NO,1,11) = '"+mchtNo+"' ORDER BY MCHT_NO";
		List<String> mchtNos = commQueryDAO.findBySQLQuery(sql);
		if (mchtNos != null && mchtNos.size() > 0 && mchtNos.get(0) != null) {
			no = GenerateNextId.genNo(mchtNos, "", 4);
		}
		return no;
	}
	
	/***
	    * 新增商户审核通过
	    */
		public String acceptAdd(String mchntId, String mchntInd,String oprInfo,
				CstMchtFeeInfTmp cstMchtFeeInfTmpDebit,
				CstMchtFeeInfTmp cstMchtFeeInfTmpCredit,String isRoute, String isWhite) throws IllegalAccessException, InvocationTargetException {
			TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
			TblMchtSettleInfPK id = new TblMchtSettleInfPK();
			id.setMchtNo(mchntId);
			id.setTermId(CommonFunction.fillString("*",' ',8,true));
			TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(id);
			TblMchtSupp1Tmp suppTmp=tblMchtSettleInfTmpDAO.getSupp1Tmp(mchntId);//商户补充信息
			List<TblMchtBeneficiaryInfTmp> amltList = tblMchtBaseInfTmpDAO.getBeneficiaryTmp(mchntId);//受益人信息
			if (null == tmp || null == tmpSettle) {
				return "没有找到商户的临时信息，请重试";
			}
			if(suppTmp==null){
				suppTmp=new TblMchtSupp1Tmp();
				suppTmp.setMchtNo(mchntId);
			}
		
			
				TblMchtBaseInf inf = new TblMchtBaseInf();
			
			
				TblMchtSettleInf infSettle = new TblMchtSettleInf();
			
			
				TblMchtSupp1 supp = new TblMchtSupp1();
			
				
				TblMchtBeneficiaryInf list = null;
				
			
			tmp.setRouteFlag(isRoute);

			// 更新时间和柜员
			tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
			tmp.setUpdOprId(opr.getOprId());
			tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());


			tmp.setReserved("");
			//记录最后一次的审核人
			tmp.setAuditOprId(opr.getOprId());
				
				// 获得下一状态
			String mchtStatus = tmp.getMchtStatus();
			if (mchtStatus.equals("I") || mchtStatus.equals("J")) {
				tmp.setMchtStatus("0");
			} else {
				tmp.setMchtStatus(StatusUtil.getNextStatus("A."	+ tmp.getMchtStatus()));
			}
			
			//遗留旧数据中mchntInd字段为空，暂时填入商户号
			if(tmp.getMchntInd()==null||"".equals(tmp.getMchntInd())){
				tmp.setMchntInd(tmp.getMchtNo());
			}
			inf.setRislLvl("0");
			// 复制新的信息
			BeanUtils.copyProperties(tmp, inf);
			BeanUtils.copyProperties(tmpSettle, infSettle);
			BeanUtils.copyProperties(suppTmp, supp);
			
			if(list == null){
				for (TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp : amltList) {
					list = new TblMchtBeneficiaryInf();
					//左边赋给右边
					BeanUtils.copyProperties(tblMchtBeneficiaryInfTmp, list);
					tblMchtBaseInfTmpDAO.beneficiaryAdd1(list);
				}
			}
			
			//更新费率信息到计费信息表中tbl_his_disc_algo1
			changeDiscAlgo1(tmp.getMchtNo());
				// 更新到数据库
				tblMchtBaseInfTmpDAO.update(tmp);
				tblMchtBaseInfDAO.saveOrUpdate(inf);
				
				tblMchtSettleInfTmpDAO.update(tmpSettle);
				tblMchtSettleInfDAO.saveOrUpdate(infSettle);
				
				tblMchtSettleInfTmpDAO.saveOrUpdate(suppTmp);
				tblMchtSettleInfTmpDAO.saveOrUpdate(supp);
				
				//限额表
				if(cstMchtFeeInfTmpDebit.getDaySingle()!=null||cstMchtFeeInfTmpDebit.getDayAmt()!=null
						||cstMchtFeeInfTmpDebit.getMonAmt()!=null){
					CstMchtFeeInf cstMchtFeeInfDebit = new CstMchtFeeInf();
					BeanUtils.copyProperties(cstMchtFeeInfTmpDebit,cstMchtFeeInfDebit);
					cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfDebit);
					cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfTmpDebit);
				}
				
				CstMchtFeeInf cstMchtFeeInfCredit = new CstMchtFeeInf();
				
				BeanUtils.copyProperties(cstMchtFeeInfTmpCredit,cstMchtFeeInfCredit);
				
				cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfCredit);
				
				cstMchtFeeInfDAO.saveOrUpdate(cstMchtFeeInfTmpCredit);
				
				if("1".equals(isWhite)){
					//加入白名单
					TblWhiteListTmp tblWhiteListTmp = new TblWhiteListTmp();
					tblWhiteListTmp.setUuid(StringUtil.getUUID());
					tblWhiteListTmp.setAppRemark(oprInfo.trim());
					tblWhiteListTmp.setMchtNo(mchntId);
					tblWhiteListTmp.setValidity("30");
					tblWhiteListTmp.setBeginDt(CommonFunction.getCurrentDate());
					tblWhiteListTmp.setInsDt(CommonFunction.getCurrentDate());
					tblWhiteListTmp.setInsOpr(opr.getOprId());
					tblWhiteListTmp.setUpdOpr("");
					tblWhiteListTmp.setState("0");
					tblWhiteListTmp.setAddType("1");
					tblWhiteListTmpDAO.save(tblWhiteListTmp);
				}
				String oprType = "商户新增审核";
				String oprStatus="通过";
				updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
				
//			}
			return Constants.SUCCESS_CODE;
		}

		/*
		 * 商户审核退回
		 * 
		 * @see com.huateng.bo.impl.mchnt.TblMchntService#refuse(java.lang.String,
		 * java.lang.String)
		 */
		@SuppressWarnings("unchecked")
		public String back2(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException {
			TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
			/*TblMchtSettleInfPK id = new TblMchtSettleInfPK();
			id.setMchtNo(mchntId);
			id.setTermId("*       ");//默认全部终端
			TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(id);*/
			TblMchtSupp1Tmp supp1Tmp = tblMchtSettleInfTmpDAO.getSupp1Tmp(mchntId);
			List<TblMchtBeneficiaryInfTmp> tmbiTmp = tblMchtBaseInfTmpDAO.getBeneficiaryTmp(mchntId);
			
			if (null == tmp /*|| null == tmpSettle*/) {
				return "没有找到商户的临时信息，请重试";
			}
			Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);

			
			
			// 记录拒绝信息
			TblMchntRefuse refuse = new TblMchntRefuse();
			TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
			refuse.setId(tblMchntRefusePK);
			refuse.setRefuseInfo(refuseInfo);
			refuse.setBrhId(opr.getOprBrhId());
			refuse.setOprId(opr.getOprId());

			// 获得拒绝信息
			refuse.setRefuseType(StatusUtil.getNextStatus("RM."	+ tmp.getMchtStatus()));
			//获取审批流程号
			String wfNo = tmp.getReserved();
			//重置审批流程号
			tmp.setReserved("");
			
			String oprType="";
			String oprStatus="";

			// 分别处理新增拒绝和修改拒绝
			if (TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tmp.getMchtStatus())) {
				
				tmp.setAuditOprId(opr.getOprId());
				
				//遗留旧数据中mchntInd字段为空，暂时填入商户号
				if(tmp.getMchntInd()==null||"".equals(tmp.getMchntInd())){
					tmp.setMchntInd(tmp.getMchtNo());
				}
				
				tmp.setMchtStatus(TblMchntInfoConstants.MCHT_ST_NEW_UNCK_REF);//20120906，20121106
				tblMchtBaseInfTmpDAO.update(tmp);//20120906				
				
				tblMchntRefuseDAO.save(refuse);
				oprType = "商户新增审核";
				oprStatus="退回";
			} else {
				// 取得原始信息
				TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
//				TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle.getId());
				if(supp1Tmp!=null){
					TblMchtSupp1 supp=tblMchtSettleInfTmpDAO.getSupp1(mchntId);
					if(supp==null)supp=new TblMchtSupp1();
					BeanUtils.copyProperties(supp,supp1Tmp);
					tblMchtSettleInfTmpDAO.saveOrUpdate(supp1Tmp);
				}
				
				
				//获取正式表信息用来退回拒绝
				List<TblMchtBeneficiaryInf> tmbi = tblMchtBaseInfTmpDAO.getBeneficiary(mchntId);
				if(tmbiTmp!=null){
					/*for (int i = 0; i < tmbi.size(); i++) {
						TblMchtBeneficiaryInfTmp tmbiTmp1 = null;
						if(tmbiTmp.size() > tmbi.size()){
							tmbiTmp.remove(i);
							tblMchtBaseInfTmpDAO.beneficiaryDel(tmbiTmp.get(i).getBeneficiaryId());
						}else if(tmbiTmp.size() < tmbi.size()){
							tmbiTmp1 = new TblMchtBeneficiaryInfTmp();
							BeanUtils.copyProperties(tmbi.get(i), tmbiTmp1);
							tmbiTmp.add(tmbiTmp1);
							tblMchtBaseInfTmpDAO.beneficiaryAdd(tmbiTmp1);
						}else if(tmbi.get(i).getBeneficiaryId() != tmbiTmp.get(i).getBeneficiaryId()){
							tmbiTmp1 = new TblMchtBeneficiaryInfTmp();
							tmbiTmp1.setBeneficiaryId(tmbi.get(i).getBeneficiaryId());
							tblMchtBaseInfTmpDAO.beneficiaryUpdateTmp(tmbiTmp1);
						}
						BeanUtils.copyProperties(tmbi.get(i), tmbiTmp.get(i));
					}
					
					for (TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp : tmbiTmp) {
						tblMchtBaseInfTmpDAO.saveOrUpdate(tblMchtBeneficiaryInfTmp);
					}*/
					for (int i = 0; i < tmbiTmp.size(); i++) {
						String beneficiaryDel = "delete from TBL_MCHT_BENEFICIARY_INF_TMP where BENEFICIARY_ID = '"+tmbiTmp.get(i).getBeneficiaryId()+"'";
						commQueryDAO.excute(beneficiaryDel);
					}
					for (int i = 0; i < tmbi.size(); i++) {
						String beneficiaryDel = "insert into TBL_MCHT_BENEFICIARY_INF_TMP(BENEFICIARY_ID,MCHT_NO,BENEFICIARY_NAME,"
								+ "BENEFICIARY_ADDRESS,BENEFICIARY_ID_TYPE,BENEFICIARY_ID_CARD,BENEFICIARY_EXPIRATION) values ('"+tmbi.get(i).getBeneficiaryId()+"'"
								+ ",'"+tmbi.get(i).getMchtNo()+"','"+tmbi.get(i).getBeneficiaryName()+"','"+tmbi.get(i).getBeneficiaryAddress()+"','"
								+ ""+tmbi.get(i).getBeneficiaryIdType()+"','"+tmbi.get(i).getBeneficiaryIdCard()+"','"+tmbi.get(i).getBeneficiaryExpiration()+"')";
						commQueryDAO.excute(beneficiaryDel);
					}
				}
				
				
				if (null == inf) {
					return "没有找到商户的正式信息，请重试";
				} else {
					// 更新时间和柜员
					inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					inf.setUpdOprId(opr.getOprId());
					
					inf.setAuditOprId(opr.getOprId());
					
					//遗留旧数据中mchntInd字段为空，暂时填入商户号
					if(inf.getMchntInd()==null||"".equals(inf.getMchntInd())){
						inf.setMchntInd(inf.getMchtNo());
					}
					
					// 复制新的信息
					BeanUtils.copyProperties(inf, tmp);
//					BeanUtils.copyProperties(infSettle, tmpSettle);
					// 更新到数据库
					tblMchtBaseInfTmpDAO.update(tmp);
					tblMchtBaseInfDAO.update(inf);
//					tblMchtSettleInfTmpDAO.update(tmpSettle);
//					tblMchtSettleInfDAO.update(infSettle);
					tblMchntRefuseDAO.save(refuse);					
					//处理清算信息
					String settleDelSql = "delete from TBL_MCHT_SETTLE_INF_TMP where MCHT_NO= '"+mchntId+"'";
					commQueryDAO.excute(settleDelSql);
					/*String settleInsSql = "insert into TBL_MCHT_SETTLE_INF_TMP(MCHT_NO,TERM_ID,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG," +
					"PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT,FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT," +
					"SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM,SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP," +
					"SPE_SETTLE_LV,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED,REC_UPD_TS,REC_CRT_TS,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME," +
					"COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE,DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG)" +
					" select MCHT_NO,TERM_ID,SETTLE_TYPE,RATE_FLAG,SETTLE_CHN,BAT_TIME,AUTO_STL_FLG,PART_NUM,FEE_TYPE,FEE_FIXED,FEE_MAX_AMT," +
					"FEE_MIN_AMT,FEE_RATE,FEE_DIV_1,FEE_DIV_2,FEE_DIV_3,SETTLE_MODE,FEE_CYCLE,SETTLE_RPT,SETTLE_BANK_NO,SETTLE_BANK_NM,SETTLE_ACCT_NM," +
					"SETTLE_ACCT,FEE_ACCT_NM,FEE_ACCT,GROUP_FLAG,OPEN_STLNO,CHANGE_STLNO,SPE_SETTLE_TP,SPE_SETTLE_LV,SPE_SETTLE_DS,FEE_BACK_FLG,RESERVED," +
					"REC_UPD_TS,REC_CRT_TS,CURR_ACCOUNT,BANK_ACCOUNT_CODE,CORP_BANK_NAME,COMP_ACCOUNT_BANK_CODE,COMP_ACCOUNT_BANK_NAME,DIR_BANK_CODE," +
					"DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG from TBL_MCHT_SETTLE_INF where MCHT_NO= '"+mchntId+"'";*/
					String settleInsSql = "insert into TBL_MCHT_SETTLE_INF_TMP(select * from TBL_MCHT_SETTLE_INF where MCHT_NO= '"+mchntId+"')";
					commQueryDAO.excute(settleInsSql);
				}
				
				oprType="商户维护审核";
				oprStatus="拒绝";
			}		
			//处理费率信息
			String sql = "select MCHT_NO,TMP_NO,TERM_ID,FEE_TYPE,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM," +
					"DISC_ID,fee_type_old,flag,sa_satute from tbl_his_disc_algo2_tmp where mcht_no='"+mchntId+"' and sa_satute in ('0','3','4')";
			List<Object[]> algo2List=	commQueryDAO.findBySQLQuery(sql);
			for(Object[] obj:algo2List){
				if("3".equals(obj[14]) || "4".equals(obj[14])){
					String uptStr="update tbl_his_disc_algo2_tmp set FEE_TYPE='"+obj[12]+"' ,sa_satute='2' where DISC_ID='"+obj[11]+"' ";
					commQueryDAO.excute(uptStr);
				}/*else if("4".equals(obj[14])){
					String uptStr="update tbl_his_disc_algo2_tmp set sa_satute='2' where DISC_ID='"+obj[11]+"' ";
					commQueryDAO.excute(uptStr);
				}*/
			}
			
			updateMchntLogs(tmp, oprType, oprStatus, refuseInfo);
			
			return Constants.SUCCESS_CODE;
		}

		public String saveSettleInfTmp(TblMchtSettleInfTmp settleInfTmp)
				throws IllegalAccessException, InvocationTargetException {
			tblMchtSettleInfTmpDAO.save(settleInfTmp);
			return Constants.SUCCESS_CODE;
		}

		public String updateSettleInfTmp(TblMchtSettleInfTmp settleInf)
				throws IllegalAccessException, InvocationTargetException {
			tblMchtSettleInfTmpDAO.update(settleInf);
			return Constants.SUCCESS_CODE;
		}

		public String deleteSettleInfTmp(TblMchtSettleInfTmp settleInf)
				throws IllegalAccessException, InvocationTargetException {
			tblMchtSettleInfTmpDAO.delete(settleInf);
			return Constants.SUCCESS_CODE;
		}

		public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
				TblMchtSettleInfTmp tblMchtSettleInfTmp,
				TblMchtSupp1Tmp supp1Tmp) {

			tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
			tblMchtSettleInfTmpDAO.update(tblMchtSettleInfTmp);
			tblMchtSettleInfTmpDAO.saveOrUpdate(supp1Tmp);
			
			String oprType = "新增商户信息";
			String oprStatus = "恢复";
			String oprInfo = "";
			updateMchntLogs(tblMchtBaseInfTmp,oprType,oprStatus,oprInfo);
			return Constants.SUCCESS_CODE;
		}

		public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
				TblMchtSettleInfTmp tblMchtSettleInfTmp,
				TblMchtSupp1Tmp supp1Tmp, String mchtNoInput) {
			TblMchtBaseInfTmp baseInf = new TblMchtBaseInfTmp();
			BeanUtils.copyProperties(tblMchtBaseInfTmp,baseInf);
			baseInf.setMchtNo(mchtNoInput);
			TblMchtSettleInfTmp settleInf = new TblMchtSettleInfTmp();
			BeanUtils.copyProperties(tblMchtSettleInfTmp,settleInf);
			TblMchtSettleInfPK settleInfPk = new TblMchtSettleInfPK();
			settleInfPk.setMchtNo(mchtNoInput);
			settleInfPk.setTermId(CommonFunction.fillString("*",' ',8,true));
			settleInf.setId(settleInfPk);
			TblMchtSupp1Tmp supp1 = new TblMchtSupp1Tmp();
			BeanUtils.copyProperties(supp1Tmp,supp1);
			supp1.setMchtNo(mchtNoInput);
			tblMchtBaseInfTmpDAO.delete(tblMchtBaseInfTmp.getMchtNo());
			tblMchtSettleInfTmpDAO.delete(tblMchtSettleInfTmp.getId());
			tblMchtSettleInfTmpDAO.delete(supp1Tmp);
			tblMchtBaseInfTmpDAO.save(baseInf);
			tblMchtSettleInfTmpDAO.save(supp1);
			tblMchtSettleInfTmpDAO.save(settleInf);
			return Constants.SUCCESS_CODE;
			}

		public String addBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange) {
			// TODO Auto-generated method stub
			tblMchtBaseInfTmpDAO.addBaseBusiRange(tblMchtBaseBusiRange);
			return "00";
		}

		public String upBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange) {
			// TODO Auto-generated method stub
			tblMchtBaseInfTmpDAO.upBaseBusiRange(tblMchtBaseBusiRange);
			return "00";
		}

		


}
