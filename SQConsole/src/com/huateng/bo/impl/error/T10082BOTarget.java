package com.huateng.bo.impl.error;

import com.huateng.bo.error.T10082BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.error.TblElecCashInfDAO;
import com.huateng.po.error.TblElecCashInf;

public class T10082BOTarget implements T10082BO {

	private TblElecCashInfDAO tblElecCashInfDAO;
	private ICommQueryDAO commQueryDAO;
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblElecCashInfDAO getTblElecCashInfDAO() {
		return tblElecCashInfDAO;
	}

	public void setTblElecCashInfDAO(TblElecCashInfDAO tblElecCashInfDAO) {
		this.tblElecCashInfDAO = tblElecCashInfDAO;
	}

	public String add(TblElecCashInf tblElecCashInf) throws Exception {
		tblElecCashInfDAO.save(tblElecCashInf);
		return Constants.SUCCESS_CODE;
	}

	public void delete(String key) throws Exception {
		tblElecCashInfDAO.delete(key);
	}

	public TblElecCashInf get(String key) {
		return tblElecCashInfDAO.get(key);
	}

	public String update(TblElecCashInf tblElecCashInf) throws Exception {
		tblElecCashInfDAO.update(tblElecCashInf);
		return Constants.SUCCESS_CODE;
	}

}
