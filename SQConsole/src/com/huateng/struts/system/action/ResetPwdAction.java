/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-31       first release
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
package com.huateng.struts.system.action;

import org.springframework.beans.BeanUtils;

import com.huateng.common.SysParamConstants;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 操作员授权码修改
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResetPwdAction extends BaseAction {

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		log("操作员[ " + resetOprId + " ]修改授权码");
		
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(resetOprId);
		
		// 判断原授权码是否输入正确
		if(!tblOprInfo.getOprPwd().trim().equals(Encryption.encrypt(resetPassword).trim())) {
			log("原始授权码输入不正确");
			writeErrorMsg("原授权码输入不正确");
			return SUCCESS;
		}
		
		//
		if(Encryption.encrypt(resetOprId).trim().equals(Encryption.encrypt(resetPassword1).trim())){
			log("新授权码不可包含操作员编号");
			writeErrorMsg("新授权码不可包含操作员编号");
			return SUCCESS;
		}
		
		tblOprInfo.setOprPwd(Encryption.encrypt(resetPassword1).trim());
		tblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		tblOprInfo.setPwdWrLastDt("-");
		tblOprInfo.setPwdWrTm("0");
		tblOprInfo.setPwdWrTmTotal("0");

		tblOprInfo.setResv2("0");
		
//		BeanUtils.copyProperties(tblOprInfo, tblOprInfoTmp);
		
		String sql = "delete from TBL_OPR_INFO_TMP where OPR_ID = '" + tblOprInfo.getId() + "'";
		commQueryDAO.excute(sql);
		
		if(tblOprInfo.getOprTel() == null || tblOprInfo.getOprTel().isEmpty() || 
		   tblOprInfo.getOprMobile() == null || tblOprInfo.getOprMobile().isEmpty() ||
		   tblOprInfo.getOprEmail() == null || tblOprInfo.getOprEmail().isEmpty()){
			String sql1 = "insert into TBL_OPR_INFO_TMP(OPR_ID, BRH_ID, OPR_DEGREE, OPR_DEGREE_RSC, OPR_STA, OPR_LOG_STA, OPR_NAME, OPR_GENDER, REGISTER_DT, OPR_PWD, OPR_TEL, OPR_MOBILE, "
					   + "OPR_EMAIL, PWD_WR_TM, PWD_WR_TM_TOTAL, PWD_WR_LAST_DT, PWD_OUT_DATE, SET_OPR_ID, LAST_UPD_OPR_ID, LAST_UPD_TXN_ID, LAST_UPD_TS, RESV2, AUDIT_STAT, ADD_OPR_ID) "
					   + "values ('" + tblOprInfo.getId() + "','" + tblOprInfo.getBrhId() + "','" + tblOprInfo.getOprDegree() + "','" + tblOprInfo.getOprDegreeRsc() + "','" + tblOprInfo.getOprSta() + "','"
					   + "" + tblOprInfo.getOprLogSta() + "','" + tblOprInfo.getOprName() + "','" + tblOprInfo.getOprGender() + "','" + tblOprInfo.getRegisterDt() + "','" + tblOprInfo.getOprPwd() + "'"
					   + ",null,null,null,'" + tblOprInfo.getPwdWrTm() + "','" + tblOprInfo.getPwdWrTmTotal() + "','"
					   + "" + tblOprInfo.getPwdWrLastDt() + "','" + tblOprInfo.getPwdOutDate() + "','" + tblOprInfo.getSetOprId() + "','" + tblOprInfo.getLastUpdOprId() + "','"
					   + "" + tblOprInfo.getLastUpdTxnId() + "','" + tblOprInfo.getLastUpdTs() + "','" + tblOprInfo.getResv2() + "','0','" + operator.getOprId() + "')";
			commQueryDAO.excute(sql1);
		}else{
			String sql1 = "insert into TBL_OPR_INFO_TMP(OPR_ID, BRH_ID, OPR_DEGREE, OPR_DEGREE_RSC, OPR_STA, OPR_LOG_STA, OPR_NAME, OPR_GENDER, REGISTER_DT, OPR_PWD, OPR_TEL, OPR_MOBILE, "
					   + "OPR_EMAIL, PWD_WR_TM, PWD_WR_TM_TOTAL, PWD_WR_LAST_DT, PWD_OUT_DATE, SET_OPR_ID, LAST_UPD_OPR_ID, LAST_UPD_TXN_ID, LAST_UPD_TS, RESV2, AUDIT_STAT, ADD_OPR_ID) "
					   + "values ('" + tblOprInfo.getId() + "','" + tblOprInfo.getBrhId() + "','" + tblOprInfo.getOprDegree() + "','" + tblOprInfo.getOprDegreeRsc() + "','" + tblOprInfo.getOprSta() + "','"
					   + "" + tblOprInfo.getOprLogSta() + "','" + tblOprInfo.getOprName() + "','" + tblOprInfo.getOprGender() + "','" + tblOprInfo.getRegisterDt() + "','" + tblOprInfo.getOprPwd() + "','"
					   + "" + tblOprInfo.getOprTel() + "','" + tblOprInfo.getOprMobile() + "','" + tblOprInfo.getOprEmail() + "','" + tblOprInfo.getPwdWrTm() + "','" + tblOprInfo.getPwdWrTmTotal() + "','"
					   + "" + tblOprInfo.getPwdWrLastDt() + "','" + tblOprInfo.getPwdOutDate() + "','" + tblOprInfo.getSetOprId() + "','" + tblOprInfo.getLastUpdOprId() + "','"
					   + "" + tblOprInfo.getLastUpdTxnId() + "','" + tblOprInfo.getLastUpdTs() + "','" + tblOprInfo.getResv2() + "','0','" + operator.getOprId() + "')";
			commQueryDAO.excute(sql1);
		}
		
		tblOprInfoDAO.update(tblOprInfo);
		log("修改授权码成功");
		writeSuccessMsg("您的授权码已经修改成功，请牢记新授权码并使用新授权码登录");
		return SUCCESS;
	}
	
	// 操作员编号
	private String resetOprId;
	// 操作员原始授权码
	private String resetPassword;
	// 操作员新授权码
	private String resetPassword1;
	/**
	 * @return the resetOprId
	 */
	public String getResetOprId() {
		return resetOprId;
	}

	/**
	 * @param resetOprId the resetOprId to set
	 */
	public void setResetOprId(String resetOprId) {
		this.resetOprId = resetOprId;
	}

	/**
	 * @return the resetPassword
	 */
	public String getResetPassword() {
		return resetPassword;
	}

	/**
	 * @param resetPassword the resetPassword to set
	 */
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	/**
	 * @return the resetPassword1
	 */
	public String getResetPassword1() {
		return resetPassword1;
	}

	/**
	 * @param resetPassword1 the resetPassword1 to set
	 */
	public void setResetPassword1(String resetPassword1) {
		this.resetPassword1 = resetPassword1;
	}
}
