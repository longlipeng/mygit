package com.huateng.dao.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblBranchManage;

public class TblBranchManageDAO extends
		_RootDAO<com.huateng.po.base.TblBranchManage> implements com.huateng.dao.iface.base.TblBranchManageDAO {
	public TblBranchManageDAO() {
	}

	public List<com.huateng.po.base.TblBranchManage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<com.huateng.po.base.TblBranchManage> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.TblBranchManage.class;
	}

	public TblBranchManage cast(Object object) {

		return (com.huateng.po.base.TblBranchManage) object;
	}

	public com.huateng.po.base.TblBranchManage load(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblBranchManage) load(getReferenceClass(),
				key);
	}

	public com.huateng.po.base.TblBranchManage get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblBranchManage) get(getReferenceClass(),
				key);
	}

	public String save(com.huateng.po.base.TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(tblBranchManage);
	}

	public void saveOrUpdate(com.huateng.po.base.TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblBranchManage);
	}

	public void update(com.huateng.po.base.TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		super.update(tblBranchManage);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(com.huateng.po.base.TblBranchManage tblBranchManage) {
		// TODO Auto-generated method stub
		super.delete((Object) tblBranchManage);
	}

}
