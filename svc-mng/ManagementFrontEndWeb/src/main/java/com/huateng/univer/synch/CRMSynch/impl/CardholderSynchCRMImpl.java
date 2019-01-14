package com.huateng.univer.synch.CRMSynch.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderEnterPriseDAO;
import com.huateng.framework.ibatis.dao.CardholderPersonalDAO;
import com.huateng.framework.ibatis.dao.UniqueUserIdDAO;
import com.huateng.framework.ibatis.model.CardholderEnterPrise;
import com.huateng.framework.ibatis.model.CardholderEnterPriseKey;
import com.huateng.framework.ibatis.model.CardholderPersonal;
import com.huateng.framework.ibatis.model.CardholderPersonalKey;
import com.huateng.framework.ibatis.model.UniqueUserId;
import com.huateng.framework.ibatis.model.UniqueUserIdExample;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.synch.CRMSynch.CardholderSynchCRM;
import com.huateng.framework.util.DateUtil;

/**
 * 将通过审查的持卡人信息同步至CRM
 * @author yly
 *
 */
public class CardholderSynchCRMImpl implements CardholderSynchCRM{
    private Logger logger = Logger.getLogger(CardholderSynchCRMImpl.class);
    private UniqueUserIdDAO uniqueUserIdDAO;
    private CardholderEnterPriseDAO cardholderEnterPriseDAO;
    private CardholderPersonalDAO cardholderPersonalDAO; 
    private CommonsDAO commonsDAO;
    

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public UniqueUserIdDAO getUniqueUserIdDAO() {
        return uniqueUserIdDAO;
    }

    public void setUniqueUserIdDAO(UniqueUserIdDAO uniqueUserIdDAO) {
        this.uniqueUserIdDAO = uniqueUserIdDAO;
    }

    public CardholderEnterPriseDAO getCardholderEnterPriseDAO() {
        return cardholderEnterPriseDAO;
    }

    public void setCardholderEnterPriseDAO(CardholderEnterPriseDAO cardholderEnterPriseDAO) {
        this.cardholderEnterPriseDAO = cardholderEnterPriseDAO;
    }

    public CardholderPersonalDAO getCardholderPersonalDAO() {
        return cardholderPersonalDAO;
    }

    public void setCardholderPersonalDAO(CardholderPersonalDAO cardholderPersonalDAO) {
        this.cardholderPersonalDAO = cardholderPersonalDAO;
    }

