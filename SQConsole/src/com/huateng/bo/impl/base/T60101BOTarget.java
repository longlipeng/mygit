package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T60101BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.ITblIssueBranchInfDao;
import com.huateng.po.base.TblIssueBranchInf;

public class T60101BOTarget implements T60101BO {
	private ITblIssueBranchInfDao tblIssueBranchInfDao;
	
	public ITblIssueBranchInfDao getTblIssueBranchInfDao() {
		return tblIssueBranchInfDao;
	}

	public void setTblIssueBranchInfDao(ITblIssueBranchInfDao tblIssueBranchInfDao) {
		this.tblIssueBranchInfDao = tblIssueBranchInfDao;
	}

	@Override
	public String add(TblIssueBranchInf tblIssueBranchInf) {
		tblIssueBranchInfDao.save(tblIssueBranchInf);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblIssueBranchInf tblIssueBranchInf) {
		tblIssueBranchInfDao.update(tblIssueBranchInf);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public TblIssueBranchInf get(String branchId) {
		return tblIssueBranchInfDao.get(branchId);
	}

	@Override
	public void delete(String branchId) {
		tblIssueBranchInfDao.delete(branchId);
	}

	@Override
	public String update(List<TblIssueBranchInf> tblIssueBranchInf) {
		for (TblIssueBranchInf tblIssueBranchInf2 : tblIssueBranchInf) {
			tblIssueBranchInfDao.update(tblIssueBranchInf2);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public void delete(TblIssueBranchInf tblIssueBranchInf) {
		tblIssueBranchInfDao.delete(tblIssueBranchInf);
	}

}
