/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailServiceProxy.java
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
 * EmailServiceProxy
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmailServiceProxy implements com.hanqinet.redm.suning.esb.ws.EmailService {
    private String _endpoint = null;
    private com.hanqinet.redm.suning.esb.ws.EmailService emailService = null;

    public EmailServiceProxy() {
        _initEmailServiceProxy();
    }

    public EmailServiceProxy(String endpoint) {
        _endpoint = endpoint;
        _initEmailServiceProxy();
    }

    private void _initEmailServiceProxy() {
        try {
            emailService = (new com.hanqinet.redm.services.impl.suning.EmailServiceImplServiceLocator())
                    .getEmailServiceImplPort();
            if (emailService != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) emailService)._setProperty("javax.xml.rpc.service.endpoint.address",
                            _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) emailService)
                            ._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (emailService != null) {
            ((javax.xml.rpc.Stub) emailService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }
    }

    public com.hanqinet.redm.suning.esb.ws.EmailService getEmailService() {
        if (emailService == null) {
            _initEmailServiceProxy();
        }
        return emailService;
    }

    public com.hanqinet.redm.suning.esb.ws.MbfResponse addEmail(com.hanqinet.redm.suning.esb.ws.MbfRequest input1)
        throws java.rmi.RemoteException {
        if (emailService == null) {
            _initEmailServiceProxy();
        }
        return emailService.addEmail(input1);
    }

}