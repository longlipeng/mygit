/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailSender.java
 * Author:   12073942
 * Date:     2013-7-1 下午8:02:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.email;

import com.suning.framework.lang.Result;

/**
 * 
 * 邮件服务接口
 * 
 * @author 秦伟
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface EmailSender {

    /**
     * 
     * 发送邮件
     * 
     * @param type 邮件模板 比如 ：激活账户邮件，找回密码邮件
     * @param receiver 收件人邮箱
     * @param params 模板参数，以逗号分隔
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    Result sendEmail(EmailTemplate template, String receiver, String params);
}
