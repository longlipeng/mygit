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

package com.allinfinance.service.issueOperation.issuerStockStream.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;


/**
 * @author administrator
 * 
 */
public class IssuerStockStreamDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private String startDate;
	private String endDate;
	
	private String ORDERID;
	private String ORDERTYPE;
	private String ORDERDATE;
	private String PRODUCTNAME;
	private String ONYMOUSSTAT;
	private String CARDTYPE;
	private String FACETYPE;
	private String FACEVALUE;
	private String ISSUERNAME1;
	private String ISSUERNAME2;
	private BigDecimal INNUM;
	private BigDecimal OUTNUM;
	private String USERNAME;
	private String STOCKDATE;
	public String getORDERID() {
		return ORDERID;
	}

	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}

	public String getORDERTYPE() {
		return ORDERTYPE;
	}

	public void setORDERTYPE(String oRDERTYPE) {
		ORDERTYPE = oRDERTYPE;
	}

	public String getORDERDATE() {
		return ORDERDATE;
	}

	public void setORDERDATE(String oRDERDATE) {
		ORDERDATE = oRDERDATE;
	}

	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}

	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}

	public String getONYMOUSSTAT() {
		return ONYMOUSSTAT;
	}

	public void setONYMOUSSTAT(String oNYMOUSSTAT) {
		ONYMOUSSTAT = oNYMOUSSTAT;
	}

	public String getCARDTYPE() {
		return CARDTYPE;
	}

	public void setCARDTYPE(String cARDTYPE) {
		CARDTYPE = cARDTYPE;
	}

	public String getFACETYPE() {
		return FACETYPE;
	}

	public void setFACETYPE(String fACETYPE) {
		FACETYPE = fACETYPE;
	}

	public String getFACEVALUE() {
		return FACEVALUE;
	}

	public void setFACEVALUE(String fACEVALUE) {
		FACEVALUE = fACEVALUE;
	}

	public String getISSUERNAME1() {
		return ISSUERNAME1;
	}

	public void setISSUERNAME1(String iSSUERNAME1) {
		ISSUERNAME1 = iSSUERNAME1;
	}

	public String getISSUERNAME2() {
		return ISSUERNAME2;
	}

	public void setISSUERNAME2(String iSSUERNAME2) {
		ISSUERNAME2 = iSSUERNAME2;
	}



	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getSTOCKDATE() {
		return STOCKDATE;
	}

	public void setSTOCKDATE(String sTOCKDATE) {
		STOCKDATE = sTOCKDATE;
	}

	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public BigDecimal getINNUM() {
		return INNUM;
	}

	public void setINNUM(BigDecimal iNNUM) {
		INNUM = iNNUM;
	}

	public BigDecimal getOUTNUM() {
		return OUTNUM;
	}

	public void setOUTNUM(BigDecimal oUTNUM) {
		OUTNUM = oUTNUM;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



}
