/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MbfAddEmailReqBody.java
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
 * MbfAddEmailReqBody
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MbfAddEmailReqBody implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            MbfAddEmailReqBody.class, true);
    private com.hanqinet.redm.suning.esb.ws.EmailRequest request;
    private java.lang.Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;
    public MbfAddEmailReqBody() {
    }

    public MbfAddEmailReqBody(com.hanqinet.redm.suning.esb.ws.EmailRequest request) {
        this.request = request;
    }

    /**
     * Gets the request value for this MbfAddEmailReqBody.
     * 
     * @return request
     */
    public com.hanqinet.redm.suning.esb.ws.EmailRequest getRequest() {
        return request;
    }

    /**
     * Sets the request value for this MbfAddEmailReqBody.
     * 
     * @param request
     */
    public void setRequest(com.hanqinet.redm.suning.esb.ws.EmailRequest request) {
        this.request = request;
    }



    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MbfAddEmailReqBody)) {
            return false;
        }
        MbfAddEmailReqBody other = (MbfAddEmailReqBody) obj;
//        if (obj == null) {
//            return false;
//        }
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return __equalsCalc == obj;
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && ((this.request == null && other.getRequest() == null) || (this.request != null && this.request
                .equals(other.getRequest())));
        __equalsCalc = null;
        return _equals;
    }



    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRequest() != null) {
            _hashCode += getRequest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/",
                "mbfAddEmailReqBody"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("", "request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/", "emailRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
            java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
            java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
    }

}
