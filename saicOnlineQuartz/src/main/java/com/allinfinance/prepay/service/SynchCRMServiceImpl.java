package com.allinfinance.prepay.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.CardholderEnterPriseMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardholderPersonalMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerEnterPriseMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerPersonalMapper;
import com.allinfinance.prepay.mapper.svc_mng.UniqueUserIdMapper;
import com.allinfinance.prepay.model.CardholderEnterPrise;
import com.allinfinance.prepay.model.CardholderEnterPriseKey;
import com.allinfinance.prepay.model.CardholderPersonal;
import com.allinfinance.prepay.model.CardholderPersonalKey;
import com.allinfinance.prepay.model.CustomerEnterPrise;
import com.allinfinance.prepay.model.CustomerEnterPriseKey;
import com.allinfinance.prepay.model.CustomerPersonal;
import com.allinfinance.prepay.model.CustomerPersonalKey;
import com.allinfinance.prepay.model.UniqueUserId;
import com.allinfinance.prepay.model.UniqueUserIdExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;

/**
 * CRM同步实现
 * @author yly
 *
 */
@Service
public class SynchCRMServiceImpl implements SynchCRMService{
    private static Logger logger = Logger.getLogger(SynchCRMServiceImpl.class);
    @Autowired
    private CommonsDAO commonsDAO;
    @Autowired
    private UniqueUserIdMapper uniqueUserIdMapper;
    @Autowired
    private CardholderPersonalMapper cardholderPersonalMapper;
    @Autowired
    private CardholderEnterPriseMapper cardholderEnterPriseMapper;
    @Autowired
    private CustomerPersonalMapper customerPersonalMapper;
    @Autowired
    private CustomerEnterPriseMapper customerEnterPriseMapper;

