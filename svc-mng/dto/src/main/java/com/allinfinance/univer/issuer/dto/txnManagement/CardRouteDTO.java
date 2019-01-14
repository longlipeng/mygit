package com.allinfinance.univer.issuer.dto.txnManagement;

import java.io.Serializable;
import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class CardRouteDTO extends PageQueryDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String minBin;
	
	private String maxBin;
	
	private String cardAttr;
	
	private String rcvInsIdCd;
	
	private String issInsIdCd;
	
	private String rsvData;

	private String binVal;

	private String recUpdUsrId;

	private Date recUpdTs;

	private Date recCrtTs;
	
	private String userName;
	
	private String insNm;
	
	private String length;

	public String getMinBin() {
		return minBin;
	}

	public void setMinBin(String minBin) {
		this.minBin = minBin;
	}

	public String getMaxBin() {
		return maxBin;
	}

	public void setMaxBin(String maxBin) {
		this.maxBin = maxBin;
	}

	public String getCardAttr() {
		return cardAttr;
	}

	public void setCardAttr(String cardAttr) {
		this.cardAttr = cardAttr;
	}

	public String getRcvInsIdCd() {
		return rcvInsIdCd;
	}

	public void setRcvInsIdCd(String rcvInsIdCd) {
		this.rcvInsIdCd = rcvInsIdCd;
	}

	public String getIssInsIdCd() {
		return issInsIdCd;
	}

	public void setIssInsIdCd(String issInsIdCd) {
		this.issInsIdCd = issInsIdCd;
	}

	public String getRsvData() {
		return rsvData;
	}

	public void setRsvData(String rsvData) {
		this.rsvData = rsvData;
	}

	public String getBinVal() {
		return binVal;
	}

	public void setBinVal(String binVal) {
		this.binVal = binVal;
	}

	public String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	public void setRecUpdUsrId(String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}

	public Date getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public Date getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(Date recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInsNm() {
		return insNm;
	}

	public void setInsNm(String insNm) {
		this.insNm = insNm;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	
}
