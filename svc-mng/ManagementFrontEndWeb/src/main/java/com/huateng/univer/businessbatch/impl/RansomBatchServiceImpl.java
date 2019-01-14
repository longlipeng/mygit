/**
 * Classname RansomBatchServiceImpl.java
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
 *		wanglu		2013-1-4
 * =============================================================================
 */

package com.huateng.univer.businessbatch.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.businessbatch.dto.RansomBatchDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.MathUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.entitystock.service.EntityStockService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;

/**
 * @author wanglu
 * 
 */
public class RansomBatchServiceImpl extends BasicBatchServiceImpl {
	private Logger logger = Logger.getLogger(RansomBatchServiceImpl.class);
	/* 类型：批量赎回 */
	private static final String BATCH_TYPE = "06";
	private DataBaseDAO databaseDAO;
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private SellOrderDAO sellOrderDAO;
	private SellOrderOrigCardListDAO sellOrderOrigCardListDAO;
	private BaseDAO baseDAO;
	private OrderBaseQueryBO orderBaseQueryBO;
	private EntityStockService entityStockService;
	
	@Override
	public String getBatchType() {
		return BATCH_TYPE;
	}

	@Override
	protected boolean orderStateChange(BatchSum sum) {
		// 查询订单信息
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sum.getOrderId());
		// 如果订单状态已经为完成状态,直接返回true
		if (OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrder
				.getOrderState())
				|| OrderConst.ORDER_STATE_ORDER_RANSOM_STOCK.equals(sellOrder
						.getOrderState())) {
			return true;
		}
		// 获取成功总金额
		Long sucAmt = Long.parseLong(databaseDAO.queryForObject("RANSOM",
				"getSucAmt", sum.getBatchNo()).toString());
		// 检查批次状态
		// 失败总数 > 0 或者 成功总数 + 失败总数 != 总记录数, 将订单状态置为失败
		if (Long.parseLong(sum.getFailCnt().trim()) > 0
				|| Long.parseLong(sum.getSucCnt().trim())
						+ Long.parseLong(sum.getFailCnt().trim()) != Long
						.parseLong(sum.getTotCnt().trim())
				|| Long.parseLong(sum.getSucAmt().trim()) != sucAmt) {

			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
		} else {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_RANSOM_STOCK);
			cardStateChange(sum.getBatchNo(),sellOrder);
		}

		sellOrder = getOrderCountTotalPrice(sellOrder, sucAmt);
		// 更新订单状态
		SellOrderExample updateDTO = new SellOrderExample();
		updateDTO.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDAO.updateByExampleSelective(sellOrder, updateDTO);
		return true;
	}

	private SellOrder getOrderCountTotalPrice(SellOrder sellOrder, Long sucAmt) {
		String totalPrice = "0";
		String serviceFeeCount = "0";

		/**
		 * 除以100得到百分比
		 */
		String serviceFee = MathUtil.divide(sellOrder.getServiceFee(), "100")
				.toString();

		serviceFeeCount = MathUtil.multiply(sucAmt.toString(), serviceFee)
				.toString();

		/* 赎回总费用=服务总费+附加费-卡总额 */
		totalPrice = MathUtil.add(totalPrice, serviceFeeCount).toString();
		totalPrice = MathUtil
				.add(totalPrice, sellOrder.getAdditionalFee()).toString();
		totalPrice = MathUtil.subtract(totalPrice, sucAmt.toString())
				.toString();

		sellOrder.setPackageFee(serviceFeeCount);
		sellOrder.setOrigcardTotalamt(sucAmt.toString());
		sellOrder.setTotalPrice(new BigDecimal(totalPrice).abs().toString());
		return sellOrder;
	}

	@Override
	public void commMessage(BatchFileDTO batchFileDTO)
			throws BizServiceException {
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_RANSOM_ORDER);
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

	@Override 
	public void insertBatch(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
		List<RansomBatchDTO> list = new ArrayList<RansomBatchDTO>();
		for (BatchParamInterface param : businessList) {
			SellOrderOrigCardListDTO dto = (SellOrderOrigCardListDTO) param;
			RansomBatchDTO ransom = new RansomBatchDTO();
			ransom.setTxnSeq(dto.getTxnSeq());
			ransom.setTxnType("0006");
			ransom.setSmltDt(DateUtil.getCurrentDateStr());
			ransom.setTxnAmt(dto.getAmount());
			ransom.setTxnFee("0");
			ransom.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
			ransom.setCardNo(dto.getCardNo());
			ransom.setCallBack(dto.getCallBack());
			String cardState = dto.getCardSate();
			if (OrderConst.RANSON_ORIG_CARD_STAT_CHECK.equals(cardState)
					|| OrderConst.RANSON_ORIG_CARD_STAT_DESTORY
							.equals(cardState)) {
				ransom.setCancelFlag(OrderConst.RANSON_ORIG_CARD_BATCH_DESTORY);
			}/* else if (OrderConst.RANSON_ORIG_CARD_STAT_ENTSTOCK
					.equals(cardState)) {
				ransom.setCancelFlag(OrderConst.RANSON_ORIG_CARD_BATCH_ENTSTOCK);
			} */else {
				ransom.setCancelFlag(OrderConst.RANSON_ORIG_CARD_BATCH_ENTSTOCK);
			}
			ransom.setRecCrtTs(DateUtil.getCurrentTime());
			ransom.setRecUpdTs(DateUtil.getCurrentTime());
			list.add(ransom);
		}
		databaseDAO.batchInsert("RANSOM.insertRansomActTxn", list);
	}

	private void cardStateChange(String batchNo , SellOrder sellOrder){
		try {
			baseDAO.update("RANSOM.cardStateChange", batchNo);			
			List<RansomBatchDTO> cards = (List<RansomBatchDTO>)baseDAO.queryForList("RANSOM.getCardNos", batchNo);
			for(RansomBatchDTO dto : cards){
				// 库存操作日志：入库
				entityStockService.addStockOperaterRecord(dto.getCardNo(), sellOrder.getOrderId(),
						sellOrder.getProcessEntityId(), Const.FUNCTION_ROLE_SELLER,
						OrderConst.ORDER_STATE_ORDER_RANSOM_STOCK,
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						dto.getCancelState(), sellOrder
								.getModifyUser());
			}
		} catch (BizServiceException b) {
			logger.error(b.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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

	public SellOrderOrigCardListDAO getSellOrderOrigCardListDAO() {
		return sellOrderOrigCardListDAO;
	}

	public void setSellOrderOrigCardListDAO(
			SellOrderOrigCardListDAO sellOrderOrigCardListDAO) {
		this.sellOrderOrigCardListDAO = sellOrderOrigCardListDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public EntityStockService getEntityStockService() {
		return entityStockService;
	}

	public void setEntityStockService(EntityStockService entityStockService) {
		this.entityStockService = entityStockService;
	}
}
