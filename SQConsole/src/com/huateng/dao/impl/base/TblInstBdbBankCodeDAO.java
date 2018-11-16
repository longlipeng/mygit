package com.huateng.dao.impl.base;


import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblInstBdbBankCode;

public class TblInstBdbBankCodeDAO extends _RootDAO<TblInstBdbBankCode>
		implements com.huateng.dao.iface.base.TblInstBdbBankCodeDAO {

	public TblInstBdbBankCode load(String id) {
		return (TblInstBdbBankCode)load(getReferenceClass(),id);
	}

	public void delete(String id) {
		super.delete((Object)load(id));

	}

	public String save(TblInstBdbBankCode tblInstBdbBankCode) {
		return  (String) super.save(tblInstBdbBankCode);
	}


	public TblInstBdbBankCode query(String id) {
		return (TblInstBdbBankCode) get(getReferenceClass(), id);
	}

	public void update(TblInstBdbBankCode tblInstBdbBankCode) {
		super.update(tblInstBdbBankCode);

	}

	public void saveOrUpdate(TblInstBdbBankCode tblInstBdbBankCode) {
		super.saveOrUpdate(tblInstBdbBankCode);

	}

	@Override
	protected Class getReferenceClass() {
		return TblInstBdbBankCode.class;
	}

}
