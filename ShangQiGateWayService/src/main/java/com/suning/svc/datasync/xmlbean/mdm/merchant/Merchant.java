package com.suning.svc.datasync.xmlbean.mdm.merchant;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 〈MDM下发的供应商（商户）数据的基本信息〉<br>
 * 〈接收所有下发的数据，后期选择满足预付卡系统需要的数据，转发给预付卡系统〉
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("T_ZMDIFS050")
public class Merchant {

    /**
     * 供应商编码
     */
    @XStreamAlias("SUPPLIER_CODE")
    private String supplierCode;

    /**
     * 供应商账户组
     */
    @XStreamAlias("SUPPLIER_ACCT_GRP")
    private String supplierAcctGrp;

    /**
     * 供应商名称
     */
    @XStreamAlias("SUPPLIER_NAME")
    private String supplierName;

    /**
     * 消费者
     */
    @XStreamAlias("CUST_CODE")
    private String custCode;

    /**
     * 货运代理运输组
     */
    @XStreamAlias("BU_CODE")
    private String buCode;

    /**
     * 货运代理描述
     */
    @XStreamAlias("BU_NAME")
    private String buName;

    /**
     * 服务代理过程组
     */
    @XStreamAlias("CENTRALIZED_PURCHASE_CODE")
    private String centralizedPurchaseCode;

    /**
     * 服务代理描述
     */
    @XStreamAlias("CENTRALIZED_PURCHASE_NAME")
    private String centralizedPurchaseName;

    /**
     * 统计组服务中介（付款方式）
     */
    @XStreamAlias("PAYMENT_TYPE_CODE")
    private String paymentTypeCode;

    /**
     * 付款方式描述
     */
    @XStreamAlias("PAYMENT_TYPE_DESC")
    private String paymentTypeDesc;

    /**
     * 税类型
     */
    @XStreamAlias("TAX_TYPE_CODE")
    private String taxTypeCode;

    /**
     * 税类型描述
     */
    @XStreamAlias("TAX_TYPE_DESC")
    private String taxTypeDesc;

    /**
     * 税号
     */
    @XStreamAlias("TAX_CODE")
    private String taxCode;

    /**
     * 税号类型
     */
    @XStreamAlias("TAX_NO_TYPE")
    private String taxNoType;

    /**
     * 组代码（组织机构代码）
     */
    @XStreamAlias("ORG_CODE")
    private String orgCode;

    /**
     * 业务类型
     */
    @XStreamAlias("BIZ_TYPE_CODE")
    private String bizTypeCode;

    /**
     * 信贷信息号码（年检年度）
     */
    @XStreamAlias("YEARLY_INSPECT_YEAR")
    private String yearlyInspectYear;

    /**
     * 将被冻结的标记
     */
    @XStreamAlias("BLOCK_FLAG")
    private String blockFlag;

    /**
     * 冻结付款
     */
    @XStreamAlias("BLOCK_FLAG_CENTER")
    private String blockFlagCenter;

    /**
     * 中心记帐冻结
     */
    @XStreamAlias("BLOCK_FLAG_ACCOUNTING_CENTER")
    private String blockFlagAccountingCenter;

    /**
     * 中心采购冻结
     */
    @XStreamAlias("BLOCK_FLAG_PURCHASE_CENTER")
    private String blockFlagPurchaseCenter;

    /**
     * 搜索项1
     */
    @XStreamAlias("SEARCH_ITEM1")
    private String searchItem1;

    /**
     * 搜索项2
     */
    @XStreamAlias("SEARCH_ITEM2")
    private String searchItem2;

    /**
     * 公司注册地址
     */
    @XStreamAlias("REG_ADDRESS")
    private String regAddress;

    /**
     * 城市邮编
     */
    @XStreamAlias("CITY_POST_CODE")
    private String cityPostCode;

    /**
     * 注册地所有省份（地区）
     */
    @XStreamAlias("REG_PROVINCE_CODE")
    private String regProvinceCode;

    /**
     * 省份描述
     */
    @XStreamAlias("PROVINCE_DESC")
    private String provinceDesc;

    /**
     * 注册地所在城市
     */
    @XStreamAlias("REG_CITY_CODE")
    private String regCityCode;

    /**
     * 邮政信箱
     */
    @XStreamAlias("MAIL_BOX")
    private String mallBox;

    /**
     * 国家
     */
    @XStreamAlias("COUNTRY_CODE")
    private String countryCode;

