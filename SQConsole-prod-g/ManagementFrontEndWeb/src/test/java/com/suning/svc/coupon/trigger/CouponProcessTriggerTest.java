/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CouponProcessTriggerTest.java
 * Author:   秦伟
 * Date:     2013-11-5 下午2:37:11
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.trigger;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})

public class CouponProcessTriggerTest {

    @Autowired
    CouponProcessTrigger trigger;
    /**
     * Test method for {@link com.suning.svc.coupon.trigger.CouponProcessTrigger#execute()}.
     * @throws Exception 
     */
    @Test
    public void testExecute() throws Exception {
        trigger.execute();
    }

}
