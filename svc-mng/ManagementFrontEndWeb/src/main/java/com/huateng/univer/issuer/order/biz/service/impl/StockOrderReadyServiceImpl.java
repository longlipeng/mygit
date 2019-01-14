package com.huateng.univer.issuer.order.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderReadyResultDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.issuer.order.biz.service.StockOrderReadyService;
import com.huateng.univer.order.business.service.OrderCardListService;

/**
 * 订单准备service
 * 
 * @author xxl
 * 
 */
public class StockOrderReadyServiceImpl implements StockOrderReadyService {

	private Logger logger = Logger.getLogger(this.getClass());
	private SellOrderDAO sellOrderDAO;
	private EntityStockDAO entityStockDAO;
	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDAO;
	private CommonsDAO commonsDAO;
	private SellOrderListDAO sellOrderListDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private StockOrderCommonService stockOrderCommonService;
	private OrderCardListService orderCardListService;
	private EntityStockService entityStockService;

	/**
	 * 查看订单明细和卡明细
	 */
	public SellOrderReadyResultDTO getOrderReadyList(
			SellOrderReadyQueryDTO orderReadyQueryDTO)
			throws BizServiceException {
		SellOrderReadyResultDTO orderReadyDTO = new SellOrderReadyResultDTO();
		try {
			SellOrderListQueryDTO orderListQueryDTO = orderReadyQueryDTO
					.getOrderListQueryDTO();
			SellOrderCardListQueryDTO orderCardListQueryDTO = orderReadyQueryDTO
					.getOrderCardListQueryDTO();

			PageDataDTO orderLists = stockOrderCommonService
					.getOrderList(orderListQueryDTO);
			PageDataDTO orderCardLists = orderCardListService
					.getOrderCardListPageData(orderCardListQueryDTO);

			orderReadyDTO.setOrderLists(orderLists);
			orderReadyDTO.setOrderCardLists(orderCardLists);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单明细失败！");
		}
		return orderReadyDTO;
	}

