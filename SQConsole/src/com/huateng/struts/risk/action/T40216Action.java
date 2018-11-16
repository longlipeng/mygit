package com.huateng.struts.risk.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.risk.T40216BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.risk.TblRiskTermTranLimit;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T40216Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T40216BO t40216BO = (T40216BO) ContextUtil.getBean("T40216BO");
	
	private String id;
	private String termID;//终端号
	private String saState;//状态
	private String oprID;//操作员编号
	private String updateTime;//修改时间
	private String txnNum;//交易代码
	private String bussType;//业务类型
	private String channel;//交易渠道
	private String channelOld;//修改前的
	private String bussTypeOld;//修改前的
	private String txnNumOld;//修改前的
	private String cardTypeOld;//修改前的
	private String limitOld;//修改前的
	private String cardType;//卡类型
	private String limit;//权限：允许、不允许
	private String mchtNo;//商户号
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	// 终端交易权限修改集
	private String riskTermTranList;
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加终端交易权限信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新终端交易权限信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除终端交易权限信息");
			rspCode = delete();
		} 
		return rspCode;
	}
	
	/**
	 * 添加终端交易权限信息
	 * @return
	 * 2010-8-26下午11:19:52
	 * @throws Exception 
	 */
	public String add() throws Exception{
		//20120813,限制给同一终端添加交易权限信息
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from TBL_RISK_TERM_TRAN_LIMIT where SA_STATE <> '1' and term_id='").append(this.termID).append("' and trim(mcht_no)='")
			.append(this.mchtNo).append("' and trim(channel_old)='").append(this.channel).append("' and trim(buss_type_old)='").append(this.bussType)
			.append("' and trim(txn_num_old)='").append(this.txnNum).append("' and trim(card_type_old)='").append(this.cardType).append("' ");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(!"0".equals(count)){
			return "该终端交易权限记录已经存在，请勿重复添加";
		}
		
		TblRiskTermTranLimit tblRiskTermTranLimit = new TblRiskTermTranLimit();
		tblRiskTermTranLimit.setId(StringUtil.getUUID());
		tblRiskTermTranLimit.setMchtNo(mchtNo);
		tblRiskTermTranLimit.setTermID(getTermID());
		tblRiskTermTranLimit.setChannel(getChannel());
		tblRiskTermTranLimit.setBussType(getBussType());
		tblRiskTermTranLimit.setCardType(getCardType());
		tblRiskTermTranLimit.setLimit("00");
		//tblRiskMchtTranLimit.setOprID(operator.getOprId());
		tblRiskTermTranLimit.setSaState(ADD_TO_CHECK);
		tblRiskTermTranLimit.setTxnNum(getTxnNum());
		tblRiskTermTranLimit.setBussTypeOld(getBussType());
		tblRiskTermTranLimit.setCardTypeOld(getCardType());
		tblRiskTermTranLimit.setChannelOld(getChannel());
		tblRiskTermTranLimit.setTxnNumOld(getTxnNum());
		tblRiskTermTranLimit.setLimitOld("00");
		tblRiskTermTranLimit.setCreid(operator.getOprId());
		tblRiskTermTranLimit.setCretime(CommonFunction.getCurrentDateTime());
	    t40216BO.add(tblRiskTermTranLimit);
		return Constants.SUCCESS_CODE;
	}

	/**删除终端交易权限信息
	 * delete city code
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception {
		if(t40216BO.get(this.id).getSaState().equals(DELETE)) {
			return "该终端交易权限已是删除状态，请勿重复删除";
		}
		TblRiskTermTranLimit tblRiskTermTranLimit = t40216BO.get(id);
		if(ADD_TO_CHECK.equals(tblRiskTermTranLimit.getSaState())){
			t40216BO.delete(id);
		}else{
			tblRiskTermTranLimit.setSaState(DELETE_TO_CHECK);
			tblRiskTermTranLimit.setCreid(operator.getOprId());
			tblRiskTermTranLimit.setCretime(CommonFunction.getCurrentDateTime());
			t40216BO.update(tblRiskTermTranLimit);
		}
		return Constants.SUCCESS_CODE;
	}

	/**更新终端交易权限信息
	 * @return
	 * @throws Exception 
	*/
	public String update() throws Exception {		
		jsonBean.parseJSONArrayData(getRiskTermTranList());
		int len = jsonBean.getArray().size();
		System.out.println(riskTermTranList);
		List<TblRiskTermTranLimit> tblRiskTermTranLimitList = new ArrayList<TblRiskTermTranLimit>(len);
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			id=jsonBean.getJSONDataAt(i).getString("id");
			TblRiskTermTranLimit tblRiskTermTranLimit = t40216BO.get(id);
			BeanUtils.setObjectWithPropertiesValue(tblRiskTermTranLimit,jsonBean,false);
			//tblRiskTermTranLimit.setOprID(operator.getOprId());
			if(tblRiskTermTranLimit.getSaState().equals(NORMAL))
				tblRiskTermTranLimit.setSaState(MODIFY_TO_CHECK);
			tblRiskTermTranLimit.setCreid(operator.getOprId());
			tblRiskTermTranLimit.setCretime(CommonFunction.getCurrentDateTime());
		    
			//20131022,限制给同一终端添加交易权限信息
			System.out.println(tblRiskTermTranLimit.getCardType());
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from TBL_RISK_TERM_TRAN_LIMIT where SA_STATE <> '1' and term_id='").append(tblRiskTermTranLimit.getTermID().trim()).append("' and trim(mcht_no)='")
				.append(tblRiskTermTranLimit.getMchtNo().trim()).append("' and trim(channel_old)='").append(tblRiskTermTranLimit.getChannelOld().trim()).append("' and trim(buss_type_old)='").append(tblRiskTermTranLimit.getBussTypeOld().trim())
				.append("' and trim(txn_num_old)='").append(tblRiskTermTranLimit.getTxnNumOld().trim()).append("' and trim(card_type_old)='").append(tblRiskTermTranLimit.getCardTypeOld().trim())
				.append("' and id !='").append(id).append("'");
			String count = commQueryDAO.findCountBySQLQuery(sql.toString());
			if(!"0".equals(count)){
				return "您所修改的终端交易权限记录已经存在";
			}
			//判断集合中是否已包含相同内容的记录20131022
			if(tblRiskTermTranLimitList!=null && tblRiskTermTranLimitList.size()>0){
				for(int j=0 ; j<tblRiskTermTranLimitList.size() ; j++){
					TblRiskTermTranLimit temp = tblRiskTermTranLimitList.get(j);
					if(tblRiskTermTranLimit.getTermID().trim().equals(temp.getTermID().trim())
							&& tblRiskTermTranLimit.getMchtNo().trim().equals(temp.getMchtNo().trim()) 
							&& tblRiskTermTranLimit.getChannelOld().trim().equals(temp.getChannelOld().trim())
							&& tblRiskTermTranLimit.getBussTypeOld().trim().equals(temp.getBussTypeOld().trim()) 
							&& tblRiskTermTranLimit.getTxnNumOld().trim().equals(temp.getTxnNumOld().trim()) 
							&& tblRiskTermTranLimit.getCardTypeOld().trim().equals(temp.getCardTypeOld().trim()) 
							){
						return "您所提交的终端交易权限配置有重复！";
					}
				}
			}
			tblRiskTermTranLimitList.add(tblRiskTermTranLimit);
		}
		t40216BO.updateList(tblRiskTermTranLimitList);
		return Constants.SUCCESS_CODE;
	}

	public T40216BO getT40216BO() {
		return t40216BO;
	}

	public void setT40216BO(T40216BO t40216bo) {
		t40216BO = t40216bo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTermID() {
		return termID;
	}

	public void setTermID(String termID) {
		this.termID = termID;
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
		if(limit==null)limit="00";
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getRiskTermTranList() {
		return riskTermTranList;
	}

	public void setRiskTermTranList(String riskTermTranList) {
		this.riskTermTranList = riskTermTranList;
	}

	/**
	 * @return the mchtNO
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNO the mchtNO to set
	 */
	public void setMchtNo(String mchtNO) {
		this.mchtNo = mchtNO;
	}

}
