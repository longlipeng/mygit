/**
 * Classname CalculateWithMerchantDetailDTO.java
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
 *		yaoxin		2012-11-21
 * =============================================================================
 */

package com.allinfinance.service.consumer.calculateWithMerchantDetail.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author yaoxin
 *
 */
public class CalculateWithMerchantDetailDTO extends IreportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String issuerId;
	private String issuerName;
	private String merchantCode;
	private String merchantName;
	
	private String startDate;
	private String endDate;
	
	//清算日期
	private Date SETTLEDT;
	//交易系统流水号
	private String DMSRELATEDKEY;
	//门店号
	private String SHOPID;
	//门店名称
	private String SHOPNAME;
	//终端号
	private String TERMID;
	//卡号
	private String PRIACCTNO;
	//交易类型
	private String TRANSNAME;
	//交易金额
	private String TRANSAMT;
	//手续费
	private String DISCAMT;
	//交易时间
	private Timestamp REC_CRT_TS;
	
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

	public Date getSETTLEDT() {
		return SETTLEDT;
	}
	public void setSETTLEDT(Date sETTLEDT) {
		SETTLEDT = sETTLEDT;
	}
	public String getDMSRELATEDKEY() {
		return DMSRELATEDKEY;
	}
	public void setDMSRELATEDKEY(String dMSRELATEDKEY) {
		DMSRELATEDKEY = dMSRELATEDKEY;
	}
	public String getSHOPID() {
		return SHOPID;
	}
	public void setSHOPID(String sHOPID) {
		SHOPID = sHOPID;
	}
	public String getSHOPNAME() {
		return SHOPNAME;
	}
	public void setSHOPNAME(String sHOPNAME) {
		SHOPNAME = sHOPNAME;
	}
	public String getTERMID() {
		return TERMID;
	}
	public void setTERMID(String tERMID) {
		TERMID = tERMID;
	}
	public String getPRIACCTNO() {
		return PRIACCTNO;
	}
	public void setPRIACCTNO(String pRIACCTNO) {
		PRIACCTNO = pRIACCTNO;
	}
	public String getTRANSNAME() {
		return TRANSNAME;
	}
	public void setTRANSNAME(String tRANSNAME) {
		TRANSNAME = tRANSNAME;
	}
	public String getTRANSAMT() {
		return TRANSAMT;
	}
	public void setTRANSAMT(String tRANSAMT) {
		TRANSAMT = tRANSAMT;
	}
	public String getDISCAMT() {
		return DISCAMT;
	}
	public void setDISCAMT(String dISCAMT) {
		DISCAMT = dISCAMT;
	}
	public Timestamp getREC_CRT_TS() {
		return REC_CRT_TS;
	}
	public void setREC_CRT_TS(Timestamp rECCRTTS) {
		REC_CRT_TS = rECCRTTS;
	}

}
