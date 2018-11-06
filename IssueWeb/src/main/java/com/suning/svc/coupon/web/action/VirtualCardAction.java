/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: VirtualCardAction.java
 * Author:   孙超
 * Date:     2013-10-29 下午05:00:29
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.VirtualCardDto;
import com.allinfinance.svc.coupon.dto.VirtualCardQueryDto;
import com.huateng.framework.action.BaseAction;

/**
 * 虚拟卡
 * 
 * @author 孙超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class VirtualCardAction extends BaseAction {

    /**
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(VirtualCardAction.class);

    private VirtualCardQueryDto virtualCardQueryDto = new VirtualCardQueryDto();
    private VirtualCardDto virtualCardDto = new VirtualCardDto();
    private int virtual_totalRows = 0;
    private List<?> virtualCardList = new ArrayList();

    public String list() {
        try {
            virtualCardQueryDto.setFatherEntityId(this.getUser().getDefaultEntityId());
            ListPageInit("virtual", virtualCardQueryDto);
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.VIRTUAL_CARD_DETAIL, virtualCardQueryDto)
                    .getDetailvo();
            virtual_totalRows = result.getTotalRecord();
            virtualCardList = result.getData();
            if (hasErrors()) {
                return "input";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "list";
    }

    public VirtualCardQueryDto getVirtualCardQueryDto() {
        return virtualCardQueryDto;
    }

    public void setVirtualCardQueryDto(VirtualCardQueryDto virtualCardQueryDto) {
        this.virtualCardQueryDto = virtualCardQueryDto;
    }

    public VirtualCardDto getVirtualCardDto() {
        return virtualCardDto;
    }

    public void setVirtualCardDto(VirtualCardDto virtualCardDto) {
        this.virtualCardDto = virtualCardDto;
    }

    public int getVirtual_totalRows() {
        return virtual_totalRows;
    }

    public void setVirtual_totalRows(int virtual_totalRows) {
        this.virtual_totalRows = virtual_totalRows;
    }

    public List<?> getVirtualCardList() {
        return virtualCardList;
    }

    public void setVirtualCardList(List<?> virtualCardList) {
        this.virtualCardList = virtualCardList;
    }

}
