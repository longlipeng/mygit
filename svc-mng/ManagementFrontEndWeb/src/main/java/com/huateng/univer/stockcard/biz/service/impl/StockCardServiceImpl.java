package com.huateng.univer.stockcard.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.stock.StockAllocateDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.stockcard.dto.StockCardNoDTO;
import com.allinfinance.univer.stockcard.dto.StockCardQueryDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.EntityStockOperaterDAO;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockOperater;
import com.huateng.framework.ibatis.model.EntityStockOperaterExample;
import com.huateng.framework.util.Amount;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.issuer.account.dao.IssuerServiceDao;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.stockcard.biz.service.StockCardService;

public class StockCardServiceImpl implements StockCardService {
	Logger logger = Logger.getLogger(StockCardServiceImpl.class);
	/** 分页DAO */
	private PageQueryDAO pageQueryDAO;
	private IssuerServiceDao issuerServiceDAO;
	private OrderBaseQueryBO orderBaseQueryBO;
	private CommonsDAO commonsDAO;
	private OrderBO orderBO;
	private EntityStockService entityStockService;
	private EntityStockDAO entityStockDAO;
	private EntityStockOperaterDAO entityStockOperaterDAO;
	private BaseDAO baseDAO;
	
	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public PageDataDTO query(StockCardQueryDTO dto) throws BizServiceException {
		try {
			PageDataDTO pd = pageQueryDAO.query(
					"STOCKCARD.selectStockCardByDTO", dto);
			return pd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询库存卡片失败！");
		}
	}
	
	//查询卡号
	public PageDataDTO view(StockCardNoDTO dto) throws BizServiceException {
		try {
			PageDataDTO pd = pageQueryDAO.query(
					"STOCKCARD.selectStockCardByProductId", dto);
			return pd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询库存卡片失败！");
		}
	}

	/**
	 * 库存调拨list
	 */
	public PageDataDTO allocateList(StockAllocateDTO stockAllocateDTO)
			throws BizServiceException {
		PageDataDTO pd = new PageDataDTO();
		try {
			pd = pageQueryDAO.query("STOCKCARD.selectAllocateList",
					stockAllocateDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询库存调拨记录失败！");
		}

		return pd;
	}

	/**
	 * 查询调拨机构 和调拨人员
	 */
	public SellOrderInputDTO selectAllocateOrgan(
			SellOrderInputDTO sellOrderInputDTO) throws Exception {
		SellOrderInputDTO inputDTO = new SellOrderInputDTO();
		try {
			inputDTO.setIssuerDTOs(issuerServiceDAO
					.getAllocateOrgan(sellOrderInputDTO.getDefaultEntityId()));
			inputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));
			StockAllocateDTO stockAllocateDTO = new StockAllocateDTO();
			stockAllocateDTO.setAllocateId(sellOrderInputDTO.getOrderId());
			inputDTO.setOrderList(pageQueryDAO.query("STOCKCARD.getOrderList",
					stockAllocateDTO));
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return inputDTO;
	}
	
	/**
	 * 修改卡状态(锁定和解锁)
	 */
	public StockCardNoDTO updateCardStockState(StockCardNoDTO stockCardNoDTO){
		String[] cardNoList = stockCardNoDTO.getCardNoList().split(",");
		String oldStockState;
		if(stockCardNoDTO.getStockState().equals("1")){
			oldStockState = "6";
		}else if(stockCardNoDTO.getStockState().equals("6")){
			oldStockState = "1";
		}else{
			return null;
		}
		
		//检查卡状态
		for(String cardNo : cardNoList){
			EntityStock entityStock  =entityStockDAO.selectByPrimaryKey(cardNo);
			//当卡原状态不正确时，返回该卡号
			if(!entityStock.getStockState().equals(oldStockState)){
				stockCardNoDTO.setCardNo(cardNo);
				return stockCardNoDTO;
			}
		}
		
		for(String cardNo : cardNoList){
			EntityStock record = new EntityStock();
			record.setCardNo(cardNo);
			record.setStockState(stockCardNoDTO.getStockState());
			
			entityStockDAO.updateByPrimaryKeySelective(record);
		}
		
		stockCardNoDTO.setCardNoList(null);
		return stockCardNoDTO;
		
	}
	
	public PageDataDTO readyCardNo(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		orderReadyDTO.setFaceValue(Amount.getDataBaseAmount(orderReadyDTO
				.getFaceValue()));
		PageDataDTO pd = orderBaseQueryBO.getCardNoforAllocate(orderReadyDTO);
		return pd;
	}

