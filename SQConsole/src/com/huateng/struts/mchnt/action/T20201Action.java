/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-5       first release
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
package com.huateng.struts.mchnt.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.huateng.dao.iface.mchnt.TblMchntLogsDAO;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户信息审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20201Action extends BaseAction {
	
	private TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");

	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改、冻结、恢复和注销时，CRT_OPR_ID均保存该交易的申请人（发起柜员），UPD_OPR_ID保存该交易的审核人
		String sql = "SELECT CRT_OPR_ID FROM Tbl_Mcht_Base_Inf_Tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}

		log("审核商户编号：" + mchntId);
		
		if("accept".equals(method)) {
			rspCode = accept();
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		}else if("back".equals(method)) {
			rspCode = back();
		}else if("acceptAdd".equals(method)){
			rspCode = acceptAdd();
		}
		return rspCode;
	}
		
	/**
	 * 审核通过
	 * @return
	 * @throws Exception 
	 */
	private String accept() throws Exception {
		
		return service.accept(mchntId,oprInfo);
	}
	
	/**
	 * 审核拒绝
	 * @return
	 * @throws Exception 
	 */
	private String refuse() throws Exception {
		return service.refuse(mchntId, oprInfo);
	}
	
	/**
	 * 审核退回
	 * @return
	 * @throws Exception 
	 */
	private String back() throws Exception {
		return service.back2(mchntId, oprInfo);
	}
	
	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	
	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}
	/**
	 * 审核通过(添加审核)
	 * @return
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		String rislLvl = tmp.getRislLvl();
		String mchtStatus = tmp.getMchtStatus();
		
		if (!mchtStatus.equals("I") && !mchtStatus.equals("J")) {
			if (rislLvl.equals("2")) {
				try {
					Operator opr = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
					log("执行新增审核！");
					String ctnmSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and ctnm = '"+ tmp.getMchtNm() +"'";
					String ctnmcout = CommonFunction.getCommQueryDAO().findCountBySQLQuery(ctnmSql);
					log("ctnmSql:"+ ctnmSql);
					if (!ctnmcout.equals("0")) {
						service.refuse(mchntId, "商户名与黑名单商户名匹配不允审核！");
						return "商户名与黑名单商户名匹配不允审核！";
					}
					String ctnmSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '1' and ctnm = '"+ tmp.getManager() +"'";
					log("ctnmSql1:"+ ctnmSql1);
					String ctnmcout1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(ctnmSql1);
					if (!ctnmcout1.equals("0")) {
						service.refuse(mchntId, "法人与黑名单法人匹配不允审核！");
						return "法人与黑名单法人匹配不允审核！";
					}
					String clidSql2 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '1' and ciid = '"+ tmp.getIdentityNo() +"'";
					log("clidSql2:"+ clidSql2);
					String clidCount2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql2);
					if (!clidCount2.equals("0")) {
						service.refuse(mchntId,"法人证件号与黑名单法人证件号匹配不允审核！");
						return "法人证件号与黑名单法人证件号匹配不允审核！";
					}
					String clegSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cleg = '"+ tmp.getManager() +"'";
					log("clegSql:"+ clegSql);
					String clegcout = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clegSql);
					if (!clegcout.equals("0")) {
						service.refuse(mchntId, "法人与黑名单法人匹配不允审核！");
						return "法人与黑名单法人匹配不允审核！";
					}
					String clidSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and ciid = '"+ tmp.getIdentityNo() +"'";
					log("clidSql:"+ clidSql);
					String clidCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql);
					if (!clidCount.equals("0")) {
						service.refuse(mchntId,"法人证件号与黑名单法人证件号匹配不允审核！");
						return "法人证件号与黑名单法人证件号匹配不允审核！";
					}
					
					if (isNotEmpty(tmp.getUscCode())) {
						String clidSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '2' and ciid = '"+ tmp.getUscCode() +"'";
						log("clidSql1:"+ clidSql1);
						String clidCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql1);
						if (!clidCount1.equals("0")) {
							service.refuse(mchntId,"社会信用代码与黑名单证件号匹配不允审核！");
							return "社会信用代码与黑名单证件号匹配不允审核！";
						}
					}
					if (isNotEmpty(tmp.getLicenceNo())) {
						String clidSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '2'  and ciid = '"+ tmp.getUscCode() +"'";
						log("clidSql1:"+ clidSql1);
						String clidCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql1);
						if (!clidCount1.equals("0")) {
							service.refuse(mchntId,"营业执照与黑名单证件号匹配不允审核！");
							return "营业执照与黑名单证件号匹配不允审核！";
						}
					}
					String careSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and care like '"+ tmp.getRegAddr() +"%'";
					log("careSql:"+ careSql);
					String careCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(careSql);
					if (!careCount.equals("0")) {
						service.refuse(mchntId,"注册地与黑名单注册地匹配不允审核！");
						return "注册地与黑名单注册地匹配不允审核！";
					}
					String regAddr1 = tmp.getRegAddr();
					String careSql1 = "select count(region_name) from t_lst_region where status = '1' and region_name like '%"+ regAddr1 +"%'";
					String careCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(careSql1);
					if (!careCount1.equals("0")) {
						service.refuse(mchntId,"注册地与黑名单注册地匹配不允审核！");
						return "注册地与黑名单注册地匹配不允审核！";
					}
					tmp.setMchtStatus("I");
					tmp.setCrtOprId(opr.getOprId());
					tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
					String oprType = "商户首审";
					String oprStatus="通过";
					service.updateMchntLogs(tmp,oprType,oprStatus,oprInfo);
					return Constants.SUCCESS_CODE;
				} catch (Exception e) {
					e.printStackTrace();
					return "审核失败！";
				}
			}
		}
		
		String ctnmSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and ctnm = '"+ tmp.getMchtNm() +"'";
		log("ctnmSql:"+ ctnmSql);
		String ctnmcout = CommonFunction.getCommQueryDAO().findCountBySQLQuery(ctnmSql);
		if (!ctnmcout.equals("0")) {
			service.refuse(mchntId, "商户名与黑名单商户名匹配不允审核！");
			return "商户名与黑名单商户名匹配不允审核！";
		}
		String clegSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cleg = '"+ tmp.getManager() +"'";
		log("clegSql:"+ clegSql);
		String clegcout = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clegSql);
		if (!clegcout.equals("0")) {
			service.refuse(mchntId, "法人与黑名单法人匹配不允审核！");
			return "法人与黑名单法人匹配不允审核！";
		}
		String ctnmSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '1' and ctnm = '"+ tmp.getManager() +"'";
		log("ctnmSql1:"+ ctnmSql1);
		String ctnmcout1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(ctnmSql1);
		if (!ctnmcout1.equals("0")) {
			service.refuse(mchntId, "法人与黑名单法人匹配不允审核！");
			return "法人与黑名单法人匹配不允审核！";
		}
		String clidSql2 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '1' and ciid = '"+ tmp.getIdentityNo() +"'";
		log("clidSql2:"+ clidSql2);
		String clidCount2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql2);
		if (!clidCount2.equals("0")) {
			service.refuse(mchntId,"法人证件号与黑名单法人证件号匹配不允审核！");
			return "法人证件号与黑名单法人证件号匹配不允审核！";
		}
		String clidSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and ciid = '"+ tmp.getIdentityNo() +"'";
		log("clidSql:"+ clidSql);
		String clidCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql);
		if (!clidCount.equals("0")) {
			service.refuse(mchntId,"法人证件号与黑名单法人证件号匹配不允审核！");
			return "法人证件号与黑名单法人证件号匹配不允审核！";
		}
		if (isNotEmpty(tmp.getUscCode())) {
			String clidSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '2' and ciid = '"+ tmp.getUscCode() +"'";
			log("clidSql1:"+ clidSql1);
			String clidCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql1);
			if (!clidCount1.equals("0")) {
				service.refuse(mchntId,"社会信用代码与黑名单证件号匹配不允审核！");
				return "社会信用代码与黑名单证件号匹配不允审核！";
			}
		}
		if (isNotEmpty(tmp.getLicenceNo())) {
			String clidSql1 = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and cttp = '2' and ciid = '"+ tmp.getUscCode() +"'";
			log("clidSql1:"+ clidSql1);
			String clidCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(clidSql1);
			if (!clidCount1.equals("0")) {
				service.refuse(mchntId,"营业执照与黑名单证件号匹配不允审核！");
				return "营业执照与黑名单证件号匹配不允审核！";
			}
		}
		String careSql = "select count(*) from t_lst_entity where lstp = '0' and ckstatus = '2' and care like '"+ tmp.getRegAddr() +"%'";
		log("careSql:"+ careSql);
		String careCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(careSql);
		if (!careCount.equals("0")) {
			service.refuse(mchntId,"注册地与黑名单注册地匹配不允审核！");
			return "注册地与黑名单注册地匹配不允审核！";
		}
		String regAddr1 = tmp.getRegAddr();
		String careSql1 = "select count(region_name) from t_lst_region where status = '1' and region_name like '%"+ regAddr1 +"%'";
		log("careSql1:"+ careSql1);
		String careCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(careSql1);
		if (!careCount1.equals("0")) {
			service.refuse(mchntId,"注册地与黑名单注册地匹配不允审核！");
			return "注册地与黑名单注册地匹配不允审核！";
		}
		
		//贷记卡、借记卡限额信息
		CstMchtFeeInfPK cstMchtFeeInfPK = new CstMchtFeeInfPK();
		CstMchtFeeInfTmp cstMchtFeeInfTmpDebit = new CstMchtFeeInfTmp();
		cstMchtFeeInfPK.setMchtCd(mchntId);
		cstMchtFeeInfPK.setCardType("01");
		cstMchtFeeInfTmpDebit.setId(cstMchtFeeInfPK);
		cstMchtFeeInfTmpDebit.setTxnNum("1101");
		if(!"".equals(monAmtDebit)&&monAmtDebit!=null){
			cstMchtFeeInfTmpDebit.setMonAmt(Double.valueOf(monAmtDebit));
		}
		if("".equals(dayAmtDebit)||dayAmtDebit==null){
			dayAmtDebit=monAmtDebit;
		}
		if(!"".equals(dayAmtDebit)&&dayAmtDebit!=null){
			cstMchtFeeInfTmpDebit.setDayAmt(Double.valueOf(dayAmtDebit));
		}
		if("".equals(daySingleDebit)||daySingleDebit==null){
			daySingleDebit=dayAmtDebit;
		}
		if(!"".equals(daySingleDebit)&&daySingleDebit!=null){
			cstMchtFeeInfTmpDebit.setDaySingle(Double.valueOf(daySingleDebit));
		}
		
		
		
		cstMchtFeeInfTmpDebit.setSaState(CommonFunction.NORMAL);
		cstMchtFeeInfTmpDebit.setSaAction("1");
		
		CstMchtFeeInfPK cstMchtFeeInfPKTmp = new CstMchtFeeInfPK();
		CstMchtFeeInfTmp cstMchtFeeInfTmpCredit = new CstMchtFeeInfTmp();
		cstMchtFeeInfPKTmp.setMchtCd(mchntId);
		cstMchtFeeInfPKTmp.setCardType("03");
		cstMchtFeeInfTmpCredit.setId(cstMchtFeeInfPKTmp);
		cstMchtFeeInfTmpCredit.setTxnNum("1101");
		cstMchtFeeInfTmpCredit.setDaySingle(Double.valueOf(daySingleCredit));
		cstMchtFeeInfTmpCredit.setDayAmt(Double.valueOf(dayAmtCredit));
		cstMchtFeeInfTmpCredit.setMonAmt(Double.valueOf(monAmtCredit));
		cstMchtFeeInfTmpCredit.setSaState(CommonFunction.NORMAL);
		cstMchtFeeInfTmpCredit.setSaAction("1");
		return service.acceptAdd(mchntId,mchntInd,oprInfo,cstMchtFeeInfTmpDebit,cstMchtFeeInfTmpCredit,isChecked(isRoute),isChecked(iswhite));
	}
	
	public String isChecked(String param) {
		return param==null?"0":"1";
	}
	
	//add
	private String mchtNm;//商户名称
	private String manager;//法人
	private String identityNo;//法人证件号
	private String regAddr;//注册地址
	public String getMchtNm() {
		return mchtNm;
	}
	public void setMchtNm(String mchtNm) {
		this.mchtNm = mchtNm;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getRegAddr() {
		return regAddr;
	}
	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	//商户索引
	private String mchntInd;
	
	//备注
	private String oprInfo;
	
	// 商户编号
	private String mchntId;
	// 商户拒绝的原因
	private String refuseInfo;
	/**
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	/**
	 * @return the refuseInfo
	 */
	public String getRefuseInfo() {
		return refuseInfo;
	}

	/**
	 * @param refuseInfo the refuseInfo to set
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	
	
	
	public String getMchntInd() {
		return mchntInd;
	}

	public void setMchntInd(String mchntInd) {
		this.mchntInd = mchntInd;
	}

	
	public String getOprInfo() {
		return oprInfo;
	}

	public void setOprInfo(String oprInfo) {
		this.oprInfo = oprInfo;
	}

	/**
	 * 添加审核时处理的字段
	 */
	//借记卡单笔交易限额
	private String daySingleDebit;
	//借记卡单日交易金额
	private String dayAmtDebit;
	//借记卡单月交易金额
	private String monAmtDebit;
	//贷记卡单笔交易限额
	private String daySingleCredit;
	//贷记卡单日交易金额
	private String dayAmtCredit;
	//贷记卡单月交易金额
	private String monAmtCredit;
	//商户组别
	private String mchtGrp;
	//适用MCC
	private String mcc;
	//是否开通路由
	private String isRoute;
	//是否加入白名单
	private String iswhite;

	

	

	public String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getDaySingleDebit() {
		return daySingleDebit;
	}

	public void setDaySingleDebit(String daySingleDebit) {
		this.daySingleDebit = daySingleDebit;
	}

	public String getDayAmtDebit() {
		return dayAmtDebit;
	}

	public void setDayAmtDebit(String dayAmtDebit) {
		this.dayAmtDebit = dayAmtDebit;
	}

	public String getMonAmtDebit() {
		return monAmtDebit;
	}

	public void setMonAmtDebit(String monAmtDebit) {
		this.monAmtDebit = monAmtDebit;
	}

	public String getDaySingleCredit() {
		return daySingleCredit;
	}

	public void setDaySingleCredit(String daySingleCredit) {
		this.daySingleCredit = daySingleCredit;
	}

	public String getDayAmtCredit() {
		return dayAmtCredit;
	}

	public void setDayAmtCredit(String dayAmtCredit) {
		this.dayAmtCredit = dayAmtCredit;
	}

	public String getMonAmtCredit() {
		return monAmtCredit;
	}

	public void setMonAmtCredit(String monAmtCredit) {
		this.monAmtCredit = monAmtCredit;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getIsRoute() {
		return isRoute;
	}

	public void setIsRoute(String isRoute) {
		this.isRoute = isRoute;
	}

	public String getIswhite() {
		return iswhite;
	}

	public void setIswhite(String iswhite) {
		this.iswhite = iswhite;
	}
	
	
}
