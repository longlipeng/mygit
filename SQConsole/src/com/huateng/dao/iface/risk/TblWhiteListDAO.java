package com.huateng.dao.iface.risk;

import com.huateng.po.risk.TblWhiteList;


public interface TblWhiteListDAO {
	
	public com.huateng.po.risk.TblWhiteList load(String key);
	
	public TblWhiteList get(String key);
	
	public void delete(String id);
	
	public String save(TblWhiteList tblWhiteList);
	
	public void update(TblWhiteList tblWhiteList);




}