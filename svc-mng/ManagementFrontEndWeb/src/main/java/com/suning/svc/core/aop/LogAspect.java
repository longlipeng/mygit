/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: LogAspect.java
 * Author:   12073942
 * Date:     2013-5-30 下午7:42:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常日志切面
 * 
 * @author yangtao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Aspect
public class LogAspect {

    Logger logger = LoggerFactory.getLogger("AopLogger");

    /** 注解切入点 */
    @Pointcut("@annotation(com.suning.svc.core.annotation.MethodLog)")
    public void methodLogPointcut() {
    }

    /** JMS收消息切入点 */
    @Pointcut("execution(* onMessage(..))")
    public void onMessagePointcut() {
    }

    /** 环绕切入 */
    @Around(value = "methodLogPointcut() || onMessagePointcut()")
    public Object doSurround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Beginning method : " + joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName()
                + "()");
        long beginTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            logger.error("Exception/Error caught : [message:]{};\n[cause:]{};\n[stackTrace:]{}",
                    new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("Ending method : " + joinPoint.getTarget().getClass() + "."
                    + joinPoint.getSignature().getName() + "()");
            logger.info("Method invocation time : " + (endTime - beginTime) + " ms.");
        }
    }
}
