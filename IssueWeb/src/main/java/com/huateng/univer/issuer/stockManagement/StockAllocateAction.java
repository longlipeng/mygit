package com.huateng.univer.issuer.stockManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.stock.StockAllocateDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.action.BaseAction;

public class StockAllocateAction extends BaseAction {
	private Logger logger = Logger.getLogger(StockAllocateAction.class);
	public StockAllocateDTO stockAllocateDTO=new StockAllocateDTO();
	public PageDataDTO pageDataDTO;
	private Integer orderList_totalRows;
	private Integer totalRows;
	private List<Map<String, String>> issuerList = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> saleUserList=new ArrayList<Map<String,String>>();
	private boolean updateFlag=false;
	private SellOrderListDTO sellOrderListDTO;
	private String[] cardNoArray;
	private OrderReadyDTO orderReadyDTO;
	private SellOrderInputDTO sellOrderInputDTO=new SellOrderInputDTO();
	private Integer orderCard_totalRows;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String list(){
		stockAllocateDTO.setDefaultEntityId(this.getUser().getEntityId());
		pageDataDTO=(PageDataDTO)sendService(ConstCode.STOCK_ALLOCATE_LIST, stockAllocateDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}else{
			totalRows=0;
		}
		if(hasActionErrors()){
			return "stockAllocateList";
		}
		return "stockAllocateList";
	}
	public String view(){
		sellOrderInputDTO.setDefaultEntityId(this.getUser().getDefaultEntityId());
		sellOrderInputDTO = (SellOrderInputDTO) sendService(
				ConstCode.STOCK_ALLOCATE_QUERY_ORGAN, sellOrderInputDTO)
				.getDetailvo();			
		for (IssuerDTO dto : sellOrderInputDTO.getIssuerDTOs()) {
			Map<String, String> issuer = new HashMap<String, String>();
			issuer.put("entityId", dto.getEntityId());
			issuer.put("entityName", dto.getIssuerName());
			issuerList.add(issuer);
		}
		for (UserDTO userDTO : sellOrderInputDTO.getSaleUserList()) {
			Map<String, String> user = new HashMap<String, String>();
			user.put("userId", userDTO.getUserId());
			user.put("userName", userDTO.getUserName());
			saleUserList.add(user);
		}
		stockAllocateDTO=(StockAllocateDTO)sendService(
				ConstCode.STOCK_ALLOCATE_GET_CARD_LIST, stockAllocateDTO).getDetailvo();
		if(stockAllocateDTO.getOrderCardList()!=null){
			totalRows = stockAllocateDTO.getOrderList().getTotalRecord();
		}else{
			totalRows=0;
		}
		return "allocateView";
	}
	public String toAdd(){

		getList();
			totalRows=0;
			return "stockAllocateAdd";
	}
	
	public void getList(){		
		sellOrderInputDTO.setDefaultEntityId(this.getUser().getDefaultEntityId());
		sellOrderInputDTO = (SellOrderInputDTO) sendService(
				ConstCode.STOCK_ALLOCATE_QUERY_ORGAN, sellOrderInputDTO)
				.getDetailvo();			
		for (IssuerDTO dto : sellOrderInputDTO.getIssuerDTOs()) {
			Map<String, String> issuer = new HashMap<String, String>();
			issuer.put("entityId", dto.getEntityId());
			issuer.put("entityName", dto.getIssuerName());
			issuerList.add(issuer);
		}
		for (UserDTO userDTO : sellOrderInputDTO.getSaleUserList()) {
			Map<String, String> user = new HashMap<String, String>();
			user.put("userId", userDTO.getUserId());
			user.put("userName", userDTO.getUserName());
			saleUserList.add(user);
		}
		if(sellOrderInputDTO.getOrderList()!=null){
			totalRows = sellOrderInputDTO.getOrderList().getTotalRecord();
		}else{
			totalRows=0;
		}
		
	}
	
	public String toList(){
		updateFlag=true;
		sellOrderInputDTO.setOrderId(stockAllocateDTO.getAllocateId());
		getList();	
		return "stockAllocateAdd";
	}
	
	public String toUpdateFlag(){
		updateFlag=true;
		stockAllocateDTO=(StockAllocateDTO)sendService(
				ConstCode.STOCK_ALLOCATE_GET_TABLE_ID, stockAllocateDTO).getDetailvo();
		getList();
		return "stockAllocateAdd";
	}
	
	public String toAddOrderList() throws Exception{	
		ListPageInit("orderList", stockAllocateDTO);
		ListPageInit("orderCard", stockAllocateDTO);
		/*if (isEmpty(stockAllocateDTO.getSortFieldName())) {
			stockAllocateDTO.setSort("desc");
			stockAllocateDTO.setSortFieldName("orderId");
		}*/
		stockAllocateDTO=(StockAllocateDTO)sendService(
				ConstCode.STOCK_ALLOCATE_STOCK_AMOUNT, stockAllocateDTO).getDetailvo();
		if(stockAllocateDTO.getOrderList()!=null){
			orderList_totalRows = stockAllocateDTO.getOrderList().getTotalRecord();
		}else{
			orderList_totalRows=0;
		}
		if(stockAllocateDTO.getOrderCardList()!=null){
			orderCard_totalRows = stockAllocateDTO.getOrderCardList().getTotalRecord();
		}else{
			orderCard_totalRows=0;
		}
		return "readyOrderList";
	}
	
