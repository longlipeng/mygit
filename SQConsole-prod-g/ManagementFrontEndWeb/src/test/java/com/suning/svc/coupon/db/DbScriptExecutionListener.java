/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DbScriptExecutionListener.java
 * Author:   秦伟
 * Date:     2013-11-15 上午10:48:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.db;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.util.Assert;

import com.suning.svc.coupon.db.annotation.DbScriptSetup;
import com.suning.svc.coupon.db.annotation.DbScriptTeardown;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DbScriptExecutionListener extends AbstractTestExecutionListener{
    Logger logger = LoggerFactory.getLogger(DbScriptExecutionListener.class);
    
    @SuppressWarnings("serial")
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        final Method testMethod = testContext.getTestMethod();
        Assert.notNull(testMethod, "The test method of the supplied TestContext must not be null");

        DbScriptSetup script = testMethod.getAnnotation(DbScriptSetup.class);
        if(script == null){
            return;
        }
        Object ds = testContext.getApplicationContext().getBean(script.datasource());
        String scriptpath = script.value();
        String encoding = script.encoding();
        boolean continueOnError = script.continueOnError();
        Assert.notNull(ds, "DbScriptSet.datasource must not be null");
        Assert.isInstanceOf(DataSource.class, ds, "need DataSource type , but " + ds.getClass());
        Assert.hasText(scriptpath, "DbScriptSet.value must not be null");
        SimpleJdbcTemplate template = new SimpleJdbcTemplate((DataSource)ds);
        Resource resource = new ClassPathResource(scriptpath);
        SimpleJdbcTestUtils.executeSqlScript(template, new EncodedResource(resource,encoding), continueOnError);
    }
    
    /* (non-Jsdoc)
     * @see org.springframework.test.context.support.AbstractTestExecutionListener#afterTestMethod(org.springframework.test.context.TestContext)
     */
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        final Method testMethod = testContext.getTestMethod();
        Assert.notNull(testMethod, "The test method of the supplied TestContext must not be null");

        DbScriptTeardown script = testMethod.getAnnotation(DbScriptTeardown.class);
        if(script == null){
            return;
        }
        Object ds = testContext.getApplicationContext().getBean(script.datasource());
        String scriptpath = script.value();
        String encoding = script.encoding();
        boolean continueOnError = script.continueOnError();
        Assert.notNull(ds, "DbScriptSet.datasource must not be null");
        Assert.isInstanceOf(DataSource.class, ds, "need DataSource type , but " + ds.getClass());
        Assert.hasText(scriptpath, "DbScriptSet.value must not be null");
        SimpleJdbcTemplate template = new SimpleJdbcTemplate((DataSource)ds);
        Resource resource = new ClassPathResource(scriptpath);
        SimpleJdbcTestUtils.executeSqlScript(template, new EncodedResource(resource,encoding), continueOnError);
    }
}
