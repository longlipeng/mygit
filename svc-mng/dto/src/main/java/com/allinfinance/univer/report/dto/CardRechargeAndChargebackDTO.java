package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

public class CardRechargeAndChargebackDTO extends IreportDTO{
	private static final long serialVersionUID = -1899300719128820480L;

	/**
	 * 预付卡充值充退报表DTO
	 */
	private String startDate;
	
	private String endDate;
	
	private String issuerId;
	
	private String saleMan;
	
	private String fatherEntityId;

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

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

    public String getFatherEntityId() {
        return fatherEntityId;
    }

    public void setFatherEntityId(String fatherEntityId) {
        this.fatherEntityId = fatherEntityId;
    }
	
	
	
}
