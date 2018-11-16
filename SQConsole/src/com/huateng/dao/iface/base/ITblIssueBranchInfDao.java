package com.huateng.dao.iface.base;

import com.huateng.po.base.TblIssueBranchInf;

public interface ITblIssueBranchInfDao {
	public Class<TblIssueBranchInf> getReferenceClass();

	public TblIssueBranchInf cast(Object object);

	public TblIssueBranchInf load(String branchId);

	public TblIssueBranchInf get(String branchId);

	public void save(TblIssueBranchInf tblIssueBranchInf);

	public void saveOrUpdate(TblIssueBranchInf tblIssueBranchInf);

	public void update(TblIssueBranchInf tblIssueBranchInf);

	public void delete(TblIssueBranchInf tblIssueBranchInf);

	public void delete(String branchId);
}
