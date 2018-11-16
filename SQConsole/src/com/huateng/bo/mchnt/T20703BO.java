package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblHisDiscAlgo1;


public interface T20703BO {
    public String add(TblHisDiscAlgo1 tblHisDiscAlgo1);
	
	public String update(TblHisDiscAlgo1 tblHisDiscAlgo1);
	
	public TblHisDiscAlgo1 get(String id);
	public void delete(String id);
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10401BO#update(java.util.List)
	 */
	public  String update(List<TblHisDiscAlgo1> tblHisDiscAlgo1List) ;
}
