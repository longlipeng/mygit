/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-20       first release
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
package com.huateng.bo.impl.risk;

import com.huateng.bo.risk.T40101BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblRiskInfDAO;
import com.huateng.dao.iface.risk.TblRiskInfUpdLogDAO;
import com.huateng.po.TblRiskInf;
import com.huateng.po.TblRiskInfUpdLog;
import com.huateng.system.util.CommonFunction;

/**
 * Title:设置风险模型
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T40101BOTarget implements T40101BO {
	
	private TblRiskInfDAO tblRiskInfDAO;
	private TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T40101BO#update(com.huateng.po.TblRiskInf)
	 */
	public String update(TblRiskInf tblRiskInfNew) throws Exception {
		//tblRiskInfNew.setSaLimitAmount(tblRiskInfNew.getSaLimitAmount());
		tblRiskInfDAO.update(tblRiskInfNew);
		return Constants.SUCCESS_CODE;
	}

	public TblRiskInf get(String key) {
		return tblRiskInfDAO.get(key);
	}
	
	/**
	 * @param tblRiskInfDAO the tblRiskInfDAO to set
	 */
	public void setTblRiskInfDAO(TblRiskInfDAO tblRiskInfDAO) {
		this.tblRiskInfDAO = tblRiskInfDAO;
	}

	/**
	 * @param tblRiskInfUpdLogDAO the tblRiskInfUpdLogDAO to set
	 */
	public void setTblRiskInfUpdLogDAO(TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO) {
		this.tblRiskInfUpdLogDAO = tblRiskInfUpdLogDAO;
	}

	public String check(TblRiskInf tblRiskInf,Operator operator) throws Exception {
		tblRiskInfDAO.update(tblRiskInf);//审核
		return Constants.SUCCESS_CODE;
	}

	public boolean recordModify(TblRiskInf tblRiskInf, Operator operator) throws Exception {
		boolean result = false;
		//保存历史修改记录
		if(!tblRiskInf.getSaDaysModify().trim().equals(tblRiskInf.getSaDays().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
			tblRiskInfUpdLog.setSaFieldName("saDays");
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskInf.getSaDays());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskInf.getSaDaysModify());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		if(!tblRiskInf.getSaLimitNumModify().trim().equals(tblRiskInf.getSaLimitNum().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
			tblRiskInfUpdLog.setSaFieldName("saLimitNum");
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskInf.getSaLimitNum());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskInf.getSaLimitNumModify());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		if(!tblRiskInf.getSaLimitAmountModify().trim().equals(tblRiskInf.getSaLimitAmount().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
			tblRiskInfUpdLog.setSaFieldName("saLimitAmount");
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskInf.getSaLimitAmount());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskInf.getSaLimitAmountModify());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		if(!tblRiskInf.getSaActionModify().trim().equals(tblRiskInf.getSaAction().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
			tblRiskInfUpdLog.setSaFieldName("saAction");
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskInf.getSaAction());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskInf.getSaActionModify());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		if(!tblRiskInf.getSaBeUseModify().trim().equals(tblRiskInf.getSaBeUse().trim())){
			TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
			tblRiskInfUpdLog.setSaModelKind(tblRiskInf.getId());
			tblRiskInfUpdLog.setSaFieldName("saBeUse");
			tblRiskInfUpdLog.setSaFieldValueBF(tblRiskInf.getSaBeUse());
			tblRiskInfUpdLog.setSaFieldValue(tblRiskInf.getSaBeUseModify());
			tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
			tblRiskInfUpdLog.setModiOprId(operator.getOprId());
			tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
			tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
		}
		result = true;
		return result;
	}

	public String insert(TblRiskInf tblRiskInf) throws Exception {
		tblRiskInfDAO.save(tblRiskInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblRiskInf tblRiskInf) throws Exception {
		tblRiskInfDAO.delete(tblRiskInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String saModelKind) {
		// TODO Auto-generated method stub
		tblRiskInfDAO.delete(saModelKind);
		return Constants.SUCCESS_CODE;
	}
	
	
	
	
}
