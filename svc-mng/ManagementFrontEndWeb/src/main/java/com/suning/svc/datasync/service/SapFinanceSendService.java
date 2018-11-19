/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapFinanceSendService.java
 * Author:   12073942
 * Date:     2013-4-25 下午7:40:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import com.suning.rsc.mqservice.ei.annotation.EsbEIMethod;
import com.suning.rsc.mqservice.ei.annotation.EsbEIService;
import com.suning.svc.datasync.xmlbean.sap.finance.FinanceBean;

/**
 * 向ESB发送消息服务(由snf-rsc组件实现)
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EsbEIService(appCode = "SVC", serviceCode = "AccountingMgmt")
public interface SapFinanceSendService {

    /**
     * 异步发送请求报文
     * 
     * @param dto 请求报文中的MbfBody节点内部的对象，可以为null
     */
    @EsbEIMethod(asynchronous = true, 
            operation = "uploadAccountingInf", 
            connectionFactory = "svcConnectionFactoryAdapter", 
            requestQueue = "sapFinanceOutQueue", 
            requestBodyClass = FinanceBean.class)
    void asynSend(FinanceBean dto);

}
