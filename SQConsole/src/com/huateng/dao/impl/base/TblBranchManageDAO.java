package com.huateng.dao.impl.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblBranchManage;

public class TblBranchManageDAO extends _RootDAO<com.huateng.po.base.TblBranchManage> implements com.huateng.dao.iface.base.TblBranchManageDAO {
	public TblBranchManageDAO() {
	}

	public List<TblBranchManage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.TblBranchManage.class;
	}

	public com.huateng.po.base.TblBranchManage cast(Object object) {
		return (com.huateng.po.base.TblBranchManage) object;
	}

	public TblBranchManage load(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblBranchManage) load(getReferenceClass(),
				key);
	}

	public TblBranchManage get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblBranchManage) get(getReferenceClass(),
				key);
	}

	public String save(TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(tblBranchManage);
	}

	public void saveOrUpdate(TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblBranchManage);
	}

	public void update(TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		super.update(tblBranchManage);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		super.delete((Object) tblBranchManage);
	}

}
