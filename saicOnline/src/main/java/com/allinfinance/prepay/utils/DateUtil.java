package com.allinfinance.prepay.utils;

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
     * 鏍规嵁涓�涓綋鍓嶆棩鏈熷拰闇�瑕佹坊鍔犵殑鏈堟暟寰楀埌涓�涓崱鐨勬湁鏁堟湡锛堜负褰撳墠鏃ユ湡鍔犱笂鏈堝悗鐨勬棩鏈熺殑鏈堢殑鏈�鍚庝竴澶╋級
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
     * 鏍规嵁涓�涓狣ATE鍙栬鏃ユ湡鏈堢殑鏈�鍚庝竴澶�
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
     * 鏍规嵁浼犲叆鐨勫瓧绗︿覆鏃堕棿鏍煎紡鎴恲yyy-MM-dd
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
     * 鏍规嵁浼犲叆鐨勫瓧绗︿覆鏃堕棿鏍煎紡鎴恲yyyMMdd
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
     * 鑾峰彇褰撳墠鏃堕棿
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 鑾峰彇褰撳墠鏃堕棿 24
     * 
     * @return
     */
    public static String getCurrentTime24() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 鑾峰彇褰撳墠鏃堕棿 24
     * 
     * @return
     */
    public static String getCurrenTime24() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * Date鍨嬬殑鏃堕棿杞崲鎴怱tring鍨嬬殑鏍煎紡涓猴細yyyyMMdd
     * 
     * @return
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 杩斿洖鎸囧畾鏃ユ湡鏍煎紡鐨勫瓧绗︿覆 濡傦細yyyyMMdd,yyyyMMddhhmmss
     * 
     * @param format
     * @return
     */
    public static String getCurrentDateFormatStr(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * Date鍨嬬殑鏃堕棿杞崲鎴怱tring鍨嬬殑鏍煎紡涓猴細yyyy-MM-dd
     * 
     * @return
     */

    public static String getStringDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 鑾峰彇褰撳墠鏃ユ湡
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
     * 鑾峰緱褰撳墠鏃ユ湡鍜屾椂闂�
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
     * 鑾峰彇鏍煎紡鍚庣殑鏁版嵁 锛岃繑鍥瀥yyyMMdd
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
     * yyyyMMddHHmmss鏃ユ湡杞崲鎴怱tring yyyy-MM-dd HH:mm:ss
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
     * Date鏃ユ湡杞崲鎴怱tring
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
     * Date鏃ユ湡杞垚甯�-String
     */
    public static final String dateToSting_(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * String鏃ユ湡杞崲鎴怐ate
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
     * String鏃ユ湡杞崲鎴怐ate(yyyymmdd)
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
     * 鎸夋棩鍔�,鎸囧畾鏃ユ湡
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
     * 鎸夊皬鏃跺姞锛屾寚瀹氭棩鏈�
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
     * 鎸夋湀鍔�
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
     * 姣旇緝date1-date2涓や釜鏃堕棿宸�()姣
     * 
     * @param date1
     * @param date2
     * @return
     * @see [鐩稿叧绫�/鏂规硶](鍙��)
     * @since [浜у搧/妯″潡鐗堟湰](鍙��)
     */
    public static long differ(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
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
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        if(str.length()!=8){
        	return false;
        }
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
         try {
        	 // 璁剧疆lenient涓篺alse. 鍚﹀垯SimpleDateFormat浼氭瘮杈冨鏉惧湴楠岃瘉鏃ユ湡锛屾瘮濡�2007/02/29浼氳鎺ュ彈锛屽苟杞崲鎴�2007/03/01
            format.setLenient(false);
            format.parse(str);
         } catch (ParseException e) {
           // e.printStackTrace();
 // 濡傛灉throw java.text.ParseException鎴栬�匩ullPointerException锛屽氨璇存槑鏍煎紡涓嶅
            convertSuccess=false;
        } 
        return convertSuccess;
 }

}
