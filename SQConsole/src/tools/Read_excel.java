package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class Read_excel {
	private static Logger log = Logger.getLogger(Read_excel.class);
	public static void main(String[] args) {
		
		/*{
			String data = "123456";
			String filestr1 = "F:\\桌面\\备付金报表\\1支付机构客户备付金信息统计报表_201702_银行名称2.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_101_cmb100_";
			String fixed = "123456";
			String date = "20170218";
			readExcel_1_1(data, filestr1, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20170202|";
			String filestr2 = "C:\\Users\\Li\\Desktop\\备付金报表\\2支付机构客户备付金出金业务明细表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_102_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_2(data, filestr2, path, name, date, fixed);
		}*/
		{
			//由银行提供支付机构代码  + 交易日期 
			String data = "123456|20170417|";
//			String filestr3 = "C:\\Users\\Li\\Desktop\\123\\1支付机构客户备付金信息统计报表_201704_招商银行南昌站前西路支行.xls";
			String filestr3 = "C:\\Users\\Li\\Desktop\\123\\1支付机构客户备付金信息统计报表_201704_招商银行上海静安寺支行.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_103_cmb100_";
			String fixed = "123456";
			String date = "20170417";
			readExcel_1_3(data, filestr3, path, name, date, fixed);
		}
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20170202|";
			String filestr4 = "C:\\Users\\Li\\Desktop\\备付金报表\\4支付机构客户资金账户转账业务统计表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_104_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_4(data, filestr4, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20170202|";
			String filestr5 = "C:\\Users\\Li\\Desktop\\备付金报表\\5支付机构客户资金账户余额统计表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_105_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_5(data, filestr5, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
			String data = "123456|";
			String filestr6 = "C:\\Users\\Li\\Desktop\\备付金报表\\6支付机构招商银行特殊业务明细表_201702_银行名称2.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_106_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_6(data, filestr6, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码 + 交易日期
			String data = "123456|";
			String filestr7 = "C:\\Users\\Li\\Desktop\\备付金报表\\7支付机构现金购卡业务统计表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_107_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_7(data, filestr7, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码 + 交易日期
			String data = "123456|";
			String filestr8 = "C:\\Users\\Li\\Desktop\\备付金报表\\8支付机构预付卡现金赎回业务统计表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_108_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_8(data, filestr8, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号  + 交易日期
			String data = "123456|";
			String filestr1 = "C:\\Users\\Li\\Desktop\\备付金报表\\9支付机构招商银行客户备付金业务未达账项统计表_201702_银行名称2.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_109_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_9(data,filestr1, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
			String data = "123456|";
			String filestr10 = "C:\\Users\\Li\\Desktop\\123\\10支付机构招商银行客户备付金业务未达账项分析表_201702_银行名称2.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_110_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_10(data, filestr10, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr11 = "C:\\Users\\Li\\Desktop\\备付金报表\\11支付机构客户资金账户余额变动调节表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_111_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_11(data, filestr11, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\备付金报表\\12支付机构客户资金账户余额试算表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_112_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_12(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\备付金报表\\13预付卡发行企业备付金账户中售卡押金统计表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_113_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_13(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\备付金报表\\15银行客户备付金入金业务调节表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_114_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_14(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\备付金报表\\16备付金新增分银行账户的表1-2_201702_银行名称2.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_115_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_15(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\备付金报表\\201702\\17备付金新增支付机构管理账户情况统计_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_116_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_16(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\18备付金新增预付卡商户白名单表_20170418.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_201_cmb100_";
			String fixed = "123456";
			String date = "20170418";
			readExcel_1_17(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|";
			String filestr12 = "C:\\Users\\Li\\Desktop\\备付金报表\\201702\\19支付机构合作行每日账户余额列表_201702.xls";
			String path = "C:\\Users\\Li\\Desktop\\备付金报表TXT\\";
			String name = "PMS_005_cmb100_";
			String fixed = "123456";
			String date = "20170202";
			readExcel_1_18(data, filestr12, path, name, date, fixed);
		}*/
	}

	/**
	 * 表1-1
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_1(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=9;
			String bank_name = sheet.getCell(2, 3).getContents();
			String account_name = sheet.getCell(2, 4).getContents();
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000", cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000", cell1Str14 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				cell10 = sheet.getCell(10, i);
				cell11 = sheet.getCell(11, i);
				cell12 = sheet.getCell(12, i);
				cell13 = sheet.getCell(13, i);
				cell14 = sheet.getCell(14, i);
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				if (cell7.getType() == CellType.LABEL) {
					LabelCell cell1_7 = (LabelCell)cell7;
					cell1Str7 = cell1_7.getString();
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_7 = (NumberCell)cell7;
					cell1Str7 = String.valueOf(cell1_7.getValue());
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}
				if(cell1Str7.trim().equals("")){
					cell1Str7 = "0.000000";
				}
				
				if (cell8.getType() == CellType.LABEL) {
					LabelCell cell1_8 = (LabelCell)cell8;
					cell1Str8 = cell1_8.getString();
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_8 = (NumberCell)cell8;
					cell1Str8 = String.valueOf(cell1_8.getValue());
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}
				if(cell1Str8.trim().equals("")){
					cell1Str8 = "0.000000";
				}
				
				if (cell9.getType() == CellType.LABEL) {
					LabelCell cell1_9 = (LabelCell)cell9;
					cell1Str9 = cell1_9.getString();
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_9 = (NumberCell)cell9;
					cell1Str9 = String.valueOf(cell1_9.getValue());
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}
				if(cell1Str9.trim().equals("")){
					cell1Str9 = "0.000000";
				}
				
				if (cell10.getType() == CellType.LABEL) {
					LabelCell cell1_10 = (LabelCell)cell10;
					cell1Str10 = cell1_10.getString();
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_10 = (NumberCell)cell10;
					cell1Str10 = String.valueOf(cell1_10.getValue());
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}
				if(cell1Str10.trim().equals("")){
					cell1Str10 = "0.000000";
				}
				
				if (cell11.getType() == CellType.LABEL) {
					LabelCell cell1_11 = (LabelCell)cell11;
					cell1Str11 = cell1_11.getString();
					if(!cell1Str11.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str11 = String.valueOf(setScale);	
					} else{
						cell1Str11 = "0.000000";
					}
				}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_11 = (NumberCell)cell11;
					cell1Str11 = String.valueOf(cell1_11.getValue());
					if(!cell1Str11.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str11 = String.valueOf(setScale);	
					} else{
						cell1Str11 = "0.000000";
					}
				}
				if(cell1Str11.trim().equals("")){
					cell1Str11 = "0.000000";
				}
				
				if (cell12.getType() == CellType.LABEL) {
					LabelCell cell1_12 = (LabelCell)cell12;
					cell1Str12 = cell1_12.getString();
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_12 = (NumberCell)cell12;
					cell1Str12 = String.valueOf(cell1_12.getValue());
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}
				if(cell1Str12.trim().equals("")){
					cell1Str12 = "0.000000";
				}
				
				if (cell13.getType() == CellType.LABEL) {
					LabelCell cell1_13 = (LabelCell)cell13;
					cell1Str13 = cell1_13.getString();
					if(!cell1Str13.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str13 = String.valueOf(setScale);	
					} else{
						cell1Str13 = "0.000000";
					}
				}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_13 = (NumberCell)cell13;
					cell1Str13 = String.valueOf(cell1_13.getValue());
					if(!cell1Str13.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str13 = String.valueOf(setScale);	
					} else{
						cell1Str13 = "0.000000";
					}
				}
				if(cell1Str13.trim().equals("")){
					cell1Str13 = "0.000000";
				}
				
				if (cell14.getType() == CellType.LABEL) {
					LabelCell cell1_14 = (LabelCell)cell14;
					cell1Str14 = cell1_14.getString();
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_14 = (NumberCell)cell14;
					cell1Str14 = String.valueOf(cell1_14.getValue());
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}
				if(cell1Str14.trim().equals("")){
					cell1Str14 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|0.000000|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|0.000000|" + cell1Str6 + "|" + cell1Str7 + "|" + cell1Str8 + "|0.000000|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|0.000000|" + cell1Str14;
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append("|" + bank_name + "|" + account_name + "|");
					sb.append(date + "|");
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("报表一出错：" + e.getMessage());
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-2
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_2(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9 ;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=7;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				if (cell7.getType() == CellType.LABEL) {
					LabelCell cell1_7 = (LabelCell)cell7;
					cell1Str7 = cell1_7.getString();
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_7 = (NumberCell)cell7;
					cell1Str7 = String.valueOf(cell1_7.getValue());
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}
				if(cell1Str7.trim().equals("")){
					cell1Str7 = "0.000000";
				}
				
				if (cell8.getType() == CellType.LABEL) {
					LabelCell cell1_8 = (LabelCell)cell8;
					cell1Str8 = cell1_8.getString();
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_8 = (NumberCell)cell8;
					cell1Str8 = String.valueOf(cell1_8.getValue());
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}
				if(cell1Str8.trim().equals("")){
					cell1Str8 = "0.000000";
				}
				
				if (cell9.getType() == CellType.LABEL) {
					LabelCell cell1_9 = (LabelCell)cell9;
					cell1Str9 = cell1_9.getString();
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_9 = (NumberCell)cell9;
					cell1Str9 = String.valueOf(cell1_9.getValue());
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}
				if(cell1Str9.trim().equals("")){
					cell1Str9 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" + cell1Str8 + "|" + cell1Str9;
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表二出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-3
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_3(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i = 0;
		int j = 0;
		String conData = "", conData1 = "";
		Cell cell1 ;
		String cell1Str1 = "0.000000";
		try {
			book = Workbook.getWorkbook(new File(readFile));
			sheet = book.getSheet(0);
			
			while(true) {
				String contents = sheet.getCell(0, i).getContents();
				if ("合计".equals(contents.trim())) {
					j = i -4;
					break;
				}
				i++;
			}
			int h = 1;
			for (int l = 4; l <= i; l++) {
					conData = sheet.getCell(0, l).getContents();
					if ("合计".equals(conData.trim())) {
						break;
					}
					if (l == 3+h*3) {
						conData1 = conData1 +  data + sheet.getCell(0, l-2).getContents() + "|" + sheet.getCell(0, l-1).getContents() + "|" + sheet.getCell(0, l).getContents() +"|";
						cell1 = sheet.getCell(riInt, l-2);
						if (cell1.getType() == CellType.LABEL) {
							LabelCell cell1_1 = (LabelCell)cell1;
							cell1Str1 = cell1_1.getString();
							if(!cell1Str1.trim().equals("")){
								BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
								cell1Str1 = String.valueOf(setScale);	
							} else{
								cell1Str1 = "0.000000";
							}
						}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
							NumberCell cell1_1 = (NumberCell)cell1;
							cell1Str1 = String.valueOf(cell1_1.getValue());
							if(!cell1Str1.trim().equals("")){
								BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
								cell1Str1 = String.valueOf(setScale);	
							} else{
								cell1Str1 = "0.000000";
							}
						}
						if(cell1Str1.trim().equals("")){
							cell1Str1 = "0.000000";
						}
						conData1 = conData1 + cell1Str1 + "\r\n";
//						System.out.println(conData1);
						h++;
					}
			}
			String pathName = path + name + fixed + "_" + date + ".txt";
			writeTxtFile(conData1, new File(pathName));
			return true;
		} catch (Exception e) {
			log.error("报表三出错：" + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 表1-4
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_4(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|0.000000" ;
				if ((riInt + "日").equals(contents)) {
					sb.append(data);
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表四出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-5
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_5(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=5;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 ;
				if ((riInt + "日").equals(contents)) {
					sb.append(data);
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表五出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-6
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_6(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28, cell29, cell30;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			String bank_name = sheet.getCell(1, 2).getContents();
			String account_name = sheet.getCell(1, 3).getContents();
//			System.out.println(bank_name + "=====" + account_name);
			i=1;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000" , cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000",cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000", cell1Str18 = "0.000000", cell1Str19 = "0.000000", cell1Str20 = "0.000000", cell1Str21 = "0.000000", cell1Str22 = "0.000000", cell1Str23 = "0.000000", cell1Str24 = "0.000000", cell1Str25 = "0.000000", cell1Str26 = "0.000000", cell1Str27 = "0.000000", cell1Str28 = "0.000000", cell1Str29 = "0.000000", cell1Str30 = "0.000000";
				String contents = sheet.getCell(i, 4).getContents();
				if (contents.equals(riInt + "日")) {
					sb.append(data);
					sb.append(bank_name + "|");
					sb.append(account_name + "|");
					sb.append(date + "|");
					cell1 = sheet.getCell(i, 5);
					cell2 = sheet.getCell(i, 6);
					cell3 = sheet.getCell(i, 7);
					cell4 = sheet.getCell(i, 8);
					cell5 = sheet.getCell(i, 9);
					cell6 = sheet.getCell(i, 10);
					cell7 = sheet.getCell(i, 11);
					cell8 = sheet.getCell(i, 12);
					cell9 = sheet.getCell(i, 13);
					cell10 = sheet.getCell(i, 14);
					cell11 = sheet.getCell(i, 15);
					cell12 = sheet.getCell(i, 16);
					cell13 = sheet.getCell(i, 17);
					cell14 = sheet.getCell(i, 18);
					cell15 = sheet.getCell(i, 19);
					cell16 = sheet.getCell(i, 20);
					cell17 = sheet.getCell(i, 21);
					cell18 = sheet.getCell(i, 22);
					cell19 = sheet.getCell(i, 23);
					cell20 = sheet.getCell(i, 24);
					cell21 = sheet.getCell(i, 25);
					cell22 = sheet.getCell(i, 26);
					cell23 = sheet.getCell(i, 27);
					cell24 = sheet.getCell(i, 28);
					cell25 = sheet.getCell(i, 29);
					cell26 = sheet.getCell(i, 30);
					cell27 = sheet.getCell(i, 31);
					cell28 = sheet.getCell(i, 32);
//					cell29 = sheet.getCell(i, 33);
//					cell30 = sheet.getCell(i, 34);
					if (cell1.getType() == CellType.LABEL) {
						LabelCell cell1_1 = (LabelCell)cell1;
						cell1Str1 = cell1_1.getString();
						if(!cell1Str1.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str1 = String.valueOf(setScale);	
						} else{
							cell1Str1 = "0.000000";
						}
					}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_1 = (NumberCell)cell1;
						cell1Str1 = String.valueOf(cell1_1.getValue());
						if(!cell1Str1.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str1 = String.valueOf(setScale);	
						} else{
							cell1Str1 = "0.000000";
						}
					}
					if(cell1Str1.trim().equals("")){
						cell1Str1 = "0.000000";
					}
					
					if (cell2.getType() == CellType.LABEL) {
						LabelCell cell1_2 = (LabelCell)cell2;
						cell1Str2 = cell1_2.getString();
						if(!cell1Str2.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str2 = String.valueOf(setScale);	
						} else{
							cell1Str2 = "0.000000";
						}
					}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_2 = (NumberCell)cell2;
						cell1Str2 = String.valueOf(cell1_2.getValue());
						if(!cell1Str2.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str2 = String.valueOf(setScale);	
						} else{
							cell1Str2 = "0.000000";
						}
					}
					if(cell1Str2.trim().equals("")){
						cell1Str2 = "0.000000";
					}
					
					if (cell3.getType() == CellType.LABEL) {
						LabelCell cell1_3 = (LabelCell)cell3;
						cell1Str3 = cell1_3.getString();
						if(!cell1Str3.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str3 = String.valueOf(setScale);	
						} else{
							cell1Str3 = "0.000000";
						}
					}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_3 = (NumberCell)cell3;
						cell1Str3 = String.valueOf(cell1_3.getValue());
						if(!cell1Str3.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str3 = String.valueOf(setScale);	
						} else{
							cell1Str3 = "0.000000";
						}
					}
					if(cell1Str3.trim().equals("")){
						cell1Str3 = "0.000000";
					}
					
					if (cell4.getType() == CellType.LABEL) {
						LabelCell cell1_4 = (LabelCell)cell4;
						cell1Str4 = cell1_4.getString();
						if(!cell1Str4.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str4 = String.valueOf(setScale);	
						} else{
							cell1Str4 = "0.000000";
						}
					}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_4 = (NumberCell)cell4;
						cell1Str4 = String.valueOf(cell1_4.getValue());
						if(!cell1Str4.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str4 = String.valueOf(setScale);	
						} else{
							cell1Str4 = "0.000000";
						}
					}
					if(cell1Str4.trim().equals("")){
						cell1Str4 = "0.000000";
					}
					
					if (cell5.getType() == CellType.LABEL) {
						LabelCell cell1_5 = (LabelCell)cell5;
						cell1Str5 = cell1_5.getString();
						if(!cell1Str5.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str5 = String.valueOf(setScale);	
						} else{
							cell1Str5 = "0.000000";
						}
					}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_5 = (NumberCell)cell5;
						cell1Str5 = String.valueOf(cell1_5.getValue());
						if(!cell1Str5.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str5 = String.valueOf(setScale);	
						} else{
							cell1Str5 = "0.000000";
						}
					}
					if(cell1Str5.trim().equals("")){
						cell1Str5 = "0.000000";
					}
					
					if (cell6.getType() == CellType.LABEL) {
						LabelCell cell1_6 = (LabelCell)cell6;
						cell1Str6 = cell1_6.getString();
						if(!cell1Str6.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str6 = String.valueOf(setScale);	
						} else{
							cell1Str6 = "0.000000";
						}
					}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_6 = (NumberCell)cell6;
						cell1Str6 = String.valueOf(cell1_6.getValue());
						if(!cell1Str6.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str6 = String.valueOf(setScale);	
						} else{
							cell1Str6 = "0.000000";
						}
					}
					if(cell1Str6.trim().equals("")){
						cell1Str6 = "0.000000";
					}
					
					if (cell7.getType() == CellType.LABEL) {
						LabelCell cell1_7 = (LabelCell)cell7;
						cell1Str7 = cell1_7.getString();
						if(!cell1Str7.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str7 = String.valueOf(setScale);	
						} else{
							cell1Str7 = "0.000000";
						}
					}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_7 = (NumberCell)cell7;
						cell1Str7 = String.valueOf(cell1_7.getValue());
						if(!cell1Str7.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str7 = String.valueOf(setScale);	
						} else{
							cell1Str7 = "0.000000";
						}
					}
					if(cell1Str7.trim().equals("")){
						cell1Str7 = "0.000000";
					}
					
					if (cell8.getType() == CellType.LABEL) {
						LabelCell cell1_8 = (LabelCell)cell8;
						cell1Str8 = cell1_8.getString();
						if(!cell1Str8.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str8 = String.valueOf(setScale);	
						} else{
							cell1Str8 = "0.000000";
						}
					}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_8 = (NumberCell)cell8;
						cell1Str8 = String.valueOf(cell1_8.getValue());
						if(!cell1Str8.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str8 = String.valueOf(setScale);	
						} else{
							cell1Str8 = "0.000000";
						}
					}
					if(cell1Str8.trim().equals("")){
						cell1Str8 = "0.000000";
					}
					
					if (cell9.getType() == CellType.LABEL) {
						LabelCell cell1_9 = (LabelCell)cell9;
						cell1Str9 = cell1_9.getString();
						if(!cell1Str9.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str9 = String.valueOf(setScale);	
						} else{
							cell1Str9 = "0.000000";
						}
					}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_9 = (NumberCell)cell9;
						cell1Str9 = String.valueOf(cell1_9.getValue());
						if(!cell1Str9.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str9 = String.valueOf(setScale);	
						} else{
							cell1Str9 = "0.000000";
						}
					}
					if(cell1Str9.trim().equals("")){
						cell1Str9 = "0.000000";
					}
					
					if (cell10.getType() == CellType.LABEL) {
						LabelCell cell1_10 = (LabelCell)cell10;
						cell1Str10 = cell1_10.getString();
						if(!cell1Str10.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str10 = String.valueOf(setScale);	
						} else{
							cell1Str10 = "0.000000";
						}
					}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_10 = (NumberCell)cell10;
						cell1Str10 = String.valueOf(cell1_10.getValue());
						if(!cell1Str10.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str10 = String.valueOf(setScale);	
						} else{
							cell1Str10 = "0.000000";
						}
					}
					if(cell1Str10.trim().equals("")){
						cell1Str10 = "0.000000";
					}
					
					if (cell11.getType() == CellType.LABEL) {
						LabelCell cell1_11 = (LabelCell)cell11;
						cell1Str11 = cell1_11.getString();
						if(!cell1Str11.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str11 = String.valueOf(setScale);	
						} else{
							cell1Str11 = "0.000000";
						}
					}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_11 = (NumberCell)cell11;
						cell1Str11 = String.valueOf(cell1_11.getValue());
						if(!cell1Str11.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str11 = String.valueOf(setScale);	
						} else{
							cell1Str11 = "0.000000";
						}
					}
					if(cell1Str11.trim().equals("")){
						cell1Str11 = "0.000000";
					}
					
					if (cell12.getType() == CellType.LABEL) {
						LabelCell cell1_12 = (LabelCell)cell12;
						cell1Str12 = cell1_12.getString();
						if(!cell1Str12.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str12 = String.valueOf(setScale);	
						} else{
							cell1Str12 = "0.000000";
						}
					}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_12 = (NumberCell)cell12;
						cell1Str12 = String.valueOf(cell1_12.getValue());
						if(!cell1Str12.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str12 = String.valueOf(setScale);	
						} else{
							cell1Str12 = "0.000000";
						}
					}
					if(cell1Str12.trim().equals("")){
						cell1Str12 = "0.000000";
					}
					
					if (cell13.getType() == CellType.LABEL) {
						LabelCell cell1_13 = (LabelCell)cell13;
						cell1Str13 = cell1_13.getString();
						if(!cell1Str13.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str13 = String.valueOf(setScale);	
						} else{
							cell1Str13 = "0.000000";
						}
					}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_13 = (NumberCell)cell13;
						cell1Str13 = String.valueOf(cell1_13.getValue());
						if(!cell1Str13.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str13 = String.valueOf(setScale);	
						} else{
							cell1Str13 = "0.000000";
						}
					}
					if(cell1Str13.trim().equals("")){
						cell1Str13 = "0.000000";
					}
					
					if (cell14.getType() == CellType.LABEL) {
						LabelCell cell1_14 = (LabelCell)cell14;
						cell1Str14 = cell1_14.getString();
						if(!cell1Str14.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str14 = String.valueOf(setScale);	
						} else{
							cell1Str14 = "0.000000";
						}
					}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_14 = (NumberCell)cell14;
						cell1Str14 = String.valueOf(cell1_14.getValue());
						if(!cell1Str14.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str14 = String.valueOf(setScale);	
						} else{
							cell1Str14 = "0.000000";
						}
					}
					if(cell1Str14.trim().equals("")){
						cell1Str14 = "0.000000";
					}
					
					if (cell15.getType() == CellType.LABEL) {
						LabelCell cell1_15 = (LabelCell)cell15;
						cell1Str15 = cell1_15.getString();
						if(!cell1Str15.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str15 = String.valueOf(setScale);	
						} else{
							cell1Str15 = "0.000000";
						}
					}else if (cell15.getType() == CellType.NUMBER || cell15.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_15 = (NumberCell)cell15;
						cell1Str15 = String.valueOf(cell1_15.getValue());
						if(!cell1Str15.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str15 = String.valueOf(setScale);	
						} else{
							cell1Str15 = "0.000000";
						}
					}
					if(cell1Str15.trim().equals("")){
						cell1Str15 = "0.000000";
					}
					
					if (cell16.getType() == CellType.LABEL) {
						LabelCell cell1_16 = (LabelCell)cell16;
						cell1Str16 = cell1_16.getString();
						if(!cell1Str16.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str16 = String.valueOf(setScale);	
						} else{
							cell1Str16 = "0.000000";
						}
					}else if (cell16.getType() == CellType.NUMBER || cell16.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_16 = (NumberCell)cell16;
						cell1Str16 = String.valueOf(cell1_16.getValue());
						if(!cell1Str16.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str16 = String.valueOf(setScale);	
						} else{
							cell1Str16 = "0.000000";
						}
					}
					if(cell1Str16.trim().equals("")){
						cell1Str16 = "0.000000";
					}
					
					if (cell17.getType() == CellType.LABEL) {
						LabelCell cell1_17 = (LabelCell)cell17;
						cell1Str17 = cell1_17.getString();
						if(!cell1Str17.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str17).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str17 = String.valueOf(setScale);	
						} else{
							cell1Str17 = "0.000000";
						}
					}else if (cell17.getType() == CellType.NUMBER || cell17.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_17 = (NumberCell)cell17;
						cell1Str17 = String.valueOf(cell1_17.getValue());
						if(!cell1Str17.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str17).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str17 = String.valueOf(setScale);	
						} else{
							cell1Str17 = "0.000000";
						}
					}
					if(cell1Str17.trim().equals("")){
						cell1Str17 = "0.000000";
					}
					
					if (cell18.getType() == CellType.LABEL) {
						LabelCell cell1_18 = (LabelCell)cell18;
						cell1Str18 = cell1_18.getString();
						if(!cell1Str18.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str18).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str18 = String.valueOf(setScale);	
						} else{
							cell1Str18 = "0.000000";
						}
					}else if (cell18.getType() == CellType.NUMBER || cell18.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_18 = (NumberCell)cell18;
						cell1Str18 = String.valueOf(cell1_18.getValue());
						if(!cell1Str18.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str18).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str18 = String.valueOf(setScale);	
						} else{
							cell1Str18 = "0.000000";
						}
					}
					if(cell1Str18.trim().equals("")){
						cell1Str18 = "0.000000";
					}
					
					if (cell19.getType() == CellType.LABEL) {
						LabelCell cell1_19 = (LabelCell)cell19;
						cell1Str19 = cell1_19.getString();
						if(!cell1Str19.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str19).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str19 = String.valueOf(setScale);	
						} else{
							cell1Str19 = "0.000000";
						}
					}else if (cell19.getType() == CellType.NUMBER || cell19.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_19 = (NumberCell)cell19;
						cell1Str19 = String.valueOf(cell1_19.getValue());
						if(!cell1Str19.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str19).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str19 = String.valueOf(setScale);	
						} else{
							cell1Str19 = "0.000000";
						}
					}
					if(cell1Str19.trim().equals("")){
						cell1Str19 = "0.000000";
					}
					
					if (cell20.getType() == CellType.LABEL) {
						LabelCell cell1_20 = (LabelCell)cell20;
						cell1Str20 = cell1_20.getString();
						if(!cell1Str20.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str20).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str20 = String.valueOf(setScale);	
						} else{
							cell1Str20 = "0.000000";
						}
					}else if (cell20.getType() == CellType.NUMBER || cell20.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_20 = (NumberCell)cell20;
						cell1Str20 = String.valueOf(cell1_20.getValue());
						if(!cell1Str20.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str20).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str20 = String.valueOf(setScale);	
						} else{
							cell1Str20 = "0.000000";
						}
					}
					if(cell1Str20.trim().equals("")){
						cell1Str20 = "0.000000";
					}
					
					if (cell21.getType() == CellType.LABEL) {
						LabelCell cell1_21 = (LabelCell)cell21;
						cell1Str21 = cell1_21.getString();
						if(!cell1Str21.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str21).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str21 = String.valueOf(setScale);	
						} else{
							cell1Str21 = "0.000000";
						}
					}else if (cell21.getType() == CellType.NUMBER || cell21.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_21 = (NumberCell)cell21;
						cell1Str21 = String.valueOf(cell1_21.getValue());
						if(!cell1Str21.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str21).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str21 = String.valueOf(setScale);	
						} else{
							cell1Str21 = "0.000000";
						}
					}
					if(cell1Str21.trim().equals("")){
						cell1Str21 = "0.000000";
					}
					
					if (cell22.getType() == CellType.LABEL) {
						LabelCell cell1_22 = (LabelCell)cell22;
						cell1Str22 = cell1_22.getString();
						if(!cell1Str22.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str22).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str22 = String.valueOf(setScale);	
						} else{
							cell1Str22 = "0.000000";
						}
					}else if (cell22.getType() == CellType.NUMBER || cell22.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_22 = (NumberCell)cell22;
						cell1Str22 = String.valueOf(cell1_22.getValue());
						if(!cell1Str22.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str22).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str22 = String.valueOf(setScale);	
						} else{
							cell1Str22 = "0.000000";
						}
					}
					if(cell1Str22.trim().equals("")){
						cell1Str22 = "0.000000";
					}
					
					if (cell23.getType() == CellType.LABEL) {
						LabelCell cell1_23 = (LabelCell)cell23;
						cell1Str23 = cell1_23.getString();
						if(!cell1Str23.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str23).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str23 = String.valueOf(setScale);	
						} else{
							cell1Str23 = "0.000000";
						}
					}else if (cell23.getType() == CellType.NUMBER || cell23.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_23 = (NumberCell)cell23;
						cell1Str23 = String.valueOf(cell1_23.getValue());
						if(!cell1Str23.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str23).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str23 = String.valueOf(setScale);	
						} else{
							cell1Str23 = "0.000000";
						}
					}
					if(cell1Str23.trim().equals("")){
						cell1Str23 = "0.000000";
					}
					
					if (cell24.getType() == CellType.LABEL) {
						LabelCell cell1_24 = (LabelCell)cell24;
						cell1Str24 = cell1_24.getString();
						if(!cell1Str24.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str24).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str24 = String.valueOf(setScale);	
						} else{
							cell1Str24 = "0.000000";
						}
					}else if (cell24.getType() == CellType.NUMBER || cell24.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_24 = (NumberCell)cell24;
						cell1Str24 = String.valueOf(cell1_24.getValue());
						if(!cell1Str24.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str24).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str24 = String.valueOf(setScale);	
						} else{
							cell1Str24 = "0.000000";
						}
					}
					if(cell1Str24.trim().equals("")){
						cell1Str24 = "0.000000";
					}
					
					if (cell25.getType() == CellType.LABEL) {
						LabelCell cell1_25 = (LabelCell)cell25;
						cell1Str25 = cell1_25.getString();
						if(!cell1Str25.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str25).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str25 = String.valueOf(setScale);	
						} else{
							cell1Str25 = "0.000000";
						}
					}else if (cell25.getType() == CellType.NUMBER || cell25.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_25 = (NumberCell)cell25;
						cell1Str25 = String.valueOf(cell1_25.getValue());
						if(!cell1Str25.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str25).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str25 = String.valueOf(setScale);	
						} else{
							cell1Str25 = "0.000000";
						}
					}
					if(cell1Str25.trim().equals("")){
						cell1Str25 = "0.000000";
					}
					
					if (cell26.getType() == CellType.LABEL) {
						LabelCell cell1_26 = (LabelCell)cell26;
						cell1Str26 = cell1_26.getString();
						if(!cell1Str26.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str26).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str26 = String.valueOf(setScale);	
						} else{
							cell1Str26 = "0.000000";
						}
					}else if (cell26.getType() == CellType.NUMBER || cell26.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_26 = (NumberCell)cell26;
						cell1Str26 = String.valueOf(cell1_26.getValue());
						if(!cell1Str26.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str26).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str26 = String.valueOf(setScale);	
						} else{
							cell1Str26 = "0.000000";
						}
					}
					if(cell1Str26.trim().equals("")){
						cell1Str26 = "0.000000";
					}
					
					if (cell27.getType() == CellType.LABEL) {
						LabelCell cell1_27 = (LabelCell)cell27;
						cell1Str27 = cell1_27.getString();
						if(!cell1Str27.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str27).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str27 = String.valueOf(setScale);	
						} else{
							cell1Str27 = "0.000000";
						}
					}else if (cell27.getType() == CellType.NUMBER || cell27.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_27 = (NumberCell)cell27;
						cell1Str27 = String.valueOf(cell1_27.getValue());
						if(!cell1Str27.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str27).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str27 = String.valueOf(setScale);	
						} else{
							cell1Str27 = "0.000000";
						}
					}
					if(cell1Str27.trim().equals("")){
						cell1Str27 = "0.000000";
					}
					
					if (cell28.getType() == CellType.LABEL) {
						LabelCell cell1_28 = (LabelCell)cell28;
						cell1Str28 = cell1_28.getString();
						if(!cell1Str28.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str28).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str28 = String.valueOf(setScale);	
						} else{
							cell1Str28 = "0.000000";
						}
					}else if (cell28.getType() == CellType.NUMBER || cell28.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_28 = (NumberCell)cell28;
						cell1Str28 = String.valueOf(cell1_28.getValue());
						if(!cell1Str28.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str28).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str28 = String.valueOf(setScale);	
						} else{
							cell1Str28 = "0.000000";
						}
					}
					if(cell1Str28.trim().equals("")){
						cell1Str28 = "0.000000";
					}
					
					/*if (cell29.getType() == CellType.LABEL) {
						LabelCell cell1_29 = (LabelCell)cell29;
						cell1Str29 = cell1_29.getString();
						if(!cell1Str29.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str29).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str29 = String.valueOf(setScale);	
						} else{
							cell1Str29 = "0.000000";
						}
					}else if (cell29.getType() == CellType.NUMBER || cell29.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_29 = (NumberCell)cell29;
						cell1Str29 = String.valueOf(cell1_29.getValue());
						if(!cell1Str29.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str29).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str29 = String.valueOf(setScale);	
						} else{
							cell1Str29 = "0.000000";
						}
					}
					if(cell1Str29.trim().equals("")){
						cell1Str29 = "0.000000";
					}
					if (cell30.getType() == CellType.LABEL) {
						LabelCell cell1_30 = (LabelCell)cell30;
						cell1Str30 = cell1_30.getString();
						if(!cell1Str30.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str30).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str30 = String.valueOf(setScale);	
						} else{
							cell1Str30 = "0.000000";
						}
					}else if (cell30.getType() == CellType.NUMBER || cell30.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_30 = (NumberCell)cell30;
						cell1Str30 = String.valueOf(cell1_30.getValue());
						if(!cell1Str30.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str30).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str30 = String.valueOf(setScale);	
						} else{
							cell1Str30 = "0.000000";
						}
					}
					if(cell1Str30.trim().equals("")){
						cell1Str30 = "0.000000";
					}*/
					
					sb.append(cell1Str2 + "|");
					sb.append(cell1Str3 + "|");
					sb.append(cell1Str4 + "|");
					sb.append(cell1Str5 + "|");
					sb.append(cell1Str6 + "|");
					sb.append(cell1Str7 + "|");
					sb.append(cell1Str8 + "|");
//					sb.append(cell1Str9 + "|");
//					sb.append(cell1Str1 + "|");
					sb.append(cell1Str10 + "|");
					sb.append(cell1Str11 + "|");
					sb.append(cell1Str12 + "|");
//					sb.append(cell1Str13 + "|");
					sb.append(cell1Str14 + "|");
					sb.append(cell1Str15 + "|");
					sb.append(cell1Str16 + "|");
					sb.append(cell1Str17 + "|");
					sb.append(cell1Str18 + "|");
					sb.append(cell1Str19 + "|");
					sb.append(cell1Str20 + "|");
					sb.append(cell1Str21 + "|");
					sb.append(cell1Str22 + "|");
					sb.append(cell1Str23 + "|");
					sb.append(cell1Str25 + "|");
					sb.append(cell1Str26 + "|");
					sb.append(cell1Str27 + "|");
					sb.append(cell1Str28 );
//					sb.append(cell1Str29 + "|");
//					sb.append(cell1Str30 + "\n");
					
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
		} catch (Exception e) {
			log.error("报表六出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		book.close();
		return true;
	}
	/**
	 * 表1-7
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_7(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4 ;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
//				String cell1Str1 = cell1.getContents();
//				String cell1Str2 = cell2.getContents();
//				String cell1Str3 = cell3.getContents();
//				String cell1Str4 = cell4.getContents();
				/*NumberCell cell1_1 = (NumberCell)cell1;
				double cell1Str1 = cell1_1.getValue();
				NumberCell cell1_2 = (NumberCell)cell2;
				double cell1Str2 = cell1_2.getValue();
				NumberCell cell1_3 = (NumberCell)cell3;
				double cell1Str3 = cell1_3.getValue();
				NumberCell cell1_4 = (NumberCell)cell4;
				double cell1Str4 = cell1_4.getValue();*/
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = date + "|" + cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 ;
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表七出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-8
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_8(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4 ;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
//				String cell1Str1 = cell1.getContents();
//				String cell1Str2 = cell2.getContents();
//				String cell1Str3 = cell3.getContents();
//				String cell1Str4 = cell4.getContents();
				/*NumberCell cell1_1 = (NumberCell)cell1;
				double cell1Str1 = cell1_1.getValue();
				NumberCell cell1_2 = (NumberCell)cell2;
				double cell1Str2 = cell1_2.getValue();
				NumberCell cell1_3 = (NumberCell)cell3;
				double cell1Str3 = cell1_3.getValue();
				NumberCell cell1_4 = (NumberCell)cell4;
				double cell1Str4 = cell1_4.getValue();*/
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = date + "|" + cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 ;
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表八出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-9
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_9(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4, cell5 ;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);// 获取左上角的单元格//（列，行）
			String bank_name = sheet.getCell(3, 2).getContents();
			String account_name = sheet.getCell(3, 3).getContents();
//			System.out.println(bank_name + "========" + account_name);
			i=1;
			while(true) {
				String cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000";
				cell1 = sheet.getCell(i, 6);
				String contents = cell1.getContents();
				if ((riInt+"日").equals(contents)) {
					cell2 = sheet.getCell(i, 7);
					cell3 = sheet.getCell(i, 8);
					cell4 = sheet.getCell(i, 9);
					cell5 = sheet.getCell(i, 10);
					if (cell2.getType() == CellType.LABEL) {
						LabelCell cell1_2 = (LabelCell)cell2;
						cell1Str2 = cell1_2.getString();
						if(!cell1Str2.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str2 = String.valueOf(setScale);	
						} else{
							cell1Str2 = "0.000000";
						}
					}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_2 = (NumberCell)cell2;
						cell1Str2 = String.valueOf(cell1_2.getValue());
						if(!cell1Str2.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str2 = String.valueOf(setScale);	
						} else{
							cell1Str2 = "0.000000";
						}
					}
					if(cell1Str2.trim().equals("")){
						cell1Str2 = "0.000000";
					}
					
					if (cell3.getType() == CellType.LABEL) {
						LabelCell cell1_3 = (LabelCell)cell3;
						cell1Str3 = cell1_3.getString();
						if(!cell1Str3.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str3 = String.valueOf(setScale);	
						} else{
							cell1Str3 = "0.000000";
						}
					}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_3 = (NumberCell)cell3;
						cell1Str3 = String.valueOf(cell1_3.getValue());
						if(!cell1Str3.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str3 = String.valueOf(setScale);	
						} else{
							cell1Str3 = "0.000000";
						}
					}
					if(cell1Str3.trim().equals("")){
						cell1Str3 = "0.000000";
					}
					
					if (cell4.getType() == CellType.LABEL) {
						LabelCell cell1_4 = (LabelCell)cell4;
						cell1Str4 = cell1_4.getString();
						if(!cell1Str4.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str4 = String.valueOf(setScale);	
						} else{
							cell1Str4 = "0.000000";
						}
					}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_4 = (NumberCell)cell4;
						cell1Str4 = String.valueOf(cell1_4.getValue());
						if(!cell1Str4.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str4 = String.valueOf(setScale);	
						} else{
							cell1Str4 = "0.000000";
						}
					}
					if(cell1Str4.trim().equals("")){
						cell1Str4 = "0.000000";
					}
					
					if (cell5.getType() == CellType.LABEL) {
						LabelCell cell1_5 = (LabelCell)cell5;
						cell1Str5 = cell1_5.getString();
						if(!cell1Str5.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str5 = String.valueOf(setScale);	
						} else{
							cell1Str5 = "0.000000";
						}
					}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_5 = (NumberCell)cell5;
						cell1Str5 = String.valueOf(cell1_5.getValue());
						if(!cell1Str5.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str5 = String.valueOf(setScale);	
						} else{
							cell1Str5 = "0.000000";
						}
					}
					if(cell1Str5.trim().equals("")){
						cell1Str5 = "0.000000";
					}
					
					/*NumberCell cell1_2 = (NumberCell)cell2;
					double cell1Str2 = cell1_2.getValue();
					NumberCell cell1_3 = (NumberCell)cell3;
					double cell1Str3 = cell1_3.getValue();
					NumberCell cell1_4 = (NumberCell)cell4;
					double cell1Str4 = cell1_4.getValue();
					NumberCell cell1_5 = (NumberCell)cell5;
					double cell1Str5 = cell1_5.getValue();*/
					
					sb.append(data + bank_name + "|" + account_name + "|" + date + "|");
					sb.append(cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 );
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表九出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-10
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_10(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24;
		
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			String bank_name = sheet.getCell(3, 1).getContents();
			String account_name = sheet.getCell(3, 2).getContents();
//			System.out.println(bank_name + "========" + account_name);
			i=6;
			while(true) {
				String cell1Str1 = "0", cell1Str2 = "0.000000", cell1Str3 = "0", cell1Str4 = "0.000000", cell1Str5 = "0", cell1Str6 = "0.000000", cell1Str7 = "0", cell1Str8 = "0.000000", cell1Str9 = "0", cell1Str10 = "0.000000" , cell1Str11 = "0", cell1Str12 = "0.000000", cell1Str13 = "0",cell1Str14 = "0.000000", cell1Str15 = "0", cell1Str16 = "0.000000", cell1Str17 = "0", cell1Str18 = "0.000000", cell1Str19 = "0", cell1Str20 = "0.000000", cell1Str21 = "0", cell1Str22 = "0.000000", cell1Str23 = "0", cell1Str24 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				cell10 = sheet.getCell(10, i);
				cell11 = sheet.getCell(11, i);
				cell12 = sheet.getCell(12, i);
				cell13 = sheet.getCell(13, i);
				cell14 = sheet.getCell(14, i);
				cell15 = sheet.getCell(15, i);
				cell16 = sheet.getCell(16, i);
				cell17 = sheet.getCell(17, i);
				cell18 = sheet.getCell(18, i);
				cell19 = sheet.getCell(19, i);
				cell20 = sheet.getCell(20, i);
				cell21 = sheet.getCell(21, i);
				cell22 = sheet.getCell(22, i);
				cell23 = sheet.getCell(23, i);
				cell24 = sheet.getCell(24, i);
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = (int)Double.parseDouble((String.valueOf(cell1_1.getValue()))) + "";
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = (int)Double.parseDouble(String.valueOf(cell1_3.getValue())) + "";
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = (int)Double.parseDouble(String.valueOf(cell1_5.getValue())) + "";
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				if (cell7.getType() == CellType.LABEL) {
					LabelCell cell1_7 = (LabelCell)cell7;
					cell1Str7 = cell1_7.getString();
				}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_7 = (NumberCell)cell7;
					cell1Str7 = (int)Double.parseDouble(String.valueOf(cell1_7.getValue())) + "";
				}
				if(cell1Str7.trim().equals("")){
					cell1Str7 = "0";
				}
				
				if (cell8.getType() == CellType.LABEL) {
					LabelCell cell1_8 = (LabelCell)cell8;
					cell1Str8 = cell1_8.getString();
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_8 = (NumberCell)cell8;
					cell1Str8 = String.valueOf(cell1_8.getValue());
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}
				if(cell1Str8.trim().equals("")){
					cell1Str8 = "0.000000";
				}
				
				if (cell9.getType() == CellType.LABEL) {
					LabelCell cell1_9 = (LabelCell)cell9;
					cell1Str9 = cell1_9.getString();
				}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_9 = (NumberCell)cell9;
					cell1Str9 = (int)Double.parseDouble(String.valueOf(cell1_9.getValue())) + "";
				}
				if(cell1Str9.trim().equals("")){
					cell1Str9 = "0";
				}
				
				if (cell10.getType() == CellType.LABEL) {
					LabelCell cell1_10 = (LabelCell)cell10;
					cell1Str10 = cell1_10.getString();
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_10 = (NumberCell)cell10;
					cell1Str10 = String.valueOf(cell1_10.getValue());
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}
				if(cell1Str10.trim().equals("")){
					cell1Str10 = "0.000000";
				}
				
				if (cell11.getType() == CellType.LABEL) {
					LabelCell cell1_11 = (LabelCell)cell11;
					cell1Str11 = cell1_11.getString();
				}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_11 = (NumberCell)cell11;
					cell1Str11 = (int)Double.parseDouble(String.valueOf(cell1_11.getValue())) + "";
				}
				if(cell1Str11.trim().equals("")){
					cell1Str11 = "0";
				}
				
				if (cell12.getType() == CellType.LABEL) {
					LabelCell cell1_12 = (LabelCell)cell12;
					cell1Str12 = cell1_12.getString();
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_12 = (NumberCell)cell12;
					cell1Str12 = String.valueOf(cell1_12.getValue());
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}
				if(cell1Str10.trim().equals("")){
					cell1Str10 = "0.000000";
				}
				
				if (cell13.getType() == CellType.LABEL) {
					LabelCell cell1_13 = (LabelCell)cell13;
					cell1Str13 = cell1_13.getString();
				}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_13 = (NumberCell)cell13;
					cell1Str13 = (int)Double.parseDouble(String.valueOf(cell1_13.getValue())) + "";
				}
				if(cell1Str13.trim().equals("")){
					cell1Str13 = "0";
				}
				
				if (cell14.getType() == CellType.LABEL) {
					LabelCell cell1_14 = (LabelCell)cell14;
					cell1Str14 = cell1_14.getString();
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_14 = (NumberCell)cell14;
					cell1Str14 = String.valueOf(cell1_14.getValue());
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}
				if(cell1Str14.trim().equals("")){
					cell1Str14 = "0.000000";
				}
				
				if (cell15.getType() == CellType.LABEL) {
					LabelCell cell1_15 = (LabelCell)cell15;
					cell1Str15 = cell1_15.getString();
				}else if (cell15.getType() == CellType.NUMBER || cell15.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_15 = (NumberCell)cell15;
					cell1Str15 = (int)Double.parseDouble(String.valueOf(cell1_15.getValue())) + "";
				}
				if(cell1Str15.trim().equals("")){
					cell1Str15 = "0";
				}
				
				if (cell16.getType() == CellType.LABEL) {
					LabelCell cell1_16 = (LabelCell)cell16;
					cell1Str16 = cell1_16.getString();
					if(!cell1Str16.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str16 = String.valueOf(setScale);	
					} else{
						cell1Str16 = "0.000000";
					}
				}else if (cell16.getType() == CellType.NUMBER || cell16.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_16 = (NumberCell)cell16;
					cell1Str16 = String.valueOf(cell1_16.getValue());
					if(!cell1Str16.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str16 = String.valueOf(setScale);	
					} else{
						cell1Str16 = "0.000000";
					}
				}
				if(cell1Str16.trim().equals("")){
					cell1Str16 = "0.000000";
				}
				
				if (cell17.getType() == CellType.LABEL) {
					LabelCell cell1_17 = (LabelCell)cell17;
					cell1Str17 = cell1_17.getString();
				}else if (cell17.getType() == CellType.NUMBER || cell17.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_17 = (NumberCell)cell17;
					cell1Str17 = (int)Double.parseDouble(String.valueOf(cell1_17.getValue())) + "";
				}
				if(cell1Str17.trim().equals("")){
					cell1Str17 = "0";
				}
				
				if (cell18.getType() == CellType.LABEL) {
					LabelCell cell1_18 = (LabelCell)cell18;
					cell1Str18 = cell1_18.getString();
					if(!cell1Str18.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str18).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str18 = String.valueOf(setScale);	
					} else{
						cell1Str18 = "0.000000";
					}
				}else if (cell18.getType() == CellType.NUMBER || cell18.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_18 = (NumberCell)cell18;
					cell1Str18 = String.valueOf(cell1_18.getValue());
					if(!cell1Str18.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str18).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str18 = String.valueOf(setScale);	
					} else{
						cell1Str18 = "0.000000";
					}
				}
				if(cell1Str18.trim().equals("")){
					cell1Str18 = "0.000000";
				}
				
				if (cell19.getType() == CellType.LABEL) {
					LabelCell cell1_19 = (LabelCell)cell19;
					cell1Str19 = cell1_19.getString();
				}else if (cell19.getType() == CellType.NUMBER || cell19.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_19 = (NumberCell)cell19;
					cell1Str19 = (int)Double.parseDouble(String.valueOf(cell1_19.getValue())) + "";
				}
				if(cell1Str19.trim().equals("")){
					cell1Str19 = "0";
				}
				
				if (cell20.getType() == CellType.LABEL) {
					LabelCell cell1_20 = (LabelCell)cell20;
					cell1Str20 = cell1_20.getString();
					if(!cell1Str20.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str20).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str20 = String.valueOf(setScale);	
					} else{
						cell1Str20 = "0.000000";
					}
				}else if (cell20.getType() == CellType.NUMBER || cell20.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_20 = (NumberCell)cell20;
					cell1Str20 = String.valueOf(cell1_20.getValue());
					if(!cell1Str20.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str20).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str20 = String.valueOf(setScale);	
					} else{
						cell1Str20 = "0.000000";
					}
				}
				if(cell1Str20.trim().equals("")){
					cell1Str20 = "0.000000";
				}
				
				if (cell21.getType() == CellType.LABEL) {
					LabelCell cell1_21 = (LabelCell)cell21;
					cell1Str21 = cell1_21.getString();
				}else if (cell21.getType() == CellType.NUMBER || cell21.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_21 = (NumberCell)cell21;
					cell1Str21 = (int)Double.parseDouble(String.valueOf(cell1_21.getValue())) + "";
				}
				if(cell1Str21.trim().equals("")){
					cell1Str21 = "0";
				}
				
				if (cell22.getType() == CellType.LABEL) {
					LabelCell cell1_22 = (LabelCell)cell22;
					cell1Str22 = cell1_22.getString();
					if(!cell1Str22.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str22).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str22 = String.valueOf(setScale);	
					} else{
						cell1Str22 = "0.000000";
					}
				}else if (cell22.getType() == CellType.NUMBER || cell22.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_22 = (NumberCell)cell22;
					cell1Str22 = String.valueOf(cell1_22.getValue());
					if(!cell1Str22.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str22).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str22 = String.valueOf(setScale);	
					} else{
						cell1Str22 = "0.000000";
					}
				}
				if(cell1Str22.trim().equals("")){
					cell1Str22 = "0.000000";
				}
				
				if (cell23.getType() == CellType.LABEL) {
					LabelCell cell1_23 = (LabelCell)cell23;
					cell1Str23 = cell1_23.getString();
				}else if (cell23.getType() == CellType.NUMBER || cell23.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_23 = (NumberCell)cell23;
					cell1Str23 = (int)Double.parseDouble(String.valueOf(cell1_23.getValue())) + "";
				}
				if(cell1Str23.trim().equals("")){
					cell1Str23 = "0";
				}
				
				if (cell24.getType() == CellType.LABEL) {
					LabelCell cell1_24 = (LabelCell)cell24;
					cell1Str24 = cell1_24.getString();
					if(!cell1Str24.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str24).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str24 = String.valueOf(setScale);	
					} else{
						cell1Str24 = "0.000000";
					}
				}else if (cell24.getType() == CellType.NUMBER || cell24.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_24 = (NumberCell)cell24;
					cell1Str24 = String.valueOf(cell1_24.getValue());
					if(!cell1Str24.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str24).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str24 = String.valueOf(setScale);	
					} else{
						cell1Str24 = "0.000000";
					}
				}
				
				String contents = sheet.getCell(0, i).getContents();
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(bank_name + "|" + account_name + "|" + date + "|");
					sb.append(cell1Str1 + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" +cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 + "|" + cell1Str17 + "|" + cell1Str18 + "|" + cell1Str19 + "|" + cell1Str20 + "|" + cell1Str21 + "|" + cell1Str22 +  "|" + cell1Str23 + "|" + cell1Str24 );
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-11
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_11(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000" , cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000",cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000", cell1Str18 = "0.000000", cell1Str19 = "0.000000", cell1Str20 = "0.000000", cell1Str21 = "0.000000", cell1Str22 = "0.000000", cell1Str23 = "0.000000", cell1Str24 = "0.000000", cell1Str25 = "0.000000", cell1Str26 = "0.000000", cell1Str27 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				cell10 = sheet.getCell(10, i);
				cell11 = sheet.getCell(11, i);
				cell12 = sheet.getCell(12, i);
				cell13 = sheet.getCell(13, i);
				cell14 = sheet.getCell(14, i);
				cell15 = sheet.getCell(15, i);
				cell16 = sheet.getCell(16, i);
				cell17 = sheet.getCell(17, i);
				cell18 = sheet.getCell(18, i);
				cell19 = sheet.getCell(19, i);
				cell20 = sheet.getCell(20, i);
				cell21 = sheet.getCell(21, i);
				cell22 = sheet.getCell(22, i);
				cell23 = sheet.getCell(23, i);
				cell24 = sheet.getCell(24, i);
				cell25 = sheet.getCell(25, i);
				cell26 = sheet.getCell(26, i);
				cell27 = sheet.getCell(27, i);
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				if (cell7.getType() == CellType.LABEL) {
					LabelCell cell1_7 = (LabelCell)cell7;
					cell1Str7 = cell1_7.getString();
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_7 = (NumberCell)cell7;
					cell1Str7 = String.valueOf(cell1_7.getValue());
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}
				if(cell1Str7.trim().equals("")){
					cell1Str7 = "0.000000";
				}
				
				if (cell8.getType() == CellType.LABEL) {
					LabelCell cell1_8 = (LabelCell)cell8;
					cell1Str8 = cell1_8.getString();
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_8 = (NumberCell)cell8;
					cell1Str8 = String.valueOf(cell1_8.getValue());
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}
				if(cell1Str8.trim().equals("")){
					cell1Str8 = "0.000000";
				}
				
				if (cell9.getType() == CellType.LABEL) {
					LabelCell cell1_9 = (LabelCell)cell9;
					cell1Str9 = cell1_9.getString();
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_9 = (NumberCell)cell9;
					cell1Str9 = String.valueOf(cell1_9.getValue());
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}
				if(cell1Str9.trim().equals("")){
					cell1Str9 = "0.000000";
				}
				
				if (cell10.getType() == CellType.LABEL) {
					LabelCell cell1_10 = (LabelCell)cell10;
					cell1Str10 = cell1_10.getString();
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_10 = (NumberCell)cell10;
					cell1Str10 = String.valueOf(cell1_10.getValue());
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}
				if(cell1Str10.trim().equals("")){
					cell1Str10 = "0.000000";
				}
				
				if (cell11.getType() == CellType.LABEL) {
					LabelCell cell1_11 = (LabelCell)cell11;
					cell1Str11 = cell1_11.getString();
					if(!cell1Str11.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str11 = String.valueOf(setScale);	
					} else{
						cell1Str11 = "0.000000";
					}
				}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_11 = (NumberCell)cell11;
					cell1Str11 = String.valueOf(cell1_11.getValue());
					if(!cell1Str11.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str11 = String.valueOf(setScale);	
					} else{
						cell1Str11 = "0.000000";
					}
				}
				if(cell1Str11.trim().equals("")){
					cell1Str11 = "0.000000";
				}
				
				if (cell12.getType() == CellType.LABEL) {
					LabelCell cell1_12 = (LabelCell)cell12;
					cell1Str12 = cell1_12.getString();
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_12 = (NumberCell)cell12;
					cell1Str12 = String.valueOf(cell1_12.getValue());
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}
				if(cell1Str12.trim().equals("")){
					cell1Str12 = "0.000000";
				}
				
				if (cell13.getType() == CellType.LABEL) {
					LabelCell cell1_13 = (LabelCell)cell13;
					cell1Str13 = cell1_13.getString();
					if(!cell1Str13.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str13 = String.valueOf(setScale);	
					} else{
						cell1Str13 = "0.000000";
					}
				}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_13 = (NumberCell)cell13;
					cell1Str13 = String.valueOf(cell1_13.getValue());
					if(!cell1Str13.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str13 = String.valueOf(setScale);	
					} else{
						cell1Str13 = "0.000000";
					}
				}
				if(cell1Str13.trim().equals("")){
					cell1Str13 = "0.000000";
				}
				
				if (cell14.getType() == CellType.LABEL) {
					LabelCell cell1_14 = (LabelCell)cell14;
					cell1Str14 = cell1_14.getString();
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_14 = (NumberCell)cell14;
					cell1Str14 = String.valueOf(cell1_14.getValue());
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}
				if(cell1Str14.trim().equals("")){
					cell1Str14 = "0.000000";
				}
				
				if (cell15.getType() == CellType.LABEL) {
					LabelCell cell1_15 = (LabelCell)cell15;
					cell1Str15 = cell1_15.getString();
					if(!cell1Str15.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str15 = String.valueOf(setScale);	
					} else{
						cell1Str15 = "0.000000";
					}
				}else if (cell15.getType() == CellType.NUMBER || cell15.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_15 = (NumberCell)cell15;
					cell1Str15 = String.valueOf(cell1_15.getValue());
					if(!cell1Str15.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str15 = String.valueOf(setScale);	
					} else{
						cell1Str15 = "0.000000";
					}
				}
				if(cell1Str15.trim().equals("")){
					cell1Str15 = "0.000000";
				}
				
				if (cell16.getType() == CellType.LABEL) {
					LabelCell cell1_16 = (LabelCell)cell16;
					cell1Str16 = cell1_16.getString();
					if(!cell1Str16.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str16 = String.valueOf(setScale);	
					} else{
						cell1Str16 = "0.000000";
					}
				}else if (cell16.getType() == CellType.NUMBER || cell16.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_16 = (NumberCell)cell16;
					cell1Str16 = String.valueOf(cell1_16.getValue());
					if(!cell1Str16.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str16 = String.valueOf(setScale);	
					} else{
						cell1Str16 = "0.000000";
					}
				}
				if(cell1Str16.trim().equals("")){
					cell1Str16 = "0.000000";
				}
				
				if (cell17.getType() == CellType.LABEL) {
					LabelCell cell1_17 = (LabelCell)cell17;
					cell1Str17 = cell1_17.getString();
					if(!cell1Str17.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str17).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str17 = String.valueOf(setScale);	
					} else{
						cell1Str17 = "0.000000";
					}
				}else if (cell17.getType() == CellType.NUMBER || cell17.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_17 = (NumberCell)cell17;
					cell1Str17 = String.valueOf(cell1_17.getValue());
					if(!cell1Str17.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str17).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str17 = String.valueOf(setScale);	
					} else{
						cell1Str17 = "0.000000";
					}
				}
				if(cell1Str17.trim().equals("")){
					cell1Str17 = "0.000000";
				}
				
				if (cell18.getType() == CellType.LABEL) {
					LabelCell cell1_18 = (LabelCell)cell18;
					cell1Str18 = cell1_18.getString();
					if(!cell1Str18.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str18).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str18 = String.valueOf(setScale);	
					} else{
						cell1Str18 = "0.000000";
					}
				}else if (cell18.getType() == CellType.NUMBER || cell18.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_18 = (NumberCell)cell18;
					cell1Str18 = String.valueOf(cell1_18.getValue());
					if(!cell1Str18.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str18).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str18 = String.valueOf(setScale);	
					} else{
						cell1Str18 = "0.000000";
					}
				}
				if(cell1Str18.trim().equals("")){
					cell1Str18 = "0.000000";
				}
				
				if (cell19.getType() == CellType.LABEL) {
					LabelCell cell1_19 = (LabelCell)cell19;
					cell1Str19 = cell1_19.getString();
					if(!cell1Str19.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str19).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str19 = String.valueOf(setScale);	
					} else{
						cell1Str19 = "0.000000";
					}
				}else if (cell19.getType() == CellType.NUMBER || cell19.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_19 = (NumberCell)cell19;
					cell1Str19 = String.valueOf(cell1_19.getValue());
					if(!cell1Str19.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str19).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str19 = String.valueOf(setScale);	
					} else{
						cell1Str19 = "0.000000";
					}
				}
				if(cell1Str19.trim().equals("")){
					cell1Str19 = "0.000000";
				}
				
				if (cell20.getType() == CellType.LABEL) {
					LabelCell cell1_20 = (LabelCell)cell20;
					cell1Str20 = cell1_20.getString();
					if(!cell1Str20.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str20).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str20 = String.valueOf(setScale);	
					} else{
						cell1Str20 = "0.000000";
					}
				}else if (cell20.getType() == CellType.NUMBER || cell20.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_20 = (NumberCell)cell20;
					cell1Str20 = String.valueOf(cell1_20.getValue());
					if(!cell1Str20.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str20).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str20 = String.valueOf(setScale);	
					} else{
						cell1Str20 = "0.000000";
					}
				}
				if(cell1Str20.trim().equals("")){
					cell1Str20 = "0.000000";
				}
				
				if (cell21.getType() == CellType.LABEL) {
					LabelCell cell1_21 = (LabelCell)cell21;
					cell1Str21 = cell1_21.getString();
					if(!cell1Str21.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str21).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str21 = String.valueOf(setScale);	
					} else{
						cell1Str21 = "0.000000";
					}
				}else if (cell21.getType() == CellType.NUMBER || cell21.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_21 = (NumberCell)cell21;
					cell1Str21 = String.valueOf(cell1_21.getValue());
					if(!cell1Str21.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str21).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str21 = String.valueOf(setScale);	
					} else{
						cell1Str21 = "0.000000";
					}
				}
				if(cell1Str21.trim().equals("")){
					cell1Str21 = "0.000000";
				}
				
				if (cell22.getType() == CellType.LABEL) {
					LabelCell cell1_22 = (LabelCell)cell22;
					cell1Str22 = cell1_22.getString();
					if(!cell1Str22.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str22).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str22 = String.valueOf(setScale);	
					} else{
						cell1Str22 = "0.000000";
					}
				}else if (cell22.getType() == CellType.NUMBER || cell22.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_22 = (NumberCell)cell22;
					cell1Str22 = String.valueOf(cell1_22.getValue());
					if(!cell1Str22.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str22).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str22 = String.valueOf(setScale);	
					} else{
						cell1Str22 = "0.000000";
					}
				}
				if(cell1Str22.trim().equals("")){
					cell1Str22 = "0.000000";
				}
				
				if (cell23.getType() == CellType.LABEL) {
					LabelCell cell1_23 = (LabelCell)cell23;
					cell1Str23 = cell1_23.getString();
					if(!cell1Str23.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str23).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str23 = String.valueOf(setScale);	
					} else{
						cell1Str23 = "0.000000";
					}
				}else if (cell23.getType() == CellType.NUMBER || cell23.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_23 = (NumberCell)cell23;
					cell1Str23 = String.valueOf(cell1_23.getValue());
					if(!cell1Str23.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str23).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str23 = String.valueOf(setScale);	
					} else{
						cell1Str23 = "0.000000";
					}
				}
				if(cell1Str23.trim().equals("")){
					cell1Str23 = "0.000000";
				}
				
				if (cell24.getType() == CellType.LABEL) {
					LabelCell cell1_24 = (LabelCell)cell24;
					cell1Str24 = cell1_24.getString();
					if(!cell1Str24.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str24).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str24 = String.valueOf(setScale);	
					} else{
						cell1Str24 = "0.000000";
					}
				}else if (cell24.getType() == CellType.NUMBER || cell24.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_24 = (NumberCell)cell24;
					cell1Str24 = String.valueOf(cell1_24.getValue());
					if(!cell1Str24.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str24).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str24 = String.valueOf(setScale);	
					} else{
						cell1Str24 = "0.000000";
					}
				}
				if(cell1Str24.trim().equals("")){
					cell1Str24 = "0.000000";
				}
				
				if (cell25.getType() == CellType.LABEL) {
					LabelCell cell1_25 = (LabelCell)cell25;
					cell1Str25 = cell1_25.getString();
					if(!cell1Str25.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str25).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str25 = String.valueOf(setScale);	
					} else{
						cell1Str25 = "0.000000";
					}
				}else if (cell25.getType() == CellType.NUMBER || cell25.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_25 = (NumberCell)cell25;
					cell1Str25 = String.valueOf(cell1_25.getValue());
					if(!cell1Str25.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str25).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str25 = String.valueOf(setScale);	
					} else{
						cell1Str25 = "0.000000";
					}
				}
				if(cell1Str25.trim().equals("")){
					cell1Str25 = "0.000000";
				}
				
				if (cell26.getType() == CellType.LABEL) {
					LabelCell cell1_26 = (LabelCell)cell26;
					cell1Str26 = cell1_26.getString();
					if(!cell1Str26.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str26).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str26 = String.valueOf(setScale);	
					} else{
						cell1Str26 = "0.000000";
					}
				}else if (cell26.getType() == CellType.NUMBER || cell26.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_26 = (NumberCell)cell26;
					cell1Str26 = String.valueOf(cell1_26.getValue());
					if(!cell1Str26.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str26).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str26 = String.valueOf(setScale);	
					} else{
						cell1Str26 = "0.000000";
					}
				}
				if(cell1Str26.trim().equals("")){
					cell1Str26 = "0.000000";
				}
				
				if (cell27.getType() == CellType.LABEL) {
					LabelCell cell1_27 = (LabelCell)cell27;
					cell1Str27 = cell1_27.getString();
					if(!cell1Str27.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str27).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str27 = String.valueOf(setScale);	
					} else{
						cell1Str27 = "0.000000";
					}
				}else if (cell27.getType() == CellType.NUMBER || cell27.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_27 = (NumberCell)cell27;
					cell1Str27 = String.valueOf(cell1_27.getValue());
					if(!cell1Str27.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str27).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str27 = String.valueOf(setScale);	
					} else{
						cell1Str27 = "0.000000";
					}
				}
				if(cell1Str27.trim().equals("")){
					cell1Str27 = "0.000000";
				}
				
				
				String contents = sheet.getCell(0, i).getContents();
				if ((riInt+"日").equals(contents)) {
					sb.append(data + date + "|");
					sb.append(cell1Str1 + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" +cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 + "|" + cell1Str17 + "|" + cell1Str18 + "|" + cell1Str19 + "|" + cell1Str20 + "|0.000000|0.000000|" + cell1Str22 + "|" + cell1Str21 + "|" + cell1Str24 + "|" + cell1Str25 + "|" + cell1Str26 + "|" + cell1Str27 + "");
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十一出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-12
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_12(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=1;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000" , cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000",cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000";
				String contents = sheet.getCell(i, 3).getContents();
				if ((riInt+"日").equals(contents)) {
//					sb.append(cell1Str1 + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" +cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 + "|" + cell1Str17 + "|" + cell1Str18 + "|" + cell1Str19 + "|" + cell1Str20 + "|" + cell1Str21 + "|" + "0.000000" +  "|" + cell1Str23 + "|" + cell1Str24 + "|" + cell1Str25 + "|" + cell1Str26 + "|");
					cell1 = sheet.getCell(i, 4);
					cell2 = sheet.getCell(i, 5);
					cell3 = sheet.getCell(i, 6);
					cell4 = sheet.getCell(i, 7);
					cell5 = sheet.getCell(i, 8);
					cell6 = sheet.getCell(i, 9);
					cell7 = sheet.getCell(i, 10);
					cell8 = sheet.getCell(i, 11);
					cell9 = sheet.getCell(i, 12);
					cell10 = sheet.getCell(i, 13);
					cell11 = sheet.getCell(i, 14);
					cell12 = sheet.getCell(i, 15);
					cell13 = sheet.getCell(i, 16);
					cell14 = sheet.getCell(i, 17);
					cell15 = sheet.getCell(i, 18);
					cell16 = sheet.getCell(i, 19);
					cell17 = sheet.getCell(i, 20);
					if (cell1.getType() == CellType.LABEL) {
						LabelCell cell1_1 = (LabelCell)cell1;
						cell1Str1 = cell1_1.getString();
						if(!cell1Str1.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str1 = String.valueOf(setScale);	
						} else{
							cell1Str1 = "0.000000";
						}
					}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_1 = (NumberCell)cell1;
						cell1Str1 = String.valueOf(cell1_1.getValue());
						if(!cell1Str1.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str1 = String.valueOf(setScale);	
						} else{
							cell1Str1 = "0.000000";
						}
					}
					if(cell1Str1.trim().equals("")){
						cell1Str1 = "0.000000";
					}
					
					if (cell2.getType() == CellType.LABEL) {
						LabelCell cell1_2 = (LabelCell)cell2;
						cell1Str2 = cell1_2.getString();
						if(!cell1Str2.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str2 = String.valueOf(setScale);	
						} else{
							cell1Str2 = "0.000000";
						}
					}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_2 = (NumberCell)cell2;
						cell1Str2 = String.valueOf(cell1_2.getValue());
						if(!cell1Str2.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str2 = String.valueOf(setScale);	
						} else{
							cell1Str2 = "0.000000";
						}
					}
					if(cell1Str2.trim().equals("")){
						cell1Str2 = "0.000000";
					}
					
					if (cell3.getType() == CellType.LABEL) {
						LabelCell cell1_3 = (LabelCell)cell3;
						cell1Str3 = cell1_3.getString();
						if(!cell1Str3.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str3 = String.valueOf(setScale);	
						} else{
							cell1Str3 = "0.000000";
						}
					}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_3 = (NumberCell)cell3;
						cell1Str3 = String.valueOf(cell1_3.getValue());
						if(!cell1Str3.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str3 = String.valueOf(setScale);	
						} else{
							cell1Str3 = "0.000000";
						}
					}
					if(cell1Str3.trim().equals("")){
						cell1Str3 = "0.000000";
					}
					
					if (cell4.getType() == CellType.LABEL) {
						LabelCell cell1_4 = (LabelCell)cell4;
						cell1Str4 = cell1_4.getString();
						if(!cell1Str4.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str4 = String.valueOf(setScale);	
						} else{
							cell1Str4 = "0.000000";
						}
					}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_4 = (NumberCell)cell4;
						cell1Str4 = String.valueOf(cell1_4.getValue());
						if(!cell1Str4.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str4 = String.valueOf(setScale);	
						} else{
							cell1Str4 = "0.000000";
						}
					}
					if(cell1Str4.trim().equals("")){
						cell1Str4 = "0.000000";
					}
					
					if (cell5.getType() == CellType.LABEL) {
						LabelCell cell1_5 = (LabelCell)cell5;
						cell1Str5 = cell1_5.getString();
						if(!cell1Str5.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str5 = String.valueOf(setScale);	
						} else{
							cell1Str5 = "0.000000";
						}
					}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_5 = (NumberCell)cell5;
						cell1Str5 = String.valueOf(cell1_5.getValue());
						if(!cell1Str5.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str5 = String.valueOf(setScale);	
						} else{
							cell1Str5 = "0.000000";
						}
					}
					if(cell1Str5.trim().equals("")){
						cell1Str5 = "0.000000";
					}
					
					if (cell6.getType() == CellType.LABEL) {
						LabelCell cell1_6 = (LabelCell)cell6;
						cell1Str6 = cell1_6.getString();
						if(!cell1Str6.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str6 = String.valueOf(setScale);	
						} else{
							cell1Str6 = "0.000000";
						}
					}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_6 = (NumberCell)cell6;
						cell1Str6 = String.valueOf(cell1_6.getValue());
						if(!cell1Str6.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str6 = String.valueOf(setScale);	
						} else{
							cell1Str6 = "0.000000";
						}
					}
					if(cell1Str6.trim().equals("")){
						cell1Str6 = "0.000000";
					}
					
					if (cell7.getType() == CellType.LABEL) {
						LabelCell cell1_7 = (LabelCell)cell7;
						cell1Str7 = cell1_7.getString();
						if(!cell1Str7.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str7 = String.valueOf(setScale);	
						} else{
							cell1Str7 = "0.000000";
						}
					}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_7 = (NumberCell)cell7;
						cell1Str7 = String.valueOf(cell1_7.getValue());
						if(!cell1Str7.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str7 = String.valueOf(setScale);	
						} else{
							cell1Str7 = "0.000000";
						}
					}
					if(cell1Str7.trim().equals("")){
						cell1Str7 = "0.000000";
					}
					
					if (cell8.getType() == CellType.LABEL) {
						LabelCell cell1_8 = (LabelCell)cell8;
						cell1Str8 = cell1_8.getString();
						if(!cell1Str8.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str8 = String.valueOf(setScale);	
						} else{
							cell1Str8 = "0.000000";
						}
					}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_8 = (NumberCell)cell8;
						cell1Str8 = String.valueOf(cell1_8.getValue());
						if(!cell1Str8.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str8 = String.valueOf(setScale);	
						} else{
							cell1Str8 = "0.000000";
						}
					}
					if(cell1Str8.trim().equals("")){
						cell1Str8 = "0.000000";
					}
					
					if (cell9.getType() == CellType.LABEL) {
						LabelCell cell1_9 = (LabelCell)cell9;
						cell1Str9 = cell1_9.getString();
						if(!cell1Str9.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str9 = String.valueOf(setScale);	
						} else{
							cell1Str9 = "0.000000";
						}
					}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_9 = (NumberCell)cell9;
						cell1Str9 = String.valueOf(cell1_9.getValue());
						if(!cell1Str9.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str9 = String.valueOf(setScale);	
						} else{
							cell1Str9 = "0.000000";
						}
					}
					if(cell1Str9.trim().equals("")){
						cell1Str9 = "0.000000";
					}
					
					if (cell10.getType() == CellType.LABEL) {
						LabelCell cell1_10 = (LabelCell)cell10;
						cell1Str10 = cell1_10.getString();
						if(!cell1Str10.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str10 = String.valueOf(setScale);	
						} else{
							cell1Str10 = "0.000000";
						}
					}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_10 = (NumberCell)cell10;
						cell1Str10 = String.valueOf(cell1_10.getValue());
						if(!cell1Str10.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str10 = String.valueOf(setScale);	
						} else{
							cell1Str10 = "0.000000";
						}
					}
					if(cell1Str10.trim().equals("")){
						cell1Str10 = "0.000000";
					}
					
					if (cell11.getType() == CellType.LABEL) {
						LabelCell cell1_11 = (LabelCell)cell11;
						cell1Str11 = cell1_11.getString();
						if(!cell1Str11.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str11 = String.valueOf(setScale);	
						} else{
							cell1Str11 = "0.000000";
						}
					}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_11 = (NumberCell)cell11;
						cell1Str11 = String.valueOf(cell1_11.getValue());
						if(!cell1Str11.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str11 = String.valueOf(setScale);	
						} else{
							cell1Str11 = "0.000000";
						}
					}
					if(cell1Str11.trim().equals("")){
						cell1Str11 = "0.000000";
					}
					
					if (cell12.getType() == CellType.LABEL) {
						LabelCell cell1_12 = (LabelCell)cell12;
						cell1Str12 = cell1_12.getString();
						if(!cell1Str12.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str12 = String.valueOf(setScale);	
						} else{
							cell1Str12 = "0.000000";
						}
					}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_12 = (NumberCell)cell12;
						cell1Str12 = String.valueOf(cell1_12.getValue());
						if(!cell1Str12.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str12 = String.valueOf(setScale);	
						} else{
							cell1Str12 = "0.000000";
						}
					}
					if(cell1Str12.trim().equals("")){
						cell1Str12 = "0.000000";
					}
					
					if (cell13.getType() == CellType.LABEL) {
						LabelCell cell1_13 = (LabelCell)cell13;
						cell1Str13 = cell1_13.getString();
						if(!cell1Str13.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str13 = String.valueOf(setScale);	
						} else{
							cell1Str13 = "0.000000";
						}
					}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_13 = (NumberCell)cell13;
						cell1Str13 = String.valueOf(cell1_13.getValue());
						if(!cell1Str13.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str13 = String.valueOf(setScale);	
						} else{
							cell1Str13 = "0.000000";
						}
					}
					if(cell1Str13.trim().equals("")){
						cell1Str13 = "0.000000";
					}
					
					if (cell14.getType() == CellType.LABEL) {
						LabelCell cell1_14 = (LabelCell)cell14;
						cell1Str14 = cell1_14.getString();
						if(!cell1Str14.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str14 = String.valueOf(setScale);	
						} else{
							cell1Str14 = "0.000000";
						}
					}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_14 = (NumberCell)cell14;
						cell1Str14 = String.valueOf(cell1_14.getValue());
						if(!cell1Str14.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str14 = String.valueOf(setScale);	
						} else{
							cell1Str14 = "0.000000";
						}
					}
					if(cell1Str14.trim().equals("")){
					}
					
					if (cell15.getType() == CellType.LABEL) {
						LabelCell cell1_15 = (LabelCell)cell15;
						cell1Str15 = cell1_15.getString();
						if(!cell1Str15.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str15 = String.valueOf(setScale);	
						} else{
							cell1Str15 = "0.000000";
						}
					}else if (cell15.getType() == CellType.NUMBER || cell15.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_15 = (NumberCell)cell15;
						cell1Str15 = String.valueOf(cell1_15.getValue());
						if(!cell1Str15.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str15 = String.valueOf(setScale);	
						} else{
							cell1Str15 = "0.000000";
						}
					}
					if(cell1Str15.trim().equals("")){
						cell1Str15 = "0.000000";
					}
					
					if (cell16.getType() == CellType.LABEL) {
						LabelCell cell1_16 = (LabelCell)cell16;
						cell1Str16 = cell1_16.getString();
						if(!cell1Str16.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str16 = String.valueOf(setScale);	
						} else{
							cell1Str16 = "0.000000";
						}
					}else if (cell16.getType() == CellType.NUMBER || cell16.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_16 = (NumberCell)cell16;
						cell1Str16 = String.valueOf(cell1_16.getValue());
						if(!cell1Str16.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str16 = String.valueOf(setScale);	
						} else{
							cell1Str16 = "0.000000";
						}
					}
					if(cell1Str16.trim().equals("")){
						cell1Str16 = "0.000000";
					}
					
					if (cell17.getType() == CellType.LABEL) {
						LabelCell cell1_17 = (LabelCell)cell17;
						cell1Str17 = cell1_17.getString();
						if(!cell1Str17.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str17).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str17 = String.valueOf(setScale);	
						} else{
							cell1Str17 = "0.000000";
						}
					}else if (cell17.getType() == CellType.NUMBER || cell17.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_17 = (NumberCell)cell17;
						cell1Str17 = String.valueOf(cell1_17.getValue());
						if(!cell1Str17.trim().equals("")){
							BigDecimal setScale = new BigDecimal(cell1Str17).setScale(6, BigDecimal.ROUND_HALF_UP);
							cell1Str17 = String.valueOf(setScale);	
						} else{
							cell1Str17 = "0.000000";
						}
					}
					if(cell1Str17.trim().equals("")){
						cell1Str17 = "0.000000";
					}
					
					/*if (cell1.getType() == CellType.LABEL) {
						LabelCell cell1_1 = (LabelCell)cell1;
						cell1Str1 = cell1_1.getString();
					}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_1 = (NumberCell)cell1;
						cell1Str1 = String.valueOf(cell1_1.getValue());
					}
					if (cell2.getType() == CellType.LABEL) {
						LabelCell cell1_2 = (LabelCell)cell2;
						cell1Str2 = cell1_2.getString();
					}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_2 = (NumberCell)cell2;
						cell1Str2 = String.valueOf(cell1_2.getValue());
					}
					if (cell3.getType() == CellType.LABEL) {
						LabelCell cell1_3 = (LabelCell)cell3;
						cell1Str3 = cell1_3.getString();
					}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_3 = (NumberCell)cell3;
						cell1Str3 = String.valueOf(cell1_3.getValue());
					}
					if (cell4.getType() == CellType.LABEL) {
						LabelCell cell1_4 = (LabelCell)cell4;
						cell1Str4 = cell1_4.getString();
					}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_4 = (NumberCell)cell4;
						cell1Str4 = String.valueOf(cell1_4.getValue());
					}
					if (cell5.getType() == CellType.LABEL) {
						LabelCell cell1_5 = (LabelCell)cell5;
						cell1Str5 = cell1_5.getString();
					}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_5 = (NumberCell)cell5;
						cell1Str5 = String.valueOf(cell1_5.getValue());
					}
					if (cell6.getType() == CellType.LABEL) {
						LabelCell cell1_6 = (LabelCell)cell6;
						cell1Str6 = cell1_6.getString();
					}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_6 = (NumberCell)cell6;
						cell1Str6 = String.valueOf(cell1_6.getValue());
					}
					if (cell7.getType() == CellType.LABEL) {
						LabelCell cell1_7 = (LabelCell)cell7;
						cell1Str7 = cell1_7.getString();
					}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_7 = (NumberCell)cell7;
						cell1Str7 = String.valueOf(cell1_7.getValue());
					}
					if (cell8.getType() == CellType.LABEL) {
						LabelCell cell1_8 = (LabelCell)cell8;
						cell1Str8 = cell1_8.getString();
					}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_8 = (NumberCell)cell8;
						cell1Str8 = String.valueOf(cell1_8.getValue());
					}
					if (cell9.getType() == CellType.LABEL) {
						LabelCell cell1_9 = (LabelCell)cell9;
						cell1Str9 = cell1_9.getString();
					}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_9 = (NumberCell)cell9;
						cell1Str9 = String.valueOf(cell1_9.getValue());
					}
					if (cell10.getType() == CellType.LABEL) {
						LabelCell cell1_10 = (LabelCell)cell10;
						cell1Str10 = cell1_10.getString();
					}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_10 = (NumberCell)cell10;
						cell1Str10 = String.valueOf(cell1_10.getValue());
					}
					if (cell11.getType() == CellType.LABEL) {
						LabelCell cell1_11 = (LabelCell)cell11;
						cell1Str11 = cell1_11.getString();
					}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_11 = (NumberCell)cell11;
						cell1Str11 = String.valueOf(cell1_11.getValue());
					}
					if (cell12.getType() == CellType.LABEL) {
						LabelCell cell1_12 = (LabelCell)cell12;
						cell1Str12 = cell1_12.getString();
					}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_12 = (NumberCell)cell12;
						cell1Str12 = String.valueOf(cell1_12.getValue());
					}
					if (cell13.getType() == CellType.LABEL) {
						LabelCell cell1_13 = (LabelCell)cell13;
						cell1Str13 = cell1_13.getString();
					}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_13 = (NumberCell)cell13;
						cell1Str13 = String.valueOf(cell1_13.getValue());
					}
					if (cell14.getType() == CellType.LABEL) {
						LabelCell cell1_14 = (LabelCell)cell14;
						cell1Str14 = cell1_14.getString();
					}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_14 = (NumberCell)cell14;
						cell1Str14 = String.valueOf(cell1_14.getValue());
					}
					if (cell15.getType() == CellType.LABEL) {
						LabelCell cell1_15 = (LabelCell)cell15;
						cell1Str15 = cell1_15.getString();
					}else if (cell15.getType() == CellType.NUMBER || cell15.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_15 = (NumberCell)cell15;
						cell1Str15 = String.valueOf(cell1_15.getValue());
					}
					if (cell16.getType() == CellType.LABEL) {
						LabelCell cell1_16 = (LabelCell)cell16;
						cell1Str16 = cell1_16.getString();
					}else if (cell16.getType() == CellType.NUMBER || cell16.getType() == CellType.NUMBER_FORMULA ) {
						NumberCell cell1_16 = (NumberCell)cell16;
						cell1Str16 = String.valueOf(cell1_16.getValue());
					}*/
					/*NumberCell cell1_1 = (NumberCell)cell1;
					double cell1Str1 = cell1_1.getValue();
					NumberCell cell1_2 = (NumberCell)cell2;
					double cell1Str2 = cell1_2.getValue();
					NumberCell cell1_3 = (NumberCell)cell3;
					double cell1Str3 = cell1_3.getValue();
					NumberCell cell1_4 = (NumberCell)cell4;
					double cell1Str4 = cell1_4.getValue();
					NumberCell cell1_5 = (NumberCell)cell5;
					double cell1Str5 = cell1_5.getValue();
					NumberCell cell1_6 = (NumberCell)cell6;
					double cell1Str6 = cell1_6.getValue();
					NumberCell cell1_7 = (NumberCell)cell7;
					double cell1Str7 = cell1_7.getValue();
					NumberCell cell1_8 = (NumberCell)cell8;
					double cell1Str8 = cell1_8.getValue();
					NumberCell cell1_9 = (NumberCell)cell9;
					double cell1Str9 = cell1_9.getValue();
					NumberCell cell1_10 = (NumberCell)cell10;
					double cell1Str10 = cell1_10.getValue();
					NumberCell cell1_11 = (NumberCell)cell11;
					double cell1Str11 = cell1_11.getValue();	
					NumberCell cell1_12 = (NumberCell)cell12;
					double cell1Str12 = cell1_12.getValue();
					NumberCell cell1_13 = (NumberCell)cell13;
					double cell1Str13 = cell1_13.getValue();
					NumberCell cell1_14 = (NumberCell)cell14;
					double cell1Str14 = cell1_14.getValue();
					NumberCell cell1_15 = (NumberCell)cell15;
					double cell1Str15 = cell1_15.getValue();
					NumberCell cell1_16 = (NumberCell)cell16;
					double cell1Str16 = cell1_16.getValue();*/
					
					sb.append(data + date + "|");
					sb.append(cell1Str1+"|");
					sb.append(cell1Str2+"|");
					sb.append(cell1Str3+"|");
					sb.append(cell1Str4+"|");
					sb.append(cell1Str5+"|");
					sb.append(cell1Str6+"|");
					sb.append(cell1Str8+"|");
					sb.append(cell1Str9+"|");
					sb.append(cell1Str10+"|");
					sb.append(cell1Str11+"|");
					sb.append("0.000000|0.000000|");
					sb.append(cell1Str14+"|");
					sb.append(cell1Str12+"|");
					sb.append(cell1Str15+"|");
					sb.append(cell1Str16+"|" );
					sb.append(cell1Str17 );
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十二出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	/**
	 * 表1-13
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_13(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6 ;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=5;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				/*if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
				}
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
				}
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
				}
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
				}
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
				}
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
				}*/
				/*NumberCell cell1_1 = (NumberCell)cell1;
				double cell1Str1 = cell1_1.getValue();
				NumberCell cell1_2 = (NumberCell)cell2;
				double cell1Str2 = cell1_2.getValue();
				NumberCell cell1_3 = (NumberCell)cell3;
				double cell1Str3 = cell1_3.getValue();
				NumberCell cell1_4 = (NumberCell)cell4;
				double cell1Str4 = cell1_4.getValue();
				NumberCell cell1_5 = (NumberCell)cell5;
				double cell1Str5 = cell1_5.getValue();
				NumberCell cell1_6 = (NumberCell)cell6;
				double cell1Str6 = cell1_6.getValue();*/
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4  + "|" + cell1Str5  + "|" + cell1Str6 ;
				if ((riInt+"日").equals(contents)) {
					sb.append(data + date + "|");
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十三出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-14
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_14(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000", cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000", cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				cell10 = sheet.getCell(10, i);
				cell11 = sheet.getCell(11, i);
				cell12 = sheet.getCell(12, i);
				cell13 = sheet.getCell(13, i);
				cell14 = sheet.getCell(14, i);
				cell15 = sheet.getCell(15, i);
				cell16 = sheet.getCell(16, i);
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				if (cell7.getType() == CellType.LABEL) {
					LabelCell cell1_7 = (LabelCell)cell7;
					cell1Str7 = cell1_7.getString();
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_7 = (NumberCell)cell7;
					cell1Str7 = String.valueOf(cell1_7.getValue());
					if(!cell1Str7.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0.000000";
					}
				}
				if(cell1Str7.trim().equals("")){
					cell1Str7 = "0.000000";
				}
				
				if (cell8.getType() == CellType.LABEL) {
					LabelCell cell1_8 = (LabelCell)cell8;
					cell1Str8 = cell1_8.getString();
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_8 = (NumberCell)cell8;
					cell1Str8 = String.valueOf(cell1_8.getValue());
					if(!cell1Str8.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0.000000";
					}
				}
				if(cell1Str8.trim().equals("")){
					cell1Str8 = "0.000000";
				}
				
				if (cell9.getType() == CellType.LABEL) {
					LabelCell cell1_9 = (LabelCell)cell9;
					cell1Str9 = cell1_9.getString();
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_9 = (NumberCell)cell9;
					cell1Str9 = String.valueOf(cell1_9.getValue());
					if(!cell1Str9.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0.000000";
					}
				}
				if(cell1Str9.trim().equals("")){
					cell1Str9 = "0.000000";
				}
				
				if (cell10.getType() == CellType.LABEL) {
					LabelCell cell1_10 = (LabelCell)cell10;
					cell1Str10 = cell1_10.getString();
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_10 = (NumberCell)cell10;
					cell1Str10 = String.valueOf(cell1_10.getValue());
					if(!cell1Str10.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = "0.000000";
					}
				}
				if(cell1Str10.trim().equals("")){
					cell1Str10 = "0.000000";
				}
				
				if (cell11.getType() == CellType.LABEL) {
					LabelCell cell1_11 = (LabelCell)cell11;
					cell1Str11 = cell1_11.getString();
					if(!cell1Str11.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str11 = String.valueOf(setScale);	
					} else{
						cell1Str11 = "0.000000";
					}
				}else if (cell11.getType() == CellType.NUMBER || cell11.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_11 = (NumberCell)cell11;
					cell1Str11 = String.valueOf(cell1_11.getValue());
					if(!cell1Str11.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str11).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str11 = String.valueOf(setScale);	
					} else{
						cell1Str11 = "0.000000";
					}
				}
				if(cell1Str11.trim().equals("")){
					cell1Str11 = "0.000000";
				}
				
				if (cell12.getType() == CellType.LABEL) {
					LabelCell cell1_12 = (LabelCell)cell12;
					cell1Str12 = cell1_12.getString();
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}else if (cell12.getType() == CellType.NUMBER || cell12.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_12 = (NumberCell)cell12;
					cell1Str12 = String.valueOf(cell1_12.getValue());
					if(!cell1Str12.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str12).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str12 = String.valueOf(setScale);	
					} else{
						cell1Str12 = "0.000000";
					}
				}
				if(cell1Str12.trim().equals("")){
					cell1Str12 = "0.000000";
				}
				
				if (cell13.getType() == CellType.LABEL) {
					LabelCell cell1_13 = (LabelCell)cell13;
					cell1Str13 = cell1_13.getString();
					if(!cell1Str13.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str13 = String.valueOf(setScale);	
					} else{
						cell1Str13 = "0.000000";
					}
				}else if (cell13.getType() == CellType.NUMBER || cell13.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_13 = (NumberCell)cell13;
					cell1Str13 = String.valueOf(cell1_13.getValue());
					if(!cell1Str13.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str13).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str13 = String.valueOf(setScale);	
					} else{
						cell1Str13 = "0.000000";
					}
				}
				if(cell1Str13.trim().equals("")){
					cell1Str13 = "0.000000";
				}
				
				if (cell14.getType() == CellType.LABEL) {
					LabelCell cell1_14 = (LabelCell)cell14;
					cell1Str14 = cell1_14.getString();
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}else if (cell14.getType() == CellType.NUMBER || cell14.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_14 = (NumberCell)cell14;
					cell1Str14 = String.valueOf(cell1_14.getValue());
					if(!cell1Str14.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str14).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str14 = String.valueOf(setScale);	
					} else{
						cell1Str14 = "0.000000";
					}
				}
				if(cell1Str14.trim().equals("")){
					cell1Str14 = "0.000000";
				}
				
				if (cell15.getType() == CellType.LABEL) {
					LabelCell cell1_15 = (LabelCell)cell15;
					cell1Str15 = cell1_15.getString();
					if(!cell1Str15.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str15 = String.valueOf(setScale);	
					} else{
						cell1Str15 = "0.000000";
					}
				}else if (cell15.getType() == CellType.NUMBER || cell15.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_15 = (NumberCell)cell15;
					cell1Str15 = String.valueOf(cell1_15.getValue());
					if(!cell1Str15.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str15).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str15 = String.valueOf(setScale);	
					} else{
						cell1Str15 = "0.000000";
					}
				}
				if(cell1Str15.trim().equals("")){
					cell1Str15 = "0.000000";
				}
				
				if (cell16.getType() == CellType.LABEL) {
					LabelCell cell1_16 = (LabelCell)cell16;
					cell1Str16 = cell1_16.getString();
					if(!cell1Str16.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str16 = String.valueOf(setScale);	
					} else{
						cell1Str16 = "0.000000";
					}
				}else if (cell16.getType() == CellType.NUMBER || cell16.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_16 = (NumberCell)cell16;
					cell1Str16 = String.valueOf(cell1_16.getValue());
					if(!cell1Str16.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str16).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str16 = String.valueOf(setScale);	
					} else{
						cell1Str16 = "0.000000";
					}
				}
				if(cell1Str16.trim().equals("")){
					cell1Str16 = "0.000000";
				}
				
				
				
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" + cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 ;
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(date + "|");
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十四出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	
	
	/**
	 * 表1-15
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_15(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			String bank_name = sheet.getCell(2, 1).getContents();
			String account_name = sheet.getCell(2, 2).getContents();
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000", cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000", cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);

				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0.000000";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0.000000";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_3 = (NumberCell)cell3;
					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0.000000";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0.000000";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0.000000";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0.000000";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6;
				if ((riInt+"日").equals(contents)) {
					sb.append(data + bank_name + "|" + account_name + "|" + date + "|");
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十五出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-16
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_16(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=5;
			while(true) {
				String cell1Str1 = "0", cell1Str2 = "0.000000", cell1Str3 = "0", cell1Str4 = "0.000000", cell1Str5 = "0", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000", cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000", cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
//				cell5 = sheet.getCell(5, i);
//				cell6 = sheet.getCell(6, i);

				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_1 = (NumberCell)cell1;
//					cell1Str1 = String.valueOf(cell1_1.getValue());
					cell1Str1 = cell1.getContents();
					if(!cell1Str1.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0.000000";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0.000000";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_3 = (NumberCell)cell3;
//					cell1Str3 = String.valueOf(cell1_3.getValue());
					cell1Str3 = cell3.getContents();
					if(!cell1Str3.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0.000000";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0.000000";
				}
				
				/*if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_5 = (NumberCell)cell5;
//					cell1Str5 = String.valueOf(cell1_5.getValue());
					cell1Str5 = cell5.getContents();
					if(!cell1Str5.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0";
				}
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0.000000";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0.000000";
				}*/
				
				String contents = sheet.getCell(0, i).getContents();
