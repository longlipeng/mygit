package com.huateng.univer.seller.sellorder;

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
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.constant.OrderConst;

/**
 * @author Yifeng.Shi
 * @since 2011-11-23
 * 换卡订单Action
 */
public class ChangeCardOrderAction extends OrderBaseAction{
	private Logger logger = Logger.getLogger(ChangeCardOrderAction.class);
	private static final long serialVersionUID = -6167017233446080748L;
	
	private SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
	
	private SellerContractDTO sellerContractDTO = new SellerContractDTO();

	private String[] orderListStr;
	
	private String jsonString;
	
	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	private SellOrderOrigCardListDTO sellOrderOrigCardListDTO=new SellOrderOrigCardListDTO();
	
	private String faceValueId;
	
	private String[] origCardListStr;
	
	private List<BankDTO> lstBankDTOs =new ArrayList<BankDTO>();
	
	public List<BankDTO> getLstBankDTOs() {
		return lstBankDTOs;
	}

	public void setLstBankDTOs(List<BankDTO> lstBankDTOs) {
		this.lstBankDTOs = lstBankDTOs;
	}
	
	@Override
	protected void init() {
		/*订单状态：草稿*/
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}
	protected void queryInit() {
		actionName= "changeCardOrderAction";
		actionMethodName="view";
	}