    /**
     * 个人持卡人同步CRM
     * @param dto
     * @throws Exception
     */
    public void syncToPerCRM(CardholderDTO dto) throws Exception {
        try {
            //根据持卡人的身份证和身份证类型，客户号查询信息 
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andIdNoEqualTo(dto.getIdNo()).andIdTypeEqualTo(dto.getIdType()).andTypeEqualTo("1");
            List<UniqueUserId>  uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //查询到客户信息
            UniqueUserId uniqueUserId;
            if(!uniqueUserIdList.isEmpty()){
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCardHolderId(dto.getCardholderId());//个人持卡人编号                    
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId);                            
            }else{
                //未查到客户信息
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCardHolderId(dto.getCardholderId());//个人持卡人编号
                //uniqueUserId.setCustomerId(dto.getEntityId());//客户ID
                uniqueUserId.setIdNo(dto.getIdNo());//个人证件号
                uniqueUserId.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
                uniqueUserId.setType("1");//'类型:1个人账号，2企业账号,3特约商户'
                uniqueUserId.setIdType(dto.getIdType());//个人证件类型
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//统一客户ID
                uniqueUserIdMapper.insertSelective(uniqueUserId);                                   
            }            
            // 个人持卡人信息同步
            syncPersonToCRM(dto, uniqueUserId);                 
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
            CardholderPersonal cardholder = cardholderPersonalMapper.selectByPrimaryKey(key);
            if (cardholder == null) {
                cardholder = new CardholderPersonal();
                cardholder.setUniqueId(id.getUniqueId());//统一客户ID
                createPerson(dto, cardholder);
                cardholderPersonalMapper.insertSelective(cardholder);
            } else {
                // update
                cardholder.setUniqueId(id.getUniqueId());//统一客户ID
                createPerson(dto, cardholder);
                cardholderPersonalMapper.updateByPrimaryKeySelective(cardholder);
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
    public void syncToCusCRM(CompanyInfoDTO dto) throws Exception {
        try {
            //根据身份证和身份证类型,客户编号查询信息
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andIdNoEqualTo(dto.getCompanyId()).andIdTypeEqualTo(dto.getCompanyIdType()).andTypeEqualTo("2");        
            List<UniqueUserId>  uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //查询到客户信息
            UniqueUserId uniqueUserId;
            if(!uniqueUserIdList.isEmpty()){
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCardHolderId(dto.getRelationNo());//企业持卡人编号                    
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId); 
            }else{
                //未查到客户信息
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCardHolderId(dto.getRelationNo());//企业持卡人编号
                //uniqueUserId.setCustomerId(dto.getEntityId());//客户号
                uniqueUserId.setIdNo(dto.getCompanyId());//企业证件号码
                uniqueUserId.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
                uniqueUserId.setType("2");//'类型:1个人账号，2企业账号,3特约商户'
                uniqueUserId.setIdType(dto.getCompanyIdType());//企业证件类型
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//统一客户ID
                uniqueUserIdMapper.insertSelective(uniqueUserId);  
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
            CardholderEnterPrise companyInfo = cardholderEnterPriseMapper.selectByPrimaryKey(key);
            if (companyInfo == null) {
                companyInfo = new CardholderEnterPrise();
                companyInfo.setUniqueId(id.getUniqueId());//统一客户ID
                createEnterprise(dto,companyInfo);
                cardholderEnterPriseMapper.insertSelective(companyInfo);
            } else {
                //update
                companyInfo.setUniqueId(id.getUniqueId());//统一客户ID
                createEnterprise(dto, companyInfo);
                cardholderEnterPriseMapper.updateByPrimaryKeySelective(companyInfo);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("企业持卡人同步失败");
        }
            
    }
    
    
    //创建企业持卡人对象
    private void createEnterprise(CompanyInfoDTO dto, CardholderEnterPrise companyInfo) {
        companyInfo.setCardHolderId(dto.getRelationNo());//持卡人id
        companyInfo.setSystemId("1");//所属卡系统:1多用途卡系统，2单用途卡系统
        companyInfo.setEnterpriseCname(dto.getCompanyName());//公司名称
        companyInfo.setEnterpriseEname(dto.getCompanyEnglishname());//公司英文名
        companyInfo.setLegalIdType(dto.getCompanyIdType());//法人证件类型
        companyInfo.setLegalIdNo(dto.getCorpCredId());//法人证件号
        companyInfo.setLegalIdValidity(DateUtil.string2date(dto.getCorpCredValidity()));//法人证件有效期
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
        companyInfo.setAgencyIdvalidity(DateUtil.string2date(dto.getOperatorValidity()));//代办理人证件有效期
        companyInfo.setBankAccountNo(dto.getBankAccount());//银行账号
        companyInfo.setBankName(dto.getBankName());//开户银行名称
        companyInfo.setCmail(dto.getEmail());//电子邮件
        companyInfo.setLinkphone(dto.getLinkphone());//联系人固定电话
        companyInfo.setScal(dto.getCompanySize());//企业规模:1:500人以上,2:200-500人,3:10-200人,4:10人及以下
        companyInfo.setRegCptl(dto.getRegisteredCapital());//注册资本
        companyInfo.setCtidEdt(dto.getCtidEdt());//主体证件有效期:YYYY-MM-DD（社会统一信用代码）        
    }


    
    
    /**
     * 个人客户同步CRM
     * @param dto
     * @throws Exception
     */
    public void synchPerToCRM(CustomerDTO dto) throws Exception {
        try {
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andTypeEqualTo("1").andIdNoEqualTo(dto.getCorpCredId())
                    .andIdTypeEqualTo(dto.getCorpCredType()).andSystemIdEqualTo("1");
            List<UniqueUserId> uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //未查到客户信息
            UniqueUserId uniqueUserId;
            if(null == uniqueUserIdList || uniqueUserIdList.size()==0 ){
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCustomerId(dto.getEntityId());//客户号
                uniqueUserId.setIdNo(dto.getCorpCredId());//个人证件号码
                uniqueUserId.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
                uniqueUserId.setType("1");//'类型:1个人账号，2企业账号,3特约商户'
                uniqueUserId.setIdType(dto.getCorpCredType());//个人证件类型
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//统一客户ID
                uniqueUserIdMapper.insertSelective(uniqueUserId); 
            }else{
                //查询到客户信息
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCustomerId(dto.getEntityId());//个人客户编号
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId); 
            }
            synchPerCustomerToCRM(dto, uniqueUserId);// 个人客户同步
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("个人客户同步失败！");
        }                
    }
    
    //个人客户信息同步
    private void synchPerCustomerToCRM(CustomerDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CustomerPersonalKey key = new CustomerPersonalKey();
            key.setCustomerId(id.getCustomerId());//个人客户编号
            key.setSystemId(id.getSystemId());//'所属卡系统:1多用途卡系统，2单用途卡系统
            CustomerPersonal customer = customerPersonalMapper.selectByPrimaryKey(key);
            if(customer == null){
                customer = new CustomerPersonal();
                customer.setUniqueId(id.getUniqueId());//统一客户ID
                createPerCustomer(dto, customer);
                customerPersonalMapper.insertSelective(customer);
            } else {
                //update
                customer.setUniqueId(id.getUniqueId());//统一客户ID
                createPerCustomer(dto, customer);
                customerPersonalMapper.updateByPrimaryKeySelective(customer);
            }  
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("个人客户同步失败");
        }        
    }
    
    //创建个人客户对象


    private void createPerCustomer(CustomerDTO dto, CustomerPersonal customer) {
        customer.setCustomerId(dto.getEntityId());// 客户id(*)
        customer.setSystemId("1");// 此系统为多用途(*)
        customer.setIdType(dto.getCorpCredType());// 证件类型
        customer.setIdNo(dto.getCorpCredId());// 证件号
        customer.setIdValidity(DateUtil.string2Dateyyyymmdd(dto.getValidity()));// 证件有效期
        customer.setCustomerName(dto.getCustomerName());// 客户姓名
        //customer.setCustomerAlias(dto.getCorpAliasName());// 客户别名
        customer.setMobile(dto.getCustomerTelephone());// 联系电话(*)
        customer.setGender(dto.getGender());//性别
        //customer.setBirthday(DateUtil.string2Dateyyyymmdd(dto.getCorpBirthday()));//出生日期
        customer.setNationality(dto.getNationality());//国家
        customer.setAddress(dto.getCustomerAddress());//客户地址
        customer.setOccupation(dto.getAwareness());// 职业
        //customer.setAccountNo(dto.getExternalId());// 关联账号
        customer.setAreaCode(dto.getAcceptArea() == null ? "@n" : dto.getAcceptArea());// 受理区域(*)
        customer.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//创建的时间戳
        customer.setCreateTime(dto.getCreateTime());//创建时间
        customer.setCreator(dto.getCreateUser());//创建人
        customer.setUpdateTimestamp((DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime())));//修改日期戳
        customer.setUpdateTime(dto.getModifyTime());//修改时间
        customer.setUpdater(dto.getModifyUser());//修改人
        customer.setStatus("1");//客户状态 默认1
        customer.setEducation(dto.getEducation());//学历
        customer.setMarriaged(dto.getMarriage());//婚姻状况
        customer.setEmail(dto.getEmail());//邮箱
        customer.setCloseDate(dto.getCloseDateDate());//关闭日期
        customer.setNation(dto.getNation());//民族
    }


