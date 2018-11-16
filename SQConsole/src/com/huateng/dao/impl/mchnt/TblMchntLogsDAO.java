package com.huateng.dao.impl.mchnt;

import com.huateng.dao._RootDAO;
import com.huateng.po.mchnt.TblMchntLogs;

public class TblMchntLogsDAO extends _RootDAO<TblMchntLogs> implements com.huateng.dao.iface.mchnt.TblMchntLogsDAO {

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.huateng.po.mchnt.TblMchntLogs.class;
	}

	@Override
	public void save(TblMchntLogs tblMchntLogs){
		super.save(tblMchntLogs);
		
	}

}
