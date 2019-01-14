/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailResponse.java
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
 * EmailResponse
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmailResponse implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            EmailResponse.class, true);
    private int emailId;
    private java.lang.Object __equalsCalc = null;
    private java.lang.String msg;
    private boolean __hashCodeCalc = false;
    private boolean success;

    public EmailResponse() {
    }

    public EmailResponse(int emailId, java.lang.String msg, boolean success) {
        this.emailId = emailId;
        this.msg = msg;
        this.success = success;
    }

    /**
     * Gets the emailId value for this EmailResponse.
     * 
     * @return emailId
     */
    public int getEmailId() {
        return emailId;
    }

    /**
     * Sets the emailId value for this EmailResponse.
     * 
     * @param emailId
     */
    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    /**
     * Gets the msg value for this EmailResponse.
     * 
     * @return msg
     */
    public java.lang.String getMsg() {
        return msg;
    }

    /**
     * Sets the msg value for this EmailResponse.
     * 
     * @param msg
     */
    public void setMsg(java.lang.String msg) {
        this.msg = msg;
    }

    /**
     * Gets the success value for this EmailResponse.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success value for this EmailResponse.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }



    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmailResponse)) {
            return false;
        }
        EmailResponse other = (EmailResponse) obj;
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
                && this.emailId == other.getEmailId()
                && ((this.msg == null && other.getMsg() == null) || (this.msg != null && this.msg
                        .equals(other.getMsg()))) && this.success == other.isSuccess();
        __equalsCalc = null;
        return _equals;
    }


    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getEmailId();
        if (getMsg() != null) {
            _hashCode += getMsg().hashCode();
        }
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/", "emailResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "msg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("", "success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
