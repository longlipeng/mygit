/**
 * SUNING APPLIANCE CHAINS.
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package com.suning.svc.core.template;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.suning.svc.core.common.BaseException;
import com.suning.svc.core.common.ErrorCode;

/**
 * 一个接一个业务处理模版默认实现<br>
 * 
 * <p>
 * 用一个业务处理表记录，在处理前对锁状态进行判断 ，判断逻辑参见{@link #beforeInvoke}方法<br>
 * 
 * 业务处理表： 业务类型 PK|业务ID PK|方法|创建时间<br>
 * 
 * @author 陈书元 2011-10-27
 */
public class OneByOneTemplateImpl implements OneByOneTemplate, InitializingBean {

    /** logger */
    private static final Logger logger = Logger.getLogger(OneByOneTemplateImpl.class);

    /** 插入处理记录 */
    protected String insert;

    /** 删除处理记录 */
    protected String delete;

    /** 表名 */
    protected String table = "T_ONE_BY_ONE";

    /** 数据源 */
    protected DataSource dataSource;

    /** 事务模版 */
    protected TransactionTemplate transactionTemplate;

    /** Jdbc模版 */
    protected JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    public <T> T execute(OneByOne oneByOne, CallBack<T> callBack) {
        oneByOne.setDescription(oneByOne.getBizType() + "-" + oneByOne.getBizId() + "-" + oneByOne.getMethod());

        try {
            this.beforeInvoke(oneByOne);

            return callBack.invoke();
        } finally {
            this.afterInvoke(oneByOne);
        }
    }

    /**
     * 设置数据源
     * 
     * @param dataSource 数据源
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 设置表名
     * 
     * @param table 表名
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        if (this.dataSource == null) {
            throw new BaseException(ErrorCode.ERROR_PARAM_NULL, "数据源为空");
        }

        // 初始化Jdbc模版和事务模版
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(this.dataSource));

        // 初始化SQL
        this.insert = "INSERT INTO " + this.table + " (BIZ_TYPE, BIZ_ID, METHOD, CREATED_TIME) VALUES (?, ?, ?, ?)";

        this.delete = "DELETE FROM " + this.table + " WHERE BIZ_TYPE = ? AND BIZ_ID = ?";
    }

    /**
     * 回调前置
     * 
     * @param oneByOne 一个接一个处理记录
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void beforeInvoke(final OneByOne oneByOne) {
        try {
            oneByOne.setInsertSuccess(true);

            // 插入处理记录
            this.transactionTemplate.execute(new TransactionCallback() {
                public Object doInTransaction(TransactionStatus status) {
                    jdbcTemplate.update(
                            insert,
                            new Object[] { oneByOne.getBizType(), oneByOne.getBizId(), oneByOne.getMethod(), new Date() });

                    return null;
                }
            });
        } catch (Throwable t) {
            oneByOne.setInsertSuccess(false);
            logger.warn(oneByOne.getDescription() + "插入处理记录失败！", t);       

            // 如果插入失败，抛出AppException
            throw new BaseException(ErrorCode.ERROR_BIZ_PROCESSING, oneByOne.getDescription() + "业务正在处理中");
        }
    }

    /**
     * 回调后置
     * 
     * @param oneByOne 一个接一个处理记录
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void afterInvoke(final OneByOne oneByOne) {
        try {
            if (!oneByOne.isInsertSuccess()) {
                return;
            }

            // 删除处理记录
            this.transactionTemplate.execute(new TransactionCallback() {
                public Object doInTransaction(TransactionStatus status) {
                    jdbcTemplate.update(delete, new Object[] { oneByOne.getBizType(), oneByOne.getBizId() });

                    return null;
                }
            });
        } catch (Throwable t) {
            logger.error(oneByOne.getDescription() + "删除处理记录失败！", t);
        }
    }
}
