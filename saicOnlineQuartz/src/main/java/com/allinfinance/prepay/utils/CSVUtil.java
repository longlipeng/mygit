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
 * ���� CSV �ļ�
 * @param filePath  �ļ�·��
 * @param fileName  �ļ�����
 * @param csvHeaders  �ļ�ͷ 
 * @param csvContent  �ʼ�����
 */
	public static void createCSV(String filePath,String fileName,String[]csvHeaders, List<String[]> csvContent){
		File fileDir = new File(filePath);  
	    if (!fileDir.exists()) { //��������� �򴴽�   
	        fileDir.mkdirs();        
	    }  
	    String csvFilePath = filePath+fileName;
	    try {
	        // ����CSVд���� ����:CsvWriter(�ļ�·�����ָ����������ʽ);
	        CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
	        // д��ͷ (���Ը�����Ҫ��д)
	        csvWriter.writeRecord(csvHeaders);
	        // д����
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
