package com.allinfinance.biz.payment.service;

import com.alibaba.fastjson.JSON;
import com.allinfinance.biz.payment.config.PaymentConfig;
import com.allinfinance.biz.payment.entity.RefundRequestInfo;
import com.allinfinance.biz.payment.entity.RefundRespondInfo;
import com.allinfinance.biz.payment.send.PaymentSend;
import com.thoughtworks.xstream.XStream;
import com.util.Decoder;
import com.util.Encoder;
import com.util.PojoChangeToMap;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by liuheli on 2017/5/23.
 */
@Service
public class RefundService {

    private static Logger logger = Logger.getLogger(RefundService.class);

    @Autowired
    private PaymentSend paymentSend;

    //处理消息队列响应报文
    public String dealWithRespondInfo(RefundRequestInfo refundRequestInfo){
        PojoChangeToMap pojoChangeToMap = new PojoChangeToMap();
        RefundRespondInfo refundRespondInfo = new RefundRespondInfo();
        refundRespondInfo = (RefundRespondInfo) pojoChangeToMap.setRespodInfo(refundRequestInfo, refundRespondInfo);
        try{
            refundRequestInfo.setTran_code("00002101");
            String info = Encoder.toXml("DATA", refundRequestInfo);
            logger.info("发送信息报文：\n" + info);

            String xmlResp = paymentSend.sendMsg(info, refundRequestInfo.getCard_no(), true);

            if(StringUtils.isEmpty(xmlResp)){
                refundRespondInfo.setResult("false");
                refundRespondInfo.setMsg("监听消息队列超时,退款申请失败");
            }else{
                //将响应报文XMLString类型转换成实体类
                refundRespondInfo = (RefundRespondInfo) Decoder.parseXml(new XStream(), xmlResp,"DATA",RefundRespondInfo.class);
            }
            refundRespondInfo.setTran_code(null);
        } catch (UnsupportedEncodingException uee) {
            logger.info(uee.getMessage());
            refundRespondInfo.setResult("false");
            refundRespondInfo.setMsg("系统错误,退款申请失败");
        }catch (AmqpException aqe) {
            logger.info(aqe.getMessage());
            refundRespondInfo.setResult("false");
            refundRespondInfo.setMsg("系统错误,退款申请失败");
        }catch (NumberFormatException nfe) {
            logger.info(nfe.getMessage());
            refundRespondInfo.setResult("false");
            refundRespondInfo.setMsg("系统错误,退款申请失败");
        } catch (IOException ioe) {
            logger.info(ioe.getMessage());
            refundRespondInfo.setResult("false");
            refundRespondInfo.setMsg("系统错误,退款申请失败");
        }catch (Exception e) {
            logger.info(e.getMessage());
            refundRespondInfo.setResult("false");
            refundRespondInfo.setMsg("系统错误,退款申请失败");
        }
        //实体报文加密处理返回给请求
        return JSON.toJSONString(pojoChangeToMap.pojoSwapToMap(refundRespondInfo));
    }
}
