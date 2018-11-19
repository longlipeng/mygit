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
 * CRMͬ��ʵ��
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
     * ���˳ֿ���ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void syncToPerCRM(CardholderDTO dto) throws Exception {
        try {
            //���ݳֿ��˵����֤�����֤���ͣ��ͻ��Ų�ѯ��Ϣ 
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andIdNoEqualTo(dto.getIdNo()).andIdTypeEqualTo(dto.getIdType()).andTypeEqualTo("1");
            List<UniqueUserId>  uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //��ѯ���ͻ���Ϣ
            UniqueUserId uniqueUserId;
            if(!uniqueUserIdList.isEmpty()){
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCardHolderId(dto.getCardholderId());//���˳ֿ��˱��                    
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId);                            
            }else{
                //δ�鵽�ͻ���Ϣ
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCardHolderId(dto.getCardholderId());//���˳ֿ��˱��
                //uniqueUserId.setCustomerId(dto.getEntityId());//�ͻ�ID
                uniqueUserId.setIdNo(dto.getIdNo());//����֤����
                uniqueUserId.setSystemId("1");//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
                uniqueUserId.setType("1");//'����:1�����˺ţ�2��ҵ�˺�,3��Լ�̻�'
                uniqueUserId.setIdType(dto.getIdType());//����֤������
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//ͳһ�ͻ�ID
                uniqueUserIdMapper.insertSelective(uniqueUserId);                                   
            }            
            // ���˳ֿ�����Ϣͬ��
            syncPersonToCRM(dto, uniqueUserId);                 
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("���˳ֿ���ͬ��CRMʧ�ܣ�");            
        }            
    }
        
    //���˳ֿ�����Ϣͬ��
    private void syncPersonToCRM(CardholderDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CardholderPersonalKey key = new CardholderPersonalKey();
            key.setCardHolderId(id.getCardHolderId());//���˳ֿ��˱��
            key.setSystemId(id.getSystemId());//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
            CardholderPersonal cardholder = cardholderPersonalMapper.selectByPrimaryKey(key);
            if (cardholder == null) {
                cardholder = new CardholderPersonal();
                cardholder.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createPerson(dto, cardholder);
                cardholderPersonalMapper.insertSelective(cardholder);
            } else {
                // update
                cardholder.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createPerson(dto, cardholder);
                cardholderPersonalMapper.updateByPrimaryKeySelective(cardholder);
            } 
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("���˳ֿ���ͬ��CRMʧ�ܣ�");            
        }
              
    }

    //�������˳ֿ��˶���
    private void createPerson(CardholderDTO dto, CardholderPersonal cardholder) {
        cardholder.setCardHolderId(dto.getCardholderId());//���˳ֿ��˱��
        cardholder.setSystemId("1");//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
        cardholder.setIdType(dto.getIdType());//����֤������
        cardholder.setIdNo(dto.getIdNo());//����֤����
        cardholder.setIdValidity(DateUtil.string2Dateyyyymmdd(dto.getValidity()));//֤����Ч��
        cardholder.setCardHolderName(dto.getFirstName());//�ֿ�������
        //cardholder.setCardHolderAlias(cardHolderAlias);//�ֿ��˱���
        cardholder.setMobile(dto.getCardholderMobile());//�ֻ���        
        cardholder.setGender(dto.getCardholderGender());//�Ա�
        cardholder.setBirthday(DateUtil.string2Dateyyyymmdd(dto.getCardholderBirthday()));//����
        cardholder.setNationality(dto.getCountry());//����
        cardholder.setAddress(dto.getMailingAddress());//��ַ
        cardholder.setOccupation(dto.getProfession());//ְҵ
        cardholder.setAreaCode("@n");//��������
        //cardholder.setAccountNo(accountNo);//�����˻�
        cardholder.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//����ʱ���
        cardholder.setCreateTime(dto.getCreateTime());//����ʱ��
        cardholder.setCreator(dto.getCreateUser());//������
        cardholder.setUpdateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime()));//����ʱ���
        cardholder.setUpdateTime(dto.getModifyTime());//����ʱ��
        cardholder.setUpdater(dto.getModifyUser());//������
        cardholder.setStatus(dto.getCardholderState());//�ֿ���״̬
        //cardholder.setUserId(userId);//������ԱID
        cardholder.setEducation(dto.getCardholderEducation());//ѧ��
        cardholder.setMarriaged(dto.getCardholderMarriage());//����״��
        cardholder.setEmail(dto.getCardholderEmail());//����
        cardholder.setCloseDate(DateUtil.string2Dateyyyymmdd(dto.getCloseDate()));//ע������
        cardholder.setNation(dto.getCardholderNation());//����       
    }

    /**
     * ��ҵ�ֿ���ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void syncToCusCRM(CompanyInfoDTO dto) throws Exception {
        try {
            //�������֤�����֤����,�ͻ���Ų�ѯ��Ϣ
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andIdNoEqualTo(dto.getCompanyId()).andIdTypeEqualTo(dto.getCompanyIdType()).andTypeEqualTo("2");        
            List<UniqueUserId>  uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //��ѯ���ͻ���Ϣ
            UniqueUserId uniqueUserId;
            if(!uniqueUserIdList.isEmpty()){
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCardHolderId(dto.getRelationNo());//��ҵ�ֿ��˱��                    
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId); 
            }else{
                //δ�鵽�ͻ���Ϣ
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCardHolderId(dto.getRelationNo());//��ҵ�ֿ��˱��
                //uniqueUserId.setCustomerId(dto.getEntityId());//�ͻ���
                uniqueUserId.setIdNo(dto.getCompanyId());//��ҵ֤������
                uniqueUserId.setSystemId("1");//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
                uniqueUserId.setType("2");//'����:1�����˺ţ�2��ҵ�˺�,3��Լ�̻�'
                uniqueUserId.setIdType(dto.getCompanyIdType());//��ҵ֤������
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//ͳһ�ͻ�ID
                uniqueUserIdMapper.insertSelective(uniqueUserId);  
            }                            
            syncEnterpriseToCRM(dto, uniqueUserId);// ��ҵ�ֿ�����Ϣͬ��
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("��ҵ�ֿ���ͬ��ʧ��");
        }                
        
    }
    
    //��ҵ�ֿ�����Ϣͬ��
    private void syncEnterpriseToCRM(CompanyInfoDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CardholderEnterPriseKey key = new CardholderEnterPriseKey();
            key.setCardHolderId(id.getCardHolderId());//��ҵ�ֿ��˱��
            key.setSystemId(id.getSystemId());//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
            CardholderEnterPrise companyInfo = cardholderEnterPriseMapper.selectByPrimaryKey(key);
            if (companyInfo == null) {
                companyInfo = new CardholderEnterPrise();
                companyInfo.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createEnterprise(dto,companyInfo);
                cardholderEnterPriseMapper.insertSelective(companyInfo);
            } else {
                //update
                companyInfo.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createEnterprise(dto, companyInfo);
                cardholderEnterPriseMapper.updateByPrimaryKeySelective(companyInfo);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("��ҵ�ֿ���ͬ��ʧ��");
        }
            
    }
    
    
    //������ҵ�ֿ��˶���
    private void createEnterprise(CompanyInfoDTO dto, CardholderEnterPrise companyInfo) {
        companyInfo.setCardHolderId(dto.getRelationNo());//�ֿ���id
        companyInfo.setSystemId("1");//������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
        companyInfo.setEnterpriseCname(dto.getCompanyName());//��˾����
        companyInfo.setEnterpriseEname(dto.getCompanyEnglishname());//��˾Ӣ����
        companyInfo.setLegalIdType(dto.getCompanyIdType());//����֤������
        companyInfo.setLegalIdNo(dto.getCorpCredId());//����֤����
        companyInfo.setLegalIdValidity(DateUtil.string2date(dto.getCorpCredValidity()));//����֤����Ч��
        companyInfo.setLegalName(dto.getCorpName());//��������
        companyInfo.setLegalAlias(dto.getCorpAliasName());//���˱���
        companyInfo.setLegalMobile(dto.getCorpTelephoneNumber());//�����ֻ���
        companyInfo.setLegalGender(dto.getCorpGender());//�����Ա�1�У�2Ů��3δ֪
        companyInfo.setLegalBirthday(DateUtil.string2date(dto.getCorpBirthday()));//���˳�������
        companyInfo.setLegalNationality(dto.getCorpCountyr());//���˹���
        companyInfo.setLegalAddress(dto.getCorpAddress());//���˵�ַ
        companyInfo.setLegalOccupation(dto.getCorpProfession());//����ְҵ
        companyInfo.setEnterpriseCountry(dto.getCompanyCountyr());//��ҵ����
        companyInfo.setEnterpriseRegistry(dto.getCompanyRegisteredAddress());//��ҵע���
        companyInfo.setEnterpriseIdType(dto.getCompanyIdType());//��ҵ֤������
        companyInfo.setEnterpriseIdNo(dto.getCompanyId());//��ҵ֤����
        companyInfo.setEnterpriseAccountant(dto.getCompanyAccountant());//��˾���
        companyInfo.setEnterpriseDesc(dto.getCompanyDescription());//��˾��������Ӫ��Χ
        companyInfo.setAreaCode("@n");//��������
        companyInfo.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//����ʱ���
        companyInfo.setCreateTime(dto.getCreateTime());//����ʱ�䣺��ʽyyyyMMddHHmmss
        companyInfo.setCreator(dto.getCreateUser());//������id'
        companyInfo.setUpdateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime()));//����ʱ���
        companyInfo.setUpdateTime(dto.getModifyTime());//����ʱ�䣺��ʽyyyyMMddHHmmss
        companyInfo.setUpdater(dto.getModifyUser());//������id
        companyInfo.setStatus(dto.getCusState());//״̬:1��Ч��0��Ч
        //companyInfo.setIndustry(industry);//��ҵ
        companyInfo.setAgencyName(dto.getOperatorName());//������������
        companyInfo.setAgencyIdtype(dto.getOperatorType());//��������֤������
        companyInfo.setAgencyIdno(dto.getOperatorId());//��������֤����
        companyInfo.setAgencyIdvalidity(DateUtil.string2date(dto.getOperatorValidity()));//��������֤����Ч��
        companyInfo.setBankAccountNo(dto.getBankAccount());//�����˺�
        companyInfo.setBankName(dto.getBankName());//������������
        companyInfo.setCmail(dto.getEmail());//�����ʼ�
        companyInfo.setLinkphone(dto.getLinkphone());//��ϵ�˹̶��绰
        companyInfo.setScal(dto.getCompanySize());//��ҵ��ģ:1:500������,2:200-500��,3:10-200��,4:10�˼�����
        companyInfo.setRegCptl(dto.getRegisteredCapital());//ע���ʱ�
        companyInfo.setCtidEdt(dto.getCtidEdt());//����֤����Ч��:YYYY-MM-DD�����ͳһ���ô��룩        
    }


    
    
    /**
     * ���˿ͻ�ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void synchPerToCRM(CustomerDTO dto) throws Exception {
        try {
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andTypeEqualTo("1").andIdNoEqualTo(dto.getCorpCredId())
                    .andIdTypeEqualTo(dto.getCorpCredType()).andSystemIdEqualTo("1");
            List<UniqueUserId> uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //δ�鵽�ͻ���Ϣ
            UniqueUserId uniqueUserId;
            if(null == uniqueUserIdList || uniqueUserIdList.size()==0 ){
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCustomerId(dto.getEntityId());//�ͻ���
                uniqueUserId.setIdNo(dto.getCorpCredId());//����֤������
                uniqueUserId.setSystemId("1");//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
                uniqueUserId.setType("1");//'����:1�����˺ţ�2��ҵ�˺�,3��Լ�̻�'
                uniqueUserId.setIdType(dto.getCorpCredType());//����֤������
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//ͳһ�ͻ�ID
                uniqueUserIdMapper.insertSelective(uniqueUserId); 
            }else{
                //��ѯ���ͻ���Ϣ
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCustomerId(dto.getEntityId());//���˿ͻ����
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId); 
            }
            synchPerCustomerToCRM(dto, uniqueUserId);// ���˿ͻ�ͬ��
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("���˿ͻ�ͬ��ʧ�ܣ�");
        }                
    }
    
    //���˿ͻ���Ϣͬ��
    private void synchPerCustomerToCRM(CustomerDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CustomerPersonalKey key = new CustomerPersonalKey();
            key.setCustomerId(id.getCustomerId());//���˿ͻ����
            key.setSystemId(id.getSystemId());//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
            CustomerPersonal customer = customerPersonalMapper.selectByPrimaryKey(key);
            if(customer == null){
                customer = new CustomerPersonal();
                customer.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createPerCustomer(dto, customer);
                customerPersonalMapper.insertSelective(customer);
            } else {
                //update
                customer.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createPerCustomer(dto, customer);
                customerPersonalMapper.updateByPrimaryKeySelective(customer);
            }  
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("���˿ͻ�ͬ��ʧ��");
        }        
    }
    
    //�������˿ͻ�����


    private void createPerCustomer(CustomerDTO dto, CustomerPersonal customer) {
        customer.setCustomerId(dto.getEntityId());// �ͻ�id(*)
        customer.setSystemId("1");// ��ϵͳΪ����;(*)
        customer.setIdType(dto.getCorpCredType());// ֤������
        customer.setIdNo(dto.getCorpCredId());// ֤����
        customer.setIdValidity(DateUtil.string2Dateyyyymmdd(dto.getValidity()));// ֤����Ч��
        customer.setCustomerName(dto.getCustomerName());// �ͻ�����
        //customer.setCustomerAlias(dto.getCorpAliasName());// �ͻ�����
        customer.setMobile(dto.getCustomerTelephone());// ��ϵ�绰(*)
        customer.setGender(dto.getGender());//�Ա�
        //customer.setBirthday(DateUtil.string2Dateyyyymmdd(dto.getCorpBirthday()));//��������
        customer.setNationality(dto.getNationality());//����
        customer.setAddress(dto.getCustomerAddress());//�ͻ���ַ
        customer.setOccupation(dto.getAwareness());// ְҵ
        //customer.setAccountNo(dto.getExternalId());// �����˺�
        customer.setAreaCode(dto.getAcceptArea() == null ? "@n" : dto.getAcceptArea());// ��������(*)
        customer.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//������ʱ���
        customer.setCreateTime(dto.getCreateTime());//����ʱ��
        customer.setCreator(dto.getCreateUser());//������
        customer.setUpdateTimestamp((DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime())));//�޸����ڴ�
        customer.setUpdateTime(dto.getModifyTime());//�޸�ʱ��
        customer.setUpdater(dto.getModifyUser());//�޸���
        customer.setStatus("1");//�ͻ�״̬ Ĭ��1
        customer.setEducation(dto.getEducation());//ѧ��
        customer.setMarriaged(dto.getMarriage());//����״��
        customer.setEmail(dto.getEmail());//����
        customer.setCloseDate(dto.getCloseDateDate());//�ر�����
        customer.setNation(dto.getNation());//����
    }


    /**
     * ��ҵ�ͻ�ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void synchCusToCRM(CompanyInfoDTO dto) throws Exception {
        try {
            UniqueUserIdExample example = new UniqueUserIdExample();
            example.createCriteria().andTypeEqualTo("2").andIdNoEqualTo(dto.getCompanyId())
                    .andIdTypeEqualTo(dto.getCompanyIdType()).andSystemIdEqualTo("1");
            List<UniqueUserId> uniqueUserIdList = uniqueUserIdMapper.selectByExample(example);
            //δ�鵽�ͻ���Ϣ
            UniqueUserId uniqueUserId;
            if(null == uniqueUserIdList || uniqueUserIdList.size()==0 ){
                uniqueUserId = new UniqueUserId();
                uniqueUserId.setCustomerId(dto.getEntityId());//�ͻ���
                uniqueUserId.setIdNo(dto.getCompanyId());//��ҵ֤������
                uniqueUserId.setSystemId("1");//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
                uniqueUserId.setType("2");//'����:1�����˺ţ�2��ҵ�˺�,3��Լ�̻�'
                uniqueUserId.setIdType(dto.getCompanyIdType());//��ҵ֤������
                uniqueUserId.setUniqueId(Long.valueOf(commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID")));//ͳһ�ͻ�ID
                uniqueUserIdMapper.insertSelective(uniqueUserId); 
            }else{
                //��ѯ���ͻ���Ϣ
                uniqueUserId = uniqueUserIdList.get(0);
                uniqueUserId.setCustomerId(dto.getRelationNo());//��ҵ�ͻ����
                uniqueUserIdMapper.updateByPrimaryKeySelective(uniqueUserId); 
            }
            synchCusCustomerToCRM(dto, uniqueUserId);//��ҵ�ͻ�ͬ��        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("��ҵ�ͻ�ͬ��ʧ�ܣ�");
        }
    }


    //��ҵ�ͻ���Ϣͬ��
    private void synchCusCustomerToCRM(CompanyInfoDTO dto, UniqueUserId id) throws BizServiceException {
        try {
            CustomerEnterPriseKey key = new CustomerEnterPriseKey();
            key.setCustomerId(id.getCustomerId());//��ҵ�ͻ����
            key.setSystemId(id.getSystemId());//'������ϵͳ:1����;��ϵͳ��2����;��ϵͳ
            CustomerEnterPrise companyInfo = customerEnterPriseMapper.selectByPrimaryKey(key);
            if(companyInfo == null){
                companyInfo = new CustomerEnterPrise();
                companyInfo.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createCusCustomer(dto,companyInfo);
                customerEnterPriseMapper.insertSelective(companyInfo);            
            } else {
                //update
                companyInfo.setUniqueId(id.getUniqueId());//ͳһ�ͻ�ID
                createCusCustomer(dto, companyInfo);
                customerEnterPriseMapper.updateByPrimaryKeySelective(companyInfo);    
            }           
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BizServiceException("��ҵ�ͻ�ͬ��ʧ��");
        }             
    }

    //������ҵ�ͻ�����
    private void createCusCustomer(CompanyInfoDTO dto, CustomerEnterPrise companyInfo) {
        ReflectionUtil.copyProperties(dto, companyInfo);
        companyInfo.setCustomerId(dto.getEntityId());// �ͻ�id(*)
        companyInfo.setSystemId("1");// ����;(*)
        companyInfo.setEnterpriseCname(dto.getCompanyName());// ��˾����(*)
        companyInfo.setEnterpriseEname(dto.getCompanyEnglishname());//��˾Ӣ����
        companyInfo.setLegalIdType(dto.getCorpCredType());// ����֤������(*)
        companyInfo.setLegalIdNo(dto.getCorpCredId());// ����֤����(*)
        companyInfo.setLegalIdValidity(DateUtil.string2date(dto.getCorpCredValidity()));// ����֤����Ч��(*)
        companyInfo.setLegalName(dto.getCorpName());// ��������(*)
        companyInfo.setLegalAlias(dto.getCorpAliasName());//���˱���
        companyInfo.setLegalMobile(dto.getCorpTelephoneNumber());// ���˵绰(*)
        companyInfo.setLegalGender(dto.getCorpGender());// �����Ա�(*)
        companyInfo.setLegalBirthday(DateUtil.string2date(dto.getCorpBirthday()));// ���˳�������(*)
        companyInfo.setLegalNationality(dto.getCorpCountyr());// ���˹���(*)
        companyInfo.setLegalAddress(dto.getCorpAddress());// ����סַ(*)
        companyInfo.setLegalOccupation(dto.getCorpProfession());// ����ְҵ(*)
        companyInfo.setEnterpriseCountry(dto.getCompanyCountyr());// ��ҵ����(*)
        companyInfo.setEnterpriseRegistry(dto.getCompanyRegisteredAddress());// ��ҵע���(*)
        companyInfo.setEnterpriseIdType(dto.getCompanyIdType());// ��ҵ֤������(*)
        companyInfo.setEnterpriseIdNo(dto.getCompanyId());// ��ҵ֤����(*)
        companyInfo.setEnterpriseAccountant(dto.getCompanyAccountant());// ��˾���
        companyInfo.setEnterpriseDesc(dto.getCompanyDescription());// ��˾��������Ӫ��Χ(*)
        //companyInfo.setAccountNo(dto.getExternalId());// �����˺�
        companyInfo.setAreaCode("@n");// ��������(*)
        companyInfo.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));// ������ʱ���
        companyInfo.setCreateTime(dto.getCreateTime());
        companyInfo.setCreator(dto.getCreateUser());// ������
        companyInfo.setUpdateTimestamp((DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime())));// �޸����ڴ�
        companyInfo.setUpdateTime(dto.getModifyTime());
        companyInfo.setUpdater(dto.getModifyUser());
        companyInfo.setStatus(dto.getCusState());//�ͻ�״̬
        companyInfo.setIndustry(dto.getCompanyFax());// �ͻ���ҵ
        companyInfo.setAgencyName(dto.getOperatorName());// ������
        companyInfo.setAgencyIdtype(dto.getOperatorType());// ������֤������
        companyInfo.setAgencyIdno(dto.getOperatorId());//������֤����
        companyInfo.setAgencyIdvalidity(DateUtil.string2date(dto.getOperatorValidity()));//������֤����Ч��
        companyInfo.setBankAccountNo(dto.getBankAccount());//�����˻�
        companyInfo.setBankName(dto.getBankName());//��������
        companyInfo.setCmail(dto.getEmail());//��˾����
        companyInfo.setLinkphone(dto.getLinkphone());//��ҵ�̻�
        companyInfo.setScal(dto.getCompanySize());// ��ҵ��ģ
        companyInfo.setRegCptl(dto.getRegisteredCapital());//ע���ʱ�
        companyInfo.setCtidEdt(dto.getCtidEdt());// ����֤����Ч�ڣ�
    }       
    
}
