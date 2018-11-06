package com.allinfinance.univer.consumer.txn.dto;

import java.util.Date;

import com.allinfinance.framework.dto.PageQueryDTO;

public class TxnQueryDTO extends PageQueryDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 交易类型
	 */
	
	private String txnNum;

	/**
	 * 查询起始时间
	 */
	private Date startDate;
	
	/**
	 * 门店号
	 */
	private Integer shopId;
	
	
	/**
	 * 查询结束时间
	 */
	private Date stopDate;
	
	
	/**
	 * 商户ID
	 */
	private String cardAccpId;
	
	
	private Integer[] txnNums;
	
	private String isBo;

	private String cardNo;
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIsBo() {
		return isBo;
	}

	public void setIsBo(String isBo) {
		this.isBo = isBo;
	}

	public Integer[] getTxnNums() {
		return txnNums;
	}

	public void setTxnNums(Integer[] txnNums) {
		this.txnNums = txnNums;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getCardAccpId() {
		return cardAccpId;
	}

	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
}
