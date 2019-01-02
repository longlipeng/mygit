package com.huateng.bo.settle;

import java.util.List;

import com.huateng.po.settle.TblMchtSumrzInf;

public interface T80224BO {

	/**
	 * 根据主键查询
	 * @param seqNo
	 * @return
	 */
	public TblMchtSumrzInf get(Integer seqNo);
	
	
	public String update(List<TblMchtSumrzInf> list) throws Exception;


	public String update(TblMchtSumrzInf tblMchtSumrzInf) throws Exception;
	
	/**
	 * 自定义列名查询
	 * @return
	 */
	public List<TblMchtSumrzInf> getSumrInf(String sumrzBatch);
	
	
}
