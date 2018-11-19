/**
 * Classname BizServiceImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wpf		2012-10-23
 * =============================================================================
 */

package com.huateng.service;

import java.util.Map;

import com.huateng.framework.db.dao.BaseDAO;

/**
 * @author wpf
 * 
 */
public class BizBaseService {
	protected BaseDAO baseDao;

	public BaseDAO getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 如果需要则复写当前方法
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> setParamters(Map<String, String> map) {
		return map;
	}
}
