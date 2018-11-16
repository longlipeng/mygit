package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * 商户对账明细报表
 * 
 * @author crystal
 *
 */
public class T80222Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	//结算日期
	private String instDate;
	private String accFlag;
	private String batchNum;
	private String destId;
	private String destIdall;
	private String destIdkuaiqian;
	private String destIdhanxin;
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer();		
		
		if(isNotEmpty(instDate)) {
			whereSql.append(" and a.INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and a.ACC_FLAG ='").append(accFlag).append("'") ;	
		}
//		if(isNotEmpty(destId)&&!"*".equals(destId)){
//			whereSql.append(" and a.DEST_ID = '").append(destId).append("'");
//		}
		
		
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and a.BATCH_NUM ='").append(batchNum).append("'") ;	
		}
		
		System.out.println(destId);
		if(isNotEmpty(destId)&&!"*".equals(destIdall)){
			System.out.print(destIdkuaiqian);
			whereSql.append(" and a.DEST_ID in ("+destId+")  group by trim(a.MCHT_NO),trim(b.MCHT_NM),trim(a.SETTLE_ACC_NAME), trim(a.SETTLE_ACC_NUM),trim(a.BANK_NAME) ");
		}
		if("*".equals(destIdall)){
			whereSql.append("  group by trim(a.MCHT_NO),trim(b.MCHT_NM),trim(a.SETTLE_ACC_NAME), trim(a.SETTLE_ACC_NUM),trim(a.BANK_NAME) ");
		}
		String sql = "SELECT trim(a.MCHT_NO),trim(b.MCHT_NM),trim(a.SETTLE_ACC_NAME), trim(a.SETTLE_ACC_NUM),trim(a.BANK_NAME),sum(a.txn_amt_sett),sum(a.HAND_AMT),sum(a.SUM_AMT) FROM TBL_MCHT_SUMRZ_INF a" +
		" left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 and a.SUM_AMT<>0 "
		+ whereSql.toString();
		sql += " order by trim(a.MCHT_NO)";	
		return sql;
	}

	@Override
	protected void reportAction() throws Exception {
		title = InformationUtil.createTitles("V_", 8);
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		/*for(int i=0;i<data.length;i++){
			if(data[i][8].equals("1702")){
				data[i][8]="翰鑫通道";
			}
			if(data[i][8].equals("1708")){
				data[i][8]="快钱通道";
			}
		}*/
		
		/*String batchNum = "";
		String sql = "select BATCH_NUM from TBL_MCHT_SUMRZ_INF WHERE INST_DATE='"+instDate+"'";
		List<String> batchNumList =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(batchNumList!=null&&batchNumList.size()>0){
			batchNum=batchNumList.get(0);
		}*/
		StringBuffer whereSql = new StringBuffer();		
		if(isNotEmpty(instDate)) {
			whereSql.append(" and INST_DATE ='").append(instDate).append("'") ;	
		}
		if(isNotEmpty(accFlag)) {
			whereSql.append(" and ACC_FLAG ='").append(accFlag).append("'") ;	
		}
		if(isNotEmpty(batchNum)) {
			whereSql.append(" and BATCH_NUM ='").append(batchNum).append("'") ;	
		}
//		if(isNotEmpty(destId)&&!"*".equals(destId)){
//			whereSql.append(" and DEST_ID ='").append(destId).append("'");
//		}
		if(isNotEmpty(destId)&&!"*".equals(destIdall)){
			System.out.print(destIdkuaiqian);
			whereSql.append(" and DEST_ID in ("+destId+") ");
		}
		if("*".equals(destIdall)){
			whereSql.append(" and DEST_ID in('1702','1708') ");
		}
		String txnAmtSum ="";
		String handAmtSum="";
		String sumAmtSum="";
		String sqlAmt = "select SUM(txn_amt_sett),SUM(HAND_AMT),SUM(SUM_AMT) from TBL_MCHT_SUMRZ_INF WHERE 1=1 and SUM_AMT<>0 "+ whereSql.toString();
		List<Object[]> sumAmtList =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlAmt);
		if(sumAmtList!=null&&sumAmtList.size()>0){
			if (sumAmtList.get(0)[0] == null) {
				txnAmtSum = "";
			}else{
				txnAmtSum=sumAmtList.get(0)[0].toString();
			}
			if (sumAmtList.get(0)[1] ==null) {
				handAmtSum = "";
			}else{
				handAmtSum=sumAmtList.get(0)[1].toString();
			}
			if (sumAmtList.get(0)[2] == null) {
				sumAmtSum = "";
			}else{
				sumAmtSum=sumAmtList.get(0)[2].toString();
			}
		}
		String accFlagInf = "";
		if("1".equals(accFlag)){
			accFlagInf="对私账户";
		}else if("2".equals(accFlag)){
			accFlagInf="对公账户";
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		//统计日期
		parameters.put("instDate", instDate);
		parameters.put("batchNum", batchNum);
		parameters.put("txnAmtSum", txnAmtSum);
		parameters.put("handAmtSum", handAmtSum);
		parameters.put("sumAmtSum", sumAmtSum);
		parameters.put("accFlagInf", accFlagInf);
		if(isNotEmpty(destIdall)){
			parameters.put("destId", "所有通道");
		}
		if(isNotEmpty(destId)){
			if(destId.contains("1708")){
				parameters.put("destIdkuaiqian", "上汽通道");
			}
			
		}
		if(isNotEmpty(destId)){
			if(destId.contains("1719")){
				parameters.put("destIdhanxin", "单用途卡通道");
			}
			
		}
		
		
		
		reportType = "PDF";
		reportCreator.initReportData(getJasperInputSteam("T80222.jasper"), parameters, 
				                     reportModel.wrapReportDataSource(), getReportType());

//		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80222RN_" +
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80222RN"));
		
//		callDownload(fileName,"shanghuchacuojiaoyimingxibiao.xls");
		writeUsefullMsg(fileName);
		return;
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

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
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

	

}
