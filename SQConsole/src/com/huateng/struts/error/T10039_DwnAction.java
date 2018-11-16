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

public class T10039_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 14);
		data = reportCreator.process(genSql(), title);
		
		//商户名
	    String sql = "select mcht_no,mcht_no||' - '||mcht_nm from tbl_mcht_base_inf";
	    List<Object[]> mchtNm = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		//机构名
	    String sql2 = "select agen_id,agen_id||' - '||agen_name from tbl_agency_info";
	    List<Object[]> agenNm = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
	    
		for (int i = 0; i < data.length; i++) {
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
			}
			
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T10039_DwnAction.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
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
		if (!StringUtil.isNull(mchtNo)) {
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
		}
		

		StringBuffer sb = new StringBuffer();
		sb.append("select * from ( SELECT MCHT_NO,INST_CODE,TERM_ID,CHANGE_ACCOUNT/100,CHANGE_REASON,CHANGE_FLAG,CHANGE_DATE,INS_OPR,UPD_TS,UPD_OPR,APR_TS,APR_OPR,ST,ENTER_TP " +
				" FROM tbl_change_acc_inf_tmp a where 1=1 " );
		
		sb.append(whereSql);
		sb.append("order by CHANGE_DATE)");
		return sb.toString();
	}
	
	private String mchtNo;
	private String termId;
	private String changeAccount;
	private String changeReason;
	private String changeDate;
	private String enterTpQ;
	private String refuseInfo;
	private String changeFlag;
	private String stQ;
	private String uptTsQ;
	private String amtList;
	private String aprTsQ;
	private String instCode;
	

	
	
	
	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getAprTsQ() {
		return aprTsQ;
	}

	public void setAprTsQ(String aprTsQ) {
		this.aprTsQ = aprTsQ;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public String getEnterTpQ() {
		return enterTpQ;
	}

	public void setEnterTpQ(String enterTpQ) {
		this.enterTpQ = enterTpQ;
	}

	public String getChangeFlag() {
		return changeFlag;
	}

	public void setChangeFlag(String changeFlag) {
		this.changeFlag = changeFlag;
	}

	public String getStQ() {
		return stQ;
	}

	public void setStQ(String stQ) {
		this.stQ = stQ;
	}

	public String getUptTsQ() {
		return uptTsQ;
	}

	public void setUptTsQ(String uptTsQ) {
		this.uptTsQ = uptTsQ;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getAmtList() {
		return amtList;
	}

	public void setAmtList(String amtList) {
		this.amtList = amtList;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getChangeAccount() {
		return changeAccount;
	}

	public void setChangeAccount(String changeAccount) {
		this.changeAccount = changeAccount;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	

}
