package com.huateng.dao.iface.base;

import com.huateng.po.base.TblHolidays;

public interface TblHolidaysDao {
	public Class<TblHolidays> getReferenceClass () ;
	public TblHolidays cast (Object object);
	public TblHolidays load(String id);
	public TblHolidays get(String id);
	public void save(TblHolidays tblholidays);
	public void saveOrUpdate(TblHolidays tblholidays);
	public void update(TblHolidays tblholidays);
	public void delete(TblHolidays tblholidays);
	public void delete(String id);
}