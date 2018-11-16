package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblHisDiscAlgo1;


public interface TblHisDiscAlgo1DAO {
	public Class<TblHisDiscAlgo1> getReferenceClass () ;
	public TblHisDiscAlgo1 cast (Object object);
	public TblHisDiscAlgo1 load(String key);
	public TblHisDiscAlgo1 get(String key);
	public String save(TblHisDiscAlgo1 tblHisDiscAlgo1);
	public void saveOrUpdate(TblHisDiscAlgo1 tblHisDiscAlgo1);
	public void update(TblHisDiscAlgo1 tblHisDiscAlgo1);
	public void delete(TblHisDiscAlgo1 tblHisDiscAlgo1);
	public void delete(String id);
}
