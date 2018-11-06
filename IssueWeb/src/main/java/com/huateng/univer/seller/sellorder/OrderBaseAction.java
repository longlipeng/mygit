package com.huateng.univer.seller.sellorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.extremecomponents.table.core.TableConstants;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.util.SystemInfo;

/***
 * 
 * @author dawn 订单基础类
 */
public abstract class OrderBaseAction extends BaseAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(OrderBaseAction.class);
    
    protected SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();

    protected SellerContractQueryDTO sellerContractQueryDTO = new SellerContractQueryDTO();

    protected SellOrderDTO sellOrderDTO = new SellOrderDTO();
    /***
     * 订单类型
     */
    protected List<DictInfoDTO> orderTypeList;

    protected abstract void init();

    protected abstract void addCondition();

    protected abstract void dealWithSellOrderInputDTO();

    protected abstract void initOrderType();

    @SuppressWarnings("unchecked")
    protected List<?> sellOrders = new ArrayList();

    protected int sellOrderListSize = 0;

    protected int sellOrder_totalRows = 0;

    protected JSONArray saleUserList;

    protected SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();

    protected List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

    protected List<CardLayoutDTO> cardLayouts = new ArrayList<CardLayoutDTO>();
    /**
     * 服务列表
     */
    protected List<ServiceDTO> services = new ArrayList<ServiceDTO>();
    /**
     * 产品面额列表
     */
    protected List<ProdFaceValueDTO> prodFaceValues = new ArrayList<ProdFaceValueDTO>();
    /**
     * 送货方式
     */
    protected List<DictInfoDTO> deliveryMeansList = new ArrayList<DictInfoDTO>();
    /**
     * 支付方式
     */
    protected List<DictInfoDTO> paymentTermList = new ArrayList<DictInfoDTO>();

    /**
     * 包装列表
     */
    protected List<PackageDTO> packages = new ArrayList<PackageDTO>();

    protected List<DeliveryRecipientDTO> deliveryRecipientDTOs = new ArrayList<DeliveryRecipientDTO>();

    protected List<DictInfoDTO> invoiceItemIds = new ArrayList<DictInfoDTO>();

    protected String productName;

    protected String[] ec_choose;

    protected CustomerDTO customerDTO = new CustomerDTO();

    protected String error_jsp;

    protected List<?> customerOrderLists = new ArrayList();

    protected List<?> origCardLists = new ArrayList();

    protected int customerOrderLists_totalRows = 0;

    protected int origCardLists_totalRows = 0;

    protected SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();

    protected SellOrderListQueryDTO sellOrderListQueryDTO = new SellOrderListQueryDTO();
    protected SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO = new SellOrderOrigCardListQueryDTO();
    protected OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();
    
    protected OrderReceiveCardListQueryDTO orderNotReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();

    protected SellOrderFlowQueryDTO sellOrderFlowQueryDTO = new SellOrderFlowQueryDTO();

    protected SellOrderPaymentQueryDTO sellOrderPaymentQueryDTO = new SellOrderPaymentQueryDTO();

    protected String actionName;

    protected String operation;

    protected String actionMethodName;

    protected SellOrderFlowDTO sellOrderFlowDTO;

    protected SellerDTO sellerDTO = new SellerDTO();

    protected SellOrderPaymentDTO orderPaymentDTO = new SellOrderPaymentDTO();

    protected String orderPaymentListStr;

    protected String paymentTypeDesc;
    
    //接收卡号段
    protected int acceptOrderCard_totalRows=0;
    protected List<?> acceptCardList=new ArrayList();
    protected AcceptOrderDTO acceptOrderDTO=new AcceptOrderDTO();
    //卡接收明细
    protected List<Map<String,Object>> orderCardReceiveList = new ArrayList<Map<String,Object>>();
    protected int orderCardReceiveList_totalRows = 0;
    
    //卡未接收明细
    protected List<Map<String,Object>> orderCardNotReceiveList = new ArrayList<Map<String,Object>>();
    protected int orderCardNotReceiveList_totalRows = 0;


    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    public String list() {
        init();
        try {
            ListPageInit("sellOrder", sellOrderQueryDTO);
            /***
             * 按订单ID的倒序排序
             */
            if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
            }
            addCondition();
            PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_AT_SELL_INPUT, sellOrderQueryDTO)
                    .getDetailvo();
            sellOrders = result.getData();
            sellOrder_totalRows = result.getTotalRecord();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list";
    }

    @SkipValidation
    public String add() {
        /***
         * 设置订单类型
         */
        initOrderType();

        /**
         * 订单来源于系统录入
         */
        sellOrderDTO.setOrderSource(OrderConst.ORDER_SOURCE_SYSTEM_INPUT);
        /**
         * 订单状态为草稿状态
         */
        sellOrderDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
        /**
         * 订单状态为未支付
         */
        sellOrderDTO.setPaymentState(OrderConst.ORDER_PAY_STATE_UNPAID);
        /**
         * 将订单相关信息 带入inputDTO中
         */
        sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
        /***
         * 订单张数
         */
        sellOrderDTO.setCardQuantity("0");

        sellOrderDTO.setProcessEntityId(getUser().getEntityId());

        /***
         * 默认订单日期为当天
         */
        if (isEmpty(sellOrderDTO.getOrderDate())) {
            sellOrderDTO.setOrderDate(getDateFormat(new Date()));
        }
        /**
         * 设置发票日期
         */
        if (isEmpty(sellOrderDTO.getInvoiceDate())) {
            sellOrderDTO.setInvoiceDate(getDateFormat(new Date()));
        }

        /**
         * 默认送货费用为0
         */
        if (isEmpty(sellOrderDTO.getDeliveryFee())) {
            sellOrderDTO.setDeliveryFee("0");
        }
        /**
         * 默认折扣费为0
         */
        if (isEmpty(sellOrderDTO.getDiscountFee())) {
            sellOrderDTO.setDiscountFee("0");
        }
        /**
         * 默认附加费为0
         */
        if (isEmpty(sellOrderDTO.getAdditionalFee())) {
            sellOrderDTO.setAdditionalFee("0");
        }
        sellOrderInputDTO.setDefaultEntityId(getUser().getEntityId());
        sellOrderInputDTO = (SellOrderInputDTO) sendService(ConstCode.SELL_ORDER_INIT, sellOrderInputDTO).getDetailvo();

        dealWithSellOrderInputDTO();
        if (sellOrderInputDTO.getSellOrderDTO() != null) {
            ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
            if (productDTO != null && productDTO.getProductId() != null && !"".equals(productDTO.getProductId())) {
                if (isNotEmpty(sellOrderDTO.getCardIssueFee())) {
                    sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
                }
                if (isNotEmpty(sellOrderDTO.getAnnualFee())) {
                    sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
                }
            }
        }
        /***
         * 将订单的支付方式修改为客户的默认支付方式
         */
        sellOrderDTO.setPaymentTerm(customerDTO.getPaymentTerm());
        /***
         * 如果支付方式为非实时支付 那么设置延期天数
         */
        if (DictInfoConstants.PAY_MENT_IS_NOT_NEED_PAY_IMMEDIATELY.equals(sellOrderDTO.getPaymentTerm())
                && ("0".equals(sellOrderDTO.getPaymentDelay()) || "".equals(sellOrderDTO.getPaymentDelay()))) {
            sellOrderDTO.setPaymentDelay(customerDTO.getPaymentDelay());
        }
        return "add";
    }

    public abstract String edit();

    public String update() {
        try {
            sendService(ConstCode.SELL_ORDER_UPDATE, sellOrderDTO).getDetailvo();
            sellOrderQueryDTO.setContractState(initContractState());
            if (!this.hasErrors()) {
                this.addActionMessage("编辑订单成功!");
            } else {
                return edit();
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }
@SuppressWarnings("unchecked")
    public String view() {
        try {
            init();
            ListPageInit("origCardLists", sellOrderOrigCardListQueryDTO);

            ListPageInit("orderList", sellOrderListQueryDTO);

            ListPageInit("orderCard", sellOrderCardListQueryDTO);

            ListPageInit("orderFlow", sellOrderFlowQueryDTO);
            		    
		    //ListPageInit("orderCardReceiveList",orderReceiveCardListQueryDTO);
            ListPageInit("acceptCardList",acceptOrderDTO);

            /* 初始化订单支付方式列表 */
            ListPageInit("orderPayment", sellOrderPaymentQueryDTO);
            SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();
            String batchNo = sellOrderDTO.getBatchNo();
             /**
             * 接收卡号段明细
             * */
            acceptOrderDTO.setOrderId(sellOrderDTO.getOrderId());
            acceptOrderDTO.setOrderType(sellOrderDTO.getOrderType());
            sellOrderCompositeDTO.setAcceptOrderDTO(acceptOrderDTO);

            /**
             * 订单
             */
            sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);

            sellOrderListQueryDTO.setOrderId(sellOrderDTO.getOrderId());

            /**
             * 订单明细
             */
            sellOrderCompositeDTO.setSellOrderListQueryDTO(sellOrderListQueryDTO);
            /**
             * 订单卡明细
             */
            sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderCardListQueryDTO.setOrderType(sellOrderDTO.getOrderType());

            sellOrderCompositeDTO.setSellOrderCardListQueryDTO(sellOrderCardListQueryDTO);
            		    /**
		     * 订单卡接收明细
		     */
		    orderReceiveCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
		    orderReceiveCardListQueryDTO.setOrderType(sellOrderDTO.getOrderType());
		    
		    sellOrderCompositeDTO.setOrderReceiveCardListQueryDTO(orderReceiveCardListQueryDTO);
		    
		    /**
		     * 订单卡未接收明细
		     */
		    orderNotReceiveCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
		    orderNotReceiveCardListQueryDTO.setOrderType(sellOrderDTO.getOrderType());
		    //默认按cardNo升序排序
		    if(orderNotReceiveCardListQueryDTO.getSortFieldName()==null||orderNotReceiveCardListQueryDTO.getSortFieldName()==""){
		    	if(orderNotReceiveCardListQueryDTO.getSort()==null||orderNotReceiveCardListQueryDTO.getSort()==""){
		    		orderNotReceiveCardListQueryDTO.setSortFieldName("cardNo");
		    		orderNotReceiveCardListQueryDTO.setSort("asc");
		    	}
		    }
		    sellOrderCompositeDTO.setOrderNotReceiveCardListQueryDTO(orderNotReceiveCardListQueryDTO);
		    
		   
            /**
             * 订单原有卡明细
             */
            sellOrderOrigCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderOrigCardListQueryDTO.setOrderType(sellOrderDTO.getOrderType());

            sellOrderCompositeDTO.setSellOrderOrigCardListQueryDTO(sellOrderOrigCardListQueryDTO);

            /* 订单支付方式明细 */
            sellOrderPaymentQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderPaymentQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            sellOrderCompositeDTO.setSellOrderPaymentQueryDTO(sellOrderPaymentQueryDTO);

            /**
             * 订单流程节点
             */
            sellOrderFlowQueryDTO.setOrderId(sellOrderDTO.getOrderId());
            sellOrderCompositeDTO.setSellOrderFlowQueryDTO(sellOrderFlowQueryDTO);

            sellOrderCompositeDTO = (SellOrderCompositeDTO) sendService(ConstCode.FRTCODE_SERVICE_SELL_ORDER_GET,
                    sellOrderCompositeDTO).getDetailvo();
            sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();
            sellOrderDTO.setBatchNo(batchNo);
            if (sellOrderCompositeDTO.getSellerDTO() != null) {
                sellerDTO = sellOrderCompositeDTO.getSellerDTO();
            }
            if (sellOrderCompositeDTO.getCustomerDTO() != null) {
                customerDTO = sellOrderCompositeDTO.getCustomerDTO();
                if ("1".equals(customerDTO.getCustomerType())) {
                    sellOrderDTO.setPerFlag("per");
                }
            }
            if (!sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
                getRequest().setAttribute("orderlistList", sellOrderCompositeDTO.getSellOrderList().getData());
                getRequest().setAttribute("orderList_" + TableConstants.TOTAL_ROWS,
                        sellOrderCompositeDTO.getSellOrderList().getTotalRecord());
            }

            if (sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)
                    || sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
                getRequest().setAttribute("origCardList", sellOrderCompositeDTO.getSellOrderOrigCardList().getData());
                getRequest().setAttribute("origCardList_" + TableConstants.TOTAL_ROWS,
                        sellOrderCompositeDTO.getSellOrderOrigCardList().getTotalRecord());
            }
            getRequest().setAttribute("orderflowList", sellOrderCompositeDTO.getSellOrderFlowList().getData());
            getRequest().setAttribute("orderFlow_" + TableConstants.TOTAL_ROWS,
                    sellOrderCompositeDTO.getSellOrderFlowList().getTotalRecord());
            if (!sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
                getRequest().setAttribute("orderCardList", sellOrderCompositeDTO.getSellOrderCardList().getData());
                getRequest().setAttribute("orderCard_" + TableConstants.TOTAL_ROWS,
                        sellOrderCompositeDTO.getSellOrderCardList().getTotalRecord());
            }
            
            if (!sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
            	if(sellOrderCompositeDTO.getSellOrderNotReceCardList()!=null){
            		getRequest().setAttribute("orderCardNotReceiveList", sellOrderCompositeDTO.getSellOrderNotReceCardList().getData());
            		getRequest().setAttribute("orderCardNotReceive_" + TableConstants.TOTAL_ROWS,
            				sellOrderCompositeDTO.getSellOrderNotReceCardList().getTotalRecord());
            	}
            }
            
            
            
            /* 初始化订单付款方式Table */
            getRequest().setAttribute("orderPaymentList", sellOrderCompositeDTO.getSellOrderPaymentList().getData());
            getRequest().setAttribute("orderPayment_" + TableConstants.TOTAL_ROWS,
                    sellOrderCompositeDTO.getSellOrderPaymentList().getTotalRecord());
            
		    //订单卡接收明细信息
		    PageDataDTO cardReceivePageDataDto = sellOrderCompositeDTO.getSellOrderReceCardList();
		    if(null!=cardReceivePageDataDto){
		       orderCardReceiveList = (List<Map<String, Object>>) cardReceivePageDataDto.getData();
		       orderCardReceiveList_totalRows = cardReceivePageDataDto.getTotalRecord();
		    }
		    if (!sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
                getRequest().setAttribute("orderCardReceiveList", sellOrderCompositeDTO.getSellOrderReceCardList().getData());
                getRequest().setAttribute("orderCardReceive_" + TableConstants.TOTAL_ROWS,
                        sellOrderCompositeDTO.getSellOrderReceCardList().getTotalRecord());
            }
		    
		    
		    //订单卡未接收明细信息
		    PageDataDTO cardNotReceivePageDataDto = sellOrderCompositeDTO.getSellOrderNotReceCardList();
		    if(null!=cardNotReceivePageDataDto){
		       orderCardNotReceiveList = (List<Map<String, Object>>) cardNotReceivePageDataDto.getData();
		       orderCardNotReceiveList_totalRows = cardReceivePageDataDto.getTotalRecord();
		    }
		    
		    
		    //接收卡号段
		    if (!sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
                getRequest().setAttribute("acceptOrderCardList", sellOrderCompositeDTO.getAcceptOrderList().getData());
                getRequest().setAttribute("acceptCardList_" + TableConstants.TOTAL_ROWS,
                        sellOrderCompositeDTO.getAcceptOrderList().getTotalRecord());
		    }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "view";
    }

    public String initContractState() {
        String contractState = "";
        // 合同买方
        // sellerContractQueryDTO
        // .setContractType(DataBaseConstant.CONTRACT_ISSUER);
        sellerContractQueryDTO.setContractBuyer(getUser().getEntityId());

        List<SellerContractDTO> sellerContractDTOList = (List<SellerContractDTO>) sendService(
                ConstCode.CONTRACT_SERVICE_INQUERY_MASTERPALTE, sellerContractQueryDTO).getDetailvo();
        if (null != sellerContractDTOList && sellerContractDTOList.size() > 0) {
            contractState = sellerContractDTOList.get(0).getContractState();
        }
        return contractState;
    }

    /**
     * 递归获取
     * 
     * @param sellerDTO
     * @param map
     * @return
     */
    private List<EntitySystemParameterDTO> getEntitySystemParameter(SellerDTO sellerDTO,
            Map<String, List<EntitySystemParameterDTO>> map) {
        sellerDTO = (SellerDTO) sendService(ConstCode.SELLER_SERVICE_INQUERY_ENTITY, sellerDTO).getDetailvo();
        List<EntitySystemParameterDTO> list = new ArrayList<EntitySystemParameterDTO>();
        if (null != sellerDTO) {
            list = map.get(sellerDTO.getFatherEntityId());
            if (null == list || list.size() == 0) {
                sellerDTO.setEntityId(sellerDTO.getFatherEntityId());
                list = getEntitySystemParameter(sellerDTO, map);
            }
        }
        return list;
    }

    /**
     * 跳转到添加订单付款方式
     * */
    public String addOrderPayment() {

        List<BankDTO> lstBankDTO = (List<BankDTO>) sendService(ConstCode.SELL_ORDER_INQUERY_BANKINFO, sellOrderDTO)
                .getDetailvo();

        if ("70000001".equals(sellOrderDTO.getOrderType())) {
            Map<String, List<EntitySystemParameterDTO>> map = SystemInfo.getEntityParameters();
            System.out.println("查询机构号："+getUser().getEntityId());
            List<EntitySystemParameterDTO> list = map.get(getUser().getEntityId());
            // 如果不存在则获取上级的entityId
            if (null == list || list.size() == 0) {
                SellerDTO sellerDTO = new SellerDTO();
                sellerDTO.setEntityId(getUser().getEntityId());
                list = getEntitySystemParameter(sellerDTO, map);
            }

            for (EntitySystemParameterDTO entitySystemParameterDTO : list) {
                if ("RANSOME_ORDER_MAX_MONEY".equals(entitySystemParameterDTO.getParameterCode())) {
                    String maxMoney = entitySystemParameterDTO.getParameterValue();
                    BigDecimal bd = new BigDecimal(maxMoney);
                    bd = bd.divide(new BigDecimal(100));
                    sellOrderDTO.setMaxRansomFee(bd.toString());
                    break;
                }
            }
        }

        orderPaymentDTO.setLstBankDTO(lstBankDTO);
        return "addOrderPayment";
    }

    /**
     * 添加订单付款方式
     * */
    public String insertOrderPayment() {
        sendService(ConstCode.SELL_ORDER_PAYMENT_INSERT, orderPaymentDTO).getDetailvo();
        if (!hasErrors()) {
            getRequest().setAttribute("sucessMessage", "添加成功!");
        } else {
            return addOrderPayment();
        }

        return "addSuccess";
    }

    /**
     * 删除订单付款方式
     * */
    public String deleteOrderPayment() {
        // orderPaymentDTO.setPaymentIds(orderPaymentListStr);
        sendService(ConstCode.SELL_ORDER_PAYMENT_DELETE, orderPaymentDTO).getDetailvo();
        if (!hasErrors()) {
            addActionMessage("删除成功");
        } else {
            return "deleteDispatch";
        }

        return null;
    }

    public SellOrderQueryDTO getSellOrderQueryDTO() {
        return sellOrderQueryDTO;
    }

    public void setSellOrderQueryDTO(SellOrderQueryDTO sellOrderQueryDTO) {
        this.sellOrderQueryDTO = sellOrderQueryDTO;
    }

    public List<?> getSellOrders() {
        return sellOrders;
    }

    public SellerContractQueryDTO getSellerContractQueryDTO() {
        return sellerContractQueryDTO;
    }

    public void setSellerContractQueryDTO(SellerContractQueryDTO sellerContractQueryDTO) {
        this.sellerContractQueryDTO = sellerContractQueryDTO;
    }

    public void setSellOrders(List<?> sellOrders) {
        this.sellOrders = sellOrders;
    }

    public int getSellOrderListSize() {
        return sellOrderListSize;
    }

    public void setSellOrderListSize(int sellOrderListSize) {
        this.sellOrderListSize = sellOrderListSize;
    }

    public int getSellOrder_totalRows() {
        return sellOrder_totalRows;
    }

    public void setSellOrder_totalRows(int sellOrderTotalRows) {
        sellOrder_totalRows = sellOrderTotalRows;
    }

    public List<DictInfoDTO> getOrderTypeList() {
        return orderTypeList;
    }

    public void setOrderTypeList(List<DictInfoDTO> orderTypeList) {
        this.orderTypeList = orderTypeList;
    }

    public SellOrderInputDTO getSellOrderInputDTO() {
        return sellOrderInputDTO;
    }

    public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
        this.sellOrderInputDTO = sellOrderInputDTO;
    }

    public void setSaleUserList(JSONArray saleUserList) {
        this.saleUserList = saleUserList;
    }

    public List<ProductDTO> getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(List<ProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }

    public JSONArray getSaleUserList() {
        return saleUserList;
    }

    public List<CardLayoutDTO> getCardLayouts() {
        return cardLayouts;
    }

    public void setCardLayouts(List<CardLayoutDTO> cardLayouts) {
        this.cardLayouts = cardLayouts;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    public List<ProdFaceValueDTO> getProdFaceValues() {
        return prodFaceValues;
    }

    public void setProdFaceValues(List<ProdFaceValueDTO> prodFaceValues) {
        this.prodFaceValues = prodFaceValues;
    }

    public List<DictInfoDTO> getDeliveryMeansList() {
        return deliveryMeansList;
    }

    public void setDeliveryMeansList(List<DictInfoDTO> deliveryMeansList) {
        this.deliveryMeansList = deliveryMeansList;
    }

    public List<DictInfoDTO> getPaymentTermList() {
        return paymentTermList;
    }

    public void setPaymentTermList(List<DictInfoDTO> paymentTermList) {
        this.paymentTermList = paymentTermList;
    }

    public List<PackageDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageDTO> packages) {
        this.packages = packages;
    }

    public List<DeliveryRecipientDTO> getDeliveryRecipientDTOs() {
        return deliveryRecipientDTOs;
    }

    public void setDeliveryRecipientDTOs(List<DeliveryRecipientDTO> deliveryRecipientDTOs) {
        this.deliveryRecipientDTOs = deliveryRecipientDTOs;
    }

    public List<DictInfoDTO> getInvoiceItemIds() {
        return invoiceItemIds;
    }

    public void setInvoiceItemIds(List<DictInfoDTO> invoiceItemIds) {
        this.invoiceItemIds = invoiceItemIds;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String[] getEc_choose() {
        return ec_choose;
    }

    public void setEc_choose(String[] ecChoose) {
        ec_choose = ecChoose;
    }

    public SellOrderDTO getSellOrderDTO() {
        return sellOrderDTO;
    }

    public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
        this.sellOrderDTO = sellOrderDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<?> getCustomerOrderLists() {
        return customerOrderLists;
    }

    public void setCustomerOrderLists(List<?> customerOrderLists) {
        this.customerOrderLists = customerOrderLists;
    }

    public int getCustomerOrderLists_totalRows() {
        return customerOrderLists_totalRows;
    }

    public void setCustomerOrderLists_totalRows(int customerOrderListsTotalRows) {
        customerOrderLists_totalRows = customerOrderListsTotalRows;
    }

    public String getError_jsp() {
        return error_jsp;
    }

    public void setError_jsp(String errorJsp) {
        error_jsp = errorJsp;
    }

    public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
        return sellOrderCardListQueryDTO;
    }

    public void setSellOrderCardListQueryDTO(SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
        this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
    }

    public SellOrderListQueryDTO getSellOrderListQueryDTO() {
        return sellOrderListQueryDTO;
    }

    public void setSellOrderListQueryDTO(SellOrderListQueryDTO sellOrderListQueryDTO) {
        this.sellOrderListQueryDTO = sellOrderListQueryDTO;
    }

    public SellOrderFlowQueryDTO getSellOrderFlowQueryDTO() {
        return sellOrderFlowQueryDTO;
    }

    public void setSellOrderFlowQueryDTO(SellOrderFlowQueryDTO sellOrderFlowQueryDTO) {
        this.sellOrderFlowQueryDTO = sellOrderFlowQueryDTO;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getActionMethodName() {
        return actionMethodName;
    }

    public void setActionMethodName(String actionMethodName) {
        this.actionMethodName = actionMethodName;
    }

    public SellOrderFlowDTO getSellOrderFlowDTO() {
        return sellOrderFlowDTO;
    }

    public void setSellOrderFlowDTO(SellOrderFlowDTO sellOrderFlowDTO) {
        this.sellOrderFlowDTO = sellOrderFlowDTO;
    }

    public SellerDTO getSellerDTO() {
        return sellerDTO;
    }

    public void setSellerDTO(SellerDTO sellerDTO) {
        this.sellerDTO = sellerDTO;
    }

    public List<?> getOrigCardLists() {
        return origCardLists;
    }

    public void setOrigCardLists(List<?> origCardLists) {
        this.origCardLists = origCardLists;
    }

    public int getOrigCardLists_totalRows() {
        return origCardLists_totalRows;
    }

    public void setOrigCardLists_totalRows(int origCardListsTotalRows) {
        origCardLists_totalRows = origCardListsTotalRows;
    }

    public SellOrderOrigCardListQueryDTO getSellOrderOrigCardListQueryDTO() {
        return sellOrderOrigCardListQueryDTO;
    }

    public void setSellOrderOrigCardListQueryDTO(SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO) {
        this.sellOrderOrigCardListQueryDTO = sellOrderOrigCardListQueryDTO;
    }

    public SellOrderPaymentQueryDTO getSellOrderPaymentQueryDTO() {
        return sellOrderPaymentQueryDTO;
    }

    public void setSellOrderPaymentQueryDTO(SellOrderPaymentQueryDTO sellOrderPaymentQueryDTO) {
        this.sellOrderPaymentQueryDTO = sellOrderPaymentQueryDTO;
    }

    public SellOrderPaymentDTO getOrderPaymentDTO() {
        return orderPaymentDTO;
    }

    public void setOrderPaymentDTO(SellOrderPaymentDTO orderPaymentDTO) {
        this.orderPaymentDTO = orderPaymentDTO;
    }

    public String getOrderPaymentListStr() {
        return orderPaymentListStr;
    }

    public void setOrderPaymentListStr(String orderPaymentListStr) {
        this.orderPaymentListStr = orderPaymentListStr;
    }
    
    
    
    public OrderReceiveCardListQueryDTO getOrderNotReceiveCardListQueryDTO() {
		return orderNotReceiveCardListQueryDTO;
	}

	public void setOrderNotReceiveCardListQueryDTO(
			OrderReceiveCardListQueryDTO orderNotReceiveCardListQueryDTO) {
		this.orderNotReceiveCardListQueryDTO = orderNotReceiveCardListQueryDTO;
	}

	/**
     * @return the orderReceiveCardListQueryDTO
     */
    public OrderReceiveCardListQueryDTO getOrderReceiveCardListQueryDTO() {
        return orderReceiveCardListQueryDTO;
    }

    /**
     * @param orderReceiveCardListQueryDTO the orderReceiveCardListQueryDTO to set
     */
    public void setOrderReceiveCardListQueryDTO(OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO) {
        this.orderReceiveCardListQueryDTO = orderReceiveCardListQueryDTO;
    }

    /**
     * @return the orderCardReceiveList
     */
    public List<Map<String, Object>> getOrderCardReceiveList() {
        return orderCardReceiveList;
    }

    /**
     * @param orderCardReceiveList the orderCardReceiveList to set
     */
    public void setOrderCardReceiveList(List<Map<String, Object>> orderCardReceiveList) {
        this.orderCardReceiveList = orderCardReceiveList;
    }

    /**
     * @return the orderCardReceiveList_totalRows
     */
    public int getOrderCardReceiveList_totalRows() {
        return orderCardReceiveList_totalRows;
    }

    /**
     * @param orderCardReceiveListTotalRows the orderCardReceiveList_totalRows to set
     */
    public void setOrderCardReceiveList_totalRows(int orderCardReceiveListTotalRows) {
        orderCardReceiveList_totalRows = orderCardReceiveListTotalRows;
    }

    /**
     * @return the acceptOrderCard_totalRows
     */
    public int getAcceptOrderCard_totalRows() {
        return acceptOrderCard_totalRows;
    }

    /**
     * @param acceptOrderCardTotalRows the acceptOrderCard_totalRows to set
     */
    public void setAcceptOrderCard_totalRows(int acceptOrderCardTotalRows) {
        acceptOrderCard_totalRows = acceptOrderCardTotalRows;
    }

    /**
     * @return the acceptCardList
     */
    public List<?> getAcceptCardList() {
        return acceptCardList;
    }

    /**
     * @param acceptCardList the acceptCardList to set
     */
    public void setAcceptCardList(List<?> acceptCardList) {
        this.acceptCardList = acceptCardList;
    }

    /**
     * @return the acceptOrderDTO
     */
    public AcceptOrderDTO getAcceptOrderDTO() {
        return acceptOrderDTO;
    }

    /**
     * @param acceptOrderDTO the acceptOrderDTO to set
     */
    public void setAcceptOrderDTO(AcceptOrderDTO acceptOrderDTO) {
        this.acceptOrderDTO = acceptOrderDTO;
    }
}
