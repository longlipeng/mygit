/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailServiceImplService.java
 * Author:   12073942
 * Date:     2013-7-4 上午10:51:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.hanqinet.redm.services.impl.suning;

/**
 * 
 * EmailServiceImplService
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface EmailServiceImplService extends javax.xml.rpc.Service {

    java.lang.String getEmailServiceImplPortAddress();

    com.hanqinet.redm.suning.esb.ws.EmailService getEmailServiceImplPort() throws javax.xml.rpc.ServiceException;

    com.hanqinet.redm.suning.esb.ws.EmailService getEmailServiceImplPort(java.net.URL portAddress)
        throws javax.xml.rpc.ServiceException;

}
