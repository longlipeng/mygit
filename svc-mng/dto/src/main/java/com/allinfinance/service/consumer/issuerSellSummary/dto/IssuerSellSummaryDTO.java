/**
 * Classname IssuerSellSummaryDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		administrator		2012-10-25
 * =============================================================================
 */
package com.allinfinance.service.consumer.issuerSellSummary.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author administrator
 * 
 */
public class IssuerSellSummaryDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;
	
	/**开始日期*/
	private String startDate;	
	/**结束日期*/
	private String endDate;	
	/**收单机构号*/
	private String consumerId;	
	/**收单机构名称*/
	private String consumerName;
	/**交易金额*/
	private BigDecimal transSum;
	/**手续费金额*/
	private BigDecimal transFee;	
	/**结算金额*/
	private BigDecimal transIn;
	/**交易笔数*/
	private BigDecimal tranNum;
	

	public BigDecimal getTransSum() {
		return transSum;
	}
	public void setTransSum(BigDecimal transSum) {
		this.transSum = transSum;
	}
	public BigDecimal getTransFee() {
		return transFee;
	}
	public void setTransFee(BigDecimal transFee) {
		this.transFee = transFee;
	}
	public BigDecimal getTransIn() {
		return transIn;
	}
	public void setTransIn(BigDecimal transIn) {
		this.transIn = transIn;
	}
	public BigDecimal getTranNum() {
		return tranNum;
	}
	public void setTranNum(BigDecimal tranNum) {
		this.tranNum = tranNum;
	}
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
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
}
