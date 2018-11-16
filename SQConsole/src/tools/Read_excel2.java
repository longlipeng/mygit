package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class Read_excel2 {
	public static void main(String[] args) {
			/*Cell cell = null;
			if (cell.getType() == CellType.DATE || cell.getType() == CellType.DATE_FORMULA) {
				DateCell cell1 = (DateCell)cell;

			} else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
				NumberCell cell2 = (NumberCell)cell;
			} else if (cell.getType() == CellType.EMPTY) {
				String contents = cell.getContents();
			} else if (cell.getType() == CellType.LABEL || cell.getType() == CellType.STRING_FORMULA) {

			} else {
				CellType type = cell.getType();
				
			}*/
		/*if (cell1.getType() == CellType.DATE || cell1.getType() == CellType.DATE_FORMULA) {
			DateCell cell11 = (DateCell)cell1;

		} else if (cell1.getType() == CellType.NUMBER || cell1.getType() == CellType.NUMBER_FORMULA) {
			NumberCell cell12 = (NumberCell)cell1;
		} else if (cell1.getType() == CellType.EMPTY) {
			String contents = cell1.getContents();
		} else if (cell1.getType() == CellType.LABEL || cell1.getType() == CellType.STRING_FORMULA) {

		} else {
			CellType type = cell1.getType();
		}*/
		
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号  + 交易日期
			String data = "123456|汇明商务服务有限公司客户备付金|121916795010602|20160902|\n";
			String filestr1 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户备付金信息统计报表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_101_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_1(data,filestr1, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20160902|\n";
			String filestr2 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户备付金出金业务明细表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_102_cmb100_";
			String fixed = "123456";
			String date = "20160803";
			readExcel_1_2(data, filestr2, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期 + 银行名称  + 账户户名 + 账户账号
			String data = "123456|20160902|招商银行上海分行静安寺支行|汇明商务服务有限公司客户备付金|121916795010602|\n";
			String filestr3 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户备付金业务实际出金明细表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_103_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_3(data, filestr3, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20160902|\n";
			String filestr4 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户资金账户转账业务统计表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_104_cmb100_";
			String fixed = "123456";
			String date = "20160814";
			readExcel_1_4(data, filestr4, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20160902|\n";
			String filestr5 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户资金账户余额统计表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_105_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_5(data, filestr5, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
			String data = "123456|汇明商务服务有限公司客户备付金|121916795010602|20160902|\n";
			String filestr6 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构招商银行特殊业务明细表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_106_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_6(data, filestr6, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码 + 交易日期
			String data = "123456|20160902|\n";
			String filestr7 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构现金购卡业务统计表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_107_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_7(data, filestr7, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码 + 交易日期
			String data = "123456|20160902|\n";
			String filestr8 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构预付卡现金赎回业务统计表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_108_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_8(data, filestr8, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
			String data = "123456|汇明商务服务有限公司客户备付金|121916795010602|20160902|\n";
			String filestr9 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构招商银行客户备付金业务未达账项统计表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_109_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_9(data, filestr9, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 备付金银行账户户名  + 备付金银行账户账号 + 交易日期
			String data = "123456|汇明商务服务有限公司客户备付金|121916795010602|20160902|\n";
			String filestr10 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构招商银行客户备付金业务未达账项分析表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_110_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_10(data, filestr10, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20160902|\n";
			String filestr11 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户资金账户余额变动调节表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_111_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_11(data, filestr11, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20160902|\n";
			String filestr12 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\支付机构客户资金账户余额试算表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_112_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_12(data, filestr12, path, name, date, fixed);
		}*/
		/*{
			//由银行提供支付机构代码  + 交易日期
			String data = "123456|20160902|\n";
			String filestr12 = "C:\\Users\\Li\\Desktop\\上传文件\\201608\\预付卡发行企业备付金账户中售卡押金统计表_0831.xls";
			String path = "C:\\Users\\Li\\Desktop\\";
			String name = "PMS_113_cmb100_";
			String fixed = "123456";
			String date = "20160801";
			readExcel_1_13(data, filestr12, path, name, date, fixed);
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
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=9;
			while(true) {
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(4, i);
				cell4 = sheet.getCell(5, i);
				cell5 = sheet.getCell(7, i);
				cell6 = sheet.getCell(8, i);
				cell7 = sheet.getCell(10, i);
				cell8 = sheet.getCell(11, i);
				cell9 = sheet.getCell(12, i);
				cell10 = sheet.getCell(14, i);
				NumberCell cell1_1 = (NumberCell)cell1;
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
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + "0|0|" + cell1Str3 + "|" + cell1Str4 + "|" + "0|0|" + cell1Str5 + "|" + cell1Str6 + "|" + "0|0|" + cell1Str7 + "|" + cell1Str8 + "|" + cell1Str9 + "|" + "0|0|" + cell1Str10+ "\n";
				if ((riInt+"日").equals(contents)) {
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
			e.printStackTrace();
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
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				cell7 = sheet.getCell(7, i);
				cell8 = sheet.getCell(8, i);
				cell9 = sheet.getCell(9, i);
				/*String cell1Str1 = cell1.getContents();
				String cell1Str2 = cell2.getContents();
				String cell1Str3 = cell3.getContents();
				String cell1Str4 = cell4.getContents();
				String cell1Str5 = cell5.getContents();
				String cell1Str6 = cell6.getContents();
				String cell1Str7 = cell7.getContents();
				String cell1Str8 = cell8.getContents();
				String cell1Str9 = cell9.getContents();*/
				NumberCell cell1_1 = (NumberCell)cell1;
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
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" + cell1Str8 + "|" + cell1Str9 + "\n";
				if ((riInt+"日").equals(contents)) {
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
		int i;
		int j;
		Cell cell1 ;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=1;
			while(true) {
				String contents = sheet.getCell(0, i).getContents();
				if ("合计".equals(contents.trim())) {
					j = 1;
					while(true) {
						cell1 = sheet.getCell(j, i);
						NumberCell cell1_1 = (NumberCell)cell1;
						double cell1Str1 = cell1_1.getValue();
						String contents1 = sheet.getCell(j, 3).getContents();
						if ((riInt + "日").equals(contents1)) {
							sb.append(data);
							sb.append(cell1Str1 + "|\n");
							String pathName = path + name + fixed + "_" + date + ".txt";
							writeTxtFile(new String(sb).trim(), new File(pathName));
							book.close();
							return true;
						}
						j++;
					}
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
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
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
//				String cell1Str1 = cell1.getContents();
//				String cell1Str2 = cell2.getContents();
//				String cell1Str3 = cell3.getContents();
				NumberCell cell1_1 = (NumberCell)cell1;
				double cell1Str1 = cell1_1.getValue();
				
				NumberCell cell1_2 = (NumberCell)cell2;
				double cell1Str2 = cell1_2.getValue();
				
				NumberCell cell1_3 = (NumberCell)cell3;
				double cell1Str3 = cell1_3.getValue();
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|0|" + "\n";
				if ((riInt + "日").equals(contents)) {
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
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
//				String cell1Str1 = cell1.getContents();
//				String cell1Str2 = cell2.getContents();
//				String cell1Str3 = cell3.getContents();
//				String cell1Str4 = cell4.getContents();
//				String cell1Str5 = cell5.getContents();
//				String cell1Str6 = cell6.getContents();
				
				NumberCell cell1_1 = (NumberCell)cell1;
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
				
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "\n";
				if ((riInt + "日").equals(contents)) {
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
	public static boolean readExcel_1_6(String data, String readFile, String path, String name, String date, String fixed ) {
		int riInt = Integer.parseInt(date.substring(6, 8));
		boolean flag = false;
		Sheet sheet;
		Workbook book = null;
		int i;
//		int j;
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28;
		StringBuffer sb = new StringBuffer();
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=1;
			while(true) {
				String contents = sheet.getCell(i, 4).getContents();
				if (contents.equals(riInt + "日")) {
					sb.append(data);
					cell1 = sheet.getCell(i, 6);
					cell2 = sheet.getCell(i, 7);
					cell3 = sheet.getCell(i, 8);
					cell4 = sheet.getCell(i, 9);
					cell5 = sheet.getCell(i, 10);
					cell6 = sheet.getCell(i, 11);
					cell7 = sheet.getCell(i, 12);
					cell8 = sheet.getCell(i, 13);
					cell9 = sheet.getCell(i, 14);
					cell10 = sheet.getCell(i, 15);
					cell11 = sheet.getCell(i, 16);
					cell12 = sheet.getCell(i, 17);
					cell13 = sheet.getCell(i, 18);
					cell14 = sheet.getCell(i, 19);
					cell15 = sheet.getCell(i, 20);
					cell16 = sheet.getCell(i, 21);
					cell17 = sheet.getCell(i, 22);
					cell18 = sheet.getCell(i, 23);
					cell19 = sheet.getCell(i, 24);
					cell20 = sheet.getCell(i, 25);
					cell21 = sheet.getCell(i, 26);
					cell22 = sheet.getCell(i, 27);
					cell23 = sheet.getCell(i, 28);
					cell24 = sheet.getCell(i, 29);
					NumberCell cell1_1 = (NumberCell)cell1;
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
					double cell1Str16 = cell1_16.getValue();
					NumberCell cell1_17 = (NumberCell)cell17;
					double cell1Str17 = cell1_17.getValue();
					NumberCell cell1_18 = (NumberCell)cell18;
					double cell1Str18 = cell1_18.getValue();
					NumberCell cell1_19 = (NumberCell)cell19;
					double cell1Str19 = cell1_19.getValue();
					NumberCell cell1_20 = (NumberCell)cell20;
					double cell1Str20 = cell1_20.getValue();
					NumberCell cell1_21 = (NumberCell)cell21;
					double cell1Str21 = cell1_21.getValue();
					NumberCell cell1_22 = (NumberCell)cell22;
					double cell1Str22 = cell1_22.getValue();
					NumberCell cell1_23 = (NumberCell)cell23;
					double cell1Str23 = cell1_23.getValue();
					NumberCell cell1_24 = (NumberCell)cell24;
					double cell1Str24 = cell1_24.getValue();
					sb.append(cell1Str1 + "|");
					sb.append(cell1Str2 + "|");
					sb.append(cell1Str3 + "|");
					sb.append(cell1Str4 + "|");
					sb.append(cell1Str5 + "|");
					sb.append(cell1Str6 + "|");
					sb.append(cell1Str7 + "|");
					sb.append(cell1Str9 + "|");
					sb.append(cell1Str8 + "|");
					sb.append(cell1Str24 + "|");
					sb.append(cell1Str11 + "|");
					sb.append(cell1Str12 + "|");
					sb.append(cell1Str13 + "|");
					sb.append(cell1Str14 + "|");
					sb.append(cell1Str15 + "|");
					sb.append(cell1Str16 + "|");
					sb.append(cell1Str17 + "|");
					sb.append(cell1Str18 + "|");
					sb.append(cell1Str19 + "|");
					sb.append(cell1Str20 + "|");
					sb.append(cell1Str22 + "|");
					sb.append(cell1Str23 + "|");
					sb.append(cell1Str21 + "|");
					sb.append(cell1Str21 + "|");
					sb.append(cell1Str24 + "|");
//					sb.append(contents10 + "|" + contents1 + "|" + contents2 + "|" + contents3 + "|" + contents4 + "|" + contents5 + "|" + contents6 + "|" + contents7 + "|" + contents8 + "|" + contents9 + "|" + contents11 + "|" + contents12 + "|" + contents13 + "|" + contents14 + "|" + contents15 + "|" + contents16 + "|" + contents17 + "|" + contents18 + "|" + contents19 + "|" + contents20 + "|" + contents21 + "|" + contents22 + "|" + contents23 +"|\n");
					
					String pathName = path + name + fixed + "_" + date + ".txt";
					writeTxtFile(new String(sb), new File(pathName));
					break;
				}
				i++;
			}
		} catch (Exception e) {
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
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
//				String cell1Str1 = cell1.getContents();
//				String cell1Str2 = cell2.getContents();
//				String cell1Str3 = cell3.getContents();
//				String cell1Str4 = cell4.getContents();
				NumberCell cell1_1 = (NumberCell)cell1;
				double cell1Str1 = cell1_1.getValue();
				
				NumberCell cell1_2 = (NumberCell)cell2;
				double cell1Str2 = cell1_2.getValue();
				
				NumberCell cell1_3 = (NumberCell)cell3;
				double cell1Str3 = cell1_3.getValue();
				
				NumberCell cell1_4 = (NumberCell)cell4;
				double cell1Str4 = cell1_4.getValue();
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "\n";
				if ((riInt+"日").equals(contents)) {
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
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
//				String cell1Str1 = cell1.getContents();
//				String cell1Str2 = cell2.getContents();
//				String cell1Str3 = cell3.getContents();
//				String cell1Str4 = cell4.getContents();
				NumberCell cell1_1 = (NumberCell)cell1;
				double cell1Str1 = cell1_1.getValue();
				
				NumberCell cell1_2 = (NumberCell)cell2;
				double cell1Str2 = cell1_2.getValue();
				
				NumberCell cell1_3 = (NumberCell)cell3;
				double cell1Str3 = cell1_3.getValue();
				
				NumberCell cell1_4 = (NumberCell)cell4;
				double cell1Str4 = cell1_4.getValue();
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "\n";
				if ((riInt+"日").equals(contents)) {
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
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=1;
			while(true) {
				cell1 = sheet.getCell(i, 6);
				String contents = cell1.getContents();
				if ((riInt+"日").equals(contents)) {
					cell2 = sheet.getCell(i, 7);
					cell3 = sheet.getCell(i, 8);
					cell4 = sheet.getCell(i, 9);
					cell5 = sheet.getCell(i, 10);
					
					NumberCell cell1_2 = (NumberCell)cell2;
					double cell1Str2 = cell1_2.getValue();
					
					NumberCell cell1_3 = (NumberCell)cell3;
					double cell1Str3 = cell1_3.getValue();
					
					NumberCell cell1_4 = (NumberCell)cell4;
					double cell1Str4 = cell1_4.getValue();
					
					NumberCell cell1_5 = (NumberCell)cell5;
					double cell1Str5 = cell1_5.getValue();
					sb.append(data);
					sb.append(cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|\n");
					String pathName = path + name + fixed + "_" + date + ".txt";
					writeTxtFile(new String(sb), new File(pathName));
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
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
			i=6;
			while(true) {
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
				NumberCell cell1_1 = (NumberCell)cell1;
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
				double cell1Str16 = cell1_16.getValue();
				
				NumberCell cell1_17 = (NumberCell)cell17;
				double cell1Str17 = cell1_17.getValue();
				
				NumberCell cell1_18 = (NumberCell)cell18;
				double cell1Str18 = cell1_18.getValue();
				
				NumberCell cell1_19 = (NumberCell)cell19;
				double cell1Str19 = cell1_19.getValue();
				
				NumberCell cell1_20 = (NumberCell)cell20;
				double cell1Str20 = cell1_20.getValue();
				
				NumberCell cell1_21 = (NumberCell)cell21;
				double cell1Str21 = cell1_21.getValue();
				
				NumberCell cell1_22 = (NumberCell)cell22;
				double cell1Str22 = cell1_22.getValue();
				
				NumberCell cell1_23 = (NumberCell)cell23;
				double cell1Str23 = cell1_23.getValue();
				
				NumberCell cell1_24 = (NumberCell)cell24;
				double cell1Str24 = cell1_24.getValue();
				
				String contents = sheet.getCell(0, i).getContents();
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(cell1Str1 + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" +cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 + "|" + cell1Str17 + "|" + cell1Str18 + "|" + cell1Str19 + "|" + cell1Str20 + "|" + cell1Str21 + "|" + cell1Str22 +  "|" + cell1Str23 + "|" + cell1Str24 + "|");
					String pathName = path + name + fixed + "_" + date + ".txt";
					writeTxtFile(new String(sb), new File(pathName));
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
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
		Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24, cell25, cell26;
		try {
			book = Workbook.getWorkbook(new File(readFile));
			// 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheet(0);
			// 获取左上角的单元格//（列，行）
			i=4;
			while(true) {
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
				NumberCell cell1_1 = (NumberCell)cell1;
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
				double cell1Str16 = cell1_16.getValue();
				
				NumberCell cell1_17 = (NumberCell)cell17;
				double cell1Str17 = cell1_17.getValue();
				
				NumberCell cell1_18 = (NumberCell)cell18;
				double cell1Str18 = cell1_18.getValue();
				
				NumberCell cell1_19 = (NumberCell)cell19;
				double cell1Str19 = cell1_19.getValue();
				
				NumberCell cell1_20 = (NumberCell)cell20;
				double cell1Str20 = cell1_20.getValue();
				
				NumberCell cell1_21 = (NumberCell)cell21;
				double cell1Str21 = cell1_21.getValue();
				
				NumberCell cell1_22 = (NumberCell)cell22;
				double cell1Str22 = cell1_22.getValue();
				
				NumberCell cell1_23 = (NumberCell)cell23;
				double cell1Str23 = cell1_23.getValue();
				
				NumberCell cell1_24 = (NumberCell)cell24;
				double cell1Str24 = cell1_24.getValue();
				
				NumberCell cell1_25 = (NumberCell)cell25;
				double cell1Str25 = cell1_25.getValue();
				
				NumberCell cell1_26 = (NumberCell)cell26;
				double cell1Str26 = cell1_26.getValue();
				
				String contents = sheet.getCell(0, i).getContents();
				if ((riInt+"日").equals(contents)) {
					sb.append(data);
					sb.append(cell1Str1 + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" +cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 + "|" + cell1Str17 + "|" + cell1Str18 + "|" + cell1Str19 + "|" + cell1Str20 + "|0|0|0|" + cell1Str21 +  "|" + cell1Str23 + "|" + cell1Str24 + "|" + cell1Str25 + "|" + cell1Str26 + "|");
					String pathName = path + name + fixed + "_" + date + ".txt";
					writeTxtFile(new String(sb), new File(pathName));
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
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
				String contents = sheet.getCell(i, 3).getContents();
				if ((riInt+"日").equals(contents)) {
//					sb.append(cell1Str1 + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4 + "|" + cell1Str5 + "|" + cell1Str6 + "|" + cell1Str7 + "|" +cell1Str8 + "|" + cell1Str9 + "|" + cell1Str10 + "|" + cell1Str11 + "|" + cell1Str12 + "|" + cell1Str13 + "|" + cell1Str14 + "|" + cell1Str15 + "|" + cell1Str16 + "|" + cell1Str17 + "|" + cell1Str18 + "|" + cell1Str19 + "|" + cell1Str20 + "|" + cell1Str21 + "|" + "0" +  "|" + cell1Str23 + "|" + cell1Str24 + "|" + cell1Str25 + "|" + cell1Str26 + "|");
					cell1 = sheet.getCell(i, 4);//第一行
					cell2 = sheet.getCell(i, 5);//第二行
					cell3 = sheet.getCell(i, 6);//第三行
					cell4 = sheet.getCell(i, 7);//第四行
					cell5 = sheet.getCell(i, 8);//第五行
					cell6 = sheet.getCell(i, 8);//第六行
					cell7 = sheet.getCell(i, 9);//第七行
												//第八行
					cell8 = sheet.getCell(i, 11);//第九行
					cell9 = sheet.getCell(i, 12);//第十行
					cell10 = sheet.getCell(i, 13);//第十一行
					cell11 = sheet.getCell(i, 14);//第十二行
					cell12 = sheet.getCell(i, 15);//第十三行
												//第十四行
					cell13 = sheet.getCell(i, 17);//第十五行
					cell14 = sheet.getCell(i, 18);//第十六行
					cell15 = sheet.getCell(i, 19);//第十七行
					NumberCell cell1_1 = (NumberCell)cell1;
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
					sb.append(data);
					sb.append(cell1Str1+"|");
					sb.append(cell1Str2+"|");
					sb.append(cell1Str3+"|");
					sb.append(cell1Str4+"|");
					sb.append(cell1Str5+"|");
					sb.append(cell1Str6+"|");
					sb.append(cell1Str7+"|");
					sb.append(cell1Str8+"|");
					sb.append(cell1Str9+"|");
					sb.append(cell1Str10+"|");
					sb.append(cell1Str11+"|");
					sb.append("0|0|0|");
					sb.append(cell1Str12+"|");
					sb.append(cell1Str13+"|");
					sb.append(cell1Str14+"|");
					sb.append(cell1Str15+"|");
					String pathName = path + name + fixed + "_" + date + ".txt";
					writeTxtFile(new String(sb), new File(pathName));
					break;
				}
				i++;
			}
			book.close();
		} catch (Exception e) {
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
				cell1 = sheet.getCell(1, i);
				cell2 = sheet.getCell(2, i);
				cell3 = sheet.getCell(3, i);
				cell4 = sheet.getCell(4, i);
				cell5 = sheet.getCell(5, i);
				cell6 = sheet.getCell(6, i);
				CellType type = cell1.getType();
				NumberCell cell1_1 = (NumberCell)cell1;
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
				String contents = sheet.getCell(0, i).getContents();
				String str1 = cell1Str1  + "|" + cell1Str2 + "|" + cell1Str3 + "|" + cell1Str4  + "|" + cell1Str5  + "|" + cell1Str6 + "\n";
				if ((riInt+"日").equals(contents)) {
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
			e.printStackTrace();
			return flag;
		}
		return true;
	}
	public static boolean writeTxtFile(String content, File fileName) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GBK"));
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
