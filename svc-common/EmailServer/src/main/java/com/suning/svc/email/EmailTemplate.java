/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailTemplate.java
 * Author:   12073942
 * Date:     2013-7-1 下午8:02:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.email;

/**
 * 
 * 邮件模板
 * 
 * @author 秦伟
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum EmailTemplate {

    /**
     * 持卡人网站账户激活邮件模板
     */
    ACTIVE(19),
    /**
     * 持卡人网站账户找回密码邮件模板
     */
    FIND_PASSWD(20);

    private int id;

    private EmailTemplate(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
