/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: RansomOrderAction.java
 * Author:   huateng
 * Date:     2013-8-13 上午09:06:22    
 * <author>      <time>      <version>    <desc>
 * huateng          2013-8-13 上午09:06:22
 */
package com.huateng.univer.seller.sellorder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.constant.OrderConst;

/**
 *  
 *赎回订单action类
 * @author huateng
 */

public class RansomOrderAction extends OrderBaseAction {
	private Logger logger = Logger.getLogger(RansomOrderAction.class);
	private static final long serialVersionUID = -6928382285747632769L;
	/**
	 * 原卡信息表DTO
	 */
	private SellOrderOrigCardListDTO sellOrderOrigCardListDTO;
	/**
	 * 原卡信息
	 */
	private String[] origCardListStr;
	/**
	 * 页面行数
	 */
	private int orderCardList_totalRows;
	/**
	 * table中的结果数据
	 */
	private List<Map<String, Object>> orderCardList;
	/**
	 * 消息
	 */
	private String message;
	
	
	@Override
	protected void addCondition() {
		setActionName("ransomOrderAction");
	}

	@Override
	protected void dealWithSellOrderInputDTO() {
		saleUserList = JSONArray
				.fromObject(sellOrderInputDTO.getSaleUserList());

		productDTOs = sellOrderInputDTO.getProductDTOs();

		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();

		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setProcessEntityName(getUser().getSellerName());

		if (isEmpty(sellOrderDTO.getSaleMan())
				&& sellOrderInputDTO.getSaleUserList().size() != 0) {
			String userId = sellOrderInputDTO.getSaleUserList().get(0)
					.getUserId();
			sellOrderDTO.setSaleMan(userId);
		}

		if (productDTO != null && productDTO.getProductId() != null
				&& !"".equals(productDTO.getProductId())) {
			/**
			 * 服务列表
			 */
			services = productDTO.getServices();
			/**
			 * 包装列表
			 */
			packages = productDTO.getPackages();

			productName = productDTO.getProductName();
			if (isEmpty(sellOrderDTO.getCardIssueFee())) {
				sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
			}
			if (isEmpty(sellOrderDTO.getAnnualFee())) {
				sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
			}
		}
		customerDTO = sellOrderInputDTO.getCustomerDTO();
		customerOrderLists = sellOrderDTO.getOrderList().getData();
		customerOrderLists_totalRows = sellOrderDTO.getOrderList()
				.getTotalRecord();
		origCardLists = sellOrderDTO.getOrigCardList().getData();
		origCardLists_totalRows = sellOrderDTO.getOrigCardList()
				.getTotalRecord();
	}
	