    /**
     * 单位所在地址
     */
    @XStreamAlias("OFFICE_ADDRESS")
    private String officeAddress;

    /**
     * 公司邮编
     */
    @XStreamAlias("OFFICE_POST_CODE")
    private String officePostCode;

    /**
     * 公司电话
     */
    @XStreamAlias("TEL_NO")
    private String telNo;

    /**
     * 公司传真
     */
    @XStreamAlias("FAX_NO")
    private String faxNo;

    /**
     * 公司EMAIL
     */
    @XStreamAlias("EMAIL")
    private String emall;

    /**
     * 凭证货币计量的净价值 开票限额
     */
    @XStreamAlias("BILLING_LIMIT_AMT")
    private String billingLimitAmt;

    /**
     * 操作模式
     */
    @XStreamAlias("OPERATION_MODE_CODE")
    private String operationModeCode;

    /**
     * 工商注册号
     */
    @XStreamAlias("BIZ_REG_NO")
    private String bizRegNo;

    /**
     * 注册资本
     */
    @XStreamAlias("REG_CAPITAL")
    private String regCapital;

    /**
     * 公司类型
     */
    @XStreamAlias("CO_TYPE_CODE")
    private String coTypeCode;

    /**
     * 营业执照注册日期
     */
    @XStreamAlias("BIZ_LICENSE_REG_DATE")
    private String bizLicenseRegDate;

    /**
     * 营业执照到期日期
     */
    @XStreamAlias("BIZ_LICENSE_EXPIRE_DATE")
    private String bizLicenseExpireDate;

    /**
     * 机构类型
     */
    @XStreamAlias("ORG_TYPE_CODE")
    private String orgTypeCode;

    /**
     * 供应商性质 描述
     */
    @XStreamAlias("SUPPLIER_NATURE_CODE")
    private String supplierNatureCode;

    /**
     * 法人代表姓名
     */
    @XStreamAlias("LEGAL_PERSON_NAME")
    private String legalPersonName;

    /**
     * 法人代表身份证号
     */
    @XStreamAlias("LEGAL_PERSON_ID_CARD")
    private String legalPersonIdCard;

    /**
     * 版本号
     */
    @XStreamAlias("VERSION_NO")
    private String versionNo;

    /**
     * 删除标记
     */
    @XStreamAlias("DELETE_FLAG")
    private String deleteFlag;

    /**
     * 备用字段
     */
    @XStreamAlias("EXTENSION")
    private String extension;

    /**
     * 供应商信息来源
     */
    @XStreamAlias("SUPPLIER_SOURCE")
    private String supplierSource;

    /**
     * 业务总类
     */
    @XStreamAlias("BUSINESS_TYPE")
    private String businessType;

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
     * @return the supplierAcctGrp
     */
    public String getSupplierAcctGrp() {
        return supplierAcctGrp;
    }

    /**
     * @param supplierAcctGrp the supplierAcctGrp to set
     */
    public void setSupplierAcctGrp(String supplierAcctGrp) {
        this.supplierAcctGrp = supplierAcctGrp;
    }

    /**
     * @return the supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName the supplierName to set
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return the custCode
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * @param custCode the custCode to set
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**
     * @return the buCode
     */
    public String getBuCode() {
        return buCode;
    }

    /**
     * @param buCode the buCode to set
     */
    public void setBuCode(String buCode) {
        this.buCode = buCode;
    }

    /**
     * @return the buName
     */
    public String getBuName() {
        return buName;
    }

    /**
     * @param buName the buName to set
     */
    public void setBuName(String buName) {
        this.buName = buName;
    }

    /**
     * @return the centralizedPurchaseCode
     */
    public String getCentralizedPurchaseCode() {
        return centralizedPurchaseCode;
    }

    /**
     * @param centralizedPurchaseCode the centralizedPurchaseCode to set
     */
    public void setCentralizedPurchaseCode(String centralizedPurchaseCode) {
        this.centralizedPurchaseCode = centralizedPurchaseCode;
    }

    /**
     * @return the centralizedPurchaseName
     */
    public String getCentralizedPurchaseName() {
        return centralizedPurchaseName;
    }

