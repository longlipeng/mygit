package com.huateng.dao.impl.rout;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.rout.TblRouteChgInf;

public class TblRouteChgInfDAO extends _RootDAO<TblRouteChgInf> implements com.huateng.dao.iface.rout.TblRouteChgInfDAO {

	public void delete(TblRouteChgInf tblRouteChgInf) {
		super.delete((Object) tblRouteChgInf);
	}

	public void deleteTblRouteChgInf(String key) {
		super.delete((Object) load(key));
	}

	public TblRouteChgInf load(String key) {
		return (TblRouteChgInf) load(getReferenceClass(), key);
	}
	
	public List<TblRouteChgInf> findAllTblRouteChgInf() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblRouteChgInf getTblRouteChgInf(String key) {
		return (TblRouteChgInf) get(getReferenceClass(), key);
	}

	public TblRouteChgInf loadTblRouteChgInf(String key) {
		return (TblRouteChgInf) load(getReferenceClass(), key);
	}

	public String save(TblRouteChgInf tblRouteChgInf) {
		return (String) super.save(tblRouteChgInf);
	}

	public void saveOrUpdate(TblRouteChgInf tblRouteChgInf) {
		super.saveOrUpdate(tblRouteChgInf);
	}

	public void update(TblRouteChgInf tblRouteChgInf) {
		super.update(tblRouteChgInf);
	}

	public TblRouteChgInf cast (Object object) {
		return (TblRouteChgInf) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<TblRouteChgInf> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class getReferenceClass() {
		return TblRouteChgInf.class;
	}
}
