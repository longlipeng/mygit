package com.huateng.framework.util;

import java.math.BigDecimal;

public class MathUtil {

	/**
	 * 判断字符串是否为货币类型
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isCurrent(String s) {

		return s.matches("\\d+(\\.\\d{1,2})?");

	}
	public static boolean isNum(String s){
	    return s.matches("\\[0]?d+");
	}
	
	/**
	 *相加
	 */
	public static BigDecimal add(String ...datas){
		
		BigDecimal total = new BigDecimal("0");
		BigDecimal data_decimal= new BigDecimal("0");
		for (String data:datas){
			if(data==null||"".equals(data)){
				data_decimal = new BigDecimal("0");
			}else{
				data_decimal = new BigDecimal(data);
			}
			total=total.add(data_decimal);
		}
		return total;
	}
	
	
	/**
	 * 相乘
	 */
	
	public static BigDecimal multiply(String ...datas){
		BigDecimal total = new BigDecimal("1");
		BigDecimal data_decimal = new BigDecimal("0");
		for (String data:datas){
			if(data==null||"".equals(data)){
				data_decimal = new BigDecimal("0");
			}else{
				data_decimal = new BigDecimal(data);
			}
			total=total.multiply(data_decimal);
		}
		return total;
		
	}
	
	/**
	 * 两数相减
	 */
	public static BigDecimal subtract(String number,String otherNumber){
		BigDecimal number_decimal = new BigDecimal(number);
		
		BigDecimal otherNumber_decimal = new BigDecimal(otherNumber);
		return number_decimal.subtract(otherNumber_decimal);
		
	}
	
	/**
	 * 两数相除
	 */
	public static BigDecimal divide(String number,String otherNumber){
		BigDecimal number_decimal = new BigDecimal(number);
		
		BigDecimal otherNumber_decimal = new BigDecimal(otherNumber);
		return number_decimal.divide(otherNumber_decimal);
		
	}
	
	
	
/*	public static void main(String args[]){
		logger.info(,"2","3"));
	}*/
	
}
