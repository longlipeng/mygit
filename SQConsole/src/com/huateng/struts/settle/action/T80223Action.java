package com.huateng.struts.settle.action;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.FileOperation;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * 银行出款表
 * 
 * @author crystal
 *
 */
public class T80223Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final long lineABCSAME=3000L; //农行同行一个文件条数
	private static final long lineABCOTHER=150L; //农行同行一个文件条数

	//结算日期
	private String instDate;
	private String accFlag;
	private String batchNum;
	private String bank;
	private String singleAmt;
	private String destId;
	private String destIdall;
	private String destIdkuaiqian;
	private String destIdhanxin;
	private String idFormats;
	
	

	@Override
	protected void reportAction() throws Exception {
		if ("1".equals(idFormats)) {
			if("1".equals(bank)){
				reportABC();
				return;
			}else if("2".equals(bank)){
				reportCMBC();
				return;
			}else if("3".equals(bank)){
				reportCMB();
				return;
			}
		}else if("2".equals(idFormats)){
			reportCMBXLS();
			return;
		}
	}
   /***
    * 民生银行
    */
	private void reportCMBC()  throws Exception{
		//对私
		if("1".equals(accFlag)){
			reportCMBC4Pri();
		}else if("2".equals(accFlag)){//对公
			reportCMBC4Pub();
		}
		
	}
	/***
	 * 招商银行
	 */
	private void reportCMB()  throws Exception{
		//对私
		if("1".equals(accFlag)){
			reportCMB4Pri();
		}else if("2".equals(accFlag)){//对公
			reportCMB4Pub();
		}
		
	}
	/***
	 * 招商银行XLS
	 */
	private void reportCMBXLS()  throws Exception{
		//对私
		if("1".equals(accFlag)){
			reportCMB4PriXLS();
		}else if("2".equals(accFlag)){//对公
			reportCMB4PubXLS();
		}
		
	}
    
	//对公
	private void reportCMBC4Pub()  throws Exception{
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMBC_" +
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
	   fileName=reportCMBC1();
	   if(fileName!=null){
		   FileOperation.zipFiles(fileName, fileZip);
		   writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
	
	}
	//对公
	private void reportCMB4Pub()  throws Exception{
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_" +
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
		fileName=reportCMB1();
		if(fileName!=null){
			FileOperation.zipFiles(fileName, fileZip);
			writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
	}
	//对公
	private void reportCMB4PubXLS()  throws Exception{
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_" +
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
		fileName=reportCMB1XLS();
		if(fileName!=null){
			FileOperation.zipFiles(fileName, fileZip);
			writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
	}
   //对私
	private void reportCMBC4Pri()  throws Exception{
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMBC_" +
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
	   String files=null;	  
	   //跨行
	   String fileName4Pri=reportCMBC1();
	   if(fileName4Pri!=null){
			   files=fileName4Pri;  
		  
	   }
	   //行内对私
	   String fileName4SamePri=reportCMBC2();
	   if(fileName4SamePri!=null){
		   if(files!=null){
			   files=files+","+fileName4SamePri;
		   }else{
			   files=fileName4SamePri;  
		   }
		  
	   }
	   
	   if(files!=null){
		   FileOperation.zipFiles(files, fileZip);
		   writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
	
}
	//对私
	private void reportCMB4Pri()  throws Exception{
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_" + operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
		String files=null;	  
		//跨行
		String fileName4Pri=reportCMB1();
		if(fileName4Pri!=null){
			files=fileName4Pri;  
			
		}
		/*//行内对私
		String fileName4SamePri=reportCMB2();
		if(fileName4SamePri!=null){
			if(files!=null){
				files=files+","+fileName4SamePri;
			}else{
				files=fileName4SamePri;  
			}
			
		}*/
		
		if(files!=null){
			FileOperation.zipFiles(files, fileZip);
			writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
	}
	//对私
	private void reportCMB4PriXLS()  throws Exception{
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_" + operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
		String files=null;	  
		//跨行
		String fileName4Pri=reportCMB1XLS();
		if(fileName4Pri!=null){
			files=fileName4Pri;  
			
		}
		//行内对私
		/*String fileName4SamePri=reportCMB2XLS();
		if(fileName4SamePri!=null){
			if(files!=null){
				files=files+","+fileName4SamePri;
			}else{
				files=fileName4SamePri;  
			}
		}*/
		
		if(files!=null){
			FileOperation.zipFiles(files, fileZip);
			writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
	}
    /**
     * 民生同行对私
     */
	private String reportCMBC2() throws Exception{
		title = InformationUtil.createTitles("V_", 3);
		data = reportCreator.process(genSqlCMBC2(), title);
		if(data.length == 0) {
			return null;
		}
		String totalAmt = getTotalAmt1();
		StringBuffer reportBuffer = new StringBuffer();
		reportBuffer.append("ATNU:0334503\r\nMICN:0\r\n").append("CUNM:武汉天喻信息产业股份有限公司\r\n").append("MIAC:691586510\r\n")
		.append("EYMD:1\r\n").append("TOAM:").append(totalAmt).append("\r\nCOUT:").append(data.length)
		.append("\r\n").append("--------------------------------------------------------\r\n");
		for(int i=0;i<data.length;i++){
			reportBuffer.append(data[i][1].toString().trim()).append("|").append(data[i][0].toString().trim())
			.append("|").append(data[i][2].toString().trim()).append("|货款|");
		}
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMBC_"+"02_"+
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".txt";
		FileOperation.writeTxtFile(reportBuffer.toString(), fileName);
		return fileName;
	}
	/**
	 * 招行同行对私
	 */
	private String reportCMB2() throws Exception{
		title = InformationUtil.createTitles("V_", 3);
		data = reportCreator.process(genSqlCMB2(), title);
		if(data.length == 0) {
			return null;
		}
		String totalAmt = getTotalAmt();
		StringBuffer reportBuffer = new StringBuffer();
		reportBuffer.append("ATNU:0334503\r\nMICN:0\r\n").append("CUNM:武汉天喻信息产业股份有限公司\r\n").append("MIAC:691586510\r\n")
		.append("EYMD:1\r\n").append("TOAM:").append(totalAmt).append("\r\nCOUT:").append(data.length)
		.append("\r\n").append("--------------------------------------------------------\r\n");
		for(int i=0;i<data.length;i++){
			reportBuffer.append(data[i][1].toString().trim()).append("|").append(data[i][0].toString().trim())
			.append("|").append(data[i][2].toString().trim()).append("|货款|");
		}
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMBC_"+"02_"+
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".txt";
		FileOperation.writeTxtFile(reportBuffer.toString(), fileName);
		return fileName;
	}
	/**
	 * 招行同行对私XLS
	 */
	private String reportCMB2XLS() throws Exception{
		title = InformationUtil.createTitles("V_", 3);
		data = reportCreator.process(genSqlCMBC2(), title);
		if(data.length == 0) {
			return null;
		}
		String totalAmt = getTotalAmt1();
		StringBuffer reportBuffer = new StringBuffer();
		reportBuffer.append("ATNU:0334503\r\nMICN:0\r\n").append("CUNM:武汉天喻信息产业股份有限公司\r\n").append("MIAC:691586510\r\n")
		.append("EYMD:1\r\n").append("TOAM:").append(totalAmt).append("\r\nCOUT:").append(data.length)
		.append("\r\n").append("--------------------------------------------------------\r\n");
		for(int i=0;i<data.length;i++){
			reportBuffer.append(data[i][1].toString().trim()).append("|").append(data[i][0].toString().trim())
			.append("|").append(data[i][2].toString().trim()).append("|货款|");
		}
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_"+"02_"+
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".txt";
		FileOperation.writeTxtFile(reportBuffer.toString(), fileName);
		return fileName;
	}
	private String reportCMBC1()  throws Exception{
		title = InformationUtil.createTitles("V_", 5);
		data = reportCreator.process(genSqlCMBC1(), title);
		if(data.length == 0) {
			return null;
		}
		String totalAmt = getTotalAmt();
		StringBuffer reportBuffer = new StringBuffer();
		reportBuffer.append("审核方式（文件类型）（必输):1\r\n");
		reportBuffer.append("总金额（必输）:").append(totalAmt).append("\r\n");
		reportBuffer.append("总笔数（必输）:").append(data.length).append("\r\n");
		reportBuffer.append("----------------------------------------------------------\r\n");
		reportBuffer.append("制单类型（必输）|企业自制凭证号|客户号（必输）|预约标志（默认填0）|付款账号（必输）|交易金额（必输）|收款账号（必输）|收款人姓名（必输）|收款账户类型|子客户号|子付款账号|子付款账户名|子付款账户开户行名|用途|汇路|是否通知收款人|手机号码|邮箱|支付行号&支付行名称|\r\n");
		for(int i=0;i<data.length;i++){
			String tmp1="11";
//			String bankCode="";
			BigDecimal sumAmt = new BigDecimal(data[i][0].toString().trim());
			BigDecimal fMillion = BigDecimal.valueOf(50000);
			if(sumAmt.compareTo(fMillion)==-1){//网银互联
				tmp1="11";
				/*String sqlBankCode="select bank_code from tbl_bank_code where bank_flag='0' and instr('"+data[i][3].toString().trim()+"',trim(bank_name))>0 ";
				List<Object> bankCodeList =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlBankCode);
				if(bankCodeList!=null&&bankCodeList.size()>0){
					bankCode = bankCodeList.get(0).toString();					
				}*/
			}else{//大额
				tmp1="7";
				//
			}
			if("1".equals(accFlag)){//对私跨行
				reportBuffer.append("2||2200253908|0|691586510|").append(data[i][0].toString().trim()).append("|").append(data[i][1].toString().trim()).append("|").append(data[i][2].toString().trim()).append("|1|||||货款|");
				
			}else{//对公
				if(data[i][3].toString().indexOf("")!=-1){//行内
					reportBuffer.append("1");
				}else{//跨行
					reportBuffer.append("2");
				}
				reportBuffer.append("||2200253908|0|691586510|").append(data[i][0].toString().trim()).append("|").append(data[i][1].toString().trim()).append("|").append(data[i][2].toString().trim()).append("|0|||||货款|");
				
			}
			reportBuffer.append(tmp1).append("|0|||").append(data[i][4].toString().trim()).append("&").append(data[i][3].toString().trim()).append("|\r\n");
		}
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMBC_"+"01_"+
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".txt";
		FileOperation.writeTxtFile(reportBuffer.toString(), fileName);
		return fileName;
	}
	private String reportCMB1()  throws Exception{
		title = InformationUtil.createTitles("V_", 9);
		data = reportCreator.process(genSqlCMB1(), title);
		if(data.length == 0) {
			return null;
		}
		String totalAmt = getTotalAmt();
		StringBuffer reportBuffer = new StringBuffer();
//		reportBuffer.append("审核方式（文件类型）（必输):1\r\n");
//		reportBuffer.append("总金额（必输）:").append(totalAmt).append("\r\n");
//		reportBuffer.append("总笔数（必输）:").append(data.length).append("\r\n");
//		reportBuffer.append("----------------------------------------------------------\r\n");
		reportBuffer.append("业务参考号,收款人编号,收款人帐号,收款人名称,收方开户支行,收款人所在省,收款人所在市,收方邮件地址,收方移动电话,币种,付款分行,结算方式,业务种类,付方帐号,付方虚拟户编号,期望日,期望时间,用途,金额,收方联行号,收方开户银行,业务摘要\r\n");
		for(int i=0;i<data.length;i++){
			String tmp1="11";
////			String bankCode="";
//			BigDecimal sumAmt = new BigDecimal(data[i][0].toString().trim());
//			BigDecimal fMillion = BigDecimal.valueOf(50000);
//			if(sumAmt.compareTo(fMillion)==-1){//网银互联
//				tmp1="11";
//				/*String sqlBankCode="select bank_code from tbl_bank_code where bank_flag='0' and instr('"+data[i][3].toString().trim()+"',trim(bank_name))>0 ";
//				List<Object> bankCodeList =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlBankCode);
//				if(bankCodeList!=null&&bankCodeList.size()>0){
//					bankCode = bankCodeList.get(0).toString();					
//				}*/
//			}else{//大额
//				tmp1="7";
//				//
//			}
			if("1".equals(accFlag)){//对私跨行
//				reportBuffer.append("2||2200253908|0|691586510|").append(data[i][0].toString().trim()).append("|").append(data[i][1].toString().trim()).append("|").append(data[i][2].toString().trim()).append("|1|||||货款|");
				reportBuffer.append(data[i][0].toString().trim()).append(",").append(data[i][1].toString().trim()).append(",").append(data[i][2].toString().trim()).append(",").append(data[i][3].toString().trim()).append(",").append(data[i][4].toString().trim()).append(",").append("上海,").append("上海,").append(" ,").append(" ,").append("人民币,上海,普通,普通汇兑,").append("121916795010801 ,").append(" ,").append(data[i][5].toString().trim()).append(",").append("080000,").append("货款,").append(data[i][6].toString().trim()).append(",").append(data[i][7].toString().trim()).append(",").append(data[i][8].toString().trim()).append(",").append(" ,");

				
			}else{//对公
//				if(data[i][3].toString().indexOf("民生银行")!=-1){//行内
//					reportBuffer.append("1");
//				}else{//跨行
//					reportBuffer.append("2");
//				}
//				reportBuffer.append("||2200253908|0|691586510|").append(data[i][0].toString().trim()).append("|").append(data[i][1].toString().trim()).append("|").append(data[i][2].toString().trim()).append("|0|||||货款|");																																							//付方帐号
				reportBuffer.append(data[i][0].toString().trim()).append(",").append(data[i][1].toString().trim()).append(",").append(data[i][2].toString().trim()).append(",").append(data[i][3].toString().trim()).append(",").append(data[i][4].toString().trim()).append(",").append("上海,").append("上海,").append(" ,").append(" ,").append("人民币,上海,普通,普通汇兑,").append("121916795010801,").append(" ,").append(data[i][5].toString().trim()).append(",").append("080000,").append("货款,").append(data[i][6].toString().trim()).append(",").append(data[i][7].toString().trim()).append(",").append(data[i][8].toString().trim()).append(",").append(" ,");
				
			}
//			reportBuffer.append(tmp1).append("|0|||").append(data[i][4].toString().trim()).append("&").append(data[i][3].toString().trim()).append("|\r\n");
		}
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_"+"01_"+
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".txt";
		FileOperation.writeTxtFile(reportBuffer.toString(), fileName);
		return fileName;
	}
	private String reportCMB1XLS()  throws Exception{
		title = InformationUtil.createTitles("V_", 9);
		data = reportCreator.process(genSqlCMB1(), title);
		if(data.length == 0) {
			return null;
		}
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("招行");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("业务参考号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("收款人编号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("收款人帐号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("收款人名称");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);  
        cell.setCellValue("收方开户支行");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);  
        cell.setCellValue("收款人所在省");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);  
        cell.setCellValue("收款人所在市");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);  
        cell.setCellValue("收方邮件地址");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);  
        cell.setCellValue("收方移动电话");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);  
        cell.setCellValue("币种");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);  
        cell.setCellValue("付款分行");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);  
        cell.setCellValue("结算方式");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);  
        cell.setCellValue("业务种类");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 13);  
        cell.setCellValue("付方帐号");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 14);  
        cell.setCellValue("付方虚拟户编号");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 15);  
        cell.setCellValue("期望日");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 16);  
        cell.setCellValue("期望时间");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 17);  
        cell.setCellValue("用途");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 18);  
        cell.setCellValue("金额");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 19);  
        cell.setCellValue("收方联行号");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 20);  
        cell.setCellValue("收方开户银行");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 21);  
        cell.setCellValue("业务摘要");  
        cell.setCellStyle(style);
        for(int i=0;i<data.length;i++){
        	if("1".equals(accFlag)){//对私跨行
//				reportBuffer.append(data[i][0].toString().trim()).append(",").
//        		append(data[i][1].toString().trim()).append(",").
//        		append(data[i][2].toString().trim()).append(",").
//        		append(data[i][3].toString().trim()).append(",").
//        		append(data[i][4].toString().trim()).append(",").
//        		append("上海,").5
//        		append("上海,").6
//        		append(" ,").7
//        		append(" ,").8
//        		append("人民币 9,上海 10,普通 11,普通汇兑 12,").
//        		append("付方帐号  13,").
//        		append(" ,").14
//        		append(data[i][5].toString().trim()).append(",").15
//        		append("080000,").16
//        		append("货款,").17
//        		append(data[i][6].toString().trim()).append(",").18
//        		append(data[i][7].toString().trim()).append(",").
//        		append(data[i][8].toString().trim()).append(",").append(" ,");
        		row = sheet.createRow((int) i + 1);
        		row.createCell((short) 0).setCellValue(data[i][0].toString().trim());
        		row.createCell((short) 1).setCellValue(data[i][1].toString().trim());
        		row.createCell((short) 2).setCellValue(data[i][2].toString().trim());
        		row.createCell((short) 3).setCellValue(data[i][3].toString().trim());
        		row.createCell((short) 4).setCellValue(data[i][4].toString().trim());
        		row.createCell((short) 5).setCellValue("上海");
        		row.createCell((short) 6).setCellValue("上海");
        		row.createCell((short) 7).setCellValue("");
        		row.createCell((short) 8).setCellValue("");
        		row.createCell((short) 9).setCellValue("人民币");
        		row.createCell((short) 10).setCellValue("上海");
        		row.createCell((short) 11).setCellValue("普通");
        		row.createCell((short) 12).setCellValue("普通汇兑");
        		row.createCell((short) 13).setCellValue("121916795010801");
        		row.createCell((short) 14).setCellValue("");
        		row.createCell((short) 15).setCellValue(data[i][5].toString().trim());
        		row.createCell((short) 16).setCellValue("080000");
        		row.createCell((short) 17).setCellValue("货款");
        		row.createCell((short) 18).setCellValue(data[i][6].toString().trim());
        		row.createCell((short) 19).setCellValue(data[i][7].toString().trim());
        		row.createCell((short) 20).setCellValue(data[i][8].toString().trim());
        		
        	}else{//对公
        		row = sheet.createRow((int) i + 1);
        		row.createCell((short) 0).setCellValue(data[i][0].toString().trim());
        		row.createCell((short) 1).setCellValue(data[i][1].toString().trim());
        		row.createCell((short) 2).setCellValue(data[i][2].toString().trim());
        		row.createCell((short) 3).setCellValue(data[i][3].toString().trim());
        		row.createCell((short) 4).setCellValue(data[i][4].toString().trim());
        		row.createCell((short) 5).setCellValue("上海");
        		row.createCell((short) 6).setCellValue("上海");
        		row.createCell((short) 7).setCellValue("");
        		row.createCell((short) 8).setCellValue("");
        		row.createCell((short) 9).setCellValue("人民币");
        		row.createCell((short) 10).setCellValue("上海");
        		row.createCell((short) 11).setCellValue("普通");
        		row.createCell((short) 12).setCellValue("普通汇兑");
        		row.createCell((short) 13).setCellValue("121916795010801");
        		row.createCell((short) 14).setCellValue("");
        		row.createCell((short) 15).setCellValue(data[i][5].toString().trim());
        		row.createCell((short) 16).setCellValue("080000");
        		row.createCell((short) 17).setCellValue("货款");
        		row.createCell((short) 18).setCellValue(data[i][6].toString().trim());
        		row.createCell((short) 19).setCellValue(data[i][7].toString().trim());
        		row.createCell((short) 20).setCellValue(data[i][8].toString().trim());
			}
        }
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_"+"01_"+operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".xls";
		FileOutputStream fout = new FileOutputStream(fileName);  
		wb.write(fout);  
        fout.close();  
