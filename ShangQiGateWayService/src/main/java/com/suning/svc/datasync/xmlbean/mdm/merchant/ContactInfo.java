/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: T_ZMDIFS052.java
 * Author:   luwanchuan
 * Date:     2013-4-22 下午04:54:10
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.merchant;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 〈联系人信息〉<br>
 * 〈MDM下发的供应商（商户）数据，包含的联系人信息，本系统不需要，只接收，不处理〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS052")
public class ContactInfo {

    @XStreamAlias("SUPPLIER_CODE")
    private String supplierCode;

    @XStreamAlias("CONTACT_PERSON_POSITION_CODE")
    private String contactPersonPositionCode;

    @XStreamAlias("CONTACT_PERSON_POSITION_DESC")
    private String contactPersonPositionDesc;

    @XStreamAlias("CONTACT_PERSON_NAME")
    private String contactPersonName;

    @XStreamAlias("CONTACT_PERSON_TEL")
    private String contactPersonTel;

    @XStreamAlias("CONTACT_PERSON_MOBILE")
    private String contactPersonMobile;

    @XStreamAlias("CONTACT_PERSON_EMAIL")
    private String contactPersonEmall;

    @XStreamAlias("CONTACT_PERSON_DEPT_CODE")
    private String contactPersonDeptCode;

    @XStreamAlias("CONTACT_PERSON_DEPT_DESC")
    private String contactPersonDeptDesc;

    /**
     * @return the supplierCode
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * @param supplierCode the supplierCode to set
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    /**
     * @return the contactPersonPositionCode
     */
    public String getContactPersonPositionCode() {
        return contactPersonPositionCode;
    }

    /**
     * @param contactPersonPositionCode the contactPersonPositionCode to set
     */
    public void setContactPersonPositionCode(String contactPersonPositionCode) {
        this.contactPersonPositionCode = contactPersonPositionCode;
    }

    /**
     * @return the contactPersonPositionDesc
     */
    public String getContactPersonPositionDesc() {
        return contactPersonPositionDesc;
    }

    /**
     * @param contactPersonPositionDesc the contactPersonPositionDesc to set
     */
    public void setContactPersonPositionDesc(String contactPersonPositionDesc) {
        this.contactPersonPositionDesc = contactPersonPositionDesc;
    }

    /**
     * @return the contactPersonName
     */
    public String getContactPersonName() {
        return contactPersonName;
    }

    /**
     * @param contactPersonName the contactPersonName to set
     */
    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    /**
     * @return the contactPersonTel
     */
    public String getContactPersonTel() {
        return contactPersonTel;
    }

    /**
     * @param contactPersonTel the contactPersonTel to set
     */
    public void setContactPersonTel(String contactPersonTel) {
        this.contactPersonTel = contactPersonTel;
    }

    /**
     * @return the contactPersonMobile
     */
    public String getContactPersonMobile() {
        return contactPersonMobile;
    }

    /**
     * @param contactPersonMobile the contactPersonMobile to set
     */
    public void setContactPersonMobile(String contactPersonMobile) {
        this.contactPersonMobile = contactPersonMobile;
    }

    /**
     * @return the contactPersonEmall
     */
    public String getContactPersonEmall() {
        return contactPersonEmall;
    }

    /**
     * @param contactPersonEmall the contactPersonEmall to set
     */
    public void setContactPersonEmall(String contactPersonEmall) {
        this.contactPersonEmall = contactPersonEmall;
    }

    /**
     * @return the contactPersonDeptCode
     */
    public String getContactPersonDeptCode() {
        return contactPersonDeptCode;
    }

    /**
     * @param contactPersonDeptCode the contactPersonDeptCode to set
     */
    public void setContactPersonDeptCode(String contactPersonDeptCode) {
        this.contactPersonDeptCode = contactPersonDeptCode;
    }

    /**
     * @return the contactPersonDeptDesc
     */
    public String getContactPersonDeptDesc() {
        return contactPersonDeptDesc;
    }

    /**
     * @param contactPersonDeptDesc the contactPersonDeptDesc to set
     */
    public void setContactPersonDeptDesc(String contactPersonDeptDesc) {
        this.contactPersonDeptDesc = contactPersonDeptDesc;
    }

}
