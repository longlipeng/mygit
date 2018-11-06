package com.huateng.univer.issuer.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.ApplicationContext;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.uploadFile.dto.UploadCardFileDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.report.dto.CardBalanceDatailDTO;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderMakeCardDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;

/**
 * 库存订单基类action
 * 
 * @author xxl
 */
public abstract class StockOrderBaseAction extends BaseAction {
	private Logger logger = Logger.getLogger(StockOrderBaseAction.class);
	private static final long serialVersionUID = -1844656183356303623L;

	protected SellOrderDTO sellOrderDTO = new SellOrderDTO();
	protected SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
	protected SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
	protected SellOrderFlowDTO sellOrderFlowDTO = new SellOrderFlowDTO();
	protected SellOrderMakeCardDTO sellOrderMakeCardDTO = new SellOrderMakeCardDTO();
	protected SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();
	protected  ProductCardBinDTO  productCardBinDTO = new ProductCardBinDTO();
	protected SellerDTO sellerDTO = new SellerDTO();
	protected UploadCardFileDTO uploadCardFileDTO = new UploadCardFileDTO();
	
	

	// 订单卡明细
	protected SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
	// 订单录入卡列表
	protected SellOrderInputCardListDTO sellOrderInputCardListDTO = new SellOrderInputCardListDTO();
	// 订单明细
	protected SellOrderListQueryDTO sellOrderListQueryDTO = new SellOrderListQueryDTO();
	// 订单流程
	protected SellOrderFlowQueryDTO sellOrderFlowQueryDTO = new SellOrderFlowQueryDTO();
	//订单卡接收明细
	protected OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();
	
    public void setOrderReceiveCardListQueryDTO(OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO) {
        this.orderReceiveCardListQueryDTO = orderReceiveCardListQueryDTO;
    }

    // 订单列表
	protected List<Map<String, Object>> sellOrders = new ArrayList<Map<String, Object>>();
	protected int sellOrder_totalRows = 0;

	// 订单流程
	protected List<Map<String, Object>> sellOrderFlowList = new ArrayList<Map<String, Object>>();
	protected int sellOrderFlow_totalRows = 0;

	// 订单明细
	protected List<Map<String, Object>> orderListList = new ArrayList<Map<String, Object>>();
	protected int orderListList_totalRows = 0;

	// 订单卡明细
	protected List<Map<String, Object>> orderCardList = new ArrayList<Map<String, Object>>();
	protected int orderCardList_totalRows = 0;
	//录入卡
	protected List<Map<String, Object>> orderInputCardList = new ArrayList<Map<String, Object>>();
	protected int orderInputCardList_totalRows = 0;
	
    //卡接收明细
	protected List<Map<String,Object>> orderCardReceiveList = new ArrayList<Map<String,Object>>();
	protected int orderCardReceiveList_totalRows = 0;
	
	// 产品
	protected List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	// 卡面
	protected List<CardLayoutDTO> cardLayouts = new ArrayList<CardLayoutDTO>();
	// 服务列表
	protected List<ServiceDTO> services = new ArrayList<ServiceDTO>();
	// 产品面额列表
	protected List<ProdFaceValueDTO> prodFaceValues = new ArrayList<ProdFaceValueDTO>();

	protected String[] choose;

	protected String actionName;
	// 订单操作:confirm/reject/cancel
	
	protected String actionMethodName;

	protected String operation;

	protected String errorJsp;

	// 查询初始化
	protected abstract void inqueryInit();

	protected abstract void initActionName();
	
	
	
	public ProductCardBinDTO getProductCardBinDTO() {
		return productCardBinDTO;
	}

	public void setProductCardBinDTO(ProductCardBinDTO productCardBinDTO) {
		this.productCardBinDTO = productCardBinDTO;
	}

	public UploadCardFileDTO getUploadCardFileDTO() {
		return uploadCardFileDTO;
	}

