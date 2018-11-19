package com.huateng.univer.issuer.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockQueryDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

/**
 * 制卡订单准备action
 * 
 * @author xxl
 */
public class StockOrderReadyAction extends StockOrderBaseAction {
	private Logger logger = Logger.getLogger(StockOrderReadyAction.class);
	private static final long serialVersionUID = 6699519622129890836L;
	private SellOrderListQueryDTO orderListQueryDTO = new SellOrderListQueryDTO();
	private SellOrderCardListQueryDTO orderCardListQueryDTO = new SellOrderCardListQueryDTO();
	private SellOrderReadyQueryDTO orderReadyQueryDTO = new SellOrderReadyQueryDTO();
	private EntityStockQueryDTO entityStockQueryDTO = new EntityStockQueryDTO();
	private EntityStockDTO entityStockDTO = new EntityStockDTO();
	private SellOrderCardListDTO sellOrderCardListDTO = new SellOrderCardListDTO();

	private SellOrderReadyResultDTO orderReadyResultDTO = new SellOrderReadyResultDTO();
	@SuppressWarnings("unchecked")
	private List<?> orderListDTOs = new ArrayList();
	private int orderListRow_totalRows = 0;
	@SuppressWarnings("unchecked")
	private List<?> orderCardListDTOs = new ArrayList();
	private int orderCardListRow_totalRows = 0;

	@SuppressWarnings("unchecked")
	private List<?> readyStocks = new ArrayList();
	private int readyStockRow_totalRows = 0;
	private String errorJsp;
	private String successFlag = ERROR;

	private int loyaltyOrderList_totalRows = 0;

	private String[] cardLayoutIdList;

	private String[] faceValueTypeList;

	private String[] faceValueList;

	private String[] cardAmountList;

	private List<LoyaltyContractDTO> loyaltyContractDTOs = new ArrayList<LoyaltyContractDTO>();

	private List<SellOrderListDTO> sellOrderListDTOs = new ArrayList<SellOrderListDTO>();

	private SellBuyOrderDTO sellBuyOrderDTO = new SellBuyOrderDTO();

	private String[] choose;

	private ProductDTO productDTO;

	protected void initActionName() {
		actionName = "stockOrderReadyAction";
	}

	protected void inqueryInit() {
		// 订单类型： 制卡订单+下级机构采购订单
		sellOrderQueryDTO
				.setOrderTypeArray(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD + ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
		// 订单状态：21订单准备
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_READY);
	}

	public int getLoyaltyOrderList_totalRows() {
		return loyaltyOrderList_totalRows;
	}

