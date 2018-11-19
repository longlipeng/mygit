/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: T_ZMDIFS117.java
 * Author:   luwanchuan
 * Date:     2013-4-22 上午10:47:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.mdm.shop;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 〈成本中心信息状态返回〉<br>
 * 〈返回给MDM的信息，字段包括：地点代码（含中心仓、门店和网点）、版本号、成功标志、成功描述〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS117")
public class FeedbackStatusInfo {

    @XStreamAlias("PLANT_CODE")
    private String plantCode;

    @XStreamAlias("VERSION_NO")
    private String versionNo;

    @XStreamAlias("PROCESS_STAT")
    private String processStat;

    @XStreamAlias("NOTES")
    private String notes;

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
