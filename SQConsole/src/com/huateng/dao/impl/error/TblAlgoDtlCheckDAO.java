package com.huateng.dao.impl.error;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.error.TblAlgoDtlCheck;

public class TblAlgoDtlCheckDAO extends _RootDAO<TblAlgoDtlCheck> implements
		com.huateng.dao.iface.error.TblAlgoDtlCheckDAO {

	public TblAlgoDtlCheckDAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblAlgoDtlCheck tblAlgoDtlCheck) {
		super.delete((Object) tblAlgoDtlCheck);
	}

	public List<TblAlgoDtlCheck> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblAlgoDtlCheck get(String key) {
		return (TblAlgoDtlCheck)get(getReferenceClass(), key);
	}

	public TblAlgoDtlCheck load(String key) {
		return (TblAlgoDtlCheck)load(getReferenceClass(), key);
	}

	public List<TblAlgoDtlCheck> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	public String save(TblAlgoDtlCheck tblAlgoDtlCheck) {
		return (String)super.save(tblAlgoDtlCheck);
	}

	public void saveOrUpdate(TblAlgoDtlCheck tblAlgoDtlCheck) {
		super.saveOrUpdate(tblAlgoDtlCheck);
	}

	public void update(TblAlgoDtlCheck tblAlgoDtlCheck) {
		super.update(tblAlgoDtlCheck);
	}

	@Override
	protected Class getReferenceClass() {
		return TblAlgoDtlCheck.class;
	}

}
