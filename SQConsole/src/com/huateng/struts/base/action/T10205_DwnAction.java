package com.huateng.struts.base.action;


import java.io.FileOutputStream;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T10205_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 12);
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][3].equals("01")){
				data[i][3] = "借记卡";
			}else if(data[i][3].equals("00")){
				data[i][3] = "贷记卡";
			}else if(data[i][3].equals("02")){
				data[i][3] = "准贷记卡";
			}else if(data[i][3].equals("03")){
				data[i][3] = "预付费卡";
			}
		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T10205_DwnAction.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN10205RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN10205RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN10205RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String whereSql = "";
		if (!StringUtil.isNull(ind)) {
			whereSql += "AND IND = '"
					+ ind + "' ";
		}
		if (!StringUtil.isNull(insIdCd)) {
			whereSql += "AND INS_ID_CD = '" + insIdCd + "' ";
		}
		if (!StringUtil.isNull(cardDis)) {
			whereSql += "AND CARD_DIS = '" + cardDis + "' ";
		}
		if (!StringUtil.isNull(CardTp)) {
			whereSql += "AND CARD_TP = '" + CardTp + "' ";
		}
		//一磁偏移量
		if (!StringUtil.isNull(acc1Offset)) {
			whereSql += " AND ACC1_OFFSET = '" + acc1Offset + "' ";
		}
		//一磁长度
		if (!StringUtil.isNull(acc1Len)) {
			whereSql += " AND ACC1_LEN like '" + acc1Len.trim() + "' ";
		}
		//一磁所在磁道号
		if (!StringUtil.isNull(acc1Tnum)) {
			whereSql += " AND rtrim(ACC1_TNUM) like '" + acc1Tnum.trim() + "' ";
		}
		//卡bin偏移量
		if (!StringUtil.isNull(binOffSet)) {
			whereSql += " AND BIN_OFFSET = '" + binOffSet + "' ";
		}
		//卡bin长度
		if(!StringUtil.isNull(binLen)){
			whereSql += "AND BIN_LEN = '" + binLen + "' ";
		}
		//卡bin起始值
		if(!StringUtil.isNull(binStaNo)){
			whereSql += "AND BIN_STA_NO = '" + binStaNo + "' ";
		}
		//卡bin终值
		if(!StringUtil.isNull(binEndNo)){
			whereSql += "AND BIN_END_NO = '" + binEndNo + "' ";
		}
		//卡bin磁道号
		if(!StringUtil.isNull(binTnum)){
			whereSql += "ADN BIN_TNUM = '" + binTnum + "' ";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select * from ( SELECT IND,INS_ID_CD,CARD_DIS," +
				"CARD_TP,ACC1_OFFSET,ACC1_LEN,ACC1_TNUM,BIN_OFFSET," +
				"BIN_LEN,BIN_STA_NO,BIN_END_NO,BIN_TNUM FROM TBL_BANK_BIN_INF where 1 = 1  " );
		
		sb.append(whereSql);
		sb.append("ORDER BY INS_ID_CD)");
		return sb.toString();
	}
	
	private String ind;
	private String CardTp;
	
	private String insIdCd;
	//一磁偏移量
	private String acc1Offset;	
    //一磁长度
	private String acc1Len;
	//一磁所在磁道号
	private String acc1Tnum;	
	//二磁偏移量
	private String acc2Offset;	
	//二磁长度
	private String acc2Len;	
	//二磁所在磁道号
	private String acc2Tnum;
	//卡bin偏移量
	private String binOffSet;
	//卡bin长度
	private String binLen;
	//卡bin起始值
	private String binStaNo;	
	//卡bin结束值
	private String binEndNo;	
	//卡bin所在磁道号
	private String binTnum;	
	//描述
	private String cardDis;	
	
	private String tblBankBinInfList;

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getCardTp() {
		return CardTp;
	}

	public void setCardTp(String cardTp) {
		CardTp = cardTp;
	}

	public String getInsIdCd() {
		return insIdCd;
	}

	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}

	public String getAcc1Offset() {
		return acc1Offset;
	}

	public void setAcc1Offset(String acc1Offset) {
		this.acc1Offset = acc1Offset;
	}

	public String getAcc1Len() {
		return acc1Len;
	}

	public void setAcc1Len(String acc1Len) {
		this.acc1Len = acc1Len;
	}

	public String getAcc1Tnum() {
		return acc1Tnum;
	}

	public void setAcc1Tnum(String acc1Tnum) {
		this.acc1Tnum = acc1Tnum;
	}

	public String getAcc2Offset() {
		return acc2Offset;
	}

	public void setAcc2Offset(String acc2Offset) {
		this.acc2Offset = acc2Offset;
	}

	public String getAcc2Len() {
		return acc2Len;
	}

	public void setAcc2Len(String acc2Len) {
		this.acc2Len = acc2Len;
	}

	public String getAcc2Tnum() {
		return acc2Tnum;
	}

	public void setAcc2Tnum(String acc2Tnum) {
		this.acc2Tnum = acc2Tnum;
	}

	public String getBinOffSet() {
		return binOffSet;
	}

	public void setBinOffSet(String binOffSet) {
		this.binOffSet = binOffSet;
	}

	public String getBinLen() {
		return binLen;
	}

	public void setBinLen(String binLen) {
		this.binLen = binLen;
	}

	public String getBinStaNo() {
		return binStaNo;
	}

	public void setBinStaNo(String binStaNo) {
		this.binStaNo = binStaNo;
	}

	public String getBinEndNo() {
		return binEndNo;
	}

	public void setBinEndNo(String binEndNo) {
		this.binEndNo = binEndNo;
	}

	public String getBinTnum() {
		return binTnum;
	}

	public void setBinTnum(String binTnum) {
		this.binTnum = binTnum;
	}

	public String getCardDis() {
		return cardDis;
	}

	public void setCardDis(String cardDis) {
		this.cardDis = cardDis;
	}

	public String getTblBankBinInfList() {
		return tblBankBinInfList;
	}

	public void setTblBankBinInfList(String tblBankBinInfList) {
		this.tblBankBinInfList = tblBankBinInfList;
	}


}
