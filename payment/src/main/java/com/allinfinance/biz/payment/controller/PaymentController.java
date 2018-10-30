package com.allinfinance.biz.payment.controller;

import com.alibaba.fastjson.JSON;
import com.allinfinance.biz.payment.entity.PaymentRequestInfo;
import com.allinfinance.biz.payment.entity.PaymentRespondInfo;
import com.allinfinance.biz.payment.service.PaymentService;
import com.util.PojoChangeToMap;
import com.util.RSASignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by liuheli on 2017/5/10.
 * 交易支付接口
 */
@RestController
public class PaymentController {

    private static Logger logger = Logger.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/paymentSend", method = RequestMethod.POST)
    public String paymentSend(PaymentRequestInfo paymentRequestInfo) {
        logger.info("paymentRequestInfo------------------------>"+paymentRequestInfo);
//        PaymentRequestInfo paymentRequestInfo = JSONObject.toJavaObject(jsonObject,PaymentRequestInfo.class);
        PojoChangeToMap pojoChangeToMap = new PojoChangeToMap();
        HashMap<String, String> infoMap = pojoChangeToMap.pojoSwapToMap(paymentRequestInfo);
        logger.info("infoMap------------------------>"+infoMap);
        //sing验证
        boolean result = RSASignUtils.verifySign(infoMap, RSASignUtils.PUBLICKEY, infoMap.get("sign"));
        logger.info("result------------------------>"+result);
        if(result){
            return paymentService.dealWithRespondInfo(paymentRequestInfo, paymentRequestInfo.getNotify_url());
        }else{
            PaymentRespondInfo paymentRespondInfo = new PaymentRespondInfo();
            paymentRespondInfo = (PaymentRespondInfo) pojoChangeToMap.setRespodInfo(paymentRequestInfo, paymentRespondInfo);
            paymentRespondInfo.setResult("false");
            paymentRespondInfo.setMsg("签名校验结果失败");
            return JSON.toJSONString(pojoChangeToMap.pojoSwapToMap(paymentRespondInfo));
        }
    }
}
