/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: FtpUtilTest.java
 * Author:   秦伟
 * Date:     2013-12-14 下午3:20:57
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.framework.util;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.dbunit.Assertion;
import org.junit.Test;

import com.suning.svc.coupon.constants.SettlementConstants;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FtpUtilTest {

    /**
     * Test method for {@link com.huateng.framework.util.FtpUtil#uploadFile(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.InputStream)}.
     * @throws Exception 
     * @throws FileNotFoundException 
     */
    @Test
    public void testUploadFile() throws FileNotFoundException, Exception {
        //boolean success = FtpUtil.uploadFile(SettlementConstants.SEND_SOP_IP, 21, SettlementConstants.SEND_SOP_USERNAME, SettlementConstants.SEND_SOP_PASSWORD, "coupon/d1", "temp_target2", new FileInputStream("d:\\coupon2.log"));
        //assertEquals(true, success);
    }


}
