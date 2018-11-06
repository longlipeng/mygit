/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapFinanceMDP.java
 * Author:   12073942
 * Date:     2013-4-22 下午2:44:46
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.mdp;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.rsc.mqservice.ei.annotation.EsbEIServerService;
import com.suning.svc.datasync.service.SapFinanceRecvService;
import com.suning.svc.datasync.utils.RscMessageUtil;
import com.suning.svc.datasync.xmlbean.sap.finance.ReceiptBean;

/**
 * SAP财务记账回执队列侦听器
 * 
 * @author yangtao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EsbEIServerService(defaultMessageListenerContainer = "sapFinanceListenerContainer")
public class SapFinanceMDP implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SapFinanceMDP.class);

    /**
     * 对BodyBean逻辑处理的服务
     */
    private SapFinanceRecvService sapFinanceRecvService;

    /*
     * (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        String msgstr = RscMessageUtil.getMessageText(message);
        LOGGER.info("接收到【SAP记账回执】：{}", msgstr);
        // 使用RscMessageUtil获取Body对象
        ReceiptBean body = RscMessageUtil.getBodyObject(msgstr, ReceiptBean.class);
        sapFinanceRecvService.processBodyBean(body);
    }

    /**
     * @param sapFinanceRecvService the sapFinanceRecvService to set
     */
    public void setSapFinanceRecvService(SapFinanceRecvService sapFinanceRecvService) {
        this.sapFinanceRecvService = sapFinanceRecvService;
    }

}
