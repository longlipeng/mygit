package com.huateng.hstserver.model;

import java.io.Serializable;
import java.util.List;

public class CardInfoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    //卡片余额
    private String balance;
    //网上支付限额
    private String limitPayMoney;
    //账户名称
    private String serviceName;
    //账户id
    private String serviceId;
    //网上支付状态
    private String epayIn;
    
    private List<CardInfoDTO> cardInfoDTO;
    
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getLimitPayMoney() {
		return limitPayMoney;
	}
	public void setLimitPayMoney(String limitPayMoney) {
		this.limitPayMoney = limitPayMoney;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public List<CardInfoDTO> getCardInfoDTO() {
		return cardInfoDTO;
	}
	public void setCardInfoDTO(List<CardInfoDTO> cardInfoDTO) {
		this.cardInfoDTO = cardInfoDTO;
	}
	public String getEpayIn() {
		return epayIn;
	}
	public void setEpayIn(String epayIn) {
		this.epayIn = epayIn;
	}
	
}
