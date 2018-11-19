/**
 * SUNING APPLIANCE CHAINS.
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package com.suning.svc.core.template;

/**
 * 一个接一个业务处理模版<br>
 * 防止在请求并发下，业务重复处理，比如重复充值。<br>
 * 
 * @author 陈书元 2011-10-27
 */
public interface OneByOneTemplate {

    /**
     * 执行
     * 
     * @param oneByOne 一个接一个处理记录
     * @param callBack 回调
     * @return 执行结果
     */
    <T> T execute(OneByOne oneByOne, CallBack<T> callBack);

}
