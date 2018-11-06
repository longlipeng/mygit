package com.huateng.univer.seller.buyorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.extremecomponents.table.core.TableConstants;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.EntityDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.seller.sellorder.OrderBaseAction;

/**
 * @author xiehao
 * @date 2015年1月4日 上午9:38:22
 * @description	采购匿名卡订单
 */
public class BuyOrderUnSignAction extends OrderBaseAction {
	private Logger logger = Logger.getLogger(BuyOrderUnSignAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
	
	private BigDecimal temp = new BigDecimal("100");
	
	private String[] orderListStr;
	
	
	private List<EntityDTO> entityDTOList = new ArrayList<EntityDTO>();
	
	
	private List<EntityStockDTO> entityStockList = new ArrayList<EntityStockDTO>();
	
	private List<ProductDTO> productDTOList=new ArrayList<ProductDTO>();

	private String faceValueId;
	
	public String getFaceValueId() {
		return faceValueId;
	}

	public void setFaceValueId(String faceValueId) {
		this.faceValueId = faceValueId;
	}

	public List<ProductDTO> getProductDTOList() {
		return productDTOList;
	}

	public void setProductDTOList(List<ProductDTO> productDTOList) {
		this.productDTOList = productDTOList;
	}

	public BigDecimal getTemp() {
		return temp;
	}

	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}
	
