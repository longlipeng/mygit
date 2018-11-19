/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ContactInfoDTO.java
 * Author:   12073942
 * Date:     2013-10-25 下午5:32:26
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.synch.dto;

/**
 * 客户同步DTO中的联系人信息
 * 
 * @author LEO
 */
public class ContactInfoDTO {

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTelephone;

    /**
     * 联系人手机
     */
    private String contactMobilePhone;

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the contactTelephone
     */
    public String getContactTelephone() {
        return contactTelephone;
    }

    /**
     * @param contactTelephone the contactTelephone to set
     */
    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    /**
     * @return the contactMobilePhone
     */
    public String getContactMobilePhone() {
        return contactMobilePhone;
    }

    /**
     * @param contactMobilePhone the contactMobilePhone to set
     */
    public void setContactMobilePhone(String contactMobilePhone) {
        this.contactMobilePhone = contactMobilePhone;
    }

}
