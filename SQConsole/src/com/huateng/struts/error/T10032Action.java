package com.huateng.struts.error;

import com.huateng.bo.error.T10033BO;
import com.huateng.common.Constants;
import com.huateng.po.error.TblAlgoDtlCheck;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10032Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T10033BO t10033BO = (T10033BO) ContextUtil.getBean("T10033BO");

	// 修改集
	private String tblAlgoDtlList;
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("cuts".equals(method)) {
			log("追扣已清算交易");
			rspCode = cuts();
		}
		return rspCode;
	}

	/**追扣已清算交易
	 * @return
	 * @throws Exception 
	*/
	public String cuts() throws Exception {	
		jsonBean.parseJSONArrayData(getTblAlgoDtlList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			TblAlgoDtlCheck tblAlgoDtlCheck = new TblAlgoDtlCheck();
			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			tblAlgoDtlCheck.setDateSettlmt(jsonBean.getStringElementByKey("dateSettlmt"));
			tblAlgoDtlCheck.setTxnKey(jsonBean.getStringElementByKey("txnKey"));
//			BeanUtils.setObjectWithPropertiesValue(tblAlgoDtlCheck,jsonBean,true);
			tblAlgoDtlCheck.setOprId(operator.getOprId());
			tblAlgoDtlCheck.setSaState("0");
			tblAlgoDtlCheck.setUpdateTime(CommonFunction.getCurrentDateTime());
			if(t10033BO.get(jsonBean.getStringElementByKey("dateSettlmt"), jsonBean.getStringElementByKey("txnKey")) != null){
				String id = t10033BO.get(jsonBean.getStringElementByKey("dateSettlmt"), jsonBean.getStringElementByKey("txnKey"));
				tblAlgoDtlCheck = t10033BO.get(id);
				tblAlgoDtlCheck.setOprId(operator.getOprId());
				tblAlgoDtlCheck.setSaState("0");
				tblAlgoDtlCheck.setUpdateTime(CommonFunction.getCurrentDateTime());
				t10033BO.update(tblAlgoDtlCheck);
			}else
				t10033BO.add(tblAlgoDtlCheck);
		}
		return Constants.SUCCESS_CODE;
	}
	
	public T10033BO getT10033BO() {
		return t10033BO;
	}

	public void setT10033BO(T10033BO t10033bo) {
		t10033BO = t10033bo;
	}
	
	public String getTblAlgoDtlList() {
		return tblAlgoDtlList;
	}

	public void setTblAlgoDtlList(String tblAlgoDtlList) {
		this.tblAlgoDtlList = tblAlgoDtlList;
	}
}
