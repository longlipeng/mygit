package com.huateng.struts.base.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.huateng.bo.base.T60101BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblIssueBranchInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T60101Action extends BaseAction {
	private static final long serialVersionUID = -3119552449953945813L;

	T60101BO t60101BO = (T60101BO) ContextUtil.getBean("T60101BO");
	private String id;
	private String branchId;
	private String branchName;
	private String branchAddr;
	private String tblIssueBranchInfList;
	private String termSn;

	public String getTermSn() {
		return termSn;
	}

	public void setTermSn(String termSn) {
		this.termSn = termSn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddr() {
		return branchAddr;
	}

	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
	}

	public String getTblIssueBranchInfList() {
		return tblIssueBranchInfList;
	}

	public void setTblIssueBranchInfList(String tblIssueBranchInfList) {
		this.tblIssueBranchInfList = tblIssueBranchInfList;
	}

	@Override
	protected String subExecute() throws Exception {
		try {
			if ("add".equals(method)) {
				rspCode = add();
			} else if ("delete".equals(method)) {
				rspCode = delete();
			} else if ("delete1".equals(method)) {
				rspCode = delete1();
			} else if ("update".equals(method)) {
				rspCode = update();
			} else {
				return "错误请求！";
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId() + "，门店维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
		}
		return rspCode;
	}

	private String delete1() {
		System.out.println("终端序列号："+termSn);
		String sqlStr = "delete from TBL_ISSUE_CARD_TERM_INF where TERM_SN = '"+termSn+"'";
		try {
			CommonFunction.getCommQueryDAO().excute(sqlStr);
		} catch (Exception e) {
			return "终端发卡终端删除失败！";
		}
		return Constants.SUCCESS_CODE;
	}

	public static String codeAddOne(String code, int len) {
		Integer intHao = Integer.parseInt(code);
		// intHao++;
		String strHao = intHao.toString();
		while (strHao.length() < len) {
			strHao = "0" + strHao;
		}
		return strHao;
	}

	private String add() {
		String sql = "select max(id) from TBL_ISSUE_CARD_BRANCH_INF";
		String result = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		
		if (result.equals("")) {
			result = "10000001";
		} else {
			int i = result.trim().length();
			int j = Integer.parseInt(result.trim());
			int k = String.valueOf(j).length();
			if (i == k) {
				result = (j + 1) + "";
			} else {
				j = j + 1;
					result = codeAddOne(j+"", 8);
			}

		}
		String sql1 = "select count(*) from TBL_ISSUE_CARD_BRANCH_INF WHERE BRANCH_ID = '"+branchId.trim()+"'";
		String result1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		if (!result1.equals("0")) {
			return "门店号不能重复！";
		}
		Date nowTime=new Date(); 
		SimpleDateFormat format =new SimpleDateFormat("yyyyMMddHHmmss"); 
		String createTime = format.format(nowTime);
		TblIssueBranchInf tblIssueBranchInf = new TblIssueBranchInf();
		tblIssueBranchInf.setId(result);
		tblIssueBranchInf.setBranchName(branchName);
		tblIssueBranchInf.setBranchId(branchId);
		tblIssueBranchInf.setBranchAddr(branchAddr);
		tblIssueBranchInf.setReserve1(createTime);
		t60101BO.add(tblIssueBranchInf);
		log("添加门店信息成功，等待审核。");
		return Constants.SUCCESS_CODE;
	}

	private String update() {
		jsonBean.parseJSONArrayData(getTblIssueBranchInfList());
		int len = jsonBean.getArray().size();
		TblIssueBranchInf tblIssueBranchInf = null;
		List<TblIssueBranchInf> tblIssueBranchInfList = new ArrayList<TblIssueBranchInf>(len);
		try {
			for (int i = 0; i < len; i++) {
				tblIssueBranchInf = t60101BO.get(jsonBean.getJSONDataAt(i).getString("branchId"));
				BeanUtils.setObjectWithPropertiesValue(tblIssueBranchInf, jsonBean, false);
				tblIssueBranchInf.setBranchId(tblIssueBranchInf.getBranchId());
				tblIssueBranchInf.setBranchName(tblIssueBranchInf.getBranchName());
				tblIssueBranchInf.setBranchAddr(tblIssueBranchInf.getBranchAddr());
				tblIssueBranchInf.setId(tblIssueBranchInf.getId());
				tblIssueBranchInfList.add(tblIssueBranchInf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "门店更新失败";
		}
		t60101BO.update(tblIssueBranchInfList);

		log("更新门店信息成功");
		return Constants.SUCCESS_CODE;
	}

	private String delete() {
		TblIssueBranchInf tblIssueBranchInf = t60101BO.get(branchId);
		if (tblIssueBranchInf == null) {
			return "门店信息不存在！";
		}
		try {
			t60101BO.delete(branchId);
		} catch (Exception e) {
			e.printStackTrace();
			return "门店删除失败！";
		}
		log("删除门店信息成功");
		return Constants.SUCCESS_CODE;
	}

}
