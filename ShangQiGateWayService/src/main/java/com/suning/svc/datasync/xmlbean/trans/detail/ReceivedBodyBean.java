/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceivedBodyBean.java
 * Author:   12073942
 * Date:     2013-10-18 下午2:25:16
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.trans.detail;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 交易请求报文主体
 * 
 * @author LEO
 */
@XStreamAlias("MbfBody")
public class ReceivedBodyBean {

    /** parterner 合作伙伴 */
    @XStreamAlias("parterner")
    private String partner;

    /** details 明细 */
    @XStreamAlias("details")
    private Details details;

    /**
     * @return the partner
     */
    public String getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * @return the details
     */
    public Details getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(Details details) {
        this.details = details;
    }

}
