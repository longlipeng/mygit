/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: StockTransferOrderAcceptAction.java
 * Author:   13071598
 * Date:     2013-10-30 下午03:14:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.seller.stockTransferOrder;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.allinfinance.univer.seller.order.dto.StockTransferOrderAcceptDTO;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.LUHNGenerator;
import com.huateng.framework.util.SystemInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StockTransferOrderAcceptAction extends StockTransferOrderBaseAction {

    /**
     */
    private static final long serialVersionUID = -8089732617155880030L;
    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(StockTransferOrderAcceptAction.class);
    /**
     * 订单明细查询DTO
     */
    private SellOrderListQueryDTO orderListQueryDTO = new SellOrderListQueryDTO();
    /**
     * 订单卡明细查询DTO
     */
    private SellOrderCardListQueryDTO orderCardListQueryDTO = new SellOrderCardListQueryDTO();
    /**
     * 进入编辑页面时查询订单信息DTO
     */
    private SellOrderReadyQueryDTO orderReadyQueryDTO = new SellOrderReadyQueryDTO();
    /**
     * 查询结果DTO
     */
    private SellOrderReadyResultDTO orderReadyResultDTO = new SellOrderReadyResultDTO();
    /**
     * 库存调拨订单入库DTO
     */
    private StockTransferOrderAcceptDTO stockTransferOrderAcceptDTO = new StockTransferOrderAcceptDTO();
    /**
     * 订单明细list
     */
    @SuppressWarnings("unchecked")
    private List<?> orderListDTOs = new ArrayList();
    /**
     * 订单明细总记录数
     */
    private int orderListRow_totalRows = 0;
    /**
     * 订单卡明细list
     */
    @SuppressWarnings("unchecked")
    private List<?> orderCardListDTOs = new ArrayList();
    /**
     * 订单卡明细总记录数
     */
    private int orderCardListRow_totalRows = 0;
    /**
     * 接收时起始卡号、结束卡号、序号、卡片张数
     */
    private String startCardNoStr;
    private String endCardNoStr;
    private int cardPeriodIdStr = 0;
    private String cardSumStr;
    /**
     * 接收时卡号段列表
     */
    @SuppressWarnings("unused")
    private List<Map> cardPeriodList = new ArrayList<Map>();
    /**
     * 接收时卡号段列表总记录数
     */
    private int cardPeriodListRow_totalRows = 0;
    /**
     * 接收时起始卡号、结束卡号、序号、卡片张数数组
     */
    private int[] cardPeriodId;
    private String[] startCardNo;
    private String[] endCardNo;
    private String[] cardSum;
    /**
     * 
     */
    private Map<Integer, Integer> cardPeriodIdForm = new HashMap<Integer, Integer>();
    private Map<Integer, String> startCardNoForm = new HashMap<Integer, String>();;
    private Map<Integer, String> endCardNoForm = new HashMap<Integer, String>();;
    private Map<Integer, String> cardSumForm = new HashMap<Integer, String>();
    /**
     * 删除卡号段
     */
    private String deleteId;
    private String[] choose;
    /**
     * 未接收卡号分也时用
     */
    private String[] cardPeriodListKey;
    private String[] cardPeriodListValue;
    
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
     * 查询已出库和部分入库状态下的调拨订单
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
            // sellOrderQueryDTO.setOrderState(OrderConst.STOCK_TRANSFER_ORDER_OUT);
            /***
             * 按订单ID的倒序排序
             */
            if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            if(null!=sellOrderQueryDTO.getOrderDateStart()&&null!=sellOrderQueryDTO.getOrderDateEnd()){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = sdf.parse(sellOrderQueryDTO.getOrderDateStart());
            	Date endDate = sdf.parse(sellOrderQueryDTO.getOrderDateEnd());
            	if(startDate.getTime()>endDate.getTime()){
            		addActionError("起始日期不能大于结束日期！");
            		return "list";
            	}
			}
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.STOCK_TRANSFER_ORDER_QUERY_AT_ACCEPT,
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
     * 进入接收页面
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String accept() {
        try {
            orderListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            ListPageInit(null, orderListQueryDTO);
            if (orderListQueryDTO.isQueryAll()) {
                orderListQueryDTO.setQueryAll(false);
                orderListQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo
                        .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
            }

            orderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            ListPageInit("orderCardListRow", orderCardListQueryDTO);
            if (orderCardListQueryDTO.isQueryAll()) {
                orderCardListQueryDTO.setQueryAll(false);
                orderCardListQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo
                        .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
            }

            orderReadyQueryDTO.setOrderListQueryDTO(orderListQueryDTO);
            orderReadyQueryDTO.setOrderCardListQueryDTO(orderCardListQueryDTO);

            orderReadyResultDTO = (SellOrderReadyResultDTO) this.sendService(ConstCode.STOCK_TRANSFER_ORDER_READY,
                    orderReadyQueryDTO).getDetailvo();

            sellOrderDTO = orderReadyResultDTO.getSellOrderDTO();

            orderListDTOs = orderReadyResultDTO.getOrderLists().getData();
            orderListRow_totalRows = orderListDTOs.size();

            orderCardListDTOs = orderReadyResultDTO.getOrderCardLists().getData();
            orderCardListRow_totalRows = orderReadyResultDTO.getOrderCardLists().getTotalRecord();
            
           // if (cardPeriodId != null) {
//                for (int i = 0; i < cardPeriodId.length; i++) {
//                    Map<String, String> cardPeriodMap = new HashMap<String, String>();
//                    cardPeriodMap.put("cardPeriodId", String.valueOf(cardPeriodId[i]));
//                    cardPeriodMap.put("startCardNo", startCardNo[i]);
//                    cardPeriodMap.put("endCardNo", endCardNo[i]);
//                    cardPeriodMap.put("cardSum", cardSum[i]);
//                    cardPeriodList.add(cardPeriodMap);
//                }
//                cardPeriodListRow_totalRows = cardPeriodList.size();
//            }
//            if(cardPeriodList.size() > 0){
//                cardPeriodListRow_totalRows = cardPeriodList.size();
           // }
            if(cardPeriodList.size() <= 0 && cardPeriodListKey != null){
                Map<String, String> cardPeriodMap = new HashMap<String, String>();
                for(int i = 0;i <= cardPeriodListKey.length;i++){
                    
                    if(i % 4 == 0 && i != 0){
                        cardPeriodList.add(cardPeriodMap);
                        if(i == cardPeriodListKey.length){
                            break;
                        }
                        cardPeriodMap = new HashMap<String, String>();
                    }
                    
                    cardPeriodMap.put(cardPeriodListKey[i], cardPeriodListValue[i]);
                    continue;
                }
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "accept";
    }

    /**
     * 
     * 功能描述: <br>
     * 接收卡号段
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public String acceptCard() {
        try {

            if (cardPeriodId != null) {
                for (int i = 0; i < cardPeriodId.length; i++) {
                    Map<String, String> cardPeriodMap = new HashMap<String, String>();
                    cardPeriodMap.put("cardPeriodId", String.valueOf(cardPeriodId[i]));
                    cardPeriodMap.put("startCardNo", startCardNo[i]);
                    cardPeriodMap.put("endCardNo", endCardNo[i]);
                    cardPeriodMap.put("cardSum", cardSum[i]);
                    cardPeriodList.add(cardPeriodMap);
                }
            }

            List<String> list = (List<String>) sendService(ConstCode.QUERY_ORDER_CARD_LIST, sellOrderDTO).getDetailvo();

            if(endCardNoStr.length() > 19 || startCardNoStr.length()>19 
                    || startCardNoStr.length() < 13 || endCardNoStr.length() < 13){
                this.addActionError("请输入13~19位卡号~!");
                throw new Exception("请输入13~19位卡号~!");
            }
            
            long cardSumStrTemp = 0;
            long endCardNoStrLong = Long.parseLong(endCardNoStr.substring(0, endCardNoStr.length() - 1));
            long startCardNoStrLong = Long.parseLong(startCardNoStr.substring(0, startCardNoStr.length() - 1));
            cardSumStrTemp = endCardNoStrLong - startCardNoStrLong + 1;
            
            if(cardSumStrTemp <= 0){
                this.addActionError("请输入有效卡号段~！");
                throw new Exception("请输入有效卡号段~！");
            }
            
            String startCardNoTemp = null;
            
            for (long i = 0; i < cardSumStrTemp; i++) {
                startCardNoTemp = LUHNGenerator.generate(String.valueOf(startCardNoStrLong + i));
                if (!list.contains(startCardNoTemp)) {
                    this.addActionError("卡号：" + startCardNoTemp + "不在出库中或已作废!");
                    throw new Exception("卡号：" + startCardNoTemp + "不在出库中或已作废!");
                }
            }

            Map<String, String> cardPeriodMap_1 = new HashMap<String, String>();
            // System.out.println(endCardNoStr - startCardNoStr);

            cardPeriodIdStr++;
            cardSumStr = String.valueOf(cardSumStrTemp);
            cardPeriodMap_1.put("cardPeriodId", String.valueOf(cardPeriodIdStr));
            cardPeriodMap_1.put("startCardNo", startCardNoStr);
            cardPeriodMap_1.put("endCardNo", endCardNoStr);
            cardPeriodMap_1.put("cardSum", cardSumStr);
            cardPeriodList.add(cardPeriodMap_1);
            cardPeriodListRow_totalRows = cardPeriodList.size();
            
        } catch (Exception e) {
            this.logger.error(e);
        }
        return accept();
    }

    /**
     * 
     * 功能描述: <br>
     * 删除某个卡号段
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String deleteRecord() {
        try {
            if (cardPeriodId != null) {
                List<String> listId = new ArrayList<String>();
                List<String> listStart = new ArrayList<String>();
                List<String> listEnd = new ArrayList<String>();
                List<String> listSum = new ArrayList<String>();

                for (int i = 0; i < cardPeriodId.length; i++) {
                    if (!String.valueOf(cardPeriodId[i]).equals(deleteId)) {
                        listId.add(String.valueOf(cardPeriodId[i]));
                        listStart.add(startCardNo[i]);
                        listEnd.add(endCardNo[i]);
                        listSum.add(cardSum[i]);
                    }
                }
                for (int i = 0; i < listId.size(); i++) {
                    Map<String, String> cardPeriodMap = new HashMap<String, String>();
                    cardPeriodMap.put("cardPeriodId", listId.get(i));
                    cardPeriodMap.put("startCardNo", listStart.get(i));
                    cardPeriodMap.put("endCardNo", listEnd.get(i));
                    cardPeriodMap.put("cardSum", listSum.get(i));
                    cardPeriodList.add(cardPeriodMap);
                }

                cardPeriodListRow_totalRows = cardPeriodList.size();

            }
        } catch (Exception e) {
            this.logger.error(e);
        }
        return accept();
    }

    /**
     * 
     * 功能描述: <br>
     * 提交接收
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String submitAccept() {
        try {
            if (cardPeriodId == null) {
                this.addActionError("没有需要提交的卡号段~！");
            }

            // List<String> list = (List<String>) sendService(
            // ConstCode.QUERY_ORDER_CARD_LIST, sellOrderDTO)
            // .getDetailvo();

            List<SellOrderCardListQueryDTO> sellOrderCardQueryList = new ArrayList<SellOrderCardListQueryDTO>();
            for (int i = 0; i < cardPeriodId.length; i++) {
                // 防止接收到提交期间卡被作废
                // String cardSumStrTemp = null;
                // cardSumStrTemp = minus(endCardNo[i],startCardNo[i]);
                // cardSumStr = sum(cardSumStrTemp,"1");
                //           
                // String startCardNoTemp = null;
                //            
                // for(long j = 0;j < Long.parseLong(cardSumStr);j++){
                // startCardNoTemp = sum(startCardNo[i],String.valueOf(j));
                // if(!list.contains(startCardNoTemp)){
                // //throw new BizServiceException("卡号："+startCardNoTemp+"不符合出库情况!");
                // this.addActionError("卡号："+startCardNoTemp+"不符合出库情况!");
                // throw new Exception("卡号："+startCardNoTemp+"不符合出库情况!");
                // }
                // }

                SellOrderCardListQueryDTO orderCardListQueryDTO = new SellOrderCardListQueryDTO();
                orderCardListQueryDTO.setStartCardNo(startCardNo[i]);
                orderCardListQueryDTO.setEndCardNo(endCardNo[i]);
                // Map<String,String> cardPeriodMap = new HashMap<String,String>();
                // cardPeriodMap.put("cardPeriodId", String.valueOf(cardPeriodId[i]));
                // cardPeriodMap.put("startCardNo", startCardNo[i]);
                // cardPeriodMap.put("endCardNo", endCardNo[i]);
                // cardPeriodMap.put("cardSum",cardSum[i]);
                // cardPeriodList.add(cardPeriodMap);
                sellOrderCardQueryList.add(orderCardListQueryDTO);
            }
            stockTransferOrderAcceptDTO.setOrderId(sellOrderDTO.getOrderId());
            stockTransferOrderAcceptDTO.setSellOrderCardQueryList(sellOrderCardQueryList);
            // 入库
            sendService(ConstCode.SUBMIT_STOCK_TRANSFER_ORDER_ACCEPT, stockTransferOrderAcceptDTO).getDetailvo();

            // 判断部分入库或完全入库
            sendService(ConstCode.CHECK_ACCEPT, sellOrderDTO).getDetailvo();

        } catch (Exception e) {
            this.logger.error(e);
        }
        if (!this.hasErrors()) {
            this.addActionMessage("卡号段入库完成");
        }
        return list();
    }

    /**
     * 
     * 功能描述: <br>
     * 进入退回页面
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String sendBackAtAccept() {
        try {
            view();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "sendBackAtAccept";
    }

    /**
     * 
     * 功能描述: <br>
     * 退回订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String sendBackAtAcceptSubmit() {
        try {
            this.sendService(ConstCode.STOCK_TRANSFER_ORDER_SENDBACK_AT_ACCEPT, sellOrderDTO);
            if (!hasErrors()) {
                addActionMessage("调拨订单退回成功！");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }
    
    /**
     * 
     * 功能描述: <br>
     * 关闭订单
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String closeStockTransferOrder(){
        try {
            sellOrderInputDTO.setEc_choose(ec_choose);
            this.sendService(ConstCode.CLOSE_ACCEPT, sellOrderInputDTO);
            if(!hasErrors()){
                addActionMessage("调拨订单关闭成功！");
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }
    

    public SellOrderListQueryDTO getOrderListQueryDTO() {
        return orderListQueryDTO;
    }

    public void setOrderListQueryDTO(SellOrderListQueryDTO orderListQueryDTO) {
        this.orderListQueryDTO = orderListQueryDTO;
    }

    public SellOrderCardListQueryDTO getOrderCardListQueryDTO() {
        return orderCardListQueryDTO;
    }

    public void setOrderCardListQueryDTO(SellOrderCardListQueryDTO orderCardListQueryDTO) {
        this.orderCardListQueryDTO = orderCardListQueryDTO;
    }

    public SellOrderReadyQueryDTO getOrderReadyQueryDTO() {
        return orderReadyQueryDTO;
    }

    public void setOrderReadyQueryDTO(SellOrderReadyQueryDTO orderReadyQueryDTO) {
        this.orderReadyQueryDTO = orderReadyQueryDTO;
    }

    public SellOrderReadyResultDTO getOrderReadyResultDTO() {
        return orderReadyResultDTO;
    }

    public void setOrderReadyResultDTO(SellOrderReadyResultDTO orderReadyResultDTO) {
        this.orderReadyResultDTO = orderReadyResultDTO;
    }

    public List<?> getOrderListDTOs() {
        return orderListDTOs;
    }

    public void setOrderListDTOs(List<?> orderListDTOs) {
        this.orderListDTOs = orderListDTOs;
    }

    public int getOrderListRow_totalRows() {
        return orderListRow_totalRows;
    }

    public void setOrderListRow_totalRows(int orderListRowTotalRows) {
        orderListRow_totalRows = orderListRowTotalRows;
    }

    public List<?> getOrderCardListDTOs() {
        return orderCardListDTOs;
    }

    public void setOrderCardListDTOs(List<?> orderCardListDTOs) {
        this.orderCardListDTOs = orderCardListDTOs;
    }

    public int getOrderCardListRow_totalRows() {
        return orderCardListRow_totalRows;
    }

    public void setOrderCardListRow_totalRows(int orderCardListRowTotalRows) {
        orderCardListRow_totalRows = orderCardListRowTotalRows;
    }

    public List<Map> getCardPeriodList() {
        return cardPeriodList;
    }

    public void setCardPeriodList(List<Map> cardPeriodList) {
        this.cardPeriodList = cardPeriodList;
    }

    public int getCardPeriodListRow_totalRows() {
        return cardPeriodListRow_totalRows;
    }

    public void setCardPeriodListRow_totalRows(int cardPeriodListRowTotalRows) {
        cardPeriodListRow_totalRows = cardPeriodListRowTotalRows;
    }

    public String getStartCardNoStr() {
        return startCardNoStr;
    }

    public void setStartCardNoStr(String startCardNoStr) {
        this.startCardNoStr = startCardNoStr;
    }

    public String getEndCardNoStr() {
        return endCardNoStr;
    }

    public void setEndCardNoStr(String endCardNoStr) {
        this.endCardNoStr = endCardNoStr;
    }

    public int getCardPeriodIdStr() {
        return cardPeriodIdStr;
    }

    public void setCardPeriodIdStr(int cardPeriodIdStr) {
        this.cardPeriodIdStr = cardPeriodIdStr;
    }

    public String getCardSumStr() {
        return cardSumStr;
    }

    public void setCardSumStr(String cardSumStr) {
        this.cardSumStr = cardSumStr;
    }

    public int[] getCardPeriodId() {
        return cardPeriodId;
    }

    public void setCardPeriodId(int[] cardPeriodId) {
        this.cardPeriodId = cardPeriodId;
    }

    public String[] getStartCardNo() {
        return startCardNo;
    }

    public void setStartCardNo(String[] startCardNo) {
        this.startCardNo = startCardNo;
    }

    public String[] getEndCardNo() {
        return endCardNo;
    }

    public void setEndCardNo(String[] endCardNo) {
        this.endCardNo = endCardNo;
    }

    public String[] getCardSum() {
        return cardSum;
    }

    public void setCardSum(String[] cardSum) {
        this.cardSum = cardSum;
    }

    public Map<Integer, Integer> getCardPeriodIdForm() {
        return cardPeriodIdForm;
    }

    public void setCardPeriodIdForm(Map<Integer, Integer> cardPeriodIdForm) {
        this.cardPeriodIdForm = cardPeriodIdForm;
    }

    public Map<Integer, String> getStartCardNoForm() {
        return startCardNoForm;
    }

    public void setStartCardNoForm(Map<Integer, String> startCardNoForm) {
        this.startCardNoForm = startCardNoForm;
    }

    public Map<Integer, String> getEndCardNoForm() {
        return endCardNoForm;
    }

    public void setEndCardNoForm(Map<Integer, String> endCardNoForm) {
        this.endCardNoForm = endCardNoForm;
    }

    public Map<Integer, String> getCardSumForm() {
        return cardSumForm;
    }

    public void setCardSumForm(Map<Integer, String> cardSumForm) {
        this.cardSumForm = cardSumForm;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public String[] getChoose() {
        return choose;
    }

    public void setChoose(String[] choose) {
        this.choose = choose;
    }

    public StockTransferOrderAcceptDTO getStockTransferOrderAcceptDTO() {
        return stockTransferOrderAcceptDTO;
    }

    public void setStockTransferOrderAcceptDTO(StockTransferOrderAcceptDTO stockTransferOrderAcceptDTO) {
        this.stockTransferOrderAcceptDTO = stockTransferOrderAcceptDTO;
    }

    public String[] getCardPeriodListKey() {
        return cardPeriodListKey;
    }

    public void setCardPeriodListKey(String[] cardPeriodListKey) {
        this.cardPeriodListKey = cardPeriodListKey;
    }

    public String[] getCardPeriodListValue() {
        return cardPeriodListValue;
    }

    public void setCardPeriodListValue(String[] cardPeriodListValue) {
        this.cardPeriodListValue = cardPeriodListValue;
    }

    

    
}
