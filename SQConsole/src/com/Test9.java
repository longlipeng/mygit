package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 操作excel表格
 * @author 
 *
 */
public class Test9 {

	public static void main(String[] args) throws Exception{
		
		File file = new File("D:\\test1.xls");
		
		//Workbook只能操作xls文件,不能操作xlsx文件
		Workbook wb = null;
		InputStream is = new FileInputStream(file);
		
//		System.out.println(file.getAbsoluteFile());
//		System.out.println(file.getAbsolutePath());
//		System.out.println(file);
		
		wb = Workbook.getWorkbook(is);
		Sheet set = wb.getSheet(0);
		//获取总行数
		int rsRows = set.getRows();
		//获取总列数
		int csColumn = set.getColumns();
		
		System.out.println(rsRows);
		System.out.println(csColumn);
		
		for (int i = 1; i < rsRows; i++) {
			Cell cell = set.getCell(1, i);
			System.out.println(cell.getContents());
			
		}
		
	}
	
	
	
}
