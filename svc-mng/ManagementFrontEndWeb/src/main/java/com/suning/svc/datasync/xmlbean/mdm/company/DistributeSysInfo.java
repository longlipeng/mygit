/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DistributeSysInfo.java
 * Author:   12073942
 * Date:     2013-7-30 下午6:10:38
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.company;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 分发系统数据【T_ZMDIFSH】
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DistributeSysInfo {

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
     * DISTRIBUTE_SYS 分发系统
     */
    @XStreamAlias("DISTRIBUTE_SYS")
    private String distributeSys;

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
     * @return the distributeSys
     */
    public String getDistributeSys() {
        return distributeSys;
    }

    /**
     * @param distributeSys the distributeSys to set
     */
    public void setDistributeSys(String distributeSys) {
        this.distributeSys = distributeSys;
    }

}
