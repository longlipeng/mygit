package com.allinfinance.biz.payment.service;

import com.alibaba.fastjson.JSON;
import com.allinfinance.biz.payment.config.PaymentConfig;
import com.allinfinance.biz.payment.entity.AsyncNoticeInfo;
import com.allinfinance.biz.payment.entity.PaymentRequestInfo;
import com.allinfinance.biz.payment.entity.PaymentRespondInfo;
import com.allinfinance.biz.payment.send.PaymentSend;
import com.thoughtworks.xstream.XStream;
import com.util.*;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by liuheli on 2017/5/10.
 */
@Service
public class PaymentService {

    private static Logger logger = Logger.getLogger(PaymentService.class);
    @Value("${async_notice.ip}")
    private String ip;
    @Value("${async_notice.port}")
    private String port;

    @Autowired
    private PaymentSend paymentSend;

    //处理消息队列响应报文
    public String dealWithRespondInfo(PaymentRequestInfo paymentRequestInfo, String notifyUrl){
        PaymentRespondInfo paymentRespondInfo = new PaymentRespondInfo();
        PojoChangeToMap pojoChangeToMap = new PojoChangeToMap();
        String card_no = paymentRequestInfo.getCard_no();
        paymentRespondInfo = (PaymentRespondInfo) pojoChangeToMap.setRespodInfo(paymentRequestInfo, paymentRespondInfo);
        try {
            //第一次发送交易支付报文信息接收交易支付响应报文信息并处理
            paymentRequestInfo.setTran_code("00003004");
            String firstInfo = Encoder.toXml("DATA", paymentRequestInfo);
            logger.info("发送信息报文：\n" + firstInfo);

            String xmlPaymentResp = paymentSend.sendMsg(firstInfo, card_no, false);

            if(StringUtils.isEmpty(xmlPaymentResp)){
                //没有接收到响应报文信息
                paymentRespondInfo.setResult("false");
                paymentRespondInfo.setMsg("监听消息队列超时,支付失败");
            }else{
                //响应报文信息转化成实体类
                paymentRespondInfo = (PaymentRespondInfo) Decoder.parseXml(new XStream(), xmlPaymentResp,"DATA",PaymentRespondInfo.class);
            }
            paymentRespondInfo.setTran_code(null);
        } catch (UnsupportedEncodingException uee) {
            logger.info(uee.getMessage());
            paymentRespondInfo.setMsg("系统错误,支付失败");
            paymentRespondInfo.setResult("false");
        }catch (AmqpException aqe) {
            logger.info(aqe.getMessage());
            paymentRespondInfo.setMsg("系统错误,支付失败");
            paymentRespondInfo.setResult("false");
        }catch (NumberFormatException nfe) {
            logger.info(nfe.getMessage());
            paymentRespondInfo.setMsg("系统错误,支付失败");
            paymentRespondInfo.setResult("false");
        } catch (IOException ioe) {
            logger.info(ioe.getMessage());
            paymentRespondInfo.setMsg("系统错误,支付失败");
            paymentRespondInfo.setResult("false");
        }catch (Exception e) {
            logger.info(e.getMessage());
            paymentRespondInfo.setMsg("系统错误,支付失败");
            paymentRespondInfo.setResult("false");
        }
        if("true".equals(paymentRespondInfo.getResult())){//支付成功
//            paymentRespondInfo.setResult("true");
            //开启异步通知线程
            logger.info("异步通知开启异步通知线程------------------->AsyncNoticeThread");
            new AsyncNoticeThread(paymentRequestInfo, notifyUrl, card_no, Thread.currentThread().getName()).start();
        }
//        else{//失败
//            paymentRespondInfo.setResult("false");
//        }
        //实体报文加密处理返回给请求
        return JSON.toJSONString(pojoChangeToMap.pojoSwapToMap(paymentRespondInfo));
    }

    private class AsyncNoticeThread extends Thread{
        private String notifyUrl;
        private PaymentRequestInfo paymentRequestInfo;
        private String card_no;
        public AsyncNoticeThread(PaymentRequestInfo paymentRequestInfo, String notifyUrl, String card_no, String threadName){
            super("AsyncNotice"+threadName);
            logger.info("支付线程名称："+threadName);
            logger.info("对应异步通知线程：AsyncNotice"+threadName);
            this.paymentRequestInfo = paymentRequestInfo;
            this.notifyUrl = notifyUrl;
            this.card_no = card_no;
        }

        @Override
        public void run() {
            asyncNoticeThread(paymentRequestInfo, notifyUrl, card_no);
        }
        private void asyncNoticeThread(PaymentRequestInfo paymentRequestInfo, String notifyUrl, String card_no){
            logger.info("异步通知开启异步通知线程------------------->AsyncNoticeThread");
            AsyncNoticeInfo asyncNoticeInfo = null;
            try {
                //第一次操作成功后，进行第二次报文发送接收异步同时参数报文并处理
                paymentRequestInfo.setTran_code("00002001");
                String secondInfo = Encoder.toXml("DATA", paymentRequestInfo);
                logger.info("发送信息报文：\n" + secondInfo);
                String xmlAsyncNotice = paymentSend.sendMsg(secondInfo, card_no, false);
               /* if(StringUtils.isEmpty(xmlAsyncNotice)){
                    //没有接收到响应报文信息
                    asyncNoticeInfo = new AsyncNoticeInfo();
                }else{*/
               if(!StringUtils.isEmpty(xmlAsyncNotice)){
                    //响应报文信息转化成实体类
                    asyncNoticeInfo = (AsyncNoticeInfo) Decoder.parseXml(new XStream(), xmlAsyncNotice,"DATA",AsyncNoticeInfo.class);
                }
                asyncNoticeInfo.setTran_code(null);
            } catch (UnsupportedEncodingException uee) {
                logger.info(uee.getMessage());
            }catch (AmqpException aqe) {
                logger.info(aqe.getMessage());
            }catch (NumberFormatException nfe) {
                logger.info(nfe.getMessage());
            } catch (IOException ioe) {
                logger.info(ioe.getMessage());
            }catch (Exception e) {
                logger.info(e.getMessage());
            }
            /**
             * POST请求参数，根据需要进行封装
             */
            logger.info("asyncNoticeInfo------------------------------>"+asyncNoticeInfo);
            if(asyncNoticeInfo != null){
                Map<String, String> respondInfo = RSASignUtils.buildRequestPara(new PojoChangeToMap().pojoSwapToMap(asyncNoticeInfo), RSASignUtils.PRIVATEKEY);
//                respondInfo.replace("+","\\+");
                try {
                    logger.info("respondInfo------------------------------>"+respondInfo);
                    respondInfo.put("sign",URLEncoder.encode(respondInfo.get("sign").toString(),"utf-8"));
                    String respond = HttpUtil.http(notifyUrl,respondInfo,ip,port);
                    logger.info("respond------------------------------>"+respond);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            /*if(!respond.equals("success")){
                asyncNoticeThread(paymentRequestInfo, notifyUrl);
            }*/

        }
    }
}
