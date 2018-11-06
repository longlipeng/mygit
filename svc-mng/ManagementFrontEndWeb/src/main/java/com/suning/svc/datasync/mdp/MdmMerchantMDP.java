/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmMerchantMDP.java
 * Author:   luwanchuan
 * Date:     2013-4-10 下午02:34:30
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
import com.suning.svc.datasync.service.MdmMerchantRecvService;
import com.suning.svc.datasync.utils.RscMessageUtil;
import com.suning.svc.datasync.xmlbean.mdm.merchant.ReceivedBodyBean;

/**
 * 
 * 供应商（商户）数据下发消息监听
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EsbEIServerService(defaultMessageListenerContainer = "mdmMerchantContainer")
public class MdmMerchantMDP implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmMerchantMDP.class);

    private MdmMerchantRecvService merchantService;

    public void onMessage(Message message) {
        String msgstr = RscMessageUtil.getMessageText(message);
        LOGGER.info("seam接收到的数据：" + msgstr);
        // 使用RscMessageUtil获取Body对象
        ReceivedBodyBean bean = RscMessageUtil.getBodyObject(msgstr, ReceivedBodyBean.class);
        // MbfRequest mbfRequest = MbfRequest.getMbfRequest(msgstr);
        // ReceivedBodyBean bean = (ReceivedBodyBean) mbfRequest.getInput().getMbfBody(ReceivedBodyBean.class);
        process(bean);
    }

    private void process(ReceivedBodyBean bean) {
        merchantService.processAtom(bean);
    }

    public MdmMerchantRecvService getMerchantService() {
        return merchantService;
    }

    public void setMerchantService(MdmMerchantRecvService merchantService) {
        this.merchantService = merchantService;
    }

}
