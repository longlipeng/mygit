package com.huateng.bo.impl.error;

import com.huateng.bo.error.T10081BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.error.TblElecCashInfTmpDAO;
import com.huateng.po.error.TblElecCashInfTmp;

public class T10081BOTarget implements T10081BO {

	private TblElecCashInfTmpDAO tblElecCashInfTmpDAO;
	private ICommQueryDAO commQueryDAO;
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblElecCashInfTmpDAO getTblElecCashInfTmpDAO() {
		return tblElecCashInfTmpDAO;
	}

	public void setTblElecCashInfTmpDAO(TblElecCashInfTmpDAO tblElecCashInfTmpDAO) {
		this.tblElecCashInfTmpDAO = tblElecCashInfTmpDAO;
	}

	public String add(TblElecCashInfTmp tblElecCashInfTmp) throws Exception {
		tblElecCashInfTmpDAO.save(tblElecCashInfTmp);
		return Constants.SUCCESS_CODE;
	}

	public void delete(String key) throws Exception {
		tblElecCashInfTmpDAO.delete(key);
	}

	public TblElecCashInfTmp get(String key) {
		return tblElecCashInfTmpDAO.get(key);
	}

	public String update(TblElecCashInfTmp tblElecCashInfTmp) throws Exception {
		tblElecCashInfTmpDAO.update(tblElecCashInfTmp);
		return Constants.SUCCESS_CODE;
	}

}
