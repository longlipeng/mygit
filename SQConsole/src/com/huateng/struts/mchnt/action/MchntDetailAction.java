/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-4       first release
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
package com.huateng.struts.mchnt.action;

import com.huateng.bo.mchnt.T20101BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:查看商户详细信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-4
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MchntDetailAction extends BaseAction {
	
	private T20101BO t20101BO = (T20101BO) ContextUtil.getBean("T20101BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		@SuppressWarnings("unused")
		TblMchtBaseInfTmp tblMchntInfoTmp = t20101BO.get(mchntId);
		
//		if(isNotEmpty(tblMchntInfoTmp.getFeeAmt())) {
//			tblMchntInfoTmp.setFeeAmt(CommonFunction.transFenToYuan(tblMchntInfoTmp.getFeeAmt()));
//		}
//		
//		if(isNotEmpty(tblMchntInfoTmp.getFeeLimit())) {
//			tblMchntInfoTmp.setFeeLimit(CommonFunction.transFenToYuan(tblMchntInfoTmp.getFeeLimit()));
//		}
//		
//		
//		Map<String, String> mchntInfoMap = new HashMap<String, String>();
//		
//		BeanUtils.iterateBeanProperties(tblMchntInfoTmp, mchntInfoMap);
//		
//		setSessionAttribute("mchntInfo", mchntInfoMap);
//		
//		setSessionAttribute("mchntDivInfo", t20101BO.loadDiv(mchntId));
		
		return Constants.SUCCESS_CODE;
	}
	
	private String mchntId;

	/**
	 * @return the mchntId
	 */
	public String getMchntId() {
		return mchntId;
	}

	/**
	 * @param mchntId the mchntId to set
	 */
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
}
