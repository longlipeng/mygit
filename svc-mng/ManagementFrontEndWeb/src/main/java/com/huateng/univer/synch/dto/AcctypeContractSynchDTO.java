/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AcctypeContractSynchDTO.java
 * Author:   lfr
 * Date:     1970-1-1 上午12:00:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

import java.io.Serializable;

/**
 * 
 * 账户合同同步DTO
 * 
 * @author lfr
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AcctypeContractSynchDTO implements Serializable {

    private static final long serialVersionUID = -3765440679341564519L;

    /**
     * 账户号
     */
    String acctypeId;
    /**
     * 计算规则号
     */
    String calculationRuleNo;

    public String getAcctypeId() {
        return acctypeId;
    }

    public void setAcctypeId(String acctypeId) {
        this.acctypeId = acctypeId;
    }

    public String getCalculationRuleNo() {
        return calculationRuleNo;
    }

    public void setCalculationRuleNo(String calculationRuleNo) {
        this.calculationRuleNo = calculationRuleNo;
    }

}
