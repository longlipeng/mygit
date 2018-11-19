package com.huateng.hstserver.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccPackageDTO {
	// 交易编号
	private String txnCode;
	// 渠道号
	private String channel;
	// 响应码
	private String respCode;
	// 交易日期
	private String txnDate;
	// 交易时间
	private String txnTime;
	// 清算日期
	private String settleDate;
	// 文件
	private String filePath;
	// 卡号
	private String cardNo;
	// 卡片密码
	private String password;
	// 新密码
	private String newPin;
	// cvv2
	private String cvv2;
	// 卡片类型
	private String cardType;
	// 卡状态
	private String cardState;
	// 卡激活状态
	private String activeState;
	// 卡挂失状态
	private String lossState;
	// 卡冻结状态
	private String freezesState;
	// 卡锁定状态
	private String lockState;
	// 换卡状态
	private String trade;
	// 注销状态
	private String off;
	// 卡调整状态
	private String adjustType;
	// 产品号
	private String productId;
	// 产品名称
	private String productName;
	// 持卡人ID
	private String cardHolder;
	// 有效期
	private String valideTime;
	// 激活日期
	private String activeTime;
	// 短信通知状态
	private String note;
	// Email通知状态
	private String emailInfo;
	// 账户名称
	private String[] serviceNames;
	// 账户ID
	private String[] serviceIDs;
	// 账户数量
	private String accountNum;
	// 可用余额
	private String[] balances;
	// 冻结金额
	private String[] congeal;
	// 网上支付状态
	private String epayIn;
	// 网上支付限额
	private String limitPayAmount;
	// 原交易类型
	private String accType;
	// 延期月数
	private String month;
	// 服务费
	private String serviceFee;
	// 状态
	private String state;
	// 类型
	private String creditAmonts;
	// CVV
	private String cvv;
	//卡是否回收
	private String callBack;
	
	

	/*
	 * 卡片安全信息 accountNo ANS8 帐户号 accountType ANS8 帐户类型 serviceName
	 * ANS20 服务名称 posDailyAmount ANS12 POS当天最大交易金额 posSingleAmount
	 * ANS12 POS当天单笔最大交易金额 webDailyAmount ANS12 WEB当天最大交易金额
	 * webSingleAmount ANS12 WEB当天单笔最大交易金额
	 */
	private List<Map<?, ?>> cardSeuriyInfos;
	// 设置卡片安全信息字符串
	private String cardSeuriyStr;
	
	// 无PIN交易限额
	private String withoutPinAmount;

	// 制卡查询批号
	private String batchNo;
	// 制卡查询状态
	private String makeCardState;
	// 充值订单号
	private String rechargeNo;

	// 开始行
	private String begin_row;

	// 结束行
	private String end_row;

	// 总行数
	private String totalRow;
	// 实际行数
	private String realRow;
	//
	private String otherData;

	private String type;

	private String batchFileInfo;
	
	/*上汽门户支付交易的流水*/
	private String txnNo;
	/*上汽车享付的交易流水*/
	private String orderNo;
	// 通用List
	private List<Map<?, ?>> list;
	private List<AccPackageDTO> accPackageDTOs = new ArrayList<AccPackageDTO>();
	private CardInfoDTO cardInfoDTO = new CardInfoDTO();

	
	
	public String getTxnNo() {
		return txnNo;
	}

	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getNewPin() {
		return newPin;
	}

	public void setNewPin(String newPin) {
		this.newPin = newPin;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public String getLossState() {
		return lossState;
	}

	public void setLossState(String lossState) {
		this.lossState = lossState;
	}

	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getValideTime() {
		return valideTime;
	}

	public void setValideTime(String valideTime) {
		this.valideTime = valideTime;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFreezesState() {
		return freezesState;
	}

	public void setFreezesState(String freezesState) {
		this.freezesState = freezesState;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getEmailInfo() {
		return emailInfo;
	}

	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}

	public String[] getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(String[] serviceNames) {
		this.serviceNames = serviceNames;
	}

	public String[] getServiceIDs() {
		return serviceIDs;
	}

	public void setServiceIDs(String[] serviceIDs) {
		this.serviceIDs = serviceIDs;
	}

	public String[] getBalances() {
		return balances;
	}

	public void setBalances(String[] balances) {
		this.balances = balances;
	}

	public String[] getCongeal() {
		return congeal;
	}

	public void setCongeal(String[] congeal) {
		this.congeal = congeal;
	}

	public String getEpayIn() {
		return epayIn;
	}

	public void setEpayIn(String epayIn) {
		this.epayIn = epayIn;
	}

	public String getLimitPayAmount() {
		return limitPayAmount;
	}

	public void setLimitPayAmount(String limitPayAmount) {
		this.limitPayAmount = limitPayAmount;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<?, ?>> getList() {
		return list;
	}

	public void setList(List<Map<?, ?>> list) {
		this.list = list;
	}

	public CardInfoDTO getCardInfoDTO() {
		return cardInfoDTO;
	}

	public void setCardInfoDTO(CardInfoDTO cardInfoDTO) {
		this.cardInfoDTO = cardInfoDTO;
	}

	public List<AccPackageDTO> getAccPackageDTOs() {
		return accPackageDTOs;
	}

	public void setAccPackageDTOs(List<AccPackageDTO> accPackageDTOs) {
		this.accPackageDTOs = accPackageDTOs;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getOff() {
		return off;
	}

	public void setOff(String off) {
		this.off = off;
	}

	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getCreditAmonts() {
		return creditAmonts;
	}

	public void setCreditAmonts(String creditAmonts) {
		this.creditAmonts = creditAmonts;
	}

	public List<Map<?, ?>> getCardSeuriyInfos() {
		return cardSeuriyInfos;
	}

	public void setCardSeuriyInfos(List<Map<?, ?>> cardSeuriyInfos) {
		this.cardSeuriyInfos = cardSeuriyInfos;
	}

	public String getWithoutPinAmount() {
		return withoutPinAmount;
	}

	public void setWithoutPinAmount(String withoutPinAmount) {
		this.withoutPinAmount = withoutPinAmount;
	}

	public String getCardSeuriyStr() {
		return cardSeuriyStr;
	}

	public void setCardSeuriyStr(String cardSeuriyStr) {
		this.cardSeuriyStr = cardSeuriyStr;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getMakeCardState() {
		return makeCardState;
	}

	public void setMakeCardState(String makeCardState) {
		this.makeCardState = makeCardState;
	}

	public String getRechargeNo() {
		return rechargeNo;
	}

	public void setRechargeNo(String rechargeNo) {
		this.rechargeNo = rechargeNo;
	}

	public String getBegin_row() {
		return begin_row;
	}

	public void setBegin_row(String beginRow) {
		begin_row = beginRow;
	}

	public String getEnd_row() {
		return end_row;
	}

	public void setEnd_row(String endRow) {
		end_row = endRow;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public String getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(String totalRow) {
		this.totalRow = totalRow;
	}

	public String getRealRow() {
		return realRow;
	}

	public void setRealRow(String realRow) {
		this.realRow = realRow;
	}

	public String getBatchFileInfo() {
		return batchFileInfo;
	}

	public void setBatchFileInfo(String batchFileInfo) {
		this.batchFileInfo = batchFileInfo;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	
	

}
