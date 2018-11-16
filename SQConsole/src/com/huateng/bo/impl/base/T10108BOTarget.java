package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10108BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblBranchManage;
import com.huateng.dao.iface.base.TblBranchManageDAO;
public class T10108BOTarget implements T10108BO {
	private TblBranchManageDAO tblBranchManageDAO;
	public String add(TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		tblBranchManageDAO.save(tblBranchManage);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		tblBranchManageDAO.delete(tblBranchManage);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String Id) {
		// TODO Auto-generated method stub
		this.tblBranchManageDAO.delete(Id);;
		return Constants.SUCCESS_CODE;
	}

	public TblBranchManage get(String Id) {
		// TODO Auto-generated method stub
		return this.tblBranchManageDAO.get(Id);
	}

	public String update(List<TblBranchManage> tblBranchManageList) {
		// TODO Auto-generated method stub
		for(TblBranchManage tblBranchManage:tblBranchManageList){
			tblBranchManageDAO.update(tblBranchManage);
		}
		return Constants.SUCCESS_CODE;
	}
	public TblBranchManageDAO getTblBranchManageDAO() {
		return tblBranchManageDAO;
	}

	public void setTblBranchManageDAO(TblBranchManageDAO tblBranchManageDAO) {
		this.tblBranchManageDAO = tblBranchManageDAO;
	}

}
