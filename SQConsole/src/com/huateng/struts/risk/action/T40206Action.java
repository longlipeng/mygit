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
package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40206BO;
import com.huateng.po.TblCtlCardInf;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
/**
 * Title:卡黑名单审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author crystal
 * 
 * @version 1.0
 */

public class T40206Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		TblCtlCardInf tblCtlCardInf = t40206bo.get(saCardId);
		String state = tblCtlCardInf.getSaState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的卡黑名单");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的卡黑名单");
				rspCode = acceptDelete();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的卡黑名单");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的卡黑名单");
				rspCode = refuseDelete();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过      新增待审核的卡黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		TblCtlCardInf tblCtlCardInf = t40206bo.get(saCardId);
		
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			
//			tblCtlCardInf.setSaInitZoneNo(operator.getOprBrhId());
			tblCtlCardInf.setSaModiOprId(operator.getOprId());   //审核人
			tblCtlCardInf.setSaModiTime(CommonFunction.getCurrentDateTime());   //审核时间
			tblCtlCardInf.setSaState(NORMAL);   //状态
			
			rspCode = t40206bo.acceptAdd(tblCtlCardInf);			
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(saCardId);             //卡号
	//	riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("5");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo);      //审核备注
		riskRefuse.setFlag("1"); //备注
		riskRefuse.setParam2(tblCtlCardInf.getRemarkAdd());   //申请备注
		riskRefuse.setParam3(tblCtlCardInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlCardInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlCardInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlCardInf.getRiskRole());  //风险规则
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlCardInf.getSaState()+"' where FLAG='1' and PARAM1 ='"+saCardId+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
		
		return rspCode;
	}
	
	/**
	 * 审核拒绝  新增待审核的卡黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		TblCtlCardInf tblCtlCardInf = t40206bo.get(saCardId);
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人。";
		else{
			tblCtlCardInf.setSaModiOprId(operator.getOprId());   //审核人
			tblCtlCardInf.setSaModiTime(CommonFunction.getCurrentDateTime());   //审核时间
			tblCtlCardInf.setSaState(DELETE);   //状态
			
			rspCode = t40206bo.acceptAdd(tblCtlCardInf);	
		//	rspCode = t40206bo.delete(saCardId);
		}
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间
		riskRefuse.setParam1(saCardId);             //卡号
	//	riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("0");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo);      //审核备注
		riskRefuse.setFlag("1"); //备注
		riskRefuse.setParam2(tblCtlCardInf.getRemarkAdd());   //申请备注
		riskRefuse.setParam3(tblCtlCardInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlCardInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlCardInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlCardInf.getRiskRole());  //风险规则
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlCardInf.getSaState()+"' where FLAG='1' and PARAM1 ='"+saCardId+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
		return rspCode;
	}
	
	/**
	 * 审核通过  删除待审核的卡黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		TblCtlCardInf tblCtlCardInf = t40206bo.get(saCardId);
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人。";
		else{
			
			tblCtlCardInf.setSaModiOprId(operator.getOprId());
			tblCtlCardInf.setSaModiTime(CommonFunction.getCurrentDateTime());
			tblCtlCardInf.setSaState(DELETE);
			
			rspCode = t40206bo.acceptDelete(tblCtlCardInf);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(saCardId);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType("6");
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("1");
		riskRefuse.setParam2(tblCtlCardInf.getRemarkAdd());   //申请备注
		riskRefuse.setParam3(tblCtlCardInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlCardInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlCardInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlCardInf.getRiskRole());  //风险规则
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlCardInf.getSaState()+"' where FLAG='1' and PARAM1 ='"+saCardId+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
		
		return rspCode;
	}
	
	/**
	 * 审核拒绝   删除待审核的卡黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		TblCtlCardInf tblCtlCardInf = t40206bo.get(saCardId);
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人。";
		else{
			
			tblCtlCardInf.setSaModiTime(CommonFunction.getCurrentDateTime());
			tblCtlCardInf.setSaModiOprId(operator.getOprId());
			tblCtlCardInf.setSaState(NORMAL);
			rspCode = t40206bo.refuseDelete(tblCtlCardInf);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(saCardId);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType("4");
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("1");
		riskRefuse.setParam2(tblCtlCardInf.getRemarkAdd());   //申请备注
		riskRefuse.setParam3(tblCtlCardInf.getSaInitOprId());   //申请人
		riskRefuse.setParam4(tblCtlCardInf.getSaInitTime());    //申请时间
		riskRefuse.setParam5(tblCtlCardInf.getSaState());    //当前状态
		riskRefuse.setParam6(tblCtlCardInf.getRiskRole());  //风险规则
		
		t40206bo.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM5 = '"+tblCtlCardInf.getSaState()+"' where FLAG='1' and PARAM1 ='"+saCardId+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
		
		return rspCode;
	}
	
	//判断卡黑名单的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblCtlCardInf tblCtlCardInf = t40206bo.get(saCardId);
		String oprID = tblCtlCardInf.getSaInitOprId();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	// 交易卡号
	private String saCardId;
	//拒绝原因
	private String refuseInfo;
	
	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getSaCardId() {
		return saCardId;
	}

	public void setSaCardId(String saCardId) {
		this.saCardId = saCardId;
	}
	
}
