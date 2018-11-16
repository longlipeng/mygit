package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblAgentDivideTmp;

public class TblAgentDivideTmpDAO extends _RootDAO<TblAgentDivideTmp> implements
		com.huateng.dao.iface.base.TblAgentDivideTmpDAO {

	public TblAgentDivideTmp load(String uuid) {
		return (TblAgentDivideTmp)load(getReferenceClass(),uuid);
	}

	public void delete(String uuid) {
		super.delete((Object)load(uuid));

	}

	public String save(TblAgentDivideTmp tblAgentDivideTmp) {
		return  (String) super.save(tblAgentDivideTmp);
	}

	public TblAgentDivideTmp query(String uuid) {
		return (TblAgentDivideTmp) get(getReferenceClass(), uuid);
	}

	public void update(TblAgentDivideTmp tblAgentDivideTmp) {
		super.update(tblAgentDivideTmp);

	}

	public void saveOrUpdate(TblAgentDivideTmp tblAgentDivideTmp) {
		super.saveOrUpdate(tblAgentDivideTmp);

	}

	@Override
	protected Class getReferenceClass() {
		return TblAgentDivideTmp.class;
	}

}
