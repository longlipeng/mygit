package com;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Test5 {

	public static void main(String[] args) throws Exception {
		
//		DecimalFormat f = new DecimalFormat("######0");   
//		
//		double d1 = 1358.28;
		
//		System.out.println(f.format(d1));
		
//		String money = yuanToFen("1358.28");
//		String money1 = fenToYuan("135828");
//		System.out.println(money);
//		System.out.println(money1);
		
		
		//解析excel文件方法
		File file = new File("C://Users//名字居然用了//Documents//WeChat Files//long1567549452//Files//2018年黑名单库//地区名单xls//FATF高风险地区和非合作司法管辖区.xls");
		
		FileInputStream fis = new FileInputStream(file);
		
		//第一种方式: 构造函数   new HSSFWorkbook(POIFSFileSystem ps)
//		POIFSFileSystem ps = new POIFSFileSystem(fis);
//		//得到文档对象
//		HSSFWorkbook hsw1 = new HSSFWorkbook(ps);
//		//得到第一个表单 
//		HSSFSheet sht1 = hsw1.getSheetAt(0);
//		//最后一行行标，比行数小1
//		int count1 = sht1.getLastRowNum();
//		
//		System.out.println(count1);
		
		
		//第二种方式: 构造函数  new HSSFWorkbook(FileInputStream fis)
		HSSFWorkbook hsw2 = new HSSFWorkbook(fis);
		//获取第一个表单 
		HSSFSheet sht2 = hsw2.getSheetAt(0);
		//最后一行行标，比行数小1
		int Max = sht2.getLastRowNum();
		
		System.out.println("总行数: " + Max);
		
		//获取第几行
		HSSFRow hf = sht2.getRow(0);
		//获取最后一个不为空的列是第几个   第一种方法
		int count = hf.getLastCellNum();
		
		//获取不为空的列个数     第二种方法
//		int count1 = hf.getPhysicalNumberOfCells(); 
		
		System.out.println("每行多少列: " + count);
//		System.out.println("每行多少列: " + count1);
		
		StringBuffer sb = new StringBuffer();
		
		String str = "";
		for (int i = 1; i <= Max; i++) {
			//获取第几行
			hf = sht2.getRow(i);
			//获取第几行有多少列
//			int countMax = hf.getLastCellNum();
			for (int j = 0; j < count; j++) {
				HSSFCell hc = hf.getCell(j);
				
//				str = hc.toString();
				
//				sb.append(hc.toString() + ",");
				System.out.print(hc.toString() + ",");
			}
			System.out.println();
		}
		
		
	}
	
	
	/**
	 * 
	 * 功能描述：金额字符串转换：单位   元转分
	  
	 * @param str 传入需要转换的金额字符串
	 * @return 转换后的金额字符串
	 */
	public static String yuanToFen(Object o) {
		if(o == null)
			return "0";
		String s = o.toString();
		int posIndex = -1;
		String str = "";
		StringBuilder sb = new StringBuilder();
		if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
			posIndex = s.indexOf(".");
			if(posIndex>0){
				int len = s.length();
			    if(len == posIndex+1){
					str = s.substring(0,posIndex);
					if(str == "0"){
				    	str = "";
				    }
				    sb.append(str).append("00");
				}else if(len == posIndex+2){
				    str = s.substring(0,posIndex);
				    if(str == "0"){
				    	str = "";
				    }
				    sb.append(str).append(s.substring(posIndex+1,posIndex+2)).append("0");
				}else if(len == posIndex+3){
					str = s.substring(0,posIndex);
					if(str == "0"){
				    	str = "";
				    }
					sb.append(str).append(s.substring(posIndex+1,posIndex+3));
				}else{
					str = s.substring(0,posIndex);
					if(str == "0"){
				    	str = "";
				    }
					sb.append(str).append(s.substring(posIndex+1,posIndex+3));
				}
			}else{
				sb.append(s).append("00");
			}
		}else{
			sb.append("0");
		}
		str = removeZero(sb.toString());
		if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
			return str;
		}else{
			return "0";
		}
	}
	
	
	/**
	 * 
	 * 功能描述：金额字符串转换：单位    分转元
	  
	 * @param str 传入需要转换的金额字符串
	 * @return 转换后的金额字符串
	 */	
	public static String fenToYuan(Object o) {
		if(o == null)
			return "0.00";
		String s = o.toString();
		int len = -1;	
		StringBuilder sb = new StringBuilder();
		if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
			s = removeZero(s);
			if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
				len = s.length();
				int tmp = s.indexOf("-");
				if(tmp>=0){
					if(len==2){
						sb.append("-0.0").append(s.substring(1));
					}else if(len==3){
						sb.append("-0.").append(s.substring(1));
					}else{
						sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));				
					}						
				}else{
					if(len==1){
						sb.append("0.0").append(s);
					}else if(len==2){
						sb.append("0.").append(s);
					}else{
						sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));				
					}					
				}
			}else{
				sb.append("0.00");
			}
		}else{
			sb.append("0.00");
		}
		return sb.toString();		
	}
	
	
	
	/**
	 * 
	 * 功能描述：去除字符串首部为"0"字符
	  
	 * @param str 传入需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String removeZero(String str){   
	   	char  ch;  
	   	String result = "";
	   	if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){				
	   		try{			
				for(int i=0;i<str.length();i++){
					ch = str.charAt(i);
					if(ch != '0'){						
						result = str.substring(i);
						break;
					}
				}
			}catch(Exception e){
				result = "";
			}	
		}else{
			result = "";
		}
	   	return result;
			
	}
	
	
	
}
