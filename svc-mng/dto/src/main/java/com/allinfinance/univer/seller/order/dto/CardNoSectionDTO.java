package com.allinfinance.univer.seller.order.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 卡号分段信息DTO
 * */
public class CardNoSectionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7071018820870685688L;
	
	/*面额*/
	private String faceValue;
	
	/*张数*/
	private String cardQuantity;

	/*卡号分段列表*/
	private List<String> cardNoSection;
	
	private String validPeriod;

	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

	public String getCardQuantity() {
		return cardQuantity;
	}

	public void setCardQuantity(String cardQuantity) {
		this.cardQuantity = cardQuantity;
	}

	public List<String> getCardNoSection() {
		return cardNoSection;
	}

	public void setCardNoSection(List<String> cardNoSection) {
		this.cardNoSection = cardNoSection;
	}
}
