package com.allinfinance.prepay.utils;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 文件处理类
 * @author taojiajun
 *
 */
public class FileUtil {
	private static Logger log=LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 写文件
	 * @param file
	 * @param str
	 * @throws IOException
	 */
	
	public static void createFile(String filePath,String file,String str) throws IOException  {
		File fileDir = new File(filePath);  
	     if (!fileDir.exists()) { //如果不存在 则创建   
	         fileDir.mkdirs();  
	         log.info("创建文件夹成功:{}",filePath);
	     }  
		OutputStream out=null;
		BufferedWriter writer =null;
		file=filePath+file;
		try{
		out=new FileOutputStream(file);
		writer= new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
		writer.write(str);
		writer.flush();
		log.info("创建文件成功:{}",file);
		}catch (IOException e){
		throw e;
		}finally{
			if(out!=null){
				out.close();
			}
			if(writer!=null){
				writer.close();
			}
		}
	}
	/**
	 * 追加文件内容
	 * @param file
	 * @param content
	 * @throws IOException 
	 */
	public static void appendFile(File file,String content) throws IOException {
		  BufferedWriter out = null; 
		  OutputStreamWriter outputStreamWriter =null;
		  FileOutputStream fileOutputStream=null;
	        try { 
	        	fileOutputStream=new FileOutputStream(file, true);
	        	outputStreamWriter=new OutputStreamWriter(                        
	        			fileOutputStream ,"UTF-8");
	             out = new BufferedWriter(outputStreamWriter);                              
	             out.write(content);  
	             out.flush();
	         } catch (Exception e) {                                                     
	             e.printStackTrace();                                                    
	         } finally {                                                                 
	            try {
	            	if(fileOutputStream!=null){
	            		fileOutputStream.close(); 
		            	}
	            	if(outputStreamWriter!=null){
	            		outputStreamWriter.close(); 
		            	}
	            	if(out!=null){
	                 out.close(); 
	            	}
	             } catch (IOException e) {                                               
	                 e.printStackTrace();                                                
	             }                                                                       
	         }           
		
	}
}
