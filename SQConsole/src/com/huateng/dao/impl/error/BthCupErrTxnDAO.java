package com.huateng.dao.impl.error;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;

public class BthCupErrTxnDAO extends _RootDAO<BthCupErrTxn> implements com.huateng.dao.iface.error.BthCupErrTxnDAO {

	public BthCupErrTxnDAO(){}
	
	public void delete(BthCupErrTxnPK id) {
		super.delete((Object) load(id));
	}

	public void delete(BthCupErrTxn bthCupErrTxn) {
		super.delete((Object) bthCupErrTxn);
	}

	public List<BthCupErrTxn> findAll() {
		return null;
	}

	public BthCupErrTxn get(BthCupErrTxnPK key) {
		return (BthCupErrTxn)get(getReferenceClass(), key);
	}

	public BthCupErrTxn load(BthCupErrTxnPK key) {
		return (BthCupErrTxn)load(getReferenceClass(), key);
	}
	public List<BthCupErrTxn> loadAll(){
		return loadAll(getReferenceClass());
	}

	public String save(BthCupErrTxn bthCupErrTxn) {
		return (String)super.save(bthCupErrTxn);
	}

	public void saveOrUpdate(BthCupErrTxn bthCupErrTxn) {
		super.saveOrUpdate(bthCupErrTxn);
	}

	public void update(BthCupErrTxn bthCupErrTxn) {
		super.update(bthCupErrTxn);
	}

	@Override
	protected Class getReferenceClass() {
		return BthCupErrTxn.class;
	}

}
