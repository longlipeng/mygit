/**
 * Classname ActBatchServiceImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wanglu		2013-1-21
 * =============================================================================
 */

package com.huateng.univer.businessbatch.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.ActBatchDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.order.business.bo.OrderBO;

/**
 * @author wanglu
 * 
 */
public class ActBatchServiceImpl extends BasicBatchServiceImpl {
	private Logger logger = Logger.getLogger(ActBatchServiceImpl.class);
	
	/**
	 * 公共工具类DAO
	 */

	private DataBaseDAO databaseDAO;
	private SellOrderDAO sellOrderDAO;
	private OrderBO orderBO;
	// 类型：激活
	private static final String BATCH_TYPE = "05";
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	/**
	 * 批次完成，更改订单状态 用于更改订单状态，进行相应的业务处理
	 */
	@Override
	protected boolean orderStateChange(BatchSum sum){
		// 查询订单信息
		String orderId=sum.getOrderId();
		if(orderId.endsWith(",")){
			orderId=orderId.split(",")[0];
		}
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(orderId);
		// 如果订单激活状态已经为激活状态,直接返回true
		if (null != sellOrder.getInitActStat()
				&& "1".equals(sellOrder.getInitActStat())) {
			return true;
		}
		// 检查批次状态
		// 失败总数 > 0 或者 成功总数 + 失败总数 != 总记录数, 将订单激活状态置为失败
		if (Long.parseLong(sum.getFailCnt().trim()) > 0
				|| Long.parseLong(sum.getSucCnt().trim())
						+ Long.parseLong(sum.getFailCnt().trim()) != Long
						.parseLong(sum.getTotCnt().trim())
				|| Long.parseLong(sum.getSucAmt().trim())
						+ Long.parseLong(sum.getFailAmt().trim()) != Long
						.parseLong(sum.getTotAmt().trim())) {

			sellOrder
					.setInitActStat(DataBaseConstant.ORDER_ACT_STATE_ACT_FAILED);
		} else {
			sellOrder.setInitActStat(DataBaseConstant.ORDER_ACT_STATE_ACT);
			try {
				orderBO.orderFlow(sellOrder.getOrderId(),
						sellOrder.getProcessEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						OrderConst.ORDER_FLOW_ORDER_ACTIVE, "", "0000");
			} catch (BizServiceException e) {
				this.logger.error(e.getMessage());
			}
		}
		logger.info("initactstat :" + sellOrder.getInitActStat());
		// 更新订单状态
		SellOrderExample updateDTO = new SellOrderExample();
		updateDTO.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());
		sellOrder.setInitActStat(sellOrder.getInitActStat());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDAO.updateByExampleSelective(sellOrder, updateDTO);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.univer.businessbatch.BusinessBatchService#commMessage(com
	 * .huateng.univer.businessbatch.dto.BatchFileDTO)
	 */
	@Override
	public void commMessage(BatchFileDTO batchFileDTO)
			throws BizServiceException {
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_ACTIVE);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setBatchNo(batchFileDTO.getBatchNo());
			// 批次信息 批次类型|总比数+'|'+总金额+'|'+剩余笔数+'|'+剩余金额
			accPackageDTO.setBatchFileInfo(BATCH_TYPE + "|"
					+ batchFileDTO.getTotCnt() + "|" + batchFileDTO.getTotAmt()
					+ "|" + 0 + "|" + 0);
			accPackageDTO = java2ACCBusinessService
					.sendRechargeMessage(accPackageDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.univer.businessbatch.BusinessBatchService#insertBatch(java
	 * .util.List)
	 */
	@Override
	public void insertBatch(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
		List<ActBatchDTO> list = new ArrayList<ActBatchDTO>();
		for (BatchParamInterface obj : businessList) {
			SellOrderListDTO dto = (SellOrderListDTO) obj;
			ActBatchDTO actBatchDTO = new ActBatchDTO();
			actBatchDTO.setTxnType(Const.TXN_TYPE_CODE_ACTIVE);
			actBatchDTO.setTxnSeq(dto.fetchSeq());
			actBatchDTO.setSmltDt(DateUtil.getCurrentDateStr());
			actBatchDTO.setTxnAmt("0");
			actBatchDTO.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
			actBatchDTO.setCardNo(dto.getCardNo());
			actBatchDTO.setCardState("1");
			actBatchDTO.setRecCrtTs(DateUtil.getCurrentTime());
			actBatchDTO.setRecUpdTs(DateUtil.getCurrentTime());
			list.add(actBatchDTO);
		}
		databaseDAO.batchInsert("ACT.insertActTxn", list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.framework.listener.batch.BatchListener#getBatchType()
	 */
	@Override
	public String getBatchType() {
		return BATCH_TYPE;
	}

	public DataBaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DataBaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

}
