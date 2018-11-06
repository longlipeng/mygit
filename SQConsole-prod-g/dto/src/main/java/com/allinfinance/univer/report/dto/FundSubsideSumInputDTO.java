/**
 * Classname FundSubsideSumQueryDTO.java
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
 *		yaoxin		2012-10-26
 * =============================================================================
 */

package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 * @author yaoxin
 *  资金沉淀报表查询条件DTO，录入查询日期
 */
public class FundSubsideSumInputDTO extends IreportDTO {
	private static final long serialVersionUID = 1L;
	private String queryDate;//按单日查询
	
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	
}