	public void setUploadCardFileDTO(UploadCardFileDTO uploadCardFileDTO) {
		this.uploadCardFileDTO = uploadCardFileDTO;
	}

	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}
	
	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}

	@SuppressWarnings("unchecked")
	@SkipValidation
	public String list() throws Exception {
		try {
			this.inqueryInit();
			this.initActionName();
			actionMethodName = "view";
			ListPageInit("sellOrder", sellOrderQueryDTO);
			if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
				sellOrderQueryDTO.setSort("desc");
				sellOrderQueryDTO.setSortFieldName("orderId");
			}
			PageDataDTO pageDataDTO = (PageDataDTO) this.sendService(
					ConstCode.STOCK_ORDER_INQUERY, sellOrderQueryDTO)
					.getDetailvo();
			sellOrders = (List<Map<String, Object>>) pageDataDTO.getData();
			sellOrder_totalRows = pageDataDTO.getTotalRecord();
//			if (hasErrors()) {
//				// errorJsp =
//				// "WEB-INF/pages/univer/issuer/order/orderinput/stockOrderInputList.jsp";
//				return INPUT;
//			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	@SuppressWarnings("unchecked")
	@SkipValidation
	public String view() throws Exception {
		try {
			this.initActionName();
			actionMethodName = "view";
			sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);

			String orderId = sellOrderDTO.getOrderId();
			sellOrderListQueryDTO.setOrderId(orderId);
			sellOrderCardListQueryDTO.setOrderId(orderId);
			sellOrderFlowQueryDTO.setOrderId(orderId);
			orderReceiveCardListQueryDTO.setOrderId(orderId);
			sellOrderInputCardListDTO.setOrderId(orderId);

			ListPageInit("orderListList", sellOrderListQueryDTO);
			ListPageInit("orderCardList", sellOrderCardListQueryDTO);
			ListPageInit("sellOrderFlow", sellOrderFlowQueryDTO);
			ListPageInit("orderCardReceiveList",orderReceiveCardListQueryDTO);
			ListPageInit("orderInputCardList",sellOrderInputCardListDTO);

			System.out.println(sellOrderListQueryDTO.getOrderId()+"______________"+sellOrderCardListQueryDTO.getOrderId());
			sellOrderCompositeDTO
					.setSellOrderListQueryDTO(sellOrderListQueryDTO);
			sellOrderCompositeDTO
					.setSellOrderCardListQueryDTO(sellOrderCardListQueryDTO);
			sellOrderCompositeDTO
					.setSellOrderFlowQueryDTO(sellOrderFlowQueryDTO);
			sellOrderCompositeDTO.setOrderReceiveCardListQueryDTO(orderReceiveCardListQueryDTO);
			sellOrderCompositeDTO.setSellOrderInputCardListDTO(sellOrderInputCardListDTO);
			sellOrderCompositeDTO = (SellOrderCompositeDTO) this.sendService(
					ConstCode.STOCK_ORDER_VIEW, sellOrderCompositeDTO)
					.getDetailvo();
			// 订单基本信息
			sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();
			if(sellOrderCompositeDTO.getSellerDTO()!=null){
		    	sellerDTO = sellOrderCompositeDTO.getSellerDTO();
		    	ProductDTO productDTO = sellOrderCompositeDTO.getProductDTO();
				if(productDTO!=null&&productDTO.getProductId()!=null &&!"".equals(productDTO.getProductId())){
					if(isEmpty(sellOrderDTO.getCardIssueFee())){
						sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
					}
					if(isEmpty(sellOrderDTO.getAnnualFee())){
						sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
					}
				}
				if(isNotEmpty(sellOrderDTO.getDeliveryFee())){
					sellOrderDTO.setDeliveryFee(Amount.getReallyAmount(sellOrderDTO.getDeliveryFee()));
				}
			}
			
			// 订单流程信息
			PageDataDTO orderFlowPageData = sellOrderCompositeDTO.getSellOrderFlowList();
			if (null != orderFlowPageData) {
				sellOrderFlowList = (List<Map<String, Object>>) orderFlowPageData.getData();
				sellOrderFlow_totalRows = orderFlowPageData.getTotalRecord();
			}
			// 订单明细
			PageDataDTO orderListPageData =  sellOrderCompositeDTO.getSellOrderList();
			if(null != orderListPageData){
				orderListList = (List<Map<String, Object>>) orderListPageData.getData();
				orderListList_totalRows = orderListPageData.getTotalRecord();
			}
			// 订单卡列表
			PageDataDTO orderCardListPageDataDTO = sellOrderCompositeDTO.getSellOrderCardList();
			if(null != orderCardListPageDataDTO){
				orderCardList = (List<Map<String, Object>>) orderCardListPageDataDTO.getData();
				orderCardList_totalRows = orderCardListPageDataDTO.getTotalRecord();
			}
			// 订单录入卡列表
			PageDataDTO orderInputCardListPageDataDTO = sellOrderCompositeDTO.getOrderInputCardPackageList();
			if(null != orderInputCardListPageDataDTO){
				orderInputCardList = (List<Map<String, Object>>) orderInputCardListPageDataDTO.getData();
				orderInputCardList_totalRows = orderInputCardListPageDataDTO.getTotalRecord();
			}
			//订单卡接收列表
			PageDataDTO orderCardReceiveListPageDataDTO = sellOrderCompositeDTO.getSellOrderReceCardList();
			if(null != orderCardReceiveListPageDataDTO){
			    orderCardReceiveList = (List<Map<String, Object>>) orderCardReceiveListPageDataDTO.getData();
			    orderCardReceiveList_totalRows = orderCardReceiveListPageDataDTO.getTotalRecord();
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "view";
	}

	public void load() {
		try {
			sellOrderDTO = (SellOrderDTO) this.sendService(
					ConstCode.STOCK_ORDER_LOAD, sellOrderDTO).getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String downLoad(){
		sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);
		String orderId = sellOrderDTO.getOrderId();
		sellOrderInputCardListDTO.setOrderId(orderId);
		//PageDataDTO orderInputCardListPageDataDTO = sellOrderCompositeDTO.getOrderInputCardPackageList();
		List<SellOrderInputCardListDTO> list = (List<SellOrderInputCardListDTO>) this.sendService(
				ConstCode.ORDER_INPUT_CARD_LIST, sellOrderInputCardListDTO)
				.getDetailvo();
		// 拼接报表名称
				String txtName = "录入卡列表" + getUser().getIssuerName()
						+ DateUtil.getCurrentTime();
				StringBuffer result = new StringBuffer();
				result.append("   ").append(Const.ORDERID).append(Const.CARD_NO).append("\r\n");
				if (list != null) {
					for (SellOrderInputCardListDTO ca : list) {
						result.append("   ").append(ca.getOrderId()+ ",").append(
								"'" + ca.getCardNo() + ",").append("\r\n");
					}
				}
				HttpServletResponse response = this.getResponse();
				// http响应，生成下载文件
				try {
					response.setContentType("application/msword");
					response.setContentType("text/html;charset=gbk");
					response.addHeader("Content-Disposition", "attachment; filename="
							+ new String(txtName.getBytes("gb2312"), "ISO8859-1")
							+ Const.CSV);
					response.getWriter().print(result.toString());
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
					this.logger.error(e.getMessage());
				}
		return null;
		
	}

	@SkipValidation
	public String viewOrderFlow() throws Exception {
		try {
			sellOrderFlowDTO = (SellOrderFlowDTO) this.sendService(
					ConstCode.STOCK_ORDER_FLOW_VIEW, sellOrderFlowDTO)
					.getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "orderFlowView";
	}

	/**
	 * 订单操作:提交/确认/取消
	 * 
	 * @return
	 */
	@SkipValidation
	public String operate() {
		try {
			sellOrderDTO.setPerFlag("1");
			this.view();
			actionMethodName= "operate";
			if (hasErrors()) {
				this.list();
				return "list";
			}
		} catch (Exception e) {
//			this.logger.error(e.getMessage());
		}
//		System.out.println();
		return operation;
	}
	/**
     * 订单操作:订单准备专用----提交/确认/取消
     * 
     * @return
     */
    @SkipValidation
    public String operateReady() {
        try {
            sellOrderDTO.setPerFlag("1");
            this.view();
            actionMethodName= "operate";
            if (hasErrors()) {
                this.list();
                return "list";
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "ready";
    }
	/**
	 * 订单确认
	 */
	@SkipValidation
	public String confirm() throws Exception {
		try {
			this.sendService(ConstCode.STOCK_ORDER_CONFIRM, sellOrderDTO);
			if (!hasErrors()) {
				addActionMessage("提交制卡订单成功！");
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	/**
	 * 订单退回
	 */
	@SkipValidation
	public String reject() throws Exception {
		try {
			this.sendService(ConstCode.STOCK_ORDER_REJECT, sellOrderDTO);
			if (!hasErrors()) {
				addActionMessage("退回制卡订单成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	/**
	 * 订单取消
	 */
	@SkipValidation
	public String cancel() throws Exception {
		try {
			this.sendService(ConstCode.STOCK_ORDER_CANCEL, sellOrderDTO);
			if (hasErrors()) {
				return "cancel";
			}
			addActionMessage("取消制卡订单成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}

	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}

	public SellOrderQueryDTO getSellOrderQueryDTO() {
		return sellOrderQueryDTO;
	}

	public void setSellOrderQueryDTO(SellOrderQueryDTO sellOrderQueryDTO) {
		this.sellOrderQueryDTO = sellOrderQueryDTO;
	}

	public SellOrderInputDTO getSellOrderInputDTO() {
		return sellOrderInputDTO;
	}

	public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
		this.sellOrderInputDTO = sellOrderInputDTO;
	}

	public SellOrderFlowDTO getSellOrderFlowDTO() {
		return sellOrderFlowDTO;
	}

	public void setSellOrderFlowDTO(SellOrderFlowDTO sellOrderFlowDTO) {
		this.sellOrderFlowDTO = sellOrderFlowDTO;
	}

	public SellOrderMakeCardDTO getSellOrderMakeCardDTO() {
		return sellOrderMakeCardDTO;
	}

	public void setSellOrderMakeCardDTO(
			SellOrderMakeCardDTO sellOrderMakeCardDTO) {
		this.sellOrderMakeCardDTO = sellOrderMakeCardDTO;
	}

	public SellOrderCompositeDTO getSellOrderCompositeDTO() {
		return sellOrderCompositeDTO;
	}

	public void setSellOrderCompositeDTO(
			SellOrderCompositeDTO sellOrderCompositeDTO) {
		this.sellOrderCompositeDTO = sellOrderCompositeDTO;
	}

	public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
		return sellOrderCardListQueryDTO;
	}

	public void setSellOrderCardListQueryDTO(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
		this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
	}

	public SellOrderListQueryDTO getSellOrderListQueryDTO() {
		return sellOrderListQueryDTO;
	}

	public void setSellOrderListQueryDTO(
			SellOrderListQueryDTO sellOrderListQueryDTO) {
		this.sellOrderListQueryDTO = sellOrderListQueryDTO;
	}

	public SellOrderFlowQueryDTO getSellOrderFlowQueryDTO() {
		return sellOrderFlowQueryDTO;
	}

	public void setSellOrderFlowQueryDTO(
			SellOrderFlowQueryDTO sellOrderFlowQueryDTO) {
		this.sellOrderFlowQueryDTO = sellOrderFlowQueryDTO;
	}

	public List<Map<String, Object>> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(List<Map<String, Object>> sellOrders) {
		this.sellOrders = sellOrders;
	}

	public int getSellOrder_totalRows() {
		return sellOrder_totalRows;
	}

	public void setSellOrder_totalRows(int sellOrderTotalRows) {
		sellOrder_totalRows = sellOrderTotalRows;
	}

	public List<Map<String, Object>> getSellOrderFlowList() {
		return sellOrderFlowList;
	}

	public void setSellOrderFlowList(List<Map<String, Object>> sellOrderFlowList) {
		this.sellOrderFlowList = sellOrderFlowList;
	}

	public int getSellOrderFlow_totalRows() {
		return sellOrderFlow_totalRows;
	}

	public void setSellOrderFlow_totalRows(int sellOrderFlowTotalRows) {
		sellOrderFlow_totalRows = sellOrderFlowTotalRows;
	}

	public List<Map<String, Object>> getOrderListList() {
		return orderListList;
	}

	public void setOrderListList(List<Map<String, Object>> orderListList) {
		this.orderListList = orderListList;
	}

	public int getOrderListList_totalRows() {
		return orderListList_totalRows;
	}

	public void setOrderListList_totalRows(int orderListListTotalRows) {
		orderListList_totalRows = orderListListTotalRows;
	}

	public List<Map<String, Object>> getOrderCardList() {
		return orderCardList;
	}

	public void setOrderCardList(List<Map<String, Object>> orderCardList) {
		this.orderCardList = orderCardList;
	}
	
	

	public SellOrderInputCardListDTO getSellOrderInputCardListDTO() {
		return sellOrderInputCardListDTO;
	}

	public void setSellOrderInputCardListDTO(
			SellOrderInputCardListDTO sellOrderInputCardListDTO) {
		this.sellOrderInputCardListDTO = sellOrderInputCardListDTO;
	}

	public List<Map<String, Object>> getOrderInputCardList() {
		return orderInputCardList;
	}

	public void setOrderInputCardList(List<Map<String, Object>> orderInputCardList) {
		this.orderInputCardList = orderInputCardList;
	}

	public int getOrderInputCardList_totalRows() {
		return orderInputCardList_totalRows;
	}

	public void setOrderInputCardList_totalRows(int orderInputCardList_totalRows) {
		this.orderInputCardList_totalRows = orderInputCardList_totalRows;
	}

	public int getOrderCardList_totalRows() {
		return orderCardList_totalRows;
	}

	public void setOrderCardList_totalRows(int orderCardListTotalRows) {
		orderCardList_totalRows = orderCardListTotalRows;
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
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

	public String[] getChoose() {
		return choose;
	}

	public void setChoose(String[] choose) {
		this.choose = choose;
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

	public String getErrorJsp() {
		return errorJsp;
	}

	public void setErrorJsp(String errorJsp) {
		this.errorJsp = errorJsp;
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
     * @return the orderReceiveCardListQueryDTO
     */
    public OrderReceiveCardListQueryDTO getOrderReceiveCardListQueryDTO() {
        return orderReceiveCardListQueryDTO;
    }

    /**
     * @param orderReceiveCardListQueryDTO the orderReceiveCardListQueryDTO to set
     */

}
