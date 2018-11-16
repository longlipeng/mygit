package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblIssueBranchInf;

public class TblIssueBranchInfDao extends _RootDAO<com.huateng.po.base.TblIssueBranchInf> implements com.huateng.dao.iface.base.ITblIssueBranchInfDao {
	public TblIssueBranchInfDao () {}
	@Override
	public Class<TblIssueBranchInf> getReferenceClass() {
		return TblIssueBranchInf.class;
	}

	@Override
	public TblIssueBranchInf cast(Object object) {
		return (TblIssueBranchInf) object;
	}

	@Override
	public TblIssueBranchInf load(String branchId)throws org.hibernate.HibernateException  {
		return (TblIssueBranchInf) load(getReferenceClass(), branchId) ;
	}

	@Override
	public TblIssueBranchInf get(String branchId)throws org.hibernate.HibernateException  {
		return (TblIssueBranchInf) get(getReferenceClass(),branchId);
	}

	@Override
	public void save(TblIssueBranchInf tblIssueBranchInf)throws org.hibernate.HibernateException  {
		super.save(tblIssueBranchInf);
	}

	@Override
	public void saveOrUpdate(TblIssueBranchInf tblIssueBranchInf)throws org.hibernate.HibernateException  {
		super.saveOrUpdate(tblIssueBranchInf);
	}

	@Override
	public void update(TblIssueBranchInf tblIssueBranchInf)throws org.hibernate.HibernateException  {
		super.update(tblIssueBranchInf);
	}

	@Override
	public void delete(TblIssueBranchInf tblIssueBranchInf)throws org.hibernate.HibernateException  {
		super.delete(tblIssueBranchInf);
	}

	@Override
	public void delete(String branchId)throws org.hibernate.HibernateException  {
		super.delete((Object) load(branchId));
	}

}
