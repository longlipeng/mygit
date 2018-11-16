package com.huateng.struts.pos.action;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.JSONBean;
import com.huateng.system.util.SysParamUtil;

public class T30204_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;
	/**JSON数据对象*/
	protected JSONBean jsonBean = new JSONBean();
	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 11);
		String []title1 = InformationUtil.createTitles("V_", 2);
		Object [][] dataNor = reportCreator.process(genSql(), title);
		Object [][] dataTmp = reportCreator.process(tmpSql(), title1);
		

		List<Object[]> dataList1=new ArrayList();
		for(int x = 0;x<dataNor.length;x++){
			for(int a = 0;a<dataTmp.length;a++){
					if(dataNor[x][0].equals(dataTmp[a][1])&&dataTmp[a][0].equals(dataNor[x][6])){
						dataList1.add(dataNor[x]);
						break;
					
				}
					if(dataNor[x][6]==null||"".equals(dataNor[x][6])){
						dataList1.add(dataNor[x]);
						break;
					}
				
				
			}
			
		}
		Object[][] data = new Object[dataList1.size()][11];   
	     for(int i=0;i<dataList1.size();i++){  

	    	 data[i]=dataList1.get(i);
	     } 

	     for(int j = 0;j<data.length;j++){
	    	 if("1".equals(data[j][2])){
	    		 data[j][2]="启用";
	    	 }
	    	 if("2".equals(data[j][4])){
	    		 data[j][4]="未申请";
	    	 }else if("1".equals(data[j][4])){
	    		 data[j][4]="已审核";
	    	 }
	     }
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T30204_DwnAction.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN30204RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN30204RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN30204RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	protected String tmpSql() {
		StringBuffer sa = new StringBuffer();
		sa.append(" select trim(max(batch_no)), trim(term_id_id) from tbl_term_tmk_log group by term_id_id");
		return sa.toString();
	}
	
	@Override
	protected String genSql() {
		String whereSql = "";
		jsonBean.parseJSONArrayData(getIdList());
		int len = jsonBean.getArray().size();
		String termIdQ ;
		if (len > 0) {
			termIdQ = "'" + jsonBean.getJSONDataAt(0).getString("termIdQ") + "'";
			for(int i = 1;i < len;i++){
//				String termIds = jsonBean.getJSONDataAt(i).getString("termId");
//				System.out.println("termIds:" + termIds);
				termIdQ = termIdQ + "," + "'" + jsonBean.getJSONDataAt(i).getString("termIdQ") + "'";
			}
			System.out.println(termIdQ);
			whereSql += " AND trim(t1.term_id) IN ( " + termIdQ + ")";
		}
		if (!StringUtil.isNull(mchnNo)) {
			whereSql += " AND t1.MCHT_CD='"
					+ mchnNo + "' ";
		}
		if (!StringUtil.isNull(termBranch)) {
			whereSql += " AND t1.TERM_BRANCH='" + termBranch + "' ";
		}
		if (!StringUtil.isNull(termId.trim())) {
			whereSql += " AND trim(t1.TERM_ID)='"
					+ termId.trim() + "' ";
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql += " AND substr(t1.REC_CRT_TS,0,8) >= " + startDate + " ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += " AND substr(t1.REC_CRT_TS,0,8) <= " + endDate + " ";
		}
		if (!StringUtil.isNull(state)) {if (state.equals("1"))
			whereSql += (" AND t2.STATE='1'");
		else
			whereSql+=(" AND t2.STATE is null");}
		
		

		StringBuffer sb = new StringBuffer();
		sb.append("select * from ( SELECT trim(t1.term_id),t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_sta,t1.term_branch,t2.state,t1.PSAM_ID,trim(t2.batch_no),t2.req_opr,t2.req_date, " +
				"t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' ) t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"+
				" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where  t3.state ='0')"+
				" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
		
		sb.append(whereSql);
		sb.append(" ORDER BY t1.term_id )");
		StringBuffer sa = new StringBuffer();
		sa.append(" select max(batch_no), term_id_id from tbl_term_tmk_log group by term_id_id");
		return sb.toString();
	}
	private String mchnNo;
	private String termBranch;
	private String endDate;
	private String startDate;
	private String termSta;
	private String termId;
	private String state;
	private String start;
	private String idList;
	
	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getMchnNo() {
		return mchnNo;
	}

	public void setMchnNo(String mchnNo) {
		this.mchnNo = mchnNo;
	}

	public String getTermBranch() {
		return termBranch;
	}

	public void setTermBranch(String termBranch) {
		this.termBranch = termBranch;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTermSta() {
		return termSta;
	}

	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}


	
	

}
