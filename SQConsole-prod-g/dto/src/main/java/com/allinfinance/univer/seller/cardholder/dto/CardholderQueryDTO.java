package com.allinfinance.univer.seller.cardholder.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 
 * @author dawn
 * 
 */
public class CardholderQueryDTO extends PageQueryDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cardholderId;

	private String memberId;

	private String cardNo;

	private String entityId;

	private String customerName;
	
	private String lastName;

	private String firstName;

	private String idType;

	private String idNo;

	private String cardholderMobile;

	private String cardholderEmail;

	private String externalId;

	private String departmentId;

	private String cardholderBirthday;

	private String cardholderGender;

	private String cardholderSalutation;

	private String cardholderFunction;

	private String cardholderSegment;

	private String cardholderState;

	private String closeDate;

	private String cardholderComment;
	
	private String checkState;
	
	private String riskGrade;//风险等级
	
	private String isblacklist;//黑名单标识
	
	private String uniqueId;//统一客户Id
	
	public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /***
	 * 订单ID
	 */
	private String orderId;
		
	
	
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

    public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getCardholderId() {
		return cardholderId;
	}

	public void setCardholderId(String cardholderId) {
		this.cardholderId = cardholderId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardholderMobile() {
		return cardholderMobile;
	}

	public void setCardholderMobile(String cardholderMobile) {
		this.cardholderMobile = cardholderMobile;
	}

	public String getCardholderEmail() {
		return cardholderEmail;
	}

	public void setCardholderEmail(String cardholderEmail) {
		this.cardholderEmail = cardholderEmail;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCardholderBirthday() {
		return cardholderBirthday;
	}

	public void setCardholderBirthday(String cardholderBirthday) {
		this.cardholderBirthday = cardholderBirthday;
	}

	public String getCardholderGender() {
		return cardholderGender;
	}

	public void setCardholderGender(String cardholderGender) {
		this.cardholderGender = cardholderGender;
	}

	public String getCardholderSalutation() {
		return cardholderSalutation;
	}

	public void setCardholderSalutation(String cardholderSalutation) {
		this.cardholderSalutation = cardholderSalutation;
	}

	public String getCardholderFunction() {
		return cardholderFunction;
	}

	public void setCardholderFunction(String cardholderFunction) {
		this.cardholderFunction = cardholderFunction;
	}

	public String getCardholderSegment() {
		return cardholderSegment;
	}

	public void setCardholderSegment(String cardholderSegment) {
		this.cardholderSegment = cardholderSegment;
	}

	public String getCardholderState() {
		return cardholderState;
	}

	public void setCardholderState(String cardholderState) {
		this.cardholderState = cardholderState;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCardholderComment() {
		return cardholderComment;
	}

	public void setCardholderComment(String cardholderComment) {
		this.cardholderComment = cardholderComment;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
