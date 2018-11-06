/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmSupplierMDP.java
 * Author:   12073942
 * Date:     2013-10-25 下午3:56:03
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
import com.suning.svc.datasync.service.MdmSupplierRecvService;
import com.suning.svc.datasync.utils.RscMessageUtil;
import com.suning.svc.datasync.xmlbean.mdm.merchant.ReceivedBodyBean;

/**
 * 供应商基本层主数据侦听器
 * 
 * @author LEO
 */
@EsbEIServerService(defaultMessageListenerContainer = "mdmSupplierListenerContainer")
public class MdmSupplierMDP implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmSupplierMDP.class);

    /**
     * 对BodyBean逻辑处理的服务
     */
    private MdmSupplierRecvService mdmSupplierRecvService;

    /*
     * (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        String msgstr = RscMessageUtil.getMessageText(message);
        LOGGER.info("接收到【MDM供应商基本层主数据】：{}", msgstr);
        // 使用RscMessageUtil获取Body对象
        ReceivedBodyBean bodyBean = RscMessageUtil.getBodyObject(msgstr, ReceivedBodyBean.class);
        mdmSupplierRecvService.processBodyBean(bodyBean);
    }

    /**
     * @param mdmSupplierRecvService the mdmSupplierRecvService to set
     */
    public void setMdmSupplierRecvService(MdmSupplierRecvService mdmSupplierRecvService) {
        this.mdmSupplierRecvService = mdmSupplierRecvService;
    }

}