//				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 ;
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 ;
				if ((riInt+"日").equals(contents)) {
					sb.append(data + date + "|");
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
//					writeTxtFile(new String(sb), new File(pathName));
					method2(pathName, sb.toString());
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十六出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-17
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_17(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=4;
			while(true) {
				String cell1Str1 = "0.000000", cell1Str2 = "0.000000", cell1Str3 = "0.000000", cell1Str4 = "0.000000", cell1Str5 = "0.000000", cell1Str6 = "0.000000", cell1Str7 = "0.000000", cell1Str8 = "0.000000", cell1Str9 = "0.000000", cell1Str10 = "0.000000", cell1Str11 = "0.000000", cell1Str12 = "0.000000", cell1Str13 = "0.000000", cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000";
				try {
					cell6 = sheet.getCell(0, i);
				} catch (Exception e) {
					log.error("该行为空" + e.getMessage());
					break;
				}
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				try {
					/*if (cell6.getContents().equals("")) {
						break;
					}*/
					System.out.println(cell6.getContents());
				} catch (Exception e) {
					log.error("该行为空" + e.getMessage());
					break;
				}finally{
				}
				
				if (cell1.getType() == CellType.LABEL) {
					cell1Str6 = cell6.getContents();
					if(!cell1Str1.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_6 = (NumberCell)cell6;
					cell1Str6 = String.valueOf(cell1_6.getValue());
					if(!cell1Str6.trim().equals("")){
					} else{
						cell1Str6 = "0";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0";
				}
				
				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_1 = (NumberCell)cell1;
					cell1Str1 = String.valueOf(cell1_1.getValue());
					if(!cell1Str1.trim().equals("")){
					} else{
						cell1Str1 = "0";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
					} else{
						cell1Str2 = "0";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
					} else{
						cell1Str2 = "0";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
					} else{
						cell1Str3 = "0";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
					cell1Str3 =  cell3.getContents();
//					NumberCell cell1_3 = (NumberCell)cell3;
//					cell1Str3 = String.valueOf(cell1_3.getValue());
					if(!cell1Str3.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
					} else{
						cell1Str4 = "0";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_4 = (NumberCell)cell4;
					cell1Str4 = String.valueOf(cell1_4.getValue());
					if(!cell1Str4.trim().equals("")){
					} else{
						cell1Str4 = "0";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
					} else{
						cell1Str5 = "0";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_5 = (NumberCell)cell5;
					cell1Str5 = String.valueOf(cell1_5.getValue());
					if(!cell1Str5.trim().equals("")){
					} else{
						cell1Str5 = "0";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str6  + "|" +cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 ;
				if (!str1.equals("0")) {
//					sb.append(data);
					sb.append(data+date+"|"+str1 +"\n");
//					break;
				}else{
				}
				i++;
			}
			String pathName = path + name + fixed + "_" + date + ".txt";
			writeTxtFile(new String(sb), new File(pathName));
			book.close();
		} catch (Exception e) {
			log.error("报表十七出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	
	/**
	 * 表1-18
	 * @param data 第一行数据
	 * @param readFile 读取文件目录
	 * @param path 放置文件目录
	 * @param name 放置文件的文件名
	 * @param date 文件日期
	 * @param fixed 文件中的固定值
	 * @return true成功	false失败
	 */
	public static boolean readExcel_1_18(String data, String readFile, String path, String name, String date, String fixed) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		StringBuffer sb = new StringBuffer();
		int i;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			String bank_name = sheet.getCell(2, 1).getContents();
			String account_name = sheet.getCell(2, 2).getContents();
			i=4;
			while(true) {
				String cell1Str1 = "0", cell1Str2 = "0", cell1Str3 = "0", cell1Str4 = "0", cell1Str5 = "0", cell1Str6 = "0", cell1Str7 = "0", cell1Str8 = "0", cell1Str9 = "0", cell1Str10 = " ", cell1Str11 = "0", cell1Str12 = "0", cell1Str13 = "0", cell1Str14 = "0.000000", cell1Str15 = "0.000000", cell1Str16 = "0.000000", cell1Str17 = "0.000000";
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				cell10 = sheet.getCell(10, i);
//				cell11 = sheet.getCell(11, i);

				if (cell1.getType() == CellType.LABEL) {
					LabelCell cell1_1 = (LabelCell)cell1;
					cell1Str1 = cell1_1.getString();
					if(!cell1Str1.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0";
					}
				}else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_1 = (NumberCell)cell1;
//					cell1Str1 = String.valueOf(cell1_1.getValue());
					cell1Str1 = cell1.getContents();
					if(!cell1Str1.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str1).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str1 = String.valueOf(setScale);	
					} else{
						cell1Str1 = "0";
					}
				}
				if(cell1Str1.trim().equals("")){
					cell1Str1 = "0";
				}
				
				if (cell2.getType() == CellType.LABEL) {
					LabelCell cell1_2 = (LabelCell)cell2;
					cell1Str2 = cell1_2.getString();
					if(!cell1Str2.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0";
					}
				}else if (cell2.getType() == CellType.NUMBER || cell2.getType() == CellType.NUMBER_FORMULA ) {
					NumberCell cell1_2 = (NumberCell)cell2;
					cell1Str2 = String.valueOf(cell1_2.getValue());
					if(!cell1Str2.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str2).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str2 = String.valueOf(setScale);	
					} else{
						cell1Str2 = "0";
					}
				}
				if(cell1Str2.trim().equals("")){
					cell1Str2 = "0";
				}
				
				if (cell3.getType() == CellType.LABEL) {
					LabelCell cell1_3 = (LabelCell)cell3;
					cell1Str3 = cell1_3.getString();
					if(!cell1Str3.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0";
					}
				}else if (cell3.getType() == CellType.NUMBER || cell3.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_3 = (NumberCell)cell3;
//					cell1Str3 = String.valueOf(cell1_3.getValue());
					cell1Str3 = cell3.getContents();
					if(!cell1Str3.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str3).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str3 = String.valueOf(setScale);	
					} else{
						cell1Str3 = "0";
					}
				}
				if(cell1Str3.trim().equals("")){
					cell1Str3 = "0";
				}
				
				if (cell4.getType() == CellType.LABEL) {
					LabelCell cell1_4 = (LabelCell)cell4;
					cell1Str4 = cell1_4.getString();
					if(!cell1Str4.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0";
					}
				}else if (cell4.getType() == CellType.NUMBER || cell4.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_4 = (NumberCell)cell4;
//					cell1Str4 = String.valueOf(cell1_4.getValue());
					cell1Str4 = cell4.getContents();
					if(!cell1Str4.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str4).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str4 = String.valueOf(setScale);	
					} else{
						cell1Str4 = "0";
					}
				}
				if(cell1Str4.trim().equals("")){
					cell1Str4 = "0";
				}
				
				if (cell5.getType() == CellType.LABEL) {
					LabelCell cell1_5 = (LabelCell)cell5;
					cell1Str5 = cell1_5.getString();
					if(!cell1Str5.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0";
					}
				}else if (cell5.getType() == CellType.NUMBER || cell5.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_5 = (NumberCell)cell5;
//					cell1Str5 = String.valueOf(cell1_5.getValue());
					cell1Str5 = cell5.getContents();
					if(!cell1Str5.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str5).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str5 = String.valueOf(setScale);	
					} else{
						cell1Str5 = "0";
					}
				}
				if(cell1Str5.trim().equals("")){
					cell1Str5 = "0";
				}
				
				if (cell6.getType() == CellType.LABEL) {
					LabelCell cell1_6 = (LabelCell)cell6;
					cell1Str6 = cell1_6.getString();
					if(!cell1Str6.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0";
					}
				}else if (cell6.getType() == CellType.NUMBER || cell6.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_6 = (NumberCell)cell6;
//					cell1Str6 = String.valueOf(cell1_6.getValue());
					cell1Str6 = cell6.getContents();
					if(!cell1Str6.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str6).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str6 = String.valueOf(setScale);	
					} else{
						cell1Str6 = "0";
					}
				}
				if(cell1Str6.trim().equals("")){
					cell1Str6 = "0";
				}
				

				if (cell7.getType() == CellType.LABEL) {
					LabelCell cell1_7 = (LabelCell)cell7;
					cell1Str7 = cell1_7.getString();
					if(!cell1Str7.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0";
					}
				}else if (cell7.getType() == CellType.NUMBER || cell7.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_7 = (NumberCell)cell7;
//					cell1Str7 = String.valueOf(cell1_7.getValue());
					cell1Str7 = cell7.getContents();
					if(!cell1Str7.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str7).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str7 = String.valueOf(setScale);	
					} else{
						cell1Str7 = "0";
					}
				}
				if(cell1Str7.trim().equals("")){
					cell1Str7 = "0";
				}
				
				if (cell8.getType() == CellType.LABEL) {
					LabelCell cell1_8 = (LabelCell)cell8;
					cell1Str8 = cell1_8.getString();
					if(!cell1Str8.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0";
					}
				}else if (cell8.getType() == CellType.NUMBER || cell8.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_8 = (NumberCell)cell8;
//					cell1Str8 = String.valueOf(cell1_8.getValue());
					cell1Str8 = cell8.getContents();
					if(!cell1Str8.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str8).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str8 = String.valueOf(setScale);	
					} else{
						cell1Str8 = "0";
					}
				}
				if(cell1Str8.trim().equals("")){
					cell1Str8 = "0";
				}
				
				if (cell9.getType() == CellType.LABEL) {
					LabelCell cell1_9 = (LabelCell)cell9;
					cell1Str9 = cell1_9.getString();
					if(!cell1Str9.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0";
					}
				}else if (cell9.getType() == CellType.NUMBER || cell9.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_9 = (NumberCell)cell9;
//					cell1Str9 = String.valueOf(cell1_9.getValue());
					cell1Str9 = cell9.getContents();
					if(!cell1Str9.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str9).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str9 = String.valueOf(setScale);	
					} else{
						cell1Str9 = "0";
					}
				}
				if(cell1Str9.trim().equals("")){
					cell1Str9 = "0";
				}
				
				if (cell10.getType() == CellType.LABEL) {
					LabelCell cell1_10 = (LabelCell)cell10;
					cell1Str10 = cell1_10.getString();
					if(!cell1Str10.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = " ";
					}
				}else if (cell10.getType() == CellType.NUMBER || cell10.getType() == CellType.NUMBER_FORMULA ) {
//					NumberCell cell1_10 = (NumberCell)cell10;
//					cell1Str10 = String.valueOf(cell1_10.getValue());
					cell1Str10 = cell10.getContents();
					if(!cell1Str10.trim().equals("")){
//						BigDecimal setScale = new BigDecimal(cell1Str10).setScale(6, BigDecimal.ROUND_HALF_UP);
//						cell1Str10 = String.valueOf(setScale);	
					} else{
						cell1Str10 = " ";
					}
				}
				if(cell1Str10.trim().equals("")){
					cell1Str10 = " ";
				}
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" + cell1Str8 + "|" + date + "|" + cell1Str9 + "|" + cell1Str10  ;
				if ((riInt+"日").equals(contents)) {
//					sb.append(data + bank_name + "|" + account_name + "|");
					sb.append(data);
					sb.append(str1);
					String pathName = path + name + fixed + "_" + date + ".txt";
					writeTxtFile(new String(sb), new File(pathName));
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
			log.error("报表十八出错：" + e.getMessage());
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	public static boolean method2(String file, String conent) throws Exception {
		BufferedWriter out = null;
		boolean flag = false;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true),"GB18030"));
//			char[] str = new String(new String(conent+"\r\n").getBytes(), Charset.forName("GB18030")).toCharArray()	;
			out.write(conent+"\r\n");
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
		/*RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			o.write(new String(conent+"\r\n").getBytes("GB18030"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;*/
	}
	public static boolean writeTxtFile(String content, File fileName) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GB18030"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}
}
