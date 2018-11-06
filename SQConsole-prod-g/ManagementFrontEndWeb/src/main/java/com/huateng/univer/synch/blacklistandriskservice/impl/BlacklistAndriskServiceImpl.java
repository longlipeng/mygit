package com.huateng.univer.synch.blacklistandriskservice.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.allinfinance.framework.dto.JudgeInforDTO;
import com.allinfinance.ibatis.attach.dao.AttachDAO;
import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderVerifyDAO;
import com.huateng.framework.ibatis.dao.CompanyInfoDAO;
import com.huateng.framework.ibatis.dao.UniqueUserIdDAO;
import com.huateng.framework.ibatis.model.CardholderVerify;
import com.huateng.framework.ibatis.model.CardholderVerifyExample;
import com.huateng.framework.ibatis.model.CompanyInfo;
import com.huateng.framework.ibatis.model.CompanyInfoExample;
import com.huateng.framework.ibatis.model.CompanyInfoKey;
import com.huateng.framework.ibatis.model.UniqueUserId;
import com.huateng.framework.util.BlackListAndRiskGradeConfig;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.synch.blacklistandriskservice.BlacklistAndRiskService;

/**
 * 客户和持卡人黑名单校验和风险等级
 * 
 * @author jason
 *
 */
public class BlacklistAndriskServiceImpl implements BlacklistAndRiskService {
	private static Logger logger = Logger.getLogger(BlacklistAndriskServiceImpl.class);
	private CustomerService customerService;
	private CardholderService cardholderService;
	private String blockListURL;
	private String riskGradeURL;
	private String blackResult;
	private String riskResult;
	//private String blackResult="{\"result\":2,\"count\":2,\"Content\":[{\"CName\":\"公安部第三批涉恐名单\",\"EName\":\"\",\"CertID\":-1,\"Country\":-1,\"Birthday\":-1,\"Gender\":-1},{\"CName\":\"公安部第三批涉恐名单\",\"EName\":\"\",\"CertID\":-1,\"Country\":-1,\"Birthday\":-1,\"Gender\":-1}]}";
	//private String riskResult="{\"result\":2}";
	private UniqueUserIdDAO uniqueUserIdDAO;	
	private AttachDAO attachDAO;
	private CompanyInfoDAO companyInfoDAO;
	private CardholderVerifyDAO cardholderVerifyDAO;
	
        
                        
    public CardholderVerifyDAO getCardholderVerifyDAO() {
        return cardholderVerifyDAO;
    }
    public void setCardholderVerifyDAO(CardholderVerifyDAO cardholderVerifyDAO) {
        this.cardholderVerifyDAO = cardholderVerifyDAO;
    }
    public CompanyInfoDAO getCompanyInfoDAO() {
        return companyInfoDAO;
    }
    public void setCompanyInfoDAO(CompanyInfoDAO companyInfoDAO) {
        this.companyInfoDAO = companyInfoDAO;
    }
    public AttachDAO getAttachDAO() {
        return attachDAO;
    }
    public void setAttachDAO(AttachDAO attachDAO) {
        this.attachDAO = attachDAO;
    }

