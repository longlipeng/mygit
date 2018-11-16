package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T11502BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgentDivideDAO;
import com.huateng.po.base.TblAgentDivide;

public class T11502BOTarget implements T11502BO {
    private TblAgentDivideDAO tblAgentDivideDAO;
    
	public TblAgentDivideDAO getTblAgentDivideDAO() {
		return tblAgentDivideDAO;
	}

	public void setTblAgentDivideDAO(TblAgentDivideDAO tblAgentDivideDAO) {
		this.tblAgentDivideDAO = tblAgentDivideDAO;
	}
    
	
	
	public String deleteList(List<String> list) throws Exception {
	
		for(int i=0;i<list.size();i++){
			tblAgentDivideDAO.delete(list.get(i));
		}
		return Constants.SUCCESS_CODE;
	}

	public String add(TblAgentDivide tblAgentDivide) throws Exception {
		tblAgentDivideDAO.save(tblAgentDivide);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblAgentDivide tblAgentDivide) throws Exception {
		tblAgentDivideDAO.update(tblAgentDivide);
		return Constants.SUCCESS_CODE;
	}
	
	public String saveOrUpdate(TblAgentDivide tblAgentDivide) throws Exception {
		tblAgentDivideDAO.saveOrUpdate(tblAgentDivide);
		return Constants.SUCCESS_CODE;
	}

	public TblAgentDivide query(String  uuid) {
		
		return tblAgentDivideDAO.query(uuid);
	}

	public String updateList(List<TblAgentDivide> list) throws Exception {
		for(int i=0;i<list.size();i++){
			tblAgentDivideDAO.update(list.get(i));
		}
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(List<TblAgentDivide> list) throws Exception {
		for(int i=0;i<list.size();i++){
			tblAgentDivideDAO.saveOrUpdate(list.get(i));
		}
		return Constants.SUCCESS_CODE;
	}



}
