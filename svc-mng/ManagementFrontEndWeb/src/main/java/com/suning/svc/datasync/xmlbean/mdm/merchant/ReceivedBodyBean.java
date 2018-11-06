/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceivedBodyBean.java
 * Author:   luwanchuan
 * Date:     2013-5-3 上午11:01:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.merchant;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 接收MDM通过ESB下发的供应商（商户）信息<br>
 * 接收MDM通过ESB下发的供应商（商户）信息的Body部分
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("MbfBody")
public class ReceivedBodyBean {

    @XStreamImplicit(itemFieldName = "T_ZMDIFSH")
    private ArrayList<DistributeSysInfo> distributeSysInfo;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS050")
    private ArrayList<Merchant> merchant;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS051")
    private ArrayList<BankInfo> bankInfo;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS052")
    private ArrayList<ContactInfo> contactInfo;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS053")
    private ArrayList<ShareHolderInfo> shareHolderInfo;

    /**
     * @return the distributeSysInfo
     */
    public ArrayList<DistributeSysInfo> getDistributeSysInfo() {
        return distributeSysInfo;
    }

    /**
     * @param distributeSysInfo the distributeSysInfo to set
     */
    public void setDistributeSysInfo(ArrayList<DistributeSysInfo> distributeSysInfo) {
        this.distributeSysInfo = distributeSysInfo;
    }

    /**
     * @return the merchant
     */
    public ArrayList<Merchant> getMerchant() {
        return merchant;
    }

    /**
     * @param merchant the merchant to set
     */
    public void setMerchant(ArrayList<Merchant> merchant) {
        this.merchant = merchant;
    }

    /**
     * @return the bankInfo
     */
    public ArrayList<BankInfo> getBankInfo() {
        return bankInfo;
    }

    /**
     * @param bankInfo the bankInfo to set
     */
    public void setBankInfo(ArrayList<BankInfo> bankInfo) {
        this.bankInfo = bankInfo;
    }

    /**
     * @return the contactInfo
     */
    public ArrayList<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    /**
     * @param contactInfo the contactInfo to set
     */
    public void setContactInfo(ArrayList<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * @return the shareHolderInfo
     */
    public ArrayList<ShareHolderInfo> getShareHolderInfo() {
        return shareHolderInfo;
    }

    /**
     * @param shareHolderInfo the shareHolderInfo to set
     */
    public void setShareHolderInfo(ArrayList<ShareHolderInfo> shareHolderInfo) {
        this.shareHolderInfo = shareHolderInfo;
    }

}
