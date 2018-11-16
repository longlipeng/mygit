package com.huateng.struts.rout.action;


import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T11031_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 18);
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][10].equals("0")){
				data[i][10] = "新增待审核";
			}else if(data[i][10].equals("1")){
				data[i][10] = "已删除";
			}else if(data[i][10].equals("2")){
				data[i][10] = "正常";
			}else if(data[i][10].equals("3")){
				data[i][10] = "修改待审核";
			}else if(data[i][10].equals("4")){
				data[i][10] = "删除待审核";
			}
		}
		
		
		//发卡机构翻译
		String cardBin = "select distinct(trim(INS_ID_CD)), trim(INS_ID_CD) ||' - '|| trim(CARD_DIS) from TBL_BANK_BIN_INF ";         
		List<Object[]> CARD_DIS =CommonFunction.getCommQueryDAO().findBySQLQuery(cardBin);
		for (int i = 0; i < data.length; i++) {
			if(data[i][0] != null && !"".equals(data[i][0].toString().trim())){//MCC
				if(CARD_DIS !=null && CARD_DIS.size() >0){
					for(int j = 0; j < CARD_DIS.size(); j++){
						if(data[i][0].toString().trim().equals(CARD_DIS.get(j)[0].toString().trim())){
							data[i][0] = CARD_DIS.get(j)[1].toString();
						}
					}
				}else{
					data[i][0] = data[i][0].toString().trim();
				}
			}else{
				data[i][0] = "";
			}						
		}
		
		//地区翻译
		String sql = "select  trim(city_code),trim(city_code)|| '-' || trim(city_des) from tbl_city_code ";         
		List<Object[]> param1 =CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		for (int i = 0; i < data.length; i++) {
			if(data[i][4] != null && !"".equals(data[i][4].toString().trim())){//MCC
				if(param1 !=null && param1.size() >0){
					for(int j = 0; j < param1.size(); j++){
						if(data[i][4].toString().trim().equals(param1.get(j)[0].toString().trim())){
							data[i][4] = param1.get(j)[1].toString();
						}
					}
				}else{
					data[i][4] = data[i][4].toString().trim();
				}
			}else{
				data[i][4] = "";
			}						
		}
		
		//受理商编号 翻译
		String sql2 = "select mcht_no,mcht_no|| '-' ||mcht_nm from TBL_MCHT_BASE_INF_TMP ";         
		List<Object[]> param2 =CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
		for (int i = 0; i < data.length; i++) {
			if(data[i][6] != null && !"".equals(data[i][6].toString().trim())){//MCC
				if(param2 !=null && param2.size() >0){
					for(int j = 0; j < param2.size(); j++){
						if(data[i][6].toString().trim().equals(param2.get(j)[0].toString().trim())){
							data[i][6] = param2.get(j)[1].toString();
						}
					}
				}else{
					data[i][6] = data[i][6].toString().trim();
				}
			}else{
				data[i][6] = "";
			}						
		}
		
		//业务类型翻译
				String bussType = "SELECT KEY, KEY ||' - '||VALUE FROM CST_SYS_PARAM WHERE OWNER = 'SERVICECODE' ";         
				List<Object[]> value =CommonFunction.getCommQueryDAO().findBySQLQuery(bussType);
				for (int i = 0; i < data.length; i++) {
					if(data[i][1] != null && !"".equals(data[i][1].toString().trim())){//MCC
						if(value !=null && value.size() >0){
							for(int j = 0; j < value.size(); j++){
								if(data[i][1].toString().trim().equals(value.get(j)[0].toString().trim())){
									data[i][1] = value.get(j)[1].toString();
								}
							}
						}else{
							data[i][1] = data[i][1].toString().trim();
						}
					}else{
						data[i][1] = "";
					}						
				}
		
		//交易类型翻译		
			String txnNum = "SELECT KEY, KEY ||' - '||VALUE FROM CST_SYS_PARAM WHERE OWNER = 'TXNTYPE'"; 
			List<Object[]> value2 =CommonFunction.getCommQueryDAO().findBySQLQuery(txnNum);
			for (int i = 0; i < data.length; i++) {
				if(data[i][2] != null && !"".equals(data[i][2].toString().trim())){//MCC
					if(value2 !=null && value2.size() >0){
						for(int j = 0; j < value2.size(); j++){
							if(data[i][2].toString().trim().equals(value2.get(j)[0].toString().trim())){
								data[i][2] = value2.get(j)[1].toString();
							}
						}
					}else{
						data[i][2] = data[i][2].toString().trim();
					}
				}else{
					data[i][2] = "";
				}						
			}
			
			
		//渠道翻译	
			String channel = "SELECT KEY, KEY ||' - '||VALUE FROM CST_SYS_PARAM WHERE OWNER = 'CHANNEL'"; 
			List<Object[]> value3 =CommonFunction.getCommQueryDAO().findBySQLQuery(channel);
			for (int i = 0; i < data.length; i++) {
				if(data[i][2] != null && !"".equals(data[i][2].toString().trim())){//MCC
					if(value3 !=null && value3.size() >0){
						for(int j = 0; j < value3.size(); j++){
							if(data[i][3].toString().trim().equals(value3.get(j)[0].toString().trim())){
								data[i][3] = value3.get(j)[1].toString();
							}
						}
					}else{
						data[i][3] = data[i][3].toString().trim();
					}
				}else{
					data[i][3] = "";
				}						
			}
			
     //卡类型翻译			
			
			for (int i = 0; i < data.length; i++) {
				if(data[i][5].equals("01")){
					data[i][5] = "借记卡";
				}else if(data[i][5].equals("00")){
					data[i][5] = "贷记卡";
				}else if(data[i][5].equals("*")){
					data[i][5] = "支持所有类型";				
				}
			}
			
		//目的机构
			String destInstId = "SELECT AGEN_ID,AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE"; 
			List<Object[]> agentName =CommonFunction.getCommQueryDAO().findBySQLQuery(destInstId);
			for (int i = 0; i < data.length; i++) {
				if(data[i][7] != null && !"".equals(data[i][7].toString().trim())){//MCC
					if(agentName !=null && agentName.size() >0){
						for(int j = 0; j < agentName.size(); j++){
							if(data[i][7].toString().trim().equals(agentName.get(j)[0].toString().trim())){
								data[i][7] = agentName.get(j)[1].toString();
							}
						}
					}else{
						data[i][7] = data[i][7].toString().trim();
					}
				}else{
					data[i][7] = "";
				}						
			}
			
			//MCC码
		    String sqlMcc = "select a.mchnt_tp as tp ,'('||b.descr||')'||a.mchnt_tp ||' - '||a.descr as tpdesc from tbl_inf_mchnt_tp a left join tbl_inf_mchnt_tp_grp b on a.mchnt_tp_grp = b.mchnt_tp_grp"
					 + "  union select trim(key) as tp,trim(key) ||' - ' ||trim(value) as tpdesc from cst_sys_param where owner='ALLMCC' and type='00' ";
			List<Object[]> mchtMcc =CommonFunction.getCommQueryDAO().findBySQLQuery(sqlMcc);
			
			for (int i = 0; i < data.length; i++) {
				if(data[i][17] != null && !"".equals(data[i][17].toString().trim())){//MCC
					if(mchtMcc !=null && mchtMcc.size() >0){
						for(int j = 0; j < mchtMcc.size(); j++){
							if(data[i][17].toString().trim().equals(mchtMcc.get(j)[0].toString().trim())){
								data[i][17] = mchtMcc.get(j)[1].toString();
							}
						}
					}else{
						data[i][17] = data[i][17].toString().trim();
					}
				}else{
					data[i][17] = "";
				}						
			}
			
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T11031_DwnAction.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN11031RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN11031RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN11031RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(cardBin)) {
			whereSql.append(" and t.CARD_BIN = '"
					+ cardBin + "' ");
		}
		if (isNotEmpty(bussType)) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ bussType + "' ");
		}
		if (isNotEmpty(txnNum)) {
			whereSql.append(" and t.TXN_NUM = '"
					+ txnNum + "' ");
		}
		if (isNotEmpty(channel)) {
			whereSql.append(" and t.CHANNEL = '"
					+ channel + "' ");
		}
		if (isNotEmpty(areaNo)) {
			whereSql.append(" and t.AREA_NO = '"
					+ areaNo + "' ");
		}
		if (isNotEmpty(cardType)) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ cardType + "' ");
		}
		if (isNotEmpty(mchntId)) {
			whereSql.append(" and t.MERCH_ID = '"
					+ mchntId + "' ");
		}
