package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test6 {

	public static void main(String[] args){
		String loadPath = "C:/Users/名字居然用了/Pictures/Saved Pictures/d7080baf4363a8294aa26591ef695c0a.jpg";
		String outPath = "D:/home/apache01/mchntFile/新建文件夹/123.jpg";
		try {
			File file1 = new File(loadPath);
			File file2 = new File(outPath);
			
//			File[] fileList = file1.listFiles();
			
			FileInputStream in = null;
			FileOutputStream out = null;
			
			in = new FileInputStream(file1);
			out = new FileOutputStream(file2);
	        byte[] buffer = new byte[100];// 缓存大小
	        int readNumber = 0;
	        while ((readNumber = in.read(buffer)) != -1) {
	            out.write(buffer, 0, readNumber);// 读取并输出buffer数组里面0~n个字节
	        }
		
//			for (File file : fileList) {
//				in = new FileInputStream(file);
//				out = new FileOutputStream(file2);
//		        byte[] buffer = new byte[100];// 缓存大小
//		        int readNumber = 0;
//		        while ((readNumber = in.read(buffer)) != -1) {
//		            out.write(buffer, 0, readNumber);// 读取并输出buffer数组里面0~n个字节
//		        }
//			}
	        
	        in.close();
	        out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
