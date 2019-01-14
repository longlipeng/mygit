/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MbfResHeader.java
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
 * MbfResHeader
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MbfResHeader implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            MbfResHeader.class, true);
    private java.lang.String serviceCode;
    private java.lang.Object __equalsCalc = null;
    private java.lang.String operation;
    private boolean __hashCodeCalc = false;
    private java.lang.String appCode;

    private java.lang.String UId;

    private com.hanqinet.redm.suning.esb.ws.MbfServiceResponse serviceResponse;

    public MbfResHeader() {
    }

    public MbfResHeader(java.lang.String serviceCode, java.lang.String operation, java.lang.String appCode,
            java.lang.String UId, com.hanqinet.redm.suning.esb.ws.MbfServiceResponse serviceResponse) {
        this.serviceCode = serviceCode;
        this.operation = operation;
        this.appCode = appCode;
        this.UId = UId;
        this.serviceResponse = serviceResponse;
    }

    /**
     * Gets the serviceCode value for this MbfResHeader.
     * 
     * @return serviceCode
     */
    public java.lang.String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the serviceCode value for this MbfResHeader.
     * 
     * @param serviceCode
     */
    public void setServiceCode(java.lang.String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * Gets the operation value for this MbfResHeader.
     * 
     * @return operation
     */
    public java.lang.String getOperation() {
        return operation;
    }

    /**
     * Sets the operation value for this MbfResHeader.
     * 
     * @param operation
     */
    public void setOperation(java.lang.String operation) {
        this.operation = operation;
    }

    /**
     * Gets the appCode value for this MbfResHeader.
     * 
     * @return appCode
     */
    public java.lang.String getAppCode() {
        return appCode;
    }

    /**
     * Sets the appCode value for this MbfResHeader.
     * 
     * @param appCode
     */
    public void setAppCode(java.lang.String appCode) {
        this.appCode = appCode;
    }

    /**
     * Gets the UId value for this MbfResHeader.
     * 
     * @return UId
     */
    public java.lang.String getUId() {
        return UId;
    }

    /**
     * Sets the UId value for this MbfResHeader.
     * 
     * @param UId
     */
    public void setUId(java.lang.String UId) {
        this.UId = UId;
    }

    /**
     * Gets the serviceResponse value for this MbfResHeader.
     * 
     * @return serviceResponse
     */
    public com.hanqinet.redm.suning.esb.ws.MbfServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    /**
     * Sets the serviceResponse value for this MbfResHeader.
     * 
     * @param serviceResponse
     */
    public void setServiceResponse(com.hanqinet.redm.suning.esb.ws.MbfServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }



    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MbfResHeader)) {
            return false;
        }
        MbfResHeader other = (MbfResHeader) obj;
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
                && ((this.serviceCode == null && other.getServiceCode() == null) || 
                        (this.serviceCode != null && this.serviceCode
                        .equals(other.getServiceCode())))
                && ((this.operation == null && other.getOperation() == null) || 
                        (this.operation != null && this.operation
                        .equals(other.getOperation())))
                && ((this.appCode == null && other.getAppCode() == null) || (this.appCode != null && this.appCode
                        .equals(other.getAppCode())))
                && ((this.UId == null && other.getUId() == null) || (this.UId != null && this.UId
                        .equals(other.getUId())))
                && ((this.serviceResponse == null && other.getServiceResponse() == null) || 
                        (this.serviceResponse != null && this.serviceResponse
                        .equals(other.getServiceResponse())));
        __equalsCalc = null;
        return _equals;
    }



    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getOperation() != null) {
            _hashCode += getOperation().hashCode();
        }
        if (getAppCode() != null) {
            _hashCode += getAppCode().hashCode();
        }
        if (getUId() != null) {
            _hashCode += getUId().hashCode();
        }
        if (getServiceResponse() != null) {
            _hashCode += getServiceResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/", "mbfResHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ServiceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Operation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AppCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ServiceResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/",
                "mbfServiceResponse"));
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