//		FileOperation.createFile();
		/*String totalAmt = getTotalAmt();
		StringBuffer reportBuffer = new StringBuffer();
		reportBuffer.append("业务参考号,收款人编号,收款人帐号,收款人名称,收方开户支行,收款人所在省,收款人所在市,收方邮件地址,收方移动电话,币种,付款分行,结算方式,业务种类,付方帐号,付方虚拟户编号,期望日,期望时间,用途,金额,收方联行号,收方开户银行,业务摘要\r\n");
		for(int i=0;i<data.length;i++){
			String tmp1="11";
			if("1".equals(accFlag)){//对私跨行
				reportBuffer.append(data[i][0].toString().trim()).append(",").append(data[i][1].toString().trim()).append(",").append(data[i][2].toString().trim()).append(",").append(data[i][3].toString().trim()).append(",").append(data[i][4].toString().trim()).append(",").append("上海,").append("上海,").append(" ,").append(" ,").append("人民币,上海,普通,普通汇兑,").append("付方帐号 ,").append(" ,").append(data[i][5].toString().trim()).append(",").append("080000,").append("货款,").append(data[i][6].toString().trim()).append(",").append(data[i][7].toString().trim()).append(",").append(data[i][8].toString().trim()).append(",").append(" ,");
			}else{//对公
				reportBuffer.append(data[i][0].toString().trim()).append(",").append(data[i][1].toString().trim()).append(",").append(data[i][2].toString().trim()).append(",").append(data[i][3].toString().trim()).append(",").append(data[i][4].toString().trim()).append(",").append("上海,").append("上海,").append(" ,").append(" ,").append("人民币,上海,普通,普通汇兑,").append("付方帐号 ,").append(" ,").append(data[i][5].toString().trim()).append(",").append("080000,").append("货款,").append(data[i][6].toString().trim()).append(",").append(data[i][7].toString().trim()).append(",").append(data[i][8].toString().trim()).append(",").append(" ,");
			}
		}
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "CMB_"+"01_"+operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".txt";
		FileOperation.writeTxtFile(reportBuffer.toString(), fileName);*/
		return fileName;
	}
   
	/**
	 * 总金额
	 * @return
	 */
	private String getTotalAmt() {
		StringBuffer whereSql = new StringBuffer();	
		if(isNotEmpty(instDate)) {
			whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
			if("1".equals(accFlag)){//对私删除同行
				whereSql.append(" and a.BANK_NAME not like '%招商银行%' ");	
			}
		}
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
		}
		
		String countSql = "SELECT SUM(a.SUM_AMT) FROM TBL_MCHT_SUMRZ_INF a" +
		" where 1=1 and a.SUM_AMT<>0 "
		+ whereSql.toString();
		List<Object> totalAmtList =CommonFunction.getCommQueryDAO().findBySQLQuery(countSql);
		if(totalAmtList!=null&&totalAmtList.size()>0){
			return totalAmtList.get(0).toString();
			
		}else{
			return "0.00";
		}
	}
	
	/**
	 * 民生同行对私总金额
	 * @return
	 */
	private String getTotalAmt1() {
		StringBuffer whereSql = new StringBuffer();	
		if(isNotEmpty(instDate)) {
			whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
			if("1".equals(accFlag)){
				whereSql.append(" and a.BANK_NAME like '%%' ");	
			}
		}
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
		}
		
		String countSql = "SELECT SUM(a.SUM_AMT) FROM TBL_MCHT_SUMRZ_INF a" +
		" where 1=1 and a.SUM_AMT<>0 "
		+ whereSql.toString();
		List<Object> totalAmtList =CommonFunction.getCommQueryDAO().findBySQLQuery(countSql);
		if(totalAmtList!=null&&totalAmtList.size()>0){
			return totalAmtList.get(0).toString();
			
		}else{
			return "0.00";
		}
	}
	
	//民生同行对私
		private String genSqlCMBC2() {
			StringBuffer whereSql = new StringBuffer();	
			if(isNotEmpty(instDate)) {
				whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
			}
			if(isNotEmpty(accFlag)) {
				whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
				if("1".equals(accFlag)){
					whereSql.append(" and a.BANK_NAME like '%%' ");	
				}
			}
			if(isNotEmpty(batchNum)) {
				whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
			}
			//FIXME
			if(isNotEmpty(destId)&& !"*".equals(destIdall)){
				whereSql.append(" and a.DEST_ID in ("+destId+")  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME ");
			}
			if("*".equals(destIdall)){
				whereSql.append("  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME ");
			}
			
			String sql = "SELECT sum(a.SUM_AMT),a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME FROM TBL_MCHT_SUMRZ_INF a " +
			" where 1=1 and a.SUM_AMT<>0 "
			+ whereSql.toString();
	        sql += " order by a.MCHT_NO";	
			return sql;
		}
		//招行同行对私
		private String genSqlCMB2() {
			StringBuffer whereSql = new StringBuffer();	
			if(isNotEmpty(instDate)) {
				whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
			}
			if(isNotEmpty(accFlag)) {
				whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
				if("3".equals(accFlag)){
					whereSql.append(" and a.BANK_NAME like '%招商银行%' ");	
				}
			}
			if(isNotEmpty(batchNum)) {
				whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
			}
			//FIXME
			if(isNotEmpty(destId)&& !"*".equals(destIdall)){
				whereSql.append(" and a.DEST_ID in ("+destId+")  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME, a.ACCT_BANK_CODE,a.inst_date ");
			}
			if("*".equals(destIdall)){
				whereSql.append("  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME, a.ACCT_BANK_CODE,a.inst_date ");
			}
			
			String sql = " SELECT substr(a.inst_date,5,8)||substr(a.MCHT_NO,10,15)||substr(a.SETTLE_ACC_NUM,1,4), substr(a.MCHT_NO,11,15), a.SETTLE_ACC_NUM, a.SETTLE_ACC_NAME, a.BANK_NAME, a.inst_date, SUM(a.SUM_AMT), a.ACCT_BANK_CODE, a.BANK_NAME FROM TBL_MCHT_SUMRZ_INF a WHERE 1=1 AND a.SUM_AMT<>0 "
					+ whereSql.toString();
			sql += " order by a.MCHT_NO";	
			return sql;
		}
		//删选掉同行对私
		private String genSqlCMBC1() {
			StringBuffer whereSql = new StringBuffer();	
			if(isNotEmpty(instDate)) {
				whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
			}
			if(isNotEmpty(accFlag)) {
				whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
				if("1".equals(accFlag)){//对私删除同行
					whereSql.append(" and a.BANK_NAME not like '%%' ");	
				}
			}
			if(isNotEmpty(batchNum)) {
				whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
			}
			
			//FIXME
			if(isNotEmpty(destId)&& !"*".equals(destIdall)){
				whereSql.append(" and a.DEST_ID in ("+destId+")  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME,a.ACCT_BANK_CODE ");
			}
			if("*".equals(destIdall)){
				whereSql.append("  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME,a.ACCT_BANK_CODE ");
			}
			
			String sql = "SELECT sum(a.SUM_AMT),a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME,a.ACCT_BANK_CODE FROM TBL_MCHT_SUMRZ_INF a" +
			" where 1=1 and a.SUM_AMT<>0 "
			+ whereSql.toString();
	       sql += " order by a.MCHT_NO";	
			return sql;
		}	
	//删选掉同行对私
	private String genSqlCMB1() {
		StringBuffer whereSql = new StringBuffer();	
		if(isNotEmpty(instDate)) {
			whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
			if("3".equals(accFlag)){//对私删除同行
				whereSql.append(" and a.BANK_NAME not like '%招商银行%' ");	
			}
		}
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
		}
		
		//FIXME
		if(isNotEmpty(destId)&& !"*".equals(destIdall)){
			whereSql.append(" and a.DEST_ID in ("+destId+")  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME,a.ACCT_BANK_CODE,a.inst_date ");
		}
		if("*".equals(destIdall)){
			whereSql.append("  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.BANK_NAME,a.ACCT_BANK_CODE,a.inst_date ");
		}
		
		String sql = " SELECT substr(a.inst_date,5,8)||substr(a.MCHT_NO,10,15)||substr(a.SETTLE_ACC_NUM,1,4), substr(a.MCHT_NO,11,15), a.SETTLE_ACC_NUM, a.SETTLE_ACC_NAME, a.BANK_NAME, a.inst_date, SUM(a.SUM_AMT), a.ACCT_BANK_CODE, a.BANK_NAME FROM TBL_MCHT_SUMRZ_INF a WHERE 1=1 AND a.SUM_AMT<>0  "
		+ whereSql.toString();
       sql += " order by a.MCHT_NO";	
		return sql;
	}

	//农行
	private void reportABC() throws Exception {
		
		int dataLength = 0;//总记录
		String fileZip = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "ABC_" +
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +".zip";
		String files = null;
		String reportTitle = "";
		long lineABC;
		double totalAmt=0d;//金额合计
		//农行出款z=0（同行），z=1(跨行)
		for(int z = 0;z<2;z++){
			String flag;
		if(z==0){
			title = InformationUtil.createTitles("V_", 3);
			flag="sameBank";
			reportTitle = "序号,收款人账号,收款人姓名,出款金额,备注";
			lineABC = lineABCSAME;
			data = reportCreator.process(genSqlSameBank(), title);
		}else{
			title = InformationUtil.createTitles("V_", 4);
			flag="otherBank";
			reportTitle = "序号,收款人账号,收款人姓名,大额行号，出款金额,备注";
			lineABC = lineABCOTHER;
			data = reportCreator.process(genSqlOtherBank(), title);
		}		
		int dataSameBank = data.length;
		dataLength += dataSameBank;
		if(dataSameBank>0){			
			StringBuffer reportData = new StringBuffer();
			reportData.append(reportTitle).append("\r\n");
			int lineNumber = 0;
			int seqNum=1;//序号
			for(int i = 0;i<dataSameBank;i++){
				double sumAmt = Double.parseDouble(data[i][z+2].toString());
				//设单笔最大金额
				if(isNotEmpty(singleAmt)){
					
					double singleAmtD=Double.parseDouble(singleAmt);
					int fileSum = (int)Math.floor(sumAmt/singleAmtD);//取整
					int cirNum = fileSum;
						//写入不满最大单笔的一条
						double lastSingleAmtTmp=sumAmt-(singleAmtD*fileSum);
						BigDecimal b   =   new BigDecimal(lastSingleAmtTmp);  
						double lastSingleAmt = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						if(lastSingleAmt!=0){
							cirNum+=1; 
						}
						for(int j = 0;j<cirNum;j++){
							//插入数据前先判断是否已满最大笔数
							if(lineNumber%lineABC==0&&lineNumber!=0){
								//满最大笔数，先写入文件，然后清空数据
								//写入金额总计
								BigDecimal totalAmtBd   =   new BigDecimal(totalAmt);  
								double totalAmtFomat = totalAmtBd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
								reportData.append("总额：").append(totalAmtFomat);
								fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "ABC_" +flag+"_"+
										operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +"_"+lineNumber/lineABC+ ".txt";
								FileOperation.writeTxtFile(reportData.toString(), fileName);
								if(files!=null){
									files=files+","+fileName;
								}else{
									files=fileName;
								}								
								reportData.delete(0, reportData.length());
								reportData.append(reportTitle).append("\r\n");
								totalAmt=0d;
								seqNum=1;
							}
							reportData.append(seqNum).append(",");
							for(int k = 0;k<data[i].length;k++){
								if(k==z+2){
									if(cirNum!=fileSum&&j==(cirNum-1)){
										reportData.append(lastSingleAmt).append(",");
										totalAmt+=lastSingleAmt;
									}else{
									reportData.append(singleAmt).append(",");
									totalAmt+=singleAmtD;
									}
								}else{
									reportData.append(data[i][k].toString().trim()).append(",");
								}
							}
							reportData.append("货款\r\n");
							lineNumber++;
							seqNum++;
						}
				}else{
					//插入数据前先判断是否已满最大笔
					if(lineNumber%lineABC==0&&lineNumber!=0){
						//已满最大笔，先写入文件，然后清空数据
						//写入金额总计
						BigDecimal totalAmtBd   =   new BigDecimal(totalAmt);  
						double totalAmtFomat = totalAmtBd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
						reportData.append("总额：").append(totalAmtFomat);
						fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "ABC_"  +flag+"_"+
								operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +"_"+lineNumber/lineABC+ ".txt";
						FileOperation.writeTxtFile(reportData.toString(), fileName);
						if(files!=null){
							files=files+","+fileName;
						}else{
							files=fileName;
						}
						reportData.delete(0, reportData.length());
						reportData.append(reportTitle).append("\r\n");
						totalAmt=0d;
						seqNum=1;
					}
					reportData.append(seqNum).append(",");
					for(int k = 0;k<data[i].length;k++){
							reportData.append(data[i][k].toString().trim()).append(",");
					}
					
					reportData.append("货款\r\n");
					totalAmt+=sumAmt;
					lineNumber++;
					seqNum++;
				}
			}
			if(lineNumber/lineABC>0&&lineNumber%lineABC==0){
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "ABC_" +flag+"_"+
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +"_"+(lineNumber/lineABC)+ ".txt";
			}else{
				fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "ABC_" +flag+"_"+
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() +"_"+(lineNumber/lineABC+1)+ ".txt";
			}
			//写入金额总计
			BigDecimal totalAmtBd   =   new BigDecimal(totalAmt);  
			double totalAmtFomat = totalAmtBd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
			reportData.append("总额：").append(totalAmtFomat);
			FileOperation.writeTxtFile(reportData.toString(), fileName);
			totalAmt=0d;
			seqNum=1;
			if(files!=null){
				files=files+","+fileName;
			}else{
				files=fileName;
			}			
     }
	}
		if(dataLength>0){
			FileOperation.zipFiles(files, fileZip);
			writeUsefullMsg(fileZip);
			return;
		}else{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
	}
	
	//农行跨行
    private String genSqlOtherBank() {
		StringBuffer whereSql = new StringBuffer();	
		whereSql.append(" and a.BANK_NAME not like '%农业银行%' ");
		if(isNotEmpty(instDate)) {
			whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
		}
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
		}
		
		//FIXME
		if(isNotEmpty(destId)&& !"*".equals(destIdall)){
			whereSql.append(" and a.DEST_ID in ("+destId+")  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.ACCT_BANK_CODE ");
		}
		if("*".equals(destIdall)){
			whereSql.append("  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.ACCT_BANK_CODE ");
		}
		
		
		String sql = "SELECT a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.ACCT_BANK_CODE,sum(a.SUM_AMT) FROM TBL_MCHT_SUMRZ_INF a" +
		" where 1=1 and a.SUM_AMT<>0 "
		+ whereSql.toString();
       sql += " order by a.MCHT_NO";	
		return sql;
	}

	//农行同行
	private String genSqlSameBank() {
		StringBuffer whereSql = new StringBuffer();	
		whereSql.append(" and a.BANK_NAME like '%农业银行%' ");
		if(isNotEmpty(instDate)) {
			whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
		}
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
		}
		//FIXME
		if(isNotEmpty(destId)&& !"*".equals(destIdall)){
			whereSql.append(" and a.DEST_ID in ("+destId+")  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.ACCT_BANK_CODE ");
		}
		if("*".equals(destIdall)){
			whereSql.append("  group by a.MCHT_NO,a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,a.ACCT_BANK_CODE ");
		}
		
		String sql = "SELECT a.SETTLE_ACC_NUM,a.SETTLE_ACC_NAME,sum(a.SUM_AMT) FROM TBL_MCHT_SUMRZ_INF a" +
		" where 1=1 and a.SUM_AMT<>0 "
		+ whereSql.toString();
       sql += " order by a.MCHT_NO";	
		return sql;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getAccFlag() {
		return accFlag;
	}

	public void setAccFlag(String accFlag) {
		this.accFlag = accFlag;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSingleAmt() {
		return singleAmt;
	}

	public void setSingleAmt(String singleAmt) {
		this.singleAmt = singleAmt;
	}
	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getDestId() {
		return destId;
	}
	public void setDestId(String destId) {
		this.destId = destId;
	}
	public String getDestIdall() {
		return destIdall;
	}
	public void setDestIdall(String destIdall) {
		this.destIdall = destIdall;
	}
	public String getDestIdkuaiqian() {
		return destIdkuaiqian;
	}
	public void setDestIdkuaiqian(String destIdkuaiqian) {
		this.destIdkuaiqian = destIdkuaiqian;
	}
	public String getDestIdhanxin() {
		return destIdhanxin;
	}
	public void setDestIdhanxin(String destIdhanxin) {
		this.destIdhanxin = destIdhanxin;
	}
	public String getIdFormats() {
		return idFormats;
	}
	public void setIdFormats(String idFormats) {
		this.idFormats = idFormats;
	}
	

}
