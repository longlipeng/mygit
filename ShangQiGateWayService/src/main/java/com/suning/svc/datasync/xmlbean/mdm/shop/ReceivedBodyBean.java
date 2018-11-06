/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceivedBodyBean.java
 * Author:   luwanchuan
 * Date:     2013-5-2 下午02:44:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.shop;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 接收MDM通过ESB下发的地点（门店）信息<br>
 * 接收MDM通过ESB下发的地点（门店）信息的Body部分
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("MbfBody")
public class ReceivedBodyBean {

    @XStreamImplicit(itemFieldName = "T_ZMDIFSH")
    private ArrayList<DistributeSysInfo> distributeSysInfo;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS081")
    private ArrayList<Shop> shop;

    @XStreamImplicit(itemFieldName = "T_ZMDIFS082")
    private ArrayList<InventoryLocationInfo> inventoryLocationInfo;

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
     * @return the shop
     */
    public ArrayList<Shop> getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(ArrayList<Shop> shop) {
        this.shop = shop;
    }

    /**
     * @return the inventoryLocationInfo
     */
    public ArrayList<InventoryLocationInfo> getInventoryLocationInfo() {
        return inventoryLocationInfo;
    }

    /**
     * @param inventoryLocationInfo the inventoryLocationInfo to set
     */
    public void setInventoryLocationInfo(ArrayList<InventoryLocationInfo> inventoryLocationInfo) {
        this.inventoryLocationInfo = inventoryLocationInfo;
    }

}