    /**
     * 个人持卡人同步CRM
     * @param dto
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public void syncToPerCRM(CardholderDTO dto) throws Exception {
        try {
            //根据持卡人的身份证和身份证类型，客户号查询信息 
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andCustomerIdEqualTo(dto.getEntityId()).andIdNoEqualTo(dto.getIdNo()).andIdTypeEqualTo(dto.getIdType());
            List<UniqueUserId>  uniqueUserIdList = uniqueUserIdDAO.selectByExample(example);
            //查询到客户信息
            UniqueUserId uniqueUserId;
            if(!uniqueUserIdList.isEmpty()){
                uniqueUserId = uniqueUserIdList.get(0);
                String cardholderId = uniqueUserId.getCardHolderId();//个人持卡人编号
                //判断持卡人编号是否存在
                if(StringUtils.isEmpty(cardholderId)){
                    uniqueUserId.setCardHolderId(dto.getCardholderId());//个人持卡人编号                    
                    uniqueUserIdDAO.updateByPrimaryKeySelective(uniqueUserId); 
                }                             
            }else{
                //未查到客户信息
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCardHolderId(dto.getCardholderId());//个人持卡人编号
                uniqueUserId.setCustomerId(dto.getEntityId());//客户ID
                uniqueUserId.setIdNo(dto.getIdNo());//个人证件号
                uniqueUserId.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
                uniqueUserId.setType("1");//'类型:1个人账号，2企业账号,3特约商户'
                uniqueUserId.setIdType(dto.getIdType());//个人证件类型
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//统一客户ID
                uniqueUserIdDAO.insertSelective(uniqueUserId);                                   
            }        
            syncPersonToCRM(dto, uniqueUserId);// 个人持卡人信息同步          
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("个人持卡人同步CRM失败！");            
        }            
    }

    //个人持卡人信息同步
    private void syncPersonToCRM(CardholderDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CardholderPersonalKey key = new CardholderPersonalKey();
            key.setCardHolderId(id.getCardHolderId());//个人持卡人编号
            key.setSystemId(id.getSystemId());//'所属卡系统:1多用途卡系统，2单用途卡系统
            CardholderPersonal cardholder = cardholderPersonalDAO.selectByPrimaryKey(key);
            if (cardholder == null) {
                cardholder = new CardholderPersonal();
                cardholder.setUniqueId(id.getUniqueId());//统一客户ID
                createPerson(dto, cardholder);
                cardholderPersonalDAO.insertSelective(cardholder);
            } else {
                // update
                cardholder.setUniqueId(id.getUniqueId());//统一客户ID
                createPerson(dto, cardholder);
                cardholderPersonalDAO.updateByPrimaryKeySelective(cardholder);
            } 
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("个人持卡人同步CRM失败！");            
        }
              
    }

    //创建个人持卡人对象
    private void createPerson(CardholderDTO dto, CardholderPersonal cardholder) {
        cardholder.setCardHolderId(dto.getCardholderId());//个人持卡人编号
        cardholder.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
        cardholder.setIdType(dto.getIdType());//个人证件类型
        cardholder.setIdNo(dto.getIdNo());//个人证件号
        cardholder.setIdValidity(DateUtil.string2Dateyyyymmdd(dto.getValidity()));//证件有效期
        cardholder.setCardHolderName(dto.getFirstName());//持卡人姓名
        //cardholder.setCardHolderAlias(cardHolderAlias);//持卡人别名
        cardholder.setMobile(dto.getCardholderMobile());//手机号        
        cardholder.setGender(dto.getCardholderGender());//性别
        cardholder.setBirthday(DateUtil.string2Dateyyyymmdd(dto.getCardholderBirthday()));//生日
        cardholder.setNationality(dto.getCountry());//国籍
        cardholder.setAddress(dto.getMailingAddress());//地址
        cardholder.setOccupation(dto.getProfession());//职业
        cardholder.setAreaCode("@n");//受理区域
        //cardholder.setAccountNo(accountNo);//关联账户
        cardholder.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//创建时间戳
        cardholder.setCreateTime(dto.getCreateTime());//创建时间
        cardholder.setCreator(dto.getCreateUser());//创建人
        cardholder.setUpdateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime()));//更新时间戳
        cardholder.setUpdateTime(dto.getModifyTime());//更新时间
        cardholder.setUpdater(dto.getModifyUser());//更新人
        cardholder.setStatus(dto.getCardholderState());//持卡人状态
        //cardholder.setUserId(userId);//上汽会员ID
        cardholder.setEducation(dto.getCardholderEducation());//学历
        cardholder.setMarriaged(dto.getCardholderMarriage());//婚姻状况
        cardholder.setEmail(dto.getCardholderEmail());//邮箱
        cardholder.setCloseDate(DateUtil.string2Dateyyyymmdd(dto.getCloseDate()));//注销日期
        cardholder.setNation(dto.getCardholderNation());//民族       
    }

    /**
     * 企业持卡人同步CRM
     * @param dto
     * @throws Exception
     */
    @SuppressWarnings({"unchecked" })
    @Override
    public void syncToCusCRM(CompanyInfoDTO dto) throws Exception {
        try {
            //根据身份证和身份证类型,客户编号查询信息
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andCustomerIdEqualTo(dto.getEntityId()).andIdNoEqualTo(dto.getCompanyId()).andIdTypeEqualTo(dto.getCompanyIdType());        
            List<UniqueUserId>  uniqueUserIdList = uniqueUserIdDAO.selectByExample(example);
            //查询到客户信息
            UniqueUserId uniqueUserId;
            if(!uniqueUserIdList.isEmpty()){
                uniqueUserId = uniqueUserIdList.get(0);
                String cardholderId = uniqueUserId.getCardHolderId();//企业持卡人编号
                //判断持卡人编号是否存在
                if(StringUtils.isEmpty(cardholderId)){
                    uniqueUserId.setCardHolderId(dto.getRelationNo());//企业持卡人编号                    
                    uniqueUserIdDAO.updateByPrimaryKeySelective(uniqueUserId); 
                } 
            }else{
                //未查到客户信息
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCardHolderId(dto.getRelationNo());//企业持卡人编号
                uniqueUserId.setCustomerId(dto.getEntityId());//客户号
                uniqueUserId.setIdNo(dto.getCompanyId());//企业证件号码
                uniqueUserId.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
                uniqueUserId.setType("2");//'类型:1个人账号，2企业账号,3特约商户'
                uniqueUserId.setIdType(dto.getCompanyIdType());//企业证件类型
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//统一客户ID
                uniqueUserIdDAO.insertSelective(uniqueUserId);  
            }                            
            syncEnterpriseToCRM(dto, uniqueUserId);// 企业持卡人信息同步
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("企业持卡人同步失败");
        }                
    }