//		if (isNotEmpty("reserved")) {
//			whereSql.append(" and t.RESERVED = '"
//					+ reserved + "' ");
//		}
		if (isNotEmpty(destInstId)) {
			whereSql.append(" and trim(t.DEST_INST_ID) = '"
					+ destInstId.split("-")[0] + "' ");
		}
		if (isNotEmpty(status)) {
			whereSql.append(" and trim(t.SA_STATE) = '"
					+ status + "' ");
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ startDate + "000000' ");
		}

		if (!StringUtil.isNull(endDate)) {
			whereSql.append(" and t.CRE_TIME <= '"
					+ endDate + "235959' ");
		}
		if (isNotEmpty(mchtMcc)) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ mchtMcc + "' ");
		}

		/*String sql = "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.CARD_TYPE,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,"
				+ "t.DEST_INST_ID,t.MAX_AMT,t.MIN_AMT,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.UPDATE_TIME,t.CREATOR_ID,t.CHECK_TIME,t.CHECK_ID"
				+ " FROM tbl_txn_route_inf_temp t,tbl_city_code c,TBL_MCHT_BASE_INF m "
				+ " where trim(c.city_code)=trim(t.area_no) And m.mcht_no=t.merch_id "
				+ whereSql.toString()
				+ " Union "
				+ "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.CARD_TYPE,d.Value as MERCH_ID,"
				+ "t.DEST_INST_ID,t.MAX_AMT,t.MIN_AMT,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.UPDATE_TIME,t.CREATOR_ID,t.CHECK_TIME,t.CHECK_ID"
				+ " FROM tbl_txn_route_inf_temp t,tbl_city_code c,cst_sys_param d "
				+ " where trim(c.city_code)=trim(t.area_no) And trim(t.merch_id)=d.Key "
				+ " and d.owner='ALLMCHNT' and d.type='00' And d.Key='*' "
				+ whereSql.toString() + " )";*/
		
		String sql = "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO,t.CARD_TYPE,t.MERCH_ID as MERCH_ID,"
				+ "t.DEST_INST_ID,t.MAX_AMT/100,t.MIN_AMT/100,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.UPDATE_TIME,t.CREATOR_ID,t.CHECK_TIME,t.CHECK_ID,t.MCHT_MCC"
				+ " FROM tbl_txn_route_inf_temp t "
				+ " where 1=1 "
				+ whereSql.toString();
		
