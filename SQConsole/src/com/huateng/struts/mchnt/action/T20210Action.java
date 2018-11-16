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
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.mchnt.TblDefMchtService;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.common.StringUtil;

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
public class T20210Action extends BaseAction {
	
	private TblDefMchtService service = (TblDefMchtService) ContextUtil.getBean("TblDefMchtService");
	
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改、冻结、恢复和注销时，CRT_OPR_ID均保存该交易的申请人（发起柜员），UPD_OPR_ID保存该交易的审核人
		/*String sql = "SELECT CRT_OPR_ID FROM Tbl_Mcht_Base_Inf_Tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}*/

		log("审核商户记录：" + mchntId);
		
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
//		return service.accept(mchntId,oprInfo);
		return "待开发……";
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
		return service.back(mchntId, oprInfo);
	}
	
	/**
	 * 审核通过(添加审核)
	 * @return
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
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
		cstMchtFeeInfPKTmp.setCardType("00");
		cstMchtFeeInfTmpCredit.setId(cstMchtFeeInfPKTmp);
		cstMchtFeeInfTmpCredit.setTxnNum("1101");
		cstMchtFeeInfTmpCredit.setDaySingle(Double.valueOf(daySingleCredit));
		cstMchtFeeInfTmpCredit.setDayAmt(Double.valueOf(dayAmtCredit));
		cstMchtFeeInfTmpCredit.setMonAmt(Double.valueOf(monAmtCredit));
		cstMchtFeeInfTmpCredit.setSaState(CommonFunction.NORMAL);
		cstMchtFeeInfTmpCredit.setSaAction("1");
		return service.acceptAdd(mchntId,oprInfo,cstMchtFeeInfTmpDebit,cstMchtFeeInfTmpCredit,isChecked(isRoute),isChecked(iswhite));
	}
	
	public String isChecked(String param) {
		return param==null?"0":"1";
	}
	
	//商户索引记录
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
