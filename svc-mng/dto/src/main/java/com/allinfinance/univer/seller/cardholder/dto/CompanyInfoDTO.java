package com.allinfinance.univer.seller.cardholder.dto;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;

public class CompanyInfoDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private String relationNo; //企业持卡人好
    private String relationType; //类型(持卡人、购卡人)
    private String entityId; //客户号
    private String companyName; //公司名称
    private String companyEnglishname; //公司英文名称
    private String corpName;  //法人姓名
    private String corpCredType; //法人证件类型
    private String corpCredId; //法人证件号码
    private String corpCredValidity; //法人证件有效期
    private String corpGender;  //法人性别
    private String corpCountyr; //法人国籍
    private String corpAliasName; //法人别名
    private String corpBirthday; //法人出生日期
    private String corpProfession; //法人职业
    private String corpTelephoneNumber; //法人联系电话
    private String corpAddress; //法人住址
    private String companyCountyr; //企业国别
    private String companyRegisteredAddress; //企业注册地
    private String companyIdType; //企业证件种类
    private String companyId; //企业证件号
    private String companyAccountant; //公司会计
    private String companyDescription; //公司描述
    private String registeredCapital; //注册资本
    private String companySize; //企业规模
    private String postcode; //邮编
    private String companyFax; //传真
    private String operatorName; //经办人姓名
    private String operatorType; //经办人证件类型
    private String operatorId; //经办人证件号码
    private String operatorValidity; //经办人证件有效期
    private String operatorTelephoneNumber; //经办人联系电话
    private String operatorAddress; //经办人住址
    private String bankAccount; //银行账户号
    private String bankName;  //银行账号开户行名称
    private String cusState; //客户状态
    private String salesmanId;  //销售ID
    private String channel;  //渠道
    private String verifyStat; //审核状态
    private String dataState; //数据状态
    private String email;//邮箱
    private String linkphone;//固定电话
    private String ctidEdt; //主体证件有效期
    private String relName; //实际控制人姓名
    private String citp; //实际控制人证件类型
    private String ctid; //实际控制人证件号
    private String citpNt; //实际控制人证件类型说明
    private String holdPer; //实际控制人持股比例
    private String holdAmt; //实际控制人持股金额
    private String riskGrade; // 风险级别(默认是未评级)
    private String isblacklist;// 黑名单标识（默认是未评级）
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private String[] cardholderIds;
    private String operatorStartValidity;//经办人证件开始日期 
    private String ctidStartValidity;//实际控制人证件起始日期    
    private String ctidEndValidity;//实际控制人证件有效期
    
    public String getCtidStartValidity() {
        return ctidStartValidity;
    }

    public String getCtidEndValidity() {
        return ctidEndValidity;
    }

    public void setCtidStartValidity(String ctidStartValidity) {
        this.ctidStartValidity = ctidStartValidity;
    }

    public void setCtidEndValidity(String ctidEndValidity) {
        this.ctidEndValidity = ctidEndValidity;
    }
            
    public String[] getCardholderIds() {
        return cardholderIds;
    }

    public String getOperatorStartValidity() {
        return operatorStartValidity;
    }

    public void setOperatorStartValidity(String operatorStartValidity) {
        this.operatorStartValidity = operatorStartValidity;
    }

    public void setCardholderIds(String[] cardholderIds) {
        this.cardholderIds = cardholderIds;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public String getCtidEdt() {
        return ctidEdt;
    }

    public void setCtidEdt(String ctidEdt) {
        this.ctidEdt = ctidEdt;
    }

    private PageDataDTO cardholderCardList = new PageDataDTO();
    
    private SellOrderCardListQueryDTO sellOrderCardListQueryDTO;
    
    private CustomerDTO customerDTO = new CustomerDTO();
        
    
    
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public PageDataDTO getCardholderCardList() {
        return cardholderCardList;
    }

    public void setCardholderCardList(PageDataDTO cardholderCardList) {
        this.cardholderCardList = cardholderCardList;
    }

    public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
        return sellOrderCardListQueryDTO;
    }

    public void setSellOrderCardListQueryDTO(SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
        this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRelationNo() {
        return relationNo;
    }
    
    public void setRelationNo(String relationNo) {
        this.relationNo = relationNo;
    }
    
    public String getRelationType() {
        return relationType;
    }
    
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
    public String getCompanyEnglishname() {
        return companyEnglishname;
    }

    public void setCompanyEnglishname(String companyEnglishname) {
        this.companyEnglishname = companyEnglishname;
    }

    public String getCorpName() {
        return corpName;
    }
    
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }
    
    public String getCorpCredType() {
        return corpCredType;
    }
    
    public void setCorpCredType(String corpCredType) {
        this.corpCredType = corpCredType;
    }
    
    public String getCorpCredId() {
        return corpCredId;
    }
    
    public void setCorpCredId(String corpCredId) {
        this.corpCredId = corpCredId;
    }
    
    public String getCorpCredValidity() {
        return corpCredValidity;
    }
    
    public void setCorpCredValidity(String corpCredValidity) {
        this.corpCredValidity = corpCredValidity;
    }
    
    public String getCorpGender() {
        return corpGender;
    }
    
    public void setCorpGender(String corpGender) {
        this.corpGender = corpGender;
    }
    
    public String getCorpCountyr() {
        return corpCountyr;
    }
    
    public void setCorpCountyr(String corpCountyr) {
        this.corpCountyr = corpCountyr;
    }
    
    public String getCorpAliasName() {
        return corpAliasName;
    }
    
    public void setCorpAliasName(String corpAliasName) {
        this.corpAliasName = corpAliasName;
    }
    
    public String getCorpBirthday() {
        return corpBirthday;
    }
    
    public void setCorpBirthday(String corpBirthday) {
        this.corpBirthday = corpBirthday;
    }
    
    public String getCorpProfession() {
        return corpProfession;
    }
    
    public void setCorpProfession(String corpProfession) {
        this.corpProfession = corpProfession;
    }
    
    public String getCorpTelephoneNumber() {
        return corpTelephoneNumber;
    }
    
    public void setCorpTelephoneNumber(String corpTelephoneNumber) {
        this.corpTelephoneNumber = corpTelephoneNumber;
    }
    
    public String getCorpAddress() {
        return corpAddress;
    }
    
    public void setCorpAddress(String corpAddress) {
        this.corpAddress = corpAddress;
    }
    
    public String getCompanyCountyr() {
        return companyCountyr;
    }
    
    public void setCompanyCountyr(String companyCountyr) {
        this.companyCountyr = companyCountyr;
    }
    
    public String getCompanyRegisteredAddress() {
        return companyRegisteredAddress;
    }
    
    public void setCompanyRegisteredAddress(String companyRegisteredAddress) {
        this.companyRegisteredAddress = companyRegisteredAddress;
    }
    
    public String getCompanyIdType() {
        return companyIdType;
    }
    
    public void setCompanyIdType(String companyIdType) {
        this.companyIdType = companyIdType;
    }
    
    public String getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    
    public String getCompanyAccountant() {
        return companyAccountant;
    }
    
    public void setCompanyAccountant(String companyAccountant) {
        this.companyAccountant = companyAccountant;
    }
    
    public String getCompanyDescription() {
        return companyDescription;
    }
    
    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
    
    public String getRegisteredCapital() {
        return registeredCapital;
    }
    
    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }
    
    public String getCompanySize() {
        return companySize;
    }
    
    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }
    
    
    
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCompanyFax() {
        return companyFax;
    }
    
    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }
    
    public String getOperatorType() {
        return operatorType;
    }
    
    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
    
    public String getOperatorId() {
        return operatorId;
    }
    
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    
    public String getOperatorValidity() {
        return operatorValidity;
    }
    
    public void setOperatorValidity(String operatorValidity) {
        this.operatorValidity = operatorValidity;
    }
    
    public String getOperatorTelephoneNumber() {
        return operatorTelephoneNumber;
    }
    
    public void setOperatorTelephoneNumber(String operatorTelephoneNumber) {
        this.operatorTelephoneNumber = operatorTelephoneNumber;
    }
    
    public String getOperatorAddress() {
        return operatorAddress;
    }
    
    public void setOperatorAddress(String operatorAddress) {
        this.operatorAddress = operatorAddress;
    }
    
    public String getBankAccount() {
        return bankAccount;
    }
    
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getCusState() {
        return cusState;
    }
    
    public void setCusState(String cusState) {
        this.cusState = cusState;
    }
    
    public String getSalesmanId() {
        return salesmanId;
    }
    
    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }
    
    public String getChannel() {
        return channel;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public String getVerifyStat() {
        return verifyStat;
    }
    
    public void setVerifyStat(String verifyStat) {
        this.verifyStat = verifyStat;
    }
    
    public String getDataState() {
        return dataState;
    }
    
    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getCitp() {
        return citp;
    }

    public void setCitp(String citp) {
        this.citp = citp;
    }

    public String getCtid() {
        return ctid;
    }

    public void setCtid(String ctid) {
        this.ctid = ctid;
    }

    public String getCitpNt() {
        return citpNt;
    }

    public void setCitpNt(String citpNt) {
        this.citpNt = citpNt;
    }

    public String getHoldPer() {
        return holdPer;
    }

    public void setHoldPer(String holdPer) {
        this.holdPer = holdPer;
    }

    public String getHoldAmt() {
        return holdAmt;
    }

    public void setHoldAmt(String holdAmt) {
        this.holdAmt = holdAmt;
    }

    public String getRiskGrade() {
        return riskGrade;
    }

    public void setRiskGrade(String riskGrade) {
        this.riskGrade = riskGrade;
    }

    public String getIsblacklist() {
        return isblacklist;
    }

    public void setIsblacklist(String isblacklist) {
        this.isblacklist = isblacklist;
	}
	
}
