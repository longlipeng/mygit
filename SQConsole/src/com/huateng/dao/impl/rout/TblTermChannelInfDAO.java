package com.huateng.dao.impl.rout;

import java.util.List;
import com.huateng.dao._RootDAO;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;

public class TblTermChannelInfDAO extends _RootDAO<TblTermChannelInf> implements
		com.huateng.dao.iface.rout.TblTermChannelInfDAO {

	public TblTermChannelInfDAO(){}
	
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	public void delete(TblTermChannelInf tblTermChannelInf) {
		super.delete((Object) tblTermChannelInf);
	}

	public List<TblTermChannelInf> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TblTermChannelInf get(String id) {
		return (TblTermChannelInf) get(getReferenceClass(), id);
	}
	
	public TblTermChannelInfTmp getTmp(String id) {
		return (TblTermChannelInfTmp) get(getReferenceClassTmp(), id);
	}

	public TblTermChannelInf load(String id) {
		return (TblTermChannelInf) load(getReferenceClass(), id);
	}

	public String save(TblTermChannelInf tblTermChannelInf) {
		return (String) super.save(tblTermChannelInf);
	}
	//add
	public void saveOrUpdate(TblTermChannelInf tblTermChannelInf) {
		super.save(tblTermChannelInf);
	}

	public void update(TblTermChannelInf tblTermChannelInf) {
		super.update(tblTermChannelInf);
	}
	
	public void updateTmp(TblTermChannelInfTmp tblTermChannelInfTmp) {
		super.update(tblTermChannelInfTmp);
	}

	public TblTermChannelInf cast (Object object) {
		return (TblTermChannelInf) object;
	}
	
	@SuppressWarnings("unchecked")
	public List<TblTermChannelInf> loadAll(){
		return loadAll(getReferenceClass());
	}
	
	@Override
	protected Class<TblTermChannelInf> getReferenceClass() {
		return TblTermChannelInf.class;
	}

	public void saveOrUpdate(TblTermChannelInfTmp tblTermChannelInfTmp) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblTermChannelInfTmp);
	}


	protected Class<TblTermChannelInfTmp> getReferenceClassTmp() {
		return TblTermChannelInfTmp.class;
	}
	

}
