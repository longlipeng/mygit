package com.huateng.dao.impl.base;

import com.huateng.dao._RootDAO;
import com.huateng.po.base.TblAgentInfo;
import com.huateng.po.risk.TblWhiteList;

public class TblAgentInfoDAO extends _RootDAO<TblAgentInfo> implements
		com.huateng.dao.iface.base.TblAgentInfoDAO {

	public TblAgentInfo load(String key) {
		return (TblAgentInfo) load(getReferenceClass(), key);
	}

	public TblAgentInfo get(String agentNo) {
		return (TblAgentInfo) get(getReferenceClass(), agentNo);
	}

	@Override
	protected Class getReferenceClass() {
		return TblAgentInfo.class;
	}

	public void delete(String agentNo) {
		super.delete((Object) load(agentNo));
		
	}

	public String save(TblAgentInfo tblAgentInfo) {
		return (String) super.save(tblAgentInfo);
	}

	public void update(TblAgentInfo tblAgentInfo) {
		super.update(tblAgentInfo);
	}

}
