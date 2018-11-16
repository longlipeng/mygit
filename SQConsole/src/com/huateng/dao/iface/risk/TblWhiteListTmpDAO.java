package com.huateng.dao.iface.risk;

import java.util.List;

import com.huateng.po.risk.TblWhiteListTmp;



public interface TblWhiteListTmpDAO {
	
	public TblWhiteListTmp load(String key);
	
	public TblWhiteListTmp get(String key);
	
	public void delete(String key);
	
	public String save(TblWhiteListTmp tblWhiteListTmp);
	
	public void update(TblWhiteListTmp tblWhiteListTmp);
	
	public void saveOrUpdate(TblWhiteListTmp tblWhiteListTmp);
}