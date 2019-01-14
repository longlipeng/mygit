package com.allinfinance.univer.seller.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;

public class CustomerDTO extends BaseDTO {

	/**
	 * 客户DTO
	 */
	private static final long serialVersionUID = 1L;
	private String validity;
	private String customerName;

	private String customerCode;

	private String customerEnglishName;

	private String customerAddress;

	private String nationality;
	/**
	 * 个人客户的性别
	 */
	private String gender;
	/**
	 * 客户的受理区域
	 */
	private String acceptArea;
	private String customerEnglishAddress;
	private String city;

	private String customerPostcode;

	private String customerTelephone;

	private String customerFax;

	private String customerWebsite;

	private String customerSize;

	private String customerComment;

	private String externalId;

	private String legCusId;

	private String channelId;

	private String chanBegDate;

	private String printName;

	private String nation;// 民族

	private String education;// 学历

	private String marriage;// 婚姻状况

	private String email;// 邮箱

	private String extPrintInfo;

	private String closeDate;

	private Date closeDateDate;

	private String paymentTerm;

	private String paymentDelay;

	private String salesRegionId;

	private String businessCity;

	private String businessAreaId;

	private String salesmanId;

	private String activitySector;

	private String hasDm;

	private String cusState;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String dataState;

	private String fatherEntityId;

	private String entityId;

	private String custContract;

	/**
	 * 新增企业客户信息
	 */
	private String companyName;

	private String companyEnglishname;

	private String corpCredValidity;

	private String corpGender;

	private String corpCountyr;
	private String corpAliasName;

	private String corpBirthday;

	private String corpProfession;

	private String corpTelephoneNumber;

	private String corpAddress;

	private String companyCountyr;

	private String companyRegisteredAddress;

	private String companyIdType;

	private String companyId;

	private String companyAccountant;

	private String companyDescription;

	private String registeredCapital;

	private String companySize;

	private String postcode;

	private String companyFax;

	private String operatorName;

	private String operatorType;

	private String operatorId;

	private String operatorValidity;

	private String operatorTelephoneNumber;

	private String operatorAddress;

	private String bankAccount;

	private String bankName;

	private String verifyStat;

	private String riskGrade; // 风险级别(默认是未评级)

	private String isblacklist;// 黑名单标识（默认是未评级）

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	// 销售人员列表
	private List<UserDTO> salesmanList;
	private String[] customerIds;

	// 发票地址
	private List<InvoiceAddressDTO> invoiceAddressList = new ArrayList<InvoiceAddressDTO>();
	// 发票公司
	private List<InvoiceCompanyDTO> invoiceCompanyList = new ArrayList<InvoiceCompanyDTO>();
	// 快递点
	private List<DeliveryPointDTO> deliveryPointList = new ArrayList<DeliveryPointDTO>();
	// 联系人
	private List<ContactDTO> contractList = new ArrayList<ContactDTO>();
	// 部门
	private List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>();
	// 银行
	private List<BankDTO> bankList = new ArrayList<BankDTO>();
	// 法人信息
	private String corpName;

	// 法人证件类型
	private String corpCredType;

	// 法人证件号
	private String corpCredId;

	// 法人证件有效开始时间
	private String corpCredStaValidity;

	// 法人证件有效结束时间
	private String corpCredEndValidity;

	// 法人联系电话
	private String corpPhone;

	// 经办人名称
	private String operName;

	// 经办人证件类型
	private String operCredType;

	// 经办人证件号
	private String operCredId;

	// 经办人证件有效期开始时间
	private String operCredStaValidity;

	// 经办人证件有效期结束时间
	private String operCredEndValidity;

	// 经办人联系电话
	private String operPhone;

	// 注册名称
	private String regiName;

	// 营业执照号码
	private String licenseId;

	// 组织机构代码
	private String organizCode;

	// 营业执照有效期开始时间
	private String licenseStaValidity;

	// 营业执照有效期结束时间
	private String licenseEndValidity;

