package com.suning.svc.core.template;

public interface CallBack<T> {
    /**
     * 调用
     * 
     * @return 结果
     */
    T invoke();
}
