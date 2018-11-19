package com.allinfinance.univer.consumer.pos.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class posParameterValueDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String UserId;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	private String cardBin;
	
	
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	

}
