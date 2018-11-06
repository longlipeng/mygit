/**
 * Classname SellerIssueSellDetailDTO.java
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
 *		administrator		2012-11-5
 * =============================================================================
 */

package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class SellerIssueSellDetailDTO extends IreportDTO {
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String sellerId;//当前营销机构ID
	private String sellerName;//当前营销结构名称
	private String nsellerId;//下级营销机构ID
	private String nsellerName;//下级营销机构名称
	private String queryType;//查询方式
	
	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
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

	
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getNsellerId() {
		return nsellerId;
	}

	public void setNsellerId(String nsellerId) {
		this.nsellerId = nsellerId;
	}

	public String getNsellerName() {
		return nsellerName;
	}

	public void setNsellerName(String nsellerName) {
		this.nsellerName = nsellerName;
	}
}
