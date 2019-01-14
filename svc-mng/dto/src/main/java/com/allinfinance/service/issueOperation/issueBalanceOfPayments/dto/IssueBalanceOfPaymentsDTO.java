package com.allinfinance.service.issueOperation.issueBalanceOfPayments.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

public class IssueBalanceOfPaymentsDTO extends IreportDTO {
	private String startDate;
	private String endDate;
	
	// 营销机构编号
	private String issuerId;
	// 营销机构名称
	private String issuerName;
	// 订单付款审核人
	private String orderPayAuditor;
	// 现金
	private BigDecimal cash;
	// 电汇
	private BigDecimal telegraph;
	// 支票
	private BigDecimal cheque;
	// 账上款
	private BigDecimal accountBalance;
	// 银行卡
	private BigDecimal bankCard;
	// 合计
	private BigDecimal total;
	//机构号
	private String entityId;
	//机构名称
	private String sellerName;

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

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getOrderPayAuditor() {
		return orderPayAuditor;
	}

	public void setOrderPayAuditor(String orderPayAuditor) {
		this.orderPayAuditor = orderPayAuditor;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

    /**
     * @return the entityId
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName the sellerName to set
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
	
}
