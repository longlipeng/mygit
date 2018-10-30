package com.allinfinance.biz.payment.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuheli on 2017/5/10.
 */
@Configuration
@ImportResource({"classpath:rabbit.xml"})
public class PaymentConfig {

    @Value("${CXF_CARDBIN}")
    private String cxfCardBin;//多用途卡前五位

    @Value("${MULTI_AREA_CODE}")
    private String multiAreaCode;//多用途受理地区的地区码

    @Value("${SPECIAL_CARDBIN}")
    private String specialCardBin;//特殊用途卡前五位

    private static Logger logger = Logger.getLogger(PaymentConfig.class);

    public static List<String> LIST_CARD_BIN = null;

    public static List<String> MULTI_AREA_CODE = null;

    public static String SPECIAL_CARD_BIN;

    //启动服务初始化必要参数
    @PostConstruct
    public void setListCardBin(){
        LIST_CARD_BIN = Arrays.asList
                (cxfCardBin.trim().split(","));
        logger.info("调用setListCardBin方法；初始化多用途卡前五位信息："+LIST_CARD_BIN.toString());

        MULTI_AREA_CODE = Arrays.asList
                (multiAreaCode.trim().split(","));
        logger.info("调用setListCardBin方法；初始化多用途受理地区的地区码信息："+MULTI_AREA_CODE.toString());

        SPECIAL_CARD_BIN = specialCardBin;
        logger.info("调用setListCardBin方法；初始化特殊用途卡前五位信息："+SPECIAL_CARD_BIN);
    }
}
