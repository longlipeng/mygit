/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BankInfoDTO.java
 * Author:   12073942
 * Date:     2013-10-25 下午5:32:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

/**
 * 客户同步DTO中的银行信息
 * 
 * @author LEO
 */
public class BankInfoDTO {

    /**
     * 银行名称
     */
    private String branchBankName;

    /**
     * 银行账户
     */
    private String bankAcctCode;

    /**
     * @return the branchBankName
     */
    public String getBranchBankName() {
        return branchBankName;
    }

    /**
     * @param branchBankName the branchBankName to set
     */
    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    /**
     * @return the bankAcctCode
     */
    public String getBankAcctCode() {
        return bankAcctCode;
    }

    /**
     * @param bankAcctCode the bankAcctCode to set
     */
    public void setBankAcctCode(String bankAcctCode) {
        this.bankAcctCode = bankAcctCode;
    }

}
