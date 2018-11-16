package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T1020101BO;
import com.huateng.dao.iface.base.TblCityCodeCodeDAO;
import com.huateng.po.base.TblCityCodeCode;



public class T1020101BOTarget implements T1020101BO{
	public TblCityCodeCodeDAO getTblCityCodeCodeDAO() {
		return tblCityCodeCodeDAO;
	}

	public void setTblCityCodeCodeDAO(TblCityCodeCodeDAO tblCityCodeCodeDAO) {
		this.tblCityCodeCodeDAO = tblCityCodeCodeDAO;
	}
	private TblCityCodeCodeDAO tblCityCodeCodeDAO;

	

	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(TblCityCodeCode tblCityCodeCode) {
		tblCityCodeCodeDAO.save(tblCityCodeCode);
		return "00";
	}

	public TblCityCodeCode get(String id) {
		return tblCityCodeCodeDAO.get(id);
	}

	public String update(TblCityCodeCode tblCityCodeCode) {
		tblCityCodeCodeDAO.update(tblCityCodeCode);
		return "00";
	}
	
	

	public void delete(String id) {
		tblCityCodeCodeDAO .delete(id);
		
	}
	public String update(List<TblCityCodeCode> tblCityCodeCodeList) {
		for(TblCityCodeCode tblOprInfo : tblCityCodeCodeList) {
			update(tblOprInfo);
		}
		return "00";
	}
}
