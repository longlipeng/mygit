package com.huateng.dao.impl.risk;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblWhiteList;

public class TblWhiteListDAO extends _RootDAO<TblWhiteList> implements com.huateng.dao.iface.risk.TblWhiteListDAO {



	@Override
/*	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}*/
	public Class<com.huateng.po.risk.TblWhiteList > getReferenceClass () {
		return com.huateng.po.risk.TblWhiteList.class;
	}
	
	public com.huateng.po.risk.TblWhiteList load(java.lang.String key)
	{
		return (com.huateng.po.risk.TblWhiteList) load(getReferenceClass(), key);
	}
	

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public TblWhiteList get(String key) {
		// TODO Auto-generated method stub
		return (TblWhiteList) get(getReferenceClass(), key);
	}
	
	public String save(TblWhiteList tblWhiteList)
	{
		return (String) super.save(tblWhiteList);
	}

	public void update(TblWhiteList tblWhiteList) {
		super.update(tblWhiteList);
	}
}
