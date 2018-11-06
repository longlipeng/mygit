/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AcctypeInfo.java
 * Author:   12074048
 * Date:     2013-5-29 上午10:28:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.soa.contract;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * SOA通过ESB下发的商户合同信息-账户号、结算扣率部分
 * 
 * @author 12074048
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("acctypeInfo")
public class AcctypeInfo {

    /**
     * 账户号
     */
    @XStreamAlias("acctypeId")
    private String acctypeId;

    /**
     * 结算扣率
     */
    @XStreamAlias("calculationRuleNo")
    private String calculationRuleNo;

    /**
     * @return the acctypeId
     */
    public String getAcctypeId() {
        return acctypeId;
    }

    /**
     * @param acctypeId the acctypeId to set
     */
    public void setAcctypeId(String acctypeId) {
        this.acctypeId = acctypeId;
    }

    /**
     * @return the calculationRuleNo
     */
    public String getCalculationRuleNo() {
        return calculationRuleNo;
    }

    /**
     * @param calculationRuleNo the calculationRuleNo to set
     */
    public void setCalculationRuleNo(String calculationRuleNo) {
        this.calculationRuleNo = calculationRuleNo;
    }

}
