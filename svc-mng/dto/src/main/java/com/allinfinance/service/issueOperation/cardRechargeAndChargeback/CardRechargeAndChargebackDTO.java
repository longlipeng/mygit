package com.allinfinance.service.issueOperation.cardRechargeAndChargeback;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * 预付卡充值充退报表DTO
 */
public class CardRechargeAndChargebackDTO extends IreportDTO {
	private static final long serialVersionUID = -3739044941906673826L;

	private String startDate;

	private String endDate;

	private String issueName;

	private String saleMan;

	private String orderType;

	private String orderDate;

	private String orderId;

	private String cardNo;

	private BigDecimal totalPrice;

	private BigDecimal cardAmount;

	private BigDecimal additionalFee;

	private BigDecimal cash;

	private BigDecimal telegraph;

	private BigDecimal cheque;

	private BigDecimal accountBalance;

	private BigDecimal bankCard;
	
	private String fatherEntityId;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getAdditionalFee() {
		return additionalFee;
	}

	public void setAdditionalFee(BigDecimal additionalFee) {
		this.additionalFee = additionalFee;
	}

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getTelegraph() {
		return telegraph;
	}

	public void setTelegraph(BigDecimal telegraph) {
		this.telegraph = telegraph;
	}

	public BigDecimal getCheque() {
		return cheque;
	}

	public void setCheque(BigDecimal cheque) {
		this.cheque = cheque;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getBankCard() {
		return bankCard;
	}

	public void setBankCard(BigDecimal bankCard) {
		this.bankCard = bankCard;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

    public String getFatherEntityId() {
        return fatherEntityId;
    }

    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }
	

}
