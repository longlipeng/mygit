/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CompanyLayerInfo.java
 * Author:   12073942
 * Date:     2013-7-30 下午5:57:46
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.company;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 公司层信息【T_ZMDIFS058】
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CompanyLayerInfo {

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
     * RECONCILIATION_ACCT_CODE 统驭科目
     */
    @XStreamAlias("RECONCILIATION_ACCT_CODE")
    private String reconciliationAcctCode;

    /**
     * PAYMENT_TYPE_CODE 付款条款
     */
    @XStreamAlias("PAYMENT_TYPE_CODE")
    private String paymentTypeCode;

    /**
     * PAYMENT_TYPE_DESC 付款条款描述
     */
    @XStreamAlias("PAYMENT_TYPE_DESC")
    private String paymentTypeDesc;

    /**
     * BLOCK_FLAG_COMP 对公司代码过帐冻结
     */
    @XStreamAlias("BLOCK_FLAG_COMP")
    private String blockFlagComp;

    /**
     * BLOCK_FLAG_PAY 付款冻结
     */
    @XStreamAlias("BLOCK_FLAG_PAY")
    private String blockFlagPay;

    /**
     * LAST_CALCULATE_DATE 上次计算日期
     */
    @XStreamAlias("LAST_CALCULATE_DATE")
    private String lastCalculateDate;

    /**
     * TOLERANCE_GRP 容差组
     */
    @XStreamAlias("TOLERANCE_GRP")
    private String toleranceGrp;

    /**
     * VERSION_NO 版本号
     */
    @XStreamAlias("VERSION_NO")
    private String versionNo;

    /**
     * DELETE_FLAG 删除标记
     */
    @XStreamAlias("DELETE_FLAG")
    private String deleteFlag;

    /**
     * EXTENSION 备用字段
     */
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
     * @return the reconciliationAcctCode
     */
    public String getReconciliationAcctCode() {
        return reconciliationAcctCode;
    }

    /**
     * @param reconciliationAcctCode the reconciliationAcctCode to set
     */
    public void setReconciliationAcctCode(String reconciliationAcctCode) {
        this.reconciliationAcctCode = reconciliationAcctCode;
    }

    /**
     * @return the paymentTypeCode
     */
    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    /**
     * @param paymentTypeCode the paymentTypeCode to set
     */
    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }

    /**
     * @return the paymentTypeDesc
     */
    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    /**
     * @param paymentTypeDesc the paymentTypeDesc to set
     */
    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    /**
     * @return the blockFlagComp
     */
    public String getBlockFlagComp() {
        return blockFlagComp;
    }

    /**
     * @param blockFlagComp the blockFlagComp to set
     */
    public void setBlockFlagComp(String blockFlagComp) {
        this.blockFlagComp = blockFlagComp;
    }

    /**
     * @return the blockFlagPay
     */
    public String getBlockFlagPay() {
        return blockFlagPay;
    }

    /**
     * @param blockFlagPay the blockFlagPay to set
     */
    public void setBlockFlagPay(String blockFlagPay) {
        this.blockFlagPay = blockFlagPay;
    }

    /**
     * @return the lastCalculateDate
     */
    public String getLastCalculateDate() {
        return lastCalculateDate;
    }

    /**
     * @param lastCalculateDate the lastCalculateDate to set
     */
    public void setLastCalculateDate(String lastCalculateDate) {
        this.lastCalculateDate = lastCalculateDate;
    }

    /**
     * @return the toleranceGrp
     */
    public String getToleranceGrp() {
        return toleranceGrp;
    }

    /**
     * @param toleranceGrp the toleranceGrp to set
     */
    public void setToleranceGrp(String toleranceGrp) {
        this.toleranceGrp = toleranceGrp;
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
     * @return the deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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
