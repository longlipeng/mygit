package com.huateng.struts.base.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import oracle.net.ns.Communication;

import com.huateng.bo.base.T10206BO;
import com.huateng.bo.base.T11501BO;
import com.huateng.bo.base.T11521BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeTmp;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T11521Action extends BaseAction {
    
	private T11521BO t11521BO = (T11521BO) ContextUtil.getBean("T11521BO");
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("delete".equals(method)) {
			rspCode = delete();
		} else if("add".equals(method)) {
			rspCode = add();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}
	
	
	
	private String delete() throws Exception{
		TblAgentFeeTmp tblAgentFeeTmp = new TblAgentFeeTmp();
		tblAgentFeeTmp =t11521BO.get(uuId);
		tblAgentFeeTmp.setState("4");
		tblAgentFeeTmp.setCrtDate(CommonFunction.getCurrentDateTime());
		tblAgentFeeTmp.setCrtPer(operator.getOprId());
		rspCode = t11521BO.update(tblAgentFeeTmp);
		return rspCode;
	}
	
	
	private String add() throws Exception{
		TblAgentFeeTmp tblAgentFeeTmp = new TblAgentFeeTmp();
		tblAgentFeeTmp.setUuId(StringUtil.getUUID());
		System.out.println("UUid"+StringUtil.getUUID());
		tblAgentFeeTmp.setFeeMax(BigDecimal.valueOf(Double.valueOf(feeMax)));
		tblAgentFeeTmp.setFeeMin(BigDecimal.valueOf(Double.valueOf(feeMin)));
		tblAgentFeeTmp.setFeeValue(BigDecimal.valueOf(Double.valueOf(feeValue)));
		tblAgentFeeTmp.setMccCode(mccCode);
		tblAgentFeeTmp.setExtend1(extend1);
		tblAgentFeeTmp.setFeeType(feeType);
		tblAgentFeeTmp.setAgentNo(agentNo);
		tblAgentFeeTmp.setState("0");
		System.out.println("feeValue="+feeValue);
		tblAgentFeeTmp.setCrtDate(CommonFunction.getCurrentDateTime());
		System.out.println("date ="+CommonFunction.getCurrentTime());
		tblAgentFeeTmp.setCrtPer(operator.getOprId());
		String sql = "select count(1) from tbl_agent_fee_tmp where agent_no = '"+ agentNo +"' and mcc_code = '"+ mccCode+"' and extend1 ='"+extend1+"' and state != '1' ";
		BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
		if(count.intValue() != 0){
			return "该条记录已存在！";
		}
		if(Double.valueOf(feeMax)<Double.valueOf(feeMin)){
			return "上限小于下限!";
		}
		rspCode=t11521BO.add(tblAgentFeeTmp);
		
		return rspCode;
	}

	public String update() throws Exception{
		jsonBean.parseJSONArrayData(getAmtList());
		int len = jsonBean.getArray().size();
		TblAgentFeeTmp tblAgentFeeTmp = new TblAgentFeeTmp();
		List<TblAgentFeeTmp> tblAgentFeeTmpList = new ArrayList<TblAgentFeeTmp>();
		try{
			for(int i= 0;i<len;i++){
				tblAgentFeeTmp = t11521BO.get(jsonBean.getJSONDataAt(i).getString("uuId"));
				BeanUtils.setObjectWithPropertiesValue(tblAgentFeeTmp, jsonBean, false);
				if(tblAgentFeeTmp.getFeeMax().compareTo(tblAgentFeeTmp.getFeeMin())<0){
					return "上限小于下限!";
				}
				tblAgentFeeTmp.setState("3");
				tblAgentFeeTmp.setCrtDate(CommonFunction.getCurrentDateTime());
				tblAgentFeeTmp.setCrtPer(operator.getOprId());
				rspCode = t11521BO.update(tblAgentFeeTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return rspCode;
	}
	
	
	private String agentNo;       //代理商编号
    private String agentNm;       //代理商名称
    private String feeMin;  
    private String feeMax;
    private String mccCode;
    private String feeValue;       
    private String feeType;        
    private String state;    
    private String crtPer;   
    private String crtDate;   
    private String upPer;  
    private String upDate;  
    private String extend1;    
    private String extend2;    
    private String extend3;
    private String extend4;    
    private String extend5;
    private String uuId;
    private String amtList;
    
    
    
	public String getAmtList() {
		return amtList;
	}



	public void setAmtList(String amtList) {
		this.amtList = amtList;
	}



	public String getUuId() {
		return uuId;
	}



	public void setUuId(String uuId) {
		this.uuId = uuId;
	}



	public String getMccCode() {
		return mccCode;
	}



	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}



	public T11521BO getT11521BO() {
		return t11521BO;
	}



	public void setT11521BO(T11521BO t11521bo) {
		t11521BO = t11521bo;
	}



	public String getAgentNo() {
		return agentNo;
	}



	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}



	public String getAgentNm() {
		return agentNm;
	}



	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}



	public String getFeeMin() {
		return feeMin;
	}



	public void setFeeMin(String feeMin) {
		this.feeMin = feeMin;
	}



	public String getFeeMax() {
		return feeMax;
	}



	public void setFeeMax(String feeMax) {
		this.feeMax = feeMax;
	}



	public String getFeeValue() {
		return feeValue;
	}



	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}



	public String getFeeType() {
		return feeType;
	}



	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCrtPer() {
		return crtPer;
	}



	public void setCrtPer(String crtPer) {
		this.crtPer = crtPer;
	}



	public String getCrtDate() {
		return crtDate;
	}



	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}



	public String getUpPer() {
		return upPer;
	}



	public void setUpPer(String upPer) {
		this.upPer = upPer;
	}



	public String getUpDate() {
		return upDate;
	}



	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}



	public String getExtend1() {
		return extend1;
	}



	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}



	public String getExtend2() {
		return extend2;
	}



	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}



	public String getExtend3() {
		return extend3;
	}



	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}



	public String getExtend4() {
		return extend4;
	}



	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}



	public String getExtend5() {
		return extend5;
	}



	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}   

    

}
