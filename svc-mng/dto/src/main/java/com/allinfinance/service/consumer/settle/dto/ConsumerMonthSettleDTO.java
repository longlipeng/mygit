/**
 * Classname ConsumerMonthSettleDTO.java
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
 *		yaoxin		2012-11-13
 * =============================================================================
 */

package com.allinfinance.service.consumer.settle.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author yaoxin
 *
 */
public class ConsumerMonthSettleDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String sellerId;
	private String sellerName;
	private String startDate;
	private String endDate;
	
	
	private String MERCHANT_ID;
	private String 	MERCHANT_NAME;
	/*期初未结算金额*/
	private BigDecimal BEGINTERM_AMT;
	/*本期生成结算金额*/
	private BigDecimal THISTERM_SETTLE_AMT;
	/*本期已取消结算金额*/
	private BigDecimal THISTERM_CANCEL_AMT;
	/*本期可结算金额*/
	private BigDecimal THISTERM_REAL_AMT;
	/*本期已支付结算金额*/
	private BigDecimal THISTERM_PAY_AMT;
	/*期末未结算金额*/
	private BigDecimal ENDTERM_NOSETTLE_AMT;
	/*未支付交易金额*/
	private BigDecimal NOPAY_TRADE_AMT;
	/*未支付结算金额*/
	private BigDecimal NOPAY_SETTLE_AMT;
	/*未收取手续费*/
	private BigDecimal NOPAY_FEE_AMT;
	
	
	private String  MERCHANT_CODE;
	public String getMERCHANT_CODE() {
		return MERCHANT_CODE;
	}
	public void setMERCHANT_CODE(String mERCHANTCODE) {
		MERCHANT_CODE = mERCHANTCODE;
	}
	public String getMERCHANT_NAME() {
		return MERCHANT_NAME;
	}
	public void setMERCHANT_NAME(String mERCHANTNAME) {
		MERCHANT_NAME = mERCHANTNAME;
	}
	public BigDecimal getBEGINTERM_AMT() {
		return BEGINTERM_AMT;
	}
	public void setBEGINTERM_AMT(BigDecimal bEGINTERMAMT) {
		BEGINTERM_AMT = bEGINTERMAMT;
	}
	public BigDecimal getTHISTERM_SETTLE_AMT() {
		return THISTERM_SETTLE_AMT;
	}
	public void setTHISTERM_SETTLE_AMT(BigDecimal tHISTERMSETTLEAMT) {
		THISTERM_SETTLE_AMT = tHISTERMSETTLEAMT;
	}
	public BigDecimal getTHISTERM_CANCEL_AMT() {
		return THISTERM_CANCEL_AMT;
	}
	public void setTHISTERM_CANCEL_AMT(BigDecimal tHISTERMCANCELAMT) {
		THISTERM_CANCEL_AMT = tHISTERMCANCELAMT;
	}
	public BigDecimal getTHISTERM_REAL_AMT() {
		return THISTERM_REAL_AMT;
	}
	public void setTHISTERM_REAL_AMT(BigDecimal tHISTERMREALAMT) {
		THISTERM_REAL_AMT = tHISTERMREALAMT;
	}
	public BigDecimal getTHISTERM_PAY_AMT() {
		return THISTERM_PAY_AMT;
	}
	public void setTHISTERM_PAY_AMT(BigDecimal tHISTERMPAYAMT) {
		THISTERM_PAY_AMT = tHISTERMPAYAMT;
	}
	public BigDecimal getENDTERM_NOSETTLE_AMT() {
		return ENDTERM_NOSETTLE_AMT;
	}
	public void setENDTERM_NOSETTLE_AMT(BigDecimal eNDTERMNOSETTLEAMT) {
		ENDTERM_NOSETTLE_AMT = eNDTERMNOSETTLEAMT;
	}
	public BigDecimal getNOPAY_TRADE_AMT() {
		return NOPAY_TRADE_AMT;
	}
	public void setNOPAY_TRADE_AMT(BigDecimal nOPAYTRADEAMT) {
		NOPAY_TRADE_AMT = nOPAYTRADEAMT;
	}
	public BigDecimal getNOPAY_SETTLE_AMT() {
		return NOPAY_SETTLE_AMT;
	}
	public void setNOPAY_SETTLE_AMT(BigDecimal nOPAYSETTLEAMT) {
		NOPAY_SETTLE_AMT = nOPAYSETTLEAMT;
	}
	public BigDecimal getNOPAY_FEE_AMT() {
		return NOPAY_FEE_AMT;
	}
	public void setNOPAY_FEE_AMT(BigDecimal nOPAYFEEAMT) {
		NOPAY_FEE_AMT = nOPAYFEEAMT;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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

	
}
