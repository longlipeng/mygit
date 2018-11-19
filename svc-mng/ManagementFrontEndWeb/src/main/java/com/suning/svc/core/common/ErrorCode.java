/**
 * SUNING APPLIANCE CHAINS.
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package com.suning.svc.core.common;

/**
 * 错误码
 * 
 * @author 陈书元 2011-11-8
 */
public class ErrorCode {

    /** 查询模板不存在 */
    public static final String ERROR_QUERY_TEMPLATE_NOT_EXIST = "error.query.template.not.exist";

    /** 生成查询语句出错 */
    public static final String ERROR_BUILD_QUERY_STRING = "error.build.query.string";

    /** 无法创建Hibernate模板 */
    public static final String ERROR_CANT_CREATE_HIBERNATE_TEMPLATE = "error.cant.create.hibernate.template";

    /** 服务模板执行出现异常 */
    public static final String ERROR_SERVICE_TEMPLATE_EXECUTE = "error.service.template.execute";

    /** 服务拦截器调用异常 */
    public static final String ERROR_SERVICE_INTERCEPTOR_INVOKE = "error.service.interceptor.invoke";

    /** 服务拦截器事务执行异常 */
    public static final String ERROR_SERVICE_INTERCEPTOR_TRANS_EXECUTE = "error.service.interceptor.trans.execute";

    /** 事件主题不存在 */
    public static final String ERROR_EVENT_TOPIC_NOT_EXIST = "error.event.topic.not.exist";

    /** 一个接一个业务处理模版执行异常 */
    public static final String ERROR_ONE_BY_ONE_TEMPLATE_EXECUTE = "error.one.by.one.tempatale.execute";

    /** 参数空异常 */
    public static final String ERROR_PARAM_NULL = "error.param.null";

    /** 业务正在处理中 */
    public static final String ERROR_BIZ_PROCESSING = "error.biz.processing";

    /** 退货受理中 */
    public static final String ERROR_BIZ_REFUND = "error.biz.refund";

}
