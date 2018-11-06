/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SupplierExtInfo.java
 * Author:   12073942
 * Date:     2013-7-30 下午6:00:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.company;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 供应商额外信息-物流【T_ZMDIFS054】
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SupplierExtInfo {

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
     * DISTRIBUTE_POINT_CODE 运输计划点
     */
    @XStreamAlias("DISTRIBUTE_POINT_CODE")
    private String distributePointCode;

    /**
     * PLANT_CODE 地点
     */
    @XStreamAlias("PLANT_CODE")
    private String plantCode;

    /**
     * TAX_CODE 运输方式
     */
    @XStreamAlias("TAX_CODE")
    private String taxCode;

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
     * @return the distributePointCode
     */
    public String getDistributePointCode() {
        return distributePointCode;
    }

    /**
     * @param distributePointCode the distributePointCode to set
     */
    public void setDistributePointCode(String distributePointCode) {
        this.distributePointCode = distributePointCode;
    }

    /**
     * @return the plantCode
     */
    public String getPlantCode() {
        return plantCode;
    }

    /**
     * @param plantCode the plantCode to set
     */
    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
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
