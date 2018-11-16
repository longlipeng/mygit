package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10202BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.CstSysParamDAO;
import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;

/**
 * Title:系统参数BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10202BOTarget implements T10202BO {
	
	private CstSysParamDAO cstSysParamDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#add(com.huateng.po.CstSysParam)
	 */
	public String add(CstSysParam cstSysParam) {
		cstSysParamDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#delete(com.huateng.po.CstSysParam)
	 */
	public String delete(CstSysParam cstSysParam) {
		cstSysParamDAO.delete(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#delete(com.huateng.po.CstSysParamPK)
	 */
	public String delete(CstSysParamPK id) {
		cstSysParamDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#get(com.huateng.po.CstSysParamPK)
	 */
	public CstSysParam get(CstSysParamPK id) {
		return cstSysParamDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<CstSysParam> cstSysParamList) {
		for(CstSysParam cstSysParam : cstSysParamList) {
			cstSysParamDAO.update(cstSysParam);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the cstSysParamDAO
	 */
	public CstSysParamDAO getCstSysParamDAO() {
		return cstSysParamDAO;
	}

	/**
	 * @param cstSysParamDAO the cstSysParamDAO to set
	 */
	public void setCstSysParamDAO(CstSysParamDAO cstSysParamDAO) {
		this.cstSysParamDAO = cstSysParamDAO;
	}
	
}