//		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		if (isNotEmpty("orderOption")) {
			if ("binasc".equals("orderOption".trim())) {
				// sql += " order by t.CARD_BIN asc ";
				sql += " order by CARD_BIN asc ";
			}
			if ("bindesc".equals("orderOption".trim())) {
				// sql += " order by t.CARD_BIN desc ";
				sql += " order by CARD_BIN desc ";
			}
			if ("mchtasc".equals("orderOption".trim())) {
				// sql += " order by t.MERCH_ID asc ";
				sql += " order by MERCH_ID asc ";
			}
			if ("mchtdesc".equals("orderOption".trim())) {
				// sql += " order by t.MERCH_ID desc ";
				sql += " order by MERCH_ID desc ";
			}
			if ("timeasc".equals("orderOption")) {
				sql += " order by UPDATE_TIME asc ";
			}
			if ("timedesc".equals("orderOption")) {
				sql += " order by UPDATE_TIME desc ";
			}
		}
		return sql.toString();
	}
	
	private String cardBin;
	private String bussType;
	
	private String txnNum;

	private String channel;	

	private String areaNo;

	private String cardType;	

	private String mchntId;	

	private String destInstId;	

	private String orderOption;

	private String status;
	
	private String startDate;	

	private String endDate;
	
	private String mchtMcc;

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getDestInstId() {
		return destInstId;
	}

	public void setDestInstId(String destInstId) {
		this.destInstId = destInstId;
	}

	public String getOrderOption() {
		return orderOption;
	}

	public void setOrderOption(String orderOption) {
		this.orderOption = orderOption;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMchtMcc() {
		return mchtMcc;
	}

	public void setMchtMcc(String mchtMcc) {
		this.mchtMcc = mchtMcc;
	}	

    

}
