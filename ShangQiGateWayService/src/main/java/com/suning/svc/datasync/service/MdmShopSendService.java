/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmShopSendService.java
 * Author:   luwanchuan
 * Date:     2013-5-2 下午03:45:57
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import com.suning.rsc.mqservice.ei.annotation.EsbEIMethod;
import com.suning.rsc.mqservice.ei.annotation.EsbEIService;
import com.suning.svc.datasync.xmlbean.mdm.shop.FeedbackBodyBean;

/**
 * 地点（门店）数据下发，seam发送返回消息，返回给MDM
 * 
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EsbEIService(appCode = "SVC", serviceCode = "OrgManagement_MDM")
public interface MdmShopSendService {

    @EsbEIMethod(reqMbfBodyNode = false, 
            asynchronous = true, 
            operation = "receiveRSOfSitesDate", 
            connectionFactory = "svcConnectionFactoryAdapter", 
            requestQueue = "svcSeamMDMShopOutQueue", 
            requestBodyClass = FeedbackBodyBean.class)
    void asynSend(FeedbackBodyBean bean);

}
