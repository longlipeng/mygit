package com.huateng.struts.risk.action;

import java.io.File;
import java.util.List;

import com.huateng.bo.risk.T40601BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.TblCnpcCardInf;
import com.huateng.po.TblCnpcCardInfPK;
import com.huateng.po.TblCtlCardInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:中石油卡黑名单信息管理
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
public class T40601Action extends BaseAction {
	
	T40601BO t40601BO = (T40601BO) ContextUtil.getBean("T40601BO");
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	private String cardLevel;
	private String customerProfile;
	private String saState;
	private String initOprId;
	private String checkCode;
//	roleInfoList
	private String roleInfoList;
	
	
	public String getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(String roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getInitOprId() {
		return initOprId;
	}

	public void setInitOprId(String initOprId) {
		this.initOprId = initOprId;
	}

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	public String getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(String customerProfile) {
		this.customerProfile = customerProfile;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		if("add".equals(method)) {
			log("添加卡黑名单信息");
			rspCode = add();
		} /*else if("update".equals(method)) {
			log("更新卡黑名单信息");
			rspCode = update();
		} else if("uploadFile".equals(method)) {
			log("批量上传卡黑名单");
			rspCode = uploadFile();
		} */else if("delete".equals(method)) {
			log("删除卡黑名单信息");
			rspCode = delete();
		}else if("accept".equals(method)) {//update
			log("通过卡黑名单信息");
			rspCode = accept();
		}else if("refuse".equals(method)) {
			log("拒绝卡黑名单信息");
			rspCode = refuse();
		}else if("update".equals(method)) {
			log("更新黑名单信息");
			rspCode = update();
		}  else {
			return "未知的交易类型";
		}
		return rspCode;
	}
	
	private String refuse()  throws Exception {
		if (initOprId.equals(operator.getOprId())) {
			return "拒绝人不能与申请人相同";
		}
		/*System.out.println("customerProfile："+customerProfile);
		System.out.println("====================================");
		System.out.println("cardLevel："+cardLevel);
		System.out.println("====================================");
		System.out.println("saState："+saState);*/
		if(saState.equals("3")) {
			log("拒绝中石油黑名单卡");
			String deSql = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '6', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"'  WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
			CommonFunction.getCommQueryDAO().excute(deSql);
			String queySql = "select count(*) from TBL_CNPC_BLACK_CARD_INFO where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ customerProfile.trim() +"'";
			String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(queySql);
			if (!findCountBySQLQuery.equals("0")) {
				try {
					TblCnpcCardInf cnpcCardInf = new TblCnpcCardInf();
					TblCnpcCardInfPK cardInfPK = new TblCnpcCardInfPK();
					cardInfPK.setCardLevel(cardLevel);
					cardInfPK.setCustomerProfile(customerProfile);
					cnpcCardInf.setId(cardInfPK);
					cnpcCardInf.setSaState("6");
					cnpcCardInf.setCheckCode(customerProfile+cardLevel);
					rspCode = t40601BO.update(cnpcCardInf);
				} catch (Exception e) {
					String deSql1 = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '3', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"'  WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
					CommonFunction.getCommQueryDAO().excute(deSql1);
					return "拒绝失败";
				}
				return rspCode;
			}	
			return "00";
		} else if(saState.equals("1")){
			log("新增中石油黑名单卡拒绝");
			try {
				String deSql = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '5', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"' WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
				CommonFunction.getCommQueryDAO().excute(deSql);
				return "00";
			} catch (Exception e) {
				String deSql1 = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '1', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"' WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
				CommonFunction.getCommQueryDAO().excute(deSql1);
				e.printStackTrace();
				log("中石油黑名单卡新增拒绝："+e.getMessage());
				return "审核拒绝失败";
			}
		}
		return rspCode;
	}

	private String accept() throws Exception {
		if (initOprId.equals(operator.getOprId())) {
			return "审核人不能与申请人相同";
		}
		/*System.out.println("customerProfile："+customerProfile);
		System.out.println("====================================");
		System.out.println("cardLevel："+cardLevel);
		System.out.println("====================================");
		System.out.println("saState："+saState);*/
		if(saState.equals("3")) {
			log("删除中石油黑名单卡");
			String deSql = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '7', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"'  WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
			CommonFunction.getCommQueryDAO().excute(deSql);
			String queySql = "select count(*) from TBL_CNPC_BLACK_CARD_INFO where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ customerProfile.trim() +"'";
			String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(queySql);
			if (!findCountBySQLQuery.equals("0")) {
				try {
					TblCnpcCardInf cnpcCardInf = new TblCnpcCardInf();
					TblCnpcCardInfPK cardInfPK = new TblCnpcCardInfPK();
					cardInfPK.setCardLevel(cardLevel);
					cardInfPK.setCustomerProfile(customerProfile);
					cnpcCardInf.setId(cardInfPK);
					cnpcCardInf.setSaState("7");
					cnpcCardInf.setCheckCode(customerProfile+cardLevel);
					rspCode = t40601BO.update(cnpcCardInf);
				} catch (Exception e) {
					String deSql1 = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '3', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"'  WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
					CommonFunction.getCommQueryDAO().excute(deSql1);
					return "审核失败";
				}
				return rspCode;
			}	
			return "00";
		}else if(saState.equals("1")){
			log("新增中石油黑名单卡");
			String deSql = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '0', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"' WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
			CommonFunction.getCommQueryDAO().excute(deSql);
			try {
				TblCnpcCardInf cnpcCardInf = new TblCnpcCardInf();
				TblCnpcCardInfPK cardInfPK = new TblCnpcCardInfPK();
				cardInfPK.setCustomerProfile(customerProfile);
				cardInfPK.setCardLevel(cardLevel);
				cnpcCardInf.setId(cardInfPK);
				cnpcCardInf.setCheckCode(customerProfile+cardLevel);
				cnpcCardInf.setVerifierOprId(operator.getOprId());
				cnpcCardInf.setSaState("0");
				cnpcCardInf.setVerifierTime(CommonFunction.getCurrentDateTime());
				t40601BO.add(cnpcCardInf);
				
				return "00";
			} catch (Exception e) {
				String deSql1 = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '1', VERIFIER_OPR_ID ='"+operator.getOprId()+"', VERIFIER_TIME = '"+CommonFunction.getCurrentDateTime()+"' WHERE CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
				CommonFunction.getCommQueryDAO().excute(deSql1);
				e.printStackTrace();
				log("中石油黑名单卡新增失败："+e.getMessage());
				return "审核失败";
			}
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
		/*System.out.println("customerProfile========"+customerProfile);
		System.out.println("cardLevel========"+cardLevel);*/
		String queySql = "select count(*) from TBL_CNPC_BLACK_CARD_INFO_TMP where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
		String findCountBySQLQuery = CommonFunction.getCommQueryDAO().findCountBySQLQuery(queySql);
		if (!findCountBySQLQuery.equals("0")) {
			String saStateSql = "select SA_STATE from TBL_CNPC_BLACK_CARD_INFO_TMP where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
			String saState = CommonFunction.getCommQueryDAO().findCountBySQLQuery(saStateSql);
			if (saState.equals("7")) {
				String queyCount = "";
				try {
					String delteTmpSql = "DELETE FROM TBL_CNPC_BLACK_CARD_INFO_TMP where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
					CommonFunction.getCommQueryDAO().excute(delteTmpSql);
					try {
						String queyCountSql = "select count(*) from TBL_CNPC_BLACK_CARD_INFO where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
						queyCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(queyCountSql);
					} catch (Exception e) {
						log("查询数据失败"+e.getMessage());
						return "查询数据失败";
					}
					if (queyCount.equals("0")) {
						try {
							String delteSql = "DELETE FROM TBL_CNPC_BLACK_CARD_INFO where CUSTOMER_PROFILE = '"+ customerProfile.trim() +"'and CARD_LEVEL = '"+ cardLevel.trim() +"'";
							CommonFunction.getCommQueryDAO().excute(delteSql);
						} catch (Exception e) {
							log("删除失败"+e.getMessage());
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					log("删除失败"+e.getMessage());
					return "运行时错误";
				}
			}else{
				return "存在相同数据";
			}
		}
		try {
			String initSql = "INSERT INTO TBL_CNPC_BLACK_CARD_INFO_TMP (CUSTOMER_PROFILE, CARD_LEVEL, CHECK_CODE, SA_STATE, INIT_OPR_ID, INIT_TIME) VALUES ('"+customerProfile+"', '"+cardLevel+"', '"+customerProfile+cardLevel+"', '"+ 1 +"', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"')";
			System.out.println("存入中石油黑名单表SQL："+initSql);
			CommonFunction.getCommQueryDAO().excute(initSql);
			rspCode = "00";
		} catch (Exception e) {
			log("中石油黑名单卡报错："+e.getMessage());
			e.printStackTrace();
		}
		/*if(t40601BO.get(saCardNo) != null) {//12.04.20
			if(t40601BO.get(saCardNo).getSaState().equals(DELETE)){
				TblCtlCardInf exsit = t40601BO.get(saCardNo);
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
		}*/
		
	/*	TblCtlCardInf tblCtlCardInf = new TblCtlCardInf();
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
		
		rspCode = t40201BO.add(tblCtlCardInf);*/
	
		
		return rspCode;
	}
	public String update() throws Exception {
//		customerProfile : record.get('customerProfile'),
//		cardLevel : record.get('cardLevel'),
//		checkCode : record.get('checkCode')
		jsonBean.parseJSONArrayData(getRoleInfoList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			String customerProfile = jsonBean.getJSONDataAt(i).getString("customerProfile");
			String checkCode = jsonBean.getJSONDataAt(i).getString("checkCode");
			String cardLevel = jsonBean.getJSONDataAt(i).getString("cardLevel");
			/*System.err.println("customerProfile+++++++"+customerProfile);
			System.err.println("checkCode+++++++++++++"+checkCode);
			System.err.println("cardLevel+++++++++++++"+cardLevel);*/
		}
		return cardLevel;
	}
	 //中石油卡黑名单删除
	private String delete() throws Exception {
		/*System.err.println("customerProfile+++++++"+customerProfile);
		System.err.println("========================================");
		System.out.println("cardLevel+++++++++++++"+cardLevel);*/
//		String deSql = "DELETE FROM TBL_CNPC_BLACK_CARD_INFO_TMP WHERE  CUSTOMER_PROFILE = '"+customerProfile+"' AND CARD_LEVEL = '"+cardLevel+"'";
		String upSql = "UPDATE TBL_CNPC_BLACK_CARD_INFO_TMP SET SA_STATE = '3' WHERE  CUSTOMER_PROFILE = '"+customerProfile+"' AND CARD_LEVEL = '"+cardLevel+"'";
		CommonFunction.getCommQueryDAO().excute(upSql);
		/*TblCnpcCardInf cnpcCardInf = new TblCnpcCardInf();
		TblCnpcCardInfPK cardInfPK = new TblCnpcCardInfPK();
		cardInfPK.setCardLevel(cardLevel);
		cardInfPK.setCustomerProfile(customerProfile);
		cnpcCardInf.setId(cardInfPK);
		cnpcCardInf.setSaState("3");
		cnpcCardInf.setCheckCode(customerProfile+cardLevel);
		rspCode = t40601BO.update(cnpcCardInf);*/
		return Constants.SUCCESS_CODE;
	}
/*	*//**
	 * 更新卡黑名单信息
	 * @param tblCtlCardInf
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:35:10
	 *//*
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
	
	*//**
	 * 删除卡黑名单信息
	 * @param tblCtlCardInf
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:35:10
	 *//*
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
	
	*//**
	 * 批量上传卡黑名单信息
	 * @return
	 * 2010-8-24下午07:25:53
	 * @throws Exception 
	 *//*
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
	
	*//**
	 * @return the saCardNo
	 *//*
	public String getSaCardNo() {
		return saCardNo;
	}

	*//**
	 * @param saCardNo the saCardNo to set
	 *//*
	public void setSaCardNo(String saCardNo) {
		this.saCardNo = saCardNo;
	}

	*//**
	 * @return the saLimitAmt
	 *//*
	public String getSaLimitAmt() {
		return saLimitAmt;
	}

	*//**
	 * @param saLimitAmt the saLimitAmt to set
	 *//*
	public void setSaLimitAmt(String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	*//**
	 * @return the saAction
	 *//*
	public String getSaAction() {
		return saAction;
	}

	*//**
	 * @param saAction the saAction to set
	 *//*
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	*//**
	 * @return the xlsFile
	 *//*
	public List<File> getXlsFile() {
		return xlsFile;
	}

	*//**
	 * @param xlsFile the xlsFile to set
	 *//*
	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	*//**
	 * @return the xlsFileFileName
	 *//*
	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	*//**
	 * @param xlsFileFileName the xlsFileFileName to set
	 *//*
	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}

	*//**
	 * @return the cardInfList
	 *//*
	public String getCardInfList() {
		return cardInfList;
	}

	*//**
	 * @param cardInfList the cardInfList to set
	 *//*
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
	*/
	
	
	
}
