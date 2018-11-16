package com.huateng.bo.base;

import java.util.List;
import com.huateng.po.base.TblHolidays;

public interface T10209BO {
	public String add(TblHolidays tblholidays);
		
	public String update(TblHolidays tblholidays);
		
	public TblHolidays get(String branchId);
	
	public void delete(String branchId);
	
	public  String update(List<TblHolidays> tblholidays) ;

}
