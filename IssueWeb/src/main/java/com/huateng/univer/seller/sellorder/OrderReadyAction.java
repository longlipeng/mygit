package com.huateng.univer.seller.sellorder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

/**
 * 
 * @author dawn
 * 
 */
public class OrderReadyAction extends OrderBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrderReadyAction.class);
	private String message;

	private OrderReadyDTO orderReadyDTO = new OrderReadyDTO();

	private SellBuyOrderDTO sellBuyOrderDTO = new SellBuyOrderDTO();
	private List<SellerContractDTO> sellerContractDTOs = new ArrayList<SellerContractDTO>();
	private List<SellOrderListDTO> sellOrderListDTOs = new ArrayList<SellOrderListDTO>();
	private ProductDTO productDTO = new ProductDTO();

	private PageDataDTO pageDataDTO;

	private List<String> orderIdList = new ArrayList<String>();
	private String[] cardNoArray;
	private int sellOrderList_totalRows = 0;

	private String[] cardLayoutIdList;
	private String[] faceValueTypeList;
	private String[] faceValueList;
	private String[] cardAmountList;
	
	private SellOrderOrigCardListDTO sellOrderOrigCardListDTO=new SellOrderOrigCardListDTO();
	private String[] origCardListStr;
	@Override
	protected void addCondition() {

	}

	@Override
	protected void dealWithSellOrderInputDTO() {

	}

	@Override
	public String edit() {
		return null;
	}

	@Override
	protected void init() {
		actionName = "orderReadyAction";
		actionMethodName = "button";
	}

	@Override
	protected void initOrderType() {

	}

	public String list() {
		try {
			ListPageInit("sellOrder", sellOrderQueryDTO);
			sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
			/***
			 * 按订单ID的倒序排序
			 */
			if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
				sellOrderQueryDTO.setSort("desc");
				sellOrderQueryDTO.setSortFieldName("orderId");
			}
			PageDataDTO result = (PageDataDTO) sendService(
					ConstCode.SELL_ORDER_QUERY_AT_READY_OR_HANDOUT,
					sellOrderQueryDTO).getDetailvo();
			sellOrders = result.getData();
			sellOrder_totalRows = result.getTotalRecord();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public String button() {
		try {
			view();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "button";
	}

	public String submitConfirm() {
		message = "提交";
		operation = "submit";
		return button();
	}

	public String sendBackConfirm() {
		message = "退回";
		operation = "sendback";
		return button();
	}

	public String submit() {
		try {
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_READY, sellOrderDTO)
					.getDetailvo();
			if (!this.hasErrors()) {
				this.addActionMessage("提交订单成功!");
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();

	}

	public String sendback() {
		try {
			sendService(ConstCode.SELL_ORDER_SENDBACK_AT_READY, sellOrderDTO)
					.getDetailvo();
			if (!this.hasErrors()) {
				this.addActionMessage("退回订单成功!");
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	public String deleteRecord() {
		try {
			sendService(ConstCode.SELL_ORDER_READY_DELETE_RECORD, orderReadyDTO)
					.getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return ready();
	}

	public String ready() {
		try {			
			if (this.hasErrors()) {
				return list();
			}
			view();
		} catch (Exception e) {
			this.logger.error(e.getMessage());

		}
		actionMethodName = "ready";
		return "ready";
	}
	public String readyChangeCard() {
		try {
			if (this.hasErrors()) {
				return list();
			}
			view();
		} catch (Exception e) {
			this.logger.error(e.getMessage());

		}
		actionMethodName = "readyChangeCard";
		return "readyChangeCard";
	}
	
	public String updateOrigCardState(){
		sellOrderOrigCardListDTO.setOrderId(sellOrderDTO.getOrderId());
		String[] cards=new String[1];
		cards[0]=origCardListStr[0].split(",")[0];
		sellOrderOrigCardListDTO.setOrigcardListIds(cards);
		sendService(ConstCode.ORIG_CARD_STATE_UPDATE, sellOrderOrigCardListDTO);
		if(!hasErrors()){
			addActionMessage("操作成功");
		}
		return readyChangeCard();
	}
	
	public String readySign() {
		try {
			if ("1".equals(sellOrderDTO.getOnymousStat()) && (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrderDTO.getOrderType())
							|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
							||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN.equals(sellOrderDTO.getOrderType()))) {
				this.addActionError("记名卡订单无须订单准备");
			}
			if (this.hasErrors()) {
				return list();
			}
			view();
		} catch (Exception e) {
			this.logger.error(e.getMessage());

		}
		actionMethodName = "readySign";
		return "readySign";
	}

	
	@SuppressWarnings("unchecked")
	public String cardReady() {
		try {			
			ListPageInit("cardStock", orderReadyDTO);
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELL_ORDER_GET_STOCK_AT_READ, orderReadyDTO)
					.getDetailvo();
			if (pageDataDTO != null && pageDataDTO.getData() != null
					&& pageDataDTO.getData().size() > 0) {
				List<HashMap<String, String>> lstStockList = (List<HashMap<String, String>>) pageDataDTO
						.getData();
				orderReadyDTO.setStartCardNo(lstStockList.get(0).get("min"));
				orderReadyDTO.setEndCardNo(lstStockList.get(0).get("max"));
				getRequest().setAttribute("totalRows",
						pageDataDTO.getTotalRecord());
			} else {
				getRequest().setAttribute("totalRows", 0);
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "cardList";
	}

	public void validateCardSegmentReady() {
		error_jsp = "WEB-INF/pages/univer/seller/order/orderready/stockSellOrderStockList.jsp";
		if (isEmpty(orderReadyDTO.getStartCardNo())) {
			this.addFieldError("orderReadyDTO.startCardNo", "起始卡号不能为空");
		}
		if (isEmpty(orderReadyDTO.getEndCardNo())) {
			this.addFieldError("orderReadyDTO.endCardNo", "截止卡号不能为空");
		}
		if (this.hasFieldErrors()) {
			cardReady();
			return;
		}

//		if (19 != orderReadyDTO.getStartCardNo().trim().length()) {
//			this.addFieldError("orderReadyDTO.startCardNo", "起始卡号必须是19位数字");
//		}
//		if (19 != orderReadyDTO.getEndCardNo().trim().length()) {
//			this.addFieldError("orderReadyDTO.endCardNo", "截止卡号必须是19位数字");
//		}
//		if (this.hasFieldErrors()) {
//			cardReady();
//			return;
//		}


		try {
			new BigInteger(orderReadyDTO.getStartCardNo());
		} catch (NumberFormatException e) {
			this.logger.error(e.getMessage());
			this.addFieldError("orderReadyDTO.startCardNo", "起始卡号必须是数字 ");
		}

		try {
			 new BigInteger(orderReadyDTO.getEndCardNo());
		} catch (NumberFormatException e) {
			this.addFieldError("orderReadyDTO.endCardNo", "截止卡号必须是数字 ");
		}

		if (this.hasFieldErrors()) {
			cardReady();
			return;
		}

		if (this.hasFieldErrors()) {
			cardReady();
			return;
		}

	}

	public String cardSegmentReady() {
		try {
			cardNoArray = null;
			orderReadyDTO.setCardNoArray(cardNoArray);
			sendService(ConstCode.SELL_ORDER_READY_BY_CARDSEGMENT, orderReadyDTO)
					.getDetailvo();
			if (this.hasErrors()) {
				return cardReady();
			}

			if (!this.hasErrors()) {
				getRequest().setAttribute("sucessMessage", "准备成功!");
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "sucessList";
	}

	public String cardArrayReady() {
		try {

			orderReadyDTO.setCardNoArray(cardNoArray);
			String startCardNoString=orderReadyDTO.getStartCardNo();
			String endCardNoString=orderReadyDTO.getEndCardNo();
			orderReadyDTO.setStartCardNo("");
			orderReadyDTO.setEndCardNo("");
			sendService(ConstCode.SELL_ORDER_READY_BY_CARD, orderReadyDTO)
					.getDetailvo();
			if (this.hasErrors()) {
				orderReadyDTO.setStartCardNo(startCardNoString);
				orderReadyDTO.setEndCardNo(endCardNoString);
				return cardReady();
			}

			if (!this.hasErrors()) {
				getRequest().setAttribute("sucessMessage", "准备成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "sucessList";
	}

	public String deleteAllCard() {
		try {
			sendService(ConstCode.SELL_ORDER_READY_DELETE_ALL_RECORD,
					sellOrderDTO).getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
				.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
						.equals(sellOrderDTO.getOrderType())
						||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
						.equals(sellOrderDTO.getOrderType())
						||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN.equals(sellOrderDTO.getOrderType())){
			return readySign();
		}else if(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO.getOrderType())){
			return readyChangeCard();
		}
		return ready();
	}

	public String addBuyOrder() {
		try {
			String orderIdArray = "";
			for (int i = 0; i < ec_choose.length; i++) {
				String orderId = ec_choose[i].split(",")[0];
				orderIdArray = orderIdArray + orderId;
				if (i != (ec_choose.length - 1)) {
					orderIdArray = orderIdArray + ",";
				}
			}

			sellBuyOrderDTO.setOrderIdArray(orderIdArray);
			//获得合同及订单明细
			sellBuyOrderDTO = (SellBuyOrderDTO) sendService(
					ConstCode.SELL_BUY_ORDER_CONTRACT_QUERY, sellBuyOrderDTO)
					.getDetailvo();
			String targetString = orderIdArray.split(",")[0];
			SellOrderDTO targetOrderDTO = new SellOrderDTO();
			targetOrderDTO.setOrderId(targetString);
			targetOrderDTO.setOrderType(sellBuyOrderDTO.getOrderType());
			SellOrderDTO resultSellOrderDTO = (SellOrderDTO) sendService(
					ConstCode.SELL_ORDER_GET_DTO, targetOrderDTO).getDetailvo();
			if (null != resultSellOrderDTO) {
				sellBuyOrderDTO.setInvoiceAddresses(resultSellOrderDTO
						.getInvoiceAddresses());
				sellBuyOrderDTO.setInvoiceCompanyName(resultSellOrderDTO
						.getInvoiceCompanyName());
				sellBuyOrderDTO.setInvoiceDate(resultSellOrderDTO
						.getInvoiceDate());
				sellBuyOrderDTO.setInvoiceItem(resultSellOrderDTO
						.getInvoiceItem());
				sellBuyOrderDTO.setInvoiceItemId(resultSellOrderDTO
						.getInvoiceItemId());

			}
			if (null != sellBuyOrderDTO) {
				// 合同明细
				sellerContractDTOs = sellBuyOrderDTO.getContractDTOs();
				JSONArray.fromObject(sellerContractDTOs);
				getRequest().setAttribute("sellerContractData",
						JSONArray.fromObject(sellerContractDTOs));
				productDTO = sellBuyOrderDTO.getProductDTO();
				// 订单明细
				sellOrderListDTOs = sellBuyOrderDTO.getSellOrderListDTOs();
				if (null != sellOrderListDTOs && sellOrderListDTOs.size() > 0) {
					sellOrderList_totalRows = sellerContractDTOs.size();
				}
				sellBuyOrderDTO.setOrderIdArray(orderIdArray);
			}
			getRequest().setAttribute("orderType",
					sellBuyOrderDTO.getOrderType());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "addBuyOrder";
	}

	/**
	 * 生成采购订单
	 */
	public String insertBuyOrder() {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellBuyOrderDTO.getOrderType())
					||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
					.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())) {
				Integer cardTotalInteger = Integer
						.parseInt((SystemInfo
								.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
				int cardAmountCount = 0;
				for (String eachCardAmount : cardAmountList) {
					cardAmountCount += Integer.parseInt(eachCardAmount);
				}
				if (cardAmountCount > cardTotalInteger) {
					addActionError("订单卡数量不能超过系统既定的上限:" + cardTotalInteger);
					ec_choose = sellBuyOrderDTO.getOrderIdArray().split(",");
					return this.addBuyOrder();
				}
				List<SellOrderListDTO> sellOrderListDTOs = new ArrayList<SellOrderListDTO>();
				for (int i = 0; i < cardLayoutIdList.length; i++) {
					SellOrderListDTO dto = new SellOrderListDTO();
					dto.setCardLayoutId(cardLayoutIdList[i]);
					dto.setFaceValueType(faceValueTypeList[i]);
					// 非固定面额则面额值为0
					if ("1".equals(faceValueTypeList[i])) {
						dto.setFaceValue("0");
					} else {
						dto.setFaceValue(faceValueList[i]);
					}
					dto.setCardAmount(cardAmountList[i]);
					sellOrderListDTOs.add(dto);
				}
				sellBuyOrderDTO.setSellOrderListDTOs(sellOrderListDTOs);
			}

			sendService(ConstCode.SELL_BUY_ORDER_INSERT, sellBuyOrderDTO);
			if (!hasErrors()) {
				this.addActionMessage("生成采购订单成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}
	
	public String orderListAllReady(){
		sendService(ConstCode.SELL_ORDER_READY_BY_ALL_ORDER_LIST, orderReadyDTO);
		if (!hasErrors()) {
			this.addActionMessage("准备成功");
		}
		return this.ready();
	}
	
	public String allReadyForChangeCard(){
		sendService(ConstCode.ORIG_CARD_ORDER_READY, orderReadyDTO);
		if (!hasErrors()) {
			this.addActionMessage("准备成功");
		}
		return readyChangeCard();
	}
	public String deleteRecordForChangeCard() {
		try {
			sendService(ConstCode.SELL_ORDER_READY_DELETE_RECORD, orderReadyDTO)
					.getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return readyChangeCard();
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public OrderReadyDTO getOrderReadyDTO() {
		return orderReadyDTO;
	}

	public void setOrderReadyDTO(OrderReadyDTO orderReadyDTO) {
		this.orderReadyDTO = orderReadyDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public String[] getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String[] cardNoArray) {
		this.cardNoArray = cardNoArray;
	}

	public SellBuyOrderDTO getSellBuyOrderDTO() {
		return sellBuyOrderDTO;
	}

	public void setSellBuyOrderDTO(SellBuyOrderDTO sellBuyOrderDTO) {
		this.sellBuyOrderDTO = sellBuyOrderDTO;
	}

	public List<SellerContractDTO> getSellerContractDTOs() {
		return sellerContractDTOs;
	}

	public void setSellerContractDTOs(List<SellerContractDTO> sellerContractDTOs) {
		this.sellerContractDTOs = sellerContractDTOs;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public List<SellOrderListDTO> getSellOrderListDTOs() {
		return sellOrderListDTOs;
	}

	public void setSellOrderListDTOs(List<SellOrderListDTO> sellOrderListDTOs) {
		this.sellOrderListDTOs = sellOrderListDTOs;
	}

	public int getSellOrderList_totalRows() {
		return sellOrderList_totalRows;
	}

	public void setSellOrderList_totalRows(int sellOrderListTotalRows) {
		sellOrderList_totalRows = sellOrderListTotalRows;
	}

	public String[] getCardLayoutIdList() {
		return cardLayoutIdList;
	}

	public void setCardLayoutIdList(String[] cardLayoutIdList) {
		this.cardLayoutIdList = cardLayoutIdList;
	}

	public String[] getFaceValueTypeList() {
		return faceValueTypeList;
	}

	public void setFaceValueTypeList(String[] faceValueTypeList) {
		this.faceValueTypeList = faceValueTypeList;
	}

	public String[] getFaceValueList() {
		return faceValueList;
	}

	public void setFaceValueList(String[] faceValueList) {
		this.faceValueList = faceValueList;
	}

	public String[] getCardAmountList() {
		return cardAmountList;
	}

	public void setCardAmountList(String[] cardAmountList) {
		this.cardAmountList = cardAmountList;
	}

	public SellOrderOrigCardListDTO getSellOrderOrigCardListDTO() {
		return sellOrderOrigCardListDTO;
	}

	public void setSellOrderOrigCardListDTO(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO) {
		this.sellOrderOrigCardListDTO = sellOrderOrigCardListDTO;
	}

	public String[] getOrigCardListStr() {
		return origCardListStr;
	}

	public void setOrigCardListStr(String[] origCardListStr) {
		this.origCardListStr = origCardListStr;
	}
	
}
