package com.allinfinance.univer.entity.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 快递点联系人DTO
 * 
 * @author dawn
 * 
 */
public class DeliveryRecipientDTO extends BaseDTO {

	private static final long serialVersionUID = 1379605312591472861L;

	private String deliveryContactId;
	private String deliveryPointId;
	private String deliveryContact;
	private String firstName;
	private String contactPhone;
	private String defaultFlag;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;

	private List<DeliveryRecipientDTO> deliveryRecipientDTO;

	public String getDeliveryContactId() {
		return deliveryContactId;
	}

	public void setDeliveryContactId(String deliveryContactId) {
		this.deliveryContactId = deliveryContactId;
	}

	public String getDeliveryPointId() {
		return deliveryPointId;
	}

	public void setDeliveryPointId(String deliveryPointId) {
		this.deliveryPointId = deliveryPointId;
	}

	public String getDeliveryContact() {
		return deliveryContact;
	}

	public void setDeliveryContact(String deliveryContact) {
		this.deliveryContact = deliveryContact;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public List<DeliveryRecipientDTO> getDeliveryRecipientDTO() {
		return deliveryRecipientDTO;
	}

	public void setDeliveryRecipientDTO(
			List<DeliveryRecipientDTO> deliveryRecipientDTO) {
		this.deliveryRecipientDTO = deliveryRecipientDTO;
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

}
