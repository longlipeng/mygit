/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderQueryAction.java
 * Author:   13071598
 * Date:     2013-10-30 下午03:14:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.OrderConst;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderQueryAction extends StockTransferOrderBaseAction{

    /**
     */
    private static final long serialVersionUID = -6542771315578671150L;

    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(StockTransferOrderQueryAction.class);
    /* (non-Javadoc)
     * @see com.huateng.univer.seller.stockTransferOrder.StockTransferOrderBaseAction#init()
     */
    @Override
    protected void init() {
        
    }
    /**
     * 
     * 功能描述: <br>
     * 进入主页面
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String list(){
    	try{
            ListPageInit("sellOrder", sellOrderQueryDTO);
            sellOrderQueryDTO.setEntityId(getUser().getEntityId());
             /***
              * 按订单ID的倒序排序
              */
            if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_QUERY,
                      sellOrderQueryDTO).getDetailvo();
            stockTransferOrders = result.getData();
            sellOrder_totalRows = result.getTotalRecord();
        }catch(Exception e){
            this.logger.error(e.getMessage());
        }
        return "list";
    }
    
    
    /**
     * 
     * 功能描述: <br>
     * 查询当前机构发起的、当前机构作为调出机构的、作为调入机构的调拨订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String query(){
        try{
            ListPageInit("sellOrder", sellOrderQueryDTO);
            if(sellOrderQueryDTO.getOrderId().trim().equals("") 
                    && sellOrderQueryDTO.getFirstEntityName().trim().equals("") 
                            && sellOrderQueryDTO.getProcessEntityName().trim().equals("")
                                    && sellOrderQueryDTO.getOrderDateStart().trim().equals("") 
                                            && sellOrderQueryDTO.getOrderDateEnd().equals("")){
                	//return list();
            }
            //sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
            sellOrderQueryDTO.setEntityId(getUser().getEntityId());
             /***
              * 按订单ID的倒序排序
              */
            if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_QUERY,
                      sellOrderQueryDTO).getDetailvo();
            stockTransferOrders = result.getData();
            sellOrder_totalRows = result.getTotalRecord();
        }catch(Exception e){
            this.logger.error(e.getMessage());
        }
        return "list";
    }

}
