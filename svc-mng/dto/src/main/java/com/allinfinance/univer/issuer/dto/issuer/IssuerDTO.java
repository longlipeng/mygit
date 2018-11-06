/**
 * 
 */
package com.allinfinance.univer.issuer.dto.issuer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardserialnumber.CardSerialNumberDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;

/**
 * @author sff 发行机构DTO
 */
public class IssuerDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String entityId;

	private String fatherEntityId;

	private String issuerName;

	private String issuerCode;

	private String issuerEnglishName;

	private String issuerAddress;

	private String issuerEnglishAddress;

	private String issuerPostcode;

	private String issuerTelephone;

	private String issuerFax;

	private String issuerComment;

	private String createUser;

	private String createTime;

	private String modifyUser;

	private String modifyTime;

	private String dataState;

	private List<IssuerDTO> issuerDTO;

	private List<DepartmentDTO> departmentDTO;

	private List<InvoiceAddressDTO> invoiceAddressDTO;

	private List<InvoiceCompanyDTO> invoiceCompanyDTO;

	private List<ContactDTO> contractDTO;

	private List<DeliveryPointDTO> deliveryPointDTO;
	private List<BankDTO> bankDTOList;
	private List<CardSerialNumberDTO> cardSerialNumberDTOList;
	private List<EntitySystemParameterDTO> entitySystemParameterDTOs;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String issuerIdPin;
	private String resourceIds;
	private List<ResourceDTO> resourceDTOs = new ArrayList<ResourceDTO>();
	private List<ResourceDTO> nresourceDTOs = new ArrayList<ResourceDTO>();

	/**
	 * 订单次日自动激活：0是不激活，1是激活
	 */
	private String autoAct;

	public List<ResourceDTO> getNresourceDTOs() {
		return nresourceDTOs;
	}

	public List<BankDTO> getBankDTOList() {
		return bankDTOList;
	}

	public void setBankDTOList(List<BankDTO> bankDTOList) {
		this.bankDTOList = bankDTOList;
	}

	public void setNresourceDTOs(List<ResourceDTO> nresourceDTOs) {
		this.nresourceDTOs = nresourceDTOs;
	}

	public List<ResourceDTO> getResourceDTOs() {
		return resourceDTOs;
	}

	public void setResourceDTOs(List<ResourceDTO> resourceDTOs) {
		this.resourceDTOs = resourceDTOs;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public List<EntitySystemParameterDTO> getEntitySystemParameterDTOs() {
		return entitySystemParameterDTOs;
	}

	public void setEntitySystemParameterDTOs(
			List<EntitySystemParameterDTO> entitySystemParameterDTOs) {
		this.entitySystemParameterDTOs = entitySystemParameterDTOs;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public List<IssuerDTO> getIssuerDTO() {
		return issuerDTO;
	}

	public void setIssuerDTO(List<IssuerDTO> issuerDTO) {
		this.issuerDTO = issuerDTO;
	}

	public List<DepartmentDTO> getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(List<DepartmentDTO> departmentDTO) {
		this.departmentDTO = departmentDTO;
	}

	public List<InvoiceAddressDTO> getInvoiceAddressDTO() {
		return invoiceAddressDTO;
	}

	public void setInvoiceAddressDTO(List<InvoiceAddressDTO> invoiceAddressDTO) {
		this.invoiceAddressDTO = invoiceAddressDTO;
	}

	public List<InvoiceCompanyDTO> getInvoiceCompanyDTO() {
		return invoiceCompanyDTO;
	}

	public void setInvoiceCompanyDTO(List<InvoiceCompanyDTO> invoiceCompanyDTO) {
		this.invoiceCompanyDTO = invoiceCompanyDTO;
	}

	public List<ContactDTO> getContractDTO() {
		return contractDTO;
	}

	public void setContractDTO(List<ContactDTO> contractDTO) {
		this.contractDTO = contractDTO;
	}

	public List<DeliveryPointDTO> getDeliveryPointDTO() {
		return deliveryPointDTO;
	}

	public void setDeliveryPointDTO(List<DeliveryPointDTO> deliveryPointDTO) {
		this.deliveryPointDTO = deliveryPointDTO;
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

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getIssuerCode() {
		return issuerCode;
	}

	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}

	public String getIssuerEnglishName() {
		return issuerEnglishName;
	}

	public void setIssuerEnglishName(String issuerEnglishName) {
		this.issuerEnglishName = issuerEnglishName;
	}

	public String getIssuerAddress() {
		return issuerAddress;
	}

	public void setIssuerAddress(String issuerAddress) {
		this.issuerAddress = issuerAddress;
	}

	public String getIssuerEnglishAddress() {
		return issuerEnglishAddress;
	}

	public void setIssuerEnglishAddress(String issuerEnglishAddress) {
		this.issuerEnglishAddress = issuerEnglishAddress;
	}

	public String getIssuerPostcode() {
		return issuerPostcode;
	}

	public void setIssuerPostcode(String issuerPostcode) {
		this.issuerPostcode = issuerPostcode;
	}

	public String getIssuerTelephone() {
		return issuerTelephone;
	}

	public void setIssuerTelephone(String issuerTelephone) {
		this.issuerTelephone = issuerTelephone;
	}

	public String getIssuerFax() {
		return issuerFax;
	}

	public void setIssuerFax(String issuerFax) {
		this.issuerFax = issuerFax;
	}

	public String getIssuerComment() {
		return issuerComment;
	}

	public void setIssuerComment(String issuerComment) {
		this.issuerComment = issuerComment;
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

	public List<CardSerialNumberDTO> getCardSerialNumberDTOList() {
		return cardSerialNumberDTOList;
	}

	public void setCardSerialNumberDTOList(
			List<CardSerialNumberDTO> cardSerialNumberDTOList) {
		this.cardSerialNumberDTOList = cardSerialNumberDTOList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIssuerIdPin() {
		return issuerIdPin;
	}

	public void setIssuerIdPin(String issuerIdPin) {
		this.issuerIdPin = issuerIdPin;
	}

	public String getAutoAct() {
		return autoAct;
	}

	public void setAutoAct(String autoAct) {
		this.autoAct = autoAct;
	}
}
