package com.allinfinance.univer.entity.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 快递点信息DTO
 * @author dawn
 *
 */
public class DeliveryPointDTO extends BaseDTO {

    private static final long serialVersionUID = -4614674408143844468L;

    private String deliveryId;
	
	private String entityId;
	
	private String deliveryName;
	
	private String deliveryAddress;
	
	private String deliveryPostcode;
	
	private String deliveryState;
	
	private String deliveryComment;
	
	private String defaultFlag;
	private String createUser;
	private String createTime;
	private String modifyUser;
	private String modifyTime;
	private String dataState;
	
	private String deliveryPointId;

    private List<DeliveryRecipientDTO> recipientList;

    private List<DeliveryPointDTO>  deliveryPointDTOs;
    
    

	public String getDeliveryPointId() {
		return deliveryPointId;
	}

	public void setDeliveryPointId(String deliveryPointId) {
		this.deliveryPointId = deliveryPointId;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryPostcode() {
		return deliveryPostcode;
	}

	public void setDeliveryPostcode(String deliveryPostcode) {
		this.deliveryPostcode = deliveryPostcode;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getDeliveryComment() {
		return deliveryComment;
	}

	public void setDeliveryComment(String deliveryComment) {
		this.deliveryComment = deliveryComment;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public List<DeliveryRecipientDTO> getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(List<DeliveryRecipientDTO> recipientList) {
		this.recipientList = recipientList;
	}

	public List<DeliveryPointDTO> getDeliveryPointDTOs() {
		return deliveryPointDTOs;
	}

	public void setDeliveryPointDTOs(List<DeliveryPointDTO> deliveryPointDTOs) {
		this.deliveryPointDTOs = deliveryPointDTOs;
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
