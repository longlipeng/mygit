/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ProvisionsBanlanceDTO.java
 * Author:   zqs
 * Date:     2013-7-18 
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs             2013-7-18        
 */
package com.allinfinance.univer.report.dto;

import java.math.BigDecimal;

import com.allinfinance.univer.report.IreportDTO;


/**
 *  备付金余额查询报表的action<br> 
 *  
 *
 * @author zqs
 * @see 
 * @since 
 */
public class ProvisionsBanlanceDTO extends IreportDTO {
	
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
	 * 卡品牌
	 */
	private String productName;
	/**
	 * 已售卡数量（张）
	 */
	private BigDecimal soldCard;
	/**
	 * 激活数量（张）
	 */
	private BigDecimal actCard;
	/**
	 *上期沉淀资金
	 */
	private BigDecimal lastPreFunds;
	/**
	 * 充值金额
	 */
	private BigDecimal rechargeAmt;
	/**
	 * 累积销售金额
	 */
	private BigDecimal soldAmtSum;
	/**
	 * 消费金额
	 */
	private BigDecimal consumeAmt;
	/**
	 * 退货金额
	 */
	private BigDecimal reFundAmt;
	/**
	 * 交易金额
	 */
	private BigDecimal transAmt;
	/**
	 * 累积交易金额
	 */
	private BigDecimal transAmtSum;
	/**
	 * 累积沉淀资金
	 */
	private BigDecimal preFundsSum;
	
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
    public BigDecimal getSoldCard() {
        return soldCard;
    }
    public void setSoldCard(BigDecimal soldCard) {
        this.soldCard = soldCard;
    }
    public BigDecimal getActCard() {
        return actCard;
    }
    public void setActCard(BigDecimal actCard) {
        this.actCard = actCard;
    }
    public BigDecimal getLastPreFunds() {
        return lastPreFunds;
    }
    public void setLastPreFunds(BigDecimal lastPreFunds) {
        this.lastPreFunds = lastPreFunds;
    }
    public BigDecimal getRechargeAmt() {
        return rechargeAmt;
    }
    public void setRechargeAmt(BigDecimal rechargeAmt) {
        this.rechargeAmt = rechargeAmt;
    }
    public BigDecimal getSoldAmtSum() {
        return soldAmtSum;
    }
    public void setSoldAmtSum(BigDecimal soldAmtSum) {
        this.soldAmtSum = soldAmtSum;
    }
    public BigDecimal getConsumeAmt() {
        return consumeAmt;
    }
    public void setConsumeAmt(BigDecimal consumeAmt) {
        this.consumeAmt = consumeAmt;
    }
    public BigDecimal getReFundAmt() {
        return reFundAmt;
    }
    public void setReFundAmt(BigDecimal reFundAmt) {
        this.reFundAmt = reFundAmt;
    }
    public BigDecimal getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
    public BigDecimal getTransAmtSum() {
        return transAmtSum;
    }
    public void setTransAmtSum(BigDecimal transAmtSum) {
        this.transAmtSum = transAmtSum;
    }
    public BigDecimal getPreFundsSum() {
        return preFundsSum;
    }
    public void setPreFundsSum(BigDecimal preFundsSum) {
        this.preFundsSum = preFundsSum;
    }

	
	

}
