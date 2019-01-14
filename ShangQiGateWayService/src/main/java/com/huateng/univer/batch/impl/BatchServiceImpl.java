package com.huateng.univer.batch.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.univer.batch.BatchService;

public class BatchServiceImpl implements BatchService {
	private BatchSumDAO batchSumDAO;
	private SellOrderDAO sellOrderDAO;
	private Logger logger = Logger.getLogger(BatchServiceImpl.class);
//	private SubmitOrderService submitOrderService;
	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDAO;
//	private BusinessBatchService rechargeActBatchService;
//	private BusinessBatchService rechargeBatchService;

	@Override
	public String getOrderState(SellOrderDTO sellOrderDTO) throws BizServiceException{
		List<String> states = new ArrayList<String>();
		states.add(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
		String result = null;
		if("act".equals(sellOrderDTO.getBatchType())){
			result = getActState(sellOrderDTO);
			return result;
		}else if("rechargeAct".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM);
		}else if ("recharge".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_CREDIT_SUCCESS);
		}else if("makeCard".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_CARDGFILE_MAKING);
		}else if ("ransom".equals(sellOrderDTO.getBatchType())){
			states.add(OrderConst.ORDER_STATE_ORDER_RANSOM_STOCK);
		}
		sellOrderDTO.setOrderStates(states);
		result = checkOrderState(sellOrderDTO);
		return result;
	}

	@Override
	public PageDataDTO rechargeActBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RECHARGE_ACT.getRechargeActState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}

	@Override
	public PageDataDTO rechargeBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RECHARGE.getRechargeState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}
	
	@Override
	public PageDataDTO makeCardBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"MAKE_CARD.getMakeCardState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}
	@Override
	public PageDataDTO ransomBatchDetailGet(
			BatchFileDetailDTO batchFileDetailDTO) throws BizServiceException {
		try {

			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"RANSOM.getRansomState", batchFileDetailDTO);
			return pageDataDTO;
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("查询批量明细失败");
		}
	}
	
	//获取激活状态
	private String getActState(SellOrderDTO sellOrderDTO){
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO
				.getOrderId());
		if (DataBaseConstant.ORDER_ACT_STATE_ACT.equals(sellOrder
				.getInitActStat())
				|| DataBaseConstant.ORDER_ACT_STATE_ACT_FAILED
						.equals(sellOrder.getInitActStat())) {
			return "success";
		}
		return null;
	}

	//检查订单状态
	private String checkOrderState(SellOrderDTO sellOrderDTO){
		SellOrderExample example = new SellOrderExample();
		example.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId()).andOrderStateIn(sellOrderDTO.getOrderStates());
		List<SellOrder> sellOrders =sellOrderDAO.selectByExample(example);
		if(null==sellOrders || sellOrders.size()<=0){
			return null;
		}
		return "success";
	}
	/**
	 * 批量充值 失败后换卡
	 */
	public int changeCardNo(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		int state ;
		try {
			state = baseDAO.update("RECHARGE.CHANGE_CARD", sellOrderQueryDTO);
		} catch (Exception e) {
			throw new BizServiceException("更新失败!");
		}
		return state;
	}
	
	public BatchSumDAO getBatchSumDAO() {
		return batchSumDAO;
	}

	public void setBatchSumDAO(BatchSumDAO batchSumDAO) {
		this.batchSumDAO = batchSumDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
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
	
//	public BusinessBatchService getRechargeActBatchService() {
//		return rechargeActBatchService;
//	}
//
//	public void setRechargeActBatchService(
//			BusinessBatchService rechargeActBatchService) {
//		this.rechargeActBatchService = rechargeActBatchService;
//	}
//
//	public BusinessBatchService getRechargeBatchService() {
//		return rechargeBatchService;
//	}
//
//	public void setRechargeBatchService(
//			BusinessBatchService rechargeBatchService) {
//		this.rechargeBatchService = rechargeBatchService;
//	}
//
//	public SubmitOrderService getSubmitOrderService() {
//		return submitOrderService;
//	}
//
//	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
//		this.submitOrderService = submitOrderService;
//	}

}
