package com.huateng.dao.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.TblBrhInfo;
import com.huateng.po.base.TblAttendanceBrh;

public class TblAttendanceBrhDAO extends _RootDAO<com.huateng.po.base.TblAttendanceBrh> implements
		com.huateng.dao.iface.base.TblAttendanceBrhDAO {

	public void saveOrUpdate(TblAttendanceBrh tblAttendanceBrh) {
		// TODO Auto-generated method stub

	}

	public void update(TblAttendanceBrh tblAttendanceBrh) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Class<com.huateng.po.base.TblAttendanceBrh> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.base.TblAttendanceBrh.class;
	}

	public TblAttendanceBrh get(java.lang.String key) {
		// TODO Auto-generated method stub
		return (com.huateng.po.base.TblAttendanceBrh) get(getReferenceClass(), key);
	}

}
