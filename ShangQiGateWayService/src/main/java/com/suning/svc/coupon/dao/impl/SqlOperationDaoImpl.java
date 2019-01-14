/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SqlOperationDaoImpl.java
 * Author:   秦伟
 * Date:     2013-12-16 下午5:08:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao.impl;

import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import com.suning.svc.coupon.dao.SqlOperationDao;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SqlOperationDaoImpl implements SqlOperationDao{

    Logger logger = org.slf4j.LoggerFactory.getLogger(SqlOperationDaoImpl.class);
    JdbcTemplate template;
    
    /**
     * @return the template
     */
    public JdbcTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    /* (non-Jsdoc)
     * @see com.suning.svc.coupon.dao.SqlOperationDao#execute(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean execute(final String sql) {

        template.execute(new StatementCallback<Object>() {

            @Override
            public Object doInStatement(Statement stmt) throws SQLException, DataAccessException {
                stmt.execute(sql);
                int count = stmt.getUpdateCount();
                logger.warn("操作[{}]影响记录数:[{}]", sql, count);
                return null;
            }
            
        });
        return false;
    }

    
}
