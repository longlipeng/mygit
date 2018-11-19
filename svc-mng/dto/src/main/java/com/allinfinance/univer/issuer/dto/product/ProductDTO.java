package com.allinfinance.univer.issuer.dto.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;

public class ProductDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productDefineIssuer;

	private String annualFee;

	private String availableOrder;

	private String availableStock;

	private String cardFee;

	private String cardType;

	private String createTime;

	private String createUser;

	private String dataState;

	private String emailSvcStat;

	private String endTime;

	private Date endTimeTime;

	private String entityId;

	private String initActStat;

	private String ivrSvcStat;

	private String maxBalance;

	private String modifyTime;

	private String modifyUser;

	private String monstmtSvcStat;

	private String onymousStat;

	private String pinDelivMeans;

	private String pinStat;

	private String preAuthStat;

	private String prodStat;

	private String productEnglishName;

	private String productId;

	private String productName;

	private String productType;

	private String replaceFee;

	private String smsSvcStat;

	private String startDate;

	private Date startDateDate;

	private String supplStat;

	private String validityPeriod;

	private String webEmailSvcStat;

	private String webPayStat;

	private String webSmsSvcStat;

	private String updateFlag;

	private String cardLayoutId;

	private String packageId;

	private String consumerTimes;

	private String rechargeTimes;

	private String consumerTimeFlag;

	private String rechargeTimeFlag;

	private List<ProductDTO> productDTOs;

	private List<ServiceDTO> services;

	private List<CardLayoutDTO> cardLayoutDTOs;

	private List<ProdFaceValueDTO> prodFaceValueDTO;

	private List<PackageDTO> packages;

	private List<ProductCardBinDTO> productCardBinDTOs;

	private String[] cardBinIds;

	// 默认计算规则
	private String defaultRuleNo;
	 //密码加密密钥索引
