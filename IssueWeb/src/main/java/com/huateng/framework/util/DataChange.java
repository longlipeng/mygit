package com.huateng.framework.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.huateng.framework.exception.BizServiceException;


public class DataChange {
	private static Logger logger = Logger.getLogger(DataChange.class);
	
	public static String getGenderDesc(String gender) {
		if (gender == null || gender.trim().length() == 0) {
			return "";
		} else if (gender.trim().equals("1")) {
			return "男";
		} else if (gender.trim().equals("2")) {
			return "女";
		} else {
			return "";
		}
	}

	public static String getBirthYear(String birthDay) {
		if (birthDay == null || birthDay.trim().length() < 8) {
			return "";
		} else {
			return birthDay.substring(0, 4);
		}
	}

	public static String getBirthMonth(String birthDay) {
		if (birthDay == null || birthDay.trim().length() < 8) {
			return "";
		} else {
			return birthDay.substring(4, 6);
		}
	}

	public static String getBirthDay(String birthDay) {
		if (birthDay == null || birthDay.trim().length() < 8) {
			return "";
		} else {
			return birthDay.substring(6, 8);
		}
	}

	public static String jspAmtToDBAmt(String vstrAmount) {
		int iDivPos = 0;
		String strAmount = vstrAmount;
		String tmpStr = "";
		String intPart = "";
		String decPart = "";
		String errorStr = "errorString";
		boolean isFirstPoint = true;
		if (vstrAmount == null) {
			return errorStr;
		}
		// check the input amount is consist of numbers
		for (int i = 0; i < strAmount.length(); i++) {
			if (strAmount.charAt(i) <= '9' && strAmount.charAt(i) >= '0') {
				tmpStr = tmpStr.concat(strAmount.substring(i, i + 1));
			} else if (strAmount.charAt(i) == '.' && isFirstPoint) {
				iDivPos = i;
				if (i != (strAmount.length() - 1)) {
					tmpStr = tmpStr.concat(strAmount.substring(i, i + 1));
					isFirstPoint = false;
				}
			} else if (strAmount.charAt(i) != ',') {
				/*
				 * if there is other symbol in the input amount string ,it will
				 * return "errorString"
				 */
				tmpStr = "errorString";
				return tmpStr;
			}
		}
		iDivPos = tmpStr.length();
		// check decimal fraction
		if (!isFirstPoint) {
			// find .
			for (int i = 0; i < tmpStr.length(); i++) {
				if (tmpStr.charAt(i) == '.') {
					iDivPos = i;
				}
			}
			// devide amount into two parts
			intPart = tmpStr.substring(0, iDivPos);
			decPart = tmpStr.substring(iDivPos + 1);
			if (decPart.length() > 2) {
				/*
				 * if there is more than 2 numbers behind the "." ,it will
				 * return "errorString"
				 */
				tmpStr = "errorString";
				return tmpStr;
			} else {
				// change decimal fraction into database format
				for (int i = (2 - decPart.length()); i > 0; i--) {
					decPart = decPart + "0";
				}
			}
			tmpStr = intPart + decPart;
			if (tmpStr.length() > 12) {
				/*
				 * if intpart + decpart is more than 12 bits ,it will return
				 * "errorString"
				 */
				tmpStr = "errorString";
				return tmpStr;
			}
		} else {
			if (tmpStr.length() > 10) {
				/*
				 * if there is no "." in the input amount , then the maximal
				 * length ofthe input amount is 10 bits
				 */
				tmpStr = "errorString";
				return tmpStr;
			}
			tmpStr = tmpStr + "00";
		}
		// fill up to 12 bit
		int len = tmpStr.length();
		for (int i = 0; i < 12 - len; i++) {
			tmpStr = "0" + tmpStr;
		}

		return tmpStr;
	}

	public static String changUnlawful(String s) {
		s = s.replace("&", "&amp;").replace("#",
				"&#35;").replace("<", "&lt;").replace(">", "&gt;")
				.replaceAll("\"", "&quot;").replace("'", "&#39;")
				.replace("(", "&#40;").replace(")", "&#41;").replace(
						"%", "&#37;").replace("+", "&#43;").replace("-",
						"&#45;");
		return s;
	}
	
	/**
	 * List 转成  字符串
	 * @param list
	 * @return
	 */
	public static String listToString(List<?> list) throws BizServiceException{
		String string="";
		try {
			string = list.toString().replace(" ", "");
			string=string.substring(1, string.length()-1);
		} catch (Exception e) {
			
			logger.error(e.getMessage());
		}
		return string;
	}
	
	
	
	/**
	 * List 转成  数组
	 * @param list
	 * @return
	 */
	public static String[] listToStringArray(List<?> list) throws BizServiceException{
		String string="";
		String[] s=new String[]{};
		try {
			string = list.toString().replace(" ", "");
			string=string.substring(1, string.length()-1);
			s=string.split(",");
		} catch (Exception e) {
			
			logger.error(e.getMessage());
		}
		return s;
	}
	
	public static boolean check(String reg,String string){
		Pattern p= Pattern.compile(reg);
		Matcher m=p.matcher(string);
		return m.matches();
	}
}