	public void setLoyaltyOrderList_totalRows(int loyaltyOrderListTotalRows) {
		loyaltyOrderList_totalRows = loyaltyOrderListTotalRows;
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

	public List<LoyaltyContractDTO> getLoyaltyContractDTOs() {
		return loyaltyContractDTOs;
	}

	public void setLoyaltyContractDTOs(
			List<LoyaltyContractDTO> loyaltyContractDTOs) {
		this.loyaltyContractDTOs = loyaltyContractDTOs;
	}

	public List<SellOrderListDTO> getSellOrderListDTOs() {
		return sellOrderListDTOs;
	}

	public void setSellOrderListDTOs(List<SellOrderListDTO> sellOrderListDTOs) {
		this.sellOrderListDTOs = sellOrderListDTOs;
	}

	public SellBuyOrderDTO getSellBuyOrderDTO() {
		return sellBuyOrderDTO;
	}

	public void setSellBuyOrderDTO(SellBuyOrderDTO sellBuyOrderDTO) {
		this.sellBuyOrderDTO = sellBuyOrderDTO;
	}

	public String[] getChoose() {
		return choose;
	}

	public void setChoose(String[] choose) {
		this.choose = choose;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	/**
	 * 查询订单明细和卡明细
	 */
	public String orderReadyList() {
		try {
			initActionName();
			orderListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			ListPageInit(null, orderListQueryDTO);
			if (orderListQueryDTO.isQueryAll()) {
				orderListQueryDTO.setQueryAll(false);
				orderListQueryDTO
						.setRowsDisplayed(Integer
								.parseInt(SystemInfo
										.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}

			orderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			ListPageInit("orderCardListRow", orderCardListQueryDTO);
			if (orderCardListQueryDTO.isQueryAll()) {
				orderCardListQueryDTO.setQueryAll(false);
				orderCardListQueryDTO
						.setRowsDisplayed(Integer
								.parseInt(SystemInfo
										.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}

			orderReadyQueryDTO.setOrderListQueryDTO(orderListQueryDTO);
			orderReadyQueryDTO.setOrderCardListQueryDTO(orderCardListQueryDTO);

			orderReadyResultDTO = (SellOrderReadyResultDTO) this.sendService(
					ConstCode.STOCK_ORDER_READY, orderReadyQueryDTO)
					.getDetailvo();
			orderListDTOs = orderReadyResultDTO.getOrderLists().getData();
			orderListRow_totalRows = orderListDTOs.size();
			orderCardListDTOs = orderReadyResultDTO.getOrderCardLists()
					.getData();
			orderCardListRow_totalRows = orderReadyResultDTO.getOrderCardLists().getTotalRecord();
			//getRequest().setAttribute("totalRows",orderReadyResultDTO.getOrderCardLists().getTotalRecord());

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "orderListList";
	}

	/**
	 * 查询符合订单明细条件的库存卡
	 */
//	public void validateOrderReadyBetweenAnd() {
//		if (isEmpty(entityStockQueryDTO.getStartCardNo())) {
//			this.addFieldError("entityStockQueryDTO.startCardNo", "请输入起始卡号！");
//			errorJsp = "WEB-INF/pages/univer/issuer/order/orderready/stockOrderStockList.jsp";
//		}
//		if (isEmpty(entityStockQueryDTO.getEndCardNo())) {
//			this.addFieldError("entityStockQueryDTO.endCardNo", "请输入结束卡号！");
//			errorJsp = "WEB-INF/pages/univer/issuer/order/orderready/stockOrderStockList.jsp";
//		}
//		if(hasFieldErrors()){
//			this.orderReadyCardList();
//		}
//	}

	public String orderReadyBetweenAnd() {
		return this.orderReadyCardList();
	}

	public String orderReadyCardList() {
		try {
			ListPageInit(null, entityStockQueryDTO);
			if (entityStockQueryDTO.isQueryAll()) {
				entityStockQueryDTO.setQueryAll(false);
				entityStockQueryDTO
						.setRowsDisplayed(Integer
								.parseInt((SystemInfo
										.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM))));
			}
			initActionName();
			entityStockQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			PageDataDTO pageDataDTO = (PageDataDTO) sendService(
					ConstCode.STOCK_ORDER_CARD_READY, entityStockQueryDTO)
					.getDetailvo();

			if (null != pageDataDTO) {
				if(pageDataDTO.getData()!= null && pageDataDTO.getData().size() > 0){
					List<HashMap<String, String>> lstStockList=(List<HashMap<String, String>>)pageDataDTO.getData();
					entityStockQueryDTO.setStartCardNo(lstStockList.get(lstStockList.size()-2).get("cardNo"));
					entityStockQueryDTO.setEndCardNo(lstStockList.get(lstStockList.size()-1).get("cardNo"));
					pageDataDTO.getData().remove(pageDataDTO.getData().size()-1);
					pageDataDTO.getData().remove(pageDataDTO.getData().size()-1);
					pageDataDTO.setTotalRecord(pageDataDTO.getTotalRecord()-2);
					readyStocks = pageDataDTO.getData();
					getRequest().setAttribute("totalRows", pageDataDTO.getTotalRecord());
				}else{
					getRequest().setAttribute("totalRows",0);
				}
			}else{
				getRequest().setAttribute("totalRows",0);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "stockList";
	}

	/**
	 * 订单准备,添加指定卡号段到订单卡明细
	 */
	public String readyStockCardToCardList() {
		try {
			String cardNoArray = "";
			for (int i = 0; i < choose.length; i++) {
				if (i == 0) {
					cardNoArray = choose[i];
				} else {
					cardNoArray = cardNoArray + "," + choose[i];
				}
			}

			entityStockDTO.setCardNoArray(cardNoArray);
			entityStockDTO.setOrderId(sellOrderDTO.getOrderId());
			this.sendService(ConstCode.STOCK_ORDER_READY_TO_CARD_LIST,
					entityStockDTO);
			if(this.hasErrors()){
				 return orderReadyCardList();
			}
			if (!hasErrors()) {
				getRequest().setAttribute("successMessage", "添加明细成功!");
				successFlag = SUCCESS;
				this.addActionMessage("订单准备成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "blank";
	}

	/**
	 * 订单准备,添加指定卡号段到订单卡明细
	 */
	public void validateReadyAllStockCardToCardList() {
		if (isEmpty(entityStockQueryDTO.getStartCardNo())) {
			this.addFieldError("entityStockQueryDTO.startCardNo", "请输入起始卡号！");
			errorJsp = "WEB-INF/pages/univer/issuer/order/orderready/stockOrderStockList.jsp";
		}
		if (isEmpty(entityStockQueryDTO.getEndCardNo())) {
			this.addFieldError("entityStockQueryDTO.endCardNo", "请输入结束卡号！");
			errorJsp = "WEB-INF/pages/univer/issuer/order/orderready/stockOrderStockList.jsp";
		}

	}

	public String readyAllStockCardToCardList() {
		try {
			entityStockDTO.setOrderId(sellOrderDTO.getOrderId());
			entityStockDTO.setCardNoArray(null);
			entityStockDTO.setStartCardNo(entityStockQueryDTO.getStartCardNo());
			entityStockDTO.setEndCardNo(entityStockQueryDTO.getEndCardNo());
			this.sendService(ConstCode.STOCK_ORDER_READY_TO_CARD_LIST,
					entityStockDTO);
			if (!hasErrors()) {
				getRequest().setAttribute("successMessage", "添加明细成功!");
				successFlag = SUCCESS;
				this.addActionMessage("订单准备成功！");
			}else{
				return this.orderReadyCardList();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return "blank";
	}

	public String deleteCardList() {
		try {
			sellOrderCardListDTO.setOrderId(sellOrderDTO.getOrderId());
			this.sendService(ConstCode.STOCK_ORDER_CARDLIST_DEL,
					sellOrderCardListDTO);
			if (!hasErrors()) {
				addActionMessage("删除订单卡明细成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return orderReadyList();

	}

	/**
	 * 转到代发卡订单生成
	 * 
	 * @author Yifeng.Shi 2011-03-14
	 * @return
	 * @throws Exception
	 */
	public String makeLoyaltyOrder() throws Exception {

		String orderIdArray = "";
		try {

			for (int i = 0; i < choose.length; i++) {
				String orderId = choose[i].split(",")[0];
				orderIdArray = orderIdArray + orderId;
				if (i != (choose.length - 1)) {
					orderIdArray = orderIdArray + ",";
				}
			}

			sellBuyOrderDTO.setOrderIdArray(orderIdArray);
			sellBuyOrderDTO = (SellBuyOrderDTO) sendService(
					ConstCode.SELL_ORDER_CONTRACT_QUERY, sellBuyOrderDTO)
					.getDetailvo();

			if (null != sellBuyOrderDTO) {

				// 返回页面合同明细
				loyaltyContractDTOs = sellBuyOrderDTO.getLoyaltyContractDTO();
				JSONArray.fromObject(loyaltyContractDTOs);
				getRequest().setAttribute("loyaltyContractDTOs",
						JSONArray.fromObject(loyaltyContractDTOs));
				productDTO = sellBuyOrderDTO.getProductDTO();

				// 匿名订单,需要显示订单明细
				sellOrderListDTOs = sellBuyOrderDTO.getSellOrderListDTOs();
				if (null != sellOrderListDTOs && sellOrderListDTOs.size() > 0) {
					loyaltyOrderList_totalRows = loyaltyContractDTOs.size();
				}

			}
			if (null != sellBuyOrderDTO) {
				sellBuyOrderDTO.setOrderIdArray(orderIdArray);
				getRequest().setAttribute("orderType",
						sellBuyOrderDTO.getOrderType());
			}
			if (hasErrors()) {
				return this.list();
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		
		return "choice";

	}

	/**
	 * 代发卡订单生成
	 * 
	 * @author Yifeng.Shi 2011-03-14
	 * @return
	 * @throws Exception
	 */
	public String insertLoyaltyOrder() throws Exception {
		try {
			if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
					.equals(sellBuyOrderDTO.getOrderType()) || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
					.equals(sellBuyOrderDTO.getOrderType()) ) {
				Integer cardTotalInteger=Integer
				.parseInt((SystemInfo
						.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
				int cardAmountCount=0;
				for(String eachCardAmount:cardAmountList){
					cardAmountCount+=Integer.parseInt(eachCardAmount);
				}
				if(cardAmountCount>cardTotalInteger){
					choose=sellBuyOrderDTO.getOrderIdArray().split(",");
					this.makeLoyaltyOrder();
					addActionError("订单卡数量不能超过系统既定的上限:"+cardTotalInteger);
					return "choice";
				}

				List<SellOrderListDTO> sellOrderListDTOs = new ArrayList<SellOrderListDTO>();
				for (int i = 0; i < cardLayoutIdList.length; i++) {
					SellOrderListDTO dto = new SellOrderListDTO();
					dto.setCardLayoutId(cardLayoutIdList[i]);
					dto.setFaceValueType(faceValueTypeList[i]);
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

			sendService(ConstCode.LOYALTY_BUY_ORDER_INSERT, sellBuyOrderDTO);
			if (!hasErrors()) {
				this.addActionMessage("生成采购订单成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}

	public List<?> getReadyStocks() {
		return readyStocks;
	}

	public void setReadyStocks(List<?> readyStocks) {
		this.readyStocks = readyStocks;
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

	public void setOrderCardListQueryDTO(
			SellOrderCardListQueryDTO orderCardListQueryDTO) {
		this.orderCardListQueryDTO = orderCardListQueryDTO;
	}

	public List<?> getOrderListDTOs() {
		return orderListDTOs;
	}

	public void setOrderListDTOs(List<?> orderListDTOs) {
		this.orderListDTOs = orderListDTOs;
	}

	public List<?> getOrderCardListDTOs() {
		return orderCardListDTOs;
	}

	public void setOrderCardListDTOs(List<?> orderCardListDTOs) {
		this.orderCardListDTOs = orderCardListDTOs;
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

	public void setOrderReadyResultDTO(
			SellOrderReadyResultDTO orderReadyResultDTO) {
		this.orderReadyResultDTO = orderReadyResultDTO;
	}

	public int getOrderListRow_totalRows() {
		return orderListRow_totalRows;
	}

	public void setOrderListRow_totalRows(int orderListRowTotalRows) {
		orderListRow_totalRows = orderListRowTotalRows;
	}

	public int getOrderCardListRow_totalRows() {
		return orderCardListRow_totalRows;
	}

	public void setOrderCardListRow_totalRows(int orderCardListRowTotalRows) {
		orderCardListRow_totalRows = orderCardListRowTotalRows;
	}

	public int getReadyStockRow_totalRows() {
		return readyStockRow_totalRows;
	}

	public void setReadyStockRow_totalRows(int readyStockRowTotalRows) {
		readyStockRow_totalRows = readyStockRowTotalRows;
	}

	public EntityStockQueryDTO getEntityStockQueryDTO() {
		return entityStockQueryDTO;
	}

	public void setEntityStockQueryDTO(EntityStockQueryDTO entityStockQueryDTO) {
		this.entityStockQueryDTO = entityStockQueryDTO;
	}

	public EntityStockDTO getEntityStockDTO() {
		return entityStockDTO;
	}

	public void setEntityStockDTO(EntityStockDTO entityStockDTO) {
		this.entityStockDTO = entityStockDTO;
	}

	public SellOrderCardListDTO getSellOrderCardListDTO() {
		return sellOrderCardListDTO;
	}

	public void setSellOrderCardListDTO(
			SellOrderCardListDTO sellOrderCardListDTO) {
		this.sellOrderCardListDTO = sellOrderCardListDTO;
	}

	public String getErrorJsp() {
		return errorJsp;
	}

	public void setErrorJsp(String errorJsp) {
		this.errorJsp = errorJsp;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
}
