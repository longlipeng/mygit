package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblAgentDivide;

public class TblAgentDivideDAO extends _RootDAO<TblAgentDivide> implements
		com.huateng.dao.iface.base.TblAgentDivideDAO {

	public TblAgentDivide load(String uuid) {
		return (TblAgentDivide)load(getReferenceClass(),uuid);
	}

	public void delete(String uuid) {
		super.delete((Object)load(uuid));
	}


	public String save(TblAgentDivide tblAgentDivide) {
		return  (String) super.save(tblAgentDivide);
	}

	public void update(TblAgentDivide tblAgentDivide) {
		super.update(tblAgentDivide);
	}
	public void saveOrUpdate(TblAgentDivide tblAgentDivide) {
		super.saveOrUpdate(tblAgentDivide);
		
	}

	
	@Override
	protected Class getReferenceClass() {
		return TblAgentDivide.class;
	}

	public TblAgentDivide query(String uuid) {
		return (TblAgentDivide) get(getReferenceClass(), uuid);
	}




}
