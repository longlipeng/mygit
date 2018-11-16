package com.huateng.dao.impl.error;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.error.TblElecCashInf;

public class TblElecCashInfDAO extends _RootDAO<TblElecCashInf> implements
		com.huateng.dao.iface.error.TblElecCashInfDAO {

	public TblElecCashInfDAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblElecCashInf tblElecCashInf) {
		super.delete((Object) tblElecCashInf);
	}

	public List<TblElecCashInf> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblElecCashInf get(String key) {
		return (TblElecCashInf)get(getReferenceClass(), key);
	}

	public TblElecCashInf load(String key) {
		return (TblElecCashInf)load(getReferenceClass(), key);
	}

	public List<TblElecCashInf> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	public String save(TblElecCashInf tblElecCashInf) {
		return (String)super.save(tblElecCashInf);
	}

	public void saveOrUpdate(TblElecCashInf tblElecCashInf) {
		super.saveOrUpdate(tblElecCashInf);
	}

	public void update(TblElecCashInf tblElecCashInf) {
		super.update(tblElecCashInf);
	}

	@Override
	protected Class getReferenceClass() {
		return TblElecCashInf.class;
	}

}
