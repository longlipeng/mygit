/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: T_ZMDIFS.java
 * Author:   luwanchuan
 * Date:     2013-4-21 下午03:03:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.shop;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 〈分发系统数据〉<br>
 * 〈MDM下发的地点代码（含中心仓、门店和网点）数据，包含的分发系统数据，本系统不需要，只接收，不处理〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFSH")
public class DistributeSysInfo {

    /**
     * 地点代码
     */
    @XStreamAlias("PLANT_CODE")
    private String plantCode;

    /**
     * 分发系统
     */
    @XStreamAlias("DISTRIBUTE_SYS")
    private String distributeSys;

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
