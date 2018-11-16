/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.mchnt.T20304BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户限额维护
 * 环讯修改为商户交易限额维护
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20304Action extends BaseAction {
	
	private T20304BO t20304BO = (T20304BO) ContextUtil.getBean("T20304BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("newUpdate".equals(method)){
				rspCode = newUpdate();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户限额维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	/**
	 * 商户限额修改
	 * @return
	 * @throws Exception
	 */
	public String newUpdate()throws Exception{
		
		CstMchtFeeInfTmp cstMchtFeeInf = t20304BO.getMchtLimitTmp(new CstMchtFeeInfPK(mchtCd,cardType));	
		
		cstMchtFeeInf.setDaySingle(Double.valueOf(daySingleU));
		cstMchtFeeInf.setDayAmt(Double.valueOf(dayAmtU));
		cstMchtFeeInf.setMonAmt(Double.valueOf(monAmtU));
		cstMchtFeeInf.setSaState(CommonFunction.MODIFY_TO_CHECK);
		//更新操作人
		cstMchtFeeInf.setOprID(operator.getOprId());
		
		/*if((remark+remarkU).length()<=1000){
			cstMchtFeeInf.setRemark(remark+";"+remarkU);
		}else{
			return "备注长度已超上限";
		}*/
		t20304BO.saveOrUpdate(cstMchtFeeInf);
		
		//限额操作记录
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(cstMchtFeeInf.getId().getMchtCd());
		riskRefuse.setParam2(cstMchtFeeInf.getId().getCardType());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
//		riskRefuse.setRefuseType("0");
		riskRefuse.setRefuseInfo(remarkU);
		riskRefuse.setFlag("9");
		riskRefuse.setParam6("商户交易限额维护");
		riskRefuse.setReserve1("修改");
		t40206bo.saveRefuseInfo(riskRefuse);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 添加商户限额维护
	 * @return
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
		CstMchtFeeInfTmp cstMchtFeeInf = new CstMchtFeeInfTmp();
		
		CstMchtFeeInfPK cstMchtFeeInfPK = new CstMchtFeeInfPK();
		
		cstMchtFeeInfPK.setCardType(cardType);
		
		cstMchtFeeInfPK.setMchtCd(mchtCd);
		
		//cstMchtFeeInfPK.setTxnNum(txnNum);
		
		CstMchtFeeInfTmp old=t20304BO.getMchtLimitTmp(cstMchtFeeInfPK);
		if(old != null&&!CommonFunction.DELETE.equals(old.getSaState())&&!"5".equals(old.getSaState())) {
			return "该商户已经拥有消费限额信息！！";
		}else{
			
			cstMchtFeeInf.setId(cstMchtFeeInfPK);
			//不考虑渠道 默认0
			cstMchtFeeInf.setChannel("0");
			
			cstMchtFeeInf.setDayNum(dayNum);
			cstMchtFeeInf.setTxnNum("1101");
//			cstMchtFeeInf.setDayAmt(Float.valueOf(dayAmt));
			cstMchtFeeInf.setDayAmt(Double.valueOf(dayAmt));
//			cstMchtFeeInf.setDaySingle(Float.valueOf(daySingle));
			cstMchtFeeInf.setDaySingle(Double.valueOf(daySingle));
//			cstMchtFeeInf.setMonAmt(Float.valueOf(monAmt));
			cstMchtFeeInf.setMonAmt(Double.valueOf(monAmt));
			cstMchtFeeInf.setMonNum(monNum);
			
			cstMchtFeeInf.setSaAction("1");
			
			cstMchtFeeInf.setSaState(CommonFunction.ADD_TO_CHECK);
			
			cstMchtFeeInf.setRecCrtTs(CommonFunction.getCurrentDate());
			
			cstMchtFeeInf.setRecUpdTs(CommonFunction.getCurrentDate());
			
			cstMchtFeeInf.setOprID(operator.getOprId());
			
			t20304BO.saveOrUpdate(cstMchtFeeInf);
			
			//限额操作记录
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			riskRefuse.setParam1(cstMchtFeeInf.getId().getMchtCd());
			riskRefuse.setParam2(cstMchtFeeInf.getId().getCardType());
			riskRefuse.setBrhId(operator.getOprBrhId());
			riskRefuse.setOprId(operator.getOprId());
//			riskRefuse.setRefuseType("0");
//			riskRefuse.setRefuseInfo(refuseInfo);
			riskRefuse.setFlag("9");
			riskRefuse.setParam6("商户交易限额维护");
			riskRefuse.setReserve1("新增");
			t40206bo.saveRefuseInfo(riskRefuse);
		}
		log("添加商户限额维护成功。操作员编号：" + operator.getOprId());		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户限额
	 * @return
	 * @throws Exception 
	 */
	private String delete() throws Exception {
		CstMchtFeeInfTmp old=t20304BO.getMchtLimitTmp(new CstMchtFeeInfPK(mchtCd,cardType));
		if(old!=null){
			if(CommonFunction.ADD_TO_CHECK.equals(old.getSaState())){
				t20304BO.delete(new CstMchtFeeInfPK(mchtCd,cardType));
			}else if(CommonFunction.NORMAL.equals(old.getSaState())){
				old.setSaState(CommonFunction.DELETE_TO_CHECK);
				old.setOprID(operator.getOprId());
				old.setRecUpdTs(CommonFunction.getCurrentDate());
				t20304BO.saveOrUpdate(old);
				
				//限额操作记录
				TblRiskRefuse riskRefuse = new TblRiskRefuse();
				riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
				riskRefuse.setParam1(old.getId().getMchtCd());
				riskRefuse.setParam2(old.getId().getCardType());
				riskRefuse.setBrhId(operator.getOprBrhId());
				riskRefuse.setOprId(operator.getOprId());
//				riskRefuse.setRefuseType("0");
				riskRefuse.setRefuseInfo(exMsg);
				riskRefuse.setFlag("9");
				riskRefuse.setParam6("商户交易限额维护");
				riskRefuse.setReserve1("删除");
				t40206bo.saveRefuseInfo(riskRefuse);
				
			}else{
				return "除新增待审核外，待审核状态的不可删除";
			}
		}
		log("删除商户限额维护成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新商户限额信息
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getCstMchtFeeList());
		
		int len = jsonBean.getArray().size();
//		
		CstMchtFeeInfTmp cstMchtFeeInf = null;
//		
		List<CstMchtFeeInfTmp> cstMchtFeeInfList = new ArrayList<CstMchtFeeInfTmp>(len);
		for(int i = 0; i < len; i++) {				
			mchtCd = jsonBean.getJSONDataAt(i).getString("mchtCd");
//			saAction=jsonBean.getJSONDataAt(i).getString("saAction");
			//txnNum = jsonBean.getJSONDataAt(i).getString("txnNum");
			cardType = jsonBean.getJSONDataAt(i).getString("cardType");
			cstMchtFeeInf = t20304BO.getMchtLimitTmp(new CstMchtFeeInfPK(mchtCd,cardType));		
			BeanUtils.setObjectWithPropertiesValue(cstMchtFeeInf, jsonBean, false);
			//不考虑渠道 默认0
			cstMchtFeeInf.setChannel("0");
			//正常状态的将其状态修改为待审核状态
			if(CommonFunction.NORMAL.equals(cstMchtFeeInf.getSaState())){
				cstMchtFeeInf.setSaState(CommonFunction.MODIFY_TO_CHECK);
			}
			cstMchtFeeInf.setOprID(operator.getOprId());
			cstMchtFeeInf.setRecUpdTs(CommonFunction.getCurrentDate());
			cstMchtFeeInfList.add(cstMchtFeeInf);
		}
		t20304BO.updateTmp(cstMchtFeeInfList);
		log("更新商户限额维护成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	

	

	// 商户编号
	private String mchtCd;
	// 交易类型
	private String txnNum="1101";
	// 卡类型
	private String cardType;
	// 交易渠道
	private String channel;
	// 单日累计交易笔数
	private String dayNum;
	// 单日累计交易金额
	private String dayAmt;
	// 单日单笔交易金额
	private String daySingle;
	// 单月累计交易笔数
	private String monNum="0";
	// 单月累计交易金额
	private String monAmt;

	private String cstMchtFeeList;
	
	//受控动作
	private String saAction;
	
	

	public String getSaAction() {
		return saAction;
	}

	public void setSaAction(String saAction) {
//		this.saAction = saAction;
		this.saAction = "1";
	}

	public T20304BO getT20304BO() {
		return t20304BO;
	}

	public void setT20304BO(T20304BO t20304bo) {
		t20304BO = t20304bo;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = "1101";//消费请求
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;//卡类型
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDayNum() {
		return dayNum;
	}

	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}

	public String getDayAmt() {
		return dayAmt;
	}

	public void setDayAmt(String dayAmt) {
		this.dayAmt = dayAmt;
	}

	public String getDaySingle() {
		return daySingle;
	}

	public void setDaySingle(String daySingle) {
		this.daySingle = daySingle;
	}

	public String getMonNum() {
		return monNum;
	}

	public void setMonNum(String monNum) {
		this.monNum = monNum;
	}

	public String getMonAmt() {
		return monAmt;
	}

	public void setMonAmt(String monAmt) {
		this.monAmt = monAmt;
	}

	public String getCstMchtFeeList() {
		return cstMchtFeeList;
	}

	public void setCstMchtFeeList(String cstMchtFeeList) {
		this.cstMchtFeeList = cstMchtFeeList;
	}
	
	
	
	public String idmchtCdU;
	public String cardTypeU;
	public String daySingleU;
	public String dayAmtU;
	public String monAmtU;
	public String remark;
	public String remarkU;
	
	private java.lang.String exMsg;
	
	

	public java.lang.String getExMsg() {
		return exMsg;
	}
	public void setExMsg(java.lang.String exMsg) {
		this.exMsg = exMsg;
	}
	public String getIdmchtCdU() {
		return idmchtCdU;
	}
	public void setIdmchtCdU(String idmchtCdU) {
		this.idmchtCdU = idmchtCdU;
	}
	public String getCardTypeU() {
		return cardTypeU;
	}
	public void setCardTypeU(String cardTypeU) {
		this.cardTypeU = cardTypeU;
	}
	public String getDaySingleU() {
		return daySingleU;
	}
	public void setDaySingleU(String daySingleU) {
		this.daySingleU = daySingleU;
	}
	public String getDayAmtU() {
		return dayAmtU;
	}
	public void setDayAmtU(String dayAmtU) {
		this.dayAmtU = dayAmtU;
	}
	public String getMonAmtU() {
		return monAmtU;
	}
	public void setMonAmtU(String monAmtU) {
		this.monAmtU = monAmtU;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkU() {
		return remarkU;
	}
	public void setRemarkU(String remarkU) {
		this.remarkU = remarkU;
	}
	
	
	
	
	
}
