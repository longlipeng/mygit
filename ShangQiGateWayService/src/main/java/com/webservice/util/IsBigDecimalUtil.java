package com.webservice.util;

public class IsBigDecimalUtil {
	
	public static boolean isBigDecimal(String value) 
	{
	      double n=Double.parseDouble(value);
	      int temp;
	      double i;
	      temp=(int)n;
	      i=n-temp;
	     if(i==0)
	     {
	           return false;
	    }
	     else
	          return true;
	}

}
