
package com.huateng.struts.base.action;


import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T1010601BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.base.TblInstBdbBankCodeTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
 
public class T1010601Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	T1010601BO t1010601BO = (T1010601BO) ContextUtil.getBean("T1010601BO");
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		 if("delete".equals(method)) {
			log("删除机构开户行信息");
			rspCode = delete();
		} else if("delete2".equals(method)){
			log("修改机构开户行信息");
			rspCode = delete2();
		}
		return rspCode;
	}

	

	private String delete() {
		t1010601BO.delete(id);
		

		return Constants.SUCCESS_CODE;
	}
	
	private String delete2() {

		
		TblInstBdbBankCodeTmp tblInstBdbBankCodeTmp = new TblInstBdbBankCodeTmp();
		tblInstBdbBankCodeTmp = t1010601BO.query(id);
		String state = tblInstBdbBankCodeTmp.getState();
		//如果是正常状态，那么状态会变为删除待审核
		if("2".equals(state)){
			tblInstBdbBankCodeTmp.setState("4");   //删除待审核
			t1010601BO.update(tblInstBdbBankCodeTmp);
		}else if("0".equals(state)){   //如果是新增待审核，那么删除会直接删掉
			t1010601BO.delete(id);
		}
		
		
				

		return Constants.SUCCESS_CODE;
	}
	
	 private String id;
	 private String tmpNo;
	 
	public String getTmpNo() {
		return tmpNo;
	}



	public void setTmpNo(String tmpNo) {
		this.tmpNo = tmpNo;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}
	 
}