	public StockAllocateDTO getTableId(StockAllocateDTO stockAllocateDTO)
			throws BizServiceException {
		stockAllocateDTO.setAllocateId(commonsDAO
				.getNextValueOfSequence("TB_STOCK_CARD"));
		return stockAllocateDTO;
	}

	// 查询库存明细信息
	public StockAllocateDTO getStockAmount(StockAllocateDTO stockAllocateDTO)
			throws BizServiceException {
		try {
			stockAllocateDTO.setOrderList(pageQueryDAO.query(
					"STOCKCARD.getStockAmount", stockAllocateDTO));
			stockAllocateDTO.setDoCount(true);
			stockAllocateDTO.setOrderCardList(pageQueryDAO.query(
					"STOCKCARD.getOrderCardList", stockAllocateDTO));
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return stockAllocateDTO;
	}

	public void stockAllocateReturn(StockAllocateDTO stockAllocateDTO)
			throws Exception {
		try {
			List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			List<String> cardNoList = new ArrayList<String>();
			EntityStockOperaterExample example = new EntityStockOperaterExample();
			example.createCriteria().andOrderIdEqualTo(
					stockAllocateDTO.getAllocateId()).andStockStateEqualTo("2")
					.andDataStateEqualTo("1").andOrderFlowNodeEqualTo(
							OrderConst.ORDER_FLOW_STOCK_ALLOCATE)
					.andOperaterTypeEqualTo("1");
			List<EntityStockOperater> list = entityStockOperaterDAO
					.selectByExample(example);
			if (null != list && list.size() > 0) {
				for (EntityStockOperater entityStockOperater : list) {
					cardNoList.add(entityStockOperater.getCardNo());
					EntityStock entityStock = new EntityStock();
					entityStock.setCardNo(entityStockOperater.getCardNo());
					entityStock.setEntityId(stockAllocateDTO.getAllocateOut());
					entityStock.setStockState(OrderConst.CARD_STOCK_IN);
					entityStockList.add(entityStock);
				}
				orderBO.updateEntityStockByPrimaryKey(entityStockList);

				entityStockService.deleteStockOperaterRecord(cardNoList,
						stockAllocateDTO.allocateId);
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}

	}

	public StockAllocateDTO getCardList(StockAllocateDTO stockAllocateDTO)
			throws Exception {
		stockAllocateDTO.setOrderCardList(pageQueryDAO.query(
				"STOCKCARD.getView", stockAllocateDTO));
		return stockAllocateDTO;
	}

	public void cardArrayReady(OrderReadyDTO orderReadyDTO) throws Exception {
		List<String> cardNoList = new ArrayList<String>();
		if (orderReadyDTO.getCardNoArray() != null) {
			if (orderReadyDTO.cardNoArray.length > Integer
					.parseInt(orderReadyDTO.getRealAmount())) {
				throw new BizServiceException("所选卡片张数不应大于  "
						+ orderReadyDTO.getRealAmount() + " 张,请重新准备! ");
			}
			cardNoList = Arrays.asList(orderReadyDTO.getCardNoArray());
		}

		List<EntityStock> entityStockList = new ArrayList<EntityStock>();
		for (int i = 0; i < cardNoList.size(); i++) {
			EntityStock entityStock = new EntityStock();
			entityStock.setCardNo(cardNoList.get(i));
			entityStock.setEntityId(orderReadyDTO.processEntityId);
			entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
			entityStockList.add(entityStock);
		}
		orderBO.updateEntityStockByPrimaryKey(entityStockList);

		// 记录库存操作日志
		entityStockService
				.addStockOperaterRecord(cardNoList, orderReadyDTO.getOrderId(),
						orderReadyDTO.getProcessEntityId(),
						Const.FUNCTION_ROLE_ISSUER,
						OrderConst.ORDER_FLOW_STOCK_ALLOCATE,
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						OrderConst.CARD_STOCK_READY_OUT, orderReadyDTO
								.getLoginUserId());
	}

	public void stockAllocate(StockAllocateDTO stockAllocateDTO)
			throws Exception {
		try {
			List<String> cardNoList=new ArrayList<String>();
			EntityStockOperaterExample stockOperaterExample=new EntityStockOperaterExample();
			stockOperaterExample.createCriteria().andStockStateEqualTo(OrderConst.CARD_STOCK_READY_OUT).andOrderIdEqualTo(stockAllocateDTO.getAllocateId());
			List<EntityStockOperater> entityStockOperaters=entityStockOperaterDAO.selectByExample(stockOperaterExample);
			List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			for (int i = 0; i < entityStockOperaters.size(); i++) {
				EntityStock entityStock = new EntityStock();
				entityStock.setCardNo(entityStockOperaters.get(i).getCardNo());
				entityStock.setEntityId(stockAllocateDTO.getAllocateIn());
				entityStock.setStockState(OrderConst.CARD_STOCK_IN);
				entityStockList.add(entityStock);
				cardNoList.add(entityStockOperaters.get(i).getCardNo());
			}
			orderBO.updateEntityStockByPrimaryKey(entityStockList);
			
			// 记录库存操作日志
			entityStockService.addStockOperaterRecord(cardNoList,
					stockAllocateDTO.getAllocateId(), stockAllocateDTO
							.getAllocateOut(), Const.FUNCTION_ROLE_ISSUER,
					OrderConst.ORDER_FLOW_STOCK_ALLOCATE,
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.CARD_STOCK_OUT, stockAllocateDTO
							.getLoginUserId());
			entityStockService
					.addStockOperaterRecord(cardNoList, stockAllocateDTO
							.getAllocateId(), stockAllocateDTO.getAllocateIn(),
							Const.FUNCTION_ROLE_ISSUER,
							OrderConst.ORDER_FLOW_STOCK_ALLOCATE,
							OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
							OrderConst.CARD_STOCK_IN, stockAllocateDTO
									.getLoginUserId());
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	}

	public void deleteCardList(StockAllocateDTO stockAllocateDTO)
			throws Exception {
		try {
			List<String> cardNoList = new ArrayList<String>();
			cardNoList.add(stockAllocateDTO.getCardNo());
			List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			EntityStock entityStock = new EntityStock();
			entityStock.setCardNo(stockAllocateDTO.getCardNo());
			entityStock.setEntityId(stockAllocateDTO.getAllocateOut());
			entityStock.setStockState(OrderConst.CARD_STOCK_IN);
			entityStockList.add(entityStock);
			orderBO.updateEntityStockByPrimaryKey(entityStockList);

			entityStockService.deleteStockOperaterRecord(cardNoList,
					stockAllocateDTO.getAllocateId());
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}

	}
	
	public void cardSegementReady(OrderReadyDTO orderReadyDTO) throws BizServiceException{
		try {
		List<HashMap<String, String>> stockCardList = (List<HashMap<String, String>>)baseDAO.queryForList("STOCKCARD", "selectCardSegmentReady", orderReadyDTO);
		List<EntityStock> entityStockList = new ArrayList<EntityStock>();
		List<String> cardNoList = new ArrayList();
		for(HashMap<String,String> tempMap : stockCardList){
			EntityStock entityStock=new EntityStock();
			entityStock.setCardNo(tempMap.get("cardNo"));
			entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
			entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
			entityStockList.add(entityStock);
			cardNoList.add(tempMap.get("cardNo"));
		}
		
		orderBO.updateEntityStockByPrimaryKey(entityStockList);
		
		/*记录库存操作日志*/
		entityStockService.addStockOperaterRecord(cardNoList, orderReadyDTO
				.getOrderId(), orderReadyDTO.getProcessEntityId(),
				Const.FUNCTION_ROLE_ISSUER, OrderConst.ORDER_FLOW_STOCK_ALLOCATE,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_READY_OUT, orderReadyDTO
						.getLoginUserId());
		}catch (BizServiceException bse) {
			throw bse;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("全部准备失败");
		}
	}

	
	public IssuerServiceDao getIssuerServiceDAO() {
		return issuerServiceDAO;
	}

	public void setIssuerServiceDAO(IssuerServiceDao issuerServiceDAO) {
		this.issuerServiceDAO = issuerServiceDAO;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}

	public EntityStockDAO getEntityStockDAO() {
		return entityStockDAO;
	}

	public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
		this.entityStockDAO = entityStockDAO;
	}

	public EntityStockOperaterDAO getEntityStockOperaterDAO() {
		return entityStockOperaterDAO;
	}

	public void setEntityStockOperaterDAO(
			EntityStockOperaterDAO entityStockOperaterDAO) {
		this.entityStockOperaterDAO = entityStockOperaterDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

}