private String pwdKeyIndex;
	//cvv计算密钥索引
	private String cvvKeyIndex;
	//余额密文密钥索引
	private String blnKeyIndex;
	
	private String validPeriodRule;
	private String changeValidPeriod;
	private String redemptiveServiceRate;
	
	//无PIN限额
	private String noPinLimit;
	//不记名卡当天限额
	private String rsvData1;
	
	public String getRsvData1() {
		return rsvData1;
	}

	public void setRsvData1(String rsvData1) {
		this.rsvData1 = rsvData1;
	}

	public String getRedemptiveServiceRate() {
		return redemptiveServiceRate;
	}

	public void setRedemptiveServiceRate(String redemptiveServiceRate) {
		this.redemptiveServiceRate = redemptiveServiceRate;
	}

	public String getChangeValidPeriod() {
		return changeValidPeriod;
	}

	public void setChangeValidPeriod(String changeValidPeriod) {
		this.changeValidPeriod = changeValidPeriod;
	}

	public String getValidPeriodRule() {
		return validPeriodRule;
	}

	public void setValidPeriodRule(String validPeriodRule) {
		this.validPeriodRule = validPeriodRule;
	}

	public String getPwdKeyIndex() {
		return pwdKeyIndex;
	}

	public void setPwdKeyIndex(String pwdKeyIndex) {
		this.pwdKeyIndex = pwdKeyIndex;
	}

	public String getCvvKeyIndex() {
		return cvvKeyIndex;
	}

	public void setCvvKeyIndex(String cvvKeyIndex) {
		this.cvvKeyIndex = cvvKeyIndex;
	}

	public String getBlnKeyIndex() {
		return blnKeyIndex;
	}

	public void setBlnKeyIndex(String blnKeyIndex) {
		this.blnKeyIndex = blnKeyIndex;
	}

	public String getDefaultRuleNo() {
		return defaultRuleNo;
	}

	public void setDefaultRuleNo(String defaultRuleNo) {
		this.defaultRuleNo = defaultRuleNo;
	}

	public List<PackageDTO> getPackages() {
		return packages;
	}

	public String getConsumerTimeFlag() {
		return consumerTimeFlag;
	}

	public void setConsumerTimeFlag(String consumerTimeFlag) {
		this.consumerTimeFlag = consumerTimeFlag;
	}

	public String getRechargeTimeFlag() {
		return rechargeTimeFlag;
	}

	public void setRechargeTimeFlag(String rechargeTimeFlag) {
		this.rechargeTimeFlag = rechargeTimeFlag;
	}

	public void setPackages(List<PackageDTO> packages) {
		this.packages = packages;
	}

	public List<ProdFaceValueDTO> getProdFaceValueDTO() {
		return prodFaceValueDTO;
	}

	public void setProdFaceValueDTO(List<ProdFaceValueDTO> prodFaceValueDTO) {
		this.prodFaceValueDTO = prodFaceValueDTO;
	}

	public List<CardLayoutDTO> getCardLayoutDTOs() {
		return cardLayoutDTOs;
	}

	public void setCardLayoutDTOs(List<CardLayoutDTO> cardLayoutDTOs) {
		this.cardLayoutDTOs = cardLayoutDTOs;
	}

	public Date getStartDateDate() {
		return startDateDate;
	}

	public void setStartDateDate(Date startDateDate) {
		this.startDateDate = startDateDate;
	}

	public String getCardLayoutId() {
		return cardLayoutId;
	}

	public void setCardLayoutId(String cardLayoutId) {
		this.cardLayoutId = cardLayoutId;
	}

	public List<ServiceDTO> getServices() {
		return services;
	}

	public void setServices(List<ServiceDTO> services) {
		this.services = services;
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public String getAnnualFee() {
		return annualFee;
	}

	public String getAvailableOrder() {
		return availableOrder;
	}

	public String getAvailableStock() {
		return availableStock;
	}

	public String getCardFee() {
		return cardFee;
	}

	public String getCardType() {
		return cardType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getDataState() {
		return dataState;
	}

	public String getEmailSvcStat() {
		return emailSvcStat;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getInitActStat() {
		return initActStat;
	}

	public String getIvrSvcStat() {
		return ivrSvcStat;
	}

	public String getMaxBalance() {
		return maxBalance;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public String getMonstmtSvcStat() {
		return monstmtSvcStat;
	}

	public String getOnymousStat() {
		return onymousStat;
	}

	public String getPinDelivMeans() {
		return pinDelivMeans;
	}

	public String getPinStat() {
		return pinStat;
	}

	public String getPreAuthStat() {
		return preAuthStat;
	}

	public String getProdStat() {
		return prodStat;
	}

	public String getProductEnglishName() {
		return productEnglishName;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductType() {
		return productType;
	}

	public String getReplaceFee() {
		return replaceFee;
	}

	public String getSmsSvcStat() {
		return smsSvcStat;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getSupplStat() {
		return supplStat;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public String getWebEmailSvcStat() {
		return webEmailSvcStat;
	}

	public String getWebPayStat() {
		return webPayStat;
	}

	public String getWebSmsSvcStat() {
		return webSmsSvcStat;
	}

	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}

	public void setAvailableOrder(String availableOrder) {
		this.availableOrder = availableOrder;
	}

	public void setAvailableStock(String availableStock) {
		this.availableStock = availableStock;
	}

	public void setCardFee(String cardFee) {
		this.cardFee = cardFee;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public void setEmailSvcStat(String emailSvcStat) {
		this.emailSvcStat = emailSvcStat;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setInitActStat(String initActStat) {
		this.initActStat = initActStat;
	}

	public void setIvrSvcStat(String ivrSvcStat) {
		this.ivrSvcStat = ivrSvcStat;
	}

	public void setMaxBalance(String maxBalance) {
		this.maxBalance = maxBalance;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setMonstmtSvcStat(String monstmtSvcStat) {
		this.monstmtSvcStat = monstmtSvcStat;
	}

	public void setOnymousStat(String onymousStat) {
		this.onymousStat = onymousStat;
	}

	public void setPinDelivMeans(String pinDelivMeans) {
		this.pinDelivMeans = pinDelivMeans;
	}

	public void setPinStat(String pinStat) {
		this.pinStat = pinStat;
	}

	public void setPreAuthStat(String preAuthStat) {
		this.preAuthStat = preAuthStat;
	}

	public void setProdStat(String prodStat) {
		this.prodStat = prodStat;
	}

	public void setProductEnglishName(String productEnglishName) {
		this.productEnglishName = productEnglishName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setReplaceFee(String replaceFee) {
		this.replaceFee = replaceFee;
	}

	public void setSmsSvcStat(String smsSvcStat) {
		this.smsSvcStat = smsSvcStat;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setSupplStat(String supplStat) {
		this.supplStat = supplStat;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public void setWebEmailSvcStat(String webEmailSvcStat) {
		this.webEmailSvcStat = webEmailSvcStat;
	}

	public void setWebPayStat(String webPayStat) {
		this.webPayStat = webPayStat;
	}

	public void setWebSmsSvcStat(String webSmsSvcStat) {
		this.webSmsSvcStat = webSmsSvcStat;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Date getEndTimeTime() {
		return endTimeTime;
	}

	public void setEndTimeTime(Date endTimeTime) {
		this.endTimeTime = endTimeTime;
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

	public List<ProductCardBinDTO> getProductCardBinDTOs() {
		return productCardBinDTOs;
	}

	public void setProductCardBinDTOs(List<ProductCardBinDTO> productCardBinDTOs) {
		this.productCardBinDTOs = productCardBinDTOs;
	}

	public String[] getCardBinIds() {
		return cardBinIds;
	}

	public void setCardBinIds(String[] cardBinIds) {
		this.cardBinIds = cardBinIds;
	}

	public String getProductDefineIssuer() {
		return productDefineIssuer;
	}

	public void setProductDefineIssuer(String productDefineIssuer) {
		this.productDefineIssuer = productDefineIssuer;
	}

	public String getNoPinLimit() {
		return noPinLimit;
	}

	public void setNoPinLimit(String noPinLimit) {
		this.noPinLimit = noPinLimit;
	}

}
