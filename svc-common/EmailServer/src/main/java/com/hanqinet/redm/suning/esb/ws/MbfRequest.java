/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MbfRequest.java
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
 * MbfRequest
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MbfRequest implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            MbfRequest.class, true);
    private com.hanqinet.redm.suning.esb.ws.MbfReqHeader mbfHeader;
    private java.lang.Object __equalsCalc = null;
    private com.hanqinet.redm.suning.esb.ws.MbfAddEmailReqBody mbfBody;
    private boolean __hashCodeCalc = false;
    public MbfRequest() {
    }

    public MbfRequest(com.hanqinet.redm.suning.esb.ws.MbfReqHeader mbfHeader,
            com.hanqinet.redm.suning.esb.ws.MbfAddEmailReqBody mbfBody) {
        this.mbfHeader = mbfHeader;
        this.mbfBody = mbfBody;
    }

    /**
     * Gets the mbfHeader value for this MbfRequest.
     * 
     * @return mbfHeader
     */
    public com.hanqinet.redm.suning.esb.ws.MbfReqHeader getMbfHeader() {
        return mbfHeader;
    }

    /**
     * Sets the mbfHeader value for this MbfRequest.
     * 
     * @param mbfHeader
     */
    public void setMbfHeader(com.hanqinet.redm.suning.esb.ws.MbfReqHeader mbfHeader) {
        this.mbfHeader = mbfHeader;
    }

    /**
     * Gets the mbfBody value for this MbfRequest.
     * 
     * @return mbfBody
     */
    public com.hanqinet.redm.suning.esb.ws.MbfAddEmailReqBody getMbfBody() {
        return mbfBody;
    }

    /**
     * Sets the mbfBody value for this MbfRequest.
     * 
     * @param mbfBody
     */
    public void setMbfBody(com.hanqinet.redm.suning.esb.ws.MbfAddEmailReqBody mbfBody) {
        this.mbfBody = mbfBody;
    }



    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MbfRequest)) {
            return false;
        }
        MbfRequest other = (MbfRequest) obj;
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
        _equals = true
                && ((this.mbfHeader == null && other.getMbfHeader() == null) || (this.mbfHeader != null && this.mbfHeader
                        .equals(other.getMbfHeader())))
                && ((this.mbfBody == null && other.getMbfBody() == null) || (this.mbfBody != null && this.mbfBody
                        .equals(other.getMbfBody())));
        __equalsCalc = null;
        return _equals;
    }



    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMbfHeader() != null) {
            _hashCode += getMbfHeader().hashCode();
        }
        if (getMbfBody() != null) {
            _hashCode += getMbfBody().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }



    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/", "mbfRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mbfHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MbfHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/", "mbfReqHeader"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mbfBody");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MbfBody"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/",
                "mbfAddEmailReqBody"));
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
