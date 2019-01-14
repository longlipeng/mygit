/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TransDetailsRecvTest.java
 * Author:   12073942
 * Date:     2013-11-2 下午4:11:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.data.prepare;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.suning.svc.datasync.service.TransDetailRecvService;
import com.suning.svc.datasync.utils.RscMessageUtil;
import com.suning.svc.datasync.xmlbean.trans.detail.ReceivedBodyBean;
import com.suning.svc.service.trade.intro.TradeIntroService;
import com.suning.svc.service.trade.intro.dto.TradeIntroDto;

/**
 * 交易数据接收测试
 * 
 * @author 12073942
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
public class TransDetailsRecvTest {

    @Autowired
    private TransDetailRecvService service;

    @Autowired
    private TradeIntroService service2;

    private String messageText = "<MbfService><input1><MbfHeader><ServiceCode>PrepaidCardMgmt_SAPFM</ServiceCode><Operation>syncTransactionDetail</Operation><AppCode>SAPFM</AppCode><UId>8CC474520DA1825DE1000000C0A876508DC474520DA1825D</UId><AuthId>bseR#_bKhh7C</AuthId></MbfHeader><MbfBody><parterner>SAP</parterner><details><item><mchtCode>RE2003</mchtCode><tradeType>0007</tradeType><billType>-1</billType><couponNo/><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>000</amount><orderNo/><itemDes/><itemRemark/><sign>473936B62419CD18EEA3B1991EA38F89</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0010</tradeType><billType>-1</billType><couponNo>05000100147539050001</couponNo><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>063</amount><orderNo/><itemDes/><itemRemark/><sign>AA9B7910F204C7CA0AE0CA88EA2D760B</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0007</tradeType><billType>-1</billType><couponNo/><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>000</amount><orderNo/><itemDes/><itemRemark/><sign>473936B62419CD18EEA3B1991EA38F89</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0010</tradeType><billType>-1</billType><couponNo>05000100147539050001</couponNo><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>063</amount><orderNo/><itemDes/><itemRemark/><sign>AA9B7910F204C7CA0AE0CA88EA2D760B</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0007</tradeType><billType>-1</billType><couponNo/><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>000</amount><orderNo/><itemDes/><itemRemark/><sign>473936B62419CD18EEA3B1991EA38F89</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0010</tradeType><billType>-1</billType><couponNo>05000100147539050001</couponNo><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>063</amount><orderNo/><itemDes/><itemRemark/><sign>AA9B7910F204C7CA0AE0CA88EA2D760B</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0007</tradeType><billType>-1</billType><couponNo/><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>000</amount><orderNo/><itemDes/><itemRemark/><sign>473936B62419CD18EEA3B1991EA38F89</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0010</tradeType><billType>-1</billType><couponNo>05000100147539050001</couponNo><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>063</amount><orderNo/><itemDes/><itemRemark/><sign>AA9B7910F204C7CA0AE0CA88EA2D760B</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0007</tradeType><billType>-1</billType><couponNo/><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>000</amount><orderNo/><itemDes/><itemRemark/><sign>473936B62419CD18EEA3B1991EA38F89</sign></item><item><mchtCode>RE2003</mchtCode><tradeType>0010</tradeType><billType>-1</billType><couponNo>05000100147539050001</couponNo><itemOrderNo>2013102911000000401</itemOrderNo><tradeTime>20131029120000</tradeTime><amount>063</amount><orderNo/><itemDes/><itemRemark/><sign>AA9B7910F204C7CA0AE0CA88EA2D760B</sign></item></details></MbfBody></input1></MbfService>";

    @Test
    public void test() {

        service.processBodyBean(RscMessageUtil.getBodyObject(messageText, ReceivedBodyBean.class));
    }

    @Test
    public void testSave() {
        TradeIntroDto dto = new TradeIntroDto();
        dto.setPartner("SAP");
        dto.setAmount("14940");
        dto.setBillType("1");
        dto.setCouponNo("6222014301079364202");
        dto.setItemDes("");
        dto.setItemOrderNo("1022480023");
        dto.setItemRemark("");
        dto.setMchtCode("RE5015");
        dto.setOrderNo("");
        dto.setTradeTime("20130701101217");
        dto.setTradeType("0008");

        service2.saveRecord(dto);
    }

}
