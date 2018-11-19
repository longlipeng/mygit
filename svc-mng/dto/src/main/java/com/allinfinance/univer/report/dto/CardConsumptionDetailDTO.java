/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardConsumptionDetailDTO.java
 * Author:   zqs
 * Date:     2013-4-17 
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-4-17        
 */
package com.allinfinance.univer.report.dto;

import com.allinfinance.univer.report.IreportDTO;

/**
 *  卡消费明细报表中的DTO<br> 
 *  
 *
 * @author zqs
 * @see 
 * @since 
 */
public class CardConsumptionDetailDTO extends IreportDTO {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 开始日期
	 * 
     */
	private String startDate;	
	/**
	 * 结束日期
	 */
	private String endDate;
	
	/**
	 * 商户编码
	 */
	private String merchantNo;
	
	/**
	 * 今天或历史
	 */
	private String cardFlag;
	
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
	
	public String getMerchantNo() {
		return merchantNo;
	}
	
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
	

}
