package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T1010602BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblInstBdbBankCodeDAO;
import com.huateng.po.base.TblInstBdbBankCode;

public class T1010602BOTarget implements T1010602BO {

	
	private TblInstBdbBankCodeDAO tblInstBdbBankCodeDAO;
	
	
	

	public TblInstBdbBankCodeDAO getTblInstBdbBankCodeDAO() {
		return tblInstBdbBankCodeDAO;
	}

	public void setTblInstBdbBankCodeDAO(TblInstBdbBankCodeDAO tblInstBdbBankCodeDAO) {
		this.tblInstBdbBankCodeDAO = tblInstBdbBankCodeDAO;
	}

	public String save(TblInstBdbBankCode tblInstBdbBankCode) {
		tblInstBdbBankCodeDAO.save(tblInstBdbBankCode);
		return Constants.SUCCESS_CODE;
	}

	public String save(List<TblInstBdbBankCode> list) {
		for(int i=0;i<list.size();i++){
			tblInstBdbBankCodeDAO.save(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		tblInstBdbBankCodeDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}
	public String delete(List<String> list){
		for(String str:list){
			tblInstBdbBankCodeDAO.delete(str);
		}
		return Constants.SUCCESS_CODE;
	}

	public TblInstBdbBankCode query(String id) {
		return tblInstBdbBankCodeDAO.query(id);
	}

	public String update(TblInstBdbBankCode tblInstBdbBankCode) {
		tblInstBdbBankCodeDAO.update(tblInstBdbBankCode);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblInstBdbBankCode> list) {
		for(int i=0;i<list.size();i++){
			tblInstBdbBankCodeDAO.update(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(List<TblInstBdbBankCode> list) {
		for(int i=0;i<list.size();i++){
			tblInstBdbBankCodeDAO.saveOrUpdate(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblInstBdbBankCode tblInstBdbBankCode) {
		tblInstBdbBankCodeDAO.saveOrUpdate(tblInstBdbBankCode);
		return Constants.SUCCESS_CODE;
	}

}
