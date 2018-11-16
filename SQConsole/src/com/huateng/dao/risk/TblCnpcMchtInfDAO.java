package com.huateng.dao.risk;

import com.huateng.dao._RootDAO;
import com.huateng.po.TblCnpcCardInf;
import com.huateng.po.TblCnpcCardInfPK;

public class TblCnpcMchtInfDAO extends _RootDAO<com.huateng.po.TblCnpcCardInf> implements com.huateng.dao.iface.risk.TblCnpcMchtInfDAO {
	public TblCnpcMchtInfDAO() { }

	@Override
	public TblCnpcCardInf get(TblCnpcCardInfPK key) {
		return (com.huateng.po.TblCnpcCardInf) get(getReferenceClass(), key);
	}

	@Override
	public TblCnpcCardInf load(String key) {
		return (com.huateng.po.TblCnpcCardInf) load(getReferenceClass(), key);
	}


	@Override
	public void save(TblCnpcCardInf TblCnpcCardInf) {
		super.save(TblCnpcCardInf);
	}

	@Override
	public void saveOrUpdate(TblCnpcCardInf TblCnpcCardInf) {
		super.saveOrUpdate(TblCnpcCardInf);
	}

	@Override
	public void update(TblCnpcCardInf TblCnpcCardInf) {
		super.update(TblCnpcCardInf);
	}

	@Override
	public void delete(TblCnpcCardInfPK TblCnpcCardInf) {
		super.delete((Object) TblCnpcCardInf);
	}

	@Override
	protected Class getReferenceClass() {
		return com.huateng.po.TblCnpcCardInf.class;
	}

}
