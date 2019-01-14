package com.allinfinance.univer.issuer.dto.consumer;

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
import com.allinfinance.univer.system.role.dto.ResourceDTO;

public class ConsumerDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String entityId;

	private String fatherEntityId;

	private String consumerName;

	private String consumerCode;

	private String consumerEnglishName;

	private String consumerAddress;

	private String consumerEnglishAddress;

	private String consumerPostcode;

	private String consumerTelephone;

	private String consumerFax;

	private String consumerWebsite;

	private String consumerSize;

	private String consumerComment;

	private String externalId;

	private String legacyMerchantId;

	private String consumerType;

	private String invoiceName;

	private String settAgencyId;

	private String responsibleEmployee;

	private String salesmanId;

	private String channelId;

	private String chanBegDate;

	private String enableWebsite;

	private String websiteUserName;

	private String websitePassword;

	private String consumerState;

	private String commissionFee;

	private String reimburseWithoutCheck;

	private String purchasePause;

	private String reimbursePause;

	private String reimbursementType;

	private String joinDate;
	
	private Date joinDateDate;

	private String settleDatePre;
	
	private Date settleDatePreDate;

	private String settleDateNext;
	
	private Date settleDateNextDate;

	private String certificateNo;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String dataState;
    private String userName;
    private String userEmail;
    private String userPassword;
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
	//银行账户
	private List<BankDTO> bankList=new ArrayList<BankDTO>();
	//收单机构权限
	private List<ResourceDTO> resourceDTOs=new ArrayList<ResourceDTO>();
	//收单机构自定义权限
	private List<ResourceDTO> nresourceDTOs=new ArrayList<ResourceDTO>();
	
	private String resourceIds;
	
	public List<BankDTO> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankDTO> bankList) {
		this.bankList = bankList;
	}

	public List<ResourceDTO> getResourceDTOs() {
		return resourceDTOs;
	}

	public void setResourceDTOs(List<ResourceDTO> resourceDTOs) {
		this.resourceDTOs = resourceDTOs;
	}

	public List<ResourceDTO> getNresourceDTOs() {
		return nresourceDTOs;
	}

	public void setNresourceDTOs(List<ResourceDTO> nresourceDTOs) {
		this.nresourceDTOs = nresourceDTOs;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	private List<ConsumerDTO> consumerDTOs;
	
	public List<ConsumerDTO> getConsumerDTOs() {
		return consumerDTOs;
	}

	public void setConsumerDTOs(List<ConsumerDTO> consumerDTOs) {
		this.consumerDTOs = consumerDTOs;
	}

	public Date getSettleDatePreDate() {
		return settleDatePreDate;
	}

	public void setSettleDatePreDate(Date settleDatePreDate) {
		this.settleDatePreDate = settleDatePreDate;
	}

	public Date getSettleDateNextDate() {
		return settleDateNextDate;
	}

	public void setSettleDateNextDate(Date settleDateNextDate) {
		this.settleDateNextDate = settleDateNextDate;
	}

	public Date getJoinDateDate() {
		return joinDateDate;
	}

	public void setJoinDateDate(Date joinDateDate) {
		this.joinDateDate = joinDateDate;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerCode() {
		return consumerCode;
	}

	public void setConsumerCode(String consumerCode) {
		this.consumerCode = consumerCode;
	}

	public String getConsumerEnglishName() {
		return consumerEnglishName;
	}

	public void setConsumerEnglishName(String consumerEnglishName) {
		this.consumerEnglishName = consumerEnglishName;
	}

	public String getConsumerAddress() {
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	public String getConsumerEnglishAddress() {
		return consumerEnglishAddress;
	}

	public void setConsumerEnglishAddress(String consumerEnglishAddress) {
		this.consumerEnglishAddress = consumerEnglishAddress;
	}

	public String getConsumerPostcode() {
		return consumerPostcode;
	}

	public void setConsumerPostcode(String consumerPostcode) {
		this.consumerPostcode = consumerPostcode;
	}

	public String getConsumerTelephone() {
		return consumerTelephone;
	}

	public void setConsumerTelephone(String consumerTelephone) {
		this.consumerTelephone = consumerTelephone;
	}

	public String getConsumerFax() {
		return consumerFax;
	}

	public void setConsumerFax(String consumerFax) {
		this.consumerFax = consumerFax;
	}

	public String getConsumerWebsite() {
		return consumerWebsite;
	}

	public void setConsumerWebsite(String consumerWebsite) {
		this.consumerWebsite = consumerWebsite;
	}

	public String getConsumerSize() {
		return consumerSize;
	}

	public void setConsumerSize(String consumerSize) {
		this.consumerSize = consumerSize;
	}

	public String getConsumerComment() {
		return consumerComment;
	}

	public void setConsumerComment(String consumerComment) {
		this.consumerComment = consumerComment;
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

	public String getConsumerType() {
		return consumerType;
	}

	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
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

	public String getConsumerState() {
		return consumerState;
	}

	public void setConsumerState(String consumerState) {
		this.consumerState = consumerState;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
}
