package com.huateng.dao.impl.rout;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.rout.TblRouteInfoTemp;

@SuppressWarnings("unchecked")
public class TblRoutingDAO extends _RootDAO<TblRouteInfoTemp> implements com.huateng.dao.iface.rout.TblRoutingDAO{

	public TblRoutingDAO(){}
	
	public void delete(String reserved) {
		super.delete((Object) load(reserved));
	}

	public void delete(TblRouteInfoTemp tblRouteInfoTemp) {
		super.delete((Object) tblRouteInfoTemp);
	}

	public List<TblRouteInfoTemp> findAll() {
		return null;
	}

	public TblRouteInfoTemp get(String key) {
		return (TblRouteInfoTemp) get(getReferenceClass(), key);
	}

	public TblRouteInfoTemp load(String key) {
		return (TblRouteInfoTemp) load(getReferenceClass(), key);
	}

	public String save(TblRouteInfoTemp tblRouteInfoTemp) {
		return (String) super.save(tblRouteInfoTemp);
	}

	public void saveOrUpdate(TblRouteInfoTemp tblRouteInfoTemp) {
		super.saveOrUpdate(tblRouteInfoTemp);
	}

	public void update(TblRouteInfoTemp tblRouteInfoTemp) {
		super.update(tblRouteInfoTemp);
	}

	public TblRouteInfoTemp cast (Object object) {
		return (TblRouteInfoTemp) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<TblRouteInfoTemp> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@Override
	protected Class<TblRouteInfoTemp> getReferenceClass() {
		return TblRouteInfoTemp.class;
	}
	
}
