package com.huateng.univer.seller.sellorderretail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.extremecomponents.table.core.TableConstants;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.seller.sellorder.OrderBaseAction;

public class SellOrderRetailUnsignAction extends OrderBaseAction{
	private Logger logger = Logger.getLogger(SellOrderRetailUnsignAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	private SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
	
	
	private SellerContractDTO sellerContractDTO = new SellerContractDTO();

	private String[] orderListStr;
	
	private String faceValueId;
	
	private List<BankDTO> lstBankDTOs =new ArrayList<BankDTO>();
	
	public List<BankDTO> getLstBankDTOs() {
		return lstBankDTOs;
	}

	public void setLstBankDTOs(List<BankDTO> lstBankDTOs) {
		this.lstBankDTOs = lstBankDTOs;
	}

	@Override
	protected void addCondition() {
		actionName="SellOrderRetailUnsignAction";
	}

	@Override
	protected void init() {
		
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	@Override
	protected void dealWithSellOrderInputDTO() {
		saleUserList = JSONArray.fromObject(sellOrderInputDTO.getSaleUserList());
		
		
		productDTOs = sellOrderInputDTO.getProductDTOs();
		
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setProcessEntityName(getUser().getSellerName());
		
		if(isEmpty(sellOrderDTO.getSaleMan())&&sellOrderInputDTO.getSaleUserList().size()!=0){
			String userId=sellOrderInputDTO.getSaleUserList().get(0).getUserId();
			sellOrderDTO.setSaleMan(userId);
		}
		
		if(productDTO!=null&&productDTO.getProductId()!=null &&!"".equals(productDTO.getProductId())){
			/**
			 * 服务列表
			 */
			services = productDTO.getServices();
			/**
			 * 包装列表
			 */
			packages = productDTO.getPackages();
			
			productName=productDTO.getProductName();
			if(isEmpty(sellOrderDTO.getCardIssueFee())){
				sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
			}
			if(isEmpty(sellOrderDTO.getAnnualFee())){
				sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
			}
		}
		customerDTO = sellOrderInputDTO.getCustomerDTO();	
		customerOrderLists = sellOrderDTO.getOrderList().getData();
		customerOrderLists_totalRows =  sellOrderDTO.getOrderList().getTotalRecord();
		lstBankDTOs=sellOrderDTO.getLstBankDTO();
	}

	@Override
	protected void initOrderType() {
		sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN);
		//散户客户
		CustomerDTO customerDTO=(CustomerDTO)sendService(ConstCode.CUSTOMER_SELECT_RETAIL, this.getUser().getEntityId()).getDetailvo();
		sellOrderDTO.setFirstEntityId(customerDTO.getEntityId());
		//sellOrderDTO.setFirstEntityId("1");
	}
	
	public void validateInsert(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			error_jsp="WEB-INF/pages/univer/seller/order/orderinput/sellorderretail/unsignorder/sellOrderRetailUnsignAdd.jsp";
			add();
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
			this.logger.error(e.getMessage());
		}
		return "isUnSignEdit";	
	}
	
	
	public void  validateUpdate(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			edit();
			error_jsp="WEB-INF/pages/univer/seller/order/orderinput/sellorderretail/unsignorder/sellOrderRetailUnsignEdit.jsp";
		}
	}
	
	@SkipValidation
	public String edit(){
		try{
				ListPageInit("customerOrderLists", sellOrderInputDTO.getSellOrderListQueryDTO());
				
				sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
				
				sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
				
				sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				sellOrderInputDTO = (SellOrderInputDTO)sendService(
		                ConstCode.SELL_ORDER_EDIT, sellOrderInputDTO).getDetailvo();	
				dealWithSellOrderInputDTO();
			actionName = "sellOrderRetailUnsignAction";
			actionMethodName = "edit";
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
			return "isUnSignEdit";
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@SkipValidation
    public String addOrderList() throws Exception {
	 try{
		 	SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		 	sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		 	
	        sellOrderInputDTO = (SellOrderInputDTO)sendService(ConstCode.SELL_ORDER_UNSIGN_GET_STOCK, sellOrderInputDTO).getDetailvo();
	        if(sellOrderInputDTO==null){
	        	 this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,0);
	        }
	        ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
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
	        //获取产品最大余额
	        if(productDTO.getMaxBalance() != null){
	        	sellOrderListDTO.getProductDTO().setMaxBalance(productDTO.getMaxBalance());
	        }
	        //设置卡的有效日期
	        if(isEmpty(sellOrderListDTO.getCardIssueFee())){
	        	if(sellOrderInputDTO.getProductStocks().getTotalRecord()>0){
	        		Map map=	(Map)(sellOrderInputDTO.getProductStocks().getData().get(0));
	        		String validityPeriod = (String)map.get("validityPeriod");
	        		sellOrderListDTO.setValidityPeriod(validityPeriod);
	        	}
	        }
	      
	 }catch(Exception e){
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
				return this.addOrderList();
			}
		 	sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
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
				cardLayouts = productDTO.getCardLayoutDTOs();
				packages = productDTO.getPackages();
				prodFaceValues = productDTO.getProdFaceValueDTO();
				//获取产品最大余额
		        if(productDTO.getMaxBalance() != null){
		        	sellOrderListDTO.getProductDTO().setMaxBalance(productDTO.getMaxBalance());
		        }
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "editorderlist";
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
	
	
	public SellOrderListDTO getSellOrderListDTO() {
		return sellOrderListDTO;
	}

	public void setSellOrderListDTO(SellOrderListDTO sellOrderListDTO) {
		this.sellOrderListDTO = sellOrderListDTO;
	}

	public SellerContractDTO getSellerContractDTO() {
		return sellerContractDTO;
	}

	public void setSellerContractDTO(SellerContractDTO sellerContractDTO) {
		this.sellerContractDTO = sellerContractDTO;
	}

	public String[] getOrderListStr() {
		return orderListStr;
	}

	public void setOrderListStr(String[] orderListStr) {
		this.orderListStr = orderListStr;
	}

	public String getFaceValueId() {
		return faceValueId;
	}

	public void setFaceValueId(String faceValueId) {
		this.faceValueId = faceValueId;
	}

	
	
	
}
