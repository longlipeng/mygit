/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceivedBodyBean.java
 * Author:   luwanchuan
 * Date:     2013-5-25 上午09:51:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.soa.contract;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * SOA通过ESB下发的商户合同信息的Body部分
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("MbfBody")
public class ReceivedBodyBean {

    /**
     * 合同编号部分
     */
    @XStreamImplicit(itemFieldName = "contInfo")
    private List<ContractInfo> contractInfo;

    /**
     * 账户号、结算扣率部分
     */
    @XStreamImplicit(itemFieldName = "acctypeInfo")
    private List<AcctypeInfo> acctypeInfo;

    /**
     * @return the contractInfo
     */
    public List<ContractInfo> getContractInfo() {
        return contractInfo;
    }

    /**
     * @param contractInfo the contractInfo to set
     */
    public void setContractInfo(List<ContractInfo> contractInfo) {
        this.contractInfo = contractInfo;
    }

    /**
     * @return the acctypeInfo
     */
    public List<AcctypeInfo> getAcctypeInfo() {
        return acctypeInfo;
    }

    /**
     * @param acctypeInfo the acctypeInfo to set
     */
    public void setAcctypeInfo(List<AcctypeInfo> acctypeInfo) {
        this.acctypeInfo = acctypeInfo;
    }

}
