/**
 * Classname sellerSellSummaryDTO.java
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
 *		administrator		2012-10-22
 * =============================================================================
 */

package com.allinfinance.service.issueOperation.issuerCardSum.dto;

import java.math.BigDecimal;
import java.sql.Date;

import com.allinfinance.univer.report.IreportDTO;


/**
 * @author administrator
 * 
 */
public class IssuerCardSumDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String queryType;
	
	private String ISSUER_ID;
	private String ISSUER_NAME;
	private Date DATA_MOUNTH;
	private Date DATA_DATE;
	private BigDecimal TRANS_NUM;
	private BigDecimal TRANS_AMT;
	private BigDecimal RECHARGE_NUM;
	private BigDecimal RECHARGE_AMT;
	private BigDecimal PREAUTH_NUM;
	private BigDecimal PREAUTH_AMT;
	private BigDecimal PREAUTH_CANCEL_NUM;
	private BigDecimal PREAUTH_CANCEL_AMT;
	private BigDecimal REFUND_NUM;
	private BigDecimal REFUND_AMT;
	private BigDecimal ACTIVE_CARD_NUM;
	private BigDecimal DEACTIVE_CARD_NUM;
	private BigDecimal BALANCE_AMT;
	private BigDecimal VALID_CARD_NUM;
	
	
	


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

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getISSUER_ID() {
		return ISSUER_ID;
	}

	public void setISSUER_ID(String iSSUERID) {
		ISSUER_ID = iSSUERID;
	}

	public String getISSUER_NAME() {
		return ISSUER_NAME;
	}

	public void setISSUER_NAME(String iSSUERNAME) {
		ISSUER_NAME = iSSUERNAME;
	}



	public Date getDATA_MOUNTH() {
		return DATA_MOUNTH;
	}

	public void setDATA_MOUNTH(Date dATAMOUNTH) {
		DATA_MOUNTH = dATAMOUNTH;
	}

	public Date getDATA_DATE() {
		return DATA_DATE;
	}

	public void setDATA_DATE(Date dATADATE) {
		DATA_DATE = dATADATE;
	}

	public BigDecimal getTRANS_AMT() {
		return TRANS_AMT;
	}

	public void setTRANS_AMT(BigDecimal tRANSAMT) {
		TRANS_AMT = tRANSAMT;
	}

	public BigDecimal getRECHARGE_AMT() {
		return RECHARGE_AMT;
	}

	public void setRECHARGE_AMT(BigDecimal rECHARGEAMT) {
		RECHARGE_AMT = rECHARGEAMT;
	}

	public BigDecimal getPREAUTH_AMT() {
		return PREAUTH_AMT;
	}

	public void setPREAUTH_AMT(BigDecimal pREAUTHAMT) {
		PREAUTH_AMT = pREAUTHAMT;
	}

	public BigDecimal getPREAUTH_CANCEL_AMT() {
		return PREAUTH_CANCEL_AMT;
	}

	public void setPREAUTH_CANCEL_AMT(BigDecimal pREAUTHCANCELAMT) {
		PREAUTH_CANCEL_AMT = pREAUTHCANCELAMT;
	}

	public BigDecimal getREFUND_AMT() {
		return REFUND_AMT;
	}

	public void setREFUND_AMT(BigDecimal rEFUNDAMT) {
		REFUND_AMT = rEFUNDAMT;
	}

	public BigDecimal getBALANCE_AMT() {
		return BALANCE_AMT;
	}

	public void setBALANCE_AMT(BigDecimal bALANCEAMT) {
		BALANCE_AMT = bALANCEAMT;
	}

	public BigDecimal getTRANS_NUM() {
		return TRANS_NUM;
	}

	public void setTRANS_NUM(BigDecimal tRANSNUM) {
		TRANS_NUM = tRANSNUM;
	}

	public BigDecimal getRECHARGE_NUM() {
		return RECHARGE_NUM;
	}

	public void setRECHARGE_NUM(BigDecimal rECHARGENUM) {
		RECHARGE_NUM = rECHARGENUM;
	}

	public BigDecimal getPREAUTH_NUM() {
		return PREAUTH_NUM;
	}

	public void setPREAUTH_NUM(BigDecimal pREAUTHNUM) {
		PREAUTH_NUM = pREAUTHNUM;
	}

	public BigDecimal getPREAUTH_CANCEL_NUM() {
		return PREAUTH_CANCEL_NUM;
	}

	public void setPREAUTH_CANCEL_NUM(BigDecimal pREAUTHCANCELNUM) {
		PREAUTH_CANCEL_NUM = pREAUTHCANCELNUM;
	}

	public BigDecimal getREFUND_NUM() {
		return REFUND_NUM;
	}

	public void setREFUND_NUM(BigDecimal rEFUNDNUM) {
		REFUND_NUM = rEFUNDNUM;
	}

	public BigDecimal getACTIVE_CARD_NUM() {
		return ACTIVE_CARD_NUM;
	}

	public void setACTIVE_CARD_NUM(BigDecimal aCTIVECARDNUM) {
		ACTIVE_CARD_NUM = aCTIVECARDNUM;
	}

	public BigDecimal getDEACTIVE_CARD_NUM() {
		return DEACTIVE_CARD_NUM;
	}

	public void setDEACTIVE_CARD_NUM(BigDecimal dEACTIVECARDNUM) {
		DEACTIVE_CARD_NUM = dEACTIVECARDNUM;
	}

	public BigDecimal getVALID_CARD_NUM() {
		return VALID_CARD_NUM;
	}

	public void setVALID_CARD_NUM(BigDecimal vALIDCARDNUM) {
		VALID_CARD_NUM = vALIDCARDNUM;
	}

	

}
