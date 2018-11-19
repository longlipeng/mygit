/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MerchantContractSynchDTO.java
 * Author:   lfr
 * Date:     1970-1-1 上午12:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 商户合同DTO
 * 
 * @author lfr
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MerchantContractSynchDTO implements Serializable {

    private static final long serialVersionUID = 7198272542546553911L;

    /**
     * 发起公司代码
     */
    String initiatorCompanyCode;
    /**
     * 发起公司名称
     */
    String initiatorCompanyName;
    /**
     * 合同编号
     */
    String consumerContractId;
    /**
     * 商户编号
     */
    String contractBuyer;
    /**
     * 商户名称
     */
    String merchantName;
    /**
     * 结算周期规则号
     */
    String periodRuleNo;
    /**
     * 合同签订时间
     */
    String contractCreateDate;
    /**
     * 合同开始日期
     */
    String contractStartDate;
    /**
     * 合同结束日期
     */
    String contractEndDate;
    /**
     * 服务合同
     */
    List<AcctypeContractSynchDTO> acctypeContractSynchDTOs;

    public String getInitiatorCompanyCode() {
        return initiatorCompanyCode;
    }

    public void setInitiatorCompanyCode(String initiatorCompanyCode) {
        this.initiatorCompanyCode = initiatorCompanyCode;
    }

    public String getInitiatorCompanyName() {
        return initiatorCompanyName;
    }

    public void setInitiatorCompanyName(String initiatorCompanyName) {
        this.initiatorCompanyName = initiatorCompanyName;
    }

    public String getConsumerContractId() {
        return consumerContractId;
    }

    public void setConsumerContractId(String consumerContractId) {
        this.consumerContractId = consumerContractId;
    }

    public String getContractBuyer() {
        return contractBuyer;
    }

    public void setContractBuyer(String contractBuyer) {
        this.contractBuyer = contractBuyer;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPeriodRuleNo() {
        return periodRuleNo;
    }

    public void setPeriodRuleNo(String periodRuleNo) {
        this.periodRuleNo = periodRuleNo;
    }

    public String getContractCreateDate() {
        return contractCreateDate;
    }

    public void setContractCreateDate(String contractCreateDate) {
        this.contractCreateDate = contractCreateDate;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public List<AcctypeContractSynchDTO> getAcctypeContractSynchDTOs() {
        return acctypeContractSynchDTOs;
    }

    public void setAcctypeContractSynchDTOs(List<AcctypeContractSynchDTO> acctypeContractSynchDTOs) {
        this.acctypeContractSynchDTOs = acctypeContractSynchDTOs;
    }

}