	/**
	 * 订单准备,根据订单所有未准备完成的明细查库存
	 */
	public PageDataDTO getStockCardForReady(
			EntityStockQueryDTO entityStockQueryDTO) throws BizServiceException {
		PageDataDTO pageDataDTO = null;
		try {
			entityStockQueryDTO.setLastRowNum("all");
			pageDataDTO = pageQueryDAO.query(
					"STOCK_ORDER.selectCardStockForReady", entityStockQueryDTO);

			/** 结果不为空时，取符合当前条件(去除分页)的首张末张卡 */
			if (pageDataDTO != null && pageDataDTO.getTotalRecord() > 0) {
				entityStockQueryDTO.setLastRowNum("min");
				entityStockQueryDTO.setQueryAll(true);
				PageDataDTO minCardDataDTO = pageQueryDAO.query(
						"STOCK_ORDER.selectCardStockForReady",
						entityStockQueryDTO);
				List<HashMap<String, String>> lstDataHashMaps = (List<HashMap<String, String>>) pageDataDTO
						.getData();
				lstDataHashMaps.add((HashMap<String, String>) minCardDataDTO
						.getData().get(0));
				entityStockQueryDTO.setLastRowNum("max");
				entityStockQueryDTO.setQueryAll(true);
				PageDataDTO maxCardDataDTO = pageQueryDAO.query(
						"STOCK_ORDER.selectCardStockForReady",
						entityStockQueryDTO);
				lstDataHashMaps.add((HashMap<String, String>) maxCardDataDTO
						.getData().get(0));
				pageDataDTO.setData(lstDataHashMaps);
				pageDataDTO.setTotalRecord(pageDataDTO.getTotalRecord() + 2);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看库存失败！");
		}
		return pageDataDTO;
	}

	/**
	 * 订单准备,添加指定卡号段到订单卡明细
	 */
	public void readyStockCardToCardList(EntityStockDTO entityStockDTO)
			throws BizServiceException {
		try {
			try {
				sellOrderDAO.selectByPrimaryKeyLockNowait(entityStockDTO
						.getOrderId());
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("订单正在准备中.");
			}

			long time = System.currentTimeMillis();
			logger.debug("开始准备");
			// 基本信息
			SellOrder sellOrder = stockOrderCommonService
											.getSellOrderById(entityStockDTO.getOrderId());
			entityStockDTO.setProductId(sellOrder.getProductId());
			// 符合条件的库存
			List<EntityStock> stockList = stockOrderCommonService
					.getStockForUpdate(entityStockDTO);
			// 待出库的卡
			List<EntityStock> stockReadyList = new ArrayList<EntityStock>();
			// 要添加的订单卡明细
			List<SellOrderCardList> orderCardLists = new ArrayList<SellOrderCardList>();
			// 要准备的卡明细
			List<SellOrderList> readyCardLists = stockOrderCommonService
					.getReadyOrderList(entityStockDTO);
			// 要更新卡实际数量的卡明细
			List<SellOrderList> toUpdateCardLists = new ArrayList<SellOrderList>();
			List<String> cardNoList = new ArrayList<String>();
			SellOrderCardListExample ex=new SellOrderCardListExample();
            ex.createCriteria().andOrderIdEqualTo(entityStockDTO.getOrderId())
            .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
            int countNum=sellOrderCardListDAO.countByExample(ex);
//			if (readyCardLists == null || readyCardLists.size() == 0) {
//				throw new BizServiceException("该订单已准备完成，请不要重复准备");
//			}
			for (SellOrderList sellOrderList : readyCardLists) {
			    Integer realAmt =0;
//				Integer cardAmt = new Integer(sellOrderList.getCardAmount());
			    if(sellOrderList.getRealAmount()==null||"".equals(sellOrderList.getRealAmount())){
			        realAmt =0;
			    }
			    realAmt = new Integer(sellOrderList.getRealAmount());
				 if(countNum==realAmt.intValue()){
				     throw new BizServiceException("该订单已准备完成，请不要重复准备");
		            }
                //用来计算准备的卡片数字；
				Integer count=new Integer(countNum);
				String cardLayoutId = sellOrderList.getCardLayoutId();
				String faceValueType = sellOrderList.getFaceValueType();
				String faceValue = sellOrderList.getFaceValue();
				//本次优化，订单准备时以配送数量为准，每次准备时必须准备这么多数量的卡。
//				if (cardAmt - realAmt == 0) {
//					throw new BizServiceException("该订单已准备完成，请不要重复准备 ");
//				}
//				if (stockList.size() > (cardAmt - realAmt)
//						&& entityStockDTO.getCardNoArray() != null) {
//					Integer reallyAmount = cardAmt - realAmt;
//					throw new BizServiceException("所选卡片张数不应大于  " + reallyAmount
//							+ " 张,请重新准备! ");
//				}
				if(realAmt.intValue()==0){
				    throw new BizServiceException("该订单没有配送张数！");
				}
				if (stockList.size()+countNum> realAmt.intValue()
                      && entityStockDTO.getCardNoArray() != null) {
//                  Integer reallyAmount = cardAmt - realAmt;
                  throw new BizServiceException("所选卡片张数应为  " + (realAmt.intValue()-countNum)
                          + " 张,请重新准备！");
              }
//                if(stockList.size()+countNum>realAmt){
//                   throw new BizServiceException("所选卡片张数应为  " + (realAmt.intValue()-countNum)
//                            + " 张,请重新准备! ");
//                }
				// 每个明细要求的卡数量
				// while ((cardAmt - realAmt) > 0) {
				// boolean flag = true;66
				Iterator<EntityStock> stockIterator = stockList.iterator();
				while (stockIterator.hasNext()) {
					EntityStock stock = stockIterator.next();
					SellOrderCardList orderCardList = new SellOrderCardList();

					if ((stock.getFaceValueType().equals(faceValueType) && stock
							.getCardLayoutId().equals(cardLayoutId))
							&& ((OrderConst.FACE_VALUE_TYPE_STATIC
									.equals(faceValueType) && stock
									.getFaceValue().equals(faceValue)) || (OrderConst.FACE_VALUE_TYPE_NOT_STATIC
									.equals(faceValueType)))) {
						orderCardList.setOrderListId(sellOrderList
								.getOrderListId());
						orderCardList
								.setOrderCardListId(commonsDAO
										.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
						orderCardList.setCardNo(stock.getCardNo());
						cardNoList.add(stock.getCardNo());
						orderCardList
								.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);
						orderCardList.setOrderId(entityStockDTO.getOrderId());
						orderCardList.setCreateUser(entityStockDTO
								.getLoginUserId());
						orderCardList.setCreateTime(DateUtil.getCurrentTime());
						orderCardList.setModifyUser(entityStockDTO
								.getLoginUserId());
						orderCardList.setModifyTime(DateUtil.getCurrentTime());
						orderCardList
								.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

						orderCardLists.add(orderCardList);

						stock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
						stockReadyList.add(stock);
						// 删掉集合中已准备的卡
						stockIterator.remove();
						//realAmt++;
						count++;
						// logger.debug("卡实际数量："+realAmt.toString());
						if (realAmt.intValue()==count.intValue()) {
							break;
						}
						// flag = false;
					}
				}
				// 循环一次没有就跳出循环
				// if (flag) {
				// break;
				// }
				// }
				// 修改明细实际卡数量，这次优化不需要
//				sellOrderList.setRealAmount(realAmt.toString());
				toUpdateCardLists.add(sellOrderList);
			}
			commonsDAO.batchInsert(
					"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
					orderCardLists);
			commonsDAO
					.batchUpdate(
							"TB_ENTITY_STOCK.abatorgenerated_updateByPrimaryKeySelective",
							stockReadyList);
			commonsDAO
					.batchUpdate(
							"TB_SELL_ORDER_LIST.abatorgenerated_updateByPrimaryKeySelective",
							toUpdateCardLists);
			logger.debug("准备共耗时：" + (System.currentTimeMillis() - time) / 1000
					+ " 秒");
			// 库存操作日志：准备-->待出库
			entityStockService.addStockOperaterRecord(cardNoList,
					entityStockDTO.getOrderId(), entityStockDTO
							.getDefaultEntityId(), Const.FUNCTION_ROLE_ISSUER,
					OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT,
					entityStockDTO.getLoginUserId());
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("订单准备失败！");
		}
	}

	/**
	 * 删除已准备的订单卡明细
	 */
	public void deleteReadCardList(SellOrderCardListDTO sellOrderCardListDTO)
			throws BizServiceException {

		try {
			// 删除订单卡明细
			SellOrderCardList cardList = stockOrderCommonService
					.getSellOrderCardListById(sellOrderCardListDTO
							.getOrderCardListId());
			cardList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			cardList.setModifyUser(sellOrderCardListDTO.getLoginUserId());
			cardList.setModifyTime(DateUtil.getCurrentTime());

			sellOrderCardListDAO.updateByPrimaryKeySelective(cardList);

			// 还原库存卡状态(卡号)
			String cardNo = cardList.getCardNo();
			EntityStockExample example = new EntityStockExample();
			example.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL)
					.andCardNoEqualTo(cardNo);
			EntityStock stock = entityStockDAO.selectByExample(example).get(0);
			stock.setStockState(OrderConst.CARD_STOCK_IN);
			stock.setModifyUser(sellOrderCardListDTO.getLoginUserId());
			stock.setModifyTime(DateUtil.getCurrentTime());

			entityStockDAO.updateByPrimaryKeySelective(stock);
			// 记录库存操作流程
			List<String> cardNoList = new ArrayList<String>();
			cardNoList.add(cardNo);
			entityStockService.addStockOperaterRecord(cardNoList,
					sellOrderCardListDTO.getOrderId(), sellOrderCardListDTO
							.getDefaultEntityId(), Const.FUNCTION_ROLE_ISSUER,
					OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_DELETE,
					OrderConst.CARD_STOCK_OPERATE_TYPE_IN, sellOrderCardListDTO
							.getLoginUserId());
            //本次优化不修改配送张数
//			SellOrderList orderList = stockOrderCommonService
//					.getSellOrderListById(cardList.getOrderListId());
//			Integer realAmt = new Integer(orderList.getRealAmount());
//			realAmt = realAmt - 1;
//			orderList.setRealAmount(realAmt.toString());
//			sellOrderListDAO.updateByPrimaryKeySelective(orderList);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单卡明细失败！");
		}

	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public EntityStockDAO getEntityStockDAO() {
		return entityStockDAO;
	}

	public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
		this.entityStockDAO = entityStockDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
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

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public OrderCardListService getOrderCardListService() {
		return orderCardListService;
	}

	public void setOrderCardListService(
			OrderCardListService orderCardListService) {
		this.orderCardListService = orderCardListService;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}

}
