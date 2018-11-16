package com.huateng.dao.impl.risk;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblWhiteListTmp;

public class TblWhiteListTmpDAO extends _RootDAO<TblWhiteListTmp> implements
		com.huateng.dao.iface.risk.TblWhiteListTmpDAO {

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblWhiteListTmp.class;
	}
	public TblWhiteListTmp load(String key) {
		return (TblWhiteListTmp) load(getReferenceClass(),key);
	}

	public TblWhiteListTmp get(String key) {
		return (TblWhiteListTmp) get(getReferenceClass(),key);
	}

	public void delete(String key) {
		super.delete((Object) load(key));

	}

	public String save(TblWhiteListTmp tblWhiteListTmp) {
		return (String) super.save(tblWhiteListTmp);
	}

	public void update(TblWhiteListTmp tblWhiteListTmp) {
		super.update(tblWhiteListTmp);
	}
	public void saveOrUpdate(TblWhiteListTmp tblWhiteListTmp) {
		super.saveOrUpdate(tblWhiteListTmp);
		
	}


}
