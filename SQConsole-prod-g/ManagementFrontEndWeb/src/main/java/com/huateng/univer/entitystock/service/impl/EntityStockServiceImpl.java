package com.huateng.univer.entitystock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.EntityStockOperaterDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockOperater;
import com.huateng.framework.ibatis.model.EntityStockOperaterExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.order.business.service.OrderCardListService;

/**
 * 库存信息service
 * 
 * @author xxl
 * 
 */
public class EntityStockServiceImpl implements EntityStockService {
	private Logger logger = Logger.getLogger(EntityStockServiceImpl.class);
	private EntityStockDAO entityStockDAO;
	private OrderCardListService orderCardListService;
	private CommonsDAO commonsDAO;
	private EntityStockOperaterDAO entityStockOperaterDAO;
	private BaseDAO baseOrderDAO;

	/**
	 * 修改过卡库存状态
	 * 
	 * @param orderId
	 * @param currentStockState
	 * 
	 *            当前卡库存状态
	 * @param newStockState
	 *            新库存状态
	 * @param stockCardNoList
	 *            要更新的卡号列表
	 * @throws Exception
	 */
	public void modifyStockState(List<String> cardNos,
			String currentStockState, String newStockState) throws Exception {
		if (null != cardNos && cardNos.size() > 0) {
			// EntityStockExample example = new EntityStockExample();
			// example.createCriteria().andDataStateEqualTo(
			// DataBaseConstant.DATA_STATE_NORMAL).andCardNoIn(cardNos)
			// .andStockStateEqualTo(currentStockState);
			// EntityStock stock = new EntityStock();
			// stock.setStockState(newStockState);
			// entityStockDAO.updateByExampleSelective(stock, example);

			// batchUpdate @ 2011-07-04
			List<EntityStock> entityStocks = new ArrayList<EntityStock>();
			for (String cardNo : cardNos) {
				EntityStock eachEntityStock = new EntityStock();
				eachEntityStock.setCardNo(cardNo);
				eachEntityStock.setStockState(newStockState);
				entityStocks.add(eachEntityStock);
			}
			commonsDAO
					.batchUpdate(
							"TB_ENTITY_STOCK.abatorgenerated_updateByPrimaryKeySelective",
							entityStocks);
		}
	}

	/**
	 * 修改库存状态
	 * 
	 * @param orderId
	 * @param newEntityId
	 * @param currentStockState
	 * @param newStockState
	 * @throws Exception
	 */
	public void modifyStockStateAndEntity(List<String> cardNos,
			String newEntityId, String currentStockState, String newStockState)
			throws Exception {
		if (null != cardNos && cardNos.size() > 0) {
			// EntityStockExample example = new EntityStockExample();
			// example.createCriteria().andDataStateEqualTo(
			// DataBaseConstant.DATA_STATE_NORMAL).andCardNoIn(cardNos)
			// .andStockStateEqualTo(currentStockState);
			// EntityStock stock = new EntityStock();
			// stock.setEntityId(newEntityId);
			// stock.setFunctionRoleId(Const.FUNCTION_ROLE_SELLER);
			// stock.setStockState(newStockState);
			// entityStockDAO.updateByExampleSelective(stock, example);

			List<EntityStock> entityStocks = new ArrayList<EntityStock>();
			Date date=DateUtil.getCurrentDateAndTime();
			for (String cardNo : cardNos) {
				EntityStock eachEntityStock = new EntityStock();
				eachEntityStock.setCardNo(cardNo);
				eachEntityStock.setStockState(newStockState);
				eachEntityStock.setFunctionRoleId(Const.FUNCTION_ROLE_SELLER);
				eachEntityStock.setEntityId(newEntityId);
				eachEntityStock.setFldResData2(String.valueOf(date));
				entityStocks.add(eachEntityStock);
			}
			commonsDAO
					.batchUpdate(
							"TB_ENTITY_STOCK.abatorgenerated_updateByPrimaryKeySelective",
							entityStocks);
		}
	}

	/**
	 * 添加库存记录
	 * 
	 * @param cardNos
	 * @param sellOrder
	 * @throws Exception
	 */
	public void addStockRecord(List<String> cardNos, SellOrder sellOrder,
			String entityId, String operateUser) throws Exception {
		List<EntityStock> stockList = new ArrayList<EntityStock>();
		for (String cardNo : cardNos) {
			EntityStock stock = new EntityStock();
			stock.setCardNo(cardNo);
			stock.setEntityId(entityId);
			stock.setFunctionRoleId(Const.FUNCTION_ROLE_ISSUER);
			stock.setCardLayoutId(sellOrder.getCardLayoutId());
			stock.setFaceValueType(sellOrder.getFaceValueType());
			stock.setFaceValue(sellOrder.getFaceValue());
			stock.setProductId(sellOrder.getProductId());
			stock.setCardValidDate(sellOrder.getValidityPeriod());
			stock.setStockState(OrderConst.CARD_STOCK_IN);
			stock.setCreateUser(operateUser);
			stock.setCreateTime(DateUtil.getCurrentTime());
			stock.setModifyUser(operateUser);
			stock.setModifyTime(DateUtil.getCurrentTime());
			stock.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			stockList.add(stock);
		}
		commonsDAO.batchInsert("TB_ENTITY_STOCK.abatorgenerated_insert",
				stockList);
	}

