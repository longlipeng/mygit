/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmCompanyMDP.java
 * Author:   12073942
 * Date:     2013-7-30 下午5:27:21
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
import com.suning.svc.datasync.service.MdmCompanyRecvService;
import com.suning.svc.datasync.utils.RscMessageUtil;
import com.suning.svc.datasync.xmlbean.mdm.company.ReceivedBodyBean;

/**
 * 供应商公司层主数据侦听器
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EsbEIServerService(defaultMessageListenerContainer = "mdmCompanyListenerContainer")
public class MdmCompanyMDP implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmCompanyMDP.class);

    /**
     * 对BodyBean逻辑处理的服务
     */
    private MdmCompanyRecvService companyService;

    /*
     * (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        String msgstr = RscMessageUtil.getMessageText(message);
        LOGGER.info("接收到【MDM供应商公司层主数据】：{}", msgstr);
        // 使用RscMessageUtil获取Body对象
        ReceivedBodyBean bodyBean = RscMessageUtil.getBodyObject(msgstr, ReceivedBodyBean.class);
        companyService.processBodyBean(bodyBean);
    }

    /**
     * @param companyService the companyService to set
     */
    public void setCompanyService(MdmCompanyRecvService companyService) {
        this.companyService = companyService;
    }

}
