package com.huateng.dao.impl.settle;

import com.huateng.dao._RootDAO;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.po.settle.TblFrozenAccInf;

public class TblFrozenAccInfDAO extends _RootDAO<TblFrozenAccInf> implements
		com.huateng.dao.iface.settle.TblFrozenAccInfDAO {

	public TblFrozenAccInf load(String id) {
		
		return (TblFrozenAccInf) load(getReferenceClass(), id);
	}

	public TblFrozenAccInf get(String id) {
		
		return (TblFrozenAccInf) get(getReferenceClass(), id);
	}

	public void delete(String id) {
		
		super.delete((Object) load(id));

	}

	public String save(TblFrozenAccInf tblFrozenAccInf) {
		
		return (String) super.save(tblFrozenAccInf);
	}

	public void update(TblFrozenAccInf tblFrozenAccInf) {
		
		super.update(tblFrozenAccInf);

	}

	
	protected Class<TblFrozenAccInf> getReferenceClass() {
		
		return TblFrozenAccInf.class;
	}

}
