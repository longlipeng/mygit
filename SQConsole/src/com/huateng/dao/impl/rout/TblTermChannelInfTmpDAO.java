package com.huateng.dao.impl.rout;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.rout.TblTermChannelInfTmp;

public class TblTermChannelInfTmpDAO extends _RootDAO<TblTermChannelInfTmp> implements
		com.huateng.dao.iface.rout.TblTermChannelInfTmpDAO {

	public TblTermChannelInfTmpDAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblTermChannelInfTmp TblTermChannelInfTmp) {
		super.delete((Object) TblTermChannelInfTmp);
	}

	public List<TblTermChannelInfTmp> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblTermChannelInfTmp get(String id) {
		return (TblTermChannelInfTmp) get(getReferenceClass(), id);
	}

	public TblTermChannelInfTmp load(String id) {
		return (TblTermChannelInfTmp) load(getReferenceClass(), id);
	}

	public String save(TblTermChannelInfTmp TblTermChannelInfTmp) {
		return (String) super.save(TblTermChannelInfTmp);
	}

	public void saveOrUpdate(TblTermChannelInfTmp TblTermChannelInfTmp) {
		super.saveOrUpdate(TblTermChannelInfTmp);
	}

	public void update(TblTermChannelInfTmp TblTermChannelInfTmp) {
		super.update(TblTermChannelInfTmp);
	}

	public TblTermChannelInfTmp cast (Object object) {
		return (TblTermChannelInfTmp) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<TblTermChannelInfTmp> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@Override
	protected Class<TblTermChannelInfTmp> getReferenceClass() {
		return TblTermChannelInfTmp.class;
	}

	public TblTermChannelInfTmp getTmp(String id) {
		// TODO Auto-generated method stub
		return (TblTermChannelInfTmp) get(getReferenceClass(), id);
	}

}
