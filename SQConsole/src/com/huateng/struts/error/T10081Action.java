package com.huateng.struts.error;

import com.huateng.bo.error.T10081BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.error.TblElecCashInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:补电子现金消费
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-7
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10081Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	//补电子现金消费维护BO
	private T10081BO t10081BO = (T10081BO) ContextUtil.getBean("T10081BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {			
				rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，补电子现金消费维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加补电子现金消费信息
	 * @throws Exception
	 */
	private String add() throws Exception {
		String sql = "select count(*) from TBL_ELEC_CASH_INF_TMP where MCHT_CD='"+this.getMchtCd()+"' and TERM_ID='"+this.getTermId()
		+"' and TXN_BATCH_NO='"+this.getTxnBatchNo()+"' and TXN_CER_NO='"+this.getTxnCerNo()+"' and TRANS_DATE='"+this.getTransDate()+"'";
		//唯一性的检验
		String count = commQueryDAO.findCountBySQLQuery(sql);
		if(!"0".equals(count)) {
			return "您所添加的补电子现金消费信息已经存在！";
		}
		
		TblElecCashInfTmp tblElecCashInfTmp = new TblElecCashInfTmp();
		tblElecCashInfTmp.setId(StringUtil.getUUID());
		tblElecCashInfTmp.setMchtCd(mchtCd);	//商户编号
		tblElecCashInfTmp.setTermId(termId);	//终端号
		tblElecCashInfTmp.setAcqInsIdCd(acqInsIdCd);	//受理行标识
		tblElecCashInfTmp.setBrhInsIdCd(brhInsIdCd);	//发卡行标识
		tblElecCashInfTmp.setPan(pan);		//卡号
		tblElecCashInfTmp.setTxnNum(txnNum);	//交易类型
		tblElecCashInfTmp.setTxnBatchNo(txnBatchNo);	//交易批次号
		tblElecCashInfTmp.setTxnCerNo(txnCerNo);	//交易凭证号
		transAmt = CommonFunction.transYuanToFen(transAmt);
		tblElecCashInfTmp.setTransAmt(CommonFunction.fillString(transAmt,'0', 12, false));	//交易金额
		tblElecCashInfTmp.setTransDate(transDate);	//交易日期
		tblElecCashInfTmp.setIcCert(icCert);	//IC卡证书
		tblElecCashInfTmp.setTvr(tvr);
		tblElecCashInfTmp.setTsi(tsi);
		tblElecCashInfTmp.setAid(aid);
		tblElecCashInfTmp.setAtc(atc);
		tblElecCashInfTmp.setAppTag(appTag);	//应用标签
		tblElecCashInfTmp.setAppPreName(appPreName);	//应用首选名称
		tblElecCashInfTmp.setSaState("0");	//记录状态,0-新增待审核
		tblElecCashInfTmp.setRecCrtUsr(operator.getOprId());
		tblElecCashInfTmp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblElecCashInfTmp.setRecUpdUsr(operator.getOprId());
		tblElecCashInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		t10081BO.add(tblElecCashInfTmp);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除信息
	 * @return
	 * @throws Exception
	 */
	private String delete() throws Exception {
		TblElecCashInfTmp tblElecCashInfTmp = t10081BO.get(id);
		if(tblElecCashInfTmp == null) {
			return "没有找到要删除的信息";
		}
		tblElecCashInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblElecCashInfTmp.setRecUpdUsr(operator.getOprId());
		tblElecCashInfTmp.setSaState("4");	//记录状态,3-删除待审核
		t10081BO.update(tblElecCashInfTmp);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新信息
	 * @return
	 */
	private String update() throws Exception {
		String sql = "select count(*) from TBL_ELEC_CASH_INF_TMP where MCHT_CD='"+this.getMchtCdUpd()+"' and TERM_ID='"+this.getTermIdUpd()
		+"' and TXN_BATCH_NO='"+this.getTxnBatchNoUpd()+"' and TXN_CER_NO='"+this.getTxnCerNoUpd()+"' and TRANS_DATE='"+this.getTransDateUpd()+"'"
		+" and ID !='"+id+"'";
		//唯一性的检验
		String count = commQueryDAO.findCountBySQLQuery(sql);
		if(!"0".equals(count)) {
			return "您所修改的补电子现金消费信息已经存在！";
		}
		
		TblElecCashInfTmp tblElecCashInfTmp = t10081BO.get(id);
		if(tblElecCashInfTmp==null){
			return "未找到您所修改的原信息！";
		}
		tblElecCashInfTmp.setMchtCd(mchtCdUpd);	//商户编号
		tblElecCashInfTmp.setTermId(termIdUpd);	//终端号
		tblElecCashInfTmp.setAcqInsIdCd(acqInsIdCdUpd);	//受理行标识
		tblElecCashInfTmp.setBrhInsIdCd(brhInsIdCdUpd);	//发卡行标识
		tblElecCashInfTmp.setPan(panUpd);		//卡号
		tblElecCashInfTmp.setTxnNum(txnNumUpd);	//交易类型
		tblElecCashInfTmp.setTxnBatchNo(txnBatchNoUpd);	//交易批次号
		tblElecCashInfTmp.setTxnCerNo(txnCerNoUpd);	//交易凭证号
		transAmtUpd = CommonFunction.transYuanToFen(transAmtUpd);
		tblElecCashInfTmp.setTransAmt(CommonFunction.fillString(transAmtUpd,'0', 12, false));	//交易金额
		tblElecCashInfTmp.setTransDate(transDateUpd);	//交易日期
		tblElecCashInfTmp.setIcCert(icCertUpd);	//IC卡证书
		tblElecCashInfTmp.setTvr(tvrUpd);
		tblElecCashInfTmp.setTsi(tsiUpd);
		tblElecCashInfTmp.setAid(aidUpd);
		tblElecCashInfTmp.setAtc(atcUpd);
		tblElecCashInfTmp.setAppTag(appTagUpd);	//应用标签
		tblElecCashInfTmp.setAppPreName(appPreNameUpd);	//应用首选名称
		if(tblElecCashInfTmp.getSaState().equals("2")){	//正常状态下修改后同时修改记录状态，其他状态不修改
			tblElecCashInfTmp.setSaState("3");	//记录状态,3-修改待审核
		}
		tblElecCashInfTmp.setRecUpdUsr(operator.getOprId());
		tblElecCashInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		t10081BO.update(tblElecCashInfTmp);
		return Constants.SUCCESS_CODE;
	}
	
	private String id; 			//主键
	//新增
	private String mchtCd;		//商户号
	private String termId;		//终端号
	private String acqInsIdCd;	//受理行标识
	private String brhInsIdCd;	//发卡行标识
	private String pan;			//卡号
	private String txnNum;		//交易类型
	private String txnBatchNo;	//交易批次号
	private String txnCerNo;	//交易凭证号
	private String transAmt;	//交易金额
	private String transDate;	//交易日期
	private String icCert;		//IC卡证书
	private String tvr;			//TVR
	private String tsi;			//tsi
	private String aid;			//aid
	private String atc;			//atc
	private String appTag;		//应用标签
	private String appPreName;	//应用首选名称
	//修改
	private String mchtCdUpd;		//商户号
	private String termIdUpd;		//终端号
	private String acqInsIdCdUpd;	//受理行标识
	private String brhInsIdCdUpd;	//发卡行标识
	private String panUpd;			//卡号
	private String txnNumUpd;		//交易类型
	private String txnBatchNoUpd;	//交易批次号
	private String txnCerNoUpd;		//交易凭证号
	private String transAmtUpd;		//交易金额
	private String transDateUpd;	//交易日期
	private String icCertUpd;		//IC卡证书
	private String tvrUpd;			//TVR
	private String tsiUpd;			//tsi
	private String aidUpd;			//aid
	private String atcUpd;			//atc
	private String appTagUpd;		//应用标签
	private String appPreNameUpd;	//应用首选名称


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getAcqInsIdCd() {
		return acqInsIdCd;
	}

	public void setAcqInsIdCd(String acqInsIdCd) {
		this.acqInsIdCd = acqInsIdCd;
	}

	public String getBrhInsIdCd() {
		return brhInsIdCd;
	}

	public void setBrhInsIdCd(String brhInsIdCd) {
		this.brhInsIdCd = brhInsIdCd;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getTxnBatchNo() {
		return txnBatchNo;
	}

	public void setTxnBatchNo(String txnBatchNo) {
		this.txnBatchNo = txnBatchNo;
	}

	public String getTxnCerNo() {
		return txnCerNo;
	}

	public void setTxnCerNo(String txnCerNo) {
		this.txnCerNo = txnCerNo;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getIcCert() {
		return icCert;
	}

	public void setIcCert(String icCert) {
		this.icCert = icCert;
	}

	public String getTvr() {
		return tvr;
	}

	public void setTvr(String tvr) {
		this.tvr = tvr;
	}

	public String getTsi() {
		return tsi;
	}

	public void setTsi(String tsi) {
		this.tsi = tsi;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	public String getAppTag() {
		return appTag;
	}

	public void setAppTag(String appTag) {
		this.appTag = appTag;
	}

	public String getAppPreName() {
		return appPreName;
	}

	public void setAppPreName(String appPreName) {
		this.appPreName = appPreName;
	}

	public String getMchtCdUpd() {
		return mchtCdUpd;
	}

	public void setMchtCdUpd(String mchtCdUpd) {
		this.mchtCdUpd = mchtCdUpd;
	}

	public String getTermIdUpd() {
		return termIdUpd;
	}

	public void setTermIdUpd(String termIdUpd) {
		this.termIdUpd = termIdUpd;
	}

	public String getAcqInsIdCdUpd() {
		return acqInsIdCdUpd;
	}

	public void setAcqInsIdCdUpd(String acqInsIdCdUpd) {
		this.acqInsIdCdUpd = acqInsIdCdUpd;
	}

	public String getBrhInsIdCdUpd() {
		return brhInsIdCdUpd;
	}

	public void setBrhInsIdCdUpd(String brhInsIdCdUpd) {
		this.brhInsIdCdUpd = brhInsIdCdUpd;
	}

	public String getPanUpd() {
		return panUpd;
	}

	public void setPanUpd(String panUpd) {
		this.panUpd = panUpd;
	}

	public String getTxnNumUpd() {
		return txnNumUpd;
	}

	public void setTxnNumUpd(String txnNumUpd) {
		this.txnNumUpd = txnNumUpd;
	}

	public String getTxnBatchNoUpd() {
		return txnBatchNoUpd;
	}

	public void setTxnBatchNoUpd(String txnBatchNoUpd) {
		this.txnBatchNoUpd = txnBatchNoUpd;
	}

	public String getTxnCerNoUpd() {
		return txnCerNoUpd;
	}

	public void setTxnCerNoUpd(String txnCerNoUpd) {
		this.txnCerNoUpd = txnCerNoUpd;
	}

	public String getTransAmtUpd() {
		return transAmtUpd;
	}

	public void setTransAmtUpd(String transAmtUpd) {
		this.transAmtUpd = transAmtUpd;
	}

	public String getTransDateUpd() {
		return transDateUpd;
	}

	public void setTransDateUpd(String transDateUpd) {
		this.transDateUpd = transDateUpd;
	}

	public String getIcCertUpd() {
		return icCertUpd;
	}

	public void setIcCertUpd(String icCertUpd) {
		this.icCertUpd = icCertUpd;
	}

	public String getTvrUpd() {
		return tvrUpd;
	}

	public void setTvrUpd(String tvrUpd) {
		this.tvrUpd = tvrUpd;
	}

	public String getTsiUpd() {
		return tsiUpd;
	}

	public void setTsiUpd(String tsiUpd) {
		this.tsiUpd = tsiUpd;
	}

	public String getAidUpd() {
		return aidUpd;
	}

	public void setAidUpd(String aidUpd) {
		this.aidUpd = aidUpd;
	}

	public String getAtcUpd() {
		return atcUpd;
	}

	public void setAtcUpd(String atcUpd) {
		this.atcUpd = atcUpd;
	}

	public String getAppTagUpd() {
		return appTagUpd;
	}

	public void setAppTagUpd(String appTagUpd) {
		this.appTagUpd = appTagUpd;
	}

	public String getAppPreNameUpd() {
		return appPreNameUpd;
	}

	public void setAppPreNameUpd(String appPreNameUpd) {
		this.appPreNameUpd = appPreNameUpd;
	}

	
	
}
