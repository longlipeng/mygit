/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmCompanySendService.java
 * Author:   12073942
 * Date:     2013-7-30 下午5:37:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import com.suning.rsc.mqservice.ei.annotation.EsbEIMethod;
import com.suning.rsc.mqservice.ei.annotation.EsbEIService;
import com.suning.svc.datasync.xmlbean.mdm.company.FeedbackBodyBean;

/**
 * 供应商公司层主数据状态返回发送接口
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EsbEIService(appCode = "SVC", serviceCode = "VendorManagement_MDM")
public interface MdmCompanySendService {

    @EsbEIMethod(reqMbfBodyNode = false, 
            asynchronous = true, 
            operation = "receiveRSOfVendorCompanyDate", 
            connectionFactory = "svcConnectionFactoryAdapter", 
            requestQueue = "mdmCompanyOutQueue", 
            requestBodyClass = FeedbackBodyBean.class)
    void asynSend(FeedbackBodyBean bean);

}
