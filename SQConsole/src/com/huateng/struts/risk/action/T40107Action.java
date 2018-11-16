package com.huateng.struts.risk.action;

import java.math.BigDecimal;

import com.huateng.bo.risk.T40101BO;
import com.huateng.common.Constants;
import com.huateng.po.TblRiskInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;


@SuppressWarnings("serial")
public class T40107Action extends BaseAction {
	
	T40101BO t40101BO = (T40101BO) ContextUtil.getBean("T40101BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加风险规则");
			rspCode = add();
		}else if("update".equals(method)) {
			log("更新风险规则");
			rspCode = update();
		}else if("delete".equals(method)) {
			log("删除风险规则");
			rspCode = delete();
		}else {
			return "未知的交易类型";
		} 
		
		return rspCode;
	}
	
	private String add() throws Exception{
		if(t40101BO.get(saModelKind)!=null){
			return "该模型已存在";
		}
		TblRiskInf tblRiskInf = new TblRiskInf();
		BigDecimal bigDecimal2 = new BigDecimal(100); 
		BigDecimal saAmt = BigDecimal.valueOf(Double.valueOf(saLimitAmount)).multiply(bigDecimal2);
		String amt = saAmt.toString();
		String AmtTransSpt = amt.substring(0, amt.indexOf("."));
		String str ="000000000000";
		AmtTransSpt=str.substring(0, 12-AmtTransSpt.length())+AmtTransSpt;
		tblRiskInf.setId(saModelKind);
		tblRiskInf.setSaModelName(saModelName);
		tblRiskInf.setSaLimitNum(saLimitNum);
		tblRiskInf.setSaLimitAmount(AmtTransSpt);
		tblRiskInf.setSaBeUse(saBeUse);
		tblRiskInf.setSaWarnlvt(Integer.parseInt(saAction));
		tblRiskInf.setSaAction(saAction);
		System.out.println("受限金额"+saAmt);
		System.out.println("受限金额"+amt);
	

		return t40101BO.insert(tblRiskInf);
	}
	
	
	
	private String update() throws Exception {
	
		jsonBean.parseJSONArrayData(getModelDataList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblRiskInf tblRiskInf = new TblRiskInf();
			BeanUtils.setObjectWithPropertiesValue(tblRiskInf,jsonBean,true);
			String saLimitAmount =jsonBean.getJSONDataAt(i).getString("saLimitAmount");
			BigDecimal bigDecimal2 = new BigDecimal(100); 
			BigDecimal saAmt = BigDecimal.valueOf(Double.valueOf(saLimitAmount)).multiply(bigDecimal2);
			String amt = saAmt.toString();
			String AmtTransSpt = amt.substring(0, amt.indexOf("."));
			String str ="000000000000";
			AmtTransSpt=str.substring(0, 12-AmtTransSpt.length())+AmtTransSpt;
			//tblRiskInf.setSaLimitAmount(CommonFunction.transYuanToFen(tblRiskInf.getSaLimitAmount()));
			tblRiskInf.setModiZoneNo(operator.getOprBrhId());
			tblRiskInf.setSaLimitAmount(AmtTransSpt);
			tblRiskInf.setSaWarnlvt(Integer.parseInt(tblRiskInf.getSaAction()));
			tblRiskInf.setModiOprId(operator.getOprId());
			tblRiskInf.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInf.setSaState("3");

			t40101BO.update(tblRiskInf);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	
	private String delete() throws Exception{
		
		if(t40101BO.get(saModelKind)==null){
			return "该模型不存在";
		}
		return t40101BO.delete(saModelKind);
	}
	
	
	
	private String saModelKind;
	
	private String saModelName;
	
	private String saWarnlvt;
	
	private String saBeUse;
	
	private String saAction;
	
	private String saLimitNum;
	
	private String saLimitAmount;
	
	private String modelDataList;
	
	
	
	public String getSaWarnlvt() {
		return saWarnlvt;
	}

	public void setSaWarnlvt(String saWarnlvt) {
		this.saWarnlvt = saWarnlvt;
	}

	public String getSaModelName() {
		return saModelName;
	}

	public void setSaModelName(String saModelName) {
		this.saModelName = saModelName;
	}

	/**
	 * @return the saModelKind
	 */
	public String getSaModelKind() {
		return saModelKind;
	}

	/**
	 * @param saModelKind the saModelKind to set
	 */
	public void setSaModelKind(String saModelKind) {
		this.saModelKind = saModelKind;
	}

	/**
	 * @return the saBeUse
	 */
	public String getSaBeUse() {
		return saBeUse;
	}

	/**
	 * @param saBeUse the saBeUse to set
	 */
	public void setSaBeUse(String saBeUse) {
		this.saBeUse = saBeUse;
	}

	/**
	 * @return the saAction
	 */
	public String getSaAction() {
		return saAction;
	}

	/**
	 * @param saAction the saAction to set
	 */
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the saLimitNum
	 */
	public String getSaLimitNum() {
		return saLimitNum;
	}

	/**
	 * @param saLimitNum the saLimitNum to set
	 */
	public void setSaLimitNum(String saLimitNum) {
		this.saLimitNum = saLimitNum;
	}

	/**
	 * @return the saLimitAmount
	 */
	public String getSaLimitAmount() {
		return saLimitAmount;
	}

	/**
	 * @param saLimitAmount the saLimitAmount to set
	 */
	public void setSaLimitAmount(String saLimitAmount) {
		this.saLimitAmount = saLimitAmount;
	}

	/**
	 * @return the modelDataList
	 */
	public String getModelDataList() {
		return modelDataList;
	}

	/**
	 * @param modelDataList the modelDataList to set
	 */
	public void setModelDataList(String modelDataList) {
		this.modelDataList = modelDataList;
	}
	
}
