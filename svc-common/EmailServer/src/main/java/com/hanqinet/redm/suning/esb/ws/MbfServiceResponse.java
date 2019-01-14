/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MbfServiceResponse.java
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
 * MbfServiceResponse
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MbfServiceResponse implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            MbfServiceResponse.class, true);
    private java.lang.String status;
    private java.lang.Object __equalsCalc = null;
    private java.lang.String code;
    private boolean __hashCodeCalc = false;
    private java.lang.String desc;

    public MbfServiceResponse() {
    }

    public MbfServiceResponse(java.lang.String status, java.lang.String code, java.lang.String desc) {
        this.status = status;
        this.code = code;
        this.desc = desc;
    }

    /**
     * Gets the status value for this MbfServiceResponse.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }

    /**
     * Sets the status value for this MbfServiceResponse.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    /**
     * Gets the code value for this MbfServiceResponse.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * Sets the code value for this MbfServiceResponse.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }

    /**
     * Gets the desc value for this MbfServiceResponse.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }

    /**
     * Sets the desc value for this MbfServiceResponse.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }



    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MbfServiceResponse)) {
            return false;
        }
        MbfServiceResponse other = (MbfServiceResponse) obj;
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
                && ((this.status == null && other.getStatus() == null) || (this.status != null && this.status
                        .equals(other.getStatus())))
                && ((this.code == null && other.getCode() == null) || (this.code != null && this.code.equals(other
                        .getCode())))
                && ((this.desc == null && other.getDesc() == null) || (this.desc != null && this.desc.equals(other
                        .getDesc())));
        __equalsCalc = null;
        return _equals;
    }



    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/",
                "mbfServiceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Desc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
