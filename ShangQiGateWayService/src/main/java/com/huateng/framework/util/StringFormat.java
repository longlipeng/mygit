package com.huateng.framework.util;

import java.nio.charset.Charset;
/***
 * 中文不足补齐
 * @author dawn
 *
 */
public class StringFormat {
	/**
	 * 当原字符串大于SIZE时
	 * 自动借取字符串
	 * 若借取后不足位数
	 * 自动补空格
	 * @param srcStr 原字符串
	 * @param size	需要补足位
	 * @return
	 */
	public static String getFormat(String srcStr,Integer size){
		if(srcStr==null||"".equals(srcStr)){
			int i=0;
			while(i<size){
				srcStr+=" ";
				i++;
			}
			return srcStr;
		}
		Charset charset = Charset.forName("GB18030");
		if(srcStr.getBytes(charset).length==size){
			return srcStr;
		}else if(srcStr.getBytes(charset).length>size){
			srcStr= srcStr.substring(0, size/2);
		}
		while(srcStr.getBytes(charset).length<size){
			srcStr+=" ";
		}
		return srcStr;
	}
	


/*	public static void main(String args[]){
		*//**
		 * FOR TEST	
		 *//*
		Charset charset = Charset.forName("GB18030");
		logger.info(getFormat("测试A   ",1).getBytes(charset).length);
		
		logger.info(getFormat("测试",4));	
		
		logger.info(getFormat("测试  ",4));
		
		logger.info();
		
	}*/
}