	@SuppressWarnings("unchecked")
	public String cardReady() {
		try {		
			
			ListPageInit("cardStock", orderReadyDTO);
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.STOCK_ALLOCATE_GET_STOCK, orderReadyDTO)
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
	
	public String cardArrayReady() {
		orderReadyDTO.setCardNoArray(cardNoArray);
		String startCardNoString=orderReadyDTO.getStartCardNo();
		String endCardNoString=orderReadyDTO.getEndCardNo();
		orderReadyDTO.setStartCardNo("");
		orderReadyDTO.setEndCardNo("");
		sendService(ConstCode.STOCK_ALLOCATE_CARD_ARRAY_READY, orderReadyDTO)
				.getDetailvo();
		if (this.hasErrors()) {
			orderReadyDTO.setStartCardNo(startCardNoString);
			orderReadyDTO.setEndCardNo(endCardNoString);
			return cardReady();
		}
	
		if (!this.hasErrors()) {
			getRequest().setAttribute("sucessMessage", "准备成功!");
		}
		stockAllocateDTO.setAllocateId(orderReadyDTO.getOrderId());
		stockAllocateDTO.setAllocateIn(orderReadyDTO.getFirstEntityId());
		stockAllocateDTO.setAllocateOut(orderReadyDTO.getProcessEntityId());
		return "successList";
	}
	
	public String stockAllocate(){
		
			sendService(ConstCode.STOCK_ALLOCATE, stockAllocateDTO)
			.getDetailvo();
			if (!this.hasErrors()) {
				getRequest().setAttribute("sucessMessage", "准备成功!");
			}
		
		return "successList";
	}
	
	public String delete() throws Exception{
		sendService(ConstCode.STOCK_ALLOCATE_RETURN, stockAllocateDTO)
		.getDetailvo();
		return toList();
		
	}
	
	public String returnAllocate() throws Exception{
		sendService(ConstCode.STOCK_ALLOCATE_RETURN, stockAllocateDTO)
			.getDetailvo();
		toAddOrderList();
		return "readyOrderList";
	}
	public String deleteCardList() throws Exception{
		sendService(ConstCode.STOCK_ALLOCATE_DELETE_CARD_LIST, stockAllocateDTO)
		.getDetailvo();
		toAddOrderList();
		return "readyOrderList";
	}
	
	public String deleteList() throws Exception{
		sendService(ConstCode.STOCK_ALLOCATE_DELETE_CARD_LIST, stockAllocateDTO)
		.getDetailvo();
		toAddOrderList();
		return "readyOrderList";
	}
	
	/**
	 * @author Yifeng.Shi since Date 2012-04-11
	 * 
	 * 库存调拨卡号段准备Action Method
	 * */
	public String cardSegmentReady() {
		try {
			cardNoArray = null;
			orderReadyDTO.setCardNoArray(cardNoArray);
			sendService(ConstCode.STOCK_ALLOCATE_CARDSEGMENT_READY, orderReadyDTO)
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
		return "successList";
	}

	
	public StockAllocateDTO getStockAllocateDTO() {
		return stockAllocateDTO;
	}

	public void setStockAllocateDTO(StockAllocateDTO stockAllocateDTO) {
		this.stockAllocateDTO = stockAllocateDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	

	public Integer getOrderList_totalRows() {
		return orderList_totalRows;
	}

	public void setOrderList_totalRows(Integer orderListTotalRows) {
		orderList_totalRows = orderListTotalRows;
	}

	public List<Map<String, String>> getIssuerList() {
		return issuerList;
	}

	public void setIssuerList(List<Map<String, String>> issuerList) {
		this.issuerList = issuerList;
	}

	public List<Map<String, String>> getSaleUserList() {
		return saleUserList;
	}

	public void setSaleUserList(List<Map<String, String>> saleUserList) {
		this.saleUserList = saleUserList;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public SellOrderListDTO getSellOrderListDTO() {
		return sellOrderListDTO;
	}

	public void setSellOrderListDTO(SellOrderListDTO sellOrderListDTO) {
		this.sellOrderListDTO = sellOrderListDTO;
	}

	public OrderReadyDTO getOrderReadyDTO() {
		return orderReadyDTO;
	}

	public void setOrderReadyDTO(OrderReadyDTO orderReadyDTO) {
		this.orderReadyDTO = orderReadyDTO;
	}

	public String[] getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String[] cardNoArray) {
		this.cardNoArray = cardNoArray;
	}

	public SellOrderInputDTO getSellOrderInputDTO() {
		return sellOrderInputDTO;
	}

	public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
		this.sellOrderInputDTO = sellOrderInputDTO;
	}

	public Integer getOrderCard_totalRows() {
		return orderCard_totalRows;
	}

	public void setOrderCard_totalRows(Integer orderCardTotalRows) {
		orderCard_totalRows = orderCardTotalRows;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	
}
