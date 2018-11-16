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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.error.T100201BO;
import com.huateng.common.Constants;
import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfId;
import com.huateng.po.error.TblChangeAccInfRefuse;
import com.huateng.po.error.TblChangeAccInfRefuseId;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblChangeAccInfTmpId;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户调账
 * Description: 
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */

@SuppressWarnings("serial")
public class T100201Action extends BaseAction {
 
	private static Logger log = Logger.getLogger(T100201Action.class);
	private T100201BO t100201BO = (T100201BO) ContextUtil.getBean("T100201BO");
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			 if("add".equals(method)) {
				rspCode = add();
			} else if ("delete".equals(method)){
				rspCode = delete();
			}else if ("deleteupdate".equals(method)){
				rspCode = delete();
			}else if ("updateone".equals(method)){
				rspCode = update();
			}else if ("refuse".equals(method)){
				rspCode = refuseList();
			}else if ("accept".equals(method)){
				rspCode = acceptList();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户调账操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String add() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
		tblChangeAccInfTmpId.setMchtNo(this.getMchtNo());
		tblChangeAccInfTmpId.setTermId(this.getTermId().trim());
		tblChangeAccInfTmpId.setChangeDate(CommonFunction.getCurrentDateTime());
		TblChangeAccInfTmp tblChangeAccInfTmp = new TblChangeAccInfTmp();
		tblChangeAccInfTmp.setId(tblChangeAccInfTmpId);
		tblChangeAccInfTmp.setChangeFlag("0");
		tblChangeAccInfTmp.setInstCode(this.getInstCode().trim());
		tblChangeAccInfTmp.setChangeReason(this.getChangeReason());
		tblChangeAccInfTmp.setChangeAccount(Double.valueOf(this.getChangeAccount())*100);
		tblChangeAccInfTmp.setSt("1");
		tblChangeAccInfTmp.setInsTs(CommonFunction.getCurrentDateTime());
		tblChangeAccInfTmp.setEnterTp("1");
		tblChangeAccInfTmp.setComfirmAccount(Double.valueOf(0));

		tblChangeAccInfTmp.setInsOpr(operator.getOprId());
		tblChangeAccInfTmp.setUpdTs(CommonFunction.getCurrentDateTime());
		tblChangeAccInfTmp.setUpdOpr(operator.getOprId());
		System.out.println(this.getInstCode().trim());
		t100201BO.addone(tblChangeAccInfTmp);
		return Constants.SUCCESS_CODE;
		
	}
	
	
	private String delete() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
		tblChangeAccInfTmpId.setMchtNo(String.format("%-15s",this.getMchtNo()));
		tblChangeAccInfTmpId.setTermId(String.format("%-8s",this.getTermId()));
		tblChangeAccInfTmpId.setChangeDate(this.getChangeDate());
		
