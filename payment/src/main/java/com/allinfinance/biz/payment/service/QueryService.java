package com.allinfinance.biz.payment.service;

import com.alibaba.fastjson.JSON;
import com.allinfinance.biz.payment.config.PaymentConfig;
import com.allinfinance.biz.payment.entity.QueryRequestInfo;
import com.allinfinance.biz.payment.entity.QueryRespondInfo;
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
 * Created by liuheli on 2017/5/11.
 */
@Service
public class QueryService {

    private static Logger logger = Logger.getLogger(QueryService.class);

    @Autowired
    private PaymentSend paymentSend;

    //处理消息队列响应报文
    public String dealWithRespondInfo(QueryRequestInfo queryRequestInfo){
        PojoChangeToMap pojoChangeToMap = new PojoChangeToMap();
        QueryRespondInfo queryRespondInfo = new QueryRespondInfo();
        queryRespondInfo = (QueryRespondInfo) pojoChangeToMap.setRespodInfo(queryRequestInfo, queryRespondInfo);
        try{
            queryRequestInfo.setTran_code("09000018");
            String info = Encoder.toXml("DATA", queryRequestInfo);
            logger.info("发送信息报文：\n" + info);
            boolean isRefund = queryRequestInfo.getOut_refund_no()== null ? false : true;
            String xmlResp = paymentSend.sendMsg(info, queryRequestInfo.getCard_no(), isRefund);

            if(StringUtils.isEmpty(xmlResp)){
                queryRespondInfo.setResult("false");
                queryRespondInfo.setMsg("监听消息队列超时,查询失败");
            }else{
                //将响应报文XMLString类型转换成实体类
                queryRespondInfo = (QueryRespondInfo) Decoder.parseXml(new XStream(), xmlResp,"DATA",QueryRespondInfo.class);
            }
            queryRespondInfo.setTran_code(null);
        } catch (UnsupportedEncodingException uee) {
            logger.info(uee.getMessage());
            queryRespondInfo.setResult("false");
            queryRespondInfo.setMsg("系统错误,查询失败");
        }catch (AmqpException aqe) {
            logger.info(aqe.getMessage());
            queryRespondInfo.setResult("false");
            queryRespondInfo.setMsg("系统错误,查询失败");
        }catch (NumberFormatException nfe) {
            logger.info(nfe.getMessage());
            queryRespondInfo.setResult("false");
            queryRespondInfo.setMsg("系统错误,查询失败");
        } catch (IOException ioe) {
            logger.info(ioe.getMessage());
            queryRespondInfo.setResult("false");
            queryRespondInfo.setMsg("系统错误,查询失败");
        }catch (Exception e) {
            logger.info(e.getMessage());
            queryRespondInfo.setResult("false");
            queryRespondInfo.setMsg("系统错误,查询失败");
        }
        //实体报文加密处理返回给请求
        return JSON.toJSONString(pojoChangeToMap.pojoSwapToMap(queryRespondInfo));
    }
}
