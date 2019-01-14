/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailRequest.java
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
 * EmailRequest
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmailRequest implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            EmailRequest.class, true);
    private java.lang.String data;
    private boolean __hashCodeCalc = false;
    private java.lang.String dataSplit;

    private java.lang.String email;

    private java.lang.String emailTag;

    private boolean encode;

    private java.lang.String fromNickname;

    private java.lang.Integer groupId;

    private java.lang.String nickname;

    private java.lang.Integer priority;

    private java.lang.Integer templateId;

    private boolean test;

    private java.lang.String wsPwd;

    private java.lang.String wsUser;
    private java.lang.Object __equalsCalc = null;
    public EmailRequest() {
    }

    public EmailRequest(java.lang.String data, java.lang.String dataSplit, java.lang.String email,
            java.lang.String emailTag, boolean encode, java.lang.String fromNickname, java.lang.Integer groupId,
            java.lang.String nickname, java.lang.Integer priority, java.lang.Integer templateId, boolean test,
            java.lang.String wsPwd, java.lang.String wsUser) {
        this.data = data;
        this.dataSplit = dataSplit;
        this.email = email;
        this.emailTag = emailTag;
        this.encode = encode;
        this.fromNickname = fromNickname;
        this.groupId = groupId;
        this.nickname = nickname;
        this.priority = priority;
        this.templateId = templateId;
        this.test = test;
        this.wsPwd = wsPwd;
        this.wsUser = wsUser;
    }

    /**
     * Gets the data value for this EmailRequest.
     * 
     * @return data
     */
    public java.lang.String getData() {
        return data;
    }

    /**
     * Sets the data value for this EmailRequest.
     * 
     * @param data
     */
    public void setData(java.lang.String data) {
        this.data = data;
    }

    /**
     * Gets the dataSplit value for this EmailRequest.
     * 
     * @return dataSplit
     */
    public java.lang.String getDataSplit() {
        return dataSplit;
    }

    /**
     * Sets the dataSplit value for this EmailRequest.
     * 
     * @param dataSplit
     */
    public void setDataSplit(java.lang.String dataSplit) {
        this.dataSplit = dataSplit;
    }

    /**
     * Gets the email value for this EmailRequest.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * Sets the email value for this EmailRequest.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * Gets the emailTag value for this EmailRequest.
     * 
     * @return emailTag
     */
    public java.lang.String getEmailTag() {
        return emailTag;
    }

    /**
     * Sets the emailTag value for this EmailRequest.
     * 
     * @param emailTag
     */
    public void setEmailTag(java.lang.String emailTag) {
        this.emailTag = emailTag;
    }

    /**
     * Gets the encode value for this EmailRequest.
     * 
     * @return encode
     */
    public boolean isEncode() {
        return encode;
    }

    /**
     * Sets the encode value for this EmailRequest.
     * 
     * @param encode
     */
    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    /**
     * Gets the fromNickname value for this EmailRequest.
     * 
     * @return fromNickname
     */
    public java.lang.String getFromNickname() {
        return fromNickname;
    }

    /**
     * Sets the fromNickname value for this EmailRequest.
     * 
     * @param fromNickname
     */
    public void setFromNickname(java.lang.String fromNickname) {
        this.fromNickname = fromNickname;
    }

    /**
     * Gets the groupId value for this EmailRequest.
     * 
     * @return groupId
     */
    public java.lang.Integer getGroupId() {
        return groupId;
    }

    /**
     * Sets the groupId value for this EmailRequest.
     * 
     * @param groupId
     */
    public void setGroupId(java.lang.Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets the nickname value for this EmailRequest.
     * 
     * @return nickname
     */
    public java.lang.String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname value for this EmailRequest.
     * 
     * @param nickname
     */
    public void setNickname(java.lang.String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets the priority value for this EmailRequest.
     * 
     * @return priority
     */
    public java.lang.Integer getPriority() {
        return priority;
    }

    /**
     * Sets the priority value for this EmailRequest.
     * 
     * @param priority
     */
    public void setPriority(java.lang.Integer priority) {
        this.priority = priority;
    }

    /**
     * Gets the templateId value for this EmailRequest.
     * 
     * @return templateId
     */
    public java.lang.Integer getTemplateId() {
        return templateId;
    }

    /**
     * Sets the templateId value for this EmailRequest.
     * 
     * @param templateId
     */
    public void setTemplateId(java.lang.Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * Gets the test value for this EmailRequest.
     * 
     * @return test
     */
    public boolean isTest() {
        return test;
    }

    /**
     * Sets the test value for this EmailRequest.
     * 
     * @param test
     */
    public void setTest(boolean test) {
        this.test = test;
    }

    /**
     * Gets the wsPwd value for this EmailRequest.
     * 
     * @return wsPwd
     */
    public java.lang.String getWsPwd() {
        return wsPwd;
    }

    /**
     * Sets the wsPwd value for this EmailRequest.
     * 
     * @param wsPwd
     */
    public void setWsPwd(java.lang.String wsPwd) {
        this.wsPwd = wsPwd;
    }

    /**
     * Gets the wsUser value for this EmailRequest.
     * 
     * @return wsUser
     */
    public java.lang.String getWsUser() {
        return wsUser;
    }

    /**
     * Sets the wsUser value for this EmailRequest.
     * 
     * @param wsUser
     */
    public void setWsUser(java.lang.String wsUser) {
        this.wsUser = wsUser;
    }



    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmailRequest)) {
            return false;
        }
        EmailRequest other = (EmailRequest) obj;
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
                && ((this.data == null && other.getData() == null) 
                        || (this.data != null && this.data.equals(other.getData())))
                && ((this.dataSplit == null && other.getDataSplit() == null) 
                        || (this.dataSplit != null && this.dataSplit
                        .equals(other.getDataSplit())))
                && ((this.email == null && other.getEmail() == null) || (this.email != null && this.email.equals(other
                        .getEmail())))
                && ((this.emailTag == null && other.getEmailTag() == null) || (this.emailTag != null && this.emailTag
                        .equals(other.getEmailTag())))
                && this.encode == other.isEncode()
                && ((this.fromNickname == null && 
                        other.getFromNickname() == null) || (this.fromNickname != null && this.fromNickname
                        .equals(other.getFromNickname())))
                && ((this.groupId == null && other.getGroupId() == null) || (this.groupId != null && this.groupId
                        .equals(other.getGroupId())))
                && ((this.nickname == null && other.getNickname() == null) || (this.nickname != null && this.nickname
                        .equals(other.getNickname())))
                && ((this.priority == null && other.getPriority() == null) || (this.priority != null && this.priority
                        .equals(other.getPriority())))
                && ((this.templateId == null && other.getTemplateId() == null) || (this.templateId != null 
                        && this.templateId.equals(other.getTemplateId())))
                && this.test == other.isTest()
                && ((this.wsPwd == null && other.getWsPwd() == null) || (this.wsPwd != null && this.wsPwd.equals(other
                        .getWsPwd())))
                && ((this.wsUser == null && other.getWsUser() == null) || (this.wsUser != null && this.wsUser
                        .equals(other.getWsUser())));
        __equalsCalc = null;
        return _equals;
    }



    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getDataSplit() != null) {
            _hashCode += getDataSplit().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getEmailTag() != null) {
            _hashCode += getEmailTag().hashCode();
        }
        _hashCode += (isEncode() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getFromNickname() != null) {
            _hashCode += getFromNickname().hashCode();
        }
        if (getGroupId() != null) {
            _hashCode += getGroupId().hashCode();
        }
        if (getNickname() != null) {
            _hashCode += getNickname().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getTemplateId() != null) {
            _hashCode += getTemplateId().hashCode();
        }
        _hashCode += (isTest() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getWsPwd() != null) {
            _hashCode += getWsPwd().hashCode();
        }
        if (getWsUser() != null) {
            _hashCode += getWsUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }


    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.esb.suning.redm.hanqinet.com/", "emailRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataSplit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataSplit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailTag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailTag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "encode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromNickname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fromNickname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nickname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nickname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "templateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("test");
        elemField.setXmlName(new javax.xml.namespace.QName("", "test"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsPwd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsPwd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsUser");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsUser"));
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
