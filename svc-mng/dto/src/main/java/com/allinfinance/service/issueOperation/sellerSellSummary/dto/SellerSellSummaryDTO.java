/**
 * Classname sellerSellSummaryDTO.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co.; Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co.; Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wpf		2012-10-22
 * =============================================================================
 */

package com.allinfinance.service.issueOperation.sellerSellSummary.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class SellerSellSummaryDTO extends IreportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	/*营销结构ID*/
	private String SELLERID;
	/*营销结构名称*/
	private String SELLERNAME;
	//充值总金额
	private BigDecimal TALCREDIT;
	//充值笔数
	private BigDecimal CREDITNUM;
	//售卡总金额
	private BigDecimal TALAMT;
	//售卡总数
	private BigDecimal TALNUM;

	private BigDecimal TALLAMT;
	//充值卡数量
	private BigDecimal CREDITCARDNUM;
	//服务费
	private BigDecimal OTHERFEE;
	//借记调整金额
	private BigDecimal DEBITAMT;
	//借记调整笔数
	private BigDecimal DEBITNUM;
	//赎回金额
	private BigDecimal REDEMPTIONAMT;
	//赎回笔数
	private BigDecimal REDEMPTIONNUM;

	public BigDecimal getREDEMPTIONAMT() {
		return REDEMPTIONAMT;
	}

	public void setREDEMPTIONAMT(BigDecimal rEDEMPTIONAMT) {
		REDEMPTIONAMT = rEDEMPTIONAMT;
	}

	public BigDecimal getREDEMPTIONNUM() {
		return REDEMPTIONNUM;
	}

	public void setREDEMPTIONNUM(BigDecimal rEDEMPTIONNUM) {
		REDEMPTIONNUM = rEDEMPTIONNUM;
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

	public String getSELLERID() {
		return SELLERID;
	}

	public void setSELLERID(String sELLERID) {
		SELLERID = sELLERID;
	}

	public String getSELLERNAME() {
		return SELLERNAME;
	}

	public void setSELLERNAME(String sELLERNAME) {
		SELLERNAME = sELLERNAME;
	}

	public BigDecimal getTALCREDIT() {
		return TALCREDIT;
	}

	public void setTALCREDIT(BigDecimal tALCREDIT) {
		TALCREDIT = tALCREDIT;
	}

	public BigDecimal getCREDITNUM() {
		return CREDITNUM;
	}

	public void setCREDITNUM(BigDecimal cREDITNUM) {
		CREDITNUM = cREDITNUM;
	}

	public BigDecimal getTALAMT() {
		return TALAMT;
	}

	public void setTALAMT(BigDecimal tALAMT) {
		TALAMT = tALAMT;
	}

	public BigDecimal getTALNUM() {
		return TALNUM;
	}

	public void setTALNUM(BigDecimal tALNUM) {
		TALNUM = tALNUM;
	}

	public BigDecimal getTALLAMT() {
		return TALLAMT;
	}

	public void setTALLAMT(BigDecimal tALLAMT) {
		TALLAMT = tALLAMT;
	}

	public BigDecimal getCREDITCARDNUM() {
		return CREDITCARDNUM;
	}

	public void setCREDITCARDNUM(BigDecimal cREDITCARDNUM) {
		CREDITCARDNUM = cREDITCARDNUM;
	}

	public BigDecimal getOTHERFEE() {
		return OTHERFEE;
	}

	public void setOTHERFEE(BigDecimal oTHERFEE) {
		OTHERFEE = oTHERFEE;
	}

	public BigDecimal getDEBITAMT() {
		return DEBITAMT;
	}

	public void setDEBITAMT(BigDecimal dEBITAMT) {
		DEBITAMT = dEBITAMT;
	}

	public BigDecimal getDEBITNUM() {
		return DEBITNUM;
	}

	public void setDEBITNUM(BigDecimal dEBITNUM) {
		DEBITNUM = dEBITNUM;
	}


	
}
