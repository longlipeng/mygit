package com.huateng.struts.rout.action;


import java.io.FileOutputStream;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T11030_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 17);
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][8].equals("0")){
				data[i][8] = "新增待审核";
			}else if(data[i][8].equals("1")){
				data[i][8] = "已删除";
			}else if(data[i][8].equals("2")){
				data[i][8] = "正常";
			}else if(data[i][8].equals("3")){
				data[i][8] = "修改待审核";
			}else if(data[i][8].equals("4")){
				data[i][8] = "删除待审核";
			}
			
//			if(data[i][7] != null){
//				if(data[i][7].equals("01")){
//					data[i][7] = "本代本";
//				}else if(data[i][7].equals("02")){
//					data[i][7] = "本代他";
//				}else if(data[i][7].equals("03")){
//					data[i][7] = "转银联";
//				}
//			}
			
			
			if(data[i][2].equals("*-")){
				data[i][2] = "*-所有商户";
			}
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T11030_DwnAction.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN11030RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN11030RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN11030RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(termIns)) {
			whereSql.append(" and t.TERM_INS = '"
					+ termIns + "' ");
		}
		if (isNotEmpty(mchtMcc)) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ mchtMcc + "' ");
		}
		if (isNotEmpty(mchtId)) {
			whereSql.append(" and t.MCHT_ID = '"
					+ mchtId + "' ");
		}
		if (isNotEmpty(mchtTermId)) {
			whereSql.append(" and t.MCHT_TERM_ID = '"
					+ mchtTermId + "' ");
		}
		if (isNotEmpty(insMcc)) {
			whereSql.append(" and t.INS_MCC = '"
					+ insMcc + "' ");
		}
		if (isNotEmpty(insMcht)) {
			whereSql.append(" and t.INS_MCHT like '%"
					+ insMcht + "%' ");
		}
		if (isNotEmpty(insTerm)) {
			whereSql.append(" and t.INS_TERM like '%"
					+ insTerm + "%' ");
		}
		if (isNotEmpty(stat)) {
			whereSql.append(" and t.STAT = '" + stat
					+ "' ");
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ startDate + "000000' ");
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql.append(" and substr(t.CRE_TIME,1,8) <= '"
					+endDate + "' ");
		}
		if (isNotEmpty(channelType)) {
			whereSql.append(" and t.CHANNEL_TYPE = '" + channelType
					+ "' ");
		}

//		String sql = "SELECT t.TERM_INS||' - '||a.AGEN_NAME,t.MCHT_MCC,t.MCHT_ID||'-'||b.MCHT_NM,t.MCHT_TERM_ID,t.INS_MCC,t.INS_MCHT,t.INS_TERM,t.CHANNEL_TYPE,t.RESERVE_01,t.STAT,t.CRE_TIME,t.CRE_OPR_ID,t.MODI_TIME,t.MODI_OPR_ID,t.CHECK_TIME,t.CHECK_OPR_ID,t.LMK "
		String sql = "SELECT t.TERM_INS||' - '||a.AGEN_NAME,t.MCHT_MCC,t.MCHT_ID||'-'||b.MCHT_NM,t.MCHT_TERM_ID,t.INS_MCC,t.INS_MCHT,t.INS_TERM,t.RESERVE_01,t.STAT,t.CRE_TIME,t.CRE_OPR_ID,t.MODI_TIME,t.MODI_OPR_ID,t.CHECK_TIME,t.CHECK_OPR_ID,t.MIN_AMT/100 as MIN_AMT,t.MAX_AMT/100 as MAX_AMT"
				+ " FROM TBL_TERM_CHANNEL_INF_TMP t left join TBL_AGENCY_INFO a  on a.STATUE='1'and t.term_ins=a.AGEN_ID left join TBL_MCHT_BASE_INF b on t.MCHT_ID= b.MCHT_NO  where 1=1"
				+ whereSql.toString();
		return sql.toString();
	}
	
	private String termIns;
	private String mchtMcc;
	
	private String mchtId;

	private String mchtTermId;	

	private String insMcc;

	private String insMcht;	

	private String insTerm;	

	private String stat;	

	private String startDate;

	private String endDate;
	
	private String channelType;
	
	

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getTermIns() {
		return termIns;
	}

	public void setTermIns(String termIns) {
		this.termIns = termIns;
	}

	public String getMchtMcc() {
		return mchtMcc;
	}

	public void setMchtMcc(String mchtMcc) {
		this.mchtMcc = mchtMcc;
	}

	public String getMchtId() {
		return mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public String getMchtTermId() {
		return mchtTermId;
	}

	public void setMchtTermId(String mchtTermId) {
		this.mchtTermId = mchtTermId;
	}

	public String getInsMcc() {
		return insMcc;
	}

	public void setInsMcc(String insMcc) {
		this.insMcc = insMcc;
	}

	public String getInsMcht() {
		return insMcht;
	}

	public void setInsMcht(String insMcht) {
		this.insMcht = insMcht;
	}

	public String getInsTerm() {
		return insTerm;
	}

	public void setInsTerm(String insTerm) {
		this.insTerm = insTerm;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	


}
