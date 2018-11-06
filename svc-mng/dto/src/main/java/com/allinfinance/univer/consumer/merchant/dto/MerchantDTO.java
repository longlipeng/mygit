package com.allinfinance.univer.consumer.merchant.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;

public class MerchantDTO extends BaseDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4099719728961588149L;
	private String entityId;
	private String fatherEntityId;
	private String merchantName;
	private String merchantCode;
	private String merchantEnglishName;

	private String merchantSize;
	private String merchantComment;
	private String externalId;
	private String legacyMerchantId;
	private String merchantType;
	private String invoiceName;

	private String responsibleEmployee;
	private String salesmanId;
	private String channelId;
	private String chanBegDate;
	private String enableWebsite;
	private String websiteUserName;
	private String websitePassword;
	private String merchantState;
	private String commissionFee;
	private String reimburseWithoutCheck;
	private String purchasePause;
	private String reimbursePause;
	private String reimbursementType;
	private String joinDate;
	private String settleDatePre;
	private String settleDateNext;
	private String certificateNo;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

	private String ePayIn;
	private String mchntUrl;
	private String dnInfo;
	private Integer txnQryTimes;

	private String merchantRealityName;
	private String merchantAttribute;
	private String merchantIdustry;
	private String merchantTransactionType;
	private String merchantManageTime;
	private Integer postApplyNum;
	private String isMovePost;
	private String isAllopatryAcquire;
	private String isAcquireExp;
	private String isApplyMaterialPic;
	private String annotation;
	private String shopMaxCode;

	//联系方式
	private Object customerManagerName;
	private Object customerManagerTelephone1;
	private String customerManagerTelephone2;
	private Object merchantLinkman;
	private String merchantTelephone;
	private Object merchantTelephone2;
	private String merchantFax;
	private String merchantWebsite;
	private String merchantAddress;
	private String merchantEnglishAddress;
	private String merchantPostcode;
	
	//结算信息
	private String settAgencyId;
	private String merchantSettType;
	private Object merchantBank;
	private Object merchantAccount;
	private Object merchantSettAccount;
	
	//证件信息
	private Object orgCode;
	private String businessLicenseFrom;
	private String businessLicenseTo;
	private String merchantLegalPerson;
	private String legalPersonIdno;
	private String merchantOpeningTime;
	private Integer merchantBranchNum;
	private String merchantRegisteredCapital;
	private Integer merchantEmployeesNum;
	private String landType;
	private String merchantSction;
	private String merchantPopularity;
	private String previousYearPos;
	private String isFiveCertificateAll;
	private String isSignHave;
	private String businessLicenseNumber;
	
	//信用记录
	private String isCreditInvestigation;
	private String isInspectLegalPerson;
	private String isInspectBadnessDrawer;
	private String isPhotoOnFile;
	private String isRejectedAcquirer;
	private String isMerchantLawsuit;
	private String isPrincipalLawsuit;
	private String isPunish;
	private String punishContent;
	private String principalCreditStatus;
	
	
	

	private List<UserDTO> salesmanList;
	private String salesmanName;
	private List<ShopDTO> shopList;
	private List<MerchantDTO> merchantDTOList;
	private List<ContactDTO> contactList = new ArrayList<ContactDTO>();;
	private Date joinDateDate;
	private Date chanBegDateDate;
	private List<String> entityIdList;
	//银行
	private List<BankDTO> bankList=new ArrayList<BankDTO>();
	//合同
	private List<ConsumerContractDTO> contractList = new ArrayList<ConsumerContractDTO>();
	//合同明细
	private List<AccTypeContractDTO> accTypeContractDTOList = new ArrayList<AccTypeContractDTO>();

	//担保方式 担保有效期
	private String guaranteeType;
	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getGuaranteeValidDate() {
		return guaranteeValidDate;
	}

	public void setGuaranteeValidDate(String guaranteeValidDate) {
		this.guaranteeValidDate = guaranteeValidDate;
	}

	private String guaranteeValidDate;
 

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantEnglishName() {
		return merchantEnglishName;
	}

	public void setMerchantEnglishName(String merchantEnglishName) {
		this.merchantEnglishName = merchantEnglishName;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMerchantEnglishAddress() {
		return merchantEnglishAddress;
	}

	public void setMerchantEnglishAddress(String merchantEnglishAddress) {
		this.merchantEnglishAddress = merchantEnglishAddress;
	}

	public String getMerchantPostcode() {
		return merchantPostcode;
	}

	public void setMerchantPostcode(String merchantPostcode) {
		this.merchantPostcode = merchantPostcode;
	}

	public String getMerchantTelephone() {
		return merchantTelephone;
	}

	public void setMerchantTelephone(String merchantTelephone) {
		this.merchantTelephone = merchantTelephone;
	}

	public String getMerchantFax() {
		return merchantFax;
	}

	public void setMerchantFax(String merchantFax) {
		this.merchantFax = merchantFax;
	}

	public String getMerchantWebsite() {
		return merchantWebsite;
	}

	public void setMerchantWebsite(String merchantWebsite) {
		this.merchantWebsite = merchantWebsite;
	}

	public String getMerchantSize() {
		return merchantSize;
	}

	public void setMerchantSize(String merchantSize) {
		this.merchantSize = merchantSize;
	}

	public String getMerchantComment() {
		return merchantComment;
	}

	public void setMerchantComment(String merchantComment) {
		this.merchantComment = merchantComment;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getLegacyMerchantId() {
		return legacyMerchantId;
	}

	public void setLegacyMerchantId(String legacyMerchantId) {
		this.legacyMerchantId = legacyMerchantId;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getSettAgencyId() {
		return settAgencyId;
	}

	public void setSettAgencyId(String settAgencyId) {
		this.settAgencyId = settAgencyId;
	}

	public String getResponsibleEmployee() {
		return responsibleEmployee;
	}

	public void setResponsibleEmployee(String responsibleEmployee) {
		this.responsibleEmployee = responsibleEmployee;
	}

	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChanBegDate() {
		return chanBegDate;
	}

	public void setChanBegDate(String chanBegDate) {
		this.chanBegDate = chanBegDate;
	}

	public String getEnableWebsite() {
		return enableWebsite;
	}

	public void setEnableWebsite(String enableWebsite) {
		this.enableWebsite = enableWebsite;
	}

	public String getWebsiteUserName() {
		return websiteUserName;
	}

	public void setWebsiteUserName(String websiteUserName) {
		this.websiteUserName = websiteUserName;
	}

	public String getWebsitePassword() {
		return websitePassword;
	}

	public void setWebsitePassword(String websitePassword) {
		this.websitePassword = websitePassword;
	}

	public String getMerchantState() {
		return merchantState;
	}

	public void setMerchantState(String merchantState) {
		this.merchantState = merchantState;
	}

	public String getCommissionFee() {
		return commissionFee;
	}

	public void setCommissionFee(String commissionFee) {
		this.commissionFee = commissionFee;
	}

	public String getReimburseWithoutCheck() {
		return reimburseWithoutCheck;
	}

	public void setReimburseWithoutCheck(String reimburseWithoutCheck) {
		this.reimburseWithoutCheck = reimburseWithoutCheck;
	}

	public String getPurchasePause() {
		return purchasePause;
	}

	public void setPurchasePause(String purchasePause) {
		this.purchasePause = purchasePause;
	}

	public String getReimbursePause() {
		return reimbursePause;
	}

	public void setReimbursePause(String reimbursePause) {
		this.reimbursePause = reimbursePause;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getSettleDatePre() {
		return settleDatePre;
	}

	public void setSettleDatePre(String settleDatePre) {
		this.settleDatePre = settleDatePre;
	}

	public String getSettleDateNext() {
		return settleDateNext;
	}

	public void setSettleDateNext(String settleDateNext) {
		this.settleDateNext = settleDateNext;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
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

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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

	public String getMerchantRealityName() {
		return merchantRealityName;
	}

	public void setMerchantRealityName(String merchantRealityName) {
		this.merchantRealityName = merchantRealityName;
	}

	public String getMerchantAttribute() {
		return merchantAttribute;
	}

	public void setMerchantAttribute(String merchantAttribute) {
		this.merchantAttribute = merchantAttribute;
	}

	public String getMerchantIdustry() {
		return merchantIdustry;
	}

	public void setMerchantIdustry(String merchantIdustry) {
		this.merchantIdustry = merchantIdustry;
	}

	public String getMerchantTransactionType() {
		return merchantTransactionType;
	}

	public void setMerchantTransactionType(String merchantTransactionType) {
		this.merchantTransactionType = merchantTransactionType;
	}

	public String getMerchantManageTime() {
		return merchantManageTime;
	}

	public void setMerchantManageTime(String merchantManageTime) {
		this.merchantManageTime = merchantManageTime;
	}

	public Integer getPostApplyNum() {
		return postApplyNum;
	}

	public void setPostApplyNum(Integer postApplyNum) {
		this.postApplyNum = postApplyNum;
	}

	public String getIsMovePost() {
		return isMovePost;
	}

	public void setIsMovePost(String isMovePost) {
		this.isMovePost = isMovePost;
	}

	public String getIsAllopatryAcquire() {
		return isAllopatryAcquire;
	}

	public void setIsAllopatryAcquire(String isAllopatryAcquire) {
		this.isAllopatryAcquire = isAllopatryAcquire;
	}

	public String getIsAcquireExp() {
		return isAcquireExp;
	}

	public void setIsAcquireExp(String isAcquireExp) {
		this.isAcquireExp = isAcquireExp;
	}

	public String getIsApplyMaterialPic() {
		return isApplyMaterialPic;
	}

	public void setIsApplyMaterialPic(String isApplyMaterialPic) {
		this.isApplyMaterialPic = isApplyMaterialPic;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public Object getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(Object customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public Object getCustomerManagerTelephone1() {
		return customerManagerTelephone1;
	}

	public void setCustomerManagerTelephone1(Object customerManagerTelephone1) {
		this.customerManagerTelephone1 = customerManagerTelephone1;
	}

	public String getCustomerManagerTelephone2() {
		return customerManagerTelephone2;
	}

	public void setCustomerManagerTelephone2(String customerManagerTelephone2) {
		this.customerManagerTelephone2 = customerManagerTelephone2;
	}

	public Object getMerchantLinkman() {
		return merchantLinkman;
	}

	public void setMerchantLinkman(Object merchantLinkman) {
		this.merchantLinkman = merchantLinkman;
	}

	public Object getMerchantTelephone2() {
		return merchantTelephone2;
	}

	public void setMerchantTelephone2(Object merchantTelephone2) {
		this.merchantTelephone2 = merchantTelephone2;
	}

	public String getMerchantSettType() {
		return merchantSettType;
	}

	public void setMerchantSettType(String merchantSettType) {
		this.merchantSettType = merchantSettType;
	}

	public Object getMerchantBank() {
		return merchantBank;
	}

	public void setMerchantBank(Object merchantBank) {
		this.merchantBank = merchantBank;
	}

	public Object getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(Object merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public Object getMerchantSettAccount() {
		return merchantSettAccount;
	}

	public void setMerchantSettAccount(Object merchantSettAccount) {
		this.merchantSettAccount = merchantSettAccount;
	}

	public Object getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(Object orgCode) {
		this.orgCode = orgCode;
	}

	public String getBusinessLicenseFrom() {
		return businessLicenseFrom;
	}

	public void setBusinessLicenseFrom(String businessLicenseFrom) {
		this.businessLicenseFrom = businessLicenseFrom;
	}

	public String getBusinessLicenseTo() {
		return businessLicenseTo;
	}

	public void setBusinessLicenseTo(String businessLicenseTo) {
		this.businessLicenseTo = businessLicenseTo;
	}

	public String getMerchantLegalPerson() {
		return merchantLegalPerson;
	}

	public void setMerchantLegalPerson(String merchantLegalPerson) {
		this.merchantLegalPerson = merchantLegalPerson;
	}

	public String getLegalPersonIdno() {
		return legalPersonIdno;
	}

	public void setLegalPersonIdno(String legalPersonIdno) {
		this.legalPersonIdno = legalPersonIdno;
	}

	public String getMerchantOpeningTime() {
		return merchantOpeningTime;
	}

	public void setMerchantOpeningTime(String merchantOpeningTime) {
		this.merchantOpeningTime = merchantOpeningTime;
	}

	public Integer getMerchantBranchNum() {
		return merchantBranchNum;
	}

	public void setMerchantBranchNum(Integer merchantBranchNum) {
		this.merchantBranchNum = merchantBranchNum;
	}

	public String getMerchantRegisteredCapital() {
		return merchantRegisteredCapital;
	}

	public void setMerchantRegisteredCapital(String merchantRegisteredCapital) {
		this.merchantRegisteredCapital = merchantRegisteredCapital;
	}

	public Integer getMerchantEmployeesNum() {
		return merchantEmployeesNum;
	}

	public void setMerchantEmployeesNum(Integer merchantEmployeesNum) {
		this.merchantEmployeesNum = merchantEmployeesNum;
	}

	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	public String getMerchantSction() {
		return merchantSction;
	}

	public void setMerchantSction(String merchantSction) {
		this.merchantSction = merchantSction;
	}

	public String getMerchantPopularity() {
		return merchantPopularity;
	}

	public void setMerchantPopularity(String merchantPopularity) {
		this.merchantPopularity = merchantPopularity;
	}

	public String getPreviousYearPos() {
		return previousYearPos;
	}

	public void setPreviousYearPos(String previousYearPos) {
		this.previousYearPos = previousYearPos;
	}

	public String getIsFiveCertificateAll() {
		return isFiveCertificateAll;
	}

	public void setIsFiveCertificateAll(String isFiveCertificateAll) {
		this.isFiveCertificateAll = isFiveCertificateAll;
	}

	public String getIsSignHave() {
		return isSignHave;
	}

	public void setIsSignHave(String isSignHave) {
		this.isSignHave = isSignHave;
	}

	public String getIsCreditInvestigation() {
		return isCreditInvestigation;
	}

	public void setIsCreditInvestigation(String isCreditInvestigation) {
		this.isCreditInvestigation = isCreditInvestigation;
	}

	public String getIsInspectLegalPerson() {
		return isInspectLegalPerson;
	}

	public void setIsInspectLegalPerson(String isInspectLegalPerson) {
		this.isInspectLegalPerson = isInspectLegalPerson;
	}

	public String getIsInspectBadnessDrawer() {
		return isInspectBadnessDrawer;
	}

	public void setIsInspectBadnessDrawer(String isInspectBadnessDrawer) {
		this.isInspectBadnessDrawer = isInspectBadnessDrawer;
	}

	public String getIsPhotoOnFile() {
		return isPhotoOnFile;
	}

	public void setIsPhotoOnFile(String isPhotoOnFile) {
		this.isPhotoOnFile = isPhotoOnFile;
	}

	public String getIsRejectedAcquirer() {
		return isRejectedAcquirer;
	}

	public void setIsRejectedAcquirer(String isRejectedAcquirer) {
		this.isRejectedAcquirer = isRejectedAcquirer;
	}

	public String getIsMerchantLawsuit() {
		return isMerchantLawsuit;
	}

	public void setIsMerchantLawsuit(String isMerchantLawsuit) {
		this.isMerchantLawsuit = isMerchantLawsuit;
	}

	public String getIsPrincipalLawsuit() {
		return isPrincipalLawsuit;
	}

	public void setIsPrincipalLawsuit(String isPrincipalLawsuit) {
		this.isPrincipalLawsuit = isPrincipalLawsuit;
	}

	public String getIsPunish() {
		return isPunish;
	}

	public void setIsPunish(String isPunish) {
		this.isPunish = isPunish;
	}

	public String getPunishContent() {
		return punishContent;
	}

	public void setPunishContent(String punishContent) {
		this.punishContent = punishContent;
	}

	public String getPrincipalCreditStatus() {
		return principalCreditStatus;
	}

	public void setPrincipalCreditStatus(String principalCreditStatus) {
		this.principalCreditStatus = principalCreditStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<UserDTO> getSalesmanList() {
		return salesmanList;
	}

	public void setSalesmanList(List<UserDTO> salesmanList) {
		this.salesmanList = salesmanList;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public List<ShopDTO> getShopList() {
		return shopList;
	}

	public void setShopList(List<ShopDTO> shopList) {
		this.shopList = shopList;
	}

	public List<MerchantDTO> getMerchantDTOList() {
		return merchantDTOList;
	}

	public void setMerchantDTOList(List<MerchantDTO> merchantDTOList) {
		this.merchantDTOList = merchantDTOList;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public List<ContactDTO> getContactList() {
		return contactList;
	}

	public void setContactList(List<ContactDTO> contactList) {
		this.contactList = contactList;
	}

	public Date getJoinDateDate() {
		return joinDateDate;
	}

	public void setJoinDateDate(Date joinDateDate) {
		this.joinDateDate = joinDateDate;
	}

	public Date getChanBegDateDate() {
		return chanBegDateDate;
	}

	public void setChanBegDateDate(Date chanBegDateDate) {
		this.chanBegDateDate = chanBegDateDate;
	}

	public List<String> getEntityIdList() {
		return entityIdList;
	}

	public void setEntityIdList(List<String> entityIdList) {
		this.entityIdList = entityIdList;
	}

	public String getePayIn() {
		return ePayIn;
	}

	public void setePayIn(String ePayIn) {
		this.ePayIn = ePayIn;
	}

	

	public String getMchntUrl() {
		return mchntUrl;
	}

	public void setMchntUrl(String mchntUrl) {
		this.mchntUrl = mchntUrl;
	}

	public String getDnInfo() {
		return dnInfo;
	}

	public void setDnInfo(String dnInfo) {
		this.dnInfo = dnInfo;
	}

	public Integer getTxnQryTimes() {
		return txnQryTimes;
	}

	public void setTxnQryTimes(Integer txnQryTimes) {
		this.txnQryTimes = txnQryTimes;
	}

	public void setBankList(List<BankDTO> bankList) {
		this.bankList = bankList;
	}

	public List<BankDTO> getBankList() {
		return bankList;
	}

	public void setContractList(List<ConsumerContractDTO> contractList) {
		this.contractList = contractList;
	}

	public List<ConsumerContractDTO> getContractList() {
		return contractList;
	}

	public void setAccTypeContractDTOList(List<AccTypeContractDTO> accTypeContractDTOList) {
		this.accTypeContractDTOList = accTypeContractDTOList;
	}

	public List<AccTypeContractDTO> getAccTypeContractDTOList() {
		return accTypeContractDTOList;
	}

	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}

	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}

	public void setShopMaxCode(String shopMaxCode) {
		this.shopMaxCode = shopMaxCode;
	}

	public String getShopMaxCode() {
		return shopMaxCode;
	}

}
