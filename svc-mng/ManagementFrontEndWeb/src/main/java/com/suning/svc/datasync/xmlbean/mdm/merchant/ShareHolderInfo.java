/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: T_ZMDIFS053.java
 * Author:   luwanchuan
 * Date:     2013-4-22 下午04:55:38
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.merchant;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 〈供应商额外信息-股东〉<br>
 * 〈MDM下发的供应商（商户）数据，包含的供应商额外信息-股东，本系统不需要，只接收，不处理〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS053")
public class ShareHolderInfo {

    @XStreamAlias("SUPPLIER_CODE")
    private String supplierCode;

    @XStreamAlias("SHAREHOLDER_NAME")
    private String shareholderName;

    @XStreamAlias("SHAREHOLDER_ID_CARD")
    private String shareholderIdCard;

    @XStreamAlias("EXTENSION")
    private String extension;

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
     * @return the shareholderName
     */
    public String getShareholderName() {
        return shareholderName;
    }

    /**
     * @param shareholderName the shareholderName to set
     */
    public void setShareholderName(String shareholderName) {
        this.shareholderName = shareholderName;
    }

    /**
     * @return the shareholderIdCard
     */
    public String getShareholderIdCard() {
        return shareholderIdCard;
    }

    /**
     * @param shareholderIdCard the shareholderIdCard to set
     */
    public void setShareholderIdCard(String shareholderIdCard) {
        this.shareholderIdCard = shareholderIdCard;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

}
