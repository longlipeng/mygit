/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: RscMessageUtil.java
 * Author:   12073942
 * Date:     2013-4-28 下午4:18:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.utils;

import java.nio.charset.Charset;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.framework.lang.AppException;
import com.suning.rsc.dto.requestdto.MbfRequest;
import com.suning.rsc.dto.responsedto.MbfResponse;
import com.suning.svc.datasync.common.SeamErrorCode;
import com.thoughtworks.xstream.converters.ConversionException;

/**
 * 基于snf-rsc的消息处理工具
 * 
 * @author yangtao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RscMessageUtil {

    private static final Logger RECEIVE_LOGGER = LoggerFactory.getLogger("JmsReceiveLogger");

    /**
     * 字符串默认值
     */
    private static final String STR_DEF_VAL = "[undefined]";

    /**
     * 
     * 从JMS消息中获取消息文本
     * 
     * @param message JMS消息
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getMessageText(Message message) {
        String messageID = STR_DEF_VAL;
        String messageCorrelationID = STR_DEF_VAL;
        String messageDestination = STR_DEF_VAL;
        String messageText = STR_DEF_VAL;
        String messageType = STR_DEF_VAL;
        try {
            messageID = message.getJMSMessageID();
            messageCorrelationID = message.getJMSCorrelationID();
            if (message.getJMSDestination() != null) {
                messageDestination = message.getJMSDestination().toString();
            }
        } catch (JMSException e) {
            RECEIVE_LOGGER.error(e.getMessage());
            throw new AppException(SeamErrorCode.SEAM_ERROR_003, e.getMessage());
        }
        if (message instanceof BytesMessage) {
            messageType = "[BytesMessage]";
            try {
                BytesMessage bm = (BytesMessage) message;
                byte[] bys = new byte[(int) bm.getBodyLength()];
                bm.readBytes(bys);
                // 从bytes构建String，指定字符集，阻止一个FindBugs问题
                messageText = new String(bys, Charset.forName("UTF-8"));
            } catch (JMSException e) {
                RECEIVE_LOGGER.error(e.getMessage());
                throw new AppException(SeamErrorCode.SEAM_ERROR_003, e.getMessage());
            }
        } else if (message instanceof TextMessage) {
            messageType = "[TextMessage]";
            try {
                TextMessage tMsg = (TextMessage) message;
                messageText = tMsg.getText();
            } catch (JMSException e) {
                RECEIVE_LOGGER.error(e.getMessage());
                throw new AppException(SeamErrorCode.SEAM_ERROR_003, e.getMessage());
            }
        } else {
            RECEIVE_LOGGER.error("The received Message is neither [BytesMessage] nor [TextMessage]!");
            throw new AppException(SeamErrorCode.SEAM_ERROR_002,
                    "The received Message is neither [BytesMessage] nor [TextMessage]!");
        }
        RECEIVE_LOGGER.info("##############################  receive.message.begin  ##############################");
        RECEIVE_LOGGER.info("##########[messageType:{},messageID:{},messageCorrelationID:{},messageDestination:{}]",
                new Object[] { messageType, messageID, messageCorrelationID, messageDestination });
        RECEIVE_LOGGER.info("##########[messageText]--------------------------------------------------------------");
        RECEIVE_LOGGER.info("\n" + messageText + "\n");
        RECEIVE_LOGGER.info("##############################  receive.message.end    ##############################");
        return messageText;
    }

    /**
     * 
     * 从消息文本（XML）获取MbfBody对象
     * 
     * @param messageText XML消息文本
     * @param clazz Body对象类
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBodyObject(String messageText, Class<T> clazz) {
        try {
            return (T) MbfRequest.getMbfRequest(messageText).getInput().getMbfBody(clazz);
        } catch (ConversionException e) {
            return (T) MbfResponse.getMbfResponse(messageText).getOutput().getMbfBody(clazz);
        }
    }

    // /**
    // *
    // * 从JMS消息获取MbfBody对象
    // *
    // * @param message JMS消息
    // * @param clazz Body对象类
    // * @return
    // * @see [相关类/方法](可选)
    // * @since [产品/模块版本](可选)
    // */
    // @Deprecated
    // public static <T> T getBodyObject(Message message, Class<T> clazz) {
    // return getBodyObject(getMessageText(message), clazz);
    // }

}
