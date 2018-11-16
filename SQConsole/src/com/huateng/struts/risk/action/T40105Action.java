package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40101BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Operator;
import com.huateng.po.TblRiskInf;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40105Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	T40101BO t40101bo = (T40101BO) ContextUtil.getBean("T40101BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		if("accept".equals(method)) {
			log("审核通过修改待审核的风险监控模型");
			rspCode = acceptModify();
		} else if("refuse".equals(method)) {
			log("审核拒绝修改待审核的风险监控模型");
			rspCode = refuseModify();
		}
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的风险监控模型
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "操作员无审核风险监控模型权限，请确认。";
		else{
			TblRiskInf tblRiskInf = t40101bo.get(saModelKind);
			
			Operator exOperator = new Operator();
			exOperator.setOprId(tblRiskInf.getModiOprId());
			exOperator.setOprBrhId(tblRiskInf.getModiZoneNo());
			t40101bo.recordModify(tblRiskInf, exOperator);//保存修改记录
			
			tblRiskInf.setSaDays(tblRiskInf.getSaDaysModify());
			tblRiskInf.setSaBeUse(tblRiskInf.getSaBeUseModify());
			tblRiskInf.setSaAction(tblRiskInf.getSaActionModify());
			tblRiskInf.setSaLimitNum(tblRiskInf.getSaLimitNumModify());
			tblRiskInf.setSaLimitAmount(tblRiskInf.getSaLimitAmountModify());
			tblRiskInf.setModiZoneNo(operator.getOprBrhId());
			tblRiskInf.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInf.setModiOprId(operator.getOprId());
			tblRiskInf.setSaState(NORMAL);
			
			rspCode = t40101bo.check(tblRiskInf,operator);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝修改待审核的风险监控模型
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		TblRiskInf tblRiskInf = new TblRiskInf();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblRiskInf = t40101bo.get(saModelKind);
			tblRiskInf.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInf.setModiZoneNo(operator.getOprBrhId());
			tblRiskInf.setModiOprId(operator.getOprId());
			tblRiskInf.setSaState(NORMAL);
			//20120807审核拒绝后，修改前的数据无法显示在列表中的问题
			tblRiskInf.setSaDaysModify(tblRiskInf.getSaDays());
			tblRiskInf.setSaBeUseModify(tblRiskInf.getSaBeUse());
			tblRiskInf.setSaActionModify(tblRiskInf.getSaAction());
			tblRiskInf.setSaLimitNumModify(tblRiskInf.getSaLimitNum());
			tblRiskInf.setSaLimitAmountModify(tblRiskInf.getSaLimitAmount());
			
			rspCode = t40101bo.check(tblRiskInf,operator);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblRiskInf.getSaDays());
		riskRefuse.setParam2(tblRiskInf.getSaBeUse());
		riskRefuse.setParam3(tblRiskInf.getSaAction());
		riskRefuse.setParam4(tblRiskInf.getSaLimitNum());
		riskRefuse.setParam5(tblRiskInf.getSaLimitAmount());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("10");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	//判断风险监控模型的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblRiskInf tblRiskInf = t40101bo.get(saModelKind);
		String oprID = tblRiskInf.getModiOprId();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	// 
	private String saModelKind;
	//拒绝原因
	private String refuseInfo;

	public String getRefuseInfo() {
		return refuseInfo;
	}
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getSaModelKind() {
		return saModelKind;
	}

	public void setSaModelKind(String saModelKind) {
		this.saModelKind = saModelKind;
	}
	
}
