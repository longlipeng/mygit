package com.huateng.dao.impl.error;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.error.TblElecCashInfTmp;

public class TblElecCashInfTmpDAO extends _RootDAO<TblElecCashInfTmp> implements
		com.huateng.dao.iface.error.TblElecCashInfTmpDAO {

	public TblElecCashInfTmpDAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblElecCashInfTmp tblElecCashInfTmp) {
		super.delete((Object) tblElecCashInfTmp);
	}

	public List<TblElecCashInfTmp> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblElecCashInfTmp get(String key) {
		return (TblElecCashInfTmp)get(getReferenceClass(), key);
	}

	public TblElecCashInfTmp load(String key) {
		return (TblElecCashInfTmp)load(getReferenceClass(), key);
	}

	public List<TblElecCashInfTmp> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	public String save(TblElecCashInfTmp tblElecCashInfTmp) {
		return (String)super.save(tblElecCashInfTmp);
	}

	public void saveOrUpdate(TblElecCashInfTmp tblElecCashInfTmp) {
		super.saveOrUpdate(tblElecCashInfTmp);
	}

	public void update(TblElecCashInfTmp tblElecCashInfTmp) {
		super.update(tblElecCashInfTmp);
	}

	@Override
	protected Class getReferenceClass() {
		return TblElecCashInfTmp.class;
	}

}
