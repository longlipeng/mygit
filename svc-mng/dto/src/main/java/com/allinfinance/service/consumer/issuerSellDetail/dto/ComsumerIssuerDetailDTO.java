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
package com.allinfinance.service.consumer.issuerSellDetail.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author administrator
 * 
 */
public class ComsumerIssuerDetailDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;
	
	/**开始日期*/
	private String startDate;	
	/**结束日期*/
	private String endDate;	
	/**收单机构号*/
	private String consumerId;	
	/**收单机构名称*/
	private String consumerName;
	
	//收单机构ID
	private String CONSUMER_ID;
	//收单机构名称
	private String CONSUMER_NAME;
	//商户ID
	private String MCHNT_CD;
	//商户名称
	private String MERCHANT_NAME;
	//卡号
	private String CARD_NO;
	//交易金额
	private BigDecimal TRANS_AT_SGL;
	
	//手续费
	private BigDecimal TRANS_FEE_SGL;
	//交易类型
	private String TRANS_NM;
	//终端ID
	private String TERM_ID;
	//流水号
	private String TXN_SEQ_NO;
	//交易日期
	private String SETTLE_DT;
	//交易时间
	private String TRANS_TIME;
	
	
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
	public String getCONSUMER_ID() {
		return CONSUMER_ID;
	}
	public void setCONSUMER_ID(String cONSUMERID) {
		CONSUMER_ID = cONSUMERID;
	}
	public String getCONSUMER_NAME() {
		return CONSUMER_NAME;
	}
	public void setCONSUMER_NAME(String cONSUMERNAME) {
		CONSUMER_NAME = cONSUMERNAME;
	}
	public String getMCHNT_CD() {
		return MCHNT_CD;
	}
	public void setMCHNT_CD(String mCHNTCD) {
		MCHNT_CD = mCHNTCD;
	}
	public String getMERCHANT_NAME() {
		return MERCHANT_NAME;
	}
	public void setMERCHANT_NAME(String mERCHANTNAME) {
		MERCHANT_NAME = mERCHANTNAME;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARDNO) {
		CARD_NO = cARDNO;
	}
	
	public String getTRANS_NM() {
		return TRANS_NM;
	}
	public void setTRANS_NM(String tRANSNM) {
		TRANS_NM = tRANSNM;
	}
	
	public String getTERM_ID() {
		return TERM_ID;
	}
	public void setTERM_ID(String tERMID) {
		TERM_ID = tERMID;
	}
	public String getTXN_SEQ_NO() {
		return TXN_SEQ_NO;
	}
	public BigDecimal getTRANS_AT_SGL() {
		return TRANS_AT_SGL;
	}
	public void setTRANS_AT_SGL(BigDecimal tRANSATSGL) {
		TRANS_AT_SGL = tRANSATSGL;
	}
	public void setTXN_SEQ_NO(String tXNSEQNO) {
		TXN_SEQ_NO = tXNSEQNO;
	}
	public String getSETTLE_DT() {
		return SETTLE_DT;
	}
	public void setSETTLE_DT(String sETTLEDT) {
		SETTLE_DT = sETTLEDT;
	}
	public String getTRANS_TIME() {
		return TRANS_TIME;
	}
	public void setTRANS_TIME(String tRANSTIME) {
		TRANS_TIME = tRANSTIME;
	}
	
	public BigDecimal getTRANS_FEE_SGL() {
		return TRANS_FEE_SGL;
	}
	public void setTRANS_FEE_SGL(BigDecimal tRANSFEESGL) {
		TRANS_FEE_SGL = tRANSFEESGL;
	}
	

	
}
