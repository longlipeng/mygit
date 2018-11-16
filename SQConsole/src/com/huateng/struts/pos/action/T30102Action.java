package com.huateng.struts.pos.action;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInfTmp;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;

/**
 * Title:终端停用管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30102Action extends BaseAction {

	private static final long serialVersionUID = -3333676568405263032L;
	private T3010BO t3010BO;
	private String termId;
	private String recCrtTs;
	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	@Override
	protected String subExecute() throws Exception {
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		//tblTermInfTmp.setRecUpdOpr(operator.getOprId());
		tblTermInfTmp.setRecCrtOpr(operator.getOprId());
		tblTermInfTmp.setRecDelTs(CommonFunction.getCurrentDateTime());
		if(tblTermInfTmp != null && TblTermInfConstants.TERM_STA_RUN.equals(tblTermInfTmp.getTermSta()))
		{
			t3010BO.update(tblTermInfTmp, TblTermInfConstants.TERM_STA_STOP_UNCHK);
			return Constants.SUCCESS_CODE;
		}
		return Constants.FAILURE_CODE;
	}
	

}
