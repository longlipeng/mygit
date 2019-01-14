package com.allinfinance.prepay.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * 根据一个当前日期和需要添加的月数得到一个卡的有效期（为当前日期加上月后的日期的月的最后一天）
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
        gregorianCalendar.add(GregorianCalendar.MONDAY, monthCount + 1);
        Date lastDate = gregorianCalendar.getTime();
        if (maxTime >= gregorianCalendar.getTime().getTime()) {
            date = setMonthLastDay(lastDate);
        } else {
            date = setMonthLastDay(maxDate.getTime());
        }
        return date;
    }

    /**
     * 根据一个DATE取该日期月的最后一天
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
     * 
     * @param date
     * @return
     */
    public static String formatStringDate(String date) {
        if (date == null)
            return null;
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }

    /**
     * 根据传入的字符串时间格式成yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static String StringDate(String date) {
        if (date == null)
            return null;
        return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间 24
     * 
     * @return
     */
    public static String getCurrentTime24() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间 24
     * 
     * @return
     */
    public static String getCurrenTime24() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * Date型的时间转换成String型的格式为：yyyyMMdd
     * 
     * @return
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 返回指定日期格式的字符串 如：yyyyMMdd,yyyyMMddhhmmss
     * 
     * @param format
     * @return
     */
    public static String getCurrentDateFormatStr(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * Date型的时间转换成String型的格式为：yyyy-MM-dd
     * 
     * @return
     */

    public static String getStringDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前日期
     */
    public static Date getCurrentDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String curDate = simpleDateFormat.format(new Date());

        try {
            return simpleDateFormat.parse(curDate);
        } catch (ParseException e) {

            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获得当前日期和时间
     * 
     * @return
     */
    public static Date getCurrentDateAndTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String curDate = simpleDateFormat.format(new Date());

        try {
            return simpleDateFormat.parse(curDate);
        } catch (ParseException e) {

            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取格式后的数据 ，返回yyyyMMdd
     */
    public static String getFormatTime(String Date) {
        if (null == Date) {
            return Date;
        }
        if (Date.equals("")) {
            return "";
        }
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(Date.substring(0, 4));
        strBuf.append(Date.substring(5, 7));
        strBuf.append(Date.substring(8, 10));
        return strBuf.toString();
    }

    public static String dbFormatToDateFormat(String dbFormat) {
        if (dbFormat != null && !"".equals(dbFormat) && dbFormat.trim().length() >= 8) {
            StringBuffer strBuf = new StringBuffer(dbFormat.substring(0, 4));
            strBuf.append("-");
            strBuf.append(dbFormat.substring(4, 6));
            strBuf.append("-");
            strBuf.append(dbFormat.substring(6, 8));
            strBuf.append(dbFormat.substring(8));
            return strBuf.toString();
        }
        return dbFormat;
    }

    /**
     * yyyyMMddHHmmss日期转换成String yyyy-MM-dd HH:mm:ss
     * 
     */
    public static String dbFormatString(String dbFormat) {
        if (dbFormat != null && !"".equals(dbFormat) && dbFormat.trim().length() >= 8) {
            StringBuffer strBuf = new StringBuffer(dbFormat.substring(0, 4));
            strBuf.append("-");
            strBuf.append(dbFormat.substring(4, 6));
            strBuf.append("-");
            strBuf.append(dbFormat.substring(6, 8));
            strBuf.append(" ");
            strBuf.append(dbFormat.substring(8, 10));
            strBuf.append(":");
            strBuf.append(dbFormat.substring(10, 12));
            strBuf.append(":");
            strBuf.append(dbFormat.substring(12, 14));
            return strBuf.toString();
        }
        return dbFormat;
    }

    /**
     * Date日期转换成String
     * 
     */
    public static final String date2String(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }

    /***
     * Date日期转成带-String
     */
    public static final String dateToSting_(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * String日期转换成Date
     * 
     */
    public static final Date string2date(String dateStr) {
        if (dateStr == null || dateStr.length() == 0)
            return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * String日期转换成Date(yyyymmdd)
     * 
     */
    public static final Date string2Dateyyyymmdd(String dateStr) {
        if (dateStr == null || dateStr.length() == 0)
            return null;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 按日加,指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static final Date addDay(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按小时加，指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static final Date addHours(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按月加
     * 
     * @param value
     * @return
     */
    public static final Date addMonth(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        // now.add(Calendar.MONTH, 1);
        // now.set(Calendar.DATE, value);
        // return now.getTime();
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 比较date1-date2两个时间差()毫秒
     * 
     * @param date1
     * @param date2
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static long differ(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }
    
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        if(str.length()!=8){
        	return false;
        }
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
         try {
        	 // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
         } catch (ParseException e) {
           // e.printStackTrace();
 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        } 
        return convertSuccess;
 }
    
    /**
     * 根据时间戳返回long
     */
    public static final long longfromyyyyMMddhhmmss(String date) {
            date = dbFormatString(date);
            return Timestamp.valueOf(date).getTime();
    }
    
    /**
     * 获取前一天日期，Date型的时间转换成String型的格式为：yyyyMMddHHmmss 
     * 
     * @return
     */
    public static String getCurrentDateToStr() {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 获取當月第一天，Date型的时间转换成String型的格式为：yyyyMMddHHmmss 
     * @return
     */
    public static String getCurrentMonthToStr(){
        Calendar calendar = Calendar.getInstance();//日历对象        
        // 获取前月的第一天
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(calendar.getTime());
    }

    // public static void main(String[] args) {
    // logger.info(Integer.parseInt("2")*7);
    // logger.info(DateUtil.date2String(DateUtil.addDay(DateUtil.string2Dateyyyymmdd("20111129"),
    // Integer.parseInt("7")-1)));
    // Calendar c=Calendar.getInstance();
    // c.setTime(DateUtil.string2Dateyyyymmdd("20111127"));
    // logger.info(c);
    // logger.info(DateUtil.date2String(DateUtil.addMonth(DateUtil.string2Dateyyyymmdd("20110110"),2)));
    //
    //
    //
    // Calendar calendar=Calendar.getInstance();
    // calendar.setTime(DateUtil.string2Dateyyyymmdd("20111129"));
    // calendar.set(Calendar.DAY_OF_MONTH, 3);
    // Date s=calendar.getTime();
    // logger.info(calendar);
    //
    //
    // logger.info(DateUtil.countCardValidate(DateUtil.string2Dateyyyymmdd("20110110"), 1));
    // logger.info(DateUtil.setMonthLastDay(DateUtil.string2Dateyyyymmdd("20110110")).getDate());
    //
    // logger.info(DateUtil.formatStringDate("20111204"));
    // logger.info(DateUtil.formatStringDate("20111205"));
    //
    // Calendar calendar1=Calendar.getInstance();
    // calendar.setTime(DateUtil.string2Dateyyyymmdd("20111129"));
    // calendar.set(Calendar.DAY_OF_MONTH, 3);
    // Date s1=calendar.getTime();
    // logger.info(calendar1);
    //
    //
    // logger.info(String.format("%20.2f", Double.parseDouble("0.0")/100).trim());
    // BigDecimal a1=new BigDecimal("10000000099.00");
    // logger.info(a1.divide(new BigDecimal(100)));
    // logger.info(getStringDate());
    // double a=new BigDecimal("456").subtract(new BigDecimal("456")).doubleValue();
    // logger.info(a==0);

    // String[] a={"1","2"};
    // logger.info(a.toString());
    // }

}
