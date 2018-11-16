package com.huateng.bo.impl.base;

import java.util.List;
import com.huateng.bo.base.T10209BO;
import com.huateng.dao.iface.base.TblHolidaysDao;
import com.huateng.po.base.TblHolidays;

public class T10209BOTarget implements T10209BO{
	private TblHolidaysDao tblHolidaysDao;
	
	/**
	 * @return the tblholidaysDao
	 */
	public TblHolidaysDao getTblHolidaysDao() {
		return tblHolidaysDao;
	}

	/**
	 * @param tblholidaysDao the tblholidaysDao to set
	 */
	public void setTblHolidaysDao(TblHolidaysDao tblHolidaysDao) {
		this.tblHolidaysDao = tblHolidaysDao;
	}
	/**
	 * @return the cstMchtFeeInfDAO
	 */
	
	public String add(TblHolidays tblHolidays) {
		tblHolidaysDao.save(tblHolidays);
		return "00";
	
	}

	public TblHolidays get(String branchId) {
		return tblHolidaysDao.get(branchId);
	}

	public String update(TblHolidays tblHolidays) {
		tblHolidaysDao.update(tblHolidays);
		return "00";
	}

	public void delete(String branchId) {
		tblHolidaysDao.delete(branchId);
		
	}
	public String update(List<TblHolidays> tblHolidaysList) {
		for(TblHolidays tblOprInfo : tblHolidaysList) {
			update(tblOprInfo);
		}
		return "00";
	}
}

