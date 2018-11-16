package com.huateng.dao.iface.base;

import com.huateng.po.base.TblAgentDivideTmp;

public interface TblAgentDivideTmpDAO {
	public TblAgentDivideTmp load(String uuid);
	public void delete(String uuid);
	public String  save(TblAgentDivideTmp tblAgentDivideTmp);
	public TblAgentDivideTmp query(String uuid);
	public void update(TblAgentDivideTmp tblAgentDivideTmp);
	public void saveOrUpdate(TblAgentDivideTmp tblAgentDivideTmp);

}
