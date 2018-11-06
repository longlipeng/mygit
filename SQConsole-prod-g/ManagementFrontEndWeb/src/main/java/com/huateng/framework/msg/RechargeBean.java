package com.huateng.framework.msg;


public class RechargeBean {
	/* 交易类型 */
	private String txnType;

	/* 发送日期 */
	private String sndDate;

	/* 发送时刻 */
	private String sndTime;

	/* 订单编号 */
	private String orderNo;
	/* 原交易类型 */
	private String oldTxnType;

	/* 检索参考号 */
	private String searchesNo;
	/* 卡号 */
	private String cardNo;
	/* 账户号 */
	private String accountNo;
	/* 账户类型 */
	private String accountType;
	/* 调整金额 */
	private String moneyAdjust;
	/* 出入账标识 */
	private String adjustType;
	/* 摘要 */
	private String summary;
	/* 应答码 */
	private String rspCode;
	/* 有效日期 */
	private String validityPeriod;
	/* 服务代码 */
	private String serviceCode;
	/* 卡号信息 */
	private String cardInfo;
	/* 卡激活状态 */
	private String actStat;
	/* 卡锁定状态 */
	private String lockStat;
	/* 卡挂失状态 */
	private String lostStat;
	/* PIN状态 */
	private String pinStat;
	/* 系统参考号 */
	private String sysSeqNum;
	/* 交易金额 */
	private String amtTrans;
	/* 余额 */
	private String addtnlAmt;
	/* 交易时间 */
	private String txnDate;
	/* 所属机构 */
	private String entity;
	/* 查询标识 */
	//private String mark;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getAmtTrans() {
		return amtTrans;
	}

	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}

	public String getAddtnlAmt() {
		return addtnlAmt;
	}

	public void setAddtnlAmt(String addtnlAmt) {
		this.addtnlAmt = addtnlAmt;
	}

	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getSndDate() {
		return sndDate;
	}

	public void setSndDate(String sndDate) {
		this.sndDate = sndDate;
	}

	public String getSndTime() {
		return sndTime;
	}

	public void setSndTime(String sndTime) {
		this.sndTime = sndTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getOldTxnType() {
		return oldTxnType;
	}

	public void setOldTxnType(String oldTxnType) {
		this.oldTxnType = oldTxnType;
	}

	public String getSearchesNo() {
		return searchesNo;
	}

	public void setSearchesNo(String searchesNo) {
		this.searchesNo = searchesNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMoneyAdjust() {
		return moneyAdjust;
	}

	public void setMoneyAdjust(String moneyAdjust) {
		this.moneyAdjust = moneyAdjust;
	}

	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getActStat() {
		return actStat;
	}

	public void setActStat(String actStat) {
		this.actStat = actStat;
	}

	public String getLockStat() {
		return lockStat;
	}

	public void setLockStat(String lockStat) {
		this.lockStat = lockStat;
	}

	public String getLostStat() {
		return lostStat;
	}

	public void setLostStat(String lostStat) {
		this.lostStat = lostStat;
	}

	public String getPinStat() {
		return pinStat;
	}

	public void setPinStat(String pinStat) {
		this.pinStat = pinStat;
	}

}
