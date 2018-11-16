package com.huateng.dao.impl.error;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblAlgoDtlPK;

public class TblAlgoDtlDAO extends _RootDAO<TblAlgoDtl> implements
		com.huateng.dao.iface.error.TblAlgoDtlDAO {

	public TblAlgoDtlDAO(){}
	
	public void delete(TblAlgoDtlPK id) {
		super.delete((Object) load(id));
	}

	public void delete(TblAlgoDtl tblAlgoDtl) {
		super.delete((Object) tblAlgoDtl);
	}

	public List<TblAlgoDtl> findAll() {
		return null;
	}
	
	public List<TblAlgoDtl> loadAll(){
		return loadAll(getReferenceClass());
	}

	public TblAlgoDtl get(TblAlgoDtlPK key) {
		return (TblAlgoDtl)get(getReferenceClass(), key);
	}

	public TblAlgoDtl load(TblAlgoDtlPK key) {
		return (TblAlgoDtl)load(getReferenceClass(), key);
	}

	public String save(TblAlgoDtl tblAlgoDtl) {
		return (String)super.save(tblAlgoDtl);
	}

	public void saveOrUpdate(TblAlgoDtl tblAlgoDtl) {
		super.saveOrUpdate(tblAlgoDtl);
	}

	public void update(TblAlgoDtl tblAlgoDtl) {
		super.update(tblAlgoDtl);
	}

	@Override
	protected Class getReferenceClass() {
		return TblAlgoDtl.class;
	}
}