	/**
	 *新增赎回订单 
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
	
	/**
	 * 
	 * 插入验证
	 */
	public void validateInsert(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			error_jsp="WEB-INF/pages/univer/seller/order/orderinput/sellorder/ransom/ransomadd.jsp";
			add();
		}
	}
	
	/**
	 * 编辑
	 */
	@Override
	public String edit() {
		try {
			ListPageInit("origCardLists", sellOrderInputDTO.getSellOrderOrigCardListQueryDTO());
			sellOrderInputDTO.getSellOrderOrigCardListQueryDTO().setOrderId(sellOrderDTO.getOrderId());
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO = (SellOrderInputDTO)sendService(
	                ConstCode.RANSOM_ORDER_EDIT, sellOrderInputDTO).getDetailvo();
			dealWithSellOrderInputDTO();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "edit";
	}
	
	/**
	 * 验证
	 *
	 */
	public void  validateUpdate(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			edit();
			error_jsp="WEB-INF/pages/univer/seller/order/orderinput/sellorder/ransom/ransomeditdetail.jsp";
		}
	}

	/**
	 * 更新
	 */
	public String update() {
		super.update();
		sellOrderQueryDTO.setOrderId(null);
		return super.list();
	}

	public String addOrigCard(){
		return "addOrigCard";
	}
	
	public String insertOrigCard(){
		try {
			// XXX by wpf  当使用批量赎回的时候. 请重新写一个方法 . 订单明细卡的状态.不好控制
			sendService(
	                ConstCode.RANSOM_ORDER_INSERT_ORIG_CARD, sellOrderOrigCardListQueryDTO).getDetailvo();	
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
	
	public String deleteOrigCard(){
		if (sellOrderOrigCardListDTO == null) {
			sellOrderOrigCardListDTO = new SellOrderOrigCardListDTO();
		}
		sellOrderOrigCardListDTO.setOrderId(sellOrderDTO.getOrderId());
		sellOrderOrigCardListDTO.setOrigcardListIds(origCardListStr);
		sendService(ConstCode.RANSOM_ORDER_DELETE_ORIG_CARD,sellOrderOrigCardListDTO).getDetailvo();

		if(hasActionErrors()){
			return edit();
		}
		addActionMessage("删除成功");
		return  edit();
	}

	/**
	 * 进入验卡流程
	 * 
	 * @return
	 */
	
	public String list() {
		try {
			if (sellOrderOrigCardListDTO == null) {
				sellOrderOrigCardListDTO = new SellOrderOrigCardListDTO();
			}
			
			if(sellOrderDTO==null){
				sellOrderDTO = new SellOrderDTO();
			}
			
			sellOrderDTO.setOrderType("70000001");
			
			ListPageInit("orderList", sellOrderOrigCardListDTO);
			addCondition();
			sellOrderOrigCardListDTO.setDefaultEntityId(getUser()
					.getDefaultEntityId());

			sellOrderOrigCardListDTO = (SellOrderOrigCardListDTO) sendService(
					ConstCode.RANSOM_ORDER_ACCEP_INQUERY_LIST,
					sellOrderOrigCardListDTO).getDetailvo();

			if (!hasActionErrors()) {
				return "orderAccpteList";
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "orderAccpteListError";
	}


	/**
	 * 验卡
	 * 
	 * @return
	 */
	public void checkCard() {
		// 得到卡信息.
		// 到后台验证卡信息有效.
		sellOrderOrigCardListDTO = (SellOrderOrigCardListDTO) sendService(
				ConstCode.RANSOM_ORDER_ACCEP_CARD_CHECK,
				sellOrderOrigCardListDTO).getDetailvo();
		Map<String, String> map = null;
		if (sellOrderOrigCardListDTO == null) {
			map = new HashMap<String, String>();
			map.put("checkCardErr", "添加失败");
		} else {
			map = sellOrderOrigCardListDTO.getCheckedOneCard();
			map.put("checkCardErr", "0");
		}
		if (hasActionErrors() || hasErrors()) {
			Collection<String> errors = getActionErrors();
			map.put("checkCardErr", errors.toString());
		}
		// json返回页面单条卡信息
		try {
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(
					JSONObject.fromObject(map).toString());
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
			map.put("checkCardErr", e.getMessage() + " 请刷新页面再试");
		}
		// 不需要返回页面.
	}


	/**
	 * 转到卡片状态修改
	 * 
	 * @return
	 */
	public String toOrderCardOperator() {
		edit();
		return "toOrderCardOperator";
	}

	/**
	 * 修改卡状态
	 * 
	 * @return
	 */
	public String orderCardOperator() {
		sellOrderOrigCardListDTO.setOrigcardListIds(origCardListStr);
		sellOrderOrigCardListDTO.setOrderId(sellOrderDTO.getOrderId());
		sendService(ConstCode.RANSOM_ORDER_CARD_STATE_MODIFY,
				sellOrderOrigCardListDTO).getDetailvo();

		if (hasActionErrors() || hasErrors()) {
			return toOrderCardOperator();
		}

		addActionMessage("状态修改成功");
		return toOrderCardOperator();
	}
	
	/**
	 * 修改回收卡状态
	 * @return
	 */
	
	public String orderCardOperators() {
		sellOrderOrigCardListDTO.setOrigcardListIds(origCardListStr);
		sellOrderOrigCardListDTO.setOrderId(sellOrderDTO.getOrderId());
		sendService(ConstCode.RANSOM_ORDER_CARD_STATE_CALLBACK,
				sellOrderOrigCardListDTO).getDetailvo();

		if (hasActionErrors() || hasErrors()) {
			return toConfirm();
		}

		addActionMessage("操作成功");
		return toConfirm();
	}

	/**
	 * 提交
	 */
    public String toConfirm(){
    	actionName="ransomOrderAction";
		message = "提交";
		operation = "confirm";
		actionMethodName="toConfirm";
		sellOrderDTO.setPerFlag("1");
		try {
			view();
			if (hasErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "confirm";
    }
	/**
	 * 确认
	 *
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
    public String confirm(){
    	try {
			sendService(ConstCode.RANSOM_ORDER_CARD_SUBMIT_ORDER, sellOrderDTO)
					.getDetailvo();

			if (!this.hasErrors()) {
				this.addActionMessage("退回订单成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
    }
    
	/**
	 * 退回
	 * 
	 * @return
	 */
	public String rejectToCardFileMake() {
		actionName="ransomOrderAction";
		message = "退回";
		operation = "reject";
		sellOrderDTO.setPerFlag("1");
		try {
			view();
			if (hasErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "button";
	}

	/**
	 * 确认退回
	 * 
	 * @return
	 */
	public String reject() {
		try {
			sendService(ConstCode.RANSOM_ORDER_CARD_BACK_CHECK, sellOrderDTO)
					.getDetailvo();

			if (!this.hasErrors()) {
				this.addActionMessage("退回订单成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	@Override
	protected void init() {
		/*订单状态：草稿*/
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	@Override
	protected void initOrderType() {
		/*订单类型：赎回订单*/
		sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_RANSOM);
	}
	
	/**
     * 将原卡信息插入到原卡信息表中 <br>
     */
    public void insertCheckCardNew() {
     // 得到卡信息.
        // 到后台验证卡信息有效.
        sellOrderOrigCardListDTO = (SellOrderOrigCardListDTO) sendService(
                ConstCode.RANSOM_CHECK_CARD_INSERT,
                sellOrderOrigCardListDTO).getDetailvo();
        Map<String, String> map = null;
        if (sellOrderOrigCardListDTO == null) {
            map = new HashMap<String, String>();
            map.put("checkCardErr", "添加失败");
        } else {
            map = sellOrderOrigCardListDTO.getCheckedOneCard();
            map.put("checkCardErr", "0");
        }
        if (hasActionErrors() || hasErrors()) {
            Collection<String> errors = getActionErrors();
            map.put("checkCardErr", errors.toString());
        }
        // json返回页面单条卡信息
        try {
            getResponse().setContentType("application/json; charset=utf-8");
            getResponse().setCharacterEncoding("utf-8");
            getResponse().getWriter().println(
                    JSONObject.fromObject(map).toString());
            getResponse().getWriter().flush();
            getResponse().getWriter().close();
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            map.put("checkCardErr", e.getMessage() + " 请刷新页面再试");
        }
        // 不需要返回页面.
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

	public int getOrderCardList_totalRows() {
		return orderCardList_totalRows;
	}

	public void setOrderCardList_totalRows(int orderCardListTotalRows) {
		orderCardList_totalRows = orderCardListTotalRows;
	}

	public List<Map<String, Object>> getOrderCardList() {
		return orderCardList;
	}

	public void setOrderCardList(List<Map<String, Object>> orderCardList) {
		this.orderCardList = orderCardList;
	}

	public String getMessage() {
		return message;
	}




	
}
