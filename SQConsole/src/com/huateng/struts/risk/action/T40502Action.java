package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T40501BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.CstAfterRuleInf;
import com.huateng.po.risk.CstAfterRuleInfPK;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40502Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40501BO t40501bo = (T40501BO) ContextUtil.getBean("T40501BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	private String saState;
	private String ruleId;
	private String instId;
	private String mcc;
	
	private String refuseInfo;//拒绝原因
	
	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		String state = this.getSaState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的商户事后风险预警系数");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的商户事后风险预警系数");
				rspCode = acceptDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的商户事后风险预警系数");
				rspCode = acceptModify();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的商户事后风险预警系数");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的商户事后风险预警系数");
				rspCode = refuseDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝修改待审核的商户事后风险预警系数");
				rspCode = refuseModify();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的商户事后风险预警系数
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			CstAfterRuleInf cstAfterRuleInf = new CstAfterRuleInf();
			CstAfterRuleInfPK id = new CstAfterRuleInfPK();
			id.setRuleId(getRuleId());
			id.setInstId(getInstId());
			id.setMcc(getMcc());
			
			cstAfterRuleInf = t40501bo.get(id);
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			cstAfterRuleInf.setSaState(NORMAL);
			
			rspCode = t40501bo.update(cstAfterRuleInf);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的商户事后风险预警系数
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		CstAfterRuleInf cstAfterRuleInf = new CstAfterRuleInf();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			CstAfterRuleInfPK id = new CstAfterRuleInfPK();
			id.setRuleId(getRuleId());
			id.setInstId(getInstId());
			id.setMcc(getMcc());
			cstAfterRuleInf = t40501bo.get(id);
			t40501bo.delete(id);
		}
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(cstAfterRuleInf.getId().getRuleId());
		riskRefuse.setParam2(cstAfterRuleInf.getId().getInstId());
		riskRefuse.setParam3(cstAfterRuleInf.getId().getMcc());
		riskRefuse.setParam4(handleDecimal(cstAfterRuleInf.getWarnLvt()));
		riskRefuse.setParam5(cstAfterRuleInf.getWarnCount());
		riskRefuse.setParam6(handleDecimal(cstAfterRuleInf.getWarnAmt()));
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("11");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的商户事后风险预警系数
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			CstAfterRuleInfPK id = new CstAfterRuleInfPK();
			id.setRuleId(getRuleId());
			id.setInstId(getInstId());
			id.setMcc(getMcc());
			CstAfterRuleInf cstAfterRuleInf = t40501bo.get(id);
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			cstAfterRuleInf.setSaState(DELETE);
			
			rspCode = t40501bo.update(cstAfterRuleInf);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的商户事后风险预警系数
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		CstAfterRuleInf cstAfterRuleInf = new CstAfterRuleInf();
		String ruleId= "";
		String instId ="";
		String mcc ="";
		String warnLvt = "";
		String warnCount ="";
		String warnAmt ="";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			CstAfterRuleInfPK id = new CstAfterRuleInfPK();
			id.setRuleId(getRuleId());
			id.setInstId(getInstId());
			id.setMcc(getMcc());
			cstAfterRuleInf = t40501bo.get(id);
			
			ruleId = cstAfterRuleInf.getId().getRuleId();
			instId = cstAfterRuleInf.getId().getInstId();
			mcc = cstAfterRuleInf.getId().getMcc();
			warnLvt = cstAfterRuleInf.getWarnLvt();
			warnCount = cstAfterRuleInf.getWarnCount();
			warnAmt = cstAfterRuleInf.getWarnAmt();
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			cstAfterRuleInf.setSaState(NORMAL);
			rspCode = t40501bo.update(cstAfterRuleInf);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(ruleId);
		riskRefuse.setParam2(instId);
		riskRefuse.setParam3(mcc);
		riskRefuse.setParam4(handleDecimal(warnLvt));
		riskRefuse.setParam5(warnCount);
		riskRefuse.setParam6(handleDecimal(warnAmt));
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("11");//商户事后风险预警系数审核
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	/**
	 * 审核通过修改待审核的商户事后风险预警系数
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "操作员无审核商户事后风险预警系数权限，请确认。";
		else{
			CstAfterRuleInfPK id = new CstAfterRuleInfPK();
			id.setRuleId(getRuleId());
			id.setInstId(getInstId());
			id.setMcc(getMcc());
			CstAfterRuleInf cstAfterRuleInf = t40501bo.get(id);
			cstAfterRuleInf.setDays(cstAfterRuleInf.getDaysOld());
			cstAfterRuleInf.setWarnLvt(cstAfterRuleInf.getWarnLvtOld());
			cstAfterRuleInf.setWarnCount(cstAfterRuleInf.getWarnCountOld());
			cstAfterRuleInf.setWarnAmt(cstAfterRuleInf.getWarnAmtOld());
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			cstAfterRuleInf.setSaState(NORMAL);
			
			rspCode = t40501bo.update(cstAfterRuleInf);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝修改待审核的商户事后风险预警系数
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		CstAfterRuleInf cstAfterRuleInf = new CstAfterRuleInf();
		String ruleId= "";
		String instId ="";
		String mcc ="";
		String warnLvt = "";
		String warnCount ="";
		String warnAmt ="";
		String days ="";
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			CstAfterRuleInfPK id = new CstAfterRuleInfPK();
			id.setRuleId(getRuleId());
			id.setInstId(getInstId());
			id.setMcc(getMcc());
			cstAfterRuleInf = t40501bo.get(id);
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			cstAfterRuleInf.setSaState(NORMAL);
			
			ruleId = cstAfterRuleInf.getId().getRuleId();
			instId = cstAfterRuleInf.getId().getInstId();
			mcc = cstAfterRuleInf.getId().getMcc();
			warnLvt = cstAfterRuleInf.getWarnLvtOld();
			warnCount = cstAfterRuleInf.getWarnCountOld();
			warnAmt = cstAfterRuleInf.getWarnAmtOld();
			days = cstAfterRuleInf.getDaysOld();
			
			cstAfterRuleInf.setWarnLvtOld(cstAfterRuleInf.getWarnLvt());
			cstAfterRuleInf.setWarnCountOld(cstAfterRuleInf.getWarnCount());
			cstAfterRuleInf.setWarnAmtOld(cstAfterRuleInf.getWarnAmt());
			cstAfterRuleInf.setDaysOld(cstAfterRuleInf.getDays());
			
			rspCode = t40501bo.update(cstAfterRuleInf);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(ruleId);
		riskRefuse.setParam2(instId);
		riskRefuse.setParam3(mcc);
		riskRefuse.setParam4(handleDecimal(warnLvt));
		riskRefuse.setParam5(warnCount);
		riskRefuse.setParam6(handleDecimal(warnAmt));
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("11");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return rspCode;
	}
	
	//判断商户事后风险预警系数的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		CstAfterRuleInfPK id = new CstAfterRuleInfPK();
		id.setRuleId(getRuleId());
		id.setInstId(getInstId());
		id.setMcc(getMcc());
		
		CstAfterRuleInf cstAfterRuleInf = t40501bo.get(id);
//		String oprID = cstAfterRuleInf.getCreator();
		String oprID = cstAfterRuleInf.getUpdateOpr();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	private String handleDecimal(String number) throws Exception{
		if(number.startsWith(".")){
			return "0"+number;
		}
		return number;
	}
}
