package com.huateng.common.grid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.groovy.tools.shell.commands.ShowCommand;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.log.Log;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.FileFilter;
import com.huateng.system.util.FtpUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:信息列表动态获取方法集合
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-6
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 */
public class GridConfigMethod {
	private static final String FILE_PREFIX_PAY = "PAY";
	// private static final String INST_ID_ICBC = "01020000";
	// private static final String INST_ID_CBC = "01050000";
	private static final String INST_ID_ICBC = "01025840";
	private static final String INST_ID_CBC = "01052900";
	private static final String INST_ID_BOCOM = "03014520";

	/**
	 * 查询机构信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhInfoBelow(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("brhName"))) {
			whereSql.append(" AND BRH_NAME  like '%"
					+ request.getParameter("brhName") + "%' ");
		}

		String sql = "SELECT BRH_ID,BRH_LEVEL,UP_BRH_ID,BRH_NAME,BRH_ADDR,BRH_TEL_NO,POST_CD,"
				+ "BRH_CONT_NAME,CUP_BRH_ID,RESV1 FROM TBL_BRH_INFO "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFO "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代理商成本费率维护
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblAgentFee(int begin, HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("extend1"))) {
			whereSql.append(" and a.extend1 = '"
					+ request.getParameter("extend1") + "' ");
		}

		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql.append(" AND a.agent_no = '"
					+ request.getParameter("agentNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("mccCode"))) {
			whereSql.append(" AND a.mcc_code = '"
					+ request.getParameter("mccCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("feeMin"))) {
			whereSql.append(" AND a.fee_min = '"
					+ request.getParameter("feeMin").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("feeMax"))) {
			whereSql.append(" AND a.fee_max = '"
					+ request.getParameter("feeMax") + "' ");
		}
		if (isNotEmpty(request.getParameter("feeValue"))) {
			whereSql.append(" AND a.fee_value = '"
					+ request.getParameter("feeValue") + "' ");
		}
		if (isNotEmpty(request.getParameter("state"))) {
			whereSql.append(" AND a.state = '" + request.getParameter("state")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("feeType"))) {
			whereSql.append(" AND a.fee_type = '"
					+ request.getParameter("feeType") + "' ");
		}
		if (isNotEmpty(request.getParameter("mccGrp"))) {
			whereSql.append(" and a.MCC_CODE in (select mchnt_tp from TBL_INF_MCHNT_TP WHERE MCHNT_TP_GRP = '"
					+ request.getParameter("mccGrp").trim() + "') ");
		}
		if (isNotEmpty(request.getParameter("state_query"))) {
			whereSql.append(" AND a.state = '"
					+ request.getParameter("state_query") + "' ");
		}

		String sql = "SELECT a.UUID,trim(a.AGENT_NO) ||' - '|| trim(b.AGENT_NM),a.MCC_CODE,a.AGENT_NM,a.FEE_MIN,a.FEE_MAX,a.FEE_VALUE,a.FEE_TYPE,a.STATE,a.CRT_PER,a.CRT_DATE,a.UP_PER,a.UP_DATE,a.extend1,a.extend2,a.extend3,a.extend4,a.extend5 "
				+ " FROM tbl_agent_fee_tmp a left join tbl_agent_info b on a.agent_no = b.agent_no where 1=1 "
				+ whereSql.toString();
		sql += "order by a.agent_no,a.state,a.crt_Date desc,a.MCC_CODE ";
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代理商成本费率拒绝原因查看
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblAgentFeeRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("extend1"))) {
			whereSql.append(" and a.extend1 = '"
					+ request.getParameter("extend1") + "' ");
		}

		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql.append(" AND a.agent_no = '"
					+ request.getParameter("agentNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("mccCode"))) {
			whereSql.append(" AND a.mcc_code = '"
					+ request.getParameter("mccCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("feeMin"))) {
			whereSql.append(" AND a.fee_min = '"
					+ request.getParameter("feeMin").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("feeMax"))) {
			whereSql.append(" AND a.fee_max = '"
					+ request.getParameter("feeMax") + "' ");
		}
		if (isNotEmpty(request.getParameter("feeValue"))) {
			whereSql.append(" AND a.fee_value = '"
					+ request.getParameter("feeValue") + "' ");
		}
		if (isNotEmpty(request.getParameter("state"))) {
			whereSql.append(" AND a.state = '" + request.getParameter("state")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("feeType"))) {
			whereSql.append(" AND a.fee_type = '"
					+ request.getParameter("feeType") + "' ");
		}
		if (isNotEmpty(request.getParameter("mccGrp"))) {
			whereSql.append(" and a.MCC_CODE in (select mchnt_tp from TBL_INF_MCHNT_TP WHERE MCHNT_TP_GRP = '"
					+ request.getParameter("mccGrp").trim() + "') ");
		}

		String sql = "SELECT a.UUID,trim(a.AGENT_NO) ||' - '|| trim(b.AGENT_NM),a.MCC_CODE,a.AGENT_NM,a.FEE_MIN,a.FEE_MAX,a.FEE_VALUE,a.FEE_TYPE,a.STATE,a.CRT_PER,a.CRT_DATE,a.UP_PER,a.UP_DATE,a.extend1,a.extend2,a.extend3,a.extend4,a.extend5,a.remark "
				+ " FROM tbl_agent_fee_refuse_info a left join tbl_agent_info b on a.agent_no = b.agent_no where 1=1 "
				+ whereSql.toString();
		sql += "order by crt_Date";
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代理商成本费率维护check
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblAgentFeeCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("extend1"))) {
			whereSql.append(" and a.extend1 = '"
					+ request.getParameter("extend1") + "' ");
		}

		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql.append(" AND a.agent_no = '"
					+ request.getParameter("agentNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("mccCode"))) {
			whereSql.append(" AND a.mcc_code = '"
					+ request.getParameter("mccCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("feeMin"))) {
			whereSql.append(" AND a.fee_min = '"
					+ request.getParameter("feeMin").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("feeMax"))) {
			whereSql.append(" AND a.fee_max = '"
					+ request.getParameter("feeMax") + "' ");
		}
		if (isNotEmpty(request.getParameter("feeValue"))) {
			whereSql.append(" AND a.fee_value = '"
					+ request.getParameter("feeValue") + "' ");
		}
		if (isNotEmpty(request.getParameter("state"))) {
			whereSql.append(" AND a.state = '" + request.getParameter("state")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("feeType"))) {
			whereSql.append(" AND a.fee_type = '"
					+ request.getParameter("feeType") + "' ");
		}
		if (isNotEmpty(request.getParameter("mccGrp"))) {
			whereSql.append(" and a.MCC_CODE in (select mchnt_tp from TBL_INF_MCHNT_TP WHERE MCHNT_TP_GRP = '"
					+ request.getParameter("mccGrp").trim() + "') ");
		}
		if (isNotEmpty(request.getParameter("state_query"))) {
			whereSql.append(" AND a.state = '"
					+ request.getParameter("state_query") + "' ");
		}

		String sql = "SELECT a.UUID,trim(a.AGENT_NO) ||' - '|| trim(b.AGENT_NM),a.MCC_CODE,a.AGENT_NM,a.FEE_MIN,a.FEE_MAX,a.FEE_VALUE,a.FEE_TYPE,a.STATE,a.CRT_PER,a.CRT_DATE,a.UP_PER,a.UP_DATE,a.extend1,a.extend2,a.extend3,a.extend4,a.extend5 "
				+ " FROM tbl_agent_fee_tmp a left join tbl_agent_info b on a.agent_no = b.agent_no where state not in(1,2) "
				+ whereSql.toString();
		sql += " order by a.agent_no,a.state,a.crt_Date desc,a.MCC_CODE ";
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户调账信息列表 01041000
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChangeAccInfTmpShen(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("instCode"))) {
			whereSql.append(" AND INST_CODE = '"
					+ request.getParameter("instCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" AND TERM_ID = '"
					+ request.getParameter("termId").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("changeAccount"))) {
			whereSql.append(" AND CHANGE_ACCOUNT/100 = '"
					+ request.getParameter("changeAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeFlag"))) {
			whereSql.append(" AND CHANGE_FLAG = '"
					+ request.getParameter("changeFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("stQ"))) {
			whereSql.append(" AND ST <> '" + request.getParameter("stQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("enterTpQ"))) {
			whereSql.append(" AND ENTER_TP = '"
					+ request.getParameter("enterTpQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("confirmAccount"))) {
			whereSql.append(" AND COMFIRM_ACCOUNT = '"
					+ request.getParameter("confirmAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeDate"))) {
			whereSql.append(" AND substr(CHANGE_DATE,1,8) = '"
					+ request.getParameter("changeDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("uptTsQ"))) {
			whereSql.append(" AND substr(UPD_TS,1,8) = '"
					+ request.getParameter("uptTsQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("aprTsQ"))) {
			whereSql.append(" AND substr(APR_TS,1,8) = '"
					+ request.getParameter("aprTsQ") + "' ");
		}

		String sql = "SELECT MCHT_NO,TERM_ID,CHANGE_ACCOUNT/100,CHANGE_REASON,CHANGE_FLAG,COMFIRM_ACCOUNT,CHANGE_DATE,INS_TS,INS_OPR,UPD_TS,UPD_OPR,APR_TS,APR_OPR,ST,ENTER_TP,INST_CODE "
				+ " FROM tbl_change_acc_inf_tmp a where 1=1 "
				+ whereSql.toString();
		sql += "order by CHANGE_DATE";
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户调账信息列表 01041000
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChangeAccInfTmpWeih(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("instCode"))) {
			whereSql.append(" AND INST_CODE = '"
					+ request.getParameter("instCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" AND TERM_ID = '"
					+ request.getParameter("termId").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("changeAccount"))) {
			whereSql.append(" AND CHANGE_ACCOUNT = '"
					+ request.getParameter("changeAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeFlag"))) {
			whereSql.append(" AND CHANGE_FLAG = '"
					+ request.getParameter("changeFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("stQ"))) {
			whereSql.append(" AND ST <> '" + request.getParameter("stQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("enterTpQ"))) {
			whereSql.append(" AND ENTER_TP = '"
					+ request.getParameter("enterTpQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("confirmAccount"))) {
			whereSql.append(" AND COMFIRM_ACCOUNT/100 = '"
					+ request.getParameter("confirmAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeDate"))) {
			whereSql.append(" AND substr(CHANGE_DATE,1,8) = '"
					+ request.getParameter("changeDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("uptTsQ"))) {
			whereSql.append(" AND substr(UPD_TS,1,8) = '"
					+ request.getParameter("uptTsQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("aprTsQ"))) {
			whereSql.append(" AND substr(APR_TS,1,8) = '"
					+ request.getParameter("aprTsQ") + "' ");
		}

		String sql = "SELECT MCHT_NO,TERM_ID,CHANGE_ACCOUNT/100,CHANGE_REASON,CHANGE_FLAG,COMFIRM_ACCOUNT,CHANGE_DATE,INS_TS,INS_OPR,UPD_TS,UPD_OPR,APR_TS,APR_OPR,ST,ENTER_TP,INST_CODE "
				+ " FROM tbl_change_acc_inf_tmp a where 1=1 "
				+ whereSql.toString();
		sql += "order by CHANGE_DATE";
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户调账信息列表 01041000
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChangeAccInfTmp(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("instCode"))) {
			whereSql.append(" AND INST_CODE = '"
					+ request.getParameter("instCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" AND TERM_ID = '"
					+ request.getParameter("termId").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("changeAccount"))) {
			whereSql.append(" AND CHANGE_ACCOUNT/100 = '"
					+ request.getParameter("changeAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeFlag"))) {
			whereSql.append(" AND CHANGE_FLAG = '"
					+ request.getParameter("changeFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("stQ"))) {
			whereSql.append(" AND ST = '" + request.getParameter("stQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("enterTpQ"))) {
			whereSql.append(" AND ENTER_TP = '"
					+ request.getParameter("enterTpQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("confirmAccount"))) {
			whereSql.append(" AND COMFIRM_ACCOUNT = '"
					+ request.getParameter("confirmAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeDate"))) {
			whereSql.append(" AND substr(CHANGE_DATE,1,8) = '"
					+ request.getParameter("changeDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("uptTsQ"))) {
			whereSql.append(" AND substr(UPD_TS,1,8) = '"
					+ request.getParameter("uptTsQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("aprTsQ"))) {
			whereSql.append(" AND substr(APR_TS,1,8) = '"
					+ request.getParameter("aprTsQ") + "' ");
		}

		String sql = "SELECT MCHT_NO,TERM_ID,CHANGE_ACCOUNT/100,CHANGE_REASON,CHANGE_FLAG,COMFIRM_ACCOUNT,CHANGE_DATE,INS_TS,INS_OPR,UPD_TS,UPD_OPR,APR_TS,APR_OPR,ST,ENTER_TP,INST_CODE "
				+ " FROM tbl_change_acc_inf_tmp a where 1=1 "
				+ whereSql.toString();
		sql += "order by CHANGE_DATE";
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户调账拒绝列表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getChangeAccInfRefuse(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("instCode"))) {
			whereSql.append(" AND INST_CODE = '"
					+ request.getParameter("instCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" AND TERM_ID = '"
					+ request.getParameter("termId").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("changeAccount"))) {
			whereSql.append(" AND CHANGE_ACCOUNT/100 = '"
					+ request.getParameter("changeAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeFlag"))) {
			whereSql.append(" AND CHANGE_FLAG = '"
					+ request.getParameter("changeFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("stQ"))) {
			whereSql.append(" AND ST = '" + request.getParameter("stQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("enterTpQ"))) {
			whereSql.append(" AND ENTER_TP = '"
					+ request.getParameter("enterTpQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("confirmAccount"))) {
			whereSql.append(" AND COMFIRM_ACCOUNT = '"
					+ request.getParameter("confirmAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeDate"))) {
			whereSql.append(" AND substr(CHANGE_DATE,1,8) = '"
					+ request.getParameter("changeDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("uptTsQ"))) {
			whereSql.append(" AND substr(UPD_TS,1,8) = '"
					+ request.getParameter("uptTsQ") + "' ");
		}
		if (isNotEmpty(request.getParameter("aprTsQ"))) {
			whereSql.append(" AND substr(APR_TS,1,8) = '"
					+ request.getParameter("aprTsQ") + "' ");
		}

		String sql = "SELECT MCHT_NO,TERM_ID,CHANGE_ACCOUNT/100,CHANGE_REASON,CHANGE_FLAG,COMFIRM_ACCOUNT,CHANGE_DATE,INS_TS,INS_OPR,UPD_TS,UPD_OPR,APR_TS,APR_OPR,ST,ENTER_TP,remark,INST_CODE "
				+ " FROM tbl_change_acc_inf_refuse a where 1=1 "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户调账审核信息列表 01041000
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getchangeAccInfTmpCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[6];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" AND MCHT_NO = '" + request.getParameter("mchtNo")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" AND TERM_ID = '" + request.getParameter("termId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("changeAccount"))) {
			whereSql.append(" AND CHANGE_ACCOUNT/100 = '"
					+ request.getParameter("changeAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeFlag"))) {
			whereSql.append(" AND CHANGE_FLAG = '"
					+ request.getParameter("changeFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("confirmAccount"))) {
			whereSql.append(" AND COMFIRM_ACCOUNT = '"
					+ request.getParameter("confirmAccount") + "' ");
		}
		if (isNotEmpty(request.getParameter("changeDate"))) {
			whereSql.append(" AND substr(CHANGE_DATE,1,8) = '"
					+ request.getParameter("changeDate") + "' ");
		}

		String sql = "SELECT MCHT_NO,TERM_ID,CHANGE_ACCOUNT/100,CHANGE_REASON,CHANGE_FLAG,COMFIRM_ACCOUNT,CHANGE_DATE,INS_TS,INS_OPR,UPD_TS,UPD_OPR,APR_TS,APR_OPR,ST "
				+ " FROM tbl_change_acc_inf_tmp a where ST <> '0' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 向中行签到信息列表 01041000
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAttendanceInfoBelow(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mcht_cd"))) {
			whereSql.append(" AND MCHT_ID = '"
					+ request.getParameter("mcht_cd") + "' ");
		}
		if (isNotEmpty(request.getParameter("term_id"))) {
			whereSql.append(" AND TERM_ID = '"
					+ request.getParameter("term_id") + "' ");
		}
		// String sql =
		// "SELECT INST_ID,INST_NAME,b.MCHT_NO||' - '||b.MCHT_NM MCHT_nm,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,BATCHNO,RESP_CODE "
		// +
		// " FROM tbl_sign_inf a,TBL_MCHT_BASE_INF_TMP b where a.TERMID_VALID='1' and a.mcht_id=b.MCHT_NO and trim(INST_ID)='04032900' "
		//
		// + whereSql.toString();

		String wheresql = "SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"
				+ request.getParameter("Aowner") + "' ";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);
		System.out.println(request.getParameter("Aowner"));
		String sql = "SELECT INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,BATCHNO,RESP_CODE "
				+ " FROM tbl_sign_inf a where a.TERMID_VALID='1' and trim(INST_ID) ='"
				+ list.get(0) + "' " + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 向农行签到信息列表 01032900
	 * 
	 * @param begin
	 * @param request
	 *            2012-09-21 created
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAttendanceInfoBelow2(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mcht_cd"))) {
			whereSql.append(" AND MCHT_ID = '"
					+ request.getParameter("mcht_cd") + "' ");
		}
		if (isNotEmpty(request.getParameter("term_id"))) {
			whereSql.append(" AND TERM_ID = '"
					+ request.getParameter("term_id") + "' ");
		}
		String sql = "SELECT INST_ID,INST_NAME,mcht_id||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = MCHT_ID),MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,BATCHNO,RESP_CODE "
				+ " FROM tbl_sign_inf where TERMID_VALID='1' and trim(INST_ID)='01032900' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询向工行签到信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getAttendanceInfoBelowForICBC(int begin,
			HttpServletRequest request) {
		return getAttendanceInfoByInstId(begin, request, INST_ID_ICBC);
	}

	/**
	 * 查询向建行签到信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getAttendanceInfoBelowForCBC(int begin,
			HttpServletRequest request) {
		return getAttendanceInfoByInstId(begin, request, INST_ID_CBC);
	}

	/**
	 * 查询向交行签到信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getAttendanceInfoBelowForBocom(int begin,
			HttpServletRequest request) {
		return getAttendanceInfoByInstId(begin, request, INST_ID_BOCOM);
	}

	@SuppressWarnings("unchecked")
	private static Object[] getAttendanceInfoByInstId(int begin,
			HttpServletRequest request, String instId) {
		Object[] ret = new Object[2];
		StringBuilder whereSql = new StringBuilder();
		if (isNotEmpty(request.getParameter("mcht_cd"))) {
			whereSql.append(" AND MCHT_ID = '"
					+ request.getParameter("mcht_cd") + "' ");
		}
		if (isNotEmpty(request.getParameter("term_id"))) {
			// mzhu 商户终端号 或者机构终端号
			String termId = request.getParameter("term_id");
			whereSql.append(" AND (TERM_ID = '"
					+ termId
					+ "' or trim(term_id) = (Select trim(ins_term) From tbl_term_channel_inf  Where  stat in('0','2','3','4') and mcht_term_id = '"
					+ termId + "' and TERM_INS='" + instId + "'))");

		}
		String sql = "SELECT INST_ID,INST_NAME,mcht_id||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = MCHT_ID),MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,BATCHNO,RESP_CODE "
				+ " FROM tbl_sign_inf where TERMID_VALID='1' and trim(INST_ID)='"
				+ instId + "'" + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询获取交行主密钥标志信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getTmkInfo(int begin, HttpServletRequest request) {
		return getTmkInfoByInstId(begin, request, INST_ID_BOCOM);
	}

	@SuppressWarnings("unchecked")
	private static Object[] getTmkInfoByInstId(int begin,
			HttpServletRequest request, String instId) {
		Object[] ret = new Object[2];
		StringBuilder whereSql = new StringBuilder();
		if (isNotEmpty(request.getParameter("mcht_cd"))) {
			whereSql.append(" AND MCHT_ID = '"
					+ request.getParameter("mcht_cd") + "' ");
		}
		if (isNotEmpty(request.getParameter("term_id"))) {
			// mzhu 商户终端号 或者机构终端号
			String termId = request.getParameter("term_id");
			whereSql.append(" AND (TERM_ID = '"
					+ termId
					+ "' or trim(term_id) = (Select trim(ins_term) From tbl_term_channel_inf  Where  stat in('0','2','3','4') and mcht_term_id = '"
					+ termId + "' and TERM_INS='" + instId + "'))");

		}
		String sql = "SELECT INST_ID,INST_NAME,mcht_id||' - '||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF_TMP WHERE MCHT_NO = MCHT_ID),MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,RESP_CODE "
				+ " FROM TBL_GET_TMK_INF where TERMID_VALID='1' and trim(INST_ID)='"
				+ instId + "'" + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 参数管理
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSysParamInfos(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("owner"))) {
			whereSql.append(" AND owner = '" + request.getParameter("owner")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("key"))) {
			whereSql.append(" AND key like '%" + request.getParameter("key")
					+ "%' ");
		}
		if (isNotEmpty(request.getParameter("value"))) {
			whereSql.append(" AND value like '%"
					+ request.getParameter("value") + "%' ");
		}
		String sql = "select owner,key,type,value,descr,reserve from cst_sys_param where type = '00'"
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		sql += " order by owner";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 分公司信息查询
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static Object[] getBranchInfoBelow(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" and a.state In('0','1','2','7','8') ");

		if (isNotEmpty(request.getParameter("brhId"))) {// 分公司编号
			whereSql.append(" And a.BRANCH_NO = '"
					+ request.getParameter("brhId") + "' ");
		}
		if (isNotEmpty(request.getParameter("branchname"))) {// 分公司名称
			whereSql.append(" And a.BRANCH_NAME  like '%"
					+ request.getParameter("branchname") + "%' ");
		}
		String sql = "SELECT BRANCH_NO,a.BRANCH_AREA,PHONE_NUM,BRANCH_POS,BRANCH_ADDR,LINKMAN,LINKMAN_PHO,LINKMAN_MAIL,BRANCH_FAX,"
				+ "BRANCH_NAME,b.STATUE_NAME,a.BRANCH_LEVEL,a.PARENT_BRANCH_ID state FROM tbl_branch_manage a,tbl_aduit_status b,TBL_CITY_CODE c "
				+ "where a.state=b.STATUE_ID and b.type='1' and c.CITY_CODE=a.BRANCH_AREA "
				+ whereSql.toString();

		String countSql = "SELECT COUNT(*) FROM tbl_branch_manage a,tbl_aduit_status b,TBL_CITY_CODE c where a.state=b.STATUE_ID and b.type='1' and c.CITY_CODE=a.BRANCH_AREA  "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	// 待审核查询
	@SuppressWarnings("unchecked")
	public static Object[] getBraStaInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" and a.state In('0','2','8') ");

		if (isNotEmpty(request.getParameter("brhId"))) {// 分公司编号
			whereSql.append(" And a.BRANCH_NO = '"
					+ request.getParameter("brhId") + "' ");
		}
		if (isNotEmpty(request.getParameter("branchname"))) {// 分公司名称
			whereSql.append(" And a.BRANCH_NAME  like '%"
					+ request.getParameter("branchname") + "%' ");
		}

		String sql = "SELECT BRANCH_NO,BRANCH_AREA||'-'||c.CITY_DES,PHONE_NUM,BRANCH_POS,BRANCH_ADDR,LINKMAN,LINKMAN_PHO,LINKMAN_MAIL,BRANCH_FAX,"
				+ "BRANCH_NAME,b.STATUE_NAME states,a.STATE,a.BRANCH_LEVEL,a.PARENT_BRANCH_ID FROM tbl_branch_manage a,tbl_aduit_status b,"
				+ "TBL_CITY_CODE c where a.state=b.STATUE_ID and b.type='1' and c.CITY_CODE=a.BRANCH_AREA "
				+ whereSql.toString();

		String countSql = "SELECT COUNT(*) FROM tbl_branch_manage a where 1=1 "
				+ whereSql.toString();
		;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询发卡机构名称
	 * 
	 * @return
	 */
	private static List<Object[]> getBankName(List<Object[]> dataList, int p) {
		Object[] data;
		if (dataList != null && dataList.size() > 0) {
			String sql = "SELECT distinct(trim(INS_ID_CD)),trim(INS_ID_CD)||' - '||trim(CARD_DIS) FROM TBL_BANK_BIN_INF"
					+ " union select trim(key),trim(value) from cst_sys_param where owner='ALLBANK' and type='00'";
			List<Object[]> list = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);
			for (int i = 0; i < dataList.size(); i++) {
				data = dataList.get(i);
				if (list != null && list.size() > 0) {
					for (Object[] tmp : list) {
						if (tmp[0].toString().trim()
								.equals(data[p].toString().trim())) {
							data[p] = tmp[1].toString().trim();
							/*
							 * System.out.println(tmp[16].toString()+"-----------"
							 * +tmp[17].toString());
							 */
						}
					}
				}
				dataList.set(i, data);
			}
		}

		return dataList;
	}

	// FIXME
	/**
	 * 路由信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRoutInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("cardBin"))) {
			whereSql.append(" and t.CARD_BIN = '"
					+ request.getParameter("cardBin") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and t.TXN_NUM = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and t.CHANNEL = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("areaNo"))/*
													 * &&!"*".equals(request.
													 * getParameter("areaNo"))
													 */) {
			whereSql.append(" and t.AREA_NO = '"
					+ request.getParameter("areaNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntId"))/*
														 * &&!"*".equals(request.
														 * getParameter
														 * ("mchntId"))
														 */) {
			whereSql.append(" and t.MERCH_ID = '"
					+ request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("reserved"))) {
			whereSql.append(" and t.RESERVED = '"
					+ request.getParameter("reserved") + "' ");
		}
		if (isNotEmpty(request.getParameter("destInstId"))) {
			whereSql.append(" and trim(t.DEST_INST_ID) = '"
					+ request.getParameter("destInstId").split("-")[0] + "' ");
		}
		if (isNotEmpty(request.getParameter("status"))) {
			whereSql.append(" and trim(t.SA_STATE) = '"
					+ request.getParameter("status") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.UPDATE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}

		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and t.UPDATE_TIME <= '"
					+ request.getParameter("endDate") + "235959' ");
		}
		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		// String sql =
		// "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,"
		// +
		// "t.DEST_INST_ID,t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100 "
		// +
		// " FROM tbl_txn_route_inf_temp t,tbl_city_code c,TBL_MCHT_BASE_INF m "
		// +
		// " where trim(c.city_code)=trim(t.area_no) And m.mcht_no=t.merch_id "
		// + whereSql.toString()
		/*
		 * String sql =
		 * "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,"
		 * +
		 * "t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100  FROM tbl_txn_route_inf_temp t"
		 * + " left join tbl_city_code c on trim(c.city_code)=trim(t.area_no) "
		 * +
		 * " left join TBL_MCHT_BASE_INF m on trim(m.mcht_no)=trim(t.merch_id) where 1=1"
		 * + whereSql.toString() + " Union " +
		 * "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,d.Value as MERCH_ID,"
		 * +
		 * "t.DEST_INST_ID,t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE ,t.MAX_AMT/100,t.MIN_AMT/100"
		 * + " FROM tbl_txn_route_inf_temp t,tbl_city_code c,cst_sys_param d " +
		 * " where trim(c.city_code)=trim(t.area_no) And trim(t.merch_id)=d.Key "
		 * + " and d.owner='ALLMCHNT' and d.type='00' And d.Key='*' " +
		 * whereSql.toString() + " )";
		 */
		String sql = "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,"
				+ "t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100,t.MCHT_MCC  FROM tbl_txn_route_inf_temp t"
				+ " left join tbl_city_code c on trim(c.city_code)=trim(t.area_no) "
				+ " left join TBL_MCHT_BASE_INF m on trim(m.mcht_no)=trim(t.merch_id) where 1=1"
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		if (isNotEmpty(request.getParameter("orderOption"))) {
			if ("binasc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.CARD_BIN asc ";
				sql += " order by CARD_BIN asc ";
			}
			if ("bindesc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.CARD_BIN desc ";
				sql += " order by CARD_BIN desc ";
			}
			if ("mchtasc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.MERCH_ID asc ";
				sql += " order by MERCH_ID asc ";
			}
			if ("mchtdesc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.MERCH_ID desc ";
				sql += " order by MERCH_ID desc ";
			}
			if ("timeasc".equals(request.getParameter("orderOption"))) {
				sql += " order by UPDATE_TIME asc ";
			}
			if ("timedesc".equals(request.getParameter("orderOption"))) {
				sql += " order by UPDATE_TIME desc ";
			}
		}

		// String countSql =
		// "SELECT COUNT(*) FROM tbl_txn_route_inf_temp t where 1=1 "+
		// whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		// dataList = getBankName(dataList, 0);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 路由信息查询_查询界面
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRoutInfoQuery(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("cardBin"))) {
			whereSql.append(" and t.CARD_BIN = '"
					+ request.getParameter("cardBin") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and t.TXN_NUM = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and t.CHANNEL = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("areaNo"))/*
													 * &&!"*".equals(request.
													 * getParameter("areaNo"))
													 */) {
			whereSql.append(" and t.AREA_NO = '"
					+ request.getParameter("areaNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntId"))/*
														 * &&!"*".equals(request.
														 * getParameter
														 * ("mchntId"))
														 */) {
			whereSql.append(" and t.MERCH_ID = '"
					+ request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("reserved"))) {
			whereSql.append(" and t.RESERVED = '"
					+ request.getParameter("reserved") + "' ");
		}
		if (isNotEmpty(request.getParameter("destInstId"))) {
			whereSql.append(" and trim(t.DEST_INST_ID) = '"
					+ request.getParameter("destInstId").split("-")[0] + "' ");
		}
		if (isNotEmpty(request.getParameter("status"))) {
			whereSql.append(" and trim(t.SA_STATE) = '"
					+ request.getParameter("status") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}

		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and t.CRE_TIME <= '"
					+ request.getParameter("endDate") + "235959' ");
		}
		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		// String sql =
		// "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,"
		// +
		// "t.DEST_INST_ID,t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100 "
		// +
		// " FROM tbl_txn_route_inf_temp t,tbl_city_code c,TBL_MCHT_BASE_INF m "
		// +
		// " where trim(c.city_code)=trim(t.area_no) And m.mcht_no=t.merch_id "
		// + whereSql.toString()
		/*
		 * String sql =
		 * "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,"
		 * +
		 * "t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100  FROM tbl_txn_route_inf_temp t"
		 * + " left join tbl_city_code c on trim(c.city_code)=trim(t.area_no) "
		 * +
		 * " left join TBL_MCHT_BASE_INF m on trim(m.mcht_no)=trim(t.merch_id) where 1=1 "
		 * + whereSql.toString() + " Union " +
		 * "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,d.Value as MERCH_ID,"
		 * +
		 * "t.DEST_INST_ID,t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100 "
		 * + " FROM tbl_txn_route_inf_temp t,tbl_city_code c,cst_sys_param d " +
		 * " where trim(c.city_code)=trim(t.area_no) And trim(t.merch_id)=d.Key "
		 * + " and d.owner='ALLMCHNT' and d.type='00' And d.Key='*' " +
		 * whereSql.toString() + " )";
		 */
		String sql = "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,"
				+ "t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100,t.MCHT_MCC  FROM tbl_txn_route_inf_temp t"
				+ " left join tbl_city_code c on trim(c.city_code)=trim(t.area_no) "
				+ " left join TBL_MCHT_BASE_INF m on trim(m.mcht_no)=trim(t.merch_id) where 1=1 "
				+ whereSql.toString();

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		if (isNotEmpty(request.getParameter("orderOption"))) {
			if ("binasc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.CARD_BIN asc ";
				sql += " order by CARD_BIN asc ";
			}
			if ("bindesc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.CARD_BIN desc ";
				sql += " order by CARD_BIN desc ";
			}
			if ("mchtasc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.MERCH_ID asc ";
				sql += " order by MERCH_ID asc ";
			}
			if ("mchtdesc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.MERCH_ID desc ";
				sql += " order by MERCH_ID desc ";
			}
			if ("timeasc".equals(request.getParameter("orderOption"))) {
				sql += " order by UPDATE_TIME asc ";
			}
			if ("timedesc".equals(request.getParameter("orderOption"))) {
				sql += " order by UPDATE_TIME desc ";
			}
		}

		// String countSql =
		// "SELECT COUNT(*) FROM tbl_txn_route_inf_temp t where 1=1 "+
		// whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		// dataList = getBankName(dataList, 0);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的路由信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRoutInfoToCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("cardBin"))) {
			whereSql.append(" and t.CARD_BIN = '"
					+ request.getParameter("cardBin") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and t.BUSS_TYPE = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and t.TXN_NUM = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and t.CHANNEL = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("areaNo"))/*
													 * &&!"*".equals(request.
													 * getParameter("areaNo"))
													 */) {
			whereSql.append(" and t.AREA_NO = '"
					+ request.getParameter("areaNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntId"))/*
														 * &&!"*".equals(request.
														 * getParameter
														 * ("mchntId"))
														 */) {
			whereSql.append(" and t.MERCH_ID = '"
					+ request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("reserved"))) {
			whereSql.append(" and t.RESERVED = '"
					+ request.getParameter("reserved") + "' ");
		}
		if (isNotEmpty(request.getParameter("destInstId"))) {
			whereSql.append(" and trim(t.DEST_INST_ID) = '"
					+ request.getParameter("destInstId").split("-")[0] + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.UPDATE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}

		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and t.UPDATE_TIME <= '"
					+ request.getParameter("endDate") + "235959' ");
		}

		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		// String sql =
		// "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,"
		// +
		// "t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100 "
		// +
		// " FROM tbl_txn_route_inf_temp t,tbl_city_code c,TBL_MCHT_BASE_INF m "
		// +
		// " where trim(c.city_code)=trim(t.area_no) And m.mcht_no=t.merch_id and t.SA_STATE in('0','3','4') "
		// + whereSql.toString()
		/*
		 * String sql =
		 * "select * from (SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,"
		 * +
		 * "t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100  FROM tbl_txn_route_inf_temp t  "
		 * + " left join tbl_city_code c on trim(c.city_code)=trim(t.area_no) "
		 * +
		 * " left join TBL_MCHT_BASE_INF m on trim(m.mcht_no)=trim(t.merch_id) where t.SA_STATE in('0','3','4') "
		 * + whereSql.toString() + " Union " +
		 * "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,d.Value as MERCH_ID,"
		 * +
		 * "t.DEST_INST_ID,t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100"
		 * + " FROM tbl_txn_route_inf_temp t,tbl_city_code c,cst_sys_param d " +
		 * " where trim(c.city_code)=trim(t.area_no) And trim(t.merch_id)=d.Key and t.SA_STATE in('0','3','4') "
		 * + " and d.owner='ALLMCHNT' and d.type='00' And d.Key='*' " +
		 * whereSql.toString() + " )";
		 */
		String sql = "SELECT t.CARD_BIN as CARD_BIN,t.BUSS_TYPE,t.TXN_NUM,t.CHANNEL,t.AREA_NO||'-'||c.CITY_DES,t.MERCH_ID||'-'||m.MCHT_NM as MERCH_ID,t.DEST_INST_ID,"
				+ "t.RESERVED,t.SA_STATE,t.CRE_TIME,t.CRE_OPR_ID,t.CREATOR_ID,t.UPDATE_TIME,t.CHECK_TIME,t.CHECK_ID,t.CARD_TYPE,t.MAX_AMT/100,t.MIN_AMT/100,t.MCHT_MCC  FROM tbl_txn_route_inf_temp t  "
				+ " left join tbl_city_code c on trim(c.city_code)=trim(t.area_no) "
				+ " left join TBL_MCHT_BASE_INF m on trim(m.mcht_no)=trim(t.merch_id) where t.SA_STATE in('0','3','4') "
				+ whereSql.toString();

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		if (isNotEmpty(request.getParameter("orderOption"))) {
			if ("binasc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.CARD_BIN asc ";
				sql += " order by CARD_BIN asc ";
			}
			if ("bindesc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.CARD_BIN desc ";
				sql += " order by CARD_BIN desc ";
			}
			if ("mchtasc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.MERCH_ID asc ";
				sql += " order by MERCH_ID asc ";
			}
			if ("mchtdesc".equals(request.getParameter("orderOption").trim())) {
				// sql += " order by t.MERCH_ID desc ";
				sql += " order by MERCH_ID desc ";
			}
			if ("timeasc".equals(request.getParameter("orderOption"))) {
				sql += " order by UPDATE_TIME asc ";
			}
			if ("timedesc".equals(request.getParameter("orderOption"))) {
				sql += " order by UPDATE_TIME desc ";
			}
		}

		// String countSql =
		// "SELECT COUNT(*) FROM tbl_txn_route_inf_temp t where t.SA_STATE in('0','3','4') "+
		// whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		// dataList = getBankName(dataList, 0);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 路由切换信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRouteChgInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT t.DEST_INST_ID,t.DFT_DEST_ID,t.CHG_FLAG,t.CHG_DEST_ID,t.CREATETIME,t.CREATEOPRID,"
				+ "t.MODITIME,t.MODIOPRID FROM tbl_route_chg_inf t ";
		String countSql = "SELECT COUNT(t.DEST_INST_ID) FROM tbl_route_chg_inf t ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户交易黑名单查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblRiskMchtTranCtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and t.MCHT_NO like '%"
					+ request.getParameter("mchtNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and t.CHANNEL_old = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and t.BUSS_TYPE_old = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and t.TXN_NUM_old = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and t.SA_STATE= '"
					+ request.getParameter("saState") + "' ");
		}
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT ID,MCHT_NO,CHANNEL_old,BUSS_TYPE_old,TXN_NUM_old,SA_STATE,OPR_ID,UPDATETIME,")
				.append(" MCC,RISL_LVL,LICENCE_NO,MCHT_NM,apply_date,bank_no,TERM_COUNT,reserved1 FROM ")
				.append("(SELECT t.ID,t.MCHT_NO,t.CHANNEL_old,t.BUSS_TYPE_old,t.TXN_NUM_old,t.SA_STATE,t.OPR_ID,t.UPDATETIME,")
				.append("m.MCC,m.RISL_LVL,m.LICENCE_NO,m.MCHT_NM,m.apply_date,m.bank_no,t.reserved1 ")
				.append(" FROM TBL_MCHT_BASE_INF m,TBL_RISK_MCHT_TRAN_CTL t ")
				.append(" where m.MCHT_No=t.MCHT_NO ")
				.append(whereSql)
				.append(") A ")
				.append("left outer join ")
				.append("(select MCHT_CD,count(term_id) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B ")
				.append("ON (A.MCHT_NO = B.MCHT_CD) ORDER BY A.SA_STATE");

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户白名单信息
	 * 
	 * @author shiyiwen
	 * @return 2014-08-06
	 */

	public static Object[] getWhiteList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		String mchtNo_query = request.getParameter("mchtNo_query");
		if (isNotEmpty(mchtNo_query)) {
			sqlsel.append(" and MCHT_NO like '%").append(mchtNo_query)
					.append("%'");
		}

		sql.append(
				"select uuid,mcht_no,begin_dt,validity,ins_opr,ins_dt,upd_opr,upd_dt,state,app_remark,add_type from tbl_white_list_tmp where 1=1 ")
				.append(sqlsel).append("order by state,mcht_no");

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核商户白名单信息
	 * 
	 * @author shiyiwen
	 * @return 2014-08-06
	 */

	public static Object[] getToCheckWhiteList(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		/*
		 * String mchtNo_query = request.getParameter("mchtNo_query"); if
		 * (isNotEmpty(mchtNo_query)) {
		 * sqlsel.append(" and MCHT_NO like '%").append(mchtNo_query)
		 * .append("%'"); }
		 */
		sql.append(
				"select uuid,mcht_no,begin_dt,validity,ins_opr,ins_dt,upd_opr,upd_dt,state,app_remark,add_type from tbl_white_list_tmp where STATE in (0,3,4)")
				.append(sqlsel).append("order by ins_dt desc");

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询代理商信息
	 * 
	 * @author shiyiwen
	 * @return 2014-08-07
	 */

	public static Object[] getAgent(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		String agentNm_query = request.getParameter("agentNm_query");
		String applyDate_query = request.getParameter("applyDate_query");
		String district_query = request.getParameter("district_query");
		String busiPerson_query = request.getParameter("busiPerson_query");

		if (isNotEmpty(agentNm_query)) {
			sqlsel.append(" and AGENT_NM like '%").append(agentNm_query)
					.append("%'");
		}
		if (isNotEmpty(applyDate_query)) {
			sqlsel.append(" and APPLY_DATE like '%").append(applyDate_query)
					.append("%'");
		}
		if (isNotEmpty(district_query)) {
			sqlsel.append(" and DISTRICT like '%").append(district_query)
					.append("%'");
		}
		if (isNotEmpty(busiPerson_query)) {
			sqlsel.append(" and BUSI_PERSON like '%").append(busiPerson_query)
					.append("%'");
		}

		sql.append(
				"SELECT AGENT_NO,AGENT_NM,APPLY_DATE,DISTRICT,BUSI_PERSON,AGENT_PROVINCE,AGENT_CITY,ZIP_CODE,EMP_NUM,MANAGE_AREA,MANAGE_OWNER,AGENT_WEB_NM,AGENT_WEB_ADD,ICP_RECORD_NO,ICP_COMP_NM,MANAGER_NM,MANAGER_IDENT_NO,LICENCE_NO,ORGANIZ_NO,ADDRESS,AGENT_MANAGER_NM,AGENT_MANAGER_TEL,AGENT_CONN_NM,AGENT_CONN_TEL,AGENT_TEL,CHANNEL_TYPE,CONTRA_END_DT  from TBL_AGENT_INFO where 1=1")
				.append(sqlsel);

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询代理商分润信息
	 * 
	 * @author shiyiwen
	 * @return 2014-08-11
	 */

	public static Object[] getAgentDivide(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		String agentNo_query = request.getParameter("agentNo_query");
		String divideType_query = request.getParameter("divideType_query");
		String state_query = request.getParameter("state_query");

		if (isNotEmpty(agentNo_query)) {
			sqlsel.append(" and AGENT_NO like '%").append(agentNo_query)
					.append("%'");
		}
		if (isNotEmpty(divideType_query)) {
			sqlsel.append(" and DIVIDE_TYPE like '%").append(divideType_query)
					.append("%'");
		}
		if (isNotEmpty(state_query)) {
			sqlsel.append(" and state = '").append(state_query).append("'");
		}

		sql.append(
				"SELECT uuid,AGENT_NO,DIVIDE_TYPE,DISC_CD,PROFIT,MIN_VALUE/10000,MAX_VALUE/10000,STATE from TBL_AGENT_DIVIDE_TMP where 1=1 ")
				.append(sqlsel);
		sql.append(" order by AGENT_NO,state,MIN_VALUE ");

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT2);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询代理商分润信息(待审核)
	 * 
	 * @author shiyiwen
	 * @return 2014-08-11
	 */

	public static Object[] getAgentDivideToShenHe(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();
		String state_query = request.getParameter("state_query");

		String agentNo_query = request.getParameter("agentNo_query");
		String divideType_query = request.getParameter("divideType_query");

		if (isNotEmpty(agentNo_query)) {
			sqlsel.append(" and AGENT_NO like '%").append(agentNo_query)
					.append("%'");
		}
		if (isNotEmpty(divideType_query)) {
			sqlsel.append(" and DIVIDE_TYPE like '%").append(divideType_query)
					.append("%'");
		}
		if (isNotEmpty(state_query)) {
			sqlsel.append(" and state = '").append(state_query).append("'");
		}
		sql.append(
				"SELECT uuid,AGENT_NO,DIVIDE_TYPE,DISC_CD,PROFIT,MIN_VALUE/10000,MAX_VALUE/10000,STATE,CRT_PER,CRT_DATE,UP_PER,UP_DATE from TBL_AGENT_DIVIDE_TMP where state in ('0','3','4') ")
				.append(sqlsel);
		sql.append(" order by AGENT_NO,state,MIN_VALUE ");

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT2);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 冻结商户信息
	 * 
	 * @author shiyiwen
	 * @return 2014-08-18
	 */

	public static Object[] getFrozen(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		String frozenDate_query = request.getParameter("frozenDate_query");
		String mchtNo_query = request.getParameter("mchtNo_query");
		String termId_query = request.getParameter("termId_query");
		String frozenRoute_query = request.getParameter("frozenRoute_query");

		if (isNotEmpty(frozenDate_query)) {
			sqlsel.append(" and FROZEN_DATE like '%").append(frozenDate_query)
					.append("%'");
		}
		if (isNotEmpty(mchtNo_query)) {
			sqlsel.append(" and MCHT_NO like '%").append(mchtNo_query)
					.append("%'");
		}
		if (isNotEmpty(termId_query)) {
			sqlsel.append(" and TERM_ID like '%").append(termId_query)
					.append("%'");
		}
		if (isNotEmpty(frozenRoute_query)) {
			sqlsel.append(" and FROZEN_ROUTE = '").append(frozenRoute_query)
					.append("'");
		}

		sql.append(
				"SELECT ID,FROZEN_DATE,MCHT_NO,TERM_ID,FROZEN_ACCOUNT,FROZEN_ACCOUNT_FINISH,FROZEN_ACCOUNT_NO_FINISH,FROZEN_ROUTE,FROZEN_REASON,FROZEN_FINISH_FLAG,OPR_FLAG,STATS,SALESSTATS,DEST_ID from TBL_FROZEN_ACC_INF where 1=1")
				.append(sqlsel);

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 冻结商户信息S
	 * 
	 * @author shiyiwen
	 * @return 2014-08-18
	 */

	public static Object[] getFrozenS(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		String frozenDate_query = request.getParameter("frozenDate_query");
		String mchtNo_query = request.getParameter("mchtNo_query");
		String termId_query = request.getParameter("termId_query");
		String frozenRoute_query = request.getParameter("frozenRoute_query");

		if (isNotEmpty(frozenDate_query)) {
			sqlsel.append(" and FROZEN_DATE like '%").append(frozenDate_query)
					.append("%'");
		}
		if (isNotEmpty(mchtNo_query)) {
			sqlsel.append(" and MCHT_NO like '%").append(mchtNo_query)
					.append("%'");
		}
		if (isNotEmpty(termId_query)) {
			sqlsel.append(" and TERM_ID like '%").append(termId_query)
					.append("%'");
		}
		if (isNotEmpty(frozenRoute_query)) {
			sqlsel.append(" and FROZEN_ROUTE = '").append(frozenRoute_query)
					.append("'");
		}

		sql.append(
				"SELECT ID,FROZEN_DATE,MCHT_NO,TERM_ID,FROZEN_ACCOUNT,FROZEN_ACCOUNT_FINISH,FROZEN_ACCOUNT_NO_FINISH,FROZEN_ROUTE,FROZEN_REASON,FROZEN_FINISH_FLAG,OPR_FLAG,STATS,DEST_ID from TBL_FROZEN_ACC_INF where 1=1 AND STATS != '2' AND STATS != '5' ")
				.append(sqlsel);

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 冻结商户信息--临时
	 * 
	 * @author shiyiwen
	 * @return 2014-08-18
	 */

	public static Object[] getFrozenTmp(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();

		String frozenDate_query = request.getParameter("frozenDate_query");
		String mchtNo_query = request.getParameter("mchtNo_query");
		String termId_query = request.getParameter("termId_query");
		String frozenRoute_query = request.getParameter("frozenRoute_query");

		if (isNotEmpty(frozenDate_query)) {
			sqlsel.append(" and FROZEN_DATE like '%").append(frozenDate_query)
					.append("%'");
		}
		if (isNotEmpty(mchtNo_query)) {
			sqlsel.append(" and MCHT_NO like '%").append(mchtNo_query)
					.append("%'");
		}
		if (isNotEmpty(termId_query)) {
			sqlsel.append(" and TERM_ID like '%").append(termId_query)
					.append("%'");
		}
		if (isNotEmpty(frozenRoute_query)) {
			sqlsel.append(" and FROZEN_ROUTE = '").append(frozenRoute_query)
					.append("'");
		}

		sql.append(
				"SELECT ID,FROZEN_DATE,MCHT_NO,TERM_ID,FROZEN_ACCOUNT,FROZEN_ACCOUNT_FINISH,FROZEN_ACCOUNT_NO_FINISH,FROZEN_ROUTE,FROZEN_REASON,FROZEN_FINISH_FLAG,OPR_FLAG,STATS from TBL_FROZEN_ACC_INF_TMP where 1=1")
				.append(sqlsel);

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的商户交易黑名单查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckTblRiskMchtTranCtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and t.MCHT_NO like '%"
					+ request.getParameter("mchtNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and t.CHANNEL_old = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and t.BUSS_TYPE_old = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and t.TXN_NUM_old = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and t.SA_STATE= '"
					+ request.getParameter("saState") + "' ");
		}

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT ID,MCHT_NO,CHANNEL_old,BUSS_TYPE_old,TXN_NUM_old,SA_STATE,OPR_ID,UPDATETIME,")
				.append("MCC,RISL_LVL,LICENCE_NO,MCHT_NM,apply_date,bank_no,TERM_COUNT,reserved1 FROM ")
				.append("(SELECT t.ID,t.MCHT_NO,t.CHANNEL_old,t.BUSS_TYPE_old,t.TXN_NUM_old,t.SA_STATE,t.OPR_ID,t.UPDATETIME,")
				.append("m.MCC,m.RISL_LVL,m.LICENCE_NO,m.MCHT_NM,m.apply_date,m.bank_no,t.reserved1 ")
				.append(" FROM TBL_MCHT_BASE_INF m,TBL_RISK_MCHT_TRAN_CTL t ")
				.append(" where t.SA_STATE IN (0,3,4) and m.MCHT_No=t.MCHT_NO ")
				.append(whereSql)
				.append(") A ")
				.append("left outer join ")
				.append("(select MCHT_CD,count(term_id) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B ")
				.append("ON (A.MCHT_NO = B.MCHT_CD) ORDER BY A.SA_STATE");

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户交易权限查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblRiskMchtTranLimit(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and t.MCHT_NO like '%"
					+ request.getParameter("mchtNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and trim(t.CHANNEL) = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and trim(t.TXN_NUM) = '"
					+ request.getParameter("txnNum") + "' ");
		}
		/*
		 * if (isNotEmpty(request.getParameter("limit"))) {
		 * whereSql.append(" and t.LIMIT = '" + request.getParameter("limit")+
		 * "' "); }
		 */
		if (isNotEmpty(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			whereSql.append(" and trim(t.CARD_TYPE) = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and t.SA_STATE= '"
					+ request.getParameter("saState") + "' ");
		}
		String sql = "SELECT t.ID,t.MCHT_NO,t.CHANNEL_OLD,t.BUSS_TYPE_OLD,t.TXN_NUM_OLD,t.CARD_TYPE_OLD,t.LIMIT,t.SA_STATE,t.CRE_ID,t.OPR_ID,t.CRETIME,t.UPDATETIME,m.mcht_nm FROM "
				+ "tbl_risk_mcht_tran_limit t,tbl_mcht_base_inf m WHERE m.mcht_no=t.MCHT_NO "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		sql += "Order By sa_state";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的商户交易权限查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckTblRiskMchtTranLimit(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and t.MCHT_NO like '%"
					+ request.getParameter("mchtNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and trim(t.CHANNEL) = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and trim(t.TXN_NUM) = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			whereSql.append(" and trim(t.CARD_TYPE) = '"
					+ request.getParameter("cardType") + "' ");
		}
		/*
		 * if (isNotEmpty(request.getParameter("limit"))) {
		 * whereSql.append(" and t.LIMIT = '" + request.getParameter("limit")+
		 * "' "); }
		 */
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and t.SA_STATE= '"
					+ request.getParameter("saState") + "' ");
		}
		String sql = "SELECT t.ID,t.MCHT_NO,t.CHANNEL_OLD,t.BUSS_TYPE_OLD,t.TXN_NUM_OLD,t.CARD_TYPE_OLD,t.LIMIT,t.SA_STATE,t.CRE_ID,t.OPR_ID,t.CRETIME,t.UPDATETIME,m.mcht_nm  FROM "
				+ "tbl_risk_mcht_tran_limit t,tbl_mcht_base_inf m WHERE t.SA_STATE IN (0,3,4) and m.mcht_no=t.MCHT_NO "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		sql += " order by t.SA_STATE ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端交易权限查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblRiskTermTranLimit(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and t2.mcht_CD ='")
					.append(request.getParameter("mchtNo")).append("'");
		}
		if (isNotEmpty(request.getParameter("termID"))) {
			whereSql.append(" and t.TERM_ID like '%"
					+ request.getParameter("termID") + "%' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and trim(t.CHANNEL) = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and trim(t.TXN_NUM) = '"
					+ request.getParameter("txnNum") + "' ");
		}
		/*
		 * if (isNotEmpty(request.getParameter("limit"))) {
		 * whereSql.append(" and t.LIMIT = '" + request.getParameter("limit")+
		 * "' "); }
		 */
		if (isNotEmpty(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			whereSql.append(" and trim(t.CARD_TYPE) = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and t.SA_STATE= '"
					+ request.getParameter("saState") + "' ");
		}
		String sql = "SELECT t.ID,t.TERM_ID,t.CHANNEL_OLD,t.BUSS_TYPE_OLD,t.TXN_NUM_OLD,t.CARD_TYPE_OLD,t.LIMIT_OLD,t.SA_STATE,t.CRE_ID,t.OPR_ID,t.CRE_TIME,t.UPDATETIME,t2.mcht_cd,m.mcht_nm FROM "
				+ "tbl_risk_term_tran_limit t,tbl_mcht_base_inf m,tbl_term_inf t2  WHERE m.mcht_no=t2.mcht_cd  and t.term_id=t2.term_id "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
		sql += " order by t.SA_STATE ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的终端交易权限查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckTblRiskTermTranLimit(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and t2.mcht_CD ='")
					.append(request.getParameter("mchtNo")).append("'");
		}
		if (isNotEmpty(request.getParameter("termID"))) {
			whereSql.append(" and  t.TERM_ID like '%"
					+ request.getParameter("termID") + "%' ");
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql.append(" and  trim(t.CHANNEL) = '"
					+ request.getParameter("channel") + "' ");
		}
		if (isNotEmpty(request.getParameter("bussType"))) {
			whereSql.append(" and trim(t.BUSS_TYPE) = '"
					+ request.getParameter("bussType") + "' ");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {
			whereSql.append(" and trim(t.TXN_NUM) = '"
					+ request.getParameter("txnNum") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			whereSql.append(" and trim(t.CARD_TYPE) = '"
					+ request.getParameter("cardType") + "' ");
		}
		/*
		 * if (isNotEmpty(request.getParameter("limit"))) {
		 * whereSql.append(" and t.LIMIT = '" + request.getParameter("limit")+
		 * "' "); }
		 */
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and t.SA_STATE= '"
					+ request.getParameter("saState") + "' ");
		}
		String sql = "SELECT t.ID,t.TERM_ID,trim(t.CHANNEL_OLD),trim(t.BUSS_TYPE_OLD),t.TXN_NUM_OLD,t.CARD_TYPE_OLD,t.LIMIT_OLD,t.SA_STATE,t.CRE_ID,t.OPR_ID,t.CRE_TIME,t.UPDATETIME,t2.mcht_cd,m.mcht_nm FROM "
				+ "tbl_risk_term_tran_limit t ,tbl_mcht_base_inf m,tbl_term_inf t2  WHERE t.SA_STATE IN (0,3,4) and m.mcht_no=t2.mcht_cd  and t.term_id=t2.term_id "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户事后风险预警系数查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCstAfterRuleInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("ruleId"))) {
			whereSql.append(" and RULE_ID = '" + request.getParameter("ruleId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("instId"))) {
			whereSql.append(" and INST_ID = '" + request.getParameter("instId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql.append(" and MCC like '%" + request.getParameter("mcc")
					+ "%' ");
		}
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and SA_STATE = '"
					+ request.getParameter("saState") + "' ");
		}
		String sql = "SELECT RULE_ID,INST_ID,MCC,DAYS_old,WARNLVT_old,WARNCOUNT_old,WARNAMT_old,SA_STATE,CREATETIME,UPDATETIME,CREATOR,UPDATEOPR FROM "
				+ "cst_after_rule_inf WHERE 1=1 " + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM cst_after_rule_inf WHERE 1=1 "
				+ whereSql.toString();
		sql += " order by RULE_ID,SA_STATE ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的商户事后风险预警系数查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckCstAfterRuleInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("ruleId"))) {
			whereSql.append(" and RULE_ID = '" + request.getParameter("ruleId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("instId"))) {
			whereSql.append(" and INST_ID = '" + request.getParameter("instId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql.append(" and MCC like '%" + request.getParameter("mcc")
					+ "%' ");
		}
		if (isNotEmpty(request.getParameter("saState"))) {
			whereSql.append(" and SA_STATE = '"
					+ request.getParameter("saState") + "' ");
		}
		String sql = "SELECT RULE_ID,INST_ID,MCC,DAYS_old,WARNLVT_old,WARNCOUNT_old,WARNAMT_old,SA_STATE,CREATETIME,UPDATETIME,CREATOR,UPDATEOPR FROM "
				+ "cst_after_rule_inf WHERE SA_STATE in(0,3,4)  "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM cst_after_rule_inf WHERE SA_STATE in(0,3,4) "
				+ whereSql.toString();
		sql += " order by RULE_ID,SA_STATE ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询中石油卡黑名单信息：正常和已删除
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCnpcCardRiskInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select CUSTOMER_PROFILE, CARD_LEVEL, CHECK_CODE, SA_STATE, INIT_OPR_ID, INIT_TIME, VERIFIER_OPR_ID from TBL_CNPC_BLACK_CARD_INFO_TMP where 1=1 ";
		String countSql = "SELECT COUNT(*) FROM TBL_CNPC_BLACK_CARD_INFO_TMP where 1=1 ";

		String whereSql = "";
		String saState = null;
		if (isNotEmpty(request.getParameter("saState"))) {
			saState = request.getParameter("saState");
			whereSql += " and SA_STATE = '" + saState + "' ";
		}
		// if (isNotEmpty(request.getParameter("srAction"))) {
		// date = request.getParameter("srAction");
		// whereSql += " and SA_ACTION = '" + date + "'";
		// }
		sql += whereSql;
		sql += " order by INIT_TIME DESC ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[1] = CommonFunction.transFenToYuan(data[1].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询卡黑名单信息：正常和已删除
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-24下午04:47:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardRiskInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "SELECT SA_CARD_NO,SA_LIMIT_AMT,SA_ACTION,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,"
				+ "SA_MODI_OPR_ID,SA_MODI_TIME,SA_STATE,SA_LIMIT_AMT_OLD,SA_ACTION_OLD,RISK_ROLE,REMARK_ADD FROM TBL_CTL_CARD_INF where 1=1 ";
		/*
		 * String sql =
		 * "SELECT SA_CARD_NO,SA_LIMIT_AMT,SA_ACTION,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,"
		 * +
		 * "SA_MODI_OPR_ID,SA_MODI_TIME,SA_STATE,SA_LIMIT_AMT_OLD,SA_ACTION_OLD FROM TBL_CTL_CARD_INF where 1=1 "
		 * ;
		 */
		String countSql = "SELECT COUNT(*) FROM TBL_CTL_CARD_INF where 1=1 ";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("srCardNo"))) {
			date = request.getParameter("srCardNo");
			whereSql += " and SA_CARD_NO like '%" + date + "%'";
		}
		// if (isNotEmpty(request.getParameter("srAction"))) {
		// date = request.getParameter("srAction");
		// whereSql += " and SA_ACTION = '" + date + "'";
		// }
		sql += whereSql;
		sql += " order by SA_STATE,SA_CARD_NO ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[1] = CommonFunction.transFenToYuan(data[1].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 卡Bin地区路由配置
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getCardbinAreaRouteInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select card_bin, area_no, inst_code, state, crt_per, crt_date, upt_per, upt_date from tbl_cardbin_area_route_inf_tmp where 1=1 ";
		String countSql = "select count(*) from tbl_cardbin_area_route_inf_tmp where 1=1 ";
		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("card_bin"))) {
			date = request.getParameter("card_bin");
			whereSql += " and card_bin like '%" + date + "%'";
		}
		sql += whereSql;
		sql += " order by crt_date desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询待审核的卡黑名单，根据状态来排序
	 * 
	 * @author crystal
	 * @return 2012-04-18
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckCardRiskInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String sql = "SELECT SA_CARD_NO,SA_LIMIT_AMT,SA_ACTION,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,SA_MODI_ZONE_NO,SA_MODI_OPR_ID"
				+ ",SA_MODI_TIME,SA_STATE,SA_LIMIT_AMT_OLD,SA_ACTION_OLD,REMARK_ADD,RISK_ROLE FROM TBL_CTL_CARD_INF WHERE SA_STATE in(0,4) ";
		String countSql = "SELECT COUNT(*) FROM TBL_CTL_CARD_INF WHERE SA_STATE in(0,4) ";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("srCardNo"))) {
			date = request.getParameter("srCardNo");
			whereSql += " and SA_CARD_NO like '%" + date + "%'";
		}
		// if (isNotEmpty(request.getParameter("srAction"))) {
		// date = request.getParameter("srAction");
		// whereSql += " and SA_ACTION = '" + date + "'";
		// }
		sql += whereSql;
		sql += " order by SA_STATE,SA_CARD_NO ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[1] = CommonFunction.transFenToYuan(data[1].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户黑名单信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2012-05-09
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntRiskInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		StringBuffer sqlsel = new StringBuffer();
		if (isNotEmpty(request.getParameter("srMerNo"))) {
			sqlsel.append(" and d.SA_MER_NO like '")
					.append(request.getParameter("srMerNo")).append("'");
		}
		if (isNotEmpty(request.getParameter("saMerChName"))) {
			sqlsel.append(" and d.SA_MER_CH_NAME like '%")
					.append(request.getParameter("saMerChName")).append("%'");
		}

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT d.SA_LIMIT_AMT,d.SA_ACTION,d.SA_ZONE_NO,d.SA_ADMI_BRAN_NO,d.SA_CONN_OR,d.SA_CONN_TEL,d.SA_INIT_ZONE_NO,")
				.append("d.SA_INIT_OPR_ID,d.SA_INIT_TIME,d.SA_MODI_ZONE_NO,d.SA_MODI_OPR_ID,d.SA_MODI_TIME,d.SA_STATE,d.SA_LIMIT_AMT_OLD,")
				.append("d.SA_ACTION_OLD,d.SA_MER_CH_NAME,d.SA_MER_NO,d.DATE_PK,d.MANAGER_TEL,d.ADD_TYPE,d.BANK_LICENCE_NO,d.LICENCE_NO,d.IDENTITY_NO,d.SA_AREA FROM TBL_CTL_MCHT_INF d where 1=1 ")
				.append(sqlsel);
		/*
		 * sql.append("SELECT SA_LIMIT_AMT,SA_ACTION,SA_ZONE_NO,SA_ADMI_BRAN_NO,"
		 * ) .append(
		 * "SA_CONN_OR,SA_CONN_TEL,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,SA_MODI_ZONE_NO,SA_MODI_OPR_ID,"
		 * ) .append(
		 * "SA_MODI_TIME,SA_STATE,SA_LIMIT_AMT_OLD,SA_ACTION_OLD,MCC,RISL_LVL,LICENCE_NO,MCHT_NM,apply_date,bank_no,mcht_no,TERM_COUNT,IDENTITY_NO,BANK_LICENCE_NO "
		 * ) .append("FROM ") .append(
		 * "(SELECT d.SA_LIMIT_AMT,d.SA_ACTION,d.SA_ZONE_NO,d.SA_ADMI_BRAN_NO,d.SA_CONN_OR,d.SA_CONN_TEL,d.SA_INIT_ZONE_NO,"
		 * ) .append(
		 * "d.SA_INIT_OPR_ID,d.SA_INIT_TIME,d.SA_MODI_ZONE_NO,d.SA_MODI_OPR_ID,d.SA_MODI_TIME,d.SA_STATE,d.SA_LIMIT_AMT_OLD,"
		 * ) .append(
		 * "d.SA_ACTION_OLD,c.MCC,c.RISL_LVL,c.LICENCE_NO,c.MCHT_NM,c.apply_date,c.bank_no,c.mcht_no,c.IDENTITY_NO,c.BANK_LICENCE_NO FROM TBL_MCHT_BASE_INF c,TBL_CTL_MCHT_INF d where c.MCHT_NO=d.SA_MER_NO "
		 * ) .append(whereSql) .append(sqlsel.toString() + ") A ")
		 * .append("left outer join ") .append(
		 * "(select MCHT_CD,count(term_id) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
		 * ) .append("ON (A.MCHT_NO = B.MCHT_CD) ORDER BY A.SA_STATE");
		 */

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询待审核的商户黑名单信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckMchntRiskInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (isNotEmpty(request.getParameter("srMerNo"))) {
			whereSql.append(" and d.SA_MER_NO like '%")
					.append(request.getParameter("srMerNo")).append("%'");
		}
		if (isNotEmpty(request.getParameter("saMerChName"))) {
			whereSql.append(" and d.SA_MER_CH_NAME '%")
					.append(request.getParameter("saMerChName")).append("%'");
		}
		/*
		 * if (isNotEmpty(request.getParameter("srBrhNo"))) {
		 * whereSql.append(" and d.SA_MER_NO like '%")
		 * .append(request.getParameter("srBrhNo")).append("%'"); }
		 */
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT d.SA_LIMIT_AMT,d.SA_ACTION,d.SA_ZONE_NO,d.SA_ADMI_BRAN_NO,d.SA_CONN_OR,d.SA_CONN_TEL,d.SA_INIT_ZONE_NO,")
				.append("d.SA_INIT_OPR_ID,d.SA_INIT_TIME,d.SA_MODI_ZONE_NO,d.SA_MODI_OPR_ID,d.SA_MODI_TIME,d.SA_STATE,d.SA_LIMIT_AMT_OLD,")
				.append("d.SA_ACTION_OLD,d.SA_MER_CH_NAME,d.SA_MER_NO,d.DATE_PK,d.MANAGER_TEL,d.ADD_TYPE,d.BANK_LICENCE_NO,d.LICENCE_NO,d.IDENTITY_NO,d.APP_REMARK,d.SA_AREA FROM TBL_CTL_MCHT_INF d where 1=1 and d.SA_STATE in (0,3,4)")
				.append(whereSql);
		/*
		 * sql.append("SELECT SA_LIMIT_AMT,SA_ACTION,SA_ZONE_NO,SA_ADMI_BRAN_NO,"
		 * ) .append(
		 * "SA_CONN_OR,SA_CONN_TEL,SA_INIT_ZONE_NO,SA_INIT_OPR_ID,SA_INIT_TIME,SA_MODI_ZONE_NO,SA_MODI_OPR_ID,"
		 * ) .append(
		 * "SA_MODI_TIME,SA_STATE,SA_LIMIT_AMT_OLD,SA_ACTION_OLD,MCC,RISL_LVL,LICENCE_NO,MCHT_NM,apply_date,bank_no,mcht_no,TERM_COUNT,IDENTITY_NO,BANK_LICENCE_NO FROM "
		 * ) .append(
		 * "(SELECT d.SA_LIMIT_AMT,d.SA_ACTION,d.SA_ZONE_NO,d.SA_ADMI_BRAN_NO,d.SA_CONN_OR,d.SA_CONN_TEL,d.SA_INIT_ZONE_NO,"
		 * ) .append(
		 * "d.SA_INIT_OPR_ID,d.SA_INIT_TIME,d.SA_MODI_ZONE_NO,d.SA_MODI_OPR_ID,d.SA_MODI_TIME,d.SA_STATE,d.SA_LIMIT_AMT_OLD,"
		 * ) .append(
		 * "d.SA_ACTION_OLD,c.MCC,c.RISL_LVL,c.LICENCE_NO,c.MCHT_NM,c.apply_date,c.bank_no,c.mcht_no,c.IDENTITY_NO,c.BANK_LICENCE_NO FROM TBL_MCHT_BASE_INF c,TBL_CTL_MCHT_INF d "
		 * ) .append("where c.MCHT_NO=d.SA_MER_NO and d.SA_STATE in (0,3,4)")
		 * .append(whereSql) .append(") A ") .append("left outer join ")
		 * .append(
		 * "(select MCHT_CD,count(term_id) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
		 * ) .append("ON (A.MCHT_NO = B.MCHT_CD) ORDER BY A.SA_STATE");
		 */

		String countSql = "SELECT COUNT(*) FROM (" + sql.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 拒绝原因查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT TXN_TIME,BRH_ID,OPR_ID,REFUSE_TYPE,REFUSE_INFO,PARAM1,PARAM2,PARAM3,PARAM4,PARAM5,PARAM6,RESERVE1"
				+ " FROM tbl_risk_refuse WHERE FLAG='"
				+ request.getParameter("flag") + "'";
		String countSql = "SELECT COUNT(1) FROM tbl_risk_refuse WHERE FLAG='"
				+ request.getParameter("flag") + "'";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("refuseType"))) {
			date = request.getParameter("refuseType");
			whereSql += " and REFUSE_TYPE = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("mchnNoQ"))) {
			date = request.getParameter("mchnNoQ");
			whereSql += " and PARAM1 = '" + date + "'";
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql += " and TXN_TIME >= '"
					+ request.getParameter("startDate") + "000000'";
		}

		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql += " and TXN_TIME <= '" + request.getParameter("endDate")
					+ "000000'";
		}
		if (isNotEmpty(request.getParameter("idCardNo"))) {
			date = request.getParameter("idCardNo");
			whereSql += " and PARAM1 like '%" + date + "%'";
		}

		if (isNotEmpty(request.getParameter("saMerChName"))) {
			date = request.getParameter("saMerChName");
			whereSql += " and PARAM1 like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("mchtNo_query"))) {
			date = request.getParameter("mchtNo_query");
			whereSql += " and PARAM1 like '%" + date + "%'";
		}

		sql += whereSql;
		sql += " order by TXN_TIME desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		// 路由拒绝信息
		if (isNotEmpty(request.getParameter("flag"))
				&& request.getParameter("flag").equals("13")) {
			dataList = getBankName(dataList, 5);
		}
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询t_lst_entity实体表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getLstEntitys(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select id,cttp,ctcr,ctnm,ciid,cleg,clid,care,lstp,create_time,creator,update_time,updator,ckstatus from t_lst_entity_tmp where 1=1 ";
		String countSql = "SELECT COUNT(1) FROM t_lst_entity_tmp WHERE 1=1 ";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("ctnm"))) {
			date = request.getParameter("ctnm");
			whereSql += " and ctnm like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("care"))) {
			date = request.getParameter("care");
			whereSql += " and care like '%" + date + "%'";
		}
		/*
		 * if (!StringUtil.isNull(request.getParameter("startDate"))) { whereSql
		 * += " and TXN_TIME >= '" + request.getParameter("startDate") +
		 * "000000'"; } if (!StringUtil.isNull(request.getParameter("endDate")))
		 * { whereSql += " and TXN_TIME <= '" + request.getParameter("endDate")
		 * + "000000'"; }
		 */
		if (isNotEmpty(request.getParameter("ciid"))) {
			date = request.getParameter("ciid");
			whereSql += " and ciid like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("lstp"))) {
			date = request.getParameter("lstp");
			whereSql += " and lstp = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("ckstatus"))) {
			date = request.getParameter("ckstatus");
			whereSql += " and ckstatus in (" + date + ")";
		}
		sql += whereSql;
		sql += " order by create_time desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询t_lst_region实体表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getLstRegions(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select id,lskd,region_type,region_code,region_name,remark,status,create_time,creator,update_time,updator from t_lst_region where 1=1 ";
		String countSql = "SELECT COUNT(1) FROM t_lst_region WHERE 1=1 ";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("rgtp"))) {
			date = request.getParameter("rgtp");
			whereSql += " and region_type = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("rgnm"))) {
			date = request.getParameter("rgnm");
			whereSql += " and region_name like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("status"))) {
			date = request.getParameter("status");
			whereSql += " and status = '" + date + "'";
		}

		sql += whereSql;
		sql += " order by create_time desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询TBL_BLACKLIST_REGION实体表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getLstBlackRegions(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select BLACK_REGION_ID,BLACK_REGION_TYPE,BLACK_REGION_NO,BLACK_REGION_NAME,BLACK_REGION_HOME,UPDATETIME from TBL_BLACKLIST_REGION where 1=1 ";
		String countSql = "SELECT COUNT(1) FROM TBL_BLACKLIST_REGION WHERE 1=1 ";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("regionNo"))) {
			date = request.getParameter("regionNo");
			whereSql += " and BLACK_REGION_NO like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("regionName"))) {
			date = request.getParameter("regionName");
			whereSql += " and BLACK_REGION_NAME like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("regionHome"))) {
			date = request.getParameter("regionHome");
			whereSql += " and BLACK_REGION_HOME like '%" + date + "%'";
		}

		sql += whereSql;
		sql += " order by UPDATETIME desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询TBL_BLACKLIST_OBSERVE实体表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getListBlackObserve(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select BLACK_OBSERVE_ID,BLACK_OBSERVE_NAME,BLACK_OBSERVE_SEX,BLACK_OBSERVE_BIR,BLACK_OBSERVE_COUNTRY,BLACK_OBSERVE_NO,BLACK_OBSERVE_TYPE,BLACK_OBSERVE_HOME,BLACK_OBSERVE_ENTITY,BLACK_OBSERVE_ADDRESS,BLACK_OBSERVE_CON,BLACK_OBSERVE_UPDATETIME from TBL_BLACKLIST_OBSERVE where 1=1 ";
		String countSql = "SELECT COUNT(1) FROM TBL_BLACKLIST_OBSERVE WHERE 1=1 ";

		String whereSql = "";
		String date = null;
		// blackObserveName blackObserveCountry blackObserveNo blackObserveHome
		// blackObserveEntity
		if (isNotEmpty(request.getParameter("blackObserveName"))) {
			date = request.getParameter("blackObserveName");
			whereSql += " and BLACK_OBSERVE_NAME like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("blackObserveCountry"))) {
			date = request.getParameter("blackObserveCountry");
			whereSql += " and BLACK_OBSERVE_COUNTRY like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("blackObserveNo"))) {
			date = request.getParameter("blackObserveNo");
			whereSql += " and BLACK_OBSERVE_NO like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("blackObserveHome"))) {
			date = request.getParameter("blackObserveHome");
			whereSql += " and BLACK_OBSERVE_HOME like '%" + date + "%'";
		}
		if (isNotEmpty(request.getParameter("blackObserveEntity"))) {
			date = request.getParameter("blackObserveEntity");
			whereSql += " and BLACK_OBSERVE_ENTITY like '%" + date + "%'";
		}

		sql += whereSql;
		sql += " order by BLACK_OBSERVE_UPDATETIME desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 拒绝原因查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskRefuseInfo7(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT TXN_TIME,BRH_ID,OPR_ID,REFUSE_TYPE,REFUSE_INFO,c.KEY||' - '||c.VALUE,PARAM2,PARAM3,PARAM4,PARAM5,PARAM6"
				+ " FROM tbl_risk_refuse,CST_SYS_PARAM c WHERE FLAG='7' and c.OWNER='CHANNEL' and c.KEY=PARAM1 ";
		String countSql = "SELECT COUNT(*) FROM tbl_risk_refuse WHERE FLAG='7'";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("refuseType"))) {
			date = request.getParameter("refuseType");
			whereSql += " and REFUSE_TYPE = '" + date + "'";
		}

		sql += whereSql;
		sql += " order by TXN_TIME desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户交易限额操作记录查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskRefuseInfo4MchtFee(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT TXN_TIME,BRH_ID,OPR_ID,REFUSE_TYPE,REFUSE_INFO,PARAM1,PARAM2,PARAM3,PARAM4,PARAM5,PARAM6,RESERVE1"
				+ " FROM tbl_risk_refuse WHERE FLAG='"
				+ request.getParameter("flag") + "'";
		String countSql = "SELECT COUNT(1) FROM tbl_risk_refuse WHERE FLAG='"
				+ request.getParameter("flag") + "'";

		String whereSql = "";
		String date = null;
		/*
		 * if (isNotEmpty(request.getParameter("refuseType"))) { date =
		 * request.getParameter("refuseType"); whereSql +=
		 * " and REFUSE_TYPE = '" + date + "'"; }
		 */
		if (isNotEmpty(request.getParameter("mchnNoQ"))) {
			date = request.getParameter("mchnNoQ");
			whereSql += " and PARAM1 = '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("cardTypeQ"))) {
			date = request.getParameter("cardTypeQ");
			whereSql += " and PARAM2 = '" + date + "'";
		}
		/*
		 * if (!StringUtil.isNull(request.getParameter("startDate"))) { whereSql
		 * += " and TXN_TIME >= '" + request.getParameter("startDate") +
		 * "000000'"; }
		 */

		/*
		 * if (!StringUtil.isNull(request.getParameter("endDate"))) { whereSql
		 * += " and TXN_TIME <= '" + request.getParameter("endDate") +
		 * "000000'"; }
		 */
		/*
		 * if (isNotEmpty(request.getParameter("idCardNo"))) { date =
		 * request.getParameter("idCardNo"); whereSql += " and PARAM1 like '%" +
		 * date + "%'"; }
		 * 
		 * if (isNotEmpty(request.getParameter("saMerChName"))) { date =
		 * request.getParameter("saMerChName"); whereSql +=
		 * " and PARAM1 like '%" + date + "%'"; }
		 */

		sql += whereSql;
		sql += " order by TXN_TIME desc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		// 路由拒绝信息
		if (isNotEmpty(request.getParameter("flag"))
				&& request.getParameter("flag").equals("13")) {
			dataList = getBankName(dataList, 5);
		}
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 差错信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBthCupErrTxn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer("");
		StringBuffer whereSql2 = new StringBuffer("");
		StringBuffer whereSqlTxn = new StringBuffer("");
		StringBuffer whereSqlEle = new StringBuffer("");
		String date = null;

		if (isNotEmpty(request.getParameter("mchtId"))) {// 商户号
			whereSql.append(" and t.MCHT_ID = '"
					+ request.getParameter("mchtId") + "'");
			whereSql2.append(" and d.MCHT_CD = '"
					+ request.getParameter("mchtId") + "'");
			whereSqlTxn.append(" and a.card_accp_id='")
					.append(request.getParameter("mchtId")).append("'");
			whereSqlEle.append(" and e.MCHT_CD ='")
					.append(request.getParameter("mchtId")).append("'");
		}
		if (isNotEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and trim(t.TERM_ID) = '"
					+ request.getParameter("termId").trim() + "'");
			whereSql2.append(" and trim(d.TERM_ID) = '"
					+ request.getParameter("termId").trim() + "'");
			whereSqlTxn.append(" and trim(a.card_accp_term_id)='")
					.append(request.getParameter("termId").trim()).append("'");
			whereSqlEle.append(" and trim(e.TERM_ID) ='")
					.append(request.getParameter("termId").trim()).append("'");
		}
		if (isNotEmpty(request.getParameter("errFlag"))) {// 差错交易类型,errFlag为交易挂账和交易解挂的查询条件不同20121211
			if (request.getParameter("errFlag").equals("2")) {
				whereSql.append(" and 1!=1 ");
				whereSql2.append(" and d.STLM_FLG = '2' ");// 20121211
				whereSqlTxn.append(" and a.BATCH_FLAG='")
						.append(request.getParameter("errFlag")).append("' ");
			} else if (request.getParameter("errFlag").equals("3")) {
				whereSql.append(" and 1!=1 ");
				whereSql2
						.append(" and (d.STLM_FLG = '1' and d.OLD_TXN_FLG='3') ");// 20121211
				whereSqlTxn.append(" and a.BATCH_FLAG='")
						.append(request.getParameter("errFlag")).append("' ");
			} else {
				whereSql.append(" and t.ERR_FLAG = '"
						+ request.getParameter("errFlag") + "'");
				// whereSql2.append(" and d.OLD_TXN_FLG='3' ");//20121207标记解挂已清分记录
				whereSql2.append(" and 1!=1 ");// 20121224
				whereSqlTxn.append(" and 1!=1 ");
			}
			whereSqlEle.append(" and e.TXN_NUM ='")
					.append(request.getParameter("errFlag")).append("'");
		} else {
			whereSql2.append(" and (d.STLM_FLG in(2,3) or d.OLD_TXN_FLG='3') ");// 20121211modified,没有查询条件时默认缺少的where条件
			// whereSqlTxn.append(" and b.STLM_FLG in(2,3) ");//20121119modified,没有查询条件时默认缺少的where条件
			whereSqlTxn.append(" and a.BATCH_FLAG in('2','3') ");// 20121207没有查询条件时默认缺少的where条件
		}
		if (isNotEmpty(request.getParameter("txnSsn"))) {// 差错系统跟踪号
			whereSql.append(" and t.TXN_SSN = '"
					+ request.getParameter("txnSsn") + "'");
			// 20120913modified
			whereSql2.append(" and d.TXN_SSN = '"
					+ request.getParameter("txnSsn") + "'");
			whereSqlTxn.append(" and a.SYS_SEQ_NUM = '"
					+ request.getParameter("txnSsn") + "'");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {// 原交易类型
			if (!"*".equals(request.getParameter("txnNum"))) {
				whereSql.append(" and t.TXN_NUM = '"
						+ request.getParameter("txnNum") + "'");
				whereSql2.append(" and d.TXN_NUM = '"
						+ request.getParameter("txnNum") + "'");
				whereSqlTxn.append(" and 1!=1 ");
			}
		}
		if (isNotEmpty(request.getParameter("startDate"))) {// 原交易起始日期
			whereSql.append(" and t.ORG_DATE_TIME >= '"
					+ request.getParameter("startDate") + "' ");
			whereSql2.append(" and d.TRANS_DATE >= '"
					+ request.getParameter("startDate") + "' ");
			whereSqlTxn.append(" and (substr(a.inst_date,0,8)) >= '"
					+ request.getParameter("startDate") + "' ");
			whereSqlEle.append(" and e.TRANS_DATE >='")
					.append(request.getParameter("startDate")).append("'");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and t.ORG_DATE_TIME <= '"
					+ request.getParameter("endDate") + "' ");
			whereSql2.append(" and d.TRANS_DATE <= '"
					+ request.getParameter("endDate") + "' ");
			whereSqlTxn.append(" and (substr(a.inst_date,0,8)) <= '"
					+ request.getParameter("endDate") + "' ");
			whereSqlEle.append(" and e.TRANS_DATE <='")
					.append(request.getParameter("endDate")).append("'");
		}
		if (isNotEmpty(request.getParameter("startTime"))) {// 原交易清算起始时间20121205
			whereSql.append(" and t.DATE_SETTLMT >= '"
					+ request.getParameter("startTime") + "' ");
			whereSql2.append(" and d.settl_date >= '"
					+ request.getParameter("startTime") + "' ");
			// whereSqlTxn.append(" and  b.settl_date>= '" +
			// request.getParameter("startTime") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endTime"))) {// 原交易清算起始时间20121205
			whereSql.append(" and t.DATE_SETTLMT <= '"
					+ request.getParameter("endTime") + "' ");
			whereSql2.append(" and d.settl_date <= '"
					+ request.getParameter("endTime") + "' ");
			// whereSqlTxn.append(" and d.settl_date <= '" +
			// request.getParameter("endTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("pan"))) {// 原交易卡号
			whereSql.append(" and t.PAN = '" + request.getParameter("pan")
					+ "'");
			whereSql2.append(" and d.PAN = '" + request.getParameter("pan")
					+ "'");
			whereSqlTxn.append(" and a.PAN = '" + request.getParameter("pan")
					+ "'");
			whereSqlEle.append(" and e.PAN ='")
					.append(request.getParameter("pan")).append("'");
		}
		if (isNotEmpty(request.getParameter("minOrgTransAmt"))) {// 原交易最小金额
			date = request.getParameter("minOrgTransAmt");
			int temp = (int) Math.round(Double.valueOf(date) * 100);
			if (temp < 0) {
				date = "-"
						+ CommonFunction.fillString(String.valueOf(temp)
								.substring(1), '0', 11, false);
			} else {
				date = CommonFunction.fillString(String.valueOf(temp), '0', 12,
						false);
			}
			whereSql.append(" and to_number(t.ORG_TRANS_AMT) >= " + temp + " ");
			whereSql2.append(" and to_number(d.TRANS_AMT) >= " + temp + " ");
			whereSqlTxn.append(" and a.AMT_TRANS >= '" + date + "' ");
			whereSqlEle.append(" and to_number(e.TRANS_AMT) >=").append(temp);
		}
		if (isNotEmpty(request.getParameter("maxOrgTransAmt"))) {// 原交易最大金额
			date = request.getParameter("maxOrgTransAmt");
			int temp = (int) Math.round(Double.valueOf(date) * 100);
			if (temp < 0) {
				date = "-"
						+ CommonFunction.fillString(String.valueOf(temp)
								.substring(1), '0', 11, false);
			} else {
				date = CommonFunction.fillString(String.valueOf(temp), '0', 12,
						false);
			}
			whereSql.append(" and to_number(t.ORG_TRANS_AMT) <= " + temp + " ");
			whereSql2.append(" and to_number(d.TRANS_AMT) <= " + temp + " ");
			whereSqlTxn.append(" and a.AMT_TRANS <= '" + date + "' ");
			whereSqlEle.append(" and to_number(e.TRANS_AMT) <=").append(temp);
		}
		if (isNotEmpty(request.getParameter("orgTxnSsn"))) {// 原收单行流水号
			whereSql.append(" and t.ORG_TXN_SSN = '"
					+ request.getParameter("orgTxnSsn") + "'");
			whereSql2.append(" and d.TXN_SSN = '"
					+ request.getParameter("orgTxnSsn") + "'");
			whereSqlTxn.append(" and a.SYS_SEQ_NUM = '"
					+ request.getParameter("orgTxnSsn") + "'");
		}
		if (isNotEmpty(request.getParameter("orgRetrivlRef"))) {// 原系统参考号
			whereSql.append(" and t.ORG_RETRIVL_REF = '"
					+ request.getParameter("orgRetrivlRef") + "'");
			whereSql2.append(" and 1!=1 ");
			whereSqlTxn.append(" and a.RETRIVL_REF = '"
					+ request.getParameter("orgRetrivlRef") + "'");
		}
		if (isNotEmpty(request.getParameter("orgTermSsn"))) {// 原终端流水号
			whereSql.append(" and t.ORG_TERM_SSN = '"
					+ request.getParameter("orgTermSsn") + "'");
			whereSql2.append(" and d.TERM_SSN = '"
					+ request.getParameter("orgTermSsn") + "'");
			whereSqlTxn.append(" and a.TERM_SSN = '"
					+ request.getParameter("orgTermSsn") + "'");
			whereSqlEle.append(" and e.TXN_CER_NO ='")
					.append(request.getParameter("orgTermSsn")).append("'");
		}
		sql.append(
				"SELECT t.DATE_SETTLMT as DATE_SETTLMT,t.TXN_NUM as TXN_NUM,t.TXN_SSN as TXN_SSN,t.ERR_FLAG as ERR_FLAG,"
						+ "t.STLM_FLG as STLM_FLG,t.ORG_DATE_TIME as TRANS_DATE_TIME,t.PAN as PAN,t.AMT_TRANS as AMT_TRANS,t.MCHT_ID as MCHT_ID,"
						+ "t.TERM_ID as TERM_ID,' ' as TXN_KEY,m.MCHT_NM,to_number(t.ORG_TRANS_AMT)/100 as orgTransAmt FROM BTH_CUP_ERR_TXN t"
						+ ",TBL_MCHT_BASE_INF m WHERE m.MCHT_NO=t.MCHT_ID ")
				.append(whereSql);

		sql.append(
				" UNION SELECT (case When trim(d.settl_date)Is Null then '' Else d.settl_date End) as DATE_SETTLMT,"// 20121205：settl_date为空时设置为空字符串
						+ "d.TXN_NUM as TXN_NUM,d.TXN_SSN as TXN_SSN,(case When trim(d.OLD_TXN_FLG) Is Null then d.STLM_FLG"
						+ " when trim(d.OLD_TXN_FLG)='' then d.STLM_FLG Else d.OLD_TXN_FLG End) as ERR_FLAG," // 20121211修改
						+ "d.STLM_FLG as STLM_FLG,d.TRANS_DATE as TRANS_DATE_TIME,d.PAN as PAN,d.TRANS_AMT as AMT_TRANS,d.MCHT_CD as MCHT_ID,"
						+ "d.TERM_ID as TERM_ID,d.TXN_KEY as TXN_KEY,m.MCHT_NM,to_number(d.TRANS_AMT)/100 as orgTransAmt FROM TBL_ALGO_DTL d,TBL_MCHT_BASE_INF m "
						+ "WHERE m.MCHT_NO=d.MCHT_CD ").append(whereSql2);
		// 20121207如果未清分清算则需要到交易流水表中先查询出挂账或解挂信息的最大交易日期，清分清算了则不需要
		String countsql = "select max(d.trans_date) from TBL_ALGO_DTL d ";
		String maxTransDate = CommonFunction.getCommQueryDAO()
				.findCountBySQLQuery(countsql);
		// 查询T-1日交易是否已经清分清算，如果未清分清算则需要到交易流水表中查询挂账或解挂信息，清分清算了则不需要
		// String
		// countsql="select count(*) from TBL_ALGO_DTL d where trans_date='"+CommonFunction.getBeforeDate(CommonFunction.getCurrentDate(),
		// -1)+"' ";
		// if("0".equals(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countsql))){
		Log.log("拼接查询未清分的记录！");
		// 由于中行的交易时间是不含年份的，故查询时需要拼接年份
		// 拼接年份逻辑：如果中行的交易时间的月份为12月 并且数据库系统时间的月份为1月 则将数据库系统时间的年份-1 否则则取数据库系统时间的年份
		// 前提：数据库系统时间与中行的服务器时间 相差不超过8小时，挂账都是在第二天做的
		// sql.append(" union select '")20120731修改
		// .append(CommonFunction.getBeforeDate(CommonFunction.getCurrentDate(),
		// -1))20120731修改
		// .append("' as DATE_SETTLMT,")20120731修改
		if (maxTransDate != null && !"".equals(maxTransDate)) {
			sql.append(
					" union select '' as DATE_SETTLMT,a.txn_num as TXN_NUM,a.SYS_SEQ_NUM as TXN_SSN,")
					// 20121128：settl_date为空时设置为空字符串
					.append("a.BATCH_FLAG as ERR_FLAG,a.BATCH_FLAG as STLM_FLG,(substr(a.inst_date,0,8)),a.PAN,a.AMT_TRANS,a.card_accp_id,")
					.append("a.card_accp_term_id,a.KEY_RSP as TXN_KEY,m.MCHT_NM,to_number(a.AMT_TRANS)/100 as orgTransAmt ")
					.append("from tbl_n_txn a,TBL_MCHT_BASE_INF m where m.MCHT_NO = a.card_accp_id ")
					// " and b.TXN_KEY = a.KEY_RSP " +
					// //20121119modify,交易流水表和清分表之间关联条件
					.append("and (Case When substr(a.Date_local_trans,0,2)='12' And to_char(Sysdate,'MM')='01' Then (to_number(to_char(Sysdate,'YYYY'))-1) ")
					.append("Else to_number(to_char(Sysdate,'YYYY')) End )||a.Date_local_trans > '")
					.append(maxTransDate.trim()).append("' ")
					.append(whereSqlTxn.toString());
		}
		// 查询补电子现金消费中审核通过的记录
		// 前题:差错系统跟踪号、原交易类型、原交易清算起始时间、原交易清算终止时间、原收单行流水号、原系统参考号为空
		if (StringUtil.isNull(request.getParameter("txnSsn"))
				&& StringUtil.isNull(request.getParameter("txnNum"))
				&& StringUtil.isNull(request.getParameter("startTime"))
				&& StringUtil.isNull(request.getParameter("endTime"))
				&& StringUtil.isNull(request.getParameter("orgTxnSsn"))
				&& StringUtil.isNull(request.getParameter("orgRetrivlRef"))) {
			sql.append(
					" union SELECT e.DATE_SETTLMT as DATE_SETTLMT,e.TXN_NUM as TXN_NUM,'' as TXN_SSN,e.TXN_NUM as ERR_FLAG,e.DATE_SETTLMT as STLM_FLG,")
					.append("e.TRANS_DATE as TRANS_DATE_TIME,e.PAN as PAN,e.TRANS_AMT as AMT_TRANS,e.MCHT_CD as MCHT_ID,e.TERM_ID as TERM_ID,e.id as TXN_KEY,")
					.append("m.MCHT_NM,0 as orgTransAmt FROM TBL_ELEC_CASH_INF e,TBL_MCHT_BASE_INF m WHERE m.MCHT_NO=e.MCHT_CD and e.SA_STATE = '2'")
					.append(whereSqlEle);
		}

		sql.append(" order by DATE_SETTLMT desc");

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String countSql = "select count(*) from (" + sql.toString() + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		// ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 差错信息查询_查询视图
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBthCupErrInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer("");
		String date = null;

		if (isNotEmpty(request.getParameter("mchtId"))) {// 商户号
			whereSql.append(" and MCHT_ID = '" + request.getParameter("mchtId")
					+ "'");
		}
		if (isNotEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and trim(TERM_ID) = '"
					+ request.getParameter("termId").trim() + "'");
		}
		if (isNotEmpty(request.getParameter("dateSettlmt"))) { // 清算日期
			whereSql.append(" and DATE_SETTLMT = '"
					+ request.getParameter("dateSettlmt") + "' ");
		}
		if (isNotEmpty(request.getParameter("orgDateTime"))) { // 交易日期
			whereSql.append(" and ORG_DATE_TIME = '"
					+ request.getParameter("orgDateTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("errType"))) { // 差错类型
			whereSql.append(" and ERR_TYPE like '%"
					+ request.getParameter("errType") + "%' ");
		}
		if (isNotEmpty(request.getParameter("txnName"))) { // 交易名称
			whereSql.append(" and TXN_NAME like '%"
					+ request.getParameter("txnName") + "%' ");
		}
		if (isNotEmpty(request.getParameter("errCode"))) { // 差错交易代码
			whereSql.append(" and ERR_CODE = '"
					+ request.getParameter("errCode") + "' ");
		}
		/*
		 * if (isNotEmpty(request.getParameter("errFlag"))) {//
		 * 差错交易类型,errFlag为交易挂账和交易解挂的查询条件不同20121211
		 * whereSql.append(" and trim(ERR_FLAG) ='")
		 * .append(request.getParameter("errFlag").trim()) .append("' "); } if
		 * (isNotEmpty(request.getParameter("txnSsn"))) {// 差错系统跟踪号
		 * whereSql.append(" and TXN_SSN = '" + request.getParameter("txnSsn") +
		 * "'"); } if (isNotEmpty(request.getParameter("txnNum"))) {// 原交易类型 if
		 * (!"*".equals(request.getParameter("txnNum"))) {
		 * whereSql.append(" and TXN_NUM = '" + request.getParameter("txnNum") +
		 * "'"); } } if (isNotEmpty(request.getParameter("startDate"))) {//
		 * 原交易起始日期 whereSql.append(" and TRANS_DATE_TIME >= '" +
		 * request.getParameter("startDate") + "' "); } if
		 * (!StringUtil.isNull(request.getParameter("endDate"))) {
		 * whereSql.append(" and TRANS_DATE_TIME <= '" +
		 * request.getParameter("endDate") + "' "); } if
		 * (isNotEmpty(request.getParameter("startTime"))) {// 原交易清算起始时间20121205
		 * whereSql.append(" and DATE_SETTLMT >= '" +
		 * request.getParameter("startTime") + "' "); } if
		 * (!StringUtil.isNull(request.getParameter("endTime"))) {//
		 * 原交易清算起始时间20121205 whereSql.append(" and DATE_SETTLMT <= '" +
		 * request.getParameter("endTime") + "' "); } if
		 * (isNotEmpty(request.getParameter("pan"))) {// 原交易卡号
		 * whereSql.append(" and trim(PAN) = '" +
		 * request.getParameter("pan").trim() + "'"); } if
		 * (isNotEmpty(request.getParameter("minOrgTransAmt"))) {// 原交易最小金额
		 * whereSql.append(" and to_number(ORG_TRANS_AMT) >= " +
		 * request.getParameter("minOrgTransAmt") + " "); } if
		 * (isNotEmpty(request.getParameter("maxOrgTransAmt"))) {// 原交易最大金额
		 * whereSql.append(" and to_number(ORG_TRANS_AMT) <= " +
		 * request.getParameter("maxOrgTransAmt") + " "); } if
		 * (isNotEmpty(request.getParameter("orgTxnSsn"))) {// 原收单行流水号
		 * whereSql.append(" and trim(ORG_TXN_SSN) = '" +
		 * request.getParameter("orgTxnSsn").trim() + "'"); } if
		 * (isNotEmpty(request.getParameter("orgRetrivlRef"))) {// 原系统参考号
		 * whereSql.append(" and trim(ORG_RETRIVL_REF) = '" +
		 * request.getParameter("orgRetrivlRef").trim() + "'"); } if
		 * (isNotEmpty(request.getParameter("orgTermSsn"))) {// 原终端流水号
		 * whereSql.append(" and trim(ORG_TERM_SSN) = '" +
		 * request.getParameter("orgTermSsn").trim() + "'"); }
		 */
		sql.append(
		// "SELECT DATE_SETTLMT,TXN_NUM,TXN_SSN,ERR_FLAG,STLM_FLG,TRANS_DATE_TIME,PAN,AMT_TRANS,MCHT_ID,TERM_ID,"
		// + "TXN_KEY,MCHT_NM,ORG_TRANS_AMT FROM BTH_CUP_ERR_INF WHERE 1=1 ")
				"SELECT MCHT_ID,MCHT_NM,MCHT_TP,TERM_ID,TXN_SSN,CHANNEL_NUM,ORG_DATE_TIME,ORG_TRANS_AMT,TXN_NAME,PAN,ERR_TYPE,ERR_CODE,INST_DATE,DATE_SETTLMT,AMT_FLAG "
						+ "FROM BTH_KQ_ERR_TXN WHERE 1=1").append(whereSql);

		sql.append(" order by DATE_SETTLMT desc");

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String countSql = "select count(*) from (" + sql.toString() + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 已清算交易信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblAlgoDtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer("");
		String date = null;

		if (isNotEmpty(request.getParameter("mchtId"))) {// 商户号
			whereSql.append(" and a.MCHT_CD = '"
					+ request.getParameter("mchtId") + "'");
		}
		if (isNotEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and a.TERM_ID = '"
					+ request.getParameter("termId") + "'");
		}
		if (isNotEmpty(request.getParameter("txnNum"))
				&& !request.getParameter("txnNum").endsWith("*")) {// 原交易类型
			whereSql.append(" and a.TXN_NUM = '"
					+ request.getParameter("txnNum") + "'");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {// 原交易起始日期
			whereSql.append(" and a.TRANS_DATE >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and a.TRANS_DATE <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("pan"))) {// 原交易卡号
			whereSql.append(" and a.PAN = '" + request.getParameter("pan")
					+ "'");
		}
		if (isNotEmpty(request.getParameter("minOrgTransAmt"))) {// 原交易最小金额
			date = request.getParameter("minOrgTransAmt");
			int temp = (int) Math.round(Double.valueOf(date) * 100);
			date = CommonFunction.fillString(String.valueOf(temp), '0', 12,
					false);

			whereSql.append(" and a.TRANS_AMT >= '" + date + "'");
		}
		if (isNotEmpty(request.getParameter("maxOrgTransAmt"))) {// 原交易最大金额
			date = request.getParameter("maxOrgTransAmt");
			int temp = (int) Math.round(Double.valueOf(date) * 100);
			date = CommonFunction.fillString(String.valueOf(temp), '0', 12,
					false);

			whereSql.append(" and a.TRANS_AMT <= '" + date + "'");
		}
		if (isNotEmpty(request.getParameter("txnSsn"))) {// 原收单行流水号
			whereSql.append(" and a.TXN_SSN = '"
					+ request.getParameter("txnSsn") + "'");
		}
		if (isNotEmpty(request.getParameter("misc1"))) {// 原系统参考号
			whereSql.append(" and a.MISC_1 like '%"
					+ request.getParameter("misc1") + "%'");
		}
		if (isNotEmpty(request.getParameter("termSsn"))) {// 原终端流水号
			whereSql.append(" and a.TERM_SSN = '"
					+ request.getParameter("termSsn") + "'");
		}

		StringBuffer countSql = new StringBuffer(
				"SELECT COUNT(*) FROM TBL_ALGO_DTL a WHERE a.STLM_FLG='1' ");

		sql.append("SELECT a.DATE_SETTLMT,a.TXN_KEY,a.MCHT_CD,a.TERM_ID,a.TXN_NUM,a.TXN_SSN,a.STLM_FLG,a.TRANS_DATE,a.PAN,a.TRANS_AMT"
				+ ",substr(a.MISC_1,1,12) as MISC_1,a.TERM_SSN,b.SA_STATE FROM TBL_ALGO_DTL a FULL JOIN TBL_ALGO_DTL_CHECK b "
				+ "on a.DATE_SETTLMT=b.DATE_SETTLMT and a.TXN_KEY=b.TXN_KEY where a.STLM_FLG='1' ");
		sql.append(whereSql);
		sql.append(" order by a.DATE_SETTLMT ");

		countSql.append(whereSql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql.toString());
		ret[0] = dataList;
		// ret[1] = dataList.size();
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的已清算交易信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckTblAlgoDtl(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer("");
		String date = null;

		if (isNotEmpty(request.getParameter("mchtId"))) {// 商户号
			whereSql.append(" and a.MCHT_CD = '"
					+ request.getParameter("mchtId") + "'");
		}
		if (isNotEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and a.TERM_ID = '"
					+ request.getParameter("termId") + "'");
		}
		if (isNotEmpty(request.getParameter("txnNum"))) {// 原交易类型
			whereSql.append(" and a.TXN_NUM = '"
					+ request.getParameter("txnNum") + "'");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {// 原交易起始日期
			whereSql.append(" and a.TRANS_DATE >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and a.TRANS_DATE <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("pan"))) {// 原交易卡号
			whereSql.append(" and a.PAN = '" + request.getParameter("pan")
					+ "'");
		}
		if (isNotEmpty(request.getParameter("minOrgTransAmt"))) {// 原交易最小金额
			date = request.getParameter("minOrgTransAmt");
			int temp = (int) Math.round(Double.valueOf(date) * 100);
			date = CommonFunction.fillString(String.valueOf(temp), '0', 12,
					false);

			whereSql.append(" and a.TRANS_AMT >= '" + date + "'");
		}
		if (isNotEmpty(request.getParameter("maxOrgTransAmt"))) {// 原交易最大金额
			date = request.getParameter("maxOrgTransAmt");
			int temp = (int) Math.round(Double.valueOf(date) * 100);
			date = CommonFunction.fillString(String.valueOf(temp), '0', 12,
					false);

			whereSql.append(" and a.TRANS_AMT <= '" + date + "'");
		}
		if (isNotEmpty(request.getParameter("orgTxnSsn"))) {// 原收单行流水号
			whereSql.append(" and a.TXN_SSN = '"
					+ request.getParameter("orgTxnSsn") + "'");
		}
		if (isNotEmpty(request.getParameter("misc1"))) {// 原系统参考号
			whereSql.append(" and a.MISC_1 = '" + request.getParameter("misc1")
					+ "'");
		}
		if (isNotEmpty(request.getParameter("orgTermSsn"))) {// 原终端流水号
			whereSql.append(" and a.TERM_SSN = '"
					+ request.getParameter("orgTermSsn") + "'");
		}

		sql.append("SELECT b.id,a.DATE_SETTLMT,a.TXN_KEY,a.MCHT_CD,a.TERM_ID,a.TXN_NUM,a.TXN_SSN,a.STLM_FLG,a.TRANS_DATE,a.PAN,a.TRANS_AMT"
				+ ",substr(a.MISC_1,1,12) as MISC_1,a.TERM_SSN,b.SA_STATE FROM TBL_ALGO_DTL a FULL JOIN TBL_ALGO_DTL_CHECK b "
				+ "on a.DATE_SETTLMT=b.DATE_SETTLMT and a.TXN_KEY=b.TXN_KEY where a.STLM_FLG='1' and b.SA_STATE='0' ");
		sql.append(whereSql);
		sql.append(" order by a.DATE_SETTLMT ");
		StringBuffer countSql = new StringBuffer("SELECT COUNT(*) FROM (")
				.append(sql).append(")");

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql.toString());
		ret[0] = dataList;
		// ret[1] = dataList.size();
		ret[1] = count;
		return ret;
	}

	/**
	 * 应答码信息维护
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getrspCodeMapInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("srcId"))) {
			whereSql.append(" and SRC_ID = '" + request.getParameter("srcId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("destId"))) {
			whereSql.append(" and DEST_ID = '" + request.getParameter("destId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("srcRspCode"))) {
			whereSql.append(" and SRC_RSP_CODE= '"
					+ request.getParameter("srcRspCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("destRspCode"))) {
			whereSql.append(" and DEST_RSP_CODE= '"
					+ request.getParameter("destRspCode") + "' ");
		}
		String sql = "SELECT SRC_ID,DEST_ID,SRC_RSP_CODE,SRC_RSP_CODE_L_OLD,DEST_RSP_CODE,DEST_RSP_CODE_L_OLD,RSP_CODE_DSP_OLD,STATUS_ID,OPRTIME,"
				+ "CHECKTIME,OPR_ID,CHECK_ID FROM tbl_rsp_code_map where 1=1 "
				+ whereSql.toString();
		sql += " order by OPRTIME desc";
		String countSql = "SELECT COUNT(*) FROM tbl_rsp_code_map where 1=1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的应答码信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckRspCodeMapInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("srcId"))) {
			whereSql.append(" and SRC_ID = '" + request.getParameter("srcId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("destId"))) {
			whereSql.append(" and DEST_ID = '" + request.getParameter("destId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("srcRspCode"))) {
			whereSql.append(" and SRC_RSP_CODE= '"
					+ request.getParameter("srcRspCode") + "' ");
		}
		if (isNotEmpty(request.getParameter("destRspCode"))) {
			whereSql.append(" and DEST_RSP_CODE= '"
					+ request.getParameter("destRspCode") + "' ");
		}
		String sql = "SELECT SRC_ID,DEST_ID,SRC_RSP_CODE,SRC_RSP_CODE_L_OLD,DEST_RSP_CODE,DEST_RSP_CODE_L_OLD,RSP_CODE_DSP_OLD,STATUS_ID,"
				+ "OPRTIME,CHECKTIME,OPR_ID,CHECK_ID FROM tbl_rsp_code_map where trim(STATUS_ID) in('0','3','4') "
				+ whereSql.toString();
		sql += " order by OPRTIME desc";
		String countSql = "SELECT COUNT(*) FROM tbl_rsp_code_map where trim(STATUS_ID) in('0','3','4') "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/*
	 * 机构信息查询全部
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAgenInfoQueryBelow(int begin,
			HttpServletRequest request) {
		String whereSql = "";
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("agenid"))) {
			whereSql += " AND AGEN_ID = '" + request.getParameter("agenid")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND STATUE = '" + request.getParameter("mchtStatus")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "Select AGEN_ID,AGEN_NAME,b.statue_name STATUE, AGEN_REG_BODY,BANK_NAME,AGEN_INCOME_ACCOUNT_NAME,"
				+ "AGEN_EXPENSES_ACCOUNT_NAME,a.TRAN_TYPE From tbl_agency_info a,TBL_ADUIT_STATUS b Where b.type='1' "
				+
				// "And a.STATUE=b.statue_id And a.agen_reg_body=c.city_code And a.statue In('0','1','2','4','6','7','8')"
				// + whereSql;
				// 20120817,删除状态4和6，添加3－－新增拒绝状态
				"And a.STATUE=b.statue_id  And a.statue In('0','1','2','3','7','8')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询机构sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 机构分润信息整体页面查询(未包含注销状态) */
	@SuppressWarnings("unchecked")
	public static Object[] getAgenFeeQueryBelow(int begin,
			HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("agenid"))) {
			whereSql += " AND AGEN_ID = '" + request.getParameter("agenid")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND STATUE = '" + request.getParameter("mchtStatus")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mccCode"))) {
			whereSql += " and MCC_CODE='"
					+ request.getParameter("mccCode").trim() + "'";
		}
		if (isNotEmpty(request.getParameter("mccGrp"))) {
			whereSql += " and MCC_CODE in (select mchnt_tp from TBL_INF_MCHNT_TP WHERE MCHNT_TP_GRP = '"
					+ request.getParameter("mccGrp").trim() + "')";
		}

		Object[] ret = new Object[2];
		String sql = "Select AGEN_ID,FEE_ID,TERM_ID,MTCH_NO,MCC_CODE,TRADE_ACCEPT_REG,b.STATUE_NAME STATUE from TBL_AGENCY_FEE_LUB_TMP a,"
				+ "TBL_ADUIT_STATUS b where b.type='1' and b.STATUE_ID=a.STATUE  And a.statue In('0','1','2','4','6','7','8')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		sql += " order by agen_id desc,fee_id desc";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机构信息里面分润查询
	 * */
	@SuppressWarnings("unchecked")
	public static Object[] getAgenFee(int begin, HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("feeid"))) {
			whereSql += " AND FEE_ID = '" + request.getParameter("feeid")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("agenid"))) {
			whereSql += " AND AGEN_ID = '" + request.getParameter("agenid")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "SELECT FEE_ID,AMOUNT_LIMIT,MCHT_PERCENT_LIMIT,MCHT_PERCENT_MAX,MCHT_LUB_PARAM,RATE_PARAM1,statue,AGEN_ID "
				+ "FROM TBL_AGENCY_FEE_LUB  WHERE statue='1'" + whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 机构信息待审核详细查询
	 * */
	@SuppressWarnings("unchecked")
	public static Object[] getAgenInfoQueryShenhe(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String whereSql = "";
		if (isNotEmpty(request.getParameter("agenid"))) {
			whereSql += " AND AGEN_ID = '" + request.getParameter("agenid")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND STATUE = '" + request.getParameter("mchtStatus")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "Select AGEN_ID,AGEN_NAME,b.statue_name STATUE, AGEN_REG_BODY,BANK_NAME,AGEN_INCOME_ACCOUNT_NAME,"
				+ "AGEN_EXPENSES_ACCOUNT_NAME,a.TRAN_TYPE From tbl_agency_info a,TBL_ADUIT_STATUS b Where b.type='1' "
				+ "and a.STATUE=b.statue_id and a.statue In('0','2','8')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 机构分润审核 */
	@SuppressWarnings("unchecked")
	public static Object[] getAgenFeeShenQueryBelow(int begin,
			HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("agenid"))) {
			whereSql += " AND AGEN_ID = '" + request.getParameter("agenid")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND STATUE = '" + request.getParameter("mchtStatus")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mccCode"))) {
			whereSql += " and MCC_CODE='"
					+ request.getParameter("mccCode").trim() + "'";
		}
		if (isNotEmpty(request.getParameter("mccGrp"))) {
			whereSql += " and MCC_CODE in (select mchnt_tp from TBL_INF_MCHNT_TP WHERE MCHNT_TP_GRP = '"
					+ request.getParameter("mccGrp").trim() + "')";
		}
		Object[] ret = new Object[2];
		String sql = "Select AGEN_ID,FEE_ID,TERM_ID,MTCH_NO,MCC_CODE,TRADE_ACCEPT_REG,b.STATUE_NAME STATUE from TBL_AGENCY_FEE_LUB_TMP a,"
				+ "TBL_ADUIT_STATUS b,(select * from TBL_CITY_CODE union select key,key||' - '||value  from cst_sys_param where owner = 'ALLAREA' ) c where b.type='1' and b.STATUE_ID=a.STATUE AND c.city_code=a.TRADE_ACCEPT_REG And a.statue In('0','2','8')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		sql += " order by agen_id desc,fee_id desc";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 事前风险控制 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskBefore(int begin, HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("mchtname"))) {
			whereSql += " AND MCHT_NM like '%"
					+ request.getParameter("mchtname").trim() + "%' ";
		}
		if (isNotEmpty(request.getParameter("grade"))) {
			whereSql += " AND GRADE = '" + request.getParameter("grade") + "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {// 起始日期
			whereSql += " AND CREATE_TIME >= '"
					+ request.getParameter("startDate") + "000000" + "' ";
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {// 结束日期
			whereSql += " AND CREATE_TIME <= '"
					+ request.getParameter("endDate") + "235959" + "' ";
		}

		Object[] ret = new Object[2];
		String sql = "select MCHT_NM,LICENSE_NO,ORG_CODE,IDENTITY,SCORE,b.VALUE,b.RESERVE grade,SA_STATUE,a.CREATE_TIME "
				+ "from RISK_BEFORE a,cst_sys_param b where b.OWNER='RISKLEVEL' and a.grade=b.KEY"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		sql += " order by a.CREATE_TIME desc ";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 事前风险控制审核 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskBeforeShenHe(int begin,
			HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("mchtname"))) {
			whereSql += " AND MCHT_NM like '%"
					+ request.getParameter("mchtname") + "%' ";
		}
		if (isNotEmpty(request.getParameter("grade"))) {
			whereSql += " AND GRADE = '" + request.getParameter("grade") + "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {// 起始日期
			whereSql += " AND CREATE_TIME >= '"
					+ request.getParameter("startDate") + "000000" + "' ";
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {// 结束日期
			whereSql += " AND CREATE_TIME <= '"
					+ request.getParameter("endDate") + "235959" + "' ";
		}

		Object[] ret = new Object[2];
		String sql = "select MCHT_NM,LICENSE_NO,ORG_CODE,IDENTITY,SCORE,b.VALUE,b.RESERVE grade,SA_STATUE "
				+ "from RISK_BEFORE a,cst_sys_param b where b.OWNER='RISKLEVEL' and a.grade=b.KEY and SA_STATUE in('0','4')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		sql += " order by a.CREATE_TIME desc ";

		System.out.println("事前风险控制审核查询sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 渠道交易黑名单检查 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskTranCtl(int begin, HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("statue"))) {
			whereSql += " AND SA_STATE = '" + request.getParameter("statue")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql += " AND CHANNEL = '" + request.getParameter("channel")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "select CHANNEL,c.Value channelname,CARD_BIN_OLD,a.TXN_NUM_OLD TXN_NUM_OLD,SA_STATE,a.id id,a.cre_id,a.up_id,a.cre_time,a.up_time from TBL_RISK_CHL_TRAN_CTL a,CST_SYS_PARAM c where c.OWNER='CHANNEL' And c.Key=a.channel and a.SA_STATE In('0','1','2','3','4')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 渠道交易黑名单审核 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskTranShenheCtl(int begin,
			HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql += " AND CHANNEL = '" + request.getParameter("channel")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("statue"))) {
			whereSql += " AND SA_STATE = '" + request.getParameter("statue")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "select CHANNEL,c.Value channelname,CARD_BIN_OLD,a.TXN_NUM_OLD TXN_NUM_OLD,SA_STATE,a.id id,a.cre_id,a.up_id,a.cre_time,a.up_time from TBL_RISK_CHL_TRAN_CTL a,CST_SYS_PARAM c where c.OWNER='CHANNEL' And c.Key=a.channel and a.SA_STATE In('0','3','4')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询机构sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 渠道交易权限 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskTranLimit(int begin,
			HttpServletRequest request) {
		String whereSql = "";
		if (isNotEmpty(request.getParameter("statue"))) {
			whereSql += " AND SA_STATE = '" + request.getParameter("statue")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql += " AND CHANNEL = '" + request.getParameter("channel")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "select CHANNEL,c.Value channelname,CARD_BIN_OLD,a.TXN_NUM_OLD TXN_NUM_OLD,limit,a.sa_state,a.id id,CRE_ID,UP_ID,CRE_TIME,UP_TIME from tbl_risk_chl_tran_limit a,CST_SYS_PARAM c where   c.OWNER='CHANNEL' And c.Key=a.channel"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询机构sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 渠道交易权限审核 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskTranShenheLimit(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String whereSql = "";
		if (isNotEmpty(request.getParameter("statue"))) {
			whereSql += " AND SA_STATE = '" + request.getParameter("statue")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("channel"))) {
			whereSql += " AND CHANNEL = '" + request.getParameter("channel")
					+ "' ";
		}
		Object[] ret = new Object[2];
		String sql = "select CHANNEL,c.Value channelname,CARD_BIN_OLD,a.TXN_NUM_OLD TXN_NUM_OLD,limit,a.sa_state,a.id id,CRE_ID from tbl_risk_chl_tran_limit a,CST_SYS_PARAM c where  c.OWNER='CHANNEL' And c.Key=a.channel and a.SA_STATE In('0','3','4')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询操作员信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOprInfoWithBrh(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "SELECT OPR_ID,BRH_ID,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL,OPR_MOBILE FROM TBL_OPR_INFO WHERE BRH_ID = '"
				+ request.getParameter("brhId") + "'";
		String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO WHERE BRH_ID = '"
				+ request.getParameter("brhId") + "'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询当前操作员下属操作员信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOprInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());
		if (isNotEmpty(request.getParameter("oprId"))) {
			whereSql.append(" AND OPR_ID = '" + request.getParameter("oprId")
					+ "' ");
		}

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND REGISTER_DT >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND REGISTER_DT <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT OPR_ID,BRH_ID,OPR_DEGREE,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL,"
				+ "OPR_MOBILE,PWD_OUT_DATE,OPR_STA,OPR_EMAIL FROM TBL_OPR_INFO "
				+ whereSql;
		sql += " order by REGISTER_DT desc";
		String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询当前操作员下属操作员信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOprInfoS(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());
		if (isNotEmpty(request.getParameter("oprId"))) {
			whereSql.append(" AND OPR_ID = '" + request.getParameter("oprId")
					+ "' ");
		}

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND REGISTER_DT >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND REGISTER_DT <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT OPR_ID,BRH_ID,OPR_DEGREE,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL,"
				+ "OPR_MOBILE,PWD_OUT_DATE,OPR_STA,OPR_EMAIL,AUDIT_STAT,ADD_OPR_ID,LAST_UPD_OPR_ID FROM TBL_OPR_INFO_TMP "
				+ whereSql;
		sql += " order by REGISTER_DT desc";
		String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO_TMP " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银行账户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getBankNoS(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "1 = 1 ");
		if (isNotEmpty(request.getParameter("bankName"))) {
			whereSql.append(" AND BANK_NAME like '%"
					+ request.getParameter("bankName") + "%' ");
		}
		if (isNotEmpty(request.getParameter("bankAccount"))) {
			whereSql.append(" AND BANK_ACCOUNT like '%"
					+ request.getParameter("bankAccount") + "%' ");
		}
		String sql = "SELECT BANK_NO,BANK_NAME,ACCOUNT_NAME,BANK_ACCOUNT,REGION FROM TBL_BANKNO_INFO_TMP "
				+ whereSql;
		sql += " order by UPD_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_BANKNO_INFO_TMP "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银行账户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getBankNoStat(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "1 = 1 ");
		if (isNotEmpty(request.getParameter("bankName"))) {
			whereSql.append(" AND BANK_NAME like '%"
					+ request.getParameter("bankName") + "%' ");
		}
		if (isNotEmpty(request.getParameter("bankAccount"))) {
			whereSql.append(" AND BANK_ACCOUNT like '%"
					+ request.getParameter("bankAccount") + "%' ");
		}
		String sql = "SELECT BANK_NO,BANK_NAME,ACCOUNT_NAME,BANK_ACCOUNT, CRE_TIME, BANK_STATUS,REGION FROM TBL_BANKNO_INFO_TMP "
				+ whereSql;
		sql += " order by UPD_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_BANKNO_INFO_TMP "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银行账户信息审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getBankNoStatA(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "1 = 1 AND BANK_STATUS IN ('2', '3') ");
		if (isNotEmpty(request.getParameter("bankName"))) {
			whereSql.append(" AND BANK_NAME like '%"
					+ request.getParameter("bankName") + "%' ");
		}
		if (isNotEmpty(request.getParameter("bankAccount"))) {
			whereSql.append(" AND BANK_ACCOUNT like '%"
					+ request.getParameter("bankAccount") + "%' ");
		}
		String sql = "SELECT BANK_NO,BANK_NAME,ACCOUNT_NAME,BANK_ACCOUNT, CRE_TIME, BANK_STATUS, UPD_OPR_ID,REGION FROM TBL_BANKNO_INFO_TMP "
				+ whereSql;
		sql += " order by UPD_TIME desc";
		String countSql = "SELECT COUNT(*) FROM TBL_BANKNO_INFO_TMP "
				+ whereSql;
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询当前操作员下属操作员信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOprInfoSF(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());

		whereSql.append(" AND AUDIT_STAT != '0' AND AUDIT_STAT != '1' AND AUDIT_STAT != '4' ");

		if (isNotEmpty(request.getParameter("oprId"))) {
			whereSql.append(" AND OPR_ID = '" + request.getParameter("oprId")
					+ "' ");
		}

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND REGISTER_DT >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND REGISTER_DT <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT OPR_ID,BRH_ID,OPR_DEGREE,OPR_NAME,OPR_GENDER,REGISTER_DT,OPR_TEL,"
				+ "OPR_MOBILE,PWD_OUT_DATE,OPR_STA,OPR_EMAIL,AUDIT_STAT FROM TBL_OPR_INFO_TMP "
				+ whereSql;
		sql += " order by REGISTER_DT desc";
		String countSql = "SELECT COUNT(*) FROM TBL_OPR_INFO_TMP " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询交易日志信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTxnInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("oprNo"))) {
			whereSql.append(" and a.OPR_ID = '" + request.getParameter("oprNo")
					+ "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and TXN_DATE >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and TXN_DATE <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("brhId"))) {
			whereSql.append(" and b.BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (!StringUtil.isNull(request.getParameter("conTxn"))) {
			whereSql.append(" and TXN_CODE = '"
					+ request.getParameter("conTxn") + "' ");
		}

		String sql = "SELECT a.OPR_ID,TXN_DATE,TXN_TIME,TXN_NAME,TXN_STA,ERR_MSG "
				+ "FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID AND "
				+ "b.BRH_ID IN "
				+ operator.getBrhBelowId()
				+ whereSql.toString() + " ORDER BY TXN_DATE DESC,TXN_TIME DESC";

		String countSql = "SELECT COUNT(*) "
				+ "FROM TBL_TXN_INFO a , TBL_OPR_INFO b WHERE a.OPR_ID = b.OPR_ID AND "
				+ "b.BRH_ID IN " + operator.getBrhBelowId()
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询集团商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getGroupMchtInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();

		whereSql.append(" WHERE 1=1 ");
		if (isNotEmpty(request.getParameter("groupMchtCd"))) {
			whereSql.append(" AND group_mcht_cd = '"
					+ request.getParameter("groupMchtCd") + "' ");
		}
		if (isNotEmpty(request.getParameter("grouName"))) {
			whereSql.append(" AND group_name = '"
					+ request.getParameter("grouName") + "' ");
		}

		String order = "ORDER BY a.GROUP_MCHT_CD";
		String sql = "SELECT a.GROUP_MCHT_CD,a.GROUP_NAME,a.GROUP_TYPE,a.REG_FUND,"
				+ "a.BUS_RANGE,a.MCHT_PERSON,a.CONTACT_ADDR FROM TBL_GROUP_MCHT_INF a"
				+ whereSql.toString() + order;
		String countSql = "SELECT COUNT(*) FROM TBL_GROUP_MCHT_INF a"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户入网信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntNet(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE a.brh_id = b.brh_id and a.last_opr_id IN "
				+ operator.getBrhBelowId());
		String operate = request.getParameter("operate");
		if ("check".equals(operate)) {
			whereSql.append(" AND a.status IN ('1','3','5','7')");
		} else {
			whereSql.append(" AND a.status IN ('0','2','4','6')");
		}
		if (isNotEmpty(request.getParameter("id"))) {
			whereSql.append(" AND id = '" + request.getParameter("id") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNm"))) {
			whereSql.append(" AND legal_nm = '"
					+ request.getParameter("mchtNm") + "' ");
		}
		String order = "ORDER BY create_time";
		String sql = "SELECT a.ID,b.BRH_NAME,a.STATUS,a.MCHT_NM,a.LEGAL_NM,create_time FROM TBL_MCHT_NET_TMP a,TBL_BRH_INFO b "
				+ whereSql.toString() + order;
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_NET_TMP a,TBL_BRH_INFO b "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getMchtFeeInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// StringBuffer whereSql1 = new StringBuffer();
		whereSql.append("AND a.mcht_cd=b.MCHT_NO  ");
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND MCHT_CD = '"
					+ request.getParameter("mchntId") + "'");
		}
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "select a.mcht_cd, b.MCHT_NM,a.txn_num, a.card_type, a.day_num,a.day_amt, "
				+ "a.day_single,a.mon_num,a.mon_amt,a.sa_state,a.sa_action from cst_mcht_fee_inf_tmp a,TBL_MCHT_BASE_INF b WHERE b.MCHT_STATUS='0' and b.ACQ_INST_ID IN "
				+ operator.getBrhBelowId();
		// System.out.println(sql);
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户消费限额待审核表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtFeeInfCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// StringBuffer whereSql1 = new StringBuffer();
		whereSql.append("AND a.mcht_cd=b.MCHT_NO  ");
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND MCHT_CD = '"
					+ request.getParameter("mchntId") + "'");
		}
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "select a.mcht_cd, b.MCHT_NM,a.txn_num, a.card_type, a.day_num,a.day_amt, "
				+ "a.day_single,a.mon_num,a.mon_amt,a.sa_state,a.sa_action from cst_mcht_fee_inf_tmp a,TBL_MCHT_BASE_INF b WHERE a.sa_state in('0','3','4') and b.MCHT_STATUS='0' and b.ACQ_INST_ID IN "
				+ operator.getBrhBelowId();
		// System.out.println(sql);
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户分店查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getrecUpdTs(int begin, HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		if (isNotEmpty(request.getParameter("mchtCd"))) {
			whereSql += " AND MCHT_CD = '" + request.getParameter("mchtCd")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("branchCd"))) {
			whereSql += " AND BRANCH_CD = '" + request.getParameter("branchCd")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("branchNm"))) {
			whereSql += " AND BRANCH_NM like '%"
					+ request.getParameter("branchNm") + "%' ";
		}
		Object[] ret = new Object[2];
		String sql = "select  a.MCHT_CD,a.BRANCH_CD,a.BRANCH_NM,a.BRANCH_AREA,a.BRANCH_SVR_LVL,a.BRANCH_CONT_MAN,"
				+ "a.BRANCH_TEL,a.BRANCH_Fax,a.BRANCH_NM_EN,a.BRANCH_ADDR,a.CUST_MNGER,a.CUST_MOBILE,a.CUST_TEL,a.OPR_NM,"
				+ "a.SIGN_DATE from TBL_MCHT_BRAN_INF a"
				+ whereSql
				+ " order by a.rec_upd_ts";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BRAN_INF " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户分店查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntMccInfo(int begin, HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHNT_TP_GRP = '"
					+ request.getParameter("mchtGrp") + "' ";
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql += " AND MCHNT_TP = '" + request.getParameter("mcc")
					+ "' ";
		}

		Object[] ret = new Object[2];
		String sql = "SELECT MCHNT_TP,MCHNT_TP_GRP,DESCR,REC_ST FROM TBL_INF_MCHNT_TP"
				+ whereSql + " order by MCHNT_TP";
		String countSql = "SELECT COUNT(*) FROM TBL_INF_MCHNT_TP " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户分店临时表查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntMccInfomcc(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHNT_TP_GRP = '"
					+ request.getParameter("mchtGrp") + "' ";
		}
		if (isNotEmpty(request.getParameter("mcc"))) {
			whereSql += " AND MCHNT_TP = '" + request.getParameter("mcc")
					+ "' ";
		}

		Object[] ret = new Object[2];
		String sql = "SELECT MCHNT_TP,MCHNT_TP_GRP,DESCR,REC_ST,STATUSIDMCC FROM TBL_INF_MCHNT_TP_TMP"
				+ whereSql + " order by MCHNT_TP";
		String countSql = "SELECT COUNT(*) FROM TBL_INF_MCHNT_TP_TMP "
				+ whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 到真实表中查询正常商户的信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoTrue(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String sql = "SELECT MCHT_NO,MCHT_CN_ABBR,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,MCHT_STATUS FROM TBL_MCHT_BASE_INF WHERE ACQ_INST_ID IN "
				+ operator.getBrhBelowId()
				+ " AND MCHT_STATUS ='0' ORDER BY  MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF WHERE ACQ_INST_ID IN "
				+ operator.getBrhBelowId() + " AND MCHT_STATUS ='0'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfo(int begin, HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		}
		// else {
		// whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		// }
		whereSql += "AND MCHT_STATUS IN ('0','2','4','6') ";
		Object[] ret = new Object[2];

		// String sql =
		// "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
		// +
		// "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS FROM TBL_MCHT_BASE_INF_TMP "
		// + whereSql + " ORDER BY MCHT_NO";

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0) FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY  MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询商户信息sql==========" + sql);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户信息TMP1
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoTmp1(int begin,
			HttpServletRequest request) {

		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		}
		// else {
		// whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		// }
		whereSql += "AND MCHT_STATUS IN ('0', '1', '3', '5', '6', '7', '8', '9', 'I', 'J') ";
		Object[] ret = new Object[2];

		// String sql =
		// "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
		// +
		// "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS FROM TBL_MCHT_BASE_INF_TMP "
		// + whereSql + " ORDER BY MCHT_NO";

		String sql = "SELECT MCHT_NO,MCHT_NM,CUST_NO,RISK_GRADE,ENG_NAME,ROUTE_FLAG,MCC,LICENCE_NO,A.USC_CODE,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY APPLY_DATE desc";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户信息TMP
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoTmp(int begin, HttpServletRequest request) {

		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		}
		// else {
		// whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		// }
		whereSql += "AND MCHT_STATUS IN ('0', '1', '3', '5', '6', '7', '8', '9', 'I', 'J') ";
		Object[] ret = new Object[2];

		// String sql =
		// "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
		// +
		// "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS FROM TBL_MCHT_BASE_INF_TMP "
		// + whereSql + " ORDER BY MCHT_NO";

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,ROUTE_FLAG,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY APPLY_DATE desc";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoAll(int begin, HttpServletRequest request) {
		// String whereSql = " WHERE 1=1 and MCHT_STATUS<>'2' ";
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("crtDate"))) {
			whereSql += " AND SUBSTR(REC_CRT_TS,0,8) = '"
					+ request.getParameter("crtDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("updDate"))) {
			whereSql += " AND SUBSTR(REC_UPD_TS,0,8) = '"
					+ request.getParameter("updDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}
		if (isNotEmpty(request.getParameter("province"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE PROVINCE= '"
					+ request.getParameter("province") + "')";
		}
		if (isNotEmpty(request.getParameter("city"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE CITY= '"
					+ request.getParameter("city") + "')";
		}
		if (isNotEmpty(request.getParameter("area"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE AREA= '"
					+ request.getParameter("area") + "')";
		}
		if (isNotEmpty(request.getParameter("expander"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE EXPANDER like '%"
					+ request.getParameter("expander") + "%')";
		}

		if (isNotEmpty(request.getParameter("etpsAttr"))) {
			whereSql += " AND ETPS_ATTR = '" + request.getParameter("etpsAttr")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("discId"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_HIS_DISC_ALGO2_TMP WHERE FEE_TYPE ='"
					+ request.getParameter("discId") + "')";
		}
		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql += " AND AGENT_NO = '" + request.getParameter("agentNo")
					+ "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,ROUTE_FLAG,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,AUDIT_OPR_ID FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoQueryAre(int begin,
			HttpServletRequest request) {
		// String whereSql = " WHERE 1=1 and MCHT_STATUS<>'2' ";
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("crtDate"))
				&& isNotEmpty(request.getParameter("crtEndDate"))) {
			whereSql += " AND SUBSTR(REC_CRT_TS,0,8) between "
					+ request.getParameter("crtDate") + " and  "
					+ request.getParameter("crtEndDate");
		}
		if (isNotEmpty(request.getParameter("updDate"))
				&& isNotEmpty(request.getParameter("updEndDate"))) {
			whereSql += " AND SUBSTR(REC_UPD_TS,0,8) between "
					+ request.getParameter("updDate") + " and "
					+ request.getParameter("updEndDate");
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}
		if (isNotEmpty(request.getParameter("province"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE PROVINCE= '"
					+ request.getParameter("province") + "')";
		}
		if (isNotEmpty(request.getParameter("city"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE CITY= '"
					+ request.getParameter("city") + "')";
		}
		if (isNotEmpty(request.getParameter("area"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE AREA= '"
					+ request.getParameter("area") + "')";
		}
		if (isNotEmpty(request.getParameter("expander"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE EXPANDER like '%"
					+ request.getParameter("expander") + "%')";
		}

		if (isNotEmpty(request.getParameter("etpsAttr"))) {
			whereSql += " AND ETPS_ATTR = '" + request.getParameter("etpsAttr")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("discId"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_HIS_DISC_ALGO2_TMP WHERE FEE_TYPE ='"
					+ request.getParameter("discId") + "')";
		}
		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql += " AND AGENT_NO = '" + request.getParameter("agentNo")
					+ "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,ROUTE_FLAG,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,AUDIT_OPR_ID,RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询全部商户信息(不包括黑名单)
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoAll2(int begin,
			HttpServletRequest request) {
		// String whereSql = " WHERE 1=1 and MCHT_STATUS<>'2' ";
		String whereSql = " WHERE 1=1 and MCHT_STATUS<>'H'";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("crtDate"))) {
			whereSql += " AND SUBSTR(REC_CRT_TS,0,8) = '"
					+ request.getParameter("crtDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("updDate"))) {
			whereSql += " AND SUBSTR(REC_UPD_TS,0,8) = '"
					+ request.getParameter("updDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}
		if (isNotEmpty(request.getParameter("province"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE PROVINCE= '"
					+ request.getParameter("province") + "')";
		}
		if (isNotEmpty(request.getParameter("city"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE CITY= '"
					+ request.getParameter("city") + "')";
		}
		if (isNotEmpty(request.getParameter("area"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE AREA= '"
					+ request.getParameter("area") + "')";
		}
		if (isNotEmpty(request.getParameter("expander"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_MCHT_SUPP1_TMP WHERE EXPANDER like '%"
					+ request.getParameter("expander") + "%')";
		}

		if (isNotEmpty(request.getParameter("etpsAttr"))) {
			whereSql += " AND ETPS_ATTR = '" + request.getParameter("etpsAttr")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("discId"))) {
			whereSql += " AND MCHT_NO IN ( SELECT MCHT_NO FROM TBL_HIS_DISC_ALGO2_TMP WHERE FEE_TYPE ='"
					+ request.getParameter("discId") + "')";
		}
		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql += " AND AGENT_NO = '" + request.getParameter("agentNo")
					+ "' ";
		}
		Object[] ret = new Object[2];

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,ROUTE_FLAG,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,UPD_OPR_ID,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,AUDIT_OPR_ID FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) ORDER BY MCHT_NO";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询修改待审核商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo4Upd(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";

		whereSql += "AND MCHT_STATUS IN ('3','5','7','9') ";
		Object[] ret = new Object[2];

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,AUDIT_OPR_ID,RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) " + "ORDER BY APPLY_DATE desc";

		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询修改待复审商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo4Upd1(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1  ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		whereSql += "AND MCHT_STATUS IN ('J','K','L') ";
		Object[] ret = new Object[2];
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,AUDIT_OPR_ID,RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) " + "ORDER BY APPLY_DATE desc";

		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询新增待审核商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo4Add(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";

		whereSql += "AND MCHT_STATUS IN ('1') ";
		Object[] ret = new Object[2];

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		/*
		 * if (isNotEmpty(request.getParameter("mchtStatus"))) { whereSql +=
		 * " AND MCHT_STATUS = '" + request.getParameter("mchtStatus") + "' "; }
		 */
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) " + "ORDER BY APPLY_DATE desc";

		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询新增待复审商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo4Add1(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";

		whereSql += "AND MCHT_STATUS IN ('I') ";
		Object[] ret = new Object[2];

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		/*
		 * if (isNotEmpty(request.getParameter("mchtStatus"))) { whereSql +=
		 * " AND MCHT_STATUS = '" + request.getParameter("mchtStatus") + "' "; }
		 */
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2),AGENT_NO,RISL_LVL FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) " + "ORDER BY APPLY_DATE desc";

		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询待审核商户信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";

		whereSql += "AND MCHT_STATUS IN ('1','3','5','7','9') ";
		Object[] ret = new Object[2];

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("mchntId")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			whereSql += " AND MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHT_GRP = '" + request.getParameter("mchtGrp")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql += " AND APPLY_DATE >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql += " AND APPLY_DATE <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql += " AND ACQ_INST_ID = '" + request.getParameter("brhId")
					+ "' ";
		} else {
			whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() + " ";
		}

		String sql = "SELECT MCHT_NO,MCHT_NM,ENG_NAME,MCC,LICENCE_NO,ADDR,POST_CODE,"
				+ "COMM_EMAIL,MANAGER,CONTACT,COMM_TEL,APPLY_DATE,MCHT_STATUS,nvl(B.TERM_COUNT,0),CRT_OPR_ID,PART_NUM,"
				+ "SUBSTR(REC_UPD_TS,1,8)||' '||SUBSTR(REC_UPD_TS,9,2)||':'||SUBSTR(REC_UPD_TS,11,2)||':'||SUBSTR(REC_UPD_TS,13,2) FROM "
				+ "(SELECT * FROM TBL_MCHT_BASE_INF_TMP "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_CD,count(1) AS TERM_COUNT from TBL_TERM_INF group by MCHT_CD) B "
				+ "ON (A.MCHT_NO = B.MCHT_CD) " + "ORDER BY APPLY_DATE desc";

		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户操作日志查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntLogs(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String whereSql = " WHERE 1=1 ";
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		if (isNotEmpty(request.getParameter("mchntInd"))) {
			whereSql += " AND MCHNT_IND = '" + request.getParameter("mchntInd")
					+ "' ";
		} else {
			whereSql += " AND MCHNT_IND = '' ";
		}
		String sql = "SELECT OPR_ID,TXN_TIME,OPR_TYPE,OPR_STATUS,OPR_INFO FROM TBL_MCHNT_LOGS "
				+ whereSql + "ORDER BY TXN_TIME DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_LOGS " + whereSql;

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户操作日志查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getDefMchntFiles(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String whereSql = " WHERE module_code='2' ";

		if (isNotEmpty(request.getParameter("mchntInd"))) {
			whereSql += " AND tbl_key = '" + request.getParameter("mchntInd")
					+ "' ";
		} else {
			whereSql += " AND tbl_key = '' ";
		}
		String sql = "SELECT MODULE_CODE,TBL_KEY,MAT_TYPE,STORE_NAME,UPLOAD_NAME,FILE_SIZE,OPR_ID,CRT_DATE FROM (select row_number() over (partition by mat_type order by crt_date desc) LEV,tbl_def_file_inf.* from tbl_def_file_inf "
				+ whereSql + ") where LEV=1  order by crt_date desc ";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询新增待审核商户信息（进件系统）
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntCheckInfo4Def(int begin,
			HttpServletRequest request) {
		String whereSql = " WHERE 1=1 ";

		whereSql += "AND STATUS IN ('1') ";
		Object[] ret = new Object[2];

		if (isNotEmpty(request.getParameter("mchtNm"))) {
			whereSql += " AND MCHT_NAME like '%"
					+ request.getParameter("mchtNm") + "%' ";
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			whereSql += " AND MCHNT_TP_GRP = '"
					+ request.getParameter("mchtGrp") + "' ";
		}
		/*
		 * if (isNotEmpty(request.getParameter("brhId"))) { whereSql +=
		 * " AND ACQ_INST_ID = '" + request.getParameter("brhId") + "' "; } else
		 * { whereSql += " AND ACQ_INST_ID IN " + operator.getBrhBelowId() +
		 * " "; }
		 */

		if (isNotEmpty(request.getParameter("agentNo"))) {
			whereSql += " AND AGENT_NO = '" + request.getParameter("agentNo")
					+ "' ";
		}

		String sql = "SELECT REC_ID,MCHT_NAME,LEGAL_NAME,MCC,BUSI_LIC_NO,REG_DATE,STATUS,"
				+ "OPR_ID,AGENT_NO,nvl(B.TERM_COUNT,0) FROM"
				+ " (SELECT * FROM TBL_DEF_MCHT_INF "
				+ whereSql
				+ ") A "
				+ "left outer join "
				+ "(select MCHT_REC_ID,count(1) AS TERM_COUNT from TBL_DEF_TERM_INF group by MCHT_REC_ID) B "
				+ "ON (A.REC_ID = B.MCHT_REC_ID) " + "  ORDER BY CRT_DATE desc";

		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取商户下挂终端（进件审核）
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-29下午05:51:01
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntTermInfo4Def(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select REC_ID,STATUS,(PRO_CODE||'-'||CITY_CODE||'-'||COUNTRY||'-'||ADDRESS) as POSADDR,TERM_TYPE,TERM_BRAND,TERM_MODEL,SN,PHONE_NO,IMEI,"
				+ " PRO_CODE,CITY_CODE,COUNTRY,ADDRESS FROM TBL_DEF_TERM_INF WHERE MCHT_REC_ID = '"
				+ request.getParameter("mchntRecId").trim() + "' ";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		// 20120823改成分页查询
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端库存查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-6-9下午01:43:32
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermManagementInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termNo");
		String mchnNo = request.getParameter("mchnNo");
		String batchNo = request.getParameter("batchNo");
		String state = request.getParameter("state");
		String termIdId = request.getParameter("termIdId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String brhId = operator.getOprBrhId().substring(0, 2);

		StringBuffer sql = new StringBuffer(
				"select TERM_NO,STATE,MANUFATURER,PRODUCT_CD,TERMINAL_TYPE,TERM_TYPE,MECH_NO,BATCH_NO,STOR_OPR_ID,")
				.append("STOR_DATE,RECI_OPR_ID,RECI_DATE,BACK_OPR_ID,BANK_DATE,INVALID_OPR_ID,INVALID_DATE,SIGN_OPR_ID,SIGN_DATE,MISC,PIN_PAD from TBL_TERM_MANAGEMENT");
		StringBuffer where = new StringBuffer(" ");
		if ("99".equals(brhId))
			where = new StringBuffer(" where 1=1 ");
		else
			where = new StringBuffer(" where substr(TERM_NO,2,2)='" + brhId
					+ "' ");
		if (state != null && !state.trim().equals("")) {
			if (state.length() == 1)
				where.append("and STATE ='").append(state).append("'");
			if (state.length() >= 2)
				where.append("and STATE in (").append(state).append(")");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			where.append("and TERM_NO='").append(termNo).append("'");
		}
		if (mchnNo != null && !mchnNo.trim().equals("")) {
			where.append("and MECH_NO='").append(mchnNo).append("'");
		}
		if (batchNo != null && !batchNo.trim().equals("")) {
			where.append("and BATCH_NO='").append(batchNo).append("'");
		}
		if (termIdId != null && !termIdId.trim().equals("")) {
			where.append("and PRODUCT_CD='").append(termIdId).append("'");
		}
		if (startTime != null && !startTime.trim().equals("")) {
			where.append("and LAST_UPD_TS >=").append("to_date('")
					.append(startTime.substring(0, 10)).append("','yyyy-mm-dd")
					.append("')");
		}
		if (endTime != null && !endTime.trim().equals("")) {
			where.append("and LAST_UPD_TS <=").append("to_date('")
					.append(endTime.substring(0, 10).concat(" 23:59:59"))
					.append("','yyyy-mm-dd hh24:mi:ss").append("')");
		}
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_MANAGEMENT "
				+ where.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.append(where).toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得终端审核信息列表
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-18上午09:38:26
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermCheckInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT TERM_ID,MCHNT_NO,TERM_STA,IS_SUPP_IC,IS_SUPP_CREDIT,TERM_TYPE,CALL_TYPE,"
				+ "CALL_NO,BRH_ID,SEQUENCE_NO,KB_SEQUENCE_NO,V_TELLER,ENC_TYPE,BIND_NO FROM TBL_TERM_INF_TMP WHERE BRH_ID IN "
				+ operator.getBrhBelowId()
				+ " AND TERM_STA IN ('1','3','5','7') ORDER BY MCHNT_NO,TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP WHERE BRH_ID IN "
				+ operator.getBrhBelowId()
				+ " AND TERM_STA IN ('1','3','5','7')";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户退回/拒绝原因查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:50:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntBackRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT a.TXN_TIME,a.MCHNT_ID,b.MCHT_NM,a.BRH_ID,a.OPR_ID,a.REFUSE_TYPE,a.REFUSE_INFO FROM TBL_MCHNT_REFUSE a"
				+ " left join TBL_MCHT_BASE_INF_TMP b on a.MCHNT_ID = b.MCHT_NO WHERE a.BRH_ID IN "
				+ operator.getBrhBelowId() + " ORDER BY a.TXN_TIME DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_REFUSE WHERE BRH_ID IN "
				+ operator.getBrhBelowId();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端退回/拒绝原因查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午09:56:42
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermBackRefuseInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT TXN_TIME,TERM_ID,BRH_ID,OPR_ID,REFUSE_TYPE,REFUSE_INFO FROM TBL_TERM_REFUSE WHERE BRH_ID IN "
				+ operator.getBrhBelowId() + " ORDER BY TXN_TIME DESC";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_REFUSE WHERE BRH_ID IN "
				+ operator.getBrhBelowId();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取终端密钥申请信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-6上午10:52:10
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfo(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer(
				" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where  t3.state ='0')")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND substr(t1.REC_CRT_TS,0,8) >= ").append(
					startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND substr(t1.REC_CRT_TS,0,8) <= ").append(
					endDate);
		}
		if (state != null && !state.trim().equals("")) {
			if (state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}

		String sql = "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t1.PSAM_ID,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
				// +
				// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' and term_tp <> '1') t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"
				+ "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' ) t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"
				+ whereSql.toString() + " ORDER BY t1.term_id";
		// String countSql =
		// "SELECT COUNT(*) FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
		// + whereSql.toString();

		// String sql =
		// "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t1.PSAM_ID,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
		// +
		// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM tbl_term_inf_tmp t1 inner join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' and t2.batch_no = (select max(batch_no) from tbl_term_tmk_log where term_id_id = t2.term_id_id)"
		// + " left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
		// + whereSql.toString() + " ORDER BY t1.term_id";
		// String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		// List<Object[]> dataList = CommonFunction.getCommQueryDAO()
		// .findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);

		String distinctSql = " select max(batch_no), term_id_id from tbl_term_tmk_log group by term_id_id";
		List<Object[]> distnctList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(distinctSql);

		int removeCount = 0;
		for (int xi = 0; xi < dataList.size(); xi++) {
			String strTermId = (String) dataList.get(xi)[0];
			String strBatchNo = (String) dataList.get(xi)[6];
			for (int xx = 0; xx < distnctList.size(); xx++) {
				String stDisTermId = (String) distnctList.get(xx)[1];
				String stDisBatchNo = (String) distnctList.get(xx)[0];
				if (stDisTermId.trim().equals(strTermId.trim())) {
					if (!stDisBatchNo.trim().equals(strBatchNo.trim())) {
						dataList.remove(xi);
						removeCount++;
						xi--;
					}
				}
			}
		}

		List<Object[]> dataList1 = new ArrayList();
		int countBreak = 0;
		for (int gi = begin; gi < dataList.size(); gi++) {
			if ((begin + Constants.QUERY_RECORD_COUNT) <= dataList.size()) {
				if (countBreak > 15 - 1) {
					break;
				}
				dataList1.add(dataList.get(gi));
				countBreak++;
			}

			if ((begin + Constants.QUERY_RECORD_COUNT) > dataList.size()) {
				dataList1.add(dataList.get(gi));
			}
		}
		// String count =
		// CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		String count = String.valueOf(dataList.size());
		ret[0] = dataList1;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取终端密钥申请信息（带验证码）
	 * 
	 * @param begin
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfo2(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer(
		// " WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where  t3.state ='0')")
				" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where  t3.state ='0') and t2.state is not null ")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND substr(t1.REC_CRT_TS, 0, 8) >= ").append(
					startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND substr(t1.REC_CRT_TS, 0, 8) <= ").append(
					endDate);
		}
		if (state != null && !state.trim().equals("")) {
			if (state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}

		String sql = "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t1.PSAM_ID,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
				// +
				// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' and term_tp <> '1') t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"
				+ "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' ) t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"
				+ whereSql.toString() + " ORDER BY t1.term_id";
		// String countSql =
		// "SELECT COUNT(*) FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
		// + whereSql.toString();

		// String sql =
		// "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t1.PSAM_ID,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
		// +
		// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM tbl_term_inf_tmp t1 inner join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' and t2.batch_no = (select max(batch_no) from tbl_term_tmk_log where term_id_id = t2.term_id_id)"
		// + " left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
		// + whereSql.toString() + " ORDER BY t1.term_id";
		// String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		// List<Object[]> dataList = CommonFunction.getCommQueryDAO()
		// .findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);

		String distinctSql = " select max(batch_no), term_id_id from tbl_term_tmk_log group by term_id_id";
		List<Object[]> distnctList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(distinctSql);

		int removeCount = 0;
		for (int xi = 0; xi < dataList.size(); xi++) {
			String strTermId = (String) dataList.get(xi)[0];
			String strBatchNo = (String) dataList.get(xi)[6];
			for (int xx = 0; xx < distnctList.size(); xx++) {
				String stDisTermId = (String) distnctList.get(xx)[1];
				String stDisBatchNo = (String) distnctList.get(xx)[0];
				if (stDisTermId.trim().equals(strTermId.trim())) {
					if (!stDisBatchNo.trim().equals(strBatchNo.trim())) {
						dataList.remove(xi);
						removeCount++;
						xi--;
					}
				}
			}
		}

		List<Object[]> dataList1 = new ArrayList();
		int countBreak = 0;
		for (int gi = begin; gi < dataList.size(); gi++) {
			if ((begin + Constants.QUERY_RECORD_COUNT) <= dataList.size()) {
				if (countBreak > 15 - 1) {
					break;
				}
				dataList1.add(dataList.get(gi));
				countBreak++;
			}

			if ((begin + Constants.QUERY_RECORD_COUNT) > dataList.size()) {
				dataList1.add(dataList.get(gi));
			}
		}
		// String count =
		// CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		String count = String.valueOf(dataList.size());
		ret[0] = dataList1;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取终端密钥申请信息（不带验证码）
	 * 
	 * @param begin
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfo3(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer whereSql = new StringBuffer(
		// " WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where  t3.state ='0')")
				" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where  t3.state ='0') and t2.state is  null ")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId());
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND substr(t1.REC_CRT_TS, 0, 8) >= ").append(
					startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND substr(t1.REC_CRT_TS, 0, 8) <= ").append(
					endDate);
		}
		if (state != null && !state.trim().equals("")) {
			if (state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}

		String sql = "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t1.PSAM_ID,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
				// +
				// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' and term_tp <> '1') t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"
				+ "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM ((select * from TBL_MCHT_BASE_INF where MCHT_STATUS = '0') t3 left outer join (select * from tbl_term_inf_tmp where term_sta = '1' ) t1 on t1.mcht_cd = t3.MCHT_NO  left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0')"
				+ whereSql.toString() + " ORDER BY t1.term_id";
		// String countSql =
		// "SELECT COUNT(*) FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
		// + whereSql.toString();

		// String sql =
		// "SELECT t1.term_id,t1.mcht_cd||' - '||t3.MCHT_NM,t1.term_branch,t1.term_sta,t1.PSAM_ID,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
		// +
		// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM tbl_term_inf_tmp t1 inner join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0' and t2.batch_no = (select max(batch_no) from tbl_term_tmk_log where term_id_id = t2.term_id_id)"
		// + " left join TBL_MCHT_BASE_INF t3 on t1.mcht_cd = t3.MCHT_NO"
		// + whereSql.toString() + " ORDER BY t1.term_id";
		// String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		// List<Object[]> dataList = CommonFunction.getCommQueryDAO()
		// .findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);

		String distinctSql = " select max(batch_no), term_id_id from tbl_term_tmk_log group by term_id_id";
		List<Object[]> distnctList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(distinctSql);

		int removeCount = 0;
		for (int xi = 0; xi < dataList.size(); xi++) {
			String strTermId = (String) dataList.get(xi)[0];
			String strBatchNo = (String) dataList.get(xi)[6];
			for (int xx = 0; xx < distnctList.size(); xx++) {
				String stDisTermId = (String) distnctList.get(xx)[1];
				String stDisBatchNo = (String) distnctList.get(xx)[0];
				if (stDisTermId.trim().equals(strTermId.trim())) {
					if (!stDisBatchNo.trim().equals(strBatchNo.trim())) {
						dataList.remove(xi);
						removeCount++;
						xi--;
					}
				}
			}
		}

		List<Object[]> dataList1 = new ArrayList();
		int countBreak = 0;
		for (int gi = begin; gi < dataList.size(); gi++) {
			if ((begin + Constants.QUERY_RECORD_COUNT) <= dataList.size()) {
				if (countBreak > 15 - 1) {
					break;
				}
				dataList1.add(dataList.get(gi));
				countBreak++;
			}

			if ((begin + Constants.QUERY_RECORD_COUNT) > dataList.size()) {
				dataList1.add(dataList.get(gi));
			}
		}
		// String count =
		// CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		String count = String.valueOf(dataList.size());
		ret[0] = dataList1;
		ret[1] = count;
		return ret;
	}

	// @SuppressWarnings("unchecked")
	// public static Object[] getTermTmkInfo(int begin, HttpServletRequest
	// request) {
	// Operator operator = (Operator) request.getSession().getAttribute(
	// Constants.OPERATOR_INFO);
	// Object[] ret = new Object[2];
	// String termNo = request.getParameter("termId");
	// String mchtCd = request.getParameter("mchnNo");
	// String state = request.getParameter("state");
	// String termBranch = request.getParameter("termBranch");
	// StringBuffer whereSql = new StringBuffer("");
	// if(state != null && !state.trim().equals("") && state.equals("2"))
	// whereSql.append(" and t2.state != '1'");
	// whereSql.append(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')")
	// .append(" and t1.TERM_BRANCH in "+operator.getBrhBelowId());
	// if(mchtCd != null && !mchtCd.trim().equals(""))
	// whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
	// if(termBranch != null && !termBranch.trim().equals(""))
	// whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch).append("'");
	// if(termNo != null && !termNo.trim().equals(""))
	// whereSql.append(" AND t1.TERM_ID='").append(CommonFunction.fillString(termNo,
	// ' ', 12, true)).append("'");
	// if(state != null && !state.trim().equals("") && state.equals("1"))
	// whereSql.append(" AND t2.state = '1'");
	// String sql =
	// "SELECT t1.term_id,t1.mcht_cd,t1.term_branch,t1.term_sta,t2.state,t2.batch_no,t2.req_opr,t2.req_date,"
	// +
	// "t2.chk_opr,t2.chk_date,t1.rec_crt_ts FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
	// + whereSql.toString() + " ORDER BY t1.term_id";
	// String countSql =
	// "SELECT COUNT(*) FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
	// + whereSql.toString();
	//
	// List<Object[]> dataList = CommonFunction.getCommQueryDAO()
	// .findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
	// String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
	// countSql);
	// ret[0] = dataList;
	// ret[1] = count;
	// return ret;
	// }

	/**
	 * 获取终端密钥待审核信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-6上午10:52:10
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfoAll(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		// StringBuffer whereSql = new
		// StringBuffer(" WHERE trim(t1.term_id_id) in (select trim(term_id) from tbl_term_inf_tmp where term_tp <>'1') and t1.TERM_BRANCH in "
		// + operator.getBrhBelowId());
		StringBuffer whereSql = new StringBuffer(
				" WHERE trim(t1.term_id_id) in (select trim(term_id) from tbl_term_inf_tmp ) and t1.TERM_BRANCH in "
						+ operator.getBrhBelowId());
		Object[] ret = new Object[2];
		String batchNo = request.getParameter("batchNo");
		String termIdId = request.getParameter("termIdId").trim();
		String reqOpr = request.getParameter("reqOpr");
		String mchntNo = request.getParameter("mchntNo");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// 只显示“已申请”的状态 by Mike 2013-8-7
		if (state == "") {
			state = "0";
		}

		if (!StringUtil.isEmpty(mchntNo))
			whereSql.append(" AND t1.MCHN_NO='").append(mchntNo).append("'");
		if (!StringUtil.isEmpty(reqOpr))
			whereSql.append(" AND t1.REQ_OPR='").append(reqOpr).append("'");
		if (!StringUtil.isEmpty(termIdId))
			whereSql.append(" AND t1.TERM_ID_ID='").append(termIdId)
					.append("'");
		if (!StringUtil.isEmpty(batchNo))
			whereSql.append(" AND t1.BATCH_NO='").append(batchNo).append("'");
		if (!StringUtil.isEmpty(startDate))
			whereSql.append(" AND t1.REQ_DATE>=").append(startDate);
		if (!StringUtil.isEmpty(endDate))
			whereSql.append(" AND t1.REQ_DATE<=").append(endDate);
		if (!StringUtil.isEmpty(state))
			whereSql.append(" AND t1.STATE='").append(state).append("'");

		String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO||' - '||t2.MCHT_NM as MCHT_NO,t1.TERM_BRANCH,t1.STATE,t1.REQ_OPR,t1.REQ_DATE,"
				+ "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE,t1.PRT_COUNT from TBL_TERM_TMK_LOG t1"
				+ " left join TBL_MCHT_BASE_INF t2 on t2.MCHT_NO=t1.MCHN_NO "
				+ whereSql.toString()
				+ " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_TMK_LOG t1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取终端密钥待审核信息（不带验证）
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-6上午10:52:10
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfoAllNoCode(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		// StringBuffer whereSql = new
		// StringBuffer(" WHERE trim(t1.term_id_id) in (select trim(term_id) from tbl_term_inf_tmp where term_tp <>'1') and t1.TERM_BRANCH in "
		// + operator.getBrhBelowId());
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.misc = '1' and trim(t1.term_id_id) in (select trim(term_id) from tbl_term_inf_tmp ) and t1.TERM_BRANCH in "
						+ operator.getBrhBelowId());

		Object[] ret = new Object[2];
		String batchNo = request.getParameter("batchNo");
		String termIdId = request.getParameter("termIdId").trim();
		String reqOpr = request.getParameter("reqOpr");
		String mchntNo = request.getParameter("mchntNo");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// 只显示“已申请”的状态 by Mike 2013-8-7
		if (state == "") {
			state = "0";
		}

		if (!StringUtil.isEmpty(mchntNo))
			whereSql.append(" AND t1.MCHN_NO='").append(mchntNo).append("'");
		if (!StringUtil.isEmpty(reqOpr))
			whereSql.append(" AND t1.REQ_OPR='").append(reqOpr).append("'");
		if (!StringUtil.isEmpty(termIdId))
			whereSql.append(" AND t1.TERM_ID_ID='").append(termIdId)
					.append("'");
		if (!StringUtil.isEmpty(batchNo))
			whereSql.append(" AND t1.BATCH_NO='").append(batchNo).append("'");
		if (!StringUtil.isEmpty(startDate))
			whereSql.append(" AND t1.REQ_DATE>=").append(startDate);
		if (!StringUtil.isEmpty(endDate))
			whereSql.append(" AND t1.REQ_DATE<=").append(endDate);
		if (!StringUtil.isEmpty(state))
			whereSql.append(" AND t1.STATE='").append(state).append("'");

		String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO||' - '||t2.MCHT_NM as MCHT_NO,t1.TERM_BRANCH,t1.STATE,t1.REQ_OPR,t1.REQ_DATE,"
				+ "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE,t1.PRT_COUNT from TBL_TERM_TMK_LOG t1"
				+ " left join TBL_MCHT_BASE_INF t2 on t2.MCHT_NO=t1.MCHN_NO "
				+ whereSql.toString()
				+ " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_TMK_LOG t1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取终端密钥待审核信息(带验证)
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-6上午10:52:10
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermTmkInfoAllCode(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		// StringBuffer whereSql = new
		// StringBuffer(" WHERE trim(t1.term_id_id) in (select trim(term_id) from tbl_term_inf_tmp where term_tp <>'1') and t1.TERM_BRANCH in "
		// + operator.getBrhBelowId());
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.misc is null and trim(t1.term_id_id) in (select trim(term_id) from tbl_term_inf_tmp ) and t1.TERM_BRANCH in "
						+ operator.getBrhBelowId());
		Object[] ret = new Object[2];
		String batchNo = request.getParameter("batchNo");
		String termIdId = request.getParameter("termIdId").trim();
		String reqOpr = request.getParameter("reqOpr");
		String mchntNo = request.getParameter("mchntNo");
		String state = request.getParameter("state");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// 只显示“已申请”的状态 by Mike 2013-8-7
		if (state == "") {
			state = "0";
		}

		if (!StringUtil.isEmpty(mchntNo))
			whereSql.append(" AND t1.MCHN_NO='").append(mchntNo).append("'");
		if (!StringUtil.isEmpty(reqOpr))
			whereSql.append(" AND t1.REQ_OPR='").append(reqOpr).append("'");
		if (!StringUtil.isEmpty(termIdId))
			whereSql.append(" AND t1.TERM_ID_ID='").append(termIdId)
					.append("'");
		if (!StringUtil.isEmpty(batchNo))
			whereSql.append(" AND t1.BATCH_NO='").append(batchNo).append("'");
		if (!StringUtil.isEmpty(startDate))
			whereSql.append(" AND t1.REQ_DATE>=").append(startDate);
		if (!StringUtil.isEmpty(endDate))
			whereSql.append(" AND t1.REQ_DATE<=").append(endDate);
		if (!StringUtil.isEmpty(state))
			whereSql.append(" AND t1.STATE='").append(state).append("'");

		String sql = "select t1.BATCH_NO,t1.TERM_ID_ID,t1.MCHN_NO||' - '||t2.MCHT_NM as MCHT_NO,t1.TERM_BRANCH,t1.STATE,t1.REQ_OPR,t1.REQ_DATE,"
				+ "t1.CHK_OPR,t1.CHK_DATE,t1.MISC,t1.REC_UPD_OPR,t1.REC_UPD_TS,t1.PRT_OPR,t1.PRT_DATE,t1.PRT_COUNT from TBL_TERM_TMK_LOG t1"
				+ " left join TBL_MCHT_BASE_INF t2 on t2.MCHT_NO=t1.MCHN_NO "
				+ whereSql.toString()
				+ " ORDER BY t1.REQ_DATE DESC,t1.BATCH_NO";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_TMK_LOG t1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询机构下所有终端信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午10:25:00
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermInfoAll(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];

		String termSta = request.getParameter("termSta");
		String startTime = request.getParameter("startTime").split("T")[0]
				.replaceAll("-", "");
		String endTime = request.getParameter("endTime").split("T")[0]
				.replaceAll("-", "");
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String cityCode = request.getParameter("cityCode");
		// ADD BY张骏恺20140820 begin
		String termTpQ = request.getParameter("termTpQ");
		String termFactoryQ = request.getParameter("termFactoryQ");
		String connectModeQ = request.getParameter("connectModeQ");
		String propTpQ = request.getParameter("propTpQ");
		String CompQ = request.getParameter("CompQ");
		String tradeTp = request.getParameter("tradeTp");
		String termMccQ = request.getParameter("termMccQ");
		String chk_state = request.getParameter("chk_state");
		String UpdstartTime = null;
		if (request.getParameter("UpdstartTime") != null) {
			UpdstartTime = request.getParameter("UpdstartTime").split("T")[0]
					.replaceAll("-", "");
		}
		String UpdendTime = null;
		if (request.getParameter("UpdendTime") != null) {
			UpdendTime = request.getParameter("UpdendTime").split("T")[0]
					.replaceAll("-", "");
		}

		// ADD BY张骏恺20140820 end
		StringBuffer whereSql = new StringBuffer(" where t1.TERM_BRANCH in "
				+ operator.getBrhBelowId());
		// .append("  and  t1.TERM_STA<>'8' ");
		if (startTime != null && !startTime.trim().equals("")) {
			if (startTime.length() == 8)
				whereSql.append(" AND t1.REC_CRT_TS>='")
						.append(CommonFunction.fillString(startTime, '0', 14,
								true)).append("'");
		}
		if (endTime != null && !endTime.trim().equals("")) {
			if (endTime.length() == 8)
				whereSql.append(" AND t1.REC_CRT_TS<='").append(endTime)
						.append("235959'");
		}
		if (province != null && !province.trim().equals("")) {
			whereSql.append(" AND t1.PROVINCE='").append(province).append("'");
		}
		if (city != null && !city.trim().equals("")) {
			whereSql.append(" AND t1.CITY='").append(city).append("'");
		}
		if (area != null && !area.trim().equals("")) {
			whereSql.append(" AND t1.AREA='").append(area).append("'");
		}
		if (cityCode != null && !cityCode.trim().equals("")) {
			whereSql.append(" AND t1.CITYCODE='").append(cityCode).append("'");
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		// ADD BY张骏恺20140820 begin
		if (termTpQ != null && !termTpQ.trim().equals("")) {
			whereSql.append(" AND t1.TERM_TP='").append(termTpQ).append("'");
		}

		if (chk_state != null && !chk_state.trim().equals("")) {
			whereSql.append(" AND t1.chk_state='").append(chk_state)
					.append("'");
		}

		if (termFactoryQ != null && !termFactoryQ.trim().equals("")) {
			whereSql.append(" AND t1.TERM_FACTORY ='").append(termFactoryQ)
					.append("'");
		}
		if (connectModeQ != null && !connectModeQ.trim().equals("")) {
			whereSql.append(" AND t1.CONNECT_MODE='").append(connectModeQ)
					.append("'");
		}
		if (propTpQ != null && !propTpQ.trim().equals("")) {
			whereSql.append(" AND t1.PROP_TP='").append(propTpQ).append("'");
		}
		if (CompQ != null && !CompQ.trim().equals("")) {
			whereSql.append(
					" AND t1.term_id in ("
							+ "select term_id from tbl_term_inf_tmp "
							+ "where MCHT_CD IN (" + "select MCHT_NO "
							+ "FROM tbl_mcht_base_inf where ACQ_inst_id = '")
					.append(CompQ).append("'))");
		}
		if (tradeTp != null && !tradeTp.trim().equals("")) {
			// 获取所有term和term_para
			String str = "select term_id , term_para from TBL_TERM_INF_TMP";
			List<Object[]> list = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(str);
			List<String[]> tp = new ArrayList<String[]>();
			Iterator<Object[]> it = list.iterator();
			// 遍历集合
			while (it.hasNext()) {
				Object[] obj = it.next();
				String[] st = new String[2];
				// 将终端号和142-144
				st[0] = (String) obj[0];
				StringBuffer result = new StringBuffer();
				if ((String) obj[1] != null && !((String) obj[1]).equals("")) {
					for (char c : ((String) obj[1]).substring(142, 144)
							.toCharArray()) {
						// 排除空格
						if (c != (char) 32) {
							result.append(CommonFunction.fillString(Integer
									.toBinaryString(Integer.parseInt(
											String.valueOf(c), 16)), '0', 4,
									false));
						} else {
							result.append("0000");
						}
					}
				} else {
					result.append("00000000");
				}
				st[1] = result.toString();
				tp.add(st);
			}
			// 拼接终端号
			StringBuffer sb = new StringBuffer();
			if (tradeTp.trim().equals("0")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(0, 1).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if (tradeTp.trim().equals("1")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(1, 2).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if (tradeTp.trim().equals("2")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(2, 3).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if (tradeTp.trim().equals("3")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(3, 4).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if (tradeTp.trim().equals("4")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(4, 5).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if (tradeTp.trim().equals("5")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(5, 6).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}
			if (tradeTp.trim().equals("6")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(6, 7).equals("1")) {
						sb.append("'").append(tp.get(m)[0]).append("',");
					}
				}
			}
			if (tradeTp.trim().equals("7")) {
				for (int m = 0; m < tp.size(); m++) {
					if (tp.get(m)[1].substring(7, 8).equals("1")) {
						sb.append(tp.get(m)[0]).append(",");
					}
				}
			}

			// 凭借查询语句
			String query = sb.toString();
			if (query != null) {
				query = query.substring(0, query.length() - 1);
				whereSql.append("and t1.term_id in (").append(query)
						.append(")");
			} else {
				whereSql.append("and t1.term_id in (null)");
			}

		}
		if (termMccQ != null && !termMccQ.trim().equals("")) {
			whereSql.append(" AND t1.TERM_MCC='").append(termMccQ).append("'");
		}

		if (UpdstartTime != null && !UpdstartTime.trim().equals("")) {
			if (UpdstartTime.length() == 8)
				whereSql.append(" AND t1.REC_DEL_TS>='")
						.append(CommonFunction.fillString(UpdstartTime, '0',
								14, true)).append("'");
		}
		if (UpdendTime != null && !UpdendTime.trim().equals("")) {
			if (UpdendTime.length() == 8)
				whereSql.append(" AND t1.REC_DEL_TS<='").append(UpdendTime)
						.append("235959'");
		}

		// ADD BY张骏恺20140820 end
		/*
		 * if (termNo != null && !termNo.trim().equals("")) {
		 * whereSql.append(" AND t1.TERM_ID = '")
		 * .append(CommonFunction.fillString(termNo, ' ', 12, true))
		 * .append("'"); }
		 */
		// 终端号改为模糊搜索
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID like '%").append(termNo)
					.append("%'");
		}
		if (termSta != null && !termSta.trim().equals("")) {
			if (termSta.length() == 1)
				whereSql.append(" AND t1.TERM_STA='").append(termSta)
						.append("'");
			else {
				termSta = termSta.replaceAll("1", "','");
				whereSql.append(" AND t1.TERM_STA in ('").append(termSta)
						.append("')");
			}
		}
		String sql = "SELECT t1.TERM_ID,t1.MCHT_CD,t1.MCHT_CD||'-'||t2.MCHT_NM,t1.TERM_STA,t1.TERM_SIGN_STA,t1.TERM_ID_ID,t1.TERM_FACTORY,"
				+ "t1.TERM_MACH_TP,t1.TERM_VER,TERM_TP,t1.TERM_BRANCH,t1.TERM_INS,t1.REC_CRT_TS,"
				+ "t1.PROVINCE||'-'||s.VALUE,t1.CITY||'-'||ss.VALUE,t1.AREA||'-'||sss.VALUE,t1.CITYCODE||'-'||c.CITY_DES,t1.REC_CRT_OPR,t1.REC_DEL_TS,t1.REC_UPD_OPR,t1.REC_UPD_TS "
				+ "FROM TBL_TERM_INF_TMP t1 left join TBL_MCHT_BASE_INF t2 on t1.MCHT_CD = t2.MCHT_NO "
				+ "left join TBL_CITY_CODE c on t1.CITYCODE=c.CITY_CODE "
				+ "left join CST_SYS_PARAM s on t1.PROVINCE=s.KEY  and s.OWNER='PROVINCE' "
				+ "left join CST_SYS_PARAM ss on t1.CITY=ss.KEY and ss.OWNER='CITY' "
				+ "left join CST_SYS_PARAM sss on t1.AREA=sss.KEY and sss.OWNER='AREA' "
				+ whereSql.toString() + " ORDER BY t1.REC_CRT_TS desc";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF_TMP t1 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端信息查询（正式表）
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-8-3下午04:15:43
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermInfo(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String termNo = request.getParameter("termId");
		String mchtCd = request.getParameter("mchnNo");
		String termBranch = request.getParameter("termBranch");

		StringBuffer whereSql = new StringBuffer(" WHERE TERM_BRANCH in "
				+ operator.getBrhBelowId());
		if (startTime != null && !startTime.trim().equals("")) {
			whereSql.append(" AND REC_UPD_TS>").append(
					startTime.split("T")[0].replaceAll("-", ""));
		}
		if (endTime != null && !endTime.trim().equals("")) {
			whereSql.append(" AND REC_UPD_TS<").append(
					endTime.split("T")[0].replaceAll("-", ""));
		}
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND TERM_BRANCH='").append(termBranch)
					.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND TERM_ID='")
					.append(CommonFunction.fillString(termNo, ' ', 12, true))
					.append("'");
		}

		String sql = "SELECT TERM_ID,MCHT_CD,TERM_STA,TERM_SIGN_STA,TERM_ID_ID,TERM_FACTORY,TERM_MACH_TP,"
				+ "TERM_VER,TERM_TP,TERM_BRANCH,TERM_INS FROM TBL_TERM_INF "
				+ whereSql.toString() + " ORDER BY MCHT_CD,TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_INF "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询终端密钥信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-19上午11:20:41
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPosKeyInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT a.MCHNT_ID,a.TERM_ID,b.BRH_ID,POS_TMK,TMK_ST FROM TBL_TERM_KEY a,TBL_TERM_INF b "
				+ "WHERE a.TERM_ID  = b.TERM_ID AND b.TERM_ST = '0' AND b.BRH_ID IN "
				+ operator.getBrhBelowId() + " ORDER BY a.MCHNT_ID,a.TERM_ID";
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_KEY a,TBL_TERM_INF b "
				+ "WHERE a.TERM_ID  = b.TERM_ID AND b.TERM_ST = '0' AND b.BRH_ID IN "
				+ operator.getBrhBelowId();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 计算时间差
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	private static int daysBetween(String smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date sd = sdf.parse(sdf.format(sdf.parse(smdate)));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(sd);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 查询联机交易信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-27上午10:47:28
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getPosTxnInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String whereSql = "";
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql += " UPDT_DATE >= '" + request.getParameter("startDate")
					+ "000000' ";
		} else {
			whereSql += " UPDT_DATE>= '" + StringUtil.getcurrdate("yyyyMMdd")
					+ "000000' ";
		}

		boolean bb = true;
		// 判断开始时间，小于90天查当前表，超过90天查全部
		try {
			if (!StringUtil.isNull(request.getParameter("startDate"))) {
				if (daysBetween(request.getParameter("startDate"), new Date()) <= 90) {
					bb = true;
				} else {
					bb = false;
				}

			}
		} catch (ParseException e) {

			Log.log("日期转换错误");
		}

		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql += " AND substr(UPDT_DATE,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (!StringUtil.isNull(request.getParameter("txnNum"))
				&& !"0000".equals(request.getParameter("txnNum"))) {
			whereSql += " AND t.TXN_NUM = '" + request.getParameter("txnNum")
					+ "' ";
		}
		// 交易方式
		String transWay = request.getParameter("transWay");
		if (!StringUtil.isNull(transWay)) {
			if (transWay.equals("02")) { // 磁条卡
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) in ('02','90') AND substr(t.FLD_RESERVED,12,2) NOT IN ('51','52')";
			} else if (transWay.equals("05")) { // IC卡
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) in ('05','95')";
			} else if (transWay.equals("60")) { // fallback
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) in ('02','90') AND substr(t.FLD_RESERVED,12,2) IN ('51','52')";
			} else {
				whereSql += " AND substr(t.POS_ENTRY_MODE,1,2) = '" + transWay
						+ "'";
			}
		}
		// 受理机构
		if (!StringUtil.isNull(request.getParameter("brhId"))) {
			whereSql += " AND rtrim(t.RCVG_CODE) = '"
					+ request.getParameter("brhId").split("-")[0] + "' ";
		}
		// 商户号
		if (!StringUtil.isNull(request.getParameter("mchntNo"))) {
			whereSql += " AND CARD_ACCP_ID = '"
					+ request.getParameter("mchntNo") + "' ";
		}
		// 终端编号
		if (!StringUtil.isNull(request.getParameter("termId"))) {
			whereSql += " AND CARD_ACCP_TERM_ID = '"
					+ request.getParameter("termId") + "' ";
		}
		System.out.println("卡BIN	idbinStaNo:		 "
				+ request.getParameter("idbinStaNo"));
		// 卡BIN
		if (!StringUtil.isNull(request.getParameter("idbinStaNo"))) {
			int length = request.getParameter("idbinStaNo").trim().length();
			whereSql += " AND substr(trim(PAN),1," + length + ") = '"
					+ request.getParameter("idbinStaNo").trim() + "' ";
		}
		// 卡号
		if (!StringUtil.isNull(request.getParameter("pan"))) {
			whereSql += " AND rtrim(PAN) like '%"
					+ request.getParameter("pan").trim() + "%' ";
		}
		// 检索参考号
		if (!StringUtil.isNull(request.getParameter("retrivlRef"))) {
			whereSql += " AND rtrim(RETRIVL_REF) like '%"
					+ request.getParameter("retrivlRef").trim() + "%' ";
		}
		// 系统流水号
		if (!StringUtil.isNull(request.getParameter("sysSeqNum"))) {
			whereSql += " AND rtrim(SYS_SEQ_NUM) like '%"
					+ request.getParameter("sysSeqNum").trim() + "%' ";
		}
		// 应答码
		if (!StringUtil.isNull(request.getParameter("respCode"))) {
			whereSql += " AND RESP_CODE = '" + request.getParameter("respCode")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("transamtsmall"))) {
			// String transamtsmall1 =
			// Double.parseDouble(request.getParameter("transamtsmall"))* 100 +
			// "";
			// String transamtsmall2 = transamtsmall1.substring(0,
			// transamtsmall1.indexOf("."));
			String transamtsmall1 = request.getParameter("transamtsmall");
			/*
			 * if(transamtsmall1.indexOf(".") ==
			 * -1){//20120911解决输入为整数且超过5位时的转化问题 transamtsmall1 += "00"; }else{
			 * transamtsmall1 = transamtsmall1.replace(".",""); }
			 */
			transamtsmall1 = CommonFunction.transYuanToFen(transamtsmall1);
			String transamtsmall = CommonFunction.fillString(transamtsmall1,
					'0', 12, false);
			whereSql += " AND AMT_TRANS >= '" + transamtsmall + "'";
		}
		if (isNotEmpty(request.getParameter("transamtbig"))) {
			// String transamtbig1 =
			// Double.parseDouble(request.getParameter("transamtbig"))* 100 +
			// "";
			// String transamtbig2 = transamtbig1.substring(0,
			// transamtbig1.indexOf("."));
			String transamtbig1 = request.getParameter("transamtbig");
			/*
			 * if(transamtbig1.indexOf(".") == -1){//20120911解决输入为整数且超过5位时的转化问题
			 * transamtbig1 += "00"; }else{ transamtbig1 =
			 * transamtbig1.replace(".",""); }
			 */
			transamtbig1 = CommonFunction.transYuanToFen(transamtbig1);
			String transamtbig = CommonFunction.fillString(transamtbig1, '0',
					12, false);
			whereSql += " AND AMT_TRANS<= '" + transamtbig + "'";
		}
		// 过滤掉签到信息
		StringBuffer sb = new StringBuffer();
		// 90天以内
		if (bb) {
			sb.append("select substr(UPDT_DATE,1,8) as inst_date,"
					+ "substr(UPDT_DATE,9,6) as inst_time,"
					+ "SYS_SEQ_NUM,"
					+ "substr(trim(PAN),1,6)||'****'||substr(trim(pan),-4,4),"
					+ "CARD_ACCP_ID,"
					+ "CARD_ACCP_NAME||'-'||CARD_ACCP_ID,"
					+ "CARD_ACCP_TERM_ID,"
					+ "RETRIVL_REF,"
					+ "AMT_TRANS,"
					+ "ACQ_INST_ID_CODE,"
					+ "RCVG_CODE,"
					+ "t.txn_num||'-'||name.TXN_NAME,"
					+ "(SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
					+ "(substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay,"
					+ "RESP_CODE,"
					+ "tt.TERM_BATCH_NM ,"
					+ " trim(order_no),"
					+ " decode(trans_State,'0','失败','1','成功','2','失败','3','失败','4','失败','5','失败','6','失败','7','失败','8','失败','R','成功',trans_State),"
					+ " substr(t.misc_2,71,15) ,t.ACCT_ID1_LEN "
					+ "from tbl_n_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) "
					+ "left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID  "
					+ "left outer join tbl_risk_inf r on trim(r.sa_model_kind) = trim(t.order_no) "
					+ " where");
			sb.append(whereSql);
			sb.append(" ORDER BY UPDT_DATE DESC");

		} else {
			// 90天以外
			sb.append("select inst_date,inst_time,SYS_SEQ_NUM,pan,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,ACQ_INST_ID_CODE,RCVG_CODE,txn,CARD_TP,transWay,RESP_CODE,TERM_BATCH_NM,order_no,trans_State,misc,ACCT_ID1_LEN from("
					+ " select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,SYS_SEQ_NUM,substr(trim(PAN),1,6)||'****'||substr(trim(pan),-4,4)as pan,"
					+ " CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,ACQ_INST_ID_CODE,RCVG_CODE,t.txn_num||'-'||name.TXN_NAME as txn,"
					+ " (SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
					+ " (substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay,RESP_CODE ,tt.TERM_BATCH_NM,trim(order_no)as order_no,"
					+ " decode(trans_State,'0','失败','1','成功','2','失败','3','失败','4','失败','5','失败','6','失败','7','失败','8','失败','R','成功',trans_State)as trans_State,"
					+ " substr(t.misc_2,71,15) as misc,t.UPDT_DATE , t.ACCT_ID1_LEN as ACCT_ID1_LEN  from tbl_n_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID"
					+ " left outer join tbl_risk_inf r on trim(r.sa_model_kind) = trim(t.order_no) where "
					+ whereSql
					+ " union all "
					+ " select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,SYS_SEQ_NUM,substr(trim(PAN),1,6)||'****'||substr(trim(pan),-4,4)as pan,"
					+ " CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,ACQ_INST_ID_CODE,RCVG_CODE,t.txn_num||'-'||name.TXN_NAME as txn,"
					+ " (SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
					+ " (substr(t.POS_ENTRY_MODE,1,2)||'-'||substr(t.FLD_RESERVED,12,2)) as transWay,RESP_CODE,tt.TERM_BATCH_NM,trim(order_no)as order_no,"
					+ " decode(trans_State,'0','失败','1','成功','2','失败','3','失败','4','失败','5','失败','6','失败','7','失败','8','失败','R','成功',trans_State)as trans_State ,"
					+ " substr(t.misc_2,71,15) as misc,t.UPDT_DATE , t.ACCT_ID1_LEN as ACCT_ID1_LEN from tbl_n_txn_his t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) left outer join tbl_term_inf tt on tt.term_id = t.CARD_ACCP_TERM_ID"
					+ " left outer join tbl_risk_inf r on trim(r.sa_model_kind) = trim(t.order_no) where "
					+ whereSql + " )ORDER BY UPDT_DATE DESC");

		}
		// 根据终端号前两位定义受理机构,总行不受控制
		/*
		 * String brhId = ""; if
		 * (!StringUtil.isNull(request.getParameter("brhId"))) { brhId =
		 * request.getParameter("brhId").trim(); } else { brhId =
		 * operator.getOprBrhId(); } if (!"9900".equals(brhId)) {
		 * sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '" +
		 * brhId.substring(0, 2) + "'"); }
		 */

		String countSql = "SELECT COUNT(*) FROM (select  distinct * from (("
				+ sb.toString() + ") t))";
		// 卡类型
		if (!StringUtil.isNull(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			countSql = "select count(*) from (" + sb.toString()
					+ ") x where card_tp = '"
					+ request.getParameter("cardType") + "' ";
		} /*
		 * else { //countSql += " WHERE " + whereSql; }
		 */
		// ADD
		String sql = "";
		// 卡类型
		if (!StringUtil.isNull(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			sql = "select distinct * from (" + sb.toString()
					+ ") x where card_tp = '"
					+ request.getParameter("cardType") + "' ";
		} else {
			sql = "select distinct * from ( " + sb.toString() + " )";
		}

		int pageSize = Constants.QUERY_RECORD_COUNT;
		if (!StringUtil.isNull(request.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, pageSize);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			data[8] = CommonFunction.transFenToYuan(data[8].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 脱机交易查询-电子现金交易
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getTxnInfoOutline(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String whereSql = "";
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql += " UPDT_DATE >= '" + request.getParameter("startDate")
					+ "000000' ";
		} else {
			whereSql += " UPDT_DATE>= '" + StringUtil.getcurrdate("yyyyMMdd")
					+ "000000' ";
		}

		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql += " AND substr(UPDT_DATE,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		// 交易类型
		if (!StringUtil.isNull(request.getParameter("txnNum"))) {
			whereSql += " AND t.TXN_NUM = '" + request.getParameter("txnNum")
					+ "' ";
		}
		// 商户号
		if (!StringUtil.isNull(request.getParameter("mchntNo"))) {
			whereSql += " AND CARD_ACCP_ID = '"
					+ request.getParameter("mchntNo") + "' ";
		}
		// 终端编号
		if (!StringUtil.isNull(request.getParameter("termId"))) {
			whereSql += " AND CARD_ACCP_TERM_ID = '"
					+ request.getParameter("termId") + "' ";
		}
		// 卡号
		if (!StringUtil.isNull(request.getParameter("pan"))) {
			whereSql += " AND rtrim(PAN) like '%"
					+ request.getParameter("pan").trim() + "%' ";
		}
		// 受理通道
		if (!StringUtil.isNull(request.getParameter("brhId"))) {
			whereSql += " AND rtrim(t.RCVG_CODE) = '"
					+ request.getParameter("brhId").split("-")[0] + "' ";
		}
		// 交易最小金额
		if (isNotEmpty(request.getParameter("transamtsmall"))) {
			String transamtsmall1 = request.getParameter("transamtsmall");
			transamtsmall1 = CommonFunction.transYuanToFen(transamtsmall1);
			String transamtsmall = CommonFunction.fillString(transamtsmall1,
					'0', 12, false);
			whereSql += " AND AMT_TRANS >= '" + transamtsmall + "'";
		}
		// 交易最大金额
		if (isNotEmpty(request.getParameter("transamtbig"))) {
			String transamtbig1 = request.getParameter("transamtbig");
			transamtbig1 = CommonFunction.transYuanToFen(transamtbig1);
			String transamtbig = CommonFunction.fillString(transamtbig1, '0',
					12, false);
			whereSql += " AND AMT_TRANS<= '" + transamtbig + "'";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,"
				+ "RCVG_CODE,FWD_INST_ID_CODE ||'-'|| (SELECT CARD_DIS FROM tbl_bank_bin_inf b WHERE FWD_INST_ID_CODE = INS_ID_CD and rownum <= 1) as CARD_DIS,"
				+ "(SELECT CARD_TP FROM tbl_bank_bin_inf b WHERE substr(PAN,1,length(rtrim(b.BIN_STA_NO))) = rtrim(b.BIN_STA_NO) and rownum <= 1) as CARD_TP,"
				+ "PAN,name.TXN_NAME,substr(t.FLD_RESERVED,3,6) as txnBatchNo,TERM_SSN,AMT_TRANS,"
				+ "case when instr(emv_val,'9F2608') !=0 then substr(emv_val,instr(emv_val,'9F2608')+6,16) end as icCert,"
				+ "case when instr(emv_val,'9505') !=0 then substr(emv_val,instr(emv_val,'9505')+4,10) end as tvr,'-' as tsi,"
				+ "case when (instr(emv_val,'9F0902',100)-instr(emv_val,'9F1E08',100))>26 then substr(emv_val,instr(emv_val,'9F1E08',100)+26,instr(emv_val,'9F0902',100)-instr(emv_val,'9F1E08',100)-26) end as aid,"
				+ "case when instr(emv_val,'9F3602') !=0 then substr(emv_val,instr(emv_val,'9F3602')+6,4) end as atc,'-' as appTag,'-' as appOreName,RESP_CODE "
				+ "from tbl_t_txn t left outer join tbl_txn_name name on (t.txn_num = name.txn_num) where");

		sb.append(whereSql);
		sb.append(" ORDER BY UPDT_DATE DESC");

		String countSql = "SELECT COUNT(*) FROM tbl_t_txn t";
		// 卡类型
		if (!StringUtil.isNull(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			countSql = "select count(*) from (" + sb.toString()
					+ ") x where card_tp = '"
					+ request.getParameter("cardType") + "' ";
		} else {
			countSql += " WHERE " + whereSql;
		}

		String sql = "";
		// 卡类型
		if (!StringUtil.isNull(request.getParameter("cardType"))
				&& !request.getParameter("cardType").equals("*")) {
			sql = "select * from (" + sb.toString() + ") x where card_tp = '"
					+ request.getParameter("cardType") + "' ";
		} else {
			sql = sb.toString();
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			data[12] = CommonFunction.transFenToYuan(data[12].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getPosTxnInfoOutline(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String whereSql = "";
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql += "AND substr(UPDT_DATE,1,8) >= '"
					+ request.getParameter("startDate") + "' ";
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql += "AND substr(UPDT_DATE,1,8) <= '"
					+ request.getParameter("endDate") + "' ";
		}
		if (!StringUtil.isNull(request.getParameter("txnNum"))
				&& !"0000".equals(request.getParameter("txnNum"))) {
			whereSql += "AND t.TXN_NUM = '" + request.getParameter("txnNum")
					+ "' ";
		}
		if (!StringUtil.isNull(request.getParameter("mchntNo"))) {
			whereSql += "AND CARD_ACCP_ID = '"
					+ request.getParameter("mchntNo") + "' ";
		}
		// 终端编号
		if (!StringUtil.isNull(request.getParameter("termId"))) {
			whereSql += " AND CARD_ACCP_TERM_ID = '"
					+ request.getParameter("termId") + "' ";
		}
		// 卡号
		if (!StringUtil.isNull(request.getParameter("pan"))) {
			whereSql += " AND rtrim(PAN) like '%"
					+ request.getParameter("pan").trim() + "%' ";
		}
		// 检索参考号
		if (!StringUtil.isNull(request.getParameter("retrivlRef"))) {
			whereSql += " AND rtrim(RETRIVL_REF) like '%"
					+ request.getParameter("retrivlRef").trim() + "%' ";
		}
		// 应答码
		if (!StringUtil.isNull(request.getParameter("respCode"))) {
			whereSql += " AND RESP_CODE = '" + request.getParameter("respCode")
					+ "' ";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
				+ "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_NAME,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
				+ "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE from tbl_t_txn t left outer join "
				+ "tbl_txn_name name on (t.txn_num = name.txn_num) where 1 = 1 ");

		// 根据终端号前两位定义受理机构,总行不受控制
		String brhId = "";
		if (!StringUtil.isNull(request.getParameter("brhId"))) {
			brhId = request.getParameter("brhId").trim();
		} else {
			brhId = operator.getOprBrhId();
		}
		if (!"9900".equals(brhId)) {
			sb.append(" and substr(CARD_ACCP_TERM_ID,1,2) = '"
					+ brhId.substring(0, 2) + "'");
		}

		sb.append(whereSql);
		sb.append(" ORDER BY UPDT_DATE DESC");

		String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sb.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			data[8] = CommonFunction.transFenToYuan(data[8].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;

		// Object[] ret = new Object[2];
		// Operator operator = (Operator) request.getSession().getAttribute(
		// Constants.OPERATOR_INFO);
		//
		// String whereSql = "";
		// if (!StringUtil.isNull(request.getParameter("startDate"))) {
		// whereSql += "AND substr(UPDT_DATE,1,8) >= '"
		// + request.getParameter("startDate") + "' ";
		// }
		// if (!StringUtil.isNull(request.getParameter("endDate"))) {
		// whereSql += "AND substr(UPDT_DATE,1,8) <= '"
		// + request.getParameter("endDate") + "' ";
		// }
		// if (!StringUtil.isNull(request.getParameter("txnNum"))
		// && !"0000".equals(request.getParameter("txnNum"))) {
		// whereSql += "AND t.TXN_NUM = '" + request.getParameter("txnNum")
		// + "' ";
		// }
		// if (!StringUtil.isNull(request.getParameter("mchntNo"))) {
		// whereSql += "AND CARD_ACCP_ID = '"
		// + request.getParameter("mchntNo") + "' ";
		// }
		// if (!StringUtil.isNull(request.getParameter("brhId"))) {
		// whereSql += "AND ACQ_INST_ID_CODE in "
		// + InformationUtil.getCupBrhGroupString(request
		// .getParameter("brhId")) + " ";
		// }
		//
		// StringBuffer sb = new StringBuffer();
		// sb
		// .append("select substr(UPDT_DATE,1,8) as inst_date,substr(UPDT_DATE,9,6) as inst_time,"
		// +
		// "SYS_SEQ_NUM,PAN,CARD_ACCP_ID,CARD_ACCP_TERM_ID,RETRIVL_REF,AMT_TRANS,"
		// +
		// "ACQ_INST_ID_CODE,name.TXN_NAME,RESP_CODE from tbl_t_txn t left outer join "
		// + "tbl_txn_name name on (t.txn_num = name.txn_num) where 1 = 1 ");
		// sb.append(whereSql);
		// sb.append(" ORDER BY UPDT_DATE DESC");
		//
		// String countSql = "SELECT COUNT(*) FROM (" + sb.toString() + ")";
		//
		// List<Object[]> dataList = CommonFunction.getCommQueryDAO()
		// .findBySQLQuery(sb.toString(), begin,
		// Constants.QUERY_RECORD_COUNT);
		// Object[] data;
		// for (int i = 0; i < dataList.size(); i++) {
		// data = dataList.get(i);
		// data[7] = CommonFunction.transFenToYuan(data[7].toString());
		// dataList.set(i, data);
		// }
		// String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
		// countSql);
		// ret[0] = dataList;
		// ret[1] = count;
		// return ret;
	}

	/**
	 * 查询历史风险记录
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHistoryRiskRecords(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT SA_TXN_CARD,SA_MCHT_NO,SA_TERM_NO,SA_TXN_NUM,"
				+ "(case SA_TXN_AMT when null then 0 when ' ' then 0 else trim(SA_TXN_AMT)*1/100 end),SA_TXN_TIME"
				+ " FROM TBL_CLC_MON WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
				+ " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF))";
		String countSql = "SELECT COUNT(*) FROM TBL_CLC_MON WHERE (SA_TXN_CARD IN (SELECT SA_CARD_NO FROM TBL_CTL_CARD_INF) OR"
				+ " SA_MCHT_NO IN (SELECT SA_MER_NO FROM TBL_CTL_MCHT_INF))";

		String whereSql = "";
		String date = null;
		if (isNotEmpty(request.getParameter("startDate"))) {
			date = request.getParameter("startDate") + "000000";
			whereSql += " and SA_TXN_DATE >= '"
					+ date.substring(4, date.length()) + "'";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			date = request.getParameter("endDate") + "2359559";
			whereSql += " and SA_TXN_DATE <= '"
					+ date.substring(4, date.length()) + "'";
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			date = request.getParameter("srBrhNo");
			whereSql += " and SA_OPEN_INST = '" + date + "' ";
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql, begin,
						Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[4] = CommonFunction.transFenToYuan(data[4].toString());
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询卡黑名单历史交易
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-24下午04:47:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCardRiskHistory(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("srCardNo"))) {
			whereSql.append(" AND trim(sa_txn_card) = '"
					+ request.getParameter("srCardNo").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			whereSql.append(" AND trim(SA_OPEN_INST) = '"
					+ request.getParameter("srBrhNo").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND trim(sa_txn_date) >= '"
					+ request.getParameter("startDate").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND trim(sa_txn_date) <= '"
					+ request.getParameter("endDate").trim() + "' ");
		}

		String sql = "select sa_txn_card,sa_mcht_no,sa_term_no,txn_name,"
				+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
				+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG "
				+ "from tbl_clc_mon,tbl_txn_name "
				+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '受控卡单笔交易额超限' ";
		sql += whereSql.toString();
		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

		String countSql = "select count(*) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户黑名单历史交易
	 * 
	 * @param begin
	 * @param request
	 * @return 2010-8-24下午04:47:34
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchtRiskHistory(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("srMerNo"))) {
			whereSql.append(" AND trim(sa_mcht_no) = '"
					+ request.getParameter("srMerNo").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			whereSql.append(" AND trim(SA_OPEN_INST) = '"
					+ request.getParameter("srBrhNo").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND trim(sa_txn_date) >= '"
					+ request.getParameter("startDate").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND trim(sa_txn_date) <= '"
					+ request.getParameter("endDate").trim() + "' ");
		}

		String sql = "select sa_txn_card,sa_mcht_no,sa_term_no,txn_name,"
				+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = ' ' then 0 else sa_txn_amt*1/100 end),"
				+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG "
				+ "from tbl_clc_mon,tbl_txn_name "
				+ "where sa_txn_num = txn_num and trim(SA_CLC_RSN1) = '商户单笔交易额超限' ";
		sql += whereSql.toString();
		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

		String countSql = "select count(*) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 风险规则查询
	 * 
	 * 
	 */

	@SuppressWarnings("unchecked")
	public static Object[] getRiskInfo(int begin, HttpServletRequest request) {

		Object[] ret = new Object[2];
		// Operator operator = (Operator) request.getSession().getAttribute(
		// Constants.OPERATOR_INFO);

		String whereString = " where  1=1 ";

		if (isNotEmpty(request.getParameter("saModelKind"))) {
			String saModelKind = request.getParameter("saModelKind");
			whereString += " and SA_MODEL_KIND like '%" + saModelKind + "%'";
		}
		if (isNotEmpty(request.getParameter("saModelName"))) {
			String saModelName = request.getParameter("saModelName");
			whereString += " and sa_model_name like '%" + saModelName + "%'";
		}

		String sql = "SELECT SA_MODEL_KIND,sa_model_name,SA_LIMIT_NUM,SA_LIMIT_AMOUNT/100,SA_BE_USE,SA_ACTION"
				+ " FROM TBL_RISK_INF  "
				+ whereString
				+ " order by SA_MODEL_KIND";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[2] = CommonFunction.transFenToYuan(data[2].toString());
			dataList.set(i, data);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;

	}

	/**
	 * 获得监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskModelInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT SA_MODEL_KIND,SA_BRANCH_CODE,SA_DAYS,SA_LIMIT_NUM,SA_LIMIT_AMOUNT,SA_BE_USE,SA_ACTION,SA_STATE,MODI_ZONE_NO,MODI_OPR_ID,MODI_TIME"
				+ ",SA_DAYS_MODIFY,SA_LIMIT_NUM_MODIFY,SA_LIMIT_AMOUNT_MODIFY,SA_BE_USE_MODIFY,SA_ACTION_MODIFY FROM TBL_RISK_INF WHERE SA_BRANCH_CODE='"
				+ operator.getOprBrhId()
				+ "'AND SUBSTR(SA_MODEL_KIND,1,1) != 'R' order by SA_MODEL_KIND";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[2] = CommonFunction.transFenToYuan(data[2].toString());
			dataList.set(i, data);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得待审核的监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getToCheckRiskModelInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT SA_MODEL_KIND,SA_BRANCH_CODE,SA_DAYS,SA_LIMIT_NUM,SA_LIMIT_AMOUNT,SA_BE_USE,SA_ACTION,SA_STATE,MODI_ZONE_NO,MODI_OPR_ID,MODI_TIME "
				+ ",SA_DAYS_MODIFY,SA_LIMIT_NUM_MODIFY,SA_LIMIT_AMOUNT_MODIFY,SA_BE_USE_MODIFY,SA_ACTION_MODIFY FROM TBL_RISK_INF WHERE SA_BRANCH_CODE='"
				+ operator.getOprBrhId()
				+ "'AND SUBSTR(SA_MODEL_KIND,1,1) != 'R' AND SA_STATE='3' order by SA_MODEL_KIND";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[2] = CommonFunction.transFenToYuan(data[2].toString());
			dataList.set(i, data);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得监控模型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRRiskModelInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		String sql = "SELECT SA_MODEL_KIND,SA_LIMIT_AMOUNT,SA_BE_USE,SA_ACTION, MODI_ZONE_NO,MODI_OPR_ID,MODI_TIME "
				+ ",SA_DAYS_MODIFY,SA_LIMIT_NUM_MODIFY,SA_LIMIT_AMOUNT_MODIFY,SA_BE_USE_MODIFY,SA_ACTION_MODIFY FROM TBL_RISK_INF WHERE SA_BRANCH_CODE='"
				+ operator.getOprBrhId()
				+ "'AND SUBSTR(SA_MODEL_KIND,1,1) = 'R' order by SA_MODEL_KIND";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			// data[2] = CommonFunction.transFenToYuan(data[2].toString());
			dataList.set(i, data);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获得监控模型修改记录
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getRiskModelInfoUpdLog(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator) request.getSession().getAttribute(
		// Constants.OPERATOR_INFO);
		String sql = "SELECT b.SA_MODEL_KIND,a.SA_BRANCH_CODE,b.SA_FIELD_NAME,b.SA_FIELD_VALUE_BF,b.SA_FIELD_VALUE,b.MODI_OPR_ID,b.MODI_TIME"
				+ " FROM TBL_RISK_INF_UPD_LOG b,TBL_RISK_INF a WHERE a.SA_MODEL_KIND = b.SA_MODEL_KIND";
		String countSql = "SELECT COUNT(*) FROM TBL_RISK_INF_UPD_LOG b,TBL_RISK_INF a WHERE a.SA_MODEL_KIND = b.SA_MODEL_KIND";

		String whereSql = "";

		String date = null;
		if (isNotEmpty(request.getParameter("startDate"))) {
			date = request.getParameter("startDate") + "000000";
			// whereSql += " and b.MODI_TIME >= '"
			// + date.substring(4, date.length()) + "'";
			whereSql += " and b.MODI_TIME >= '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			date = request.getParameter("endDate") + "2359559";
			// whereSql += " and b.MODI_TIME <= '"
			// + date.substring(4, date.length()) + "'";
			whereSql += " and b.MODI_TIME <= '" + date + "'";
		}
		if (isNotEmpty(request.getParameter("srBrhNo"))) {
			date = request.getParameter("srBrhNo");
			whereSql += " and a.SA_BRANCH_CODE = '" + date + "' ";
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql + whereSql, begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql + whereSql);

		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银联卡信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBankBinInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("insIdCd"))) {
			whereSql.append(" and INS_ID_CD = '"
					+ request.getParameter("insIdCd").trim() + "'");
		}

		if (!StringUtil.isNull(request.getParameter("binStaNo"))) {
			whereSql.append(" and BIN_STA_NO = '"
					+ request.getParameter("binStaNo").trim() + "'");
		}

		if (!StringUtil.isNull(request.getParameter("cardType"))) {
			if (!"*".equals(request.getParameter("cardType").trim())) {// 20121026
				whereSql.append(" and CARD_TP = '"
						+ request.getParameter("cardType").trim() + "'");
			}
		}

		String sql = "SELECT IND,INS_ID_CD,CARD_DIS,CARD_TP,ACC1_OFFSET,ACC1_LEN,ACC1_TNUM,"
				+ "BIN_OFFSET,BIN_LEN,BIN_STA_NO,BIN_END_NO,BIN_TNUM FROM TBL_BANK_BIN_INF where 1 = 1 ";
		sql += whereSql.toString();
		sql += " ORDER BY INS_ID_CD";

		String countSql = "select count(1) from TBL_BANK_BIN_INF where 1=1 "
				+ whereSql.toString() + "";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询节假日信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHolidays(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("unionFlag"))) {
			whereSql.append(" and UNION_FLAG= '"
					+ request.getParameter("unionFlag").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("holidayStart"))) {
			whereSql.append(" and HOLIDAY_START>= '"
					+ request.getParameter("holidayStart").trim().split("T")[0]
							.replace("-", "") + "'");
		}
		if (!StringUtil.isNull(request.getParameter("holidayEnd"))) {
			whereSql.append(" and HOLIDAY_END<= '"
					+ request.getParameter("holidayEnd").trim().split("T")[0]
							.replace("-", "") + "'");
		}
		if (!StringUtil.isNull(request.getParameter("name"))) {
			whereSql.append(" and NAME like '%"
					+ request.getParameter("name").trim() + "%'");
		}

		String sql = "SELECT ID,HOLIDAY_START,HOLIDAY_END,NAME,UNION_FLAG,CREATE_OPR_ID,CREATE_TIME,UPD_OPR_ID,UPD_TIME,SA_STATE "
				+ "FROM TBL_HOLIDAYS where 1=1 "
				+ whereSql.toString()
				+ " ORDER BY HOLIDAY_START";

		String countSql = "select count(1) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询门店
	 */
	public static Object[] getBranch(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("branchId"))) {
			whereSql.append(" and BRANCH_ID= '"
					+ request.getParameter("branchId").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("branchName"))) {
			whereSql.append(" and BRANCH_NAME>= '"
					+ request.getParameter("branchName").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("branchAddr"))) {
			whereSql.append(" and BRANCH_ADDR like '%"
					+ request.getParameter("branchAddr").trim() + "%'");
		}
		if (!StringUtil.isNull(request.getParameter("createTime"))) {
			whereSql.append(" and SUBSTR(RESERVE_1,1,8) = '"
					+ request.getParameter("createTime").trim() + "'");
		}
		String sql = "SELECT ID,BRANCH_ID,BRANCH_NAME,BRANCH_ADDR,RESERVE_1 FROM TBL_ISSUE_CARD_BRANCH_INF where 1=1 "
				+ whereSql.toString() + " ORDER BY RESERVE_1";

		String countSql = "select count(1) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端发卡终端信息
	 */
	public static Object[] getStoresTerm(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("mchtNo"))) {
			whereSql.append(" and MCHT_NO= '"
					+ request.getParameter("mchtNo").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("termId"))) {
			whereSql.append(" and TERM_ID = '"
					+ request.getParameter("termId").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("branchId"))) {
			whereSql.append(" and BRANCH_ID = '"
					+ request.getParameter("branchId").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("termSn"))) {
			whereSql.append(" and TERM_SN = '"
					+ request.getParameter("termSn").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("instDate"))) {
			whereSql.append(" and SUBSTR(INST_DATE,1,8) = '"
					+ request.getParameter("instDate").trim() + "'");
		}

		String sql = "SELECT MCHT_NO, TERM_ID, BRANCH_ID, TERM_SN, INST_DATE FROM TBL_ISSUE_CARD_TERM_INF where 1=1 "
				+ whereSql.toString() + " ORDER BY INST_DATE";

		String countSql = "select count(1) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询节假日日期审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHolidaysShenhe(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("saState"))) {
			whereSql.append(" and SA_STATE= '"
					+ request.getParameter("saState").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("holidayStart"))) {
			whereSql.append(" and HOLIDAY_START>= '"
					+ request.getParameter("holidayStart").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("holidayEnd"))) {
			whereSql.append(" and HOLIDAY_END<= '"
					+ request.getParameter("holidayEnd").trim() + "'");
		}
		if (!StringUtil.isNull(request.getParameter("name"))) {
			whereSql.append(" and NAME like '%"
					+ request.getParameter("name").trim() + "%'");
		}

		String sql = "SELECT ID,HOLIDAY_START,HOLIDAY_END,NAME,CREATE_OPR_ID,CREATE_TIME,UPD_OPR_ID,UPD_TIME,SA_STATE "
				+ "FROM TBL_HOLIDAYS where SA_STATE in('0','3','4')"
				+ whereSql.toString() + " ORDER BY SA_STATE";

		String countSql = "select count(1) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询银联CA公钥
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getEmvPara(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT USAGE_KEY,PARA_IDX,PARA_ORG,PARA_VAL FROM TBL_EMV_PARA WHERE trim(USAGE_KEY) = '1' order by PARA_IDX";
		String countSql = "SELECT COUNT(*) FROM TBL_EMV_PARA WHERE trim(USAGE_KEY) = '1'";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		List<Object[]> newDataList = new ArrayList();

		for (int i = 0; i < dataList.size(); i++) {
			Object[] obj = new Object[11];

			String USAGE_KEY = (String) dataList.get(i)[0];
			String PARA_IDX = (String) dataList.get(i)[1];
			String PARA_ORG = (String) dataList.get(i)[2];
			String PARA_VAL = (String) dataList.get(i)[3];

			String paraIdx = PARA_IDX; // 索引
			String usageKey = USAGE_KEY; // 参数种类
			String paraOrg = PARA_ORG; // 参数名称

			String a9F06 = PARA_VAL.substring(6, 16); // 注册应用提供商标识
			PARA_VAL = PARA_VAL.substring(16);

			String a9F22 = PARA_VAL.substring(6, 8); // 根CA公钥索引
			PARA_VAL = PARA_VAL.substring(8);

			String DF05 = PARA_VAL.substring(6, 22); // 证书失效日期
			char[] DF05Char = DF05.toCharArray();
			StringBuffer DF05StringBuffer = new StringBuffer();
			for (int j = 1; j < DF05Char.length; j = j + 2)
				DF05StringBuffer.append(DF05Char[j]);
			DF05 = DF05StringBuffer.toString();
			PARA_VAL = PARA_VAL.substring(22);

			String DF06 = PARA_VAL.substring(6, 8); // 哈希算法标识
			PARA_VAL = PARA_VAL.substring(8);

			String DF07 = PARA_VAL.substring(6, 8); // 数字签名算法长度
			PARA_VAL = PARA_VAL.substring(8);

			String DF02 = null; // 根CA公钥模
			PARA_VAL = PARA_VAL.substring(4);
			String lengthSi = PARA_VAL.substring(0, 2);
			if (lengthSi.equals("81")) {

				String lengthString = PARA_VAL.substring(2, 4);
				int length = Integer.parseInt(lengthString, 16);
				DF02 = PARA_VAL.substring(4, length * 2 + 4);
				PARA_VAL = PARA_VAL.substring(4 + length * 2);
			} else if (lengthSi.equals("82")) {

				String lengthString = PARA_VAL.substring(3, 6);
				int length = Integer.parseInt(lengthString, 16);
				DF02 = PARA_VAL.substring(6, length * 2 + 6);
				PARA_VAL = PARA_VAL.substring(6 + length * 2);
			} else {

				String lengthString = PARA_VAL.substring(0, 2);
				int length = Integer.parseInt(lengthString, 16);
				DF02 = PARA_VAL.substring(2, length * 2 + 2);
				PARA_VAL = PARA_VAL.substring(2 + length * 2);
			}

			String DF04 = PARA_VAL.substring(6, 8); // 公钥指数算法长度
			PARA_VAL = PARA_VAL.substring(8);

			String DF03 = PARA_VAL.substring(6); // 哈希结果

			obj[0] = paraIdx;
			obj[1] = usageKey;
			obj[2] = paraOrg;
			obj[3] = a9F06;
			obj[4] = a9F22;
			obj[5] = DF05;
			obj[6] = DF06;
			obj[7] = DF07;
			obj[8] = DF02;
			obj[9] = DF04;
			obj[10] = DF03;

			newDataList.add(obj);
		}
		ret[0] = newDataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询虚拟柜员
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBrhTlrInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "BRH_ID IN " + operator.getBrhBelowId());

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND BRH_ID = '" + request.getParameter("brhId")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("tlrId"))) {
			whereSql.append(" AND TLR_ID  = '" + request.getParameter("tlrId")
					+ "' ");
		}

		String sql = "SELECT BRH_ID,TLR_ID,TLR_STA,RESV1,LAST_UPD_OPR_ID,REC_UPD_TS,REC_CRT_TS FROM TBL_BRH_TLR_INFO "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_TLR_INFO "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	// 商户管理时 计算费率查询
	@SuppressWarnings("unchecked")
	public static Object[] getTblInfDiscCdT(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" and DISC_ORG IN " + operator.getBrhBelowId());
		if (isNotEmpty(request.getParameter("discOrg"))) {
			whereSql.append(" AND DISC_ORG = '"
					+ request.getParameter("discOrg") + "' ");
		}
		if (isNotEmpty(request.getParameter("discCd"))) {
			whereSql.append(" AND DISC_CD = '" + request.getParameter("discCd")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("discNm"))) {
			whereSql.append(" AND DISC_NM like '%"
					+ request.getParameter("discNm") + "%' ");
		}
		String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,CRE_ID,substr(REC_CRT_TS,1,8),REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),SA_STATE "
				+ "FROM TBL_INF_DISC_CD where sa_state ='2' "
				+ whereSql.toString();
		sql += " order by DISC_CD";

		String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD where sa_state ='2' "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询计费算法
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblInfDiscCd(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "DISC_ORG IN " + operator.getBrhBelowId());
		if (isNotEmpty(request.getParameter("discOrg"))) {
			whereSql.append(" AND DISC_ORG = '"
					+ request.getParameter("discOrg") + "' ");
		}
		if (isNotEmpty(request.getParameter("discCd"))) {
			whereSql.append(" AND DISC_CD = '" + request.getParameter("discCd")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("discNm"))) {
			whereSql.append(" AND DISC_NM like '%"
					+ request.getParameter("discNm") + "%' ");
		}
		String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,CRE_ID,substr(REC_CRT_TS,1,8),REC_UPD_USER_ID,substr(REC_UPD_TS,1,8),SA_STATE "
				+ "FROM TBL_INF_DISC_CD_temp " + whereSql.toString();
		sql += " order by DISC_CD";

		String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD_temp "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询计费算法审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblInfDiscCdShenHe(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "DISC_ORG IN " + operator.getBrhBelowId());
		// 20120814修改
		whereSql.append(" and SA_STATE in('0','3','4') ");
		if (isNotEmpty(request.getParameter("discOrg"))) {
			whereSql.append(" AND DISC_ORG = '"
					+ request.getParameter("discOrg") + "' ");
		}
		if (isNotEmpty(request.getParameter("discCd"))) {
			whereSql.append(" AND DISC_CD = '" + request.getParameter("discCd")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("discNm"))) {
			whereSql.append(" AND DISC_NM like '%"
					+ request.getParameter("discNm") + "%' ");
		}
		String sql = "SELECT DISC_CD,DISC_NM,DISC_ORG,CRE_ID,substr(REC_CRT_TS,1,8),SA_STATE FROM TBL_INF_DISC_CD_temp "
				+ whereSql.toString();
		sql += " order by DISC_CD ";

		String countSql = "SELECT COUNT(*) FROM TBL_INF_DISC_CD_TEMP "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询交易类型
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblTxnName(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		// StringBuffer whereSql = new StringBuffer();
		// whereSql.append(" WHERE " + "DISC_ORG IN " +
		// operator.getBrhBelowId());
		// if (isNotEmpty(request.getParameter("discOrg"))) {
		// whereSql.append(" AND DISC_ORG = '" + request.getParameter("discOrg")
		// + "' ");
		// }
		// if (isNotEmpty(request.getParameter("discCd"))) {
		// whereSql.append(" AND DISC_CD = '" + request.getParameter("discCd")
		// + "' ");
		// }
		String sql = "SELECT TXN_NUM,TXN_NAME,TXN_DSP,DC_FLAG FROM TBL_TXN_NAME ";
		// + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TXN_NAME ";
		// + whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 清算凭证
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSettleDocInfo(int begin,
			HttpServletRequest request) {

		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String sql = "select BRH_ID,EXCHANGE_NO,PAYER_NAME,PAYER_ACCOUNT_NO,PAY_BANK_NO,IN_BANK_SETTLE_NO,SETTLE_BUS_NO,INNER_ACCT,INNER_ACCT1,INNER_ACCT2 from SETTLE_DOC_INFO";
		sql = sql + " WHERE " + "BRH_ID IN " + operator.getBrhBelowId();
		String countSql = "select count(*) from SETTLE_DOC_INFO";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询计费算法
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getDiscInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String discId = request.getParameter("discCd");

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE trim(DISC_ID) = '" + discId.trim() + "'");

		String sql = "SELECT TXN_NUM,FLOOR_AMOUNT,"
				+ "(case when FLAG != 2 then null else MIN_FEE end),"
				+ "(case when FLAG != 2 then null when FLAG = 2 and MAX_FEE = 999999999 then null else MAX_FEE end),"
				+ "FLAG,FEE_VALUE FROM tbl_his_disc_algo_tmp "
				+ whereSql.toString();
		sql += " order by TXN_NUM,FLOOR_AMOUNT";

		String countSql = "SELECT COUNT(*) FROM tbl_his_disc_algo_tmp "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询差错列表
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getErrorList(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE " + "1=1 ");

		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND mchnt_no = '"
					+ request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("errType"))) {
			whereSql.append(" AND err_type = '"
					+ request.getParameter("errType") + "' ");
		}
		if (isNotEmpty(request.getParameter("stRegTime"))) {
			whereSql.append(" AND regist_time >='"
					+ request.getParameter("stRegTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("enRegTime"))) {
			whereSql.append(" AND regist_time < '"
					+ request.getParameter("enRegTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("stTime"))) {
			whereSql.append(" AND start_time >= '"
					+ request.getParameter("stTime") + "' ");
		}
		if (isNotEmpty(request.getParameter("enTime"))) {
			whereSql.append(" AND start_time < '"
					+ request.getParameter("enTime") + "' ");
		}

		String sql = "SELECT ERR_SEQ_NO,MCHNT_NO,TERM_ID,MCHNT_NM,ERR_TYPE,ERR_DESC,CD_FLAG,SYS_SEQ_NO,AMT1,FEE1,REGIST_OPR,reserved,rec_Crt_Ts,rec_Upd_Ts,RECORD_FLAG,REGIST_TIME,ERR_STATUS,"
				+ " START_TIME FROM BTH_ERR_REGIST_TXN " + whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM BTH_ERR_REGIST_TXN "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 证书影像
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getImgInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String imagesId = request.getParameter("imagesId");

		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil
				.getParam(SysParamConstants.FILE_UPLOAD_DISK);

		basePath = basePath.replace("\\", "/");
		File fl = new File(basePath);
		FileFilter filter = new FileFilter(imagesId);
		// 启用过滤
		File[] files = fl.listFiles(filter);

		// File[] files = fl.listFiles();

		if (null == files) {
			ret[0] = dataList;
			ret[1] = dataList.size();
			return ret;
		}
		for (File file : files) {
			if (file.getName().indexOf(".zip") != -1
					|| file.getName().indexOf(".rar") != -1)
				continue;
			Object[] obj = new Object[8];
			obj[0] = "imageSelf1" + dataList.size();
			obj[1] = "btBig" + dataList.size();
			obj[2] = "btDw" + dataList.size();
			obj[3] = "btDel" + dataList.size();
			try {
				BufferedImage bi = ImageIO.read(file);
				double width = bi.getWidth();
				double height = bi.getHeight();
				double rate = 0;
				// 等比例缩放
				if (width > 400 || height > 400) {
					if (width > height) {
						rate = 400 / width;
						width = 400;
						height = height * rate;
					} else {
						rate = 400 / height;
						height = 400;
						width = width * rate;
					}
				}
				obj[4] = (int) width;
				obj[5] = (int) height;
			} catch (Exception e) {
				obj[4] = 400;
				obj[5] = 400;
				e.printStackTrace();
			}
			obj[6] = file.getName();
			obj[7] = basePath + "/" + file.getName();
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 证书影像
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getZipInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String imagesId = request.getParameter("imagesId");

		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil
				.getParam(SysParamConstants.FILE_UPLOAD_DISK);

		basePath = basePath.replace("\\", "/");
		File fl = new File(basePath);
		FileFilter filter = new FileFilter(imagesId);
		// 启用过滤
		File[] files = fl.listFiles(filter);

		// File[] files = fl.listFiles();

		if (null == files) {
			ret[0] = dataList;
			ret[1] = dataList.size();
			return ret;
		}
		for (File file : files) {
			if (file.getName().indexOf(".jpg") != -1
					|| file.getName().indexOf(".png") != -1
					|| file.getName().indexOf(".jpeg") != -1)
				continue;
			Object[] obj = new Object[5];
			obj[0] = "imageSelf1" + dataList.size();
			obj[1] = "btDw1" + dataList.size();
			obj[2] = "btDel1" + dataList.size();
			obj[3] = file.getName();
			obj[4] = basePath + "/" + file.getName();
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 对账任务处理
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatchMission(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		/*
		 * String fileName = ""; String fileName2 = ""; String fileName3 = "";
		 * String fileName4 = ""; String fileName5 = "";
		 */
		String sql = "select a.BAT_ID as batID,a.BAT_DSP as batDsp,NVL(b.TASK_STATE,0) as batState,a.BAT_NOTE as batNote,"
				+ "NVL(b.TASK_STATE,0) as oprate from "// 20121210修改，加上as别名，因数据库方言更换，所以需要有此修改，否则查询报错
				+ "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
				+ "where substr(BAT_ID,1,1) = 'K' AND BAT_ID<='K0005' and TASK_START_FLG = '0') a "
				// +
				// "where substr(BAT_ID,1,1) = 'B' AND BAT_ID<='B0007' and TASK_START_FLG = '0') a "//20120724修改
				+ "left outer join "
				+ "(select DATE_SETTLMT,BAT_ID,TASK_STATE from TBL_BAT_TASK "
				+ "where trim(DATE_SETTLMT) = '"
				+ request.getParameter("date")
				+ "') b " + "on (a.BAT_ID = b.BAT_ID)) ORDER BY a.BAT_ID";
		/*
		 * +
		 * "on (a.BAT_ID = b.BAT_ID)) ORDER BY to_number(trim(a.BAT_CLS)),b.rec_upd_ts"
		 * ;
		 */

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		/*
		 * FtpUtil ftp = new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),
		 * SysParamUtil.getParam("FTPPORT"),
		 * SysParamUtil.getParam("FTPUSERNAME"),
		 * SysParamUtil.getParam("FTPPASSWORD"));
		 * 
		 * try {// FTP到清算服务器
		 * 去判断改日对账文件是否存在，存在则返回对账文件名，20120925修改：需要同时取到中行和农行的对账文件 String basePath
		 * = SysParamUtil .getParam("FILE_PATH_SETTLE_UPLOADJIGOU") +
		 * request.getParameter("date") + "/"; basePath = basePath.replace("\\",
		 * "/");
		 * 
		 * fileName = "ZH." + request.getParameter("date");
		 *//**
		 * modify by lvzg at 2012-1-14 for POSXF--> PAY+商户号+日期
		 */
		/*
		 * // fileName2 = "POSXF" + request.getParameter("date"); fileName2 =
		 * FILE_PREFIX_PAY +
		 * SysParamUtil.getParam(SysParamConstants.IPS_ABC_MCHNO) +
		 * request.getParameter("date");
		 *//**
		 * XUNFUJH + YYYYMMDD.txt 建行对账文件名称
		 **/
		/*
		 * fileName3 = "XUNFUJH" + request.getParameter("date") + ".txt";
		 *//**
		 * RZ+数据日期(YYYYMMDD).txt 工行入帐文件
		 **/
		/*
		 * fileName4 = "RZ" + request.getParameter("date") + ".txt";
		 *//**
		 * TH+数据日期(YYYYMMDD).txt 工行退货文件
		 **/
		/*
		 * fileName5 = "TH" + request.getParameter("date") + ".txt";
		 * 
		 * ftp.connectServer(); if (!ftp.ftpFileIsExist(basePath + fileName))
		 * fileName = "无中行加载文件"; if (!ftp.ftpFileIsExist(basePath + fileName2))
		 * fileName2 = "无农行加载文件"; if (!ftp.ftpFileIsExist(basePath + fileName3))
		 * fileName3 = "无建行对账文件"; if (!ftp.ftpFileIsExist(basePath + fileName4))
		 * fileName4 = "无工行入帐文件"; if (!ftp.ftpFileIsExist(basePath + fileName5))
		 * fileName5 = "无工行退货文件"; ftp.closeServer(); } catch (Exception e) {
		 * e.printStackTrace(); fileName = "FTP错误"; ftp.closeServer(); } for
		 * (Object[] obj : dataList) { if ("B0003".equals(obj[0])) obj[3] =
		 * fileName + "  " + fileName2 + "  " + fileName3 + "  " + fileName4 +
		 * "  " + fileName5; }
		 */
		// dataList.get(2)[3]=fileName;
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 对账任务处理(翰鑫对账)
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatchMission2(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		/*
		 * String fileName = ""; String fileName2 = ""; String fileName3 = "";
		 * String fileName4 = ""; String fileName5 = "";
		 */
		String sql = "select a.BAT_ID as batID,a.BAT_DSP as batDsp,NVL(b.TASK_STATE,0) as batState,a.BAT_NOTE as batNote,"
				+ "NVL(b.TASK_STATE,0) as oprate from "// 20121210修改，加上as别名，因数据库方言更换，所以需要有此修改，否则查询报错
				+ "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
				+ "where substr(BAT_ID,1,1) = 'H' AND BAT_ID<='H0004' and TASK_START_FLG = '0') a "
				// +
				// "where substr(BAT_ID,1,1) = 'B' AND BAT_ID<='B0007' and TASK_START_FLG = '0') a "//20120724修改
				+ "left outer join "
				+ "(select DATE_SETTLMT,BAT_ID,TASK_STATE from TBL_BAT_TASK "
				+ "where trim(DATE_SETTLMT) = '"
				+ request.getParameter("date")
				+ "') b " + "on (a.BAT_ID = b.BAT_ID)) ORDER BY a.BAT_ID";
		/*
		 * +
		 * "on (a.BAT_ID = b.BAT_ID)) ORDER BY to_number(trim(a.BAT_CLS)),b.rec_upd_ts"
		 * ;
		 */

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		/*
		 * FtpUtil ftp = new FtpUtil(SysParamUtil.getParam("FTPSERVERIP"),
		 * SysParamUtil.getParam("FTPPORT"),
		 * SysParamUtil.getParam("FTPUSERNAME"),
		 * SysParamUtil.getParam("FTPPASSWORD"));
		 * 
		 * try {// FTP到清算服务器
		 * 去判断改日对账文件是否存在，存在则返回对账文件名，20120925修改：需要同时取到中行和农行的对账文件 String basePath
		 * = SysParamUtil .getParam("FILE_PATH_SETTLE_UPLOADJIGOU") +
		 * request.getParameter("date") + "/"; basePath = basePath.replace("\\",
		 * "/");
		 * 
		 * fileName = "ZH." + request.getParameter("date");
		 *//**
		 * modify by lvzg at 2012-1-14 for POSXF--> PAY+商户号+日期
		 */
		/*
		 * // fileName2 = "POSXF" + request.getParameter("date"); fileName2 =
		 * FILE_PREFIX_PAY +
		 * SysParamUtil.getParam(SysParamConstants.IPS_ABC_MCHNO) +
		 * request.getParameter("date");
		 *//**
		 * XUNFUJH + YYYYMMDD.txt 建行对账文件名称
		 **/
		/*
		 * fileName3 = "XUNFUJH" + request.getParameter("date") + ".txt";
		 *//**
		 * RZ+数据日期(YYYYMMDD).txt 工行入帐文件
		 **/
		/*
		 * fileName4 = "RZ" + request.getParameter("date") + ".txt";
		 *//**
		 * TH+数据日期(YYYYMMDD).txt 工行退货文件
		 **/
		/*
		 * fileName5 = "TH" + request.getParameter("date") + ".txt";
		 * 
		 * ftp.connectServer(); if (!ftp.ftpFileIsExist(basePath + fileName))
		 * fileName = "无中行加载文件"; if (!ftp.ftpFileIsExist(basePath + fileName2))
		 * fileName2 = "无农行加载文件"; if (!ftp.ftpFileIsExist(basePath + fileName3))
		 * fileName3 = "无建行对账文件"; if (!ftp.ftpFileIsExist(basePath + fileName4))
		 * fileName4 = "无工行入帐文件"; if (!ftp.ftpFileIsExist(basePath + fileName5))
		 * fileName5 = "无工行退货文件"; ftp.closeServer(); } catch (Exception e) {
		 * e.printStackTrace(); fileName = "FTP错误"; ftp.closeServer(); } for
		 * (Object[] obj : dataList) { if ("B0003".equals(obj[0])) obj[3] =
		 * fileName + "  " + fileName2 + "  " + fileName3 + "  " + fileName4 +
		 * "  " + fileName5; }
		 */
		// dataList.get(2)[3]=fileName;
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getBatchSettle(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select a.BAT_ID as batID,a.BAT_DSP as batDsp,NVL(b.BAT_STATE,0) as batState,"
				+ "a.BAT_NOTE as batNote,NVL(b.BAT_STATE,0) as oprate from "// 20121211修改
				+ "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
				// +
				// "where substr(BAT_ID,1,1) = 'B' AND BAT_ID>'B0004' and TASK_START_FLG = '0') a "
				+ "where substr(BAT_ID,1,1) = 'B' AND BAT_ID>'B0007' and TASK_START_FLG = '0') a "// 20120724修改
				+ "left outer join "
				+ "(select rec_upd_ts,DATE_SETTLMT,BAT_ID,BAT_STATE from TBL_BAT_MAIN_CTL_DTL "
				+ "where trim(DATE_SETTLMT) = '"
				+ request.getParameter("date").substring(8, 16)// 20120921
				+ "') b "
				+ "on (a.BAT_ID = b.BAT_ID)) ORDER BY to_number(trim(a.BAT_CLS)),b.rec_upd_ts";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 脱机对账任务处理
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] batchMissionOutline(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "select a.BAT_ID,a.BAT_DSP,NVL(b.BAT_STATE,0),a.BAT_NOTE,NVL(b.BAT_STATE,0) from "
				+ "((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL "
				+ "where substr(BAT_ID,1,1) = 'C' and TASK_START_FLG = '0') a "
				+ "left outer join "
				+ "(select DATE_SETTLMT,BAT_ID,BAT_STATE from TBL_BAT_MAIN_CTL_DTL "
				+ "where trim(DATE_SETTLMT) = '"
				+ request.getParameter("date")
				+ "') b "
				+ "on (a.BAT_ID = b.BAT_ID)) ORDER BY to_number(trim(a.BAT_CLS))";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		// 读取文件信息
		String s = "select BAT_ID,FILE_NAME,trim(FILE_STA),MISC_1,trim(FILE_STA) as STA from TBL_ON_LINE_CTL "
				+ "WHERE trim(DATE_SETTLMT) = '"
				+ request.getParameter("date").trim() + "'";
		List<Object[]> list = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(s);
		if (null != list && !list.isEmpty()) {
			Iterator<Object[]> it = list.iterator();
			int index = 1;
			while (it.hasNext()) {
				Object[] obj = it.next();
				obj[0] = "F000" + String.valueOf(index++);
				obj[1] = "获取文件[" + obj[1] + "]";
				if (null != obj[1] && obj[1].toString().indexOf("ICONF") != -1) {
					obj[2] = "I" + obj[2];
					obj[4] = "I" + obj[4];
				} else {
					obj[2] = "F" + obj[2];
					obj[4] = "F" + obj[4];
				}
				dataList.add(index - 2, obj);
			}
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取清算信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-29下午05:51:01
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatchDtl(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String date = request.getParameter("date");
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.BRH_CODE = t2.BRH_ID and t1.BRH_CODE != '9900'");
		if (!StringUtil.isEmpty(date))
			whereSql.append("and t1.DATE_SETTLMT='")
					.append(date.split("T")[0].replaceAll("-", "")).append("'");
		else
			whereSql.append("and t1.DATE_SETTLMT='")
					.append(CommonFunction.getCurrentDate()).append("'");

		String sql = "SELECT DATE_SETTLMT,BRH_CODE,SETTLE_AMT_1,SETTLE_AMT_2,SETTLE_AMT_3,t2.BRH_NAME,"
				+ "(SETTLE_AMT_1-SETTLE_AMT_2-SETTLE_AMT_3) as SETTLE_AMT,SEND_NUM,SETTLE_FLAG as flag,FAIL_RESN,SETTLE_FLAG"
				+ " FROM TBL_BRH_INFILE_DTL t1,TBL_BRH_INFO t2"
				+ whereSql.toString() + " order by BRH_CODE";
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFILE_DTL t1,TBL_BRH_INFO t2"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取总行资金清算信息
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-8-2上午11:08:55
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBatchBankInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String date = request.getParameter("date");
		StringBuffer whereSql = new StringBuffer(" WHERE t1.BRH_CODE = '9900' ");
		if (!StringUtil.isEmpty(date))
			whereSql.append("and t1.DATE_SETTLMT='")
					.append(date.split("T")[0].replaceAll("-", "")).append("'");
		else
			whereSql.append("and t1.DATE_SETTLMT='")
					.append(CommonFunction.getCurrentDate()).append("'");
		String sql = "SELECT DATE_SETTLMT,BRH_CODE,SETTLE_AMT_1,SETTLE_AMT_2,SETTLE_AMT_3,(SETTLE_AMT_1-SETTLE_AMT_2-SETTLE_AMT_3) as SETTLE_AMT"
				+ " FROM TBL_BRH_INFILE_DTL t1"
				+ whereSql.toString()
				+ " order by BRH_CODE";
		String countSql = "SELECT COUNT(*) FROM TBL_BRH_INFILE_DTL t1"
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 1);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取商户下挂终端
	 * 
	 * @param begin
	 * @param request
	 * @return 2011-7-29下午05:51:01
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntTermInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		String sql = "select TERM_ID,TERM_STA,TERM_SIGN_STA,REC_CRT_TS,(PROVINCE||'-'||CITY||'-'||AREA||'-'||TERM_ADDR) as POSADDR FROM TBL_TERM_INF WHERE MCHT_CD = '"
				+ request.getParameter("mchntNo").trim() + "' ";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		// 20120823改成分页查询
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim()))
			return true;
		else
			return false;
	}

	/**
	 * 查询商户入账结果
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntSettleInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();

		whereSql.append(" WHERE trim(BRH_CODE) IN " + operator.getBrhBelowId());

		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND trim(BRH_CODE) in "
					+ InformationUtil.getBrhGroupString(request
							.getParameter("brhId")));
		}
		if (isNotEmpty(request.getParameter("date"))) {
			whereSql.append(" AND START_DATE = '"
					+ request.getParameter("date") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntId"))) {
			whereSql.append(" AND trim(MCHT_NO) = '"
					+ request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntAcct"))) {
			whereSql.append(" AND MCHT_ACCT = '"
					+ request.getParameter("mchntAcct") + "' ");
		}
		if (isNotEmpty(request.getParameter("settleFlag"))) {
			whereSql.append(" AND SETTLE_FLAG = '"
					+ request.getParameter("settleFlag") + "' ");
		}

		String sql = "SELECT BRH_CODE,MCHT_NO,MCHT_NM,"
				+ "(case ACCT_TYPE when 'A' then '本行对公账户' when 'P' then '本行对私账户或单位卡' "
				+ "when 'O' then '他行对公账户' when 'S' then '他行对私账户' else '' end),"
				+ "MCHT_ACCT,ACCT_NM,"
				+ "DATE_SETTLMT,SETTLE_AMT,TXN_AMT,SETTLE_FEE1,"
				+ "TXN_NUM,SETTLE_FLAG,FAIL_RESN,SETTLE_FLAG AS FLAG FROM "
				+ "TBL_MCHNT_INFILE_DTL ";

		sql += whereSql.toString();

		sql += "order by BRH_CODE,MCHT_NO,DATE_SETTLMT ";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] riskTxnInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("saTxnCard"))) {
			whereSql.append(" AND trim(sa_txn_card) = '"
					+ request.getParameter("saTxnCard").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("saMchtNo"))) {
			whereSql.append(" AND trim(sa_mcht_no) = '"
					+ request.getParameter("saMchtNo").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			whereSql.append(" AND trim(sa_txn_date) >= '"
					+ request.getParameter("startDate").trim() + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			whereSql.append(" AND trim(sa_txn_date) <= '"
					+ request.getParameter("endDate").trim() + "' ");
		}

		String sql = "select sa_txn_card,sa_mcht_no,mcht_nm,sa_term_no,txn_name,"
				+ "(case when sa_txn_amt is null then 0 when sa_txn_amt  = '            ' then 0 else sa_txn_amt*1/100 end),"
				+ "sa_txn_date,SA_TXN_TIME,SA_CLC_FLAG,SA_CLC_RSN1 "
				+ "from tbl_clc_mon,tbl_txn_name,tbl_mcht_base_inf  "
				+ "where sa_txn_num = txn_num And sa_mcht_no=mcht_no ";
		// "and trim(SA_CLC_RSN1) in (select SA_MODEL_KIND from TBL_RISK_INF) ";

		sql += whereSql.toString();
		sql += "order by sa_txn_date desc,SA_TXN_TIME desc";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 代发记录查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSettleLog(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" WHERE trim(BRH_ID) IN " + operator.getBrhBelowId());
		// 代发机构
		if (isNotEmpty(request.getParameter("brhId"))) {
			whereSql.append(" AND trim(BRH_ID) in "
					+ InformationUtil.getBrhGroupString(request
							.getParameter("brhId")));
		}
		// 代发日期
		if (isNotEmpty(request.getParameter("date"))) {
			whereSql.append(" AND INST_DATE = '" + request.getParameter("date")
					+ "' ");
		}

		String sql = "SELECT BRH_ID,INST_DATE,START_DATE,END_DATE,SETTLE_AMT,TXN_AMT,SETTLE_FEE1,"
				+ "MCHNT_COUNT,SETTLE_AMT_DIS,TXN_AMT_DIS,SETTLE_FEE1_DIS,MCHNT_COUNT_DIS,"
				+ "(SETTLE_AMT + SETTLE_AMT_DIS) AS SETTLE_AMT_TAL,"
				+ "(TXN_AMT + TXN_AMT_DIS) AS TXN_AMT_TAL,"
				+ "(SETTLE_FEE1 + SETTLE_FEE1_DIS) AS SETTLE_FEE1_TAL,"
				+ "(MCHNT_COUNT + MCHNT_COUNT_DIS) AS MCHNT_COUNT_TAL,"
				+ "SUCC_FILE,FAIL_FILE,STLM_FLAG,STLM_FLAG2,OPR_ID1,OPR_ID2,STLM_DSP,SUBSTR(REC_CRE_TIME,9),SUBSTR(REC_UPD_TIME,9) "
				+ "FROM TBL_INFILE_OPT ";
		sql += whereSql.toString();
		sql += "ORDER BY BRH_ID,INST_DATE";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询专业服务机构信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getOrganInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String orgId = request.getParameter("orgId");
		String branch = request.getParameter("branch");
		StringBuffer whereSql = new StringBuffer("WHERE branch in")
				.append(operator.getBrhBelowId());
		if (orgId != null && !orgId.equals(""))
			whereSql.append(" and org_Id='").append(orgId).append("'");
		if (branch != null && !branch.equals(""))
			whereSql.append(" and branch='").append(branch).append("'");
		StringBuffer sql = new StringBuffer(
				"SELECT ORG_ID,BRANCH,ORG_NAME,RATE,RATE_TYPE,REMARKS,MISC,CRT_OPR,CRT_TS,LAST_UPD_OPR,LAST_UPD_TS FROM TBL_PROFESSIONAL_ORGAN ");

		sql.append(whereSql);

		String countSql = "SELECT COUNT(*) FROM TBL_PROFESSIONAL_ORGAN "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin,
						Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 问卷查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperRecInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// String curPaperId = InformationUtil.getCurPaperId();

		// 现使用RESERVE1来保存是否为当前试卷，为当前试卷RESERVE1=1，否则为0
		String sql = "select r.PAPER_ID,r.RESERVE1,r.RESERVE2,r.CRT_USER,substr(r.CRT_TIME,1,8),"
				+ "(case when p.QUESTION = r.PAPER_ID then '1' else '0' end) as STA "
				+ "from TBL_PAPER_REC_INF r,TBL_PAPER_DEF_INF p where p.PAPER_ID = 'PAPER_STATUS' order by r.CRT_TIME DESC";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 问卷历史查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperHisInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		// 问卷编号
		if (isNotEmpty(request.getParameter("PAPER_ID"))) {
			whereSql.append(" WHERE PAPER_ID = '"
					+ request.getParameter("PAPER_ID") + "' ");
		}

		String sql = "SELECT QUES_ID,QUES_INDEX,QUESTION,OPTIONS,REMARKS FROM TBL_PAPER_HIS_INF ";
		sql += whereSql.toString();
		sql += "ORDER BY QUES_INDEX";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 实时问卷查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperDefInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT PAPER_ID,QUES_ID,QUES_INDEX,QUESTION,OPTIONS,REMARKS FROM TBL_PAPER_DEF_INF ";
		sql += "where PAPER_ID != 'PAPER_STATUS' ";
		sql += "ORDER BY QUES_INDEX";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户评级查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] PaperLelInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);

		StringBuffer sb = new StringBuffer(
				" where trim(MCHT_ID) = trim(b.MCHT_NO) ");
		sb.append("and b.ACQ_INST_ID in " + operator.getBrhBelowId());

		if (!StringUtil.isNull(request.getParameter("ACQ_INST_ID"))) {
			sb.append(" and b.ACQ_INST_ID in "
					+ InformationUtil.getBrhGroupString(request
							.getParameter("ACQ_INST_ID")));
		}

		if (!StringUtil.isNull(request.getParameter("MCHT_NO"))) {
			sb.append(" and MCHT_ID = '" + request.getParameter("MCHT_NO")
					+ "' ");
		}

		if (!StringUtil.isNull(request.getParameter("MCHT_LEVEL"))) {
			sb.append(" and MCHT_LEVEL = '"
					+ request.getParameter("MCHT_LEVEL") + "' ");
		}

		String sql = "SELECT SEL_MCHT_ID, PAPER_ID, MCHT_ID,b.MCHT_NM, MCHT_POINT, MCHT_LEVEL, RESERVE1, RESERVE2, CRT_USER, substr(CRT_TIME,1,8) "
				+ "FROM TBL_PAPER_LEL_INF,TBL_MCHT_BASE_INF b ";

		sql += sb.toString();

		sql += " order by MCHT_ID,CRT_TIME desc";

		String countSql = "select count(*) from (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取选项信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getQuestionInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// StringBuffer whereSql = new StringBuffer();

		String sql = "SELECT OPT_ID,OPT,POINT FROM TBL_PAPER_OPT_INF where trim(QUES_ID) = '"
				+ request.getParameter("quesId").trim() + "' ";
		sql += "order by POINT DESC";

		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取营销活动信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMarketAct(int begin, HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String actNo = request.getParameter("actNo");
		String actName = request.getParameter("actName");
		String actCycle = request.getParameter("actCycle");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String state = request.getParameter("state");
		StringBuffer whereSql = new StringBuffer("WHERE BANK_NO in ")
				.append(operator.getBrhBelowId());

		if (isNotEmpty(state)) {
			whereSql.append(" AND STATE ='").append(state).append("'");
		}
		if (isNotEmpty(actNo)) {
			whereSql.append(" AND ACT_NO ='").append(actNo).append("'");
		}
		if (isNotEmpty(actName)) {
			whereSql.append(" AND ACT_NAME ='").append(actName).append("'");
		}
		if (isNotEmpty(actCycle)) {
			whereSql.append(" AND ACT_CYCLE ='").append(actCycle).append("'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND START_DATE <").append(startDate);
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND END_DATE >").append(endDate);
		}

		String sql = "SELECT BANK_NO,ACT_NO,ACT_NAME,STATE,START_DATE,END_DATE,ACT_CYCLE,ORGAN_NO,ORGAN_TYPE,ACT_CONTENT,ACT_FEE,REMARKS,FLAG01,CRT_DT,CRT_OPR,UPD_DT,UPD_OPR,REC_OPR,MISC1 FROM TBL_MARKET_ACT_REVIEW "
				+ whereSql.append(" ORDER BY ACT_NO").toString();

		String countSql = "SELECT COUNT(*) FROM TBL_MARKET_ACT_REVIEW "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, 100);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 获取营销活动商户参与信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntParticipat(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		Object[] ret = new Object[2];
		String actNo = request.getParameter("actNo");
		String state = request.getParameter("state");
		String flag = request.getParameter("flag");
		String noFlag = request.getParameter("noFlag");
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.STATE!='2' AND t1.BANK_NO in ").append(operator
				.getBrhBelowId());
		if (!StringUtil.isNull(flag))
			whereSql.append(" AND t1.FLAG01='").append(flag).append("'");
		if (!StringUtil.isNull(noFlag))
			whereSql.append(" AND t1.FLAG01!='").append(noFlag).append("'");
		if (!StringUtil.isNull(actNo))
			whereSql.append(" AND t1.ACT_NO='").append(actNo).append("'");
		if (!StringUtil.isNull(state))
			whereSql.append(" AND t1.STATE='").append(state).append("'");
		StringBuffer sql = new StringBuffer(
				"SELECT t1.ACT_NO,t1.ACT_NAME,t1.BANK_NO,")
				.append("t1.STATE,t1.START_DATE,t1.END_DATE,t1.MCHNT_NO,t2.MCHT_NM,")
				.append("t1.ACT_CONTENT,t1.ACT_FEE,t1.FLAG01 FROM TBL_MCHNT_PARTICIPAT_REVIEW t1 ")
				.append("left join tbl_mcht_base_inf t2 on t1.MCHNT_NO=t2.MCHT_NO")
				.append(whereSql).append(" ORDER BY t1.ACT_NO");

		String countSql = "SELECT COUNT(*) FROM TBL_MCHNT_PARTICIPAT_REVIEW t1"
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin, 200);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询商户信息(营销活动)
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchntInfoForActivity(int begin,
			HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String bankNo = request.getParameter("bankNo");
		String mchtGrp = request.getParameter("mchtGrp");
		String mchnNo = request.getParameter("mchnNo");
		String connType = request.getParameter("connType");
		StringBuffer whereSql = new StringBuffer(
				" WHERE t1.MCHT_NO= t2.MCHT_NO and t2.FEE_RATE = t3.DISC_CD ")
				.append("and  t1.MCHT_STATUS = '0' ")
				.append("and ACQ_INST_ID in ").append(operator.getBrhBelowId());

		if (isNotEmpty(mchnNo)) {
			whereSql.append(" AND t1.MCHT_NO = '").append(mchnNo).append("' ");
		}
		if (isNotEmpty(mchtGrp)) {
			whereSql.append(" AND t1.MCC = '").append(mchtGrp).append("' ");
		}
		if (isNotEmpty(bankNo)) {
			whereSql.append(" AND t1.ACQ_INST_ID = '").append(bankNo)
					.append("' ");
		}
		if (isNotEmpty(connType)) {
			whereSql.append(" AND t1.CONN_TYPE = '").append(connType)
					.append("' ");
		}
		Object[] ret = new Object[2];

		StringBuffer sql = new StringBuffer(
				"SELECT t1.MCHT_NO,t1.MCHT_NM,substr(t4.DESCR,1,4),t1.MCC,t1.ACQ_INST_ID,")
				.append("case CONN_TYPE when 'J' then '间联' when 'Z' then '直联' else CONN_TYPE end CONN_TYPE")
				.append(",t3.DISC_NM,' ' FROM　TBL_MCHT_BASE_INF t1 left join TBL_INF_MCHNT_TP_GRP t4 on t1.MCHT_GRP=t4.MCHNT_TP_GRP,")
				.append("TBL_MCHT_SETTLE_INF t2,TBL_INF_DISC_CD t3 ")
				.append(whereSql).append(" ORDER BY t1.MCHT_NO");
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_BASE_INF t1,TBL_MCHT_SETTLE_INF t2,TBL_INF_DISC_CD t3 "
				+ whereSql.toString();

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql.toString(), begin, 200);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 终端交易限额 */
	@SuppressWarnings("unchecked")
	public static Object[] getCstTermFeeInf(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String whereSql = "";
		if (isNotEmpty(request.getParameter("statue"))) {
			whereSql += " AND SA_STATE = '" + request.getParameter("statue")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("mtchid"))) {
			whereSql += " AND MCHT_CD = '" + request.getParameter("mtchid")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("termid"))) {
			whereSql += " AND TERM_ID = '" + request.getParameter("termid")
					+ "' ";
		}
		whereSql += "Order By a.SA_STATE,MCHT_CD asc";
		Object[] ret = new Object[2];
		String sql = "select b.MCHT_NM,MCHT_CD,TERM_ID,DAY_NUM,DAY_AMT,DAY_SINGLE,SA_STATE,a.SA_ACTION,a.cre_id,a.REC_CRT_TS,a.UP_ID,a.REC_UPD_TS,a.CARD_TYPE,a.MON_AMT from CST_TERM_FEE_INF a,tbl_mcht_base_inf b where a.MCHT_CD=b.MCHT_NO "
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询终端交易限额sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 终端交易审核 */
	@SuppressWarnings("unchecked")
	public static Object[] getCstTermFeeShenHeInf(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("statue"))) {
		// whereSql += " AND STATUE = '" + request.getParameter("statue")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("channel"))) {
		// whereSql += " AND CHANNEL = '"
		// + request.getParameter("channel") + "' ";
		// }
		Object[] ret = new Object[2];
		String sql = "select b.MCHT_NM,MCHT_CD,TERM_ID,DAY_NUM,DAY_AMT,DAY_SINGLE,SA_STATE,a.SA_ACTION,a.cre_id,a.REC_CRT_TS,a.UP_ID,a.REC_UPD_TS,a.CARD_TYPE,a.MON_AMT from CST_TERM_FEE_INF a,tbl_mcht_base_inf b where a.MCHT_CD=b.MCHT_NO and a.SA_STATE In('0','3','4') Order By a.SA_STATE,MCHT_CD asc";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询终端交易审核sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户手续费规则查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHisDisCalQuery(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String whereSql = "";
		if (isNotEmpty(request.getParameter("idStatus"))) {
			whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("idmchtid"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("idmchtid")
					+ "' ";
		}
		whereSql += " Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select DISC_ID,TERM_ID,MCHT_NO,TO_BRCH_NO,FK_BRCH_NO,SA_SATUTE from TBL_HIS_DISC_ALGO1_TMP where SA_SATUTE in('0','1','2','3','4')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		System.out.print("查询商户手续费规则sql ====" + sql);
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户手续费规则审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getHisDisCalShenHeQuery(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String whereSql = "";
		if (isNotEmpty(request.getParameter("idStatus"))) {
			whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
					+ "' ";
		}
		if (isNotEmpty(request.getParameter("idmchtid"))) {
			whereSql += " AND MCHT_NO = '" + request.getParameter("idmchtid")
					+ "' ";
		}
		whereSql += " Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select DISC_ID,TERM_ID,MCHT_NO,TO_BRCH_NO,FK_BRCH_NO,SA_SATUTE from TBL_HIS_DISC_ALGO1_TMP where SA_SATUTE in('0','3','4')"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按交易解挂
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTransHang(int begin, HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select FLOW_NUM,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK from TBL_TRANS_HANG";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * IC卡其他参数维护
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] icParam(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		// StringBuffer whereSql = new StringBuffer();

		String sql = "SELECT USAGE_KEY,PARA_IDX,PARA_ID,PARA_LEN,PARA_VAL,REC_UPD_OPR,REC_CRT_TS from TBL_EMV_PARA where trim(USAGE_KEY) = '0' order by PARA_IDX";
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	// 根据条件从交易流水表或交易流水历史记录中查询出待退货信息
	@SuppressWarnings("unchecked")
	public static Object[] getAmtReturn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (!StringUtil.isEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and (substr(t.inst_date,0,8)='")
					.append(request.getParameter("instDate")).append("') ");
		}
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {
			whereSql.append(" and t.card_accp_term_id='")
					.append(request.getParameter("termId").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("pan"))) {
			whereSql.append(" and t.pan='")
					.append(StringUtil.replace(request.getParameter("pan")
							.trim(), "'", "''")).append("' ");
		}

		// ADD LHF
		if (!StringUtil.isEmpty(request.getParameter("retrivlRef"))) {
			whereSql.append(" and t.Retrivl_Ref ='")
					.append(StringUtil.replace(
							request.getParameter("retrivlRef").trim(), "'",
							"''")).append("' ");
		}

		if (!StringUtil.isEmpty(request.getParameter("amtTrans"))) {
			// 接口传过来的是以元为单位的，数据库中存的是以分为单位的
			long amtMax = (long) (Float.parseFloat(request.getParameter(
					"amtTrans").trim()) * 100);
			whereSql.append(" And cast(replace(t.amt_trans,' ','0') As Number) = "
					+ amtMax);
		}
		// 先更据条件从流水表中查询
		String sql = "select t.Card_Accp_Id  as mchtNo,t.card_accp_term_id as termId,t.pan as pan,cast(replace(amt_trans,' ','0') As Number)/100 As amtTrans,"
				+ "t.Inst_Date as instDate,t.Retrivl_Ref As retrivlRef,'',to_number(amt_return)/100,t.Sys_Seq_Num,t.Term_Ssn,t.txn_num ,t.cancel_flag,t.revsal_flag,t.resp_code,"
				+ " t.AUTHR_ID_RESP,t.FWD_INST_ID_CODE,t.acq_inst_id_code from TBL_N_TXN t where t.Card_Accp_Id='"
				+ request.getParameter("mchtNo").trim()
				+ "' and t.txn_num='1101' and revsal_flag='0' and cancel_flag='0' and resp_code='00'";
		// + "and  t.Retrivl_Ref='" +
		// StringUtil.replace(request.getParameter("retrivlRef").trim(), "'",
		// "''") + "' ";
		if (!StringUtil.isEmpty(whereSql.toString())) {
			sql += whereSql.toString();
		}
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);

		// 当流水表中的没有记录的时候 需要从流水历史记录表中查询
		if ("0".equals(count)) {
			sql = "select t.Card_Accp_Id  as mchtNo,t.card_accp_term_id as termId,t.pan as pan,cast(replace(amt_trans,' ','0') As Number)/100 As amtTrans,"
					+ "t.Inst_Date as instDate,t.Retrivl_Ref As retrivlRef,'',to_number(amt_return)/100,t.Sys_Seq_Num,t.Term_Ssn,t.txn_num ,t.cancel_flag,t.revsal_flag,t.resp_code,"
					+ " t.AUTHR_ID_RESP,t.FWD_INST_ID_CODE,t.acq_inst_id_code from TBL_N_TXN_HIS t where t.Card_Accp_Id='"
					+ request.getParameter("mchtNo").trim()
					+ "' and t.txn_num='1101' and revsal_flag='0' and cancel_flag='0' and resp_code='00'";

			// ADD LHF
			// + " and  t.Retrivl_Ref='" +
			// StringUtil.replace(request.getParameter("retrivlRef").trim(),
			// "'", "''") + "' ";
			if (!StringUtil.isEmpty(whereSql.toString())) {
				sql += whereSql.toString();
			}
			countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
			count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
					countSql);
		}

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		if (dataList != null && dataList.size() != 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				List list = new ArrayList();
				Collections.addAll(list, obj);
				String algoDtl = "select date_settlmt,settl_amt/100,stlm_flg from tbl_algo_dtl where TRANS_DATE||TRANS_DATE_TIME='"
						+ obj[4].toString().trim()
						+ "' and pan='"
						+ obj[2].toString().trim()
						+ "' and txn_ssn='"
						+ obj[8].toString().trim() + "' ";
				List<Object[]> algoList = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(algoDtl, begin,
								Constants.QUERY_RECORD_COUNT);
				if (algoList != null && algoList.size() > 0) {
					list.add(algoList.get(0)[0]);
					list.add(algoList.get(0)[1]);
					list.add(algoList.get(0)[2]);
				} else {
					String newGc = "select DATE_SETTLMT_8,case when AMT_SETTLMT= '            ' then 0 else  AMT_SETTLMT/100 end as settl_amt,STLM_FLAG from BTH_NEW_GC_TXN_SUCC where "
							+ "INST_DATE||INST_TIME='"
							+ obj[4].toString().trim()
							+ "' and pan='"
							+ obj[2].toString().trim()
							+ "' and SYS_SEQ_NUM='"
							+ obj[8].toString().trim() + "'";
					List<Object[]> newGcList = CommonFunction.getCommQueryDAO()
							.findBySQLQuery(newGc, begin,
									Constants.QUERY_RECORD_COUNT);
					if (newGcList != null && newGcList.size() > 0) {
						list.add(newGcList.get(0)[0]);
						list.add(newGcList.get(0)[1]);
						list.add(newGcList.get(0)[2]);
					} else {
						list.add("");
						list.add("");
						list.add("");
					}
				}
				dataList.set(i, list.toArray());
			}
		}
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 查询已退货信息 */
	@SuppressWarnings("unchecked")
	public static Object[] getManualReturn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (!StringUtil.isEmpty(request.getParameter("saState"))) {
			whereSql.append(" and sa_State='")
					.append(request.getParameter("saState")).append("'");
		} /*
		 * else {// 2 为审核拒绝状态 whereSql.append(" and sa_state<>'2'"); }
		 */
		if (!StringUtil.isEmpty(request.getParameter("createDate"))) {
			whereSql.append(" and substr(create_date,0,8)='")
					.append(request.getParameter("createDate")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("instDate"))) {// 原交易日期,20120820修改为date，原为instDate
			whereSql.append(" and substr(inst_date,0,8)='")
					.append(request.getParameter("instDate")).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("amtTrans"))) {// 原交易金额
			whereSql.append(" and t.amt_trans/100='")
					.append(request.getParameter("amtTrans").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and t.term_id='")
					.append(request.getParameter("termId").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("sysSsn"))) {// 流水号
			whereSql.append(" and t.sys_ssn='")
					.append(request.getParameter("sysSsn").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("mchtNo"))) {// 商户编 号
			whereSql.append(" and t.mchtNo='")
					.append(request.getParameter("mchtNo").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("returnFee"))) {// 退货手续费
			whereSql.append(" and t.return_fee='")
					.append(request.getParameter("returnFee").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("pan"))) {//
			whereSql.append(" and t.pan='")
					.append(request.getParameter("pan").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("retrivlRef"))) {// 参考号
			whereSql.append(" and t.Retrivl_Ref='")
					.append(StringUtil.replace(
							request.getParameter("retrivlRef").trim(), "'",
							"''")).append("' ");
		}
		String sql = "select mchtNo,term_id,sys_ssn,pan,amt_trans/100 as amt_trans,inst_date,retrivl_ref,"
				+ "  amt_return/100 as amt_return ,sa_state,opr_id,create_date,id,txn_num,return_fee,src_id,potal_ssn,rsp_code from tbl_manual_return t "
				+ "where 1=1 ";/*
								 * + "sa_state <> '1' ";
								 */
		// + request.getParameter("mchtNo").trim()
		// + "' and  t.Retrivl_Ref='"
		// + StringUtil.replace(request.getParameter("retrivlRef").trim(),
		// "'", "''") + "' ";
		if (!StringUtil.isEmpty(whereSql.toString())) {
			sql += whereSql.toString();
		}
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		sql += " order by create_date desc";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 线上交易流水
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getXsNTxn(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		boolean bb = true;
		// 判断开始时间，小于90天查当前表，超过90天查全部
		try {
			if (!StringUtil.isNull(request.getParameter("instDate"))) {
				if (daysBetween(request.getParameter("instDate"), new Date()) <= 90) {
					bb = true;
				} else {
					bb = false;
				}

			}
		} catch (ParseException e) {

			Log.log("日期转换错误");
		}
		if (!StringUtil.isEmpty(request.getParameter("instDate"))) {// 原交易日期,20120820修改为date，原为instDate
			whereSql.append(" and substr(inst_date,0,8)='")
					.append(request.getParameter("instDate")).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and term_id='")
					.append(request.getParameter("termId").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("mchtId"))) {// 流水号
			whereSql.append(" and mcht_id='")
					.append(request.getParameter("mchtId").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("pan"))) {
			whereSql.append(" and pan like'%")
					.append(request.getParameter("pan").trim()).append("%' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("orderNo"))) {
			whereSql.append(" and order_no='")
					.append(request.getParameter("orderNo").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("transState"))) {
			whereSql.append(" and trans_state='")
					.append(request.getParameter("transState").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("instId"))) {
			whereSql.append(" and inst_id='")
					.append(request.getParameter("instId").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("misc1"))) {
			whereSql.append(" and misc_1='")
					.append(request.getParameter("misc1").trim()).append("' ");
		}
		String sql;
		if (bb) {
			sql = "select distinct inst_date, mcht_id, term_id, pan, order_no, amt_trans, trans_state, trim(inst_id),misc_1, resp_code, txn_num, misc_2 from tbl_xs_n_txn where 1=1 ";
		} else {
			sql = "select distinct inst_date, mcht_id, term_id, pan, order_no, amt_trans, trans_state, trim(inst_id),misc_1, resp_code, txn_num, misc_2 from tbl_xs_n_txn_his where 1=1 ";
		}
		if (!StringUtil.isEmpty(whereSql.toString())) {
			sql += whereSql.toString();
		}
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		sql += " order by inst_date desc";
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 查询已退货信息 */
	@SuppressWarnings("unchecked")
	public static Object[] getManualReturnWeih(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (!StringUtil.isEmpty(request.getParameter("saState"))) {
			whereSql.append(" and sa_State='")
					.append(request.getParameter("saState")).append("'");
		} /*
		 * else {// 2 为审核拒绝状态 whereSql.append(" and sa_state<>'2'"); }
		 */
		if (!StringUtil.isEmpty(request.getParameter("createDate"))) {
			whereSql.append(" and substr(create_date,0,8)='")
					.append(request.getParameter("createDate")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("instDate"))) {// 原交易日期,20120820修改为date，原为instDate
			whereSql.append(" and substr(inst_date,0,8)='")
					.append(request.getParameter("instDate")).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("amtTrans"))) {// 原交易金额
			whereSql.append(" and t.amt_trans/100='")
					.append(request.getParameter("amtTrans").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {// 终端号
			whereSql.append(" and t.term_id='")
					.append(request.getParameter("termId").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("sysSsn"))) {// 流水号
			whereSql.append(" and t.sys_ssn='")
					.append(request.getParameter("sysSsn").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("mchtNo"))) {// 商户编 号
			whereSql.append(" and t.mchtNo='")
					.append(request.getParameter("mchtNo").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("returnFee"))) {// 退货手续费
			whereSql.append(" and t.return_fee='")
					.append(request.getParameter("returnFee").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("pan"))) {//
			whereSql.append(" and t.pan='")
					.append(request.getParameter("pan").trim()).append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("retrivlRef"))) {// 参考号
			whereSql.append(" and t.Retrivl_Ref='")
					.append(StringUtil.replace(
							request.getParameter("retrivlRef").trim(), "'",
							"''")).append("' ");
		}
		// UPDATE 审核状态
		if (!StringUtil.isEmpty(request.getParameter("saState"))) {// 审核状态
			whereSql.append(" and t.sa_state='")
					.append(StringUtil.replace(request.getParameter("saState")
							.trim(), "'", "''")).append("' ");
		}

		String sql = "select mchtNo,term_id as termId,sys_ssn as sysSsn,pan,amt_trans/100,inst_date as instDate,Retrivl_Ref as retrivlRef,"
				+ "amt_return/100 as amtReturn ,sa_state,opr_id,create_date as createDate,id,txn_num,return_fee as returnFee,src_id,potal_ssn,rsp_code from tbl_manual_return t "
				+ "where 1=1 ";// END
		/*
		 * String sql =
		 * "select mchtNo,term_id as termId,sys_ssn as sysSsn,pan,amt_trans/100,inst_date as instDate,Retrivl_Ref as retrivlRef,"
		 * +
		 * "amt_return/100 as amtReturn ,sa_state,opr_id,create_date as createDate,id,txn_num,return_fee as returnFee from tbl_manual_return t "
		 * + "where sa_state <> '1' and sa_state <> '4' ";
		 */
		/* + "sa_state <> '1' "; */
		// + request.getParameter("mchtNo").trim()
		// + "' and  t.Retrivl_Ref='"
		// + StringUtil.replace(request.getParameter("retrivlRef").trim(),
		// "'", "''") + "' ";
		if (!StringUtil.isEmpty(whereSql.toString())) {
			sql += whereSql.toString();
		}
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		sql += " order by create_date desc";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getManualReturnCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		/*
		 * StringBuffer whereSql=new StringBuffer();
		 * if(!StringUtil.isEmpty(request.getParameter("saState"))){
		 * whereSql.append
		 * (" and sa_State='").append(request.getParameter("saState"
		 * )).append("'"); }
		 * if(!StringUtil.isEmpty(request.getParameter("instDate"))){
		 * whereSql.append
		 * (" and (substr(inst_date,0,8)='").append(request.getParameter
		 * ("instDate")).append("' "); }
		 * if(!StringUtil.isEmpty(request.getParameter("termId"))){
		 * whereSql.append
		 * (" and t.term_id='").append(request.getParameter("termId"
		 * ).trim()).append("' "); }
		 * if(!StringUtil.isEmpty(request.getParameter("pan"))){
		 * whereSql.append(
		 * " and t.pan='").append(StringUtil.replace(request.getParameter
		 * ("pan").trim(),"'","''")).append("' "); }
		 */
		String sql = "select mchtNo,term_id as termId,pan,amt_trans/100,inst_date as instDate,Retrivl_Ref as retrivlRef,"
				+ "amt_return/100 as amtReturn ,sa_state,opr_id,create_date,id,txn_num,return_fee,sys_ssn,src_id,potal_ssn,rsp_code  from tbl_manual_return t where sa_State<>1 and sa_State<>4 ";
		/*
		 * if(!StringUtil.isEmpty(whereSql.toString())){
		 * sql+=whereSql.toString(); }
		 */
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		sql += " order by create_date desc";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	public static Object[] getXsReturnInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		if (!StringUtil.isEmpty(request.getParameter("returnDate"))) {
			whereSql.append(" and substr(return_date,0,8) ='")
					.append(request.getParameter("returnDate")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("transDate"))) {
			whereSql.append(" and substr(trans_date,0,8) ='")
					.append(request.getParameter("transDate")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("mchtId"))) {
			whereSql.append(" and mcht_id ='")
					.append(request.getParameter("mchtId")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {
			whereSql.append(" and term_id ='")
					.append(request.getParameter("termId")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("pan"))) {
			whereSql.append(" and pan like '%")
					.append(request.getParameter("pan")).append("%'");
		}
		if (!StringUtil.isEmpty(request.getParameter("orderNo"))) {
			whereSql.append(" and trim(order_no) ='")
					.append(request.getParameter("orderNo")).append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("returnOrderno"))) {
			whereSql.append(" and trim(return_orderno) ='")
					.append(request.getParameter("returnOrderno").trim())
					.append("'");
		}
		if (!StringUtil.isEmpty(request.getParameter("returnFlag"))) {
			whereSql.append(" and return_flag ='")
					.append(request.getParameter("returnFlag").trim())
					.append("' ");
		}
		if (!StringUtil.isEmpty(request.getParameter("misc5"))) {
			if (request.getParameter("misc5").equals("0")) {
				whereSql.append(" and misc_5 is Null ");
			} else {
				whereSql.append(" and misc_5 = '")
						.append(request.getParameter("misc5")).append("' ");
			}
		}
		if (!StringUtil.isEmpty(request.getParameter("channel"))) {
			whereSql.append(" and channel ='")
					.append(request.getParameter("channel")).append("'");
		}
		String sql = "select distinct return_date,pay_date,trans_date,mcht_id,term_id,pan,order_no,return_orderno,return_amt,amt_trans,return_flag,misc_1,opr_id,channel,channel_discount,resp_code,misc_5 from tbl_xs_return_inf t where 1=1 ";
		if (!StringUtil.isEmpty(whereSql.toString())) {
			sql += whereSql.toString();
		}
		String countSql = "SELECT COUNT(*) FROM ( " + sql + ")";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		sql += " order by return_date desc";
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按交易查询未挂账的信息
	 * <p>
	 * 先去清分清算表中查询该交易日的数据是否已经进行了清分清算，如果进行了清分清算 则去清分清算表中查询可以挂账的交易；如果未进行清分清算
	 * 则去交易流水表中查询待清算的交易。
	 * <p>
	 * 由于赶进度，没考虑性能，目前只是为了实现功能，后期有时间了，这里需要优化
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAlgoDtl(int begin, HttpServletRequest request) {
		List<Object[]> algoListNew = new ArrayList<Object[]>();
		Object[] ret = new Object[2];
		String mchtno = request.getParameter("mchtno");
		String transdateend = request.getParameter("transdateend");
		String txnNums = SysParamUtil.getParam("HANGCODE");
		String startDate = transdateend;
		String endDate = transdateend;
		/*
		 * if(isNotEmpty(request.getParameter("transdatestart"))){
		 * startDate=request.getParameter("transdatestart"); }
		 */

		Map termMap = new HashMap();
		Map transMap = new HashMap();

		// 先判断该商户在该个交易日是否已经按照商户挂账 0-挂账待审核，1-挂账，3-解挂待审核
		String mchtSql = "select count(*) from tbl_mcht_hang where mcht_no='"
				+ mchtno + "'" + " and trans_date='" + transdateend
				+ "' and hang_flag in('0','1','3') ";
		if (!"0".equals(CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				mchtSql))) {
			ret[0] = algoListNew;
			ret[1] = "0";
			// ret[0]="该商户已经按照商户进行了挂账";
			return ret;
		}

		// 查出该交易日所有按终端挂账的终端号 （待审核、挂账、解挂待审核）
		String termSql = "select term_id from tbl_term_hang where trans_date='"
				+ transdateend + "' and hang_flag in('0','1','3') ";
		List termList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(termSql);
		// 将已经安装终端挂账的 终端信息存放到 map中,以便下边对 需要进行挂账的交易 进行过滤
		if (termList != null && termList.size() > 0) {
			for (Object obj : termList) {
				termMap.put(obj, "");
			}
		}

		// 查出该商户该交易日 按交易挂账的数据 （待审核、挂账、解挂待审核）
		StringBuffer tranHang = new StringBuffer();
		tranHang.append(
				" select flow_num||txn_ssn||mcht_no from Tbl_Trans_Hang  where mcht_no='")
				.append(mchtno)
				.append("' and HANG_FLAG in ('0','1','3') and trans_date in ('")
				.append(startDate.substring(0, 8)).append("','")
				.append(endDate.substring(0, 8)).append("')");
		List tranList = CommonFunction.getCommQueryDAO().findBySQLQuery(
				tranHang.toString());
		if (tranList != null && tranList.size() > 0) {
			for (Object obj : tranList) {
				transMap.put(obj, "");
			}
		}

		// rcvg_code 交易渠道-中行
		// 从入网机构中查出各入网机构的机构代码、日切时间
		StringBuffer agenSql = new StringBuffer();
		agenSql.append("select AGEN_ID,EXTENSION_FIELD1 from TBL_AGENCY_INFO_TRUE where statue='1'");
		List<Object[]> agenList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(agenSql.toString());
		if (agenList == null || agenList.size() == 0) {
			// 入网机构不存在 则直接返回空
			ret[0] = algoListNew;
			ret[1] = "0";
			return ret;
		}
		// 清分清算表中的查询条件
		String whereSql = "";
		// 清分清算表中的查询语句
		StringBuffer algoSql = new StringBuffer();
		// 交易流水表中的查询条件
		StringBuffer whereTxn = new StringBuffer();

		/*
		 * if (isNotEmpty(request.getParameter("transdatestart"))) { whereSql +=
		 * " AND TRANS_DATE >= '" + request.getParameter("transdatestart") +
		 * "'"; whereTxn.append(" and (substr(inst_date,0,8))>='").append(
		 * request.getParameter("transdatestart")).append("'"); } if
		 * (isNotEmpty(request.getParameter("transdateend"))) { whereSql +=
		 * " AND TRANS_DATE <= '" + request.getParameter("transdateend") + "'";
		 * whereTxn.append(" and (substr(inst_date,0,8))<='").append(
		 * request.getParameter("transdateend")).append("'"); }
		 */

		if (isNotEmpty(request.getParameter("termid"))) {
			whereSql += " AND TERM_ID = '" + request.getParameter("termid")
					+ "' ";
			whereTxn.append(" and card_accp_term_id='")
					.append(request.getParameter("termid").trim()
							.replace("'", "''")).append("'");
		}
		if (isNotEmpty(request.getParameter("txnnum"))) {
			whereSql += " AND TXN_NUM = '" + request.getParameter("txnnum")
					+ "' ";
			whereTxn.append(" and TXN_NUM='")
					.append(request.getParameter("txnnum").trim()).append("'");
		}
		if (isNotEmpty(request.getParameter("pan"))) {
			whereSql += " AND PAN like '%" + request.getParameter("pan")
					+ "%' ";
			whereTxn.append(" and pan like '%")
					.append(request.getParameter("pan").trim()).append("%'");
		}
		if (isNotEmpty(request.getParameter("txnssn"))) {// 系统参考号
			whereSql += " AND (substr(MISC_1,0,12))= '"
					+ request.getParameter("txnssn") + "' ";
			whereTxn.append(" and RETRIVL_REF='")
					.append(request.getParameter("txnssn")).append("'");
		}
		if (isNotEmpty(request.getParameter("termssn"))) {
			whereSql += " AND trim(TERM_SSN) = '"
					+ request.getParameter("termssn").trim() + "' ";
			whereTxn.append(" and trim(TERM_SSN)='")
					.append(request.getParameter("termssn").trim()).append("'");
		}

		if (isNotEmpty(request.getParameter("transamtsmall"))) {
			String transamtsmall1 = Double.parseDouble(request
					.getParameter("transamtsmall")) * 100 + "";
			String transamtsmall2 = transamtsmall1.substring(0,
					transamtsmall1.indexOf("."));
			String transamtsmall = CommonFunction.fillString(transamtsmall2,
					'0', 12, false);
			whereSql += " AND TRANS_AMT >= '" + transamtsmall + "'";
			whereTxn.append(" AND AMT_TRANS>='").append(transamtsmall)
					.append("'");
		}
		if (isNotEmpty(request.getParameter("transamtbig"))) {
			String transamtbig1 = Double.parseDouble(request
					.getParameter("transamtbig")) * 100 + "";
			String transamtbig2 = transamtbig1.substring(0,
					transamtbig1.indexOf("."));
			String transamtbig = CommonFunction.fillString(transamtbig2, '0',
					12, false);
			whereSql += " AND TRANS_AMT<= '" + transamtbig + "'";
			whereTxn.append(" AND AMT_TRANS<='").append(transamtbig)
					.append("'");
		}
		// 去清分清算表中查询，该商户在该交易日是否已经进行了清分
		String isAlgoSql = "";
		for (Object[] obj : agenList) {// 不同入网机构的日切时间点不一样
			// 获取日切时间点 (20131022 sql拼接有问题)
			startDate = StringUtil.getCutTime(
					CommonFunction.getBeforeDate(transdateend, -1),
					String.valueOf(obj[1]));
			endDate = StringUtil.getCutTime(transdateend,
					String.valueOf(obj[1]));
			isAlgoSql = "select count(*) as total from TBL_ALGO_DTL where MCHT_CD='"
					+ mchtno
					+ "' "
					+ "and trim(stlm_ins_id_cd)='"
					+ obj[0]
					+ "'  "
					+ "and TRANS_DATE||TRANS_DATE_TIME>'"
					+ startDate
					+ "' and TRANS_DATE||TRANS_DATE_TIME<='" + endDate + "'";
			isAlgoSql += " union";
		}
		isAlgoSql = "select sum(total) from ("
				+ isAlgoSql.toString().substring(0,
						isAlgoSql.toString().length() - 5) + ")";

		if (!"0".equals(CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				isAlgoSql))) {
			// 如果交易日期大于T-1 则直接去清分清算表中查询待挂账的交易
			// if(StringUtil.daysOfTwo(CommonFunction.getCurrentDate(),
			// transdateend)>1){
			Log.log("从清分清算表中获取待挂账数据");
			for (Object[] obj : agenList) {
				// 获取日切时间点
				startDate = StringUtil.getCutTime(
						CommonFunction.getBeforeDate(transdateend, -1),
						String.valueOf(obj[1]));
				endDate = StringUtil.getCutTime(transdateend,
						String.valueOf(obj[1]));
				// 清分清算表查询数据
				algoSql.append(
						" select MCHT_CD,m.MCHT_NM,TERM_ID,TXN_NUM,TRANS_DATE,PAN,TRANS_AMT,(substr(MISC_1,0,12)) MISC_1,TERM_SSN,TXN_SSN,"
								+ "TRANS_DATE_time from TBL_ALGO_DTL,TBL_MCHT_BASE_INF m where MCHT_CD=m.MCHT_NO and MCHT_CD='")
						.append(mchtno).append("'").append(" and txn_num in ")
						.append(txnNums).append(" and stlm_flg in ('0','3') ")
						.append(whereSql).append(" and trim(stlm_ins_id_cd)='")
						.append(obj[0]).append("'");
				/*
				 * //对跨年的交易进行特殊处理 清分清算表中未YYYYMMDDHHSSMi格式
				 * if("1231".equals(startDate.substring(4,
				 * 8))&&"0101".equals(endDate.substring(4, 8))){
				 * algoSql.append(" and (TRANS_DATE||TRANS_DATE_TIME>'"
				 * ).append(startDate
				 * ).append("' or TRANS_DATE||TRANS_DATE_TIME<='")
				 * .append(endDate).append("') "); }else{
				 */
				algoSql.append(" and TRANS_DATE||TRANS_DATE_TIME>'")
						.append(startDate)
						.append("' and TRANS_DATE||TRANS_DATE_TIME<='")
						.append(endDate).append("' ");
				// }
				algoSql.append(" union");
			}
			if (algoSql.toString().length() > 0) {
				String sql = algoSql.toString().substring(0,
						algoSql.toString().length() - 5)
						+ " order by txn_ssn ";
				List<Object[]> algoList = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(sql);
				if (algoList == null || algoList.size() == 0) {// 没有可挂账的交易
																// 则直接返回结果
					ret[0] = algoList;
					ret[1] = 0;
					return ret;
				} else {// 有值 则需要过滤掉已经存在的待审核的数据
					for (Object[] obj : algoList) {
						// 如果该记录已经在终端挂账中则 过滤掉
						if (termMap.get(obj[1]) != null)
							continue;
						// 如果该条记录在交易挂账中 则过滤掉
						if (transMap.get((String) obj[6] + (String) obj[8]
								+ (String) obj[0]) != null)
							continue;
						algoListNew.add(obj);
					}
				}
			}
		} else {// 从交易流水表中查询数据
			Log.log("从交易流水表中获取待挂账数据");
			// 查出可挂账的交易流水 只查询出交易成功的流水 并且被冲正、被撤销的原交易也需过滤掉
			StringBuffer txnSql = new StringBuffer();
			txnSql.append(
					" select card_accp_id,m.MCHT_NM,card_accp_term_id,TXN_NUM,(substr(inst_date,0,8)),PAN,AMT_TRANS,RETRIVL_REF,"
							+ "TERM_SSN,SYS_SEQ_NUM,(substr(inst_date,9,14)) from tbl_n_txn t,TBL_MCHT_BASE_INF m where card_accp_id=m.MCHT_NO ")
					.append(" and card_accp_id='").append(mchtno)
					.append("' and txn_num in ").append(txnNums)
					.append(" and  resp_code = '00' and revsal_flag<>'1'")
					.append(" and cancel_flag<>'1' ")
					.append(whereTxn.toString()).append(" and ");
			StringBuffer txnSqlWhere = new StringBuffer();
			txnSqlWhere.append("(");
			for (Object[] obj : agenList) {
				// 获取日切时间点
				startDate = StringUtil.getCutTime(
						CommonFunction.getBeforeDate(transdateend, -1),
						String.valueOf(obj[1]));
				endDate = StringUtil.getCutTime(transdateend,
						String.valueOf(obj[1]));

				txnSqlWhere.append("(trim(rcvg_code)='").append(obj[0])
						.append("' ");
				// 对跨年的交易进行特殊处理 流水表中为 MMDDHHssmi
				if ("1231".equals(startDate.substring(4, 8))
						&& "0101".equals(endDate.substring(4, 8))) {
					txnSqlWhere
							.append(" and (date_local_trans||time_local_trans>'")
							.append(startDate.substring(4))
							.append("' or date_local_trans||time_local_trans<='")
							.append(endDate.substring(4)).append("') ");
				} else {
					txnSqlWhere
							.append(" and date_local_trans||time_local_trans>'")
							.append(startDate.substring(4))
							.append("' and date_local_trans||time_local_trans<='")
							.append(endDate.substring(4)).append("' ");
				}
				txnSqlWhere.append(") or ");
			}
			if (txnSqlWhere.toString().length() > 3) {
				String tSql = txnSql.toString()
						+ txnSqlWhere.toString().substring(0,
								txnSqlWhere.toString().length() - 3)
						+ ") order by SYS_SEQ_NUM";
				List<Object[]> txnList = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(tSql);
				if (txnList == null || txnList.size() == 0) {// 没有可挂账的交易 则直接返回结果
					ret[0] = txnList;
					ret[1] = 0;
					return ret;
				} else {// 有值 则需要过滤掉已经存在的待审核的数据
					String sql = "";
					for (Object[] obj : txnList) {
						// 如果该记录已经在终端挂账中则 过滤掉
						if (termMap.get(obj[1]) != null)
							continue;
						// 如果该条记录在交易挂账中 则过滤掉
						if (transMap.get((String) obj[7] + (String) obj[9]
								+ (String) obj[0]) != null)
							continue;
						// 去清分清算表中查询该笔交易是否已经清算
						sql = "select count(*) from TBL_ALGO_DTL where MCHT_CD='"
								+ obj[0]
								+ "' and TRANS_DATE='"
								+ obj[3]
								+ "' and TXN_SSN='"
								+ obj[8]
								+ "' and TERM_SSN='"
								+ obj[7]
								+ "' and (substr(MISC_1,0,12))='"
								+ obj[6]
								+ "' and stlm_flg in ('1','2') ";
						if (!"0".equals(CommonFunction.getCommQueryDAO()
								.findCountBySQLQuery(sql)))
							continue;
						algoListNew.add(obj);
					}
				}
			}
		}
		if (algoListNew.size() == 0) {
			ret[0] = algoListNew;
			ret[1] = 0;
			return ret;
		} else {
			// 对List进行分页
			ret[0] = algoListNew;
			ret[1] = algoListNew.size();
			return ret;
		}

		/*
		 * // 先判断该商户需要挂账的记录是否在清分清算表中 String sql1 =
		 * "select count(*) from  tbl_algo_dtl where MCHT_CD='" + mchtno + "' ";
		 * sql1 += whereSql; String total =
		 * CommonFunction.getCommQueryDAO().findCountBySQLQuery( sql1); if
		 * (!"0".equals(total)) {// 商户交易记录已经在清分清算表中 whereSql +=
		 * " and stlm_flg in ('0','3')"; String sql =
		 * "select MCHT_CD,TERM_ID,TXN_NUM,TRANS_DATE,PAN,TRANS_AMT,(substr(MISC_1,0,12)) MISC_1,TERM_SSN,TXN_SSN from TBL_ALGO_DTL where MCHT_CD='"
		 * + mchtno + "' " + whereSql; String countSql =
		 * "SELECT COUNT(*) FROM (" + sql + ")"; List<Object[]> dataList =
		 * CommonFunction.getCommQueryDAO() .findBySQLQuery(sql, begin,
		 * Constants.QUERY_RECORD_COUNT); String count =
		 * CommonFunction.getCommQueryDAO() .findCountBySQLQuery(countSql);
		 * ret[0] = dataList; ret[1] = count; } else {//
		 * 商户交易记录不在清分清算表中，则去交易流水表中查找 String sql =
		 * "select card_accp_id,card_accp_term_id,TXN_NUM,(substr(inst_date,0,8)),"
		 * +
		 * "PAN,AMT_TRANS,RETRIVL_REF,TERM_SSN,SYS_SEQ_NUM from tbl_n_txn t where card_accp_id='"
		 * + mchtno + "' " + whereTxn.toString(); sql+=
		 * " And t.resp_code = '00' and revsal_flag<>'1' And txn_num <>'1021' and txn_num<>'8311' and txn_num not in "
		 * +SysParamUtil.getParam("REVSALCODE");//过滤掉失败的交易以及冲正的交易 String
		 * countSql = "select count(*) from (" + sql + ")"; List<Object[]>
		 * dataList = CommonFunction.getCommQueryDAO() .findBySQLQuery(sql,
		 * begin, Constants.QUERY_RECORD_COUNT); String count =
		 * CommonFunction.getCommQueryDAO() .findCountBySQLQuery(countSql);
		 * ret[0] = dataList; ret[1] = count; } return ret;
		 */
	}

	/**
	 * 按交易查询挂账的信息
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getAlgoDtlJie(int begin, HttpServletRequest request) {
		String whereSql = "";// 清分清算表中的查询条件
		StringBuffer whereTxn = new StringBuffer();// 交易流水表中的查询条件
		Object[] ret = new Object[2];
		String mchtno = request.getParameter("mchtno");
		String transDate = request.getParameter("transdateend");
		if (isNotEmpty(request.getParameter("termid"))) {
			whereSql += " AND TERM_ID = '" + request.getParameter("termid")
					+ "' ";
			whereTxn.append(" and card_accp_term_id='")
					.append(request.getParameter("termid").trim()
							.replace("'", "''")).append("'");
		}
		if (isNotEmpty(request.getParameter("txnnum"))) {
			whereSql += " AND TXN_NUM = '" + request.getParameter("txnnum")
					+ "' ";
			whereTxn.append(" and TXN_NUM='")
					.append(request.getParameter("txnnum").trim()).append("'");
		}
		if (isNotEmpty(request.getParameter("pan"))) {
			whereSql += " AND PAN like '%" + request.getParameter("pan")
					+ "%' ";
			whereTxn.append(" and pan like '%")
					.append(request.getParameter("pan").trim()).append("%'");
		}
		if (isNotEmpty(request.getParameter("txnssn"))) {// 系统参考号
			whereSql += " AND (substr(MISC_1,0,12))= '"
					+ request.getParameter("txnssn") + "' ";
			whereTxn.append(" and RETRIVL_REF='")
					.append(request.getParameter("txnssn")).append("'");
		}
		if (isNotEmpty(request.getParameter("termssn"))) {
			whereSql += " AND TERM_SSN = '"
					+ request.getParameter("termssn").trim() + "' ";
			whereTxn.append(" and TERM_SSN='")
					.append(request.getParameter("termssn").trim()).append("'");
		}
		/*
		 * if (isNotEmpty(request.getParameter("transdatestart"))) { whereSql +=
		 * " AND TRANS_DATE >= '" + request.getParameter("transdatestart") +
		 * "'"; whereTxn.append(" and (substr(inst_date,0,8))>='").append(
		 * request.getParameter("transdatestart")).append("'"); }
		 */
		if (isNotEmpty(request.getParameter("transdateend"))) {
			whereSql += " AND TRANS_DATE = '"
					+ request.getParameter("transdateend") + "'";
			whereTxn.append(" and (substr(inst_date,0,8))='")
					.append(request.getParameter("transdateend")).append("'");
		}
		if (isNotEmpty(request.getParameter("transamtsmall"))) {
			String transamtsmall1 = Double.parseDouble(request
					.getParameter("transamtsmall")) * 100 + "";
			String transamtsmall2 = transamtsmall1.substring(0,
					transamtsmall1.indexOf("."));
			String transamtsmall = CommonFunction.fillString(transamtsmall2,
					'0', 12, false);
			whereSql += " AND TRANS_AMT >= '" + transamtsmall + "'";
			whereTxn.append(" AND AMT_TRANS>='").append(transamtsmall)
					.append("'");
		}
		if (isNotEmpty(request.getParameter("transamtbig"))) {
			String transamtbig1 = Double.parseDouble(request
					.getParameter("transamtbig")) * 100 + "";
			String transamtbig2 = transamtbig1.substring(0,
					transamtbig1.indexOf("."));
			String transamtbig = CommonFunction.fillString(transamtbig2, '0',
					12, false);
			whereSql += " AND TRANS_AMT<= '" + transamtbig + "'";
			whereTxn.append(" AND AMT_TRANS<='").append(transamtbig)
					.append("'");
		}
		whereSql += " and stlm_flg='2'";
		whereTxn.append(" and batch_flag='2' ");

		List<Object[]> algoListNew = new ArrayList<Object[]>();
		Map transMap = new HashMap();
		// 查出该商户该交易日 按交易挂账的数据 （待审核、挂账、解挂待审核）
		StringBuffer tranHang = new StringBuffer();
		tranHang.append(
				" select flow_num||txn_ssn||mcht_no from Tbl_Trans_Hang  where mcht_no='")
				.append(mchtno)
				.append("' and HANG_FLAG in ('2','3') and trans_date in ('")
				.append(transDate.substring(0, 8)).append("','")
				.append(transDate.substring(0, 8)).append("')");
		List tranList = CommonFunction.getCommQueryDAO().findBySQLQuery(
				tranHang.toString());
		if (tranList != null && tranList.size() > 0) {
			for (Object obj : tranList) {
				transMap.put(obj, "");
			}
		}

		// 先判断该商户需要挂账的记录是否在清分清算表中
		String sql1 = "select count(*) from  tbl_algo_dtl where MCHT_CD='"
				+ mchtno + "' " + whereSql;
		String total = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				sql1);

		if (!"0".equals(total)) {// 商户交易记录已经在清分清算表中
			Log.log("从清分清算表中查询待解挂交易");
			String sql = "select MCHT_CD,TERM_ID,TXN_NUM,TRANS_DATE,PAN,TRANS_AMT,(substr(MISC_1,0,12)) MISC_1,TERM_SSN,TXN_SSN,trans_date_time"
					+ " from TBL_ALGO_DTL where MCHT_CD='"
					+ mchtno
					+ "'"
					+ whereSql;
			// String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
			// List<Object[]> dataList =
			// CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin,
			// Constants.QUERY_RECORD_COUNT);
			List<Object[]> dataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);// 20120914
			// String count =
			// CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
			if (dataList == null || dataList.size() == 0) {// 没有可解账的交易 则直接返回结果
				ret[0] = dataList;
				ret[1] = 0;
				return ret;
			} else {// 有值 则需要过滤掉已经存在的待审核的数据
				for (Object[] obj : dataList) {
					// 如果该条记录在交易解账中 则过滤掉
					if (transMap.get((String) obj[6] + (String) obj[8]
							+ (String) obj[0]) != null)
						continue;
					algoListNew.add(obj);
				}
			}
			ret[0] = algoListNew;
			ret[1] = algoListNew.size();
			return ret;
		} else {// 商户交易记录不在清分清算表中，则去交易流水表中查找
			String sql = "select card_accp_id,card_accp_term_id,TXN_NUM,(substr(inst_date,0,8)),PAN,AMT_TRANS,RETRIVL_REF,TERM_SSN,SYS_SEQ_NUM,"
					+ "(substr(inst_date,9,14)) from tbl_n_txn t where card_accp_id='"
					+ mchtno + "' " + whereTxn.toString();
			sql += " And t.resp_code = '00' and revsal_flag<>'1' And txn_num in ('1101','1091') ";// 过滤掉失败的交易以及冲正的交易
			// String countSql = "select count(*) from (" + sql + ")";
			// List<Object[]> dataList =
			// CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin,
			// Constants.QUERY_RECORD_COUNT);
			List<Object[]> dataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);
			// String count =
			// CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
			if (dataList == null || dataList.size() == 0) {// 没有可解账的交易 则直接返回结果
				ret[0] = dataList;
				ret[1] = 0;
				return ret;
			} else {// 有值 则需要过滤掉已经存在的待审核的数据
				for (Object[] obj : dataList) {
					// 如果该条记录在交易解账中则过滤掉
					if (transMap.get((String) obj[6] + (String) obj[8]
							+ (String) obj[0]) != null)
						continue;
					algoListNew.add(obj);
				}
			}
			ret[0] = algoListNew;
			ret[1] = algoListNew.size();
			return ret;
		}
		// if(algoListNew.size()==0){
		// ret[0]=algoListNew;
		// ret[1]=0;
		// return ret;
		// }else{
		// //对List进行分页
		// int count=algoListNew.size();
		// ret[0]=algoListNew;
		// ret[1]=count;
		// return ret;
		// }
		// return ret;
	}

	/**
	 * 按交易挂账审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTranShangShenHe(int begin,
			HttpServletRequest request) {
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select a.MCHT_NO,FLOW_NUM,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK,TXN_SSN,txn_num,trans_amt,term_id,m.mcht_nm "
				+ "from TBL_TRANS_HANG a,tbl_mcht_base_inf m where a.mcht_no=m.mcht_no and HANG_FLAG='0'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		sql += " order by HANG_DATE desc ,a.MCHT_NO";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按终端解挂审核
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermhangShenHeJie(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select TERM_ID,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK from TBL_TERM_HANG where HANG_FLAG='3'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按终端挂账审核
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermhangShenHe(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select TERM_ID,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK from TBL_TERM_HANG where HANG_FLAG='0'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按商户挂账审核
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchthangShenHe(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select a.MCHT_NO,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK,m.mcht_nm from TBL_MCHT_HANG a,tbl_mcht_base_inf m "
				+ "where a.MCHT_NO=m.MCHT_NO and a.HANG_FLAG='0'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按终端解挂,查询挂账的
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermHang(int begin, HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select TERM_ID,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK from TBL_TERM_HANG where HANG_FLAG='1'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按商户解挂,查询挂账的
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchthang(int begin, HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select t.MCHT_NO,m.MCHT_NM,t.TRANS_DATE,t.HANG_FLAG,t.HANG_DATE,t.RELIEVE_DATE,t.REMARK "
				+ "from TBL_MCHT_HANG t,TBL_MCHT_BASE_INF m where t.MCHT_NO=m.MCHT_NO and t.HANG_FLAG='1'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按商户解挂审核
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getMchthangJie(int begin, HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select MCHT_NO,TRANS_DATE,HANG_FLAG,HANG_DATE,RELIEVE_DATE,REMARK from TBL_MCHT_HANG where HANG_FLAG='3'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 按交易解挂审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTranShangShenHeJie(int begin,
			HttpServletRequest request) {
		// Operator operator = (Operator)
		// request.getSession().getAttribute(Constants.OPERATOR_INFO);
		// String whereSql = "";
		// if (isNotEmpty(request.getParameter("idStatus"))) {
		// whereSql += " AND SA_SATUTE = '" + request.getParameter("idStatus")
		// + "' ";
		// }
		// if (isNotEmpty(request.getParameter("idmchtid"))) {
		// whereSql += " AND MCHT_NO = '"
		// + request.getParameter("idmchtid") + "' ";
		// }
		// whereSql +=" Order By SA_SATUTE";
		Object[] ret = new Object[2];
		String sql = "select t.MCHT_NO,t.FLOW_NUM,t.TRANS_DATE,t.HANG_FLAG,t.HANG_DATE,t.RELIEVE_DATE,t.REMARK,t.TXN_SSN,m.mcht_nm "
				+ "from TBL_TRANS_HANG t,tbl_mcht_base_inf m where t.HANG_FLAG='3' and t.MCHT_NO=m.MCHT_NO ";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getTblInfsettle(int begin, HttpServletRequest request) {
		/*
		 * Operator operator = (Operator) request.getSession().getAttribute(
		 * Constants.OPERATOR_INFO);
		 */
		Object[] ret = new Object[2];

		// String sql =
		// "SELECT MCHT_NO,TERM_ID,SETTLE_RPT,CURR_ACCOUNT,SETTLE_BANK_NM,SETTLE_BANK_NO,COMP_ACCOUNT_BANK_CODE,"
		// +
		// "COMP_ACCOUNT_BANK_NAME,SETTLE_ACCT_NM,SETTLE_ACCT,BANK_ACCOUNT_CODE,CORP_BANK_NAME,FEE_ACCT_NM,"
		// +
		// "FEE_ACCT,DIR_BANK_CODE,DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG,SETTLE_TYPE,SETTLE_CHN"
		// + " from TBL_MCHT_SETTLE_INF_TMP where MCHT_NO='"
		// + request.getParameter("mchtNo") + "'";
		//COMP_ACCOUNT_BANK_CODE  对公开户行行号
		//BANK_ACCOUNT_CODE       对私开户行行号
		String sql = "SELECT MCHT_NO,TERM_ID,SETTLE_RPT,CURR_ACCOUNT,SETTLE_BANK_NM,SETTLE_BANK_NO,COMP_ACCOUNT_BANK_CODE,"
				+ "COMP_ACCOUNT_BANK_NAME,COMPANY_NAM,SETTLE_ACCT,BANK_ACCOUNT_CODE,CORP_BANK_NAME,LEGAL_NAM,"
				+ "FEE_ACCT,DIR_BANK_CODE,DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT,DIR_FLAG,AUTO_FLAG,HOLIDAY_SET_FLAG,CRE_FLAG,RETURN_FEE_FLAG,SETTLE_TYPE,SETTLE_CHN,DIR_OPEN_BANK , DIR_BANK_PROVINCE , DIR_BANK_CITY , COMP_OPEN_BANK , COMP_BANK_PROVINCE , COMP_BANK_CITY , CORP_OPEN_BANK , CORP_BANK_PROVINCE , CORP_BANK_CITY "
				+ " from TBL_MCHT_SETTLE_INF_TMP where MCHT_NO='"
				+ request.getParameter("mchtNo") + "'";
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {
			sql += " and TERM_ID='"
					+ request.getParameter("termId").trim().replace("'", "''")
					+ "'";
		}
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户费率
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblInfAlgo2(int begin, HttpServletRequest request) {
		/*
		 * Operator operator = (Operator) request.getSession().getAttribute(
		 * Constants.OPERATOR_INFO);
		 */
		Object[] ret = new Object[2];

		String sql = "select MCHT_NO,TMP_NO,TERM_ID,FEE_TYPE,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM,DISC_ID,sa_satute "
				+ "from TBL_HIS_DISC_ALGO2_TMP where tmp_No='"
				+ request.getParameter("tmpNo")
				+ "' and sa_satute in('0','2','3','4') ";
		if (!StringUtil.isEmpty(request.getParameter("termId"))) {
			sql += " and term_id='"
					+ request.getParameter("termId").trim().replace("'", "''")
					+ "'";
		}
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	// 添加机构中的发卡行添加
	public static Object[] getBankCode(int begin, HttpServletRequest request) {
		/*
		 * Operator operator = (Operator) request.getSession().getAttribute(
		 * Constants.OPERATOR_INFO);
		 */
		Object[] ret = new Object[2];

		String sql = "select ID,TMP_NO,INST_CODE,BANK_CODE,state "
				+ "from TBL_Inst_Bdb_Bank_Code_TMP where tmp_No='"
				+ request.getParameter("tmpNo") + "'";
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	// 添加机构中的发卡行添加（修改表单）
	public static Object[] getBankCode2(int begin, HttpServletRequest request) {
		/*
		 * Operator operator = (Operator) request.getSession().getAttribute(
		 * Constants.OPERATOR_INFO);
		 */
		Object[] ret = new Object[2];

		String sql = "select ID,TMP_NO,INST_CODE,BANK_CODE,state "
				+ "from TBL_Inst_Bdb_Bank_Code_TMP ";

		if (!StringUtil.isEmpty(request.getParameter("agenid"))) {
			sql += " where INST_CODE ='"
					+ request.getParameter("agenid").trim() + "'";
		}
		String countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getTblMsThread1(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "SELECT THREADS_NAME,THREADS_NUMBER,SHOULD_NUMBER,SERVER_NAME,THREAD_DIS from TBL_MS_THREADS where trim(server_name) ='"
				+ request.getParameter("flag") + "'";
		String countSql = "SELECT COUNT(*) FROM TBL_MS_THREADS where trim(server_name) ='"
				+ request.getParameter("flag") + "'";

		sql += " order by THREADS_NUMBER,SERVER_NAME asc ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * author:mzhu@ips.com 日终处理
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getNewBatMain(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String rzDATE = SysParamUtil.getParam("RZDATE");
		String startDate = "", endDate = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(calendar.getTime());
		endDate = str;
		Date dt2 = null, dt1 = null;

		calendar.add(Calendar.DATE, -8);
		str = sdf.format(calendar.getTime());

		try {
			dt2 = sdf.parse(rzDATE);
			dt1 = sdf.parse(str);

			if (dt1.getTime() < dt2.getTime()) {
				startDate = rzDATE;
			} else {
				startDate = str;
			}
			String sql = "select * from (select * from ( select t.new_date_settlmt,"
					+ "sum(decode(t.new_execute,'NewLoadtblNTxnfile',new_state,null)) as NewLoadtblNTxnfile,"
					+ "sum(decode(t.new_execute,'NewCalcCharge',new_state,null)) as NewCalcCharge,"
					+ "sum(decode(t.new_execute,'NewBrchProc',new_state,null)) as NewBrchProc,"
					+ "sum(decode(t.new_execute,'NewCreateTblAlgoDtl',new_state,null)) as NewCreateTblAlgoDtl,"
					+ "sum(decode(t.new_execute,'NewSettlmt',new_state, null)) as NewSettlmt,"
					+ "sum(decode(t.new_execute,'NewDayFile',new_state, null)) as NewDayFile,"
					+ "sum(decode(t.new_execute,'NewcreateTblNTxn',new_state,null)) as NewcreateTblNTxn"
					+ " from bth_new_bat_main t group by t.new_date_settlmt ) t"
					+ " left join "
					+ "(select monitor_date,"
					+ "sum(decode(t.monitor_name,'memory_2',monitor_status,null)) as memory_2,"
					+ "sum(decode(t.monitor_name,'memory_1',monitor_status,null)) as memory_1,"
					+ "sum(decode(t.monitor_name,'fileback_1',monitor_status,null)) as fileback_1,"
					+ "sum(decode(t.monitor_name,'fileback_2',monitor_status,null)) as fileback_2"
					+ " from tbl_ips_monitor t group by t.monitor_date ) t1 on t.new_date_settlmt = t1.monitor_date) where trim(new_date_settlmt) >= "
					+ "'"
					+ startDate
					+ "' and trim(new_date_settlmt) <='"
					+ endDate + "' order by new_date_settlmt desc";

			List<Object[]> dataList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);

			Object[] data;
			for (int i = 0; i < dataList.size(); i++) {
				data = dataList.get(i);
				dataList.set(i, data);
			}
			// String count = CommonFunction.getCommQueryDAO()
			// .findCountBySQLQuery(countSql);
			ret[0] = dataList;
			ret[1] = 0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 联机交易监控的查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblOnlineTransMonitor(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer("");

		if (isNotEmpty(request.getParameter("instId"))) {// 机构
			whereSql.append(" and trim(t.inst_ID) = '"
					+ request.getParameter("instId").split("-")[0] + "'");
		}
		if (isNotEmpty(request.getParameter("startTime"))) {// 起始日期
			whereSql.append(" and t.Monitor_Time >= '"
					+ request.getParameter("startTime").split("T")[0].replace(
							"-", "") + "000000" + "' ");
		}
		if (isNotEmpty(request.getParameter("endTime"))) {// 结束日期
			whereSql.append(" and t.Monitor_Time <= '"
					+ request.getParameter("endTime").split("T")[0].replace(
							"-", "") + "235959" + "' ");
		}
		String sql = "SELECT t.Monitor_Time,substr((t.Failure_Count/t.Sum_Count),0,6)*100||'%' as FailurePro,t.Failure_Count,t.Sum_Count"
				+ ",t.Trans_Type,t.Inst_ID||'-'||c.VALUE as InstID from TBL_Online_Trans_Monitor t,CST_SYS_PARAM c "
				+ " where c.owner='ORGCODE' and c.KEY=t.Inst_ID " + whereSql;
		String countSql = "SELECT COUNT(*) FROM TBL_Online_Trans_Monitor t where 1=1 "
				+ whereSql;

		sql += " order by Monitor_Time desc";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 终端通道配置信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermChannelInf(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		// 区分中行、工行、农行、建行
		if (isNotEmpty(request.getParameter("insType"))) {
			whereSql.append(" and t.TERM_INS = (SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"
					+ request.getParameter("insType") + "') ");
		}
		if (isNotEmpty(request.getParameter("termIns"))) {
			whereSql.append(" and t.TERM_INS = '"
					+ request.getParameter("termIns") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtId"))) {
			whereSql.append(" and t.MCHT_ID = '"
					+ request.getParameter("mchtId") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtTermId"))) {
			whereSql.append(" and t.MCHT_TERM_ID = '"
					+ request.getParameter("mchtTermId") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcc"))) {
			whereSql.append(" and t.INS_MCC = '"
					+ request.getParameter("insMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcht"))) {
			whereSql.append(" and t.INS_MCHT like '%"
					+ request.getParameter("insMcht") + "%' ");
		}
		if (isNotEmpty(request.getParameter("insTerm"))) {
			whereSql.append(" and t.INS_TERM like '%"
					+ request.getParameter("insTerm") + "%' ");
		}
		if (isNotEmpty(request.getParameter("stat"))) {
			whereSql.append(" and t.STAT = '" + request.getParameter("stat")
					+ "' ");
		}
		if (isNotEmpty(request.getParameter("channelType"))) {
			whereSql.append(" and t.CHANNEL_TYPE = '"
					+ request.getParameter("channelType") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and substr(t.CRE_TIME,1,8) <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT t.ID,t.TERM_INS,t.MCHT_MCC,t.MCHT_ID,t.MCHT_TERM_ID,t.INS_MCC,t.INS_MCHT,t.INS_TERM,t.STAT,t.CRE_TIME,t.CRE_OPR_ID,t.MODI_TIME,"
				+ "t.MODI_OPR_ID,t.CHECK_TIME,t.CHECK_OPR_ID,t.LMK,t.RESERVE_01,t.CHANNEL_TYPE,t.MAX_AMT/100,T.MIN_AMT/100 FROM TBL_TERM_CHANNEL_INF_TMP t where 1=1 "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_CHANNEL_INF_TMP t where 1=1 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的终端通道配置信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermChannelInfToCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		// 区分中行、工行、农行、建行
		if (isNotEmpty(request.getParameter("insType"))) {
			whereSql.append(" and t.TERM_INS = (SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"
					+ request.getParameter("insType") + "') ");
		}
		if (isNotEmpty(request.getParameter("termIns"))) {
			whereSql.append(" and t.TERM_INS = '"
					+ request.getParameter("termIns") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtId"))) {
			whereSql.append(" and t.MCHT_ID = '"
					+ request.getParameter("mchtId") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtTermId"))) {
			whereSql.append(" and t.MCHT_TERM_ID = '"
					+ request.getParameter("mchtTermId") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcc"))) {
			whereSql.append(" and t.INS_MCC = '"
					+ request.getParameter("insMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcht"))) {
			whereSql.append(" and t.INS_MCHT like '%"
					+ request.getParameter("insMcht") + "%' ");
		}
		if (isNotEmpty(request.getParameter("insTerm"))) {
			whereSql.append(" and t.INS_TERM like '%"
					+ request.getParameter("insTerm") + "%' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and substr(t.CRE_TIME,1,8) <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("channelType"))) {
			whereSql.append(" and t.CHANNEL_TYPE = '"
					+ request.getParameter("channelType") + "' ");
		}

		String sql = "SELECT t.ID,t.TERM_INS,t.MCHT_MCC,t.MCHT_ID,t.MCHT_TERM_ID,t.INS_MCC,t.INS_MCHT,t.INS_TERM,t.STAT,t.CRE_TIME,t.CRE_OPR_ID,t.MODI_TIME,"
				+ "t.MODI_OPR_ID,t.CHECK_TIME,t.CHECK_OPR_ID,t.LMK,t.RESERVE_01,t.CHANNEL_TYPE,t.MAX_AMT/100,T.MIN_AMT/100 FROM TBL_TERM_CHANNEL_INF_TMP t where t.STAT in('0','3','4') "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_CHANNEL_INF_TMP t where t.STAT in('0','3','4') "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 交行终端通道配置信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermChannelInf2(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("termIns"))) {
			whereSql.append(" and t.TERM_INS = '"
					+ request.getParameter("termIns") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtId"))) {
			whereSql.append(" and t.MCHT_ID = '"
					+ request.getParameter("mchtId") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtTermId"))) {
			whereSql.append(" and t.MCHT_TERM_ID = '"
					+ request.getParameter("mchtTermId") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcc"))) {
			whereSql.append(" and t.INS_MCC = '"
					+ request.getParameter("insMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcht"))) {
			whereSql.append(" and t.INS_MCHT_2 like '%"
					+ request.getParameter("insMcht") + "%' ");
		}
		if (isNotEmpty(request.getParameter("insTerm"))) {
			whereSql.append(" and t.INS_TERM_2 like '%"
					+ request.getParameter("insTerm") + "%' ");
		}
		if (isNotEmpty(request.getParameter("stat"))) {
			whereSql.append(" and t.STAT = '" + request.getParameter("stat")
					+ "' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and substr(t.CRE_TIME,1,8) <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT t.ID,t.TERM_INS,t.MCHT_MCC,t.MCHT_ID,t.MCHT_TERM_ID,t.INS_MCC,t.INS_MCHT,t.INS_TERM,"
				+ "t.CARD_TYPE,t.INS_MCHT_2,t.INS_TERM_2,t.STAT,t.CRE_TIME,t.CRE_OPR_ID,t.MODI_TIME,"
				+ "t.MODI_OPR_ID,t.CHECK_TIME,t.CHECK_OPR_ID,t.LMK,t.RESERVE_01 FROM TBL_TERM_CHANNEL_INF_2 t where 1=1 "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_CHANNEL_INF_2 t where 1=1 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 待审核的交行终端通道配置信息查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTermChannelInf2ToCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("termIns"))) {
			whereSql.append(" and t.TERM_INS = '"
					+ request.getParameter("termIns") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtMcc"))) {
			whereSql.append(" and t.MCHT_MCC = '"
					+ request.getParameter("mchtMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtId"))) {
			whereSql.append(" and t.MCHT_ID = '"
					+ request.getParameter("mchtId") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtTermId"))) {
			whereSql.append(" and t.MCHT_TERM_ID = '"
					+ request.getParameter("mchtTermId") + "' ");
		}
		if (isNotEmpty(request.getParameter("cardType"))) {
			whereSql.append(" and t.CARD_TYPE = '"
					+ request.getParameter("cardType") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcc"))) {
			whereSql.append(" and t.INS_MCC = '"
					+ request.getParameter("insMcc") + "' ");
		}
		if (isNotEmpty(request.getParameter("insMcht"))) {
			whereSql.append(" and t.INS_MCHT like '%"
					+ request.getParameter("insMcht") + "%' ");
		}
		if (isNotEmpty(request.getParameter("insTerm"))) {
			whereSql.append(" and t.INS_TERM like '%"
					+ request.getParameter("insTerm") + "%' ");
		}
		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and t.CRE_TIME >= '"
					+ request.getParameter("startDate") + "000000' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and substr(t.CRE_TIME,1,8) <= '"
					+ request.getParameter("endDate") + "' ");
		}

		String sql = "SELECT t.ID,t.TERM_INS,t.MCHT_MCC,t.MCHT_ID,t.MCHT_TERM_ID,t.INS_MCC,t.INS_MCHT,t.INS_TERM,"
				+ "t.CARD_TYPE,t.INS_MCHT_2,t.INS_TERM_2,t.STAT,t.CRE_TIME,t.CRE_OPR_ID,t.MODI_TIME,"
				+ "t.MODI_OPR_ID,t.CHECK_TIME,t.CHECK_OPR_ID,t.LMK,t.RESERVE_01 FROM TBL_TERM_CHANNEL_INF_2 t where t.STAT in('0','3','4') "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_TERM_CHANNEL_INF_2 t where t.STAT in('0','3','4') "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询补电子现金消费信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getElecCashInfo(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and e.TRANS_DATE >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and e.TRANS_DATE <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntNo"))) {
			whereSql.append(" and e.MCHT_CD = '"
					+ request.getParameter("mchntNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" and e.TERM_ID = '"
					+ request.getParameter("termId") + "' ");
		}
		if (isNotEmpty(request.getParameter("batchNo"))) {
			whereSql.append(" and e.TXN_BATCH_NO like '%"
					+ request.getParameter("batchNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("cerNo"))) {
			whereSql.append(" and e.TXN_CER_NO like '%"
					+ request.getParameter("cerNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("stat"))) {
			whereSql.append(" and e.SA_STATE = '"
					+ request.getParameter("stat") + "' ");
		}

		String sql = "SELECT e.ID,e.MCHT_CD,e.TERM_ID,e.ACQ_INS_ID_CD,e.BRH_INS_ID_CD,e.PAN,e.TXN_NUM,e.TXN_BATCH_NO,e.TXN_CER_NO,"
				+ "e.TRANS_AMT,e.TRANS_DATE,e.IC_CERT,e.TVR,e.TSI,e.AID,e.ATC,e.APP_TAG,e.APP_PRE_NAME,e.SA_STATE,e.REC_CRT_USR,e.REC_CRT_TS,"
				+ "e.REC_UPD_USR,e.REC_UPD_TS,e.REC_CHE_USR,e.REC_CHE_TS from TBL_ELEC_CASH_INF_TMP e where 1=1 "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_ELEC_CASH_INF_TMP e where 1=1 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询待审核补电子现金消费信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getElecCashInfoToCheck(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (!StringUtil.isNull(request.getParameter("startDate"))) {
			whereSql.append(" and e.TRANS_DATE >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (!StringUtil.isNull(request.getParameter("endDate"))) {
			whereSql.append(" and e.TRANS_DATE <= '"
					+ request.getParameter("endDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchntNo"))) {
			whereSql.append(" and e.MCHT_CD = '"
					+ request.getParameter("mchntNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("termId"))) {
			whereSql.append(" and e.TERM_ID = '"
					+ request.getParameter("termId") + "' ");
		}
		if (isNotEmpty(request.getParameter("batchNo"))) {
			whereSql.append(" and e.TXN_BATCH_NO like '%"
					+ request.getParameter("batchNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("cerNo"))) {
			whereSql.append(" and e.TXN_CER_NO like '%"
					+ request.getParameter("cerNo") + "%' ");
		}
		if (isNotEmpty(request.getParameter("stat"))) {
			whereSql.append(" and e.SA_STATE = '"
					+ request.getParameter("stat") + "' ");
		}

		String sql = "SELECT e.ID,e.MCHT_CD,e.TERM_ID,e.ACQ_INS_ID_CD,e.BRH_INS_ID_CD,e.PAN,e.TXN_NUM,e.TXN_BATCH_NO,e.TXN_CER_NO,"
				+ "e.TRANS_AMT,e.TRANS_DATE,e.IC_CERT,e.TVR,e.TSI,e.AID,e.ATC,e.APP_TAG,e.APP_PRE_NAME,e.SA_STATE,e.REC_CRT_USR,"
				+ "e.REC_CRT_TS,e.REC_UPD_USR,e.REC_UPD_TS,e.REC_CHE_USR,e.REC_CHE_TS "
				+ "from TBL_ELEC_CASH_INF_TMP e where e.SA_STATE in ('0','3','4') "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM TBL_ELEC_CASH_INF_TMP e where e.SA_STATE in ('0','3','4') "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 先结后算查询
	 * 
	 * @param begin
	 * @param request
	 * @return 2013-8-14 下午02:28:14
	 * @author zhangqi
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getBthNewBatMainInfo(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		StringBuffer whereSql = new StringBuffer("");

		if (isNotEmpty(request.getParameter("startTime"))) {// 起始日期
			whereSql.append(" and b.NEW_DATE_SETTLMT >= '"
					+ request.getParameter("startTime").split("T")[0].replace(
							"-", "") + "'");
		}
		if (isNotEmpty(request.getParameter("endTime"))) {// 结束日期
			whereSql.append(" and b.NEW_DATE_SETTLMT <= '"
					+ request.getParameter("endTime").split("T")[0].replace(
							"-", "") + "'");
		}
		String sql = "SELECT b.NEW_DATE_SETTLMT,b.NEW_EXECUTE,b.NEW_STATE,b.NEW_DIS from BTH_NEW_BAT_MAIN b where 1=1"
				+ whereSql;
		String countSql = "SELECT COUNT(*) FROM BTH_NEW_BAT_MAIN b where 1=1"
				+ whereSql;

		sql += " order by b.NEW_DATE_SETTLMT desc,(case b.NEW_EXECUTE "
				+ "when 'NewcreateTblNTxn' then 1 "
				+ "when 'NewLoadtblNTxnfile' then 2 "
				+ "when 'NewCalcCharge' then 3 " + "when 'NewBrchProc' then 4 "
				+ "when 'NewCreateTblAlgoDtl' then 5 "
				+ "when 'NewSettlmt' then 6 " + "when 'NewDayFile' then 7 end)";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		Object[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			dataList.set(i, data);
		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 带查询条件的地区代码查询
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getCityCodeInfoNew(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("cityCode"))) {
			whereSql.append(" and CITY_CODE like '%"
					+ request.getParameter("cityCode") + "%' ");
		}
		if (isNotEmpty(request.getParameter("cityName"))) {
			whereSql.append(" and CITY_DES like '%"
					+ request.getParameter("cityName") + "%' ");
		}

		String sql = "SELECT CITY_CODE,CITY_DES FROM TBL_CITY_CODE  where 1=1 "
				+ whereSql.toString();
		sql += " order by CITY_CODE";
		String countSql = "SELECT COUNT(*) FROM TBL_CITY_CODE where 1=1 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInf(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();

		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("saStatus"))) {
			whereSql.append(" and a.SA_STATUS = '"
					+ request.getParameter("saStatus") + "' ");
		}
		if (isNotEmpty(request.getParameter("sumrzDate"))) {
			whereSql.append(" and a.SUMRZ_DATE = '"
					+ request.getParameter("sumrzDate") + "' ");
		}
		String sql = "SELECT a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS FROM TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1  and a.SUM_AMT<>0 "
				+ whereSql.toString();
		sql += " order by a.INST_DATE,a.SEQ_NUM";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_SUMRZ_INF a where 1=1  and a.SUM_AMT<>0 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUp(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL )");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}
		String sql = "SELECT a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS FROM TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1  and a.SUM_AMT<>0 "
				+ whereSql.toString();
		sql += " order by a.INST_DATE,a.SEQ_NUM";
		String countSql = "SELECT COUNT(*) FROM TBL_MCHT_SUMRZ_INF a where 1=1  and a.SUM_AMT<>0 "
				+ whereSql.toString();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQ(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL )");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}
		String sql = "SELECT a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, a.CAUSE_STAT FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一次划款
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQaudit(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL )");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '" + request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '" + request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '" + request.getParameter("mchtNo") + "' ");
		}
		if (isNotEmpty(request.getParameter("settleRpt"))) {
			whereSql.append(" and c.SETTLE_RPT = '" + request.getParameter("settleRpt") + "' ");
		}
		String sql = "SELECT distinct a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.AUDIT_STATUS,a.AUDIT_ID,a.AUDIT_DATE,a.REC_ID,a.REC_DATE,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, c.COMP_ACCOUNT_BANK_CODE, c.BANK_ACCOUNT_CODE, a.CAUSE_STAT, a.ACCT_BANK_CODE FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 AND a.DEST_ID = '1708' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString() + " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		List<Object[]> datasList = null;

		for (Object[] objects : dataList) {
			Object mchtNo = objects[2];// 商户号
			Object settleRpt = objects[31];// 结算账户类型
			if (settleRpt.equals("1")) {// 对私
				datasList = CommonFunction.getCommQueryDAO().findBySQLQuery("select CORP_BANK_NAME,LEGAL_NAM,FEE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = " + mchtNo + "");
				Object[] objects2 = datasList.get(0);
				Object corpBankName = objects2[0];// 开户行名称
				Object feeAcctNm = objects2[1];// 账户名称
				Object feeAcct = objects2[2];// 账户账号
				objects[10] = feeAcctNm;
				objects[11] = feeAcct;
				objects[12] = corpBankName;
				// CORP_BANK_NAME对私开户行名称 FEE_ACCT_NM对私开户行账号 FEE_ACCT对私账户
				// select CORP_BANK_NAME,FEE_ACCT_NM,FEE_ACCT from
				// TBL_MCHT_SETTLE_INF where MCHT_NO = mchtNo
			} else if (settleRpt.equals("2")) {// 对公
				datasList = CommonFunction.getCommQueryDAO().findBySQLQuery("select COMP_ACCOUNT_BANK_NAME,COMPANY_NAM,SETTLE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = '" + mchtNo + "'");
				Object[] objects3 = datasList.get(0);
				Object compAccountBankName = objects3[0];// 开户行名称
				Object settleAcctNm = objects3[1];// 账户名称
				Object settleAcct = objects3[2];// 账户账号
				objects[10] = settleAcctNm;
				objects[11] = settleAcct;
				objects[12] = compAccountBankName;
			} else if (settleRpt.equals("3")) {// 定向
				datasList = CommonFunction.getCommQueryDAO().findBySQLQuery("select DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT from TBL_MCHT_SETTLE_INF where MCHT_NO = '" + mchtNo + "'");
				Object[] objects4 = datasList.get(0);
				Object dirBankNname = objects4[0];
				Object dirAaccountNname = objects4[1];
				Object dirAccount = objects4[2];
				objects[10] = dirAaccountNname;
				objects[11] = dirAccount;
				objects[12] = dirBankNname;
			}

		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 一次划款审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQauditS(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL ) ");
		whereSql.append(" and a.AUDIT_STATUS = '0' ");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}
		String sql = "SELECT distinct a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.AUDIT_STATUS,a.AUDIT_ID,a.AUDIT_DATE,a.REC_ID,a.REC_DATE,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, c.COMP_ACCOUNT_BANK_CODE, c.BANK_ACCOUNT_CODE, a.CAUSE_STAT, a.ACCT_BANK_CODE FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 AND a.DEST_ID = '1708' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		List<Object[]> datasList = null;

		for (Object[] objects : dataList) {
			Object mchtNo = objects[2];// 商户号
			Object settleRpt = objects[31];// 结算账户类型
			if (settleRpt.equals("1")) {// 对私
				datasList = CommonFunction
						.getCommQueryDAO()
						.findBySQLQuery(
								"select CORP_BANK_NAME,LEGAL_NAM,FEE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = "
										+ mchtNo + "");
				Object[] objects2 = datasList.get(0);
				Object corpBankName = objects2[0];
				Object feeAcctNm = objects2[1];
				Object feeAcct = objects2[2];
				objects[10] = feeAcctNm;
				objects[11] = feeAcct;
				objects[12] = corpBankName;
				// CORP_BANK_NAME对私开户行名称 FEE_ACCT_NM对私开户行账号 FEE_ACCT对私账户
				// select CORP_BANK_NAME,FEE_ACCT_NM,FEE_ACCT from
				// TBL_MCHT_SETTLE_INF where MCHT_NO = mchtNo
			} else if (settleRpt.equals("2")) {// 对公
				datasList = CommonFunction
						.getCommQueryDAO()
						.findBySQLQuery(
								"select COMP_ACCOUNT_BANK_NAME,COMPANY_NAM,SETTLE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = '"
										+ mchtNo + "'");
				Object[] objects3 = datasList.get(0);
				Object compAccountBankName = objects3[0];
				Object settleAcctNm = objects3[1];
				Object settleAcct = objects3[2];
				objects[10] = settleAcctNm;
				objects[11] = settleAcct;
				objects[12] = compAccountBankName;
			} else if (settleRpt.equals("3")) {// 定向
				datasList = CommonFunction
						.getCommQueryDAO()
						.findBySQLQuery(
								"select DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT from TBL_MCHT_SETTLE_INF where MCHT_NO = '"
										+ mchtNo + "'");
				Object[] objects4 = datasList.get(0);
				Object dirBankNname = objects4[0];
				Object dirAaccountNname = objects4[1];
				Object dirAccount = objects4[2];
				objects[10] = dirAaccountNname;
				objects[11] = dirAccount;
				objects[12] = dirBankNname;
			}

		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 二次划款审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQauditSE(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL ) ");
		whereSql.append(" and a.AUDIT_STATUS = '3' ");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}
		String sql = "SELECT distinct a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.AUDIT_STATUS,a.AUDIT_ID,a.AUDIT_DATE,a.REC_ID,a.REC_DATE,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, c.COMP_ACCOUNT_BANK_CODE, c.BANK_ACCOUNT_CODE, a.CAUSE_STAT, a.ACCT_BANK_CODE FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		List<Object[]> datasList = null;

		for (Object[] objects : dataList) {
			Object mchtNo = objects[2];// 商户号
			Object settleRpt = objects[31];// 结算账户类型
			if (settleRpt.equals("1")) {// 对私
				datasList = CommonFunction
						.getCommQueryDAO()
						.findBySQLQuery(
								"select CORP_BANK_NAME,LEGAL_NAM,FEE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = "
										+ mchtNo + "");
				Object[] objects2 = datasList.get(0);
				Object corpBankName = objects2[0];
				Object feeAcctNm = objects2[1];
				Object feeAcct = objects2[2];
				objects[10] = feeAcctNm;
				objects[11] = feeAcct;
				objects[12] = corpBankName;
				// CORP_BANK_NAME对私开户行名称 FEE_ACCT_NM对私开户行账号 FEE_ACCT对私账户
				// select CORP_BANK_NAME,FEE_ACCT_NM,FEE_ACCT from
				// TBL_MCHT_SETTLE_INF where MCHT_NO = mchtNo
			} else if (settleRpt.equals("2")) {// 对公
				datasList = CommonFunction
						.getCommQueryDAO()
						.findBySQLQuery(
								"select COMP_ACCOUNT_BANK_NAME,COMPANY_NAM,SETTLE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = '"
										+ mchtNo + "'");
				Object[] objects3 = datasList.get(0);
				Object compAccountBankName = objects3[0];
				Object settleAcctNm = objects3[1];
				Object settleAcct = objects3[2];
				objects[10] = settleAcctNm;
				objects[11] = settleAcct;
				objects[12] = compAccountBankName;
			} else if (settleRpt.equals("3")) {// 定向
				datasList = CommonFunction
						.getCommQueryDAO()
						.findBySQLQuery(
								"select DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT from TBL_MCHT_SETTLE_INF where MCHT_NO = '"
										+ mchtNo + "'");
				Object[] objects4 = datasList.get(0);
				Object dirBankNname = objects4[0];
				Object dirAaccountNname = objects4[1];
				Object dirAccount = objects4[2];
				objects[10] = dirAaccountNname;
				objects[11] = dirAccount;
				objects[12] = dirBankNname;
			}

		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQF(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		// whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL )");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}
		String sql = "SELECT a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, a.CAUSE_STAT FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 AND a.SA_STATUS = '0' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 二次划款
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQFe(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL )");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '" + request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '" + request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '" + request.getParameter("mchtNo") + "' ");
		}
		String sql = "SELECT distinct a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.AUDIT_STATUS,a.AUDIT_ID,a.AUDIT_DATE,a.REC_ID,a.REC_DATE,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, c.COMP_ACCOUNT_BANK_CODE, c.BANK_ACCOUNT_CODE, a.CAUSE_STAT FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 AND a.SA_STATUS = '0' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		List<Object[]> datasList = null;

		for (Object[] objects : dataList) {
			Object mchtNo = objects[2];// 商户号
			Object settleRpt = objects[31];// 结算账户类型
			if (settleRpt.equals("1")) {// 对私
				datasList = CommonFunction.getCommQueryDAO().findBySQLQuery("select CORP_BANK_NAME,LEGAL_NAM,FEE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = " + mchtNo + "");
				Object[] objects2 = datasList.get(0);
				Object corpBankName = objects2[0];
				Object feeAcctNm = objects2[1];
				Object feeAcct = objects2[2];
				objects[10] = feeAcctNm;
				objects[11] = feeAcct;
				objects[12] = corpBankName;
				// CORP_BANK_NAME对私开户行名称 FEE_ACCT_NM对私开户行账号 FEE_ACCT对私账户
				// select CORP_BANK_NAME,FEE_ACCT_NM,FEE_ACCT from
				// TBL_MCHT_SETTLE_INF where MCHT_NO = mchtNo
			} else if (settleRpt.equals("2")) {// 对公
				datasList = CommonFunction.getCommQueryDAO().findBySQLQuery("select COMP_ACCOUNT_BANK_NAME,COMPANY_NAM,SETTLE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = '" + mchtNo + "'");
				Object[] objects3 = datasList.get(0);
				Object compAccountBankName = objects3[0];
				Object settleAcctNm = objects3[1];
				Object settleAcct = objects3[2];
				objects[10] = settleAcctNm;
				objects[11] = settleAcct;
				objects[12] = compAccountBankName;
			} else if (settleRpt.equals("3")) {// 定向
				datasList = CommonFunction.getCommQueryDAO().findBySQLQuery("select DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT from TBL_MCHT_SETTLE_INF where MCHT_NO = '" + mchtNo + "'");
				Object[] objects4 = datasList.get(0);
				Object dirBankNname = objects4[0];
				Object dirAaccountNname = objects4[1];
				Object dirAccount = objects4[2];
				objects[10] = dirAaccountNname;
				objects[11] = dirAccount;
				objects[12] = dirBankNname;
			}

		}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户回填
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getSumrzInfUpYQFF(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer whereSql = new StringBuffer();
		// add lhf
		// whereSql.append(" and (a.SA_STATUS<>'1' or a.SA_STATUS IS NULL )");
		if (isNotEmpty(request.getParameter("instDate"))) {
			whereSql.append(" and a.INST_DATE = '"
					+ request.getParameter("instDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("accFlag"))) {
			whereSql.append(" and a.ACC_FLAG = '"
					+ request.getParameter("accFlag") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtNo"))) {
			whereSql.append(" and a.MCHT_NO = '"
					+ request.getParameter("mchtNo") + "' ");
		}

		// whereSql.append(" AND a.SA_STATUS = '2' ");

		String sql = "SELECT a.INST_DATE, a.SEQ_NUM,a.MCHT_NO,a.MCHT_NO||'-'||b.MCHT_NM,a.ACC_FLAG,a.SETTLE_ACC_NAME, a.SETTLE_ACC_NUM,a.BANK_NAME,a.TXN_AMT,a.HAND_AMT,a.SUM_AMT,a.SA_STATUS,a.SUMRZ_DATE,a.SUMRZ_NOTE,a.REC_UPD_OPR,a.REC_CRT_TS,a.REC_UPD_TS,  c.dir_open_bank, c.dir_bank_province, c.dir_bank_city, c.comp_open_bank, c.comp_bank_province, c.comp_bank_city, c.corp_open_bank, c.corp_bank_province, c.corp_bank_city, c.SETTLE_RPT, c.COMPANY_NAM, c.SETTLE_ACCT, c.LEGAL_NAM, c.FEE_ACCT, c.DIR_ACCOUNT_NAME, c.DIR_ACCOUNT, a.CAUSE_STAT, a.SUMRZ_BATCH FROM TBL_MCHT_SETTLE_INF c, TBL_MCHT_SUMRZ_INF a"
				+ " left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO where 1=1 AND c.MCHT_NO = a.MCHT_NO and a.SUM_AMT<>0 AND a.DEST_ID = '1708' "
				+ whereSql.toString();
		String countSql = "SELECT COUNT(*) FROM (" + sql + whereSql.toString()
				+ " order by a.INST_DATE DESC ) ";
		sql += " order by a.INST_DATE DESC";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT2);
		/*
		 * List<Object[]> datasList = null;
		 * 
		 * for (Object[] objects : dataList) { Object mchtNo = objects[2];//商户号
		 * Object settleRpt = objects[31];//结算账户类型 if (settleRpt.equals("1"))
		 * {//对私 datasList = CommonFunction.getCommQueryDAO().findBySQLQuery(
		 * "select CORP_BANK_NAME,LEGAL_NAM,FEE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = "
		 * +mchtNo+""); Object[] objects2 = datasList.get(0); Object
		 * corpBankName = objects2[0]; Object feeAcctNm = objects2[1]; Object
		 * feeAcct = objects2[2]; objects[10] = feeAcctNm; objects[11] =
		 * feeAcct; objects[12] = corpBankName; //CORP_BANK_NAME对私开户行名称
		 * FEE_ACCT_NM对私开户行账号 FEE_ACCT对私账户 //select
		 * CORP_BANK_NAME,FEE_ACCT_NM,FEE_ACCT from TBL_MCHT_SETTLE_INF where
		 * MCHT_NO = mchtNo }else if (settleRpt.equals("2")) {//对公 datasList =
		 * CommonFunction.getCommQueryDAO().findBySQLQuery(
		 * "select COMP_ACCOUNT_BANK_NAME,COMPANY_NAM,SETTLE_ACCT from TBL_MCHT_SETTLE_INF where MCHT_NO = '"
		 * +mchtNo+"'"); Object[] objects3 = datasList.get(0); Object
		 * compAccountBankName = objects3[0]; Object settleAcctNm = objects3[1];
		 * Object settleAcct = objects3[2]; objects[10] = settleAcctNm;
		 * objects[11] = settleAcct; objects[12] = compAccountBankName; }else if
		 * (settleRpt.equals("3")) {//定向 datasList =
		 * CommonFunction.getCommQueryDAO().findBySQLQuery(
		 * "select DIR_BANK_NAME,DIR_ACCOUNT_NAME,DIR_ACCOUNT from TBL_MCHT_SETTLE_INF where MCHT_NO = '"
		 * +mchtNo+"'"); Object[] objects4 = datasList.get(0); Object
		 * dirBankNname = objects4[0]; Object dirAaccountNname = objects4[1];
		 * Object dirAccount = objects4[2]; objects[10] = dirAaccountNname;
		 * objects[11] = dirAccount; objects[12] = dirBankNname; }
		 * 
		 * }
		 */
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 查询受益人信息
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getTblMchtBeneficiaryInfTmp(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];

		String sql = "select BENEFICIARY_ID,MCHT_NO,BENEFICIARY_NAME,BENEFICIARY_ADDRESS,BENEFICIARY_ID_TYPE,BENEFICIARY_ID_CARD,BENEFICIARY_EXPIRATION "
				+ "from TBL_MCHT_BENEFICIARY_INF_TMP where MCHT_NO = "
				+ request.getParameter("mchtNo");
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by BENEFICIARY_ID";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 受益所有人图片文件
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getImgInf1(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String imagesId = request.getParameter("imagesId");
		String beneficiaryId = request.getParameter("beneficiaryId");
		String beneficiaryExpiration = request.getParameter("beneficiaryExpiration");

		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil.getParam(SysParamConstants.FILE_UPLOAD_DISK);

		// 追加文件夹
		basePath += beneficiaryId + "_" + beneficiaryExpiration + "/";

		basePath = basePath.replace("\\", "/");
		File fl = new File(basePath);
		FileFilter filter = new FileFilter(imagesId);
		// 启用过滤
		File[] files = fl.listFiles(filter);
		
		// File[] files = fl.listFiles();

		if (null == files) {
			ret[0] = dataList;
			ret[1] = dataList.size();
			return ret;
		}
		for (File file : files) {
			if (file.getName().indexOf(".csv") != -1
					|| file.getName().indexOf(".CSV") != -1)
				continue;
			Object[] obj = new Object[8];
			obj[0] = "imageSelfs2" + dataList.size();
			obj[1] = "btBigs" + dataList.size();
			obj[2] = "btDws" + dataList.size();
			obj[3] = "btDels" + dataList.size();
			try {
				BufferedImage bi = ImageIO.read(file);
				double width = bi.getWidth();
				double height = bi.getHeight();
				double rate = 0;
				// 等比例缩放
				if (width > 400 || height > 400) {
					if (width > height) {
						rate = 400 / width;
						width = 400;
						height = height * rate;
					} else {
						rate = 400 / height;
						height = 400;
						width = width * rate;
					}
				}
				obj[4] = (int) width;
				obj[5] = (int) height;
			} catch (Exception e) {
				obj[4] = 400;
				obj[5] = 400;
				e.printStackTrace();
			}
			obj[6] = file.getName();
			obj[7] = basePath + "/" + file.getName();
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 受益所有人图片文件
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	public static Object[] getZipInf1(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		String imagesId = request.getParameter("imagesId");

		String beneficiaryId = request.getParameter("beneficiaryId");
		String beneficiaryExpiration = request
				.getParameter("beneficiaryExpiration");

		List<Object[]> dataList = new ArrayList<Object[]>();
		String basePath = SysParamUtil
				.getParam(SysParamConstants.FILE_UPLOAD_DISK);

		// 追加文件夹
		basePath += beneficiaryId + "_" + beneficiaryExpiration + "/";

		basePath = basePath.replace("\\", "/");
		File fl = new File(basePath);
		// FileFilter filter = new FileFilter(imagesId);
		// 启用过滤
		File[] files = fl.listFiles();

		// File[] files = fl.listFiles();

		if (null == files) {
			ret[0] = dataList;
			ret[1] = dataList.size();
			return ret;
		}
		for (File file : files) {
			if (file.getName().indexOf(".jpg") != -1
					|| file.getName().indexOf(".png") != -1
					|| file.getName().indexOf(".jpeg") != -1)
				continue;
			Object[] obj = new Object[5];
			obj[0] = "imageSelfs2" + dataList.size();
			obj[1] = "btDws1" + dataList.size();
			obj[2] = "btDels1" + dataList.size();
			obj[3] = file.getName();
			obj[4] = basePath + "/" + file.getName();
			dataList.add(obj);
		}
		ret[0] = dataList;
		ret[1] = dataList.size();
		return ret;
	}

	/**
	 * 客户赎回入账查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getredempTionMethod(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("redempTionAccount"))) {
			sb.append(" and REDEMPTION_ACCOUNT = '"
					+ request.getParameter("redempTionAccount") + "'");
		}

		String sql = "select REDEMPTION_ID,REDEMPTION_ACCOUNT_NAME,REDEMPTION_ACCOUNT,REDEMPTION_MONEY,REDEMPTION_BANK_CARD,REDEMPTION_STATUS,REDEMPTION_ACCOUNT_STATUS,REDEMPTION_PAY_STATUS,REDEMPTION_ADD_TIME,REDEMPTION_ADD_NAME,REDEMPTION_AUDIT_DATE,REDEMPTION_AUDIT_NAME,REDEMPTION_ENTRY from TBL_SETTLE_REDEMPTION_INF_TMP "
				+ "where 1=1 and (REDEMPTION_ACCOUNT_STATUS <> '0' or REDEMPTION_ACCOUNT_STATUS is null)"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by REDEMPTION_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 客户赎回入账审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getredempTionMethods(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("redempTionAccount"))) {
			sb.append(" and REDEMPTION_ACCOUNT = '"
					+ request.getParameter("redempTionAccount") + "'");
		}

		String sql = "select REDEMPTION_ID,REDEMPTION_ACCOUNT_NAME,REDEMPTION_ACCOUNT,REDEMPTION_MONEY,REDEMPTION_BANK_CARD,REDEMPTION_STATUS,REDEMPTION_ACCOUNT_STATUS,REDEMPTION_PAY_STATUS,REDEMPTION_ADD_TIME,REDEMPTION_ADD_NAME,REDEMPTION_AUDIT_DATE,REDEMPTION_AUDIT_NAME,REDEMPTION_ENTRY from TBL_SETTLE_REDEMPTION_INF_TMP where 1=1 and REDEMPTION_STATUS = '1' or REDEMPTION_STATUS = '3' or REDEMPTION_STATUS = '6'"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by REDEMPTION_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 客户赎回入账回填
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getredempTionMethodBackFill(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("redempTionAccount"))) {
			sb.append(" and REDEMPTION_ACCOUNT = '"
					+ request.getParameter("redempTionAccount") + "'");
		}

		String sql = "select REDEMPTION_ID,REDEMPTION_ACCOUNT_NAME,REDEMPTION_ACCOUNT,REDEMPTION_MONEY,REDEMPTION_BANK_CARD,REDEMPTION_STATUS,REDEMPTION_ACCOUNT_STATUS,REDEMPTION_PAY_STATUS,REDEMPTION_ADD_TIME,REDEMPTION_ADD_NAME,REDEMPTION_AUDIT_DATE,REDEMPTION_AUDIT_NAME,REDEMPTION_ENTRY,REDEMPTION_BATCH from TBL_SETTLE_REDEMPTION_INF_TMP where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by REDEMPTION_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户备款查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getredempGridStoreTmpBK(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();
		// 前一日日期
		String predate = request.getParameter("predate");
		// 备款金额
		String reserveMoney = request.getParameter("reserveMoney");

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and RESERVE_TIME = '" + request.getParameter("date") + "'");
		}
		
		String sql = "select RESERVE_ID,RESERVE_TIME,REDEMPTION_MONEY,RESERVE_SETTLE_MONEY,RESERVE_MONEY,RESERVE_STATUS,RESERVE_SETTLE_STATUS,RESERVE_PAY_STATUS,RESERVE_LAUNCH_TIME,RESERVE_LAUNCH_NAME,RESERVE_AUDIT_TIME,RESERVE_AUDIT_NAME,RESERVE_BATCH "
				+ "from TBL_MCHT_SETTLE_RESERVE_TMP "
				+ "where 1=1" + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by RESERVE_ID";

		// 查询所有未入账客户的赎回金额 1未入账
		/*String sql1 = "select sum(REDEMPTION_MONEY) from TBL_SETTLE_REDEMPTION_INF_TMP where REDEMPTION_ACCOUNT_STATUS = '1'";
		String accountMoney = CommonFunction.getCommQueryDAO()
				.findCountBySQLQuery(sql1);
		// 查询前一日的商户结算金额
		String sql2 = "select sum(RESERVE_SETTLE_MONEY) from TBL_MCHT_SETTLE_RESERVE  where RESERVE_TIME = '"
				+ predate + "'";
		String accountSettleMoney = CommonFunction.getCommQueryDAO()
				.findCountBySQLQuery(sql2);
		double sum;
		if (accountSettleMoney.equals("")) {
			sum = Double.parseDouble(accountMoney);
		} else {
			sum = Double.parseDouble(accountMoney)
					+ Double.parseDouble(accountSettleMoney);
		}*/

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);

		//赎回金额+结算金额=备款金额
//		for (Object[] objects : dataList) {
//			Object str1 = objects[2];
//			Object str2 = objects[3];
//			Float money1 = Float.parseFloat(String.valueOf(str1));
//			Float money2 = Float.parseFloat(String.valueOf(str2));
//			
//			Float money = money1 + money2;
//			
//			String sql3 = "update TBL_MCHT_SETTLE_RESERVE_TMP set RESERVE_MONEY = '" + String.valueOf(money) + "' where RESERVE_ID = '" + objects[0] + "'";
//			CommonFunction.getCommQueryDAO().excute(sql3);
//		}

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户备款审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getredempGridStoreTmpBK1(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and RESERVE_TIME = '" + request.getParameter("date")
					+ "'");
		}

		String sql = "select RESERVE_ID,RESERVE_TIME,REDEMPTION_MONEY,RESERVE_SETTLE_MONEY,RESERVE_MONEY,RESERVE_STATUS,RESERVE_SETTLE_STATUS,RESERVE_PAY_STATUS,RESERVE_LAUNCH_TIME,RESERVE_LAUNCH_NAME,RESERVE_AUDIT_TIME,RESERVE_AUDIT_NAME,RESERVE_BATCH "
				+ "from TBL_MCHT_SETTLE_RESERVE_TMP "
				+ "where RESERVE_STATUS = '1' or RESERVE_STATUS = '2'"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by RESERVE_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 商户备款回填
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getredempGridStoreTmpBK2(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and RESERVE_TIME = '" + request.getParameter("date")
					+ "'");
		}

		String sql = "select RESERVE_ID,RESERVE_TIME,REDEMPTION_MONEY,RESERVE_SETTLE_MONEY,RESERVE_MONEY,RESERVE_STATUS,RESERVE_SETTLE_STATUS,RESERVE_PAY_STATUS,RESERVE_LAUNCH_TIME,RESERVE_LAUNCH_NAME,RESERVE_AUDIT_TIME,RESERVE_AUDIT_NAME,RESERVE_BATCH "
				+ "from TBL_MCHT_SETTLE_RESERVE_TMP " + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by RESERVE_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 账户白名单
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getrosterGridStoreTmp(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("rosterAccount"))) {
			sb.append(" and ROSTER_ACCOUNT = '"
					+ request.getParameter("rosterAccount") + "'");
		}

		String sql = "select ROSTER_ID, ROSTER_BANK_CARD, ROSTER_ACCOUNT, ROSTER_ACCOUNT_NAME, ROSTER_STATUS, ROSTER_LAUNCH_TIME, ROSTER_LAUNCH_NAME, ROSTER_AUDIT_TIME, ROSTER_AUDIT_NAME from TBL_SETTLE_ROSTER_INF_TMP where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by ROSTER_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 账户白名单审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getrosterGridStoreTmpSH(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("rosterAccount"))) {
			sb.append(" and ROSTER_ACCOUNT = '"
					+ request.getParameter("rosterAccount") + "'");
		}

		String sql = "select ROSTER_ID, ROSTER_BANK_CARD, ROSTER_ACCOUNT, ROSTER_ACCOUNT_NAME, ROSTER_STATUS, ROSTER_LAUNCH_TIME, ROSTER_LAUNCH_NAME, ROSTER_AUDIT_TIME, ROSTER_AUDIT_NAME "
				+ "from TBL_SETTLE_ROSTER_INF_TMP "
				+ "where 1=1 and ROSTER_STATUS = '1' or ROSTER_STATUS = '2' or ROSTER_STATUS = '3'"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by ROSTER_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 人行集中缴存备款回填
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getfocusStore(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("rosterAccount"))) {
			sb.append(" and ROSTER_ACCOUNT = '"
					+ request.getParameter("rosterAccount") + "'");
		}

		String sql = "select FOCUS_ID, FOCUS_ACCOUNT, FOCUS_ACCOUNT_NAME, FOCUS_MONEY, FOCUS_STATUS, FOCUS_DATE, FOCUS_PAY_STATUS, FOCUS_LAUNCH_TIME, FOCUS_LAUNCH_NAME, FOCUS_AUDIT_TIME, FOCUS_AUDIT_NAME, FOCUS_AUDIT_STATUS, FOCUS_BATCH "
					+ "from TBL_FOCUS_RESERVE_TMP where 1=1" + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by FOCUS_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 人行集中缴存备款查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getfocusStore1(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("focusAccount"))) {
			sb.append(" and FOCUS_ACCOUNT = '"
					+ request.getParameter("focusAccount") + "'");
		}
		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and FOCUS_DATE = '" + request.getParameter("date") + "'");
		}

		String sql = "select FOCUS_ID, FOCUS_ACCOUNT, FOCUS_ACCOUNT_NAME, FOCUS_MONEY, FOCUS_STATUS, FOCUS_DATE, FOCUS_PAY_STATUS, FOCUS_LAUNCH_TIME, FOCUS_LAUNCH_NAME, FOCUS_AUDIT_TIME, FOCUS_AUDIT_NAME, FOCUS_AUDIT_STATUS, FOCUS_BATCH "
					+ "from TBL_FOCUS_RESERVE_TMP where (FOCUS_STATUS in ('1', '2') or FOCUS_STATUS is null)" + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by FOCUS_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 人行集中缴存备款审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getfocusStore2(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("focusAccount"))) {
			sb.append(" and FOCUS_ACCOUNT = '"
					+ request.getParameter("focusAccount") + "'");
		}
		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and FOCUS_DATE = '" + request.getParameter("date") + "'");
		}

		String sql = "select FOCUS_ID, FOCUS_ACCOUNT, FOCUS_ACCOUNT_NAME, FOCUS_MONEY, FOCUS_STATUS, FOCUS_DATE, FOCUS_PAY_STATUS, FOCUS_LAUNCH_TIME, FOCUS_LAUNCH_NAME, FOCUS_AUDIT_TIME, FOCUS_AUDIT_NAME, FOCUS_AUDIT_STATUS, FOCUS_BATCH "
					+ "from TBL_FOCUS_RESERVE_TMP where FOCUS_AUDIT_STATUS in ('1','3','6','8')" + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by FOCUS_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 人行集中缴存回款回填
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getpayMentStore(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("paymentAccount"))) {
			sb.append(" and PAYMENT_ACCOUNT = '"
					+ request.getParameter("paymentAccount") + "'");
		}

		String sql = "select PAYMENT_ID, PAYMENT_ACCOUNT, PAYMENT_ACCOUNT_NAME, PAYMENT_MONEY, PAYMENT_STATUS, PAYMENT_DATE, PAYMENT_PAY_STATUS, PAYMENT_LAUNCH_TIME, PAYMENT_LAUNCH_NAME, PAYMENT_AUDIT_TIME, PAYMENT_AUDIT_NAME, PAYMENT_AUDIT_STATUS, PAYMENT_BATCH, PAYMENT_INS_SEQ "
				+ "from TBL_PAYMENT_RESERVE_TMP " + "where 1=1" + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by PAYMENT_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 人行集中缴存回款查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getpayMentStore1(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("paymentAccount"))) {
			sb.append(" and PAYMENT_ACCOUNT = '"
					+ request.getParameter("paymentAccount") + "'");
		}

		String sql = "select PAYMENT_ID, PAYMENT_ACCOUNT, PAYMENT_ACCOUNT_NAME, PAYMENT_MONEY, PAYMENT_STATUS, PAYMENT_DATE, PAYMENT_PAY_STATUS, PAYMENT_LAUNCH_TIME, PAYMENT_LAUNCH_NAME, PAYMENT_AUDIT_TIME, PAYMENT_AUDIT_NAME, PAYMENT_AUDIT_STATUS, PAYMENT_BATCH, PAYMENT_INS_SEQ "
				+ "from TBL_PAYMENT_RESERVE_TMP where (PAYMENT_STATUS <> '0' or PAYMENT_STATUS is null)" + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by PAYMENT_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 人行集中缴存回款审核
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getpayMentStore2(int begin, HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("paymentAccount"))) {
			sb.append(" and PAYMENT_ACCOUNT = '"
					+ request.getParameter("paymentAccount") + "'");
		}
		//1368
		String sql = "select PAYMENT_ID, PAYMENT_ACCOUNT, PAYMENT_ACCOUNT_NAME, PAYMENT_MONEY, PAYMENT_STATUS, PAYMENT_DATE, PAYMENT_PAY_STATUS, PAYMENT_LAUNCH_TIME, PAYMENT_LAUNCH_NAME, PAYMENT_AUDIT_TIME, PAYMENT_AUDIT_NAME, PAYMENT_AUDIT_STATUS, PAYMENT_BATCH, PAYMENT_INS_SEQ "
				+ "from TBL_PAYMENT_RESERVE_TMP where PAYMENT_AUDIT_STATUS in ('1','3','6','8') " + sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by PAYMENT_ID";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/**
	 * 人行备付金余额查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getbalanceReserveQuery(int begin,
			HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and BALANCE_DATE = '" + request.getParameter("date")
					+ "'");
		}

		String sql = "select BALANCE_NO, BALANCE_DATE, BALANCE_ACS_BANK_NO, BALANCE_ACCT_BAL, BALANCE_AVLB_BAL, BALANCE_ACS_ACCT_BAL, BALANCE_ACS_ACCT_NAME, BALANCE_AVLB_QUOTA_AMT "
				+ "from TBL_BALANCE_RESERVE_QUERY "
				+ "where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by BALANCE_NO";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 虚拟记账余额查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblFictitiousQuery(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and FICTITIOUS_DATE = '" + request.getParameter("date") + "'");
		}

		String sql = "select FICTITIOUS_NO, FICTITIOUS_DATE, FICTITIOUS_ACCTNO, FICTITIOUS_ACCTNAME, FICTITIOUS_ACCTBAL, FICTITIOUS_AVLBBAL "
				+ "from TBL_FICTITIOUS_QUERY "
				+ "where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by FICTITIOUS_NO";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 历史余额查询
	 * 
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblHistoryQuery(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and HISTORY_DATE = '" + request.getParameter("date") + "'");
		}

		String sql = "select HISTORY_NO, HISTORY_DATE, HISTORY_ACCTNO, HISTORY_ACCTNAME, HISTORY_OPENBAL, HISTORY_ENCBAL, HISTORY_TOTALDBTRQTY, HISTORY_TOTALCDTRQTY, HISTORY_TOTALDBTRAMT, HISTORY_TOTALCDTRAMT "
				+ "from TBL_HISTORY_QUERY "
				+ "where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by HISTORY_NO";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 来账通知
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblAccountNotify(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and TAN_DATE = '" + request.getParameter("date") + "'");
		}

		String sql = "select TAN_NO, TAN_DATE, TAN_PAYERBANKNO, TAN_PAYERACCTNO, TAN_PAYERACCTNAME, TAN_PAYEEACCTNO, TAN_PAYEEACCTNAME, TAN_TXNAMT, TAN_PAYEEACCTBAL, TAN_ACCTDATE "
				+ "from TBL_ACCOUNT_NOTIFY "
				+ "where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by TAN_NO";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	/**
	 * 退汇通知
	 * @param begin
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] gettblRejectedNotify(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();

		if (isNotEmpty(request.getParameter("date"))) {
			sb.append(" and TRN_DATE = '" + request.getParameter("date") + "'");
		}

		String sql = "select TRN_NO, TRN_DATE, TRN_PAYERBANKNO, TRN_PAYERACCTNO, TRN_PAYERACCTNAME, TRN_PAYEEACCTNO, TRN_PAYEEACCTNAME, TRN_TXNAMT, TRN_PAYEEACCTBAL, TRN_ACCTDATE, TRN_ORIG_TXNNO, TRN_ORIG_TXNDATE, TRN_MER_ID, TRN_MER_NAME "
				+ "from TBL_REJECTED_NOTIFY "
				+ "where 1=1"
				+ sb.toString();
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by TRN_NO";

		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql);
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static Object[] getlstEntitys111(int begin,HttpServletRequest request) {
		Object[] ret = new Object[2];
		StringBuffer sb = new StringBuffer();
		
		String mchtNm = request.getParameter("mchtNm");
		String cttp = request.getParameter("cttp");
		String nationality = request.getParameter("nationality");
		
		if(nationality=="86" || nationality.equals("86")){
			nationality = "中国";
		}else if(nationality=="800" || nationality.equals("800")){
			nationality = "外籍";
		}else if(nationality=="852" || nationality.equals("852")){
			nationality = "港澳台";
		}
		
		String identityNo = request.getParameter("identityNo");
		String manager = request.getParameter("manager");
		String province = request.getParameter("province");
		String lstp = request.getParameter("lstp");
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		
		Object[] mchntObject = new Object[7];
		mchntObject[0] = mchtNm;
		mchntObject[1] = cttp;
		mchntObject[2] = nationality;
		mchntObject[3] = identityNo;
		mchntObject[4] = manager;
		mchntObject[5] = province;
		mchntObject[6] = lstp;
		
		dataList.add(mchntObject);
		
		ret[0] = dataList;
		ret[1] = 1;
		return ret;
	}
	

}
