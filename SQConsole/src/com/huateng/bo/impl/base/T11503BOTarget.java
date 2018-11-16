package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T11503BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAgentDivideTmpDAO;
import com.huateng.po.base.TblAgentDivideTmp;

public class T11503BOTarget implements T11503BO {
	
	private TblAgentDivideTmpDAO tblAgentDivideTmpDAO;
	
	


	public TblAgentDivideTmpDAO getTblAgentDivideTmpDAO() {
		return tblAgentDivideTmpDAO;
	}

	public void setTblAgentDivideTmpDAO(TblAgentDivideTmpDAO tblAgentDivideTmpDAO) {
		this.tblAgentDivideTmpDAO = tblAgentDivideTmpDAO;
	}

	public String save(TblAgentDivideTmp tblAgentDivideTmp) {
		tblAgentDivideTmpDAO.save(tblAgentDivideTmp);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String uuid) {
		tblAgentDivideTmpDAO.delete(uuid);
		return Constants.SUCCESS_CODE;
	}

	public String delete(List<String> list) {
        for(int i=0;i<list.size();i++){
        	tblAgentDivideTmpDAO.delete(list.get(i));
        	
        }

		return Constants.SUCCESS_CODE;
	}

	public TblAgentDivideTmp query(String uuid) {
		return tblAgentDivideTmpDAO.query(uuid);
	}

	public String update(TblAgentDivideTmp tblAgentDivideTmp) {
		tblAgentDivideTmpDAO.update(tblAgentDivideTmp);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblAgentDivideTmp> list) {
		for(int i=0;i<list.size();i++){
        	tblAgentDivideTmpDAO.update(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(List<TblAgentDivideTmp> list) {
		for(int i=0;i<list.size();i++){
        	tblAgentDivideTmpDAO.saveOrUpdate(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}
	
	public String saveOrUpdate(TblAgentDivideTmp tblAgentDivideTmp){
		tblAgentDivideTmpDAO.saveOrUpdate(tblAgentDivideTmp);
		return Constants.SUCCESS_CODE;
	}

}
