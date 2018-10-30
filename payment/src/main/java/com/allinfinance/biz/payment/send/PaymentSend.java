package com.allinfinance.biz.payment.send;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.allinfinance.biz.payment.config.PaymentConfig;
import com.util.RSASignUtils;
import org.apache.log4j.Logger;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by liuheli on 2017/5/10.
 */
@Component
public class PaymentSend{

    private static Logger logger = Logger.getLogger(PaymentSend.class);

    @Resource(name = "cxfSigTemplate")
    private RabbitTemplate cxfSigTemplate;//单用途卡消息队列

    @Resource(name = "cxfMultTemplate")
    private RabbitTemplate cxfMultTemplate;//多用途卡消息队列

    @Resource(name = "refundMultTemplate")
    private RabbitTemplate refundMultTemplate;//多用途卡退款查询或退款消息队列

    //通过RabbitMq将请求报文发送到消息对联
    public String sendMsg(String info, String card_no, boolean isRefund) throws UnsupportedEncodingException {
        //判断是否是多用途卡
        boolean isCxfMult = isCxfMult(card_no);
        logger.info("礼品卡卡号card_no：\t" + card_no);
        //报文消息转码
        String correlationId = RSASignUtils.getCorrelationId();
        logger.info("correlationId：\n" + correlationId);
        MessageProperties mp = new MessageProperties();
        mp.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        mp.setCorrelationId(correlationId.getBytes("GBK"));
        Message rbtReq = new Message(info.getBytes("GBK"), mp);
        //发送请求报文、接收响应报文
        Message rbtResp;
        if(isCxfMult){
            if(isRefund){
                logger.info("多用途卡退款查询或退款申请操作：\t refundMultTemplate" );
                rbtResp = refundMultTemplate.sendAndReceive(rbtReq);
            }else{
                logger.info("多用途卡：\t cxfMultTemplate");
                rbtResp = cxfMultTemplate.sendAndReceive(rbtReq);
            }
        }else{
            logger.info("单用途卡：\t cxfSigTemplate");
            rbtResp = cxfSigTemplate.sendAndReceive(rbtReq);
        }
        //响应报文转码
        if(rbtResp == null){
            logger.info("接收到Rabbit报文：null" );
            return null;
        }else {
            String xmlResp = new String(rbtResp.getBody(), "GBK");
            logger.info("接收到Rabbit报文：\n" + xmlResp);
            return xmlResp;
        }
    }

    //判断是否是多用途卡
    private static boolean isCxfMult(String card_no){
        //校验card_no前五位是否存在card_bin中，选择不同的消息队列
        String cardBin = card_no.substring(0,5);
        if(PaymentConfig.SPECIAL_CARD_BIN.equals(cardBin)){//90888开头的特殊卡
            int length = card_no.length();
            int beginIndex = length - 3;
            int endIndex = length -1;
            String multiAreaCode = card_no.substring(beginIndex, endIndex);

            if(PaymentConfig.MULTI_AREA_CODE.contains(multiAreaCode))
                return true;
            else
                return false;
        }if(PaymentConfig.LIST_CARD_BIN.contains(cardBin)){
            return true;
        }else{
            return false;
        }
    }
}
