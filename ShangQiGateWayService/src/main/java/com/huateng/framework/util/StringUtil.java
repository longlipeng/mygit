package com.huateng.framework.util;

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

}