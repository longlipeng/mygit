package com.allinfinance.service.consumer.calculateWithMerchant.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * Classname CalculateWithMerchantDTO.java
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

public class CalculateWithMerchantDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;

	/**开始日期*/
	private String startDate;	
	/**结束日期*/
	private String endDate;
	
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
	
	/**商户号*/
	private String merchantCode;
	/**商户名称*/
	private String merchantName;
	/**交易总笔数*/
	private BigDecimal txnTotalCount;
	/**交易总金额*/
	private BigDecimal txnTotalAmt;	
	/**消费总笔数*/
	private BigDecimal consumeTotalCount;
	/**消费总金额*/
	private BigDecimal consumeTotalAmt;		
	/**消费撤销总笔数*/
	private BigDecimal consumeCancelTotalCount;
	/**消费撤销总金额*/
	private BigDecimal consumeCancelTotalAmt;
	/**预授权完成总笔数*/
	private BigDecimal preAuthCompTotalCount;
	/**预授权完成总金额*/
	private BigDecimal preAuthCompTotalAmt;	
	/**预授权完成撤销总笔数*/
	private BigDecimal preAuthCompCancelTotalCount;
	/**预授权完成撤销总金额*/
	private BigDecimal preAuthCompCancelTotalAmt;	
	/**充值总笔数*/
	private BigDecimal chargeTotalCount;
	/**充值总金额*/
	private BigDecimal chargeTotalAmt;	
	/**充值撤销总笔数*/
	private BigDecimal chargeCancelTotalCount;
	/**充值撤销总金额*/
	private BigDecimal chargeCancelTotalAmt;	
	/**退货总笔数*/
	private BigDecimal refundTotalCount;
	/**退货总金额*/
	private BigDecimal refundTotalAmt;		
	/**售卡总笔数*/
	private BigDecimal cardSellingTotalCount;
	/**售卡总金额*/
	private BigDecimal cardSellingTotalAmt;		
	/**贷记调整总笔数*/
	private BigDecimal creditAdjustmentTotalCount;
	/**贷记调整总金额*/
	private BigDecimal creditAdjustmentTotalAmt;	
	/**借记调整总笔数*/
	private BigDecimal debitAdjustmentTotalCount;
	/**借记调整总金额*/
	private BigDecimal debitAdjustmentTotalAmt;

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public BigDecimal getTxnTotalCount() {
		return txnTotalCount;
	}
	public void setTxnTotalCount(BigDecimal txnTotalCount) {
		this.txnTotalCount = txnTotalCount;
	}
	public BigDecimal getTxnTotalAmt() {
		return txnTotalAmt;
	}
	public void setTxnTotalAmt(BigDecimal txnTotalAmt) {
		this.txnTotalAmt = txnTotalAmt;
	}
	public BigDecimal getConsumeTotalCount() {
		return consumeTotalCount;
	}
	public void setConsumeTotalCount(BigDecimal consumeTotalCount) {
		this.consumeTotalCount = consumeTotalCount;
	}
	public BigDecimal getConsumeTotalAmt() {
		return consumeTotalAmt;
	}
	public void setConsumeTotalAmt(BigDecimal consumeTotalAmt) {
		this.consumeTotalAmt = consumeTotalAmt;
	}
	public BigDecimal getConsumeCancelTotalCount() {
		return consumeCancelTotalCount;
	}
	public void setConsumeCancelTotalCount(BigDecimal consumeCancelTotalCount) {
		this.consumeCancelTotalCount = consumeCancelTotalCount;
	}
	public BigDecimal getConsumeCancelTotalAmt() {
		return consumeCancelTotalAmt;
	}
	public void setConsumeCancelTotalAmt(BigDecimal consumeCancelTotalAmt) {
		this.consumeCancelTotalAmt = consumeCancelTotalAmt;
	}
	public BigDecimal getPreAuthCompTotalCount() {
		return preAuthCompTotalCount;
	}
	public void setPreAuthCompTotalCount(BigDecimal preAuthCompTotalCount) {
		this.preAuthCompTotalCount = preAuthCompTotalCount;
	}
	public BigDecimal getPreAuthCompTotalAmt() {
		return preAuthCompTotalAmt;
	}
	public void setPreAuthCompTotalAmt(BigDecimal preAuthCompTotalAmt) {
		this.preAuthCompTotalAmt = preAuthCompTotalAmt;
	}
	public BigDecimal getPreAuthCompCancelTotalCount() {
		return preAuthCompCancelTotalCount;
	}
	public void setPreAuthCompCancelTotalCount(
			BigDecimal preAuthCompCancelTotalCount) {
		this.preAuthCompCancelTotalCount = preAuthCompCancelTotalCount;
	}
	public BigDecimal getPreAuthCompCancelTotalAmt() {
		return preAuthCompCancelTotalAmt;
	}
	public void setPreAuthCompCancelTotalAmt(BigDecimal preAuthCompCancelTotalAmt) {
		this.preAuthCompCancelTotalAmt = preAuthCompCancelTotalAmt;
	}
	public BigDecimal getChargeTotalCount() {
		return chargeTotalCount;
	}
	public void setChargeTotalCount(BigDecimal chargeTotalCount) {
		this.chargeTotalCount = chargeTotalCount;
	}
	public BigDecimal getChargeTotalAmt() {
		return chargeTotalAmt;
	}
	public void setChargeTotalAmt(BigDecimal chargeTotalAmt) {
		this.chargeTotalAmt = chargeTotalAmt;
	}
	public BigDecimal getChargeCancelTotalCount() {
		return chargeCancelTotalCount;
	}
	public void setChargeCancelTotalCount(BigDecimal chargeCancelTotalCount) {
		this.chargeCancelTotalCount = chargeCancelTotalCount;
	}
	public BigDecimal getChargeCancelTotalAmt() {
		return chargeCancelTotalAmt;
	}
	public void setChargeCancelTotalAmt(BigDecimal chargeCancelTotalAmt) {
		this.chargeCancelTotalAmt = chargeCancelTotalAmt;
	}
	public BigDecimal getRefundTotalCount() {
		return refundTotalCount;
	}
	public void setRefundTotalCount(BigDecimal refundTotalCount) {
		this.refundTotalCount = refundTotalCount;
	}
	public BigDecimal getRefundTotalAmt() {
		return refundTotalAmt;
	}
	public void setRefundTotalAmt(BigDecimal refundTotalAmt) {
		this.refundTotalAmt = refundTotalAmt;
	}
	public BigDecimal getCardSellingTotalCount() {
		return cardSellingTotalCount;
	}
	public void setCardSellingTotalCount(BigDecimal cardSellingTotalCount) {
		this.cardSellingTotalCount = cardSellingTotalCount;
	}
	public BigDecimal getCardSellingTotalAmt() {
		return cardSellingTotalAmt;
	}
	public void setCardSellingTotalAmt(BigDecimal cardSellingTotalAmt) {
		this.cardSellingTotalAmt = cardSellingTotalAmt;
	}
	public BigDecimal getCreditAdjustmentTotalCount() {
		return creditAdjustmentTotalCount;
	}
	public void setCreditAdjustmentTotalCount(BigDecimal creditAdjustmentTotalCount) {
		this.creditAdjustmentTotalCount = creditAdjustmentTotalCount;
	}
	public BigDecimal getCreditAdjustmentTotalAmt() {
		return creditAdjustmentTotalAmt;
	}
	public void setCreditAdjustmentTotalAmt(BigDecimal creditAdjustmentTotalAmt) {
		this.creditAdjustmentTotalAmt = creditAdjustmentTotalAmt;
	}
	public BigDecimal getDebitAdjustmentTotalCount() {
		return debitAdjustmentTotalCount;
	}
	public void setDebitAdjustmentTotalCount(BigDecimal debitAdjustmentTotalCount) {
		this.debitAdjustmentTotalCount = debitAdjustmentTotalCount;
	}
	public BigDecimal getDebitAdjustmentTotalAmt() {
		return debitAdjustmentTotalAmt;
	}
	public void setDebitAdjustmentTotalAmt(BigDecimal debitAdjustmentTotalAmt) {
		this.debitAdjustmentTotalAmt = debitAdjustmentTotalAmt;
	}

	
}
