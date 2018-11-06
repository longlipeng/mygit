package com.allinfinance.univer.entity.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 发票地址信息DTO
 * 
 * @author dawn
 * 
 */
public class InvoiceAddressDTO extends BaseDTO {

	private static final long serialVersionUID = -7805176369922665421L;

	private String invoiceAddressId;
	private String entityId;
	private String invoiceRecipient;
	private String addressPostcode;
	private String invoiceAddress;
	private String deliveryOption;
	private String defaultFlag;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

	private List<InvoiceAddressDTO> invoiceAddressDTO;

	public List<InvoiceAddressDTO> getInvoiceAddressDTO() {
		return invoiceAddressDTO;
	}

	public String getInvoiceAddressId() {
		return invoiceAddressId;
	}

	public void setInvoiceAddressId(String invoiceAddressId) {
		this.invoiceAddressId = invoiceAddressId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getInvoiceRecipient() {
		return invoiceRecipient;
	}

	public void setInvoiceRecipient(String invoiceRecipient) {
		this.invoiceRecipient = invoiceRecipient;
	}

	public String getAddressPostcode() {
		return addressPostcode;
	}

	public void setAddressPostcode(String addressPostcode) {
		this.addressPostcode = addressPostcode;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
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

	public void setInvoiceAddressDTO(List<InvoiceAddressDTO> invoiceAddressDTO) {
		this.invoiceAddressDTO = invoiceAddressDTO;
	}

}
