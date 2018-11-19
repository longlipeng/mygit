package com.allinfinance.univer.issuer.dto.cardcompany;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 制卡商DTO
 * 
 * @author xxl
 * 
 */
public class CardCompanyDTO extends BaseDTO {

	private static final long serialVersionUID = 4452726291275450839L;

	private String cardCompanyId;
	private String entityId;
	private String cardCompanyName;
	private String cardCompanyAddress;
	private String cardCompanyPostcode;
	private String cardCompanyContact;
	private String cardCompanyPhone;
	private String cardCompanyKey;
	private String cardCompanyState;
	private String isIcCard;
	private String icFileFormat;
	private String isMsCard;
	private String msFileFormat;
	private String privateKeyIndex;

	public String getCardCompanyId() {
		return cardCompanyId;
	}

	public void setCardCompanyId(String cardCompanyId) {
		this.cardCompanyId = cardCompanyId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCardCompanyName() {
		return cardCompanyName;
	}

	public void setCardCompanyName(String cardCompanyName) {
		this.cardCompanyName = cardCompanyName;
	}

	public String getCardCompanyAddress() {
		return cardCompanyAddress;
	}

	public void setCardCompanyAddress(String cardCompanyAddress) {
		this.cardCompanyAddress = cardCompanyAddress;
	}

	public String getCardCompanyPostcode() {
		return cardCompanyPostcode;
	}

	public void setCardCompanyPostcode(String cardCompanyPostcode) {
		this.cardCompanyPostcode = cardCompanyPostcode;
	}

	public String getCardCompanyContact() {
		return cardCompanyContact;
	}

	public void setCardCompanyContact(String cardCompanyContact) {
		this.cardCompanyContact = cardCompanyContact;
	}

	public String getCardCompanyPhone() {
		return cardCompanyPhone;
	}

	public void setCardCompanyPhone(String cardCompanyPhone) {
		this.cardCompanyPhone = cardCompanyPhone;
	}

	public String getCardCompanyKey() {
		return cardCompanyKey;
	}

	public void setCardCompanyKey(String cardCompanyKey) {
		this.cardCompanyKey = cardCompanyKey;
	}

	public String getCardCompanyState() {
		return cardCompanyState;
	}

	public void setCardCompanyState(String cardCompanyState) {
		this.cardCompanyState = cardCompanyState;
	}

	public String getIsIcCard() {
		return isIcCard;
	}

	public void setIsIcCard(String isIcCard) {
		this.isIcCard = isIcCard;
	}

	public String getIcFileFormat() {
		return icFileFormat;
	}

	public void setIcFileFormat(String icFileFormat) {
		this.icFileFormat = icFileFormat;
	}

	public String getIsMsCard() {
		return isMsCard;
	}

	public void setIsMsCard(String isMsCard) {
		this.isMsCard = isMsCard;
	}

	public String getMsFileFormat() {
		return msFileFormat;
	}

	public void setMsFileFormat(String msFileFormat) {
		this.msFileFormat = msFileFormat;
	}

	public String getPrivateKeyIndex() {
		return privateKeyIndex;
	}

	public void setPrivateKeyIndex(String privateKeyIndex) {
		this.privateKeyIndex = privateKeyIndex;
	}

}
