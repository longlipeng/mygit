/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: T_ZMDIFS082.java
 * Author:   luwanchuan
 * Date:     2013-4-21 下午06:57:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.shop;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 〈地点--库位关系〉<br>
 * 〈MDM下发的地点代码（含中心仓、门店和网点）数据，包含的地点--库位关系，本系统不需要，只接收，不处理〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS082")
public class InventoryLocationInfo {

    @XStreamAlias("PLANT_CODE")
    private String plantCode;

    @XStreamAlias("INVENTORY_LOCATION_CODE")
    private String inventoryLocationCode;

    @XStreamAlias("INVENTORY_LOCATION_NAME")
    private String inventoryLocationName;

    @XStreamAlias("EXTENSION")
    private String extension;

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
     * @return the inventoryLocationCode
     */
    public String getInventoryLocationCode() {
        return inventoryLocationCode;
    }

    /**
     * @param inventoryLocationCode the inventoryLocationCode to set
     */
    public void setInventoryLocationCode(String inventoryLocationCode) {
        this.inventoryLocationCode = inventoryLocationCode;
    }

    /**
     * @return the inventoryLocationName
     */
    public String getInventoryLocationName() {
        return inventoryLocationName;
    }

    /**
     * @param inventoryLocationName the inventoryLocationName to set
     */
    public void setInventoryLocationName(String inventoryLocationName) {
        this.inventoryLocationName = inventoryLocationName;
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
