/**
 * 
 */
package com.allinfinance.univer.seller.seller.dto;

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
import com.allinfinance.univer.system.user.dto.UserDTO;

/**
 * @author dawn 营销机构DTO
 */
public class SellerDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	// 默认用户
	private UserDTO userDTO;

	private String entityId;

	private String fatherEntityId;

	private String sellerName;

	private String sellerCode;

	private String sellerEnglishName;

	private String sellerAddress;

	private String sellerEnglishAddress;

	private String sellerPostcode;

	private String sellerTelephone;

	private String sellerFax;

	private String sellerWebsite;

	private String sellerSize;

	private String sellerComment;

	private String externalId;

	private String legCusId;

	private String channelId;

	private String chanBegDate;

	private String printName;

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

	private String sellerState;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String dataState;

	private String functionRoleId;
	
	/**
     * 上级营销机构
     */
    private String newFatherEntityId;

	public String getFunctionRoleId() {
		return functionRoleId;
	}

	public void setFunctionRoleId(String functionRoleId) {
		this.functionRoleId = functionRoleId;
	}

	// 营销机构id (entityId,fatherEntityId)
	private String[] sellerIds;
	// 销售人员列表
	private List<UserDTO> salesmanList;

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
	// 营销机构权限
	private List<ResourceDTO> resourceDTOs = new ArrayList<ResourceDTO>();
	// 营销机构自定义权限
	private List<ResourceDTO> nresourceDTOs = new ArrayList<ResourceDTO>();
	// 银行账户信息
	private List<BankDTO> bankList = new ArrayList<BankDTO>();
	private String resourceIds;

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public List<ResourceDTO> getResourceDTOs() {
		return resourceDTOs;
	}

	public List<BankDTO> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankDTO> bankList) {
		this.bankList = bankList;
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

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerEnglishName() {
		return sellerEnglishName;
	}

	public void setSellerEnglishName(String sellerEnglishName) {
		this.sellerEnglishName = sellerEnglishName;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getSellerEnglishAddress() {
		return sellerEnglishAddress;
	}

	public void setSellerEnglishAddress(String sellerEnglishAddress) {
		this.sellerEnglishAddress = sellerEnglishAddress;
	}

	public String getSellerPostcode() {
		return sellerPostcode;
	}

	public void setSellerPostcode(String sellerPostcode) {
		this.sellerPostcode = sellerPostcode;
	}

	public String getSellerTelephone() {
		return sellerTelephone;
	}

	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}

	public String getSellerFax() {
		return sellerFax;
	}

	public void setSellerFax(String sellerFax) {
		this.sellerFax = sellerFax;
	}

	public String getSellerWebsite() {
		return sellerWebsite;
	}

	public void setSellerWebsite(String sellerWebsite) {
		this.sellerWebsite = sellerWebsite;
	}

	public String getSellerSize() {
		return sellerSize;
	}

	public void setSellerSize(String sellerSize) {
		this.sellerSize = sellerSize;
	}

	public String getSellerComment() {
		return sellerComment;
	}

	public void setSellerComment(String sellerComment) {
		this.sellerComment = sellerComment;
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

	public String getSellerState() {
		return sellerState;
	}

	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
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

	public String[] getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(String[] sellerIds) {
		this.sellerIds = sellerIds;
	}

	public List<UserDTO> getSalesmanList() {
		return salesmanList;
	}

	public void setSalesmanList(List<UserDTO> salesmanList) {
		this.salesmanList = salesmanList;
	}

	public Date getCloseDateDate() {
		return closeDateDate;
	}

	public void setCloseDateDate(Date closeDateDate) {
		this.closeDateDate = closeDateDate;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

    /**
     * @return the newFatherEntityId
     */
    public String getNewFatherEntityId() {
        return newFatherEntityId;
    }

    /**
     * @param newFatherEntityId the newFatherEntityId to set
     */
    public void setNewFatherEntityId(String newFatherEntityId) {
        this.newFatherEntityId = newFatherEntityId;
    }
	
	
}
