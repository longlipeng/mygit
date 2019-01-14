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
 * 同步CRM系统
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
            logger.info("Quartz的任务调度！！！"+(new Date()).toString());           
            String startTime = DateUtil.getCurrentDateToStr();//开始时间
            //String startTime = DateUtil.getCurrentMonthToStr();//开始时间
            String endTime = DateUtil.getCurrentTime();//结束时间    
            
            //个人客户批量同步CRM处理
            CustomerExample example1 = new CustomerExample();
            example1.createCriteria().andModifyTimeBetween(startTime, endTime).andCustomerTypeEqualTo("1").andDataStateEqualTo("1");
            //根据更新时间查询个人客户信息
            List<Customer> customerList = customerMapper.selectByExample(example1);
            //如果不为空
            if(null != customerList && customerList.size() >0){
                for (Customer customer : customerList) {
                    try {
                        AttachInfoKey key = new AttachInfoKey();
                        key.setPeopleNo(customer.getEntityId());//持卡人编号
                        key.setPeopleType("00");//00表示客户，01表示持卡人
                        AttachInfo attachInfo = attachInfoMapper.selectByPeopleAttachKey(key);
                        if(attachInfo != null){
                            CustomerDTO customerDTO = new CustomerDTO();
                            ReflectionUtil.copyProperties(customer, customerDTO);
                            customerDTO.setGender(attachInfo.getRs1());//性别
                            customerDTO.setActivitySector(attachInfo.getIndustry());//行业
                            customerDTO.setAwareness(attachInfo.getProfession());//职业
                            customerDTO.setNation(attachInfo.getNation());//民族
                            customerDTO.setEducation(attachInfo.getEducation());//学历
                            customerDTO.setMarriage(attachInfo.getMarriage());//婚姻状况
                            customerDTO.setNationality(attachInfo.getCountyr());//国家
                            customerDTO.setCity(attachInfo.getCity());//城市
                            customerDTO.setValidity(attachInfo.getValidity());//证件有效期
                            customerDTO.setIsblacklist(attachInfo.getIsblacklist());//黑名单
                            customerDTO.setRiskGrade(attachInfo.getRiskGrade());//风险等级
                            customerDTO.setEmail(attachInfo.getEmail());//邮箱
                            //同步个人客户到CRM
                            synchCRMService.synchPerToCRM(customerDTO);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("个人客户同步CRM失败"+e.getMessage());
                    }                    
                }                                
            }
            
            
            //个人持卡人批量同步CRM处理
            CardHolderExample example = new CardHolderExample();
            example.createCriteria().andModifyTimeBetween(startTime, endTime).andDataStateEqualTo("1");
            //根据更新时间查询个人持卡人信息            
            List<CardHolder> cardholderList =cardholderMapper.selectByExample(example);
            //如果不为空
            if(null!= cardholderList && cardholderList.size()>0){
                for (CardHolder cardHolder : cardholderList) {
                    try {
                        AttachInfoKey key = new AttachInfoKey();
                        key.setPeopleNo(cardHolder.getCardholderId());//持卡人编号
                        key.setPeopleType("01");//00表示客户，01表示持卡人
                        AttachInfo attachInfo = attachInfoMapper.selectByPrimaryKey(key);
                        if(attachInfo != null){
                            CardholderDTO cardholderDTO = new CardholderDTO();
                            ReflectionUtil.copyProperties(cardHolder, cardholderDTO);
                            cardholderDTO.setIndustry(attachInfo.getIndustry());//行业
                            cardholderDTO.setProfession(attachInfo.getProfession());//职业
                            cardholderDTO.setValidity(attachInfo.getValidity());//证件有效期
                            cardholderDTO.setCountry(attachInfo.getCountyr());//国家
                            cardholderDTO.setCity(attachInfo.getCity());//城市
                            cardholderDTO.setCardholderNation(attachInfo.getNation());//民族
                            cardholderDTO.setCardholderEducation(attachInfo.getEducation());//学历
                            cardholderDTO.setCardholderMarriage(attachInfo.getMarriage());//婚姻状况
                            cardholderDTO.setCardholderEmail(attachInfo.getEmail());//邮箱
                            cardholderDTO.setIsblacklist(attachInfo.getIsblacklist());//黑名单
                            cardholderDTO.setRiskGrade(attachInfo.getRiskGrade());//风险等级
                            //同步个人持卡人到CRM
                            synchCRMService.syncToPerCRM(cardholderDTO);                                                            
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("个人持卡人同步CRM失败"+e.getMessage());
                    }                    
                } 
            } 
                       
            
            //企业信息批量同步CRM处理
            CompanyInfoExample example2 = new CompanyInfoExample();
            example2.createCriteria().andModifyTimeBetween(startTime, endTime).andDataStateEqualTo("1");
            //根据更新时间查询企业客户信息
            List<CompanyInfo> companyInfoList = companyInfoMapper.selectByExample(example2);
            //如果不为空
            if(null != companyInfoList && companyInfoList.size() >0){
                for (CompanyInfo companyInfo : companyInfoList) {                    
                    try {
                        CompanyInfoDTO companyInfoDTO = new CompanyInfoDTO();
                        ReflectionUtil.copyProperties(companyInfo, companyInfoDTO);
                        //如果为企业客户
                        if("00".equals(companyInfo.getRelationType())){                        
                            //企业客户批量同步CRM 
                            synchCRMService.synchCusToCRM(companyInfoDTO);                        
                        }
                        //如果为企业持卡人
                        else if("01".equals(companyInfo.getRelationType())){
                            //企业持卡人批量同步CRM
                            synchCRMService.syncToCusCRM(companyInfoDTO);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("企业信息同步CRM失败"+e.getMessage());
                    }                    
                }
            }                        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("同步CRM失败"+e.getMessage());
        }            
    }   
        
    
    
    @SuppressWarnings({ "unused", "resource" })
    public static void main(String[] args) {

        System.out.println("Test start.");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
        // 如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
        // synchCRM qj=(synchCRM)ctx.getBean("synchCRM");
        // qj.work();
        System.out.println("请输入信息：");
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        System.out.println(x);
        System.out.print("Test end..");
    }

}
