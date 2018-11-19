package com.huateng.univer.seller.sellorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.extremecomponents.table.core.TableConstants;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;
/***
 * 销售订单
 * @author dawn
 *
 */
public class SellOrderSignAction extends OrderBaseAction{
	
	private Logger logger = Logger.getLogger(SellOrderSignAction.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal temp = new BigDecimal("100");

    private CardholderQueryDTO cardholderQueryDTO = new CardholderQueryDTO();
    
    private List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
    
    private String[] orderListStr;
   
    private String orderType;
    
    private SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
    
    private List<BankDTO> lstBankDTOs =new ArrayList<BankDTO>();
    
	public List<BankDTO> getLstBankDTOs() {
		return lstBankDTOs;
	}
	public void setLstBankDTOs(List<BankDTO> lstBankDTOs) {
		this.lstBankDTOs = lstBankDTOs;
	}
	@Override
	protected void addCondition() {
		
	}
	@Override
	protected void init() {
		/***
		 * 订单状态为录入
		 */
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}
	
	@Override
	protected void initOrderType() {	
		//sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN);
		if(null!=orderType&&!"".equals(orderType)){
			sellOrderDTO.setOrderType(orderType);
		}
		
	}
	
	/***
	 * 初始化一些数据
	 */
	@Override
	protected void dealWithSellOrderInputDTO() {
		saleUserList = JSONArray.fromObject(sellOrderInputDTO.getSaleUserList());
		
		productDTOs = sellOrderInputDTO.getProductDTOs();
		
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		String tempMaxBalanceString="";
		if(productDTO!= null && productDTO.getMaxBalance()!= null && !productDTO.getMaxBalance().equals("")){
			//tempMaxBalanceString=Integer.parseInt(productDTO.getMaxBalance())/100;
			tempMaxBalanceString=new BigDecimal(productDTO.getMaxBalance()).divide(temp).toString();
		}
		getRequest().setAttribute("currentProdMaxBalance", tempMaxBalanceString);
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setProcessEntityName(getUser().getSellerName());
		
		if(isEmpty(sellOrderDTO.getSaleMan())&&sellOrderInputDTO.getSaleUserList().size()!=0){
			String userId=sellOrderInputDTO.getSaleUserList().get(0).getUserId();
			sellOrderDTO.setSaleMan(userId);
		}
		
		if(productDTO!=null&&productDTO.getProductId()!=null &&!"".equals(productDTO.getProductId())){
			/***
			 * 卡面列表
			 */
			cardLayouts = productDTO.getCardLayoutDTOs();
			/**
			 * 包装列表
			 */
			packages = productDTO.getPackages();
			/**
			 * 服务列表
			 */
			services = productDTO.getServices();
			/**
			 * 面额列表
			 */
			prodFaceValues = productDTO.getProdFaceValueDTO();
			
			productName=productDTO.getProductName();
			if(isEmpty(sellOrderDTO.getCardIssueFee())){
				sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
			}
			if(isEmpty(sellOrderDTO.getAnnualFee())){
				sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
			}
		}
		customerDTO = sellOrderInputDTO.getCustomerDTO();
		if("1".equals(customerDTO.getCustomerType())){
			sellOrderDTO.setPerFlag("per");
		}
		customerOrderLists = sellOrderDTO.getOrderCardList().getData();
		
		customerOrderLists_totalRows =  sellOrderDTO.getOrderCardList().getTotalRecord();
	}
	
	public void validateInsert(){
		sellOrderDTO.getOrderType();
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			add();
			error_jsp = "/WEB-INF/pages/univer/seller/order/orderinput/sellorder/signorder/sellOrderAdd.jsp";
		}
	}
	
	
	public String insert(){
		try{
			sellOrderInputDTO=(SellOrderInputDTO)sendService(ConstCode.SELL_ORDER_INSERT,
					sellOrderDTO).getDetailvo();
			if(!this.hasErrors()){
				dealWithSellOrderInputDTO();
				this.addActionMessage("新增订单成功!");
			}else{
				sellOrderInputDTO = new SellOrderInputDTO();
				add();
				return "add";
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
			return "isSignEdit";	
	}
	
	
		@SkipValidation
	    public String addOrderList() throws Exception {
		 try{
	        SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();

	        ListPageInit(null, cardholderQueryDTO);
	        
	        cardholderQueryDTO.setOrderId(sellOrderDTO.getOrderId());
	        
	        sellOrderInputDTO.setCardholderQueryDTO(cardholderQueryDTO);
	        
	        sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
	        sellOrderInputDTO.setDefaultEntityId(getUser().getEntityId());
	        sellOrderInputDTO= (SellOrderInputDTO) sendService(
	                ConstCode.SELL_ORDER_GET_CARDHOLDER, sellOrderInputDTO).getDetailvo();
	        
	        departments = sellOrderInputDTO.getCustomerDTO().getDepartmentList();
	        
	        this.getRequest().setAttribute("list", sellOrderInputDTO.getCardholderList().getData());
	        
	        this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,
	        		sellOrderInputDTO.getCardholderList().getTotalRecord());
		 }catch(Exception e){
			 logger.error(e.getMessage());
		 }
	        return "addCardlist";
	    }
	
		@SkipValidation
		public String edit(){
			try{
					ListPageInit("customerOrderLists", sellOrderInputDTO.getSellOrderCardListQueryDTO());
				
					ListPageInit(null, sellOrderInputDTO.getSellOrderListQueryDTO());
					
					sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
					
					sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderType(sellOrderDTO.getOrderType());
					
					sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
					
					sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
					sellOrderInputDTO = (SellOrderInputDTO)sendService(
			                ConstCode.SELL_ORDER_EDIT, sellOrderInputDTO).getDetailvo();	
					dealWithSellOrderInputDTO();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
				return "isSignEdit";
		}
		
		
		@SkipValidation
	    public String insertOrderList()  {
		 try{
			 /*
			  * 注：暂用defaultEntityId
			  * defaultEntityId的机构号在BaseAction中sendService方法中会重新覆盖掉，不能用这个字段
			 String defaultEntityIdString=sellOrderInputDTO.getDefaultEntityId();
			 sellOrderInputDTO.setDefaultEntityId(SystemInfo
						.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM));*/
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO.setOrderListStr(orderListStr);
			 
			ListPageInit(null, sellOrderInputDTO.getSellOrderCardListQueryDTO());
			
			ListPageInit(null, sellOrderInputDTO.getSellOrderListQueryDTO());
			
			sellOrderInputDTO=(SellOrderInputDTO)sendService(
	                ConstCode.SELL_ORDER_INSERT_CARDHOLDER, sellOrderInputDTO).getDetailvo();
			//sellOrderInputDTO.setDefaultEntityId(defaultEntityIdString);
			if(!this.hasErrors()){
				getRequest().setAttribute("sucessMessage", "添加订单明细信息成功!");
			}else{
				return 	addOrderList();
			}
		 }catch(Exception e){
			 logger.error(e.getMessage());
		 }
	        return "addSucess";
	    }
		@SkipValidation
		public String deleteOrderList(){
			try{
				sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
				sellOrderListDTO.setOrderListIdStr(orderListStr);
			sendService(
		                ConstCode.SELL_ORDER_DELETE_CARDHOLDER, sellOrderListDTO).getDetailvo();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			return  edit();
		}
		
	
		
		public void  validateUpdate(){
			if(logger.isDebugEnabled()){
				logger.debug(this.getFieldErrors());
			}
			if(this.hasFieldErrors()){
				edit();
				error_jsp="/WEB-INF/pages/univer/seller/order/orderinput/sellorder/signorder/sellOrderSignEdit.jsp";
			}
		}
		
	public CardholderQueryDTO getCardholderQueryDTO() {
		return cardholderQueryDTO;
	}
	public void setCardholderQueryDTO(CardholderQueryDTO cardholderQueryDTO) {
		this.cardholderQueryDTO = cardholderQueryDTO;
	}
	public List<DepartmentDTO> getDepartments() {
		return departments;
	}
	public void setDepartments(List<DepartmentDTO> departments) {
		this.departments = departments;
	}
	public String[] getOrderListStr() {
		return orderListStr;
	}
	public void setOrderListStr(String[] orderListStr) {
		this.orderListStr = orderListStr;
	}
	public SellOrderListDTO getSellOrderListDTO() {
		return sellOrderListDTO;
	}
	public void setSellOrderListDTO(SellOrderListDTO sellOrderListDTO) {
		this.sellOrderListDTO = sellOrderListDTO;
	}
	public BigDecimal getTemp() {
		return temp;
	}
	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