	@Override
	protected void initOrderType() {
		/*订单类型：换卡订单*/
		sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD);
	}
	
	@Override
	protected void addCondition() {
	}

	@Override
	protected void dealWithSellOrderInputDTO() {
 		saleUserList = JSONArray.fromObject(sellOrderInputDTO.getSaleUserList());
 		productDTOs = sellOrderInputDTO.getProductDTOs();	
// 		//去除记名个性化卡
// 		List<ProductDTO> products = new ArrayList<ProductDTO>();
// 		products = sellOrderInputDTO.getProductDTOs();		
//		for(ProductDTO p :products){
//			if(!"1".equals(p.getOnymousStat())){
//				productDTOs.add(p);
//			}
//		}
		
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		sellOrderDTO.setProductName(sellOrderInputDTO.getSellOrderDTO().getProductName());
		sellOrderDTO.setProductId(sellOrderInputDTO.getSellOrderDTO().getProductId());
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
		origCardLists=sellOrderDTO.getOrigCardList().getData();
		origCardLists_totalRows =sellOrderDTO.getOrigCardList().getTotalRecord();
		lstBankDTOs=sellOrderDTO.getLstBankDTO();
	}
	
	/**
	 *新增换卡订单 
	 */
	public String insert(){
		try {
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
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		
		return "edit";
	}
	public void validateInsert(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			error_jsp="WEB-INF/pages/univer/seller/order/orderinput/sellorder/changeCardOrder/changeCardOrderAdd.jsp";
			add();
		}
	}
	/**
	 *转向换卡订单编辑 
	 */
	@SkipValidation
	public String edit() {
		try {
			ListPageInit("origCardLists", sellOrderInputDTO.getSellOrderOrigCardListQueryDTO());
			ListPageInit("customerOrderLists", sellOrderInputDTO.getSellOrderListQueryDTO());
			sellOrderInputDTO.getSellOrderOrigCardListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
			sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO = (SellOrderInputDTO)sendService(
	                ConstCode.CHANGE_CARD_ORDER_EDIT, sellOrderInputDTO).getDetailvo();	
			dealWithSellOrderInputDTO();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "edit";
	}
	
	public void  validateUpdate(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			edit();
			error_jsp="WEB-INF/pages/univer/seller/order/orderinput/sellorder/changeCardOrder/changeCardOrderEdit.jsp";
		}
	}
	/**
	 * 换卡订单详细信息查看
	 * */
	@SkipValidation
	public String view(){
		try{
		queryInit();
	    ListPageInit("origCardLists", sellOrderOrigCardListQueryDTO);	   
	    ListPageInit("customerOrderLists", sellOrderListQueryDTO);
	    ListPageInit("orderFlow", sellOrderFlowQueryDTO);
		  SellOrderCompositeDTO sellOrderCompositeDTO = new SellOrderCompositeDTO();
		    
	    /*订单*/
	    sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);
	    
	    /* 订单明细 */
	    sellOrderCompositeDTO.setSellOrderListQueryDTO(sellOrderListQueryDTO);
	    sellOrderListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
	    
	    /*订单卡明细*/
	    sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
	    sellOrderCardListQueryDTO.setOrderType(sellOrderDTO.getOrderType());
	    sellOrderCompositeDTO.setSellOrderCardListQueryDTO(sellOrderCardListQueryDTO);
	    
	    sellOrderOrigCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
	    sellOrderOrigCardListQueryDTO.setOrderType(sellOrderDTO.getOrderType());
	    
	    sellOrderCompositeDTO.setSellOrderOrigCardListQueryDTO(sellOrderOrigCardListQueryDTO);
	    /*订单流程节点*/
	    sellOrderFlowQueryDTO.setOrderId(sellOrderDTO.getOrderId());
	    sellOrderCompositeDTO.setSellOrderFlowQueryDTO(sellOrderFlowQueryDTO);
	   
	    sellOrderCompositeDTO = (SellOrderCompositeDTO) sendService(
	            ConstCode.FRTCODE_SERVICE_SELL_ORDER_GET, sellOrderCompositeDTO).getDetailvo();
	    sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();
	    if(sellOrderCompositeDTO.getSellerDTO()!=null){
	    	sellerDTO = sellOrderCompositeDTO.getSellerDTO();
	    }
	    if(sellOrderCompositeDTO.getCustomerDTO()!=null){
	    	customerDTO = sellOrderCompositeDTO.getCustomerDTO();
	    }
	    getRequest().setAttribute("orderlistList", sellOrderCompositeDTO.getSellOrderList().getData());
	    getRequest().setAttribute("orderList_" + TableConstants.TOTAL_ROWS,
	            sellOrderCompositeDTO.getSellOrderList().getTotalRecord());
	    getRequest().setAttribute("origCardList", sellOrderCompositeDTO.getSellOrderOrigCardList().getData());
	    getRequest().setAttribute("origCardList_" + TableConstants.TOTAL_ROWS,
	            sellOrderCompositeDTO.getSellOrderList().getTotalRecord());
	    getRequest().setAttribute("orderflowList", sellOrderCompositeDTO.getSellOrderFlowList().getData());
	    getRequest().setAttribute("orderFlow_" + TableConstants.TOTAL_ROWS,
	            sellOrderCompositeDTO.getSellOrderFlowList().getTotalRecord());
	
	    getRequest().setAttribute("orderCardList", sellOrderCompositeDTO.getSellOrderCardList().getData());
	    getRequest().setAttribute("orderCard_" + TableConstants.TOTAL_ROWS,
	           sellOrderCompositeDTO.getSellOrderCardList().getTotalRecord());
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "view";
	}
	@SkipValidation
	public String deleteOrigCard(){
		sellOrderOrigCardListDTO.setOrigcardListIds(origCardListStr);
		sendService(
	            ConstCode.CHANGE_CARD_ORDER_ORIG_CARD_DEL,sellOrderOrigCardListDTO).getDetailvo();
		sellOrderDTO.setOrderId(sellOrderOrigCardListQueryDTO.getOrderId());
		addActionMessage("删除成功");
		return  edit();
	}
	@SkipValidation
	public String insertOrderList(){
		try {
			sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
			sellOrderListDTO.setTotalPrice(String.valueOf(Double.parseDouble(sellOrderListDTO.getFaceValue())*Double.parseDouble(sellOrderListDTO.getCardAmount())*100));
			sellOrderListDTO.setTotalPrice(String.valueOf(Double.parseDouble(sellOrderListDTO.getFaceValue())*Double.parseDouble(sellOrderListDTO.getCardAmount())));
			sendService(ConstCode.CHANGE_CARD_ORDER_INSERT_ORDER_LIST, sellOrderListDTO).getDetailvo();
			if(!this.hasErrors()){
				getRequest().setAttribute("sucessMessage", "添加订单明细信息成功!");
			}else{
				return 	addOrderList();
			}
		} catch (Exception e) {
			
		}
		return "addSucess";	
	}
	@SkipValidation
	public String deleteOrderList(){
		try{
			sellOrderListDTO.setOrderListIdStr(orderListStr);
			sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
			sendService(ConstCode.CHANGE_CARD_ORDER_DELETE_ORDER_LIST, sellOrderListDTO).getDetailvo();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return  edit();
	}
	@SkipValidation
	public String addOrigCard(){
		return "addOrigCard";
	}
	/***
	 * 编辑订单明细
	 */
	@SkipValidation
	public String editOrderList(){
		try{
			sellOrderListDTO.setTotalPrice(String.valueOf(Integer.parseInt(sellOrderListDTO.getFaceValue())*Integer.parseInt(sellOrderListDTO.getCardAmount())*100));
			sellOrderListDTO =(SellOrderListDTO) sendService(ConstCode.CHANGE_CARD_ORDER_EDIT_ORDER_LIST, sellOrderListDTO).getDetailvo();
			/***
			 * 这里需要将面额*100
			 */
			faceValueId =new BigDecimal(sellOrderListDTO.getFaceValue()).multiply(new BigDecimal(100)).toString();
			
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
		return "editOrderList";
	}
	/**
	 * 更新订单明细
	 */
	@SkipValidation
	public String updateOrderList(){
		try{
			sendService(ConstCode.CHANGE_CARD_ORDER_UPDATE_ORDER_LIST, sellOrderListDTO).getDetailvo();
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
	public String insertOrigCard(){
		try {
			sellOrderDTO.setCardNo(sellOrderOrigCardListQueryDTO.getCardNo());
			sellOrderDTO.setOrderId(sellOrderOrigCardListQueryDTO.getOrderId());
			sendService(
	                ConstCode.CHANGE_CARD_ORDER_ORIG_CARD_INSERT, sellOrderDTO).getDetailvo();	
			if(!this.hasErrors()){
				getRequest().setAttribute("sucessMessage", "添加原卡信息成功!");
			}else{
				return 	addOrigCard();
			}
		} catch (Exception e) {
			 this.logger.error(e.getMessage());
		}
		
		return "addSucess";
	}
	
	@SkipValidation
	public String addOrderList(){
		try {
			SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		 	sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		 	
	        sellOrderInputDTO = (SellOrderInputDTO)sendService(ConstCode.SELL_ORDER_CHANGECARD_GET_STOCK, sellOrderInputDTO).getDetailvo();
	        if(sellOrderInputDTO==null){
	       	 this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,0);
	        }
	        ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
//	        productDTOs=sellOrderInputDTO.getProductDTOs();
	        this.getRequest().setAttribute("list", sellOrderInputDTO.getProductStocks().getData());
	        this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,
	        		sellOrderInputDTO.getProductStocks().getTotalRecord());
	        cardLayouts = productDTO.getCardLayoutDTOs();
	        if (cardLayouts == null){
	            cardLayouts = new ArrayList<CardLayoutDTO>();
	        }
	        prodFaceValues = productDTO.getProdFaceValueDTO();
	        services = productDTO.getServices();
	        if (prodFaceValues == null){
	            prodFaceValues = new ArrayList<ProdFaceValueDTO>();
	        }
	        sellOrderListDTO.setProductId(productDTO.getProductId());
	        sellOrderListDTO.setProductName(productDTO.getProductName());
	        //卡产品类型（记名、不记名）暂存字段
	        sellOrderListDTO.setCallBack(productDTO.getOnymousStat());
	        // 初始化面额值
	        if (prodFaceValues.size() > 0) {
	            sellOrderListDTO.setFaceValue(new BigDecimal(prodFaceValues.get(0).getFaceValue()).divide(new BigDecimal("100")).toString());
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
	        		Map map=(Map)(sellOrderInputDTO.getProductStocks().getData().get(0));
	        		String validityPeriod = (String)map.get("cardValidDate");
	        		sellOrderListDTO.setValidityPeriod(validityPeriod);
	        	}
	        }
	        JSONArray jo = JSONArray.fromObject(productDTO);
	        
	        jsonString= jo.toString();
	        
	        logger.info(jsonString);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		
		return "addOrderList";
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

	public String[] getOrigCardListStr() {
		return origCardListStr;
	}

	public void setOrigCardListStr(String[] origCardListStr) {
		this.origCardListStr = origCardListStr;
	}

	public SellOrderOrigCardListDTO getSellOrderOrigCardListDTO() {
		return sellOrderOrigCardListDTO;
	}

	public void setSellOrderOrigCardListDTO(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO) {
		this.sellOrderOrigCardListDTO = sellOrderOrigCardListDTO;
	}
}
