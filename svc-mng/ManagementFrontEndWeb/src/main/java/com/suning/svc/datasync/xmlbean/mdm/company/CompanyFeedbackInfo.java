/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CompanyFeedbackInfo.java
 * Author:   12073942
 * Date:     2013-7-31 上午9:00:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.company;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 供应商公司层信息状态返回【T_ZMDIFS106】
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CompanyFeedbackInfo {

    /**
     * SUPPLIER_CODE 供应商编码
     */
    @XStreamAlias("SUPPLIER_CODE")
    private String supplierCode;

    /**
     * CO_CODE 公司代码
     */
    @XStreamAlias("CO_CODE")
    private String coCode;

    /**
     * VERSION_NO 版本
     */
    @XStreamAlias("VERSION_NO")
    private String versionNo;

    /**
     * PROCESS_STAT 处理状态
     */
    @XStreamAlias("PROCESS_STAT")
    private String processStat;

    /**
     * NOTES 备注
     */
    @XStreamAlias("NOTES")
    private String notes;

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
     * @return the coCode
     */
    public String getCoCode() {
        return coCode;
    }

    /**
     * @param coCode the coCode to set
     */
    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    /**
     * @return the versionNo
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * @param versionNo the versionNo to set
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * @return the processStat
     */
    public String getProcessStat() {
        return processStat;
    }

    /**
     * @param processStat the processStat to set
     */
    public void setProcessStat(String processStat) {
        this.processStat = processStat;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
