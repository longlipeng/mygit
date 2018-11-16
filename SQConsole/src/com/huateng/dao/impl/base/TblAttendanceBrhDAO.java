package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblAttendanceBrh;

public class TblAttendanceBrhDAO extends _RootDAO<com.huateng.po.base.TblAttendanceBrh> implements
		com.huateng.dao.iface.base.TblAttendanceBrhDAO {

	public TblAttendanceBrh get(String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblAttendanceBrh) get(getReferenceClass(), key);
	}

	public void saveOrUpdate(TblAttendanceBrh tblAttendanceBrh) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblAttendanceBrh);
	}

	public void update(TblAttendanceBrh tblAttendanceBrh) {
		// TODO Auto-generated method stub
		super.update(tblAttendanceBrh);
	}

	@Override
	protected Class<com.huateng.po.base.TblAttendanceBrh> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.TblAttendanceBrh.class;
	}

}
