package com.huateng.univer.order.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputCardListDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.order.business.service.OrderCardListService;

/**
 * 订单卡明细service
 * 
 * @author xxl
 * 
 */
public class OrderCardListServiceImpl implements OrderCardListService {
	private Logger logger = Logger.getLogger(this.getClass());
	private BaseDAO baseDAO;
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private StockOrderCommonService stockOrderCommonService;
	private SellOrderDAO sellOrderDAO;

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	/**
	 * 修改制卡完成
	 */
	public void makeCardComplate(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			String orderId = sellOrderDTO.getOrderId();
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			/**
			 * 
			 * 订单的处理方非本机构，则为本机构的采购订单，不允许修改制卡完成。
			 * */
			if (!sellOrder.getProcessEntityId().equals(
					sellOrderDTO.getDefaultEntityId())) {
				throw new BizServiceException("订单中的产品非本机构定义,无权做制卡完成!");
			}
			List<SellOrderCardList> orderCardLists = this
					.getOrderCardList(sellOrderDTO.getOrderId());
			setOrderCardListState(OrderConst.MAKE_CARD_STATE_SUCCESS,
					orderCardLists);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改制卡完成状态失败！");
		}
	}

	/**
	 * 更新采购订单卡列表制卡状态
	 */
	private void setOrderCardListState(String cardState,
			List<SellOrderCardList> orderCardLists) throws Exception {
		List<SellOrderCardList> cardLists = new ArrayList<SellOrderCardList>();
		for (SellOrderCardList orderCardList : orderCardLists) {
			orderCardList.setCardState(cardState);
			cardLists.add(orderCardList);
		}
		commonsDAO
				.batchUpdate(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKeySelective",
						cardLists);
	}

	/**
	 * 修改制卡状态
	 */
	public PageDataDTO getOrderCardListForCardState(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = null;
		try {
			String orderId = sellOrderCardListQueryDTO.getOrderId();
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			/**
			 * 
			 * 订单的处理方非本机构，则为本机构的采购订单，不允许修改制卡状态。
			 * */
			if (!sellOrder.getProcessEntityId().equals(
					sellOrderCardListQueryDTO.getDefaultEntityId())) {
				throw new BizServiceException("订单中的产品非本机构定义,无权修改制卡状态!");
			}
			Integer count = this.countUnSuccessCard(orderId);
			logger.debug(count.toString());
			if (count > 0) {
				throw new BizServiceException("订单：" + orderId
						+ " 中有制卡未完成的卡,不能修改制卡状态！");
			}

			pageDataDTO = getOrderCardListPageData(sellOrderCardListQueryDTO);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改制卡状态失败！");
		}
		return pageDataDTO;
	}
	
	private void checkBuyStockCardState(String orderId) throws Exception {
		List<SellOrderCardList> orderCardLists = this
				.getCardListForBuy(orderId);
		for (SellOrderCardList cardList : orderCardLists) {
			if (!OrderConst.MAKE_CARD_STATE_SUCCESS.equals(cardList
					.getCardState())
					&& !OrderConst.MAKE_CARD_STATE_FAILTURE.equals(cardList
							.getCardState())) {
				throw new BizServiceException("订单：" + orderId
						+ " 中有制卡未完成的卡,不能修改制卡状态！");
			}
		}
	}

	/**
	 * 查询订单卡明细
	 */
	public List<SellOrderCardList> getOrderCardList(String orderId)
			throws Exception {
		try {
			List<SellOrderCardList> orderCardLists = null;
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())) {
				orderCardLists = getCardListForStock(orderId);
			} else {
				// 记名采购订单
				orderCardLists = getCardListForBuy(orderId);
			}
			return orderCardLists;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单卡明细信息失败！");
		}
	}

	/**
	 * 开始验卡
	 */
	public void checkCard(SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException {
		try {

			SellOrderCardListExample example = new SellOrderCardListExample();
			example.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andCardNoEqualTo(
					sellOrderCardListQueryDTO.getCardNo());
			if (sellOrderCardListDAO.countByExample(example) <= 0) {
				throw new BizServiceException("验卡失败！");
			} else {
				SellOrderCardList sellOrderCardList = new SellOrderCardList();
				SellOrderCardListExample sellOrderCardListExample = new SellOrderCardListExample();
				sellOrderCardListExample.createCriteria().andCardNoEqualTo(
						sellOrderCardListQueryDTO.getCardNo());
				sellOrderCardList
						.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);
				sellOrderCardList.setModifyUser(sellOrderCardListQueryDTO
						.getLoginUserId());
				sellOrderCardList.setModifyTime(DateUtil.getCurrentTime24());
				sellOrderCardListDAO.updateByExampleSelective(
						sellOrderCardList, sellOrderCardListExample);
			}

		} catch (BizServiceException e1) {

			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("验卡失败！");
		}
	}

	/**
	 * 结束验卡
	 */
	public void endCheckCard(SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException {
		try {

			SellOrderCardListExample example = new SellOrderCardListExample();
			// 得到数据状态,只修改制卡中的卡片
			example.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andCardStateEqualTo(
					OrderConst.MAKE_CARD_STATE_MAKING).andOrderIdEqualTo(
					sellOrderCardListQueryDTO.getOrderId());
			List<SellOrderCardList> sellOrderCardLists = sellOrderCardListDAO
					.selectByExample(example);
			for (SellOrderCardList sellOrderCardList : sellOrderCardLists) {
				SellOrderCardListExample sellOrderCardListExample = new SellOrderCardListExample();
				sellOrderCardListExample.createCriteria().andCardNoEqualTo(
						sellOrderCardList.getCardNo());
				sellOrderCardList
						.setCardState(OrderConst.MAKE_CARD_STATE_FAILTURE);
				sellOrderCardList.setModifyUser(sellOrderCardListQueryDTO
						.getLoginUserId());
				sellOrderCardList.setModifyTime(DateUtil.getCurrentTime24());
				sellOrderCardListDAO.updateByExampleSelective(
						sellOrderCardList, sellOrderCardListExample);

			}
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderCardListQueryDTO.getOrderId());
			sellOrder.setIsCheckCard("1");// 已验卡
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("结束验卡失败！");
		}
	}

	/**
	 * 判断是否已经验卡
	 * 
	 * @param sellOrderCardListQueryDTO
	 * @throws BizServiceException
	 */
	public SellOrderDTO hadCheckCard(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {

			SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO
					.getOrderId());
			ReflectionUtil.copyProperties(sellOrder, sellOrderDTO);
			return sellOrderDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("结束验卡失败！");
		}
	}

	/**
	 * 验卡是查询订单卡明细
	 */
	public PageDataDTO getOrderCardListForCheckcard(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = null;
		try {
			String orderId = sellOrderCardListQueryDTO.getOrderId();
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			/**
			 * 
			 * 订单的处理方非本机构，则为本机构的采购订单，不允许进行验卡。
			 * */
			if (!sellOrder.getProcessEntityId().equals(
					sellOrderCardListQueryDTO.getDefaultEntityId())) {
				throw new BizServiceException("订单中的产品非本机构定义,无权进行验卡!");
			}
			pageDataDTO = getOrderCardListPageData(sellOrderCardListQueryDTO);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单卡明细失败！");
		}
		return pageDataDTO;
	}

	/**
	 * 查询分页订单卡明细
	 */
	public PageDataDTO getOrderCardListPageData(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = null;
		try {
			String orderId = sellOrderCardListQueryDTO.getOrderId();
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())) {
				pageDataDTO = getCardListForStockPageData(sellOrderCardListQueryDTO);
			} else {
				// 记名采购订单
				pageDataDTO = getCardListForBuyPageData(orderId);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单卡明细信息失败！");
		}
		return pageDataDTO;
	}
	
	/**
	 * 查询分页订单录入卡明细
	 */
	public PageDataDTO getOrderInputCardListPageData(
			SellOrderInputCardListDTO sellOrderInputCardListDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = null;
		try {
			String orderId = sellOrderInputCardListDTO.getOrderId();
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())) {
				pageDataDTO = getInputCardListForStockPageData(sellOrderInputCardListDTO);
			} else {
				// 记名采购订单
				pageDataDTO = getCardListForBuyPageData(orderId);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单录入卡信息失败！");
		}
		return pageDataDTO;
	}

	// 库存订单明细
	public PageDataDTO getCardListForStockPageData(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query(
					"STOCK_ORDER_CARD_LIST.selectOrderCardListPageData",
					sellOrderCardListQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单卡明细信息失败！");
		}
	}
	
	// 订单卡录入列表
		public PageDataDTO getInputCardListForStockPageData(
				SellOrderInputCardListDTO sellOrderInputCardListDTO)
				throws BizServiceException {
			try {
				return pageQueryDAO.query(
						"STOCK_ORDER_CARD_LIST.selectOrderInputCardListPageData",
						sellOrderInputCardListDTO);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("查询订单录入卡失败！");
			}
		}

	// 采购订单明细
	@SuppressWarnings("unchecked")
    public PageDataDTO getCardListForBuyPageData(String orderId)
			throws BizServiceException {
		try {
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
			sellOrderCardListQueryDTO.setOrderId(orderId);
			List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
	        if(oldOrderIdOrderList.size() == 0){
	            sellOrderCardListQueryDTO.setOldOrderList(null);
	        }
	        else{
	            sellOrderCardListQueryDTO.setOldOrderList((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
	        }
			return pageQueryDAO.query(
							"STOCK_ORDER_CARD_LIST.selectOrderCardListPageDataIterator",
							sellOrderCardListQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单卡明细信息失败！");
		}
	}
	

	// 库存订单明细
	private List<SellOrderCardList> getCardListForStock(String orderId)
			throws Exception {
		SellOrderCardListExample example = new SellOrderCardListExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(orderId);
		return sellOrderCardListDAO.selectByExample(example);
	}

	// 采购订单明细
	@SuppressWarnings("unchecked")
	private List<SellOrderCardList> getCardListForBuy(String orderId)
			throws Exception {
		SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
		sellOrderCardListQueryDTO.setOrderId(orderId);

		List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
        if(oldOrderIdOrderList.size() == 0){
            sellOrderCardListQueryDTO.setOldOrderList(null);
        }
        else{
            sellOrderCardListQueryDTO.setOldOrderList((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
        }
		return baseDAO.queryForList(
				"STOCK_ORDER_CARD_LIST.selectOrderCardListIterator",
				sellOrderCardListQueryDTO);
	}

	/**
	 * 获取制卡完成的卡号
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSuccessCardNoList(String orderId)
			throws BizServiceException {
		List<String> cardNoList = null;
		try {
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrder
							.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrder.getOrderType())) {
				cardNoList = baseDAO.queryForList(
						"STOCK_ORDER_CARD_LIST.selectStockSuccessCardNoList",
						orderId);
			} else {
				// 记名采购订单
			    SellOrderDTO sellOrderDTO = new SellOrderDTO();
		        sellOrderDTO.setOrderId(orderId);
		        List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
		        if(oldOrderIdOrderList.size() == 0){
		            sellOrderDTO.setOrderIds(null);
		        }
		        else{
		            sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
		        }
				cardNoList = baseDAO.queryForList(
						"STOCK_ORDER_CARD_LIST.selectBuySuccessCardNoList",
						sellOrderDTO);
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡号失败！");
		}
		return cardNoList;

	}

	/**
	 * 查看制卡未完成数量
	 */
	public Integer countUnSuccessCard(String orderId)
			throws BizServiceException {
		Integer count = null;
		try {
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())) {
				count = (Integer) baseDAO.queryForObject(
						"STOCK_ORDER_CARD_LIST.countStockOrderUnSuccessCard",
						orderId);
			} else {
				// 记名采购订单
			    SellOrderDTO sellOrderDTO = new SellOrderDTO();
		        sellOrderDTO.setOrderId(orderId);
		        List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
		        if(oldOrderIdOrderList.size() == 0){
		            sellOrderDTO.setOrderIds(null);
		        }
		        else{
		            sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
		        }
				count = (Integer) baseDAO.queryForObject(
						"STOCK_ORDER_CARD_LIST.countBuyOrderUnSuccessCard",
						sellOrderDTO);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return count;

	}

	public Integer countFailCard(String orderId) throws BizServiceException {
		Integer count = null;
		try {
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())) {
				count = (Integer) baseDAO.queryForObject(
						"STOCK_ORDER_CARD_LIST.countStockOrderFailCard",
						orderId);
			} else {
				// 记名采购订单
			    SellOrderDTO sellOrderDTO = new SellOrderDTO();
                sellOrderDTO.setOrderId(orderId);
                List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
                if(oldOrderIdOrderList.size() == 0){
                    sellOrderDTO.setOrderIds(null);
                }
                else{
                    sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
                }
				count = (Integer) baseDAO.queryForObject(
						"STOCK_ORDER_CARD_LIST.countBuyOrderFailCard", sellOrderDTO);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return count;
	}

	public SellOrderCardListDTO view(SellOrderCardListDTO sellOrderCardListDTO)
			throws BizServiceException {
		try {
			SellOrderCardList orderCardList = sellOrderCardListDAO
					.selectByPrimaryKey(sellOrderCardListDTO.getOrderListId());
			ReflectionUtil.copyProperties(orderCardList, sellOrderCardListDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看订单卡明细信息失败！");
		}
		return sellOrderCardListDTO;
	}

	public void update(SellOrderCardListDTO sellOrderCardListDTO)
			throws BizServiceException {
		try {
			SellOrderCardList orderCardList = new SellOrderCardList();
			ReflectionUtil.copyProperties(sellOrderCardListDTO, orderCardList);
			orderCardList.setModifyUser(sellOrderCardListDTO.getLoginUserId());
			orderCardList.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardListDAO.updateByPrimaryKeySelective(orderCardList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单卡明细信息失败！");
		}
	}

	/**
	 * 删除订单对应的所有卡明细列表
	 */
	public void deleteCardListByOrderId(String orderId) throws Exception {
		try {
			SellOrderCardListExample example = new SellOrderCardListExample();
			example.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(
					orderId);
			SellOrderCardList sellOrderCardList = new SellOrderCardList();
			sellOrderCardList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			sellOrderCardListDAO.updateByExampleSelective(sellOrderCardList,
					example);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	/**
	 * 查看采购订单卡接收明细
	 */
	public PageDataDTO getOrderReceiveListPageData(OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO) throws BizServiceException {
	    PageDataDTO pageDataDTO = null;
        try{
            String orderId = orderReceiveCardListQueryDTO.getOrderId();
            SellOrder sellOrder = stockOrderCommonService
                    .getSellOrderById(orderId);
            if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
                            .equals(sellOrder.getOrderType())
                    || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
                            .equals(sellOrder.getOrderType())) {
                pageDataDTO  = getCardReceiveListForPageData(orderReceiveCardListQueryDTO);
            }
        }catch(Exception e){
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看卡接收明细失败!");
        }
	    return pageDataDTO;
   
	}
	

    // 卡接收明细
    public PageDataDTO getCardReceiveListForPageData(
            OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO)
            throws BizServiceException {
        try {
            PageDataDTO pageDataDTO = pageQueryDAO.query(
                    "STOCK_ORDER_CARD_LIST.selectOrderCardReceiveListPageData",
                    orderReceiveCardListQueryDTO);
            return pageDataDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡接收明细信息失败！");
        }
    }
	/**
	 * 迭代查询采购订单原订单明细
	 */
	/*
	 * public List<SellOrderCardList> recursiveOrderCardList11111(String
	 * orderId) throws Exception { List<SellOrderCardList> cardLists = new
	 * ArrayList<SellOrderCardList>();
	 * 
	 * UnionOrderExample example = new UnionOrderExample();
	 * example.createCriteria().andNewOrderEqualTo(orderId);
	 * 
	 * List<UnionOrder> unionOrders = unionOrderDAO.selectByExample(example);
	 * for (UnionOrder unionOrder : unionOrders) { if
	 * (OrderConst.IS_NOT_LEAF_NODE.equals(unionOrder.getLeafNode())) {
	 * this.recursiveOrderCardList(unionOrder.getOldOrder()); } else { cardLists
	 * = getOrderCardListsByOrderId(unionOrder.getOldOrder());
	 * orderCardLists.addAll(cardLists); } }
	 * 
	 * return orderCardLists; }
	 */

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

   

}