	// 银行开户许可证号
	private String bankPermit;

	// 注册资本
	private String regiCapital;

	// 员工人数
	private String peopleNumber;

	// 客户知名度
	private String awareness;

	// 五证是否齐全
	private String fiveCertificate;

	// 客户信息调查表是否分支行
	private String survey;

	// 人行征信系统是否已征信
	private String creditInformation;

	// 法人身份信息是否已核查
	private String identityInspect;

	// 不良出票人系统是否已核查
	private String badnessInspect;

	// 客户证件照片信息是否已存档
	private String pictureSave;

	// 客户是否卷入法律诉讼
	private String actionAtLaw;

	// 客户负责人（法人代表或高管）是否卷入法律诉讼
	private String corpActionAtLaw;

	// 客户是否被执法部门处罚记录
	private String punishRecordFlag;

	// 客户被执法部门处罚记录原因
	private String punishRecordInfo;

	// 客户负责人（法人）的信用状况
	private String creditStatus;

	// 客户类型(个人/企业)
	private String customerType;

	// 上年度购卡总金额
	private String lastYear;
	// 渠道信息
	private String channel;

	// 客户证件照片
	private String imgfPath;
	private String imgbPath;
	private String ctidEdt;// 主体证件有效期

	private String linkphone;// 联系人固话
	private String relName; // 实际控制人姓名
	private String citp; // 实际控制人证件类型
	private String ctid; // 实际控制人证件号
	private String citpNt; // 实际控制人证件类型说明
	private String holdPer; // 实际控制人持股比例
	private String holdAmt; // 实际控制人持股金额
	// 实际控制人证件起始日期
	private String ctidStartValidity;
	// 实际控制人证件有效期
	private String ctidEndValidity;

	private String operatorStartValidity;// 代办人证件前世有时间

	public String getImgfPath() {
		return imgfPath;
	}

	public void setImgfPath(String imgfPath) {
		this.imgfPath = imgfPath;
	}

	public String getImgbPath() {
		return imgbPath;
	}

	public void setImgbPath(String imgbPath) {
		this.imgbPath = imgbPath;
	}

	public String getCustContract() {
		return custContract;
	}

