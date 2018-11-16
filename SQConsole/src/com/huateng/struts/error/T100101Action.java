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
package com.huateng.struts.error;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.huateng.bo.error.T100101BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.error.ManualReturn;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:手工退货 
 * Description: 
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */

@SuppressWarnings("serial")
public class T100101Action extends BaseAction {
 
	private static Logger log = Logger.getLogger(T100101Action.class);
	private T100101BO t100101BO = (T100101BO) ContextUtil.getBean("T100101BO");
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			 if("update".equals(method)) {
				rspCode = update();
			} else if ("delete".equals(method)){
				rspCode = delete();
			}else if ("updateone".equals(method)){
				rspCode = updateone();
			}else if ("updatestate".equals(method)){
				rspCode = updatestate();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，手工退货操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String updatestate() {
		ManualReturn manualReturn = t100101BO.get(ID);
		if(manualReturn == null) {
			return "没有找到要更新的信息";
		}
		manualReturn.setOprId(operator.getOprId());
		manualReturn.setUpdId(operator.getOprId());
		manualReturn.setCreateDate(CommonFunction.getCurrentDateTime());
		manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
		manualReturn.setSaState("3");
		t100101BO.saveOrUpdate(manualReturn);
		return Constants.SUCCESS_CODE;
	}

	private String updateone() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException  {
		jsonBean.parseJSONArrayData(getAmtReturnList());
		
		int len = jsonBean.getArray().size();
		ManualReturn manualReturn = new ManualReturn();

		List<ManualReturn> manualReturnList = new ArrayList<ManualReturn>(len);
		try {
			for (int i = 0; i < len; i++) {
				String id = jsonBean
				.getJSONDataAt(i).getString("ID");
				manualReturn = t100101BO.get(id);
				
				BeanUtils.setObjectWithPropertiesValue(manualReturn, jsonBean, false);
				double amtReturnP = Double.valueOf(Double.valueOf(jsonBean.getJSONDataAt(i).getString("amtReturn")));
//				double amtTransP = Double.valueOf(Double.valueOf(jsonBean.getJSONDataAt(i).getString("amtTrans")));
				BigDecimal bigDecimal2 = new BigDecimal(100); 
//				BigDecimal AmtTrans = BigDecimal.valueOf(amtTransP).multiply(bigDecimal2);
				BigDecimal saAmt = BigDecimal.valueOf(amtReturnP).multiply(bigDecimal2);
				String amtR = saAmt.toString();
//				String AmtTransSt = AmtTrans.toString();
//				String AmtTransSpt = AmtTransSt.substring(0, AmtTransSt.indexOf("."));
				String amtRT = amtR.substring(0,amtR.indexOf("."));
				String str ="000000000000";
				amtRT=str.substring(0, 12-amtRT.length())+amtRT;
//				AmtTransSpt =str.subSequence(0, 12-AmtTransSpt.length())+AmtTransSpt;
				manualReturn.setOprId(operator.getOprId());
				manualReturn.setUpdId(operator.getOprId());
				manualReturn.setAmtReturn(amtRT);
				manualReturn.setCreateDate(CommonFunction.getCurrentDateTime());
				manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
				manualReturn.setSaState("2");
				manualReturn.setSrcId("1901");
				manualReturn.setPotalSsn(manualReturn.getId()+"%%%");
				manualReturnList.add(manualReturn);
//				manualReturn.setAmtTrans(AmtTransSpt);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t100101BO.saveupdate(manualReturnList);
		return Constants.SUCCESS_CODE;
	}

	private String delete() {
		ManualReturn manualReturn = t100101BO.get(ID);
		System.out.println(ID);
//		String string = jsonBean.getJSONDataAt(0).getString("ID");
//		System.out.println(string);
		System.out.println(mchtNo);
		
		if(t100101BO.get(ID) == null) {
			return "没有找到要相应的信息";
		}
		
		t100101BO.delete(ID);
		
		return Constants.SUCCESS_CODE;

	}

	/**
	 *  
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @thr同步ows IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getAmtReturnList());
		
		int len = jsonBean.getArray().size();
//		
		ManualReturn manualReturn = new ManualReturn();
		double totalAmt=0;//累计退货金额
		List<ManualReturn> manualReturnList = new ArrayList<ManualReturn>(len);
		for(int i = 0; i < len; i++) {
			double amtReturnP = Double.valueOf(Double.valueOf(jsonBean.getJSONDataAt(i).getString("amtReturn")));
			double amtTransP = Double.valueOf(Double.valueOf(jsonBean.getJSONDataAt(i).getString("amtTrans")));
			System.out.println("退货金额:"+amtReturnP);
			//页面传入的待保存字段值
			mchtNo = jsonBean.getJSONDataAt(i).getString("mchtNo");
			termId = jsonBean.getJSONDataAt(i).getString("termId");
			pan = jsonBean.getJSONDataAt(i).getString("pan");
			amtTrans = jsonBean.getJSONDataAt(i).getString("amtTrans");
			retrivlRef = jsonBean.getJSONDataAt(i).getString("retrivlRef");
			sysSSn = jsonBean.getJSONDataAt(i).getString("sysSsn");
			amtReturn = Double.toString(amtReturnP);
			createDate = jsonBean.getJSONDataAt(i).getString("createDate");
			instDate = jsonBean.getJSONDataAt(i).getString("instDate");
			returnFee = jsonBean.getJSONDataAt(i).getString("returnFee");
			src=jsonBean.getJSONDataAt(i).getString("src");//累计退货金额
			//累计退货金额不可直接从页面传入，页面传入会存在bug，需要直接从数据库中读取累计退货金额
			//系统流水号+交易日期可以唯一确定一笔交易
			
			//modify by zxc 20130506 01 start 
			
			String srcSql = "select to_number(amt_return)/100 From tbl_n_txn where  SYS_SEQ_NUM='"+sysSSn+"' and INST_DATE='"+instDate+"'";
			src = commQueryDAO.findCountBySQLQuery(srcSql);
			if( "0".equals(src) ) {
			//如果累计退货金额为空 则需要去交易历史流水表中查询（交易流水表中只存放若干天的数据，交易历史流水表中存放除了当天外的所有数据）
				srcSql = "select to_number(amt_return)/100 From tbl_n_txn_his where SYS_SEQ_NUM='"+sysSSn+"' and INST_DATE='"+instDate+"' ";
				src = commQueryDAO.findCountBySQLQuery(srcSql);
			}
			String desSql = "select MSG_DEST_ID From tbl_n_txn where  SYS_SEQ_NUM='"+sysSSn+"' and INST_DATE='"+instDate+"'";
			des = commQueryDAO.findCountBySQLQuery(desSql);
			if( des.equals("")) {
				//如果累计退货金额为空 则需要去交易历史流水表中查询（交易流水表中只存放若干天的数据，交易历史流水表中存放除了当天外的所有数据）
				desSql = "select MSG_DEST_ID From tbl_n_txn_his where  SYS_SEQ_NUM='"+sysSSn+"' and INST_DATE='"+instDate+"'";
				des = commQueryDAO.findCountBySQLQuery(srcSql);
			}
			
			//modify by zxc 20130506 01 end
			
			
			//modify by zxc 20130506 02 start 
			//如果累计退货金额为空 则需要去交易历史流水表中查询（交易流水表中只存放若干天的数据，交易历史流水表中存放除了当天外的所有数据）
//			String srcSql = "select to_number(amt_return)/100 From tbl_n_txn_his where SYS_SEQ_NUM='"+sysSSn+"' and INST_DATE='"+instDate+"' ";
//			src = commQueryDAO.findCountBySQLQuery(srcSql);
			//modify by zxc 20130506 02 end
			
			log("累计已退货金额:"+src);
			String sql="select sum(AMT_RETURN) from TBL_MANUAL_RETURN where  MCHTNO='"+mchtNo+"' and inst_Date='"+instDate+"'" +
					" and RETRIVL_REF='"+retrivlRef+"' " +
					" and  pan='"+pan+"' and sa_state='0'";
			
			String total = commQueryDAO.findCountBySQLQuery(sql);
			if(!StringUtil.isEmpty(total)){
				totalAmt=Double.valueOf(total)/100;
			}
			if(StringUtil.isEmpty(amtReturn)){
				return "退货金额不能为空！";
			}
			double amtR=Double.valueOf(Double.valueOf(jsonBean.getJSONDataAt(i).getString("amtReturn")));
			
			//add by zxc 20130506 03 start
//			if(StringUtil.isEmpty(src)){
//				return "查询的累计退货金额为空！";
//			}
			if(StringUtil.isEmpty(amtTrans)){
				return "原交易金额金额为空！";
			}
			
			
			//查询表tbl_term_channel_inf中的TERM_INS字段，插入到手工退货表中的inst_code字段

			String instsql="select TERM_INS from tbl_term_channel_inf where  MCHT_ID='"+mchtNo+"' and MCHT_TERM_ID='"+termId+"'";
	
			String inscodetsql = commQueryDAO.findCountBySQLQuery(instsql);
			//add by zxc 20130506 03 end
			if("".equals(src)){
				if((amtR+totalAmt)>Double.valueOf(amtTrans)){
					return "退货累计金额不能超过原交易金额";
				}
			}else{
				if((amtR+totalAmt+Double.valueOf(src))>Double.valueOf(amtTrans)){
				return "退货累计金额不能超过原交易金额";
			}}
			BigDecimal bigDecimal2 = new BigDecimal(100); 
			BigDecimal saAmt = BigDecimal.valueOf(amtReturnP).multiply(bigDecimal2);
			BigDecimal AmtTrans = BigDecimal.valueOf(amtTransP).multiply(bigDecimal2);
			String AmtTransSt = AmtTrans.toString();
			String amtRM = saAmt.toString();
			String AmtTransSpt = AmtTransSt.substring(0, AmtTransSt.indexOf("."));
			String amtRT = amtRM.substring(0,amtRM.indexOf("."));
			String str ="000000000000";
			amtRT=str.substring(0, 12-amtRT.length())+amtRT;
			AmtTransSpt =str.subSequence(0, 12-AmtTransSpt.length())+AmtTransSpt;
			BeanUtils.setObjectWithPropertiesValue(manualReturn, jsonBean, false);
			manualReturn.setOprId(operator.getOprId());
			manualReturn.setUpdId(operator.getOprId());
			manualReturn.setAmtReturn(amtRT);
			manualReturn.setAmtTrans(AmtTransSpt);
			manualReturn.setCreateDate(CommonFunction.getCurrentDateTime());
			manualReturn.setUpdDate(CommonFunction.getCurrentDateTime());
			manualReturn.setSaState("0");
			manualReturn.setOprFlag("0");
			manualReturn.setSrcId("1901");
			manualReturn.setDestId(des);
			String idMax = "select ID from tbl_manual_return where inst_date = (select max(inst_date) from tbl_manual_return) ";
			String idMaxStr = commQueryDAO.findCountBySQLQuery(idMax) +  new Random().nextInt(1000000) + "%";
			manualReturn.setPotalSsn(idMaxStr);
			manualReturn.setInstCode(inscodetsql);
			manualReturnList.add(manualReturn);
		}
	
		
		t100101BO.add(manualReturnList);
		log("手工退货信息更新成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}

	
	
	private String mchtNo;
	private String termId;
	private String pan;
	private String amtTrans;
	private String retrivlRef;
	private String amtReturn;
	private String instDate;
	private String sysSSn;
	private String termSsn;
	private String txnNum;
	private String amtReturnList;
	private String src;
	private String createDate;
	private String returnFee;
	private String ID;
	private String saState;
	private String des;
	

	

	

	

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getReturnFee() {
		return returnFee;
	}

	public void setReturnFee(String returnFee) {
		this.returnFee = returnFee;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSysSSn() {
		return sysSSn;
	}

	public void setSysSSn(String sysSSn) {
		this.sysSSn = sysSSn;
	}

	public String getTermSsn() {
		return termSsn;
	}

	public void setTermSsn(String termSsn) {
		this.termSsn = termSsn;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAmtTrans() {
		return amtTrans;
	}

	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}

	public String getRetrivlRef() {
		return retrivlRef;
	}

	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}

	public String getAmtReturn() {
		return amtReturn;
	}

	public void setAmtReturn(String amtReturn) {
		this.amtReturn = amtReturn;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getAmtReturnList() {
		return amtReturnList;
	}

	public void setAmtReturnList(String amtReturnList) {
		this.amtReturnList = amtReturnList;
	}
	
}