		t100201BO.deletelist(tblChangeAccInfTmpId);
		return Constants.SUCCESS_CODE;
	}
	
	private String updateone() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException  {
		jsonBean.parseJSONArrayData(getAmtList());
		
		int len = jsonBean.getArray().size();
		TblChangeAccInfTmp tblChangeAccInfTmp = new TblChangeAccInfTmp();

		List<TblChangeAccInfTmp> tblChangeAccInfTmpList = new ArrayList<TblChangeAccInfTmp>(len);
		try {
			for (int i = 0; i < len; i++) {
				TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();	
				tblChangeAccInfTmpId.setChangeDate(jsonBean.getJSONDataAt(i).getString("changeDate"));
				tblChangeAccInfTmpId.setMchtNo(String.format("%-15s", jsonBean.getJSONDataAt(i).getString("mchtNo")));
				tblChangeAccInfTmpId.setTermId(String.format("%-8s",jsonBean.getJSONDataAt(i).getString("termId")));
				tblChangeAccInfTmp = t100201BO.get(tblChangeAccInfTmpId);
				BeanUtils.setObjectWithPropertiesValue(tblChangeAccInfTmp, jsonBean, false);
//				tblChangeAccInfTmp.setChangeFlag("0");
				tblChangeAccInfTmp.setSt("3");
				tblChangeAccInfTmp.setUpdTs(CommonFunction.getCurrentDateTime());

				tblChangeAccInfTmp.setUpdOpr(operator.getOprId());
				tblChangeAccInfTmpList.add(tblChangeAccInfTmp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t100201BO.saveupdate(tblChangeAccInfTmpList);
		return Constants.SUCCESS_CODE;
	}
	
	
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException  {
		jsonBean.parseJSONArrayData(getAmtList());
		
		int len = jsonBean.getArray().size();
		TblChangeAccInfTmp tblChangeAccInfTmp = new TblChangeAccInfTmp();

		List<TblChangeAccInfTmp> tblChangeAccInfTmpList = new ArrayList<TblChangeAccInfTmp>(len);
		try {
			for (int i = 0; i < len; i++) {
				TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
				tblChangeAccInfTmpId.setChangeDate(jsonBean.getJSONDataAt(i).getString("changeDate"));
				tblChangeAccInfTmpId.setMchtNo(String.format("%-15s", jsonBean.getJSONDataAt(i).getString("mchtNo")));
				tblChangeAccInfTmpId.setTermId(String.format("%-8s",jsonBean.getJSONDataAt(i).getString("termId")));
				tblChangeAccInfTmp = t100201BO.get(tblChangeAccInfTmpId);
				BeanUtils.setObjectWithPropertiesValue(tblChangeAccInfTmp, jsonBean, false);
//				tblChangeAccInfTmp.setChangeFlag("0");
				tblChangeAccInfTmp.setSt("2");
				tblChangeAccInfTmp.setChangeAccount(tblChangeAccInfTmp.getChangeAccount()*100);
				tblChangeAccInfTmp.setUpdTs(CommonFunction.getCurrentDateTime());

				tblChangeAccInfTmp.setUpdOpr(operator.getOprId());
				tblChangeAccInfTmpList.add(tblChangeAccInfTmp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t100201BO.saveupdate(tblChangeAccInfTmpList);
		return Constants.SUCCESS_CODE;
	}
	
	/*private String accept() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
		
		tblChangeAccInfTmpId.setMchtNo(String.format("%-15s",this.getMchtNo()));
		tblChangeAccInfTmpId.setTermId(String.format("%-8s",this.getTermId()));
		tblChangeAccInfTmpId.setChangeDate(this.getChangeDate());
		TblChangeAccInfTmp tblChangeAccInfTmp = t100201BO.get(tblChangeAccInfTmpId);
		tblChangeAccInfTmp.setChangeFlag("0");
		tblChangeAccInfTmp.setSt("0");
		tblChangeAccInfTmp.setAprTs(CommonFunction.getCurrentDateTime());
		tblChangeAccInfTmp.setAprOpr(operator.getOprId());
		
		TblChangeAccInf tblChangeAccInf = new TblChangeAccInf();
		TblChangeAccInfId tblChangeAccInfId = new TblChangeAccInfId();
		tblChangeAccInfId.setMchtNo(String.format("%-15s",this.getMchtNo()));
		tblChangeAccInfId.setTermId(String.format("%-8s",this.getTermId()));
		tblChangeAccInfId.setChangeDate(this.getChangeDate());
		tblChangeAccInf.setId(tblChangeAccInfId);
		tblChangeAccInf.setChangeAccount(tblChangeAccInfTmp.getChangeAccount());
		tblChangeAccInf.setChangeReason(tblChangeAccInfTmp.getChangeReason());
		tblChangeAccInf.setChangeFlag("0");
		tblChangeAccInf.setAprTs(CommonFunction.getCurrentDateTime());
		tblChangeAccInf.setAprOpr(operator.getOprId());
		tblChangeAccInf.setInsOpr(tblChangeAccInfTmp.getInsOpr());
		tblChangeAccInf.setInsTs(tblChangeAccInfTmp.getInsTs());
		tblChangeAccInf.setUpdOpr(tblChangeAccInfTmp.getUpdOpr());
		tblChangeAccInf.setUpdTs(tblChangeAccInfTmp.getUpdTs());
		tblChangeAccInf.setSt("0");
		tblChangeAccInf.setEnterTp(tblChangeAccInfTmp.getEnterTp());
		
		t100201BO.addone(tblChangeAccInfTmp);
		t100201BO.saveOrUpdate(tblChangeAccInf);
		return Constants.SUCCESS_CODE;
	}*/
	
	
    private String acceptList() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
    	jsonBean.parseJSONArrayData(getChangeList());
		int len = jsonBean.getArray().size();   	
		
		List<TblChangeAccInfTmp> listTmp = new ArrayList<TblChangeAccInfTmp>();
		List<TblChangeAccInf> list = new ArrayList<TblChangeAccInf>();
		
		
		for(int i = 0; i < len; i++) {		
			//临时表
			TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();

			tblChangeAccInfTmpId.setMchtNo(String.format("%-15s",jsonBean.getJSONDataAt(i).get("mchtNo").toString()));
			tblChangeAccInfTmpId.setTermId(String.format("%-8s",jsonBean.getJSONDataAt(i).get("termId").toString()));
			tblChangeAccInfTmpId.setChangeDate(jsonBean.getJSONDataAt(i).get("changeDate").toString());
			TblChangeAccInfTmp tblChangeAccInfTmp = t100201BO.get(tblChangeAccInfTmpId);
		    //判断当前操作人和审核人是否是同一人
			if(operator.getOprId().equals(tblChangeAccInfTmp.getInsOpr()))
				return "操作人与审核人不能是同一人";
			
			tblChangeAccInfTmp.setChangeFlag("0");
			tblChangeAccInfTmp.setSt("0");
			tblChangeAccInfTmp.setAprTs(CommonFunction.getCurrentDateTime());
			tblChangeAccInfTmp.setAprOpr(operator.getOprId());
			
		  
			
			//正式表
			TblChangeAccInf tblChangeAccInf = new TblChangeAccInf();
			TblChangeAccInfId tblChangeAccInfId = new TblChangeAccInfId();
			tblChangeAccInfId.setMchtNo(String.format("%-15s",jsonBean.getJSONDataAt(i).get("mchtNo").toString()));
			tblChangeAccInfId.setTermId(String.format("%-8s",jsonBean.getJSONDataAt(i).get("termId").toString()));
			tblChangeAccInfId.setChangeDate(jsonBean.getJSONDataAt(i).get("changeDate").toString());
			tblChangeAccInf.setId(tblChangeAccInfId);
			tblChangeAccInf.setChangeAccount(tblChangeAccInfTmp.getChangeAccount());
			tblChangeAccInf.setChangeReason(tblChangeAccInfTmp.getChangeReason());
			tblChangeAccInf.setInstCode(tblChangeAccInfTmp.getInstCode());
			tblChangeAccInf.setChangeFlag("0");
			tblChangeAccInf.setAprTs(CommonFunction.getCurrentDateTime());
			tblChangeAccInf.setAprOpr(operator.getOprId());
			tblChangeAccInf.setInsOpr(tblChangeAccInfTmp.getInsOpr());
			tblChangeAccInf.setInsTs(tblChangeAccInfTmp.getInsTs());
			tblChangeAccInf.setUpdOpr(tblChangeAccInfTmp.getUpdOpr());
			tblChangeAccInf.setUpdTs(tblChangeAccInfTmp.getUpdTs());
			tblChangeAccInf.setSt("0");
			tblChangeAccInf.setEnterTp(tblChangeAccInfTmp.getEnterTp());
			
			list.add(tblChangeAccInf);
			listTmp.add(tblChangeAccInfTmp);
		}
		
		t100201BO.addAll(listTmp);
		t100201BO.saveOrUpdate(list);
		return Constants.SUCCESS_CODE;
	}
	
	
	
	/*private String refuse() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
		
		tblChangeAccInfTmpId.setMchtNo(String.format("%-15s",this.getMchtNo()));
		tblChangeAccInfTmpId.setTermId(String.format("%-8s",this.getTermId()));
		tblChangeAccInfTmpId.setChangeDate(this.getChangeDate());
		TblChangeAccInfTmp tblChangeAccInfTmp = t100201BO.get(tblChangeAccInfTmpId);
		
		TblChangeAccInfRefuse tblChangeAccInfRefuse = new TblChangeAccInfRefuse();
		TblChangeAccInfRefuseId tblChangeAccInfRefuseId = new TblChangeAccInfRefuseId();
		tblChangeAccInfRefuseId.setMchtNo(String.format("%-15s",this.getMchtNo()));
		tblChangeAccInfRefuseId.setTermId(String.format("%-8s",this.getTermId()));
		tblChangeAccInfRefuseId.setChangeDate(this.getChangeDate());
		tblChangeAccInfRefuse.setId(tblChangeAccInfRefuseId);
		tblChangeAccInfRefuse.setChangeAccount(tblChangeAccInfTmp.getChangeAccount());
		tblChangeAccInfRefuse.setChangeReason(tblChangeAccInfTmp.getChangeReason());
		tblChangeAccInfRefuse.setChangeFlag("0");
		tblChangeAccInfRefuse.setAprTs(CommonFunction.getCurrentDateTime());
		tblChangeAccInfRefuse.setAprOpr(operator.getOprId());
		tblChangeAccInfRefuse.setInsOpr(tblChangeAccInfTmp.getInsOpr());
		tblChangeAccInfRefuse.setInsTs(tblChangeAccInfTmp.getInsTs());
		tblChangeAccInfRefuse.setUpdOpr(tblChangeAccInfTmp.getUpdOpr());
		tblChangeAccInfRefuse.setUpdTs(tblChangeAccInfTmp.getUpdTs());
		tblChangeAccInfRefuse.setSt("4");
		tblChangeAccInfRefuse.setEnterTp(tblChangeAccInfTmp.getEnterTp());
		tblChangeAccInfRefuse.setRemark(refuseInfo);
		
		t100201BO.deletelist(tblChangeAccInfTmpId);
		t100201BO.saveRefuse(tblChangeAccInfRefuse);
		return Constants.SUCCESS_CODE;
	
	}*/
	
	
    private String refuseList() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

    	jsonBean.parseJSONArrayData(getChangeList());
		int len = jsonBean.getArray().size();   	
		
		List<TblChangeAccInfTmpId> listTmpId = new ArrayList<TblChangeAccInfTmpId>();
		List<TblChangeAccInfRefuse> listRefuse = new ArrayList<TblChangeAccInfRefuse>();
		
		for(int i = 0; i < len; i++) {	
			//临时表
			TblChangeAccInfTmpId tblChangeAccInfTmpId = new TblChangeAccInfTmpId();
			
			tblChangeAccInfTmpId.setMchtNo(String.format("%-15s",jsonBean.getJSONDataAt(i).get("mchtNo").toString()));
			tblChangeAccInfTmpId.setTermId(String.format("%-8s",jsonBean.getJSONDataAt(i).get("termId").toString()));
			tblChangeAccInfTmpId.setChangeDate(jsonBean.getJSONDataAt(i).get("changeDate").toString());
			TblChangeAccInfTmp tblChangeAccInfTmp = t100201BO.get(tblChangeAccInfTmpId);
			//判断当前操作人和审核人是否是同一人
			if(operator.getOprId().equals(tblChangeAccInfTmp.getInsOpr()))
				return "操作人与审核人不能是同一人";
			
			TblChangeAccInfRefuse tblChangeAccInfRefuse = new TblChangeAccInfRefuse();
			TblChangeAccInfRefuseId tblChangeAccInfRefuseId = new TblChangeAccInfRefuseId();
			tblChangeAccInfRefuseId.setMchtNo(String.format("%-15s",jsonBean.getJSONDataAt(i).get("mchtNo").toString()));
			tblChangeAccInfRefuseId.setTermId(String.format("%-8s",jsonBean.getJSONDataAt(i).get("termId").toString()));
			tblChangeAccInfRefuseId.setChangeDate(jsonBean.getJSONDataAt(i).get("changeDate").toString());
			tblChangeAccInfRefuse.setId(tblChangeAccInfRefuseId);
			tblChangeAccInfRefuse.setChangeAccount(tblChangeAccInfTmp.getChangeAccount());
			tblChangeAccInfRefuse.setChangeReason(tblChangeAccInfTmp.getChangeReason());
			tblChangeAccInfRefuse.setChangeFlag("0");
			tblChangeAccInfRefuse.setInstCode(tblChangeAccInfTmp.getInstCode());
			tblChangeAccInfRefuse.setAprTs(CommonFunction.getCurrentDateTime());
			tblChangeAccInfRefuse.setAprOpr(operator.getOprId());
			tblChangeAccInfRefuse.setInsOpr(tblChangeAccInfTmp.getInsOpr());
			tblChangeAccInfRefuse.setInsTs(tblChangeAccInfTmp.getInsTs());
			tblChangeAccInfRefuse.setUpdOpr(tblChangeAccInfTmp.getUpdOpr());
			tblChangeAccInfRefuse.setUpdTs(tblChangeAccInfTmp.getUpdTs());
			tblChangeAccInfRefuse.setSt("4");
			tblChangeAccInfRefuse.setEnterTp(tblChangeAccInfTmp.getEnterTp());
			tblChangeAccInfRefuse.setRemark(refuseInfo);
			
			listTmpId.add(tblChangeAccInfTmpId);
			listRefuse.add(tblChangeAccInfRefuse);
			
		}
		
		t100201BO.deletelist(listTmpId);
		t100201BO.saveRefuse(listRefuse);
		return Constants.SUCCESS_CODE;
	
	}
	

 
    
	
	
	private String mchtNo;
	private String termId;
	private String changeAccount;
	private String changeReason;
	private String changeDate;
	private String amtList;
	private String refuseInfo;
	private String changeList;
	private String instCode;
//	private String sysSSn;
//	private String termSsn;
//	private String txnNum;
//	private String amtReturnList;
//	private String src;
//	private String createDate;
//	private String returnFee;
//	private String ID;
//	private String saState;
	
	
	
	public String getMchtNo() {
		return mchtNo;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getAmtList() {
		return amtList;
	}

	public void setAmtList(String amtList) {
		this.amtList = amtList;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
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

	public String getChangeAccount() {
		return changeAccount;
	}

	public void setChangeAccount(String changeAccount) {
		this.changeAccount = changeAccount;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getChangeList() {
		return changeList;
	}

	public void setChangeList(String changeList) {
		this.changeList = changeList;
	}
	
	

	

	

	

	

	
}