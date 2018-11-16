package com.huateng.dao.iface.base;


import com.huateng.po.base.TblCityCodeCode;

public interface TblCityCodeCodeDAO {
	public Class<TblCityCodeCode> getReferenceClass() ;
	public TblCityCodeCode cast (Object object);
	public TblCityCodeCode load(String id);
	public TblCityCodeCode get(String id);
	public void save(TblCityCodeCode tblCityCodeCode);
	public void saveOrUpdate(TblCityCodeCode tblCityCodeCode);
	public void update(TblCityCodeCode tblCityCodeCode);
	public void delete(TblCityCodeCode tblCityCodeCode);
	public void delete(String id);
}
