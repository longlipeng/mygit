package com.suning.svc.datasync.xmlbean.mdm.shop;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 〈MDM下发的地点信息（含中心仓、门店和网点）数据的基本信息〉<br>
 * 〈接收所有下发的数据，后期选择满足预付卡系统需要的数据，转发给预付卡系统〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS081")
public class Shop {

    /**
     * 地点代码（含中心仓、门店和网点）
     */
    @XStreamAlias("PLANT_CODE")
    private String plantCode;

    /**
     * 地点名称（含中心仓、门店和网点）
     */
    @XStreamAlias("PLANT_NAME")
    private String plantName;

    /**
     * 地点所属公司代码
     */
    @XStreamAlias("CO_CODE")
    private String coCode;

    /**
     * 地点所属公司描述
     */
    @XStreamAlias("CO_DESC")
    private String coDesc;

    /**
     * 地点所属采购组织
     */
    @XStreamAlias("PURCHASE_ORG_CODE")
    private String purchaseOrgCode;

    /**
     * 地点所属销售组织（中心仓所在公司的销售组织）
     */
    @XStreamAlias("SALES_ORG_CODE")
    private String salesOrgCode;

    /**
     * 地点-城市
     */
    @XStreamAlias("CITY_NAME")
    private String cityName;

    /**
     * 地点-住宅号及街道
     */
    @XStreamAlias("STREET_CODE")
    private String streetCode;

    /**
     * 地点-邮政编码
     */
    @XStreamAlias("POST_CODE")
    private String postCode;

    /**
     * 地点-国家
     */
    @XStreamAlias("COUNTRY_CODE")
    private String countryCode;

    /**
     * 切换后地点
     */
    @XStreamAlias("SWITCHED_PLANT_CODE")
    private String switchedPlantCode;

    /**
     * 切换后地点名称
     */
    @XStreamAlias("SWITCHED_PLANT_NAME")
    private String switchedPlantName;

    /**
     * 中心仓/门店/网点
     */
    @XStreamAlias("PLANT_TYPE_CODE")
    private String plantTypeCode;

    /**
     * 中心仓/门店/网点描述
     */
    @XStreamAlias("PLANT_TYPE_DESC")
    private String plantTypeDesc;

    /**
     * 中心仓/门店/网点细分
     */
    @XStreamAlias("SUB_PLANT_TYPE_CODE")
    private String subPlantTypeCode;

    /**
     * 中心仓/门店/网点细分描述
     */
    @XStreamAlias("SUB_PLANT_TYPE_DESC")
    private String subPlantTypeDesc;

    /**
     * 中心仓/门店/网点业态
     */
    @XStreamAlias("INDUSTRY_SITUATION_CODE")
    private String industrySituationCode;

    /**
     * 中心仓/门店/网点业态描述
     */
    @XStreamAlias("INDUSTRY_SITUATION_DESC")
    private String industrySituationDesc;

    /**
     * 门店级别
     */
    @XStreamAlias("STORE_LEVEL_CODE")
    private String storeLevelCode;

    /**
     * 门店级别描述
     */
    @XStreamAlias("STORE_LEVEL_DESC")
    private String storeLevelDesc;

    /**
     * 是否管理店
     */
    @XStreamAlias("MGMT_STORE_FLAG")
    private String mgmtStoreFlag;

    /**
     * 地点的开始日期
     */
    @XStreamAlias("BEGIN_DATE")
    private String beginDate;

    /**
     * 地点的结束日期
     */
    @XStreamAlias("END_DATE")
    private String endDate;

    /**
     * 地点状态
     */
    @XStreamAlias("PLANT_STAT_CODE")
    private String plantStatCode;

    /**
     * 地点状态描述
     */
    @XStreamAlias("PLANT_STAT_DESC")
    private String plantStatDesc;

    /**
     * 版本号
     */
    @XStreamAlias("VERSION_NO")
    private String versionNo;

    /**
     * 删除标记
     */
    @XStreamAlias("DELETE_FLAG")
    private String deledeFlag;

    /**
     * 备用字段
     */
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
     * @return the plantName
     */
    public String getPlantName() {
        return plantName;
    }

    /**
     * @param plantName the plantName to set
     */
    public void setPlantName(String plantName) {
        this.plantName = plantName;
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
     * @return the coDesc
     */
    public String getCoDesc() {
        return coDesc;
    }

    /**
     * @param coDesc the coDesc to set
     */
    public void setCoDesc(String coDesc) {
        this.coDesc = coDesc;
    }

    /**
     * @return the purchaseOrgCode
     */
    public String getPurchaseOrgCode() {
        return purchaseOrgCode;
    }

    /**
     * @param purchaseOrgCode the purchaseOrgCode to set
     */
    public void setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
    }