    //企业持卡人信息同步
    private void syncEnterpriseToCRM(CompanyInfoDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CardholderEnterPriseKey key = new CardholderEnterPriseKey();
            key.setCardHolderId(id.getCardHolderId());//企业持卡人编号
            key.setSystemId(id.getSystemId());//'所属卡系统:1多用途卡系统，2单用途卡系统
            CardholderEnterPrise companyInfo = cardholderEnterPriseDAO.selectByPrimaryKey(key);
            if (companyInfo == null) {
                companyInfo = new CardholderEnterPrise();
                companyInfo.setUniqueId(id.getUniqueId());//统一客户ID
                createEnterprise(dto,companyInfo);
                cardholderEnterPriseDAO.insertSelective(companyInfo);
            } else {
                //update
                companyInfo.setUniqueId(id.getUniqueId());//统一客户ID
                createEnterprise(dto, companyInfo);
                cardholderEnterPriseDAO.updateByPrimaryKeySelective(companyInfo);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("企业持卡人同步失败");
        }
            
    }

    //创建企业持卡人对象
    private void createEnterprise(CompanyInfoDTO dto, CardholderEnterPrise companyInfo) {
        ReflectionUtil.copyProperties(dto, companyInfo);
        companyInfo.setCardHolderId(dto.getRelationNo());//持卡人id
        companyInfo.setSystemId("1");//所属卡系统:1多用途卡系统，2单用途卡系统
        companyInfo.setEnterpriseCname(dto.getCompanyName());//公司名称
        companyInfo.setEnterpriseEname(dto.getCompanyEnglishname());//公司英文名
        companyInfo.setLegalIdType(dto.getCompanyIdType());//法人证件类型
        companyInfo.setLegalIdNo(dto.getCorpCredId());//法人证件号
        companyInfo.setLegalIdValidity(DateUtil.string2Dateyyyymmdd(dto.getCorpCredValidity()));//法人证件有效期
        companyInfo.setLegalName(dto.getCorpName());//法人姓名
        companyInfo.setLegalAlias(dto.getCorpAliasName());//法人别名
        companyInfo.setLegalMobile(dto.getCorpTelephoneNumber());//法人手机号
        companyInfo.setLegalGender(dto.getCorpGender());//法人性别：1男，2女，3未知
        companyInfo.setLegalBirthday(DateUtil.string2date(dto.getCorpBirthday()));//法人出生日期
        companyInfo.setLegalNationality(dto.getCorpCountyr());//法人国籍
        companyInfo.setLegalAddress(dto.getCorpAddress());//法人地址
        companyInfo.setLegalOccupation(dto.getCorpProfession());//法人职业
        companyInfo.setEnterpriseCountry(dto.getCompanyCountyr());//企业国别
        companyInfo.setEnterpriseRegistry(dto.getCompanyRegisteredAddress());//企业注册地
        companyInfo.setEnterpriseIdType(dto.getCompanyIdType());//企业证件类型
        companyInfo.setEnterpriseIdNo(dto.getCompanyId());//企业证件号
        companyInfo.setEnterpriseAccountant(dto.getCompanyAccountant());//公司会计
        companyInfo.setEnterpriseDesc(dto.getCompanyDescription());//公司描述，经营范围
        companyInfo.setAreaCode("@n");//受理区域
        companyInfo.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//创建时间戳
        companyInfo.setCreateTime(dto.getCreateTime());//创建时间：格式yyyyMMddHHmmss
        companyInfo.setCreator(dto.getCreateUser());//创建人id'
        companyInfo.setUpdateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime()));//更新时间戳
        companyInfo.setUpdateTime(dto.getModifyTime());//更新时间：格式yyyyMMddHHmmss
        companyInfo.setUpdater(dto.getModifyUser());//更新人id
        companyInfo.setStatus(dto.getCusState());//状态:1有效，0无效
        //companyInfo.setIndustry(industry);//行业
        companyInfo.setAgencyName(dto.getOperatorName());//代办理人姓名
        companyInfo.setAgencyIdtype(dto.getOperatorType());//代办理人证件类型
        companyInfo.setAgencyIdno(dto.getOperatorId());//代办理人证件号
        companyInfo.setAgencyIdvalidity(DateUtil.string2Dateyyyymmdd(dto.getOperatorValidity()));//代办理人证件有效期
        companyInfo.setBankAccountNo(dto.getBankAccount());//银行账号
        companyInfo.setBankName(dto.getBankName());//开户银行名称
        companyInfo.setCmail(dto.getEmail());//电子邮件
        companyInfo.setLinkphone(dto.getLinkphone());//联系人固定电话
        companyInfo.setScal(dto.getCompanySize());//企业规模:1:500人以上,2:200-500人,3:10-200人,4:10人及以下
        companyInfo.setRegCptl(dto.getRegisteredCapital());//注册资本
        companyInfo.setCtidEdt(dto.getCtidEdt());//主体证件有效期:YYYY-MM-DD（社会统一信用代码）        
    }
    
    /**
     * 根据传入的证件类型,产生crm对应的证件类型 1:身份证 2:护照 3:其他
     * 
     * @param id
     * @return
     */
    /*private String judgeIdType(String id) {
            if ("1".equals(id)) {
                    return "1";
            } else if ("4".equals(id)) {
                    return "2";
            }
            return "3";
    }*/
}
