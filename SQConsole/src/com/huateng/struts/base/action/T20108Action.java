package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T20108BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblRespCodeInfo;
import com.huateng.po.base.TblRespCodeInfoTrue;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
//import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
@SuppressWarnings("serial")
public class T20108Action extends BaseAction {
private T20108BO t20108BO = (T20108BO)ContextUtil.getBean("T20108BO");
private String respCodeId;
private String respCodeName;
private String respCodeNum;
private String respCodeDesc;
private String respCodeList;
private String statusId;

public String getStatusId() {
	return statusId;
}
public void setStatusId(String statusId) {
	this.statusId = statusId;
}
public String getRespCodeId() {
	return respCodeId;
}
public void setRespCodeId(String respCodeId) {
	this.respCodeId = respCodeId;
}

public String getRespCodeName() {
	return respCodeName;
}
public void setRespCodeName(String respCodeName) {
	this.respCodeName = respCodeName;
}
public String getRespCodeNum() {
	return respCodeNum;
}
public void setRespCodeNum(String respCodeNum) {
	this.respCodeNum = respCodeNum;
}
public String getRespCodeDesc() {
	return respCodeDesc;
}
public void setRespCodeDesc(String respCodeDesc) {
	this.respCodeDesc = respCodeDesc;
}
public String getRespCodeList() {
	return respCodeList;
}
public void setRespCodeList(String respCodeList) {
	this.respCodeList = respCodeList;
}


public T20108BO getT20108BO() {
		return t20108BO;
}

public void setT20107BO(T20108BO t20107bo) {
		t20108BO = t20107bo;
}

@Override
protected String subExecute(){
	try {
		if("accept".equals(method)) {
			rspCode = accept();			
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		}
	} catch (Exception e) {
		e.printStackTrace();
		log("操作员编号：" + operator.getOprId()+ "，虚拟柜员维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
	}
	return rspCode;
}


public String accept() throws Exception{
	
		return Constants.SUCCESS_CODE;
}
/**拒绝
 * refuse code
 * @return
 */
public String refuse() {
	return Constants.SUCCESS_CODE;
}
	
}
