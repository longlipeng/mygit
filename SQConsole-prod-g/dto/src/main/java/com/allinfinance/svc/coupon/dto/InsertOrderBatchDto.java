/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SumOrderBatchInsertDto.java
 * Author:   13010154
 * Date:     2013-10-31 下午05:52:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.svc.coupon.dto;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InsertOrderBatchDto {
    
    /**
     * 需要插入的汇总批次号
     */
    private Long batchNo;
    /**
     * 交易日期
     */
    private String tradeDate;
    /**
     * 交易类型
     */
    private String txnType;

    /**
     * 汇总时交易类型编码
     */
    private String sumTxnTypeCode;

    /**
     * @return the batchNo
     */
    public Long getBatchNo() {
        return batchNo;
    }


    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }


    /**
     * @return the tradeDate
     */
    public String getTradeDate() {
        return tradeDate;
    }


    /**
     * @param tradeDate the tradeDate to set
     */
    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }


    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }


    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }


    /**
     * @return the sumTxnTypeCode
     */
    public String getSumTxnTypeCode() {
        return sumTxnTypeCode;
    }


    /**
     * @param sumTxnTypeCode the sumTxnTypeCode to set
     */
    public void setSumTxnTypeCode(String sumTxnTypeCode) {
        this.sumTxnTypeCode = sumTxnTypeCode;
    }
    
    
    
    
}
