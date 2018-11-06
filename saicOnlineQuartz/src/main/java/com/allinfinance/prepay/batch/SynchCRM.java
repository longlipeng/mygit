package com.allinfinance.prepay.batch;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.mapper.svc_mng.AttachInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardHolderMapper;
import com.allinfinance.prepay.mapper.svc_mng.CompanyInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerMapper;
import com.allinfinance.prepay.model.AttachInfo;
import com.allinfinance.prepay.model.AttachInfoKey;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.CompanyInfo;
import com.allinfinance.prepay.model.CompanyInfoExample;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.service.SynchCRMService;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;

/**
 * ͬ��CRMϵͳ
 * @author yly
 *
 */
public class SynchCRM {
    private static Logger logger = Logger.getLogger(SynchCRM.class);
    @Autowired
    private CardHolderMapper cardholderMapper;
    @Autowired
    private AttachInfoMapper attachInfoMapper;
    @Autowired
    private SynchCRMService synchCRMService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CompanyInfoMapper companyInfoMapper;
    
    public void SynchCRMQuartz(){
        try {
            logger.info("Quartz��������ȣ�����"+(new Date()).toString());           
            String startTime = DateUtil.getCurrentDateToStr();//��ʼʱ��
            //String startTime = DateUtil.getCurrentMonthToStr();//��ʼʱ��
            String endTime = DateUtil.getCurrentTime();//����ʱ��    
            
            //���˿ͻ�����ͬ��CRM����
            CustomerExample example1 = new CustomerExample();
            example1.createCriteria().andModifyTimeBetween(startTime, endTime).andCustomerTypeEqualTo("1").andDataStateEqualTo("1");
            //���ݸ���ʱ���ѯ���˿ͻ���Ϣ
            List<Customer> customerList = customerMapper.selectByExample(example1);
            //�����Ϊ��
            if(null != customerList && customerList.size() >0){
                for (Customer customer : customerList) {
                    try {
                        AttachInfoKey key = new AttachInfoKey();
                        key.setPeopleNo(customer.getEntityId());//�ֿ��˱��
                        key.setPeopleType("00");//00��ʾ�ͻ���01��ʾ�ֿ���
                        AttachInfo attachInfo = attachInfoMapper.selectByPeopleAttachKey(key);
                        if(attachInfo != null){
                            CustomerDTO customerDTO = new CustomerDTO();
                            ReflectionUtil.copyProperties(customer, customerDTO);
                            customerDTO.setGender(attachInfo.getRs1());//�Ա�
                            customerDTO.setActivitySector(attachInfo.getIndustry());//��ҵ
                            customerDTO.setAwareness(attachInfo.getProfession());//ְҵ
                            customerDTO.setNation(attachInfo.getNation());//����
                            customerDTO.setEducation(attachInfo.getEducation());//ѧ��
                            customerDTO.setMarriage(attachInfo.getMarriage());//����״��
                            customerDTO.setNationality(attachInfo.getCountyr());//����
                            customerDTO.setCity(attachInfo.getCity());//����
                            customerDTO.setValidity(attachInfo.getValidity());//֤����Ч��
                            customerDTO.setIsblacklist(attachInfo.getIsblacklist());//������
                            customerDTO.setRiskGrade(attachInfo.getRiskGrade());//���յȼ�
                            customerDTO.setEmail(attachInfo.getEmail());//����
                            //ͬ�����˿ͻ���CRM
                            synchCRMService.synchPerToCRM(customerDTO);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("���˿ͻ�ͬ��CRMʧ��"+e.getMessage());
                    }                    
                }                                
            }
            
            
            //���˳ֿ�������ͬ��CRM����
            CardHolderExample example = new CardHolderExample();
            example.createCriteria().andModifyTimeBetween(startTime, endTime).andDataStateEqualTo("1");
            //���ݸ���ʱ���ѯ���˳ֿ�����Ϣ            
            List<CardHolder> cardholderList =cardholderMapper.selectByExample(example);
            //�����Ϊ��
            if(null!= cardholderList && cardholderList.size()>0){
                for (CardHolder cardHolder : cardholderList) {
                    try {
                        AttachInfoKey key = new AttachInfoKey();
                        key.setPeopleNo(cardHolder.getCardholderId());//�ֿ��˱��
                        key.setPeopleType("01");//00��ʾ�ͻ���01��ʾ�ֿ���
                        AttachInfo attachInfo = attachInfoMapper.selectByPrimaryKey(key);
                        if(attachInfo != null){
                            CardholderDTO cardholderDTO = new CardholderDTO();
                            ReflectionUtil.copyProperties(cardHolder, cardholderDTO);
                            cardholderDTO.setIndustry(attachInfo.getIndustry());//��ҵ
                            cardholderDTO.setProfession(attachInfo.getProfession());//ְҵ
                            cardholderDTO.setValidity(attachInfo.getValidity());//֤����Ч��
                            cardholderDTO.setCountry(attachInfo.getCountyr());//����
                            cardholderDTO.setCity(attachInfo.getCity());//����
                            cardholderDTO.setCardholderNation(attachInfo.getNation());//����
                            cardholderDTO.setCardholderEducation(attachInfo.getEducation());//ѧ��
                            cardholderDTO.setCardholderMarriage(attachInfo.getMarriage());//����״��
                            cardholderDTO.setCardholderEmail(attachInfo.getEmail());//����
                            cardholderDTO.setIsblacklist(attachInfo.getIsblacklist());//������
                            cardholderDTO.setRiskGrade(attachInfo.getRiskGrade());//���յȼ�
                            //ͬ�����˳ֿ��˵�CRM
                            synchCRMService.syncToPerCRM(cardholderDTO);                                                            
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("���˳ֿ���ͬ��CRMʧ��"+e.getMessage());
                    }                    
                } 
            } 
                       
            
            //��ҵ��Ϣ����ͬ��CRM����
            CompanyInfoExample example2 = new CompanyInfoExample();
            example2.createCriteria().andModifyTimeBetween(startTime, endTime).andDataStateEqualTo("1");
            //���ݸ���ʱ���ѯ��ҵ�ͻ���Ϣ
            List<CompanyInfo> companyInfoList = companyInfoMapper.selectByExample(example2);
            //�����Ϊ��
            if(null != companyInfoList && companyInfoList.size() >0){
                for (CompanyInfo companyInfo : companyInfoList) {                    
                    try {
                        CompanyInfoDTO companyInfoDTO = new CompanyInfoDTO();
                        ReflectionUtil.copyProperties(companyInfo, companyInfoDTO);
                        //���Ϊ��ҵ�ͻ�
                        if("00".equals(companyInfo.getRelationType())){                        
                            //��ҵ�ͻ�����ͬ��CRM 
                            synchCRMService.synchCusToCRM(companyInfoDTO);                        
                        }
                        //���Ϊ��ҵ�ֿ���
                        else if("01".equals(companyInfo.getRelationType())){
                            //��ҵ�ֿ�������ͬ��CRM
                            synchCRMService.syncToCusCRM(companyInfoDTO);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("��ҵ��Ϣͬ��CRMʧ��"+e.getMessage());
                    }                    
                }
            }                        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ͬ��CRMʧ��"+e.getMessage());
        }            
    }   
        
    
    
    @SuppressWarnings({ "unused", "resource" })
    public static void main(String[] args) {

        System.out.println("Test start.");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
        // ��������ļ��н�startQuertz bean��lazy-init����Ϊfalse ����ʵ����
        // synchCRM qj=(synchCRM)ctx.getBean("synchCRM");
        // qj.work();
        System.out.println("��������Ϣ��");
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        System.out.println(x);
        System.out.print("Test end..");
    }

}
