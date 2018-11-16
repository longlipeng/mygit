package com.huateng.bo.base;

import java.util.List;
import com.huateng.po.base.TblCityCodeCode;

public interface T1020101BO {
    public String add(TblCityCodeCode tblCityCodeCode);
	
	public String update(TblCityCodeCode tblCityCodeCode);
	
	public TblCityCodeCode get(String id);
	public void delete(String id);
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10401BO#update(java.util.List)
	 */
	public  String update(List<TblCityCodeCode> tblCityCodeCodeList) ;
}
