/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SerialNumberUtilTest.java
 * Author:   秦伟
 * Date:     2013-10-29 下午8:57:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
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

public class SerialNumberUtilTest {

    /**
     * Test method for {@link com.suning.svc.coupon.util.SerialNumberUtil#getSerialNumberOf16BySeqName(java.lang.String)}.
     */
    @Test
    public void testGetSerialNumberOf16BySeqName() {
        System.out.println(SerialNumberUtil.getSerialNumberOf16BySeqName("seq_settle_no"));
    }

}
