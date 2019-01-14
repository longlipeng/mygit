package com.allinfinance.prepay.utils;

import java.io.UnsupportedEncodingException;

public class ConvertEncodeUtil {
	
	
	public static String gbk2utf8(String gbkStr) throws UnsupportedEncodingException{
		String utf8=new String("".getBytes(),"utf-8");
        for(int i=0;i<gbkStr.length();i++){
       	 String singleStr=gbkStr.substring(i, i+1);
       	 if(isChinese(singleStr)){
       		 byte [] fullByte = chineseGBK2UTF8(singleStr);
		         String fullStr = new String(fullByte, "UTF-8");
		         utf8+=fullStr;
       	 }else{
       		 utf8+=singleStr;
       	 }
        }
		return utf8;
		
	}
	
	
		 
		 public static byte[] chineseGBK2UTF8(String chenese){
		  char c[] = chenese.toCharArray();
		        byte [] fullByte =new byte[3*c.length];
		        for(int i=0; i<c.length; i++){
		         int m = (int)c[i];
		         String word = Integer.toBinaryString(m);
//		         System.out.println(word);
		         
		         StringBuffer sb = new StringBuffer();
		         int len = 16 - word.length();
		         //补零
		         for(int j=0; j<len; j++){
		          sb.append("0");
		         }
		         sb.append(word);
		         sb.insert(0, "1110");
		         sb.insert(8, "10");
		         sb.insert(16, "10");
		         
//		         System.out.println(sb.toString());
		         
		         String s1 = sb.substring(0, 8);          
		         String s2 = sb.substring(8, 16);          
		         String s3 = sb.substring(16);
		         
		         byte b0 = Integer.valueOf(s1, 2).byteValue();
		         byte b1 = Integer.valueOf(s2, 2).byteValue();
		         byte b2 = Integer.valueOf(s3, 2).byteValue();
		         byte[] bf = new byte[3];
		         bf[0] = b0;
		         fullByte[i*3] = bf[0];
		         bf[1] = b1;
		         fullByte[i*3+1] = bf[1];
		         bf[2] = b2;
		         fullByte[i*3+2] = bf[2];
		         
		        }
		        return fullByte;
		 }

		 
		// GENERAL_PUNCTUATION 判断中文的“号  
		    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
		    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号  
		    private static final boolean isChinese(char c) {  
		        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
		        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
		                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
		                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
		                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
		                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
		                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
		            return true;  
		        }  
		        return false;  
		    }  
		  
		    public static final boolean isChinese(String strName) {  
		        char[] ch = strName.toCharArray();  
		        for (int i = 0; i < ch.length; i++) {  
		            char c = ch[i];  
		            if (isChinese(c)) {  
		                return true;  
		            }  
		        }  
		        return false;  
		    }  

}
