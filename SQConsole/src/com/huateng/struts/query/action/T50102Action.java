/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-10       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.struts.query.action;

import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T50102Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T50102Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 21);
		
		data = reportCreator.process(genSql(), title);
		
		String sqlBrh = "SELECT AGEN_ID,AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE";
		List<Object[]> brhName =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlBrh);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][5] != null && !"".equals(data[i][5].toString().trim())){//受理通道
				if(brhName !=null && brhName.size() >0){
					for(int j = 0; j < brhName.size(); j++){
						if(data[i][5].toString().trim().equals(brhName.get(j)[0].toString().trim())){
							data[i][5] = brhName.get(j)[1].toString();
						}
					}
				}else{
					data[i][5] = data[i][5].toString().trim();
				}
			}else{
				data[i][5] = " ";
			}
			if(data[i][7] != null){
				String cardTyp = data[i][7].toString().trim();	//卡类型
				if(cardTyp.equals("00")){
					data[i][7] = "贷记卡";
				}else if(cardTyp.equals("01")){
					data[i][7] = "借记卡";
				}else if(cardTyp.equals("02")){
					data[i][7] = "准贷记卡";
				}else{
					data[i][7] = cardTyp;
				}
			}else{
				data[i][7] = " ";
			}
			data[i][12] = CommonFunction.transFenToYuan(data[i][12].toString());
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("betdate", startDate + " - " + endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T50102.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50102RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50102RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50101RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		
		String whereSql = "";
		if (!StringUtil.isNull(startDate)) {
			whereSql += "substr(UPDT_DATE,1,8) >= '" + startDate + "' ";
		}else{
			whereSql += "UPDT_DATE>= '"
				+ StringUtil.getcurrdate("yyyyMMdd") + "000000' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += "AND substr(UPDT_DATE,1,8) <= '" + endDate + "' ";
		}
		//交易类型
		if (!StringUtil.isNull(txnNum)) {
			whereSql += "AND t.TXN_NUM = '" + txnNum + "' ";
		}
		if (!StringUtil.isNull(mchntNo)) {
			whereSql += "AND CARD_ACCP_ID = '" + mchntNo + "' ";
		}
		//终端编号
		if (!StringUtil.isNull(termId)) {
			whereSql += " AND CARD_ACCP_TERM_ID = '" + termId + "' ";
		}
		//卡号
		if (!StringUtil.isNull(pan)) {
			whereSql += " AND rtrim(PAN) like '%" + pan.trim() + "%' ";
		}
		//交易最小金额
		if (!StringUtil.isNull(transamtsmall)) {
			String small = CommonFunction.transYuanToFen(transamtsmall);
			String transamtsmall = CommonFunction.fillString(small,'0', 12, false);
			whereSql += " AND AMT_TRANS >= '" + transamtsmall + "'";
		}
		//交易最大金额
		if (!StringUtil.isNull(transamtbig)) {
			String big1 =  CommonFunction.transYuanToFen(transamtbig);
			String transamtbig = CommonFunction.fillString(big1, '0',12, false);
			whereSql += " AND AMT_TRANS<= '" + transamtbig + "'";
		}
		//受理机构
		if (!StringUtil.isNull(brhId)) {
			whereSql += " AND rtrim(t.RCVG_CODE) = '" + brhId.split("-")[0]+ "' ";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,"
				+ "RCVG_CODE,FWD_INST_ID_CODE ||'-'|| (SELECT CARD_DIS FROM tbl_bank_bin_inf b WHERE FWD_INST_ID_CODE = INS_ID_CD and rownum <= 1) as CARD_DIS,"
				+ "(SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
				+ "PAN,name.TXN_NAME,substr(t.FLD_RESERVED,3,6) as txnBatchNo,TERM_SSN,AMT_TRANS,"
				+ "substr(emv_val,instr(emv_val,'9F2608')+6,16) as icCert,substr(emv_val,instr(emv_val,'9505')+4,10) as tvr,'-' as tsi,"
				+ "substr(emv_val,instr(emv_val,'9F1E',100)+26,instr(emv_val,'9F09',100)-instr(emv_val,'9F1E',100)-26) as aid,"
				+ "substr(emv_val,instr(emv_val,'9F3602')+6,4) as atc,'-' as appTag,'-' as appOreName,RESP_CODE "
				+ "from tbl_t_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) where ");
		
		//根据终端号前两位定义受理机构,总行不受控制
		/*if (StringUtil.isNull(brhId)) {
			brhId = operator.getOprBrhId();
		}
		if (!"9900".equals(brhId)){
			sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '" + brhId.substring(0, 2) + "'");
		}*/
		sb.append(whereSql);
		sb.append(" ORDER BY UPDT_DATE DESC");
		
		String sql = "";
		//卡类型
		if (!StringUtil.isNull(cardType) && !cardType.equals("*")) {
			sql = "select * from ("+sb.toString() + ") x where card_tp = '" + cardType + "' ";
		}else{
			sql = sb.toString();
		}
		
		return sql;
	}
	
	private String startDate;
	private String endDate;
	private String mchntNo;
	private String brhId;
	private String txnNum;
	private String cardType;
	private String transamtsmall;
	private String transamtbig;
	
	private String termId;
	private String pan;
	private String retrivlRef;
	private String respCode;

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the mchntNo
	 */
	public String getMchntNo() {
		return mchntNo;
	}

	/**
	 * @param mchntNo the mchntNo to set
	 */
	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getTransamtsmall() {
		return transamtsmall;
	}

	public void setTransamtsmall(String transamtsmall) {
		this.transamtsmall = transamtsmall;
	}

	public String getTransamtbig() {
		return transamtbig;
	}

	public void setTransamtbig(String transamtbig) {
		this.transamtbig = transamtbig;
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the pan
	 */
	public String getPan() {
		return pan;
	}

	/**
	 * @param pan the pan to set
	 */
	public void setPan(String pan) {
		this.pan = pan;
	}

	/**
	 * @return the retrivlRef
	 */
	public String getRetrivlRef() {
		return retrivlRef;
	}

	/**
	 * @param retrivlRef the retrivlRef to set
	 */
	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}

	/**
	 * @return the respCode
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * @param respCode the respCode to set
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
}
