package com.huateng.struts.mchnt.action;


import java.io.FileOutputStream;

import org.jaxen.function.SubstringAfterFunction;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T20707_DwnAction extends ReportBaseAction{


	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 8);
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			if(data[i][2].equals("0")){
				data[i][2] = "新增待审核";
			}else if(data[i][2].equals("1")){
				data[i][2] = "已删除";
			}else if(data[i][2].equals("2")){
				data[i][2] = "正常";
			}else if(data[i][2].equals("3")){
				data[i][2] = "修改待审核";
			}else if(data[i][2].equals("4")){
				data[i][2] = "删除待审核";
			}
			
			
			if(data[i][4] != null){
				String param = data[i][4].toString().trim();
				if(param.equals("1")){
					data[i][4] = "按笔收费 (元)";
				}else if(param.equals("2")){
					data[i][4] = "按比例收费(%)";
				}else{
					data[i][4] = " ";
				}
			}else{
				data[i][4] = " ";
			}
		}
		
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		
		reportCreator.initReportData(getJasperInputSteam("T20707_DwnAction.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN20707RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN20707RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN20707RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer("where 1=1");

		if (isNotEmpty(discCd)) {
			whereSql.append(" and t.DISC_CD = '"
					+ discCd + "' ");
		}
		if (isNotEmpty(discNm)) {
			whereSql.append(" and t.DISC_NM = '"
					+ discNm + "' ");
		}
		if (isNotEmpty(discOrg)) {
			whereSql.append(" and t.DISC_ORG = '"
					+ discOrg + "' ");
		}

		
		String sql = "SELECT  t.DISC_CD," +      //计费算法代码
				"t.DISC_NM," +                    //计费算法名称
				"t.SA_STATE, "+                    //状态
				"t2.floor_amount, "+               //最低交易金额
				"t2.flag, "+                       //回佣类型
				"t2.fee_value, "+                  //回佣值
				"t2.min_fee, "+                     //按比最低收费（元）
				"t2.max_fee "+                    //按比最高收费（元）
				"from tbl_inf_disc_cd_temp t left join Tbl_his_disc_algo_tmp t2 on t.disc_cd = t2.disc_id "
				+ whereSql.toString();
		return sql.toString();
	}
	
	private String discCd;
	private String discNm;
	
	private String discOrg;

	public String getDiscCd() {
		return discCd;
	}

	public void setDiscCd(String discCd) {
		this.discCd = discCd;
	}

	public String getDiscNm() {
		return discNm;
	}

	public void setDiscNm(String discNm) {
		this.discNm = discNm;
	}

	public String getDiscOrg() {
		return discOrg;
	}

	public void setDiscOrg(String discOrg) {
		this.discOrg = discOrg;
	}
	
	




}
