package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10201BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblCityCodeDAO;
import com.huateng.po.TblCityCode;

/**
 * Title:地区码BO实现
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
public class T10201BOTarget implements T10201BO {
	
	private TblCityCodeDAO tblCityCodeDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#add(com.huateng.po.TblCityCode)
	 */
	public String add(TblCityCode tblCityCode) {
		tblCityCodeDAO.save(tblCityCode);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#delete(com.huateng.po.TblCityCode)
	 */
	public String delete(TblCityCode tblCityCode) {
		tblCityCodeDAO.delete(tblCityCode);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#delete(java.lang.String)
	 */
	public String delete(String id) {
		tblCityCodeDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#get(com.huateng.po.TblCityCode)
	 */
	public TblCityCode get(String id) {
		return tblCityCodeDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10203BO#update(java.util.List)
	 */
	public String update(List<TblCityCode> tblCityCodeList) {
		for(TblCityCode tblCityCode : tblCityCodeList) {
			tblCityCodeDAO.update(tblCityCode);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblCityCodeDAO
	 */
	public TblCityCodeDAO getTblCityCodeDAO() {
		return tblCityCodeDAO;
	}

	/**
	 * @param tblCityCodeDAO the tblCityCodeDAO to set
	 */
	public void setTblCityCodeDAO(TblCityCodeDAO tblCityCodeDAO) {
		this.tblCityCodeDAO = tblCityCodeDAO;
	}
	
}
