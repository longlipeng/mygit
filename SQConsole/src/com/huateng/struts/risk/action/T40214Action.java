package com.huateng.struts.risk.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.risk.T40214BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.risk.TblRiskMchtTranLimit;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40214Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40214BO t40214BO = (T40214BO) ContextUtil.getBean("T40214BO");
	
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
	private String cardTypeOld;//修改后的
	private String limitOld;//修改后的
	private String cardType;//卡类型
	private String limit;//权限：允许、不允许
	
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	// 商户交易权限修改集
	private String mchtTranCtlList;
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户交易权限信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新商户交易权限信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除商户交易权限信息");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 添加商户交易权限信息
	 * @return
	 * 2010-8-26下午11:19:52
	 * @throws Exception 
	 */
	public String add() throws Exception{
		//20131022,限制给同一商户添加交易权限信息
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from TBL_RISK_MCHT_TRAN_LIMIT where SA_STATE <> '1' and trim(mcht_no)='")
			.append(this.mchtNo).append("' and trim(channel_old)='").append(channelOld).append("' and trim(buss_type_old)='").append(bussTypeOld)
			.append("' and trim(txn_num_old)='").append(txnNumOld).append("' and trim(card_type_old)='").append(cardTypeOld).append("' ");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(!"0".equals(count)){
			return "该商户交易权限记录已经存在，请勿重复添加";
		}
		
		TblRiskMchtTranLimit tblRiskMchtTranLimit = new TblRiskMchtTranLimit();
		tblRiskMchtTranLimit.setId(StringUtil.getUUID());
		tblRiskMchtTranLimit.setMchtNo(getMchtNo());
		tblRiskMchtTranLimit.setChannel(channelOld);
		tblRiskMchtTranLimit.setBussType(bussTypeOld);
		tblRiskMchtTranLimit.setCardType(cardTypeOld);
		tblRiskMchtTranLimit.setTxnNum(txnNumOld);
		tblRiskMchtTranLimit.setLimit("00");
		tblRiskMchtTranLimit.setChannelOld(channelOld);//修改前
		tblRiskMchtTranLimit.setBussTypeOld(bussTypeOld);//修改前
		tblRiskMchtTranLimit.setTxnNumOld(txnNumOld);
		tblRiskMchtTranLimit.setCardTypeOld(cardTypeOld);
		tblRiskMchtTranLimit.setLimitOld("00");
		tblRiskMchtTranLimit.setCreOprId(operator.getOprId());
		tblRiskMchtTranLimit.setCreTime(CommonFunction.getCurrentDateTime());
		tblRiskMchtTranLimit.setOprId(" ");
		tblRiskMchtTranLimit.setUpdateTime(" ");
		tblRiskMchtTranLimit.setSaState(ADD_TO_CHECK);
	    t40214BO.add(tblRiskMchtTranLimit);
		return Constants.SUCCESS_CODE;
	}

	/**删除商户交易权限信息
	 * delete city code
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception {
		if(t40214BO.get(this.id).getSaState().equals(DELETE)) {
			return "该商户交易权限已是删除状态，请勿重复删除";
		}
		TblRiskMchtTranLimit tblRiskMchtTranLimit = t40214BO.get(id);
		if(ADD_TO_CHECK.equals(tblRiskMchtTranLimit.getSaState())){
			t40214BO.delete(id);
		}else{
			tblRiskMchtTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setCreOprId(operator.getOprId());
			tblRiskMchtTranLimit.setCreTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setSaState(DELETE_TO_CHECK);
			t40214BO.update(tblRiskMchtTranLimit);
		}
		return Constants.SUCCESS_CODE;
	}

	/**更新商户交易权限信息
	 * @return
	 * @throws Exception 
	*/
	public String update() throws Exception {		
		jsonBean.parseJSONArrayData(getMchtTranCtlList());
		
		int len = jsonBean.getArray().size();
		List<TblRiskMchtTranLimit> tblRiskMchtTranLimitList = new ArrayList<TblRiskMchtTranLimit>(len);
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			id=jsonBean.getJSONDataAt(i).getString("id");
			TblRiskMchtTranLimit tblRiskMchtTranLimit =t40214BO.get(id);
			BeanUtils.setObjectWithPropertiesValue(tblRiskMchtTranLimit,jsonBean,false);
			//只有正常状态在修改的时候要改状态
			if(tblRiskMchtTranLimit.getSaState().equals(NORMAL)){
				tblRiskMchtTranLimit.setSaState(MODIFY_TO_CHECK);
				}
			tblRiskMchtTranLimit.setCreOprId(operator.getOprId());
			tblRiskMchtTranLimit.setCreTime(CommonFunction.getCurrentDateTime());
			tblRiskMchtTranLimit.setUpdateTime(CommonFunction.getCurrentDateTime());
			
			//20131022,限制给同一商户添加交易权限信息
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from TBL_RISK_MCHT_TRAN_LIMIT where SA_STATE <> '1' and trim(mcht_no)='")
				.append(tblRiskMchtTranLimit.getMchtNo().trim()).append("' and trim(channel_old)='").append(tblRiskMchtTranLimit.getChannelOld().trim()).append("' and trim(buss_type_old)='").append(tblRiskMchtTranLimit.getBussTypeOld().trim())
				.append("' and trim(txn_num_old)='").append(tblRiskMchtTranLimit.getTxnNumOld().trim()).append("' and trim(card_type_old)='").append(tblRiskMchtTranLimit.getCardTypeOld().trim())
				.append("' and id !='").append(id).append("'");
			String count = commQueryDAO.findCountBySQLQuery(sql.toString());
			if(!"0".equals(count)){
				return "您所修改的商户交易权限记录已经存在";
			}
			//判断集合中是否已包含相同内容的记录20131022
			if(tblRiskMchtTranLimitList!=null && tblRiskMchtTranLimitList.size()>0){
				for(int j=0 ; j<tblRiskMchtTranLimitList.size() ; j++){
					TblRiskMchtTranLimit temp = tblRiskMchtTranLimitList.get(j);
					if(tblRiskMchtTranLimit.getMchtNo().trim().equals(temp.getMchtNo().trim()) 
							&& tblRiskMchtTranLimit.getChannelOld().trim().equals(temp.getChannelOld().trim())
							&& tblRiskMchtTranLimit.getBussTypeOld().trim().equals(temp.getBussTypeOld().trim()) 
							&& tblRiskMchtTranLimit.getTxnNumOld().trim().equals(temp.getTxnNumOld().trim()) 
							&& tblRiskMchtTranLimit.getCardTypeOld().trim().equals(temp.getCardTypeOld().trim()) 
							){
						return "您所提交的商户交易权限信息有重复！";
					}
				}
			}
			tblRiskMchtTranLimitList.add(tblRiskMchtTranLimit);
		}
		t40214BO.updateList(tblRiskMchtTranLimitList);	
		return Constants.SUCCESS_CODE;
	}

	public T40214BO getT40214BO() {
		return t40214BO;
	}

	public void setT40214BO(T40214BO t40214bo) {
		t40214BO = t40214bo;
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

	public String getCardTypeOld() {
		return cardTypeOld;
	}

	public void setCardTypeOld(String cardTypeOld) {
		this.cardTypeOld = cardTypeOld;
	}

	public String getLimitOld() {
		return limitOld;
	}

	public void setLimitOld(String limitOld) {
		this.limitOld = limitOld;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getLimit() {
		if(limit==null)return "00";
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getMchtTranCtlList() {
		return mchtTranCtlList;
	}

	public void setMchtTranCtlList(String mchtTranCtlList) {
		this.mchtTranCtlList = mchtTranCtlList;
	}

}
