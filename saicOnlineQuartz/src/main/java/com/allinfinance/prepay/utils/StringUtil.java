package com.allinfinance.prepay.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtil {
	static Logger logger = Logger.getLogger(StringUtil.class);

	public static boolean isNotEmpty(String string) {
		if (string != null && !"".equals(string)) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String string) {
		return !isNotEmpty(string);
	}

/*	public static void main(String args[]) throws Exception {
		logger.debug(getFormatStr("1", "0", 10, true));
	}*/

	public static String getFormatStr(String str, String appendStr, int length,
			boolean appendBefore) throws Exception {

		if (null == str) {
			str = "";
		}
		if (null == appendStr) {
			appendStr = "0";
		}
		StringBuffer strBuffer = null;
		int circleNum = length - str.length();
		if (circleNum < 0) {
			str = str.substring(length);
			strBuffer = new StringBuffer(str);
		} else {
			strBuffer = new StringBuffer(str);
			for (int i = 0; i < circleNum; i++) {
				if (appendBefore) {
					strBuffer.insert(0, appendStr);
				} else {
					strBuffer.append(appendStr);
				}
			}
		}
		return strBuffer.toString();

	}
	
	 public static boolean isNumeric(String str) {
	        Pattern pattern = Pattern.compile("0|[1-9]{1}[0-9]{0,6}");
	        Matcher isNum = pattern.matcher(str);
	        if (isNum.matches()) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	 
	 public static String generate(String strCardNo) {
	        byte[] bytes = strCardNo.getBytes();
	        boolean isOdd = bytes.length % 2 == 1;
	        int b = 0;
	        for (int i = bytes.length - 1; i >= 0; i--) {
	            if ((i % 2 == 1) != isOdd) {
	                b += ((bytes[i] - 48) * 2) % 10;
	                b += ((bytes[i] - 48) * 2) / 10;
	            } else {
	                b += (bytes[i] - 48);
	            }
	        }
	        int c = 10 - b % 10;
	        if (c == 10) {
	            c = 0;
	        }
	        return strCardNo + c;
	    }
	

}