package com.allinfinance.univer.cardmanagement.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
//import com.huateng.univer.merchant.dto.MerchantDTO;
//import com.huateng.univer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;

public class CardManagementDTO extends PageQueryDTO implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String cardManagementId;
    private String prodType;
    private String customerId;
    private String cardNo;
    private String misc;
    private String txnType1;
    private String prodName;
    private String operationType;
  //转出卡卡号
    private String transferOutCard;
    //转出卡账号
    private String transferOutAccount;
    //转入卡卡号
    private String transferInCard;
    //转入卡账号
    private String transferInAccount;
    //转出卡密码
    private String transferOutCardPassword;
    //转账金额
    private String transAmount;
  //转账交易流水号
    private String txnSeqNo;
    //原转出交易流水
    private String transferOutTxnNo;
    private String cardValidityPeriod; 
    private String cardActDate;

    private PageDataDTO pageDateDTO;

    private String extensionMonth;

    private String newCardValidityPeriod;

    private String serviceFee;

    private String startDate;

    private String endDate;

    private List<String> posSingleAmounts;

    private List<String> posDailyAmounts;

    private List<String> webSingleAmounts;

    private List<String> webDailyAmounts;
    private List<String> creditAmonts;
    private String pinErrTime;

    
    private String withoutPinAmount;
    private String withoutPinAmountHis;
    private String txnId;

    private String adjustType;
    private String adjustAmount;
    
    private String distributedAmount;
    private String undistributedAmount;
    private String memo;

    private String createUser;

    private String createTime;

    private String userName;

    private String modifyTime;

    private String dataState;

    private String cvv2;

    private String cardholderName;
    private String agentrName;
    private String agentrCertType;
    private String agentrCertTypeNo;
    private String cardType;

    private String password;
    //持卡人证件类型
    private String idType;
    //证件号
    private String idNo;
    private String externalId;
    private String sysSeqNum;
    private String retrivlRef;
    private String acctId1;
    private String acctType;
    private String acctId;
    private String txnNum;
    private String txnType;
    private String txnAmt;
    private String txnDate;
    private String txnDaten;
    private String stxnDate;
    //挂失状态
    private String hang;
    //锁定状态
    private String lock;
    //注销状态
    private String off;
    //激活状态
    private String active;
    //换卡状态
    private String trade;
    //在库状态
    private String stockState;
    //卡冻结状态
    private String freezeStat;
    //本人办理标识
    private String owner="2";
    //转入卡持卡人信息
    private String incardholderName;
    private String npassword;
    private String cardstate;
    private String accountId;
    private String accountType;
    private List<AccountDTO> accountDTOs;
    private List<CardBalanceDTO> cardBalanceDTOs;
    private String summary;
    private RiskDTO riskDTO;
    private String showFlag;
    private RiskSvcCtrlDTO riskSvcCtrlDTO;
    
    private String cardholderId;
    private String date;
    private String memBirthday;
    private String customerName;
    private String customerType;
    private String rsvd1;
    private ShopDTO shopDTO;
    private MerchantDTO merchantDTO;
    private CardholderDTO cardholderDTO;

    
    private String mobile;
    private String email;
    
    private String merchantId;
    
    private String postId;
    
    private String shopId;
    
    private String points;
    private String newPassword;
    private String queryFlag;
    private String disableFlag;
    private String accountNo;
    private String accountName;
    private String balance;	//余额
    //冻结余额
    private String congeal;

	/**用作卡操作查询检验时，页面的显示控制*/
    private String isDisplayFlag;
    private List<?> accountList;
    private List<?> InCardAccountList;
    private Map<String, String> accountRltBalance;
    private Map<String, String> accountMap;
    private List inCardAccounts;
    private PageDataDTO pageDataDTO;
    private String totalBalance;
    private String transferInCardConfirm;
    
    //原订单
    private String oldOrder;
    private String orderType;
    
    //充值流水（由上汽支付交易完成后传过来）
    private String txnNo;
    //充值流水（上汽车享付交易流水）
    private String orderNo;
    //相关成本费
    private String costPrice;
    //赎回金额
    private String redemptionMoney;
    /**
     * 赎回标记   1、入库      2、销户
     * @return
     */
    
    private String flag;
    
    private String operator;
    private String operatorTel;
    private String payChannel;
    private String intoBankId;
    private String fromBankId;
    //卡片的署名类型
    private String onymusState;
    private String creditAmont;
    private List<Map<?, ?>> cardSeuriyInfos;
    private String payChnl;
    /**
     * 作废按钮
     * */
    private String invalidFlag;
    //是否回收
    private String callBack;//0:不回收，1：回收
    
    
    
	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getFreezeStat() {
		return freezeStat;
	}

	public void setFreezeStat(String freezeStat) {
		this.freezeStat = freezeStat;
	}

	public String getPinErrTime() {
		return pinErrTime;
	}

	public void setPinErrTime(String pinErrTime) {
		this.pinErrTime = pinErrTime;
	}

	public String getCardActDate() {
		return cardActDate;
	}

	public void setCardActDate(String cardActDate) {
		this.cardActDate = cardActDate;
	}

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

	public String getPayChnl() {
		return payChnl;
	}

	public void setPayChnl(String payChnl) {
		this.payChnl = payChnl;
	}

	public String getIncardholderName() {
		return incardholderName;
	}

	public void setIncardholderName(String incardholderName) {
		this.incardholderName = incardholderName;
	}

	public String getWithoutPinAmountHis() {
		return withoutPinAmountHis;
	}

	public void setWithoutPinAmountHis(String withoutPinAmountHis) {
		this.withoutPinAmountHis = withoutPinAmountHis;
	}

	public List<Map<?, ?>> getCardSeuriyInfos() {
		return cardSeuriyInfos;
	}

	public void setCardSeuriyInfos(List<Map<?, ?>> cardSeuriyInfos) {
		this.cardSeuriyInfos = cardSeuriyInfos;
	}

	public List<String> getCreditAmonts() {
		return creditAmonts;
	}

	public void setCreditAmonts(List<String> creditAmonts) {
		this.creditAmonts = creditAmonts;
	}

	public String getCreditAmont() {
		return creditAmont;
	}

	public void setCreditAmont(String creditAmont) {
		this.creditAmont = creditAmont;
	}
    public String getOnymusState() {
		return onymusState;
	}
	public void setOnymusState(String onymusState) {
		this.onymusState = onymusState;
	}
	public String getStockState() {
		return stockState;
	}
	public void setStockState(String stockState) {
		this.stockState = stockState;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getDistributedAmount() {
		return distributedAmount;
	}
	public void setDistributedAmount(String distributedAmount) {
		this.distributedAmount = distributedAmount;
	}
	public String getUndistributedAmount() {
		return undistributedAmount;
	}
	public void setUndistributedAmount(String undistributedAmount) {
		this.undistributedAmount = undistributedAmount;
	}
	public String getDisableFlag() {
		return disableFlag;
	}
	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}
	public String getTotalBalance() {
		return new BigDecimal(totalBalance).divide(new BigDecimal(100)).toString();
	}
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}
	public String getTransferInCardConfirm() {
		return transferInCardConfirm;
	}
	public void setTransferInCardConfirm(String transferInCardConfirm) {
		this.transferInCardConfirm = transferInCardConfirm;
	}
	public Map<String, String> getAccountMap() {
		return accountMap;
	}
	public void setAccountMap(Map<String, String> accountMap) {
		this.accountMap = accountMap;
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}
	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}
	public List getInCardAccounts() {
		return inCardAccounts;
	}
	public void setInCardAccounts(List inCardAccounts) {
		this.inCardAccounts = inCardAccounts;
	}
	public Map<String, String> getAccountRltBalance() {
		return accountRltBalance;
	}
	public void setAccountRltBalance(Map<String, String> accountRltBalance) {
		this.accountRltBalance = accountRltBalance;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public List<?> getInCardAccountList() {
		return InCardAccountList;
	}
	public void setInCardAccountList(List<?> inCardAccountList) {
		InCardAccountList = inCardAccountList;
	}
	public List<?> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<?> accountList) {
		this.accountList = accountList;
	}
	public String getTxnSeqNo() {
		return txnSeqNo;
	}
	public void setTxnSeqNo(String txnSeqNo) {
		this.txnSeqNo = txnSeqNo;
	}
	public String getTransferOutTxnNo() {
		return transferOutTxnNo;
	}
	public void setTransferOutTxnNo(String transferOutTxnNo) {
		this.transferOutTxnNo = transferOutTxnNo;
	}
	public String getTransferOutCard() {
		return transferOutCard;
	}
	public void setTransferOutCard(String transferOutCard) {
		this.transferOutCard = transferOutCard;
	}
	public String getTransferOutAccount() {
		return transferOutAccount;
	}
	public void setTransferOutAccount(String transferOutAccount) {
		this.transferOutAccount = transferOutAccount;
	}
	public String getTransferInCard() {
		return transferInCard;
	}
	public void setTransferInCard(String transferInCard) {
		this.transferInCard = transferInCard;
	}
	public String getTransferInAccount() {
		return transferInAccount;
	}
	public void setTransferInAccount(String transferInAccount) {
		this.transferInAccount = transferInAccount;
	}
	public String getTransferOutCardPassword() {
		return transferOutCardPassword;
	}
	public void setTransferOutCardPassword(String transferOutCardPassword) {
		this.transferOutCardPassword = transferOutCardPassword;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	private String cardFlag;
    
	public String getAgentrName() {
		return agentrName;
	}
	public void setAgentrName(String agentrName) {
		this.agentrName = agentrName;
	}
	public String getAgentrCertType() {
		return agentrCertType;
	}
	public void setAgentrCertType(String agentrCertType) {
		this.agentrCertType = agentrCertType;
	}
	public String getAgentrCertTypeNo() {
		return agentrCertTypeNo;
	}
	public void setAgentrCertTypeNo(String agentrCertTypeNo) {
		this.agentrCertTypeNo = agentrCertTypeNo;
	}
	public String getCardFlag() {
		return cardFlag;
	}
	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
	public String getIsDisplayFlag() {
		return isDisplayFlag;
	}
	public void setIsDisplayFlag(String isDisplayFlag) {
		this.isDisplayFlag = isDisplayFlag;
	}
	public String getCardManagementId() {
		return cardManagementId;
	}
	public void setCardManagementId(String cardManagementId) {
		this.cardManagementId = cardManagementId;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	public String getHang() {
		return hang;
	}
	public void setHang(String hang) {
		this.hang = hang;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getOff() {
		return off;
	}
	public void setOff(String off) {
		this.off = off;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getTxnType1() {
		return txnType1;
	}
	public void setTxnType1(String txnType1) {
		this.txnType1 = txnType1;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getCardValidityPeriod() {
		return cardValidityPeriod;
	}
	public void setCardValidityPeriod(String cardValidityPeriod) {
		this.cardValidityPeriod = cardValidityPeriod;
	}
	public PageDataDTO getPageDateDTO() {
		return pageDateDTO;
	}
	public void setPageDateDTO(PageDataDTO pageDateDTO) {
		this.pageDateDTO = pageDateDTO;
	}
	public String getExtensionMonth() {
		return extensionMonth;
	}
	public void setExtensionMonth(String extensionMonth) {
		this.extensionMonth = extensionMonth;
	}
	public String getNewCardValidityPeriod() {
		return newCardValidityPeriod;
	}
	public void setNewCardValidityPeriod(String newCardValidityPeriod) {
		this.newCardValidityPeriod = newCardValidityPeriod;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
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
	public List<String> getPosSingleAmounts() {
		return posSingleAmounts;
	}
	public void setPosSingleAmounts(List<String> posSingleAmounts) {
		this.posSingleAmounts = posSingleAmounts;
	}
	public List<String> getPosDailyAmounts() {
		return posDailyAmounts;
	}
	public void setPosDailyAmounts(List<String> posDailyAmounts) {
		this.posDailyAmounts = posDailyAmounts;
	}
	public List<String> getWebSingleAmounts() {
		return webSingleAmounts;
	}
	public void setWebSingleAmounts(List<String> webSingleAmounts) {
		this.webSingleAmounts = webSingleAmounts;
	}
	public List<String> getWebDailyAmounts() {
		return webDailyAmounts;
	}
	public void setWebDailyAmounts(List<String> webDailyAmounts) {
		this.webDailyAmounts = webDailyAmounts;
	}
	public String getWithoutPinAmount() {
		return withoutPinAmount;
	}
	public void setWithoutPinAmount(String withoutPinAmount) {
		this.withoutPinAmount = withoutPinAmount;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getAdjustType() {
		return adjustType;
	}
	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}
	public String getAdjustAmount() {
		return adjustAmount;
	}
	public void setAdjustAmount(String adjustAmount) {
		this.adjustAmount = adjustAmount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getSysSeqNum() {
		return sysSeqNum;
	}
	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}
	public String getRetrivlRef() {
		return retrivlRef;
	}
	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}
	public String getAcctId1() {
		return acctId1;
	}
	public void setAcctId1(String acctId1) {
		this.acctId1 = acctId1;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public String getTxnNum() {
		return txnNum;
	}
	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnDaten() {
		return txnDaten;
	}
	public void setTxnDaten(String txnDaten) {
		this.txnDaten = txnDaten;
	}
	public String getStxnDate() {
		return stxnDate;
	}
	public void setStxnDate(String stxnDate) {
		this.stxnDate = stxnDate;
	}
	public String getNpassword() {
		return npassword;
	}
	public void setNpassword(String npassword) {
		this.npassword = npassword;
	}
	public String getCardstate() {
		return cardstate;
	}
	public void setCardstate(String cardstate) {
		this.cardstate = cardstate;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public List<AccountDTO> getAccountDTOs() {
		return accountDTOs;
	}
	public void setAccountDTOs(List<AccountDTO> accountDTOs) {
		this.accountDTOs = accountDTOs;
	}
	public List<CardBalanceDTO> getCardBalanceDTOs() {
		return cardBalanceDTOs;
	}
	public void setCardBalanceDTOs(List<CardBalanceDTO> cardBalanceDTOs) {
		this.cardBalanceDTOs = cardBalanceDTOs;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public RiskDTO getRiskDTO() {
		return riskDTO;
	}
	public void setRiskDTO(RiskDTO riskDTO) {
		this.riskDTO = riskDTO;
	}
	public String getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	public RiskSvcCtrlDTO getRiskSvcCtrlDTO() {
		return riskSvcCtrlDTO;
	}
	public void setRiskSvcCtrlDTO(RiskSvcCtrlDTO riskSvcCtrlDTO) {
		this.riskSvcCtrlDTO = riskSvcCtrlDTO;
	}
	public String getCardholderId() {
		return cardholderId;
	}
	public void setCardholderId(String cardholderId) {
		this.cardholderId = cardholderId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMemBirthday() {
		return memBirthday;
	}
	public void setMemBirthday(String memBirthday) {
		this.memBirthday = memBirthday;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getRsvd1() {
		return rsvd1;
	}
	public void setRsvd1(String rsvd1) {
		this.rsvd1 = rsvd1;
	}
	public ShopDTO getShopDTO() {
		return shopDTO;
	}
	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}
	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public CardholderDTO getCardholderDTO() {
		return cardholderDTO;
	}
	public void setCardholderDTO(CardholderDTO cardholderDTO) {
		this.cardholderDTO = cardholderDTO;
	}
	
	public String getOldOrder() {
		return oldOrder;
	}
	public void setOldOrder(String oldOrder) {
		this.oldOrder = oldOrder;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	public String getRedemptionMoney() {
		return redemptionMoney;
	}
	public void setRedemptionMoney(String redemptionMoney) {
		this.redemptionMoney = redemptionMoney;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorTel() {
		return operatorTel;
	}
	public void setOperatorTel(String operatorTel) {
		this.operatorTel = operatorTel;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getIntoBankId() {
		return intoBankId;
	}
	public void setIntoBankId(String intoBankId) {
		this.intoBankId = intoBankId;
	}
	public String getFromBankId() {
		return fromBankId;
	}
	public void setFromBankId(String fromBankId) {
		this.fromBankId = fromBankId;
	}

	public String getCongeal() {
		return congeal;
	}

	public void setCongeal(String congeal) {
		this.congeal = congeal;
	}

    /**
     * @return the invalidFlag
     */
    public String getInvalidFlag() {
        return invalidFlag;
    }

    /**
     * @param invalidFlag the invalidFlag to set
     */
    public void setInvalidFlag(String invalidFlag) {
        this.invalidFlag = invalidFlag;
    }
	
   
	
}