	public void setCustContract(String custContract) {
		this.custContract = custContract;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpCredType() {
		return corpCredType;
	}

	public void setCorpCredType(String corpCredType) {
		this.corpCredType = corpCredType;
	}

	public String getCorpCredId() {
		return corpCredId;
	}

	public void setCorpCredId(String corpCredId) {
		this.corpCredId = corpCredId;
	}

	public String getCorpCredStaValidity() {
		return corpCredStaValidity;
	}

	public void setCorpCredStaValidity(String corpCredStaValidity) {
		this.corpCredStaValidity = corpCredStaValidity;
	}

	public String getCorpCredEndValidity() {
		return corpCredEndValidity;
	}

	public void setCorpCredEndValidity(String corpCredEndValidity) {
		this.corpCredEndValidity = corpCredEndValidity;
	}

	public String getCorpPhone() {
		return corpPhone;
	}

	public void setCorpPhone(String corpPhone) {
		this.corpPhone = corpPhone;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperCredType() {
		return operCredType;
	}

	public void setOperCredType(String operCredType) {
		this.operCredType = operCredType;
	}

	public String getOperCredId() {
		return operCredId;
	}

	public void setOperCredId(String operCredId) {
		this.operCredId = operCredId;
	}

	public String getOperCredStaValidity() {
		return operCredStaValidity;
	}

	public void setOperCredStaValidity(String operCredStaValidity) {
		this.operCredStaValidity = operCredStaValidity;
	}

	public String getOperCredEndValidity() {
		return operCredEndValidity;
	}

	public void setOperCredEndValidity(String operCredEndValidity) {
		this.operCredEndValidity = operCredEndValidity;
	}

	public String getOperPhone() {
		return operPhone;
	}

	public void setOperPhone(String operPhone) {
		this.operPhone = operPhone;
	}

	public String getRegiName() {
		return regiName;
	}

	public void setRegiName(String regiName) {
		this.regiName = regiName;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getOrganizCode() {
		return organizCode;
	}

	public void setOrganizCode(String organizCode) {
		this.organizCode = organizCode;
	}

	public String getLicenseStaValidity() {
		return licenseStaValidity;
	}

	public void setLicenseStaValidity(String licenseStaValidity) {
		this.licenseStaValidity = licenseStaValidity;
	}

	public String getLicenseEndValidity() {
		return licenseEndValidity;
	}

	public void setLicenseEndValidity(String licenseEndValidity) {
		this.licenseEndValidity = licenseEndValidity;
	}

	public String getBankPermit() {
		return bankPermit;
	}

	public void setBankPermit(String bankPermit) {
		this.bankPermit = bankPermit;
	}

	public String getRegiCapital() {
		return regiCapital;
	}

	public void setRegiCapital(String regiCapital) {
		this.regiCapital = regiCapital;
	}

	public String getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(String peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public String getAwareness() {
		return awareness;
	}

	public void setAwareness(String awareness) {
		this.awareness = awareness;
	}

	public String getFiveCertificate() {
		return fiveCertificate;
	}

	public void setFiveCertificate(String fiveCertificate) {
		this.fiveCertificate = fiveCertificate;
	}

	public String getSurvey() {
		return survey;
	}

	public void setSurvey(String survey) {
		this.survey = survey;
	}

	public String getCreditInformation() {
		return creditInformation;
	}

	public void setCreditInformation(String creditInformation) {
		this.creditInformation = creditInformation;
	}

	public String getIdentityInspect() {
		return identityInspect;
	}

	public void setIdentityInspect(String identityInspect) {
		this.identityInspect = identityInspect;
	}

	public String getBadnessInspect() {
		return badnessInspect;
	}

	public void setBadnessInspect(String badnessInspect) {
		this.badnessInspect = badnessInspect;
	}

	public String getPictureSave() {
		return pictureSave;
	}

	public void setPictureSave(String pictureSave) {
		this.pictureSave = pictureSave;
	}

	public String getActionAtLaw() {
		return actionAtLaw;
	}

	public void setActionAtLaw(String actionAtLaw) {
		this.actionAtLaw = actionAtLaw;
	}

	public String getCorpActionAtLaw() {
		return corpActionAtLaw;
	}

	public void setCorpActionAtLaw(String corpActionAtLaw) {
		this.corpActionAtLaw = corpActionAtLaw;
	}

	public String getPunishRecordFlag() {
		return punishRecordFlag;
	}

	public void setPunishRecordFlag(String punishRecordFlag) {
		this.punishRecordFlag = punishRecordFlag;
	}

	public String getPunishRecordInfo() {
		return punishRecordInfo;
	}

	public void setPunishRecordInfo(String punishRecordInfo) {
		this.punishRecordInfo = punishRecordInfo;
	}

	public String getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getLastYear() {
		return lastYear;
	}

	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerEnglishName() {
		return customerEnglishName;
	}

	public void setCustomerEnglishName(String customerEnglishName) {
		this.customerEnglishName = customerEnglishName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEnglishAddress() {
		return customerEnglishAddress;
	}

	public void setCustomerEnglishAddress(String customerEnglishAddress) {
		this.customerEnglishAddress = customerEnglishAddress;
	}

	public String getCustomerPostcode() {
		return customerPostcode;
	}

	public void setCustomerPostcode(String customerPostcode) {
		this.customerPostcode = customerPostcode;
	}

	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	public String getCustomerFax() {
		return customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	public String getCustomerWebsite() {
		return customerWebsite;
	}

	public void setCustomerWebsite(String customerWebsite) {
		this.customerWebsite = customerWebsite;
	}

	public String getCustomerSize() {
		return customerSize;
	}

	public void setCustomerSize(String customerSize) {
		this.customerSize = customerSize;
	}

	public String getCustomerComment() {
		return customerComment;
	}

	public void setCustomerComment(String customerComment) {
		this.customerComment = customerComment;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getLegCusId() {
		return legCusId;
	}

	public void setLegCusId(String legCusId) {
		this.legCusId = legCusId;
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

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public String getExtPrintInfo() {
		return extPrintInfo;
	}

	public void setExtPrintInfo(String extPrintInfo) {
		this.extPrintInfo = extPrintInfo;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getPaymentDelay() {
		return paymentDelay;
	}

	public void setPaymentDelay(String paymentDelay) {
		this.paymentDelay = paymentDelay;
	}

	public String getSalesRegionId() {
		return salesRegionId;
	}

	public void setSalesRegionId(String salesRegionId) {
		this.salesRegionId = salesRegionId;
	}

	public String getBusinessCity() {
		return businessCity;
	}

	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}

	public String getBusinessAreaId() {
		return businessAreaId;
	}

	public void setBusinessAreaId(String businessAreaId) {
		this.businessAreaId = businessAreaId;
	}

	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getActivitySector() {
		return activitySector;
	}

	public void setActivitySector(String activitySector) {
		this.activitySector = activitySector;
	}

	public String getHasDm() {
		return hasDm;
	}

	public void setHasDm(String hasDm) {
		this.hasDm = hasDm;
	}

	public String getCusState() {
		return cusState;
	}

	public void setCusState(String cusState) {
		this.cusState = cusState;
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

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getCloseDateDate() {
		return closeDateDate;
	}

	public void setCloseDateDate(Date closeDateDate) {
		this.closeDateDate = closeDateDate;
	}

	public List<UserDTO> getSalesmanList() {
		return salesmanList;
	}

	public void setSalesmanList(List<UserDTO> salesmanList) {
		this.salesmanList = salesmanList;
	}

	public String[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	public List<InvoiceAddressDTO> getInvoiceAddressList() {
		return invoiceAddressList;
	}

	public void setInvoiceAddressList(List<InvoiceAddressDTO> invoiceAddressList) {
		this.invoiceAddressList = invoiceAddressList;
	}

	public List<InvoiceCompanyDTO> getInvoiceCompanyList() {
		return invoiceCompanyList;
	}

	public void setInvoiceCompanyList(List<InvoiceCompanyDTO> invoiceCompanyList) {
		this.invoiceCompanyList = invoiceCompanyList;
	}

	public List<DeliveryPointDTO> getDeliveryPointList() {
		return deliveryPointList;
	}

	public void setDeliveryPointList(List<DeliveryPointDTO> deliveryPointList) {
		this.deliveryPointList = deliveryPointList;
	}

	public List<ContactDTO> getContractList() {
		return contractList;
	}

	public void setContractList(List<ContactDTO> contractList) {
		this.contractList = contractList;
	}

	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}

	public List<BankDTO> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankDTO> bankList) {
		this.bankList = bankList;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAcceptArea() {
		return acceptArea;
	}

	public void setAcceptArea(String acceptArea) {
		this.acceptArea = acceptArea;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEnglishname() {
		return companyEnglishname;
	}

	public void setCompanyEnglishname(String companyEnglishname) {
		this.companyEnglishname = companyEnglishname;
	}

	public String getCorpCredValidity() {
		return corpCredValidity;
	}

	public void setCorpCredValidity(String corpCredValidity) {
		this.corpCredValidity = corpCredValidity;
	}

	public String getCorpGender() {
		return corpGender;
	}

	public void setCorpGender(String corpGender) {
		this.corpGender = corpGender;
	}

	public String getCorpAliasName() {
		return corpAliasName;
	}

	public void setCorpAliasName(String corpAliasName) {
		this.corpAliasName = corpAliasName;
	}

	public String getCorpBirthday() {
		return corpBirthday;
	}

	public void setCorpBirthday(String corpBirthday) {
		this.corpBirthday = corpBirthday;
	}

	public String getCorpProfession() {
		return corpProfession;
	}

	public void setCorpProfession(String corpProfession) {
		this.corpProfession = corpProfession;
	}

	public String getCorpTelephoneNumber() {
		return corpTelephoneNumber;
	}

	public void setCorpTelephoneNumber(String corpTelephoneNumber) {
		this.corpTelephoneNumber = corpTelephoneNumber;
	}

	public String getCorpAddress() {
		return corpAddress;
	}

	public void setCorpAddress(String corpAddress) {
		this.corpAddress = corpAddress;
	}

	public String getCompanyCountyr() {
		return companyCountyr;
	}

	public void setCompanyCountyr(String companyCountyr) {
		this.companyCountyr = companyCountyr;
	}

	public String getCompanyRegisteredAddress() {
		return companyRegisteredAddress;
	}

	public void setCompanyRegisteredAddress(String companyRegisteredAddress) {
		this.companyRegisteredAddress = companyRegisteredAddress;
	}

	public String getCompanyIdType() {
		return companyIdType;
	}

	public void setCompanyIdType(String companyIdType) {
		this.companyIdType = companyIdType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyAccountant() {
		return companyAccountant;
	}

	public void setCompanyAccountant(String companyAccountant) {
		this.companyAccountant = companyAccountant;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorValidity() {
		return operatorValidity;
	}

	public void setOperatorValidity(String operatorValidity) {
		this.operatorValidity = operatorValidity;
	}

	public String getOperatorTelephoneNumber() {
		return operatorTelephoneNumber;
	}

	public void setOperatorTelephoneNumber(String operatorTelephoneNumber) {
		this.operatorTelephoneNumber = operatorTelephoneNumber;
	}

	public String getOperatorAddress() {
		return operatorAddress;
	}

	public void setOperatorAddress(String operatorAddress) {
		this.operatorAddress = operatorAddress;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getVerifyStat() {
		return verifyStat;
	}

	public void setVerifyStat(String verifyStat) {
		this.verifyStat = verifyStat;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCorpCountyr() {
		return corpCountyr;
	}

	public void setCorpCountyr(String corpCountyr) {
		this.corpCountyr = corpCountyr;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCtidEdt() {
		return ctidEdt;
	}

	public void setCtidEdt(String ctidEdt) {
		this.ctidEdt = ctidEdt;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getRiskGrade() {
		return riskGrade;
	}

	public void setRiskGrade(String riskGrade) {
		this.riskGrade = riskGrade;
	}

	public String getIsblacklist() {
		return isblacklist;
	}

	public void setIsblacklist(String isblacklist) {
		this.isblacklist = isblacklist;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getCitp() {
		return citp;
	}

	public void setCitp(String citp) {
		this.citp = citp;
	}

	public String getCtid() {
		return ctid;
	}

	public void setCtid(String ctid) {
		this.ctid = ctid;
	}

	public String getCitpNt() {
		return citpNt;
	}

	public void setCitpNt(String citpNt) {
		this.citpNt = citpNt;
	}

	public String getHoldPer() {
		return holdPer;
	}

	public void setHoldPer(String holdPer) {
		this.holdPer = holdPer;
	}

	public String getHoldAmt() {
		return holdAmt;
	}

	public void setHoldAmt(String holdAmt) {
		this.holdAmt = holdAmt;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getOperatorStartValidity() {
		return operatorStartValidity;
	}

	public void setOperatorStartValidity(String operatorStartValidity) {
		this.operatorStartValidity = operatorStartValidity;
	}

	public String getCtidStartValidity() {
		return ctidStartValidity;
	}

	public void setCtidStartValidity(String ctidStartValidity) {
		this.ctidStartValidity = ctidStartValidity;
	}

	public String getCtidEndValidity() {
		return ctidEndValidity;
	}

	public void setCtidEndValidity(String ctidEndValidity) {
		this.ctidEndValidity = ctidEndValidity;
	}

}
