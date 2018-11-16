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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.common.grid.GridConfigMethod;
import com.huateng.log.Log;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T50101Action.java
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
public class T50101Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 计算时间差
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
    private int daysBetween(String smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
        Date sd=sdf.parse(sdf.format(sdf.parse(smdate)));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sd);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 21);
		data = reportCreator.process(genSql(), title);
		
		String sqlBrh = "SELECT AGEN_ID,AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE";
		List<Object[]> brhName =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlBrh);
		
		for (int i = 0; i < data.length; i++) {
			data[i][8] = CommonFunction.transFenToYuan(data[i][8].toString());		//交易金额
			if(data[i][10] != null && !"".equals(data[i][10].toString().trim())){//受理机构
				if(brhName !=null && brhName.size() >0){
					for(int j = 0; j < brhName.size(); j++){
						if(data[i][10].toString().trim().equals(brhName.get(j)[0].toString().trim())){
							data[i][10] = brhName.get(j)[1].toString();
						}
					}
				}else{
					data[i][10] = data[i][10].toString().trim();
				}
			}else{
				data[i][10] = "";
			}
			
			if(data[i][12] != null){
				String cardTyp = data[i][12].toString().trim();	//卡类型
				if(cardTyp.equals("00")){
					data[i][12] = "贷记卡";
				}else if(cardTyp.equals("01")){
					data[i][12] = "借记卡";
				}else if(cardTyp.equals("02")){
					data[i][12] = "准贷记卡";
				}else if(cardTyp.equals("03")){
					data[i][12] = "预付卡";
				}else{
					data[i][12] = cardTyp;
				}
			}else{
				data[i][12] = " ";
			}
			
			String[] tmp = data[i][13].toString().split("-");	//交易方式
			if(tmp[0].toString().equals("00")){
				data[i][13] = "未指明";
			}else if(tmp[0].toString().equals("01")){
				data[i][13] = "无卡交易";
			}else if(tmp[0].toString().equals("02") || tmp[0].toString().equals("90")){
				if(tmp[1].equals("51") || tmp[1].equals("52")){
					data[i][13] = "Fall Back";
				}else{
					data[i][13] = "磁条卡";
				}
			}else if(tmp[0].toString().equals("05") || tmp[0].toString().equals("95")){
				data[i][13] = "IC卡";
			}else if(tmp[0].toString().equals("96")){
				data[i][13] = "芯片卡";
			}else if(tmp[0].toString().equals("07")){
				data[i][13] = "非接";
			}else{
				data[i][13] = tmp[0].toString();
			}
			
			StringBuffer bs = new StringBuffer();
			if(data[i][16]!=null){
				String str = ((String) data[i][16]).trim();
				if(str.length()%2==0){
					if(str.length()>=2){
						String rq = "select sa_model_kind, decode(sa_action,'0','关注','1','预警','2','冻结',sa_action ) from tbl_risk_inf " +
								"where sa_model_kind = '" + str.substring(0,2) + "'" ;
						List<Object[]> rql =CommonFunction.getCommQueryDAO().findBySQLQuery(rq);
						if(rql!=null&&!rql.isEmpty()){
							bs.append(rql.get(0)[0]).append("-").append(rql.get(0)[1]).append(" ");
						}else{
							bs.append(str.substring(0,2)).append(" ");
						}
					}
					if(str.length()>=4){
						String rq = "select sa_model_kind, decode(sa_action,'0','关注','1','预警','2','冻结',sa_action ) from tbl_risk_inf " +
								"where sa_model_kind = '" + str.substring(2,4) + "'" ;
						List<Object[]> rql =CommonFunction.getCommQueryDAO().findBySQLQuery(rq);
						if(rql!=null&&!rql.isEmpty()){
							bs.append(rql.get(0)[0]).append("-").append(rql.get(0)[1]).append(" ");
						}else{
							bs.append(str.substring(2,4)).append(" ");
						}
					}
					if(str.length()>=6){
						String rq = "select sa_model_kind, decode(sa_action,'0','关注','1','预警','2','冻结',sa_action ) from tbl_risk_inf " +
								"where sa_model_kind = '" + str.substring(4,6) + "'" ;
						List<Object[]> rql =CommonFunction.getCommQueryDAO().findBySQLQuery(rq);
						if(rql!=null&&!rql.isEmpty()){
							bs.append(rql.get(0)[0]).append("-").append(rql.get(0)[1]).append(" ");
						}else{
							bs.append(str.substring(4,6)).append(" ");
						}
					}
					if(str.length()>=8){
						String rq = "select sa_model_kind, decode(sa_action,'0','关注','1','预警','2','冻结',sa_action ) from tbl_risk_inf " +
								"where sa_model_kind = '" + str.substring(6,8) + "'" ;
						List<Object[]> rql =CommonFunction.getCommQueryDAO().findBySQLQuery(rq);
						if(rql!=null&&!rql.isEmpty()){
							bs.append(rql.get(0)[0]).append("-").append(rql.get(0)[1]).append(" ");
						}else{
							bs.append(str.substring(6,8)).append(" ");
						}
					}
						
				}
			}
			String risksql = "select risk_mode_reason , decode(risk_flag,'0','关注','1','预警','2','冻结',risk_flag )from tbl_risk_result where mcht_no = '" + data[i][4] + "'";
			List<Object[]> risk =CommonFunction.getCommQueryDAO().findBySQLQuery(risksql);
			if(risk!=null&&!risk.isEmpty()){
				for (Object[] objects : risk) {
					if((objects[0]).equals("14")||objects[0].equals("15")||objects[0].equals("23")){
						bs.append(objects[0]).append("-").append(objects[1]).append(" ");
					}
				}
			}
			data[i][16] = bs.toString();
			
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		parameters.put("betdate", startDate + " - " + endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T50101.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50101RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50101RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50101RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = "";
		
		boolean bb = true;
		//判断开始时间，小于90天查当前表，超过90天查全部
		
		
		try {
			if(!StringUtil.isNull(startDate)) {
				if(daysBetween(startDate,new Date())<=90){
					bb = true;
				}else{
					bb = false;
				}
			}
		} catch (ParseException e) {
			bb = false;
			System.out.println(e);
			Log.log("日期转换错误");
		}
		
		
		if (!StringUtil.isNull(startDate)) {
			whereSql += " UPDT_DATE >= '"
					+ startDate + "000000' ";
		}else{
			whereSql += " UPDT_DATE>= '"
				+ StringUtil.getcurrdate("yyyyMMdd") + "000000' ";
		}
		

		
		
		if (!StringUtil.isNull(endDate)) {
			whereSql += "AND substr(UPDT_DATE,1,8) <= '" + endDate + "' ";
		}
		if (!StringUtil.isNull(txnNum)) {
			whereSql += "AND t.TXN_NUM = '" + txnNum + "' ";
		}
		//交易方式
		if (!StringUtil.isNull(transWay)) {
			if(transWay.equals("02")){	//磁条卡
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) in ('02','90') AND substr(t.FLD_RESERVED,12,2) NOT IN ('51','52')";
			}else if(transWay.equals("05")){	//IC卡
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) in ('05','95')";
			}else if(transWay.equals("60")){	//fallback
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) in ('02','90') AND substr(t.FLD_RESERVED,12,2) IN ('51','52')";
			}else{
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) = '" + transWay + "'";
			}
		}
		//受理机构
		if (!StringUtil.isNull(brhId)) {
			whereSql += " AND rtrim(t.RCVG_CODE) = '" + brhId.split("-")[0]+ "' ";
		}
		//商户号
		if (!StringUtil.isNull(mchntNo)) {
			whereSql += "AND CARD_ACCP_ID = '" + mchntNo + "' ";
		}
		//终端编号
		if (!StringUtil.isNull(termId)) {
			whereSql += " AND CARD_ACCP_TERM_ID = '" + termId + "' ";
		}
		if (!StringUtil.isNull(idbinStaNo)) {
			int length = idbinStaNo.trim().length();
			whereSql += " AND substr(trim(PAN),1,"+length+") = '" + idbinStaNo.trim() + "' ";
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
		//检索参考号
		if (!StringUtil.isNull(retrivlRef)) {
			whereSql += " AND rtrim(RETRIVL_REF) like '%" + retrivlRef.trim() + "%' ";
		}
		// 系统流水号
		if (!StringUtil.isNull(sysSeqNum)) {
			whereSql += " AND rtrim(SYS_SEQ_NUM) like '%"+ sysSeqNum.trim() + "%' ";
		}
		//应答码
		if (!StringUtil.isNull(respCode)) {
			whereSql += " AND RESP_CODE = '" + respCode + "' ";
		}

		StringBuffer sb = new StringBuffer();
/*		sb.append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
						+ "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
						+ "ACQ_INST_ID_CODE,RCVG_CODE,name.TXN_NAME,"
						+ "(SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
						+ "(substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay," 
						+ "RESP_CODE from tbl_n_txn t left outer join "
						+ "tbl_txn_name name on (t.txn_num = name.txn_num) where ");*/
		//90天以内
		if(bb){sb.append("select substr(UPDT_DATE,1,8) as inst_date," +
				"substr(UPDT_DATE,9,6) as inst_time," +
				"SYS_SEQ_NUM," +
				"substr(trim(PAN),1,6)||'****'||substr(trim(pan),-4,4)," +
				"CARD_ACCP_ID," +
				"CARD_ACCP_NAME," +
				"CARD_ACCP_TERM_ID," +
				"RETRIVL_REF," +
				"AMT_TRANS," +
				"ACQ_INST_ID_CODE," +
				"RCVG_CODE," +
				"t.txn_num||'-'||name.TXN_NAME,"
				+ "(SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
				+ "(substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay,"
				+ "RESP_CODE," +
				"tt.TERM_BATCH_NM ," +
				" trim(order_no)," +
				" decode(trans_State,'0','失败','1','成功','2','失败','3','失败','4','失败','5','失败','6','失败','7','失败','8','失败','R','成功',trans_State)," +
//				" substr(t.misc_2,71,15)," +
				"t.switch_data," +  //退货原交易流水
				"t.CANCEL_SSN," +    //撤销原交易流水
				"t.REVSAL_SSN  " +   //冲正原交易流水
				"from tbl_n_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) " +
				"left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID  " +
				"left outer join tbl_risk_inf r on trim(r.sa_model_kind) = trim(t.order_no) " +
				" where");
			sb.append(whereSql);
			sb.append(" ORDER BY UPDT_DATE DESC");
				
		}else{
			//90天以外
			sb.append("select inst_date,inst_time,SYS_SEQ_NUM,pan,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,ACQ_INST_ID_CODE,RCVG_CODE,txn,CARD_TP,transWay,RESP_CODE,TERM_BATCH_NM,order_no,trans_State,switch_data,CANCEL_SSN,REVSAL_SSN from("
+" select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,SYS_SEQ_NUM,substr(trim(PAN),1,6)||'****'||substr(trim(pan),-4,4)as pan,"
       +" CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,ACQ_INST_ID_CODE,RCVG_CODE,t.txn_num||'-'||name.TXN_NAME as txn,"
       +" (SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
       +" (substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay,RESP_CODE ,tt.TERM_BATCH_NM,trim(order_no)as order_no,"
       +" decode(trans_State,'0','失败','1','成功','2','失败','3','失败','4','失败','5','失败','6','失败','7','失败','8','失败','R','成功',trans_State)as trans_State,"
      +" t.UPDT_DATE,t.switch_data,t.CANCEL_SSN,t.REVSAL_SSN from tbl_n_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID"             
//      +" substr(t.misc_2,71,15) as misc,t.UPDT_DATE,t.switch_data,t.CANCEL_SSN,t.REVSAL_SSN from tbl_n_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID"             
      +" left outer join tbl_risk_inf r on trim(r.sa_model_kind) = trim(t.order_no) where "+ whereSql                 
      +" union all "
+" select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,SYS_SEQ_NUM,substr(trim(PAN),1,6)||'****'||substr(trim(pan),-4,4)as pan,"
       +" CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,ACQ_INST_ID_CODE,RCVG_CODE,t.txn_num||'-'||name.TXN_NAME as txn,"
       +" (SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
      +" (substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay,RESP_CODE,tt.TERM_BATCH_NM,trim(order_no)as order_no,"
      +" decode(trans_State,'0','失败','1','成功','2','失败','3','失败','4','失败','5','失败','6','失败','7','失败','8','失败','R','成功',trans_State)as trans_State ,"
//      +" substr(t.misc_2,71,15) as misc,t.UPDT_DATE,t.switch_data,t.CANCEL_SSN,t.REVSAL_SSN from tbl_n_txn_his t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID"             
      +" t.UPDT_DATE,t.switch_data,t.CANCEL_SSN,t.REVSAL_SSN from tbl_n_txn_his t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID"             
      +" left outer join tbl_risk_inf r on trim(r.sa_model_kind) = trim(t.order_no) where "+ whereSql
      +" )ORDER BY UPDT_DATE DESC");
			
		}
		//根据终端号前两位定义受理机构,总行不受控制
		/*if (StringUtil.isNull(brhId)) {
			brhId = operator.getOprBrhId();
		}sa_model_kind
		if (!"9900".equals(brhId)){
			sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '" + brhId.substring(0, 2) + "'");
		}*/
//		sb.append(whereSql);
//		sb.append(" ORDER BY UPDT_DATE DESC");
		
		String sql = "";
		//卡类型
		if (!StringUtil.isNull(cardType) && !cardType.equals("*")) {
//			sql = "select * from ("+sb.toString() + ") x where card_tp = '" + cardType + "' ";
			sql = "select distinct * from (" + sb.toString() + ") x where card_tp = '" + cardType + "' ";
		}else{
//			sql = sb.toString();
			sql = "select distinct * from ( " +sb.toString()+ " )";
		}
		
		return sql;
	}
	
	private String startDate;
	private String endDate;
	private String cardType;
	private String txnNum;
	private String transWay;
	private String mchntNo;
	private String brhId;
	private String transamtsmall;
	private String transamtbig;
	
	private String termId;
	private String pan;
	private String retrivlRef;
	private String sysSeqNum;
	private String respCode;
	private String idbinStaNo;

	
	
	public String getIdbinStaNo() {
		return idbinStaNo;
	}


	public void setIdbinStaNo(String idbinStaNo) {
		this.idbinStaNo = idbinStaNo;
	}


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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getTransWay() {
		return transWay;
	}

	public void setTransWay(String transWay) {
		this.transWay = transWay;
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
	 * @return the txnNum
	 */
	public String getTxnNum() {
		return txnNum;
	}

	/**
	 * @param txnNum the txnNum to set
	 */
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
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

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
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
