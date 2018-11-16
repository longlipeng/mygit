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
import com.huateng.bo.risk.T40208BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskMchtTranCtl;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户交易黑名单审核
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
public class T40209Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40208BO t40208bo = (T40208BO) ContextUtil.getBean("T40208BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		TblRiskMchtTranCtl tblRiskMchtTranCtl = t40208bo.get(id);
		String state = tblRiskMchtTranCtl.getSaState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的商户交易黑名单");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的商户交易黑名单");
				rspCode = acceptDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的商户交易黑名单");
				rspCode = acceptModify();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的商户交易黑名单");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的商户交易黑名单");
				rspCode = refuseDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝修改待审核的商户交易黑名单");
				rspCode = refuseModify();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的商户交易黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		if(this.checkOperator())
			return "操作人与审核人不能是同一个人";
		else{
			TblRiskMchtTranCtl tblRiskMchtTranCtl = t40208bo.get(id);
//			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setSaState(NORMAL);
			tblRiskMchtTranCtl.setReserved1(operator.getOprId());
			
			rspCode = t40208bo.update(tblRiskMchtTranCtl);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的商户交易黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		TblRiskMchtTranCtl tblRiskMchtTranCtl = new TblRiskMchtTranCtl();
		if(this.checkOperator())
			return "操作人与审核人不能是同一个人";
		else{
			tblRiskMchtTranCtl = t40208bo.get(id);
			t40208bo.delete(id);
		}
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRiskMchtTranCtl.getMchtNo());
		riskRefuse.setParam2(tblRiskMchtTranCtl.getChannel());
		riskRefuse.setParam3(tblRiskMchtTranCtl.getBussType());
		riskRefuse.setParam4(tblRiskMchtTranCtl.getTxnNum());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("3");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的商户交易黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		if(this.checkOperator())
			return "操作人与审核人不能是同一个人";
		else{
			TblRiskMchtTranCtl tblRiskMchtTranCtl = t40208bo.get(id);
//			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setReserved1(operator.getOprId());
			tblRiskMchtTranCtl.setSaState(DELETE);
			
			rspCode = t40208bo.update(tblRiskMchtTranCtl);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的商户交易黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		TblRiskMchtTranCtl tblRiskMchtTranCtl = new TblRiskMchtTranCtl();
		if(this.checkOperator())
			return "操作人与审核人不能是同一个人";
		else{
			tblRiskMchtTranCtl = t40208bo.get(id);
//			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setReserved1(operator.getOprId());
			tblRiskMchtTranCtl.setSaState(NORMAL);
			rspCode = t40208bo.update(tblRiskMchtTranCtl);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRiskMchtTranCtl.getMchtNo());
		riskRefuse.setParam2(tblRiskMchtTranCtl.getChannel());
		riskRefuse.setParam3(tblRiskMchtTranCtl.getBussType());
		riskRefuse.setParam4(tblRiskMchtTranCtl.getTxnNum());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("3");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过修改待审核的商户交易黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "操作人与审核人不能是同一个人";
		else{
			TblRiskMchtTranCtl tblRiskMchtTranCtl = t40208bo.get(id);
//			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setReserved1(operator.getOprId());
			tblRiskMchtTranCtl.setSaState(NORMAL);
			//将修改后的字段覆盖原字段
			tblRiskMchtTranCtl.setChannel(tblRiskMchtTranCtl.getChannelOld());
			tblRiskMchtTranCtl.setBussType(tblRiskMchtTranCtl.getBussTypeOld());
			tblRiskMchtTranCtl.setTxnNum(tblRiskMchtTranCtl.getTxnNumOld());
			rspCode = t40208bo.update(tblRiskMchtTranCtl);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝修改待审核的商户交易黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		TblRiskMchtTranCtl tblRiskMchtTranCtl = new TblRiskMchtTranCtl();
		String channel = "";
		String bussType ="";
		String txnNum ="";
		if(this.checkOperator())
			return "操作人与审核人不能是同一个人";
		else{
			tblRiskMchtTranCtl = t40208bo.get(id);
			channel = tblRiskMchtTranCtl.getChannel();
			bussType = tblRiskMchtTranCtl.getBussType();
			txnNum = tblRiskMchtTranCtl.getTxnNum();
//			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setReserved1(operator.getOprId());
			tblRiskMchtTranCtl.setSaState(NORMAL);
			//用原字段值覆盖修改后的值
			tblRiskMchtTranCtl.setChannelOld(tblRiskMchtTranCtl.getChannel());
			tblRiskMchtTranCtl.setBussTypeOld(tblRiskMchtTranCtl.getBussType());
			tblRiskMchtTranCtl.setTxnNumOld(tblRiskMchtTranCtl.getTxnNum());
			rspCode = t40208bo.update(tblRiskMchtTranCtl);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRiskMchtTranCtl.getMchtNo());
		riskRefuse.setParam2(channel);
		riskRefuse.setParam3(bussType);
		riskRefuse.setParam4(txnNum);
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("3");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	//判断商户交易黑名单的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblRiskMchtTranCtl TblRiskMchtTranCtl = t40208bo.get(id);
		String oprID = TblRiskMchtTranCtl.getOprID();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	// 
	private String id;
	//拒绝原因
	private String refuseInfo;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	
}
