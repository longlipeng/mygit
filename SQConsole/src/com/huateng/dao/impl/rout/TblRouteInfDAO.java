package com.huateng.dao.impl.rout;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.rout.TblRouteInfo;

public class TblRouteInfDAO extends _RootDAO<TblRouteInfo> implements com.huateng.dao.iface.rout.TblRouteInfDAO {
	public void delete(TblRouteInfo tblRouteInfo) {
		super.delete((Object) tblRouteInfo);
	}

	public void deleteTblRouteInfo(String reserved) {
		super.delete((Object) load(reserved));
	}

	public TblRouteInfo load(String key) {
		return (TblRouteInfo) load(getReferenceClass(), key);
	}
	
	public List<TblRouteInfo> findAllTblRouteInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblRouteInfo getTblRouteInfo(String key) {
		return (TblRouteInfo) get(getReferenceClass(), key);
	}

	public TblRouteInfo loadTblRouteInfo(String key) {
		return (TblRouteInfo) load(getReferenceClass(), key);
	}

	public String save(TblRouteInfo tblRouteInfo) {
		return (String) super.save(tblRouteInfo);
	}

	public void saveOrUpdate(TblRouteInfo tblRouteInfo) {
		super.saveOrUpdate(tblRouteInfo);
	}

	public void update(TblRouteInfo tblRouteInfo) {
		super.update(tblRouteInfo);
	}

	public TblRouteInfo cast (Object object) {
		return (TblRouteInfo) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<TblRouteInfo> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@Override
	protected Class getReferenceClass() {
		return TblRouteInfo.class;
	}

}
