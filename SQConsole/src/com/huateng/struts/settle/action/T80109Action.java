package com.huateng.struts.settle.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import tools.SFTPClientProvider;

import com.csvreader.CsvReader;
import com.huateng.bo.base.T10202BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;
import com.jcraft.jsch.ChannelSftp;

public class T80109Action  extends BaseSupport {
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger(T80109Action.class);
	T10202BO t10202BO = (T10202BO) ContextUtil.getBean("T10202BO");
	
	private String date;
	private String reportName;
	public boolean flag;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	protected String genSql() {
		return null;
	}
	public static void main(String[] args) throws Exception {
		
		String str = "C:\\Users\\Li\\Desktop\\TBACCTXNSTLM_20170915.csv";
		Object[][] arrRead = arrRead(str);
		for (int i = 0; i < arrRead.length; i++) {
			for (int j = 0; j < arrRead[i].length; j++) {
				System.out.println(arrRead[i]);
			}
		}
	}
	public static Object [][] arrRead(String fileName) throws Exception{
		 File file = new File(fileName);
	     InputStream in = null;
	     in = new FileInputStream(file);
	     Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	     Object [] [] data = null;
	     String[] arr = {"交易日期", "交易时间", "交易码", "终端流水号", "核心流水号", "卡号", "金额(元)", "手续费(元)", "商户号", "商户名", "终端号", "货币代码", "云卡卡号"};
	     int i = 0;
		try {
			CsvReader csvReader = new CsvReader(in, Charset.forName("GBK"));// 创建CSV读对象
			csvReader.readHeaders();
			while (csvReader.readRecord()) {
				ArrayList<String> fromCSVLinetoArray = fromCSVLinetoArray(csvReader.getRawRecord());
				String merchantNo = fromCSVLinetoArray.get(8);
				String transactionCode = fromCSVLinetoArray.get(2);
				if (transactionCode.trim().equals("1105")) {
					transactionCode = "1105-消费";
				} else if(transactionCode.trim().equals("5205")) {
					transactionCode = "5205-退货";
				} else if(transactionCode.trim().equals("3105")) {
					transactionCode = "3105-消费撤销";
				} 
				String sum = fromCSVLinetoArray.get(6);
				String factorage = fromCSVLinetoArray.get(7);
				
//				String merchantName = "";
				String merchantName = CommonFunction.getCommQueryDAO().findCountBySQLQuery("select trim(MCHT_NM) from TBL_MCHT_BASE_INF where MCHT_NO = '" + merchantNo + "'");
				fromCSVLinetoArray.add(9, merchantName);
				fromCSVLinetoArray.set(6, Double.parseDouble(sum)/100 + "");
				fromCSVLinetoArray.set(7, Double.parseDouble(factorage)/100 + "");
				fromCSVLinetoArray.set(2, transactionCode);
				map.put(i+"", fromCSVLinetoArray);
				i++;
			}
			data = new Object[map.size()+1][];
			data[0] = arr;
			for (String key : map.keySet()) {
				ArrayList<String> arrayList = map.get(key);
				Object[] array = (Object[])arrayList.toArray();
				data[Integer.parseInt(key) + 1] = array;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 把CSV文件的一行转换成字符串数组。不指定数组长度。
	 */
	public static ArrayList<String> fromCSVLinetoArray(String source) {
		if (source == null || source.length() == 0) {
			return new ArrayList<String>();
		}
		int currentPosition = 0;
		int maxPosition = source.length();
		int nextComma = 0;
		ArrayList<String> rtnArray = new ArrayList<String>();
		while (currentPosition < maxPosition) {
			nextComma = nextComma(source, currentPosition);
			rtnArray.add(nextToken(source, currentPosition, nextComma));
			currentPosition = nextComma + 1;
			if (currentPosition == maxPosition) {
				rtnArray.add("");
			}
		}
		return rtnArray;
	}
	/**
	 * 查询下一个逗号的位置。
	 * @param source 文字列
	 * @param st 检索开始位置
	 * @return 下一个逗号的位置。
	 */
	private static int nextComma(String source, int st) {
		int maxPosition = source.length();
		boolean inquote = false;
		while (st < maxPosition) {
			char ch = source.charAt(st);
			if (!inquote && ch == ',') {
				break;
			} else if ('"' == ch) {
				inquote = !inquote;
			}
			st++;
		}
		return st;
	}
	/**
	 * 取得下一个字符串
	 */
	private static String nextToken(String source, int st, int nextComma) {
		StringBuffer strb = new StringBuffer();
		int next = st;
		while (next < nextComma) {
			char ch = source.charAt(next++);
			if (ch == '"') {
				if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {
					strb.append(ch);
					next++;
				}
			} else {
				strb.append(ch);
			}
		}
		return strb.toString();
	}
	
	
	/**
	 * 清算文件下载
	 * @return
	 */
	public String report(){
		
		try {
			if (StringUtil.isNull(date)) {
				//writeNoDataMsg("请选择数据");
				//return;
				return returnService(Constants.ERR_ATTRIBUTE);
			}
			//reportType = "EXCEL";
			CstSysParamPK cstSysParamPK=new CstSysParamPK();
			cstSysParamPK.setKey("00000002");
			cstSysParamPK.setOwner("CHANNELREPORT");
			CstSysParam cstSysParam=t10202BO.get(cstSysParamPK);
			//TBACCTXNSTLM_YYYYMMDD.csv改为TBACCTXNSTLM_YYYYMMDD.tar.gz
			String filename1=cstSysParam.getDescr();//报表名称
			String path1 = SysParamUtil.getParam("FILE_PATH_SETTLE_REPORT")+date+"/";//路径
			String birId=getOperator().getOprBrhId();
			if(path1.contains("xxxx")){   
			   path1=path1.replace("xxxx", birId);
			}
			if(path1.contains("YYYYMMDD")){   
				   path1=path1.replace("YYYYMMDD", date);
			}
			filename1=filename1.replace("YYYYMMDD", date);
			filename1=filename1.replace("$$$$", operator.getOprBrhId());
			String path = path1+filename1;
			path = path.replace("\\", "/");
			log("GET FILE:" + path);
			//创建STFP连接
			SFTPClientProvider provider = new SFTPClientProvider();
			ChannelSftp sftp = provider.connect(SysParamUtil.getParam("FTPSERVERIP"), Integer.parseInt(SysParamUtil.getParam("FTPPORT")), SysParamUtil.getParam("FTPUSERNAME"), SysParamUtil.getParam("FTPPASSWORD"));
			
			try {
				//调用下载文件方法
				flag = provider.download(path1, path1+filename1, "C:\\"+filename1, sftp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return returnService("SFTP 到报表服务器异常");
			}
			if(flag){
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "C:\\"+filename1);
			}else{
				return returnService("您所请求的报表文件不存在!");
			}
			//path：/home/posp/online/file/20180620/TBACCTXNSTLM_20180620.csv
			/*File file = new File(path);
			if (!file.exists()) {
				writeNoDataMsg("文件不存在！");
				return;
			}
			Object[][] arrRead = arrRead(path);
			title = InformationUtil.createTitles("V_", 13);
			reportModel.setData(arrRead);
			reportModel.setTitle(title);
			reportCreator.initReportData(getJasperInputSteam("T80109.jasper"), parameters,  reportModel.wrapReportDataSource(), getReportType());
			if(Constants.REPORT_EXCELMODE.equals(reportType))
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + filename1.replace(".csv", ".xls");
			outputStream = new FileOutputStream(fileName);
			reportCreator.exportReport(outputStream, filename1.replace(".csv", ""));
			writeUsefullMsg(fileName);*/
		} catch (Exception e) {
			//writeNoDataMsg("操作失败");
			//log.error("下载清算文件下载失败", e);
			e.printStackTrace();
			return returnService("对不起，本次操作失败!",e);
		}
		
	}
	

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}

}
