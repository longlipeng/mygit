package com.huateng.dao.base;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblBranchManageTrue;

public class TblBranchManageTrueDAO extends
		_RootDAO<com.huateng.po.base.TblBranchManageTrue> implements com.huateng.dao.iface.base.TblBranchManageTrueDAO {
	public TblBranchManageTrueDAO() {
	}

	public List<com.huateng.po.base.TblBranchManageTrue> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<com.huateng.po.base.TblBranchManageTrue> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.TblBranchManageTrue.class;
	}

	public TblBranchManageTrue cast(Object object) {

		return (com.huateng.po.base.TblBranchManageTrue) object;
	}

	public com.huateng.po.base.TblBranchManageTrue load(String key) {
		// TODO Auto-generated method stub
		return (TblBranchManageTrue) load(getReferenceClass(),
				key);
	}

	public com.huateng.po.base.TblBranchManageTrue get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblBranchManageTrue) get(getReferenceClass(),
				key);
	}

	public String save(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue) {
		// TODO Auto-generated method stub
		return (java.lang.String) super.save(tblBranchManageTrue);
	}

	public void saveOrUpdate(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblBranchManageTrue);
	}

	public void update(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue) {
		// TODO Auto-generated method stub
		super.update(tblBranchManageTrue);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public void delete(com.huateng.po.base.TblBranchManageTrue tblBranchManageTrue) {
		// TODO Auto-generated method stub
		super.delete((Object) tblBranchManageTrue);
	}

}
