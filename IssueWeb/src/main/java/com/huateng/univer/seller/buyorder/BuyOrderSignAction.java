package com.huateng.univer.seller.buyorder;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.univer.seller.sellorder.OrderBaseAction;

/**
 * @author xiehao
 * @date 2015年1月4日 上午9:38:43
 * @description	采购记名卡订单
 */
public class BuyOrderSignAction extends OrderBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	private Logger logger = Logger.getLogger(BuyOrderSignAction.class);
	
	@Override
	protected void addCondition() {
		
	}
	@SkipValidation
	public String list(){
		try{
			ListPageInit("sellOrder", sellOrderQueryDTO);
			 sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_AT_BUY_INPUT,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "listBuyOrder";
	}

	@Override
	protected void dealWithSellOrderInputDTO() {
		
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		/**
		 * 设置订单发起者
		 */
		sellOrderDTO.setFirstEntityName(getUser().getSellerName());
		
		
		
		if(productDTO!=null&&productDTO.getProductId()!=null &&!"".equals(productDTO.getProductId())){
			/***
			 * 卡面列表
			 */
			cardLayouts = productDTO.getCardLayoutDTOs();
			/**
			 * 包装列表
			 */
			//packages = productDTO.getPackages();
			/**
			 * 服务列表
			 */
			//services = productDTO.getServices();
			/**
			 * 面额列表
			 */
			//prodFaceValues = productDTO.getProdFaceValueDTO();
			
			productName=productDTO.getProductName();
			if(isEmpty(sellOrderDTO.getCardIssueFee())){
				sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
			}
			if(isEmpty(sellOrderDTO.getAnnualFee())){
				sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
			}
		}
		sellerDTO = sellOrderInputDTO.getSellerDTO();
		
		customerOrderLists = sellOrderDTO.getOrderCardList().getData();
		
		customerOrderLists_totalRows =  sellOrderDTO.getOrderCardList().getTotalRecord();
	}



	@Override
	protected void init() {
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	@Override
	protected void initOrderType() {
		
	}
	
	@SkipValidation
	public String edit(){
		try{
				ListPageInit(null, sellOrderInputDTO.getSellOrderCardListQueryDTO());
			
				ListPageInit(null, sellOrderInputDTO.getSellOrderListQueryDTO());
				
				sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
				
				sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
				
				sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				sellOrderInputDTO = (SellOrderInputDTO)sendService(
		                ConstCode.BUY_ORDER_EDIT, sellOrderInputDTO).getDetailvo();	
				dealWithSellOrderInputDTO();
		}catch(Exception e){
			this.logger.error(e.getMessage());
			logger.error(e);
		}
			return "isSignEdit";
	}
	
	
	public void validateUpdate(){
		String discountFee=sellOrderDTO.getDiscountFee()==null?"":sellOrderDTO.getDiscountFee();
		String additionalFee=sellOrderDTO.getAdditionalFee()==null?"":sellOrderDTO.getAdditionalFee();

		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasErrors()){
			edit();
			sellOrderDTO.setDiscountFee(discountFee);
			sellOrderDTO.setAdditionalFee(additionalFee);
			error_jsp = "WEB-INF/pages/univer/seller/order/orderinput/sellorder/buyordersign/buyOrderSignEdit.jsp";

		}
	}
	
}
