/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AppException.java
 * Author:   陈书元
 * Date:     2010-7-12
 * Description:     
 * History: 
 * <author>      <time>      <version>    <desc>
 *  陈书元          2010-7-12        
 */
package com.suning.framework.lang;

/**
 * 应用异常
 * 
 * @author 陈书元 2010-7-12
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -101473286311108767L;

    /** 错误码 */
    protected String          errorCode;

    /**
     * 构造函数
     * @param errorCode 错误码
     */
    public AppException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     * @param errorCode 错误码
     * @param message 异常信息
     */
    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     * @param errorCode 错误码
     * @param cause 原异常
     */
    public AppException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     * @param errorCode 错误码
     * @param message 异常信息
     * @param cause 原异常
     */
    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

}