        public BlacklistAndriskServiceImpl() {
                blockListURL=BlackListAndRiskGradeConfig.getBlackListRUL();
		riskGradeURL = BlackListAndRiskGradeConfig.getRiskGradeURL();
        }
        //黑名单和风险控制的入口
        @Override
        public void judge(JudgeInforDTO dto) throws Exception {
            try {
                if (dto.getJudgeType().equals("5")) {
                    // 黑名单校验
                    Map<String, String> parameters = createBlackListMap(dto);
                    //购卡人黑名单校验
                    if(dto.getUserType().equals("1")) {
                            CustomerDTO customer = new CustomerDTO();
                            customer.setEntityId(dto.getEntityID());
                            customer= customerService.viewCustomer(customer);
                            parameters.put("CName",customer.getCustomerName());
                            if(customer.getCustomerType().trim().equals("0")) {//类型为企业的
                                    parameters.put("CertID", customer.getLicenseId());                                      
                            }else {
                                    //类型为个人的
                                    parameters.put("CertID", customer.getCorpCredId());             
                                    parameters.put("Country", customer.getCorpCountyr());
                                    parameters.put("Birthday",customer.getCorpBirthday());
                            }
                    }else {
                            //持卡人封装
                            parameters.put("CName",dto.getCName());
                            // 类型为个人的
                            
                            parameters.put("CertID", dto.getCertID());
                            parameters.put("Country", dto.getCountry());
                            parameters.put("Birthday", dto.getBirthday());  
                    }
                    blackResult =sendGet(blockListURL,parameters);
                    analysisBlackListResult(blackResult,dto);
                } else if (dto.getJudgeType().equals("4")) {
                    // 风险等级
                    Map<String, String> parameters = createRiskGradeMap(dto);
                    riskResult = sendGet(riskGradeURL, parameters);
	                analysisRiskGradeResult(riskResult,dto);
	            }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new BizServiceException("更新黑名单状态失败！");
            }
                
        }
        //分析风险等级返回结果返回结果
        @SuppressWarnings({ "unused", "unchecked" })
        private void analysisRiskGradeResult(String riskResult, JudgeInforDTO dto) throws Exception {
            if(riskResult==null||riskResult.trim().equals("")) {
                throw new BizServiceException("返回为空!");
            }
            try {
                JSONObject object=JSONObject.parseObject(riskResult);                
                UniqueUserId uniqueUserId;
                uniqueUserId =  uniqueUserIdDAO.selectByPrimaryKey(Long.valueOf(dto.getCNum()));
                //判断是企业还是个人，1为个人，2为企业
                if(uniqueUserId.getType().trim().equals("1")){
                    //判断持卡人编号是否为空，更改个人持卡人
                    if(StringUtils.isNotEmpty(uniqueUserId.getCardHolderId())){
                        AttachInfoDTO attachInfo = new AttachInfoDTO();
                        attachInfo.setPeopleNo(uniqueUserId.getCardHolderId());//个人持卡人编号
                        attachInfo.setPeopleType("01");//个人持卡人
                        attachInfo.setEntityId(dto.getDefaultEntityId());
                        attachInfo.setDataStat("1");
                        String result = object.getString("result");
                        if("-1".equals(result)) {
                                    result="O";
                         }                        
                        attachInfo.setRiskGrade(result);                       
                        List<AttachInfoDTO> attachInfos = attachDAO.getAttachInfos(attachInfo);
                        if (null!=attachInfos&&attachInfos.size()>0){
                                attachDAO.updateAttachInfo(attachInfo);
                        }else{
                                attachDAO.insertAttachInfo(attachInfo);
                        }                                       
                    }
                    //判断客户号是否为空,更改个人客户
                    if(StringUtils.isNotEmpty(uniqueUserId.getCustomerId())){
                        CustomerDTO customerDTO=new CustomerDTO();
                        customerDTO.setCorpCredId(uniqueUserId.getIdNo());
                        customerDTO.setCorpCredType(uniqueUserId.getIdType());
                        List<CustomerDTO> list = customerService.getCustomerByIdNo(customerDTO);
                        if(null != list && list.size()>0)
                        {
                            AttachInfoDTO attachInfo = new AttachInfoDTO();
                            attachInfo.setPeopleNo(uniqueUserId.getCustomerId());//个人客户编号
                            attachInfo.setPeopleType("00");//个人客户
                            attachInfo.setEntityId(dto.getDefaultEntityId());
                            attachInfo.setDataStat("1");
                            String result = object.getString("result");
                            if("-1".equals(result)) {
                                        result="O";
                             }                        
                            attachInfo.setRiskGrade(result);                       
                            List<AttachInfoDTO> attachInfos = attachDAO.getAttachInfos(attachInfo);
                            if (null!=attachInfos&&attachInfos.size()>0){
                                    attachDAO.updateAttachInfo(attachInfo);
                            }else{
                                    attachDAO.insertAttachInfo(attachInfo);
                            }
                        }                                                 
                    }
                }else{
                    //判断持卡人编号是否为空，更改企业持卡人
                    if(StringUtils.isNotEmpty(uniqueUserId.getCardHolderId())){                                                                        
                        CompanyInfoKey key=new CompanyInfoKey();
                        key.setRelationNo(uniqueUserId.getCardHolderId());//企业持卡人编号
                        key.setRelationType("01");//类型 00:客户     01:持卡人
                        CompanyInfo companyInfo = companyInfoDAO.selectByPrimaryKey(key);
                        String result = object.getString("result");                       
                        if("-1".equals(result)) {
                                result="O";
                        }                        
                        companyInfo.setRiskGrade(result);
                        if (null != companyInfo){
                            companyInfoDAO.updateByPrimaryKeySelective(companyInfo); 
                        }else{
                            companyInfoDAO.insertSelective(companyInfo);
                        }                       
                    }
                    //判断客户号是否为空，更改企业客户
                    if(StringUtils.isNotEmpty(uniqueUserId.getCustomerId())){  
                        CompanyInfoExample example = new CompanyInfoExample();
                        example.createCriteria().andCompanyIdEqualTo(uniqueUserId.getIdNo()).andCompanyIdTypeEqualTo(uniqueUserId.getIdType()).andRelationTypeEqualTo("00");
                        List<CompanyInfo> list =  companyInfoDAO.selectByExample(example);
                        if(null != list && list.size() > 0){
                            CompanyInfoKey key=new CompanyInfoKey();
                            key.setRelationNo(uniqueUserId.getCustomerId());//企业客户编号
                            key.setRelationType("00");//类型 00:客户     01:持卡人
                            CompanyInfo companyInfo = companyInfoDAO.selectByPrimaryKey(key);
                            String result = object.getString("result");                       
                            if("-1".equals(result)) {
                                    result="O";
                            }                        
                            companyInfo.setRiskGrade(result);
                            if (null != companyInfo){
                                companyInfoDAO.updateByPrimaryKeySelective(companyInfo); 
                            }else{
                                companyInfoDAO.insertSelective(companyInfo);
                            }    
                        }                        
                    }
                }               
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new BizServiceException("风险等级更新失败！");
            }
        	
                
        }
        
        
        //分析黑名单返回结果
        private void analysisBlackListResult(String blackResult, JudgeInforDTO dto) throws Exception {
        	if(blackResult==null||blackResult.trim().equals("")) {
    			throw new Exception("返回为空!");
    		}
        	try {
        	    JSONObject object=JSONObject.parseObject(blackResult);
                    //持卡人处理
                    if(dto.getUserType().trim().equals("2")) {
                        //持卡人更新
                        if(dto.getCustomerType().trim().equals("1")) {
                            //根据个人持卡人编号查询持卡人信息
                            CardholderVerify cardholderVerify = cardholderVerifyDAO.selectByPrimaryKey(dto.getEntityID());
                            if(null != cardholderVerify){
                                String result =object.getString("result");
                                if(!"1".equals(result)) {
                                    result="0";                    
                                }
                                cardholderVerify.setIsblacklist(result);//返回的黑名单标识
                                CardholderVerifyExample example = new CardholderVerifyExample();
                                example.createCriteria().andCardholderIdEqualTo(dto.getEntityID());
                                //进行持卡人编号进行更新
                                cardholderVerifyDAO.updateByExampleSelective(cardholderVerify, example);
                            }else{
                                cardholderVerifyDAO.insertSelective(cardholderVerify);
                            }                                                                                                 
                        }
                        else {
                          //企业持卡人更新
                          CompanyInfo recode = new CompanyInfo();
                          recode.setRelationNo(dto.getEntityID());
                          recode.setRelationType("01");
                          String result =object.getString("result");
                          if(!"1".equals(result)) {
                              result="0";                 
                          }
                            recode.setIsblacklist(result);    
                            companyInfoDAO.updateByPrimaryKeySelective(recode);                  
                        }                   
                    }else {
                        //购卡人处理  
                        CustomerDTO customerDTO = new CustomerDTO();
                        customerDTO.setEntityId(dto.getEntityID());
                        customerDTO.setDefaultEntityId(dto.getDefaultEntityId());
                        customerDTO=customerService.viewCustomer(customerDTO);
                        String result =object.getString("result");
                        if(!"1".equals(result)) {
                            result="0";                    
                        }
                        customerDTO.setIsblacklist(result);    
                        customerService.updateCustomer(customerDTO);
                        }          
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new BizServiceException("黑名单标识更新失败！");
                }
                              
        }
        /**
         * 封装黑名单访问对象
         * @return
         */
        public Map<String, String> createBlackListMap(JudgeInforDTO dto){
                Map<String, String> parameters = new HashMap<String,String>();
                parameters.put("CName",dto.getCName());
		// parameters.put("CName", dto.getEName());
                parameters.put("CertID", dto.getCertID());
                parameters.put("Country", dto.getCountry());
                parameters.put("Birthday", dto.getBirthday());
                parameters.put("Gender", dto.getGender());
                return parameters;
        }
        
