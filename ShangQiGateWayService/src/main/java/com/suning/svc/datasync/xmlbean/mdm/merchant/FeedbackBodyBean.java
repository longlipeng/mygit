/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: FeedbackBodyBean.java
 * Author:   luwanchuan
 * Date:     2013-5-3 上午11:00:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.merchant;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 返回给MDM的报文主体<br>
 * 主体包含：分发系统、返回的供应商基本层信息状态
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("MbfBody")
public class FeedbackBodyBean {

    /**
     * 分发系统
     */
    @XStreamAlias("DISTRIBUTE_SYS")
    private String distributeSys = "SVC";

    @XStreamImplicit(itemFieldName = "T_ZMDIFS104")
    private ArrayList<FeedbackStatusInfo> feedbackStatusInfo;

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
     * @return the feedbackStatusInfo
     */
    public ArrayList<FeedbackStatusInfo> getFeedbackStatusInfo() {
        return feedbackStatusInfo;
    }

    /**
     * @param feedbackStatusInfo the feedbackStatusInfo to set
     */
    public void setFeedbackStatusInfo(ArrayList<FeedbackStatusInfo> feedbackStatusInfo) {
        this.feedbackStatusInfo = feedbackStatusInfo;
    }

}
