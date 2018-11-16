package com.huateng.bo.impl.error;

import java.util.List;
import com.huateng.bo.error.T10033BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.error.TblAlgoDtlCheckDAO;
import com.huateng.po.error.TblAlgoDtlCheck;

public class T10033BOTarget implements T10033BO {

	private TblAlgoDtlCheckDAO tblAlgoDtlCheckDAO;
	private ICommQueryDAO commQueryDAO;
	
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblAlgoDtlCheckDAO getTblAlgoDtlCheckDAO() {
		return tblAlgoDtlCheckDAO;
	}

	public void setTblAlgoDtlCheckDAO(TblAlgoDtlCheckDAO tblAlgoDtlCheckDAO) {
		this.tblAlgoDtlCheckDAO = tblAlgoDtlCheckDAO;
	}

	public String add(TblAlgoDtlCheck tblAlgoDtlCheck) throws Exception {
		tblAlgoDtlCheckDAO.save(tblAlgoDtlCheck);
		return Constants.SUCCESS_CODE;
	}

	public void delete(String key) throws Exception {
		tblAlgoDtlCheckDAO.delete(key);
	}

	public TblAlgoDtlCheck get(String key) {
		return tblAlgoDtlCheckDAO.get(key);
	}

	public String update(TblAlgoDtlCheck tblAlgoDtlCheck) throws Exception {
		tblAlgoDtlCheckDAO.update(tblAlgoDtlCheck);
		return Constants.SUCCESS_CODE;
	}

	@SuppressWarnings("unchecked")
	public String get(String dateSettlmt,String txnKey){
		String sql = "select ID from TBL_ALGO_DTL_CHECK where DATE_SETTLMT='" + dateSettlmt
			+"' and TXN_Key like '" + txnKey + "%'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list==null || list.size()==0)//20120917追扣报错处理
			return null;
		return (String)list.get(0);
	}
}
