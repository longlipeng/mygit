package com.allinfinance.prepay.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import com.csvreader.CsvWriter;

public class CSVUtil {
/**	
 * 创建 CSV 文件
 * @param filePath  文件路径
 * @param fileName  文件名字
 * @param csvHeaders  文件头 
 * @param csvContent  问价内容
 */
	public static void createCSV(String filePath,String fileName,String[]csvHeaders, List<String[]> csvContent){
		File fileDir = new File(filePath);  
	    if (!fileDir.exists()) { //如果不存在 则创建   
	        fileDir.mkdirs();        
	    }  
	    String csvFilePath = filePath+fileName;
	    try {
	        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
	        CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
	        // 写表头 (可以根据需要填写)
	        csvWriter.writeRecord(csvHeaders);
	        // 写内容
	        if(csvContent!=null&&csvContent.size()==0){
	        	OutputStream out=null;
	        	fileName=filePath+fileName;
	    		try{
	    		out=new FileOutputStream(fileName);
	    		byte[] words="".getBytes();
	    		out.write(words);
	    		}catch (IOException e){
	    		throw e;
	    		}finally{
	    			if(out!=null){
	    				out.close();
	    			}
	    		}
	        }else{
	        for (String[] strings : csvContent) {
	        	 csvWriter.writeRecord(strings);
			}
	        }
	        csvWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
