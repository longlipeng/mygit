/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: FeedbackBodyBean.java
 * Author:   12073942
 * Date:     2013-7-30 下午5:40:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.company;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 供应商公司层主数据状态返回BodyBean
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("MbfBody")
public class FeedbackBodyBean {

    /**
     * DISTRIBUTE_SYS 分发系统
     */
    @XStreamAlias("DISTRIBUTE_SYS")
    private String distributeSys;

    /**
     * T_ZMDIFS106 商品主数据状态返回
     */
    @XStreamImplicit(itemFieldName = "T_ZMDIFS106")
    private ArrayList<CompanyFeedbackInfo> companyFeedbackInfoList;

    /**
     * @return the distributeSys
     */
    public String getDistributeSys() {
        return distributeSys;
    }

    /**
     * @param distributeSys the distributeSys to set
     */
    public void setDistributeSys(String distributeSys) {
        this.distributeSys = distributeSys;
    }

    /**
     * @return the companyFeedbackInfoList
     */
    public ArrayList<CompanyFeedbackInfo> getCompanyFeedbackInfoList() {
        return companyFeedbackInfoList;
    }

    /**
     * @param companyFeedbackInfoList the companyFeedbackInfoList to set
     */
    public void setCompanyFeedbackInfoList(ArrayList<CompanyFeedbackInfo> companyFeedbackInfoList) {
        this.companyFeedbackInfoList = companyFeedbackInfoList;
    }

}
