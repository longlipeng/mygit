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


package com.allinfinance.service.sellOperation.cardConsumptionDetail.dto;

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
	 * 开始日期
	 */
	private String startDate;
	
	/**
	 * 结束日期
	 */
	private String endDate;
	
	/**
	 * 今天或历史
	 */
	private String cardFlag;
	
	/**
	 * 商户编码
	 */
	private String merchantNo;
	
	/**
	 * 卡号
	 */
	private String priAcctNo;
	
	/**
	 * 商户编码
	 */
	private String mchntCd;
	
	/**
	 * 商户名称
	 */
	private String merchantName;
	
	/**
	 * 门店代码
	 */
	private String shopCode;
	
	/**
	 * 门店名称
	 */
	private String shopName;
	
	/**
	 * 终端号
	 */
	private String termId;
	
	/**
	 * 流水号
	 */
	private String primaryKey;
	
	/**
	 * 卡产品名称
	 */
	private String primaryKey2;
	
	/**
	 * 交易名称
	 */
	private String transName;
	
	/**
	 * 交易时间
	 */
	private String transRcvTS;
	
	/**
	 * 交易金额
	 */
	private String transAt;
	
	public String getPriAcctNo() {
		return priAcctNo;
	}
	public void setPriAcctNo(String priAcctNo) {
		this.priAcctNo = priAcctNo;
	}
	public String getMchntCd() {
		return mchntCd;
	}
	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getPrimaryKey2() {
		return primaryKey2;
	}
	public void setPrimaryKey2(String primaryKey2) {
		this.primaryKey2 = primaryKey2;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getTransRcvTS() {
		return transRcvTS;
	}
	public void setTransRcvTS(String transRcvTS) {
		this.transRcvTS = transRcvTS;
	}
	public String getTransAt() {
		return transAt;
	}
	public void setTransAt(String transAt) {
		this.transAt = transAt;
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
	public String getCardFlag() {
		return cardFlag;
	}
	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	
		
	

}
