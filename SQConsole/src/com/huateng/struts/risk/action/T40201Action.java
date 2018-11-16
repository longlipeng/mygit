package com.huateng.struts.risk.action;

import java.io.File;
import java.util.List;
import com.huateng.bo.risk.T40201BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.TblCtlCardInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:卡黑名单信息管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-24
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T40201Action extends BaseAction {
	
	T40201BO t40201BO = (T40201BO) ContextUtil.getBean("T40201BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		if("add".equals(method)) {
			log("添加卡黑名单信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新卡黑名单信息");
			rspCode = update();
		} else if("uploadFile".equals(method)) {
			log("批量上传卡黑名单");
			rspCode = uploadFile();
		} else if("delete".equals(method)) {
			log("删除卡黑名单信息");
			rspCode = delete();
		} else {
			return "未知的交易类型";
		}
		return rspCode;
	}
	
	/**
	 * 添加卡黑名单信息
	 * @return
	 * 2010-8-24下午05:15:33
	 * @throws Exception 
	 */
	private String add() throws Exception {
		if(t40201BO.get(saCardNo) != null) {//12.04.20
			if(t40201BO.get(saCardNo).getSaState().equals(DELETE)){
				TblCtlCardInf exsit = t40201BO.get(saCardNo);
				exsit.setSaInitOprId(operator.getOprId());
				exsit.setSaInitTime(CommonFunction.getCurrentDateTime());
				exsit.setSaState(ADD_TO_CHECK);
				exsit.setSaModiZoneNo(operator.getOprBrhId());
				exsit.setRiskRole(riskRole);
				exsit.setRemarkAdd(remarkAdd);
				exsit.setSaModiTime("");
				exsit.setSaModiOprId("");
		
				t40201BO.update(exsit);
//				return "该卡号状态已从删除成为新增待审核";
				return Constants.SUCCESS_CODE;
			}else if(t40201BO.get(saCardNo).getSaState().equals(NORMAL))
				return "该卡号状态为正常";
			else if(t40201BO.get(saCardNo).getSaState().equals(ADD_TO_CHECK))
				return "该卡号状态为新增待审核黑名单";
			else if(t40201BO.get(saCardNo).getSaState().equals(DELETE_TO_CHECK))
				return "该卡号状态为删除待审核黑名单";
		}
		
		TblCtlCardInf tblCtlCardInf = new TblCtlCardInf();
		tblCtlCardInf.setId(saCardNo);
		//tblCtlCardInf.setSaAction(saAction);
		//tblCtlCardInf.setSaLimitAmt(saLimitAmt);
		if(StringUtil.isNull(saAction)){
			tblCtlCardInf.setSaAction("0");
		}else{
			tblCtlCardInf.setSaAction(saAction);
		}
		if(StringUtil.isNull(saLimitAmt)){
			tblCtlCardInf.setSaLimitAmt("0.0");
		}else{
			tblCtlCardInf.setSaLimitAmt(saLimitAmt);
		}
		tblCtlCardInf.setSaInitZoneNo(operator.getOprBrhId());
		tblCtlCardInf.setSaInitOprId(operator.getOprId());
		tblCtlCardInf.setSaInitTime(CommonFunction.getCurrentDateTime());
		tblCtlCardInf.setSaModiZoneNo(operator.getOprBrhId());
		tblCtlCardInf.setSaState(ADD_TO_CHECK);
		tblCtlCardInf.setSaLimitAmtOld(String.valueOf(0.0));
		tblCtlCardInf.setSaActionOld(String.valueOf(0));
		tblCtlCardInf.setRiskRole(riskRole);
		tblCtlCardInf.setRemarkAdd(remarkAdd);
		tblCtlCardInf.setSaModiOprId("");
		
		rspCode = t40201BO.add(tblCtlCardInf);
	
		
		return rspCode;
	}
	
	/**
	 * 更新卡黑名单信息
	 * @param tblCtlCardInf
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:35:10
	 */
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getCardInfList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblCtlCardInf tblCtlCardInf = new TblCtlCardInf();
			BeanUtils.setObjectWithPropertiesValue(tblCtlCardInf,jsonBean,true);
			tblCtlCardInf.setSaModiZoneNo(operator.getOprBrhId());
			tblCtlCardInf.setSaInitOprId(operator.getOprId());
			tblCtlCardInf.setSaInitTime(CommonFunction.getCurrentDateTime());
			t40201BO.update(tblCtlCardInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除卡黑名单信息
	 * @param tblCtlCardInf
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:35:10
	 */
	private String delete() throws Exception {
//		t40201BO.delete(saCardNo);
//		return Constants.SUCCESS_CODE;
		if(t40201BO.get(saCardNo).getSaState().equals(DELETE)) {
			return "该卡黑名单已是删除状态，请勿重复删除";
		}
		TblCtlCardInf tblCtlCardInf = t40201BO.get(saCardNo);
		if(ADD_TO_CHECK.equals(tblCtlCardInf.getSaState())){
			t40201BO.delete(saCardNo);
		}else{
			tblCtlCardInf.setSaState(DELETE_TO_CHECK);
			tblCtlCardInf.setRemarkAdd(remarkAdd);
			tblCtlCardInf.setSaModiZoneNo(operator.getOprBrhId());
			tblCtlCardInf.setSaInitOprId(operator.getOprId());
			tblCtlCardInf.setSaInitTime(CommonFunction.getCurrentDateTime());
			tblCtlCardInf.setSaModiOprId("");
			t40201BO.update(tblCtlCardInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 批量上传卡黑名单信息
	 * @return
	 * 2010-8-24下午07:25:53
	 * @throws Exception 
	 */
	private String uploadFile() throws Exception {
		rspCode = t40201BO.importFile(xlsFile,xlsFileFileName,operator);
		return rspCode;
	}
	
	// 交易卡号
	private String saCardNo;
	// 受控金额
	private String saLimitAmt;
	// 受控动作
	private String saAction="2";
	// 文件集合
	private List<File> xlsFile;
	// 文件名称集合
	private List<String> xlsFileFileName;
	// 黑名单卡修改集
	private String cardInfList;
	
	private String riskRole;   // 风险规则
	private String remarkAdd;   // 录入备注
	
	/**
	 * @return the saCardNo
	 */
	public String getSaCardNo() {
		return saCardNo;
	}

	/**
	 * @param saCardNo the saCardNo to set
	 */
	public void setSaCardNo(String saCardNo) {
		this.saCardNo = saCardNo;
	}

	/**
	 * @return the saLimitAmt
	 */
	public String getSaLimitAmt() {
		return saLimitAmt;
	}

	/**
	 * @param saLimitAmt the saLimitAmt to set
	 */
	public void setSaLimitAmt(String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	/**
	 * @return the saAction
	 */
	public String getSaAction() {
		return saAction;
	}

	/**
	 * @param saAction the saAction to set
	 */
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the xlsFile
	 */
	public List<File> getXlsFile() {
		return xlsFile;
	}

	/**
	 * @param xlsFile the xlsFile to set
	 */
	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	/**
	 * @return the xlsFileFileName
	 */
	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	/**
	 * @param xlsFileFileName the xlsFileFileName to set
	 */
	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}

	/**
	 * @return the cardInfList
	 */
	public String getCardInfList() {
		return cardInfList;
	}

	/**
	 * @param cardInfList the cardInfList to set
	 */
	public void setCardInfList(String cardInfList) {
		this.cardInfList = cardInfList;
	}

	public String getRiskRole() {
		return riskRole;
	}

	public void setRiskRole(String riskRole) {
		this.riskRole = riskRole;
	}

	public String getRemarkAdd() {
		return remarkAdd;
	}

	public void setRemarkAdd(String remarkAdd) {
		this.remarkAdd = remarkAdd;
	}
	
	
	
	
}
