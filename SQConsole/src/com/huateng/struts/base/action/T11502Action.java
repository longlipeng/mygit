package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T11502BO;
import com.huateng.bo.base.T11503BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.TblAgentDivide;
import com.huateng.po.base.TblAgentDivideTmp;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;




/**
 * Title: 代理商分润信息管理
 * Copyright: Copyright (c) 2014-08-11
 * @author shiyiwen
 * 
 */
@SuppressWarnings("serial")
public class T11502Action extends BaseAction {
	
	T11503BO t11503BO = (T11503BO) ContextUtil.getBean("T11503BO");
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加代理商分润信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新代理商分润信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除代理商分润信息");
			rspCode = deleteList();
		} 
		return rspCode;
	}
	

	private String deleteList() throws Exception {
		jsonBean.parseJSONArrayData(getDivideList());
		int len = jsonBean.getArray().size();
	//	List<String> list = new ArrayList<String>();
		TblAgentDivideTmp tblAgentDivideTmp = null;
		List<TblAgentDivideTmp> list = new ArrayList<TblAgentDivideTmp>();
		String uuid = "";
		
		for(int i = 0; i < len; i++) {				
		//	jsonBean.setObject(jsonBean.getJSONDataAt(i));
				
			uuid = jsonBean.getJSONDataAt(i).getString("uuid");
			tblAgentDivideTmp = t11503BO.query(uuid);
			tblAgentDivideTmp.setState("4");  //把状态置为删除待审核
			tblAgentDivideTmp.setCrtPer(operator.getOprId());
			tblAgentDivideTmp.setCrtDate(CommonFunction.getCurrentDateTime());
			
			list.add(tblAgentDivideTmp);
			
		 
		//	BeanUtils.setObjectWithPropertiesValue(uuid,jsonBean,false);
		  
		}
		rspCode = t11503BO.update(list);
		
		
		return Constants.SUCCESS_CODE;
	}
	
	

	private String add() throws Exception {
		
		TblAgentDivideTmp tblAgentDivideTmp = new TblAgentDivideTmp();
		
		if("1".equals(divideType)){
			String sql = "select count(*) from tbl_agent_divide_tmp where agent_no = '"+agentNo+"' and divide_type ='1' and state !='1'";
			String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
			if(!"0".equals(count)){
       	       return "该代理商按交易金额已存在";
            }

		}

		tblAgentDivideTmp.setUuid(StringUtil.getUUID());
		tblAgentDivideTmp.setAgentNo(agentNo);
		tblAgentDivideTmp.setDivideType(divideType);
		tblAgentDivideTmp.setDiscCd(discCd);
		tblAgentDivideTmp.setProfit(profit);
		tblAgentDivideTmp.setMinValue(minValue*10000);  //万元转到元
		tblAgentDivideTmp.setMaxValue(maxValue*10000);   //万元转到元
		tblAgentDivideTmp.setState("0");  //新增未审核
		tblAgentDivideTmp.setCrtPer(operator.getOprId());
		tblAgentDivideTmp.setCrtDate(CommonFunction.getCurrentDateTime());
	
		rspCode = t11503BO.save(tblAgentDivideTmp);
		return rspCode;
	}
	
	/**
	 * 修改代理商分润信息(只是针对新增待审核的记录进行修改)
	 * @return
	 * 2014-08-04
	 * @throws Exception 
	 */
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getDivideList());		
		int len = jsonBean.getArray().size();
	//	List<TblAgentDivide> list = new ArrayList<TblAgentDivide>();
		TblAgentDivideTmp tblAgentDivideTmp = null;
		List<TblAgentDivideTmp> list = new ArrayList<TblAgentDivideTmp>();
		String uuid = "";
		
		for(int i = 0; i < len; i++) {				
		//	jsonBean.setObject(jsonBean.getJSONDataAt(i));
			uuid = jsonBean.getJSONDataAt(i).getString("uuid");		
	//		BeanUtils.setObjectWithPropertiesValue(pk,jsonBean,false)	
			tblAgentDivideTmp = t11503BO.query(uuid);
			if(Double.valueOf(jsonBean.getJSONDataAt(i).getString("minValue")) > Double.valueOf(jsonBean.getJSONDataAt(i).getString("maxValue"))){
				return "代理商"+tblAgentDivideTmp.getAgentNo()+"下限值大于上限值,请重新修改!";
			}
			tblAgentDivideTmp.setProfit(jsonBean.getJSONDataAt(i).getString("profit"));
			tblAgentDivideTmp.setMinValue(Double.valueOf(jsonBean.getJSONDataAt(i).getString("minValue"))*10000);
			tblAgentDivideTmp.setMaxValue(Double.valueOf(jsonBean.getJSONDataAt(i).getString("maxValue"))*10000);
			tblAgentDivideTmp.setState("0");  //状态为新增待审核 不变
		//	tblAgentDivideTmp.setCrtPer(operator.getOprId());
		//	tblAgentDivideTmp.setCrtDate(CommonFunction.getCurrentDateTime());
			/*TblAgentDivide tblAgentDivide = new TblAgentDivide();
			tblAgentDivide.setPrimaryKey(pk);
			
			BeanUtils.setObjectWithPropertiesValue(tblAgentDivide,jsonBean,false);*/
            
			list.add(tblAgentDivideTmp);
		  
		}
		rspCode = t11503BO.update(list);
		return Constants.SUCCESS_CODE;
	}
	
	
	
	private String agentNo;   
	private String divideType;
	private String discCd;
	private String profit;
	private String divideList;
	private double minValue;
	private double maxValue;
	
	public String getAgentNo() {
		return agentNo;
	}


	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}


	public String getDivideType() {
		return divideType;
	}


	public void setDivideType(String divideType) {
		this.divideType = divideType;
	}


	public String getDiscCd() {
		return discCd;
	}


	public void setDiscCd(String discCd) {
		this.discCd = discCd;
	}


	public String getProfit() {
		return profit;
	}


	public void setProfit(String profit) {
		this.profit = profit;
	}


	public String getDivideList() {
		return divideList;
	}


	public void setDivideList(String divideList) {
		this.divideList = divideList;
	}


	public double getMinValue() {
		return minValue;
	}


	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}


	public double getMaxValue() {
		return maxValue;
	}


	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
    


	
	
}
