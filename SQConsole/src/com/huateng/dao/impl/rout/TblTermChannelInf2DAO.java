package com.huateng.dao.impl.rout;

import java.util.List;

import com.huateng.dao._RootDAO;
import com.huateng.po.rout.TblTermChannelInf2;

public class TblTermChannelInf2DAO extends _RootDAO<TblTermChannelInf2> implements
		com.huateng.dao.iface.rout.TblTermChannelInf2DAO {

	public TblTermChannelInf2DAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblTermChannelInf2 tblTermChannelInf2) {
		super.delete((Object) tblTermChannelInf2);
	}

	public List<TblTermChannelInf2> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblTermChannelInf2 get(String id) {
		return (TblTermChannelInf2) get(getReferenceClass(), id);
	}

	public TblTermChannelInf2 load(String id) {
		return (TblTermChannelInf2) load(getReferenceClass(), id);
	}

	public String save(TblTermChannelInf2 tblTermChannelInf2) {
		return (String) super.save(tblTermChannelInf2);
	}

	public void saveOrUpdate(TblTermChannelInf2 tblTermChannelInf2) {
		super.saveOrUpdate(tblTermChannelInf2);
	}

	public void update(TblTermChannelInf2 tblTermChannelInf2) {
		super.update(tblTermChannelInf2);
	}

	public TblTermChannelInf2 cast (Object object) {
		return (TblTermChannelInf2) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<TblTermChannelInf2> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@Override
	protected Class<TblTermChannelInf2> getReferenceClass() {
		return TblTermChannelInf2.class;
	}

}
