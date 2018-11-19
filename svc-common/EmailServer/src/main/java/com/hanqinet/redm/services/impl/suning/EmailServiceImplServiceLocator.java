/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailServiceImplServiceLocator.java
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
 * EmailServiceImplServiceLocator
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmailServiceImplServiceLocator extends org.apache.axis.client.Service implements
        com.hanqinet.redm.services.impl.suning.EmailServiceImplService {

    public static final String EMAIL_SERVICE_IMPL_PORT = "EmailServiceImplPort";
    // Use to get a proxy class for EmailServiceImplPort
    private java.lang.String EmailServiceImplPort_address = 
        "http://192.168.102.54:8080/smartredm/cxfservices/EmailService";
    private java.lang.String EmailServiceImplPortWSDDServiceName = "EmailServiceImplPort";
    private java.util.HashSet ports = null;
    public EmailServiceImplServiceLocator() {
    }

    public EmailServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EmailServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
        throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }



    public java.lang.String getEmailServiceImplPortAddress() {
        return EmailServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.


    public java.lang.String getEmailServiceImplPortWSDDServiceName() {
        return EmailServiceImplPortWSDDServiceName;
    }

    public void setEmailServiceImplPortWSDDServiceName(java.lang.String name) {
        EmailServiceImplPortWSDDServiceName = name;
    }

    public com.hanqinet.redm.suning.esb.ws.EmailService getEmailServiceImplPort() 
        throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EmailServiceImplPort_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEmailServiceImplPort(endpoint);
    }

    public com.hanqinet.redm.suning.esb.ws.EmailService getEmailServiceImplPort(java.net.URL portAddress)
        throws javax.xml.rpc.ServiceException {
        try {
            com.hanqinet.redm.services.impl.suning.EmailServiceImplServiceSoapBindingStub _stub = new com.hanqinet.redm.services.impl.suning.EmailServiceImplServiceSoapBindingStub(
                    portAddress, this);
            _stub.setPortName(getEmailServiceImplPortWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEmailServiceImplPortEndpointAddress(java.lang.String address) {
        EmailServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.hanqinet.redm.suning.esb.ws.EmailService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.hanqinet.redm.services.impl.suning.EmailServiceImplServiceSoapBindingStub _stub = new com.hanqinet.redm.services.impl.suning.EmailServiceImplServiceSoapBindingStub(
                        new java.net.URL(EmailServiceImplPort_address), this);
                _stub.setPortName(getEmailServiceImplPortWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
                + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has no port for the given interface, then
     * ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
        throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if (EMAIL_SERVICE_IMPL_PORT.equals(inputPortName)) {
            return getEmailServiceImplPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://suning.impl.services.redm.hanqinet.com/",
                "EmailServiceImplService");
    }



    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://suning.impl.services.redm.hanqinet.com/",
                    EMAIL_SERVICE_IMPL_PORT));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address)
        throws javax.xml.rpc.ServiceException {

        if (EMAIL_SERVICE_IMPL_PORT.equals(portName)) {
            setEmailServiceImplPortEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
        throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
