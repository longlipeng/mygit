package com.huateng.dao.impl.risk;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblRiskRefuse;

public class TblRiskRefuseDAO extends _RootDAO<com.huateng.po.risk.TblRiskRefuse> implements
		com.huateng.dao.iface.risk.TblRiskRefuseDAO {

	public TblRiskRefuseDAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblRiskRefuse tblRiskRefuse) {
		super.delete((Object) tblRiskRefuse);
	}

	public List<TblRiskRefuse> findAll() {
		return null;
	}

	public TblRiskRefuse get(String key) {
		return (TblRiskRefuse)get(getReferenceClass(), key);
	}

	public TblRiskRefuse cast (Object object) {
		return (TblRiskRefuse) object;
	}
	
	public TblRiskRefuse load(String key) {
		return (TblRiskRefuse)load(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<TblRiskRefuse> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	public String save(TblRiskRefuse tblRiskRefuse) {
		return (String)super.save(tblRiskRefuse);
	}

	public void saveOrUpdate(TblRiskRefuse tblRiskRefuse) {
		super.saveOrUpdate(tblRiskRefuse);
	}

	public void update(TblRiskRefuse tblRiskRefuse) {
		super.update(tblRiskRefuse);
	}

	@Override
	protected Class getReferenceClass() {
		return com.huateng.po.risk.TblRiskRefuse.class;
	}

}
