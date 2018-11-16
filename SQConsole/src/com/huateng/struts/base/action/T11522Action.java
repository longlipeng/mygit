package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;

import oracle.net.ns.Communication;

import com.huateng.bo.base.T10206BO;
import com.huateng.bo.base.T11501BO;
import com.huateng.bo.base.T11521BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeRefuseInfo;
import com.huateng.po.base.TblAgentFeeTmp;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T11522Action extends BaseAction {
    
	private T11521BO t11521BO = (T11521BO) ContextUtil.getBean("T11521BO");
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	//primary key
	private String uuId;
	//拒绝原因
	private String refuseInfo;
	
	private String infList;
	
	
	public T11521BO getT11521BO() {
		return t11521BO;
	}


	public void setT11521BO(T11521BO t11521bo) {
		t11521BO = t11521bo;
	}


	public String getUuId() {
		return uuId;
	}


	public void setUuId(String uuId) {
		this.uuId = uuId;
	}


	public String getRefuseInfo() {
		return refuseInfo;
	}


	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}


	public String getInfList() {
		return infList;
	}


	public void setInfList(String infList) {
		this.infList = infList;
	}


	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		jsonBean.parseJSONArrayData(getInfList());
		
		int len = jsonBean.getArray().size();
		for(int i = 0;i < len;i++){
			String idkey = jsonBean.getJSONDataAt(i).getString("uuId");
			TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
			String state = tblAgentFeeTmp.getState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的信息");
				rspCode = acceptAdd(idkey);
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的信息");
				rspCode = acceptDelete(idkey);
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的信息");
				rspCode = acceptModify(idkey);
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的信息");
				rspCode = refuseAdd(idkey);
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的信息");
				rspCode = refuseDelete(idkey);
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝修改待审核的信息");
				rspCode = refuseModify(idkey);
			}
		}
		}
		return rspCode;
	}
	
	/**
	 * 审核通过新增待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd(String idkey) throws Exception {
		if(this.checkOperator(idkey))
			return "提交人与审核人不能是同一个人";
		else{
			
			
				TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
				BeanUtils.setObjectWithPropertiesValue(tblAgentFeeTmp, jsonBean,false);
				tblAgentFeeTmp.setUpDate(CommonFunction.getCurrentDateTime());
				tblAgentFeeTmp.setUpPer(operator.getOprId());
				tblAgentFeeTmp.setState(NORMAL);
				
				TblAgentFee tblAgentFee = new TblAgentFee();
				BeanUtils.copyProperties(tblAgentFee, tblAgentFeeTmp);
				rspCode = t11521BO.update(tblAgentFeeTmp);
				rspCode = t11521BO.add(tblAgentFee);

			

			
			}
			
			
		return rspCode;
	}

	/**
	 * 审核通过删除待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete(String idkey) throws Exception {
		if(this.checkOperator(idkey))
			return "提交人与审核人不能是同一个人";
		else{
			
				TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
				BeanUtils.setObjectWithPropertiesValue(tblAgentFeeTmp, jsonBean,false);
//				
				tblAgentFeeTmp.setUpDate(CommonFunction.getCurrentDateTime());
				tblAgentFeeTmp.setUpPer(operator.getOprId());
				tblAgentFeeTmp.setState(DELETE);
				rspCode = t11521BO.update(tblAgentFeeTmp);
//				String uuid = jsonBean.getJSONDataAt(i).getString("uuId");
				rspCode = t11521BO.delete(idkey);
				
				
			
		}
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify(String idkey) throws Exception {
		if(this.checkOperator(idkey))
			return "提交人与审核人不能是同一个人";
		else{
			
				TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
				BeanUtils.setObjectWithPropertiesValue(tblAgentFeeTmp, jsonBean,false);
				

				TblAgentFee tblAgentFee = new TblAgentFee();
				tblAgentFeeTmp.setUpDate(CommonFunction.getCurrentDateTime());
				tblAgentFeeTmp.setUpPer(operator.getOprId());
				tblAgentFeeTmp.setState(NORMAL);
				
				BeanUtils.copyProperties(tblAgentFee, tblAgentFeeTmp);
				rspCode = t11521BO.update(tblAgentFeeTmp);
				rspCode = t11521BO.saveOrUpdate(tblAgentFee);
				
			
			
		}
		return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 审核拒绝新增待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd(String idkey) throws Exception {
		
			TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
			if(this.checkOperator(idkey))
				return "提交人与审核人不能是同一个人";
			else{
//				tblTermChannelInf =  t11015BO.get(this.id);
				t11521BO.deleteTmp(idkey);
			}
			TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo = new TblAgentFeeRefuseInfo();
			tblAgentFeeRefuseInfo.setUuId(StringUtil.getUUID());
			tblAgentFeeRefuseInfo.setAgentNo(tblAgentFeeTmp.getAgentNo());
			tblAgentFeeRefuseInfo.setFeeMax(tblAgentFeeTmp.getFeeMax());
			tblAgentFeeRefuseInfo.setFeeMin(tblAgentFeeTmp.getFeeMin());
			tblAgentFeeRefuseInfo.setFeeValue(tblAgentFeeTmp.getFeeValue());
			tblAgentFeeRefuseInfo.setFeeType(tblAgentFeeTmp.getFeeType());
			tblAgentFeeRefuseInfo.setExtend1(tblAgentFeeTmp.getExtend1());
			tblAgentFeeRefuseInfo.setMccCode(tblAgentFeeTmp.getMccCode());
			tblAgentFeeRefuseInfo.setCrtDate(CommonFunction.getCurrentDateTime());
			tblAgentFeeRefuseInfo.setCrtPer(operator.getOprId());
			tblAgentFeeRefuseInfo.setRemark(refuseInfo);
			t11521BO.add(tblAgentFeeRefuseInfo);
			
			


		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝删除待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete(String idkey) throws Exception {
		
			TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
			BeanUtils.setObjectWithPropertiesValue(tblAgentFeeTmp, jsonBean,false);
			if(this.checkOperator(idkey))
				return "提交人与审核人不能是同一个人";
			else{

				tblAgentFeeTmp.setUpDate(CommonFunction.getCurrentDateTime());
				tblAgentFeeTmp.setUpPer(operator.getOprId());
				tblAgentFeeTmp.setState(NORMAL);
				TblAgentFee tblAgentFee = new TblAgentFee();
				BeanUtils.copyProperties(tblAgentFee, tblAgentFeeTmp);
				rspCode = t11521BO.update(tblAgentFeeTmp);
				rspCode = t11521BO.update(tblAgentFee);
				
			}
			TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo = new TblAgentFeeRefuseInfo();
			tblAgentFeeRefuseInfo.setUuId(StringUtil.getUUID());
			tblAgentFeeRefuseInfo.setAgentNo(tblAgentFeeTmp.getAgentNo());
			tblAgentFeeRefuseInfo.setFeeMax(tblAgentFeeTmp.getFeeMax());
			tblAgentFeeRefuseInfo.setFeeMin(tblAgentFeeTmp.getFeeMin());
			tblAgentFeeRefuseInfo.setFeeValue(tblAgentFeeTmp.getFeeValue());
			tblAgentFeeRefuseInfo.setFeeType(tblAgentFeeTmp.getFeeType());
			tblAgentFeeRefuseInfo.setExtend1(tblAgentFeeTmp.getExtend1());
			tblAgentFeeRefuseInfo.setMccCode(tblAgentFeeTmp.getMccCode());
			tblAgentFeeRefuseInfo.setCrtDate(CommonFunction.getCurrentDateTime());
			tblAgentFeeRefuseInfo.setCrtPer(operator.getOprId());
			tblAgentFeeRefuseInfo.setRemark(refuseInfo);
			t11521BO.add(tblAgentFeeRefuseInfo);
			

		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝修改待审核的信息
	 * @param idkey 
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify(String idkey) throws Exception {
		    TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
		    
			//BeanUtils.setObjectWithPropertiesValue(tblAgentFeeTmp, jsonBean,false);
			if(this.checkOperator(idkey))
				return "提交人与审核人不能是同一个人";
			else{
		//		tblAgentFeeTmp.setUpPer(operator.getOprId());
		//		tblAgentFeeTmp.setState(NORMAL);
				
				//判断真实表中是否存在这条记录
				String sql = "select count(*) from Tbl_Agent_Fee where UUID ='"+idkey.trim()+"'";
				String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
				if("0".equals(count)){   //如果真实表里不存在这条记录，那么直接删除
					t11521BO.deleteTmp(idkey);
				}else{    //如果真实表里存在这条记录，那么做正常的【拒绝修改待审核】操作
					TblAgentFee tblAgentFee = t11521BO.getTrue(idkey);
					BeanUtils.copyProperties(tblAgentFeeTmp,tblAgentFee);
					tblAgentFeeTmp.setUpDate(CommonFunction.getCurrentDateTime());
					tblAgentFeeTmp.setUpPer(operator.getOprId());
					rspCode = t11521BO.update(tblAgentFeeTmp);
				}
			}
			TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo = new TblAgentFeeRefuseInfo();
			tblAgentFeeRefuseInfo.setUuId(StringUtil.getUUID());
			tblAgentFeeRefuseInfo.setAgentNo(tblAgentFeeTmp.getAgentNo());
			tblAgentFeeRefuseInfo.setFeeMax(tblAgentFeeTmp.getFeeMax());
			tblAgentFeeRefuseInfo.setFeeMin(tblAgentFeeTmp.getFeeMin());
			tblAgentFeeRefuseInfo.setFeeValue(tblAgentFeeTmp.getFeeValue());
			tblAgentFeeRefuseInfo.setFeeType(tblAgentFeeTmp.getFeeType());
			tblAgentFeeRefuseInfo.setExtend1(tblAgentFeeTmp.getExtend1());
			tblAgentFeeRefuseInfo.setMccCode(tblAgentFeeTmp.getMccCode());
			tblAgentFeeRefuseInfo.setCrtDate(CommonFunction.getCurrentDateTime());
			tblAgentFeeRefuseInfo.setCrtPer(operator.getOprId());
			tblAgentFeeRefuseInfo.setRemark(refuseInfo);
			t11521BO.add(tblAgentFeeRefuseInfo);
			
			

		return Constants.SUCCESS_CODE;
	}
	
	
	//判断终端通道配置的操作人和审核人是否相同
	private boolean checkOperator(String idkey)throws Exception {
		
			
			TblAgentFeeTmp tblAgentFeeTmp = t11521BO.get(idkey);
			String oprID = tblAgentFeeTmp.getCrtPer();
			if(operator.getOprId().equals(oprID))
				return true;//相同
			else
				return false;//不同
			
		
	}

}
