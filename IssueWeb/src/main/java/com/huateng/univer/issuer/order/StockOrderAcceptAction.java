package com.huateng.univer.issuer.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;

/**
 * 制卡订单接收action
 * 
 * @author xxl
 * 
 */
public class StockOrderAcceptAction extends StockOrderBaseAction {
	private Logger logger = Logger.getLogger(StockOrderAcceptAction.class);
	private static final long serialVersionUID = -2950289581477079163L;
	private SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
	private SellOrderCardListDTO sellOrderCardListDTO = new SellOrderCardListDTO();
	private String errorMess;
	private String display;
	private EntitySystemParameterDTO entitySystemParameterDTO;
	
	private String orderId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public EntitySystemParameterDTO getEntitySystemParameterDTO() {
		return entitySystemParameterDTO;
	}

	public void setEntitySystemParameterDTO(
			EntitySystemParameterDTO entitySystemParameterDTO) {
		this.entitySystemParameterDTO = entitySystemParameterDTO;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getErrorMess() {
		return errorMess;
	}

	public void setErrorMess(String errorMess) {
		this.errorMess = errorMess;
	}

	protected void initActionName() {
		actionName = "stockOrderAcceptAction";
	}
	
	protected void inqueryInit() {
		// 订单类型： 制卡订单+下级机构记名采购订单
		sellOrderQueryDTO
				.setOrderTypeArray(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD + ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
						+ ","
						+OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN);
		// 订单状态：26订单接收
		//sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_ACCEPT);
		sellOrderQueryDTO.setOrderStateArray(OrderConst.ORDER_STATE_ORDER_ACCEPT+","+OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT);
	}
	
	/**
	 * 订单接收列表页面
	 */
	public String accpet() throws Exception {
		
		entitySystemParameterDTO =new EntitySystemParameterDTO();
		
		entitySystemParameterDTO.setEntityId(getUser().getEntityId());
		entitySystemParameterDTO.setParameterCode(SystemInfoConstants.CHECK_CARD);
		entitySystemParameterDTO=(EntitySystemParameterDTO)sendService(ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_VIEW, entitySystemParameterDTO).getDetailvo();
		if(entitySystemParameterDTO.getParameterValue().equals("1")){
			
			display="disabled";
		}	
		
	
		
		  
		  return  list() ;
	}
	
	/**
	 * 点选择订单时判断是否已经验过卡
	 * 
	 */
	public void hadCheckCardForOrder() throws Exception {
		if (null == sellOrderDTO) {

			sellOrderDTO = new SellOrderDTO();
		}
		sellOrderDTO = (SellOrderDTO) sendService(ConstCode.HAD_CHECK_CARD,
				sellOrderDTO).getDetailvo();

		if ("1".equals(sellOrderDTO.getIsCheckCard())
				|| "1".equals(entitySystemParameterDTO.getParameterValue())) {// 已全部验过卡
			Map map = new HashMap();
			map.put("0", "disabled");
			JSONArray jsonObject = JSONArray.fromObject(map);

			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(jsonObject);
			getResponse().getWriter().close();
		} else if ("0".equals(sellOrderDTO.getIsCheckCard())
				|| "0".equals(entitySystemParameterDTO.getParameterValue())) {
			Map map = new HashMap();
			map.put("0", "display");
			JSONArray jsonObject = JSONArray.fromObject(map);
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(jsonObject);
			getResponse().getWriter().close();
		}

	}
	
	/**
	 * 制卡完成
	 */
	public String makeCardComplate() throws Exception {
		try {
			sellOrderDTO.setDefaultEntityId(getUser().getEntityId());
			this.sendService(ConstCode.MAKECARD_STATE_COMPLATE, sellOrderDTO);
			if (hasErrors()) {
				return list();
			}
			this.addActionMessage("修改制卡完成状态成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	/**
	 * 卡明细列表
	 */
	@SuppressWarnings("unchecked")
	public String makeCardList() throws Exception {
		try {
			sellOrderCardListQueryDTO.setQueryAll(true);
			sellOrderCardListQueryDTO.setDefaultEntityId(getUser().getEntityId());
			sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			PageDataDTO pageDataDTO = (PageDataDTO) sendService(
					ConstCode.ORDER_CARD_LIST_QUERY, sellOrderCardListQueryDTO)
					.getDetailvo();
			if (null != pageDataDTO) {
				orderCardList = (List<Map<String, Object>>) pageDataDTO.getData();
				if (null != orderCardList) {
					orderCardList_totalRows = orderCardList.size();
				}
			}

			if (hasErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "makeCardList";
	}
	/**
	 * 
	 * 返回
	 */
	public String returnBack() throws Exception {
		 sellOrderDTO=(SellOrderDTO)sendService(
				ConstCode.HAD_CHECK_CARD, sellOrderDTO).getDetailvo();
		  if(sellOrderDTO.getIsCheckCard().equals("1")){//已全部验过卡
			  display="disabled";
			  
		  }
		  return list();
	
	}
	
    /**
     * 
     * 验卡初始化
     **/
	public String checkCardInit() throws Exception {
		try {
			sellOrderCardListQueryDTO.setDefaultEntityId(getUser().getEntityId());
			sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			sellOrderCardListQueryDTO.setQueryAll(true);
			PageDataDTO pageDataDTO = (PageDataDTO) sendService(
					ConstCode.ORDER_CARD_LIST_QUERY_FOR_CHECKCARD, sellOrderCardListQueryDTO)
					.getDetailvo();
			if (null != pageDataDTO) {
				orderCardList = (List<Map<String, Object>>) pageDataDTO.getData();
				if (null != orderCardList) {
					orderCardList_totalRows = orderCardList.size();
				}
			}
			sellOrderCardListQueryDTO.setCardNo("1");
			if (hasErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "checkCardInitSucc";
	}
    /***
     * 开始验卡
     * @return
     * @throws Exception
     */
	public String checkCard() throws Exception {
		try {
			//开始验卡
			if(null!=sellOrderCardListQueryDTO.getCardNo()&&!"".equals(sellOrderCardListQueryDTO.getCardNo())){
			sendService(
					ConstCode.ORDER_CARD_LIST_CHECK_CARD, sellOrderCardListQueryDTO);
			if(this.hasErrors()){
	        	  errorMess="验卡失败，请再次验卡!";
	        	  
	          }
			}
		     //订单卡明细
			sellOrderCardListQueryDTO.setDefaultEntityId(getUser().getEntityId());
			sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			sellOrderCardListQueryDTO.setQueryAll(true);
			sellOrderCardListQueryDTO.setCardNo(null);
			PageDataDTO pageDataDTO = (PageDataDTO) sendService(
					ConstCode.ORDER_CARD_LIST_QUERY_FOR_CHECKCARD, sellOrderCardListQueryDTO)
					.getDetailvo();
			if (null != pageDataDTO) {
				orderCardList = (List<Map<String, Object>>) pageDataDTO.getData();
				if (null != orderCardList) {
					orderCardList_totalRows = orderCardList.size();
				}
			}
			sellOrderCardListQueryDTO.setCardNo("2");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "checkCardSucc";
	}
	/***
     * 结束验卡
     * @return
     * @throws Exception
     */
	public String endCheckCard() throws Exception {
		try {
			//结束验卡
			
			sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			sendService(
					ConstCode.ORDER_CARD_LIST_CHECK_CARD_END, sellOrderCardListQueryDTO);
		
			
		     //订单卡明细
			sellOrderCardListQueryDTO.setDefaultEntityId(getUser().getEntityId());
			sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());
			sellOrderCardListQueryDTO.setQueryAll(true);
			sellOrderCardListQueryDTO.setCardNo(null);
			PageDataDTO pageDataDTO = (PageDataDTO) sendService(
					ConstCode.ORDER_CARD_LIST_QUERY_FOR_CHECKCARD, sellOrderCardListQueryDTO)
					.getDetailvo();
			if (null != pageDataDTO) {
				orderCardList = (List<Map<String, Object>>) pageDataDTO.getData();
				if (null != orderCardList) {
					orderCardList_totalRows = orderCardList.size();
				}
			}
			if(this.hasErrors()){
				return "input";
			}
			sellOrderCardListQueryDTO.setCardNo("1");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "endCheckCardSucc";
	}
	
	public String editCardState() throws Exception {
		try {
			sellOrderCardListDTO = (SellOrderCardListDTO) sendService(
					ConstCode.ORDER_CARD_LIST_VIEW, sellOrderCardListDTO)
					.getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "modifyCardState";
	}

	public String modifyCardState() throws Exception {
		try {
			this.sendService(ConstCode.ORDER_CARD_LIST_UPDATE,
					sellOrderCardListDTO);
			if (hasActionErrors()) {
				return "modifyCardState";
			}
			addActionMessage("修改卡状态成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "blank";
	}

	public String rejectToCardFileMake() throws Exception{
		try {
			sellOrderDTO.setDefaultEntityId(getUser().getEntityId());
			sendService(ConstCode.STOCK_ORDER_REJECT_CARD_FILE_MAKE, sellOrderDTO);
			
			if(!hasErrors()){
				addActionMessage("退回订单成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}
	
	public SellOrderCardListDTO getSellOrderCardListDTO() {
		return sellOrderCardListDTO;
	}

	public void setSellOrderCardListDTO(
			SellOrderCardListDTO sellOrderCardListDTO) {
		this.sellOrderCardListDTO = sellOrderCardListDTO;
	}

	public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
		return sellOrderCardListQueryDTO;
	}

	public void setSellOrderCardListQueryDTO(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
		this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
	}

}
