package com;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.huateng.system.util.CommonFunction;

public class Test {
	public static void main(String[] args) throws Exception {
		Date a,b;
		a = new Date();
		b = new Date();
		System.out.println( b.getTime() - a.getTime());
		
		String time = "20171229165700";
		StringBuilder  sb = new StringBuilder (time);  
		sb.insert(4, "-");  
		sb.insert(7, "-");  
		sb.insert(10, " ");  
		sb.insert(13, ":"); 
		sb.insert(16, ":"); 
		
		String time2 = "20171229165900";
		StringBuilder  sb2 = new StringBuilder (time2);  
		sb2.insert(4, "-");  
		sb2.insert(7, "-");  
		sb2.insert(10, " ");  
		sb2.insert(13, ":"); 
		sb2.insert(16, ":"); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(sb.toString());
		Date date2 = sdf.parse(sb2.toString());
		
		System.out.println(date.getTime());
		System.out.println(date2.getTime());
		
		System.out.println(date2.getTime()-date.getTime());
		System.out.println((date2.getTime()-date.getTime())/(1000));
	}
}
