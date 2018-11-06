/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderInputAction.java
 * Author:   13071598
 * Date:     2013-10-29 下午03:03:04
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.extremecomponents.table.core.TableConstants;

/**
 * 〈一句话功能简述〉<br>
 * 调拨订单录入action
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderInputAction extends StockTransferOrderBaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -3501404086746263153L;
    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(StockTransferOrderInputAction.class);
    /**
     * 营销机构查询DTO
     */
    private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();
    /**
     * 订单明细DTO
     */
    private SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
    /**
     * 所属营销机构
     */
    private List<Map<String, String>> entityList = new ArrayList<Map<String, String>>();
    /**
     * 产品列表 
     */
    List<Map<String, String>> productList = new ArrayList<Map<String, String>>();
    /**
     * 调出机构ID 
     */
    private String firstEntityId;
    /**
     * 订单ID 
     */
    private String orderId;
    /**
     * 订单明细ID 
     */
    private String orderListId;

    /*
     * (non-Javadoc)
     * @see com.huateng.univer.seller.stockTransferOrder.StockTransferOrderBaseAction#init()
     */
    @Override
    protected void init() {
    }

    /**
     * 
     * 功能描述: <br>
     * 查询录入状态下的调拨订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String list() {
        try {
            ListPageInit("sellOrder", sellOrderQueryDTO);
            // sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
            sellOrderQueryDTO.setEntityId(getUser().getEntityId());
            sellOrderQueryDTO.setOrderState(OrderConst.STOCK_TRANSFER_ORDER_INPUT);
            /***
             * 按订单ID的倒序排序
             */
            if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            if(!isEmpty(sellOrderQueryDTO.getOrderDateStart())&&!isEmpty(sellOrderQueryDTO.getOrderDateEnd())){
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            	Date startDate = sdf.parse(sellOrderQueryDTO.getOrderDateStart());
            	Date endDate = sdf.parse(sellOrderQueryDTO.getOrderDateEnd());
            	if(startDate.getTime()>endDate.getTime()){
            		addActionError("订单起始日期不能大于结束日期！");
            		return "list";
            	}
            }
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_QUERY_AT_INPUT,
                    sellOrderQueryDTO).getDetailvo();
            stockTransferOrders = result.getData();
            sellOrder_totalRows = result.getTotalRecord();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list";
    }

    /**
     * 
     * 功能描述: <br>
     * 获得调出机构和调入机构的机构列表
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String add() {

        sellerQueryDTO.setEntityId(getUser().getEntityId());
        entityList = getSellerList(sellerQueryDTO);
        return "add";
    }

    /**
     * 
     * 功能描述: <br>
     * 获得营销机构及下属机构
     *
     * @param sellerQueryDTO
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getSellerList(SellerQueryDTO sellerQueryDTO) {

        List<SellerQueryDTO> sDTO = (List<SellerQueryDTO>) sendService(ConstCode.SELLER_LIST_QUERY, sellerQueryDTO)
                .getDetailvo();
        List<Map<String, String>> sellerList = new ArrayList<Map<String, String>>();
        for (SellerQueryDTO serviceDto : sDTO) {
            Map<String, String> seller = new HashMap<String, String>();
            seller.put("entityId", serviceDto.getEntityId());
            seller.put("entityName", serviceDto.getSellerName());
            sellerList.add(seller);
        }
        return sellerList;
    }

    /**
     * 
     * 功能描述: <br>
     * 获得调出机构和调入机构都允许销售的产品
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public String getProducts() {
        try {
            List<ProductQueryDTO> pDTO = (List<ProductQueryDTO>) sendService(ConstCode.QUERY_FIRST_PROCESS_PRODUCTS,
                    sellOrderDTO).getDetailvo();
            for (ProductQueryDTO serviceDto : pDTO) {
                Map<String, String> product = new HashMap<String, String>();
                product.put("productId", serviceDto.getProductId());
                product.put("productName", serviceDto.getProductName());
                productList.add(product);
            }
            getResponse().setContentType("application/json; charset=utf-8");
            getResponse().setCharacterEncoding("utf-8");
            getResponse().getWriter().println(JSONArray.fromObject(productList));
            getResponse().getWriter().close();
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
        return "edit";
    }

    /**
     * 
     * 功能描述: <br>
     * 查询调出机构的库存信息
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getFirstEntityStock() {
        try {
            sendService(ConstCode.STOCK_TRANSFER_ORDER_UPDATE, sellOrderDTO).getDetailvo();

            sellOrderQueryDTO.setFirstEntityId(sellOrderDTO.getFirstEntityId());
            sellOrderQueryDTO.setProductId(sellOrderDTO.getProductId());
            // sellOrderQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.FIRST_ENTITY_STOCK_QUERY, sellOrderQueryDTO)
                    .getDetailvo();
            // stockTransferOrders = result.getData();
            this.getRequest().setAttribute("list", result.getData());
            this.getRequest().setAttribute("orderId", sellOrderDTO.getOrderId());
            this.getRequest().setAttribute("productId", sellOrderDTO.getProductId());
            this.getRequest().setAttribute("firstEntityId", sellOrderDTO.getFirstEntityId());
            this.getRequest().setAttribute(TableConstants.TOTAL_ROWS, result.getTotalRecord());
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "getFirstEntityStock";
    }

    /**
     * 
     * 功能描述: <br>
     * 新增订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String insert() {
        try {
            SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();
            ListPageInit("stockTransferOrderList", sellOrderCompositeDTO);

            if(sellOrderDTO.getFirstEntityId().equals(sellOrderDTO.getProcessEntityId())){
                this.addActionError("调出机构和调入机构不能相同");
                throw new Exception("调出机构和调入机构不能相同");
            }
            
            sellOrderDTO.setOrderType(OrderConst.STOCK_TRANSFER_ORDER_TYPE);
            sellOrderDTO.setOrderState(OrderConst.STOCK_TRANSFER_ORDER_INPUT);
            sellOrderCompositeDTO = (SellOrderCompositeDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_INSERT,
                    sellOrderDTO).getDetailvo();
            stockTransferOrderList = sellOrderCompositeDTO.getSellOrderList().getData();
            stockTransferOrderList_totalRows = sellOrderCompositeDTO.getSellOrderList().getTotalRecord();
            sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();

            sellerQueryDTO.setEntityId(getUser().getEntityId());
            entityList = getSellerList(sellerQueryDTO);
            if (!this.hasErrors()) {
                this.addActionMessage("新增库存调拨订单成功!");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            return list();
        }
        return "edit";
    }

    /**
     * 
     * 功能描述: <br>
     * 添加订单明细
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String insertOrderList() {

        try {
            Integer cardTotalInteger = Integer.parseInt((SystemInfo
                    .getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
            if (Integer.parseInt(sellOrderListDTO.getCardAmount()) > cardTotalInteger) {
                this.addActionError("订单卡数量不能超过系统既定最大值:" + cardTotalInteger);
                sellOrderQueryDTO.setFirstEntityId(firstEntityId);
                sellOrderQueryDTO.setProductId(sellOrderListDTO.getProductId());
                // sellOrderQueryDTO.setOrderId(sellOrderDTO.getOrderId());
                PageDataDTO result = (PageDataDTO) sendService(ConstCode.FIRST_ENTITY_STOCK_QUERY, sellOrderQueryDTO)
                        .getDetailvo();
                // stockTransferOrders = result.getData();
                this.getRequest().setAttribute("list", result.getData());
                this.getRequest().setAttribute("orderId", sellOrderListDTO.getOrderId());
                this.getRequest().setAttribute("productId", sellOrderListDTO.getProductId());
                this.getRequest().setAttribute("firstEntityId", firstEntityId);
                this.getRequest().setAttribute(TableConstants.TOTAL_ROWS, result.getTotalRecord());
                return "getFirstEntityStock";
            }
            sellOrderListDTO.setRealAmount("0");
            sendService(ConstCode.STOCK_TRANSFER_ORDER_LIST_INSERT, sellOrderListDTO).getDetailvo();
            if (!this.hasErrors()) {
                getRequest().setAttribute("sucessMessage", "添加订单明细信息成功!");
                getRequest().setAttribute("orderId", sellOrderListDTO.getOrderId());
            } else {
                sellOrderQueryDTO.setFirstEntityId(firstEntityId);
                sellOrderQueryDTO.setProductId(sellOrderListDTO.getProductId());
                // sellOrderQueryDTO.setOrderId(sellOrderDTO.getOrderId());
                PageDataDTO result = (PageDataDTO) sendService(ConstCode.FIRST_ENTITY_STOCK_QUERY, sellOrderQueryDTO)
                        .getDetailvo();
                // stockTransferOrders = result.getData();
                this.getRequest().setAttribute("list", result.getData());
                this.getRequest().setAttribute("orderId", sellOrderListDTO.getOrderId());
                this.getRequest().setAttribute("productId", sellOrderListDTO.getProductId());
                this.getRequest().setAttribute("firstEntityId", firstEntityId);
                this.getRequest().setAttribute(TableConstants.TOTAL_ROWS, result.getTotalRecord());
                return "getFirstEntityStock";
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "addSuccess";
    }

    /**
     * 
     * 功能描述: <br>
     *  进入编辑页面
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String stockTransferOrderEdit() {

        try {
            sellOrderDTO.setOrderId(orderId);

            SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();

            sellOrderCompositeDTO = (SellOrderCompositeDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_EDIT,
                    sellOrderDTO).getDetailvo();

            stockTransferOrderList = sellOrderCompositeDTO.getSellOrderList().getData();
            stockTransferOrderList_totalRows = sellOrderCompositeDTO.getSellOrderList().getTotalRecord();
            sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();

            sellerQueryDTO.setEntityId(getUser().getEntityId());
            entityList = getSellerList(sellerQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "edit";
    }

    /**
     * 
     * 功能描述: <br>
     * 编辑完更新订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String update() {

        try {
            if(sellOrderDTO.getFirstEntityId().equals(sellOrderDTO.getProcessEntityId())){
                this.addActionError("调出机构和调入机构不能为空");
                throw new Exception("调出机构和调入机构不能为空");
            }
            sendService(ConstCode.STOCK_TRANSFER_ORDER_UPDATE, sellOrderDTO).getDetailvo();
            if (!this.hasErrors()) {
                this.addActionMessage("编辑订单成功!");
            } else {
                orderId = sellOrderDTO.getOrderId();
                return stockTransferOrderEdit();
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    /**
     * 
     * 功能描述: <br>
     * 删除一条订单明细
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String deleteRecord() {
        try {
            sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderListDTO.setOrderListId(orderListId);

            this.sendService(ConstCode.DELETE_RECORD_AT_INPUT, sellOrderListDTO);

            orderId = sellOrderDTO.getOrderId();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return stockTransferOrderEdit();
    }

    /**
     * 
     * 功能描述: <br>
     * 提交订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String submitOrder() {
        try {
            sellOrderInputDTO.setEc_choose(ec_choose);
            sendService(ConstCode.STOCK_TRANSFER_ORDER_SUBMIT_AT_INPUT, sellOrderInputDTO).getDetailvo();

            if (!this.hasErrors()) {
                this.addActionMessage("提交订单成功");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    /**
     * 
     * 功能描述: <br>
     * 取消订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String cancel() {
        try {
            sellOrderInputDTO.setEc_choose(ec_choose);
            sendService(ConstCode.STOCK_TRANSFER_ORDER_CANCEL_AT_INPUT, sellOrderInputDTO).getDetailvo();

            if (!this.hasErrors()) {
                this.addActionMessage("取消订单成功!");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    public SellerQueryDTO getSellerQueryDTO() {
        return sellerQueryDTO;
    }

    public void setSellerQueryDTO(SellerQueryDTO sellerQueryDTO) {
        this.sellerQueryDTO = sellerQueryDTO;
    }

    public List<Map<String, String>> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Map<String, String>> entityList) {
        this.entityList = entityList;
    }

    public List<Map<String, String>> getProductList() {
        return productList;
    }

    public void setProductList(List<Map<String, String>> productList) {
        this.productList = productList;
    }

    public SellOrderListDTO getSellOrderListDTO() {
        return sellOrderListDTO;
    }

    public void setSellOrderListDTO(SellOrderListDTO sellOrderListDTO) {
        this.sellOrderListDTO = sellOrderListDTO;
    }

    public String getFirstEntityId() {
        return firstEntityId;
    }

    public void setFirstEntityId(String firstEntityId) {
        this.firstEntityId = firstEntityId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(String orderListId) {
        this.orderListId = orderListId;
    }

}
