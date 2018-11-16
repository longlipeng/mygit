package com.huateng.bo.impl.base;

import com.huateng.bo.base.T10600BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblAttendanceBrhDAO;
import com.huateng.po.base.TblAttendanceBrh;

public class T10600BOTarget implements T10600BO {
	private TblAttendanceBrhDAO tblAttendanceBrhDAO;

	public TblAttendanceBrhDAO getTblAttendanceBrhDAO() {
		return tblAttendanceBrhDAO;
	}

	public void setTblAttendanceBrhDAO(TblAttendanceBrhDAO tblAttendanceBrhDAO) {
		this.tblAttendanceBrhDAO = tblAttendanceBrhDAO;
	}

	public TblAttendanceBrh get(String brhId) {
		// TODO Auto-generated method stub
		return this.tblAttendanceBrhDAO.get(brhId);
	}

	public String update(TblAttendanceBrh tblAttendanceBrh) {
		// TODO Auto-generated method stub
		tblAttendanceBrhDAO.update(tblAttendanceBrh);
		return Constants.SUCCESS_CODE;
	}

}
