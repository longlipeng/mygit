package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblAgentDivide;


public interface T11502BO {
	public String deleteList(List<String> list) throws Exception;
	public String add(TblAgentDivide tblAgentDivide) throws Exception;
	public String update(TblAgentDivide tblAgentDivide) throws Exception;
	public TblAgentDivide query(String uuid);
	public String updateList(List<TblAgentDivide> list) throws Exception;
	public String saveOrUpdate(TblAgentDivide tblAgentDivide)throws Exception;
	public String saveOrUpdate(List<TblAgentDivide> list) throws Exception;
}
