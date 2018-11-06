package com.allinfinance.univer.issuer.dto.cardcompany;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 制卡商DTO
 * 
 * @author xxl
 * 
 */
public class CardCompanyQueryDTO extends PageQueryDTO {

	private static final long serialVersionUID = 4452726291275450839L;

	private String cardCompanyId;
	private String entityId;
	private String cardCompanyName;
	private String cardCompanyState;
	private String isIcCard;
	private String isMsCard;

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

	public String getIsMsCard() {
		return isMsCard;
	}

	public void setIsMsCard(String isMsCard) {
		this.isMsCard = isMsCard;
	}

}
