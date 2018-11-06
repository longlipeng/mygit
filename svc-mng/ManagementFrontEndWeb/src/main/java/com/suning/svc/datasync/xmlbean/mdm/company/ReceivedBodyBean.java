/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceivedBodyBean.java
 * Author:   12073942
 * Date:     2013-7-30 下午5:43:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.company;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 供应商公司层主数据BodyBean
 * 
 * @author LEO
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("MbfBody")
public class ReceivedBodyBean {

    @XStreamImplicit(itemFieldName = "T_ZMDIFSH")
    private ArrayList<DistributeSysInfo> distributeSysInfo;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS058")
    private ArrayList<CompanyLayerInfo> companyLayerInfoList;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS054")
    private ArrayList<SupplierExtInfo> supplierExtInfoList;

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
     * @return the companyLayerInfoList
     */
    public ArrayList<CompanyLayerInfo> getCompanyLayerInfoList() {
        return companyLayerInfoList;
    }

    /**
     * @param companyLayerInfoList the companyLayerInfoList to set
     */
    public void setCompanyLayerInfoList(ArrayList<CompanyLayerInfo> companyLayerInfoList) {
        this.companyLayerInfoList = companyLayerInfoList;
    }

    /**
     * @return the supplierExtInfoList
     */
    public ArrayList<SupplierExtInfo> getSupplierExtInfoList() {
        return supplierExtInfoList;
    }

    /**
     * @param supplierExtInfoList the supplierExtInfoList to set
     */
    public void setSupplierExtInfoList(ArrayList<SupplierExtInfo> supplierExtInfoList) {
        this.supplierExtInfoList = supplierExtInfoList;
    }

}
