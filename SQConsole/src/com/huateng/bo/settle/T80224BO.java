package com.huateng.bo.settle;

import java.util.List;

import com.huateng.po.settle.TblMchtSumrzInf;

public interface T80224BO {

	public TblMchtSumrzInf get(Integer seqNo);
	
	
	public String update(List<TblMchtSumrzInf> list) throws Exception;


	public String update(TblMchtSumrzInf tblMchtSumrzInf) throws Exception;
}
