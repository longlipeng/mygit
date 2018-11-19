package com.allinfinance.prepay.utils;

import java.math.BigDecimal;

public class MathUtil {
	/**
	 * �ж��ַ����Ƿ�Ϊ��������
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
	 *���
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
	 * ���
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
	 * �������
	 */
	public static BigDecimal subtract(String number,String otherNumber){
		BigDecimal number_decimal = new BigDecimal(number);
		
		BigDecimal otherNumber_decimal = new BigDecimal(otherNumber);
		return number_decimal.subtract(otherNumber_decimal);
		
	}
	
	/**
	 * �������
	 */
	public static BigDecimal divide(String number,String otherNumber){
		BigDecimal number_decimal = new BigDecimal(number);
		
		BigDecimal otherNumber_decimal = new BigDecimal(otherNumber);
		return number_decimal.divide(otherNumber_decimal);
		
	}
	
	
	
/*	public static void main(String args[]){
		logger.info(add("1","2","3"));
	}*/

}