        /**
         * 封装风险等级访问对象
         * @return
         */
        public Map<String, String> createRiskGradeMap(JudgeInforDTO dto){
                Map<String, String> parameters = new HashMap<String,String>();
                parameters.put("CNum", dto.getCNum());
                parameters.put("CName", dto.getCName());
                return parameters;
        }
        
        /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGet(String url, Map<String, String> parameters) {
        
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "utf-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) { 
                        if(parameters.get(name)!=null) {
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "utf-8")).append("&");  
                        }
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            String full_url = url + "?" + params;  
            logger.info("请求地址&参数："+full_url);  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error(e.getMessage());
        } finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
                logger.error(ex.getMessage());
            }  
        }
        logger.info("返回&参数："+result);
        return result; 
        
    }  
        

        @Override
        public void judgeBlackList(JudgeInforDTO dto) {

        }

        @Override
        public void judgeRiskGrade(JudgeInforDTO dto) {

        }

        public CustomerService getCustomerService() {
                return customerService;
        }

        public void setCustomerService(CustomerService customerService) {
                this.customerService = customerService;
        }

        public CardholderService getCardholderService() {
                return cardholderService;
        }

        public void setCardholderService(CardholderService cardholderService) {
                this.cardholderService = cardholderService;
        }
        public UniqueUserIdDAO getUniqueUserIdDAO() {
                return uniqueUserIdDAO;
        }
        public void setUniqueUserIdDAO(UniqueUserIdDAO uniqueUserIdDAO) {
                this.uniqueUserIdDAO = uniqueUserIdDAO;
        }
}