    /**
     * @return the salesOrgCode
     */
    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    /**
     * @param salesOrgCode the salesOrgCode to set
     */
    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the streetCode
     */
    public String getStreetCode() {
        return streetCode;
    }

    /**
     * @param streetCode the streetCode to set
     */
    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the switchedPlantCode
     */
    public String getSwitchedPlantCode() {
        return switchedPlantCode;
    }

    /**
     * @param switchedPlantCode the switchedPlantCode to set
     */
    public void setSwitchedPlantCode(String switchedPlantCode) {
        this.switchedPlantCode = switchedPlantCode;
    }

    /**
     * @return the switchedPlantName
     */
    public String getSwitchedPlantName() {
        return switchedPlantName;
    }

    /**
     * @param switchedPlantName the switchedPlantName to set
     */
    public void setSwitchedPlantName(String switchedPlantName) {
        this.switchedPlantName = switchedPlantName;
    }

    /**
     * @return the plantTypeCode
     */
    public String getPlantTypeCode() {
        return plantTypeCode;
    }

    /**
     * @param plantTypeCode the plantTypeCode to set
     */
    public void setPlantTypeCode(String plantTypeCode) {
        this.plantTypeCode = plantTypeCode;
    }

    /**
     * @return the plantTypeDesc
     */
    public String getPlantTypeDesc() {
        return plantTypeDesc;
    }

    /**
     * @param plantTypeDesc the plantTypeDesc to set
     */
    public void setPlantTypeDesc(String plantTypeDesc) {
        this.plantTypeDesc = plantTypeDesc;
    }

    /**
     * @return the subPlantTypeCode
     */
    public String getSubPlantTypeCode() {
        return subPlantTypeCode;
    }

    /**
     * @param subPlantTypeCode the subPlantTypeCode to set
     */
    public void setSubPlantTypeCode(String subPlantTypeCode) {
        this.subPlantTypeCode = subPlantTypeCode;
    }

    /**
     * @return the subPlantTypeDesc
     */
    public String getSubPlantTypeDesc() {
        return subPlantTypeDesc;
    }

    /**
     * @param subPlantTypeDesc the subPlantTypeDesc to set
     */
    public void setSubPlantTypeDesc(String subPlantTypeDesc) {
        this.subPlantTypeDesc = subPlantTypeDesc;
    }

    /**
     * @return the industrySituationCode
     */
    public String getIndustrySituationCode() {
        return industrySituationCode;
    }

    /**
     * @param industrySituationCode the industrySituationCode to set
     */
    public void setIndustrySituationCode(String industrySituationCode) {
        this.industrySituationCode = industrySituationCode;
    }

    /**
     * @return the industrySituationDesc
     */
    public String getIndustrySituationDesc() {
        return industrySituationDesc;
    }

    /**
     * @param industrySituationDesc the industrySituationDesc to set
     */
    public void setIndustrySituationDesc(String industrySituationDesc) {
        this.industrySituationDesc = industrySituationDesc;
    }

    /**
     * @return the storeLevelCode
     */
    public String getStoreLevelCode() {
        return storeLevelCode;
    }

    /**
     * @param storeLevelCode the storeLevelCode to set
     */
    public void setStoreLevelCode(String storeLevelCode) {
        this.storeLevelCode = storeLevelCode;
    }

    /**
     * @return the storeLevelDesc
     */
    public String getStoreLevelDesc() {
        return storeLevelDesc;
    }

    /**
     * @param storeLevelDesc the storeLevelDesc to set
     */
    public void setStoreLevelDesc(String storeLevelDesc) {
        this.storeLevelDesc = storeLevelDesc;
    }

    /**
     * @return the mgmtStoreFlag
     */
    public String getMgmtStoreFlag() {
        return mgmtStoreFlag;
    }

    /**
     * @param mgmtStoreFlag the mgmtStoreFlag to set
     */
    public void setMgmtStoreFlag(String mgmtStoreFlag) {
        this.mgmtStoreFlag = mgmtStoreFlag;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the plantStatCode
     */
    public String getPlantStatCode() {
        return plantStatCode;
    }

    /**
     * @param plantStatCode the plantStatCode to set
     */
    public void setPlantStatCode(String plantStatCode) {
        this.plantStatCode = plantStatCode;
    }

    /**
     * @return the plantStatDesc
     */
    public String getPlantStatDesc() {
        return plantStatDesc;
    }

    /**
     * @param plantStatDesc the plantStatDesc to set
     */
    public void setPlantStatDesc(String plantStatDesc) {
        this.plantStatDesc = plantStatDesc;
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
     * @return the deledeFlag
     */
    public String getDeledeFlag() {
        return deledeFlag;
    }

    /**
     * @param deledeFlag the deledeFlag to set
     */
    public void setDeledeFlag(String deledeFlag) {
        this.deledeFlag = deledeFlag;
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
