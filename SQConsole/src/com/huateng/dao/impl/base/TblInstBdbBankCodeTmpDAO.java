package com.huateng.dao.impl.base;


import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblInstBdbBankCodeTmp;

public class TblInstBdbBankCodeTmpDAO extends _RootDAO<TblInstBdbBankCodeTmp>
		implements com.huateng.dao.iface.base.TblInstBdbBankCodeTmpDAO {

	public TblInstBdbBankCodeTmp load(String id) {
		return (TblInstBdbBankCodeTmp)load(getReferenceClass(),id);
	}

	public void delete(String id) {
		super.delete((Object)load(id));

	}

	public String save(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp) {
		return  (String) super.save(tblInstBdbBankCodeTmp);
	}


	public TblInstBdbBankCodeTmp query(String id) {
		return (TblInstBdbBankCodeTmp) get(getReferenceClass(), id);
	}

	public void update(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp) {
		super.update(tblInstBdbBankCodeTmp);

	}

	public void saveOrUpdate(TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp) {
		super.saveOrUpdate(tblInstBdbBankCodeTmp);

	}

	@Override
	protected Class getReferenceClass() {
		return TblInstBdbBankCodeTmp.class;
	}

}
