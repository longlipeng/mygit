package com.huateng.dao.iface.settle;

import com.huateng.po.settle.TblFrozenAccInf;

public interface TblFrozenAccInfDAO {
  
	public TblFrozenAccInf load(String id);
	public TblFrozenAccInf get(String id);
	public void delete(String id);
	public String save(TblFrozenAccInf tblFrozenAccInf);
	public void update(TblFrozenAccInf tblFrozenAccInf);
}
