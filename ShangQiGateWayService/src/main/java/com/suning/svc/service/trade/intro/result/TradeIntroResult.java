/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TradeIntroResult.java
 * Author:   12073942
 * Date:     2013-10-18 下午6:08:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.service.trade.intro.result;

import com.suning.framework.lang.Result;
import com.suning.svc.service.trade.intro.dto.TradeIntroDto;

/**
 * 交易接入结果
 * 
 * @author LEO
 */
public class TradeIntroResult extends Result {

    /**
     */
    private static final long serialVersionUID = 1806025554992417662L;

    /**
     * 本次请求的DTO
     */
    private TradeIntroDto tradeIntroDto;

    /**
     * @return the tradeIntroDto
     */
    public TradeIntroDto getTradeIntroDto() {
        return tradeIntroDto;
    }

    /**
     * @param tradeIntroDto the tradeIntroDto to set
     */
    public void setTradeIntroDto(TradeIntroDto tradeIntroDto) {
        this.tradeIntroDto = tradeIntroDto;
    }

}