	public void deleteStockOperaterRecord(List<String> cardNos, String orderId)
			throws Exception {
		try {
			for (String card : cardNos) {
				EntityStockOperaterExample entityStockOperaterExample = new EntityStockOperaterExample();
				entityStockOperaterExample.createCriteria().andCardNoEqualTo(
						card).andOrderIdEqualTo(orderId).andDataStateEqualTo(
						"1").andStockStateEqualTo("2").andOrderFlowNodeEqualTo(
						OrderConst.ORDER_FLOW_STOCK_ALLOCATE);
				baseOrderDAO.deleteByExample("TB_ENTITY_STOCK_OPERATER",
						entityStockOperaterExample);
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	}

	/**
	 * 添加库存操作日志信息
	 * 
	 * @param cardNos
	 * @param operaterState
	 * @throws BizServiceException
	 */
	public void addStockOperaterRecord(List<String> cardNos, String orderId,
			String entityId, String functionRoleId, String orderFlowNode,
			String operaterType, String stockState, String operateUser)
			throws Exception {
		List<EntityStockOperater> stockOperaterList = new ArrayList<EntityStockOperater>();
		for (String cardNo : cardNos) {
			EntityStockOperater stockOperater = new EntityStockOperater();
			stockOperater.setOperaterId(commonsDAO
					.getNextValueOfSequence("TB_ENTITY_STOCK_OPERATER"));
			stockOperater.setEntityId(entityId);
			stockOperater.setFuncationRoleId(functionRoleId);
			stockOperater.setOperaterType(operaterType);
			stockOperater.setOrderFlowNode(orderFlowNode);
			stockOperater.setStockState(stockState);
			stockOperater.setCardNo(cardNo);
			stockOperater.setOrderId(orderId);
			stockOperater.setCreateUser(operateUser);
			stockOperater.setCreateTime(DateUtil.getCurrentTime());
			stockOperater.setModifyUser(operateUser);
			stockOperater.setModifyTime(DateUtil.getCurrentTime());
			stockOperater.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			stockOperaterList.add(stockOperater);
		}
		commonsDAO.batchInsert(
				"TB_ENTITY_STOCK_OPERATER.abatorgenerated_insert",
				stockOperaterList);
	}
	
	/**
	 * 添加单条库存操作日志信息
	 * @param cardNo
	 * @param orderId
	 * @param entityId
	 * @param functionRoleId
	 * @param orderFlowNode
	 * @param operaterType
	 * @param stockState
	 * @param operateUser
	 * @throws Exception
	 */
	public void addStockOperaterRecord(String cardNo, String orderId,
			String entityId, String functionRoleId, String orderFlowNode,
			String operaterType, String stockState, String operateUser)
			throws Exception {
			EntityStockOperater stockOperater = new EntityStockOperater();
			stockOperater.setOperaterId(commonsDAO
					.getNextValueOfSequence("TB_ENTITY_STOCK_OPERATER"));
			stockOperater.setEntityId(entityId);
			stockOperater.setFuncationRoleId(functionRoleId);
			stockOperater.setOperaterType(operaterType);
			stockOperater.setOrderFlowNode(orderFlowNode);
			stockOperater.setStockState(stockState);
			stockOperater.setCardNo(cardNo);
			stockOperater.setOrderId(orderId);
			stockOperater.setCreateUser(operateUser);
			stockOperater.setCreateTime(DateUtil.getCurrentTime());
			stockOperater.setModifyUser(operateUser);
			stockOperater.setModifyTime(DateUtil.getCurrentTime());
			stockOperater.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			entityStockOperaterDAO.insert(stockOperater);
	}

	/**
	 * 
	 * 查询库存操作记录
	 * 
	 * @author Yifeng.Shi
	 * @serialData 2011-06-27
	 * */
	public List<EntityStockOperater> queryEntityStockOperaters(
			EntityStockOperaterExample stockOperaterExample) throws Exception {
		return entityStockOperaterDAO.selectByExample(stockOperaterExample);
	}

	public EntityStockDAO getEntityStockDAO() {
		return entityStockDAO;
	}

	public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
		this.entityStockDAO = entityStockDAO;
	}

	public OrderCardListService getOrderCardListService() {
		return orderCardListService;
	}

	public void setOrderCardListService(
			OrderCardListService orderCardListService) {
		this.orderCardListService = orderCardListService;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public EntityStockOperaterDAO getEntityStockOperaterDAO() {
		return entityStockOperaterDAO;
	}

	public void setEntityStockOperaterDAO(
			EntityStockOperaterDAO entityStockOperaterDAO) {
		this.entityStockOperaterDAO = entityStockOperaterDAO;
	}

	public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}

	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}

}
