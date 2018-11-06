/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MethodLog.java
 * Author:   12073942
 * Date:     2013-5-30 下午7:23:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识方法需要切面日志
 * 
 * @author yangtao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
}
