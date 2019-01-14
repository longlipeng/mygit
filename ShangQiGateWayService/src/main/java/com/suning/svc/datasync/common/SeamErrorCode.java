/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SeamErrorCode.java
 * Author:   杨青
 * Date:     2013-1-6 上午09:19:41
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 杨青                          2013-1-6  
 */
package com.suning.svc.datasync.common;

/**
 * Seam 平台服务定义系统级错误代码
 * 
 * @author 杨青
 * 
 */
public class SeamErrorCode {
    /**
     * SEAM通用异常-未知的平台框架异常
     */
    public static final String SEAM_ERROR_001 = "EMP001X";

    /**
     * SEAM通用异常-接收到未知JMS消息类型。
     */
    public static final String SEAM_ERROR_002 = "EMP002X";

    /**
     * SEAM通用异常-解析jms消息体到文本或者解析jms的ID出现异常。
     */
    public static final String SEAM_ERROR_003 = "EMP003X";

    /**
     * SEAM通用异常-解析xml消息序列化xmlBean对象失败
     */
    public static final String SEAM_ERROR_007 = "EMP007X";

    /**
     * SEAM通用异常-xmlBean对象反序列化到xml消息失败
     */
    public static final String SEAM_ERROR_008 = "EMP008X";

    /**
     * SEAM通用异常-远程调用ws服务出现框架未捕获异常
     */
    public static final String SEAM_ERROR_004 = "EMP004X";

    /**
     * SEAM通用异常-处理完事务向源头发送回执消息失败
     */
    public static final String SEAM_ERROR_005 = "EMP005X";

    /**
     * SEAM通用异常-发送消息到目标队列发送失败
     */
    public static final String SEAM_ERROR_006 = "EMP006X";

    /**
     * SEAM通用异常-xmlBean to DTO beanCopy异常
     */
    public static final String SEAM_ERROR_009 = "EMP009X";

    /**
     * WebService调用ESB异常
     */
    public static final String SEAM_ERROR_010 = "EMP010X";

    /**
     * WebService调用超时 自定义超时
     */
    public static final String SEAM_ERROR_011 = "SEAM_ERROR_011";

    /**
     * 
     * http post调用异常
     */
    public static final String SEAM_ERROR_012 = "SEAM_ERROR_012";

    /**
     * 
     * Hessian调用ADMIN等超时
     */
    public static final String SEAM_ERROR_013 = "SEAM_ERROR_013";
}
