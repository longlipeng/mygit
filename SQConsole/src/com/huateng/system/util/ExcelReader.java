package com.huateng.system.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**    
 * 操作Excel表格的功能类    
 * @author：    
 * @version 1.0    
 */     
public class ExcelReader {      
    private POIFSFileSystem fs;      
    private HSSFWorkbook wb;
    
//    private XSSFWorkbook swb;
    
    private HSSFSheet sheet;      
    private HSSFRow row;      
    /**    
     * 读取Excel表格表头的内容    
     * @param InputStream    
     * @return String 表头内容的数组    
     * @throws BizServiceException 
     *     
     */     
    public String[] readExcelTitle(InputStream is) throws Exception {      
        try {      
            fs = new POIFSFileSystem(is);      
            wb = new HSSFWorkbook(fs);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        row = sheet.getRow(0);      
        //标题总列数      
        int colNum = row.getPhysicalNumberOfCells();      
        String[] title = new String[colNum];      
        for (int i=0; i<colNum; i++) {      
            title[i] = getStringCellValue(row.getCell((short) i));      
        }      
        return title;      
    }      
          
    /**    
     * 读取Excel数据内容    
     * @param InputStream    
     * @return Map 包含单元格数据内容的Map对象    
     * @throws BizServiceException 
     */     
    public Map<Integer,String> readExcelContent(InputStream is) throws Exception {      
        Map<Integer,String> content = new HashMap<Integer,String>();      
        String str = "";      
        try {      
            fs = new POIFSFileSystem(is);      
            wb = new HSSFWorkbook(fs);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        //得到总行数      
        int rowNum = sheet.getLastRowNum();      
        row = sheet.getRow(0);      
        int colNum = row.getPhysicalNumberOfCells();      
        //正文内容应该从第二行开始,第一行为表头的标题      
        for (int i = 1; i <= rowNum; i++) {      
            row = sheet.getRow(i);      
            int j = 0;      
            while (j<colNum) {      
        //每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据      
        //也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean      
                str += getStringCellValue(row.getCell((short) j)).trim();      
                j ++;      
            }      
            content.put(i, str);      
            str = "";      
        }      
        return content;      
    }      
          
    /**    
     * 获取单元格数据内容为字符串类型的数据    
     * @param cell Excel单元格    
     * @return String 单元格数据内容    
     */     
    private String getStringCellValue(HSSFCell cell) throws Exception{     
    	if (cell == null) {      
            return "";      
        }
        String strCell = "";      
        switch (cell.getCellType()) {      
        case HSSFCell.CELL_TYPE_STRING:      
            strCell = cell.getStringCellValue();      
            break;      
        case HSSFCell.CELL_TYPE_NUMERIC:
//        	DecimalFormat df = new DecimalFormat("0.00");
//        	strCell = df.format(cell.getNumericCellValue());
            strCell = String.valueOf(cell.getNumericCellValue());
            break;  
//        	throw new Exception("excel 中格式有误，请设置单元格为文本格式！");
        case HSSFCell.CELL_TYPE_BOOLEAN:      
            strCell = String.valueOf(cell.getBooleanCellValue());      
            break;    
//        	throw new Exception("excel 中格式有误，请设置单元格为文本格式！");
        case HSSFCell.CELL_TYPE_BLANK:      
            strCell = "";      
            break;   
//        	throw new BizServiceException("excel 中格式有误，请设置单元格为文本格式！");
        default:      
            strCell = "";      
            break;   
//        	throw new Exception("excel 中格式有误，请设置单元格为文本格式！");
        	
        }      
        if (strCell.equals("") || strCell == null) {      
            return "";      
        }
        return strCell;      
    }      
          
    /**    
     * 获取单元格数据内容为日期类型的数据    
     * @param cell Excel单元格    
     * @return String 单元格数据内容    
     */     
    private String getDateCellValue(HSSFCell cell) {      
        String result = "";      
        try {      
            int cellType = cell.getCellType();      
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {      
                Date date = cell.getDateCellValue();      
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)       
                + "-" + date.getDate();      
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {      
                String date = getStringCellValue(cell);      
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();      
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {      
                result = "";      
            }      
        } catch (Exception e) {      
            System.out.println("日期格式不正确!");      
            e.printStackTrace();      
        }      
        return result;      
    }      
        
    
    /**    
     * 读取Excel数据内容    
     * @param InputStream    
     * @return Map 包含单元格数据内容的Map对象    
     */     
    public Map<Integer,String> readExcelContent2(InputStream is) throws Exception {      
        Map<Integer,String> content = new HashMap<Integer,String>();      
        String str = "";      
        try {      
            fs = new POIFSFileSystem(is);      
            wb = new HSSFWorkbook(fs);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        //得到总行数      
        int rowNum = sheet.getLastRowNum();      
        row = sheet.getRow(0);      
        int colNum = row.getPhysicalNumberOfCells();      
        //正文内容应该从第二行开始,第一行为表头的标题      
        for (int i = 1; i <= rowNum; i++) {      
            row = sheet.getRow(i);      
            int j = 0;      
            while (j<colNum) {      
        //每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据      
        //也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean      
               if(j==colNum-1) {
            	   str += getStringCellValue(row.getCell((short) j)).trim(); 
               }else{
            	   if (j == 5) {
            		   String idType = getStringCellValue(row.getCell((short) 4)).trim();
            		   if (idType.equals("1")) {
            			   String idNo = getStringCellValue(row.getCell((short) j)).trim();
            			   str += idNo.toUpperCase()+"--";
            		   }else{
            			   str += getStringCellValue(row.getCell((short) j)).trim()+"--"; 
            		   }
            	   }else{
            	   str += getStringCellValue(row.getCell((short) j)).trim()+"--"; 
            	   }
               }     
                j ++;      
            }
            if(str.split("--").length!=0){
            	content.put(i, str);
            }
            str = "";      
        }      
        return content;      
    }      
    
//    public static void main(String[] args) {      
//        try {      
//            //对读取Excel表格标题测试      
//            InputStream is = new FileInputStream("C://Users//Li//Desktop//线下持卡人和客户信息录入(1).xls");      
//            ExcelReader excelReader = new ExcelReader();      
//            String[] title = null;
//			try {
//				title = excelReader.readExcelTitle(is);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//           
//            System.out.println("获得Excel表格的标题:");      
//            for (String s : title) {      
//                System.out.print(s + " ");      
//            }      
//            
//            System.out.println();     
//            //对读取Excel表格内容测试      
//            InputStream is2 = new FileInputStream("C://Users//Li//Desktop//线下持卡人和客户信息录入(1).xls");      
//            Map<Integer, String> map = null;
//			try {
//				map = excelReader.readExcelContent2(is2);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}      
//            System.out.println("获得Excel表格的内容:");      
//            for (int i=1; i<=map.size(); i++) {      
//                System.out.println(map.get(i));      
//            }      
//        } catch (FileNotFoundException e) {      
//            System.out.println("未找到指定路径的文件!");      
//            e.printStackTrace();      
//        }      
//    }      
    
    
}   