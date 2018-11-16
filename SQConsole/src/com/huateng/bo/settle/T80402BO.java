package com.huateng.bo.settle;

import java.util.List;

import com.huateng.po.settle.TblFrozenAccInf;

public interface T80402BO {
    
	public String add(TblFrozenAccInf tblFrozenAccInf) throws Exception;
	public String delete(String id) throws Exception;
	public TblFrozenAccInf get(String id);
	public String update(TblFrozenAccInf tblFrozenAccInf) throws Exception;
	public String updateAll(List<TblFrozenAccInf> list)throws Exception;
}
