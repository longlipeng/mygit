/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailService.java
 * Author:   12073942
 * Date:     2013-7-4 上午10:51:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.hanqinet.redm.suning.esb.ws;

/**
 * 
 * EmailService
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface EmailService extends java.rmi.Remote {
    com.hanqinet.redm.suning.esb.ws.MbfResponse addEmail(com.hanqinet.redm.suning.esb.ws.MbfRequest input1)
        throws java.rmi.RemoteException;
}
