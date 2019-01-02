package com.huateng.dao.iface.settle;

import java.util.List;

import com.huateng.po.settle.TblMchtSumrzInf;

public interface TblMchtSumrzInfDAO {

	public TblMchtSumrzInf get(Integer seqNo);

	void update(TblMchtSumrzInf tblMchtSumrzInf);

	
	/**
	 * 自定义列名查询
	 * @return
	 */
	public List<TblMchtSumrzInf> getSumrInf(String sumrzBatch);
}
