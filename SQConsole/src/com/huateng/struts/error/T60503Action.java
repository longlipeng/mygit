package com.huateng.struts.error;

import com.huateng.bo.error.T60501BO;
import com.huateng.common.Constants;
import com.huateng.po.error.BthErrRegistTxn;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;

public class T60503Action extends BaseAction {

private static final long serialVersionUID = 1L;
	
private T60501BO t60501BO = (T60501BO) ContextUtil.getBean("T60501BO");
	
	//差错编号
	private String errSeqNo;
	/**
	 * @return the errSeqNo
	 */
	//审核状态
	private String errStatus;
	/**
	 * @return the errSeqNoList
	 */
	public String getErrSeqNoList() {
		return errSeqNoList;
	}


	/**
	 * @param errSeqNoList the errSeqNoList to set
	 */
	public void setErrSeqNoList(String errSeqNoList) {
		this.errSeqNoList = errSeqNoList;
	}


	private String errSeqNoList;

	private String resv1;

	public String getErrSeqNo() {
		return errSeqNo;
	}


	/**
	 * @param errSeqNo the errSeqNo to set
	 */
	public void setErrSeqNo(String errSeqNo) {
		this.errSeqNo = errSeqNo;
	}
	/**
	 * @return the errStatus
	 */
	public String getErrStatus() {
		return errStatus;
	}


	/**
	 * @param errStatus the errStatus to set
	 */
	public void setErrStatus(String errStatus) {
		this.errStatus = errStatus;
	}
	


	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
		if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，差错调帐登记操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	
	/**
	 * 更新差错审核
	 * @return
	 */
	private String update() throws Exception {
////		List<BthErrRegistTxn> bthErrRegistTxnList = new ArrayList<BthErrRegistTxn>();		 
//		BthErrRegistTxn bthErrRegistTxn = t60501BO.get(errSeqNo);
//		System.out.println(t60501BO.get(errSeqNo));
////		System.out.println(bthErrRegistTxn.);
//		if(bthErrRegistTxn == null){
//			return "错误";
//		}else{
//			bthErrRegistTxn.setErrStatus("1");
////			bthErrRegistTxnList.add(bthErrRegistTxn);
//		}
//		t60501BO.update(bthErrRegistTxn);
//		
////		t60501BO.update(bthErrRegistTxnList);
//		return Constants.SUCCESS_CODE;
		jsonBean.parseJSONArrayData(getErrSeqNoList());
		int len = jsonBean.getArray().size();
//		List<BthErrRegistTxn> bthErrRegistTxn = new ArrayList<BthErrRegistTxn>();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BthErrRegistTxn bthErrRegistTxn = new BthErrRegistTxn();
			BeanUtils.setObjectWithPropertiesValue(bthErrRegistTxn,jsonBean,true);
//			bthErrRegistTxn.setErrStatus("1");
			t60501BO.update(bthErrRegistTxn);
		}
		return Constants.SUCCESS_CODE;
	}
}
