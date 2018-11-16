package com.huateng.dao.iface.base;

import com.huateng.po.base.TblAgentDivide;



public interface TblAgentDivideDAO {
    
	public TblAgentDivide load(String uuid);
	
	public void delete(String uuid);
	
	public String save(TblAgentDivide tblAgentDivide);
	
	public void update(TblAgentDivide tblAgentDivide);
	
	public void saveOrUpdate(TblAgentDivide tblAgentDivide);
	
	public  TblAgentDivide query(String  uuid);
}
