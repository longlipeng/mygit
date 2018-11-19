/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchCardServiceImplTest.java
 * Author:   Administrator
 * Date:     2013-11-8 上午11:34:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.cardmanage.biz.service.impl;

import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardInfoDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AccCardInfoDao;
import com.huateng.framework.ibatis.dao.AccCardInfoExample;
import com.huateng.test.BaseTest;
import com.huateng.univer.cardmanage.biz.service.BatchCardService;
import com.huateng.univer.cardmanage.biz.service.CardManageService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-test.xml")
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
//@DatabaseSetup(type = DatabaseOperation.DELETE, value = { "/dbunit/cp_virtual_card_makecard1.xml" })
@Transactional
public class BatchCardServiceImplTest  {
    private Logger logger = Logger.getLogger(BatchCardServiceImplTest.class);
    @Autowired
    private BatchCardService batchCardService;
    @Autowired
    private AccCardInfoDao dao;
    @Autowired
    private CardManageService cardManageService;
//    @Test
//    public void testCheckStatus() throws BizServiceException{
//        BatchCardActionDTO DTO =new BatchCardActionDTO();
//        String[] cardNoArray={"6699669901000000020","6699669901000000046","6699669901000000012","6699669901000000038","6699669901000000053"};
//        DTO.setCardNoArray(cardNoArray);
//        DTO.setIssuerId("5101");
//        long i=batchCardService.checkStatus(DTO);
//        logger.info(i);
//    }
//    @Test
//    public void testInvalid() throws BizServiceException{
//        BatchCardActionDTO DTO =new BatchCardActionDTO();
//        String[] cardNoArray={"6699669901000000020","6699669901000000020","6699669901000000012","6699669901000000038","6699669901000000053"};
//        DTO.setUser("test");
//        DTO.setIssuerId("5101");
//        DTO.setCardNoArray(cardNoArray);
//        DTO.setMemo("作废原因不详！");
//        batchCardService.submitInvalid(DTO);
//    }
//    @Test
//    public void testCheckProductId() throws BizServiceException{
//        BatchCardActionDTO DTO =new BatchCardActionDTO();
//      String[] cardNoArray={"6699669901000000046"};
//      DTO.setCardNoArray(cardNoArray);
//      DTO.setIssuerId("5101");
//      int i= batchCardService.checkProductId(DTO);
//      logger.info(i);
//    }
//    @Test
//    public void testDao(){
//        AccCardInfoExample example =new AccCardInfoExample();
//        example.createCriteria().andCardNoGreaterThanOrEqualTo("6699669901000000046");
//        example.createCriteria().andCardNoLessThanOrEqualTo("6699669901000000046");
//        List<String> list=new ArrayList();
//        list.add("0");
//        list.add("1");
//        example.createCriteria().andCardStatIn(list);
//        example.createCriteria().andIssuerIdEqualTo("5101");
//        dao.getAccCardInfo2(example).size();
//        logger.info(dao.getAccCardInfo2(example).size());
//    }
//    @Test
//    public void testDAO(){
//        BatchCardActionDTO DTO =new BatchCardActionDTO();
//      String[] cardNoArray={"6699669901000000046"};
//      DTO.setCardNoArray(cardNoArray);
//      DTO.setIssuerId("5101");
//      int i=dao.getAccCardInfo(DTO).size();
//      BatchCardInfoDTO bean=new BatchCardInfoDTO();
//      if(dao.getAccCardInfo(DTO).size()>0){
//          bean=dao.getAccCardInfo(DTO).get(0);
//      }
//      logger.info("卡号："+bean.getCardNo());
//      logger.info("卡号："+bean.getIssuerId());
//      logger.info("卡号："+bean.getProductId());
//      logger.info(+i);
//    }
//    @Test
//    public void testSingleCardValid() throws BizServiceException{
//        BatchCardActionDTO dto =new BatchCardActionDTO();
//        String[] cardNoArray={"6699669901000000046"};
//        dto.setCardNoArray(cardNoArray);
//        dto.setIssuerId("5101");
//        System.out.println(cardManageService.checkCardIsStock(dto)); 
//    }
    @Test
    public void testValid() throws BizServiceException{
        BatchCardActionDTO dto =new BatchCardActionDTO();
        String[] cardNoArray={"6699669999000000016"};
        dto.setCardNoArray(cardNoArray);
        dto.setIssuerId("5101");
        dto.setUser("test");
        dto.setMemo("单卡作废");
        cardManageService.submitInvalid(dto);
    }
    
}
