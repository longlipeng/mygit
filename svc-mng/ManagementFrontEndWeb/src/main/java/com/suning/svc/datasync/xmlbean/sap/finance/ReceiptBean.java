/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ReceiptBean.java
 * Author:   12073942
 * Date:     2013-4-22 下午2:35:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.sap.finance;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 财务记账ESB确认报文数据内容Bean
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@XStreamAlias("IT_OUT")
public class ReceiptBean {

    @XStreamAlias("STATUS")
    private String status;

    @XStreamAlias("ERROR")
    private String error;

    @XStreamAlias("TRANS_SEQ")
    private String transSeq;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTransSeq() {
        return transSeq;
    }

    public void setTransSeq(String transSeq) {
        this.transSeq = transSeq;
    }

}
