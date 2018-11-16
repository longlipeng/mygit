package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T90102BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblBranchManageTrue;
import com.huateng.dao.iface.base.TblBranchManageTrueDAO;;
public class T90102BOTarget implements T90102BO {
	public TblBranchManageTrueDAO getTblBranchManageTrueDAO() {
		return tblBranchManageTrueDAO;
	}

	public void setTblBranchManageTrueDAO(
			TblBranchManageTrueDAO tblBranchManageTrueDAO) {
		this.tblBranchManageTrueDAO = tblBranchManageTrueDAO;
	}

	private TblBranchManageTrueDAO tblBranchManageTrueDAO;
	public String add(TblBranchManageTrue tblBranchManageTrue) {
		// TODO Auto-generated method stub
		tblBranchManageTrueDAO.save(tblBranchManageTrue);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblBranchManageTrue tblBranchManageTrue) {
		// TODO Auto-generated method stub
		tblBranchManageTrueDAO.delete(tblBranchManageTrue);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String Id) {
		// TODO Auto-generated method stub
		this.tblBranchManageTrueDAO.delete(Id);;
		return Constants.SUCCESS_CODE;
	}

	public TblBranchManageTrue get(String Id) {
		// TODO Auto-generated method stub
		return this.tblBranchManageTrueDAO.get(Id);
	}

	public String update(List<TblBranchManageTrue> tblBranchManageTrueList) {
		// TODO Auto-generated method stub
		for(TblBranchManageTrue tblBranchManageTrue:tblBranchManageTrueList){
			tblBranchManageTrueDAO.update(tblBranchManageTrue);
		}
		return Constants.SUCCESS_CODE;
	}
	public void update(TblBranchManageTrue tblBranchManageTrue) {
		this.tblBranchManageTrueDAO.update(tblBranchManageTrue);	
	}
	

}
