/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-26       first release
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

import com.huateng.bo.risk.T40601BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.risk.TblCnpcMchtInfDAO;
import com.huateng.po.TblCnpcCardInf;
import com.huateng.po.TblCnpcCardInfPK;

/**
 * Title: 中石油黑名单管理
 * 
 * 
 * @version 1.0
 */
public class T40601BOTarget implements T40601BO {

	private TblCnpcMchtInfDAO tblCnpcMchtInfDAO;
	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#add(com.huateng.po.TblCnpcCardInf)
	 */
	public String add(TblCnpcCardInf TblCnpcCardInf) throws Exception {
	//	TblCnpcCardInf.setSaLimitAmt(CommonFunction.transYuanToFen(tblCnpcMchtInf.getSaLimitAmt()));
		tblCnpcMchtInfDAO.save(TblCnpcCardInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#update(com.huateng.po.TblCnpcCardInf)
	 */
	public String update(TblCnpcCardInf TblCnpcCardInf) throws Exception {
	//	TblCnpcCardInf.setSaLimitAmt(CommonFunction.transYuanToFen(TblCnpcCardInf.getSaLimitAmt()));
		tblCnpcMchtInfDAO.update(TblCnpcCardInf);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * @return the tblCnpcMchtInfDAO
	 */
	public TblCnpcMchtInfDAO getTblCnpcMchtInfDAO() {
		return tblCnpcMchtInfDAO;
	}
	/**
	 * @param tblCnpcMchtInfDAO the tblCnpcMchtInfDAO to set
	 */
	public void setTblCnpcMchtInfDAO(TblCnpcMchtInfDAO tblCnpcMchtInfDAO) {
		this.tblCnpcMchtInfDAO = tblCnpcMchtInfDAO;
	}
	


	@Override
	public TblCnpcCardInf get(TblCnpcCardInfPK key) {
		
		return tblCnpcMchtInfDAO.get(key);
	}

	@Override
	public void delete(TblCnpcCardInfPK key) throws Exception {
		tblCnpcMchtInfDAO.delete(key);
	}
}
