package com.huateng.struts.risk.action;

import com.huateng.bo.risk.T40501BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.CstAfterRuleInf;
import com.huateng.po.risk.CstAfterRuleInfPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T40501Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	T40501BO t40501BO = (T40501BO) ContextUtil.getBean("T40501BO");
	
	private String ruleId;
	private String instId;
	private String mcc;
	private String days;
	private String warnLvt;
	private String warnCount;
	private String warnAmt;
	private String saState;
	private String daysOld;
	private String warnLvtOld;
	private String warnCountOld;
	private String warnAmtOld;
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	// 修改集
	private String cstAfterRuleInfList;
	
	public String getCstAfterRuleInfList() {
		return cstAfterRuleInfList;
	}

	public void setCstAfterRuleInfList(String cstAfterRuleInfList) {
		this.cstAfterRuleInfList = cstAfterRuleInfList;
	}

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户事后风险预警系数");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新商户事后风险预警系数");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除商户事后风险预警系数");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 添加商户事后风险预警系数
	 * @return
	 * 2012-4-28
	 * @throws Exception 
	 */
	public String add() throws Exception{
		CstAfterRuleInf cstAfterRuleInf = new CstAfterRuleInf();
		CstAfterRuleInfPK id = new CstAfterRuleInfPK();
		id.setRuleId(getRuleId());
		
		id.setInstId(getInstId());
		id.setMcc(getMcc());
		/*if(getMcc()==null || getMcc().equals("")){
			id.setMcc("*");
			id.setInstId(getInstId());
		}
		if(getInstId()==null || getInstId().equals("")){
			id.setInstId("*");
			id.setMcc(getMcc());
		}*/
		
		cstAfterRuleInf.setId(id);
		cstAfterRuleInf.setDaysOld(getDays());
		cstAfterRuleInf.setWarnLvtOld(getWarnLvt());
		cstAfterRuleInf.setWarnCountOld(getWarnCount());
		cstAfterRuleInf.setWarnAmtOld(getWarnAmt());
		cstAfterRuleInf.setDays(getDays());
		cstAfterRuleInf.setWarnLvt(getWarnLvt());
		cstAfterRuleInf.setWarnCount(getWarnCount());
		cstAfterRuleInf.setWarnAmt(getWarnAmt());
		cstAfterRuleInf.setCaseid(GenerateNextId.getCaseId(ruleId));
		cstAfterRuleInf.setSaState(ADD_TO_CHECK);
		cstAfterRuleInf.setCreator(operator.getOprId());
		cstAfterRuleInf.setCreateTime(CommonFunction.getCurrentDateTime());
		cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
		cstAfterRuleInf.setUpdateOpr(operator.getOprId());
		
	    t40501BO.add(cstAfterRuleInf);
		return Constants.SUCCESS_CODE;
	}

	/**删除商户事后风险预警系数
	 * delete city code
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception {
		CstAfterRuleInfPK key = new CstAfterRuleInfPK();
		key.setRuleId(getRuleId());
		key.setInstId(getInstId());
		key.setMcc(getMcc());
		
		CstAfterRuleInf cstAfterRuleInf = t40501BO.get(key);
		if(cstAfterRuleInf.getSaState().equals(DELETE)) {
			return "该商户事后风险预警系数已是删除状态，请勿重复删除";
		}
		if(ADD_TO_CHECK.equals(cstAfterRuleInf.getSaState())){
			t40501BO.delete(key);
		}else{
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			cstAfterRuleInf.setSaState(DELETE_TO_CHECK);
			t40501BO.update(cstAfterRuleInf);
		}
		return Constants.SUCCESS_CODE;
	}

	/**更新商户事后风险预警系数
	 * @return
	 * @throws Exception 
	*/
	public String update() throws Exception {		
		jsonBean.parseJSONArrayData(getCstAfterRuleInfList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			//CstAfterRuleInf cstAfterRuleInf = new CstAfterRuleInf();
			CstAfterRuleInfPK cstAfterRuleInfPK = new CstAfterRuleInfPK();
			//BeanUtils.setObjectWithPropertiesValue(cstAfterRuleInf,jsonBean,true);
			cstAfterRuleInfPK.setRuleId((String)(jsonBean.getElementByKey("ruleId")));
			cstAfterRuleInfPK.setInstId((String)(jsonBean.getElementByKey("instId")));
			cstAfterRuleInfPK.setMcc((String)(jsonBean.getElementByKey("mcc")));
			CstAfterRuleInf cstAfterRuleInf=t40501BO.get(cstAfterRuleInfPK);
			//cstAfterRuleInf.setId(cstAfterRuleInfPK);
			
			cstAfterRuleInf.setDaysOld((String)(jsonBean.getElementByKey("days")));
			cstAfterRuleInf.setWarnLvtOld((String)(jsonBean.getElementByKey("warnLvt")));
			cstAfterRuleInf.setWarnCountOld((String)(jsonBean.getElementByKey("warnCount")));
			cstAfterRuleInf.setWarnAmtOld((String)(jsonBean.getElementByKey("warnAmt")));
			cstAfterRuleInf.setSaState((String)(jsonBean.getElementByKey("saState")));
			
		//	cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			if(cstAfterRuleInf.getSaState().equals(NORMAL))
				cstAfterRuleInf.setSaState(MODIFY_TO_CHECK);
		//	cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			
			/*CstAfterRuleInf cstAfterRuleInfOld = t40501BO.get(cstAfterRuleInf.getId());
			cstAfterRuleInf.setCaseid(cstAfterRuleInfOld.getCaseid());
			cstAfterRuleInf.setDaysOld(cstAfterRuleInfOld.getDays());
			cstAfterRuleInf.setWarnLvtOld(cstAfterRuleInfOld.getWarnLvt());
			cstAfterRuleInf.setWarnCountOld(cstAfterRuleInfOld.getWarnCount());
			cstAfterRuleInf.setWarnAmtOld(cstAfterRuleInfOld.getWarnAmt());*/
//			cstAfterRuleInf.setCreateTime(CommonFunction.getCurrentDateTime());
//			cstAfterRuleInf.setCreator(operator.getOprId());
			cstAfterRuleInf.setUpdateTime(CommonFunction.getCurrentDateTime());
			cstAfterRuleInf.setUpdateOpr(operator.getOprId());
			
			t40501BO.update(cstAfterRuleInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	public T40501BO getT40501BO() {
		return t40501BO;
	}

	public void setT40501BO(T40501BO t40501bo) {
		t40501BO = t40501bo;
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

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getWarnLvt() {
		return warnLvt;
	}

	public void setWarnLvt(String warnLvt) {
		this.warnLvt = warnLvt;
	}

	public String getWarnCount() {
		return warnCount;
	}

	public void setWarnCount(String warnCount) {
		this.warnCount = warnCount;
	}

	public String getWarnAmt() {
		return warnAmt;
	}

	public void setWarnAmt(String warnAmt) {
		this.warnAmt = warnAmt;
	}

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getDaysOld() {
		return daysOld;
	}

	public void setDaysOld(String daysOld) {
		this.daysOld = daysOld;
	}

	public String getWarnLvtOld() {
		return warnLvtOld;
	}

	public void setWarnLvtOld(String warnLvtOld) {
		this.warnLvtOld = warnLvtOld;
	}

	public String getWarnCountOld() {
		return warnCountOld;
	}

	public void setWarnCountOld(String warnCountOld) {
		this.warnCountOld = warnCountOld;
	}

	public String getWarnAmtOld() {
		return warnAmtOld;
	}

	public void setWarnAmtOld(String warnAmtOld) {
		this.warnAmtOld = warnAmtOld;
	}

}
