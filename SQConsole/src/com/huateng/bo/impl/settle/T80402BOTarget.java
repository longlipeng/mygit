package com.huateng.bo.impl.settle;

import java.util.List;

import com.huateng.bo.settle.T80402BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.settle.TblFrozenAccInfDAO;
import com.huateng.po.settle.TblFrozenAccInf;

public class T80402BOTarget implements T80402BO {

	public String add(TblFrozenAccInf tblFrozenAccInf) throws Exception {
		tblFrozenAccInfDAO.save(tblFrozenAccInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) throws Exception {
		tblFrozenAccInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblFrozenAccInf get(String id) {
		
		return tblFrozenAccInfDAO.get(id);
	}

	public String update(TblFrozenAccInf tblFrozenAccInf) throws Exception {
		tblFrozenAccInfDAO.update(tblFrozenAccInf);
		return Constants.SUCCESS_CODE;
	}

	
	private TblFrozenAccInfDAO tblFrozenAccInfDAO;

	public TblFrozenAccInfDAO getTblFrozenAccInfDAO() {
		return tblFrozenAccInfDAO;
	}

	public void setTblFrozenAccInfDAO(TblFrozenAccInfDAO tblFrozenAccInfDAO) {
		this.tblFrozenAccInfDAO = tblFrozenAccInfDAO;
	}

	public String updateAll(List<TblFrozenAccInf> list) throws Exception {
		for(int i=0;i<list.size();i++){
			tblFrozenAccInfDAO.update(list.get(i));
		}
		return Constants.SUCCESS_CODE;
	}
	
}
