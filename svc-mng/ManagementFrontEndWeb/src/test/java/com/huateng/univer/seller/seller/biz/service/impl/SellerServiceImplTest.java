/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SellerServiceImplTest.java
 * Author:   13010154
 * Date:     2013-11-22 上午10:24:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.seller.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.allinfinance.univer.seller.seller.dto.TreeNodeDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.seller.biz.service.SellerService;
import com.suning.svc.coupon.db.DbScriptExecutionListener;


/**
 * 营销机构方法测试
 * 〈功能详细描述〉
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DbScriptExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSourceConnection", dataSetLoader = FlatXmlDataSetLoader.class)
public class SellerServiceImplTest {
    
    Logger logger = Logger.getLogger(SellerServiceImplTest.class);
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private CommonsDAO commonsDAO;
    
    @Test
    public void testBuildTree() {
        try {
            List<TreeNodeDTO> treeList = sellerService.buildTree("5101");
            if(null == treeList || treeList.size() <= 0) {
                Assert.fail("没有下级营销机构");  
            }
            else {
                for(TreeNodeDTO dto : treeList) {
                    logger.info("本机构号：" + dto.getId()  + " 机构名称：" + dto.getName() + " 上级营销机构号：" + dto.getpId());
                }
            }
        } catch (BizServiceException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testIsAuthorityResourceId() {
        try {
            String resourceId = DataBaseConstant.PURCHASE_RESOURCE_ID;
            List<ResourceDTO> pResourceList = (List<ResourceDTO>)commonsDAO.queryForList(
                    "SELLER.nextResourceIds", DataBaseConstant.PURCHASE_RESOURCE_ID);
            for(ResourceDTO dto : pResourceList) {
                if(resourceId.equals(dto.getResourceId())) {
                    logger.info("没有采购权限");
                   break;
                }
            }
       
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }

    }
}
