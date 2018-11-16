package com.huateng.bo.impl.base;


import com.huateng.bo.base.T10601BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblSignInfDAO;
import com.huateng.po.base.TblSignInf;

public class T10601BOTarget implements T10601BO {
	private TblSignInfDAO tblSignInfDAO;
	/**
	 * @return the tblSignInfDAO
	 */
	public TblSignInfDAO getTblSignInfDAO() {
		return tblSignInfDAO;
	}

	/**
	 * @param tblSignInfDAO the tblSignInfDAO to set
	 */
	public void setTblSignInfDAO(TblSignInfDAO tblSignInfDAO) {
		this.tblSignInfDAO = tblSignInfDAO;
	}

	public TblSignInf get(String brhId) {
		// TODO Auto-generated method stub
		return this.tblSignInfDAO.get(brhId);
	}

	public String update(TblSignInf tblSignInf) {
		// TODO Auto-generated method stub
		tblSignInfDAO.update(tblSignInf);
		return Constants.SUCCESS_CODE;
	}
}
