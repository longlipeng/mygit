package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 银企直连清算报表
 * 
 * File: T80229Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2016-6-6
 * 
 * @version 1.0
 */
public class T80229Action extends ReportBaseAction{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T80229Action.class);
//			if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
	
	private String reportName;
	private String date;
	
	private String bankAccount;
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	protected String genSql() {
		return "";
	}
	
	protected void reportAction() throws Exception {
		if (StringUtil.isNull(date) || StringUtil.isNull(reportName)) {
			writeNoDataMsg("请选择数据");
			return;
		}
		reportType = "EXCEL";
		
//		data = reportCreator.process(genSql(), title);
		/*int yue1 = Integer.parseInt(yue);
		System.out.println("年："+nian+"月："+yue1);
		System.out.println(date);
		System.out.println(reportName);
		c.set(Calendar.YEAR, Integer.parseInt(nian)); 
		c.set(Calendar.MONTH, yue1); 
		int tian = c.getActualMaximum(Calendar.DAY_OF_MONTH);//天数    */	
		String nian = date.substring(0, 4);//20160605
		String yue = date.substring(4, 6);
		String ri = date.substring(6, 8);
		int riInt =Integer.parseInt(ri);
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); // 如果写成年月日的形式的话，要写小d，如："yyyy/MM/dd"
		rightNow.setTime(simpleDate.parse(nian + "/" + yue)); //要计算你想要的月份，改变这里即可
		
		int tian = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String cvsfile = SysParamUtil.getParam(SysParamConstants.CVSFILE);
		String cvsname = SysParamUtil.getParam(SysParamConstants.CVSNAME);
		
		int startri = Integer.parseInt(nian + yue + "01");
		int endri = Integer.parseInt(date);
		System.out.println("月初："+startri +"	选择日期：" + endri);
		String nextMonth = getNextMonth(String.valueOf(startri));
		String beforeDay = getBeforeDay(nextMonth);
		if (reportName.equals("1")) {
			
			System.out.println("1-支付机构客户备付金信息统计报表");
			title = InformationUtil.createTitles("V_", 15);
			data = new Object[tian][15];
			System.err.println("CVS地址："+cvsfile + " CVS名字：" + cvsname);
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat2 = 0.0f;
			float parseFloat3 = 0.0f;
			float parseFloat4 = 0.0f;
			BigDecimal b1 = null;
			BigDecimal b2 = null;
			BigDecimal b3 = null;
			BigDecimal b4 = null;
			BigDecimal b5 = new BigDecimal("0");
			BigDecimal b6 = new BigDecimal("0");
			BigDecimal b7 = new BigDecimal("0");
			BigDecimal b8 = new BigDecimal("0");
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					String str2 = cvsfile + cvsname + j +".csv";
					int j2 = 0 ;
					String j1 = String.valueOf(j).substring(6, 8);
			        j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址1：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine3.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	data[j2][4] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat1 = Float.parseFloat(replace1);
				        	b1 = new BigDecimal(replace1);
				        	b5 = b5.add(b1);
						} catch (Exception e) {
							data[j2][4] = "0.000000";
							e.printStackTrace();
						}
				        try {
				        	String replace2 = sourceStrArray[2].replace("+", "0");
				        	data[j2][5] = new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat2 = Float.parseFloat(replace2);
				        	b2 = new BigDecimal(replace2);
				        	b6 = b6.add(b2);
						} catch (Exception e) {
							data[j2][5] = "0.000000";
							e.printStackTrace();
						}
				        
				        ++z;
					} catch (Exception e) {
						data[j2][4] = "0.000000";
						data[j2][5] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					String j5 = String.valueOf(j).substring(6, 8);
					int j3 = 0;
					j3 = Integer.parseInt(j5) - 1;
					try {
						System.out.println("路径地址2：" + str2);
						com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String readLine7 = csvFileUtil2.readLine();
						String readLine8 = csvFileUtil2.readLine();
						String[] sourceStrArray = readLine8.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
							data[j3][7] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat3 = Float.parseFloat(replace1);
							b3 = new BigDecimal(replace1);
							b7 = b7.add(b3);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							String replace2 = sourceStrArray[2].replace("+", "0");
							data[j3][8] =  new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat4 = Float.parseFloat(replace2);
							b4 = new BigDecimal(replace2);
							b8 = b8.add(b4);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						++z;
					} catch (Exception e) {
						data[j3][7] = "0.000000";
						data[j3][8] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
			} else{
				for (int j =startri ; j <= endri ; j++) {
					System.out.println(j);
					String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					String str2 = cvsfile + cvsname + j +".csv";
					int j2 = 0 ;
					String j1 = String.valueOf(j).substring(6, 8);
			        j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址1：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine3.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	data[j2][4] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat1 = Float.parseFloat(replace1);
				        	b1 = new BigDecimal(replace1);
				        	b5 = b5.add(b1);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        try {
				        	String replace2 = sourceStrArray[2].replace("+", "0");
				        	data[j2][5] = new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat2 = Float.parseFloat(replace2);
				        	b2 = new BigDecimal(replace2);
				        	b6 = b6.add(b2);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        
				        ++z;
					} catch (Exception e) {
						data[j2][4] = "0.000000";
						data[j2][5] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					String j5 = String.valueOf(j).substring(6, 8);
					int j3 = 0;
					j3 = Integer.parseInt(j5) - 1;
					try {
						System.out.println("路径地址2：" + str2);
	//					if (y > Integer.parseInt(beforeDay)) {
	//						str2 = cvsfile + cvsname + nextMonth +".csv";
	//					}
						com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String readLine7 = csvFileUtil2.readLine();
						String readLine8 = csvFileUtil2.readLine();
						String[] sourceStrArray = readLine8.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
							data[j3][7] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat3 = Float.parseFloat(replace1);
							b3 = new BigDecimal(replace1);
							b7 = b7.add(b3);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							String replace2 = sourceStrArray[2].replace("+", "0");
							data[j3][8] =  new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat4 = Float.parseFloat(replace2);
							b4 = new BigDecimal(replace2);
							b8 = b8.add(b4);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						++z;
					} catch (Exception e) {
						data[j3][7] = "0.000000";
						data[j3][8] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			
			parameters.put("qSum", b5.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("qsxSum", b6.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("dSum", b7.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("dsxSum", b8.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			reportCreator.initReportData(getJasperInputSteam("T110101.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40001RN_" + nian + yue + "_" + bank_name + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40001RN_" + nian + yue + "_"  + bank_name + ".pdf";
			
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40001RN"));
			writeUsefullMsg(fileName);
			return;
			
		} else if(reportName.equals("2")) {
			
			System.err.println("bankAccount：" + bankAccount);
			
			System.out.println("2-支付机构客户备付金出金业务明细表");
			title = InformationUtil.createTitles("V_", 15);
			data = new Object[tian][15];
			int z = 0;
			float parseFloat1 = 0.000000f;
			float parseFloat2 = 0.000000f;
			float parseFloat3 = 0.000000f;
			BigDecimal b1 = null;
			BigDecimal b2 = null;
			BigDecimal b3 = null;
			BigDecimal b5 = new BigDecimal("0.000000");
			BigDecimal b6 = new BigDecimal("0.000000");
			BigDecimal b7 = new BigDecimal("0.000000");
			for (int j =startri ; j <= endri ; j++) {
				String nextDay = getNextDay(String.valueOf(j));
				String str = cvsfile + cvsname + nextDay +".csv";
				String j1 = String.valueOf(j).substring(6, 8);
				int j2 = Integer.parseInt(j1) - 1;
				try {
					String txnAmt = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT SUM(a.SUM_AMT)/10000 FROM TBL_MCHT_SUMRZ_INF a where a.SUM_AMT<> 0 AND a.INST_DATE ='"+j+"'");
					String handAmt = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT SUM(a.HAND_AMT)/10000 FROM TBL_MCHT_SUMRZ_INF a where a.SUM_AMT<> 0 AND a.INST_DATE ='"+j+"'");
					if (txnAmt.equals("")) {
						parseFloat2 = Float.parseFloat("0.000000");
					} else{
						parseFloat2 = Float.parseFloat(txnAmt);
					}
					if (handAmt.equals("")) {
						parseFloat3 = Float.parseFloat("0.000000");
					} else{
						parseFloat3 = Float.parseFloat(handAmt);
					}
					if (!txnAmt.equals("")) {
						b2 = new BigDecimal(txnAmt);
						b6 = b6.add(b2);
					} 
					if (!handAmt.equals("")) {
						b3 = new BigDecimal(handAmt);
						b7 = b7.add(b3);
					}
					BigDecimal format = null;
					try {
						format =  new BigDecimal(txnAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
						if (format.equals("0")) {
							data[j2][2] = "0.000000" ;
						}else{
							data[j2][2] = format;
						}
					} catch (Exception e) {
						data[j2][2] = "0.000000" ;
					}
			        BigDecimal format1 = null;
			        try {
			        	format1 = new BigDecimal(handAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
			        	if (format1.equals("0")) {
			        		data[j2][3] = "0.000000";
			        	}else{
			        		data[j2][3] = format1;
			        	}
					} catch (Exception e) {
						data[j2][3] = "0.000000";
					}
			        
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + nextDay +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
					int j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址 - 支付机构客户备付金出金业务明细表 ：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine2.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP).toString();
				        	parseFloat1 = Float.parseFloat(replace1);
				        	b1 = new BigDecimal(replace1);
				        	b5 = b5.add(b1);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        ++z;
					} catch (Exception e) {
						data[j2][1] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}else{
				for (int j =startri ; j < endri ; j++) {
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + nextDay +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
					int j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址 - 支付机构客户备付金出金业务明细表 ：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine2.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP).toString();
				        	parseFloat1 = Float.parseFloat(replace1);
				        	b1 = new BigDecimal(replace1);
				        	b5 = b5.add(b1);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        ++z;
					} catch (Exception e) {
						data[j2][1] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("dSum", b5.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("dpSum", b6.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("dcpSum", b7.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110102.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40002RN_" +  nian + yue  + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40002RN_" + nian  + yue  + ".pdf";
			
			outputStream = new FileOutputStream(fileName);
			
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40002RN"));
			
			writeUsefullMsg(fileName);
			return;
			
		} else if(reportName.equals("3")) {
			System.out.println("3-支付机构客户备付金业务实际出金明细表");
			title = InformationUtil.createTitles("V_", tian + 3);
//			data = new Object[1][tian];
			/*for(int i = 0; i < tian ; i ++){
				int j = i+1;
				data[0][i] = j+"日";
			}*/
			String sqlStr = "select BANK_NAME, ACCOUNT_NAME, BANK_ACCOUNT from TBL_BANKNO_INFO ";
			List<Object[]> listD =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlStr);
			data = new Object[listD.size()][tian + 3];
			for(int i = 0; i < listD.size(); i ++){
				data[i][0] = listD.get(i)[0];
				data[i][1] = listD.get(i)[1];
				data[i][2] = listD.get(i)[2];
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			if (tian == 31) {
				reportCreator.initReportData(getJasperInputSteam("T110103.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 30) {
				reportCreator.initReportData(getJasperInputSteam("T1101031.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 29) {
				reportCreator.initReportData(getJasperInputSteam("T1101032.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 28) {
				reportCreator.initReportData(getJasperInputSteam("T1101033.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40003RN_" + nian + yue  + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40003RN_" + nian  + yue  + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40003RN"));
			writeUsefullMsg(fileName);
			return;

		} else if(reportName.equals("4")) {
			System.out.println("4-支付机构客户资金账户转账业务统计表");
			title = InformationUtil.createTitles("V_", 4);
			data = new Object[tian][4];
			
			System.err.println("CVS地址："+cvsfile + " CVS名字：" + cvsname);
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat2 = 0.0f;
			BigDecimal b1 = null;
			BigDecimal b2 = null;
			BigDecimal b3 = null;
			BigDecimal b5 = new BigDecimal("0");
			BigDecimal b6 = new BigDecimal("0");
			BigDecimal b7 = new BigDecimal("0");
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + nextDay +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址 - 支付机构客户资金账户转账业务统计表 ：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine2.split(",");
						String[] sourceStrArray2 = readLine3.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	String replace3 = sourceStrArray2[1].replace("+", "0");
				        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat1 = Float.parseFloat(replace1);
				        	b1 = new BigDecimal(replace1);
				        	b5 = b5.add(b1);
				        	data[j2][2] = new BigDecimal(replace3).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat2 = Float.parseFloat(replace3);
				        	b2 = new BigDecimal(replace3);
				        	b6 = b6.add(b2);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        ++z;
					} catch (Exception e) {
						data[j2][1] = "0.000000";
				        data[j2][2] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					/*String handAmt = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT SUM(a.HAND_AMT)/10000 FROM TBL_MCHT_SUMRZ_INF a where a.SUM_AMT<> 0 AND a.INST_DATE ='"+j+"'");
					if (!handAmt.equals("")) {
						b3 = new BigDecimal(handAmt);
						b7 = b7.add(b3);
					}
					BigDecimal format1 = null;
					try {
					    format1 = new BigDecimal(handAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
						if (format1.equals("0")) {
							data[j2][3] = "0.000000";
						}else{
							data[j2][3] = format1;
						}
					} catch (Exception e) {
						data[j2][3] = "0.000000";
					}*/
				}
			}else{
			for (int j =startri ; j < endri ; j++) {
				String nextDay = String.valueOf(j);
				String str = cvsfile + cvsname + nextDay +".csv";
				String j1 = String.valueOf(j).substring(6, 8);
		        int j2 = Integer.parseInt(j1) - 1;
				try {
					System.out.println("路径地址 - 支付机构客户资金账户转账业务统计表 ：" + str);
					com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
					String readLine1 = csvFileUtil.readLine();
					String readLine2 = csvFileUtil.readLine();
					String readLine3 = csvFileUtil.readLine();
					String readLine4 = csvFileUtil.readLine();
					String readLine5 = csvFileUtil.readLine();
					String readLine6 = csvFileUtil.readLine();
					String readLine7 = csvFileUtil.readLine();
					String[] sourceStrArray = readLine7.split(",");
//					String[] sourceStrArray2 = readLine7.split(",");
			        try {
			        	String replace1 = sourceStrArray[1].replace("+", "0");
//			        	String replace3 = sourceStrArray2[1].replace("+", "0");
			        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
			        	data[j2][2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
//			        	parseFloat1 = Float.parseFloat(replace1);
//			        	b1 = new BigDecimal(replace1);
//			        	b5 = b5.add(b1);
//			        	data[j2][2] = new BigDecimal(replace3).setScale(6, BigDecimal.ROUND_HALF_UP);
//			        	parseFloat2 = Float.parseFloat(replace3);
//			        	b2 = new BigDecimal(replace3);
//			        	b6 = b6.add(b2);
					} catch (Exception e) {
						e.printStackTrace();
					}
			        ++z;
				} catch (Exception e) {
					data[j2][1] = "0.000000";
			        data[j2][2] = "0.000000";
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
			}
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data [i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("cSum", b5.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("rSum", b6.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("hSum", b7.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110104.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40004RN_" +  nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40004RN_" +  nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40004RN"));
			writeUsefullMsg(fileName);
			return;
		} else if(reportName.equals("5")) {
			System.out.println("5-支付机构客户资金账户余额统计表");
			title = InformationUtil.createTitles("V_", 7);
			data = new Object[tian][7];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat2 = 0.0f;
			float parseFloat3 = 0.0f;
			float parseFloat4 = 0.0f;
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					int y = j+1;
					String str2 = cvsfile + cvsname + j +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址1：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String readLine4 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine4.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        ++z;
					} catch (Exception e) {
						data[j2][1] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					String j5 = String.valueOf(j).substring(6, 8);
					int j3 = Integer.parseInt(j5) - 1;
					try {
						com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String readLine7 = csvFileUtil2.readLine();
						String readLine8 = csvFileUtil2.readLine();
						String readLine9 = csvFileUtil2.readLine();
						String readLine10 = csvFileUtil2.readLine();
						String[] sourceStrArray = readLine6.split(",");
						String[] sourceStrArray2 = readLine5.split(",");
						String[] sourceStrArray3 = readLine7.split(",");
						String[] sourceStrArray4 = readLine10.split(",");
						try {
							String replace1 = sourceStrArray3[1].replace("+", "0");
							data[j3][6] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray4[1].replace("+", "0");
							data[j3][4] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							data[j3][5] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[j3][4] =  "0.000000";
							data[j3][5] =  "0.000000";
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray2[1].replace("+", "0");
							data[j3][3] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[j3][3] = "0.000000";
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
							data[j3][2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[j3][2] = "0.000000";
							e.printStackTrace();
						}
						
						++z;
					} catch (Exception e) {
						data[j3][6] = "0.000000";
						data[j3][5] = "0.000000";
						data[j3][4] = "0.000000";
						data[j3][3] = "0.000000";
						data[j3][2] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}else{
			for (int j =startri ; j < endri ; j++) {
				String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
				int y = j+1;
				String str2 = cvsfile + cvsname + j +".csv";
				String j1 = String.valueOf(j).substring(6, 8);
		        int j2 = Integer.parseInt(j1) - 1;
				try {
					System.out.println("路径地址1：" + str);
					com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
					String readLine1 = csvFileUtil.readLine();
					String readLine2 = csvFileUtil.readLine();
					String readLine3 = csvFileUtil.readLine();
					String readLine4 = csvFileUtil.readLine();
					String[] sourceStrArray = readLine4.split(",");
			        try {
			        	String replace1 = sourceStrArray[1].replace("+", "0");
			        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						e.printStackTrace();
					}
			        ++z;
				} catch (Exception e) {
					data[j2][1] = "0.000000";
					log.error(e.getMessage());
					e.printStackTrace();
				}
				String j5 = String.valueOf(j).substring(6, 8);
				int j3 = Integer.parseInt(j5) - 1;
				try {
					if (y > Integer.parseInt(beforeDay)) {
						str2 = cvsfile + cvsname + nextMonth +".csv";
					}
					System.out.println("路径地址2：" + str2);
					com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
					String readLine4 = csvFileUtil2.readLine();
					String readLine5 = csvFileUtil2.readLine();
					String readLine6 = csvFileUtil2.readLine();
					String readLine7 = csvFileUtil2.readLine();
					String readLine8 = csvFileUtil2.readLine();
					String readLine9 = csvFileUtil2.readLine();
					String readLine10 = csvFileUtil2.readLine();
					String[] sourceStrArray = readLine6.split(",");
					String[] sourceStrArray2 = readLine5.split(",");
					String[] sourceStrArray3 = readLine7.split(",");
					String[] sourceStrArray4 = readLine10.split(",");
					try {
						String replace1 = sourceStrArray3[1].replace("+", "0");
						data[j3][6] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j3][6] = "0.000000";
						e.printStackTrace();
					}
					try {
						String replace1 = sourceStrArray4[1].replace("+", "0");
						data[j3][5] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j3][5] = "0.000000";
						e.printStackTrace();
					}
					try {
						String replace1 = sourceStrArray4[1].replace("+", "0");
						data[j3][4] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j3][4] = "0.000000";
						e.printStackTrace();
					}
					try {
						String replace1 = sourceStrArray2[1].replace("+", "0");
						data[j3][3] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j3][3] = "0.000000";
						e.printStackTrace();
					}
					try {
						String replace1 = sourceStrArray[1].replace("+", "0");
						data[j3][2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j3][2] = "0.000000";
						e.printStackTrace();
					}
					
					++z;
				} catch (Exception e) {
					data[j3][6] = "0.000000";
					data[j3][5] = "0.000000";
					data[j3][4] = "0.000000";
					data[j3][3] = "0.000000";
					data[j3][2] = "0.000000";
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}}
			
			
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110105.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40005RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40005RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40005RN"));
			writeUsefullMsg(fileName);
			return;
			
		} else if(reportName.equals("6")) {
			System.out.println("6-支付机构招商银行特殊业务明细表");
			
			title = InformationUtil.createTitles("V_", tian);
			data = new Object[1][tian];
//			for(int i = 0; i < tian ; i ++){
//				int j = i+1;
//				data[0][i] = j+"日";
//			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			if (tian == 31) {
				reportCreator.initReportData(getJasperInputSteam("T110106.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 30) {
				reportCreator.initReportData(getJasperInputSteam("T1101061.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 29) {
				reportCreator.initReportData(getJasperInputSteam("T1101062.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 28) {
				reportCreator.initReportData(getJasperInputSteam("T1101063.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40006RN_"+  nian + yue + "_" + bank_name + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40006RN_" + nian + yue + "_" + bank_name + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40006RN"));
			writeUsefullMsg(fileName);
			return;
			
			
		} else if(reportName.equals("7")) {
			System.out.println("7-支付机构现金购卡业务统计表");

			title = InformationUtil.createTitles("V_", 5);
			data = new Object[tian][5];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110107.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40007RN_" +  nian + yue  + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40007RN_" +  nian + yue  + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40007RN"));
			writeUsefullMsg(fileName);
			return;
			
		} else if(reportName.equals("8")) {
			System.out.println("8-支付机构预付卡现金赎回业务统计表");
			title = InformationUtil.createTitles("V_", 5);
			data = new Object[tian][5];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110108.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40008RN_" +  nian + yue  + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40008RN_" +  nian + yue  + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40008RN"));
			writeUsefullMsg(fileName);
			return;
			
		} else if(reportName.equals("9")) {
			System.out.println("9-支付机构招商银行客户备付金业务未达账项统计表");
			
			title = InformationUtil.createTitles("V_", tian+1);
			data = new Object[5][tian+1];
			data[0][0] = "未达账项类型"; 
			for(int i = 1; i <= tian ; i ++){
//				int j = i;
				data[0][i] = i+"日";
			}
			data[1][0] = "1.支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额";
			data[2][0] = "2.支付机构业务系统已减少客户资金账户余额，备付金银行未减少备付金银行账户余额";
			data[3][0] = "3.备付金银行已增加备付金银行账户余额，支付机构业务系统未增加客户资金账户余额";
			data[4][0] = "4.备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户余额";
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat3 = 0.0f;
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
//					int j = i+1;
//					data[0][z] = j+"日";
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + j +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) ;//- 1;
					try {
						System.out.println("路径地址 - 支付机构招商银行客户备付金业务未达账项统计表 ：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String readLine4 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine1.split(",");
						String[] sourceStrArray3 = readLine3.split(",");
						try {//核心当日入金数据 
							String replace3 = sourceStrArray3[1].replace("+", "0");
							parseFloat3 = Float.parseFloat(replace3);
							data[1][j2] = new BigDecimal(replace3).setScale(6, BigDecimal.ROUND_HALF_UP);
							++z;
						} catch (Exception e) {
							
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {//核心当日食堂数据
							String replace1 = sourceStrArray[1].replace("+", "0");
							parseFloat1 = Float.parseFloat(replace1);
					        data[2][j2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					        ++z;
						} catch (Exception e) {
							
							log.error(e.getMessage());
							e.printStackTrace();
						}
					} catch (Exception e) {
						data[1][j2] = "0.000000";
						data[2][j2] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					
				}
			}else{
			for (int j =startri ; j < endri ; j++) {
//				int j = i+1;
//				data[0][z] = j+"日";
				String nextDay = String.valueOf(j);
				String str = cvsfile + cvsname + j +".csv";
				String j1 = String.valueOf(j).substring(6, 8);
		        int j2 = Integer.parseInt(j1) ;//- 1;
				try {
					System.out.println("路径地址 - 支付机构招商银行客户备付金业务未达账项统计表 ：" + str);
					com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
					String readLine1 = csvFileUtil.readLine();
					String readLine2 = csvFileUtil.readLine();
					String readLine3 = csvFileUtil.readLine();
					String readLine4 = csvFileUtil.readLine();
					String[] sourceStrArray = readLine1.split(",");
					String[] sourceStrArray3 = readLine3.split(",");
					try {
						String replace3 = sourceStrArray3[1].replace("+", "0");
						parseFloat3 = Float.parseFloat(replace3);
						data[1][j2] = new BigDecimal(replace3).setScale(6, BigDecimal.ROUND_HALF_UP);
						++z;
					} catch (Exception e) {
						
						log.error(e.getMessage());
						e.printStackTrace();
					}
					try {
						String replace1 = sourceStrArray[1].replace("+", "0");
						parseFloat1 = Float.parseFloat(replace1);
				        data[2][j2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        ++z;
					} catch (Exception e) {
						
						log.error(e.getMessage());
						e.printStackTrace();
					}
				} catch (Exception e) {
					data[2][j2] = "0.000000";
					log.error(e.getMessage());
					e.printStackTrace();
				}
				
			}}
			
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			if (tian == 31) {
				reportCreator.initReportData(getJasperInputSteam("T110109.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 30) {
				reportCreator.initReportData(getJasperInputSteam("T1101091.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 29) {
				reportCreator.initReportData(getJasperInputSteam("T1101092.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 28) {
				reportCreator.initReportData(getJasperInputSteam("T1101093.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40009RN_" + nian + yue + "_" + bank_name + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40009RN_" + nian + yue + "_" + bank_name + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40009RN"));
			writeUsefullMsg(fileName);
			return;

		} else if(reportName.equals("10")) {
			System.out.println("10-支付机构招商银行客户备付金业务未达账项分析表");
			
			title = InformationUtil.createTitles("V_", 25);
			data = new Object[tian][25];
			
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat2 = 0.0f;
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + nextDay +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址 - 支付机构招商银行客户备付金业务未达账项分析表 ：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine1.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	String replace2 = sourceStrArray[3].replace("+", "0");
				        	data[j2][12] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	data[j2][11] = replace2;
				        	parseFloat1 = Float.parseFloat(replace1);
				        	parseFloat2 = Float.parseFloat(replace2);
						} catch (Exception e) {
							e.printStackTrace();
						}
				        
				        ++z;
					} catch (Exception e) {
						data[j2][12] = "0.000000";
				        data[j2][11] = "0";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					
				}
			}else{
			for (int j =startri ; j < endri ; j++) {
				String nextDay = String.valueOf(j);
				String str = cvsfile + cvsname + nextDay +".csv";
				String j1 = String.valueOf(j).substring(6, 8);
		        int j2 = Integer.parseInt(j1) - 1;
				try {
					System.out.println("路径地址 - 支付机构招商银行客户备付金业务未达账项分析表 ：" + str);
					com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
					String readLine1 = csvFileUtil.readLine();
					String[] sourceStrArray = readLine1.split(",");
			        try {
			        	String replace1 = sourceStrArray[1].replace("+", "0");
			        	String replace2 = sourceStrArray[3].replace("+", "0");
			        	data[j2][12] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
			        	data[j2][11] = replace2;
			        	parseFloat1 = Float.parseFloat(replace1);
			        	parseFloat2 = Float.parseFloat(replace2);
					} catch (Exception e) {
						e.printStackTrace();
					}
			        
			        ++z;
				} catch (Exception e) {
					data[j2][12] = "0.000000";
			        data[j2][11] = "0";
					log.error(e.getMessage());
					e.printStackTrace();
				}
				
			}}
			
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			reportCreator.initReportData(getJasperInputSteam("T110110.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40010RN_" + nian + yue+ "_" + bank_name + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40010RN_" + nian + yue + "_" + bank_name + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40010RN"));
			writeUsefullMsg(fileName);
			return;

		} else if(reportName.equals("11")) {
			System.out.println("11-支付机构客户资金账户余额变动调节表");
			title = InformationUtil.createTitles("V_", 28);
			data = new Object[tian][28];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat2 = 0.0f;
			float parseFloat3 = 0.0f;
			float parseFloat4 = 0.0f;
			BigDecimal b1 = null ;
			BigDecimal b2 = null;
			BigDecimal b3 = null;
			BigDecimal b4 = null;
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					int y = j+1;
					String str2 = cvsfile + cvsname + j +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) - 1;
					try {
						System.out.println("路径地址1：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String readLine4 = csvFileUtil.readLine();
						String[] sourceStrArray1 = readLine3.split(",");
						String[] sourceStrArray = readLine4.split(",");
				        try {
				        	String replace1 = sourceStrArray[1].replace("+", "0");
				        	b1 = new BigDecimal(replace1);
				        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat1 = Float.parseFloat(replace1);
						} catch (Exception e) {
							data[j2][1] = "0.000000";
							b1 = new BigDecimal("0");
							e.printStackTrace();
						}
				        try {//前一日入金
				        	String replace1 = sourceStrArray1[1].replace("+", "0");
				        	b4 = new BigDecimal(replace1);
						} catch (Exception e) {
							b4 = new BigDecimal("0");
							e.printStackTrace();
						}
				        ++z;
					} catch (Exception e) {
						b1 = new BigDecimal("0");
						data[j2][1] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					String j5 = String.valueOf(j).substring(6, 8);
					int j3 = Integer.parseInt(j5) - 1;
					try {
						System.out.println("路径地址2：" + str2);
						com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String readLine7 = csvFileUtil2.readLine();
						String[] sourceStrArray6 = readLine5.split(",");
						String[] sourceStrArray5 = readLine4.split(",");
						String[] sourceStrArray4 = readLine6.split(",");
						String[] sourceStrArray3 = readLine7.split(",");
						try {//核心当日入金数据
							String replace4 = sourceStrArray4[1].replace("+", "0");
							data[j3][10] = new BigDecimal(replace4).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[j3][10] = "0.000000";
							e.printStackTrace();
						}
						try {//当日核心出金核心手续费+当日核心入金手续费
							String replace1 = sourceStrArray6[2].replace("+", "0");
							String replace2 = sourceStrArray4[2].replace("+", "0");
							BigDecimal setScale = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							BigDecimal setScale2 = new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
							BigDecimal subtract = setScale.add(setScale2);
							data[j3][9] = subtract;
						} catch (Exception e) {
							data[j3][9] = "0.000000";
							e.printStackTrace();
						}
						try {//当日食堂 核心当日食堂数据
							String replace1 = sourceStrArray5[1].replace("+", "0");
							data[j3][11] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[j3][11] = "0.000000";
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray3[1].replace("+", "0");
							b2 = new BigDecimal(replace1);
							data[j3][2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat4 = Float.parseFloat(replace1);
						} catch (Exception e) {
							data[j3][2] = "0.000000";
							b2 = new BigDecimal("0");
							e.printStackTrace();
						}
						try {//核心当日入金
							String replace1 = sourceStrArray4[1].replace("+", "0");
							b3 = new BigDecimal(replace1);
						} catch (Exception e) {
							b3 = new BigDecimal("0");
							e.printStackTrace();
						}
						++z;
					} catch (Exception e) {
						b2 = new BigDecimal("0");
						data[j3][2] = "0.000000";
						data[j3][9] = "0.000000";
						data[j3][10] = "0.000000";
						data[j3][11] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
					try {
						data[j3][3] = new BigDecimal(b2.subtract(b1).doubleValue()).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j3][3] = "0.000000";
					}
				}
			} else{
			for (int j =startri ; j < endri ; j++) {
				String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
				int y = j+1;
				String str2 = cvsfile + cvsname + j +".csv";
				String j1 = String.valueOf(j).substring(6, 8);
		        int j2 = Integer.parseInt(j1) - 1;
				try {
					System.out.println("路径地址1：" + str);
					com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
					String readLine1 = csvFileUtil.readLine();
					String readLine2 = csvFileUtil.readLine();
					String readLine3 = csvFileUtil.readLine();
					String readLine4 = csvFileUtil.readLine();
					String[] sourceStrArray2 = readLine1.split(",");
					String[] sourceStrArray1 = readLine3.split(",");
					String[] sourceStrArray = readLine4.split(",");
			        try {
			        	String replace1 = sourceStrArray[1].replace("+", "0");
			        	b1 = new BigDecimal(replace1);
			        	data[j2][1] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
			        	parseFloat1 = Float.parseFloat(replace1);
			        	
					} catch (Exception e) {
						b1 = new BigDecimal("0");
						e.printStackTrace();
					}
			        try {//前一日入金
			        	String replace1 = sourceStrArray1[1].replace("+", "0");
			        	b4 = new BigDecimal(replace1);
					} catch (Exception e) {
						b4 = new BigDecimal("0");
						e.printStackTrace();
					}
			        ++z;
				} catch (Exception e) {
					b1 = new BigDecimal("0");
					data[j2][1] = "0.000000";
					log.error(e.getMessage());
					e.printStackTrace();
				}
				String j5 = String.valueOf(j).substring(6, 8);
				int j3 = Integer.parseInt(j5) - 1;
				try {
					System.out.println("路径地址2：" + str2);
					if (y > Integer.parseInt(beforeDay)) {
						str2 = cvsfile + cvsname + nextMonth +".csv";
					}
					com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
					String readLine4 = csvFileUtil2.readLine();
					String readLine5 = csvFileUtil2.readLine();
					String readLine6 = csvFileUtil2.readLine();
					String readLine7 = csvFileUtil2.readLine();
					String[] sourceStrArray3 = readLine7.split(",");
					String[] sourceStrArray4 = readLine6.split(",");
					String[] sourceStrArray5 = readLine4.split(",");
					String[] sourceStrArray6 = readLine5.split(",");
					try {
						String replace1 = sourceStrArray3[1].replace("+", "0");
						b2 = new BigDecimal(replace1);
						data[j3][2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						parseFloat4 = Float.parseFloat(replace1);
					} catch (Exception e) {
						b2 = new BigDecimal("0");
						e.printStackTrace();
					}
					try {// 当日核心出金核心手续费+当日核心入金手续费
						String replace1 = sourceStrArray6[2].replace("+", "0");
						String replace2 = sourceStrArray4[2].replace("+", "0");
						BigDecimal setScale = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						BigDecimal setScale2 = new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
						BigDecimal add = setScale.add(setScale2);
						data[j2][9] = add;
					} catch (Exception e) {
						data[j2][9] = "0.000000";
						e.printStackTrace();
					}
					try {// 核心当日入金数据
						String replace1 = sourceStrArray4[1].replace("+", "0");
						data[j2][10] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j2][10] = "0.000000";
						e.printStackTrace();
					}
					try {//当日食堂   核心当日食堂数据
						String replace1 = sourceStrArray5[1].replace("+", "0");
						data[j2][11] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						data[j2][11] = "0.000000";
						e.printStackTrace();
					}
					try {//核心当日入金
						String replace1 = sourceStrArray4[1].replace("+", "0");
						b3 = new BigDecimal(replace1);
					} catch (Exception e) {
						b3 = new BigDecimal("0");
						e.printStackTrace();
					}
					++z;
				} catch (Exception e) {
					b2 = new BigDecimal("0");
					data[j2][2] = "0.000000";
					data[j2][9] = "0.000000";
					data[j2][10] = "0.000000";
					data[j2][11] = "0.000000";
					log.error(e.getMessage());
					e.printStackTrace();
				}
				try {
					data[j3][3] = new BigDecimal(b2.subtract(b1).doubleValue()).setScale(6, BigDecimal.ROUND_HALF_UP);
				} catch (Exception e) {
					data[j3][3] = "0.000000";
				}
			}}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110111.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40011RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40011RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40011RN"));
			writeUsefullMsg(fileName);
			return;
		} else if(reportName.equals("12")) {
			BigDecimal b1 = null ;
			BigDecimal b2 = null;
			System.out.println("12-支付机构客户资金账户余额试算表");
			title = InformationUtil.createTitles("V_", tian+1);
			data = new Object[18][tian+1];
			data[0][0] = "项目";
			data[1][0] = "一、备付金银行账户余额";
			data[2][0] = "减：备付金银行账户中未结转的备付金银行存款利息余额（累计实现的利息收入总额-累计计提的风险准备金-累计结转的利息收入）";
			data[3][0] = "减：备付金银行账户中累计申请存放的自有资金余额（累计申请存放的-累计申请提回的）";
			data[4][0] = "减：未结转的支付业务净收入余额（累计实现的收入-累计扣取的手续费支出-累计结转的手续费收入）";
			data[5][0] = "加：期末以现金形式持有的客户备付金余额（累计接受的现金形式客户备付金-累计缴存备付金银行金额）";
			data[6][0] = "减：本期期末仍存在的以自有资金先行偿付的预付卡赎回金额（累计以自有资金先行偿付金额-累计向存管银行申请结转金额）";
			data[7][0] = "二、未达账项调整(期末余额)";
			data[8][0] = "加：支付机构已增加客户资金余额，备付金银行未增加备付金银行账户余额";
			data[9][0] = "减：支付机构已减少客户资金余额，备付金银行未减少备付金银行账户余额";
			data[10][0] = "减：备付金银行已增加备付金银行账户余额，支付机构未增加客户资金余额";
			data[11][0] = "加：备付金银行已减少备付金银行账户余额，支付机构未减少客户资金余额";
			data[12][0] = "三、其他调整项目";
			data[13][0] = "减：预付卡押金业务调整金额";
			data[14][0] = "减：其他";
			data[15][0] = "四、支付机构客户资金账户余额试算值（备付金银行账户余额加减调整项及未达账后得出的数值）";
			data[16][0] = "五、支付机构客户资金账户余额实际值";
			data[17][0] = "六、实际值-试算值";
			for(int i = 1; i <= tian ; i ++){
				data[0][i] = i +"日";
			}
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + nextDay +".csv";
					String str1 = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					String str2 = cvsfile + cvsname + j +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) ;//- 1;
			        try {
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine1.split(",");
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
					}
			        try {//前一日核心入金
			        	com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str1);
			        	String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String readLine4 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine3.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
				        	b1 = new BigDecimal(replace1);
						} catch (Exception e) {
							b1 = new BigDecimal("0");
							e.printStackTrace();
						}
					} catch (Exception e) {
						b1 = new BigDecimal("0");
						e.printStackTrace();
					}
			        try {
			        	com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String readLine7 = csvFileUtil2.readLine();
						String[] sourceStrArray1 = readLine4.split(",");
						String[] sourceStrArray2 = readLine6.split(",");
						String[] sourceStrArray3 = readLine7.split(",");
						try {
							String replace = sourceStrArray2[1].replace("+", "0");
							data[8][j2] = new BigDecimal(replace).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[8][j2] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray1[1].replace("+", "0");
					        data[9][j2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[9][j2] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray3[1].replace("+", "0");
							data[16][j2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[16][j2] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray2[1].replace("+", "0");
				        	b2 = new BigDecimal(replace1);
						} catch (Exception e) {
							b2 = new BigDecimal("0");
							e.printStackTrace();
						}
					} catch (Exception e) {
						data[8][j2] = "0.000000";
						data[9][j2] = "0.000000";
						data[16][j2] = "0.000000";
						b2 = new BigDecimal("0");
						e.printStackTrace();
					}
				}
			} else{
				for (int j =startri ; j < endri ; j++) {
					String nextDay = String.valueOf(j);
					String str = cvsfile + cvsname + nextDay +".csv";
					String str1 = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					String str2 = cvsfile + cvsname + j +".csv";
					String j1 = String.valueOf(j).substring(6, 8);
			        int j2 = Integer.parseInt(j1) ;//- 1;
					try {
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine1.split(",");
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
					}
					try {//前一日核心入金
			        	com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str1);
			        	String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String readLine4 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine3.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
				        	b1 = new BigDecimal(replace1);
						} catch (Exception e) {
							b1 = new BigDecimal("0");
							e.printStackTrace();
						}
					} catch (Exception e) {
						b1 = new BigDecimal("0");
						e.printStackTrace();
					}
			        try {//当日核心入金
			        	com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String readLine7 = csvFileUtil2.readLine();
						String[] sourceStrArray1 = readLine4.split(",");
						String[] sourceStrArray2 = readLine6.split(",");
						String[] sourceStrArray3 = readLine7.split(",");
						try {
							String replace = sourceStrArray2[1].replace("+", "0");
							data[8][j2] = new BigDecimal(replace).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[8][j2] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray1[1].replace("+", "0");
					        data[9][j2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[9][j2] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray3[1].replace("+", "0");
							data[16][j2] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							data[16][j2] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace1 = sourceStrArray2[1].replace("+", "0");
				        	b2 = new BigDecimal(replace1);
						} catch (Exception e) {
							b2 = new BigDecimal("0");
							e.printStackTrace();
						}
					} catch (Exception e) {
						data[8][j2] = "0.000000";
						data[9][j2] = "0.000000";
						data[16][j2] = "0.000000";
						b2 = new BigDecimal("0");
						e.printStackTrace();
					}
				}
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			if (tian == 31) {
				reportCreator.initReportData(getJasperInputSteam("T110112.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 30) {
				reportCreator.initReportData(getJasperInputSteam("T1101121.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 29) {
				reportCreator.initReportData(getJasperInputSteam("T1101122.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}else if (tian == 28) {
				reportCreator.initReportData(getJasperInputSteam("T1101123.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			}
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40012RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40012RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40012RN"));
			writeUsefullMsg(fileName);
			return;

		}else if(reportName.equals("13")) {
			System.out.println("13-预付卡发行企业备付金账户中售卡押金统计表");
			
			title = InformationUtil.createTitles("V_", 7);
			data = new Object[tian][7];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110113.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40013RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40013RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40013RN"));
			writeUsefullMsg(fileName);
			return;
			
		}else if(reportName.equals("14")) {
			System.out.println("14-招商银行汇明商务服务有限公司支付机构备付金银行账户余额统计表");
			
			title = InformationUtil.createTitles("V_", 12);
			data = new Object[tian][12];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110114.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40014RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40014RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN40014RN"));
			writeUsefullMsg(fileName);
			return;
		}else if(reportName.equals("15")) {
			
			System.out.println("支付机构XX银行客户备付金入金业务调节表");
			title = InformationUtil.createTitles("V_", 17);
			data = new Object[tian][17];
			System.err.println("CVS地址："+cvsfile + " CVS名字：" + cvsname);
			
			int z = 0;
			float parseFloat1 = 0.0f;
			float parseFloat2 = 0.0f;
			float parseFloat3 = 0.0f;
			float parseFloat4 = 0.0f;
			BigDecimal b1 = null;
			BigDecimal b2 = null;
			BigDecimal b3 = null;
			BigDecimal b4 = null;
			BigDecimal b5 = new BigDecimal("0");
			BigDecimal b6 = new BigDecimal("0");
			BigDecimal b7 = new BigDecimal("0");
			BigDecimal b8 = new BigDecimal("0");
			
			if (date.equals(beforeDay)) {
				for (int j =startri ; j <= endri ; j++) {
					String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					String str2 = cvsfile + cvsname + j +".csv";
					int j2 = 0 ;
					String j1 = String.valueOf(j).substring(6, 8);
			        j2 = Integer.parseInt(j1) - 1;
			        try {
			        	System.out.println("路径地址1：" + str);
						com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
						String readLine1 = csvFileUtil.readLine();
						String readLine2 = csvFileUtil.readLine();
						String readLine3 = csvFileUtil.readLine();
						String[] sourceStrArray = readLine3.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
				        	data[j2][5] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
				        	parseFloat1 = Float.parseFloat(replace1);
				        	b1 = new BigDecimal(replace1);
				        	b5 = b5.add(b1);
						} catch (Exception e) {
							data[j2][5] = "0.000000";
							e.printStackTrace();
						}
						try {
					       	String replace2 = sourceStrArray[2].replace("+", "0");
					       	data[j2][6] = new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
					       	parseFloat2 = Float.parseFloat(replace2);
					       	b2 = new BigDecimal(replace2);
					       	b6 = b6.add(b2);
						} catch (Exception e) {
							data[j2][6] = "0.000000";
							e.printStackTrace();
						}
						++z;
					} catch (Exception e) {
						data[j2][5] = "0.000000";
						data[j2][6] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
			        String j5 = String.valueOf(j).substring(6, 8);
					int j3 = 0;
					j3 = Integer.parseInt(j5) - 1;
					try {
						System.out.println("路径地址2：" + str2);
						com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String[] sourceStrArray = readLine6.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
							data[j3][9] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat3 = Float.parseFloat(replace1);
							b3 = new BigDecimal(replace1);
							b7 = b7.add(b3);
						} catch (Exception e) {
							data[j3][9] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						try {
							String replace2 = sourceStrArray[2].replace("+", "0");
							data[j3][10] =  new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat4 = Float.parseFloat(replace2);
							b4 = new BigDecimal(replace2);
							b8 = b8.add(b4);
						} catch (Exception e) {
							data[j3][10] = "0.000000";
							log.error(e.getMessage());
							e.printStackTrace();
						}
						++z;
					} catch (Exception e) {
						data[j3][9] = "0.000000";
						data[j3][10] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}else {
				for (int j =startri ; j < endri ; j++) {
					System.out.println(j);
					String str = cvsfile + cvsname + getBeforeDay(String.valueOf(j)) +".csv";
					String str2 = cvsfile + cvsname + j +".csv";
					int j2 = 0 ;
					String j1 = String.valueOf(j).substring(6, 8);
			        j2 = Integer.parseInt(j1) - 1;
			        try {
			        	System.out.println("路径地址1：" + str);
			        	com.huateng.system.util.CSVFileUtil csvFileUtil = new com.huateng.system.util.CSVFileUtil(str);
			        	String readLine1 = csvFileUtil.readLine();
			        	String readLine2 = csvFileUtil.readLine();
			        	String readLine3 = csvFileUtil.readLine();
			        	String[] sourceStrArray = readLine3.split(",");
			        	try {
			        		String replace1 = sourceStrArray[1].replace("+", "0");
			        		data[j2][5] =  new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
			        		parseFloat1 = Float.parseFloat(replace1);
			        		b1 = new BigDecimal(replace1);
			        		b5 = b5.add(b1);
			        	} catch (Exception e) {
			        		data[j2][5] = "0.000000";
			        		e.printStackTrace();
			        	}
			        	try {
			        		String replace2 = sourceStrArray[2].replace("+", "0");
			        		data[j2][6] = new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
			        		parseFloat2 = Float.parseFloat(replace2);
			        		b2 = new BigDecimal(replace2);
			        		b6 = b6.add(b2);
			        	} catch (Exception e) {
			        		e.printStackTrace();
			        	}
			        	++z;
					} catch (Exception e) {
						data[j2][5] = "0.000000";
						data[j2][6] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
			        String j5 = String.valueOf(j).substring(6, 8);
					int j3 = 0;
					j3 = Integer.parseInt(j5) - 1;
					try {
						System.out.println("路径地址2：" + str2);
						com.huateng.system.util.CSVFileUtil csvFileUtil2 = new com.huateng.system.util.CSVFileUtil(str2);
						String readLine4 = csvFileUtil2.readLine();
						String readLine5 = csvFileUtil2.readLine();
						String readLine6 = csvFileUtil2.readLine();
						String[] sourceStrArray = readLine6.split(",");
						try {
							String replace1 = sourceStrArray[1].replace("+", "0");
							data[j3][9] = new BigDecimal(replace1).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat3 = Float.parseFloat(replace1);
							b3 = new BigDecimal(replace1);
							b7 = b7.add(b3);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							String replace2 = sourceStrArray[2].replace("+", "0");
							data[j3][10] =  new BigDecimal(replace2).setScale(6, BigDecimal.ROUND_HALF_UP);
							parseFloat4 = Float.parseFloat(replace2);
							b4 = new BigDecimal(replace2);
							b8 = b8.add(b4);
						} catch (Exception e) {
							e.printStackTrace();
						}
						++z;
					} catch (Exception e) {
						data[j3][9] = "0.000000";
						data[j3][10] = "0.000000";
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
				
			}
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110204.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110204RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110204RN_" + nian + yue + ".pdf";
			
			outputStream = new FileOutputStream(fileName);
			
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN110204RN"));
			
			writeUsefullMsg(fileName);
			return;
			
		}else if(reportName.equals("16")) {
			System.out.println("分银行账户的表");
			title = InformationUtil.createTitles("V_", 7);
			data = new Object[tian][7];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
//			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110205.jasper"), parameters, reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110205RN_" + nian + yue + "_" + bank_name + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110205RN_" + nian + yue + "_" + bank_name + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN110205RN"));
			writeUsefullMsg(fileName);
			return;
			
		}else if(reportName.equals("17")) {
			System.out.println("支付机构管理账户情况统计");
			title = InformationUtil.createTitles("V_", 5);
			data = new Object[tian][5];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110206.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110206RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110206RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN110206RN"));
			writeUsefullMsg(fileName);
			return;
			
		}else if(reportName.equals("18")) {
			System.out.println("预付卡商户白名单");
			title = InformationUtil.createTitles("V_", 6);
			data = new Object[tian][6];
//			for(int i = 0; i < data.length ; i ++){
//				int j = i+1;
//				data[i][0] = j+"日";
//			}
			int z = 0;
//			List<Object> objectLists = new ArrayList<Object>();
			List settleRpts = CommonFunction.getCommQueryDAO().findBySQLQuery("select  tmsi.SETTLE_RPT from tbl_mcht_base_inf tmbi, TBL_MCHT_SETTLE_INF tmsi where tmbi.MCHT_NO = tmsi.MCHT_NO and substr(tmbi.REC_UPD_TS,1,8) = '" + nian + yue  + ri + "'");
			Object[][] datas = new Object[settleRpts.size() + 1 ][6];
			String bank_No = "";
			if (settleRpts.size() != 0) {
				for (Object object : settleRpts) {
					if (object.equals("1")) {
						System.out.println("对私");
						List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery("select tmbi.MCHT_NO, tmbi.MCHT_NM, tmsi.corp_bank_name, tmbi.REC_UPD_TS, tmbi.REC_CRT_TS from tbl_mcht_base_inf tmbi, TBL_MCHT_SETTLE_INF tmsi where tmbi.MCHT_NO = tmsi.MCHT_NO and substr(tmbi.REC_UPD_TS,1,8) = '" + nian + yue  + ri + "'");
						Object[] objects = list.get(0);
						int indexOf = objects[2].toString().indexOf("招商");
						int indexOf1 = objects[2].toString().indexOf("工商");
						int indexOf2 = objects[2].toString().indexOf("建设");
						int indexOf3 = objects[2].toString().indexOf("农业");
						int indexOf4 = objects[2].toString().indexOf("中国银行");
						int indexOf5 = objects[2].toString().indexOf("浦发");
						int indexOf6 = objects[2].toString().indexOf("民生");
						int indexOf7 = objects[2].toString().indexOf("兴业");
						int indexOf8 = objects[2].toString().indexOf("光大");
						int indexOf9 = objects[2].toString().indexOf("中信");
						int indexOf10 = objects[2].toString().indexOf("广发");
						int indexOf11 = objects[2].toString().indexOf("交通");
						String isZh = "";
						if (indexOf != -1) {//是否招行账号 	1-招行、2-其他行
							System.out.println("是否招行账号:" + 1);
							isZh = "1";
						}else { 
							System.out.println("是否招行账号:" + 2);
							isZh = "2";
						}
						//银行编号  	招商银行 cmb100,工商银行GH100,建设银行 JH100,农业银行NH100,中国银行 ZH100,浦发银行PF100,民生银行MS100,兴业银行 XY100,光大银行GD100,中信银行 ZX100,广发银行GF100,交通银行JT100
						if (indexOf != -1) {
							bank_No = "cmb100";
						}else if(indexOf1 != -1){
							bank_No = "GH100";
						}else if(indexOf2 != -1){
							bank_No = "JH100";
						}else if(indexOf3 != -1){
							bank_No = "NH100";
						}else if(indexOf4 != -1){
							bank_No = "ZH100";
						}else if(indexOf5 != -1){
							bank_No = "PF100";
						}else if(indexOf6 != -1){
							bank_No = "MS100";
						}else if(indexOf7 != -1){
							bank_No = "XY100";
						}else if(indexOf8 != -1){
							bank_No = "GD100";
						}else if(indexOf9 != -1){
							bank_No = "ZX100";
						}else if(indexOf10 != -1){
							bank_No = "GF100";
						}else if(indexOf11 != -1){
							bank_No = "JT100";
						}
						//标志 A-新增 U-修改 D-删除
						String flag = "";
						if(StringUtil.isNull(objects[3]) && StringUtil.isNull(objects[4])){
							String start = objects[3].toString();
							String end = objects[4].toString();
							int start1 = Integer.parseInt(start);
							int end1 = Integer.parseInt(end);
							int i = start1 - end1;
							if (start1 - end1 > 10000) {
								flag = "U";
							}else{
								flag = "A";
							}
						}
						datas[z][0] = objects[0];
						datas[z][1] = objects[1];
						datas[z][2] = objects[2];
						datas[z][3] = isZh;
						datas[z][4] = bank_No;
						datas[z][5] = flag;
						
//						datas[z][] = {objects[0], objects[1], objects[2], isZh, bank_No, flag};
					}else if(object.equals("2")) {
						System.out.println("对公");//comp_account_bank_name
						List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery("select tmbi.MCHT_NO, tmbi.MCHT_NM, tmsi.comp_account_bank_name, tmbi.REC_UPD_TS, tmbi.REC_CRT_TS from tbl_mcht_base_inf tmbi, TBL_MCHT_SETTLE_INF tmsi where tmbi.MCHT_NO = tmsi.MCHT_NO and substr(tmbi.REC_UPD_TS,1,8) = '" + nian + yue  + ri + "'");
						Object[] objects = list.get(0);
						int indexOf = objects[2].toString().indexOf("招商");
						int indexOf1 = objects[2].toString().indexOf("工商");
						int indexOf2 = objects[2].toString().indexOf("建设");
						int indexOf3 = objects[2].toString().indexOf("农业");
						int indexOf4 = objects[2].toString().indexOf("中国银行");
						int indexOf5 = objects[2].toString().indexOf("浦发");
						int indexOf6 = objects[2].toString().indexOf("民生");
						int indexOf7 = objects[2].toString().indexOf("兴业");
						int indexOf8 = objects[2].toString().indexOf("光大");
						int indexOf9 = objects[2].toString().indexOf("中信");
						int indexOf10 = objects[2].toString().indexOf("广发");
						int indexOf11 = objects[2].toString().indexOf("交通");
						String isZh = "";
						if (indexOf != -1) {//是否招行账号 	1-招行、2-其他行
							System.out.println("是否招行账号:" + 1);
							isZh = "1";
						}else { 
							System.out.println("是否招行账号:" + 2);
							isZh = "2";
						}
						//银行编号  	招商银行 cmb100,工商银行GH100,建设银行 JH100,农业银行NH100,中国银行 ZH100,浦发银行PF100,民生银行MS100,兴业银行 XY100,光大银行GD100,中信银行 ZX100,广发银行GF100,交通银行JT100
						if (indexOf != -1) {
							bank_No = "cmb100";
						}else if(indexOf1 != -1){
							bank_No = "GH100";
						}else if(indexOf2 != -1){
							bank_No = "JH100";
						}else if(indexOf3 != -1){
							bank_No = "NH100";
						}else if(indexOf4 != -1){
							bank_No = "ZH100";
						}else if(indexOf5 != -1){
							bank_No = "PF100";
						}else if(indexOf6 != -1){
							bank_No = "MS100";
						}else if(indexOf7 != -1){
							bank_No = "XY100";
						}else if(indexOf8 != -1){
							bank_No = "GD100";
						}else if(indexOf9 != -1){
							bank_No = "ZX100";
						}else if(indexOf10 != -1){
							bank_No = "GF100";
						}else if(indexOf11 != -1){
							bank_No = "JT100";
						}
						//标志 A-新增 U-修改 D-删除
						String flag = "";
						if(!StringUtil.isNull(objects[3]) && !StringUtil.isNull(objects[4])){
							String start = objects[3].toString();
							String end = objects[4].toString();
							double start1 = Double.parseDouble(start);
							double end1 = Double.parseDouble(end);
							double i = start1 - end1;
							if (start1 - end1 > 10000) {
								flag = "U";
							}else{
								flag = "A";
							}
						}
						datas[z][0] = objects[0];
						datas[z][1] = objects[1];
						datas[z][2] = objects[2];
						datas[z][3] = isZh;
						datas[z][4] = bank_No;
						datas[z][5] = flag;
						
					}else if(object.equals("3")) {
						System.out.println("定向");//corp_bank_name
						List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery("select tmbi.MCHT_NO, tmbi.MCHT_NM, tmsi.dir_bank_name, tmbi.REC_UPD_TS, tmbi.REC_CRT_TS from tbl_mcht_base_inf tmbi, TBL_MCHT_SETTLE_INF tmsi where tmbi.MCHT_NO = tmsi.MCHT_NO and substr(tmbi.REC_UPD_TS,1,8) = '" + nian + yue  + ri + "'");
						Object[] objects = list.get(0);
						int indexOf = objects[2].toString().indexOf("招商");
						int indexOf1 = objects[2].toString().indexOf("工商");
						int indexOf2 = objects[2].toString().indexOf("建设");
						int indexOf3 = objects[2].toString().indexOf("农业");
						int indexOf4 = objects[2].toString().indexOf("中国银行");
						int indexOf5 = objects[2].toString().indexOf("浦发");
						int indexOf6 = objects[2].toString().indexOf("民生");
						int indexOf7 = objects[2].toString().indexOf("兴业");
						int indexOf8 = objects[2].toString().indexOf("光大");
						int indexOf9 = objects[2].toString().indexOf("中信");
						int indexOf10 = objects[2].toString().indexOf("广发");
						int indexOf11 = objects[2].toString().indexOf("交通");
						String isZh = "";
						if (indexOf != -1) {//是否招行账号 	1-招行、2-其他行
							System.out.println("是否招行账号:" + 1);
							isZh = "1";
						}else { 
							System.out.println("是否招行账号:" + 2);
							isZh = "2";
						}
						//银行编号  	招商银行 cmb100,工商银行GH100,建设银行 JH100,农业银行NH100,中国银行 ZH100,浦发银行PF100,民生银行MS100,兴业银行 XY100,光大银行GD100,中信银行 ZX100,广发银行GF100,交通银行JT100
						if (indexOf != -1) {
							bank_No = "cmb100";
						}else if(indexOf1 != -1){
							bank_No = "GH100";
						}else if(indexOf2 != -1){
							bank_No = "JH100";
						}else if(indexOf3 != -1){
							bank_No = "NH100";
						}else if(indexOf4 != -1){
							bank_No = "ZH100";
						}else if(indexOf5 != -1){
							bank_No = "PF100";
						}else if(indexOf6 != -1){
							bank_No = "MS100";
						}else if(indexOf7 != -1){
							bank_No = "XY100";
						}else if(indexOf8 != -1){
							bank_No = "GD100";
						}else if(indexOf9 != -1){
							bank_No = "ZX100";
						}else if(indexOf10 != -1){
							bank_No = "GF100";
						}else if(indexOf11 != -1){
							bank_No = "JT100";
						}
						//标志 A-新增 U-修改 D-删除
						String flag = "";
						if(StringUtil.isNull(objects[3]) && StringUtil.isNull(objects[4])){
							String start = objects[3].toString();
							String end = objects[4].toString();
							int start1 = Integer.parseInt(start);
							int end1 = Integer.parseInt(end);
							int i = start1 - end1;
							if (start1 - end1 > 10000) {
								flag = "U";
							}else{
								flag = "A";
							}
						}
						datas[z][0] = objects[0];
						datas[z][1] = objects[1];
						datas[z][2] = objects[2];
						datas[z][3] = isZh;
						datas[z][4] = bank_No;
						datas[z][5] = flag;
						
					}
					z++;
				}
			}
			if (datas.length == 1 && datas[0][0] == null) {
				datas = new Object[2][6];
				datas[0][0] = "0";
				datas[0][1] = "0";
				datas[0][2] = "0";
				datas[0][3] = "0";
				datas[0][4] = "0";
				datas[0][5] = "0";
				datas[1][0] = null;
				datas[1][1] = null;
				datas[1][2] = null;
				datas[1][3] = null;
				datas[1][4] = null;
				datas[1][5] = null;
			} else{
				datas[datas.length - 1][0] = null;
				datas[datas.length - 1][1] = null;
				datas[datas.length - 1][2] = null;
				datas[datas.length - 1][3] = null;
				datas[datas.length - 1][4] = null;
				datas[datas.length - 1][5] = null;
				
			}
			reportModel.setData(datas);
			reportModel.setTitle(title);
			parameters.put("PRINT_DATE", nian + "年" + yue + "月" + ri + "日");
			reportCreator.initReportData(getJasperInputSteam("T110207.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110207RN_" + nian +  yue + ri  + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110207RN_" + nian +  yue + ri  + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN110207RN"));
			writeUsefullMsg(fileName);
			return;
			
		}else if(reportName.equals("19")) {
			System.out.println("支付机构合作行每日账户余额列表");
			title = InformationUtil.createTitles("V_", 12);
			data = new Object[tian][12];
			for(int i = 0; i < data.length ; i ++){
				int j = i+1;
				data[i][0] = j+"日";
			}
			reportModel.setData(data);
			reportModel.setTitle(title);
			String bank_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT BANK_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			String account_name = CommonFunction.getCommQueryDAO().findCountBySQLQuery("SELECT ACCOUNT_NAME FROM TBL_BANKNO_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'");
			parameters.put("BANK_NAME", bank_name);
			parameters.put("ACCOUNT_NAME", account_name);
			parameters.put("BANK_ACCOUNT", bankAccount);
			parameters.put("PRINT_DATE", nian+"年"+yue+"月");
			reportCreator.initReportData(getJasperInputSteam("T110208.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110208RN_" + nian + yue + ".xls";
			else if(Constants.REPORT_PDFMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN110208RN_" + nian + yue + ".pdf";
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN110208RN"));
			writeUsefullMsg(fileName);
			return;
			
		}
		
		
	}
	//下个月
	public static String getNextMonth(String dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = formatter.parse(dates);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    calendar.add(Calendar.MONTH, 1);
	    date = calendar.getTime();
	    String dateString = formatter.format(date);
		return dateString;
	}
	//前一天
	public static String getBeforeDay(String dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = formatter.parse(dates);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		String dateString = formatter.format(date);
		return dateString;
	}
	//下一天
	public static String getNextDay(String dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = formatter.parse(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		String dateString = formatter.format(date);
		return dateString;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
