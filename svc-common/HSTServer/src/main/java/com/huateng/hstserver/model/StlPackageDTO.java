package com.huateng.hstserver.model;

public class StlPackageDTO {
	

	//交易编号
	private String txnCode;
	//渠道号
	private String channel;
	//响应码
	private String rspCode;
	//终端号
	private String termId;
	//收单机构号
	private String consumerCode;
	//商户号
	private String merchantCode;
	//商户名称
	private String merchantName;
	//门店号
	private String shopCode;
	
	//结算单号
	private String settleId;
	//结算单状态
	private String settleState;
	//上级机构
	private String settleObject1;	
	//最小结算金额
	private String minAmt;
	//最大结算金额
	private String maxAmt;	
	//结算开始日期
	private String startDate;
	//结算结束日期
	private String endDate;	
	//起始行
	private String startRow;
	//结束行
	private String endRow;	
	//保留域
	private String Reserved;

	//返回值
	private String otherData;
	//实际行数
	private String row_num;	
	//总行数
	private String row_tot;	
	//上一结算日期
	private String settle_dt_pre;
	//下一结算日期
	private String settle_dt_next;
	//手续费偏移量
	private String fee_offset;
	
	
	private String userid;
	//商户合同号
	private String contractId;


	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTxnCode() {
		return txnCode;
	}
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getConsumerCode() {
		return consumerCode;
	}
	public void setConsumerCode(String consumerCode) {
		this.consumerCode = consumerCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getSettleId() {
		return settleId;
	}
	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}
	public String getSettleState() {
		return settleState;
	}
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	public String getSettleObject1() {
		return settleObject1;
	}
	public void setSettleObject1(String settleObject1) {
		this.settleObject1 = settleObject1;
	}
	public String getMinAmt() {
		return minAmt;
	}
	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}
	public String getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartRow() {
		return startRow;
	}
	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}
	public String getEndRow() {
		return endRow;
	}
	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}
	public String getReserved() {
		return Reserved;
	}
	public void setReserved(String reserved) {
		Reserved = reserved;
	}
	public String getOtherData() {
		return otherData;
	}
	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}
	public String getRow_num() {
		return row_num;
	}
	public void setRow_num(String rowNum) {
		row_num = rowNum;
	}
	public String getRow_tot() {
		return row_tot;
	}
	public void setRow_tot(String rowTot) {
		row_tot = rowTot;
	}
	public String getSettle_dt_pre() {
		return settle_dt_pre;
	}
	public void setSettle_dt_pre(String settleDtPre) {
		settle_dt_pre = settleDtPre;
	}
	public String getSettle_dt_next() {
		return settle_dt_next;
	}
	public void setSettle_dt_next(String settleDtNext) {
		settle_dt_next = settleDtNext;
	}
	
	public String getFee_offset() {
		return fee_offset;
	}
	public void setFee_offset(String feeOffset) {
		fee_offset = feeOffset;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}
