package com.allinfinance.biz.payment.controller;

import com.alibaba.fastjson.JSON;
import com.allinfinance.biz.payment.entity.QueryRequestInfo;
import com.allinfinance.biz.payment.entity.QueryRespondInfo;
import com.allinfinance.biz.payment.service.QueryService;
import com.util.PojoChangeToMap;
import com.util.RSASignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by liuheli on 2017/5/11.
 * 交易查询接口、交易退款查询接口
 */
@RestController
public class QueryController {

    private static Logger logger = Logger.getLogger(QueryController.class);

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/querySend", method = RequestMethod.POST)
    public String querySend(QueryRequestInfo queryRequestInfo){
        logger.info("queryRequestInfo------------------------>"+queryRequestInfo);
//        QueryRequestInfo queryRequestInfo = JSONObject.toJavaObject(jsonObject,QueryRequestInfo.class);
        PojoChangeToMap pojoChangeToMap = new PojoChangeToMap();
        HashMap<String, String> infoMap = pojoChangeToMap.pojoSwapToMap(queryRequestInfo);
        logger.info("infoMap------------------------>"+infoMap);
        //sing验证
        boolean result = RSASignUtils.verifySign(infoMap, RSASignUtils.PUBLICKEY, infoMap.get("sign"));
        logger.info("result------------------------>"+result);
        if(result){
            return queryService.dealWithRespondInfo(queryRequestInfo);
        }else{
            QueryRespondInfo queryRespondInfo = new QueryRespondInfo();
            queryRespondInfo = (QueryRespondInfo) pojoChangeToMap.setRespodInfo(queryRequestInfo, queryRespondInfo);
            queryRespondInfo.setResult("false");
            queryRespondInfo.setMsg("签名校验结果失败");
            return  JSON.toJSONString(pojoChangeToMap.pojoSwapToMap(queryRespondInfo));
        }
    }
}
