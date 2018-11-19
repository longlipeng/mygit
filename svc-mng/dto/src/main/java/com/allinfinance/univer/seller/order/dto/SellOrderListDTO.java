package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;

public class SellOrderListDTO extends BaseDTO implements BatchParamInterface {

	/**
	 * 销售订单明细
	 */
	private static final long serialVersionUID = 1L;

	private String orderListId;

	private String orderId;

	private String cardIssueFee;

	private String packageId;

	private String packageFee;

	private String cardLayoutId;

	private String cardAmount;

	private String realAmount;

	private String stockAmount;

	private String sumAmount;

	private String requireAmount;

	private String validityPeriod;

	private String totalPrice;

	private String faceValue;

	private String faceValueType;

	private String orderListIdStr[];

	private String productId;
	
	private String productName;

	private ProductDTO productDTO = new ProductDTO();

	private String cardNo;

	private String serviceId;
	
	private String actFlag;

	//制卡类型
	private String makeCardType;
	
	//过期日期
	private String expDate;
	
	//pin状态
	private String pinStat;
	
	//无pin限额
	private String noPinLimit;
	
	//持卡人Id
	private String cardHolderId;
	
	//发行机构
	private String entityId;
	
	//消费次数
	private String consumerTimes;
	
	//充值次数
	private String rechargeTimes;
	
	//打印公司名
	private String companyName;
	
	//打印持卡人名
	private String cardHolderName;
	
	private String userId;
	
	//是否回收
	private String callBack;
	
	
	
	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	//账户信息
	private String accountInfo;
	public String getOrderListId() {
		return orderListId;
	}

	public void setOrderListId(String orderListId) {
		this.orderListId = orderListId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCardIssueFee() {
		return cardIssueFee;
	}

	public void setCardIssueFee(String cardIssueFee) {
		this.cardIssueFee = cardIssueFee;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
	}

	public String getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getFaceValueType() {
		return faceValueType;
	}

	public void setFaceValueType(String faceValueType) {
		this.faceValueType = faceValueType;
	}

	public String[] getOrderListIdStr() {
		return orderListIdStr;
	}

	public void setOrderListIdStr(String[] orderListIdStr) {
		this.orderListIdStr = orderListIdStr;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getRequireAmount() {
		return requireAmount;
	}

	public void setRequireAmount(String requireAmount) {
		this.requireAmount = requireAmount;
	}

	public String getActFlag() {
		return actFlag;
	}

	public void setActFlag(String actFlag) {
		this.actFlag = actFlag;
	}

	@Override
	public String calcAmt() {
		// FIXME  请确认金额取值是否正确
		return getFaceValue();
	}

	/**
	 * 用来存放业务流水号
	 */
	private String txnSeq;
	
	@Override
	public String fetchSeq() {
		
		return this.txnSeq;
	}

	@Override
	public void setSeq(String seq) {
		this.txnSeq = seq;
	}

	public String getMakeCardType() {
		return makeCardType;
	}

	public void setMakeCardType(String makeCardType) {
		this.makeCardType = makeCardType;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getPinStat() {
		return pinStat;
	}

	public void setPinStat(String pinStat) {
		this.pinStat = pinStat;
	}

	public String getNoPinLimit() {
		return noPinLimit;
	}

	public void setNoPinLimit(String noPinLimit) {
		this.noPinLimit = noPinLimit;
	}

	public String getCardHolderId() {
		return cardHolderId;
	}

	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getConsumerTimes() {
		return consumerTimes;
	}

	public void setConsumerTimes(String consumerTimes) {
		this.consumerTimes = consumerTimes;
	}

	public String getRechargeTimes() {
		return rechargeTimes;
	}

	public void setRechargeTimes(String rechargeTimes) {
		this.rechargeTimes = rechargeTimes;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}

	public String getTxnSeq() {
		return txnSeq;
	}

	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	

}
