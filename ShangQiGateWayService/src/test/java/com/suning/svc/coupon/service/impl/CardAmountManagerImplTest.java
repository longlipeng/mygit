/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CardAmountManagerImplTest.java
 * Author:   秦伟
 * Date:     2013-11-5 下午4:31:56
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DBTestCase;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huateng.framework.util.ApplicationContextUtil;
import com.suning.svc.coupon.service.CardAmountManager;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-test.xml"})
public class CardAmountManagerImplTest extends DBTestCase{

    @Autowired
    CardAmountManager cardManager;
    
    /* (non-Jsdoc)
     * @see org.dbunit.DatabaseTestCase#setUp()
     */
    @Override
    @Before
    public void setUp() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("xxxx");
        super.setUp();
    }
    
    /**
     * Test method for {@link com.suning.svc.coupon.service.impl.CardAmountManagerImpl#addAmount(java.lang.String, long)}.
     */
    @Test
    public void testAddAmount() {
        long retAmount = cardManager.addAmount("6699661110001880616", 100);
        assertEquals(400, retAmount);
    }

    /**
     * Test method for {@link com.suning.svc.coupon.service.impl.CardAmountManagerImpl#minusAmount(java.lang.String, long)}.
     */
    @Test
    public void testMinusAmount() {
        long retAmount = cardManager.minusAmount("6699661110001880616", 100);
        assertEquals(200, retAmount);
    }

    /* (non-Jsdoc)
     * @see org.dbunit.DatabaseTestCase#getDataSet()
     */ 
    @Override
    protected IDataSet getDataSet() throws Exception {
        DataFileLoader dataFileLoader = new FlatXmlDataFileLoader();
        String[] dataFiles = new String[]{"/dbunit/cp_virtual_card.xml"};

        int count = dataFiles.length;
        /*LOG.debug("makeCompositeDataSet: dataFiles count=" + count);
        if (count == 0) {
            LOG.info("makeCompositeDataSet: Specified zero data files");
        }*/

        List list = new ArrayList();
        for (int i = 0; i < count; i++) {
            IDataSet ds = dataFileLoader.load(dataFiles[i]);
            list.add(ds);
        }

        IDataSet[] dataSet = (IDataSet[]) list.toArray(new IDataSet[] {});
        IDataSet compositeDS = new CompositeDataSet(dataSet);
        return compositeDS;
    }
/* (non-Jsdoc)
 * @see org.dbunit.DatabaseTestCase#getSetUpOperation()
 */
    @Override
    
    protected DatabaseOperation getSetUpOperation() throws Exception {
        // TODO Auto-generated method stub
        //return super.getSetUpOperation();
        return new CompositeOperation(DatabaseOperation.DELETE, DatabaseOperation.INSERT);  
    }
    
    protected IDatabaseTester newDatabaseTester() throws Exception {
        return new DataSourceDatabaseTester((DataSource)ApplicationContextUtil.getBean("dataSource"), "svc_mng");
      }
}
