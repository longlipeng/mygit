/**
 * Classname IreportWarehouseFlowDTO.java
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
 *		wpf		2012-10-18
 * =============================================================================
 */

package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author wpf
 * 
 */
public class IreportWarehouseFlowDTO extends IreportDTO {

	private static final long serialVersionUID = -942068734166417637L;
	private String startDate;
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
}
