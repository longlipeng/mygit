package com.huateng.hstserver.frameworkUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * 根据丄1�7个当前日期和霄1�7要添加的月数得到丄1�7个卡的有效期（为当前日期加上月后的日期的月的朄1�7后一天）
	 * 
	 * @param date
	 * @param monthCount
	 * @return
	 */
	public static Date countCardValidate(Date date, int monthCount) {
		Calendar maxDate = Calendar.getInstance();
		maxDate.set(Calendar.YEAR, 2099);
		maxDate.set(Calendar.MONTH, 12);
		maxDate.set(Calendar.DATE, 31);
		long maxTime = maxDate.getTimeInMillis();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		gregorianCalendar.add(GregorianCalendar.MONDAY, monthCount+1);
		Date lastDate = gregorianCalendar.getTime();
		if(maxTime>=gregorianCalendar.getTime().getTime()){
			date = setMonthLastDay(lastDate);
		}else{
			date = setMonthLastDay(maxDate.getTime());
		}
		return date;
	}

	/**
	 * 根据丄1�7个DATE取该日期月的朄1�7后一处1�7
	 * 
	 * @param date
	 * @return
	 */
	public static Date setMonthLastDay(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(GregorianCalendar.DATE, 1);
		gregorianCalendar.add(GregorianCalendar.DATE, -1);
		date = gregorianCalendar.getTime();
		return date;
	}
	/**
	 * 根据传入的字符串时间格式成yyyy-MM-dd
	 * @param date2
	 * @return
	 */
	public static String formatStringDate(String date){
		if(date==null) return null;
		return date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
	}

}
