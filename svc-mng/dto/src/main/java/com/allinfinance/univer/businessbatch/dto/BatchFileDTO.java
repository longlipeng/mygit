package com.allinfinance.univer.businessbatch.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class BatchFileDTO extends BaseDTO {

	public static final String BATCH_STATE_INIT = "00";
	public static final String BATCH_STATE_START = "01";
	public static final String BATCH_STATE_DOING = "02";
	public static final String BATCH_STATE_END = "03";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;
	// 批量文件类型
	// 01-激活
	// 02-充值
	// 03-制卡

	private String batchType;
	// 批次号s
	private String batchNo;
	// 结算日期
	private String smltDate;
	// 批次状态
	private String batchState;
	// 文件名称
	private String fileName;
	// 文件路径
	private String filePath;
	// 总计笔数
	private String totCnt;
	// 总计金额
	private String totAmt = "0";
	// 成功笔数
	private String sucCnt;
	// 成功金额
	private String sucAmt;
	// 失败笔数
	private String failCnt;
	// 失败金额
	private String failAmt;
	
	private String createTime;
	private String cvn2;//卡片cvn2（订单激活的时候当做单卡激活的时候用）
	
	
	
	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getBatchState() {
		return batchState;
	}

	public void setBatchState(String batchState) {
		this.batchState = batchState;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}

	public String getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(String totAmt) {
		this.totAmt = totAmt;
	}

	public String getSucCnt() {
		return sucCnt;
	}

	public void setSucCnt(String sucCnt) {
		this.sucCnt = sucCnt;
	}

	public String getSucAmt() {
		return sucAmt;
	}

	public void setSucAmt(String sucAmt) {
		this.sucAmt = sucAmt;
	}

	public String getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(String failCnt) {
		this.failCnt = failCnt;
	}

	public String getFailAmt() {
		return failAmt;
	}

	public void setFailAmt(String failAmt) {
		this.failAmt = failAmt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSmltDate() {
		return smltDate;
	}

	public void setSmltDate(String smltDate) {
		this.smltDate = smltDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
