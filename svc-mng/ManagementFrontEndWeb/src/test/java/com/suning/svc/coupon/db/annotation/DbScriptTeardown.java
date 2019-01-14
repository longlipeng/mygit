/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DbScriptConfig.java
 * Author:   秦伟
 * Date:     2013-11-15 上午11:03:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.db.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface DbScriptTeardown {

    /**
     * 执行数据库脚本使用的数据源bean name
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String datasource();
    
    /**
     * 数据库脚本的路径 
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String value();
    
    /**
     * 文件编码
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String encoding() default "UTF-8";
    
    /**
     * 加载文件失败是否继续 
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    boolean continueOnError() default true;
}
