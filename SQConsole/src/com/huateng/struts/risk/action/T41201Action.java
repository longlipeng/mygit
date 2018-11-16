 package com.huateng.struts.risk.action;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.huateng.bo.risk.T41202BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.po.risk.TblWhiteListTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 商户白名单管理
 * Copyright: Copyright (c) 2014-08-01
 * @author shiyiwen
 * 
 */
@SuppressWarnings("serial")
public class T41201Action extends BaseAction {
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	T41202BO t41202BO = (T41202BO) ContextUtil.getBean("T41202BO");  //白名单临时表
	
//	public static String ADD_TO_CHECK = "0";
//	public static String DELETE = "1";
//	public static String NORMAL = "2";
//	public static String MODIFY_TO_CHECK = "3";
//	public static String DELETE_TO_CHECK = "4"; 
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户白名单信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新商户黑名单信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除商户白名单信息");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 删除商户白名单信息
	 * @return
	 * 2010-8-26下午11:56:26
	 * @throws Exception 
	 */
	private String delete() throws Exception {
		
	if(t41202BO.get(uuid).getState().equals("1")) {
		return "该商户黑名单已是删除状态，请勿重复删除";
	}
	TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
	if(ADD_TO_CHECK.equals(tblWhiteListTmp.getState())){
		t41202BO.delete(uuid);
	}else{
		tblWhiteListTmp.setState(DELETE_TO_CHECK);
		tblWhiteListTmp.setInsOpr(operator.getOprId());
		tblWhiteListTmp.setInsDt(CommonFunction.getCurrentDateTime());
		tblWhiteListTmp.setAppRemark(appRemark);
		tblWhiteListTmp.setUpdOpr("");
		
		t41202BO.update(tblWhiteListTmp);
		
		
	}
	return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 添加商户白名单信息
	 * @return
	 * 2014-08-04
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
        //除非已删除的商户，其余情况下不得重复添加商户
		String mchtNoSql = "select count(*) from tbl_white_list_tmp where MCHT_NO = '"+mchtNo+"' and STATE <> '1'";
		String result= CommonFunction.getCommQueryDAO().findCountBySQLQuery(mchtNoSql);
		if(!("0").equals(result)){
			return "该商户已在新增列表中";
		}
		TblWhiteListTmp tblWhiteListTmp = new TblWhiteListTmp();
		
		BeanUtils.copyProperties(tblWhiteListTmp, this);  //商户号，有效期，起始日期，申请备注
		tblWhiteListTmp.setAppRemark(appRemark.trim());
		tblWhiteListTmp.setUuid(StringUtil.getUUID());
		tblWhiteListTmp.setAddType("0");
		tblWhiteListTmp.setInsOpr(operator.getOprId());
		tblWhiteListTmp.setInsDt(CommonFunction.getCurrentDateTime());
		tblWhiteListTmp.setUpdOpr("");
		tblWhiteListTmp.setState(ADD_TO_CHECK);
		
		rspCode = t41202BO.add(tblWhiteListTmp);
		return rspCode;
	}
	
	/**
	 * 修改商户白名单信息
	 * @return
	 * 2014-08-04
	 * @throws Exception 
	 */
	private String update() throws Exception {
		System.out.println(appRemark);
		
		jsonBean.parseJSONArrayData(getWhiteInfList());		
		int len = jsonBean.getArray().size();
		String beginDate = "";
		String beginDt[] = null;
		List<TblWhiteListTmp> list = new ArrayList<TblWhiteListTmp>();
		for(int i = 0; i < len; i++) {				
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
		//	TblWhiteList tblWhiteList = new TblWhiteList();
			TblWhiteListTmp tblWhiteListTmp = new TblWhiteListTmp();
			
			BeanUtils.setObjectWithPropertiesValue(tblWhiteListTmp,jsonBean,false);
//			TblWhiteList tblWhiteListOld = t41201BO.get(tblWhiteList.getMchtNo());
			
//			tblWhiteList.setMchtNo(tblWhiteListOld.getMchtNo());
//			tblWhiteList.setValidity(tblWhiteListOld.getValidity());
//			tblWhiteList.setBeginDt(tblWhiteListOld.getBeginDt());
		    
			beginDate = tblWhiteListTmp.getBeginDt();
			if(beginDate.length() > 8){			
			beginDt = beginDate.substring(0, 10).split("-");
			tblWhiteListTmp.setBeginDt(beginDt[0]+beginDt[1]+beginDt[2]);
			}
			tblWhiteListTmp.setInsDt(CommonFunction.getCurrentDateTime());
			tblWhiteListTmp.setInsOpr(operator.getOprId());
			tblWhiteListTmp.setUpdOpr("");
			tblWhiteListTmp.setState(MODIFY_TO_CHECK);
			tblWhiteListTmp.setAppRemark(appRemark.trim());
			list.add(tblWhiteListTmp);
			//t41202BO.update(tblWhiteList);
		}
		t41202BO.update(list);
		return Constants.SUCCESS_CODE;
	}
	
	
	private String uuid;
	private String mchtNo;   // 白名单商户号 
	private String validity;//有效期
	private String beginDt;//起始日期
	private String whiteInfList;
	private String appRemark;  //备注
	
	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(String beginDt) {
		this.beginDt = beginDt;
	}

	public String getWhiteInfList() {
		return whiteInfList;
	}

	public void setWhiteInfList(String whiteInfList) {
		this.whiteInfList = whiteInfList;
	}

	public String getAppRemark() {
		return appRemark;
	}

	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
    
	
	
}
