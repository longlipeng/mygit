package com.huateng.bo.impl.error;

import com.huateng.bo.error.T10031BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.error.BthCupErrTxnDAO;
import com.huateng.dao.iface.error.TblAlgoDtlDAO;
import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblAlgoDtlPK;

public class T10031BOTarget implements T10031BO {

	private BthCupErrTxnDAO bthCupErrTxnDAO;
	private TblAlgoDtlDAO tblAlgoDtlDAO;
	
	public TblAlgoDtlDAO getTblAlgoDtlDAO() {
		return tblAlgoDtlDAO;
	}

	public void setTblAlgoDtlDAO(TblAlgoDtlDAO tblAlgoDtlDAO) {
		this.tblAlgoDtlDAO = tblAlgoDtlDAO;
	}

	public BthCupErrTxn get(BthCupErrTxnPK key) throws Exception {
		return bthCupErrTxnDAO.get(key);
	}

	public BthCupErrTxnDAO getBthCupErrTxnDAO() {
		return bthCupErrTxnDAO;
	}

	public void setBthCupErrTxnDAO(BthCupErrTxnDAO bthCupErrTxnDAO) {
		this.bthCupErrTxnDAO = bthCupErrTxnDAO;
	}

	public TblAlgoDtl get(TblAlgoDtlPK key) throws Exception {
		return tblAlgoDtlDAO.get(key);
	}

	public void saveOrUpdate(BthCupErrTxn errTxn) {
		bthCupErrTxnDAO.saveOrUpdate(errTxn);
		
	}

	public String add(BthCupErrTxn bthCupErrTxn) throws Exception {
		bthCupErrTxnDAO.saveOrUpdate(bthCupErrTxn);
		return Constants.SUCCESS_CODE;
	}
	
}
