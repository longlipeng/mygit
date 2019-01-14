package com.webservice.util;

import java.util.Map;
import java.util.Set;

public class ParseToXML {
	
	 public static String converterPayPalm(Map<String, String> dataMap)  
	    {  
	        
	            StringBuilder strBuilder = new StringBuilder();  
	            strBuilder.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");  
	            strBuilder.append("<Envelope>");
	            strBuilder.append("<Body>");
	            Set<String> objSet = dataMap.keySet();  
	            for (Object key : objSet)  
	            {  
	                if (key == null)  
	                {  
	                    continue;  
	                }  
	                 if( dataMap.get(key)==null){
	                	 strBuilder.append("<").append(key.toString()).append(">");  
	 	                strBuilder.append("</").append(key.toString()).append(">");  
	                 }else{
	                strBuilder.append("<").append(key.toString()).append(">");  
	                Object value = dataMap.get(key);  
	                strBuilder.append(value.toString());  
	                strBuilder.append("</").append(key.toString()).append(">");  
	                 }
	            }  
	            strBuilder.append("</Body>"); 
	            strBuilder.append("</Envelope>");
	             
	            return strBuilder.toString();  
	        }
	 
	 public static String converterPayPalm(Map<String, String> dataMap,String methed)  
	    {  
	        
	            StringBuilder strBuilder = new StringBuilder();  
	            strBuilder.append("<?xml version='1.0' encoding='UTF-8' ?>");  
	            strBuilder.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
	            strBuilder.append("<soap12:Body>");
	            strBuilder.append("<"+methed+" xmlns=\"http://tempuri.org/\">");
	            Set<String> objSet = dataMap.keySet();  
	            for (Object key : objSet)  
	            {  
	                if (key == null)  
	                {  
	                    continue;  
	                }  
	                 
	                strBuilder.append("<").append(key.toString()).append(">");  
	                Object value = dataMap.get(key);  
	                strBuilder.append(value.toString());  
	                strBuilder.append("</").append(key.toString()).append(">");  
	            }  
	            strBuilder.append("</"+methed+">"); 
	            strBuilder.append("</soap12:Body>");
	            strBuilder.append("</soap12:Envelope>");
	            return strBuilder.toString();  
	        }
	 
	 public static String converterXML(Map<String, String> dataMap)  
	    {  
	        
	            StringBuilder strBuilder = new StringBuilder();  
	            strBuilder.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");  
	            strBuilder.append("<DATA>");
	            Set<String> objSet = dataMap.keySet();  
	            for (Object key : objSet)  
	            {  
	                if (key == null)  
	                {  
	                    continue;  
	                }  
	                 if( dataMap.get(key)==null){
	                	 strBuilder.append("<").append(key.toString()).append(">");  
	 	                strBuilder.append("</").append(key.toString()).append(">");  
	                 }else{
	                strBuilder.append("<").append(key.toString()).append(">");  
	                Object value = dataMap.get(key);  
	                strBuilder.append(value.toString());  
	                strBuilder.append("</").append(key.toString()).append(">");  
	                 }
	            }   
	            strBuilder.append("</DATA>");
	             
	            return strBuilder.toString();  
	        }
	 
}
	
	
