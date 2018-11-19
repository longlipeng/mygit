package com.huateng.univer.businessbatch.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.constant.Const;
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

public class RechargeBatchServiceImpl extends BasicBatchServiceImpl {
	
	private Logger logger = Logger.getLogger(RechargeBatchServiceImpl.class);
	/**
	 * 公共工具类DAO
	 */

	private DataBaseDAO databaseDAO;
	private SellOrderDAO sellOrderDAO;
	public DataBaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DataBaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	// 类型：激活充值
	private static final String BATCH_TYPE = "02";
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	@Override
	public String getBatchType() {
		return BATCH_TYPE;
	}

	@Override
	public void commMessage(BatchFileDTO batchFileDTO)
			throws BizServiceException {
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_RECHARGE_ORDER);
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
		}

	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	@Override
	protected boolean orderStateChange(BatchSum sum) {
		//查询订单信息
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sum.getOrderId());
		//如果订单状态已经为完成状态,直接返回true
		if (OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrder.getOrderState())||
				OrderConst.ORDER_STATE_CREDIT_SUCCESS.equals(sellOrder.getOrderState())) {
			return true;
		}
		//检查批次状态
		//失败总数 > 0 或者 成功总数 + 失败总数 != 总记录数, 将订单状态置为失败
		if (Long.parseLong(sum.getFailCnt().trim())> 0 ||
				Long.parseLong(sum.getSucCnt().trim()) + Long.parseLong(sum.getFailCnt().trim()) != Long.parseLong(sum.getTotCnt().trim())||
				Long.parseLong(sum.getSucAmt().trim()) + Long.parseLong(sum.getFailAmt().trim()) != Long.parseLong(sum.getTotAmt().trim())) {
			
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
		} else {
			sellOrder.setOrderState(OrderConst.ORDER_STATE_CREDIT_SUCCESS);
		}
		
		//更新订单状态
		SellOrderExample updateDTO = new SellOrderExample();
		updateDTO.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDAO.updateByExampleSelective(sellOrder, updateDTO);
		return true;	
	}

	@Override
	public void insertBatch(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
		List<BatchFileDetailDTO> list = new ArrayList<BatchFileDetailDTO>();
		for (BatchParamInterface it : businessList) {
			SellOrderListDTO dto = (SellOrderListDTO) it;
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			batchFileDetailDTO.setTxnSeq(dto.fetchSeq());
			batchFileDetailDTO.setSmltDate(DateUtil.getCurrentDateStr());
			batchFileDetailDTO.setBatchType(BATCH_TYPE);
			batchFileDetailDTO.setTxnAmt(dto.calcAmt());
			batchFileDetailDTO.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
			batchFileDetailDTO.setCardNo(dto.getCardNo());
			batchFileDetailDTO.setAccType(dto.getServiceId());
			batchFileDetailDTO.setExpDate(dto.getValidityPeriod());
			batchFileDetailDTO.setRecCrtTs(DateUtil.getCurrentTime());
			batchFileDetailDTO.setRecUpdTs(DateUtil.getCurrentTime());
			list.add(batchFileDetailDTO);
		}
		databaseDAO.batchInsert("RECHARGE.insertRechargeTxn", list);
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}
	
}
