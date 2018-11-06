/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardLifeCycleQueryDTO.java
 * Author:   zqs
 * Date:     2013-4-25 上午10:09:22
 * Description:       
 * History: 
 * <author>      <time>      <version>    <desc>
 * zqs            2013-4-25 上午10:09:22
 */
package com.allinfinance.service.issueOperation.cardLifeCycleQuery.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

/**
 * 卡生命周期查询DTO<br> 
 * 
 *
 * @author zqs
 * @see 
 * @since 
 */
public class CardLifeCycleQueryDTO extends PageQueryDTO {
    private static final long serialVersionUID = 2235685692201572706L;
    
    /**
     * 卡号
     */
    private String cardNo;
    
    /**
     * 账号
     */
    private String accountNo;
    
    /**
     * 交易日期
     */
    private String txnDate;
    
    /**
     * 交易类型
     */
    private String txnType;
    
    /**
     * 交易金额
     */
    private String txnAmt;
    
    /**
     * 余额
     */
    private String accBal;
    
    /**
     * 发行机构
     */
    private String issuerId;
    
    /**
     * 开始时间
     */
    private String startDate;
    
    /**
     * 结束时间
     */
    private String endDate;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getAccBal() {
        return accBal;
    }

    public void setAccBal(String accBal) {
        this.accBal = accBal;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
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

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }
    

}
