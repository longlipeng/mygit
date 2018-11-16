package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblAttendanceBrh;
import com.huateng.po.base.TblSignInf;

public class TblSignInfDAO extends _RootDAO<com.huateng.po.base.TblSignInf> implements
com.huateng.dao.iface.base.TblSignInfDAO {
	public TblSignInf get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblSignInf) get(getReferenceClass(), key);
	}

	public void saveOrUpdate(TblSignInf tblSignInf) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblSignInf);
	}

	public void update(TblSignInf tblSignInf) {
		// TODO Auto-generated method stub
		super.update(tblSignInf);
	}

	@Override
	protected Class<com.huateng.po.base.TblSignInf> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.TblSignInf.class;
	}

	

}