    /**
     * 企业客户同步CRM
     * @param dto
     * @throws Exception
     */
    public void synchCusToCRM(CompanyInfoDTO dto) throws Exception {
        try {
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andTypeEqualTo("2").andIdNoEqualTo(dto.getCompanyId())
                    .andIdTypeEqualTo(dto.getCompanyIdType()).andSystemIdEqualTo("1");
            List<UniqueUserId> uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //未查到客户信息
            UniqueUserId uniqueUserId;
            if(null == uniqueUserIdList || uniqueUserIdList.size()==0 ){
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCustomerId(dto.getEntityId());//客户号
                uniqueUserId.setIdNo(dto.getCompanyId());//企业证件号码
                uniqueUserId.setSystemId("1");//'所属卡系统:1多用途卡系统，2单用途卡系统
                uniqueUserId.setType("2");//'类型:1个人账号，2企业账号,3特约商户'
                uniqueUserId.setIdType(dto.getCompanyIdType());//企业证件类型
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//统一客户ID
                uniqueUserIdMapper.insertSelective(uniqueUserId); 
            }else{
                //查询到客户信息
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCustomerId(dto.getRelationNo());//企业客户编号
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId); 
            }
            synchCusCustomerToCRM(dto, uniqueUserId);//企业客户同步        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("企业客户同步失败！");
        }
    }


    //企业客户信息同步
    private void synchCusCustomerToCRM(CompanyInfoDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CustomerEnterPriseKey key = new CustomerEnterPriseKey();
            key.setCustomerId(id.getCustomerId());//企业客户编号
            key.setSystemId(id.getSystemId());//'所属卡系统:1多用途卡系统，2单用途卡系统
            CustomerEnterPrise companyInfo = customerEnterPriseMapper.selectByPrimaryKey(key);
            if(companyInfo == null){
                companyInfo = new CustomerEnterPrise();
                companyInfo.setUniqueId(id.getUniqueId());//统一客户ID
                createCusCustomer(dto,companyInfo);
                customerEnterPriseMapper.insertSelective(companyInfo);            
            } else {
                //update
                companyInfo.setUniqueId(id.getUniqueId());//统一客户ID
                createCusCustomer(dto, companyInfo);
                customerEnterPriseMapper.updateByPrimaryKeySelective(companyInfo);    
            }           
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("企业客户同步失败");
        }             
    }

    //创建企业客户对象
    private void createCusCustomer(CompanyInfoDTO dto, CustomerEnterPrise companyInfo) {
        ReflectionUtil.copyProperties(dto, companyInfo);
        companyInfo.setCustomerId(dto.getEntityId());// 客户id(*)
        companyInfo.setSystemId("1");// 多用途(*)
        companyInfo.setEnterpriseCname(dto.getCompanyName());// 公司名称(*)
        companyInfo.setEnterpriseEname(dto.getCompanyEnglishname());//公司英文名
        companyInfo.setLegalIdType(dto.getCorpCredType());// 法人证件类型(*)
        companyInfo.setLegalIdNo(dto.getCorpCredId());// 法人证件号(*)
        companyInfo.setLegalIdValidity(DateUtil.string2date(dto.getCorpCredValidity()));// 法人证件有效期(*)
        companyInfo.setLegalName(dto.getCorpName());// 法人姓名(*)
        companyInfo.setLegalAlias(dto.getCorpAliasName());//法人别名
        companyInfo.setLegalMobile(dto.getCorpTelephoneNumber());// 法人电话(*)
        companyInfo.setLegalGender(dto.getCorpGender());// 法人性别(*)
        companyInfo.setLegalBirthday(DateUtil.string2date(dto.getCorpBirthday()));// 法人出生日期(*)
        companyInfo.setLegalNationality(dto.getCorpCountyr());// 法人国籍(*)
        companyInfo.setLegalAddress(dto.getCorpAddress());// 法人住址(*)
        companyInfo.setLegalOccupation(dto.getCorpProfession());// 法人职业(*)
        companyInfo.setEnterpriseCountry(dto.getCompanyCountyr());// 企业国别(*)
        companyInfo.setEnterpriseRegistry(dto.getCompanyRegisteredAddress());// 企业注册地(*)
        companyInfo.setEnterpriseIdType(dto.getCompanyIdType());// 企业证件类型(*)
        companyInfo.setEnterpriseIdNo(dto.getCompanyId());// 企业证件号(*)
        companyInfo.setEnterpriseAccountant(dto.getCompanyAccountant());// 公司会计
        companyInfo.setEnterpriseDesc(dto.getCompanyDescription());// 公司描述，经营范围(*)
        //companyInfo.setAccountNo(dto.getExternalId());// 关联账号
        companyInfo.setAreaCode("@n");// 受理区域(*)
        companyInfo.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));// 创建的时间戳
        companyInfo.setCreateTime(dto.getCreateTime());
        companyInfo.setCreator(dto.getCreateUser());// 创建人
        companyInfo.setUpdateTimestamp((DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime())));// 修改日期戳
        companyInfo.setUpdateTime(dto.getModifyTime());
        companyInfo.setUpdater(dto.getModifyUser());
        companyInfo.setStatus(dto.getCusState());//客户状态
        companyInfo.setIndustry(dto.getCompanyFax());// 客户行业
        companyInfo.setAgencyName(dto.getOperatorName());// 代办人
        companyInfo.setAgencyIdtype(dto.getOperatorType());// 代办人证件类型
        companyInfo.setAgencyIdno(dto.getOperatorId());//经办人证件号
        companyInfo.setAgencyIdvalidity(DateUtil.string2date(dto.getOperatorValidity()));//经办人证件有效期
        companyInfo.setBankAccountNo(dto.getBankAccount());//银行账户
        companyInfo.setBankName(dto.getBankName());//银行名称
        companyInfo.setCmail(dto.getEmail());//公司邮箱
        companyInfo.setLinkphone(dto.getLinkphone());//企业固话
        companyInfo.setScal(dto.getCompanySize());// 企业规模
        companyInfo.setRegCptl(dto.getRegisteredCapital());//注册资本
        companyInfo.setCtidEdt(dto.getCtidEdt());// 主体证件有效期，
    }       
    
}
