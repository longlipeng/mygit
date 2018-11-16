package com.huateng.struts.error;


import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T10040_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 11);
		data = reportCreator.process(genSql(), title);
		
//		//商户名
//	    String sql = "select mcht_no,mcht_no||' - '||mcht_nm from tbl_mcht_base_inf";
//	    List<Object[]> mchtNm = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
//		//机构名
//	    String sql2 = "select agen_id,agen_id||' - '||agen_name from tbl_agency_info";
//	    List<Object[]> agenNm = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
	    
		for (int i = 0; i < data.length; i++) {
			if(data[i][5].equals("1")){
				data[i][5] = "posp多";
			}else if(data[i][5].equals("2")){
				data[i][5] = "核心预付费卡多";
			}else if(data[i][5].equals("3")){
				data[i][5] = "金额有误";
			}else if(data[i][5].equals("3")){
				data[i][5] = "卡号有误";
			}else{
				data[i][5] = "未知原因";
			}
			/*
			 //商户名
			if(data[i][0] != null && !"".equals(data[i][0].toString().trim())){
				if(mchtNm !=null && mchtNm.size() >0){
					for(int j = 0; j < mchtNm.size(); j++){
						if(data[i][0].toString().trim().equals(mchtNm.get(j)[0].toString().trim())){
							data[i][0] = mchtNm.get(j)[1].toString();
						}
					}
				}else{
					data[i][0] = data[i][0].toString().trim();
				}
			}else{
				data[i][0] = "";
			}
			//机构号
			if(data[i][1] != null && !"".equals(data[i][1].toString().trim())){
				if(agenNm !=null && agenNm.size() >0){
					for(int j = 0; j < agenNm.size(); j++){
						if(data[i][1].toString().trim().equals(agenNm.get(j)[0].toString().trim())){
							data[i][1] = agenNm.get(j)[1].toString();
						}
					}
				}else{
					data[i][1] = data[i][1].toString().trim();
				}
			}else{
				data[i][1] = "";
			}
			
			
			if(data[i][5].equals("0")){
				data[i][5] = "未调账";
			}else {
				data[i][5] = "贷记卡";
			}
			
			if(data[i][13].equals("0")){
				data[i][13] = "自动录入";
			}else if(data[i][13].equals("1")){
				data[i][13] = "手动录入";
			}else{
				data[i][13] = "其它";
			}
			
			if(data[i][12].equals("0")){
				data[i][12] = "正常";
			}else if(data[i][12].equals("1")){
				data[i][12] = "新增待审核";
			}else if(data[i][12].equals("2")){
				data[i][12] = "修改待审核";
			}else if(data[i][12].equals("3")){
				data[i][12] = "删除待审核";
			}else{
				data[i][12] = "审核拒绝";
			}
			String date = (String) data[i][6];
			if(isNotEmpty(date)){
			String sdate =date.substring(0, 8);
			data[i][6] = sdate;
			}
			String udate = (String) data[i][8];
			if(isNotEmpty(udate)){
				String sudate =udate.substring(0, 8);
				data[i][8] = sudate;
			}
			String cdate = (String) data[i][10];
			if(isNotEmpty(cdate)){
			String scdate =cdate.substring(0, 8);
			data[i][10] = scdate;
			}*/
			
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T10040_DwnAction.jasper"), parameters, reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN10039RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN10039RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN10039RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = "";
		/*if (!StringUtil.isNull(mchtNo)) {
			whereSql += " AND MCHT_NO = '"
					+ mchtNo + "' ";
		}
		if (!StringUtil.isNull(termId)) {
			whereSql += "AND TERM_ID = '" + termId + "' ";
		}
		if (!StringUtil.isNull(enterTpQ)) {
			whereSql += " AND ENTER_TP = '"
					+ enterTpQ + "' ";
		}
		if (!StringUtil.isNull(stQ)) {
			whereSql += "AND ST = '" + stQ + "' ";
		}
		if (!StringUtil.isNull(uptTsQ)) {
			whereSql += " AND substr(UPD_TS,1,8) = '"
					+ uptTsQ + "' ";
		}
		if (!StringUtil.isNull(aprTsQ)) {
			whereSql += " AND substr(APR_TS,1,8) = '"
					+ aprTsQ + "' ";
		}
		if (!StringUtil.isNull(changeFlag)) {
			whereSql += "AND CHANGE_FLAG = '" + changeFlag + "' ";
		}
		if (!StringUtil.isNull(changeDate)) {
			whereSql += "AND substr(CHANGE_DATE,1,8) = '" + changeDate + "' ";
		}
		if (!StringUtil.isNull(instCode)) {
			whereSql += "AND INST_CODE = '" + instCode + "' ";
		}*/
		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT BKET.DATE_SETTLMT, BKET.ERR_TYPE, BKET.TERM_ID, BKET.MCHT_ID||' - '||TMBI.MCHT_NM, BKET.MCHT_TP, BKET.ERR_CODE, trim(BKET.TXN_NAME), BKET.ORG_DATE_TIME, BKET.ORG_TRANS_AMT, BKET.PAN, BKET.TXN_SSN FROM BTH_KQ_ERR_TXN BKET, TBL_MCHT_BASE_INF TMBI WHERE BKET.MCHT_ID = TMBI.MCHT_NO AND 1=1 " );
		
		sb.append(whereSql);
		sb.append(" ORDER BY BKET.DATE_SETTLMT DESC ");
		return sb.toString();
	}
	
	
}