	@Override
	protected void addCondition() {
		
		
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
		customerOrderLists = sellOrderDTO.getOrderList().getData();
		customerOrderLists_totalRows =  sellOrderDTO.getOrderList().getTotalRecord();
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
				sellOrderInputDTO.getProductDTO().setAnnualFee(Integer.toString((Integer.parseInt(sellOrderInputDTO.getProductDTO().getAnnualFee())/100)));
				dealWithSellOrderInputDTO();
		}catch(Exception e){
			this.logger.error(e.getMessage());
			logger.error(e);
		}
			return "isUnsignEdit";
	}
	
	/**
	 * 对于采购匿名订单
	 * 可以添加相关的记录
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
    public String addOrderList() throws Exception {
		try{
		 	SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		 	sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		 	
	        sellOrderInputDTO = (SellOrderInputDTO)sendService(ConstCode.SELL_ORDER_UNSIGN_GET_STOCK, sellOrderInputDTO).getDetailvo();
	        if(sellOrderInputDTO==null){
	        	 this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,0);
	        }
	        if(sellOrderInputDTO!=null){
	        	ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
	        	this.getRequest().setAttribute("productType",productDTO.getOnymousStat() );
	       
		        this.getRequest().setAttribute("list", sellOrderInputDTO.getProductStocks().getData());
		      
		        this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,
		        		sellOrderInputDTO.getProductStocks().getTotalRecord());
		        cardLayouts = productDTO.getCardLayoutDTOs();
		        if (cardLayouts == null){
		            cardLayouts = new ArrayList<CardLayoutDTO>();
		        }
		        prodFaceValues = productDTO.getProdFaceValueDTO();
		        if (prodFaceValues == null){
		            prodFaceValues = new ArrayList<ProdFaceValueDTO>();
		        }
		        // 初始化面额值
		        if (prodFaceValues.size() > 0) {
		            sellOrderListDTO.setFaceValue(new BigDecimal(prodFaceValues.get(0).getFaceValue()).divide(new BigDecimal("100")).toString());
		        }
		        // 初始化包装费
		        packages = productDTO.getPackages();
		        if (packages == null)
		            packages = new ArrayList<PackageDTO>();
		        
		        if (sellOrderListDTO.getCardIssueFee() == null) {
		            sellOrderListDTO.setCardIssueFee(productDTO.getCardFee());
		        }
		        // 初始化卡面信息
		        if (cardLayouts != null && cardLayouts.size() > 0) {
		            if (sellOrderListDTO.getCardLayoutId() == null)
		            	sellOrderListDTO.setCardLayoutId(cardLayouts.get(0).getCardLayoutId());
		        }
			
		        //设置卡的有效日期
		        if(isEmpty(sellOrderListDTO.getCardIssueFee())){
		        	if(sellOrderInputDTO.getProductStocks().getTotalRecord()>0){
		        		Map map=	(Map)(sellOrderInputDTO.getProductStocks().getData().get(0));
		        		String validityPeriod = (String)map.get("validityPeriod");
		        		sellOrderListDTO.setValidityPeriod(validityPeriod);
		        	}
		        }
	        }
	      
	 } catch(Exception e){
		 this.logger.error(e.getMessage());
	 }
	  return "addorderlist";
    }
	
	@SkipValidation
    public String insertOrderList()  {
	 try{
		 Integer cardTotalInteger=Integer
			.parseInt((SystemInfo
					.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
			if(Integer.parseInt(sellOrderListDTO.getCardAmount())>cardTotalInteger){
				this.addActionError("订单卡数量不能超过系统既定最大值:"+cardTotalInteger);
				return addOrderList();
			}
		 	sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
		 	sellOrderListDTO.setRealAmount("0");
		   sendService(ConstCode.SELL_ORDER_UNSIGN_INSERT_ORDER_LIST, sellOrderListDTO).getDetailvo();
		if(!this.hasErrors()){
			getRequest().setAttribute("sucessMessage", "添加订单明细信息成功!");
		}else{
			return 	addOrderList();
		}
	 }catch(Exception e){
		 this.logger.error(e.getMessage());
	 }
        return "addSucess";
    }
	@SkipValidation
	public String deleteOrderList(){
		try{
			sellOrderListDTO.setOrderListIdStr(orderListStr);
			sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
		sendService(ConstCode.SELL_ORDER_UNSIGN_DELETE_ORDER_LIST, sellOrderListDTO).getDetailvo();
		if(!this.hasErrors()){
			this.addActionMessage("删除明细信息成功!");
		}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return  edit();
	}

	
	/***
	 * 编辑订单明细
	 */
	@SkipValidation
	public String editOrderList(){
		try{
			sellOrderListDTO =(SellOrderListDTO) sendService(ConstCode.SELL_ORDER_UNSIGN_EDIT_ORDER_LIST, sellOrderListDTO).getDetailvo();
			/***
			 * 这里需要将面额*100
			 */
			faceValueId =new BigDecimal( sellOrderListDTO.getFaceValue()).multiply(new BigDecimal(100)).toString();
			if(sellOrderListDTO!=null){
				ProductDTO productDTO = sellOrderListDTO.getProductDTO();
				this.getRequest().setAttribute("productType",productDTO.getOnymousStat() );
				cardLayouts = productDTO.getCardLayoutDTOs();
				packages = productDTO.getPackages();
				prodFaceValues = productDTO.getProdFaceValueDTO();
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "editorderlist";
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
				return "addBuyOrder";
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
			return edit();	
	}
	
	
	
	/**
	 * 更新订单明细
	 */
	@SkipValidation
	public String updateOrderList(){
		try{
			 Integer cardTotalInteger=Integer
				.parseInt((SystemInfo
						.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
				if(Integer.parseInt(sellOrderListDTO.getCardAmount())>cardTotalInteger){
					this.addActionError("订单卡数量不能超过系统既定最大值:"+cardTotalInteger);
					return this.editOrderList();
				}
			sendService(ConstCode.SELL_ORDER_UNSIGN_UPDATE_ORDER_LIST, sellOrderListDTO).getDetailvo();
			if(!this.hasErrors()){
				getRequest().setAttribute("sucessMessage", "修改明细信息成功!");
				return "addSucess";
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return editOrderList();
	}
	
	
	
	@SkipValidation
	public String addBuyOrder(){
//		System.out.println("进入添加订单");
		sellerContractQueryDTO.setContractBuyer(getUser().getEntityId());
		List<SellerContractDTO>  sellerContractDTOList= (List<SellerContractDTO>)sendService(ConstCode.CONTRACT_SERVICE_INQUERY_MASTERPALTE,
				 sellerContractQueryDTO).getDetailvo();
		if (null != sellerContractDTOList && sellerContractDTOList.size() > 0) {
			String expiryDate = sellerContractDTOList.get(0).getExpiryDate();
			String sysDate = DateUtil.getCurrentDateStr();
//			System.out.println("合同失效日期:" + expiryDate);
//			System.out.println("当前系统日期:" + DateUtil.getCurrentDateStr());
			if(Integer.parseInt(sysDate) > Integer.parseInt(expiryDate)) {
				this.addActionError("当前机构与上级发行机构合同已过期!");
				return list();
			}
		}
		
		
		try{
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			
			sellOrderInputDTO=(SellOrderInputDTO)sendService(ConstCode.SELL_ORDER_INIT_BUY_ORDER,sellOrderInputDTO).getDetailvo();
			
		
			if(!this.hasErrors()){
				sellOrderDTO  = sellOrderInputDTO.getSellOrderDTO();
			}
			/**
			 * 订单日期今天
			 */
			if(isEmpty(sellOrderDTO.getOrderDate())){
				sellOrderDTO.setOrderDate(getDateFormat(new Date()));
			}
			/**
			 * 发票日期今天
			 */
			if(isEmpty(sellOrderDTO.getInvoiceDate())){
				sellOrderDTO.setInvoiceDate(getDateFormat(new Date()));
			}
			
			/**
			 * 设置订单发起者
			 */
			sellOrderDTO.setFirstEntityId(getUser().getEntityId());
			sellOrderDTO.setFirstEntityName(getUser().getSellerName());
			
			entityDTOList = sellOrderInputDTO.getEntityDTOList();
			sellerDTO = sellOrderInputDTO.getSellerDTO();
			/**
			 * 设置支付方式
			 */
			sellOrderDTO.setDiscountFee("0");
			sellOrderDTO.setAdditionalFee("0");
			sellOrderDTO.setTotalPrice("0");
			sellOrderDTO.setPaymentTerm(sellerDTO.getPaymentTerm());
			sellOrderDTO.setPaymentDelay(sellerDTO.getPaymentDelay());
			sellOrderDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			sellOrderDTO.setOrderSource(OrderConst.ORDER_SOURCE_SYSTEM_INPUT);
			sellOrderDTO.setCardQuantity("0");
			
	
		}catch(Exception e){
			this.logger.error(e.getMessage());
			
		}
		return "addBuyOrder";
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
			 sellOrderQueryDTO.setContractState(initContractState());
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "listBuyOrder";
	}
	
	@SkipValidation
	@SuppressWarnings("unchecked")
	public String getProductInfoByContract(){
		try{
			entityStockList=(List<EntityStockDTO>)sendService(ConstCode.SELL_ORDER_GET_STOCK_BY_ENTITY_ID,
														getUser().getEntityId()).getDetailvo();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		
		return "productInfolist";
	}
	
	@SkipValidation
	@SuppressWarnings("unchecked")
	public String loadProductInfoByContract(){
		try{
			productDTOList=(List<ProductDTO>)sendService(ConstCode.BUY_ORDER_GET_PRODUCT_BY_CONTRACT,
														getUser().getEntityId()).getDetailvo();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		
		return "BuyOrderUnsignProdInfolist";
	}
	
	@Override
	protected void init() {
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	@Override
	protected void initOrderType() {
		
	}
	
	public void validateInsert(){
		String discountFee=sellOrderDTO.getDiscountFee()==null?"":sellOrderDTO.getDiscountFee();
		String additionalFee=sellOrderDTO.getAdditionalFee()==null?"":sellOrderDTO.getAdditionalFee();
		if(this.hasErrors()){
			addBuyOrder();
			sellOrderDTO.setDiscountFee(discountFee);
			sellOrderDTO.setAdditionalFee(additionalFee);
			error_jsp = "WEB-INF/pages/univer/seller/order/orderinput/sellorder/buyorderunsign/buyOrderUnsignAdd.jsp";
		}
	}
	
	public void validateUpdate(){
		String discountFee=sellOrderDTO.getDiscountFee()==null?"":sellOrderDTO.getDiscountFee();
		String additionalFee=sellOrderDTO.getAdditionalFee()==null?"":sellOrderDTO.getAdditionalFee();
		if(this.hasErrors()){
			logger.info(this.getFieldErrors());
			edit();
			sellOrderDTO.setDiscountFee(discountFee);
			sellOrderDTO.setAdditionalFee(additionalFee);
			error_jsp = "WEB-INF/pages/univer/seller/order/orderinput/sellorder/buyorderunsign/buyOrderUnsignEdit.jsp";
		}
	}

	public SellOrderListDTO getSellOrderListDTO() {
		return sellOrderListDTO;
	}

	public void setSellOrderListDTO(SellOrderListDTO sellOrderListDTO) {
		this.sellOrderListDTO = sellOrderListDTO;
	}

	public String[] getOrderListStr() {
		return orderListStr;
	}

	public void setOrderListStr(String[] orderListStr) {
		this.orderListStr = orderListStr;
	}

	public List<EntityDTO> getEntityDTOList() {
		return entityDTOList;
	}

	public void setEntityDTOList(List<EntityDTO> entityDTOList) {
		this.entityDTOList = entityDTOList;
	}

	public List<EntityStockDTO> getEntityStockList() {
		return entityStockList;
	}

	public void setEntityStockList(List<EntityStockDTO> entityStockList) {
		this.entityStockList = entityStockList;
	}
	
	

}
