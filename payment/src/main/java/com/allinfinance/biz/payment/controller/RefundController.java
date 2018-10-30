package com.allinfinance.biz.payment.controller;

import com.alibaba.fastjson.JSON;
import com.allinfinance.biz.payment.entity.RefundRequestInfo;
import com.allinfinance.biz.payment.entity.RefundRespondInfo;
import com.allinfinance.biz.payment.service.RefundService;
import com.util.PojoChangeToMap;
import com.util.RSASignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by liuheli on 2017/5/23.
 * 交易退款接口
 */
@RestController
public class RefundController {

    private static Logger logger = Logger.getLogger(RefundController.class);

    @Autowired
    private RefundService refundService;

    @RequestMapping(value = "/refundSend", method = RequestMethod.POST)
    public String refundSend(RefundRequestInfo refundRequestInfo) {
        logger.info("refundRequestInfo------------------------>"+refundRequestInfo);
//        PaymentRequestInfo paymentRequestInfo = JSONObject.toJavaObject(jsonObject,PaymentRequestInfo.class);
        PojoChangeToMap pojoChangeToMap = new PojoChangeToMap();
        HashMap<String, String> infoMap = pojoChangeToMap.pojoSwapToMap(refundRequestInfo);
        logger.info("infoMap------------------------>"+infoMap);
        //sing验证
        boolean result = RSASignUtils.verifySign(infoMap, RSASignUtils.PUBLICKEY, infoMap.get("sign"));
        logger.info("result------------------------>"+result);
        if(result){
            return refundService.dealWithRespondInfo(refundRequestInfo);
        }else{
            RefundRespondInfo refundRespondInfo = new RefundRespondInfo();
            refundRespondInfo = (RefundRespondInfo) pojoChangeToMap.setRespodInfo(refundRequestInfo, refundRespondInfo);
            refundRespondInfo.setResult("false");
            refundRespondInfo.setMsg("签名校验结果失败");
            return JSON.toJSONString(pojoChangeToMap.pojoSwapToMap(refundRespondInfo));
        }
    }
}
