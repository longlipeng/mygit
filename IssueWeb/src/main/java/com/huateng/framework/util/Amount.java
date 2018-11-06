package com.huateng.framework.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Amount {
	private static BigDecimal divisor = new BigDecimal(100);
	
	private static final String  pattern = "([-]?)[0-9]+(.[0-9]+)?";
	
	public static  String getReallyAmount(String amount) {
		if(!"".equals(amount)&&amount!=null){
			Pattern p = Pattern.compile(pattern);		
			Matcher m = p.matcher(amount.trim());
			boolean b = m.matches();
			if(b){
				BigDecimal bigDecimal = new BigDecimal(amount.trim());
				return bigDecimal.divide(divisor).toString();
			}
		}
		return amount;
	}
	
	public static String getDataBaseAmount(String amount){
		if(!"".equals(amount)&&amount!=null){
			Pattern p = Pattern.compile(pattern);		
			Matcher m = p.matcher(amount.trim());
			boolean b = m.matches();
			if(b){
				BigDecimal bigDecimal = new BigDecimal(amount);
				return bigDecimal.multiply(divisor).toString();
			}
		}
		return amount;
	}
	public static String getDataBaseAmountForTwoFloat(String amount){
		if(!"".equals(amount)&&amount!=null){
			Pattern p = Pattern.compile(pattern);		
			Matcher m = p.matcher(amount.trim());
			boolean b = m.matches();
			if(b){
				BigDecimal bigDecimal = new BigDecimal(amount);
				return Integer.toString(bigDecimal.multiply(divisor).intValue());
			}
		}
		return amount;
	}
	
	public static String addAmount(String amount,String addamount){
		if(!"".equals(amount)&&amount!=null&&!"".equals(addamount)&&addamount!=null){
			Pattern p = Pattern.compile(pattern);		
			Matcher m = p.matcher(amount.trim());
			boolean b = m.matches();
			m.reset();
			m = p.matcher(addamount.trim());
			boolean c =  m.matches();
			if(b&&c){
				BigDecimal amount_decimal = new BigDecimal(amount.trim());
				BigDecimal addamount_decimal = new BigDecimal(addamount.trim());
				return  amount_decimal.add(addamount_decimal).toString();
			}
		}
		return "0";
	}
	
	
	
/*	public static void main(String args[]) {
		logger.info(addAmount("123","123 "));
		logger.info(getDataBaseAmount(""));
	}*/
}
