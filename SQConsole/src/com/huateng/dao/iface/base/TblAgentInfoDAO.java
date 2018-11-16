package com.huateng.dao.iface.base;

import com.huateng.po.base.TblAgentInfo;

public interface TblAgentInfoDAO {
	
	public TblAgentInfo load(String key);
	
	public TblAgentInfo get(String agentNo);
	
	public void delete(String agentNo);
	
	public String save(TblAgentInfo tblAgentInfo);
	
	public void update(TblAgentInfo tblAgentInfo);

}
