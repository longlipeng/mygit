package com.huateng.common.select;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.TxnInfo;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.TblTermManagement;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * 
 * Title: SelectOption接口方法
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2009-12-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SelectMethod {
	
	static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	
	/**
	 * 根据当前操作员级别获得机构级别信息，
	 * 只有总行返回
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhLvlByOprInfo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		Operator operator = (Operator) params[0];
		
		//总行
		if("0".endsWith(operator.getOprBrhLvl())) {
			dataMap.put("1", "分行");
			dataMap.put("2", "支行");
			dataMap.put("3", "网点");
		} else {
			dataMap.put("2", "支行");
			dataMap.put("3", "网点");
		}
		return dataMap;
	}
	
	/**
	 * 获得分公司级别信息（不包括总公司）
	 * 
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBranchLevelList(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		dataMap.put("1", "分公司");
		dataMap.put("2", "办事处");
		return dataMap;
	}
	
	/** 交易权限 */
	public static LinkedHashMap<String, String> getRiskLimit(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		Operator operator = (Operator) params[0];
		dataMap.put("00", "不允许");
		dataMap.put("01", "允许");
	
		return dataMap;
	}
	
	
	/** 审核拒绝原因查询：拒绝类型下拉框 */
	public static LinkedHashMap<String, String> getRefuseType(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "新增审核拒绝");
		dataMap.put("3", "修改审核拒绝");
		dataMap.put("4", "删除审核拒绝");
	
		return dataMap;
	}
	
	/** 审核拒绝原因查询：拒绝类型下拉框2 */
	public static LinkedHashMap<String, String> getRefuseType2(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "新增审核拒绝");
		dataMap.put("4", "删除审核拒绝");
	
		return dataMap;
	}
	
	/** 交易权限的卡类型下拉框信息 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRiskCardType(Object[] params) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
		String sql = "SELECT trim(KEY), trim(KEY) ||' - '||trim(VALUE) FROM CST_SYS_PARAM WHERE OWNER = 'CARDTYPE' ORDER BY KEY";
		List dates = commQueryDAO.findBySQLQuery(sql);
		Iterator itor = dates.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;

	}
	//受控动作
	public static LinkedHashMap<String, String> getAction(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "警告");
		dataMap.put("1", "拒绝");
	
		return dataMap;
	}
	
	/** 商户规则管理的目的机构 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAgenCode(Object[] params) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
		List dates = null;
		String sql = "SELECT AGEN_ID, AGEN_ID ||' - '||AGEN_NAME FROM TBL_AGENCY_INFO_TRUE where STATUE in('1','2','3','4','6','8')";
		dates = commQueryDAO.findBySQLQuery(sql);
		Object[] obj1={"*","* - 支持所有机构"};
		dates.add(obj1);
		Iterator itor = dates.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			
			hashMap.put(docType, descTx);
		}
		return hashMap;
	}

	/**
	 * 根据操作员所在机构返回机构信息
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getUpBrhIdByOprInfo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];
		Map<String, String> brhInfoMap = operator.getBrhBelowMap();
		Iterator<String> iterator = brhInfoMap.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			dataMap.put(key, key+" - "+brhInfoMap.get(key));
		}
		return dataMap;
	}
	
	/**
	 * 根据上级菜单编号查询菜单信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getLevelMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select func_id,func_name from tbl_func_inf where func_parent_id = " + params[1].toString() + " and func_type != '3'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据角色编号查找角色菜单信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRoleMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select value_id,func_name from tbl_role_func_map,tbl_func_inf where value_id = func_id and key_id = " + params[1].toString();
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhInfoBelow(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构,正常状态的
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoBelowNormal(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select branch_no,branch_no||'-'||branch_name FROM tbl_branch_manage WHERE state='1' AND trim(branch_no) IN " + 
			sb.toString();
		sql += " ORDER BY branch_no";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 获取卡bin
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getCardBin(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];
		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT trim(BIN_STA_NO),trim(BIN_STA_NO)||'-'||trim(CARD_DIS) FROM TBL_BANK_BIN_INF WHERE 1 = 1 ";// +  sb.toString();
		sql += " ORDER BY INS_ID_CD";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 获取机构吗
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getInstCode(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];
		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select trim(INS_ID_CD),trim(INS_ID_CD)||'-'||trim(CARD_DIS) from TBL_BANK_BIN_INF where 1=1 "; 
		sql += " ORDER BY INS_ID_CD";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(总分行)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getBrhInfoBelowBranch(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select BRH_ID,BRH_ID||'-'||BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') AND trim(BRH_ID) IN " + 
			sb.toString();
		sql += " ORDER BY BRH_LEVEL";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(总分行)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupInfoBelowBranch(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		dataMap = (LinkedHashMap<String, String>) operator.getBrhBelowMap();
		
		Iterator<String> it = dataMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("(");
		while (it.hasNext()) {
			sb.append("'").append(it.next().trim()).append("'").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		dataMap = new LinkedHashMap<String, String>();
		String sql = "select CUP_BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_LEVEL IN ('0','1') AND trim(BRH_ID) IN " + 
			sb.toString();
		sql += " ORDER BY BRH_LEVEL";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得当前机构的下属机构(-)
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getBrhInfoBelowShowId(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		Operator operator = (Operator) params[0];

		Iterator<String> it = operator.getBrhBelowMap().keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			dataMap.put(key, key + "-" + operator.getBrhBelowMap().get(key));
		}
		return dataMap;
	}
	
	/**
	 * 根据机构级别获得角色信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRoleInfoByBrh(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select role_id,role_name from tbl_role_inf where role_type = " +
					 "(select brh_level from tbl_brh_info where BRH_ID = '" + params[1].toString().trim() + "')";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据商户组别获得商户编号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntTpByGrp(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,descr from tbl_inf_mchnt_tp where mchnt_tp_grp = '" +  params[1].toString().trim() + "'" + " order by mchnt_tp";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询商户分店列表
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtBranInf(Object[] params) {
//		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select  a.MCHT_CD,a.BRANCH_CD,a.BRANCH_NM,a.rec_upd_ts from TBL_MCHT_BRAN_INF a order by a.rec_upd_ts";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * getTermID
	 * 终端数据集
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermID(Object[] args){
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		List dates = null;
		String sql = "select TERM_ID from TBL_TERM_INF order by TERM_ID";
		dates = commQueryDAO.findBySQLQuery(sql);
		Iterator itor = dates.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;
	}
	
	/**
	 * 查询商户限额查询
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCstMchtFee(Object[] params) {
//		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select a.mcht_cd,a.txn_num, a.card_type, a.channel, a.day_num, a.day_amt, " +
				"a.day_single, a.mon_num, a.mon_amt from cst_mcht_fee_inf a";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询真实表商户编号
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntNo(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF where ACQ_INST_ID in " +  operator.getBrhBelowId() 
			+ " and MCHT_STATUS = '0'";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询真实表商户编号,union * 所有商户
	 * @param params
	 * @return
	 * 2012-11-07
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntNo2(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF "
//			"where ACQ_INST_ID in " +  operator.getBrhBelowId() 
//			+ " and MCHT_STATUS = '0' " 20121226去掉此查询过滤条件
			 + " union select key,key|| '-' ||value from cst_sys_param where owner='ALLMCHNT' and type='00' ";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 风险模型类型
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getKind(Object[] params) {

		LinkedHashMap dataMapTmp = new LinkedHashMap();

		dataMapTmp.put("C2", "3日内，同一卡号在同一受理行内交易限制");
		dataMapTmp.put("C3", "3日内，同一卡号交易限制");
		dataMapTmp.put("M3", "同一商户当日同一卡号交易限制");
		dataMapTmp.put("M5", "同一商户当日有超过一笔同金额的限制");
		dataMapTmp.put("R1", "同卡在几家商户交易间隔短于正常时间");
		
		return dataMapTmp;
	}
	
	/**
	 * 获取终端厂商
	 * @param params
	 * @return
	 * 2011-6-8下午02:46:17
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getManufacturer(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select trim(key),trim(value) from CST_SYS_PARAM where owner = '")
//			.append(TblTermManagement.REF).append("'");
		.append("FACTORY").append("'");
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获取终端类型（出厂的终端类型）
	 * @param params
	 * @return
	 * 2011-6-8下午02:46:17
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermType(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select trim(key)||'-'||trim(value),value from CST_SYS_PARAM where 1<>1 ");
		if(params != null&& params.length>=2 && params[1] != null){
			String manufacturer = params[1].toString();
			sql.append("or owner = '").append(manufacturer).append("'");
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获取终端库存编号
	 * @param params
	 * @return
	 * 2011-6-21下午03:14:37
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermIdId(Object[] params){
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select term_no,product_cd from tbl_term_management where STATE ='4' ");
//		if(params.length<2 || params[1] == null)
//			return dataMap;
//		String[] tmp = params[1].toString().split(",");
//		String mechNo = tmp.length>=1?tmp[0]:"";
//		String terminalType = tmp.length>=2?tmp[1]:"";
//		String manufaturer = tmp.length>=3?tmp[2]:"";
//		if(mechNo!=null && !mechNo.equals(""))
//			sql.append("or MECH_NO = '").append(mechNo).append("' ");
//		if(terminalType!=null && !terminalType.equals(""))
//			sql.append("and TERMINAL_TYPE = '").append(terminalType).append("' ");
//		if(manufaturer!=null && !manufaturer.equals(""))
//			sql.append("and MANUFATURER = '").append(manufaturer).append("'");
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 查询POS交易类型
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getPosTxnNum(Object[] params) {
		return (LinkedHashMap<String, String>) TxnInfo.txnNameMap;
	}
	
	/**
	 * 查询商户地区代码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	public static LinkedHashMap<String, String> getCityCode(Object[] args) {
		/*LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
//			ICommQueryDAO commonQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAOCopy");
			List dates = null;
			String sql = "select CITY_CODE_NEW,CITY_CODE_NEW ||' - '|| CITY_NAME AS CITY_NAME From CST_CITY_CODE";
//			String sql = "select CITY_CODE_NEW, CITY_CODE_NEW ||'-' ||CITY_NAME AS CITY_NAME From CST_CITY_CODE";
			dates = commQueryDAO.findBySQLQuery(sql);
			Iterator itor = dates.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		return hashMap;*/
		return getCityCodecode(args);

	}
	/** 分公司所属地区 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCityCodecode(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
			List dates = null;
			String sql = "select CITY_CODE,CITY_CODE||' - '||CITY_DES from TBL_CITY_CODE";
			dates = commQueryDAO.findBySQLQuery(sql);
			Iterator itor = dates.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		return hashMap;

	}
	//渠道交易黑名单
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getRiskChannel(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
			List dates = null;
			String sql = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='CHANNEL'";
			dates = commQueryDAO.findBySQLQuery(sql);
			Iterator itor = dates.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		return hashMap;
	}
	
	/** 渠道交易黑名单 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTradeTxn(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
		List dates = null;
		String sql = "select trim(KEY),trim(KEY)||' - '||trim(VALUE) from CST_SYS_PARAM where OWNER='TXNTYPE'";
		dates = commQueryDAO.findBySQLQuery(sql);
		Iterator itor = dates.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;
	}
	
	/** 渠道交易黑名单 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermid(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();		
			List dates = null;
			String sql = "SELECT TERM_ID,TERM_ID FROM TBL_TERM_INF";
			dates = commQueryDAO.findBySQLQuery(sql);
			Iterator itor = dates.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		return hashMap;
	}
	
	/**
	 * 查询机构地区代码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCityCodeOld(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();	
		List dates = null;
		String sql = "select DISTINCT(CITY_CODE_OLD),CITY_CODE_OLD　 From CST_CITY_CODE";
		dates = commQueryDAO.findBySQLQuery(sql);
		Iterator itor = dates.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;
	}
	
	/**
	 * 根据商户编号查询该商户已经选择的交易权限码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtRoleFuncByMerId(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		if(args.length >= 2){
			//查找所有交易码
			String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";;
			List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);
			
			Long merId = Long.valueOf(args[1].toString().toString());			
			//查找选择商户所在 Mcc
			List<Object[]> roleIds = commQueryDAO.findBySQLQuery("select MCC,SIGN_INST_ID from TBL_MCHT_BASE_INF where MCHT_NO = "+ merId);
			String roleId = roleIds.get(0)[0].toString();	
			//相关二级商户具有的交易权限
			sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + roleId + " order by VALUE_ID";
			List<Object> funcRole = commQueryDAO.findBySQLQuery(sql);

			//该商户所在机构所拥有的权限集
			List<Object[]> rightFuncLIst = new ArrayList<Object[]>();
			
			//筛选权限信息
			for(int i = 0; i < funcAll.size(); i++) {
				Long funcId = Long.valueOf(funcAll.get(i)[0].toString().trim());
				for(int j = 0; j <funcRole.size(); j++ ) {
					Long valueId = Long.valueOf(String.valueOf(funcRole.get(j)));
					if(valueId.longValue() == funcId.longValue()) {
						Object[] funcInfo = new Object[2];
						funcInfo[0] = funcId;
						funcInfo[1] = funcAll.get(i)[1].toString();
						rightFuncLIst.add(funcInfo);
						funcAll.remove(i--);
						break;
					}
				}
			}
			
			Iterator itor = rightFuncLIst.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		}		
		return hashMap;
	}
	
	/**
	 * 根据商户类型编号查询商户组别已经选择的交易权限码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMerRoleFunc(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		if(args.length>=2){
			Long roleId = Long.valueOf(args[1].toString());
			String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";;
			List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);
			sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + roleId + " order by VALUE_ID";
			System.out.println(sql);
			
			List<Object> funcRole = commQueryDAO.findBySQLQuery(sql);
			//右侧权限集
			List<Object[]> rightFuncLIst = new ArrayList<Object[]>();
			//筛选权限信息
			for(int i = 0; i < funcAll.size(); i++) {				
//				Long funcId = Long.valueOf(funcAll.get(i)[0].toString());
				int funcId = Integer.parseInt(funcAll.get(i)[0].toString().trim());
				for(int j = 0; j <funcRole.size(); j++ ) {
//					Long valueId = Long.valueOf(String.valueOf(funcRole.get(j)));
					int valueId = Integer.parseInt(String.valueOf(funcRole.get(j)).trim());
//					if(valueId.longValue() == funcId.longValue()) {
					if(valueId == funcId) {
						Object[] funcInfo = new Object[2];
						funcInfo[0] = funcId;
						funcInfo[1] = funcAll.get(i)[1].toString();
						rightFuncLIst.add(funcInfo);
						funcAll.remove(i--);
						break;
					}
				}
			}
			
			Iterator itor = rightFuncLIst.iterator();
			Object[] obj;
			String docType, descTx;
			while (itor.hasNext()) {
				obj = (Object[]) itor.next();
				docType = obj[0].toString().trim();
				descTx = obj[1].toString().trim();
				hashMap.put(docType, descTx);
			}
		}		
		return hashMap;
	}
	
	/**
	 * 根据商户类型编号查询商户组别已经选择的权限码
	 * @param params
	 * @return
	 * 2010-8-27上午11:21:12
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMerMerFuncAll(Object[] args) {
//		String sql = "select FUNC_ID,FUNC_NAME from TBL_MER_FUNC_INF where FUNC_TYPE = '0' order by FUNC_ID";
		String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";
		List<Object[]> funcAll = commQueryDAO.findBySQLQuery(sql);		
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		Iterator itor = funcAll.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;
	}
	
	/**
	 * 获得商户服务等级
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getBranchSvrLvl(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "VIP");
		hashMap.put("1", "重点");
		hashMap.put("2", "普通");
		return hashMap;
	}
	
	/**
	 * 获得商户资质等级
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getMchtCreLvl(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("1", "一级");
		hashMap.put("2", "二级");
		hashMap.put("3", "三级");
		hashMap.put("9", "未分级");
		return hashMap;
	}
	/**
	 * getYesOrNotSelect
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getYesOrNotSelect(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "是");
		hashMap.put("1", "否");
		return hashMap;
	}
	
	/**
	 * getAOrPSelect
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getAOrPSelect(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "A");
		hashMap.put("1", "P");
		return hashMap;
	}
	
	/**
	 * getTxnNum
	 * 回佣类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getDiscAlgoFlag(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "按比率");
		hashMap.put("1", "按金额");
		return hashMap;
	}
	
	/**
	 * getTxnNum
	 * 交易类型
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTxnNum(Object[] args) {
//		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
//		hashMap.put("1101", "信用卡消费");
//		hashMap.put("1091", "信用卡预授权完成");
//		hashMap.put("1701", "T+0收款");
//		return hashMap;
		LinkedHashMap<String, String> hashMap = new 

		LinkedHashMap<String, String>();
		List dates = null;
		String sql = "select TXN_NUM,TXN_NAME from TBL_TXN_NAME where DC_FLAG = '0' order by TXN_NUM";;
		dates = commQueryDAO.findBySQLQuery(sql);
		Iterator itor = dates.iterator();
		Object[] obj;
		String docType, descTx;
		while (itor.hasNext()) {
			obj = (Object[]) itor.next();
			docType = obj[0].toString().trim();
			descTx = obj[1].toString().trim();
			hashMap.put(docType, descTx);
		}
		return hashMap;
	}
	
	/**
	 * getCardType
	 * 卡类型
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCardType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("01", "借记卡");
		hashMap.put("00", "贷记卡");
//		hashMap.put("11", "本行IC借记卡");
//		hashMap.put("10", "本行IC贷记卡");
//		hashMap.put("12", "移动卡");
		return hashMap;
	}
	
	/**
	 * getChannel
	 * 交易渠道CHANNEL
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getChannel(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "间联POS");
		hashMap.put("1", "否");		
		return hashMap;
	}
	
	/**
	 * 获得币种信息
	 * 
	 * @param args
	 * @return
	 */
	public static LinkedHashMap<String, String> getCurType(Object[] args) {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("0", "人民币");
		hashMap.put("1", "港币");
		hashMap.put("2", "美元");
		hashMap.put("3", "日元");
		hashMap.put("4", "马克");
		hashMap.put("5", "英镑");
		hashMap.put("6", "法郎");
		hashMap.put("7", "台币");
		return hashMap;
	}
	
	/**
	 * 授权管理的一级菜单获取
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAuthorMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		
		String sql = "select FUNC_ID,FUNC_NAME from tbl_func_inf where func_id in " +
				"(select distinct func_parent_id from TBL_FUNC_INF where func_id in " +
				"(select distinct func_parent_id from TBL_FUNC_INF where trim(misc1) in ('1','2')))";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据上级菜单编号查询菜单信息(授权管理)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAuthorNextMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		if (params.length == 1) {
			return dataMap;
		}
		
		String sql = "select FUNC_TYPE from tbl_func_inf where func_id = '" + params[1].toString().trim() + "'";
		List type = commQueryDAO.findBySQLQuery(sql);
		if (null != type && !type.isEmpty()) {
			if (!StringUtil.isNull(type.get(0))) {
				if ("1".equals(type.get(0).toString())) {
					sql = "select func_id,func_name from tbl_func_inf where func_parent_id = '" 
						+ params[1].toString().trim() 
						+ "' and func_id in (select distinct func_parent_id from TBL_FUNC_INF where trim(misc1) in ('1','2')) ";
				} else {
					sql = "select func_id,func_name from tbl_func_inf where func_parent_id = '" 
						+ params[1].toString().trim() 
						+ "' and trim(misc1) in ('1','2')";
				}
			}
			List<Object[]> list = commQueryDAO.findBySQLQuery(sql);
			Iterator<Object[]> iterator = list.iterator();
			while(iterator.hasNext()) {
				Object[] obj = iterator.next();
				dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
			}
		}
		return dataMap;
	}
	
	/**
	 * 授权管理的已列为需授权的交易获取
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAuthorisedMenu(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		
		String sql = "select FUNC_ID,FUNC_NAME from tbl_func_inf where trim(misc1) = '1' and FUNC_TYPE = '0'";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	/**
	 * 当前机构和下属机构的32域
	 * @param params
	 * @return
	 * 2010-8-17下午03:13:55
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCupBrhInfoBelow(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select CUP_BRH_ID, CUP_BRH_ID ||' - '|| BRH_NAME from TBL_BRH_INFO where BRH_ID in " +  operator.getBrhBelowId();
		sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 *  终端活动编号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getActNo(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select ACT_NO, ACT_NO ||' - '|| ACT_NAME from TBL_MARKET_ACT where STATE = '0' and BANK_NO in " +  operator.getBrhBelowId();
		sql += " ORDER BY ACT_NO";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**
	 * 获取专业服务机构
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getOragn(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select ORG_ID,ORG_ID||'-'||ORG_NAME from TBL_PROFESSIONAL_ORGAN where BRANCH in " +  operator.getBrhBelowId();
		sql += " ORDER BY ORG_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 差错交易类型，下拉框static
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getErrFlagInfo(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		dataMap.put("E05", "调单回复");
		dataMap.put("E22", "请款");
		dataMap.put("E23", "退单");
		dataMap.put("E24","再请款");
		dataMap.put("E25","二次退单");
		dataMap.put("E32","贷记调整");
		dataMap.put("E33","一般转帐转入贷记调整");
		dataMap.put("E34","一般转帐转出贷记调整");
		dataMap.put("E35","一般转帐转入对贷记调整的请款");
		dataMap.put("E36","一般转帐转出请款");
		dataMap.put("E73","差错例外");
		dataMap.put("E74","能查找到原始交易的手工退货");
		dataMap.put("E84","不能查找到原始交易的手工退货");
		dataMap.put("E80","贷记调整（存入类）");
		dataMap.put("H01","追扣");
		dataMap.put("2","交易挂账");
		dataMap.put("3","交易解挂");
		dataMap.put("5221","补电子现金消费");
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getFeeDisc(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select DISC_CD, DISC_CD from TBL_INF_DISC_CD where DISC_ORG in " +  operator.getBrhBelowId();
		//sql += " ORDER BY BRH_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	//20120803：商户修改界面中，商户费率的计费代码下拉框中显示正常状态的数据，即sa_state=2
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getFeeDiscNormal(Object[] params) {
		Operator operator = (Operator) params[0];
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select DISC_CD, DISC_CD from TBL_INF_DISC_CD where SA_STATE='2' and DISC_ORG in " +  operator.getBrhBelowId();
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 终端安装地所属地区代码
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermCityCode(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		String sql = "select CITY_CODE,CITY_CODE ||' - '||CITY_DES from TBL_CITY_CODE";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 省、直辖市
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getProvinces(Object[] params) {//取出所有省和直辖市信息
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,3,2)='00' ";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 市
	 * @param params
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCities(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select KEY,VALUE from CST_SYS_PARAM where owner='CITY'");
		if(params != null&& params.length>=2 && params[1] != null){
			String province = params[1].toString();
			sql.append(" and descr = '").append(province).append("'");//根据省取市的信息
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}
	*/
	
	/**
	 * 市
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getCities(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  ((substr(CITY_CODE,3,1)<>'0' and substr(CITY_CODE,4,1)='0')" +
		" or (CITY_CODE IN (1000,1100,2900,6900))) " ;
		if(params != null&& params.length>=2 && params[1] != null){
			String descr = params[1].toString();

//			sql+=provideSqlWith(sql, "descr", descr);
			String cityParentP =descr.substring(0, 2);
			String descr2 = String.valueOf(Integer.parseInt(descr)+100);
			String sqlTmp = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr2+"' ";
			List listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp);
			if(!(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0)){
				String descr3 = String.valueOf(Integer.parseInt(descr)+200);
				String sqlTmp1 = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr3+"' ";
				List listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
				if(!(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0)){
					sql+=" and substr(CITY_CODE,1,2) in ('"+descr.substring(0, 2)+"','"+descr2.substring(0, 2)+"','"+descr3.substring(0, 2)+"') ";
				}else{
					sql+=" and substr(CITY_CODE,1,2) in ('"+descr.substring(0, 2)+"','"+descr2.substring(0, 2)+"') ";
				}
			
			}else{
			sql +=" and substr(CITY_CODE,1,2)= '"+cityParentP+"' ";
			}
		
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),obj[1].toString().trim());
		}
		return dataMap;
	}
	
	
	
	/**
	 * 县/区
	 * @param params
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAreas(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuffer sql = new StringBuffer("select KEY,VALUE from CST_SYS_PARAM where owner='AREA'");
		if(params != null&& params.length>=2 && params[1] != null){
			String city = params[1].toString();
			sql.append(" and descr = '").append(city).append("'");//根据市来取县区信息
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[0].toString().trim() + "-" + obj[1].toString().trim());
		}
		return dataMap;
	}*/
	
	/**
	 * 县/区
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAreas(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  substr(CITY_CODE,4,1)<>'0' " ;
		if(params != null&& params.length>=2 && params[1] != null){
			String descr = params[1].toString();

			if("1000".equals(descr)||"1100".equals(descr)||"2900".equals(descr)||"6900".equals(descr)){
				sql+=" and substr(CITY_CODE,1,2)='"+descr.substring(0, 2)+"' ";
			}else{
			String descr2 = String.valueOf(Integer.parseInt(descr)+10);
			String sqlTmp = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr2+"' ";
			List listTmp1 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp);
			if(!(null != listTmp1 && !listTmp1.isEmpty() && listTmp1.size() > 0)){
				String descr3 = String.valueOf(Integer.parseInt(descr)+20);
				String sqlTmp1 = "select CITY_CODE from TBL_CITY_CODE where CITY_CODE= '"+descr3+"' ";
				List listTmp2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlTmp1);
				if(!(null != listTmp2 && !listTmp2.isEmpty() && listTmp2.size() > 0)){
					sql+=" and substr(CITY_CODE,1,3) in ('"+descr.substring(0, 3)+"','"+descr2.substring(0, 3)+"','"+descr3.substring(0, 3)+"') ";
				}else{
					sql+=" and substr(CITY_CODE,1,3) in ('"+descr.substring(0, 3)+"','"+descr2.substring(0, 3)+"') ";
				}
			}else{
				sql+=" and substr(CITY_CODE,1,3)= '"+descr.substring(0, 3)+"' ";
			}
//			sql+=provideSqlWith(sql, "descr", descr);
		}
		
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/** 查询真实表发卡行,union * 所有发卡行
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getSendCardBank(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "SELECT distinct(trim(INS_ID_CD)),trim(INS_ID_CD)||' - '||trim(CARD_DIS) FROM TBL_BANK_BIN_INF"
			 + " union select trim(key),trim(key)||' - '||trim(value) from cst_sys_param where owner='ALLBANK' and type='00' ";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**所属机构（银行）编号(区分中行、工行、农行、建行
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getAgencyIdBank(Object[] params) {
		String type = params[1].toString().trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String wheresql = "SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"+type+"' ";
    	List list =   CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);
		String sql = "SELECT AGEN_ID,AGEN_ID||'-'||AGEN_NAME as AGEN_NAME FROM TBL_AGENCY_INFO_TRUE where statue='1'and AGEN_ID = '"+list.get(0)+"' ";
		
		sql = sql + " order by AGEN_ID";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	public static LinkedHashMap<String, String> getAgencyIdBankIndex(Object[] params) {
		String type = params[1].toString().trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
/*		String wheresql = "SELECT trim(KEY) FROM CST_SYS_PARAM WHERE OWNER = '"+type+"' ";
    	List list =   CommonFunction.getCommQueryDAO().findBySQLQuery(wheresql);*/
		String sql = "SELECT KEY,VALUE FROM CST_SYS_PARAM WHERE OWNER = '"+type+"Index' ";
		
		sql = sql + " order by KEY";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**查询商户mcc，包含*
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtMcc(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select mchnt_tp,mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp"
			 + " union select trim(key),trim(value) from cst_sys_param where owner='ALLMCC' and type='00' ";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**查询商户mcc，(带组别)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchtMcc2(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
	//	String sql = "select mchnt_tp,mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp"
		
	    String sql = "select a.mchnt_tp as tp ,'('||b.descr||')'||a.mchnt_tp ||' - '||a.descr as tpdesc from tbl_inf_mchnt_tp a left join tbl_inf_mchnt_tp_grp b on a.mchnt_tp_grp = b.mchnt_tp_grp"
			 + "  union select trim(key) as tp,trim(key) ||' - ' ||trim(value) as tpdesc from cst_sys_param where owner='ALLMCC' and type='00' ";
		
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**根据商户MCC筛选的商户编号（包含*）
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntNoMcc(Object[] params) {
		String mcc = params[1].toString().trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuilder sql = new StringBuilder();
		sql.append("select MCHT_NO,MCHT_NO||'-'||MCHT_NM from TBL_MCHT_BASE_INF WHERE MCHT_STATUS = '0'");
		if(isNotEmpty(mcc) && !mcc.equals("*")){
			sql.append(" and MCC = '").append(mcc).append("'");
		}
		sql.append(" union select key,key|| '-' ||value from cst_sys_param where owner='ALLMCHNT' and type='00'");
		sql.append(" order by MCHT_NO");
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	/**根据商户号筛选终端号，包含*;(若商户号为*，再根据商户MCC筛选终端)
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermIdMchnt(Object[] params) {
		String mchntNo = params[1].toString().trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuilder sql = new StringBuilder();
		sql.append("select trim(TERM_ID) as ID,trim(TERM_ID) as name from TBL_TERM_INF where 1=1");
		if(isNotEmpty(mchntNo) && !mchntNo.equals("*")){
			String mcc = mchntNo.split("-")[0].trim();
			String mchtNo = mchntNo.split("-")[1].trim();
			if(!mchtNo.equals("*")){
				sql.append(" and MCHT_CD = '").append(mchtNo).append("'");
			}else if(!mcc.equals("*")){
				sql.append(" and MCHT_CD in ").
				append("(select MCHT_NO from TBL_MCHT_BASE_INF where MCHT_STATUS = '0' and MCC ='").append(mcc).append("')");
			}
		}
		sql.append(" union select key,value from cst_sys_param where owner='ALLTERM' and type='00'");
		sql.append(" order by ID");
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**根据商户号筛选终端号，不包含*
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTermIdMcht(Object[] params) {
		String mchtNo = params[1].toString().trim();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		StringBuilder sql = new StringBuilder();
		sql.append("select trim(TERM_ID) as ID,trim(TERM_ID) as name from TBL_TERM_INF where 1=1");
		if(isNotEmpty(mchtNo)){
			sql.append(" and MCHT_CD = '").append(mchtNo).append("'");
		}
		sql.append(" order by ID");
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql.toString());
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 获得联机系统定义的通道代码
	 * 
	 * @param params
	 * @return
	 */
	public static LinkedHashMap<String, String> getRoutingChannel(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		/*dataMap.put("1601", "银联通道");
		dataMap.put("1701", "中行通道");
		dataMap.put("1702", "工行通道");
		dataMap.put("1703", "农行通道");
		dataMap.put("1704", "交行通道");
		dataMap.put("1705", "建行通道");*/
//		dataMap.put("1702", "翰鑫通道");
		dataMap.put("1708", "上汽通道");
		dataMap.put("1709", "中石油通道");
		dataMap.put("1710", "上汽HCE通道");
		dataMap.put("1719", "单用途卡通道");
		
		return dataMap;
	}
	
	/**
	 * 行政区号码
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getXingZheng(Object[] params) {//取出所有省和直辖市信息
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		List<Object[]> list = null;
		String sql = "select trim(CITY_CODE),trim(CITY_CODE) ||' - '||trim(CITY_DES) from TBL_CITY_CODE where  (substr(CITY_CODE,3,2)<>'00' " +
				" or (CITY_CODE IN (1000,1100,2900,6900)))";
		
		list = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}
	
	/**
	 * 根据终端厂商来选择终端型号
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getTERMMACHTP(Object[] params) {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select key,value from CST_SYS_PARAM where OWNER = 'TERMMACHTP'" ;
		if(params != null&& params.length>=2 && params[1] != null){
			String reserve = (String) params[1];
            
			if(!StringUtil.isEmpty(reserve)){
				sql+="and RESERVE = '"+reserve+"'";
			}
			
		
		}
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(),obj[1].toString().trim());
		}
		return dataMap;
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
}
