package com.huateng.struts.risk.action;

import java.util.List;

import com.huateng.bo.risk.T40208BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskMchtTranCtl;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 商户交易黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T40208Action extends BaseAction {
	
	T40208BO t40208BO = (T40208BO) ContextUtil.getBean("T40208BO");
	private String id;
	private String mchtNo;//商户号
	private String saState;//状态
	private String oprID;//操作员编号
	private String updateTime;//修改时间
	private String txnNum;//交易代码
	private String bussType;//业务类型
	private String channel;//交易渠道
	private String channelOld;//修改后的
	private String bussTypeOld;//修改后的
	private String txnNumOld;//修改后的
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	// 商户交易黑名单修改集
	private String mchtTranCtlList;
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户交易黑名单信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新商户交易黑名单信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除商户交易黑名单信息");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 添加商户交易黑名单信息
	 * @return
	 * 2010-8-26下午11:19:52
	 * @throws Exception 
	 */
	public String add() throws Exception{
//		TblRiskMchtTranCtl before = new TblRiskMchtTranCtl();
//		if(this.mchtNo!=null && !this.mchtNo.equals("")){
//			before.setMchtNo(this.mchtNo);
//		}
//		if(this.bussType!=null && !this.bussType.equals("")){
//			before.setBussType(this.bussType);
//		}
//		if(this.txnNum!=null && !this.txnNum.equals("")){
//			before.setTxnNum(this.txnNum);
//		}
		
//		if(t40208BO.get(this.id)!= null) {
//			if(t40208BO.get(id).getSaState().equals(DELETE)){
//				TblRiskMchtTranCtl exsit = t40208BO.get(id);
//				exsit.setSaState(ADD_TO_CHECK);
//				exsit.setUpdateTime(CommonFunction.getCurrentDateTime());
//				exsit.setOprID(operator.getOprId());
//				t40208BO.update( exsit );
//				return "您所添加的商户交易信息的状态已从删除成为新增待审核";
//			}else if(t40208BO.get(id).getSaState().equals(NORMAL))
//				return "您所添加的商户交易信息已受控且状态正常";
//			else if(t40208BO.get(id).getSaState().equals(ADD_TO_CHECK))
//				return "您所添加的商户交易信息的状态为新增待审核黑名单";
//			else if(t40208BO.get(id).getSaState().equals(DELETE_TO_CHECK))
//				return "您所添加的商户交易信息的状态为删除待审核黑名单";
//		}
		//20120822,限制添加同一商户交易黑名单记录
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from TBL_RISK_MCHT_TRAN_CTL where (sa_state in('0','2','4') and trim(mcht_no)='").append(this.mchtNo)
			.append("' and trim(channel)='").append(this.channel).append("' and trim(buss_type)='").append(this.bussType)
			.append("' and trim(txn_num)='").append(this.txnNum).append("') or(sa_state='3' and trim(mcht_no)='").append(this.mchtNo)
			.append("' and trim(channel_old)='").append(this.channel).append("' and trim(buss_type_old)='").append(this.bussType)
			.append("' and trim(txn_num_old)='").append(this.txnNum).append("') ");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(!"0".equals(count)){
			return "您所添加的商户交易黑名单信息中已经存在，请勿重复添加";
		}
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) from TBL_RISK_MCHT_TRAN_CTL where sa_state='1' and trim(mcht_no)='").append(this.mchtNo)
			.append("' and trim(channel)='").append(this.channel).append("' and trim(buss_type)='").append(this.bussType)
			.append("' and trim(txn_num)='").append(this.txnNum).append("' ");
		String count2 = commQueryDAO.findCountBySQLQuery(sql2.toString());
		
		StringBuffer sql3 = new StringBuffer();
		sql3.append("select ID from TBL_RISK_MCHT_TRAN_CTL where sa_state='1' and trim(mcht_no)='").append(this.mchtNo)
			.append("' and trim(channel)='").append(this.channel).append("' and trim(buss_type)='").append(this.bussType)
			.append("' and trim(txn_num)='").append(this.txnNum).append("' ");
		List list = commQueryDAO.findBySQLQuery(sql3.toString());
		
		if(!"0".equals(count2)){
			TblRiskMchtTranCtl exsit = t40208BO.get((String)list.get(0));
			exsit.setSaState(ADD_TO_CHECK);
			exsit.setUpdateTime(CommonFunction.getCurrentDateTime());
			exsit.setOprID(operator.getOprId());
			t40208BO.update( exsit );
//			return "您所添加的商户交易黑名单信息的状态已从删除成为新增待审核";
			return Constants.SUCCESS_CODE;
		}
		
		TblRiskMchtTranCtl tblRiskMchtTranCtl = new TblRiskMchtTranCtl();
		tblRiskMchtTranCtl.setMchtNo(getMchtNo());
		tblRiskMchtTranCtl.setChannel(getChannel());
		tblRiskMchtTranCtl.setBussType(getBussType());
		tblRiskMchtTranCtl.setOprID(operator.getOprId());
		tblRiskMchtTranCtl.setSaState(ADD_TO_CHECK);
		tblRiskMchtTranCtl.setTxnNum(getTxnNum());
		tblRiskMchtTranCtl.setTxnNumOld(getTxnNum());
		tblRiskMchtTranCtl.setBussTypeOld(getBussType());
		tblRiskMchtTranCtl.setChannelOld(getChannel());
		tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
		
	    t40208BO.add(tblRiskMchtTranCtl);
		return Constants.SUCCESS_CODE;
	}

	/**删除商户交易黑名单信息
	 * delete city code
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception {
		if(t40208BO.get(this.id).getSaState().equals(DELETE)) {
			return "该商户交易黑名单已是删除状态，请勿重复删除";
		}
		TblRiskMchtTranCtl tblRiskMchtTranCtl = t40208BO.get(id);
		if(ADD_TO_CHECK.equals(tblRiskMchtTranCtl.getSaState())){
			t40208BO.delete(id);
		}else{
			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranCtl.setOprID(operator.getOprId());
			tblRiskMchtTranCtl.setSaState(DELETE_TO_CHECK);
			t40208BO.update(tblRiskMchtTranCtl);
		}
		return Constants.SUCCESS_CODE;
	}

	/**更新商户交易黑名单信息
	 * @return
	 * @throws Exception 
	*/
	public String update() throws Exception {		
		jsonBean.parseJSONArrayData(getMchtTranCtlList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			TblRiskMchtTranCtl tblRiskMchtTranCtl = new TblRiskMchtTranCtl();
			BeanUtils.setObjectWithPropertiesValue(tblRiskMchtTranCtl,jsonBean,true);
			tblRiskMchtTranCtl.setOprID(operator.getOprId());
			if(tblRiskMchtTranCtl.getSaState().equals(NORMAL))
				tblRiskMchtTranCtl.setSaState(MODIFY_TO_CHECK);
			tblRiskMchtTranCtl.setUpdateTime(CommonFunction.getCurrentDateTime());
			
			TblRiskMchtTranCtl tblRiskMchtTranCtlOld = t40208BO.get(tblRiskMchtTranCtl.getId());
			tblRiskMchtTranCtl.setChannel(tblRiskMchtTranCtlOld.getChannel());
			tblRiskMchtTranCtl.setBussType(tblRiskMchtTranCtlOld.getBussType());
			tblRiskMchtTranCtl.setTxnNum(tblRiskMchtTranCtlOld.getTxnNum());
			t40208BO.update(tblRiskMchtTranCtl);
		}
		return Constants.SUCCESS_CODE;
	}

	public T40208BO getT40208BO() {
		return t40208BO;
	}

	public void setT40208BO(T40208BO t40208bo) {
		t40208BO = t40208bo;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getOprID() {
		return oprID;
	}

	public void setOprID(String oprID) {
		this.oprID = oprID;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelOld() {
		return channelOld;
	}

	public void setChannelOld(String channelOld) {
		this.channelOld = channelOld;
	}

	public String getBussTypeOld() {
		return bussTypeOld;
	}

	public void setBussTypeOld(String bussTypeOld) {
		this.bussTypeOld = bussTypeOld;
	}

	public String getTxnNumOld() {
		return txnNumOld;
	}

	public void setTxnNumOld(String txnNumOld) {
		this.txnNumOld = txnNumOld;
	}

	public String getMchtTranCtlList() {
		return mchtTranCtlList;
	}

	public void setMchtTranCtlList(String mchtTranCtlList) {
		this.mchtTranCtlList = mchtTranCtlList;
	}
	
}
