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

import com.huateng.bo.risk.T41201BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.risk.TblWhiteListDAO;
import com.huateng.po.risk.TblWhiteList;


/**
 * Title: 商户白名单管理
 * Copyright: Copyright (c) 2014-08-01
 * @author shiyiwen
 * 
 * @version 1.0
 */
public class T41201BOTarget implements T41201BO {
	private TblWhiteListDAO tblWhiteListDAO;
    
	public TblWhiteListDAO getTblWhiteListDAO() {
		return tblWhiteListDAO;
	}

	public void setTblWhiteListDAO(TblWhiteListDAO tblWhiteListDAO) {
		this.tblWhiteListDAO = tblWhiteListDAO;
	}

	

	public String delete(String key) throws Exception {
		// TODO Auto-generated method stub
		tblWhiteListDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}

	public TblWhiteList get(String key) {
		
		return tblWhiteListDAO.get(key); 
	}

	
	public String add(TblWhiteList tblWhiteList) {
		tblWhiteListDAO.save(tblWhiteList);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblWhiteList tblWhiteList)  {
		tblWhiteListDAO.update(tblWhiteList);
		return Constants.SUCCESS_CODE;
	}
    
}