    /**
     * @param centralizedPurchaseName the centralizedPurchaseName to set
     */
    public void setCentralizedPurchaseName(String centralizedPurchaseName) {
        this.centralizedPurchaseName = centralizedPurchaseName;
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
     * @return the taxTypeCode
     */
    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    /**
     * @param taxTypeCode the taxTypeCode to set
     */
    public void setTaxTypeCode(String taxTypeCode) {
        this.taxTypeCode = taxTypeCode;
    }

    /**
     * @return the taxTypeDesc
     */
    public String getTaxTypeDesc() {
        return taxTypeDesc;
    }

    /**
     * @param taxTypeDesc the taxTypeDesc to set
     */
    public void setTaxTypeDesc(String taxTypeDesc) {
        this.taxTypeDesc = taxTypeDesc;
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
     * @return the taxNoType
     */
    public String getTaxNoType() {
        return taxNoType;
    }

    /**
     * @param taxNoType the taxNoType to set
     */
    public void setTaxNoType(String taxNoType) {
        this.taxNoType = taxNoType;
    }

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the bizTypeCode
     */
    public String getBizTypeCode() {
        return bizTypeCode;
    }

    /**
     * @param bizTypeCode the bizTypeCode to set
     */
    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode;
    }

    /**
     * @return the yearlyInspectYear
     */
    public String getYearlyInspectYear() {
        return yearlyInspectYear;
    }

    /**
     * @param yearlyInspectYear the yearlyInspectYear to set
     */
    public void setYearlyInspectYear(String yearlyInspectYear) {
        this.yearlyInspectYear = yearlyInspectYear;
    }

    /**
     * @return the blockFlag
     */
    public String getBlockFlag() {
        return blockFlag;
    }

    /**
     * @param blockFlag the blockFlag to set
     */
    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    /**
     * @return the blockFlagCenter
     */
    public String getBlockFlagCenter() {
        return blockFlagCenter;
    }

    /**
     * @param blockFlagCenter the blockFlagCenter to set
     */
    public void setBlockFlagCenter(String blockFlagCenter) {
        this.blockFlagCenter = blockFlagCenter;
    }

    /**
     * @return the blockFlagAccountingCenter
     */
    public String getBlockFlagAccountingCenter() {
        return blockFlagAccountingCenter;
    }

    /**
     * @param blockFlagAccountingCenter the blockFlagAccountingCenter to set
     */
    public void setBlockFlagAccountingCenter(String blockFlagAccountingCenter) {
        this.blockFlagAccountingCenter = blockFlagAccountingCenter;
    }

    /**
     * @return the blockFlagPurchaseCenter
     */
    public String getBlockFlagPurchaseCenter() {
        return blockFlagPurchaseCenter;
    }

    /**
     * @param blockFlagPurchaseCenter the blockFlagPurchaseCenter to set
     */
    public void setBlockFlagPurchaseCenter(String blockFlagPurchaseCenter) {
        this.blockFlagPurchaseCenter = blockFlagPurchaseCenter;
    }

    /**
     * @return the searchItem1
     */
    public String getSearchItem1() {
        return searchItem1;
    }

    /**
     * @param searchItem1 the searchItem1 to set
     */
    public void setSearchItem1(String searchItem1) {
        this.searchItem1 = searchItem1;
    }

    /**
     * @return the searchItem2
     */
    public String getSearchItem2() {
        return searchItem2;
    }

    /**
     * @param searchItem2 the searchItem2 to set
     */
    public void setSearchItem2(String searchItem2) {
        this.searchItem2 = searchItem2;
    }

    /**
     * @return the regAddress
     */
    public String getRegAddress() {
        return regAddress;
    }

    /**
     * @param regAddress the regAddress to set
     */
    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    /**
     * @return the cityPostCode
     */
    public String getCityPostCode() {
        return cityPostCode;
    }

    /**
     * @param cityPostCode the cityPostCode to set
     */
    public void setCityPostCode(String cityPostCode) {
        this.cityPostCode = cityPostCode;
    }

    /**
     * @return the regProvinceCode
     */
    public String getRegProvinceCode() {
        return regProvinceCode;
    }

    /**
     * @param regProvinceCode the regProvinceCode to set
     */
    public void setRegProvinceCode(String regProvinceCode) {
        this.regProvinceCode = regProvinceCode;
    }

    /**
     * @return the provinceDesc
     */
    public String getProvinceDesc() {
        return provinceDesc;
    }

    /**
     * @param provinceDesc the provinceDesc to set
     */
    public void setProvinceDesc(String provinceDesc) {
        this.provinceDesc = provinceDesc;
    }

    /**
     * @return the regCityCode
     */
    public String getRegCityCode() {
        return regCityCode;
    }

    /**
     * @param regCityCode the regCityCode to set
     */
    public void setRegCityCode(String regCityCode) {
        this.regCityCode = regCityCode;
    }

    /**
     * @return the mallBox
     */
    public String getMallBox() {
        return mallBox;
    }

    /**
     * @param mallBox the mallBox to set
     */
    public void setMallBox(String mallBox) {
        this.mallBox = mallBox;
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
     * @return the officeAddress
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     * @param officeAddress the officeAddress to set
     */
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    /**
     * @return the officePostCode
     */
    public String getOfficePostCode() {
        return officePostCode;
    }

    /**
     * @param officePostCode the officePostCode to set
     */
    public void setOfficePostCode(String officePostCode) {
        this.officePostCode = officePostCode;
    }

    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }

    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    /**
     * @return the emall
     */
    public String getEmall() {
        return emall;
    }

    /**
     * @param emall the emall to set
     */
    public void setEmall(String emall) {
        this.emall = emall;
    }

    /**
     * @return the billingLimitAmt
     */
    public String getBillingLimitAmt() {
        return billingLimitAmt;
    }

    /**
     * @param billingLimitAmt the billingLimitAmt to set
     */
    public void setBillingLimitAmt(String billingLimitAmt) {
        this.billingLimitAmt = billingLimitAmt;
    }

    /**
     * @return the operationModeCode
     */
    public String getOperationModeCode() {
        return operationModeCode;
    }

    /**
     * @param operationModeCode the operationModeCode to set
     */
    public void setOperationModeCode(String operationModeCode) {
        this.operationModeCode = operationModeCode;
    }

    /**
     * @return the bizRegNo
     */
    public String getBizRegNo() {
        return bizRegNo;
    }

    /**
     * @param bizRegNo the bizRegNo to set
     */
    public void setBizRegNo(String bizRegNo) {
        this.bizRegNo = bizRegNo;
    }

    /**
     * @return the regCapital
     */
    public String getRegCapital() {
        return regCapital;
    }

    /**
     * @param regCapital the regCapital to set
     */
    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    /**
     * @return the coTypeCode
     */
    public String getCoTypeCode() {
        return coTypeCode;
    }

    /**
     * @param coTypeCode the coTypeCode to set
     */
    public void setCoTypeCode(String coTypeCode) {
        this.coTypeCode = coTypeCode;
    }

    /**
     * @return the bizLicenseRegDate
     */
    public String getBizLicenseRegDate() {
        return bizLicenseRegDate;
    }

    /**
     * @param bizLicenseRegDate the bizLicenseRegDate to set
     */
    public void setBizLicenseRegDate(String bizLicenseRegDate) {
        this.bizLicenseRegDate = bizLicenseRegDate;
    }

    /**
     * @return the bizLicenseExpireDate
     */
    public String getBizLicenseExpireDate() {
        return bizLicenseExpireDate;
    }

    /**
     * @param bizLicenseExpireDate the bizLicenseExpireDate to set
     */
    public void setBizLicenseExpireDate(String bizLicenseExpireDate) {
        this.bizLicenseExpireDate = bizLicenseExpireDate;
    }

    /**
     * @return the orgTypeCode
     */
    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    /**
     * @param orgTypeCode the orgTypeCode to set
     */
    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    /**
     * @return the supplierNatureCode
     */
    public String getSupplierNatureCode() {
        return supplierNatureCode;
    }

    /**
     * @param supplierNatureCode the supplierNatureCode to set
     */
    public void setSupplierNatureCode(String supplierNatureCode) {
        this.supplierNatureCode = supplierNatureCode;
    }

    /**
     * @return the legalPersonName
     */
    public String getLegalPersonName() {
        return legalPersonName;
    }

    /**
     * @param legalPersonName the legalPersonName to set
     */
    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    /**
     * @return the legalPersonIdCard
     */
    public String getLegalPersonIdCard() {
        return legalPersonIdCard;
    }

    /**
     * @param legalPersonIdCard the legalPersonIdCard to set
     */
    public void setLegalPersonIdCard(String legalPersonIdCard) {
        this.legalPersonIdCard = legalPersonIdCard;
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

    /**
     * @return the supplierSource
     */
    public String getSupplierSource() {
        return supplierSource;
    }

    /**
     * @param supplierSource the supplierSource to set
     */
    public void setSupplierSource(String supplierSource) {
        this.supplierSource = supplierSource;
    }

    /**
     * @return the businessType
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType the businessType to set
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

}
