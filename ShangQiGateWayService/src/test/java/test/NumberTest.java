/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: NumberTest.java
 * Author:   秦伟
 * Date:     2013-12-9 下午7:10:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package test;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class NumberTest {

    @Test
    public void testDouble() {
        double d = 18.4;
        //double d = 119;
        System.out.println(d * 100);

        BigDecimal b1 = new BigDecimal(Double.toString(d));
        BigDecimal b2 = new BigDecimal(Double.toString(100));

        System.out.println(new Double(b1.multiply(b2).doubleValue()).intValue());

        System.out.println(MoneyUtils.fromYuanToFen("18.4"));
    }
}
