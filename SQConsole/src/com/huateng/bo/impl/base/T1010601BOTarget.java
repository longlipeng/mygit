package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T1010601BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblInstBdbBankCodeTmpDAO;
import com.huateng.po.base.TblInstBdbBankCodeTmp;

public class T1010601BOTarget implements T1010601BO {

	
	private TblInstBdbBankCodeTmpDAO tblInstBdbBankCodeTmpDAO;
	
	
	public TblInstBdbBankCodeTmpDAO getTblInstBdbBankCodeTmpDAO() {
		return tblInstBdbBankCodeTmpDAO;
	}

	public void setTblInstBdbBankCodeTmpDAO(
			TblInstBdbBankCodeTmpDAO tblInstBdbBankCodeTmpDAO) {
		this.tblInstBdbBankCodeTmpDAO = tblInstBdbBankCodeTmpDAO;
	}

	public String save(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp) {
		tblInstBdbBankCodeTmpDAO.save(tblInstBdbBankCodeTmp);
		return Constants.SUCCESS_CODE;
	}

	public String save(List<TblInstBdbBankCodeTmp> list) {
		for(int i=0;i<list.size();i++){
			tblInstBdbBankCodeTmpDAO.save(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		tblInstBdbBankCodeTmpDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(List<String> list){
		for(String str:list){
			tblInstBdbBankCodeTmpDAO.delete(str);
		}
		return Constants.SUCCESS_CODE;
	}
	

	public TblInstBdbBankCodeTmp query(String id) {
		return tblInstBdbBankCodeTmpDAO.query(id);
	}

	public String update(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp) {
		tblInstBdbBankCodeTmpDAO.update(tblInstBdbBankCodeTmp);
		return Constants.SUCCESS_CODE;
	}

	public String update(List<TblInstBdbBankCodeTmp> list) {
		for(int i=0;i<list.size();i++){
			tblInstBdbBankCodeTmpDAO.update(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(List<TblInstBdbBankCodeTmp> list) {
		for(int i=0;i<list.size();i++){
			tblInstBdbBankCodeTmpDAO.saveOrUpdate(list.get(i));
        	
        }
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp) {
		tblInstBdbBankCodeTmpDAO.saveOrUpdate(tblInstBdbBankCodeTmp);
		return Constants.SUCCESS_CODE;
	}

}
